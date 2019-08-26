package com.shopping.dao.dataObject;

import lombok.Data;

/**
 * 用户密码.
 * Created by TongHaiJun
 * 2019/8/20 22:50
 */
@Data
public class UserPasswordDO {

    private Integer id;

    /**
     * 加密密码（注意不是明文密码）
     */
    private String password;

    /**
     * 用户id
     */
    private Integer userId;
}
