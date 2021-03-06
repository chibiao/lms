package com.chibiao.lms.service.impl;

import com.alibaba.druid.util.StringUtils;
import com.alibaba.excel.util.CollectionUtils;
import com.alibaba.fastjson.JSON;
import com.chibiao.lms.domain.Clazz;
import com.chibiao.lms.domain.Department;
import com.chibiao.lms.domain.Specialty;
import com.chibiao.lms.enums.RedisPrefixEnum;
import com.chibiao.lms.error.BusinessErrorCode;
import com.chibiao.lms.exception.BusinessException;
import com.chibiao.lms.mapper.ClazzMapper;
import com.chibiao.lms.mapper.DepartmentMapper;
import com.chibiao.lms.mapper.SpecialtyMapper;
import com.chibiao.lms.param.PageParam;
import com.chibiao.lms.result.PageListRes;
import com.chibiao.lms.service.ClazzService;
import com.chibiao.lms.util.JsonUtil;
import com.chibiao.lms.util.PageListResUtil;
import com.chibiao.lms.util.RedisClient;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.TimeUnit;

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
    @Autowired
    private RedisClient redisClient;

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

    @Override
    public List<Clazz> clazzBySpecialtyNo(Long specialtyNo) {
        return clazzMapper.selectBySpecialtyNo(specialtyNo);
        /*try {
            // 从缓存中查找
            String allDepartmentJson = redisClient.get(RedisPrefixEnum.clazzPrefix + "list" + specialtyNo);
            if (!StringUtils.isEmpty(allDepartmentJson)) {
                return JsonUtil.parseArray(allDepartmentJson, Clazz.class);
            }
            // 数据库查找
            List<Clazz> clazzes = clazzMapper.selectBySpecialtyNo(specialtyNo);
            if (!CollectionUtils.isEmpty(clazzes)){
                // 放入缓存 时间为10s
                redisClient.setEx(RedisPrefixEnum.clazzPrefix + "list" + specialtyNo, JSON.toJSONString(clazzes), 10, TimeUnit.SECONDS);
            }
            return clazzes;
        }catch (Throwable e){

        }*/
    }

    @Override
    public Clazz selectByClazzNo(Long clazzNo) {

        return clazzMapper.selectByClazzNo(clazzNo);
    }
}
