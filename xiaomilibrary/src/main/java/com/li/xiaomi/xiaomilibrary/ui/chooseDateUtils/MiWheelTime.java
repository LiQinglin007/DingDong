package com.li.xiaomi.xiaomilibrary.ui.chooseDateUtils;

import android.app.Dialog;
import android.content.Context;
import android.support.v7.widget.AppCompatButton;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;


import com.li.xiaomi.xiaomilibrary.R;
import com.li.xiaomi.xiaomilibrary.utils.DateUtils;
import com.li.xiaomi.xiaomilibrary.utils.ScreenUtils;
import com.wx.wheelview.adapter.ArrayWheelAdapter;
import com.wx.wheelview.widget.WheelView;

import java.util.Arrays;
import java.util.Date;
import java.util.ArrayList;
import java.util.List;

/**
 * 作者：dell or Xiaomi Li
 * 时间： 2018/4/30
 * 内容：选择时间，支持区间  年月日
 * 最后修改：
 */
public class MiWheelTime {
    private static Date mStartDate;
    private static Date mEndDate;

    static int startYear;
    static int endYear;
    static int startMonth;
    static int endMonth;
    static int startDay;
    static int endDay;

    static int selectYear = 0;//选中的年
    static int selectMonth = 0;//选中的月
    static int selectDay = 0;//选中的天

    static String[] months_big = {"1", "3", "5", "7", "8", "10", "12"};
    static String[] months_little = {"4", "6", "9", "11"};
    static List bigMonth = Arrays.asList(months_big);
    static List littleMonth = Arrays.asList(months_little);

    //初始化年份数组
    static ArrayList<String> yearList = new ArrayList<>();
    static ArrayList<String> monthList = new ArrayList<>();
    static ArrayList<String> dayList = new ArrayList<>();

