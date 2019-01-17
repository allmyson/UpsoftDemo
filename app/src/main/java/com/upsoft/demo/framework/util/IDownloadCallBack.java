package com.upsoft.demo.framework.util;

/**
 * Created by Administrator on 2017/10/11.
 */

public interface IDownloadCallBack {
    void onSuccess(String var1);

    void onFail(String var1);

    void onProgress(int var1, int var2);
}
