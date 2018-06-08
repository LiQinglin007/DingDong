package com.li.xiaomi.dingdong.adapter;


import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import com.li.xiaomi.dingdong.R;
import com.li.xiaomi.dingdong.db.NoticeBean;
import com.li.xiaomi.xiaomilibrary.utils.DateUtils;
import com.li.xiaomi.xiaomilibrary.utils.LogUtils;


/**
 * 作者：dell or Xiaomi Li
 * 时间： 2018/5/16
 * 内容：打卡记录
 * 最后修改：
 */
public class NoticeAdapter extends BaseQuickAdapter<NoticeBean, BaseViewHolder> {

    public NoticeAdapter(int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(BaseViewHolder helper, NoticeBean item) {
        LogUtils.Loge("getPosition:"+helper.getPosition());
        LogUtils.Loge("getLayoutPosition:"+helper.getLayoutPosition());
        LogUtils.Loge("getOldPosition:"+helper.getOldPosition());
        helper.setText(R.id.item_notice_time, DateUtils.getMonthDayWeek(DateUtils.LogToDate(item.getClockTime())));
        helper.setText(R.id.item_notice_content, item.getNoticeContent());
    }
}
