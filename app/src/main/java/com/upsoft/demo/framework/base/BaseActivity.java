package com.upsoft.demo.framework.base;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.Toast;

import com.gyf.barlibrary.ImmersionBar;
import com.upsoft.demo.R;
import com.upsoft.demo.framework.api.FunctionApi;
import com.upsoft.demo.framework.sp.TextSizeSP;
import com.upsoft.demo.framework.ui.AllTitleView;
import com.upsoft.demo.framework.util.ActivityUtil;
import com.upsoft.demo.framework.util.TextScaleUtil;
import com.upsoft.sdk.Constant;
import com.upsoft.sdk.util.L;
import com.upsoft.sdk.util.PermissionUtil;
import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.PermissionListener;
import com.yanzhenjie.permission.Rationale;
import com.yanzhenjie.permission.RationaleListener;

import java.util.List;


/**
 * Copyright (c) 2017,重庆扬讯软件技术有限公司<br>
 * All rights reserved.<br>
 * <p/>
 * 文件名称：WelcomeActivity<br>
 * 摘要：简要描述本文件的内容<br>
 * -------------------------------------------------------<br>
 * 当前版本：1.1.1<br>
 * 作者：lj<br>
 * 完成日期：2017/12/4<br>
 * -------------------------------------------------------<br>
 * 取代版本：1.1.0<br>
 * 原作者：lj<br>
 * 完成日期：2017/12/4<br>
 */

