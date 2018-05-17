package com.li.xiaomi.xiaomilibrary.ui.dialog;

import android.content.Context;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.LinearLayoutCompat;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.li.xiaomi.xiaomilibrary.R;


/**
 * 类描述：自己写了一个dialog
 * 作  者：Admin or 李小米
 * 时  间：2017/9/28
 * 修改备注：
 */
public class MiDialog {
    private Context context;
    private Display display;
    AlertDialog dialog;
    AlertDialog.Builder builder;
    private LinearLayoutCompat lLayout_bg;//整个布局
    private AppCompatTextView txt_title;
    private AppCompatTextView txt_msg;
    private AppCompatEditText edit_msg;

    private AppCompatTextView okBtn;
    private AppCompatTextView cannleBtn;

    private AppCompatTextView img_line;

    private LinearLayoutCompat line_ly;

    public static int EditType = 0x1;
    public static int MessageType = 0x2;

    int Type;

    private boolean showTitle = false;
    private boolean showMsg = false;
    private boolean showOkBtn = false;
    private boolean showCannleBtn = false;

    public MiDialog(Context context, int type) {
        this.context = context;
        this.Type = type;
        WindowManager windowManager = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);
        display = windowManager.getDefaultDisplay();
    }

    public MiDialog builder() {
        // 获取Dialog布局
        View view = LayoutInflater.from(context).inflate(
                R.layout.dialog_mi, null);

        // 获取自定义Dialog布局中的控件
        lLayout_bg = (LinearLayoutCompat) view.findViewById(R.id.lLayout_bg);
        txt_title = (AppCompatTextView) view.findViewById(R.id.txt_title);
        txt_title.setVisibility(View.GONE);
        txt_msg = (AppCompatTextView) view.findViewById(R.id.message_tv);
        txt_msg.setVisibility(View.GONE);
        edit_msg = (AppCompatEditText) view.findViewById(R.id.dialog_edit);
        edit_msg.setVisibility(View.GONE);

        okBtn = (AppCompatTextView) view.findViewById(R.id.dialog_ok);
        okBtn.setVisibility(View.GONE);


        cannleBtn = (AppCompatTextView) view.findViewById(R.id.dialog_cannle);
        cannleBtn.setVisibility(View.GONE);

        img_line = (AppCompatTextView) view.findViewById(R.id.img_line);
        img_line.setVisibility(View.GONE);

        line_ly = (LinearLayoutCompat) view.findViewById(R.id.line_ly);
        line_ly.setVisibility(View.GONE);

        // 定义Dialog布局和参数
        builder = new AlertDialog.Builder(context);
        builder.setView(view);
        dialog = builder.show();

        // 调整dialog背景大小
        lLayout_bg.setLayoutParams(new FrameLayout.LayoutParams((int) (display
                .getWidth() * 0.85), LinearLayout.LayoutParams.WRAP_CONTENT));

        return this;
    }

    public MiDialog setTitle(String title) {
        showTitle = true;
        if ("".equals(title)) {
            txt_title.setText("标题");
        } else {
            txt_title.setText(title);
        }
        return this;
    }

    public MiDialog setMsg(String msg) {
        showMsg = true;
        if (Type == EditType) {
            edit_msg.setVisibility(View.VISIBLE);
            txt_msg.setVisibility(View.GONE);
            if ("".equals(msg)) {
                edit_msg.setHint("内容");
            } else {
                edit_msg.setHint(msg);
            }
        } else {
            txt_msg.setVisibility(View.VISIBLE);
            edit_msg.setVisibility(View.GONE);
            if ("".equals(msg)) {
                txt_msg.setText("内容");
            } else {
                txt_msg.setText(msg);
            }
        }

        return this;
    }

    public MiDialog setOkButton(String text, final DialogCallBack listener) {
        showOkBtn = true;
        if ("".equals(text)) {
            okBtn.setText("确定");
        } else {
            okBtn.setText(text);
        }
        okBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (Type == EditType) {
                    listener.DialogCallBack(edit_msg.getText().toString());
                } else {
                    listener.DialogCallBack(null);
                }

                dialog.dismiss();
            }
        });
        return this;
    }

    public MiDialog setCannleButton(String text, final View.OnClickListener listener) {
        showCannleBtn = true;
        if ("".equals(text)) {
            cannleBtn.setText("取消");
        } else {
            cannleBtn.setText(text);
        }
        cannleBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onClick(v);
                }
                dialog.dismiss();
            }
        });
        return this;
    }

    private void setLayout() {
        if (!showTitle && !showMsg) {
            txt_title.setText("提示");
            txt_title.setVisibility(View.VISIBLE);
        }

        if (showTitle) {
            txt_title.setVisibility(View.VISIBLE);
        }

        if (showMsg) {
            txt_msg.setVisibility(View.VISIBLE);
        }


        if (showOkBtn && showCannleBtn) {
            okBtn.setVisibility(View.VISIBLE);
            cannleBtn.setVisibility(View.VISIBLE);
            img_line.setVisibility(View.VISIBLE);
            line_ly.setVisibility(View.VISIBLE);
        }

        if (showOkBtn && !showCannleBtn) {
            okBtn.setVisibility(View.VISIBLE);
            line_ly.setVisibility(View.VISIBLE);
        }

        if (!showOkBtn && showCannleBtn) {
            cannleBtn.setVisibility(View.VISIBLE);
            line_ly.setVisibility(View.VISIBLE);
        }

        if (Type == EditType) {
            edit_msg.setVisibility(View.VISIBLE);
            txt_msg.setVisibility(View.GONE);
        } else {
            edit_msg.setVisibility(View.GONE);
            txt_msg.setVisibility(View.VISIBLE);
        }

    }

    public void show() {
        setLayout();
        dialog.show();
    }

    /**
     * popupwindow 消失后的回调
     */
    public interface DialogCallBack {
        void DialogCallBack(String connect);
    }
}
