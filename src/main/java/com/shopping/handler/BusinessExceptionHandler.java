package com.shopping.handler;

import com.shopping.enums.BusinessErrorEnum;
import com.shopping.exception.BusinessException;
import com.shopping.result.CommonReturn;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by TongHaiJun
 * 2019/8/22 19:47
 */
@ControllerAdvice
public class BusinessExceptionHandler {

    /**
     * 异常统一处理
     */
    @ExceptionHandler(value = Exception.class)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public Object handlerBusinessException(Exception e) {
        Map<String, Object> responseData = new HashMap<>();

        // 判断 异常是否为 自定义、手动抛出、已知的异常
        if (e instanceof BusinessException) {
            BusinessException businessException = (BusinessException) e;
            responseData.put("errCode", businessException.getErrCode());
            responseData.put("errMsg", businessException.getErrMsg());
        } else {
            // 未知异常 抛出未知错误msg
            responseData.put("errCode", BusinessErrorEnum.UNKNOWN_ERROR.getErrCode());
            responseData.put("errMsg", BusinessErrorEnum.UNKNOWN_ERROR.getErrMsg());
        }
        return CommonReturn.create("fail", responseData);
    }

}
