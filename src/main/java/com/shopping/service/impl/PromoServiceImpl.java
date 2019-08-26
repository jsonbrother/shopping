package com.shopping.service.impl;

import com.shopping.dao.IPromoDao;
import com.shopping.dao.dataObject.PromoDO;
import com.shopping.service.IPromoService;
import com.shopping.service.dto.PromoDTO;
import org.joda.time.DateTime;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

/**
 * Created by TongHaiJun
 * 2019/8/25 20:38
 */
@Service
public class PromoServiceImpl implements IPromoService {

    @Autowired
    private IPromoDao iPromoDao;

    @Override
    public PromoDTO getPromoByItemId(Integer itemId) {

        // 1.获取对应商品的秒杀活动信息
        PromoDO promoDO = iPromoDao.selectByItemId(itemId);

        // 2.dataObject -> DTO
        PromoDTO promoDTO = this.convertPromoDTOFromPromoDO(promoDO);
        if (promoDTO == null) {
            return null;
        }

        // 3.判断当前时间是否秒杀活动即将开始,还是正在进行
        if (promoDTO.getStartDate().isAfterNow()) {
            promoDTO.setPromoStatus(1);
        } else if (promoDTO.getEndDate().isBeforeNow()) {
            promoDTO.setPromoStatus(3);
        } else {
            promoDTO.setPromoStatus(2);
        }

        return promoDTO;
    }

    private PromoDTO convertPromoDTOFromPromoDO(PromoDO promoDO) {
        if (promoDO == null) {
            return null;
        }

        PromoDTO promoDTO = new PromoDTO();
        BeanUtils.copyProperties(promoDO, promoDTO);
        promoDTO.setPromoItemPrice(new BigDecimal(promoDO.getPromoItemPrice()));
        promoDTO.setStartDate(new DateTime(promoDO.getStartDate()));
        promoDTO.setEndDate(new DateTime(promoDO.getEndDate()));

        return promoDTO;
    }
}
