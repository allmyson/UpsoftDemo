package com.upsoft.demo.framework.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.upsoft.demo.R;
import com.upsoft.demo.framework.api.FunctionApi;
import com.upsoft.demo.framework.base.BaseActivity;
import com.upsoft.demo.framework.bean.InfoBean;
import com.upsoft.demo.framework.bean.YzmBean;
import com.upsoft.demo.framework.sp.UserSP;
import com.upsoft.demo.framework.ui.CircleImageView;
import com.upsoft.demo.framework.ui.SlideSwitch;
import com.upsoft.demo.framework.util.ActivityUtil;
import com.upsoft.sdk.Constant;
import com.upsoft.sdk.http.BaseBean;
import com.upsoft.sdk.http.BaseHttp;
import com.upsoft.sdk.http.nohttp.HttpListener;
import com.upsoft.sdk.util.L;
import com.upsoft.sdk.util.Md5Util;
import com.upsoft.sdk.util.StringUtil;
import com.upsoft.sdk.util.SystemUtil;
import com.upsoft.sdk.util.ToastUtil;
import com.yanzhenjie.nohttp.rest.Response;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends BaseActivity implements SlideSwitch.SlideListener, TextWatcher {
    public static final String KEY_USER = "username";
    public static final String KEY_PASSWORD = "password";
    public static final String KEY_ISFINISH_ALL = "is_finish_all";
    private CircleImageView civ;
    private TextView appNameTV;
    private EditText userET, psdET, yzmET;
    private ImageView psdIV, clearIV, showOrHideIV, yzmIV;
    private SlideSwitch ss;
    private Button loginBtn;
    private LinearLayout yzmLL;
    private boolean isShowPsd = false;
    private RelativeLayout netRL;

    private boolean needYZM = false;//是否需要显示验证码
    private String username, password;
    private boolean isRemenber = false;//是否记住密码
    private boolean isFinishAll;

    @Override
    public int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    public void initView() {
        civ = getView(R.id.civ_logo);
        appNameTV = getView(R.id.tv_appName);
        appNameTV.setText(SystemUtil.AppName(mContext));
        userET = getView(R.id.et_username);
        psdET = getView(R.id.et_psd);
        yzmET = getView(R.id.et_yzm);
        psdIV = getView(R.id.iv_psd);
        clearIV = getView(R.id.iv_clear);
        showOrHideIV = getView(R.id.iv_showOrHide);
        yzmIV = getView(R.id.iv_yzm);
        ss = getView(R.id.ss);
        loginBtn = getView(R.id.btn_login);
        yzmLL = getView(R.id.ll_yzm);
        netRL = getView(R.id.rl_net);
        netRL.setOnClickListener(this);
        clearIV.setOnClickListener(this);
        showOrHideIV.setOnClickListener(this);
        ss.setSlideListener(this);
        loginBtn.setOnClickListener(this);
        userET.addTextChangedListener(this);
        Drawable drawable = SystemUtil.AppIcon(mContext);
        if (drawable != null) {
            civ.setImageDrawable(drawable);
        }
        ((ImageView) getView(R.id.iv_icon_username)).setColorFilter(Color.parseColor(textIconColor));
        psdIV.setColorFilter(Color.parseColor(textIconColor));
        ss.setColor_theme(Color.parseColor(textIconColor));

        userET.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                return (event.getKeyCode() == KeyEvent.KEYCODE_ENTER);
            }

        });

        psdET.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                return (event.getKeyCode() == KeyEvent.KEYCODE_ENTER);
            }
        });

    }

    @Override
    public void getData() {
        username = getIntent().getStringExtra(KEY_USER);
        password = getIntent().getStringExtra(KEY_PASSWORD);
        isFinishAll = getIntent().getBooleanExtra(KEY_ISFINISH_ALL, false);
        isRemenber = UserSP.isRemenber(mContext);
        if (isRemenber) {
            ss.setState(true);
        } else {
            ss.setState(false);
        }
        if (StringUtil.isBlank(username)) {
            username = UserSP.getUsername(mContext);
        }
        if (StringUtil.isBlank(password)) {
            password = UserSP.getPassword(mContext);
        }

        userET.setText(StringUtil.valueOf(username));
        if (isRemenber) {
            psdET.setText(StringUtil.valueOf(password));
        }

        if (!StringUtil.isBlank(username)) {
            needYZM();
        }

        canClear();
        showOrHidePsd();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_clear:
                userET.setText("");
                break;
            case R.id.iv_showOrHide:
                isShowPsd = !isShowPsd;
                showOrHidePsd();
                psdET.requestFocus();
                break;
            case R.id.btn_login:
                if (isCanLogin()) {
                    login();
                }
                break;
            case R.id.rl_net:
                startActivity(new Intent(mContext, NetworkActivity.class));
                break;

        }
    }

    @Override
    protected boolean isCheckPermission() {
        return false;
    }

    @Override
    public void open() {
        UserSP.setRemenber(mContext, true);
    }

    @Override
    public void close() {
        UserSP.setRemenber(mContext, false);
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        canClear();
    }

    private void canClear() {
        String str = userET.getText().toString();
        if (!StringUtil.isBlank(str)) {
            clearIV.setVisibility(View.VISIBLE);
        } else {
            clearIV.setVisibility(View.GONE);
        }
    }

    private void showOrHidePsd() {
        if (isShowPsd) {
            psdIV.setImageResource(R.mipmap.ic_psd);
            showOrHideIV.setImageResource(R.mipmap.ic_psd_show);
            psdET.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
            psdET.setSelection(psdET.getText().toString().length());//将光标移至文字末尾
        } else {
            psdIV.setImageResource(R.mipmap.ic_psd_close);
            showOrHideIV.setImageResource(R.mipmap.f_ic_psd_noshow);
            psdET.setTransformationMethod(PasswordTransformationMethod.getInstance());
            psdET.setSelection(psdET.getText().toString().length());//将光标移至文字末尾
        }
    }

    private boolean isCanLogin() {
        String username = userET.getText().toString();
        String psd = psdET.getText().toString();
        String yzm = yzmET.getText().toString();
        if (StringUtil.isBlank(username)) {
            show("用户名不能为空!");
            return false;
        } else if (StringUtil.isBlank(psd)) {
            show("密码不能为空!");
            return false;
        }
        return true;
    }


    private void login() {
        String username = userET.getText().toString().trim();
        String password = psdET.getText().toString().trim();
        String yzm = yzmET.getText().toString().trim();
        String url = FunctionApi.getHttpIp(mContext) + Constant.Url.LOGIN;
        if (TextUtils.isEmpty(username)) {
            ToastUtil.showShort(mContext, "请输入用户名！");
            return;
        } else if (TextUtils.isEmpty(password)) {
            ToastUtil.showShort(mContext, "请输入密码！");
            return;
        }
        if (TextUtils.isEmpty(yzm) && needYZM) {
            ToastUtil.showShort(mContext, "请输入验证码！");
            return;
        }
        /**--用户名不区分大小写--**/
        String lowerUserName = username.toLowerCase();
        Map<String, String> params = new HashMap<>();
        params.put("loginId", lowerUserName);
        params.put("pwd", Md5Util.getMD5String(password));
        params.put("clientType", "app");
        params.put("verifyCode", yzm);
        String json = new Gson().toJson(params);
        L.e("json", json);
        BaseHttp.getInstance().postJson(this, url, json, new HttpListener<String>() {
            @Override
            public void onSucceed(int what, Response<String> response) {
                BaseBean baseBean = new Gson().fromJson(response.get() + "", BaseBean.class);
                if (baseBean != null) {
                    if ("1".equals(baseBean.status)) {
                        getUserInfo();
                    } else {
                        ToastUtil.showShort(mContext, StringUtil.valueOf(baseBean.msg));
                        //密码输错3次和正常的验证码输入错误都是返回的这个状态，讲道理应该区分开的，哎
                        /***2018/1/18  18:09  edit by lh***/
                        if (Constant.WRONG_USER.equals(baseBean.errorCode)) {
                            return;
                        }
                        needYZM();
                    }
                }
            }

            @Override
            public void onFailed(int what, Response<String> response) {
                L.e("fail" + response.get());
                //登录失败超时
            }
        });
    }

    private void needYZM() {
        String url = FunctionApi.getHttpIp(mContext) + Constant.Url.NEED_YZM;
        String username = userET.getText().toString();
        Map<String, String> params = new HashMap<>();
        params.put("loginId", StringUtil.valueOf(username));
        params.put("clientType", "app");
        String json = new Gson().toJson(params);
        L.e(json);
        BaseHttp.getInstance().postJson(this, url, json, new HttpListener<String>() {
            @Override
            public void onSucceed(int what, Response<String> response) {
                try {
                    YzmBean yzmBean = new Gson().fromJson(response.get(), YzmBean.class);
                    if (yzmBean != null && "1".equals(yzmBean.status)) {
                        needYZM = yzmBean.data;
                    }
                    showYZM();
                } catch (Exception e) {

                }
            }

            @Override
            public void onFailed(int what, Response<String> response) {
                L.e("是否需要验证码接口访问失败");
            }
        }, false);
    }

    private void showYZM() {
        if (needYZM) {
            yzmLL.setVisibility(View.VISIBLE);
            getYZM();
        } else {
            yzmLL.setVisibility(View.GONE);
        }
    }

    private void getUserInfo() {
        String url = FunctionApi.getHttpIp(mContext) + Constant.Url.USERINFO;
        BaseHttp.getInstance().postJson(this, url, "", new HttpListener<String>() {
            @Override
            public void onSucceed(int what, Response<String> response) {
                BaseBean baseBean = new Gson().fromJson(response.get(), BaseBean.class);
                if (baseBean != null && "1".equals(baseBean.status)) {
                    UserSP.saveUsername(mContext, userET.getText().toString());
                    UserSP.savePassword(mContext, psdET.getText().toString());
                    //存用户信息json
                    UserSP.saveInfo(mContext, response.get());
                    InfoBean infoBean = new Gson().fromJson(response.get(), InfoBean.class);
                    if (infoBean != null && "1".equals(infoBean.status) && infoBean.data != null) {
                        show("登录成功！");
                    } else {
                        L.e("用户信息为空！");
                        ToastUtil.showShort(mContext, "登录失败");
                    }
                    if (isFinishAll) {
                        ActivityUtil.finish();
                        Intent intent1 = new Intent(mContext, WelcomeActivity.class);
                        startActivity(intent1);
                    } else {
                        startActivity(new Intent(mContext, MainActivity.class));
                        finish();
                    }
                } else {
                    ToastUtil.showShort(mContext, "登录失败");
                }
            }

            @Override
            public void onFailed(int what, Response<String> response) {
                L.e("fail" + response.get());
            }
        });
    }

    private void getYZM() {
        BaseHttp.getInstance().loadImag(mContext, FunctionApi.getHttpIp(mContext) + Constant.Url.YZM_IMAGE, new
                HttpListener<Bitmap>() {
                    @Override
                    public void onSucceed(int what, Response<Bitmap> response) {
                        Bitmap bitmap = response.get();
                        if (bitmap != null) {
                            yzmIV.setImageBitmap(bitmap);
                        }
                    }

                    @Override
                    public void onFailed(int what, Response<Bitmap> response) {

                    }
                });
    }

    @Override
    protected void onResume() {
        super.onResume();
        getView(R.id.rl_top).setBackgroundColor(Color.parseColor(titleColor));
        shapeSolid(loginBtn, btnColor);
    }
}
