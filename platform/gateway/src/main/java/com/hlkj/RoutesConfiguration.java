package com.hlkj;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cloud.gateway.filter.ratelimit.KeyResolver;
import org.springframework.cloud.gateway.filter.ratelimit.RateLimiter;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;

import javax.annotation.Resource;

/**
 * @author Lixiangping
 * @createTime 2022年10月27日 16:00
 * @decription: 路由规则配置
 */
@Configuration
public class RoutesConfiguration {

    @Resource
    private KeyResolver hostNameResolver;
    @Resource
    @Qualifier("redisLimitItem")
    private RateLimiter redisLimitItem;

    @Bean//千万不要忘了
    @Order
    public RouteLocator routes(RouteLocatorBuilder builder){
        /**
         * 只对controller配置
         */
        return builder.routes()
                /** 商品controller */
                .route(r -> r.path("/item/**"/*,"/passport/**", "/userInfo/**" */)
                .filters(f -> f.requestRateLimiter(
                        c -> {
                            c.setKeyResolver(hostNameResolver);
                            c.setRateLimiter(redisLimitItem);
                            c.setStatusCode(HttpStatus.BAD_GATEWAY);
                        }
                ))
//                .filters(f -> f.stripPrefix(1))
                .uri("lb://CLOUD-ITEM-SERVICE"))
                /** 用户controller */
                .route(r -> r.path("/user/**")
                .uri("lb://CLOUD-USER-SERVICE"))
                /** 订单controller */
                .route(r -> r.path("/order/**")
                .uri("lb://CLOUD-ORDER-SERVICE"))
                .build();
    }
}
