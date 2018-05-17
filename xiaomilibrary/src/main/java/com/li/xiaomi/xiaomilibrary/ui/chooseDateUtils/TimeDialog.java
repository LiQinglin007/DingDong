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
import com.wx.wheelview.widget.WheelView;

import java.util.ArrayList;
import java.util.Date;

/**
 * 作者：dell or Xiaomi Li
 * 时间： 2018/4/29
 * 内容：选择时间，未来三天，模仿滴滴预约
 * 最后修改：
 */
public class TimeDialog {

    public static void showDialog(Context context, final TimeDialogCallBack timeDialogCallBack) {
        final Dialog dialog = new Dialog(context, R.style.LoadingDialog);
        View dialogView = LayoutInflater.from(context)
                .inflate(R.layout.dialog_choose_time, null);

        AppCompatButton submitTitle = (AppCompatButton) dialogView.findViewById(R.id.dialog_choose_time_submit);
        AppCompatButton cannleTitle = (AppCompatButton) dialogView.findViewById(R.id.dialog_choose_time_cancel);
        final WheelView dateWheel = dialogView.findViewById(R.id.dialog_choose_time_year);
        final WheelView hourWheel = dialogView.findViewById(R.id.dialog_choose_time_month);
        final WheelView dayWheel = dialogView.findViewById(R.id.dialog_choose_time_day);
        dayWheel.setVisibility(View.GONE);


        /**
         * 营业时间 7：00-22：00
         * 今天晚上22点之前的，能预约今，明，后三天的；
         * 今天晚上22点之后的，能预约明，后，大后三天。
         */
        Date mDate = new Date();
        int hours = mDate.getHours();
        ArrayList<String> mDateList = new ArrayList<>();
        final ArrayList<String> mTimeList = new ArrayList<>();
        final ArrayList<String> mTimeList1 = new ArrayList<>();
        if (hours < 22) {
            int i = hours < 7 ? 7 : hours + 1;
            for (; i < 23; i++) {
                mTimeList.add(i + "");
            }
            for (int j = 7; j < 23; j++) {
                mTimeList1.add(j + "");
            }
            mDateList.add(DateUtils.getMonthDayWeek(mDate));//今天
            mDateList.add(DateUtils.getMonthDayWeek(DateUtils.getNextDay(mDate)));//明天
            mDateList.add(DateUtils.getMonthDayWeek(DateUtils.getSecondDay(mDate)));//后天
        } else {
            for (int i = 7; i < 23; i++) {
                mTimeList1.add(i + "");
                mTimeList.add(i + "");
            }
            mDateList.add(DateUtils.getMonthDayWeek(DateUtils.getNextDay(mDate)));//明天
            mDateList.add(DateUtils.getMonthDayWeek(DateUtils.getSecondDay(mDate)));//后天
            mDateList.add(DateUtils.getMonthDayWeek(DateUtils.getThirdDay(mDate)));//大后天
        }


        dateWheel.setWheelAdapter(new com.wx.wheelview.adapter.ArrayWheelAdapter(context));
        dateWheel.setWheelSize(5);
        dateWheel.setSkin(WheelView.Skin.Holo);
        dateWheel.setWheelData(mDateList);
        dateWheel.setSelection(0);
        dateWheel.setVisibility(View.VISIBLE);

        hourWheel.setWheelAdapter(new com.wx.wheelview.adapter.ArrayWheelAdapter(context));
        hourWheel.setWheelSize(5);
        hourWheel.setSkin(WheelView.Skin.Holo);
        hourWheel.setWheelData(mTimeList);
        hourWheel.setSelection(0);
        hourWheel.setVisibility(View.VISIBLE);

        WheelView.WheelViewStyle style = new WheelView.WheelViewStyle();
        style.selectedTextColor = context.getResources().getColor(R.color.color_51D8BA);
        dateWheel.setStyle(style);
        hourWheel.setStyle(style);

        hourWheel.setExtraText("时", context.getResources().getColor(R.color.color_51D8BA), 40, 70);

        dateWheel.setOnWheelItemSelectedListener(new WheelView.OnWheelItemSelectedListener() {
            @Override
            public void onItemSelected(int position, Object o) {
                if (position == 0) {//如果滚动到了第一个，后边的小时就加载第一天的
                    hourWheel.setWheelData(mTimeList);
                } else {//加载后边的
                    hourWheel.setWheelData(mTimeList1);
                }
            }
        });

        int textSize = (ScreenUtils.getScreenHeight(context) / 200) * 2;
        submitTitle.setTextSize(textSize);
        cannleTitle.setTextSize(textSize);

        submitTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String timeSelectionItem = (String) dateWheel.getSelectionItem();
                String hourSelectionItem = (String) hourWheel.getSelectionItem();
                timeDialogCallBack.callback(timeSelectionItem, hourSelectionItem);
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

    public interface TimeDialogCallBack {
        void callback(String date, String time);
    }
}
