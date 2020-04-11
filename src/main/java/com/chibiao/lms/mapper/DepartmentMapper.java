package com.chibiao.lms.mapper;

import com.chibiao.lms.domain.Department;
import java.util.List;

public interface DepartmentMapper {
    /**
     * 添加院系
     * @param record
     * @return
     */
    int insert(Department record);

    /**
     * 根据院系编号删除院系
     * @param deptNo
     * @return
     */
    int deleteDepartment(Long deptNo);

    /**
     * 根据院系编号 更新院系的名称和描述
     * @param department
     * @return
     */
    int updateByDeptNo(Department department);

    /**
     * 查询院系
     * @return
     */
    List<Department> queryDepartment();

    /**
     * 根据院系编号查询院系
     * @param deptNo 院系编号
     * @return 院系
     */
    Department selectByDeptNo(Long deptNo);
}