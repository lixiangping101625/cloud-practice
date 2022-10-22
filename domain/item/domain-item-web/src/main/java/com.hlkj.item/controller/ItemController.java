package com.hlkj.item.controller;

import com.hlkj.item.pojo.Item;
import com.hlkj.item.service.ItemService;
import com.hlkj.pojo.UnifyResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author Lixiangping
 * @createTime 2022年10月22日 11:58
 * @decription:
 */
@RestController
@RequestMapping("/item")
public class ItemController {

    @Resource
    private ItemService itemService;

    @GetMapping("/listAll")
    public UnifyResponse listAll(){
        List<Item> items = itemService.listAll();
        return UnifyResponse.buildSuccess(items);
    }

    @GetMapping("/info/{id}")
    public UnifyResponse detail(@PathVariable(name = "id") Long id){
        Item item = itemService.info(id);
        return UnifyResponse.buildSuccess(item);
    }
}
