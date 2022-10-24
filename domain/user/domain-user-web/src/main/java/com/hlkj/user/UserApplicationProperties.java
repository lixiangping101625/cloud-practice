package com.hlkj.user;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Configuration;

/**
 * @author Lixiangping
 * @createTime 2022年10月24日 11:19
 * @decription: 配置中心拉取的配置属性
 */
@Configuration
@RefreshScope //注册功能希望从外部控制它的开启和关闭（运行期间可以不断刷新）
@Data
public class UserApplicationProperties {

    @Value("${userservice.registration.disabled}")
    private boolean disableRegistration;
}
