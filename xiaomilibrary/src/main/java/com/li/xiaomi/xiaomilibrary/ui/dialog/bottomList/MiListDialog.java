package com.li.xiaomi.xiaomilibrary.ui.dialog.bottomList;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.li.xiaomi.xiaomilibrary.R;
import com.li.xiaomi.xiaomilibrary.utils.ScreenUtils;

/**
 * 作者：dell or Xiaomi Li
 * 时间： 2018/4/13
 * 内容：
 * 最后修改：
 */

public class MiListDialog<Adapter extends BaseAdapter> {
    public static final int MILIST_DIALOG_BOTTOM = Gravity.BOTTOM;
    public static final int MILIST_DIALOG_CENTER = Gravity.CENTER;

    public void showDialog(final Context mContext, String title, Adapter adapter, int gravity, final OnListBottomDialogCallback onListBottomDialogCallback) {
        final Dialog dialog = new Dialog(mContext, R.style.LoadingDialog);
        View dialogView = LayoutInflater.from(mContext)
                .inflate(R.layout.dialog_bottom_list, null);

        TextView tvTitle = (TextView) dialogView.findViewById(R.id.dialog_bottom_title);
        tvTitle.setText(title);
        ListView listData = dialogView.findViewById(R.id.dialog_bottom_listview);
        listData.setAdapter(adapter);
        listData.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                onListBottomDialogCallback.onListCallback(position);
                dialog.dismiss();
            }
        });

        dialog.setContentView(dialogView);

        Window window = dialog.getWindow();
        WindowManager.LayoutParams lp = window.getAttributes();
        //设置对话框的宽度
        lp.width = gravity == Gravity.BOTTOM ? ScreenUtils.getScreenWidth(mContext) : ScreenUtils.getScreenWidth(mContext) / 5 * 4;
        //设置对话框的高度
        lp.height = adapter.getCount() < 7 ? LinearLayout.LayoutParams.WRAP_CONTENT : (ScreenUtils.getScreenHeight(mContext) / 2);
//        lp.alpha = 0.6f;
        dialog.getWindow().setGravity(gravity == Gravity.BOTTOM ? gravity : Gravity.CENTER);
        dialog.getWindow().setAttributes(lp);

        dialog.show();
    }

    public interface OnListBottomDialogCallback {
        void onListCallback(int position);
    }
}
