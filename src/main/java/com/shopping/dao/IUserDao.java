package com.shopping.dao;

import com.shopping.dao.dataObject.UserDO;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * Created by TongHaiJun
 * 2019/8/20 22:06
 */
@Repository
public interface IUserDao {

    void insert(UserDO userDO);

    UserDO selectById(Integer id);

    UserDO selectByTelPhone(String telPhone);

    List<UserDO> selectAll();
}
