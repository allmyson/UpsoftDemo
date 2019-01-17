package com.upsoft.demo.framework.sp;

import android.content.Context;

import com.upsoft.sdk.sp.SPUtil;

/**
 * @author lh
 * @version 1.0.0
 * @filename ColorSP
 * @description -------------------------------------------------------
 * @date 2018/10/22 13:59
 */
public class ColorSP {
    public static final String KEY_TITLE = "upsoft_color_title";//标题栏
    public static final String KEY_BAR = "upsoft_color_bar";//状态栏
    public static final String KEY_BUTTON = "upsoft_color_btn";//按钮
    public static final String KEY_TEXT_ICON = "upsoft_color_text_icon";//部分文字及图标
    public static final String KEY_ALPHA = "upsoft_color_alpha";//部分文字及图标
    private static String defaultTitleColor = "#2196F3";
    private static String defaultBarColor = "#2196F3";
    private static String defaultBtnColor = "#2196F3";
    private static String defaultTextIconColor = "#2196F3";
    private static int defaultAlpha = 255;//默认透明度

    /**
     * 设置默认标题栏颜色
     *
     * @param color #2196F3
     */
    public static void setDefaultTitleColor(String color) {
        defaultTitleColor = color;
    }

    /**
     * 设置默认状态栏颜色
     *
     * @param color #2196F3
     */
    public static void setDefaultBarColor(String color) {
        defaultBarColor = color;
    }

    /**
     * 设置按钮背景颜色
     *
     * @param color
     */
    public static void setDefaultBtnColor(String color) {
        defaultBtnColor = color;
    }

    public static void setDefaultTextIconColor(String color) {
        defaultTextIconColor = color;
    }

    public static String getDefaultBtnColor() {
        return defaultBtnColor;
    }

    public static String getDefaultTextIconColor() {
        return defaultTextIconColor;
    }

    public static String getDefaultTitleColor() {
        return defaultTitleColor;
    }

    public static String getDefaultBarColor() {
        return defaultBarColor;
    }

    /**
     * @param context
     * @param titleColor #000000
     */
    public static void saveTitleColor(Context context, String titleColor) {
        SPUtil.put(context, KEY_TITLE, titleColor);
    }

    /**
     * @param context
     * @param barColor #000000
     */
    public static void saveBarColor(Context context, String barColor) {
        SPUtil.put(context, KEY_BAR, barColor);
    }

    /**
     * @param context
     * @param btnColor #000000
     */
    public static void saveBtnColor(Context context, String btnColor) {
        SPUtil.put(context, KEY_BUTTON, btnColor);
    }

    public static void saveTextIconColor(Context context, String textIconColor) {
        SPUtil.put(context, KEY_TEXT_ICON, textIconColor);
    }

    public static String getTitleColor(Context context) {
        return SPUtil.get(context, KEY_TITLE, defaultTitleColor) + "";
    }

    public static String getBarColor(Context context) {
        return SPUtil.get(context, KEY_BAR, defaultBarColor) + "";
    }

    public static String getBtnColor(Context context) {
        return SPUtil.get(context, KEY_BUTTON, defaultBtnColor) + "";
    }

    public static String getTextIconColor(Context context) {
        return SPUtil.get(context, KEY_TEXT_ICON, defaultTextIconColor) + "";
    }

    public static void saveAlpha(Context context,int alpha){
        SPUtil.put(context, KEY_ALPHA, alpha);
    }
    public static int getAlpha(Context context) {
        return (int) SPUtil.get(context, KEY_ALPHA, defaultAlpha);
    }
}
