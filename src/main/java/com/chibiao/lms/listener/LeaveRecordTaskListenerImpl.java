package com.chibiao.lms.listener;

import com.chibiao.lms.domain.Student;
import com.chibiao.lms.domain.Teacher;
import com.chibiao.lms.service.TeacherService;
import com.chibiao.lms.util.SpringUtil;
import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.delegate.TaskListener;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

public class LeaveRecordTaskListenerImpl implements TaskListener {

    @Override
    public void notify(DelegateTask delegateTask) {
        //得到当前用户
        // 1,得到办理人信息
        Student student = (Student) SecurityUtils.getSubject().getPrincipal();
        //取出领导ID
        TeacherService teacherService = SpringUtil.getObject(TeacherService.class);
        Teacher teacher = teacherService.selectByClazzNo(student.getClazz().getClazzNo());
        //4,设置办理人
        delegateTask.setAssignee(teacher.getTeacherName());
    }
}
