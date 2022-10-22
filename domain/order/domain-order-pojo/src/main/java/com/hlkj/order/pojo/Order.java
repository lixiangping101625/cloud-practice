package com.hlkj.order.pojo;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * @author Lixiangping
 * @createTime 2022年10月17日 16:42
 * @decription: 商品
 */
@Table(name = "order")
@Data
public class Order implements Serializable {

    @Id
    private Long id;

    @Column(name = "title")
    private String title;

    @Column(name = "total_price")
    private Float totalPrice;

    @Column(name = "addr")
    private String addr;

    @Column(name = "items_json")
    private String itemsJson;

    @Column(name = "count")
    private Integer count;

    @Column(name = "user_id")
    private Long userId;
}