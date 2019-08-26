package com.shopping.dao.dataObject;

import lombok.Data;


/**
 * 商品.
 * Created by TongHaiJun
 * 2019/8/24 1:06
 */
@Data
public class ItemDO {

    private Integer id;

    /**
     * 商品名称
     */
    private String title;

    /**
     * 商品价格
     */
    private Double price;

    /**
     * 商品详情
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
}
