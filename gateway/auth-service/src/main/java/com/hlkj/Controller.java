package com.hlkj;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.UUID;

/**
 * @author Lixiangping
 * @createTime 2022年10月26日 11:57
 * @decription:
 */
@RestController
@Slf4j
public class Controller implements AuthService{

    @Resource
    private JwtService jwtService;
    @Resource
    private RedisTemplate redisTemplate;

    @PostMapping("/login")
    public AuthResponse login(@RequestParam String username,
                              @RequestParam String password){
        Account account = Account.builder()
                .username(username)
                .build();
        // 省去了数据库验证
        String token = jwtService.token(account);
        account.setToken(token);
        account.setRefreshToken(UUID.randomUUID().toString());

        //存入redis
        redisTemplate.opsForValue().set(account.getRefreshToken(), account);

        return AuthResponse.builder()
                .account(account)
                .code(ResponseCode.SUCCESS)
                .build();
    }

    @PostMapping("/refresh")
    public AuthResponse refresh(@RequestParam String refreshToken){
        Account account = (Account) redisTemplate.opsForValue().get(refreshToken);
        if (account == null) {
            return AuthResponse.builder().code(ResponseCode.USER_NOT_FOUND)
                    .build();
        }

        String token = jwtService.token(account);
        account.setToken(token);
        account.setRefreshToken(UUID.randomUUID().toString());
        redisTemplate.delete(account.getRefreshToken());
        redisTemplate.opsForValue().set(account.getRefreshToken(), account);

        return AuthResponse.builder()
                .account(account)
                .code(ResponseCode.SUCCESS)
                .build();
    }

    @GetMapping("/verify")
    public AuthResponse verify(@RequestParam String token,
                               @RequestParam String username){
        boolean success = jwtService.verify(token, username);
        return AuthResponse.builder()
                .code(success ? ResponseCode.SUCCESS:ResponseCode.INVALID_TOKEN)
                .build();
    }

    @Override
    public AuthResponse delete(Account account) {
        AuthResponse result = new AuthResponse();

        AuthResponse response = verify(account.getRefreshToken(), account.getUsername());
        if (ResponseCode.SUCCESS.equals(response.getCode())){
            redisTemplate.delete(account.getRefreshToken());
            redisTemplate.delete(account.getToken());
            result.setCode(ResponseCode.SUCCESS);
        } else
        {
            result.setCode(ResponseCode.USER_NOT_FOUND);
        }
        return result;
    }

}
