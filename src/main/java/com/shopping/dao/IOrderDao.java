package com.shopping.dao;

import com.shopping.dao.dataObject.OrderDO;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by TongHaiJun
 * 2019/8/25 17:14
 */
@Repository
public interface IOrderDao {

    void insert(OrderDO orderDO);

    OrderDO selectById(String id);

    List<OrderDO> selectAll();
}
