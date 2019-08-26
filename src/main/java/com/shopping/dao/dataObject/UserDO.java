package com.shopping.dao.dataObject;

import lombok.Data;

/**
 * 用户.
 * Created by TongHaiJun
 * 2019/8/20 22:10
 */
@Data
public class UserDO {

    private Integer id;

    /**
     * 用户名
     */
    private String name;

    /**
     * 性别 1-男 2-女
     */
    private Integer gender;

    /**
     * 年龄
     */
    private Integer age;

    /**
     * 手机号码
     */
    private String telPhone;

    /**
     * 注册方式（byPhone,byWeChat,byAliPay）
     */
    private String registerMode;

    /**
     * 第三方id（微信、支付宝）
     */
    private String thirdPartyId;

}
