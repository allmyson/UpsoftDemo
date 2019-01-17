package com.upsoft.demo.framework.activity;

import android.graphics.Color;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.upsoft.demo.R;
import com.upsoft.demo.framework.api.FunctionApi;
import com.upsoft.demo.framework.base.BaseActivity;
import com.upsoft.demo.framework.bean.InfoBean;
import com.upsoft.demo.framework.sp.ColorSP;
import com.upsoft.demo.framework.sp.UserSP;
import com.upsoft.demo.framework.ui.AllTitleView;
import com.upsoft.demo.framework.ui.CircleImageView;
import com.upsoft.sdk.http.BaseBean;
import com.upsoft.sdk.http.nohttp.HttpListener;
import com.upsoft.sdk.image.loader.ImageLoader;
import com.upsoft.sdk.util.L;
import com.upsoft.sdk.util.StringUtil;
import com.yanzhenjie.nohttp.rest.Response;

public class UpdatePsdActivity extends BaseActivity implements AllTitleView.ITitleViewOnClick {
    private CircleImageView headerCIV;
    private TextView nickNameTV, orgNameTV;
    private EditText oldPsdET, newPsdET, reNewPsdET;
    private ImageView showOrHide1IV, showOrHide2IV, showOrHide3IV;
    private Button sureBtn;
    private boolean isShowPsd1, isShowPsd2, isShowPsd3;
    private String buttonColor;

    @Override
    public int getLayoutId() {
        return R.layout.activity_update_psd;
    }

    @Override
    public void initView() {
        allTitleView = getView(R.id.title);
        allTitleView.setLeftImg(R.mipmap.ic_back);
        allTitleView.setCenterText("修改密码");
        allTitleView.setTitleOnClickListener(this);
        headerCIV = getView(R.id.civ_header);
        nickNameTV = getView(R.id.tv_nickName);
        orgNameTV = getView(R.id.tv_orgName);
        oldPsdET = getView(R.id.et_psd);
        newPsdET = getView(R.id.et_psd2);
        reNewPsdET = getView(R.id.et_psd3);
        showOrHide1IV = getView(R.id.iv_showOrHide);
        showOrHide2IV = getView(R.id.iv_showOrHide2);
        showOrHide3IV = getView(R.id.iv_showOrHide3);
        sureBtn = getView(R.id.btn_sure);
        showOrHide1IV.setOnClickListener(this);
        showOrHide2IV.setOnClickListener(this);
        showOrHide3IV.setOnClickListener(this);
        sureBtn.setOnClickListener(this);

        ((ImageView)getView(R.id.iv_psd)).setColorFilter(Color.parseColor(textIconColor));
        ((ImageView)getView(R.id.iv_re_psd)).setColorFilter(Color.parseColor(textIconColor));
        ((ImageView)getView(R.id.iv_re_psd2)).setColorFilter(Color.parseColor(textIconColor));

    }

    @Override
    public void getData() {
        buttonColor = ColorSP.getBtnColor(mContext);
        shapeSolid(sureBtn, buttonColor);
        InfoBean infoBean = UserSP.getInfo(mContext);
        setData(infoBean);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_showOrHide:
                isShowPsd1 = !isShowPsd1;
                showOrHidePsd(isShowPsd1, showOrHide1IV, oldPsdET);
                oldPsdET.requestFocus();
                break;
            case R.id.iv_showOrHide2:
                isShowPsd2 = !isShowPsd2;
                showOrHidePsd(isShowPsd2, showOrHide2IV, newPsdET);
                newPsdET.requestFocus();
                break;
            case R.id.iv_showOrHide3:
                isShowPsd3 = !isShowPsd3;
                showOrHidePsd(isShowPsd3, showOrHide3IV, reNewPsdET);
                reNewPsdET.requestFocus();
                break;
            case R.id.btn_sure:
                if (canUpdate()) {
                    update();
                }
                break;
        }
    }

    private void showOrHidePsd(boolean isShowPsd, ImageView showOrHideIV, EditText psdET) {
        if (isShowPsd) {
            showOrHideIV.setImageResource(R.mipmap.ic_psd_show);
            psdET.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
            psdET.setSelection(psdET.getText().toString().length());//将光标移至文字末尾
        } else {
            showOrHideIV.setImageResource(R.mipmap.f_ic_psd_noshow);
            psdET.setTransformationMethod(PasswordTransformationMethod.getInstance());
            psdET.setSelection(psdET.getText().toString().length());//将光标移至文字末尾
        }
    }

    @Override
    public void backLeft() {
        finish();
    }

    @Override
    public void backRight() {

    }

    private boolean canUpdate() {
        String old = oldPsdET.getText().toString().trim();
        String newPsd = newPsdET.getText().toString().trim();
        String reNewPsd = reNewPsdET.getText().toString().trim();
        if (StringUtil.isBlank(old)) {
            show("原密码不能为空！");
            return false;
        } else if (StringUtil.isBlank(newPsd)) {
            show("新密码不能为空！");
            return false;
        } else if (StringUtil.isBlank(reNewPsd)) {
            show("确认新密码不能为空！");
            return false;
        } else if (newPsd.equals(old)) {
            show("新密码不能和旧密码相同！");
            return false;
        } else if (!newPsd.equals(reNewPsd)) {
            show("两次输入密码不一致！");
            return false;
        }
        return true;
    }

    private void update() {
        String old = oldPsdET.getText().toString().trim();
        final String newPsd = newPsdET.getText().toString().trim();
        FunctionApi.updatePassword(mActivity, old, newPsd, new HttpListener<String>() {
            @Override
            public void onSucceed(int what, Response<String> response) {
                L.e(response.get() + "");
                BaseBean baseBean = new Gson().fromJson(response.get(), BaseBean.class);
                if (baseBean != null) {
                    if ("1".equals(baseBean.status)) {
                        show("密码修改成功！");
                        UserSP.savePassword(mContext, newPsd);
                        finish();
                    } else {
                        show(StringUtil.valueOf(baseBean.msg));
                    }
                } else {
                    show("密码修改失败！");
                }
            }

            @Override
            public void onFailed(int what, Response<String> response) {

            }
        });
    }

    private void setData(InfoBean infoBean) {
        if (infoBean != null && infoBean.data != null) {
            nickNameTV.setText(StringUtil.valueOf(infoBean.data.name));
            if (infoBean.data.userOrg != null) {
                orgNameTV.setText(StringUtil.valueOf(infoBean.data.userOrg.name));
            }
            if (infoBean.data.extInfo != null) {
                String photo = infoBean.data.extInfo.photo;
                String downloadUrl = FunctionApi.getDownloadUrlByFileId(mContext, photo);
                L.e(downloadUrl);
                ImageLoader.with(mContext).url(downloadUrl).error(R.mipmap.bg_default_header2).into(headerCIV);
            }
        }
    }
}
