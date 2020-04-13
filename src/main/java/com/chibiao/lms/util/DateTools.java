package com.chibiao.lms.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 日期处理工具类
 *
 * @author : 迟彪
 * @date : 2020/4/12
 */
public class DateTools {
    /**
     * 日期转换为星期工具类 周日为 0 周一为 1 ...
     * @param datetime 时间
     * @return 周
     */
    public static Integer dateToWeek(Date datetime) {
        SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd");
        Calendar cal = Calendar.getInstance(); // 获得一个日历
        cal.setTime(datetime);
        int w = cal.get(Calendar.DAY_OF_WEEK) - 1; // 指示一个星期中的某天。
        if (w < 0){
            w = 0;
        }
        return w;
    }
}
