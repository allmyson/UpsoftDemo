package com.upsoft.demo.framework.sp;

import android.content.Context;

import com.upsoft.sdk.sp.SPUtil;

/**
 * @author lh
 * @version 1.0.0
 * @filename TextSizeSP
 * @description -------------------------------------------------------
 * @date 2018/12/24 15:53
 */
public class TextSizeSP {
    public static final String KEY_SIZE_SCALE = "upsoft_text_size_scale";//字体大小

    public static void saveTextSizeScale(Context context, float scale) {
        SPUtil.put(context, KEY_SIZE_SCALE, scale);
    }

    public static float getTextSizeScale(Context context) {
        return (float) SPUtil.get(context, KEY_SIZE_SCALE, 1f);
    }
}
