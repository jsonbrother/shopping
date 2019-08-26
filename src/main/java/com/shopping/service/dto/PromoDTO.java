package com.shopping.service.dto;

import lombok.Data;
import org.joda.time.DateTime;

import java.math.BigDecimal;

/**
 * 秒杀传输对象.
 * Created by TongHaiJun
 * 2019/8/25 19:09
 */
@Data
public class PromoDTO {

    private Integer id;

    /**
     * 秒杀活动名称
     */
    private String PromoName;

    /**
     * 秒杀活动开始时间
     */
    private DateTime startDate;

    /**
     * 秒杀活动结束时间
     */
    private DateTime endDate;

    /**
     * 秒杀活动的商品id
     */
    private Integer itemId;

    /**
     * 秒杀活动的商品的价格
     */
    private BigDecimal promoItemPrice;

    /**
     * 秒杀活动状态：1-未开始 2-进行中 3-已结束
     */
    private Integer promoStatus;
}
