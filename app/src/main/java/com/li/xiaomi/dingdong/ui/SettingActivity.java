package com.li.xiaomi.dingdong.ui;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;

import com.li.xiaomi.dingdong.R;
import com.li.xiaomi.dingdong.db.NoticeBean;
import com.li.xiaomi.dingdong.db.NoticeManager;
import com.li.xiaomi.dingdong.utils.FinalData;
import com.li.xiaomi.dingdong.utils.TimingUtils.TimingUtils;
import com.li.xiaomi.xiaomilibrary.base.BaseActivity;
import com.li.xiaomi.xiaomilibrary.base.BasePresenter;
import com.li.xiaomi.xiaomilibrary.ui.chooseDateUtils.TimeMinDialog;
import com.li.xiaomi.xiaomilibrary.ui.dialog.MiDialog;
import com.li.xiaomi.xiaomilibrary.ui.dialog.bottomList.BottomDialogListAdapter;
import com.li.xiaomi.xiaomilibrary.ui.dialog.bottomList.MiListDialog;
import com.li.xiaomi.xiaomilibrary.utils.PreferenceUtils;
import com.li.xiaomi.xiaomilibrary.utils.T;

import java.util.ArrayList;
import java.util.Calendar;

/**
 * 作者：dell or Xiaomi Li
 * 时间： 2018/5/16
 * 内容：
 * 最后修改：
 */
public class SettingActivity extends BaseActivity implements View.OnClickListener {

    LinearLayout chooseWorktimeLy;
    LinearLayout chooseStartuptimeLy;
    LinearLayout chooseRemindtimeLy;

    TextView chooseWorktimeTv;
    TextView chooseStartuptimeTv;
    TextView chooseRemindtimeTv;

    Switch mSwitch;

    TextView submitTv;

    MiListDialog miListDialog;
    ArrayList<String> timeList = new ArrayList<>();
    int time = 5;//提前多久提醒
    int workHour = 8;
    int workmin = 30;
    int startUpHour = 8;
    int startUpmin = 25;


    boolean restType = false;//单休？
    boolean IS_UPDATE_STARTTIME = false;//是否修改了启动时间

    @Override
    protected Object setLayout() {
        return R.layout.activity_setting;
    }

