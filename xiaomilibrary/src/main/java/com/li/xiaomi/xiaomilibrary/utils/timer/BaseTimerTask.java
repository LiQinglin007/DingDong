package com.li.xiaomi.xiaomilibrary.utils.timer;

import java.util.TimerTask;

/**
 * 作者：dell or Xiaomi Li
 * 时间： 2018/3/23
 * 内容：
 * 最后修改：
 */

public class BaseTimerTask extends TimerTask {

    private ITimerListener mITimerListener;

    public BaseTimerTask(ITimerListener ITimerListener) {
        mITimerListener = ITimerListener;
    }

    @Override
    public void run() {
        if (mITimerListener != null) {
            mITimerListener.onTimer();
        }
    }
}
