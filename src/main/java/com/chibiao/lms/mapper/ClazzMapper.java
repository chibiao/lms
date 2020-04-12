package com.chibiao.lms.mapper;

import com.chibiao.lms.domain.Clazz;
import java.util.List;

public interface ClazzMapper {

    int insert(Clazz record);

    List<Clazz> selectAll();

    Clazz selectByClazzNo(Long clazzNo);

    /**
     * 根据专业编号查询班级list
     * @param specialtyNo 专业编号
     * @return
     */
    List<Clazz> selectBySpecialtyNo(Long specialtyNo);
}