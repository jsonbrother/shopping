package com.shopping.service.impl;

import com.shopping.dao.IUserPasswordDao;
import com.shopping.dao.dataObject.UserDO;
import com.shopping.dao.IUserDao;
import com.shopping.dao.dataObject.UserPasswordDO;
import com.shopping.enums.BusinessErrorEnum;
import com.shopping.exception.BusinessException;
import com.shopping.service.IUserService;
import com.shopping.service.dto.UserDTO;
import com.shopping.validator.ValidationResult;
import com.shopping.validator.ValidatorImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

/**
 * Created by TongHaiJun
 * 2019/8/20 22:34
 */
@Service
public class UserServiceImpl implements IUserService {

    @Autowired
    private IUserDao iUserDao;

    @Autowired
    private IUserPasswordDao iUserPasswordDao;

    @Autowired
    private ValidatorImpl validator;

    @Override
    public UserDTO selectById(Integer id) {
        // 调用iUserDao获取用户信息的dataObject数据
        UserDO userDO = iUserDao.selectById(id);
        if (userDO == null) {
            return null;
        }
        // 通过用户id获取对应的用户加密密码信息
        UserPasswordDO userPasswordDO = iUserPasswordDao.selectByUserId(userDO.getId());
        return convertFromDataObject(userDO, userPasswordDO);
    }

    @Override
    public UserDTO validateLogin(String telPhone, String encryptionPassword) throws BusinessException {
        // 通过用户的手机获取用户信息
        UserDO userDO = iUserDao.selectByTelPhone(telPhone);
        if (userDO == null) {
            throw new BusinessException(BusinessErrorEnum.USER_LOGIN_FAIL);
        }
        UserPasswordDO userPasswordDO = iUserPasswordDao.selectByUserId(userDO.getId());

        UserDTO userDTO = convertFromDataObject(userDO, userPasswordDO);
        // 比对用户信息内加密的密码是否和传输进来的密码相匹配

        if (!StringUtils.equals(encryptionPassword, userDTO.getPassword())) {
            throw new BusinessException(BusinessErrorEnum.USER_LOGIN_FAIL);
        }

        return userDTO;
    }

    @Override
    @Transactional
    public void register(UserDTO userDTO) throws BusinessException {
        if (userDTO == null) {
            throw new BusinessException(BusinessErrorEnum.PARAMETER_VALIDATION_ERROR);
        }

        // 校验userDTO数据
        ValidationResult result = validator.validate(userDTO);
        if (result.isHasErrors()) {
            throw new BusinessException(BusinessErrorEnum.PARAMETER_VALIDATION_ERROR, result.getErrMsg());
        }

        // 校验手机号是否已经被注册
        UserDO userDO = convertFromDTO(userDTO);
        try {
            iUserDao.insert(userDO);
        } catch (DuplicateKeyException e) {
            throw new BusinessException(BusinessErrorEnum.PARAMETER_VALIDATION_ERROR, "手机号已注册");
        }

        // 返回userId 查询加密密码信息
        userDTO.setId(userDO.getId());

        UserPasswordDO userPasswordDO = convertPasswordFromDTO(userDTO);
        iUserPasswordDao.insert(userPasswordDO);
    }

    /**
     * user DTO对象转换 userPassword DTO对象
     */
    private UserPasswordDO convertPasswordFromDTO(UserDTO userDTO) {
        UserPasswordDO userPasswordDO = new UserPasswordDO();
        userPasswordDO.setUserId(userDTO.getId());
        userPasswordDO.setPassword(userDTO.getPassword());
        return userPasswordDO;
    }

    /**
     * user DTO对象转换DO对象
     */
    private UserDO convertFromDTO(UserDTO userDTO) {
        UserDO userDO = new UserDO();
        BeanUtils.copyProperties(userDTO, userDO);
        return userDO;
    }

    /**
     * user、userPassword DO对象转换DTO对象
     */
    private UserDTO convertFromDataObject(UserDO userDO, UserPasswordDO userPasswordDO) {
        if (userDO == null) {
            return null;
        }
        UserDTO userDTO = new UserDTO();
        BeanUtils.copyProperties(userDO, userDTO);

        if (userPasswordDO == null) {
            return null;
        }
        userDTO.setPassword(userPasswordDO.getPassword());

        return userDTO;
    }
}
