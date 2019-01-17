package com.upsoft.sdk.util;

import android.text.TextUtils;

import com.google.gson.JsonParseException;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;

import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * Copyright (c) 2015,重庆扬讯软件技术有限公司<br>
 * All rights reserved.<br>
 * <p>
 * 文件名称：StringUtil<br>
 * 摘要：简要描述本文件的内容<br>
 * -------------------------------------------------------<br>
 * 当前版本：1.1.1<br>
 * 作者：龙辉<br>
 * 完成日期：2016/10/12<br>
 * -------------------------------------------------------<br>
 * 取代版本：1.1.0<br>
 * 原作者：龙辉<br>
 * 完成日期：2016/10/12<br>
 */
public class StringUtil {

    public static String valueOf(Object obj) {
        return obj == null ? "" : String.valueOf(obj);
    }

    public static String valueOf2(Object address) {
        String s = valueOf(address);
        if ("null".equals(s)) {
            s = "";
        }
        return s;
    }

    public static boolean isBlank(String str) {
        return str == null || "".equals(str);
    }

    public static boolean checkNull(Object checkable) {
        return checkable != null;
    }

    /**
     * @return params type 1.图片 2.录音，3视频
     * @描述: 得到当前时间的字符串
     * @retrun String
     */
    public static String getNowTimeStr(int type) {
        SimpleDateFormat sf = new SimpleDateFormat("yyyyMMddhhmmss");
        String result = "";
        if (type == 1) {
            result = sf.format(new Date()).toString() + ".jpg";
        } else if (type == 2) {
            result = sf.format(new Date()).toString() + ".mp3";
        } else if (type == 3) {
            result = sf.format(new Date()).toString() + ".mp4";
        } else if (type == 4) {
            result = sf.format(new Date()).toString();
        }
        return result;
    }


    /* 将List<V>按照V的methodName方法返回值（返回值必须为K类型）分组，合入到Map<K, List<V>>中<br>
     * 要保证入参的method必须为V的某一个有返回值的方法，并且该返回值必须为K类型
     *
             * @param list 待分组的列表
     * @param map 存放分组后的map
     * @param clazz 泛型V的类型
     * @param methodName 方法名
     */
    public static <K, V> void listGroup2Map(List<V> list, Map<K, List<V>> map, Class<V> clazz, String methodName) {
        // 入参非法行校验
        if (null == list || null == map || null == clazz || StringUtil.isBlank(methodName)) {
            L.e("CommonUtils.listGroup2Map 入参错误，list：" + list + " ；map：" + map
                    + " ；clazz：" + clazz + " ；methodName：" + methodName);
            return;
        }

        // 获取方法
        Method method = getMethodByName(clazz, methodName);
        // 非空判断
        if (null == method) {
            return;
        }
        // 正式分组
        listGroup2Map(list, map, method);
    }

    /**
     * 根据类和方法名，获取方法对象
     *
     * @param clazz
     * @param methodName
     * @return
     */
    public static Method getMethodByName(Class<?> clazz, String methodName) {
        Method method = null;
        // 入参不能为空
        if (null == clazz || StringUtil.isBlank(methodName)) {
            L.e("CommonUtils.getMethodByName 入参错误，clazz：" + clazz + " ；methodName："
                    + methodName);
            return method;
        }

        try {
            method = clazz.getDeclaredMethod(methodName);
        } catch (Exception e) {
            L.e("类获取方法失败！", e.getMessage());
        }

        return method;
    }

    /**
     * 将List<V>按照V的某个方法返回值（返回值必须为K类型）分组，合入到Map<K, List<V>>中<br>
     * 要保证入参的method必须为V的某一个有返回值的方法，并且该返回值必须为K类型
     *
     * @param list   待分组的列表
     * @param map    存放分组后的map
     * @param method 方法
     */
    @SuppressWarnings("unchecked")
    public static <K, V> void listGroup2Map(List<V> list, Map<K, List<V>> map, Method method) {
        // 入参非法行校验
        if (null == list || null == map || null == method) {
            L.e("CommonUtils.listGroup2Map 入参错误，list：" + list + " ；map：" + map
                    + " ；method：" + method);
            return;
        }
        try {
            // 开始分组
            Object key;
            List<V> listTmp;
            for (V val : list) {
                key = method.invoke(val);
                listTmp = map.get(key);
                if (null == listTmp) {
                    listTmp = new ArrayList<V>();
                    map.put((K) key, listTmp);
                }
                listTmp.add(val);
            }
        } catch (Exception e) {
            L.e("分组失败！", e.getMessage());
        }
    }

    public static String getUUID() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }

    public static int StringToInt(String data) {
        return (int) StringToDouble(data);
    }

    /**
     * 保留一位小数，四舍五入
     *
     * @param data
     * @return
     */
    public static double StringToDoubleOne(String data) {
        double result = StringToDouble(data);
        BigDecimal b = new BigDecimal(result);
        double f1 = b.setScale(1, BigDecimal.ROUND_HALF_UP).doubleValue();
        return f1;
    }

    public static double StringToDouble(String data) {
        try {
            if (data == null || "".equals(data)) {
                return 0;
            } else {
                return Double.valueOf(data);
            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
            L.e(e.getMessage());
            return 0;
        }
    }

    public static long StringToLong(String data) {
        try {
            if (data == null || "".equals(data)) {
                return 0;
            } else {
                return Long.valueOf(data);
            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
            L.e(e.getMessage());
            return 0;
        }
    }

    public static boolean isGoodJson(String json) {
        if (TextUtils.isEmpty(json)) {
            return false;
        }
        try {
            new JsonParser().parse(json);
            return true;
        } catch (JsonSyntaxException e) {
            return false;
        } catch (JsonParseException e) {
            return false;
        }
    }

}
