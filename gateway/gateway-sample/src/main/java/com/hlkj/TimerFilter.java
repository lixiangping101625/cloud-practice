package com.hlkj;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * @author Lixiangping
 * @createTime 2022年10月26日 10:43
 * @decription: 自定义过滤器
 */
@Slf4j
@Component
public class TimerFilter implements GatewayFilter, Ordered {

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        StopWatch timer = new StopWatch();
        timer.start(exchange.getRequest().getURI().getRawPath());//计时器打印log

//        exchange.getAttributes().put("requestTimeBegin", System.currentTimeMillis());
//        exchange.getRequest();
//        exchange.getResponse();
        return chain.filter(exchange).then(//接口请求完成后
            Mono.fromRunnable(()->{
                timer.stop();
                log.info(timer.prettyPrint());
            })
        );
    }

    @Override
    public int getOrder() {
        return 0;//越小优先级越高
    }
}
