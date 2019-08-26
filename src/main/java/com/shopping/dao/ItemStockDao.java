package com.shopping.dao;

import com.shopping.dao.dataObject.ItemStockDO;
import com.shopping.exception.BusinessException;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * Created by TongHaiJun
 * 2019/8/24 1:19
 */
@Repository
public interface ItemStockDao {

    void insert(ItemStockDO itemStockDO);

    void update(ItemStockDO itemStockDO);

    ItemStockDO selectByItemId(Integer itemId);

    /**
     * 减库存
     * 思路: 通过影响的条数来判断是否成功
     * 优点: 比起传统的方式,不用连接两次数据库和做java运算
     */
    int decreaseStock(@Param("itemId") Integer itemId, @Param("amount") Integer amount) throws BusinessException;
}
