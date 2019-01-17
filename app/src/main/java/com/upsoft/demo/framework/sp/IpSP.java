package com.upsoft.demo.framework.sp;


import android.content.Context;

import com.alibaba.fastjson.JSONArray;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.upsoft.demo.framework.bean.NetworkBean;
import com.upsoft.sdk.sp.SPUtil;
import com.upsoft.sdk.util.L;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


public class IpSP {
    private static final String IP = "ip";
    private static final String ADD_NAME = "addName";
    private static final String ADD_ADDRESS = "addAddress";
    private static final String IP_LIST = "ipList";


    /**
     * 获取当前Ip
     *
     * @param context
     * @return
     */

    public static String getIp(Context context) {
        return SPUtil.get(context, IP, "") + "";
    }

    public static String getHttpIp(Context context) {
        return "http://" + SPUtil.get(context, IP, "") + "";
    }


    /**
     * 保存Ip
     *
     * @param context
     * @param ipAddress
     */

    public static void saveIp(Context context, String ipAddress) {
        SPUtil.put(context, IP, ipAddress);
    }


    /**
     * 获取自定义Ip名字
     *
     * @param context
     * @return
     */

    public static String getAddIpName(Context context) {
        return SPUtil.get(context, ADD_NAME, "") + "";
    }

    /**
     * 获取自定义Ip地址
     *
     * @param context
     * @return
     */

    public static String getAddIpAres(Context context) {
        return SPUtil.get(context, ADD_ADDRESS, "") + "";
    }


    /**
     * 保存自定义Ip
     *
     * @param context
     * @param
     */
    public static void saveAddIp(Context context, String name, String address) {
        SPUtil.put(context, ADD_NAME, name);
        SPUtil.put(context, ADD_ADDRESS, address);
    }

    public static List<String> getNameAndAddress(Context context) {
        List<String> list = new ArrayList<>();
        String addName = (String) SPUtil.get(context, ADD_NAME, "");
        String addAddress = (String) SPUtil.get(context, ADD_ADDRESS, "");
        if (!"".equals(addName) && !"".equals(addAddress)) {
            list.add(addName);
            list.add(addAddress);
        }
        return list;
    }

    /**
     * 保存Ip列表
     *
     * @param context
     * @param list
     */
    public static void saveIPList(Context context, List<NetworkBean> list) {
        JSONArray json = (JSONArray) JSONArray.toJSON(list);
        L.e("json数据", json.toString());
        SPUtil.put(context, IP_LIST, json.toString());
    }


    /**
     * 获取IP列表
     *
     * @return
     */
    public static List<NetworkBean> getIPList(Context context) {
        String list = SPUtil.get(context, IP_LIST, "") + "";
        L.e("json数据2", list);
        List<NetworkBean> networkBeans = new ArrayList<>();
        networkBeans.addAll((Collection<? extends NetworkBean>) new Gson().fromJson(list, new TypeToken<List<NetworkBean>>() {
        }.getType()));
        return networkBeans;
    }

}
