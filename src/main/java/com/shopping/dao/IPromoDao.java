package com.shopping.dao;

import com.shopping.dao.dataObject.PromoDO;
import org.springframework.stereotype.Repository;

/**
 * Created by TongHaiJun
 * 2019/8/25 19:20
 */
@Repository
public interface IPromoDao {

    PromoDO selectByItemId(Integer itemId);
}
