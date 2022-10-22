package com.hlkj.order;

import com.hlkj.order.fallback.itemservice.UserFeignClient;
import com.hlkj.user.service.UserService;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

/**
 * @author Lixiangping
 * @createTime 2022年10月22日 11:57
 * @decription:
 */
@SpringBootApplication
@EnableDiscoveryClient
@MapperScan(basePackages = "com.hlkj.order.mapper")
//@EnableFeignClients(basePackages = {"com.hlkj.user.service"})
@EnableFeignClients(clients = {
        UserFeignClient.class
})
public class OrderApplication {

    @Bean
    public RestTemplate restTemplate(){
        return new RestTemplate();
    }

    public static void main(String[] args) {
        new SpringApplicationBuilder(OrderApplication.class)
                .web(WebApplicationType.SERVLET)
                .run(args);
    }

}
