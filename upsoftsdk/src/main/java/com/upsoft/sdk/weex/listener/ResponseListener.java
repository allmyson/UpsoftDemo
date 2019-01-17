package com.upsoft.sdk.weex.listener;

/**
 * @author lh
 * @version 1.0.0
 * @filename ResponseListener
 * @description -------------------------------------------------------
 * @date 2017/9/6 10:34
 */
public interface ResponseListener {
    void onResponse(long consumed, long total, boolean done);
}
