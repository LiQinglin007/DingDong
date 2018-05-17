package com.li.xiaomi.dingdong.ui;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.TextView;

import com.li.xiaomi.dingdong.R;
import com.li.xiaomi.dingdong.bean.AppDataBean;
import com.li.xiaomi.dingdong.db.NoticeManager;
import com.li.xiaomi.dingdong.utils.FinalData;

import com.li.xiaomi.xiaomilibrary.base.BaseActivity;
import com.li.xiaomi.xiaomilibrary.base.BasePresenter;
import com.li.xiaomi.xiaomilibrary.bean.NoticeBean;
import com.li.xiaomi.xiaomilibrary.utils.LogUtils;
import com.li.xiaomi.xiaomilibrary.utils.NetWorkUtils;
import com.li.xiaomi.xiaomilibrary.utils.PreferenceUtils;
import com.li.xiaomi.xiaomilibrary.utils.timer.BaseTimerTask;
import com.li.xiaomi.xiaomilibrary.utils.timer.ITimerListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Timer;

/**
 * 作者：dell or Xiaomi Li
 * 时间： 2018/5/16
 * 内容：
 * 最后修改：
 */
public class ClockActivity extends BaseActivity implements ITimerListener {

    TextView mTextView;

    ArrayList<AppDataBean> mAppDataBeans = new ArrayList<>();
    ArrayList<String> mAppPackageName = new ArrayList<>();
    String DingDingPackageName = "com.alibaba.android.rimet";
    Timer mTimer;
    boolean Result = false;//打卡成功了么
    long workTime = 0;//当天上班时间的时间戳
    long startUpTime = 0;//每天启动App的时间戳

    long timeId = 0;

    @Override
    protected Object setLayout() {
        return R.layout.activity_clock;
    }

    @Override
    protected void initData() {
        timeId = getIntent().getLongExtra("timeId", -1);
        scanLocalInstallAppList(ClockActivity.this.getPackageManager());
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        mTextView = findViewById(R.id.result_tv);
        if (NetWorkUtils.isNetworkConnected(ClockActivity.this)) {
            //如果有网络
            doSomething();
        } else {//启动一个定时器，每隔30s去检查一下网络
            startUpTime = new Date().getTime();
            int anInt = PreferenceUtils.getInt(FinalData.WORK_HOUR, 8);
            int anInt1 = PreferenceUtils.getInt(FinalData.WORK_MINE, 30);
            //今天0点
            Calendar cal = Calendar.getInstance();
            cal.setTimeInMillis(System.currentTimeMillis());
            cal.set(Calendar.HOUR_OF_DAY, anInt);
            cal.set(Calendar.MINUTE, anInt1);
            cal.set(Calendar.SECOND, 0);
            workTime = cal.getTimeInMillis();
            mTimer = new Timer();
            final BaseTimerTask baseTimerTask = new BaseTimerTask(ClockActivity.this);
            mTimer.schedule(baseTimerTask, 0, 30000);
        }
    }

    @Override
    protected BasePresenter createPersenter() {
        return null;
    }

    @Override
    protected boolean translucentStatusBar() {
        return false;
    }

    int mInt = 0;

