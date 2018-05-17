package com.li.xiaomi.xiaomilibrary.net.retrofit;

import android.content.Context;
import android.os.AsyncTask;

import com.google.gson.Gson;
import com.li.xiaomi.xiaomilibrary.net.HttpUtils;
import com.li.xiaomi.xiaomilibrary.utils.LogUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.WeakHashMap;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * 作者：dell or Xiaomi Li
 * 时间： 2018/4/2
 * 内容：
 * 最后修改：
 */

public final class SendRequestRetrofit {
    private static Call<String> call = null;

    /**
     * 没有请求头，文件+参数
     *
     * @param params        参数
     * @param fileArrayList 文件
     * @param url           地址
     * @param myCallBack    回调
     */
    public static void senPost(WeakHashMap<String, Object> params, ArrayList<File> fileArrayList, String url, MyRetrofitCallBack myCallBack) {
        RestService restService = MyRestService.getRestService();
        if (fileArrayList == null || fileArrayList.size() == 0) {//没有文件就上传参数
            call = restService.post(url, params);
        } else {//有文件遍历
            WeakHashMap<String, MultipartBody.Part> partWeakHashMap = getPartWeakHashMap(fileArrayList);
            call = restService.post(url, params, partWeakHashMap);
        }
        call.enqueue(myCallBack);
    }

    /**
     * 有请求头  文件+参数
     *
     * @param heads         请求头
     * @param params        参数
     * @param fileArrayList 文件
     * @param url           地址
     * @param myCallBack    回调
     */
    public static void sendPost(WeakHashMap<String, String> heads, WeakHashMap<String, Object> params, ArrayList<File> fileArrayList, String url, MyRetrofitCallBack myCallBack) {
        RestService restService = MyRestService.getRestService();
        if (fileArrayList == null || fileArrayList.size() == 0) {//没有文件就上传参数
            call = restService.post(heads, url, params);
        } else {//有文件遍历
            WeakHashMap<String, MultipartBody.Part> partWeakHashMap = getPartWeakHashMap(fileArrayList);
            call = restService.post(heads, url, params, partWeakHashMap);
        }
        call.enqueue(myCallBack);
    }

    /**
     * 文件批量上传
     *
     * @param heads         请求头
     * @param fileArrayList 文件数组
     * @param url           请求地址
     * @param myCallBack    回调
     */
    public static void uploadFile(WeakHashMap<String, String> heads, ArrayList<File> fileArrayList, String url, MyRetrofitCallBack myCallBack) {
        RestService restService = MyRestService.getRestService();
        WeakHashMap<String, MultipartBody.Part> partWeakHashMap = getPartWeakHashMap(fileArrayList);
        if (heads != null) {
            call = restService.upload(heads, url, partWeakHashMap);
        } else {
            call = restService.upload(url, partWeakHashMap);
        }
        call.enqueue(myCallBack);
    }


    /**
     * 文件上传
     *
     * @param heads      请求头  可为空
     * @param mFile      文件
     * @param url        地址
     * @param myCallBack 回调
     */
    public static void uploadFile(WeakHashMap<String, String> heads, File mFile, String url, MyRetrofitCallBack myCallBack) {
        RestService restService = MyRestService.getRestService();
        final RequestBody requestBody =
                RequestBody.create(MediaType.parse(MultipartBody.FORM.toString()), mFile);
        final MultipartBody.Part body =
                MultipartBody.Part.createFormData("file", mFile.getName(), requestBody);
        if (heads != null) {
            call = restService.upload(heads, url, body);
        } else {
            call = restService.upload(url, body);
        }
        call.enqueue(myCallBack);
    }

    /**
     * post 请求头+参数
     *
     * @param heads      请求头
     * @param params     参数
     * @param url        地址
     * @param myCallBack 回调
     */
    public static void sendPost(WeakHashMap<String, String> heads, WeakHashMap<String, Object> params, String url, MyRetrofitCallBack myCallBack) {
        RestService restService = MyRestService.getRestService();
        if (heads != null) {
            call = restService.post(heads, url, params);
        } else {
            call = restService.post(url, params);
        }
        call.enqueue(myCallBack);
    }

