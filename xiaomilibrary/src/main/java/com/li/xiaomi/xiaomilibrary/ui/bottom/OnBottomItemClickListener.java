package com.li.xiaomi.xiaomilibrary.ui.bottom;

/**
 * 作者：dell or Xiaomi Li
 * 时间： 2018/3/31
 * 内容：底部按钮的点击接口
 * 最后修改：
 */
public interface OnBottomItemClickListener {
    /**
     *
     * @param position  点击的第几个
     * @return  true：允许切换  false:不予许切换
     */
    boolean Click(int position);
}
