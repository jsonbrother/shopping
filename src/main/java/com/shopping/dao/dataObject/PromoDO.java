package com.shopping.dao.dataObject;

import lombok.Data;

import java.util.Date;

/**
 * Created by TongHaiJun
 * 2019/8/25 19:18
 */
@Data
public class PromoDO {

    private Integer id;

    /**
     * 秒杀活动名称
     */
    private String PromoName;

    /**
     * 秒杀活动开始时间
     */
    private Date startDate;

    /**
     * 秒杀活动结束时间
     */
    private Date endDate;

    /**
     * 秒杀活动的商品id
     */
    private Integer itemId;

    /**
     * 秒杀活动的商品的价格
     */
    private Double promoItemPrice;
}
