package com.li.xiaomi.xiaomilibrary.net.okhttp;

import com.google.gson.Gson;
import com.li.xiaomi.xiaomilibrary.net.HeadBean;
import com.li.xiaomi.xiaomilibrary.net.HttpUtils;
import com.li.xiaomi.xiaomilibrary.utils.LogUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.WeakHashMap;

import okhttp3.Call;
import okhttp3.FormBody;
import okhttp3.MultipartBody;
import okhttp3.Request;
import okhttp3.RequestBody;

/**
 * 作者：dell or Xiaomi Li
 * 时间： 2018/3/31
 * 内容：
 * 最后修改：
 */

public final class SendRequestOkHttp {
    private final static String TAG = SendRequestOkHttp.class.getSimpleName();

    /**
     * 文件和post一起上传
     *
     * @param heads         请求头，可为空
     * @param params        参数  可为空
     * @param fileArrayList 上传文件 可为空
     * @param url           请求地址
     * @param myCallBack    请求回调
     */
    public static void sendPost(ArrayList<HeadBean> heads, WeakHashMap<String, String> params, ArrayList<File> fileArrayList, String url, MyOkHttpCallBack myCallBack) {
        MultipartBody.Builder multipartBody = new MultipartBody.Builder();
        multipartBody.setType(MultipartBody.FORM);
        if (fileArrayList != null && fileArrayList.size() != 0) {
            for (File file : fileArrayList) {
                multipartBody.addFormDataPart("file", file.getName(), RequestBody.create(HttpUtils.MEDIA_TYPE_PNG, file));//web端的表单域；图片名称；图片的RequestBody
            }
        }
        if (params != null && params.size() != 0) {
            for (WeakHashMap.Entry<String, String> map : params.entrySet()) {
                multipartBody.addFormDataPart(map.getKey(), map.getValue());
            }
        }
        RequestBody requestBody = multipartBody.build();
        okhttpSend(heads, requestBody, url, myCallBack);
    }

    public static void sendPost(ArrayList<HeadBean> heads, WeakHashMap<String, String> params, String url, MyOkHttpCallBack myCallBack) {
        FormBody.Builder formBodyBuilder = new FormBody.Builder();
        if (params != null && params.size() != 0) {
            for (WeakHashMap.Entry<String, String> map : params.entrySet()) {
                formBodyBuilder.add(map.getKey(), map.getValue());
            }
        }
        FormBody formBody = formBodyBuilder.build();
        okhttpSend(heads, formBody, url, myCallBack);
    }

    public static void sendPost(ArrayList<HeadBean> heads, Object mSendBean, String Url, MyOkHttpCallBack mOkCallBack) {
        //创建json请求体
        if (null != mSendBean) {
            RequestBody jsonBody = RequestBody.create(HttpUtils.MEDIA_TYPE_JSON, new Gson().toJson(mSendBean));
            okhttpSend(heads, jsonBody, Url, mOkCallBack);
        } else {
            throw new RuntimeException("mSendBean can not null");
        }
    }

    public static void sendGet(ArrayList<HeadBean> heads, WeakHashMap<String, String> params, String url, MyOkHttpCallBack myCallBack) {
        StringBuilder SendUrl = new StringBuilder(url);
        String substring = SendUrl.substring(SendUrl.length() - 2, SendUrl.length() - 1);
        if (!substring.equals("?")) {//判断传进来的url最后一位是不是？
            SendUrl.append("?");
        }
        if (params != null && params.size() != 0) {
            for (WeakHashMap.Entry<String, String> map : params.entrySet()) {
                SendUrl.append(map.getKey());
                SendUrl.append("=");
                SendUrl.append(map.getValue());
                SendUrl.append("&");
            }
            url = SendUrl.substring(0, SendUrl.length() - 1);
        }
        okhttpSend(heads, null, url, myCallBack);
    }

    private static void okhttpSend(ArrayList<HeadBean> heads, RequestBody requestBody, String url, MyOkHttpCallBack myCallBack) {
        LogUtils.Loge(TAG, "url:" + url);
        Request.Builder requestBuilder = new Request.Builder();
        if (heads != null && heads.size() != 0) {
            for (HeadBean head : heads) {
                requestBuilder.addHeader(head.getHeadKey(), head.getHeadValue());
            }
        }
        //如果请求体是空的，那就是get请求，否则是post请求
        if (requestBody != null) {
            requestBuilder.post(requestBody);
        }
        Request request = requestBuilder.
                url(url).
                build();
        Call call = MyOkHttpClient.getOkHttpClient().newCall(request);
        call.enqueue(myCallBack);
    }
}
