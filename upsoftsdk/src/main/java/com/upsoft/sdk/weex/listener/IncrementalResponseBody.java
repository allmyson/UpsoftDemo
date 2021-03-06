package com.upsoft.sdk.weex.listener;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.ResponseBody;
import okio.Buffer;
import okio.BufferedSource;
import okio.ForwardingSource;
import okio.Okio;
import okio.Source;

/**
 * @author lh
 * @version 1.0.0
 * @filename IncrementalResponseBody
 * @description -------------------------------------------------------
 * @date 2017/9/6 10:35
 */
public class IncrementalResponseBody extends ResponseBody {

    private ResponseBody realBody;
    private ResponseListener responseListener;

    public IncrementalResponseBody(ResponseBody realBody, ResponseListener responseListener){
        this.realBody = realBody;
        this.responseListener = responseListener;
    }

    @Override
    public MediaType contentType() {
        return realBody.contentType();
    }

    @Override
    public long contentLength() {
        return realBody.contentLength();
    }

    @Override
    public BufferedSource source() {
        return Okio.buffer(source(realBody.source()));
    }

    private Source source(Source source) {

        return new ForwardingSource(source) {
            long totalConsumed = 0L;
            @Override public long read(Buffer sink, long byteCount) throws IOException {
                long currentConsumed = super.read(sink, byteCount);
                totalConsumed += currentConsumed != -1 ? currentConsumed : 0;
                responseListener.onResponse(totalConsumed, IncrementalResponseBody.this.contentLength(), currentConsumed == -1);
                return currentConsumed;
            }
        };
    }
}