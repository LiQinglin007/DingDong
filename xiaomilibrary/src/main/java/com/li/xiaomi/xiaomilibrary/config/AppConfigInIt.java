package com.li.xiaomi.xiaomilibrary.config;

import android.content.Context;

import com.li.xiaomi.xiaomilibrary.utils.CheckStringEmptyUtils;


/**
 * 作者：dell or Xiaomi Li
 * 时间： 2018/3/31
 * 内容：
 * 最后修改：
 */

public final class AppConfigInIt {

    /**
     * 初始化配置
     *
     * @param mContext
     * @return
     */
    public static AppConfig init(Context mContext) {
        AppConfig.getInstance().
                getLatteConfigs().
                put(AppConfigType.APPLICATIO_CONTEXT, mContext.getApplicationContext());
        return AppConfig.getInstance();
    }

    public static <T> T getConfiguration(Object key) {
        return AppConfig.getInstance().getConfiguration(key);
    }


    public static Context getApplicationContext() {
        return getConfiguration(AppConfigType.APPLICATIO_CONTEXT);
    }
}
