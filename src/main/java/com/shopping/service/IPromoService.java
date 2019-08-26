package com.shopping.service;

import com.shopping.service.dto.PromoDTO;

/**
 * Created by TongHaiJun
 * 2019/8/25 20:38
 */
public interface IPromoService {

    /**
     * 根据itemId获得商品信息
     */
    PromoDTO getPromoByItemId(Integer itemId);
}
