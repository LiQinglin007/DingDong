package com.li.xiaomi.dingdong.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;


import com.li.xiaomi.dingdong.R;
import com.li.xiaomi.dingdong.adapter.NoticeAdapter;
import com.li.xiaomi.dingdong.db.NoticeManager;
import com.li.xiaomi.dingdong.utils.FinalData;
import com.li.xiaomi.xiaomilibrary.base.BaseActivity;
import com.li.xiaomi.xiaomilibrary.base.BasePresenter;
import com.li.xiaomi.xiaomilibrary.bean.NoticeBean;
import com.li.xiaomi.xiaomilibrary.utils.PreferenceUtils;
import com.li.xiaomi.xiaomilibrary.utils.timer.BaseTimerTask;
import com.li.xiaomi.xiaomilibrary.utils.timer.ITimerListener;

import java.util.Calendar;
import java.util.Date;
import java.util.ArrayList;
import java.util.Timer;

/**
 * 作者：dell or Xiaomi Li
 * 时间： 2018/4/25
 * 内容：启动页
 * 最后修改：
 */

public class MainActivity extends BaseActivity implements ITimerListener {

    private Timer mTimer = null;
    int mCount = 2;//倒计时时间
    RecyclerView mRecyclerView;
    NoticeAdapter mNoticeAdapter;
    Toolbar mToolbar;

    @Override
    protected Object setLayout() {
        return R.layout.activity_main;
    }

    @Override
    protected boolean translucentStatusBar() {
        return false;
    }

    @Override
    protected BasePresenter createPersenter() {
        return null;
    }

    @Override
    protected void initData() {

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        mToolbar = findViewById(R.id.main_toolbar);
        mToolbar.setTitle("");
        setSupportActionBar(mToolbar);

        mRecyclerView = findViewById(R.id.main_recycler);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        mNoticeAdapter = new NoticeAdapter(R.layout.item_notice);
        mRecyclerView.setAdapter(mNoticeAdapter);
        getData();

        mTimer = new Timer();
        final BaseTimerTask baseTimerTask = new BaseTimerTask(this);
        mTimer.schedule(baseTimerTask, 0, 1000);

        mToolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.menu_main_setting:
                        startActivity(new Intent(MainActivity.this, SettingActivity.class));
                        break;
                    case R.id.menu_main_test:
                        startActivity(new Intent(MainActivity.this, ClockActivity.class));
                        break;
                }
                return true;
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        getData();
    }

    private void getData() {
        ArrayList<NoticeBean> noticeBeans = NoticeManager.LoadAllDay();
        mNoticeAdapter.setNewData(noticeBeans);
    }

    @Override
    public void onTimer() {
        this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mCount--;
                if (mCount <= 0) {
                    if (mTimer != null) {
                        mTimer.cancel();
                        mTimer = null;
                        checkIsShowScroll();
                    }
                }
            }
        });
    }


    //判断是否显示滑动启动页
    private void checkIsShowScroll() {
        boolean aBoolean = PreferenceUtils.getBoolean(FinalData.IS_SETTING, false);
        if (!aBoolean) {
            startActivity(new Intent(MainActivity.this, SettingActivity.class));
        } else {
            ArrayList<NoticeBean> noticeBeans = NoticeManager.LoadAllThisDay();
            int size = noticeBeans.size();
            if (size != 0) {
                if (!noticeBeans.get(0).getResult()) {//如果今天的还没打卡，就去打卡页面
                    long time = new Date().getTime();
                    //今天0点
                    Calendar cal = Calendar.getInstance();
                    cal.setTimeInMillis(System.currentTimeMillis());
                    cal.set(Calendar.HOUR_OF_DAY, PreferenceUtils.getInt(FinalData.WORK_HOUR, 0));
                    cal.set(Calendar.MINUTE, PreferenceUtils.getInt(FinalData.WORK_MINE, 0));
                    cal.set(Calendar.SECOND, 0);
                    long timeInMillis = cal.getTimeInMillis();

                    if (time > noticeBeans.get(0).getNoticeTitle() || time < timeInMillis) {//并且现在的时间已经超过了设定的时间，并且还没到上班时间
                        Intent intent = new Intent(MainActivity.this, ClockActivity.class);
                        intent.putExtra("timeId", noticeBeans.get(0).getNoticeTitle());
                        startActivity(intent);
                    }
                }
            }
        }
    }
}
