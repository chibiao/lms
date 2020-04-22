package com.chibiao.lms.service;

import com.chibiao.lms.domain.Admin;
import com.chibiao.lms.param.PageParam;
import com.chibiao.lms.result.PageListRes;

/**
 * 管理员服务service
 *
 * @author : 迟彪
 * @date : 2020/4/22
 */
public interface AdminService {
    /**
     * 分页查询管理员列表
     * @param pageParam
     * @return
     */
    PageListRes queryAdminList(PageParam pageParam);

    /**
     * 添加管理员信息
     * @param admin 管理员
     * @return 是否添加成功
     */
    Boolean addAdmin(Admin admin);
}
