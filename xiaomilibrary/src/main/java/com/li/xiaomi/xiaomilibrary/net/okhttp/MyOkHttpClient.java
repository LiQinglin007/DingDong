package com.li.xiaomi.xiaomilibrary.net.okhttp;

import com.li.xiaomi.xiaomilibrary.config.AppConfigInIt;
import com.li.xiaomi.xiaomilibrary.config.AppConfigType;

import java.security.SecureRandom;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;

/**
 * 作者：dell or Xiaomi Li
 * 时间： 2018/3/31
 * 内容：okhttpClient
 * 最后修改：
 */

public final class MyOkHttpClient {

    public static OkHttpClient getOkHttpClient() {
        return OkHttpHolder.OkHttp_CLIENT;
    }

    private static final class OkHttpHolder {
        private final static OkHttpClient.Builder mBuilder = new OkHttpClient.Builder();
        private static final ArrayList<Interceptor> INTERCEPTORS = AppConfigInIt.getConfiguration(AppConfigType.INTERCEPTOR);

        private final static long CONNECT_TIME_OUT = 20;
        private final static long WRITE_TIME_OUT = 20;
        private final static long READ_TIME_OUT = 20;

        //给okhttp添加拦截器
        private static final OkHttpClient.Builder addInterceptors() {
            if (INTERCEPTORS != null && !INTERCEPTORS.isEmpty()) {
                for (Interceptor interceptor : INTERCEPTORS) {
                    mBuilder.addInterceptor(interceptor);
                }
            }
            return mBuilder;
        }

        private static final OkHttpClient OkHttp_CLIENT = addInterceptors()
                .connectTimeout(CONNECT_TIME_OUT, TimeUnit.SECONDS)//链接超时
                .readTimeout(READ_TIME_OUT, TimeUnit.SECONDS)//读取超时
                .writeTimeout(WRITE_TIME_OUT, TimeUnit.SECONDS)//写入超时
                .sslSocketFactory(createSSLSocketFactory())//设置忽略证书 兼容https
                .hostnameVerifier(new HostnameVerifier() {
                    @Override
                    public boolean verify(String hostname, SSLSession session) {
                        return true;
                    }
                })
                .build();
    }

    private static SSLSocketFactory createSSLSocketFactory() {
        SSLSocketFactory ssfFactory = null;
        try {
            SSLContext sc = SSLContext.getInstance("TLS");
            sc.init(null, new TrustManager[]{new TrustAllCerts()}, new SecureRandom());

            ssfFactory = sc.getSocketFactory();
        } catch (Exception e) {
        }

        return ssfFactory;
    }


    public static class TrustAllCerts implements X509TrustManager {
        @Override
        public void checkClientTrusted(X509Certificate[] chain, String authType) {
        }

        @Override
        public void checkServerTrusted(X509Certificate[] chain, String authType) {
        }

        @Override
        public X509Certificate[] getAcceptedIssuers() {
            return new X509Certificate[0];
        }
    }
}
