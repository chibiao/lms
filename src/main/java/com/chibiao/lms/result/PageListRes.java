package com.chibiao.lms.result;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * 分页结果
 *
 * @author : 迟彪
 * @date : 2020/3/21
 */
@Data
public class PageListRes {
    private int code;
    private String msg;
    private Long count;
    private List<?> data=new ArrayList<>();
}
