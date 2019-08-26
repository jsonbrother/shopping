package com.shopping.service.dto;

import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * Created by TongHaiJun
 * 2019/8/24 0:47
 */
@Data
public class ItemDTO {

    private Integer id;

    /**
     * 商品名称
     */
    @NotBlank(message = "商品名称不为空")
    private String title;

    /**
     * 商品价格
     */
    @NotNull(message = "商品价格不为空")
    @Min(value = 0, message = "商品价格必须大于0")
    private BigDecimal price;

    /**
     * 商品库存
     */
    @NotNull(message = "商品库存不能不填")
    private Integer stock;

    /**
     * 商品描述
     */
    @NotBlank(message = "商品描述信息不为空")
    private String description;

    /**
     * 商品销量
     */
    private Integer sales;

    /**
     * 商品图片
     */
    @NotBlank(message = "商品图片不为空")
    private String imgUrl;

    /**
     * 秒杀活动
     */
    private PromoDTO promoDTO;
}
