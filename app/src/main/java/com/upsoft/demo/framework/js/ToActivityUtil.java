package com.upsoft.demo.framework.js;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.text.TextUtils;

import java.util.List;


/**
 * Copyright (c) 2016,重庆扬讯软件技术有限公司<br>
 * All rights reserved.<br>
 *
 * 文件名称：L.java<br>
 * 摘要：ACTIVITY跳转方法<br>
 * -------------------------------------------------------<br>
 * 当前版本：1.1.1<br>
 * 作者：董杰科<br>
 * 完成日期：2016年4月26日<br>
 * -------------------------------------------------------<br>
 * 取代版本：1.1.0<br>
 * 原作者：董杰科<br>
 * 完成日期：2016年4月26日<br>
 */
public class ToActivityUtil {

    /**
     * 正常调整使用CLASS 与 其全类名
     * */
    public static void startIntentClassName(Context context, String className){
        Intent intent = new Intent();
        if (!TextUtils.isEmpty(className)){
            className = "";
        }
        intent.setClassName(context, className);
        context.startActivity(intent);
    }
    /**
     * 使用全类名跳转 带参数
     * classname = 类名
     * Action 唯一标示
     *  key—value
     * */
    public static void startIntentClassName(Context context,String className,String action,String params){
        Intent intent = new Intent();
        if (!TextUtils.isEmpty(className)){
            intent.setClassName(context, className);
        }
        if (!TextUtils.isEmpty(params)){
            intent.putExtra("params",params);
        }
        if (!TextUtils.isEmpty(action)){
            ((Activity)context).startActivityForResult(intent,Integer.valueOf(action));
        }else{
            context.startActivity(intent);
        }
    }

    /**
     * 使用全类名跳转 带参数
     * */
    public static void startIntentClassAction(Context context, String actionName){
        Intent intent = new Intent();
        intent.setAction(actionName);
        context.startActivity(intent);
    }

    /**
     *
     * @Description: 隐式启动,跳转
     * @param packageContext
     * @param action
     *  含操作的Intent
     */
    public static void startActivityIntentSafe(Context packageContext,
                                               Intent action) {
        // Verify it resolves
        PackageManager packageManager = packageContext.getPackageManager();
        List activities = packageManager.queryIntentActivities(action,
                PackageManager.MATCH_DEFAULT_ONLY);
        boolean isIntentSafe = activities.size() > 0;
        // Start an activity if it's safe
        if (isIntentSafe) {
            packageContext.startActivity(action);
        }
    }

    /**
     * @Description: 跳转
     * @param packageContext
     * from,一般传XXXActivity.this
     * @param cls
     * to,一般传XXXActivity.class
     */
    public static void toNextActivity(Context packageContext, Class<?> cls) {
        Intent i = new Intent(packageContext, cls);
        packageContext.startActivity(i);
    }

    /**
     * @Description: 跳转,带参数的方法;需要其它的数据类型,再继续重载吧
     * @param packageContext
     * @param cls
     * @param keyvalues  需要传进去的String参数{{key1,values},{key2,value2}...}
     */
    public static void toNextActivity(Context packageContext, Class<?> cls,
                                      String[][] keyvalues) {
        Intent i = new Intent(packageContext, cls);
        for (String[] strings : keyvalues) {
            i.putExtra(strings[0], strings[1]);
        }
        packageContext.startActivity(i);
    }

    public static void toNextActivityAndFinish(Context packageContext,
                                               Class<?> cls) {
        Intent i = new Intent(packageContext, cls);
        packageContext.startActivity(i);

        ((Activity) packageContext).finish();
    }

    public static void finish(Activity activity) {
        activity.finish();
    }
}