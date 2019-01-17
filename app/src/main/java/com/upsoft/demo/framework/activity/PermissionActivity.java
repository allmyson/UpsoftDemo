package com.upsoft.demo.framework.activity;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.upsoft.demo.R;
import com.upsoft.demo.framework.adapter.PermissionAdapter;
import com.upsoft.demo.framework.api.FunctionApi;
import com.upsoft.demo.framework.base.BaseActivity;
import com.upsoft.demo.framework.bean.PermissionBean;
import com.upsoft.sdk.util.L;
import com.upsoft.sdk.util.PermissionUtil;
import com.upsoft.sdk.util.SystemUtil;
import com.vise.log.ViseLog;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class PermissionActivity extends BaseActivity {
    private TextView tipTV;
    private ListView lv;
    private Button sureBtn;
    private PermissionAdapter mAdapter;
    private List<PermissionBean> list;

    @Override
    public int getLayoutId() {
        return R.layout.activity_permission;
    }

    @Override
    public void initView() {
        tipTV = getView(R.id.tv_tip);
        lv = getView(R.id.lv_);
        sureBtn = getView(R.id.btn_sure);
        sureBtn.setOnClickListener(this);
        tipTV.setText(SystemUtil.AppName(mContext) + "需要使用您的以下权限");
        list = new ArrayList<>();
        mAdapter = new PermissionAdapter(mContext, list, R.layout.item_permission);
        lv.setAdapter(mAdapter);
    }

    @Override
    public void getData() {
        String[] data = SystemUtil.AppPremission(mContext);
        ViseLog.d(data);
        List<String> permissionNameList = new ArrayList<>();
        Map<String, String> dangerMap = PermissionUtil.dangerMap;
        for (String str : data) {
            if (dangerMap.containsKey(str)) {
                String value = dangerMap.get(str);
                if (!permissionNameList.contains(value)) {
                    permissionNameList.add(value);
                }
            }
        }
        ViseLog.d(permissionNameList);
        List<PermissionBean> newList = new ArrayList<>();
        if (permissionNameList.size() > 0) {
            Map<String, PermissionBean> beanMap = PermissionBean.map;
            for (String key : permissionNameList) {
                if (beanMap.containsKey(key)) {
                    newList.add(beanMap.get(key));
                }
            }
        }
        list.clear();
        list.addAll(newList);
        mAdapter.refresh(list);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_sure:
                checkPermission();
                break;
        }
    }

    @Override
    protected boolean isCheckPermission() {
        return false;
    }


    @Override
    protected void doSomeThing() {
        super.doSomeThing();
        L.e("doSomeThing执行");
        toMain();
    }

    @Override
    protected void onSucc() {
        super.onSucc();
        L.e("onSucc执行");
        toMain();
    }

    private void toMain(){
        FunctionApi.setNoFirst(mContext);
        if (FunctionApi.isLogin(mContext)) {
            startActivity(new Intent(mContext, LogActivity.class));
        } else {
            startActivity(new Intent(mContext, LoginActivity.class));
        }
        finish();
    }
}
