package com.chibiao.lms.service;

import com.chibiao.lms.domain.Clazz;
import com.chibiao.lms.param.PageParam;
import com.chibiao.lms.result.PageListRes;

/**
 * 请输入描述
 *
 * @author : 迟彪
 * @date : 2020/3/29
 */
public interface ClazzService {
    /**
     * 分页查询班级信息
     * @param pageParam 分页参数
     * @return list
     */
    PageListRes queryClazz(PageParam pageParam);

    /**
     * 添加班级
     * @param clazz 班级
     * @return 是否添加成功
     */
    Boolean addClazz(Clazz clazz);
}
