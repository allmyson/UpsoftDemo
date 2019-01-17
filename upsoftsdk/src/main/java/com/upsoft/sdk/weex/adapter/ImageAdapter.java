package com.upsoft.sdk.weex.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.taobao.weex.WXSDKManager;
import com.taobao.weex.adapter.IWXImgLoaderAdapter;
import com.taobao.weex.common.WXImageStrategy;
import com.taobao.weex.dom.WXImageQuality;


/**
 * @author lh
 * @version 1.0.0
 * @filename ImageAdapter
 * @description -------------------------------------------------------
 * @date 2017/9/5 9:29
 */
public class ImageAdapter implements IWXImgLoaderAdapter {
    private Context context;

    public ImageAdapter(Context context) {
        this.context = context;
    }

    @Override
    public void setImage(final String url, final ImageView view, WXImageQuality quality, WXImageStrategy strategy) {
//        view.setScaleType(ImageView.ScaleType.CENTER_CROP);
        WXSDKManager.getInstance().postOnUiThread(new Runnable() {

            @Override
            public void run() {
                String temp = url;
                if (TextUtils.isEmpty(temp)) {
                    return;
                }
                if (temp.startsWith("http://") || temp.startsWith("https://")) {
                    Glide.with(context).load(temp).into(view);
                } else if (temp.startsWith("drawable://")) {
                    int resId = getId(temp.replace("drawable://", ""), "drawable");
                    if (resId != 0) {
                        Glide.with(context).load(resId).into(view);
                    }
                    Glide.with(context).load(resId).into(view);
                } else if (temp.startsWith("mipmap://")) {
                    int resId = getId(temp.replace("mipmap://", ""), "mipmap");
                    if (resId != 0) {
                        Glide.with(context).load(resId).into(view);
                    }
                } else if (temp.startsWith("file://")) {
                    Glide.with(context).load(temp).into(view);
                } else if (temp.startsWith("assets://")) {
                    Glide.with(context).load(getAssetsPath(temp)).into(view);
                }
//        else {
//            Glide.with(context).load(temp).into(view);
//        }
            }
        }, 0);
    }

    /**
     * @param imageName 图片名称 eg  ic_launcher
     * @param className "drawable"   "mipmap"
     * @return
     */
    private int getId(String imageName, String className) {
        int resId = context.getResources().getIdentifier(imageName, className, context.getPackageName());
        //如果没有在"mipmap"下找到imageName,将会返回0
        return resId;
    }

    /**
     * @param assets assets://aaa.png
     * @return
     */
    private String getAssetsPath(String assets) {
        String a = assets;
        a = a.replace("assets://", "file:///android_asset/");
        return a;
    }
}
