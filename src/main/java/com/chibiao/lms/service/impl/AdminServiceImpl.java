package com.chibiao.lms.service.impl;

import com.chibiao.lms.domain.Admin;
import com.chibiao.lms.mapper.AdminMapper;
import com.chibiao.lms.param.PageParam;
import com.chibiao.lms.result.PageListRes;
import com.chibiao.lms.service.AdminService;
import com.chibiao.lms.util.PageListResUtil;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 管理员相关service
 *
 * @author : 迟彪
 * @date : 2020/4/22
 */
@Service
public class AdminServiceImpl implements AdminService {
    @Autowired
    private AdminMapper adminMapper;
    @Override
    public PageListRes queryAdminList(PageParam pageParam) {
        Page<Object> page = PageHelper.startPage(pageParam.getPage(), pageParam.getLimit());
        List<Admin> admins = adminMapper.selectAll();
        return PageListResUtil.buildSuccessPageListRes(page,admins);
    }

    @Override
    public Boolean addAdmin(Admin admin) {
        adminMapper.insert(admin);
        return Boolean.TRUE;
    }

    @Override
    public Admin selectByAdminAccount(String adminAccount) {
        return adminMapper.selectByAdminAccount(adminAccount);
    }
}
