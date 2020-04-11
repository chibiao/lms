package com.chibiao.lms.util;

import com.chibiao.lms.error.DefaultErrorCode;
import com.chibiao.lms.error.ErrorCode;
import com.chibiao.lms.result.HttpResult;

/**
 * 结果工具类
 *
 * @author : 迟彪
 * @date : 2020/3/21
 */
public class HttpResultUtil {
    private static final ErrorCode SUCCESS_ERROR_CODE;
    private static final ErrorCode FAULT_ERROR_CODE;

    private HttpResultUtil() {
        throw new UnsupportedOperationException("you can't instance this class");
    }

    public static <T> HttpResult<T> buildSuccessHttpResult(T data) {
        HttpResult<T> HttpResult = new HttpResult<>();
        HttpResult.setCode(SUCCESS_ERROR_CODE.getCode());
        HttpResult.setMessage(SUCCESS_ERROR_CODE.getDescription());
        HttpResult.setData(data);
        return HttpResult;
    }

    public static HttpResult buildHttpFaultResult() {
        HttpResult HttpResult = new HttpResult();
        HttpResult.setCode(FAULT_ERROR_CODE.getCode());
        HttpResult.setMessage(FAULT_ERROR_CODE.getDescription());
        return HttpResult;
    }

    public static HttpResult buildHttpResult(ErrorCode errorCode) {
        HttpResult HttpResult = new HttpResult();
        HttpResult.setCode(errorCode.getCode());
        HttpResult.setMessage(errorCode.getDescription());
        return HttpResult;
    }

    public static <T> HttpResult<T> buildHttpResult(ErrorCode errorCode, T data) {
        HttpResult<T> HttpResult = new HttpResult<>();
        HttpResult.setCode(errorCode.getCode());
        HttpResult.setMessage(errorCode.getDescription());
        HttpResult.setData(data);
        return HttpResult;
    }

    static {
        SUCCESS_ERROR_CODE = DefaultErrorCode.SUCCESS;
        FAULT_ERROR_CODE = DefaultErrorCode.UNKNOWN_ERROR;
    }
}
