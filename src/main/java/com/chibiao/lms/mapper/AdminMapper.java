package com.chibiao.lms.mapper;

import com.chibiao.lms.domain.Admin;
import java.util.List;

public interface AdminMapper {

    int insert(Admin record);


    List<Admin> selectAll();

    /**
     * 根据账户查询管理员信息
     * @param adminAccount
     * @return
     */
    Admin selectByAdminAccount(String adminAccount);
}