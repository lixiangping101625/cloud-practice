package com.hlkj;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.config.server.EnableConfigServer;

/**
 * @author Lixiangping
 * @createTime 2022年10月23日 15:30
 * @decription:
 */
@SpringBootApplication
@EnableConfigServer
@EnableDiscoveryClient
public class ConfigBusServerApplication {

    public static void main(String[] args) {
        new SpringApplicationBuilder(ConfigBusServerApplication.class)
                .web(WebApplicationType.SERVLET)
                .run(args);
    }
}
