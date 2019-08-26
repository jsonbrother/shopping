package com.shopping.dao.dataObject;

import lombok.Data;

/**
 * 商品库存.
 * Created by TongHaiJun
 * 2019/8/24 1:07
 */
@Data
public class ItemStockDO {

    /**
     * 库存id
     */
    private Integer id;

    /**
     * 商品库存
     */
    private Integer stock;

    /**
     * 商品id
     */
    private Integer itemId;

}
