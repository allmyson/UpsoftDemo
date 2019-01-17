package com.upsoft.demo.framework.weex.activity;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.taobao.weex.IWXRenderListener;
import com.taobao.weex.WXSDKInstance;
import com.taobao.weex.bridge.JSCallback;
import com.taobao.weex.common.WXRenderStrategy;
import com.upsoft.demo.framework.activity.NFCScanActivity;
import com.upsoft.demo.framework.api.NativeUtil;
import com.upsoft.demo.framework.base.BaseActivity;
import com.upsoft.qrlibrary.CaptureActivity;
import com.upsoft.sdk.util.L;
import com.upsoft.sdk.util.ScreenUtil;
import com.yongchun.library.view.ImageSelectorActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public abstract class BaseWeexActivity extends BaseActivity implements IWXRenderListener {
    public static final int REQUEST_NFC = 501;//打开NFC请求码
    public static final int REQUEST_QRCODE = 502;//打开二维码扫描请求码
    public static final int REQUEST_TAKE_PHOTO = 503;//拍照
    protected WXSDKInstance mWXSDKInstance;
    protected ProgressBar mProgressBar;
    protected ViewGroup container;
    protected JSCallback jsCallback;

    @Override
    public void initView() {
        createWeexInstance();
    }

    @Override
    public void onViewCreated(WXSDKInstance instance, View view) {
        Log.e("lh", "onViewCreated执行");
        if (mProgressBar != null) {
            mProgressBar.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onRenderSuccess(WXSDKInstance instance, int width, int height) {
        Log.e("lh", "onRenderSuccess执行");
        if (mProgressBar != null) {
            mProgressBar.setVisibility(View.GONE);
        }
    }

    @Override
    public void onRefreshSuccess(WXSDKInstance instance, int width, int height) {
        Log.e("lh", "onRefreshSuccess执行");
        if (mProgressBar != null) {
            mProgressBar.setVisibility(View.GONE);
        }
    }

    @Override
    public void onException(WXSDKInstance instance, String errCode, String msg) {
        Log.e("lh", "onException执行");

        Log.e("lh", "errCode=" + errCode + "--msg=" + msg);
        if (mProgressBar != null) {
            mProgressBar.setVisibility(View.GONE);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mWXSDKInstance != null) {
            mWXSDKInstance.onActivityResume();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mWXSDKInstance != null) {
            mWXSDKInstance.onActivityPause();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mWXSDKInstance != null) {
            mWXSDKInstance.onActivityStop();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mWXSDKInstance != null) {
            mWXSDKInstance.onActivityDestroy();
        }
    }

    public JSCallback getJsCallback() {
        return jsCallback;
    }

    public void setJsCallback(JSCallback jsCallback) {
        this.jsCallback = jsCallback;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK && requestCode == ImageSelectorActivity.REQUEST_IMAGE) {
            ArrayList<String> images = (ArrayList<String>) data.getSerializableExtra(ImageSelectorActivity
                    .REQUEST_OUTPUT);
            if (images != null && images.size() > 0) {
                for (String path : images) {
                    L.e("图片地址：" + path);
                }
                if (jsCallback != null) {
                    Map<String, Object> result = new HashMap<>();
                    result.put("status", 1);
                    result.put("msg", "success");
                    result.put("data", images);
                    jsCallback.invoke(result);
                    jsCallback = null;
                }
            }
        } else if (resultCode == RESULT_OK && requestCode == REQUEST_NFC) {
            String result = data.getStringExtra(NFCScanActivity.NFC_RESULT);
            if (jsCallback != null) {
                Map<String, String> map = new HashMap<>();
                map.put("data", result);
                jsCallback.invoke(map);
                jsCallback = null;
            }
        } else if (resultCode == RESULT_OK && requestCode == REQUEST_QRCODE) {
            String result = data.getStringExtra(CaptureActivity.SCAN_RESULT);
            if (jsCallback != null) {
                Map<String, String> map = new HashMap<>();
                map.put("data", result);
                jsCallback.invoke(map);
                jsCallback = null;
            }
        } else if (resultCode == RESULT_OK && requestCode == REQUEST_TAKE_PHOTO) {
            String imgPath = NativeUtil.imgPath;
            L.e("imagePath=" + imgPath);
            if (jsCallback != null) {
                Map<String, String> map = new HashMap<>();
                map.put("data", imgPath);
                jsCallback.invoke(map);
                jsCallback = null;
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onClick(View v) {

    }

    protected void createWeexInstance() {
        destoryWeexInstance();
        mWXSDKInstance = new WXSDKInstance(this);
        mWXSDKInstance.registerRenderListener(this);
    }

    protected void destoryWeexInstance() {
        if (mWXSDKInstance != null) {
            mWXSDKInstance.registerRenderListener(null);
            mWXSDKInstance.destroy();
            mWXSDKInstance = null;
        }
    }


    protected void renderPageByURL(String url) {
        renderPageByURL(url, null);
    }

    protected void renderPageByURL(String url, String jsonInitData) {
        if (container == null) {
            throw new RuntimeException("Can't render page, container is null");
        }
        Map<String, Object> options = new HashMap<>();
        options.put(WXSDKInstance.BUNDLE_URL, url);
        mWXSDKInstance.renderByUrl(
                this.getClass().getSimpleName(),
                url,
                options,
                jsonInitData,
                ScreenUtil.getScreenWidth(this),
                ScreenUtil.getScreenHeight(this),
                WXRenderStrategy.APPEND_ASYNC);
    }
}
