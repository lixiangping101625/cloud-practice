package com.hlkj.order.fallback.itemservice;

import com.hlkj.user.pojo.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * @author Lixiangping
 * @createTime 2022年10月22日 22:00
 * @decription:
 */
@Component
@RequestMapping("/jokejoke")
public class UserFallback implements UserFeignClient {

    @Override
    public List<User> listAll() {
        return null;
    }

    @Override
    public User detail(Long id) {

        return new User();
    }

    @Override
    public String error() {
        return "进入了feign的降级方法";
//        return null;
    }

    @Override
    public String timeout(Integer count) {
        return null;
    }
}
