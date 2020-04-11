package com.chibiao.lms.result;

import java.io.Serializable;

/**
 * 请输入描述
 *
 * @author : 迟彪
 * @date : 2020/3/21
 */
public class HttpResult<T> implements Serializable, Result {
    private static final long serialVersionUID = 1L;
    private String code;
    private String message;
    private T data;

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    @Override
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
