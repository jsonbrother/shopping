package com.shopping.validator;

import lombok.Data;
import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by TongHaiJun
 * 2019/8/22 23:44
 */
@Data
public class ValidationResult {

    /**
     * 校验结果是否有错，默认false
     */
    private boolean hasErrors = false;

    /**
     * 存在错误信息的Map，默认初始化
     */
    private Map<String, Object> errorMsgMap = new HashMap<>();

    /**
     * 实现通用的通过格式化字符串信息获取错误结果的msg方法
     * 因为可能是多条错误信息，所以通过逗号拼接
     */
    public String getErrMsg() {
        return StringUtils.join(errorMsgMap.values().toArray(), ",");
    }
}
