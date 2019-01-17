package com.upsoft.demo.framework.fragment;

import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.widget.AdapterView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.upsoft.demo.R;
import com.upsoft.demo.framework.activity.AboutActivity;
import com.upsoft.demo.framework.activity.DownloadActivity;
import com.upsoft.demo.framework.activity.IndividualizationActivity;
import com.upsoft.demo.framework.activity.NFCScanActivity;
import com.upsoft.demo.framework.activity.NetworkActivity;
import com.upsoft.demo.framework.activity.SetTextSizeActivity;
import com.upsoft.demo.framework.activity.TestActivity;
import com.upsoft.demo.framework.activity.UserInfoActivity;
import com.upsoft.demo.framework.adapter.DownloadAdapter;
import com.upsoft.demo.framework.adapter.MyAdapter;
import com.upsoft.demo.framework.api.FunctionApi;
import com.upsoft.demo.framework.api.WeexApi;
import com.upsoft.demo.framework.base.BaseFragment;
import com.upsoft.demo.framework.bean.InfoBean;
import com.upsoft.demo.framework.bean.PlugInfo;
import com.upsoft.demo.framework.bean.UploadFileBean;
import com.upsoft.demo.framework.config.FrameworkSDK;
import com.upsoft.demo.framework.dialog.DialogUtil;
import com.upsoft.demo.framework.dialog.List3Dialog;
import com.upsoft.demo.framework.sp.UserSP;
import com.upsoft.demo.framework.ui.CircleImageView;
import com.upsoft.demo.framework.ui.MyListView;
import com.upsoft.demo.framework.update.UpdateManager;
import com.upsoft.demo.framework.weex.activity.WeexPageActivity;
import com.upsoft.qrlibrary.CaptureActivity;
import com.upsoft.sdk.Constant;
import com.upsoft.sdk.http.BaseBean;
import com.upsoft.sdk.http.nohttp.HttpListener;
import com.upsoft.sdk.image.loader.ImageLoader;
import com.upsoft.sdk.sp.SPUtil;
import com.upsoft.sdk.util.FileUtil;
import com.upsoft.sdk.util.L;
import com.upsoft.sdk.util.NetUtil;
import com.upsoft.sdk.util.StringUtil;
import com.upsoft.sdk.util.ToastUtil;
import com.yanzhenjie.nohttp.rest.Response;
import com.yongchun.library.view.ImageSelectorActivity;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static android.app.Activity.RESULT_OK;

/**
 * @author lh
 * @version 1.0.0
 * @filename MyFragment
 * @description -------------------------------------------------------
 * @date 2018/9/20 15:18
 */
public class MyFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener {
    public static final int KEY_SCAN = 100;
    public static final int KEY_NFC = 101;

    private RelativeLayout headerRL;
    private CircleImageView headerCIV;
    private TextView nickNameTV, orgNameTV;
    //    private LinearLayout scanLL, nfcLL, downloadLL, clearLL, updateLL, aboutLL, netLL;
    private TextView sdkTV;
    private SwipeRefreshLayout mySRL;
    private RelativeLayout individualizationRL;
    private MyListView myListView;
    private MyAdapter myAdapter;
    private List<PlugInfo> list;

