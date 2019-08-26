package com.shopping.dao.dataObject;

import lombok.Data;

import java.math.BigDecimal;

/**
 * Created by TongHaiJun
 * 2019/8/25 1:49
 */
@Data
public class OrderDO {

    private String id;

    /**
     * 购买的用户id
     */
    private Integer userId;

    /**
     * 购买的商品id
     */
    private Integer itemId;

    /**
     * 购买商品的单价
     */
    private Double itemPrice;

    /**
     * 购买数量
     */
    private Integer amount;

    /**
     * 订单价格
     */
    private Double orderPrice;

    /**
     * 秒杀商品id（如果非空 itemPrice则是秒杀价格）
     */
    private Integer promoId;
}