    @Override
    protected void initData() {
        for (int i = 1; i < 15; i++) {
            timeList.add(i + "");
        }
        miListDialog = new MiListDialog();

        workHour = PreferenceUtils.getInt(FinalData.WORK_HOUR, 8);
        workmin = PreferenceUtils.getInt(FinalData.WORK_MINE, 30);

        startUpHour = PreferenceUtils.getInt(FinalData.START_UP_HOUR, 8);
        startUpmin = PreferenceUtils.getInt(FinalData.START_UP_MINE, 25);
        Long aLong = PreferenceUtils.getLong(FinalData.TIME_MINE, 30000);
        time = (int) (aLong / 6000);
        restType = PreferenceUtils.getBoolean(FinalData.DOUBLE_REST, false);

    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        chooseWorktimeLy = findViewById(R.id.choose_worktime_ly);
        chooseStartuptimeLy = findViewById(R.id.choose_startup_time_ly);
        chooseRemindtimeLy = findViewById(R.id.choose_remind_time_ly);

        chooseWorktimeTv = findViewById(R.id.choose_worktime);
        chooseStartuptimeTv = findViewById(R.id.choose_startup_time);
        chooseRemindtimeTv = findViewById(R.id.choose_remind_time);

        mSwitch = findViewById(R.id.setting_switch);
        submitTv = findViewById(R.id.setting_submit);

        chooseWorktimeLy.setOnClickListener(this);
        chooseStartuptimeLy.setOnClickListener(this);
        chooseRemindtimeLy.setOnClickListener(this);
        submitTv.setOnClickListener(this);

        mSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                restType = isChecked;
            }
        });

        setData();
    }


    private void setData() {
        chooseWorktimeTv.setText(workHour + ":" + workmin);
        chooseStartuptimeTv.setText(startUpHour + ":" + startUpmin);
        chooseRemindtimeTv.setText("上班前" + time + "分钟");
        mSwitch.setChecked(restType);
    }

    @Override
    protected BasePresenter createPersenter() {
        return null;
    }

    @Override
    protected boolean translucentStatusBar() {
        return false;
    }


    @TargetApi(Build.VERSION_CODES.N)
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.choose_worktime_ly:
                TimeMinDialog.showDialog(SettingActivity.this, workHour, workmin, new TimeMinDialog.TimeDialogCallBack() {
                    @Override
                    public void callback(String min, String hour) {
                        workHour = Integer.parseInt(hour);
                        workmin = Integer.parseInt(min);
                        chooseWorktimeTv.setText(hour + ":" + min);
                    }
                });
                break;
            case R.id.choose_startup_time_ly:
                TimeMinDialog.showDialog(SettingActivity.this, startUpHour, startUpmin, new TimeMinDialog.TimeDialogCallBack() {
                    @Override
                    public void callback(String min, String hour) {
                        if (startUpHour != Integer.parseInt(hour)) {
                            startUpHour = Integer.parseInt(hour);
                            IS_UPDATE_STARTTIME = true;
                        }
                        if (startUpmin != Integer.parseInt(min)) {
                            startUpmin = Integer.parseInt(min);
                            IS_UPDATE_STARTTIME = true;
                        }
                        chooseStartuptimeTv.setText(hour + ":" + min);
                    }
                });
                break;
            case R.id.choose_remind_time_ly:
                BottomDialogListAdapter mAdapter1 = new BottomDialogListAdapter(SettingActivity.this);
                mAdapter1.setList(timeList);
                miListDialog.showDialog(SettingActivity.this, "选择标题", mAdapter1, MiListDialog.MILIST_DIALOG_BOTTOM, new MiListDialog.OnListBottomDialogCallback() {
                    @Override
                    public void onListCallback(int position) {
                        time = Integer.parseInt(timeList.get(position));
                        chooseRemindtimeTv.setText("上班前" + timeList.get(position) + "min");
                    }
                });
                break;
            case R.id.setting_submit:
                submit();
                break;
            default:
                break;
        }
    }

    private void submit() {
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(System.currentTimeMillis());
        cal.set(Calendar.HOUR_OF_DAY, workHour);
        cal.set(Calendar.MINUTE, workmin);
        cal.set(Calendar.SECOND, 0);
        long workTime = cal.getTimeInMillis();

        Calendar cal1 = Calendar.getInstance();
        cal1.setTimeInMillis(System.currentTimeMillis());
        cal1.set(Calendar.HOUR_OF_DAY, startUpHour);
        cal1.set(Calendar.MINUTE, startUpmin);
        cal1.set(Calendar.SECOND, 0);
        long startTime = cal1.getTimeInMillis();

        if (workTime > startTime + time * 60000) {
            PreferenceUtils.setLong(FinalData.TIME_MINE, time * 6000);//上班前几分钟提醒
            PreferenceUtils.setInt(FinalData.WORK_HOUR, workHour);//上班时间
            PreferenceUtils.setInt(FinalData.WORK_MINE, workmin);
            PreferenceUtils.setInt(FinalData.START_UP_HOUR, startUpHour);//App启动时间
            PreferenceUtils.setInt(FinalData.START_UP_MINE, startUpmin);
            PreferenceUtils.setBoolean(FinalData.DOUBLE_REST, restType);//是否单休
            PreferenceUtils.setBoolean(FinalData.IS_SETTING, true);//是否设置好了
            if (IS_UPDATE_STARTTIME) {//如果修改了启动时间(就是闹钟时间)，这里去关闭之前的闹钟然后重新设置
                ArrayList<NoticeBean> noticeBeans = NoticeManager.LoadFalse();
                for (NoticeBean noticeBean : noticeBeans) {
                    TimingUtils.cancelAlarm(SettingActivity.this, noticeBean.getClockId());
                }
            }
            NoticeManager.deleteAll();//先把之前的数据删掉
            //下一个周一
            TimingUtils.setClock(SettingActivity.this, startUpHour, startUpmin, 1, 101);
            //下一个周二
            TimingUtils.setClock(SettingActivity.this, startUpHour, startUpmin, 2, 202);
            //下一个周三
            TimingUtils.setClock(SettingActivity.this, startUpHour, startUpmin, 3, 303);
            //下一个周四
            TimingUtils.setClock(SettingActivity.this, startUpHour, startUpmin, 4, 404);
            //下一个周五
            TimingUtils.setClock(SettingActivity.this, startUpHour, startUpmin, 5, 505);

            if (restType) {//单休  //下一个周六
                TimingUtils.setClock(SettingActivity.this, startUpHour, startUpmin, 6, 606);
            }

            new MiDialog(SettingActivity.this, MiDialog.MessageType).
                    builder().
                    setTitle("提示").
                    setMsg("设置完成,将启动自动打卡").
                    setOkButton("确定", new MiDialog.DialogCallBack() {
                        @Override
                        public void DialogCallBack(String connect) {
                            finish();
                        }
                    }).
                    show();
        } else {
            T.shortToast(SettingActivity.this, "时间逻辑有误");
        }
    }

}
