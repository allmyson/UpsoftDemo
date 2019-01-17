package com.upsoft.demo.framework.adapter;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.view.View;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.upsoft.demo.R;
import com.upsoft.demo.framework.api.FunctionApi;
import com.upsoft.demo.framework.bean.DownloadBean;
import com.upsoft.demo.framework.bean.FileBean;
import com.upsoft.demo.framework.util.OpenFileUtil;
import com.upsoft.demo.framework.util.UrlUtil;
import com.upsoft.sdk.Constant;
import com.upsoft.sdk.adapter.CommonAdapter;
import com.upsoft.sdk.adapter.ViewHolder;
import com.upsoft.sdk.http.BaseHttp;
import com.upsoft.sdk.http.nohttp.HttpListener;
import com.upsoft.sdk.image.loader.ImageLoader;
import com.upsoft.sdk.sp.SPUtil;
import com.upsoft.sdk.util.FileUtil;
import com.upsoft.sdk.util.L;
import com.upsoft.sdk.util.StringUtil;
import com.upsoft.sdk.util.ToastUtil;
import com.yanzhenjie.nohttp.Headers;
import com.yanzhenjie.nohttp.download.DownloadListener;
import com.yanzhenjie.nohttp.download.DownloadRequest;
import com.yanzhenjie.nohttp.error.NetworkError;
import com.yanzhenjie.nohttp.error.ServerError;
import com.yanzhenjie.nohttp.error.StorageReadWriteError;
import com.yanzhenjie.nohttp.error.StorageSpaceNotEnoughError;
import com.yanzhenjie.nohttp.error.TimeoutError;
import com.yanzhenjie.nohttp.error.URLError;
import com.yanzhenjie.nohttp.error.UnKnownHostError;
import com.yanzhenjie.nohttp.rest.Response;
import com.yanzhenjie.permission.AndPermission;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;


public class DownloadAdapter extends CommonAdapter<DownloadBean.DataBeanX.DataBean> {
    private Context mContext;
    private ProgressDialog mProgressDialog;
    int totle = 0;
    int pro = 0;
    private boolean downloadState = false;
    private String currentIp;
    private DownloadRequest mDownloadRequest;
    private String textIconColor;
    public static final String KEY_DOWNLOAD = "key_download";
    private Map<String, String> map = null;

    public void onDestroy() {
        if (mDownloadRequest != null) {
            mDownloadRequest.cancel();
        }
    }

    public DownloadAdapter(Context context, List<DownloadBean.DataBeanX.DataBean> mDatas, int itemLayoutId) {
        super(context, mDatas, itemLayoutId);
        mContext = context;
        currentIp = FunctionApi.getHttpIp(mContext);
        textIconColor = FunctionApi.getCurrentTextIconColor(mContext);
        String json = (String) SPUtil.get(mContext, KEY_DOWNLOAD, "");
        if (!StringUtil.isBlank(json)) {
            map = new Gson().fromJson(json, new TypeToken<Map<String, String>>() {
            }.getType());
        }
        if (map == null) {
            map = new HashMap<>();
        }
    }

