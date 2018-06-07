package com.li.xiaomi.dingdong.utils.TimingUtils;

import android.annotation.TargetApi;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.icu.util.Calendar;
import android.icu.util.TimeZone;
import android.os.Build;

import com.li.xiaomi.dingdong.db.NoticeBean;
import com.li.xiaomi.dingdong.db.NoticeManager;
import com.li.xiaomi.xiaomilibrary.utils.LogUtils;
import com.li.xiaomi.xiaomilibrary.utils.T;

import static android.icu.util.Calendar.getInstance;


/**
 * Created by Admin on 2017/7/20.
 */

public class TimingUtils {
    private final static String Tag = TimingUtils.class.getSimpleName();

    @TargetApi(Build.VERSION_CODES.N)
    public static void setAlarmTime(Context context, Intent intent) {

        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        int clockId = intent.getIntExtra("ClockId", 0);
        int week = intent.getIntExtra("week", 0);
        int HourClock = intent.getIntExtra("hourClock", 0);
        int MinClock = intent.getIntExtra("minClock", 0);
        PendingIntent sender = PendingIntent.getBroadcast(context, clockId, intent, PendingIntent.FLAG_CANCEL_CURRENT);
        Calendar calendarClock = getInstance();
        calendarClock.setTimeZone(TimeZone.getTimeZone("GMT+8"));
        calendarClock.set(calendarClock.get(Calendar.YEAR), calendarClock.get(Calendar.MONTH), calendarClock.get
                (Calendar.DAY_OF_MONTH), HourClock, MinClock, 0);
        long clockTime = calMethod(week, calendarClock.getTimeInMillis());

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            LogUtils.Loge(Tag, "开启下次定时，时间(API>=19)：" + clockTime);
            alarmManager.setExact(AlarmManager.RTC_WAKEUP, clockTime, sender);
            NoticeBean mNoticeBean = new NoticeBean(null, clockTime, "未启动", false);
            NoticeManager.add(mNoticeBean);
        } else {
            LogUtils.Loge(Tag, "开启下次定时，时间(API<19)：" + clockTime);
            alarmManager.set(AlarmManager.RTC_WAKEUP, clockTime, sender);
            NoticeBean mNoticeBean = new NoticeBean(null, clockTime, "未启动", false);
            NoticeManager.add(mNoticeBean);
        }
    }

    /**
     * 周期闹钟
     *
     * @param mContext  //     * @param flag     0：一次性闹钟  1：每周重复一次
     * @param HourClock 闹钟时间
     * @param MinClock
     * @param week      周几   1-7（周一到周日）
     * @param ClockId   闹钟id
     * @param msg       提示信息
     */

    @TargetApi(Build.VERSION_CODES.N)
    public static void setClock(Context mContext, int HourClock, int MinClock, int week, int ClockId, String msg) {
        Calendar calendar = getInstance();
        calendar.setTimeZone(TimeZone.getTimeZone("GMT+8"));
        calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get
                (Calendar.DAY_OF_MONTH), HourClock, MinClock, 0);
        long timeClock = calMethod(week, calendar.getTimeInMillis());

        AlarmManager alarmManager = (AlarmManager) mContext.getSystemService(Context.ALARM_SERVICE);

        Intent mIntent = new Intent(mContext, AlarmReceiver.class);
        mIntent.putExtra("ClockId", ClockId);
        mIntent.putExtra("week", week);
        mIntent.putExtra("hourClock", HourClock);
        mIntent.putExtra("minClock", MinClock);
        mIntent.putExtra("timeId", timeClock);
        PendingIntent broadcast = PendingIntent.getBroadcast(mContext, ClockId, mIntent, PendingIntent
                .FLAG_CANCEL_CURRENT);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            LogUtils.Loge(Tag, "开启定时，时间(API>=19)：" + timeClock);
            alarmManager.setExact(AlarmManager.RTC_WAKEUP, timeClock, broadcast);
            NoticeBean mNoticeBean = new NoticeBean(null, timeClock, "未启动", false);
            NoticeManager.add(mNoticeBean);
        } else {
            LogUtils.Loge(Tag, "开启定时，时间(API>=19)：" + timeClock);
            alarmManager.set(AlarmManager.RTC_WAKEUP, timeClock, broadcast);
            NoticeBean mNoticeBean = new NoticeBean(null, timeClock, "未启动", false);
            NoticeManager.add(mNoticeBean);
        }
        T.shortToast(mContext, "设置成功");
    }

    /**
     * 关闭某个闹钟
     *
     * @param mContext
     * @param id
     */
    public static void cancelAlarm(Context mContext, int id) {
        Intent intent = new Intent(mContext, AlarmReceiver.class);
        PendingIntent pi = PendingIntent.getBroadcast(mContext, id, intent, PendingIntent
                .FLAG_CANCEL_CURRENT);
        AlarmManager am = (AlarmManager) mContext.getSystemService(Context.ALARM_SERVICE);
        am.cancel(pi);
    }


    /**
     * @param weekflag 传入的是周几
     * @param dateTime 传入的是时间戳（设置当天的年月日+从选择框拿来的时分秒）
     * @return 返回起始闹钟时间的时间戳
     */
    @TargetApi(Build.VERSION_CODES.N)
    private static long calMethod(int weekflag, long dateTime) {
        long time = 0;
        //weekflag == 0表示是按天为周期性的时间间隔或者是一次行的，weekfalg非0时表示每周几的闹钟并以周为时间间隔
        if (weekflag != 0) {
            Calendar c = getInstance();
            int week = c.get(Calendar.DAY_OF_WEEK);
            if (1 == week) {
                week = 7;
            } else if (2 == week) {
                week = 1;
            } else if (3 == week) {
                week = 2;
            } else if (4 == week) {
                week = 3;
            } else if (5 == week) {
                week = 4;
            } else if (6 == week) {
                week = 5;
            } else if (7 == week) {
                week = 6;
            }

            if (weekflag == week) {
                if (dateTime > System.currentTimeMillis()) {
                    time = dateTime;
                } else {
                    time = dateTime + 7 * 24 * 3600 * 1000;
                }
            } else if (weekflag > week) {
                time = dateTime + (weekflag - week) * 24 * 3600 * 1000;
            } else if (weekflag < week) {
                time = dateTime + (weekflag - week + 7) * 24 * 3600 * 1000;
            }
        } else {
            if (dateTime > System.currentTimeMillis()) {
                time = dateTime;
            } else {
                time = dateTime + 24 * 3600 * 1000;
            }
        }
        return time;
    }
}
