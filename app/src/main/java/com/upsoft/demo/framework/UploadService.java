package com.upsoft.demo.framework;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.google.gson.Gson;
import com.upsoft.demo.framework.api.FunctionApi;
import com.upsoft.sdk.Constant;
import com.upsoft.sdk.http.BaseBean;
import com.upsoft.sdk.http.nohttp.HttpListener;
import com.upsoft.sdk.util.FileUtil;
import com.upsoft.sdk.util.L;
import com.upsoft.sdk.util.StringUtil;
import com.yanzhenjie.nohttp.rest.Response;

/**
 * @author lh
 * @version 1.0.0
 * @filename UploadService
 * @description -------------------------------------------------------
 * @date 2018/10/24 9:42
 */
public class UploadService extends Service {
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    int i = 0;

    @Override
    public void onCreate() {
        super.onCreate();
        L.e("UploadService启动了");
        final String uuid = StringUtil.getUUID();
        L.e("uuid=" + uuid);
        FunctionApi.uploadLogFile(this, uuid, new HttpListener<String>() {
            @Override
            public void onSucceed(int what, Response<String> response) {
                FunctionApi.uploadLog(UploadService.this, uuid, new HttpListener() {
                    @Override
                    public void onSucceed(int what, Response response) {
                        BaseBean baseBean = new Gson().fromJson(response.get() + "", BaseBean.class);
                        if (baseBean != null && "1".equals(baseBean.status)) {
                            L.e(StringUtil.valueOf(baseBean.msg));
                            FileUtil.deleteFile(Constant.CACHE_LOGE_PATH);
                        } else {
                            L.e("崩溃日志信息上传失败");
                        }
                    }

                    @Override
                    public void onFailed(int what, Response response) {

                    }
                });
            }

            @Override
            public void onFailed(int what, Response<String> response) {

            }
        });
    }
}
