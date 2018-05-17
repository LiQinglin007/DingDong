package com.li.xiaomi.xiaomilibrary.utils.recycler;

/**
 * 作者：dell or Xiaomi Li
 * 时间： 2018/1/25
 * 内容：配合LrcyclerView实现下拉+侧滑的接口
 * 最后修改：
 */

public interface OnSideslipClick {
    //删除按钮
    void OnDeleteClick(int position);

    //置顶按钮
    void OnTopClick(int position);
}
