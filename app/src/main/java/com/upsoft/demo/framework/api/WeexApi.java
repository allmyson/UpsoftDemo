package com.upsoft.demo.framework.api;

import android.content.Context;
import android.content.Intent;

import com.upsoft.demo.framework.weex.activity.WeexPageActivity;

/**
 * @author lh
 * @version 1.0.0
 * @filename WeexApi
 * @description -------------------------------------------------------
 * @date 2018/10/8 14:14
 */
public class WeexApi {
    public static void loadWX(Context context, int type, String path) {
        Intent intent = new Intent(context, WeexPageActivity.class);
        intent.putExtra("type", type);
        intent.putExtra("path", path);
        context.startActivity(intent);
    }
}
