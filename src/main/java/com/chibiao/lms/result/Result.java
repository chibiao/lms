package com.chibiao.lms.result;

/**
 * 对外结果接口
 *
 * @author : 迟彪
 * @date : 2020/3/21
 */
public interface Result<T> {
    String getCode();

    String getMessage();

    T getData();
}
