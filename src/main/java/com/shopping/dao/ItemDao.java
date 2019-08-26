package com.shopping.dao;

import com.shopping.dao.dataObject.ItemDO;
import com.shopping.exception.BusinessException;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by TongHaiJun
 * 2019/8/24 1:10
 */
@Repository
public interface ItemDao {

    void insert(ItemDO itemDO);

    void update(ItemDO itemDO);

    ItemDO selectById(Integer id);

    List<ItemDO> selectAll();

    /**
     * 商品销量新增
     */
    int increaseSales(@Param("id") Integer id, @Param("amount") Integer amount) throws BusinessException;
}
