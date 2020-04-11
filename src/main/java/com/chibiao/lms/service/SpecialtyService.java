package com.chibiao.lms.service;

import com.chibiao.lms.domain.Specialty;
import com.chibiao.lms.param.PageParam;
import com.chibiao.lms.result.PageListRes;

import java.util.List;

/**
 * 请输入描述
 *
 * @author : 迟彪
 * @date : 2020/3/21
 */
public interface SpecialtyService {
    /**
     * 添加专业
     *
     * @param specialty 专业实体
     * @return 是否成功
     */
    Boolean addSpecialty(Specialty specialty);

    /**
     * 根据专业编号更新专业信息
     *
     * @param specialty 专业实体
     * @return 是否成功
     */
    Boolean updateSpecialty(Specialty specialty);

    /**
     * 根据专业编号删除专业信息
     *
     * @param specialtyNo 专业编号
     * @return 是否成功
     */
    Boolean deleteSpecialty(Long specialtyNo);

    /**
     * 分页查询专业信息
     * @param pageParam 分页参数
     * @return 专业list
     */
    PageListRes querySpecialty(PageParam pageParam);

    /**
     * 根据院系编号查询专业list
     * @param detpNo 院系编号
     * @return 专业list
     */
    List<Specialty> specialtyByDeptNo(Long detpNo);
}
