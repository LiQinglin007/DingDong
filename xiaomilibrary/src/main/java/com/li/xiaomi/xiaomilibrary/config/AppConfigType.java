package com.li.xiaomi.xiaomilibrary.config;

/**
 * 作者：dell or Xiaomi Li
 * 时间： 2018/3/31
 * 内容：
 * 最后修改：
 */

public enum AppConfigType {
    SHARED_PREFERENCES,//sharedPreference文件名
    DBNAME,//数据库名称
    APPLICATIO_CONTEXT,//上下文
    CONFIGREADY,//是否初始化完成的标志位
    INTERCEPTOR,//okhttp的拦截器
    HOST_API//使用Retrofit2请求框架的时候，配置的BaseUrl
}
