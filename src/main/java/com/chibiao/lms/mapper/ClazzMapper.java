package com.chibiao.lms.mapper;

import com.chibiao.lms.domain.Clazz;
import java.util.List;

public interface ClazzMapper {

    int insert(Clazz record);

    List<Clazz> selectAll();

}