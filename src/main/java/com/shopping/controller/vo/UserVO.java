package com.shopping.controller.vo;

import lombok.Data;

/**
 * Created by TongHaiJun
 * 2019/8/20 23:26
 */
@Data
public class UserVO {

    private Integer id;

    /**
     * 用户名
     */
    private String name;

    /**
     * 性别 1-男 2-女
     */
    private Byte gender;

    /**
     * 年龄
     */
    private Integer age;

    /**
     * 手机号码
     */
    private String telPhone;

}
