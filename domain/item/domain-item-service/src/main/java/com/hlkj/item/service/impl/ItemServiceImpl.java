package com.hlkj.item.service.impl;

import com.hlkj.item.mapper.ItemMapper;
import com.hlkj.item.pojo.Item;
import com.hlkj.item.service.ItemService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author Lixiangping
 * @createTime 2022年10月22日 11:52
 * @decription:
 */
@RestController //eureka时基于http的服务治理框架，所以service提供的服务需要声明成controller
//@Service //注意不需要
@Slf4j
public class ItemServiceImpl implements ItemService {

    @Resource
    private ItemMapper itemMapper;

    @Override
    public List<Item> listAll() {
        return itemMapper.list();
    }

    @Override
    public Item info(@RequestParam(name = "id") Long id) {
        return itemMapper.getDetail(id);
    }
}
