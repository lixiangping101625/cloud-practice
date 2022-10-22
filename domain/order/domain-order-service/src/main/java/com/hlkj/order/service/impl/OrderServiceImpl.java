package com.hlkj.order.service.impl;

import com.hlkj.order.mapper.OrderMapper;
import com.hlkj.order.pojo.Order;
import com.hlkj.order.service.OrderService;
import com.hlkj.order.vo.OrderVO;
import com.hlkj.user.pojo.User;
import com.hlkj.user.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author Lixiangping
 * @createTime 2022年10月17日 17:23
 * @decription:
 */
@RestController //eureka时基于http的服务治理框架，所以service提供的服务需要声明成controller
//@Service //注意不需要
@Slf4j
public class OrderServiceImpl implements OrderService {
    private final static Logger logger = LoggerFactory.getLogger(OrderServiceImpl.class);

    @Resource
    private OrderMapper orderMapper;

//    @Resource
//    private RestTemplate restTemplate;//发起服务调用 //todo feign章节改为服务接口调用
//    @Resource
//    private LoadBalancerClient client;//找寻服务地址 //todo feign章节改为服务接口调用
    //使用Feign调用，实际注入的是feign框架生成的代理类
    @Resource
    private UserService userService;

    @Override
    public List<Order> listAll() {
        logger.info("查询所有订单");
        return orderMapper.list();
    }
    @Override
    public OrderVO detail(@RequestParam(name = "id") Long id) {
        Order order = orderMapper.getDetail(id);
        Long userId = order.getUserId();

//        ServiceInstance instance = client.choose("CLOUD-USER-SERVICE");
//        String target = String.format("http://%s:%s/users-api/info?id=%s",
//                instance.getHost(),
//                instance.getPort(),
//                userId);
//        User user = restTemplate.getForObject(target, User.class);

        User user = userService.detail(id);

        OrderVO orderVO = new OrderVO();
        BeanUtils.copyProperties(order, orderVO);
        orderVO.setUserId(userId);
        orderVO.setUsername(user.getUsername());
        orderVO.setPassword(user.getPassword());
        orderVO.setMobile(user.getMobile());
        String itemsJson = order.getItemsJson();
        return orderVO;
    }

}