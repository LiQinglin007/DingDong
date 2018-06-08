package com.li.xiaomi.dingdong.db;


import com.li.xiaomi.dingdong.utils.greendaoUtils.DBManager;

import java.util.Date;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * 作者：dell or Xiaomi Li
 * 时间： 2018/5/16
 * 内容：
 * 最后修改：
 */
public class NoticeManager {

    public static ArrayList<NoticeBean> LoadAll() {
        NoticeBeanDao noticeBeanDao = DBManager.getInstance().getDaoSession().getNoticeBeanDao();
        List<NoticeBean> noticeBeans = noticeBeanDao.loadAll();
        ArrayList<NoticeBean> mList = new ArrayList<>();
        if (noticeBeans != null && noticeBeans.size() != 0) {
            mList.addAll(noticeBeans);
        }
        return mList;
    }

    /**
     * 查询未打卡的
     *
     * @return
     */
    public static ArrayList<NoticeBean> LoadFalse() {
        NoticeBeanDao noticeBeanDao = DBManager.getInstance().getDaoSession().getNoticeBeanDao();
        List<NoticeBean> noticeBeans = noticeBeanDao.
                queryBuilder().
                where(NoticeBeanDao.Properties.Result.eq(false)).//查询未打卡的
                orderAsc(NoticeBeanDao.Properties.ClockTime).//倒序排列
                list();
        ArrayList<NoticeBean> mList = new ArrayList<>();
        if (noticeBeans != null && noticeBeans.size() != 0) {
            mList.addAll(noticeBeans);
        }
        return mList;
    }

    /**
     * 删除现在以后的数据，重新设置闹钟
     */
    public static void delete() {
        long timeInMillis = new Date().getTime();
        NoticeBeanDao noticeBeanDao = DBManager.getInstance().getDaoSession().getNoticeBeanDao();
//        1. > : gt
//        2. < : lt
//        3. >= : ge
//        4. <= : le
        List<NoticeBean> noticeBeans = noticeBeanDao.
                queryBuilder().
                where(NoticeBeanDao.Properties.ClockTime.gt(timeInMillis)).//现在以后的
                list();

        if (noticeBeans != null && noticeBeans.size() != 0) {
            for (NoticeBean noticeBean : noticeBeans) {
                noticeBeanDao.delete(noticeBean);
            }
        }
    }

    /**
     * 查询今天和今天之前的
     *
     * @return
     */
    public static ArrayList<NoticeBean> LoadAllDay() {
        //今天0点
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(System.currentTimeMillis());
        cal.set(Calendar.HOUR_OF_DAY, 23);
        cal.set(Calendar.MINUTE, 59);
        cal.set(Calendar.SECOND, 59);
        long timeInMillis = cal.getTimeInMillis();

        NoticeBeanDao noticeBeanDao = DBManager.getInstance().getDaoSession().getNoticeBeanDao();
//        1. > : gt
//        2. < : lt
//        3. >= : ge
//        4. <= : le
        List<NoticeBean> noticeBeans = noticeBeanDao.
                queryBuilder().
                where(NoticeBeanDao.Properties.ClockTime.le(timeInMillis)).//查询今天之前的(包含今天)
                orderDesc(NoticeBeanDao.Properties.ClockTime).//倒序排列
                list();
        ArrayList<NoticeBean> mList = new ArrayList<>();
        if (noticeBeans != null && noticeBeans.size() != 0) {
            mList.addAll(noticeBeans);
        }
        return mList;
    }


    /**
     * 查询今天的
     *
     * @return
     */
    public static ArrayList<NoticeBean> LoadAllThisDay() {
        //今天0点
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(System.currentTimeMillis());
        cal.set(Calendar.HOUR_OF_DAY, 23);
        cal.set(Calendar.MINUTE, 59);
        cal.set(Calendar.SECOND, 59);
        long timeInMillis = cal.getTimeInMillis();


        //今天0点
        Calendar cal1 = Calendar.getInstance();
        cal1.setTimeInMillis(System.currentTimeMillis());
        cal1.set(Calendar.HOUR_OF_DAY, 0);
        cal1.set(Calendar.MINUTE, 0);
        cal1.set(Calendar.SECOND, 0);
        long timeInMillis1 = cal1.getTimeInMillis();

        NoticeBeanDao noticeBeanDao = DBManager.getInstance().getDaoSession().getNoticeBeanDao();
//        1. > : gt
//        2. < : lt
//        3. >= : ge
//        4. <= : le
        List<NoticeBean> noticeBeans = noticeBeanDao.
                queryBuilder().
                where(NoticeBeanDao.Properties.ClockTime.le(timeInMillis)).//查询今天之前的(包含今天)
                where(NoticeBeanDao.Properties.ClockTime.ge(timeInMillis1)).//查询今天之后的(包含今天)
                orderDesc(NoticeBeanDao.Properties.ClockTime).//倒序排列
                list();
        ArrayList<NoticeBean> mList = new ArrayList<>();
        if (noticeBeans != null && noticeBeans.size() != 0) {
            mList.addAll(noticeBeans);
        }
        return mList;
    }


    public static void add(NoticeBean mBean) {
        NoticeBeanDao noticeBeanDao = DBManager.getInstance().getDaoSession().getNoticeBeanDao();
        noticeBeanDao.insert(mBean);
    }

    public static void update(NoticeBean mBean, long time) {
        NoticeBeanDao noticeBeanDao = DBManager.getInstance().getDaoSession().getNoticeBeanDao();
        NoticeBean load = noticeBeanDao.queryBuilder().where(NoticeBeanDao.Properties.ClockTime.eq(time)).unique();
        load.setResult(mBean.getResult());
        load.setNoticeContent(mBean.getNoticeContent());
        load.setClockTime(mBean.getClockTime());
        noticeBeanDao.update(load);
    }

}
