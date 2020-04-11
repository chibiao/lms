package com.chibiao.lms.service;

import com.chibiao.lms.domain.Department;
import com.chibiao.lms.param.PageParam;
import com.chibiao.lms.result.PageListRes;

import java.util.List;

/**
 * 请输入描述
 *
 * @author : 迟彪
 * @date : 2020/3/21
 */
public interface DepartmentService {
    /**
     * 添加院系
     * @param department 院系实体
     * @return 是否成功
     */
    Boolean addDepartment(Department department);

    /**
     * 根据院系编号删除院系
     * @param deptNo 院系编号
     * @return 是否成功
     */
    Boolean deleteDepartment(Long deptNo);

    /**
     * 更新院系信息 只更新描述和名称
     * @param department 院系实体
     * @return 是否成功
     */
    Boolean updateDepartment(Department department);

    /**
     * 分页查询院系
     * @param pageParam
     * @return
     */
    PageListRes queryDepartment(PageParam pageParam);

    /**
     * 获取所有院系
     * @return list
     */
    List<Department> allDepartment();

}
