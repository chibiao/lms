package com.chibiao.lms.util;

import com.chibiao.lms.domain.ActDeploymentEntity;
import com.chibiao.lms.result.PageListRes;
import com.github.pagehelper.Page;

import java.util.List;

/**
 * layui表格出参工具类
 *
 * @author : 迟彪
 * @date : 2020/3/21
 */
public class PageListResUtil {
    public static PageListRes buildSuccessPageListRes(Page page, List list) {
        PageListRes pageListRes = new PageListRes();
        pageListRes.setData(list);
        pageListRes.setCode(0);
        pageListRes.setCount(page.getTotal());
        return pageListRes;
    }
    public static PageListRes buildSuccessListRes(List list) {
        PageListRes pageListRes = new PageListRes();
        pageListRes.setData(list);
        pageListRes.setCode(0);
        pageListRes.setCount((long)list.size());
        return pageListRes;
    }

    public static PageListRes buildSuccessCountListRes(long count, List list) {
        PageListRes pageListRes = new PageListRes();
        pageListRes.setData(list);
        pageListRes.setCode(0);
        pageListRes.setCount(count);
        return pageListRes;
    }
}
