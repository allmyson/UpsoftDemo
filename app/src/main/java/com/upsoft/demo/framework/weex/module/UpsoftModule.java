package com.upsoft.demo.framework.weex.module;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.taobao.weex.annotation.JSMethod;
import com.taobao.weex.bridge.JSCallback;
import com.taobao.weex.common.WXModule;
import com.upsoft.demo.framework.activity.NFCScanActivity;
import com.upsoft.demo.framework.api.FunctionApi;
import com.upsoft.demo.framework.api.NativeUtil;
import com.upsoft.demo.framework.weex.activity.BaseWeexActivity;
import com.upsoft.qrlibrary.CaptureActivity;
import com.upsoft.sdk.util.L;
import com.yongchun.library.view.ImageSelectorActivity;

import java.util.HashMap;
import java.util.Map;

/**
 * @author lh
 * @version 1.0.0
 * @filename UpsoftModule
 * @description -------------------------------------------------------
 * @date 2017/10/25 14:56
 */
public class UpsoftModule extends WXModule {

    /**
     * @param jsCallback
     * @param max           最大选择张数
     * @param mode          选择模式 1多选，2单
     * @param showCamera    显示拍照
     * @param enablePreview 预览
     * @param enableCrop    剪切
     */
    @JSMethod(uiThread = true)
    public void choosePic2(int max, int mode, boolean showCamera, boolean enablePreview,
                          boolean enableCrop, JSCallback jsCallback) {
        if ((ActivityCompat.checkSelfPermission(mWXSDKInstance.getContext(), Manifest.permission
                .WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)) {
//            SdkToastUtil.show(mWXSDKInstance.getContext(), "请开启相关权限");
            ActivityCompat.requestPermissions((BaseWeexActivity) mWXSDKInstance.getContext(), new String[]{Manifest
                    .permission.WRITE_EXTERNAL_STORAGE}, 1);
            return;
        }
        if ((ActivityCompat.checkSelfPermission(mWXSDKInstance.getContext(), Manifest.permission
                .CAMERA) != PackageManager.PERMISSION_GRANTED)) {
            ActivityCompat.requestPermissions((BaseWeexActivity) mWXSDKInstance.getContext(), new String[]{Manifest
                    .permission.CAMERA}, 1);
            return;
        }
        ((BaseWeexActivity) mWXSDKInstance.getContext()).setJsCallback(jsCallback);
        ImageSelectorActivity.start((BaseWeexActivity) mWXSDKInstance.getContext(), max, mode, showCamera,
                enablePreview, enableCrop);
    }

    /**
     * @param param      json{
     *                   mode:1~2
     *                   max:1~9
     *                   showCamera:true/false
     *                   enablePreview:true/false
     *                   enableCrop：true/false
     *                   }
     * @param jsCallback
     */
    @JSMethod(uiThread = true)
    public void choosePic(String param, JSCallback jsCallback) {
        try {
            JSONObject jsonObject = JSON.parseObject(param);
            int mode = jsonObject.getInteger("mode");
            if (mode != 1) {
                mode = 2;
            }
            int max = jsonObject.getInteger("max");
            if (max < 1) {
                max = 1;
            }
            if (max > 9) {
                max = 9;
            }
            boolean isShowCamera = true;
            boolean isPreview = true;
            boolean isCrop = false;
            String showCamera = jsonObject.getString("showCamera");
            if ("false".equals(showCamera)) {
                isShowCamera = false;
            }
            String enablePreview = jsonObject.getString("enablePreview");
            if ("false".equals(enablePreview)) {
                isPreview = false;
            }
            String enableCrop = jsonObject.getString("enableCrop");
            if ("true".equals(enableCrop)) {
                isCrop = true;
            }
            choosePic2(max, mode, isShowCamera, isPreview, isCrop, jsCallback);
        } catch (Exception e) {
            L.e(e.getMessage());
            if (jsCallback != null) {
                Map<String, Object> result = new HashMap<>();
                result.put("status", 0);
                result.put("msg", e.getMessage());
                jsCallback.invoke(result);
            }
        }
    }

    /**
     * 打开NFC
     *
     * @param jsCallback
     */
    @JSMethod(uiThread = true)
    public void intentToNFC(JSCallback jsCallback) {
        Intent intent = new Intent(mWXSDKInstance.getContext(), NFCScanActivity.class);
        ((BaseWeexActivity) mWXSDKInstance.getContext()).setJsCallback(jsCallback);
        ((BaseWeexActivity) mWXSDKInstance.getContext()).startActivityForResult(intent, BaseWeexActivity.REQUEST_NFC);
    }

    /**
     * 二维码扫描
     *
     * @param jsCallback
     */
    @JSMethod(uiThread = true)
    public void intentToCapture(JSCallback jsCallback) {
        Intent intent = new Intent(mWXSDKInstance.getContext(), CaptureActivity.class);
        ((BaseWeexActivity) mWXSDKInstance.getContext()).setJsCallback(jsCallback);
        ((BaseWeexActivity) mWXSDKInstance.getContext()).startActivityForResult(intent, BaseWeexActivity
                .REQUEST_QRCODE);

    }

    /**
     * 拍照
     *
     * @param jsCallback
     */
    @JSMethod(uiThread = true)
    public void takePhoto(JSCallback jsCallback) {
        ((BaseWeexActivity) mWXSDKInstance.getContext()).setJsCallback(jsCallback);
        NativeUtil.intentToCamera(mWXSDKInstance.getContext(), BaseWeexActivity.REQUEST_TAKE_PHOTO);
    }

    /**
     * 查看大图
     * @param path
     * @param position
     */
    @JSMethod(uiThread = true)
    public void lookBigPic(String[] path, int position) {
        FunctionApi.LargerImage(mWXSDKInstance.getContext(), path, position);
    }
}
