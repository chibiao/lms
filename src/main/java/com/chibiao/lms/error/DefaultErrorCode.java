package com.chibiao.lms.error;

/**
 * 请输入描述
 *
 * @author : 迟彪
 * @date : 2020/3/21
 */
public enum  DefaultErrorCode implements ErrorCode{
    SUCCESS("0000","OK"),
    UNKNOWN_ERROR("9999","未知异常")
    ;
    private final String code;
    private final String description;

    DefaultErrorCode(String code, String description) {
        this.code = code;
        this.description = description;
    }

    @Override
    public String getCode() {
        return code;
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public String toString() {
        return "DefaultErrorCode{" +
                "code='" + code + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
