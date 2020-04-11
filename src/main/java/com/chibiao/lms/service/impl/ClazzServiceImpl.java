package com.chibiao.lms.service.impl;

import com.chibiao.lms.domain.Clazz;
import com.chibiao.lms.domain.Department;
import com.chibiao.lms.domain.Specialty;
import com.chibiao.lms.error.BusinessErrorCode;
import com.chibiao.lms.exception.BusinessException;
import com.chibiao.lms.mapper.ClazzMapper;
import com.chibiao.lms.mapper.DepartmentMapper;
import com.chibiao.lms.mapper.SpecialtyMapper;
import com.chibiao.lms.param.PageParam;
import com.chibiao.lms.result.PageListRes;
import com.chibiao.lms.service.ClazzService;
import com.chibiao.lms.service.DepartmentService;
import com.chibiao.lms.service.SpecialtyService;
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
 * @date : 2020/3/29
 */
@Service
public class ClazzServiceImpl implements ClazzService {
    @Autowired
    private ClazzMapper clazzMapper;
    @Autowired
    private DepartmentMapper departmentMapper;
    @Autowired
    private SpecialtyMapper specialtyMapper;

    @Override
    public PageListRes queryClazz(PageParam pageParam) {
        Page<Object> page = PageHelper.startPage(pageParam.getPage(), pageParam.getLimit());
        List<Clazz> list = clazzMapper.selectAll();
        return PageListResUtil.buildSuccessPageListRes(page, list);
    }

    @Override
    public Boolean addClazz(Clazz clazz) {
        // 查询专业是否存在
        Specialty specialty = specialtyMapper.selectBySpecialtyNo(clazz.getSpecialty().getSpecialtyNo());
        if (specialty == null){
            throw new BusinessException(BusinessErrorCode.SPECIALTY_NOT_EXIST);
        }
        if (!clazz.getDepartment().getDeptNo().equals(specialty.getDepartment().getDeptNo())){
            throw new BusinessException(BusinessErrorCode.SPECIALTY_NOT_IN_DEPARTMENT);
        }
        Department department = departmentMapper.selectByDeptNo(clazz.getDepartment().getDeptNo());
        if (department == null){
            throw new BusinessException(BusinessErrorCode.DEPARTMENT_NOT_EXIST);
        }
        // 查询院系是否存在
        int count = clazzMapper.insert(clazz);
        return count > 0;
    }
}
