package com.chibiao.lms.service.impl;

import com.alibaba.fastjson.JSON;
import com.chibiao.lms.domain.Department;
import com.chibiao.lms.domain.Specialty;
import com.chibiao.lms.enums.RedisPrefixEnum;
import com.chibiao.lms.mapper.SpecialtyMapper;
import com.chibiao.lms.param.PageParam;
import com.chibiao.lms.result.PageListRes;
import com.chibiao.lms.service.SpecialtyService;
import com.chibiao.lms.util.JsonUtil;
import com.chibiao.lms.util.PageListResUtil;
import com.chibiao.lms.util.RedisClient;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * 请输入描述
 *
 * @author : 迟彪
 * @date : 2020/3/21
 */
@Service
public class SpecialtyServiceImpl implements SpecialtyService {
    @Autowired
    private SpecialtyMapper specialtyMapper;
    @Autowired
    private RedisClient redisClient;

    @Override
    public Boolean addSpecialty(Specialty specialty) {
        int insert = specialtyMapper.insert(specialty);
        return insert > 0;
    }

    @Override
    public Boolean updateSpecialty(Specialty specialty) {
        int i = specialtyMapper.updateBySpecialtyNo(specialty);
        return i > 0;
    }

    @Override
    public Boolean deleteSpecialty(Long specialtyNo) {
        int i = specialtyMapper.deleteBySpecialtyNo(specialtyNo);
        return i > 0;
    }

    @Override
    public PageListRes querySpecialty(PageParam pageParam) {
        Page<Object> page = PageHelper.startPage(pageParam.getPage(), pageParam.getLimit());
        List<Specialty> list = specialtyMapper.querySpecialty();
        return PageListResUtil.buildSuccessPageListRes(page, list);
    }

    @Override
    public List<Specialty> specialtyByDeptNo(Long detpNo) {
        return specialtyMapper.querySpecialtyByDeptNo(detpNo);

        /*try {
            // 从缓存中查找
            String allDepartmentJson = redisClient.get(RedisPrefixEnum.specialtyPrefix + "list" + detpNo);
            if (!StringUtils.isEmpty(allDepartmentJson)) {
                return JsonUtil.parseArray(allDepartmentJson, Specialty.class);
            }
            // 数据库查找
            List<Specialty> specialties = specialtyMapper.querySpecialtyByDeptNo(detpNo);
            if (!CollectionUtils.isEmpty(specialties)){
                // 放入缓存 时间为10s
                redisClient.setEx(RedisPrefixEnum.departmentPrefix + "list" + detpNo, JSON.toJSONString(specialties), 10, TimeUnit.SECONDS);
            }
            return specialties;
        }catch (Throwable e){
        }*/
    }

}
