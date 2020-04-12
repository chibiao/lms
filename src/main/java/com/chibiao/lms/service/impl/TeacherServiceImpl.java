package com.chibiao.lms.service.impl;

import com.chibiao.lms.domain.Teacher;
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

    @Override
    public PageListRes queryTeachers(Teacher teacher, PageParam pageParam) {
        Page<Object> page = PageHelper.startPage(pageParam.getPage(), pageParam.getLimit());
        List<Teacher> list = teacherMapper.queryTeachers(teacher);
        return PageListResUtil.buildSuccessPageListRes(page, list);
    }

    @Override
    public Boolean addTeacher(Teacher teacher) {
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
}
