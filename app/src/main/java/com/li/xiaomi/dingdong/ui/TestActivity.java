package com.li.xiaomi.dingdong.ui;

import android.os.Bundle;

import com.li.xiaomi.dingdong.R;
import com.li.xiaomi.xiaomilibrary.base.BaseActivity;
import com.li.xiaomi.xiaomilibrary.base.BasePresenter;

/**
 * 作者：dell or Xiaomi Li
 * 时间： 2018/6/8
 * 内容：
 * 最后修改：
 */
public class TestActivity extends BaseActivity {
    @Override
    protected Object setLayout() {
        return R.id.menu_main_test;
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initView(Bundle savedInstanceState) {

    }

    @Override
    protected BasePresenter createPersenter() {
        return null;
    }

    @Override
    protected boolean translucentStatusBar() {
        return false;
    }
}