    /**
     * 到时间了去做点什么
     */
    @Override
    public void onTimer() {
        this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mInt++;
                startUpTime = startUpTime + 30000;
                if (NetWorkUtils.isNetworkConnected(ClockActivity.this)) {
                    //如果有网络
                    doSomething();
                } else {//如果没有网络，并且距离上班还有五分钟,去发一个通知
                    LogUtils.Loge(ClockActivity.class.getSimpleName(), "workTime:" + workTime);
                    LogUtils.Loge(ClockActivity.class.getSimpleName(), "startUpTime:" + startUpTime);
                    Long aLong = PreferenceUtils.getLong(FinalData.TIME_MINE, 300000);
                    if (workTime - startUpTime < aLong) {
                        showNotifictionIcon("将到上班时间,打卡失败,请开启网络手动打卡" + mInt);
                        mTextView.setText("将到上班时间\n打卡失败,请开启网络手动打卡" + mInt);
                        closeTimer();
                    } else {
                        mTextView.setText("暂无网络\n正在自动检测(" + mInt + "次)\n有网络后会自动打卡");
                    }
                }
            }
        });
    }

    /**
     * 去跳转到钉钉
     */
    private void doSomething() {
        if (mAppPackageName.contains(DingDingPackageName)) {
            PackageManager packageManager = ClockActivity.this.getPackageManager();  // 当前Activity获得packageManager对象
            try {
                Intent intent = new Intent();
                intent = packageManager.getLaunchIntentForPackage(DingDingPackageName);
                Result = true;
                startActivity(intent);
                if (timeId != -1) {
                    NoticeBean mNoticeBean = new NoticeBean(timeId, new Date().getTime(), "启动钉钉成功", true);
                    NoticeManager.update(mNoticeBean, timeId);
                }
                mTextView.setText("打卡成功");
                closeTimer();
                Result = false;
            } catch (Exception e) {
                Log.e(ClockActivity.class.getSimpleName(), "exception:" + e.toString());
            }
        } else {
            closeTimer();
            showNotifictionIcon("打卡失败，请先安装钉钉");
            mTextView.setText("打卡失败，请先安装钉钉");
        }
    }

    /**
     * 打卡失败了，发个通知
     */
    public void showNotifictionIcon(String content) {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(ClockActivity.this);
        Intent intent = new Intent();//将要跳转的界面
        intent.putExtra("notificationId", "我是通知id");
        //Intent intent = new Intent();//只显示通知，无页面跳转
        builder.setAutoCancel(true);//点击后消失
        builder.setSmallIcon(R.mipmap.ic_launcher);//设置通知栏消息标题的头像
        builder.setDefaults(NotificationCompat.DEFAULT_SOUND);//设置通知铃声
        builder.setTicker(content);
        builder.setContentTitle("钉钉打卡");
        builder.setContentText(content);
        //利用PendingIntent来包装我们的intent对象,使其延迟跳转
//        FLAG_ONE_SHOT   表示返回的PendingIntent仅能执行一次，执行完后自动取消
//        FLAG_NO_CREATE     表示如果描述的PendingIntent不存在，并不创建相应的PendingIntent，而是返回NULL
//        FLAG_CANCEL_CURRENT      表示相应的PendingIntent已经存在，则取消前者，然后创建新的PendingIntent，这个有利于数据保持为最新的，可以用于即时通信的通信场景
//        FLAG_UPDATE_CURRENT     表示更新的PendingIntent
        PendingIntent intentPend = PendingIntent.getActivity(ClockActivity.this, 0, intent, PendingIntent.FLAG_ONE_SHOT);
        builder.setContentIntent(intentPend);
        NotificationManager manager = (NotificationManager) ClockActivity.this.getSystemService(ClockActivity.this.NOTIFICATION_SERVICE);
        manager.notify(0, builder.build());
    }

    /**
     * 获取应用列表
     *
     * @param packageManager
     */
    public void scanLocalInstallAppList(PackageManager packageManager) {
        mAppDataBeans.clear();
        mAppPackageName.clear();
        try {
            List<PackageInfo> packageInfos = packageManager.getInstalledPackages(0);
            int size = packageInfos.size();
            for (int i = 0; i < size; i++) {
                PackageInfo packageInfo = packageInfos.get(i);
                //过滤掉系统app
                if ((ApplicationInfo.FLAG_SYSTEM & packageInfo.applicationInfo.flags) != 0) {
                    continue;
                }
                AppDataBean myAppInfo = new AppDataBean();
                myAppInfo.setPackageName(packageInfo.packageName);
                if (packageInfo.applicationInfo.loadIcon(packageManager) == null) {
                    continue;
                }
                myAppInfo.setAppIcon(packageInfo.applicationInfo.loadIcon(packageManager));
                myAppInfo.setAppName(packageInfo.applicationInfo.loadLabel(getPackageManager()).toString());//应用名称
                mAppDataBeans.add(myAppInfo);
                mAppPackageName.add(packageInfo.packageName);
            }
        } catch (Exception e) {
            Log.e(ClockActivity.class.getSimpleName(), "===============获取应用包信息失败" + e.toString());
        }
    }

    /**
     * 关闭计时器
     */
    private void closeTimer() {
        if (mTimer != null) {
            mTimer.cancel();
            mTimer = null;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        closeTimer();
    }
}