    @Override
    public void convert(ViewHolder helper, final DownloadBean.DataBeanX.DataBean item, int position) {
        String imgPath = UrlUtil.getFileUrlByFileId(currentIp, StringUtil.valueOf(item.fileIcon));
        ImageLoader.with(mContext).url(imgPath).placeHolder(R.mipmap.bg_default02).error(R.mipmap.bg_default02).into
                (helper.getView(R.id.iv_apk_logo));
        helper.setText(R.id.tv_apk_name, StringUtil.valueOf(item.fileName));
        helper.setText(R.id.tv_desc, StringUtil.valueOf(item.fileType));
        final TextView statusTV = helper.getView(R.id.tv_down_or_del);
        shapeSolid(statusTV, textIconColor);
        statusTV.setTextColor(Color.parseColor(textIconColor));
//        final String fileName = !StringUtil.isBlank(item.downloadUrl) ? item.downloadUrl.substring(item.downloadUrl
//                .lastIndexOf('/') + 1) : "";
        final String fileName = map.get(item.downloadUrl);
        L.e("fileName=" + fileName);
        if (!StringUtil.isBlank(fileName)) {
            if (FileUtil.isFileExist(Constant.DOWNLOAD_PATH + File.separator + fileName) && !StringUtil.isBlank
                    (fileName)) {
                L.e(Constant.DOWNLOAD_PATH + File.separator + fileName + "存在");
                statusTV.setText("查看");
            } else {
                L.e(Constant.DOWNLOAD_PATH + File.separator + fileName + "不存在");
                statusTV.setText("下载");
            }
        } else {
            statusTV.setText("下载");
        }
        statusTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                ToastUtil.show(mContext, "点击了下载");
                if ("下载".equals(statusTV.getText().toString())) {
                    if (AndPermission.hasPermission(mContext, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                        FunctionApi.getFileInfoByFileId(mContext, item.downloadUrl, new HttpListener<String>() {
                            @Override
                            public void onSucceed(int what, Response<String> response) {
                                FileBean fileBean = new Gson().fromJson(response.get(), FileBean.class);
                                if (fileBean != null && fileBean.data != null) {
                                    download(fileBean.data.fileId, fileBean.data.fileOriginal);
                                }else {
                                    ToastUtil.show(mContext,"文件信息为空");
                                }
                            }

                            @Override
                            public void onFailed(int what, Response response) {

                            }
                        });
//                            notify_progress();
                    } else {
                        L.e("无" + Manifest.permission.WRITE_EXTERNAL_STORAGE + "权限");
//                            AndPermission.with(getActivity())
//                                    .permission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
//                                    .requestCode(REQUEST_CODE_PERMISSION_WRITE)
//                                    .send();
                    }
                } else {
                    //查看
                    OpenFileUtil.openFileByLh(mContext, Constant.DOWNLOAD_PATH + File.separator + fileName);
                }
            }
        });

    }

    private class DownloadCallback implements DownloadListener {
        private String fileId;
        private String fileName;

        public DownloadCallback(String fileId, String fileName) {
            this.fileId = fileId;
            this.fileName = fileName;
        }

        @Override
        public void onStart(int what, boolean isResume, long beforeLength, Headers headers, long allCount) {
//                int progress = AppConfig.getInstance().getInt(PROGRESS_KEY, 0);
//                if (allCount != 0) {
//                    progress = (int) (beforeLength * 100 / allCount);
//                    mProgressBar.setProgress(progress);
//                }
//                updateProgress(progress, 0);
//
//                mBtnStart.setText(R.string.download_status_pause);
//                mBtnStart.setEnabled(true);
            L.e("开始下载");
            ToastUtil.show(mContext, "开始下载");
        }

        @Override
        public void onDownloadError(int what, Exception exception) {
//                mBtnStart.setText(R.string.download_status_again_download);
//                mBtnStart.setEnabled(true);

            String message = mContext.getString(R.string.download_error);
            String messageContent;
            if (exception instanceof ServerError) {
                messageContent = mContext.getString(R.string.download_error_server);
            } else if (exception instanceof NetworkError) {
                messageContent = mContext.getString(R.string.download_error_network);
            } else if (exception instanceof StorageReadWriteError) {
                messageContent = mContext.getString(R.string.download_error_storage);
            } else if (exception instanceof StorageSpaceNotEnoughError) {
                messageContent = mContext.getString(R.string.download_error_space);
            } else if (exception instanceof TimeoutError) {
                messageContent = mContext.getString(R.string.download_error_timeout);
            } else if (exception instanceof UnKnownHostError) {
                messageContent = mContext.getString(R.string.download_error_un_know_host);
            } else if (exception instanceof URLError) {
                messageContent = mContext.getString(R.string.download_error_url);
            } else {
                messageContent = mContext.getString(R.string.download_error_un);
            }
            message = String.format(Locale.getDefault(), message, messageContent);
            L.e(message + "");
            ToastUtil.show(mContext, StringUtil.valueOf(message));
        }

        @Override
        public void onProgress(int what, int progress, long fileCount, long speed) {
            updateProgress(progress, speed);
//                mProgressBar.setProgress(progress);
//                AppConfig.getInstance().putInt(PROGRESS_KEY, progress);
        }

        @Override
        public void onFinish(int what, String filePath) {
            L.e("Download finish, file path: " + filePath);
            map.put(fileId, fileName);
            String json = new Gson().toJson(map);
            SPUtil.put(mContext, KEY_DOWNLOAD, json);
            ToastUtil.show(mContext, "下载完成");// 提示下载完成
            notifyDataSetChanged();
            OpenFileUtil.openFileByLh(mContext, filePath);
        }

        @Override
        public void onCancel(int what) {
//                mTvResult.setText(R.string.download_status_be_pause);
//                mBtnStart.setText(R.string.download_status_resume);
//                mBtnStart.setEnabled(true);
        }

        private void updateProgress(int progress, long speed) {
//                double newSpeed = speed / 1024D;
//                DecimalFormat decimalFormat = new DecimalFormat("###0.00");
//                String sProgress = getString(R.string.download_progress);
//                sProgress = String.format(Locale.getDefault(), sProgress, progress, decimalFormat.format(newSpeed));
//                mTvResult.setText(sProgress);
        }
    }

    private void download(String fileId, String fileName) {
        String downloadUrl = UrlUtil.getFileUrlByFileId(currentIp, StringUtil.valueOf(fileId));
        try {
            FileUtil.createSDDir(Constant.DOWNLOAD_PATH);
        } catch (IOException e) {
            e.printStackTrace();
        }
        BaseHttp.getInstance().downloadFile(mContext, downloadUrl, Constant.DOWNLOAD_PATH, fileName, new
                DownloadCallback(fileId, fileName));
    }

    private void shapeSolid(View v, String fillColor) {
        GradientDrawable gd = (GradientDrawable) v.getBackground();
        gd.setStroke(2, Color.parseColor(textIconColor));
//        gd.setColor(Color.parseColor(fillColor));
    }
}
