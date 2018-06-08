package com.li.xiaomi.dingdong.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;


import com.li.xiaomi.dingdong.R;
import com.li.xiaomi.dingdong.adapter.NoticeAdapter;
import com.li.xiaomi.dingdong.adapter.ViewPagerAdapter;
import com.li.xiaomi.dingdong.db.NoticeBean;
import com.li.xiaomi.dingdong.db.NoticeManager;
import com.li.xiaomi.dingdong.utils.FinalData;
import com.li.xiaomi.xiaomilibrary.base.BaseActivity;
import com.li.xiaomi.xiaomilibrary.base.BasePresenter;
import com.li.xiaomi.xiaomilibrary.utils.LogUtils;
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

public class MainActivity extends BaseActivity {

    Toolbar mToolbar;
    TabLayout mTabLayout;
    ViewPager mViewPager;

    ViewPagerAdapter mViewPagerAdapter;
    ArrayList<Fragment> mFragments = new ArrayList<>();
    ArrayList<String> mTitle = new ArrayList<>();

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
        mFragments.clear();
        mFragments.add(LeftFragment.getInstance());
        mFragments.add(RightFragment.getInstance());

        mTitle.clear();
        mTitle.add("打卡记录");
        mTitle.add("预约打卡");

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        mToolbar = findViewById(R.id.main_toolbar);
        mTabLayout = findViewById(R.id.main_tablayout);
        mViewPager = findViewById(R.id.main_viewpager);

        mToolbar.setTitle("");
        setSupportActionBar(mToolbar);

        mToolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.menu_main_setting:
                        startActivity(new Intent(MainActivity.this, SettingActivity.class));
                        break;
                    case R.id.menu_main_test:
                        startActivity(new Intent(MainActivity.this, TestActivity.class));
                        break;
                }
                return true;
            }
        });


        mViewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        mViewPagerAdapter.setList(mFragments, mTitle);
        mViewPager.setAdapter(mViewPagerAdapter);

        mTabLayout.setBackgroundColor(getResources().getColor(R.color.default_color));
        mTabLayout.setTabMode(TabLayout.MODE_FIXED);
        mTabLayout.setTabTextColors(getResources().getColor(R.color.warning_color5), getResources().getColor(R.color.color_white));
        mTabLayout.setSelectedTabIndicatorColor(getResources().getColor(R.color.color_white));
        mTabLayout.setSelectedTabIndicatorHeight(5);
        mTabLayout.setupWithViewPager(mViewPager);
    }
}
