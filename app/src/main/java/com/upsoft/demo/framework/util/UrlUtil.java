package com.upsoft.demo.framework.util;

import android.content.Context;

import com.upsoft.demo.framework.api.FunctionApi;

/**
 * @author lh
 * @version 1.0.0
 * @filename UrlUtil
 * @description -------------------------------------------------------
 * @date 2018/10/19 18:10
 */
public class UrlUtil {

    public static String getFileUrl(Context context, String url) {
        return FunctionApi.getHttpIp(context) + "/cgi/bp/files" + url;
    }

    public static String getFileUrl(String currentIp, String url) {
        return currentIp + "/cgi/bp/files" + url;
    }

    public static String getFileUrlByFileId(String currentIp, String fileId) {
        return currentIp + "/cgi/bp/filemgt/special/download?fileId=" + fileId;
    }
}
