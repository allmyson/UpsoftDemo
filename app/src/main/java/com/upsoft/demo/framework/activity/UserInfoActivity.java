package com.upsoft.demo.framework.activity;

import android.content.Intent;
import android.graphics.Color;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.upsoft.demo.R;
import com.upsoft.demo.framework.api.FunctionApi;
import com.upsoft.demo.framework.base.BaseActivity;
import com.upsoft.demo.framework.bean.InfoBean;
import com.upsoft.demo.framework.dialog.CommonDialog;
import com.upsoft.demo.framework.sp.UserSP;
import com.upsoft.demo.framework.ui.AllTitleView;
import com.upsoft.demo.framework.ui.CircleImageView;
import com.upsoft.sdk.Constant;
import com.upsoft.sdk.http.BaseBean;
import com.upsoft.sdk.http.nohttp.HttpListener;
import com.upsoft.sdk.image.loader.ImageLoader;
import com.upsoft.sdk.util.L;
import com.upsoft.sdk.util.NetUtil;
import com.upsoft.sdk.util.StringUtil;
import com.upsoft.sdk.util.ToastUtil;
import com.yanzhenjie.nohttp.rest.Response;


public class UserInfoActivity extends BaseActivity implements AllTitleView.ITitleViewOnClick, SwipeRefreshLayout
        .OnRefreshListener {

    //头像layout
    private RelativeLayout mHeadLayout;
    //用户头像
    private CircleImageView mUserHead;
    //姓名
    private TextView mName;
    //性别
    private TextView mGender;
    //单位
    private TextView mUnit;
    //职位
    private TextView mZw;
    //手机
    private TextView mPhone;
    //电话
    private TextView mTel;
    //修改密码
    private TextView mChangePassword;
    //退出账号
    private TextView mLoginOut;
    private TextView keshiTV;
    //刷新
    private SwipeRefreshLayout mSwipeRefreshLayout;
    public static String INFO_BACK = "infoBack";
    public static String INFO_CKPASSWORD = "0";
    public static String INFO_LOGOUT = "1";
    private String currentIp;

    @Override
    public int getLayoutId() {
        return R.layout.activity_userinfo;
    }

    @Override
    public void initView() {
        allTitleView = (AllTitleView) this.findViewById(R.id.title);
        allTitleView.setLeftImg(R.mipmap.ic_back);
        allTitleView.setCenterText("个人信息");
        allTitleView.setTitleOnClickListener(this);
        mHeadLayout = (RelativeLayout) findViewById(R.id.rl_userinfo_headlayout);
        mUserHead = (CircleImageView) findViewById(R.id.iv_userinfo_head);
        mZw = (TextView) findViewById(R.id.tv_userinfo_zw);
        mName = (TextView) findViewById(R.id.tv_userinfo_name);
        mGender = (TextView) findViewById(R.id.tv_userinfo_gender);
        mUnit = (TextView) findViewById(R.id.tv_userinfo_unit);
        keshiTV = (TextView) findViewById(R.id.tv_userinfo_keshi);
        mPhone = (TextView) findViewById(R.id.tv_userinfo_phone);
        mTel = (TextView) findViewById(R.id.tv_userinfo_tel);
        mChangePassword = (TextView) findViewById(R.id.tv_userinfo_changepassword);
        mLoginOut = (TextView) findViewById(R.id.tv_userinfo_loginout);
        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.info_refresh);
        mHeadLayout.setOnClickListener(this);
        mChangePassword.setOnClickListener(this);
        mLoginOut.setOnClickListener(this);
        mSwipeRefreshLayout.setOnRefreshListener(this);
        mSwipeRefreshLayout.setColorSchemeColors(Color.BLUE, Color.RED, Color.GREEN, Color.YELLOW);
    }

    @Override
    public void getData() {
        if (NetUtil.isNetworkConnected(mContext)) {
            FunctionApi.getUserInfo(mActivity, new HttpListener<String>() {
                @Override
                public void onSucceed(int what, Response<String> response) {
                    BaseBean baseBean = new Gson().fromJson(response.get(), BaseBean.class);
                    if (baseBean != null && "1".equals(baseBean.status)) {
                        //存用户信息json
                        UserSP.saveInfo(mContext, response.get());
                        InfoBean infoBean = new Gson().fromJson(response.get(), InfoBean.class);
                        setData(infoBean);
                    } else {
                        ToastUtil.showShort(mContext, "登录失败");
                    }
                    mSwipeRefreshLayout.setRefreshing(false);
                }

                @Override
                public void onFailed(int what, Response<String> response) {
                    mSwipeRefreshLayout.setRefreshing(false);
                }
            });
        } else {
            InfoBean infoBean = UserSP.getInfo(mContext);
            setData(infoBean);
            mSwipeRefreshLayout.setRefreshing(false);
        }
    }


    /**
     * @return void
     * @author 彭粟
     * @version 1.0
     * @Description: 设置用户信息
     * @time： 2016/12/30
     */

    private void setData(InfoBean infoBean) {
        if (infoBean != null && infoBean.data != null) {
            mName.setText(StringUtil.valueOf(infoBean.data.name));
            mGender.setText("1".equals(infoBean.data.sex) ? "男" : "女");
            mPhone.setText(StringUtil.valueOf(infoBean.data.tel));
            if (infoBean.data.userOrg != null) {
                mUnit.setText(StringUtil.valueOf(infoBean.data.userOrg.name));
            }
            if (infoBean.data.extInfo != null) {
                mTel.setText(StringUtil.valueOf(infoBean.data.extInfo.mobile));
                mZw.setText(StringUtil.valueOf(infoBean.data.extInfo.positionName));
                String photo = infoBean.data.extInfo.photo;
                String downloadUrl = FunctionApi.getDownloadUrlByFileId(mContext, photo);
                L.e(downloadUrl);
                ImageLoader.with(mContext).url(downloadUrl).error(R.mipmap.bg_default_header2).into(mUserHead);
            }
        }
    }


    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.tv_userinfo_changepassword) {
//            startActivityForResult(new Intent(mContext, UpdatePasswordActivity.class), 601);
            startActivity(new Intent(mContext, UpdatePsdActivity.class));
        } else if (i == R.id.tv_userinfo_loginout) {
            LogOut();
        }
    }


    private void LogOut() {
        CommonDialog taskDialog = new CommonDialog(mContext, "", "是否确认退出当前账号？", "", "", "否", "是", new CommonDialog
                .ItaskDialogBack() {
            @Override
            public void cancel() {
            }

            @Override
            public void affirm() {
                FunctionApi.logout(mContext, new FunctionApi.LogoutCallback() {
                    @Override
                    public void onSuccess() {
                        FunctionApi.loginOut(mContext);
                        boolean isRemember = UserSP.isRemenber(mContext);
                        String username = UserSP.getUsername(mContext);
                        String psd = UserSP.getPassword(mContext);
                        if (isRemember) {
                            FunctionApi.intentToLogin(mContext, username, psd, true);
                        } else {
                            FunctionApi.intentToLogin(mContext, username, "", true);
                        }
                        finish();
                    }

                    @Override
                    public void onFail(Constant.ErrorCode errorCode) {
                        ToastUtil.show(mContext, "退出失败");
                        L.e("退出登录失败》", errorCode.toString());
                    }
                });
            }
        });
        taskDialog.setCancelable(false);
        taskDialog.show();
    }


    @Override
    public void backLeft() {
        finish();
    }

    @Override
    public void backRight() {
    }

    @Override
    public void onRefresh() {
        getData();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK && requestCode == 601) {
            Intent intent = new Intent();
            intent.putExtra(INFO_BACK, UserInfoActivity.INFO_CKPASSWORD);
            setResult(RESULT_OK, intent);
            finish();
        }
    }
}