public abstract class BaseActivity extends FragmentActivity implements PermissionListener, View.OnClickListener {
    protected Context mContext;
    protected Activity mActivity;
    protected ImmersionBar mImmersionBar;
    public static final int REQUEST_CODE_PERMISSION = 517;
    private static final int REQUEST_CODE_SETTING = 300;
    private String[] permissions;
    protected SessionReceiver sessionReceiver;
    protected AllTitleView allTitleView;

    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
        mContext = this;
        mActivity = this;
        initImmersionBar();
        ActivityUtil.addActivity(this);
        TextScaleUtil.scaleTextSize(mActivity, TextSizeSP.getTextSizeScale(mContext));//设置字体倍数
        if (isCheckPermission()) {
            checkPermission();
        }
        registReceiver();
        registLogin();
        registSizeChange();
        setContentView(getLayoutId());
        barColor = FunctionApi.getCurrentBarColor(mContext);
        titleColor = FunctionApi.getCurrentTitleColor(mContext);
        btnColor = FunctionApi.getCurrentBtnColor(mContext);
        textIconColor = FunctionApi.getCurrentTextIconColor(mContext);
        alpha = FunctionApi.getCurrentAlpha(mContext);
        initView();
        getData();
    }

    protected boolean isCheckPermission() {
        return true;
    }

    public abstract int getLayoutId();

    public abstract void initView();

    public abstract void getData();

    protected void checkPermission() {
        permissions = PermissionUtil.getMyDanger(mContext);
        if (!AndPermission.hasPermission(mContext, permissions)) {
            L.e("权限不足");
            AndPermission.with(this)
                    .requestCode(REQUEST_CODE_PERMISSION)
                    .permission(permissions)
                    // rationale作用是：用户拒绝一次权限，再次申请时先征求用户同意，再打开授权对话框，避免用户勾选不再提示。
                    .rationale(new RationaleListener() {
                        @Override
                        public void showRequestPermissionRationale(int requestCode, Rationale rationale) {
                            // 这里的对话框可以自定义，只要调用rationale.resume()就可以继续申请。
                            AndPermission.rationaleDialog(mContext, rationale).show();
                        }
                    }).send();
        } else {
            //有权限了
            L.e("权限足");
//            FunctionApi.uploadLocation(mContext);
            doSomeThing();
        }
    }


    protected void doSomeThing() {

    }


    protected final <E extends View> E getView(int id) {
        try {
            return (E) findViewById(id);
        } catch (ClassCastException ex) {
            L.e(ex.toString());
            throw ex;
        }
    }

    protected void show(String str) {
        Toast.makeText(mContext, str, Toast.LENGTH_SHORT).show();
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[]
            grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        /**
         * 转给AndPermission分析结果。
         *
         * @param requestCode  请求码。
         * @param permissions  权限数组，一个或者多个。
         * @param grantResults 请求结果。
         * @param listener PermissionListener 对象。
         */
        AndPermission.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    @Override
    public void onSucceed(int requestCode, List<String> grantPermissions) {
        switch (requestCode) {
            case REQUEST_CODE_PERMISSION: {
//                Toast.makeText(this, "获取定位相关权限成功", Toast.LENGTH_SHORT).show();
                onSucc();
                break;
            }
        }
    }

    protected void onSucc() {
        L.e("获取相关权限成功", "222");
//        FunctionApi.uploadLocation(mContext);
    }

    @Override
    public void onFailed(int requestCode, List<String> deniedPermissions) {
        switch (requestCode) {
            case REQUEST_CODE_PERMISSION: {
                StringBuilder sb = new StringBuilder();
                for (String s : deniedPermissions) {
                    sb.append(s + "、");
                }
                sb.deleteCharAt(sb.length() - 1);
//                Toast.makeText(this, "获取定位相关权限失败,失败的权限包括" + sb.toString(), Toast.LENGTH_SHORT).show();

                L.e("获取相关权限失败,失败的权限包括" + sb.toString());
                break;
            }
        }

        // 用户否勾选了不再提示并且拒绝了权限，那么提示用户到设置中授权。
        if (AndPermission.hasAlwaysDeniedPermission(this, deniedPermissions)) {
            // 第一种：用默认的提示语。
            AndPermission.defaultSettingDialog(this, REQUEST_CODE_SETTING).show();

            // 第二种：用自定义的提示语。
//             AndPermission.defaultSettingDialog(this, REQUEST_CODE_SETTING)
//                     .setTitle("权限申请失败")
//                     .setMessage("我们需要的一些权限被您拒绝或者系统发生错误申请失败，请您到设置页面手动授权，否则功能无法正常使用！")
//                     .setPositiveButton("好，去设置")
//                     .show();

//            第三种：自定义dialog样式。
//            SettingService settingService = AndPermission.defineSettingDialog(this, REQUEST_CODE_SETTING);
//            你的dialog点击了确定调用：
//            settingService.execute();
//            你的dialog点击了取消调用：
//            settingService.cancel();
        }
    }

//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        switch (requestCode) {
//            case REQUEST_CODE_SETTING:
////                Toast.makeText(this, "用户从设置回来了", Toast.LENGTH_LONG).show();
//                L.e("用户从设置回来了");
//                break;
//        }
//    }


    private LoginReceiver loginReceiver;

    private void registLogin() {
        IntentFilter intentFilter = new IntentFilter(Constant.Receiver.ACTION_LOGIN_SUCCESS);
        loginReceiver = new LoginReceiver();
        registerReceiver(loginReceiver, intentFilter);
    }

    private void unRegistLogin() {
        if (loginReceiver != null) {
            unregisterReceiver(loginReceiver);
        }
    }

    private class LoginReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            L.e("收到登录成功的广播" + intent.getAction());
            loginCallback(context, intent);
        }
    }

    private TextSizeChangeReceiver textSizeChangeReceiver;

    private void registSizeChange() {
        IntentFilter intentFilter = new IntentFilter(Constant.Receiver.ACTION_TEXT_SIZE_CHANGE);
        textSizeChangeReceiver = new TextSizeChangeReceiver();
        registerReceiver(textSizeChangeReceiver, intentFilter);
    }

    private void unRegistSizeChange() {
        if (textSizeChangeReceiver != null) {
            unregisterReceiver(textSizeChangeReceiver);
        }
    }

    //字体大小变化通知
    private class TextSizeChangeReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            recreate();//重建activity
        }
    }

    /**
     * 用于子类接收登录成功广播通知
     *
     * @param context
     * @param intent
     */
    protected void loginCallback(Context context, Intent intent) {

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        mImmersionBar.destroy();
        unRegistLogin();
        unRegistReceiver();
        unRegistSizeChange();
    }


    /**
     * 打开新Activity
     *
     * @param intent  intent
     * @param animIn  新Activity进入的动画
     * @param animOut 当前Activity退出的动画
     */
    public void startActivity(Intent intent, int animIn, int animOut) {
        super.startActivity(intent);
        overridePendingTransition(animIn, animOut);
    }

    /**
     * 打开新的Activity
     *
     * @param intent intent
     * @param isAnim 是否开启过渡动画
     */
    public void startActivity(Intent intent, boolean isAnim) {
        if (isAnim) {
            startActivity(intent, R.anim.act_right_in, R.anim.act_left_out);
        } else {
            super.startActivity(intent);
        }
    }

    /**
     * 打开Activity
     *
     * @param intent intent
     */
    @Override
    public void startActivity(Intent intent) {
        startActivity(intent, true);
    }

    /**
     * 退出Activity
     */
    @Override
    public void finish() {
        finish(true);
    }

    /**
     * 退出Activity
     *
     * @param animIn  老Activity进入的动画
     * @param animOut 当前Activity退出的动画
     */
    public void finish(int animIn, int animOut) {
        super.finish();
        overridePendingTransition(animIn, animOut);
    }

    /**
     * 退出Activity
     *
     * @param isAnim 是否开启过渡动画
     */
    public void finish(boolean isAnim) {
        if (isAnim) {
            finish(R.anim.act_left_in, R.anim.act_right_out);
        } else {
            super.finish();
        }
    }


    protected void initImmersionBar() {
        mImmersionBar = ImmersionBar.with(this);
        mImmersionBar
                .statusBarColor(R.color.main_color)   //状态栏颜色，不写默认透明色
                .fitsSystemWindows(true)//解决状态栏和布局重叠问题，任选其一，默认为false，当为true时一定要指定statusBarColor()，不然状态栏为透明色
                .init();   //所有子类都将继承这些相同的属性
    }


    protected void setTransparent() {
        mImmersionBar
                .statusBarColor(android.R.color.transparent)     //状态栏颜色，不写默认透明色
                .fitsSystemWindows(true)//解决状态栏和布局重叠问题，任选其一，默认为false，当为true时一定要指定statusBarColor()，不然状态栏为透明色
                .init();   //所有子类都将继承这些相同的属性
    }


    protected void setBarColor(String color) {
        mImmersionBar
                .statusBarColor(color)     //状态栏颜色，不写默认透明色
                .fitsSystemWindows(true)//解决状态栏和布局重叠问题，任选其一，默认为false，当为true时一定要指定statusBarColor()，不然状态栏为透明色
                .init();   //所有子类都将继承这些相同的属性
    }

    //添加fragment
    protected void addFragment(BaseFragment fragment, int parentId) {
        if (fragment != null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(parentId, fragment, fragment.getClass().getSimpleName())
                    .addToBackStack(fragment.getClass().getSimpleName())
                    .commitAllowingStateLoss();
        }
    }

    //移除fragment
    protected void removeFragment() {
        if (getSupportFragmentManager().getBackStackEntryCount() > 1) {
            getSupportFragmentManager().popBackStack();
        } else {
            finish();
        }
    }


    private void registReceiver() {
        IntentFilter intentFilter = new IntentFilter(Constant.Action.ACTION_SESSION_INVALID);
        sessionReceiver = new SessionReceiver();
        registerReceiver(sessionReceiver, intentFilter);
    }

    private void unRegistReceiver() {
        if (sessionReceiver != null) {
            unregisterReceiver(sessionReceiver);
        }
    }

    class SessionReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            String msg = intent.getStringExtra("msg");
            L.e("收到Session失效广播" + msg);
            handleSession();
        }
    }

    protected void handleSession() {
        FunctionApi.showLoginDialog(mActivity);
    }


    protected String titleColor;
    protected String barColor;
    protected String btnColor;
    protected String textIconColor;
    protected int alpha;

    @Override
    protected void onResume() {
        super.onResume();
        titleColor = FunctionApi.getCurrentTitleColor(mContext);
        if (allTitleView != null) {
            allTitleView.setBackgroundColor(Color.parseColor(titleColor));
        }
        barColor = FunctionApi.getCurrentBarColor(mContext);
        L.e("barColor=" + barColor);
        setBarColor(barColor);
        btnColor = FunctionApi.getCurrentBtnColor(mContext);
    }

    protected void shapeSolid(View v, String fillColor) {
        GradientDrawable gd = (GradientDrawable) v.getBackground();
        gd.setColor(Color.parseColor(fillColor));
    }
}
