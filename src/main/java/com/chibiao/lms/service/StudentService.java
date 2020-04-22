package com.chibiao.lms.service;

import com.chibiao.lms.domain.Student;
import com.chibiao.lms.domain.StudentData;
import com.chibiao.lms.param.PageParam;
import com.chibiao.lms.result.PageListRes;

import java.util.List;

/**
 * 学生信息管理service
 *
 * @author : 迟彪
 * @date : 2020/4/11
 */
public interface StudentService {
    /**
     * 分页查询学生信息
     * @param student 查询条件
     * @param pageParam 分页参数
     * @return list
     */
    PageListRes queryStudents(Student student, PageParam pageParam);

    /**
     * 添加学生信息
     * @param student 学生信息
     * @return 是否添加成功 true 是 false 否
     */
    Boolean addStudent(Student student);

    /**
     * 根据学号删除学生信息
     * @param studentId 学号
     * @return 是否删除成功
     */
    Boolean deleteStudent(Long studentId);

    /**
     * 更新学生信息
     * @param student 学生信息
     * @return 是否更新成功
     */
    Boolean updateStudent(Student student);

    /**
     * 根据学生编号重置密码
     * @param studentId 学生编号
     * @return
     */
    Boolean resetStudentPassword(Long studentId);

    /**
     * 批量添加学生信息
     * @param list
     */
    void addStudentList(List<StudentData> list);

    /**
     * 根据学生编号 查询学生信息
     * @param studentId
     * @return
     */
    Student selectByStudentId(Long studentId);
}
