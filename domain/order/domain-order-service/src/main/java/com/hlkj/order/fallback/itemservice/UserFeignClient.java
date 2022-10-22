package com.hlkj.order.fallback.itemservice;

import com.hlkj.user.service.UserService;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * @author Lixiangping
 * @createTime 2022年10月22日 21:46
 * @decription:
 */
@FeignClient(value = "cloud-user-service", fallback = UserFallback.class)
public interface UserFeignClient extends UserService {
}
