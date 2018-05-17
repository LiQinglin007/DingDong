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

import java.util.ArrayList;
import java.util.Date;

/**
 * 作者：dell or Xiaomi Li
 * 时间： 2018/5/16
 * 内容：选择时分
 * 最后修改：
 */
public class TimeMinDialog {

    public static void showDialog(Context context, int chooseHour, int chooseMin, final TimeDialogCallBack timeDialogCallBack) {
        final Dialog dialog = new Dialog(context, R.style.LoadingDialog);
        View dialogView = LayoutInflater.from(context)
                .inflate(R.layout.dialog_choose_time, null);

        AppCompatButton submitTitle = (AppCompatButton) dialogView.findViewById(R.id.dialog_choose_time_submit);
        AppCompatButton cannleTitle = (AppCompatButton) dialogView.findViewById(R.id.dialog_choose_time_cancel);
        final WheelView hourWheel = dialogView.findViewById(R.id.dialog_choose_time_year);
        final WheelView minWheel = dialogView.findViewById(R.id.dialog_choose_time_month);
        final WheelView dayWheel = dialogView.findViewById(R.id.dialog_choose_time_day);
        dayWheel.setVisibility(View.GONE);

        ArrayList<String> mHourList = new ArrayList<>();
        final ArrayList<String> mMinList = new ArrayList<>();

        for (int i = 0; i < 24; i++) {
            if (i < 10) {
                mHourList.add("0" + i);
            } else {
                mHourList.add(i + "");
            }
        }

        for (int i = 0; i < 60; i++) {
            if (i < 10) {
                mMinList.add("0" + i);
            } else {
                mMinList.add(i + "");
            }
        }
        ArrayWheelAdapter arrayWheelAdapter = new ArrayWheelAdapter(context);
        hourWheel.setWheelAdapter(arrayWheelAdapter);
        hourWheel.setWheelSize(5);
        hourWheel.setSkin(WheelView.Skin.Holo);
        hourWheel.setWheelData(mHourList);
        hourWheel.setSelection(chooseHour);
        hourWheel.setVisibility(View.VISIBLE); //放在setSelection()方法后

        ArrayWheelAdapter arrayWheelAdapter1 = new ArrayWheelAdapter(context);
        minWheel.setWheelAdapter(arrayWheelAdapter1);
        minWheel.setWheelSize(5);
        minWheel.setSkin(WheelView.Skin.Holo);
        minWheel.setWheelData(mMinList);
        minWheel.setSelection(chooseMin);
        minWheel.setVisibility(View.VISIBLE); //放在setSelection()方法后

        WheelView.WheelViewStyle style = new WheelView.WheelViewStyle();
        style.selectedTextColor = context.getResources().getColor(R.color.color_51D8BA);
        hourWheel.setStyle(style);
        minWheel.setStyle(style);

        hourWheel.setExtraText("时", context.getResources().getColor(R.color.color_51D8BA), 40, 70);
        minWheel.setExtraText("分", context.getResources().getColor(R.color.color_51D8BA), 40, 70);

        hourWheel.setOnWheelItemSelectedListener(new WheelView.OnWheelItemSelectedListener() {
            @Override
            public void onItemSelected(int position, Object o) {
                minWheel.setWheelData(mMinList);
            }
        });

        int textSize = (ScreenUtils.getScreenHeight(context) / 200) * 2;
        submitTitle.setTextSize(textSize);
        cannleTitle.setTextSize(textSize);

        submitTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String hourSelectionItem = (String) hourWheel.getSelectionItem();
                String minSelectionItem = (String) minWheel.getSelectionItem();
                timeDialogCallBack.callback(minSelectionItem, hourSelectionItem);
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
        void callback(String min, String hour);
    }
}
