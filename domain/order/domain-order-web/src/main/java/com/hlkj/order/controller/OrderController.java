package com.hlkj.order.controller;

import com.hlkj.order.service.OrderService;
import com.hlkj.pojo.UnifyResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author Lixiangping
 * @createTime 2022年10月18日 16:12
 * @decription:
 */
@RestController
@RequestMapping("/order")
public class OrderController {

    @Resource
    private OrderService orderService;

    @GetMapping("/listAll")
    public UnifyResponse listAll(){
        return UnifyResponse.buildSuccess(orderService.listAll());
    }

    @GetMapping("/detail/{id}")
    public UnifyResponse detail(@PathVariable(name = "id") Long id){
        return UnifyResponse.buildSuccess(orderService.detail(id));
    }

}