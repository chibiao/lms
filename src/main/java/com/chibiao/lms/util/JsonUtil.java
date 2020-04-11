package com.chibiao.lms.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;

import java.util.List;

/**
 * json工具类
 *
 * @author : 迟彪
 * @date : 2020/3/29
 */
public class JsonUtil {
    private JsonUtil() {
        throw new UnsupportedOperationException("can't instance this class");
    }

    /**
     * 对象转json字符串,日期为数字
     */
    public static String toJSONString(Object object){
        if(object == null){
            return null;
        }
        return JSON.toJSONString(object);
    }

    /**
     * 解析json字符串到对象实例
     */
    public static <T> T parseObject(String json, Class<T> clazz) {
        return JSON.parseObject(json, clazz);
    }

    /**
     * 解析json字符串到List
     */
    public static <T> List<T> parseArray(String json, Class<T> clazz) {
        return JSON.parseArray(json, clazz);
    }

    /**
     * 解析json字符到JSONObject对象,用于无固定对象场景,不建议使用,所有json字符串建议对象化
     */
    public static JSONObject parseObject(String json) {
        return JSONObject.parseObject(json);
    }

    public static <T> T parseObject(String json, TypeReference<T> type) {
        return JSON.parseObject(json, type);
    }
}
