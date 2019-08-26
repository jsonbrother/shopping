package com.shopping.enums;

import com.shopping.result.CommonError;

/**
 * Created by TongHaiJun
 * 2019/8/20 23:52
 */
public enum BusinessErrorEnum implements CommonError {

    /**
     * 通用错误类型
     */
    PARAMETER_VALIDATION_ERROR(10001, "参数不合法"),

    /**
     * 未知错误
     */
    UNKNOWN_ERROR(10002, " 未知错误"),

    /**
     * 20000开头为用户信息相关的错误码
     */
    USER_NOT_EXIST(20001, "用户不存在"),

    USER_LOGIN_FAIL(20002, "用户手机号或者密码不正确"),

    USER_NOT_LOGIN(20002,"用户还未登陆"),

    /**
     * 30000开头为交易信息错误
     */
    STOCK_NOT_ENOUGH(30001, "库存不足"),

    ;
    private int errCode;
    private String errMsg;

    BusinessErrorEnum(int errCode, String errMsg) {
        this.errCode = errCode;
        this.errMsg = errMsg;
    }

    @Override
    public int getErrCode() {
        return this.errCode;
    }

    @Override
    public String getErrMsg() {
        return this.errMsg;
    }

    @Override
    public CommonError setErrMsg(String errMsg) {
        this.errMsg = errMsg;
        return this;
    }}