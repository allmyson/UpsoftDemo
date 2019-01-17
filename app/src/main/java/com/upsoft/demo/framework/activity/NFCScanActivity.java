package com.upsoft.demo.framework.activity;

import android.content.Intent;
import android.graphics.Color;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.upsoft.demo.R;
import com.upsoft.demo.framework.ui.AllTitleView;
import com.upsoft.sdk.util.StringUtil;
import com.upsoft.sdk.util.ToastUtil;


public class NFCScanActivity extends NFCActivity {
    public static final String NFC_RESULT = "resultStrId";
    private TextView textTV;
    //标题
    private boolean isReturnWeb = false;

    private ImageView scanIV;

    @Override
    protected void onNFCAction(Intent intent) {
        // 解析NFC信息
        Intent openIntent = null;
        String resultStr = NFCActivity.praseRfid(intent, this);
        if (!StringUtil.isBlank(resultStr)) {
            openIntent = new Intent();
            openIntent.putExtra(NFC_RESULT, resultStr);
            this.setResult(RESULT_OK, openIntent);
            finish();
        } else {
            ToastUtil.show(this, "标签损坏无法识别", 0);
        }
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_nfc_scan;
    }

    @Override
    public void initView() {
        allTitleView = getView(R.id.title);
        allTitleView.setCenterText("扫描标签");
        allTitleView.setLeftImg(R.mipmap.ic_back);
        allTitleView.setTitleOnClickListener(new AllTitleView.ITitleViewOnClick() {
            @Override
            public void backLeft() {
                finish();
            }

            @Override
            public void backRight() {

            }
        });
        allTitleView.setBackgroundColor(Color.parseColor(titleColor));
        textTV = getView(R.id.tv_text);
        String content = textTV.getText().toString();
        int bstart = content.indexOf("2cm");
        int bend = bstart + "2cm".length();
        SpannableStringBuilder style = new SpannableStringBuilder(content);
        style.setSpan(new ForegroundColorSpan(Color.parseColor("#FF9927")), bstart, bend, Spannable
                .SPAN_EXCLUSIVE_EXCLUSIVE);
        textTV.setText(style);
        scanIV = getView(R.id.iv_scan);
    }

    @Override
    public void onResume() {
        super.onResume();
        scanIV.setColorFilter(Color.parseColor(titleColor));
    }

    @Override
    public void getData() {

    }

    @Override
    public void onClick(View v) {

    }
}
