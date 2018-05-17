package com.li.xiaomi.xiaomilibrary.net.retrofit;

import java.io.File;

/**
 * 作者：dell or Xiaomi Li
 * 时间： 2018/4/3
 * 内容：
 * 最后修改：
 */

public interface MyRetrofitDownCallBack {
    void dowmLoadSuccess(File mFile);

    void downLoadFinish();

    void downLoadFaile(int code, String msg);
}
