package com.li.xiaomi.xiaomilibrary.net;

import okhttp3.MediaType;

/**
 * 作者：dell or Xiaomi Li
 * 时间： 2018/4/1
 * 内容：
 * 最后修改：
 */

public class HttpUtils {
    //服务器code
    public final static int SERVICE_TOKEN_INVALID = -1;//token失效
    public final static int SERVICE_SUCCESS = 200;//code成功
    public final static int SERVICE_FINAL = -100;//code失败

    //本地code
    public final static int Local_SUCCESS = 0x1;//请求成功
    public final static int Local_FINAL = 0x2;//请求失败
    public final static int Local_PARSING_ERROR = 0x3;//解析错误
    public final static int Local_DATA_ERROR = 0x4;//后台数据错误（包含标签语言认为是后台数据错误）

    //创建jsonType
    public final static MediaType MEDIA_TYPE_JSON = MediaType.parse("application/json; charset=utf-8");
    //参数类型
    public final static MediaType MEDIA_TYPE_PNG = MediaType.parse("application/octet-stream");

}
