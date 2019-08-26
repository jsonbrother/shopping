package com.shopping.service;

import com.shopping.exception.BusinessException;
import com.shopping.service.dto.ItemDTO;

import java.util.List;

/**
 * Created by TongHaiJun
 * 2019/8/24 1:30
 */
public interface IItemService {

    /**
     * 创建商品
     */
    ItemDTO createItem(ItemDTO itemDTO) throws BusinessException;

    /**
     * 商品详情浏览
     */
    ItemDTO getItemById(Integer id);

    /**
     * 商品列表浏览
     */
    List<ItemDTO> listItem();

    /**
     * 库存扣减
     */
    boolean decreaseStock(Integer itemId, Integer amount) throws BusinessException;

    /**
     * 商品销量增加
     */
    void increaseSales(Integer itemId, Integer amount) throws BusinessException;
}
