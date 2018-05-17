package com.li.xiaomi.xiaomilibrary.utils;

import android.os.Environment;

/**
 * Created by LQL on 2016/10/11.
 */

public class AppConstant {

    //sharePrefrence的文件名称
    public static final String PREFRENCE_NAME = "TestDemoLQL";
    public static final String DATABASE_NAME = "TestDemoLQL.db";

    // 外部存储设备的根路径
    private static final String ExternalStorageRootPath = EnvironmentStateUtils.getExternalStorageDirectory().getPath();

    private static final String BasePath = ExternalStorageRootPath + "/smarthome/";
    // 文件存放路径
    public static final String FILE_CACHE_PATH = BasePath + "filecache/";
    //缓存的图片
    public static final String IMAGE_CACHE_PATH = BasePath + "imagecache/";

    public static final String VIDEO_SAVE_PATH = BasePath + "video/";
    // 保存图片
    public static final String IMAGE_SAVE_PATH = BasePath + "photos/";

    // 下载存储地址
    public static final String DOWNLOAD_PATH = BasePath + "download/";


    private static final String SDCARD = Environment.getExternalStorageDirectory().getAbsolutePath();
    //图片拍照、裁剪和压缩后保存的本地路径
    public static final String IMAGE_PATH = SDCARD + "/YAO_BA/IMAGE";
    //图片缓存路径
    public static final String CACHE_DIR = SDCARD + "/YAO_BA/cache/";

}
