package com.shopping.controller;

/**
 * Created by TongHaiJun
 * 2019/8/25 17:52
 */

import com.shopping.enums.BusinessErrorEnum;
import com.shopping.exception.BusinessException;
import com.shopping.result.CommonReturn;
import com.shopping.service.IOrderService;
import com.shopping.service.dto.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * 订单模块.
 * Created by TongHaiJun
 * 2019/8/20 22:32
 */
@Controller
@RequestMapping("/order")
@CrossOrigin(allowCredentials = "true", allowedHeaders = "*")
public class OrderController {

    @Autowired
    private IOrderService iOrderService;

    @Autowired
    private HttpServletRequest httpServletRequest;

    @PostMapping("createOrder")
    @ResponseBody
    public CommonReturn createOrder(@RequestParam("itemId") Integer itemId,
                                    @RequestParam("amount") Integer amount,
                                    @RequestParam(value = "promoId", required = false) Integer promoId) throws BusinessException {

        Boolean isLogin = (Boolean) httpServletRequest.getSession().getAttribute("is_login");
        if (isLogin == null || !isLogin.booleanValue()) {
            throw new BusinessException(BusinessErrorEnum.USER_NOT_LOGIN, "用户还未登陆,不能下单");
        }

        // 获取用户信息
        UserDTO userDTO = (UserDTO) httpServletRequest.getSession().getAttribute("login_user");

        iOrderService.createOrder(userDTO.getId(), itemId, amount, promoId);

        return CommonReturn.create(null);
    }
}
