package com.upsoft.demo.framework.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import com.gyf.barlibrary.ImmersionBar;
import com.upsoft.demo.R;
import com.vise.log.ViseLog;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LogActivity extends FragmentActivity {
    ImmersionBar mImmersionBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initImmersionBar();
//        setTransparent();
//        setContentView(R.layout.activity_main);
        ViseLog.d("longhui");
        ViseLog.d(new Boolean(true));
        ViseLog.d(true);
        ViseLog.d(new Intent());
        ViseLog.e(new NullPointerException("this object is null!"));
        List<String> list = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            list.add("test" + i);
        }
        ViseLog.d(list);
        Map<String, String> map = new HashMap<>();
        for (int i = 0; i < 5; i++) {
            map.put("xyy" + i, "test" + i);
        }
        ViseLog.d(map);
        String json = "{'xyy1':[{'test1':'test1'},{'test2':'test2'}],'xyy2':{'test3':'test3','test4':'test4'}}";
        ViseLog.json(json);
        String xml = "<xyy><test1><test2>key</test2></test1><test3>name</test3><test4>value</test4></xyy>";
        ViseLog.xml(xml);
    }

    protected void initImmersionBar() {
        mImmersionBar = ImmersionBar.with(this);
        mImmersionBar
                .statusBarColor(R.color.main_color)//状态栏颜色，不写默认透明色
                .fitsSystemWindows(true)//解决状态栏和布局重叠问题，任选其一，默认为false，当为true时一定要指定statusBarColor()，不然状态栏为透明色
                .init();   //所有子类都将继承这些相同的属性
    }


    protected void setTransparent() {
        mImmersionBar
                .statusBarColor(android.R.color.transparent)     //状态栏颜色，不写默认透明色
                .fitsSystemWindows(false)//解决状态栏和布局重叠问题，任选其一，默认为false，当为true时一定要指定statusBarColor()，不然状态栏为透明色
                .init();   //所有子类都将继承这些相同的属性
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mImmersionBar != null) {
            mImmersionBar.destroy();
        }
    }
}
