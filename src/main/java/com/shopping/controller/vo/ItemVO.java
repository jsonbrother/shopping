package com.shopping.controller.vo;

import lombok.Data;
import org.joda.time.DateTime;

import java.math.BigDecimal;

/**
 * Created by TongHaiJun
 * 2019/8/24 2:09
 */
@Data
public class ItemVO {

    private Integer id;

    /**
     * 商品名称
     */
    private String title;

    /**
     * 商品价格
     */
    private BigDecimal price;

    /**
     * 商品库存
     */
    private Integer stock;

    /**
     * 商品描述
     */
    private String description;

    /**
     * 商品销量
     */
    private Integer sales;

    /**
     * 商品图片
     */
    private String imgUrl;

    /**
     * 记录商品是否在秒杀活动中,以及对应的状态：0-没有活动 1-待开始 2-进行中
     */
    private Integer promoStatus;

    /**
     * 秒杀活动Id
     */
    private Integer promoId;

    /**
     * 秒杀活动的价格
     */
    private BigDecimal promoPrice;

    /**
     * 秒杀活动的开始时间
     */
    private String promoStartDate;


}
