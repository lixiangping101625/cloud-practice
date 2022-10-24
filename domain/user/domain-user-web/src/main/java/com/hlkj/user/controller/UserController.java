package com.hlkj.user.controller;

import com.hlkj.pojo.UnifyResponse;
import com.hlkj.user.UserApplicationProperties;
import com.hlkj.user.pojo.User;
import com.hlkj.user.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author Lixiangping
 * @createTime 2022年10月17日 17:40
 * @decription:
 */
@RestController
@RequestMapping("/user")
@Api(value = "商品接口", tags = "用户信息展示相关接口")
@Slf4j
@RefreshScope
public class UserController {
    @Resource
    private UserService userService;

    @ApiOperation(value = "查询所有", tags = "查询所有用户信息")
    @GetMapping("/listAll")
    public List<User> list(){
        return userService.listAll();
    }

    @ApiOperation(value = "查询用户详情", tags = "查询用户详情")
    @ApiImplicitParams(
            @ApiImplicitParam(name = "id",value = "用户id", required = true)
    )
    @GetMapping("/info")
    public User detail(@RequestParam(name = "id") Long id){
        return userService.detail(id);
    }

    @Resource
    private UserApplicationProperties userApplicationProperties;
    @PostMapping("/login")
    public String login(@RequestBody User user){
        if (userApplicationProperties.isDisableRegistration()) {
            log.info("user registration request is blocked - {}~", user.getUsername());
            // 话术
            return "当前注册用户过多，请稍后再试~";
        }
        return userService.login(user);
    }

}
