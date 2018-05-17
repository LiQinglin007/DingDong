package com.li.xiaomi.xiaomilibrary.net.retrofit;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.support.v4.content.FileProvider;


import com.li.xiaomi.xiaomilibrary.utils.CheckStringEmptyUtils;
import com.li.xiaomi.xiaomilibrary.utils.FileUtil;
import com.li.xiaomi.xiaomilibrary.utils.LogUtils;

import java.io.File;

import okhttp3.ResponseBody;

/**
 * 作者：dell or Xiaomi Li
 * 时间： 2018/3/22
 * 内容：保存文件
 * 最后修改：
 */

public class SaveFileAsyncTask extends AsyncTask<Object, Void, File> {
    MyRetrofitDownCallBack mMyRetrofitDownCallBack;
    final Context mContext;

    public SaveFileAsyncTask(Context context) {
        mContext = context;
    }

    public void setDownCallBack(MyRetrofitDownCallBack myRetrofitDownCallBack) {
        mMyRetrofitDownCallBack = myRetrofitDownCallBack;
    }


    @Override
    protected File doInBackground(Object... objects) {
        String downloadFileName = (String) objects[0];//文件名
        String downloadFilePath = (String) objects[1];//下载地址
        String downloadFileExtensionName = (String) objects[2];//扩展名
        final ResponseBody mResponseBody = (ResponseBody) objects[3];

        if (CheckStringEmptyUtils.IsEmpty(downloadFilePath)) {
            downloadFilePath = "down_loads";
        }
        if (CheckStringEmptyUtils.IsEmpty(downloadFileExtensionName)) {
            downloadFileExtensionName = "";
        }

        if (!CheckStringEmptyUtils.IsEmpty(downloadFileName)) {//文件名不是空的，就用文件名来构建一个文件
            return FileUtil.writeToDisk(mResponseBody.byteStream(), downloadFilePath, downloadFileName);
        } else {
            //输入流、下载地址、文件的扩展名转换成大写、扩展名
            return FileUtil.writeToDisk(mResponseBody.byteStream(), downloadFilePath, downloadFileExtensionName.toUpperCase(), downloadFileExtensionName);
        }
    }

    @Override
    protected void onPostExecute(File file) {
        super.onPostExecute(file);
        if (mMyRetrofitDownCallBack != null) {
            mMyRetrofitDownCallBack.dowmLoadSuccess(file);
            mMyRetrofitDownCallBack.downLoadFinish();
            LogUtils.Loge("###", "下载完成回调回调回调");
        }
        autoInstallApk(file, mContext);
    }


    /**
     * 检查是不是app，然后去安装
     */
    private void autoInstallApk(File file, Context mContext) {
        if (FileUtil.getExtension(file.getPath()).equals("apk")) {
            final Intent mIntent = new Intent();
            Uri uri;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                mIntent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                uri = FileProvider.getUriForFile(mContext, mContext.getPackageName() + ".provider", file);
            } else {
                uri = Uri.fromFile(file);
                mIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            }
            mIntent.setDataAndType(uri, "application/vnd.android.package-archive");
            mContext.startActivity(mIntent);
        }
    }
}
