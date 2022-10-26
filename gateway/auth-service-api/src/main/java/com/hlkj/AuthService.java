package com.hlkj;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author Lixiangping
 * @createTime 2022年10月26日 11:36
 * @decription:
 */
@FeignClient("auth-service")
public interface AuthService {

    /**
     * 登录
     * @param username
     * @param password
     * @return
     */
    @PostMapping("/login")
    @ResponseBody
    public AuthResponse login(@RequestParam(name = "username") String username,
                              @RequestParam(name = "password") String password);

    /**
     * token校验
     * @param username
     * @param token
     * @return
     */
    @GetMapping("/verify")
    @ResponseBody
    public AuthResponse verify(@RequestParam(name = "username") String username,
                              @RequestParam(name = "token") String token);

    /**
     * 刷新token
     * @param refresh
     * @return
     */
    @PostMapping("/refresh")
    @ResponseBody
    public AuthResponse refresh(@RequestParam(name = "refresh") String refresh);

}
