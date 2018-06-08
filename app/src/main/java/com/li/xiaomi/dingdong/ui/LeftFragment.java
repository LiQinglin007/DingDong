package com.li.xiaomi.dingdong.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.li.xiaomi.dingdong.R;
import com.li.xiaomi.dingdong.adapter.NoticeAdapter;
import com.li.xiaomi.dingdong.db.NoticeBean;
import com.li.xiaomi.dingdong.db.NoticeManager;
import com.li.xiaomi.dingdong.utils.FinalData;
import com.li.xiaomi.xiaomilibrary.base.BaseFragment;
import com.li.xiaomi.xiaomilibrary.base.BasePresenter;
import com.li.xiaomi.xiaomilibrary.utils.LogUtils;
import com.li.xiaomi.xiaomilibrary.utils.PreferenceUtils;
import com.li.xiaomi.xiaomilibrary.utils.timer.BaseTimerTask;
import com.li.xiaomi.xiaomilibrary.utils.timer.ITimerListener;

import java.util.Date;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Timer;

/**
 * 作者：dell or Xiaomi Li
 * 时间： 2018/6/8
 * 内容：打卡记录
 * 最后修改：
 */
public class LeftFragment extends BaseFragment {

    public static LeftFragment getInstance() {
        return LeftFragmentHolder.leftFragment;
    }

    private static class LeftFragmentHolder {
        private static LeftFragment leftFragment = new LeftFragment();
    }

    RecyclerView mRecyclerView;
    NoticeAdapter mNoticeAdapter;

    @Override
    protected Object setLayout() {
        return R.layout.fragment_main_left;
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initView(View rootView, Bundle savedInstanceState) {
        mRecyclerView = rootView.findViewById(R.id.main_recycler);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL));
        mNoticeAdapter = new NoticeAdapter(R.layout.item_notice);
        mRecyclerView.setAdapter(mNoticeAdapter);
        getData();
    }

    @Override
    public void onResume() {
        super.onResume();
        getData();
    }

    private void getData() {
        ArrayList<NoticeBean> noticeBeans = NoticeManager.LoadAllDay();
        mNoticeAdapter.setNewData(noticeBeans);
        checkIsShowScroll();
    }


    //判断是否要去设置页面
    private void checkIsShowScroll() {
        boolean aBoolean = PreferenceUtils.getBoolean(FinalData.IS_SETTING, false);
        if (!aBoolean) {
            startActivity(new Intent(getActivity(), SettingActivity.class));
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
                    LogUtils.Loge(TAG, "今天上班时间：" + timeInMillis);
                    LogUtils.Loge(TAG, "现在的时间：" + time);

                    if (time > noticeBeans.get(0).getClockTime() && time < timeInMillis) {//并且现在的时间已经超过了设定的时间，并且还没到上班时间
                        Intent intent = new Intent(getActivity(), ClockActivity.class);
                        intent.putExtra("timeId", noticeBeans.get(0).getClockTime());
                        startActivity(intent);
                    }
                }
            }
        }
    }

    @Override
    protected BasePresenter createPersenter() {
        return null;
    }
}
