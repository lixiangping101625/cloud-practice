package com.hlkj.item.service;

import lombok.Builder;
import lombok.Data;

/**
 * @author Lixiangping
 * @createTime 2022年10月26日 10:00
 * @decription: 用于断言测试
 */
@Data
@Builder
public class Product {

    private Long productId;
    private String description;
    private Long stock;

}
