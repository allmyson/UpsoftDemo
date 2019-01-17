package com.upsoft.sdk.weex.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.google.gson.Gson;
import com.taobao.weex.adapter.IWXHttpAdapter;
import com.taobao.weex.common.WXRequest;
import com.taobao.weex.common.WXResponse;
import com.upsoft.sdk.Constant;
import com.upsoft.sdk.http.BaseBean;
import com.upsoft.sdk.sp.CookieSP;
import com.upsoft.sdk.util.GsonUtil;
import com.upsoft.sdk.util.StringUtil;
import com.upsoft.sdk.weex.listener.IncrementalResponseBody;
import com.upsoft.sdk.weex.listener.RequestListener;
import com.upsoft.sdk.weex.listener.ResponseListener;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.internal.http.HttpMethod;

/**
 * @author lh
 * @version 1.0.0
 * @filename OkHttpAdapter
 * @description -------------------------------------------------------
 * @date 2017/9/6 10:36
 */
public class OkHttpAdapter implements IWXHttpAdapter {

    private static final String METHOD_GET = "GET";
    private static final String METHOD_POST = "POST";

    public static final int REQUEST_FAILURE = -100;
    private Context context;

    public OkHttpAdapter() {
    }

    public OkHttpAdapter(Context context) {
        this.context = context;
    }

    @Override
    public void sendRequest(final WXRequest request, final OnHttpListener listener) {
        if (listener != null) {
            listener.onHttpStart();
        }
        printRequest(request);
        RequestListener requestListener = new RequestListener() {
            @Override
            public void onRequest(long consumed, long total, boolean done) {
                if (StringUtil.checkNull(listener)) {
                    listener.onHttpUploadProgress((int) (consumed));
                }
            }
        };

        final ResponseListener responseListener = new ResponseListener() {
            @Override
            public void onResponse(long consumed, long total, boolean done) {
                if (StringUtil.checkNull(listener)) {
                    listener.onHttpResponseProgress((int) (consumed));
                }
            }
        };

        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Response originalResponse = chain.proceed(chain.request());
                return originalResponse.newBuilder()
                        .body(new IncrementalResponseBody(originalResponse.body(), responseListener))
                        .build();
            }
        }).build();


        String method = request.method == null ? "GET" : request.method.toUpperCase();
        String requestBodyString = request.body == null ? "{}" : request.body;
        RequestBody body = HttpMethod.requiresRequestBody(method) ? RequestBody.create(MediaType.parse
                ("application/json"), requestBodyString) : null;
//        RequestBody body = new IncrementaRequestBody(RequestBody.create(MediaType.parse(StringUtil.valueOf(request
//                .body)), StringUtil.valueOf(request.body)), requestListener);
        Request okHttpRequest;
        if (METHOD_GET.equalsIgnoreCase(request.method)) {
            okHttpRequest = new Request.Builder().url(request.url).addHeader("Cookie", CookieSP.getCookie(context))
                    .build();
        } else {
            Request.Builder requestBuilder = new Request.Builder()
                    .url(request.url)
                    .addHeader("Cookie", CookieSP.getCookie(context))
                    .method(method, body);

            for (Map.Entry<String, String> param : request.paramMap.entrySet()) {
                requestBuilder.addHeader(param.getKey(), param.getValue());
            }
            okHttpRequest = requestBuilder.build();
        }
        client.newCall(okHttpRequest).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                WXResponse wxResponse = new WXResponse();
                wxResponse.errorMsg = e.getMessage();
                wxResponse.errorCode = "-1";
                wxResponse.statusCode = "-1";
                listener.onHttpFinish(wxResponse);
            }

            @Override
            public void onResponse(Call call, Response response) {
                WXResponse wxResponse = new WXResponse();
                byte[] responseBody = new byte[0];
                try {
                    responseBody = response.body().bytes();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                String result = new String(responseBody);
                if (result != null && GsonUtil.isJSONValid3(result)) {
                    BaseBean baseBean = new Gson().fromJson(result, BaseBean.class);
                    if (baseBean != null && "0".equals(baseBean.status)) {
                        if (Constant.SESSION_EXPIRED.equals(baseBean.errorCode) || Constant.SESSION_INVALID.equals
                                (baseBean.errorCode)) {
                            //session过期或者无效弹出跳转登录界面弹窗
//                        FunctionApi.showLoginDialog(mContext);
                            Intent intent = new Intent(Constant.Action.ACTION_SESSION_INVALID);
                            intent.putExtra("msg", baseBean.errorCode);
                            context.sendBroadcast(intent);
                            return;
                        }
                    }
                }
                wxResponse.data = new String(responseBody);
                wxResponse.statusCode = String.valueOf(response.code());
                wxResponse.originalData = responseBody;
                wxResponse.extendParams = new HashMap<>();
                for (Map.Entry<String, List<String>> entry : response.headers().toMultimap().entrySet()) {
                    wxResponse.extendParams.put(entry.getKey(), entry.getValue());
                }

                if (response.code() < 200 || response.code() > 299) {
                    wxResponse.errorMsg = response.message();
                }

                listener.onHttpFinish(wxResponse);
            }
        });

