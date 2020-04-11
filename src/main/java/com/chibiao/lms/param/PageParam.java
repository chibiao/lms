package com.chibiao.lms.param;

import lombok.Data;

/**
 * 请输入描述
 *
 * @author : 迟彪
 * @date : 2020/3/21
 */
@Data
public class PageParam {
    // 页码
    private int page;
    // 要查询的记录条数
    private int limit;
}
