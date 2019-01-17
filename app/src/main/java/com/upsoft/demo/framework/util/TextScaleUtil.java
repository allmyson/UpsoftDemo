package com.upsoft.demo.framework.util;

import android.app.Activity;
import android.content.res.Configuration;
import android.util.DisplayMetrics;

/**
 * @author lh
 * @version 1.0.0
 * @filename TextScaleUtil
 * @description -------------------------------------------------------
 * @date 2018/12/24 10:57
 */
public class TextScaleUtil {
    public static final int TEXTSIZE_SMALL = -1;
    public static final int TEXTSIZE_NORMAL = 0;
    public static final int TEXTSIZE_BIG = 1;

    public static void scaleTextSize(Activity activity, int type) {
        float size;
        Configuration configuration = activity.getResources().getConfiguration();
        switch (type) {
            case TEXTSIZE_SMALL:
                size = 0.85f;
                break;
            case TEXTSIZE_NORMAL:
                size = 1f;
                break;
            case TEXTSIZE_BIG:
                size = 1.5f;
                break;
            default:
                size = 1f;
                break;
        }
        configuration.fontScale = size; //0.85 small size, 1 normal size, 1.15 big etc
        DisplayMetrics metrics = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(metrics);
        metrics.scaledDensity = configuration.fontScale * metrics.density;
        activity.getBaseContext().getResources().updateConfiguration(configuration, metrics);//更新全局的配置
    }


    public static void scaleTextSize(Activity activity, boolean isScale) {
        float size;
        Configuration configuration = activity.getResources().getConfiguration();
        if (isScale) {
            size = 1.25f;
        } else {
            size = 1f;
        }
        configuration.fontScale = size; //0.85 small size, 1 normal size, 1.15 big etc
        DisplayMetrics metrics = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(metrics);
        metrics.scaledDensity = configuration.fontScale * metrics.density;
        activity.getBaseContext().getResources().updateConfiguration(configuration, metrics);//更新全局的配置
    }


    public static void scaleTextSize(Activity activity, float scale) {
        float size = scale;
        Configuration configuration = activity.getResources().getConfiguration();
        configuration.fontScale = size; //0.85 small size, 1 normal size, 1.15 big etc
        DisplayMetrics metrics = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(metrics);
        metrics.scaledDensity = configuration.fontScale * metrics.density;
        activity.getBaseContext().getResources().updateConfiguration(configuration, metrics);//更新全局的配置
    }
}
