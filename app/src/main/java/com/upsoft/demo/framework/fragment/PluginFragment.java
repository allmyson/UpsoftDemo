package com.upsoft.demo.framework.fragment;

import android.content.Intent;
import android.graphics.Color;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import com.google.gson.Gson;
import com.upsoft.demo.R;
import com.upsoft.demo.business.activity.CommonWebviewActivity;
import com.upsoft.demo.framework.adapter.PluginAdapter2;
import com.upsoft.demo.framework.api.FunctionApi;
import com.upsoft.demo.framework.api.WeexApi;
import com.upsoft.demo.framework.base.BaseFragment;
import com.upsoft.demo.framework.bean.PlugBean2;
import com.upsoft.demo.framework.weex.activity.WeexPageActivity;
import com.upsoft.sdk.http.nohttp.HttpListener;
import com.upsoft.sdk.util.StringUtil;
import com.yanzhenjie.nohttp.rest.Response;

import java.util.ArrayList;
import java.util.List;

/**
 * @author lh
 * @version 1.0.0
 * @filename PluginFragment
 * @description -------------------------------------------------------
 * @date 2018/9/20 15:17
 */
public class PluginFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener {
    private GridView gv;
    private List<PlugBean2.DataBeanX.DataBean> pluginBeanList;
    //    private PluginAdapter mAdapter;
    private PluginAdapter2 mAdapter;
    private String currentIp;
    private SwipeRefreshLayout pluginSRL;

    @Override
    protected void init() {
        titleTV = getView(R.id.tv_title);
        gv = (GridView) mView.findViewById(R.id.gv_);
        pluginSRL = getView(R.id.srl_plugin);
        pluginSRL.setColorSchemeColors(Color.BLUE, Color.RED, Color.GREEN, Color.YELLOW);

        pluginSRL.setOnRefreshListener(this);

        pluginBeanList = new ArrayList<>();
//        pluginBeanList.addAll(PluginBean.getDefaultData());
        mAdapter = new PluginAdapter2(mContext, pluginBeanList, R.layout.item_plugin);
        gv.setAdapter(mAdapter);
        gv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                PlugBean2.DataBeanX.DataBean pluginBean = mAdapter.getItem(position);
                if (pluginBean != null) {
                    switch (StringUtil.StringToInt(pluginBean.pluginType)) {
                        case 0:
//                            show("Native Plugin");
                            try {
                                Intent intent = new Intent();
                                intent.setClassName(mContext, pluginBean.handleUrl);
                                startActivity(intent);
                            } catch (Exception e) {
                                e.printStackTrace();
//                                show(e.getMessage());
                                Intent intent = getActivity().getPackageManager().getLaunchIntentForPackage
                                        (pluginBean.handleUrl);
                                if (intent != null) {
                                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                    startActivity(intent);
                                }
                            }
                            break;
                        case 1:
                            String handleUrl = StringUtil.valueOf(pluginBean.handleUrl);
                            if (!handleUrl.contains("http://") && !handleUrl.contains("https://")) {
                                handleUrl = currentIp + handleUrl;
                            }
                            CommonWebviewActivity.openWebUrl(mContext, handleUrl);
                            break;
                        case 2:
//                            show("Weex Plugin");
                            if (pluginBean.handleUrl == null) {
                                return;
                            }
                            if (pluginBean.handleUrl.startsWith("file://")) {
                                WeexApi.loadWX(mContext, WeexPageActivity.TYPE_ASSETS, pluginBean.handleUrl);
                            } else if (pluginBean.handleUrl.startsWith("http://") || pluginBean.handleUrl.startsWith
                                    ("https://")) {
                                WeexApi.loadWX(mContext, WeexPageActivity.TYPE_NET, pluginBean.handleUrl);
                            } else if (pluginBean.handleUrl.startsWith("sdcard://")) {
                                WeexApi.loadWX(mContext, WeexPageActivity.TYPE_SDCARD, pluginBean.handleUrl);
                            }
                            break;
                    }
                }
            }
        });
        currentIp = FunctionApi.getHttpIp(mContext);
    }

    @Override
    protected void getData() {
        FunctionApi.getPlugInfo(mActivity, new HttpListener<String>() {
            @Override
            public void onSucceed(int what, Response<String> response) {
                pluginBeanList.clear();
                PlugBean2 plugBean2 = new Gson().fromJson(response.get() + "", PlugBean2.class);
                if (plugBean2 != null && "1".equals(plugBean2.status) && plugBean2.data != null) {
                    if (plugBean2.data.data != null && plugBean2.data.data.size() > 0) {
                        for (PlugBean2.DataBeanX.DataBean dataBean : plugBean2.data.data) {
                            if (dataBean != null && "1".equals(dataBean.status) && "1".equals(dataBean.available)) {
                                pluginBeanList.add(dataBean);
                            }
                        }
                    }
                }
                mAdapter.refresh(pluginBeanList);
                pluginSRL.setRefreshing(false);
            }

            @Override
            public void onFailed(int what, Response<String> response) {
                pluginSRL.setRefreshing(false);
            }
        });
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_plugin;
    }


    public static PluginFragment newInstance() {
        return new PluginFragment();
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void onRefresh() {
        getData();
    }
}
