package com.chibiao.lms.mapper;

import com.chibiao.lms.domain.Admin;
import java.util.List;

public interface AdminMapper {

    int insert(Admin record);


    List<Admin> selectAll();

}