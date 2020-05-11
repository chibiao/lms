package com.chibiao.lms.mapper;

import com.chibiao.lms.domain.Teacher;
import com.chibiao.lms.domain.TeacherClazzRel;
import com.chibiao.lms.domain.TeacherCourseRel;
import org.apache.ibatis.annotations.Param;

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

    /**
     * 添加老师教授的课程
     * @param teacherCourseRel
     */
    void addMyCourse(TeacherCourseRel teacherCourseRel);

    /**
     * 查询教师和课程关系是否存在
     * @param courseNo 课程编号
     * @param teacherNo 教师编号
     * @return
     */
    TeacherCourseRel selectTeacherCourseRel(@Param("courseNo") Long courseNo,@Param("teacherNo") Long teacherNo);

    /**
     * 查询教师和班级关系是否存在
     * @param teacherNo 教师编号
     * @param clazzNo 课程编号
     * @return
     */
    TeacherClazzRel selectTeacherClazzRel(@Param("teacherNo") Long teacherNo,@Param("clazzNo") Long clazzNo);

    /**
     * 添加教师班级信息
     * @param teacherClazzRel
     */
    void addMyClazz(TeacherClazzRel teacherClazzRel);

    /**
     * 根据班级查询出班级班主任
     * @param clazzNo 班级编号
     * @return
     */
    Teacher selectByClazzNo(Long clazzNo);
}