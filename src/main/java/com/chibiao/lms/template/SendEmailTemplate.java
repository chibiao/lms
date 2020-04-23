package com.chibiao.lms.template;

import com.chibiao.lms.param.EmailParam;

/**
 * 邮件模版
 *
 * @author : 迟彪
 * @date : 2020/4/20
 */
public class SendEmailTemplate {
    public static String reminderMail(EmailParam emailParam){
        StringBuilder sb = new StringBuilder();
        sb.append("尊敬的")
                .append(emailParam.getTeacherName())
                .append("你好:")
                .append("您的学生")
                .append(emailParam.getStartTime())
                .append("于")
                .append(emailParam.getStartTime())
                .append("-")
                .append(emailParam.getEndTime())
                .append("请假,")
                .append("请假时长:")
                .append(emailParam.getLeaveDays())
                .append("请假原因为:")
                .append(emailParam.getLeaveReason())
                .append(",望您尽快处理.");
        return sb.toString();
    }
}
