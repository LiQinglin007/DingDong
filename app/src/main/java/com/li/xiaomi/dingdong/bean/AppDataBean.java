package com.li.xiaomi.dingdong.bean;

import android.graphics.drawable.Drawable;

/**
 * 作者：dell or Xiaomi Li
 * 时间： 2018/5/16
 * 内容：
 * 最后修改：
 */
public class AppDataBean {
    private String PackageName;//包名称
    private Drawable AppIcon;//图标
    private String AppName;//应用名称

    public String getPackageName() {
        return PackageName;
    }

    public void setPackageName(String packageName) {
        PackageName = packageName;
    }

    public Drawable getAppIcon() {
        return AppIcon;
    }

    public void setAppIcon(Drawable appIcon) {
        AppIcon = appIcon;
    }

    public String getAppName() {
        return AppName;
    }

    public void setAppName(String appName) {
        AppName = appName;
    }


    @Override
    public String toString() {
        return "AppDataBean{" +
                "PackageName='" + PackageName + '\'' +
                ", AppIcon=" + AppIcon +
                ", AppName='" + AppName + '\'' +
                '}';
    }
}
