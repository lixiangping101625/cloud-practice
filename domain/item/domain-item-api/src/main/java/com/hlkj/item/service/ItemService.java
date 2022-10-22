package com.hlkj.item.service;

import com.hlkj.item.pojo.Item;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @author Lixiangping
 * @createTime 2022年10月22日 11:45
 * @decription: 接口层现在需要对外提供服务，所以需要添加@RequestMapping等注解将其声明为controller。
 *              这些注解会被继承，同时避免了下游服务调用时还要配置寻址路径
 */
@RequestMapping("item-api")
public interface ItemService {

    @GetMapping("listAll")
    List<Item> listAll();

    @GetMapping("info")
    Item info(@RequestParam(name = "id") Long id);

}
