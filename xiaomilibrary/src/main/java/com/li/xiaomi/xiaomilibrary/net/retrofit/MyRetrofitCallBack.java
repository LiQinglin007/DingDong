package com.li.xiaomi.xiaomilibrary.net.retrofit;

import android.os.Handler;
import android.os.Looper;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * 作者：dell or Xiaomi Li
 * 时间： 2018/4/2
 * 内容：
 * 最后修改：
 */

public abstract class MyRetrofitCallBack implements Callback {
    Handler handler = new Handler(Looper.getMainLooper());

    @Override
    public void onResponse(Call call, final Response response) {
        handler.post(new Runnable() {
            @Override
            public void run() {
                onSuccess(response.code(), response.body().toString());
            }
        });
    }

    @Override
    public void onFailure(Call call, final Throwable t) {
        handler.post(new Runnable() {
            @Override
            public void run() {
                onFailure(t);
            }
        });
    }


    public abstract void onSuccess(int code, String response);

    public abstract void onFailure(Throwable e);
}
