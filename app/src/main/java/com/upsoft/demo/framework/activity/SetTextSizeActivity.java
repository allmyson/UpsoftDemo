package com.upsoft.demo.framework.activity;

import android.content.Intent;
import android.util.TypedValue;
import android.view.View;
import android.widget.TextView;

import com.upsoft.demo.R;
import com.upsoft.demo.framework.base.BaseActivity;
import com.upsoft.demo.framework.sp.TextSizeSP;
import com.upsoft.demo.framework.ui.AllTitleView;
import com.upsoft.demo.framework.ui.SetTextSizeView;
import com.upsoft.sdk.Constant;

/**
 * 设置字体大小
 */
public class SetTextSizeActivity extends BaseActivity implements AllTitleView.ITitleViewOnClick {
    private TextView contentTV, smallTV, normalTV, bigTV;
    private SetTextSizeView stsv;
    private float normalSize;
    private float scale = 1f;

    @Override
    public int getLayoutId() {
        return R.layout.activity_set_text_size;
    }

    @Override
    public void initView() {
        allTitleView = getView(R.id.title);
        allTitleView.setLeftImg(R.mipmap.ic_back);
        allTitleView.setCenterText("字体设置");
        allTitleView.setTitleOnClickListener(this);
        allTitleView.setRightText("确定");
        contentTV = getView(R.id.tv_);
        smallTV = getView(R.id.tv_small);
        normalTV = getView(R.id.tv_normal);
        bigTV = getView(R.id.tv_big);
        stsv = getView(R.id.stsv_);
        normalSize = normalTV.getTextSize();
        smallTV.setTextSize(TypedValue.COMPLEX_UNIT_PX, (float) (normalSize * 0.9));
        bigTV.setTextSize(TypedValue.COMPLEX_UNIT_PX, (float) (normalSize * 1.5));
        stsv.setOnPointResultListener(new SetTextSizeView.OnPointResultListener() {
            @Override
            public void onPointResult(int position) {
//                show(position + "");
                scale = 1f + (position - 1) * 1f / 10;
                contentTV.setTextSize(TypedValue.COMPLEX_UNIT_PX, normalSize * scale);
            }
        });
    }

    @Override
    public void getData() {
        scale = TextSizeSP.getTextSizeScale(mContext);
        int position = (int) ((scale - 1) * 10 + 1);
        stsv.setCurrentProgress(position);
        contentTV.setTextSize(TypedValue.COMPLEX_UNIT_PX, normalSize * scale);
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void backLeft() {
        finish();
    }

    @Override
    public void backRight() {
        TextSizeSP.saveTextSizeScale(mContext, scale);
//        ActivityUtil.finish();
//        startActivity(new Intent(mContext, WelcomeActivity.class));
        sendChange();
        finish();
    }

    @Override
    protected void onDestroy() {
//        sendChange();
        super.onDestroy();

    }

    private void sendChange() {
        Intent intent = new Intent(Constant.Receiver.ACTION_TEXT_SIZE_CHANGE);
        sendBroadcast(intent);
    }
}
