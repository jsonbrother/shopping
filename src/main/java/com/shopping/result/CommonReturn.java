package com.shopping.result;

import lombok.Data;

/**
 * Created by TongHaiJun
 * 2019/8/20 23:38
 */
@Data
public class CommonReturn {

    /**
     * 表明对应请求的返回处理结果"success"或者"fail"
     */
    private String status;

    /**
     * 如果 status=success 则data内返回前端需要的json数据
     * 如果 status=fail 则data内使用通用的错误码格式
     */
    private Object data;

    /**
     * 定义一个通用的创建方法
     */
    public static CommonReturn create(Object result) {
        return CommonReturn.create("success", result);
    }

    public static CommonReturn create(String status, Object result) {
        CommonReturn commonReturn = new CommonReturn();
        commonReturn.setStatus(status);
        commonReturn.setData(result);
        return commonReturn;
    }
}
