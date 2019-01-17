package com.upsoft.demo.framework.weex.activity;

import android.net.Uri;
import android.text.TextUtils;
import android.view.View;

import com.taobao.weex.RenderContainer;
import com.taobao.weex.WXSDKEngine;
import com.taobao.weex.WXSDKInstance;
import com.taobao.weex.common.WXRenderStrategy;
import com.taobao.weex.ui.component.NestedContainer;
import com.taobao.weex.utils.WXFileUtils;
import com.upsoft.demo.R;
import com.upsoft.demo.framework.ui.AllTitleView;
import com.upsoft.demo.framework.weex.Constant_;
import com.upsoft.demo.framework.weex.adapter.NavigatorAdapter;
import com.upsoft.sdk.http.BaseHttp;
import com.upsoft.sdk.http.nohttp.HttpListener;
import com.upsoft.sdk.util.L;
import com.upsoft.sdk.util.ScreenUtil;
import com.upsoft.sdk.util.StringUtil;
import com.upsoft.sdk.weex.util.FileUtil;
import com.yanzhenjie.nohttp.rest.Response;

import java.util.HashMap;
import java.util.Map;

public class WeexPageActivity extends BaseWeexActivity implements AllTitleView.ITitleViewOnClick, WXSDKInstance
        .NestedInstanceInterceptor {
    private final static String URL = "url";
    public static final int TYPE_NET = 1;
    public static final int TYPE_SDCARD = 2;
    public static final int TYPE_ASSETS = 3;
    private int currentType = TYPE_NET;
    private String currentPath;

    @Override
    public int getLayoutId() {
        return R.layout.activity_weex_page;
    }

    @Override
    public void initView() {
        super.initView();
        init();
    }

    @Override
    public void getData() {

    }

    private void init() {
        WXSDKEngine.setActivityNavBarSetter(new NavigatorAdapter(mContext));
        allTitleView = getView(R.id.title);
        allTitleView.setLeftImg(R.mipmap.ic_back);
        allTitleView.setRightImg(R.mipmap.f_ic_refresh);
        allTitleView.setTitleOnClickListener(this);
        mProgressBar = getView(R.id.pb);
        container = getView(R.id.container);
        currentType = getIntent().getIntExtra("type", 1);
        currentPath = getIntent().getStringExtra("path");
        if (!StringUtil.isBlank(currentPath)) {
            String title = currentPath.substring(currentPath.lastIndexOf("/") + 1);
            allTitleView.setCenterText(StringUtil.valueOf(title));
        }
        loadPage();
    }

    private String getAssetPath(String currentPath) {
        String s = currentPath;
        if (s != null && s.startsWith("file://assets/")) {
            s = s.replace("file://assets/", "");
        }
        return s;
    }

    @Override
    public void onViewCreated(WXSDKInstance instance, View view) {
        super.onViewCreated(instance, view);
        if (view.getParent() == null) {
            container.addView(view);
        }
        container.requestLayout();
    }

    @Override
    public void backLeft() {
        finish();
    }

    @Override
    public void backRight() {
        reLoadPage();
    }


    private String getSDPath(String sdcardPath) {
        String s = sdcardPath;
        if (s != null && s.startsWith("sdcard://")) {
            s = s.replace("sdcard://", "");
        }
        return s;
    }

    private String getUrl(Uri uri) {
        String url = uri.toString();
        String scheme = uri.getScheme();
        if (uri.isHierarchical()) {
            if (TextUtils.equals(scheme, "http") || TextUtils.equals(scheme, "https")) {
                String weexTpl = uri.getQueryParameter(Constant_.Weex.WEEX_TPL_KEY);
                if (!TextUtils.isEmpty(weexTpl)) {
                    url = weexTpl;
                }
            }
        }
        return url;
    }

    private void loadPage() {
        Uri uri = Uri.parse(currentPath);
        currentPath = getUrl(uri);
        L.e("currentPath=" + currentPath);
        switch (currentType) {
            case TYPE_NET:
                Map<String, Object> options = new HashMap<>();
                options.put(WXSDKInstance.BUNDLE_URL, currentPath);
                mWXSDKInstance.renderByUrl("UpsoftSdk", currentPath, options, null, WXRenderStrategy.APPEND_ASYNC);
                break;
            case TYPE_SDCARD:
                /**
                 * WXSample 可以替换成自定义的字符串，针对埋点有效。
                 * template 是.we transform 后的 js文件。
                 * option 可以为空，或者通过option传入 js需要的参数。例如bundle js的地址等。
                 * jsonInitData 可以为空。
                 * width 为-1 默认全屏，可以自己定制。
                 * height =-1 默认全屏，可以自己定制。
                 */
                Map<String, Object> options1 = new HashMap<>();
                options1.put(WXSDKInstance.BUNDLE_URL, currentPath);
                mWXSDKInstance.render("UpsoftSdk", FileUtil.readSDFile(getSDPath(currentPath)), options1, null,
                        WXRenderStrategy
                                .APPEND_ASYNC);
                break;
            case TYPE_ASSETS:
                /**
                 * WXSample 可以替换成自定义的字符串，针对埋点有效。
                 * template 是.we transform 后的 js文件。
                 * option 可以为空，或者通过option传入 js需要的参数。例如bundle js的地址等。
                 * jsonInitData 可以为空。
                 * width 为-1 默认全屏，可以自己定制。
                 * height =-1 默认全屏，可以自己定制。
                 */
                Map<String, Object> options2 = new HashMap<>();
                options2.put(WXSDKInstance.BUNDLE_URL, currentPath);
                mWXSDKInstance.render("UpsoftSdk", WXFileUtils.loadAsset(getAssetPath(currentPath), this), options2,
                        null, WXRenderStrategy.APPEND_ASYNC);
                break;
        }
    }

    private void reLoadPage() {
        createWeexInstance();
        loadPage();
    }


    private void loadWXfromService(final String url) {
        mProgressBar.setVisibility(View.VISIBLE);

        if (mWXSDKInstance != null) {
            mWXSDKInstance.destroy();
        }

        RenderContainer renderContainer = new RenderContainer(this);
        mWXSDKInstance = new WXSDKInstance(this);
        mWXSDKInstance.setRenderContainer(renderContainer);
        mWXSDKInstance.registerRenderListener(this);
        mWXSDKInstance.setNestedInstanceInterceptor(this);
        mWXSDKInstance.setBundleUrl(url);
        mWXSDKInstance.setTrackComponent(true);
        container.addView(renderContainer);
        final HashMap mConfigMap = new HashMap<String, Object>();
        BaseHttp.getInstance().simpleGet(mContext, url, new HttpListener<String>() {
            @Override
            public void onSucceed(int what, Response<String> response) {
                mConfigMap.put("bundleUrl", url);
                mWXSDKInstance.render("UpsoftSDK", response.get(), mConfigMap, null, ScreenUtil.getScreenWidth
                        (mContext), ScreenUtil.getScreenHeight(mContext), WXRenderStrategy.APPEND_ASYNC);
            }

            @Override
            public void onFailed(int what, Response<String> response) {
                mProgressBar.setVisibility(View.GONE);
            }
        });
    }

    @Override
    public void onCreateNestInstance(WXSDKInstance instance, NestedContainer container) {
        L.e("onCreateNestInstance执行");
    }
}
