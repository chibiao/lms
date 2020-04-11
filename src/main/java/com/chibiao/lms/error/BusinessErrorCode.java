package com.chibiao.lms.error;


/**
 * 业务异常枚举值
 *
 * @author : 迟彪
 * @date : 2020/3/21
 */
public enum  BusinessErrorCode implements ErrorCode{
    SUCCESS("0000","OK"),
    UNKNOWN_ERROR("9999","未知异常"),
    /**
     * 院系异常 1000 - 1099
     */
    DEPARTMENT_NOT_EXIST("1000","院系不存在"),
    DEPT_NO_EXIST("1001","院系编号已存在"),
    /**
     * 专业异常 1100-1199
     */
    SPECIALTY_NOT_EXIST("1100","专业不存在"),
    SPECIALTY_NOT_IN_DEPARTMENT("1101","本专业不属于该院系"),


    ;
    private final String code;
    private final String description;

    BusinessErrorCode(String code, String description) {
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
        return "BusinessErrorCode{" +
                "code='" + code + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
