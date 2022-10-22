package com.hlkj.order.vo;

import com.hlkj.order.pojo.Order;
import lombok.Data;

/**
 * @author Lixiangping
 * @createTime 2022年10月18日 15:51
 * @decription:
 */
@Data
public class OrderVO extends Order {

    private Long userId;
    private String username;
    private String password;
    private String mobile;
}
