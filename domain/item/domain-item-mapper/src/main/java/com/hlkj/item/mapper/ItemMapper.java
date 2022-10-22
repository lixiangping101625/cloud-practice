package com.hlkj.item.mapper;

import com.hlkj.item.pojo.Item;
import com.hlkj.my.mapper.MyMapper;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * @author Lixiangping
 * @createTime 2022年10月22日 11:41
 * @decription:
 */
public interface ItemMapper extends MyMapper<Item> {

    //fixme 注意搜索应该划分到主搜模块
    List<Item> list();

    Item getDetail(@Param("id") Long id);
}
