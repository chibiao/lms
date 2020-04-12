package com.chibiao.lms.mapper;

import com.chibiao.lms.domain.Teacher;
import java.util.List;

public interface TeacherMapper {
    int deleteByPrimaryKey(Long teacherNo);

    int insert(Teacher record);

    Teacher selectByPrimaryKey(Long teacherNo);

    List<Teacher> selectAll();

    int updateByPrimaryKey(Teacher record);

    /**
     * 查询教师list
     * @param teacher 查询条件
     * @return
     */
    List<Teacher> queryTeachers(Teacher teacher);
}