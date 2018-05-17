package com.li.xiaomi.xiaomilibrary.ui.dialog;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.provider.Settings;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;

/**
 * 作者：dell or Xiaomi Li
 * 时间： 2018/1/17
 * 内容：提醒用户开启权限弹框
 * 最后修改：
 */

public class MiPreMissionDialog {

    /**
     * 提示用户前往设置页面的  dialog
     *
     * @param mActivity
     * @param message  提示内容，只传进来关键字就好
     * @param code  去设置页面的code
     */
    public static void showPermissionDialog(final Activity mActivity, String message, final int code) {
        new MiDialog(mActivity, MiDialog.MessageType).
                builder().
                setTitle("权限提醒").
                setMsg("请在权限管理中允许" + message + "权限").
                setOkButton("去设置", new MiDialog.DialogCallBack() {
                    @Override
                    public void DialogCallBack(String connect) {
                        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                        Uri uri = Uri.fromParts("package", mActivity.getPackageName(), null);
                        intent.setData(uri);
                        mActivity.startActivityForResult(intent, code);
                    }
                }).
                setCannleButton("取消", null)
                .show();
    }


    /**
     * 提示用户前往设置页面的  dialog
     *
     * @param mFragment
     * @param message  提示内容，只传进来关键字就好
     * @param code  去设置页面的code
     */
    public static void showPermissionDialog(final Fragment mFragment, String message, final int code) {
        new MiDialog(mFragment.getContext(), MiDialog.MessageType).
                builder().
                setTitle("权限提醒").
                setMsg("请在权限管理中允许" + message + "权限").
                setOkButton("去设置", new MiDialog.DialogCallBack() {
                    @Override
                    public void DialogCallBack(String connect) {
                        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                        Uri uri = Uri.fromParts("package", mFragment.getContext().getPackageName(), null);
                        intent.setData(uri);
                        mFragment.startActivityForResult(intent, code);
                    }
                }).
                setCannleButton("取消", null)
                .show();
    }
}