    public static void showDialog(Context context, Date startDate, Date endDate, final TimeDialogCallBack timeDialogCallBack) {
        init(startDate, endDate);

        final Dialog dialog = new Dialog(context, R.style.LoadingDialog);
        View dialogView = LayoutInflater.from(context)
                .inflate(R.layout.dialog_choose_time, null);

        AppCompatButton submitTitle = (AppCompatButton) dialogView.findViewById(R.id.dialog_choose_time_submit);
        AppCompatButton cannleTitle = (AppCompatButton) dialogView.findViewById(R.id.dialog_choose_time_cancel);
        final WheelView yearWheel = dialogView.findViewById(R.id.dialog_choose_time_year);
        final WheelView monthWheel = dialogView.findViewById(R.id.dialog_choose_time_month);
        final WheelView dayWheel = dialogView.findViewById(R.id.dialog_choose_time_day);

        int textSize = (ScreenUtils.getScreenHeight(context) / 200) * 2;
        submitTitle.setTextSize(textSize);
        cannleTitle.setTextSize(textSize);

        yearList.clear();
        monthList.clear();
        dayList.clear();
        for (int i = startYear; i < endYear + 1; i++) {
            yearList.add(i + "");
        }
        //初始化月份
        monthList.addAll(getMonthList(startYear));
        //初始化天
        dayList.addAll(getDayList(startYear, startMonth));

        yearWheel.setWheelAdapter(new ArrayWheelAdapter(context));
        yearWheel.setWheelSize(5);
        yearWheel.setSkin(WheelView.Skin.Holo);
        yearWheel.setWheelData(yearList);
        yearWheel.setSelection(0);
        yearWheel.setVisibility(View.VISIBLE);

        monthWheel.setWheelAdapter(new ArrayWheelAdapter(context));
        monthWheel.setWheelSize(5);
        monthWheel.setSkin(WheelView.Skin.Holo);
        monthWheel.setWheelData(monthList);
        monthWheel.setSelection(0);
        monthWheel.setVisibility(View.VISIBLE);

        dayWheel.setWheelAdapter(new ArrayWheelAdapter(context));
        dayWheel.setWheelSize(5);
        dayWheel.setSkin(WheelView.Skin.Holo);
        dayWheel.setWheelData(dayList);
        dayWheel.setSelection(0);
        dayWheel.setVisibility(View.VISIBLE);

        WheelView.WheelViewStyle style = new WheelView.WheelViewStyle();
        style.selectedTextColor = context.getResources().getColor(R.color.color_51D8BA);
        yearWheel.setStyle(style);
        monthWheel.setStyle(style);
        dayWheel.setStyle(style);

        yearWheel.setExtraText("年", context.getResources().getColor(R.color.color_51D8BA), 40, 90);
        monthWheel.setExtraText("月", context.getResources().getColor(R.color.color_51D8BA), 40, 70);
        dayWheel.setExtraText("日", context.getResources().getColor(R.color.color_51D8BA), 40, 70);


        yearWheel.setOnWheelItemSelectedListener(new WheelView.OnWheelItemSelectedListener() {
            @Override
            public void onItemSelected(int position, Object o) {
                int parseInt = Integer.parseInt(yearList.get(position));
                monthList.clear();
                monthList.addAll(getMonthList(parseInt));
                monthWheel.setWheelData(monthList);
            }
        });


        monthWheel.setOnWheelItemSelectedListener(new WheelView.OnWheelItemSelectedListener() {
            @Override
            public void onItemSelected(int position, Object o) {
                int year = Integer.parseInt((String) yearWheel.getSelectionItem());
                int parseInt = Integer.parseInt(monthList.get(position));
                dayList.clear();
                dayList.addAll(getDayList(year, parseInt));
                dayWheel.setWheelData(dayList);
            }
        });


        submitTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String date = (String) yearWheel.getSelectionItem() + "-" + (String) monthWheel.getSelectionItem() + "-" + (String) dayWheel.getSelectionItem();
                timeDialogCallBack.callback(date);
                dialog.dismiss();
            }
        });
        cannleTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        Window window = dialog.getWindow();
        WindowManager.LayoutParams lp = window.getAttributes();
        //设置对话框的宽度
        lp.width = ScreenUtils.getScreenWidth(context);
        window.setGravity(Gravity.BOTTOM);
        window.setAttributes(lp);
        dialog.setContentView(dialogView);
        dialog.show();
    }

    /**
     * 根据年份返回月份的集合
     *
     * @param thisYear 年份
     * @return 月份集合
     */
    private static ArrayList<String> getMonthList(int thisYear) {
        ArrayList<String> monthList = new ArrayList<>();
        int startNumber = 1;
        int endNumber = 12;
        if (thisYear == startYear) {//第一年
            startNumber = startMonth;
        } else if (thisYear == endYear) {//最后一年
            endNumber = endMonth;
        }
        monthList.clear();
        for (int i = startNumber; i < endNumber + 1; i++) {
            monthList.add(i + "");
        }
        return monthList;
    }


    /**
     * 根据年份和月份  返回当前月份的天集合
     *
     * @param thisYear  年份
     * @param thisMonth 月份
     * @return 当前月的天集合
     */
    private static ArrayList<String> getDayList(int thisYear, int thisMonth) {
        ArrayList<String> dayList = new ArrayList<>();
        int startNumber = 1;
        int endNumber = 31;
        if (thisYear == startYear) {//第一年
            if (thisMonth == startMonth) {//第一年的第一个月，需要初始化开始天，其他月份都是从1开始
                startNumber = startDay;
            }
            endNumber = getEndNumber(thisYear, thisMonth);
        } else if (thisYear == endYear) {//最后一年，这里就不用管开始了，都是从1号开始，只处理结束时间
            //如果是最后一个月，就到最后一天就好了,//不是最后一个月，要去判断大月和小月，还有平年闰年
            endNumber = thisMonth == endMonth ? endDay : getEndNumber(thisYear, thisMonth);
        } else {//中间年份，也是只处理结束时间
            endNumber = getEndNumber(thisYear, thisMonth);
        }
        dayList.clear();
        for (int i = startNumber; i < endNumber + 1; i++) {
            dayList.add(i + "");
        }
        return dayList;
    }

    /**
     * 根据平年闰年和大小月来判断这个月最后一天是几号
     *
     * @param thisYear 年份
     * @param month    月份
     * @return 最后一天是几号
     */
    private static int getEndNumber(int thisYear, int month) {
        int endNumber = 31;
        if (bigMonth.contains(month + "")) {//31天的月份
            endNumber = 31;
        } else if (littleMonth.contains(month + "")) {//30天的月份
            endNumber = 30;
        } else {//2月 这里去判断平年或者闰年
            if ((thisYear % 4 == 0 && thisYear % 100 != 0)
                    || thisYear % 400 == 0) {//闰年
                endNumber = 29;
            } else {//平年
                endNumber = 28;
            }
        }
        return endNumber;
    }

    private static void init(Date startDate, Date endDate) {
        mStartDate = startDate;
        mEndDate = endDate;
        startYear = DateUtils.getYear(startDate);
        endYear = DateUtils.getYear(endDate);
        startMonth = DateUtils.getMonth(startDate);
        endMonth = DateUtils.getMonth(endDate);
        startDay = DateUtils.getDay(startDate);
        endDay = DateUtils.getDay(endDate);
    }



    public interface TimeDialogCallBack {
        void callback(String date);
    }
}
