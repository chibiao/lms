package com.chibiao.lms.mapper;

import com.chibiao.lms.domain.Specialty;

import java.util.List;

public interface SpecialtyMapper {

    /**
     * 添加专业信息
     *
     * @param record
     * @return
     */
    int insert(Specialty record);


    /**
     * 根据专业编号更新专业信息
     *
     * @param specialty
     * @return
     */
    int updateBySpecialtyNo(Specialty specialty);

    /**
     * 根据专业编号 删除专业信息
     *
     * @param specialtyNo
     * @return
     */
    int deleteBySpecialtyNo(Long specialtyNo);

    /**
     * 查询所有专业信息
     * @return
     */
    List<Specialty> querySpecialty();

    /**
     * 根据院系编号查询专业
     * @param detpNo
     * @return
     */
    List<Specialty> querySpecialtyByDeptNo(Long detpNo);

    /**
     * 根据专业编号查询专业
     * @param specialtyNo 专业编号
     * @return
     */
    Specialty selectBySpecialtyNo(Long specialtyNo);
}