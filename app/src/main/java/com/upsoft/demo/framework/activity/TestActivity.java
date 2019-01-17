package com.upsoft.demo.framework.activity;

import android.content.Intent;
import android.view.View;

import com.upsoft.demo.R;
import com.upsoft.demo.business.activity.CommonWebviewActivity;
import com.upsoft.demo.framework.base.BaseActivity;
import com.upsoft.demo.framework.ui.BamButton;

public class TestActivity extends BaseActivity {
    private BamButton nativeBB, jsBB, weexBB;

    @Override
    public int getLayoutId() {
        return R.layout.activity_test;
    }

    @Override
    public void initView() {
        nativeBB = getView(R.id.bb_native);
        jsBB = getView(R.id.bb_js);
        weexBB = getView(R.id.bb_weex);
        nativeBB.setOnClickListener(this);
        jsBB.setOnClickListener(this);
        weexBB.setOnClickListener(this);
    }

    @Override
    public void getData() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bb_native:
                startActivity(new Intent(mContext, NativeTestActivity.class));
                break;
            case R.id.bb_js:
                CommonWebviewActivity.openWebUrl(mContext, "file:///android_asset/index.html");
                break;
            case R.id.bb_weex:
                startActivity(new Intent(mContext, WeexTestActivity.class));
                break;
        }
    }
}
