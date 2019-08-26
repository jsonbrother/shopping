package com.shopping.dao;

import com.shopping.dao.dataObject.UserPasswordDO;
import org.springframework.stereotype.Repository;


/**
 * Created by TongHaiJun
 * 2019/8/20 22:06
 */
@Repository
public interface IUserPasswordDao {

    void insert(UserPasswordDO userPasswordDO);

    UserPasswordDO selectByUserId(Integer userId);

}
