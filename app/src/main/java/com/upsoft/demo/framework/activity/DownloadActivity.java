package com.upsoft.demo.framework.activity;

import android.graphics.Color;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.widget.ListView;

import com.google.gson.Gson;
import com.upsoft.demo.R;
import com.upsoft.demo.framework.adapter.DownloadAdapter;
import com.upsoft.demo.framework.api.FunctionApi;
import com.upsoft.demo.framework.base.BaseActivity;
import com.upsoft.demo.framework.bean.DownloadBean;
import com.upsoft.demo.framework.ui.AllTitleView;
import com.upsoft.sdk.http.nohttp.HttpListener;
import com.yanzhenjie.nohttp.rest.Response;

import java.util.ArrayList;
import java.util.List;

public class DownloadActivity extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener{
    private List<DownloadBean.DataBeanX.DataBean> data;
    private DownloadAdapter downloadAdapter;
    private ListView listView;
    private SwipeRefreshLayout srl;
    @Override
    public int getLayoutId() {
        return R.layout.activity_download;
    }

    @Override
    public void initView() {
        data = new ArrayList<>();
        allTitleView = (AllTitleView) this.findViewById(R.id.title);
        allTitleView.setCenterText("相关下载");
        allTitleView.setTitleOnClickListener(new AllTitleView.ITitleViewOnClick() {
            @Override
            public void backLeft() {
                finish();
            }

            @Override
            public void backRight() {
            }
        });
        allTitleView.setLeftImg(R.mipmap.ic_back);
        listView = getView(R.id.lv_listview);
        downloadAdapter = new DownloadAdapter(mContext, data, R.layout.download_item);
        listView.setAdapter(downloadAdapter);
        srl = getView(R.id.srl_);
        srl.setColorSchemeColors(Color.BLUE, Color.RED, Color.GREEN, Color.YELLOW);
        srl.setOnRefreshListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        refresh();
    }

    @Override
    public void getData() {

    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void onRefresh() {
        refresh();
    }

    private void refresh(){
        FunctionApi.getDownloadInfo(mActivity, new HttpListener<String>() {
            @Override
            public void onSucceed(int what, Response<String> response) {
                data.clear();
                DownloadBean downloadBean = new Gson().fromJson(response.get(), DownloadBean.class);
                if (downloadBean != null && "1".equals(downloadBean.status) && downloadBean.data != null &&
                        downloadBean.data.data != null && downloadBean.data.data.size() > 0) {
                    data.addAll(downloadBean.data.data);
                }
                downloadAdapter.refresh(data);
                srl.setRefreshing(false);
            }

            @Override
            public void onFailed(int what, Response<String> response) {
                srl.setRefreshing(false);
            }
        });
    }
}
