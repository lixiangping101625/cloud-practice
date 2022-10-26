package com.hlkj.item.controller;

import com.hlkj.item.service.Product;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Lixiangping
 * @createTime 2022年10月26日 09:58
 * @decription: 建议秒杀接口
 */
@RestController
@RequestMapping("gateway")
public class GatewayController {

    public static final Map<Long, Product> items = new ConcurrentHashMap<>();
    @GetMapping("detail")
    public Product detail(@RequestParam(name = "pId") Long pId){
        if (!items.containsKey(pId)) {
            Product product = Product.builder()
                    .productId(pId)
                    .description("好吃不贵")
                    .stock(100L)
                    .build();
            items.putIfAbsent(pId, product);//当不存在的时候添加
        }
        return items.get(pId);
    }

    @PostMapping("placeOrder")
    public String buy(@RequestParam(name = "pId") Long pId){
        Product product = items.get(pId);
        if (product == null) {
            return "product not found";
        } else if(product.getStock() <= 0L){
            return "sold out";
        }
        synchronized (product) {
            if (product.getStock() <= 0L){
                return "sold out";
            }
            product.setStock(product.getStock() - 1);
        }
        return "order placed";
    }

}
