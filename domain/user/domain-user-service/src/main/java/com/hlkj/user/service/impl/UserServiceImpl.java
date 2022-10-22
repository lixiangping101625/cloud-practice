package com.hlkj.user.service.impl;

import com.hlkj.user.mapper.UserMapper;
import com.hlkj.user.pojo.User;
import com.hlkj.user.service.UserService;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
public class UserServiceImpl implements UserService {

    private final static Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    @Resource
    private UserMapper userMapper;

    @HystrixCommand(
            commandKey = "listFail",//全局唯一的标识符（默认是函数名称listAll）
            groupKey = "list", //全局服务分组。用于组织仪表盘、统计信息。默认是类名
//            fallbackMethod = "listFail", //同一个类中，public和private都可以
//            ignoreExceptions = {IllegalArgumentException.class}, //配置例外的情况（列表中的exception不会触发降级）
            threadPoolKey = "threadPoolA", //线程组(多个服务可以公用一个线程组)
            threadPoolProperties = {
                    @HystrixProperty(name = "coreSize", value = "20"),//核心线程数
                    //size>0, linkedBlockingQueue ->请求等待队列
                    //默认-1, SynchronousQueue -> 不存储元素的阻塞队列（建议读源码）
                    @HystrixProperty(name = "maxQueueSize", value = "40"),
                    // maxQueueSize=-1时无效。队列没有达到maxQueueSize依然拒绝
                    @HystrixProperty(name = "queueSizeRejectionThreshold", value = "15"),
                    // 统计窗口（线程池）持续时间
                    @HystrixProperty(name = "metrics.rollingStats.timeInMilliseconds", value = "1024"),
                    // 窗口内桶子的数量
                    @HystrixProperty(name = "metrics.rollingStats.numBuckets", value = "18")
            })
    public List<User> listAll() {
        logger.info("查询用户列表");
        return userMapper.list();
    }
    public User detail(@RequestParam(name = "id") Long id) {
        return userMapper.getDetail(id);
    }

    @Override
    public String error() {
        throw new RuntimeException("black sheep");
    }

    @HystrixCommand
    @Override
    public String timeout(Integer count) {
        log.info("进入cloud-user-api的timeout方法");
        //线程挂起，模拟接口超时
        while (--count > 0) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return "success";
    }
}