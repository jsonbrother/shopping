package com.shopping.service;

import com.shopping.exception.BusinessException;
import com.shopping.service.dto.UserDTO;

/**
 * Created by TongHaiJun
 * 2019/8/20 22:34
 */
public interface IUserService {

    UserDTO selectById(Integer id);

    /**
     * @param telPhone 注册手机号
     * @param encryptionPassword 加密后的密码
     */
    UserDTO validateLogin(String telPhone, String encryptionPassword) throws BusinessException;

    void register(UserDTO userDTO) throws BusinessException;
}
