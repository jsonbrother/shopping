package com.shopping.service.dto;

import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * Created by TongHaiJun
 * 2019/8/20 22:45
 */
@Data
public class UserDTO {

    private Integer id;

    /**
     * 用户名
     */
    @NotBlank(message = "用户名不能为空")
    private String name;

    /**
     * 性别
     */
    @NotNull(message = "性别必填")
    private Integer gender;

    /**
     * 年龄
     */
    @NotNull(message = "年龄必填")
    @Min(value = 0, message = "年龄必须大于0岁")
    @Max(value = 150, message = "年龄必须小于150岁")
    private Integer age;

    /**
     * 手机号码
     */
    @NotBlank(message = "手机号不能为空")
    private String telPhone;

    /**
     * 注册方式（byPhone,byWeChat,byAliPay）
     */
    private String registerMode;

    /**
     * 第三方id（微信、支付宝）
     */
    private String thirdPartyId;

    /**
     * 加密密码
     */
    @NotBlank(message = "密码不能为空")
    private String password;

}
