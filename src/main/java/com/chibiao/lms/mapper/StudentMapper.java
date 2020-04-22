package com.chibiao.lms.mapper;

import com.chibiao.lms.domain.Student;
import com.chibiao.lms.domain.StudentData;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface StudentMapper {
    /**
     * 添加学生信息
     * @param record
     * @return
     */
    int insert(Student record);

    /**
     * 查询学生信息list
     * @param student 查询条件
     * @return list
     */
    List<Student> queryStudents(Student student);

    /**
     * 根据学生编号删除学生信息
     * @param studentId 学生编号
     * @return
     */
    Integer deleteByStudentId(Long studentId);

    /**
     * 根据学生编号更新学生信息
     * @param student 学生信息
     * @return
     */
    Integer updateByStudentId(Student student);

    /**
     * 根据学生编号查询学生信息
     * @param studentId
     * @return
     */
    Student selectByStudentId(Long studentId);

    /**
     * 设置初始密码
     * @param studentId 学生编号
     * @param initStudentPassword 初始密码
     * @return
     */
    Integer resetStudentPassword(@Param("studentId") Long studentId, @Param("initStudentPassword") String initStudentPassword);

    /**
     * 批量添加学生信息
     * @param list
     */
    void addStudentList(List<StudentData> list);
}