//        if (METHOD_POST.equalsIgnoreCase(request.method)) {
//            Request okHttpRequest = new Request.Builder()
//                    .url(request.url)
//                    .addHeader("Cookie", CookieSP.getCookie(context))
//                    .post(new IncrementaRequestBody(RequestBody.create(MediaType.parse(StringUtil.valueOf(request
//                            .body)), StringUtil.valueOf(request.body)),
//                            requestListener))
//                    .build();
//
//            client.newCall(okHttpRequest).enqueue(new Callback() {
//                @Override
//                public void onFailure(Call call, IOException e) {
//                    if (StringUtil.checkNull(listener)) {
//                        WXResponse wxResponse = new WXResponse();
//                        wxResponse.errorCode = String.valueOf(REQUEST_FAILURE);
//                        wxResponse.statusCode = String.valueOf(REQUEST_FAILURE);
//                        wxResponse.errorMsg = e.getMessage();
//                    }
//                }
//
//                @Override
//                public void onResponse(Call call, Response response) throws IOException {
//                    if (StringUtil.checkNull(listener)) {
//                        WXResponse wxResponse = new WXResponse();
//                        wxResponse.statusCode = String.valueOf(response.code());
//                        if (requestSuccess(Integer.parseInt(wxResponse.statusCode))) {
//                            wxResponse.originalData = response.body().bytes();
//                            String result = new String(wxResponse.originalData);
//                            L.e("result=" + result);
//                        } else {
//                            wxResponse.errorCode = String.valueOf(response.code());
//                            wxResponse.errorMsg = response.body().string();
//                            L.e("errorCode=" + wxResponse.errorCode + "---errorMsg=" + wxResponse.errorMsg);
//                        }
//
//                        listener.onHttpFinish(wxResponse);
//                    }
//                }
//            });
//        } else {
//            Request okHttpRequest = new Request.Builder().url(request.url).addHeader("Cookie", CookieSP.getCookie
//                    (context)).build();
//            client.newCall(okHttpRequest).enqueue(new Callback() {
//                @Override
//                public void onFailure(Call call, IOException e) {
//                    if (StringUtil.checkNull(listener)) {
//                        WXResponse wxResponse = new WXResponse();
//                        wxResponse.errorCode = String.valueOf(REQUEST_FAILURE);
//                        wxResponse.statusCode = String.valueOf(REQUEST_FAILURE);
//                        wxResponse.errorMsg = e.getMessage();
//                    }
//                }
//
//                @Override
//                public void onResponse(Call call, Response response) throws IOException {
//                    if (StringUtil.checkNull(listener)) {
//
//                        WXResponse wxResponse = new WXResponse();
//                        wxResponse.statusCode = String.valueOf(response.code());
//                        if (requestSuccess(Integer.parseInt(wxResponse.statusCode))) {
//                            wxResponse.originalData = response.body().bytes();
//                            String result = new String(wxResponse.originalData);
//                            L.e("result=" + result);
//                        } else {
//                            wxResponse.errorCode = String.valueOf(response.code());
//                            wxResponse.errorMsg = response.body().string();
//                            L.e("errorCode=" + wxResponse.errorCode + "---errorMsg=" + wxResponse.errorMsg);
//                        }
//
//                        listener.onHttpFinish(wxResponse);
//                    }
//                }
//            });
//        }
    }

    private boolean requestSuccess(int statusCode) {
        return statusCode >= 200 && statusCode <= 299;
    }


    private void printRequest(WXRequest request) {
        Log.e("lh", "url=" + request.url);
        Log.e("lh", "method=" + request.method);
        Log.e("lh", "timeoutMs=" + request.timeoutMs);
        Log.e("lh", "body=" + request.body);
        Map<String, String> map = request.paramMap;
        if (map != null) {
            for (String key : map.keySet()) {
                Log.e("lh", "key=" + key + "--value=" + map.get(key));
            }
        }
    }
}
