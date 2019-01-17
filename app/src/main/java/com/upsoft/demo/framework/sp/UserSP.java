package com.upsoft.demo.framework.sp;

import android.content.Context;

import com.google.gson.Gson;
import com.upsoft.demo.framework.bean.InfoBean;
import com.upsoft.sdk.sp.SPUtil;
import com.upsoft.sdk.util.StringUtil;

/**
 * @author lh
 * @version 1.0.0
 * @filename UserSP
 * @description -------------------------------------------------------
 * @date 2017/10/10 14:12
 */
public class UserSP {
    public static final String KEY_INFO = "upsoft_userinfo";
    public static final String KEY_USERNAME = "upsoft_username";
    public static final String KEY_PASSWORD = "upsoft_password";
    public static final String KEY_REMENBER = "upsoft_remenber";
    public static final String KEY_USERROLE = "upsoft_user_role";//角色
    public static final String BIZ_CODE = "ldjsc";//领导驾驶舱角色业务编码

    //是否登录
    public static boolean isLogin(Context context) {
        String json = getUserInfo(context);
        if (StringUtil.isBlank(json)) {
            return false;
        } else {
            return true;
        }
    }

    public static void saveUsername(Context context, String username) {
        SPUtil.put(context, KEY_USERNAME, username);
    }

    public static void savePassword(Context context, String password) {
        //正常需加密
        SPUtil.put(context, KEY_PASSWORD, password);
    }

    public static void saveUserRole(Context context, String userRole) {
        SPUtil.put(context, KEY_USERROLE, userRole);
    }

    public static String getUsername(Context context) {
        return (String) SPUtil.get(context, KEY_USERNAME, "");
    }

    public static String getPassword(Context context) {
        //正常取出来需解密
        return (String) SPUtil.get(context, KEY_PASSWORD, "");
    }

    public static String getUserRole(Context context) {
        return (String) SPUtil.get(context, KEY_USERROLE, "");
    }


    public static void saveInfo(Context context, String infoJson) {
        SPUtil.put(context, KEY_INFO, infoJson);
    }

    public static String getUserInfo(Context context) {
        return (String) SPUtil.get(context, KEY_INFO, "");
    }


    public static InfoBean getInfo(Context context) {
        InfoBean infoBean = null;
        String json = getUserInfo(context);
        if (!StringUtil.isBlank(json)) {
            infoBean = new Gson().fromJson(json, InfoBean.class);
        }
        return infoBean;
    }

    public static void setRemenber(Context context, boolean isRemenber) {
        SPUtil.put(context, KEY_REMENBER, isRemenber);
    }

    public static boolean isRemenber(Context context) {
        return (boolean) SPUtil.get(context, KEY_REMENBER, false);
    }

    public static void clear(Context context) {
        SPUtil.remove(context, KEY_INFO);
        SPUtil.remove(context, KEY_USERNAME);
        SPUtil.remove(context, KEY_PASSWORD);
        SPUtil.remove(context, KEY_REMENBER);
    }
}
