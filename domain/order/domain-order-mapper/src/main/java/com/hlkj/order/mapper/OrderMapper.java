package com.hlkj.order.mapper;

import com.hlkj.my.mapper.MyMapper;
import com.hlkj.order.pojo.Order;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * @author Lixiangping
 * @createTime 2022年10月17日 16:51
 * @decription:
 */
public interface OrderMapper extends MyMapper<Order> {

    //fixme 注意搜索应该划分到主搜模块
    List<Order> list();

    Order getDetail(@Param("id") Long id);

}