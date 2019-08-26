package com.shopping.service.impl;

import com.shopping.dao.IOrderDao;
import com.shopping.dao.dataObject.OrderDO;
import com.shopping.enums.BusinessErrorEnum;
import com.shopping.exception.BusinessException;
import com.shopping.service.IItemService;
import com.shopping.service.IOrderService;
import com.shopping.service.IUserService;
import com.shopping.service.dto.ItemDTO;
import com.shopping.service.dto.OrderDTO;
import com.shopping.service.dto.UserDTO;
import org.joda.time.format.DateTimeFormat;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;

/**
 * Created by TongHaiJun
 * 2019/8/25 17:19
 */
@Service
public class OrderServiceImpl implements IOrderService {

    @Autowired
    private IOrderDao iOrderDao;

    @Autowired
    private IUserService iUserService;

    @Autowired
    private IItemService iItemService;


    @Override
    @Transactional
    public OrderDTO createOrder(Integer userId, Integer itemId, Integer amount, Integer promoId) throws BusinessException {
        // 1.校验下单状态,下单的商品是否存在,用户是否合法,购买数量是否正确
        UserDTO userDTO = iUserService.selectById(userId);
        if (userDTO == null) {
            throw new BusinessException(BusinessErrorEnum.PARAMETER_VALIDATION_ERROR, "用户信息不存在");
        }

        ItemDTO itemDTO = iItemService.getItemById(itemId);
        if (itemDTO == null) {
            throw new BusinessException(BusinessErrorEnum.PARAMETER_VALIDATION_ERROR, "商户信息不存在");
        }

        if (amount <= 0 || amount > 99) {
            throw new BusinessException(BusinessErrorEnum.PARAMETER_VALIDATION_ERROR, "数量信息不存在");
        }

        if (promoId != null) {
            // 1.校验对应活动是否存在适用商品
            if (promoId.intValue() != itemDTO.getPromoDTO().getId()) {
                throw new BusinessException(BusinessErrorEnum.PARAMETER_VALIDATION_ERROR, "活动信息不正确");
            }
            if (itemDTO.getPromoDTO().getPromoStatus() != 2) {
                throw new BusinessException(BusinessErrorEnum.PARAMETER_VALIDATION_ERROR, "活动还未开始");
            }
        }

        // 2.落单减库存(不推荐支付减库存)
        boolean result = iItemService.decreaseStock(itemId, amount);
        if (!result) {
            throw new BusinessException(BusinessErrorEnum.STOCK_NOT_ENOUGH);
        }

        // 3.订单入库
        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setId(this.genUniqueKey());
        orderDTO.setUserId(userId);
        orderDTO.setItemId(itemId);
        orderDTO.setAmount(amount);
        if (promoId != null) {
            orderDTO.setItemPrice(itemDTO.getPromoDTO().getPromoItemPrice());
        } else {
            orderDTO.setItemPrice(itemDTO.getPrice());
        }
        orderDTO.setPromoId(promoId);
        orderDTO.setOrderPrice(new BigDecimal(amount).multiply(orderDTO.getItemPrice()));

        OrderDO orderDO = this.convertOrderDOFromOrderDTO(orderDTO);
        iOrderDao.insert(orderDO);

        // 商品的销量增加
        iItemService.increaseSales(itemId, amount);
        // 4.返回前端

        return null;
    }

    /**
     * 生成唯一的交易流水号
     * 格式：8位时间 + 8位随机数 + 2位分库分表(默认00)
     */
    private synchronized String genUniqueKey() {

        StringBuilder stringBuilder = new StringBuilder();

        LocalDateTime now = LocalDateTime.now();
        String newDate = now.format(DateTimeFormatter.ISO_DATE).replace("-", "");
        stringBuilder.append(newDate);

        Random random = new Random();
        Integer number = random.nextInt(90000000) + 10000000;
        stringBuilder.append(number);

        stringBuilder.append("00");

        return stringBuilder.toString();
    }

    /**
     * order DTO对象转换 DO对象
     */
    private OrderDO convertOrderDOFromOrderDTO(OrderDTO orderDTO) {
        if (orderDTO == null) {
            return null;
        }

        OrderDO orderDO = new OrderDO();
        BeanUtils.copyProperties(orderDTO, orderDO);
        orderDO.setItemPrice(orderDTO.getItemPrice().doubleValue());
        orderDO.setOrderPrice(orderDTO.getOrderPrice().doubleValue());

        return orderDO;
    }
}
