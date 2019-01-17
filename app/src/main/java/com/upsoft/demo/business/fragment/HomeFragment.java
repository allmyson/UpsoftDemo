package com.upsoft.demo.business.fragment;

import android.view.View;
import android.view.ViewGroup;

import com.taobao.weex.IWXRenderListener;
import com.taobao.weex.WXSDKEngine;
import com.taobao.weex.WXSDKInstance;
import com.taobao.weex.common.WXRenderStrategy;
import com.upsoft.demo.R;
import com.upsoft.demo.framework.base.BaseFragment;
import com.upsoft.demo.framework.weex.adapter.NavigatorAdapter;

import java.util.HashMap;
import java.util.Map;

/**
 * @author lh
 * @version 1.0.0
 * @filename HomeFragment
 * @description -------------------------------------------------------
 * @date 2018/9/20 15:15
 */
public class HomeFragment extends BaseFragment implements IWXRenderListener {
    protected WXSDKInstance mWXSDKInstance;
    protected ViewGroup container;
    private String currentPath = "http://dotwe.org/raw/dist/f6b32e5aa7de30f93c3fe1331872cae9.bundle.wx";

    @Override
    protected void init() {
        container = getView(R.id.fl_parent);
        createWeexInstance();
        WXSDKEngine.setActivityNavBarSetter(new NavigatorAdapter(mContext));
    }

    @Override
    protected void getData() {
        Map<String, Object> options = new HashMap<>();
        options.put(WXSDKInstance.BUNDLE_URL, currentPath);
        mWXSDKInstance.renderByUrl("LHSample", currentPath, options, null, WXRenderStrategy.APPEND_ONCE);
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_home;
    }

    public static HomeFragment newInstance() {
        return new HomeFragment();
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void onViewCreated(WXSDKInstance instance, View view) {
        if (view.getParent() == null) {
            container.addView(view);
        }
        container.requestLayout();
//        container.addView(view);
    }

    @Override
    public void onRenderSuccess(WXSDKInstance instance, int width, int height) {

    }

    @Override
    public void onRefreshSuccess(WXSDKInstance instance, int width, int height) {

    }

    @Override
    public void onException(WXSDKInstance instance, String errCode, String msg) {

    }


    @Override
    public void onPause() {
        super.onPause();
        if (mWXSDKInstance != null) {
            mWXSDKInstance.onActivityPause();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mWXSDKInstance != null) {
            mWXSDKInstance.onActivityStop();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mWXSDKInstance != null) {
            mWXSDKInstance.onActivityDestroy();
        }
    }

    protected void createWeexInstance() {
        destoryWeexInstance();
        mWXSDKInstance = new WXSDKInstance(mContext);
        mWXSDKInstance.registerRenderListener(this);
    }

    protected void destoryWeexInstance() {
        if (mWXSDKInstance != null) {
            mWXSDKInstance.registerRenderListener(null);
            mWXSDKInstance.destroy();
            mWXSDKInstance = null;
        }
    }

}
