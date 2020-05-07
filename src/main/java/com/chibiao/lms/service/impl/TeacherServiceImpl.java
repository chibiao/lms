package com.chibiao.lms.service.impl;

import com.chibiao.lms.constant.StudentConst;
import com.chibiao.lms.domain.*;
import com.chibiao.lms.error.BusinessErrorCode;
import com.chibiao.lms.exception.BusinessException;
import com.chibiao.lms.mapper.ClazzCourseTimeMapper;
import com.chibiao.lms.mapper.ClazzMapper;
import com.chibiao.lms.mapper.CourseMapper;
import com.chibiao.lms.mapper.TeacherMapper;
import com.chibiao.lms.param.PageParam;
import com.chibiao.lms.result.PageListRes;
import com.chibiao.lms.service.TeacherService;
import com.chibiao.lms.util.PageListResUtil;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 教师相关服务
 *
 * @author : 迟彪
 * @date : 2020/4/12
 */
@Service
public class TeacherServiceImpl implements TeacherService {
    @Autowired
    private TeacherMapper teacherMapper;
    @Autowired
    private CourseMapper courseMapper;
    @Autowired
    private ClazzMapper clazzMapper;
    @Autowired
    private ClazzCourseTimeMapper clazzCourseTimeMapper;

    @Override
    public PageListRes queryTeachers(Teacher teacher, PageParam pageParam) {
        Page<Object> page = PageHelper.startPage(pageParam.getPage(), pageParam.getLimit());
        List<Teacher> list = teacherMapper.queryTeachers(teacher);
        return PageListResUtil.buildSuccessPageListRes(page, list);
    }

    @Override
    public Boolean addTeacher(Teacher teacher) {
        teacher.setTeacherPassword(StudentConst.INIT_STUDENT_PASSWORD);
        teacherMapper.insert(teacher);
        return Boolean.TRUE;
    }

    @Override
    public Boolean deleteTeacher(Long teacherNo) {
        teacherMapper.deleteByPrimaryKey(teacherNo);
        return Boolean.TRUE;
    }

    @Override
    public Boolean updateTeacher(Teacher teacher) {
        teacherMapper.updateByPrimaryKey(teacher);
        return Boolean.TRUE;
    }

    @Override
    public Teacher selectByTeacherNo(Long teacherNo) {
        return teacherMapper.selectByPrimaryKey(teacherNo);
    }

    /**
     * 添加教师教授的课程
     * @param teacherCourseRel 教师和课程编号
     * @return 是否添加成功
     */
    @Override
    public Boolean addMyCourse(TeacherCourseRel teacherCourseRel) {
        TeacherCourseRel courseRel = teacherMapper.selectTeacherCourseRel(teacherCourseRel.getCourseNo(),teacherCourseRel.getTeacherNo());
        if (courseRel != null){
            throw new BusinessException(BusinessErrorCode.TEACHER_COURSE_REL_EXIST);
        }
        teacherMapper.addMyCourse(teacherCourseRel);
        return Boolean.TRUE;
    }

    @Override
    public List<Course> selectMyCourse(Long teacherNo) {
        return courseMapper.selectMyCourse(teacherNo);
    }

    @Override
    public Boolean addMyClazz(TeacherClazzRel teacherClazzRel) {
        TeacherClazzRel courseRel = teacherMapper.selectTeacherClazzRel(teacherClazzRel.getTeacherNo(),teacherClazzRel.getClazzNo());
        if (courseRel != null){
            throw new BusinessException(BusinessErrorCode.TEACHER_CLAZZ_REL_EXIST);
        }
        teacherMapper.addMyClazz(teacherClazzRel);
        return Boolean.TRUE;
    }

    @Override
    public List<Clazz> selectMyClazz(Long teacherNo) {
        return clazzMapper.selectByTeacherNo(teacherNo);
    }

}
