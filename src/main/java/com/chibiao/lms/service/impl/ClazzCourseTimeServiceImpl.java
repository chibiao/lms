package com.chibiao.lms.service.impl;

import com.chibiao.lms.domain.ClazzCourseTime;
import com.chibiao.lms.error.BusinessErrorCode;
import com.chibiao.lms.exception.BusinessException;
import com.chibiao.lms.mapper.ClazzCourseTimeMapper;
import com.chibiao.lms.param.PageParam;
import com.chibiao.lms.result.PageListRes;
import com.chibiao.lms.service.ClazzCourseTimeService;
import com.chibiao.lms.util.PageListResUtil;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 请输入描述
 *
 * @author : 迟彪
 * @date : 2020/5/6
 */
@Service
public class ClazzCourseTimeServiceImpl implements ClazzCourseTimeService {
    @Autowired
    private ClazzCourseTimeMapper clazzCourseTimeMapper;
    /**
     * 添加教师上课时间
     * @param clazzCourseTime 上课时间
     * @return
     */
    @Override
    public Boolean addClazzCourseTime(ClazzCourseTime clazzCourseTime) {
        ClazzCourseTime courseTime = clazzCourseTimeMapper.selectClazzCourseTime(clazzCourseTime);
        if (courseTime != null){
            throw new BusinessException(BusinessErrorCode.COURSE_TIME_EXIST);
        }
        clazzCourseTimeMapper.addClazzCourseTime(clazzCourseTime);
        return Boolean.TRUE;
    }

    @Override
    public List<ClazzCourseTime> selectClazzCourseTimeByTeacherNo(Long teacherNo) {
        return clazzCourseTimeMapper.selectClazzCourseTimeByTeacherNo(teacherNo);
    }

    @Override
    public PageListRes selectClazzCourseTimeByClazzNo(ClazzCourseTime clazzCourseTime, PageParam pageParam) {
        Page<Object> page = PageHelper.startPage(pageParam.getPage(), pageParam.getLimit());
        List<ClazzCourseTime> list = clazzCourseTimeMapper.selectClazzCourseTimeByClazzNo(clazzCourseTime);
        return PageListResUtil.buildSuccessPageListRes(page,list);
    }
}
