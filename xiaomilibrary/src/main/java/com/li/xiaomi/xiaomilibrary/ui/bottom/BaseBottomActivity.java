package com.li.xiaomi.xiaomilibrary.ui.bottom;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.LinearLayoutCompat;
import android.view.LayoutInflater;
import android.view.View;

import com.li.xiaomi.xiaomilibrary.R;
import com.li.xiaomi.xiaomilibrary.base.BaseActivity;
import com.li.xiaomi.xiaomilibrary.base.BasePresenter;
import com.li.xiaomi.xiaomilibrary.utils.CheckStringEmptyUtils;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;


/**
 * 作者：dell or Xiaomi Li
 * 时间： 2018/3/31
 * 内容：底部菜单的页面
 * 最后修改：
 */

public abstract class BaseBottomActivity extends BaseActivity implements View.OnClickListener {

    private final ArrayList<BottomTabBean> TAB_BEANS = new ArrayList<>();
    private final ArrayList<Fragment> ITEM_DELEGATES = new ArrayList<>();
    private final LinkedHashMap<BottomTabBean, Fragment> ITEMS = new LinkedHashMap<>();
    private int mChooseIndex = 0;//默认选中第0个
    private int mClickedColor;
    private int mUnClickedColor;
    private int mBackGroundColor;
    private LinearLayoutCompat mLayoutCompat;//底部放按钮的布局
    private OnBottomItemClickListener mOnBottomItemClickListener;
    Fragment openEdFragment = null;

    /**
     * 这里去做一些准备工作
     */
    public abstract void init();

    /**
     * 添加ItemBean
     *
     * @param builder
     * @return
     */
    public abstract LinkedHashMap<BottomTabBean, Fragment> setItems(ItemBuilder builder);

    /**
     * 设置选中第几个
     *
     * @return
     */
    public abstract int setChooseIndex();

    /**
     * 设置选中的原色
     *
     * @return
     */
    public abstract int setClickedColor();

    /**
     * 设置未选中的原色
     *
     * @return
     */
    public abstract int setUnClickedColor();

    /**
     * 设置背景颜色
     *
     * @return
     */
    public abstract int setBackGroundColor();


    /**
     * 底部按钮的点击接口，
     * 如果没有验证是想切换，就返回true
     * 如果有验证，比如说没有登录的不允许切换，就返回false
     *
     * @return
     */
    public abstract OnBottomItemClickListener setOnBottomItemClickListener();


    @Override
    protected Object setLayout() {
        return R.layout.base_bottom;
    }


    @Override
    protected void initData() {
        mChooseIndex = setChooseIndex();
        mClickedColor = (setClickedColor() == 0 ? R.color.color_51D8BA : setClickedColor());
        mUnClickedColor = (setUnClickedColor() == 0 ? R.color.color_666 : setUnClickedColor());
        mBackGroundColor = setBackGroundColor() == 0 ? R.color.color_white : setBackGroundColor();
        mOnBottomItemClickListener = setOnBottomItemClickListener();
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        init();
        mLayoutCompat = findViewById(R.id.bottom_bar);
        mLayoutCompat.setBackgroundResource(mBackGroundColor);

        final ItemBuilder builder = ItemBuilder.builder();
        final LinkedHashMap<BottomTabBean, Fragment> items = setItems(builder);
        ITEMS.putAll(items);
        for (Map.Entry<BottomTabBean, Fragment> item : ITEMS.entrySet()) {
            final BottomTabBean key = item.getKey();
            final Fragment value = item.getValue();
            TAB_BEANS.add(key);
            ITEM_DELEGATES.add(value);
        }
        setView();
    }


