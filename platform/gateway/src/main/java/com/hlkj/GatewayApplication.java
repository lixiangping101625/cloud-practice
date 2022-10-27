package com.hlkj;

import com.hlkj.auth.AuthService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * @author Lixiangping
 * @createTime 2022年10月27日 15:52
 * @decription:
 */
@EnableDiscoveryClient
@SpringBootApplication
//@EnableFeignClients(basePackages = "com.hlkj")或
@EnableFeignClients(basePackageClasses = AuthService.class)
public class GatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(GatewayApplication.class, args);
    }

}
