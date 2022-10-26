package com.hlkj;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;

import java.time.ZonedDateTime;

/**
 * @author Lixiangping
 * @createTime 2022年10月25日 17:44
 * @decription:
 */
@Configuration
public class GatewayConfiguration {

    @Bean
    @Order
    public RouteLocator customizedRoutes(RouteLocatorBuilder builder){
        return builder.routes()
                .route("java_path_route",//路由id
                        r -> r.path("/java-path/**")//只接受这个路径的
//                        .and().method("GET")//并且是GET请求
//                        .and().header("name")//header中必须要那么属性
                        //filter和逻辑相关
                        .filters(f -> f.stripPrefix(1)
                            .addResponseHeader("java-param", "gateway-config")
                        )
                        //目标地址
                        .uri("lb://CLOUD-ITEM-SERVICE")
                )
                .route(
                        "secKill",
                        r -> r.path("/seckill/**")
                                .and().after(ZonedDateTime.now().plusMinutes(1L))//服务启动加载完成往后推迟1分钟生效
//                                .and().before()
//                                .and().between()
                        .filters(f -> f.stripPrefix(1))
                        //目标地址
                        .uri("lb://CLOUD-ITEM-SERVICE")
                    )
                .build();
    }

}
