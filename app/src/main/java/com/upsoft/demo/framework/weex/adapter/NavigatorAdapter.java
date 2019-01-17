package com.upsoft.demo.framework.weex.adapter;

import android.content.Context;
import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.taobao.weex.appfram.navigator.IActivityNavBarSetter;
import com.taobao.weex.common.Constants;
import com.upsoft.demo.framework.api.WeexApi;
import com.upsoft.demo.framework.weex.Constant_;
import com.upsoft.demo.framework.weex.activity.WeexPageActivity;

/**
 * @author lh
 * @version 1.0.0
 * @filename NavigatorAdapter
 * @description -------------------------------------------------------
 * @date 2018/10/15 13:57
 */
public class NavigatorAdapter implements IActivityNavBarSetter {
    private Context mContext;

    public NavigatorAdapter(Context mContext) {
        this.mContext = mContext;
    }

    @Override
    public boolean push(String param) {
        Log.e("lh", Thread.currentThread().getId() + "");
        Log.e("lh", param.toString());
        try {
            JSONObject jsonObject = JSON.parseObject(param);
            String url = jsonObject.getString("url");
            if (!TextUtils.isEmpty(url)) {
                Uri rawUri = Uri.parse(url);
                String scheme = rawUri.getScheme();
                Uri.Builder builder = rawUri.buildUpon();
                if (TextUtils.isEmpty(scheme)) {
                    builder.scheme(Constants.Scheme.HTTP);
                }
                Uri newUri = builder.build();
                Log.e("lh", newUri.getScheme());
                if ("http".equals(newUri.getScheme()) || "https".equals(newUri.getScheme())) {
                    String weexTpl = newUri.getQueryParameter(Constant_.Weex.WEEX_TPL_KEY);
                    if (!TextUtils.isEmpty(weexTpl)) {
                        url = weexTpl;
                    }
                    WeexApi.loadWX(mContext, WeexPageActivity.TYPE_NET, url);
                } else if ("sdcard".equals(newUri.getScheme())) {
                    WeexApi.loadWX(mContext, WeexPageActivity.TYPE_SDCARD, url);
                } else if ("file".equals(newUri.getScheme())) {
                    WeexApi.loadWX(mContext, WeexPageActivity.TYPE_ASSETS, url);
                }
            }
        } catch (Exception e) {
            Log.e("lh", e.getMessage());
            return true;
        }

        return true;
    }

    @Override
    public boolean pop(String param) {
        return false;
    }

    @Override
    public boolean setNavBarRightItem(String param) {
        return false;
    }

    @Override
    public boolean clearNavBarRightItem(String param) {
        return false;
    }

    @Override
    public boolean setNavBarLeftItem(String param) {
        return false;
    }

    @Override
    public boolean clearNavBarLeftItem(String param) {
        return false;
    }

    @Override
    public boolean setNavBarMoreItem(String param) {
        return false;
    }

    @Override
    public boolean clearNavBarMoreItem(String param) {
        return false;
    }

    @Override
    public boolean setNavBarTitle(String param) {
        return false;
    }

}

