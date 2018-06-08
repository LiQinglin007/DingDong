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

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Timer;

/**
 * 作者：dell or Xiaomi Li
 * 时间： 2018/6/8
 * 内容：预约打卡
 * 最后修改：
 */
public class RightFragment extends BaseFragment {

    public static RightFragment getInstance() {
        return LeftFragmentHolder.rightFragment;
    }

    private static class LeftFragmentHolder {
        private static RightFragment rightFragment = new RightFragment();
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
        ArrayList<NoticeBean> noticeBeans = NoticeManager.LoadFalse();
        mNoticeAdapter.setNewData(noticeBeans);
    }

    @Override
    protected BasePresenter createPersenter() {
        return null;
    }
}
