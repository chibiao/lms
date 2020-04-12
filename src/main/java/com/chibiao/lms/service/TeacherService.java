package com.chibiao.lms.service;

import com.chibiao.lms.domain.Teacher;
import com.chibiao.lms.param.PageParam;
import com.chibiao.lms.result.PageListRes;

/**
 * 教师服务
 *
 * @author : 迟彪
 * @date : 2020/4/12
 */
public interface TeacherService {
    /**
     * 分页查询教师信息
     * @param teacher 查询条件 教师名称 教师编号 教师类型
     * @param pageParam 分页参数
     * @return
     */
    PageListRes queryTeachers(Teacher teacher, PageParam pageParam);

    /**
     * 添加教师
     * @param teacher 添加教师
     * @return
     */
    Boolean addTeacher(Teacher teacher);

    /**
     * 删除教师信息
     * @param teacherNo 教师编号
     * @return
     */
    Boolean deleteTeacher(Long teacherNo);

    /**
     * 修改教师信息 根据教师编号
     * @param teacher 教师信息
     * @return
     */
    Boolean updateTeacher(Teacher teacher);
}
