package com.upsoft.demo.framework.activity;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.upsoft.demo.R;
import com.upsoft.demo.framework.api.WeexApi;
import com.upsoft.demo.framework.base.BaseActivity;
import com.upsoft.demo.framework.weex.activity.WeexPageActivity;
import com.upsoft.sdk.Constant;
import com.upsoft.sdk.util.ToastUtil;

public class WeexTestActivity extends BaseActivity {
    private ListView lv;
    private ArrayAdapter<String> mAdapter;
    String[] list = new String[]{"加载assets文件", "加载网络文件", "加载SDCard文件", "图片加载", "网络请求", "Weex Navigator", "weex选择图片",
            "NFC扫描", "二维码扫描", "拍照", "查看大图"};
    @Override
    public int getLayoutId() {
        return R.layout.activity_weex_test;
    }

    @Override
    public void initView() {
        init();
    }

    @Override
    public void getData() {

    }

    @Override
    public void onClick(View v) {

    }


    private void init() {
        lv = getView(R.id.lv);
        mAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, list);
        lv.setAdapter(mAdapter);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        WeexApi.loadWX(mContext, WeexPageActivity.TYPE_ASSETS, "file://assets/js/text.js");
                        break;
                    case 1:
                        WeexApi.loadWX(mContext, WeexPageActivity.TYPE_NET,
                                "http://dotwe.org/raw/dist/f6b32e5aa7de30f93c3fe1331872cae9.bundle.wx");
                        break;
                    case 2:
                        if ((ActivityCompat.checkSelfPermission(mContext, Manifest.permission.READ_EXTERNAL_STORAGE)
                                != PackageManager.PERMISSION_GRANTED) ||
                                (ActivityCompat.checkSelfPermission(mContext, Manifest.permission
                                        .WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)) {
                            ToastUtil.show(mContext, "请开启相关权限");
                            ActivityCompat.requestPermissions((Activity) mContext, new String[]{Manifest.permission
                                    .READ_EXTERNAL_STORAGE, Manifest
                                    .permission.WRITE_EXTERNAL_STORAGE}, 1);
                            return;
                        }
                        WeexApi.loadWX(mContext, WeexPageActivity.TYPE_SDCARD, "sdcard://" + Constant.WEEX_PATH
                                + "/RichText.js");
                        break;
                    case 3:
                        WeexApi.loadWX(mContext, WeexPageActivity.TYPE_ASSETS, "file://assets/js/image.js");
                        break;
                    case 4:
                        //网络请求
                        WeexApi.loadWX(mContext, WeexPageActivity.TYPE_ASSETS, "file://assets/js/http.js");
                        break;
                    case 5:
                        //weex 路由
                        WeexApi.loadWX(mContext, WeexPageActivity.TYPE_ASSETS, "file://assets/js/navigator.js");
                        break;
                    case 6:
                        //图片选择
                        WeexApi.loadWX(mContext, WeexPageActivity.TYPE_ASSETS, "file://assets/js/choosePic.js");
                        break;
                    case 7:
                        //NFC
                        WeexApi.loadWX(mContext, WeexPageActivity.TYPE_ASSETS, "file://assets/js/nfc.js");
                        break;
                    case 8:
                        //二维码
                        WeexApi.loadWX(mContext, WeexPageActivity.TYPE_ASSETS, "file://assets/js/qrcode.js");
                        break;
                    case 9:
                        //拍照
                        WeexApi.loadWX(mContext, WeexPageActivity.TYPE_ASSETS, "file://assets/js/takePhoto.js");
                        break;
                    case 10:
                        //查看大图
                        WeexApi.loadWX(mContext, WeexPageActivity.TYPE_ASSETS, "file://assets/js/lookBigPic.js");
                        break;
                }
            }
        });
    }
}
