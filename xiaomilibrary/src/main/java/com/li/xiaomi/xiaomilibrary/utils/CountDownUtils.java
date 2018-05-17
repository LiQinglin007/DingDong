package com.li.xiaomi.xiaomilibrary.utils;

import android.os.CountDownTimer;
import android.widget.TextView;

/**
 * 短信验证码倒计时
 */

public class CountDownUtils {
    public static int time = 60;//剩余时间
    public static void startTime(final TextView codetv) {
        time = 60;
        new CountDownTimer(60 * 1000, 1000) {
            public void onTick(long millisUntilFinished) {
                String timeStr=time>10?time+"":"0"+time;
                codetv.setText("剩余" + timeStr + "秒");
                time--;
                codetv.setClickable(false);
            }

            public void onFinish() {
                codetv.setText("获取验证码");
                codetv.setClickable(true);
            }
        }.start();
    }
}
