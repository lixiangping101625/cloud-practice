package com.hlkj;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Configuration;

/**
 * @author Lixiangping
 * @createTime 2022年10月24日 17:58
 * @decription:
 */

@Configuration
@RefreshScope //注册功能希望从外部控制它的开启和关闭（运行期间可以不断刷新）
@Data
public class DynamicMessages {

    @Value("${userservice.registration.disabled}")
    private boolean disableRegistration;
}
