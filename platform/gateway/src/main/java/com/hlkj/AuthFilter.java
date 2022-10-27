package com.hlkj;

import com.hlkj.auth.AuthResponse;
import com.hlkj.auth.AuthService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.core.Ordered;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import javax.annotation.Resource;

/**
 * @author Lixiangping
 * @createTime 2022年10月26日 14:58
 * @decription:
 */
@Component("authFilter")
@Slf4j
public class AuthFilter implements GatewayFilter, Ordered {

    private static final String AUTH = "Authorization";
    private static final String USERNAME = "hlkj-user-name";

    @Resource
    private AuthService authService;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        log.info("Auth start");
        ServerHttpRequest request = exchange.getRequest();
        HttpHeaders headers = request.getHeaders();
        String token = headers.getFirst(AUTH);
        String username = headers.getFirst(USERNAME);

        ServerHttpResponse response = exchange.getResponse();
        if (StringUtils.isEmpty(token)) {
            log.info("token not found");
            response.setStatusCode(HttpStatus.UNAUTHORIZED);
            return response.setComplete();
        }
        AuthResponse response1 = authService.verify(username, token);
        if (response1.getCode() != 1L) {
            log.info("invalid token");
            response.setStatusCode(HttpStatus.FORBIDDEN);
            return response.setComplete();
        }
        ServerHttpRequest.Builder mutate = request.mutate();
        mutate.header("hlkj-user-name", username);
        ServerHttpRequest buildRequest = mutate.build();

        response.setStatusCode(HttpStatus.OK);
        response.getHeaders().add("hlkj-username", username);
        return chain.filter(exchange.mutate()
                .request(buildRequest)
                .response(response)
                .build());
    }

    @Override
    public int getOrder() {
        return 0;
    }
}
