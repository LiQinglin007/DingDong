package com.li.xiaomi.xiaomilibrary.utils.recycler;

import android.support.v7.widget.RecyclerView;

/**
 * 类描述：判断是否滑动到了底部
 * 作  者：李清林
 * 时  间：
 * 修改备注：
 */
public class IsBottom {
    public static  boolean isSlideToBottom(RecyclerView recyclerView) {
        if (recyclerView == null) return false;
        if (recyclerView.computeVerticalScrollExtent() + recyclerView.computeVerticalScrollOffset() >= recyclerView.computeVerticalScrollRange())
            return true;
        return false;
    }
}
