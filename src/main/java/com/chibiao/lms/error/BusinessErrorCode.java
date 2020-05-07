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
    /**
     * 班级异常 1200-1299
     */
    CLAZZ_NOT_EXIST("1200","班级不存在"),
    /**
     * 学生信息异常 1300-1399
     */
    STUDENT_NO_EXIST("1300","学生编号已存在"),
    LEAVERECORD_IS_EXIST("1301","您的请假单时间冲突"),
    /**
     * 登录异常 1400-1499
     */
    UNKOWN_ACCOUNT("1400","未知账户"),
    PASSWORD_FALSE("1401","密码不正确"),
    ACCOUNT_OR_PASSWORD_FALSE("1402","用户名或密码不正确"),
    /**
     * 教师信息异常 1500-1599
     */
    TEACHER_COURSE_REL_EXIST("1500","您教授的课程已存在"),
    TEACHER_CLAZZ_REL_EXIST("1501","您的班级已存在"),
    COURSE_TIME_EXIST("1502","您选择的课程时间冲突")
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
