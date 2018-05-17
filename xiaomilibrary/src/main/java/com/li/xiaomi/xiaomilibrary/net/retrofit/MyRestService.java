package com.li.xiaomi.xiaomilibrary.net.retrofit;

import com.li.xiaomi.xiaomilibrary.config.AppConfigInIt;
import com.li.xiaomi.xiaomilibrary.config.AppConfigType;
import com.li.xiaomi.xiaomilibrary.net.okhttp.MyOkHttpClient;

import retrofit2.Retrofit;
import retrofit2.converter.scalars.ScalarsConverterFactory;

/**
 * 作者：dell or Xiaomi Li
 * 时间： 2018/4/2
 * 内容：RetrofitClient
 * 最后修改：
 */

public final class MyRestService {

    public static RestService getRestService() {
        return RestServiceHolder.REST_SERVICE;
    }

    private static final class RestServiceHolder {
        private static final RestService REST_SERVICE =
                RetrofitHolder.Retrofit_CLIENT.create(RestService.class);
    }

    private static final class RetrofitHolder {
        private static final String BASE_URL = AppConfigInIt.getConfiguration(AppConfigType.HOST_API);
        private static final Retrofit Retrofit_CLIENT = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(MyOkHttpClient.getOkHttpClient())
                .addConverterFactory(ScalarsConverterFactory.create())
                .build();
    }

}
