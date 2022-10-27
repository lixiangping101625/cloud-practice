package com.hlkj.auth;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.UUID;

/**
 * @author Lixiangping
 * @createTime 2022年10月26日 11:57
 * @decription:
 */
@RestController
@Slf4j
@RequestMapping("/auth")
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

    /**
     * token校验一般放在网关层或者各个微服务模块：减少每次请求、符合规范
     * @param token
     * @param username
     * @return
     */
    @GetMapping("/verify")
    public AuthResponse verify(@RequestParam String token,
                               @RequestParam String username){
        boolean success = jwtService.verify(token, username);
        return AuthResponse.builder()
                .code(success ? ResponseCode.SUCCESS:ResponseCode.INVALID_TOKEN)
                .build();
    }

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