    /**
     * @param heads
     * @param mSendBean
     * @param url
     * @param myCallBack
     */
    public static void sendPost(WeakHashMap<String, String> heads, Object mSendBean, String url, MyRetrofitCallBack myCallBack) {
        RestService restService = MyRestService.getRestService();
        RequestBody mRequestBody = RequestBody.create(HttpUtils.MEDIA_TYPE_JSON, new Gson().toJson(mSendBean));
        if (heads != null) {
            call = restService.postBody(heads, url, mRequestBody);
        } else {
            call = restService.postBody(url, mRequestBody);
        }
        call.enqueue(myCallBack);
    }

    /**
     * 发送get请求
     *
     * @param heads
     * @param url
     * @param params
     * @param myCallBack
     */
    public static void sendGet(WeakHashMap<String, String> heads, String url, WeakHashMap<String, Object> params, MyRetrofitCallBack myCallBack) {
        RestService restService = MyRestService.getRestService();
        if (heads != null) {
            if (params != null) {
                call = restService.get(heads, url, params);
            } else {
                call = restService.get(heads, url);
            }
        } else {
            if (params != null) {
                call = restService.get(url, params);
            } else {
                call = restService.get(url);
            }
        }
        call.enqueue(myCallBack);
    }


    /**
     * 文件下载
     *
     * @param params                    参数
     * @param url                       下载地址
     * @param downloadFileName          带有后缀名的文件名称  这个和文件后缀名只用一个就好了
     * @param downloadFileExtensionName 文件后缀名
     * @param downloadFilePath          下载到哪里
     */
    public static void download(WeakHashMap<String, Object> params, String url, final String downloadFileName,
                                final String downloadFileExtensionName, final String downloadFilePath, final Context mContext,
                                final MyRetrofitDownCallBack myRetrofitDownCallBack) {

        RestService restService = MyRestService.getRestService();

        Call<ResponseBody> download;
        if (params != null) {
            download = restService.download(url, params);
        } else {
            download = restService.download(url);
        }
        download.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    SaveFileAsyncTask saveFileAsyncTask = new SaveFileAsyncTask(mContext);
                    ResponseBody responseBody = response.body();
                    saveFileAsyncTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, downloadFileName, downloadFilePath, downloadFileExtensionName, responseBody);
//                saveFileAsyncTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, downloadFileName, downloadFilePath, downloadFileExtensionName, responseBody);
                    //这里去检查Task是不是已经关闭了，一定要判断，要不然可能会导致文件下载的不全
                    if (saveFileAsyncTask.isCancelled()) {
                        saveFileAsyncTask.setDownCallBack(myRetrofitDownCallBack);
                        LogUtils.Loge("###", "下载完成好了好了");
                    }
                } else {
                    LogUtils.Loge("###", "下载完成没成功");
                    myRetrofitDownCallBack.downLoadFaile(response.code(), response.message());
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                LogUtils.Loge("###", "下载完成失败失败");
                myRetrofitDownCallBack.downLoadFaile(HttpUtils.Local_FINAL, t.toString());
            }
        });
    }


    private static WeakHashMap<String, MultipartBody.Part> getPartWeakHashMap(ArrayList<File> fileArrayList) {
        WeakHashMap<String, MultipartBody.Part> partWeakHashMap = new WeakHashMap<>();
        int fileSize = fileArrayList.size();
        for (int i = 0; i < fileSize; i++) {
            final RequestBody requestBody =
                    RequestBody.create(MediaType.parse(MultipartBody.FORM.toString()), fileArrayList.get(i));
            final MultipartBody.Part body =
                    MultipartBody.Part.createFormData("file", fileArrayList.get(i).getName(), requestBody);
            partWeakHashMap.put(fileArrayList.get(i).getName(), body);
        }
        return partWeakHashMap;
    }
}
