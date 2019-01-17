package com.upsoft.demo.business.activity;

import android.content.Context;
import android.content.Intent;

import com.upsoft.demo.business.db.EpDbHelper;
import com.upsoft.demo.framework.api.JSApi;
import com.upsoft.demo.framework.js.WebViewActivity;

public class CommonWebviewActivity extends WebViewActivity {
    public static final String KEY_URL = "url";
    public static final String FUNCTION_NAME = "AndroidJsInterface";
    private String url;

    @Override
    protected void setExtra() {
        jsInterface = new JSApi(mContext, upsoftWebview, EpDbHelper.getDBOpenHelperInstance(mContext));
        addJavascriptInterface(jsInterface, FUNCTION_NAME);
        url = getIntent().getStringExtra(KEY_URL);
        upsoftWebview.loadUrl(url);
    }

    public static void openWebUrl(Context context, String url) {
        Intent intent = new Intent(context, CommonWebviewActivity.class);
        intent.putExtra(KEY_URL, url);
        context.startActivity(intent);
    }
}
