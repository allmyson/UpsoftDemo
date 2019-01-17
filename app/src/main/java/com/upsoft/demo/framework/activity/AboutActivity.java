package com.upsoft.demo.framework.activity;

import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.upsoft.demo.R;
import com.upsoft.demo.framework.base.BaseActivity;
import com.upsoft.demo.framework.ui.AllTitleView;
import com.upsoft.sdk.util.SystemUtil;

public class AboutActivity extends BaseActivity implements AllTitleView.ITitleViewOnClick {
    private ImageView mIcon;//图标
    private TextView mName;//软件名称
    private TextView mAboute;//软件介绍
    private TextView mOrg;//软件公司
    private TextView versionNameTV;//
    public static String INFO_NAME = "name";
    public static String INFO_ABOUT = "about";
    public static String INFO_ORG = "org";

    @Override
    public int getLayoutId() {
        return R.layout.activity_about;
    }

    @Override
    public void initView() {
        allTitleView = getView(R.id.title_about);
        allTitleView.setLeftImg(R.mipmap.ic_back);
        allTitleView.setCenterText("关于软件");
        allTitleView.setTitleOnClickListener(this);
//
        mIcon = getView(R.id.iv_logo);
        mName = getView(R.id.tv_app_name);
        mAboute = getView(R.id.tv_introduce);
        mOrg = getView(R.id.tv_org_name);
        versionNameTV = getView(R.id.tv_app_version);
        Drawable drawable = SystemUtil.AppIcon(mContext);
        if (drawable != null) {
            mIcon.setImageDrawable(drawable);
        }
        mName.setText(SystemUtil.AppName(mContext));
        versionNameTV.setText("v" + SystemUtil.VersionName(mContext));
    }

    @Override
    protected void onResume() {
        super.onResume();
//        mIcon.setColorFilter(Color.parseColor(titleColor));
    }

    @Override
    public void getData() {

    }

    @Override
    public void backLeft() {
        finish();
    }

    @Override
    public void backRight() {

    }

    @Override
    public void onClick(View v) {

    }
}
