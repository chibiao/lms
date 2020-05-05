package com.chibiao.lms.service.impl;

import com.alibaba.druid.support.json.JSONUtils;
import com.alibaba.fastjson.JSON;
import com.chibiao.lms.domain.Department;
import com.chibiao.lms.enums.RedisPrefixEnum;
import com.chibiao.lms.error.BusinessErrorCode;
import com.chibiao.lms.exception.BusinessException;
import com.chibiao.lms.mapper.DepartmentMapper;
import com.chibiao.lms.param.PageParam;
import com.chibiao.lms.result.PageListRes;
import com.chibiao.lms.service.DepartmentService;
import com.chibiao.lms.util.JsonUtil;
import com.chibiao.lms.util.PageListResUtil;
import com.chibiao.lms.util.RedisClient;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * 院系Service
 *
 * @author : 迟彪
 * @date : 2020/3/21
 */
@Slf4j
@Service
public class DepartmentServiceImpl implements DepartmentService {
    @Autowired
    private DepartmentMapper departmentMapper;
    @Autowired
    private RedisClient redisClient;

    /**
     * 添加院系
     *
     * @param department 院系实体
     * @return 是否成功
     */
    @Override
    public Boolean addDepartment(Department department) {
        Department dept = departmentMapper.selectByDeptNo(department.getDeptNo());
        if (dept != null){
            throw new BusinessException(BusinessErrorCode.DEPT_NO_EXIST);
        }
        int insert = departmentMapper.insert(department);
        return insert > 0;
    }

    /**
     * 根据院系编号删除院系
     *
     * @param deptNo 院系编号
     * @return 是否成功
     */
    @Override
    public Boolean deleteDepartment(Long deptNo) {
        int i = departmentMapper.deleteDepartment(deptNo);
        return i > 0;
    }

    /**
     * 更新院系信息 只更新描述和名称
     *
     * @param department 院系实体
     * @return 是否成功
     */
    @Override
    public Boolean updateDepartment(Department department) {
        int i = departmentMapper.updateByDeptNo(department);
        return i > 0;
    }

    @Override
    public PageListRes queryDepartment(PageParam pageParam) {
        Page<Object> page = PageHelper.startPage(pageParam.getPage(), pageParam.getLimit());
        List<Department> departments = departmentMapper.queryDepartment();
        return PageListResUtil.buildSuccessPageListRes(page, departments);
    }

    @Override
    public List<Department> allDepartment() {
        try {
            // 从缓存中查找
            String allDepartmentJson = redisClient.get(RedisPrefixEnum.departmentPrefix + "list");
            if (!StringUtils.isEmpty(allDepartmentJson)){
                return JsonUtil.parseArray(allDepartmentJson,Department.class);
            }
            // 数据库查找
            List<Department> departments = departmentMapper.queryDepartment();
            // 放入缓存 时间为10s
            redisClient.setEx(RedisPrefixEnum.departmentPrefix + "list",JSON.toJSONString(departments),10, TimeUnit.SECONDS);
            return departments;
        }catch (Throwable e){
            // 数据库查找
            return departmentMapper.queryDepartment();
        }
    }
}