    @Override
    protected void init() {
        myListView = getView(R.id.mlv_);
        individualizationRL = getView(R.id.rl_individualization);
        individualizationRL.setOnClickListener(this);
        titleTV = getView(R.id.tv_title);
        headerRL = getView(R.id.rl_header);
        headerCIV = getView(R.id.civ_header);
        nickNameTV = getView(R.id.tv_nickName);
        orgNameTV = getView(R.id.tv_orgName);
//        scanLL = getView(R.id.ll_sys);
//        nfcLL = getView(R.id.ll_nfc);
//        downloadLL = getView(R.id.ll_download);
//        clearLL = getView(R.id.ll_clear);
//        updateLL = getView(R.id.ll_update);
//        aboutLL = getView(R.id.ll_about);
//        netLL = getView(R.id.ll_net);
        sdkTV = getView(R.id.tv_sdk);
        mySRL = getView(R.id.srl_my);
        mySRL.setColorSchemeColors(Color.BLUE, Color.RED, Color.GREEN, Color.YELLOW);

        mySRL.setOnRefreshListener(this);

        headerRL.setOnClickListener(this);
        headerCIV.setOnClickListener(this);
//        scanLL.setOnClickListener(this);
//        nfcLL.setOnClickListener(this);
//        downloadLL.setOnClickListener(this);
//        clearLL.setOnClickListener(this);
//        updateLL.setOnClickListener(this);
//        aboutLL.setOnClickListener(this);
//        netLL.setOnClickListener(this);
        sdkTV.setOnClickListener(this);


        list = new ArrayList<>();
//        list.add(new PlugInfo(R.mipmap.ic_sys, "扫一扫"));
//        list.add(new PlugInfo(R.mipmap.ic_nfc, "扫标签"));
        list.addAll(FrameworkSDK.getDefaultConfig().getMyPlugList());
//        list.add(new PlugInfo(R.mipmap.ic_xgxz, "相关下载"));
//        list.add(new PlugInfo(R.mipmap.ic_hcql, "缓存清理"));
//        list.add(new PlugInfo(R.mipmap.ic_update, "检查更新"));
//        list.add(new PlugInfo(R.mipmap.ic_about, "关于"));
//        list.add(new PlugInfo(R.mipmap.ic_wlpz, "网络配置"));
        myAdapter = new MyAdapter(mContext, list, R.layout.item_my);
        myListView.setAdapter(myAdapter);
        myListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if("相关下载".equals(myAdapter.getItem(position).name)){
                    startActivity(new Intent(mContext, DownloadActivity.class));
                }else if("缓存清理".equals(myAdapter.getItem(position).name)){
                    showClearDilaog();
                }else if("检查更新".equals(myAdapter.getItem(position).name)){
                    new UpdateManager(mContext, true).checkUpdate();
                }else if("关于".equals(myAdapter.getItem(position).name)){
                    startActivity(new Intent(mContext, AboutActivity.class));
                }else if("网络配置".equals(myAdapter.getItem(position).name)){
                    startActivity(new Intent(mContext, NetworkActivity.class));
                }
            }
        });
        startAnima(sdkTV);
    }

    @Override
    protected void getData() {
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
                    mySRL.setRefreshing(false);
                }

                @Override
                public void onFailed(int what, Response<String> response) {
                    mySRL.setRefreshing(false);
                }
            });
        } else {
            InfoBean infoBean = UserSP.getInfo(mContext);
            setData(infoBean);
            mySRL.setRefreshing(false);
        }
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

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_my;
    }

    public static MyFragment newInstance() {
        return new MyFragment();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.civ_header:
                FunctionApi.takePicture(this, 1, 2, true, true, true);
                break;
            case R.id.rl_header:
                startActivity(new Intent(mContext, UserInfoActivity.class));
                break;
//            case R.id.ll_sys:
//                FunctionApi.intentToCapture(this, KEY_SCAN);
//                break;
//            case R.id.ll_nfc:
//                FunctionApi.intentToNFC(this, KEY_NFC);
//                break;
//            case R.id.ll_clear:
//                showClearDilaog();
//                break;
//            case R.id.ll_update:
////                startActivity(new Intent(mContext, TActivity.class));
//                new UpdateManager(mContext, true).checkUpdate();
//                break;
//            case R.id.ll_about:
//                startActivity(new Intent(mContext, AboutActivity.class));
//                break;
//            case R.id.ll_net:
//                startActivity(new Intent(mContext, NetworkActivity.class));
//                break;
//
            case R.id.tv_sdk:
                startActivity(new Intent(mContext, TestActivity.class));
                break;
//            case R.id.ll_download:
//                startActivity(new Intent(mContext, DownloadActivity.class));
//                break;
            //个性化
            case R.id.rl_individualization:
                DialogUtil.showSetList(mContext, getSetList(), new List3Dialog.ClickListener() {
                    @Override
                    public void click(int position) {
                        switch (position) {
                            case 0:
                                startActivity(new Intent(mContext, IndividualizationActivity.class));
                                break;
                            case 1:
                                startActivity(new Intent(mContext, SetTextSizeActivity.class));
                                break;
                        }
                    }
                });
//                startActivity(new Intent(mContext, IndividualizationActivity.class));
                break;

        }
    }

    private void showClearDilaog() {
        String size = FileUtil.getAutoFileOrFilesSize(Constant.getProjectRoot());
        L.e("缓存目录大小=" + size);
        AlertDialog.Builder mDialog = new AlertDialog.Builder(mContext);
        mDialog.setTitle("提示");
        mDialog.setMessage("即将清除缓存文件的大小为" + StringUtil.valueOf(size));
        mDialog.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        //设置第二个按钮
        mDialog.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                FileUtil.deleteFile2(new File(Constant.getProjectRoot()));
//                Constant.buildFile();
                SPUtil.deleteKey(mContext, DownloadAdapter.KEY_DOWNLOAD);
                show("清除缓存成功");
                dialog.dismiss();
            }
        });
        mDialog.create().show();
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case KEY_SCAN:
                    String result = data.getStringExtra(CaptureActivity.SCAN_RESULT);
                    show(result);
                    if (!StringUtil.isBlank(result) && (result.startsWith("http") || result.startsWith("https"))) {
                        WeexApi.loadWX(mContext, WeexPageActivity.TYPE_NET, result);
                    }
                    break;
                case KEY_NFC:
                    String nfcStr = data.getStringExtra(NFCScanActivity.NFC_RESULT);
                    show(nfcStr);
                    break;
                case ImageSelectorActivity.REQUEST_IMAGE://相册图片选取返回
                    ArrayList<String> images = (ArrayList<String>) data.getSerializableExtra(ImageSelectorActivity
                            .REQUEST_OUTPUT);
                    if (images != null && images.size() >= 1) {
                        updateHead(images.get(0));
                    }

            }
        }
    }

    private static final int RED = 0xffFF8080;
    private static final int BLUE = 0xff8080FF;
    private static final int CYAN = 0xff80ffff;
    private static final int GREEN = 0xff80ff80;


    private void startAnima(View view) {
        ValueAnimator colorAnim = ObjectAnimator.ofInt(view, "backgroundColor", RED, BLUE);
        colorAnim.setDuration(3000);
        colorAnim.setEvaluator(new ArgbEvaluator());
        colorAnim.setRepeatCount(ValueAnimator.INFINITE);
        colorAnim.setRepeatMode(ValueAnimator.REVERSE);
        colorAnim.start();

    }

    @Override
    public void onRefresh() {
        getData();
    }

    private void updateHead(final String filePath) {
        if (StringUtil.isBlank(filePath)) {
            show("头像不能为空！");
            return;
        }
        FunctionApi.updateFile(mActivity, filePath, new HttpListener<String>() {
            @Override
            public void onSucceed(int what, Response<String> response) {
                L.e(response.get() + "");
                final UploadFileBean bean = new Gson().fromJson(response.get(), UploadFileBean.class);
                if (bean != null && "1".equals(bean.status)) {
                    String fileId = bean.fileId;
                    FunctionApi.updatePhoto(mActivity, fileId, new HttpListener<String>() {
                        @Override
                        public void onSucceed(int what, Response<String> response) {
                            L.e(response.get() + "");
                            BaseBean baseBean = new Gson().fromJson(response.get(), BaseBean.class);
                            if (baseBean != null && "1".equals(baseBean.status)) {
                                ImageLoader.with(mContext).file(filePath).error(R.mipmap.bg_default_header2).into
                                        (headerCIV);
                            } else {
                                show("修改用户头像失败！");
                            }
                        }

                        @Override
                        public void onFailed(int what, Response<String> response) {

                        }
                    });
                } else {
                    show("上传头像失败！");
                }
            }

            @Override
            public void onFailed(int what, Response<String> response) {

            }
        });
    }

    private List<String> getSetList() {
        List<String> list = new ArrayList<>();
        list.add("一键换肤");
        list.add("字体设置");
        return list;
    }
}
