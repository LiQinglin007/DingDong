package com.li.xiaomi.xiaomilibrary.config;

import android.content.Context;
import android.content.SharedPreferences;

import com.li.xiaomi.xiaomilibrary.utils.CheckStringEmptyUtils;
import com.li.xiaomi.xiaomilibrary.utils.greendaoUtils.DBManager;

import java.util.ArrayList;
import java.util.HashMap;

import okhttp3.Interceptor;

/**
 * 作者：dell or Xiaomi Li
 * 时间： 2018/3/31
 * 内容：配置文件
 * 最后修改：
 */

public final class AppConfig {

    public final static HashMap<Object, Object> APP_CONFIGS = new HashMap<>();

    //okhttp的拦截器
    private static final ArrayList<Interceptor> INTERCEPTORS = new ArrayList<>();

    public static AppConfig getInstance() {
        return SingletonHolder.instance;
    }

    private static class SingletonHolder {
        //静态初始化器，由JVM来保证线程安全
        //这里使用final 关键字 ，因为这个只在这里使用，其他的情况也不会变，为了以后不会误操作发生错误
        private static final AppConfig instance = new AppConfig();
    }

    final HashMap<Object, Object> getLatteConfigs() {
        return APP_CONFIGS;
    }

    /**
     * 配置SharedPreferences
     *
     * @param sharedPreferences
     * @return
     */
    public final AppConfig withSharedPreferences(SharedPreferences sharedPreferences) {
        APP_CONFIGS.put(AppConfigType.SHARED_PREFERENCES, sharedPreferences);
        return this;
    }

    /**
     * 配置拦截器
     *
     * @param interceptor
     * @return
     */
    public final AppConfig withInterceptors(Interceptor interceptor) {
        INTERCEPTORS.add(interceptor);
        APP_CONFIGS.put(AppConfigType.INTERCEPTOR, INTERCEPTORS);
        return this;
    }


    public final AppConfig withInterceptors(ArrayList<Interceptor> interceptorList) {
        INTERCEPTORS.addAll(interceptorList);
        APP_CONFIGS.put(AppConfigType.INTERCEPTOR, INTERCEPTORS);
        return this;
    }

    /**
     * 配置数据库
     *
     * @param DBName
     * @return
     */
    public final AppConfig withDBGreenDao(String DBName) {
        APP_CONFIGS.put(AppConfigType.DBNAME, DBName);
        return this;
    }

    /**
     * 配置网络请求主机地址
     *
     * @param baseUrl
     * @return
     */
    public final AppConfig withBaseUrl(String baseUrl) {
        APP_CONFIGS.put(AppConfigType.HOST_API, baseUrl);
        return this;
    }

    /**
     * 结束配置，可以在这里去做初始化数据库等操作
     */
    public final void configure() {
        APP_CONFIGS.put(AppConfigType.CONFIGREADY, true);
    }


    /**
     * 检查初始化状态是否完成
     *
     * @return
     */
    private static final void checkConfiguration() {
        Object o = APP_CONFIGS.get(AppConfigType.CONFIGREADY);
        if (o != null) {
            if (!(boolean) o) {//如果没有完成，但是要着急去做下边的操作，就在这里抛出一个异常
                throw new RuntimeException("Configuration is not ready,call configure");
            }
        } else {
            throw new RuntimeException("Configuration is not ready,call configure");
        }

    }

    /**
     * 获取键对应的值
     *
     * @param key
     * @param <T>
     * @return
     */
    final <T> T getConfiguration(Object key) {
        checkConfiguration();
        return (T) APP_CONFIGS.get(key);
    }
}
