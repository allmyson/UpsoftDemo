package com.upsoft.sdk.weex.listener;

/**
 * @author lh
 * @version 1.0.0
 * @filename RequestListener
 * @description -------------------------------------------------------
 * @date 2017/9/6 10:33
 */
public interface RequestListener {
    void onRequest(long consumed, long total, boolean done);
}
