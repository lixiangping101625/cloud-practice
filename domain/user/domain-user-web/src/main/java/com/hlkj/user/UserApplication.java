package com.hlkj.user;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @author Lixiangping
 * @createTime 2022年10月22日 11:57
 * @decription:
 */
@SpringBootApplication
@EnableDiscoveryClient
@MapperScan(basePackages = "com.hlkj.user.mapper")
//@EnableFeignClients(basePackages = "") //这里由于用户微服务没有调用其他服务所以不需要Feign扫包
public class UserApplication {

    public static void main(String[] args) {
        new SpringApplicationBuilder(UserApplication.class)
                .web(WebApplicationType.SERVLET)
                .run(args);
    }

}
