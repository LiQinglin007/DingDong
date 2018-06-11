package com.li.xiaomi.dingdong.utils.TimingUtils;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.li.xiaomi.dingdong.ui.ClockActivity;


/**
 * Created by nomasp on 2015/10/07.
 */
public class AlarmReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Log.e("###", "闹钟执行了！");
        //重复闹钟
        TimingUtils.setAlarmTime(context, intent);

        AlarmManager am = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        am.cancel(PendingIntent.getBroadcast(context, getResultCode(),
                new Intent(context, AlarmReceiver.class), 0));
        Intent i = new Intent(context, ClockActivity.class);
        i.putExtra("timeId", intent.getLongExtra("timeId", 0));
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(i);
    }
}
