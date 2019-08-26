package com.shopping.result;

/**
 * Created by TongHaiJun
 * 2019/8/20 23:48
 */
public interface CommonError {

    int getErrCode();

    String getErrMsg();

    CommonError setErrMsg(String errMsg);
}