    private void setView() {
        final int size = ITEMS.size();
        for (int i = 0; i < size; i++) {
            LayoutInflater.from(this).inflate(R.layout.bottom_item_icon_text_layout, mLayoutCompat);
            final LinearLayoutCompat item = (LinearLayoutCompat) mLayoutCompat.getChildAt(i);
            final AppCompatImageView itemIcon = (AppCompatImageView) item.getChildAt(0);
            final AppCompatTextView itemTitle = (AppCompatTextView) item.getChildAt(1);
            //设置每个item的点击事件this
            item.setTag(i);
            item.setOnClickListener(this);

            final BottomTabBean bean = TAB_BEANS.get(i);
            //初始化数据
            if (CheckStringEmptyUtils.IsEmpty(bean.getBottomTv())) {
                itemTitle.setVisibility(View.GONE);
            } else {
                itemTitle.setVisibility(View.VISIBLE);
                itemTitle.setText(bean.getBottomTv());
            }
            itemIcon.setImageResource(i == mChooseIndex ? bean.getBottomImgChecked() : bean.getBottomImgUnChecked());
            itemTitle.setTextColor(getResources().getColor(i == mChooseIndex ? mClickedColor : mUnClickedColor));

        }

        switchFragment();
    }


    /**
     * 还原颜色
     */
    private void resetColor() {
        final int count = mLayoutCompat.getChildCount();
        for (int i = 0; i < count; i++) {
            BottomTabBean bottomTabBean = TAB_BEANS.get(i);
            final LinearLayoutCompat item = (LinearLayoutCompat) mLayoutCompat.getChildAt(i);
            final AppCompatImageView itemIcon = (AppCompatImageView) item.getChildAt(0);
            final AppCompatTextView itemTitle = (AppCompatTextView) item.getChildAt(1);

            itemIcon.setImageResource(bottomTabBean.getBottomImgUnChecked());
            itemTitle.setTextColor(getResources().getColor(mUnClickedColor));
        }
    }


    @Override
    public void onClick(View v) {
        //点击的是第几个
        final int tag = (int) v.getTag();
        if (mOnBottomItemClickListener != null) {
            if (mOnBottomItemClickListener.Click(tag)) {
                //看上层让不让切换
                HandoffPage(v, tag);
            }
        }
    }


    private void HandoffPage(View v, int tag) {
        //取到相应的Tab
        BottomTabBean bottomTabBean = TAB_BEANS.get(tag);
        //还原所有颜色，图片
        resetColor();
        //取到View
        final LinearLayoutCompat item = (LinearLayoutCompat) v;
        final AppCompatImageView itemIcon = (AppCompatImageView) item.getChildAt(0);
        final AppCompatTextView itemTitle = (AppCompatTextView) item.getChildAt(1);
        //设置选中的颜色和图片
        itemIcon.setImageResource(bottomTabBean.getBottomImgChecked());
        itemTitle.setTextColor(getResources().getColor(mClickedColor));
        //改变选中的index,注意先后顺序
        mChooseIndex = tag;
        //切换view
        switchFragment();
    }


    private void switchFragment() {
        Fragment fragment = ITEM_DELEGATES.get(mChooseIndex);
        if (fragment != null) {
            if (openEdFragment == null) {
                getSupportFragmentManager().beginTransaction().add(R.id.bottom_bar_delegate_container, fragment).commit();
                openEdFragment = fragment;
            } else {
                if (!openEdFragment.equals(fragment)) {
                    if (fragment.isAdded()) {
                        getSupportFragmentManager().beginTransaction().hide(openEdFragment).show(fragment).commit();
                        openEdFragment = fragment;
                    } else {
                        getSupportFragmentManager().beginTransaction().hide(openEdFragment).add(R.id.bottom_bar_delegate_container, fragment).commit();
                        openEdFragment = fragment;
                    }
                }
            }
        }
    }


    /**
     * 底部按钮的点击接口，
     * 如果没有验证是想切换，就返回true
     * 如果有验证，比如说没有登录的不允许切换，就返回false
     */
    protected interface OnBottomItemClickListener {
        boolean Click(int position);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        TAB_BEANS.clear();
        ITEM_DELEGATES.clear();
        ITEMS.clear();
        mChooseIndex = 0;
        mClickedColor = 0;
        mUnClickedColor = 0;
        mBackGroundColor = 0;
        mLayoutCompat = null;//底部放按钮的布局
        mOnBottomItemClickListener = null;
        openEdFragment = null;
    }
}
