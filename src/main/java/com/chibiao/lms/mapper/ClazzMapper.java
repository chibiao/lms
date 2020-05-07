package com.chibiao.lms.mapper;

import com.chibiao.lms.domain.Clazz;
import java.util.List;

public interface ClazzMapper {

    int insert(Clazz record);

    List<Clazz> selectAll();

    Clazz selectByClazzNo(Long clazzNo);

    /**
     * 根据专业编号查询班级list
     * @param specialtyNo 专业编号
     * @return
     */
    List<Clazz> selectBySpecialtyNo(Long specialtyNo);

    /**
     * 根据教师和班级关系 查询出班级信息
     * @param teacherNo 教师编号
     * @return 班级信息
     */
    List<Clazz> selectByTeacherNo(Long teacherNo);
}