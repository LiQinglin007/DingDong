package com.li.xiaomi.dingdong.application;

import android.app.Activity;
import android.app.Application;

import com.li.xiaomi.dingdong.R;
import com.li.xiaomi.xiaomilibrary.config.AppConfigInIt;

/**
 * 作者：dell or Xiaomi Li
 * 时间： 2018/5/16
 * 内容：
 * 最后修改：
 */
public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        AppConfigInIt.init(this).
                withSharedPreferences(getSharedPreferences(getResources().getString(R.string.shared_preferences_name), Activity.MODE_PRIVATE)).
                withBaseUrl("").
                withDBGreenDao(getResources().getString(R.string.db_name)).
                configure();
        AppConfigInIt.initDB();
    }
}
