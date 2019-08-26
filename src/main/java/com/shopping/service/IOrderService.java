package com.shopping.service;

import com.shopping.exception.BusinessException;
import com.shopping.service.dto.OrderDTO;

/**
 * Created by TongHaiJun
 * 2019/8/25 17:19
 */
public interface IOrderService {

    /**
     * 订单创建
     */
    OrderDTO createOrder(Integer userId, Integer itemId, Integer amount, Integer promoId) throws BusinessException;
}
