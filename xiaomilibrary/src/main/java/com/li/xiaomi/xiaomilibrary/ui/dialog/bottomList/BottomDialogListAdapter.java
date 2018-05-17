package com.li.xiaomi.xiaomilibrary.ui.dialog.bottomList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.li.xiaomi.xiaomilibrary.R;

import java.util.ArrayList;

/**
 * 作者：dell or Xiaomi Li
 * 时间： 2018/4/13
 * 内容：单项选择器的adapter
 * 最后修改：2018.4.29  修改参数类型，可以只传递ArrayList<String>进来,单独的String集合不用再封装Bean了.
 */

public class BottomDialogListAdapter<T> extends BaseAdapter {

    private final Context mContext;
    private ArrayList<T> mList = new ArrayList<>();

    public BottomDialogListAdapter(Context context) {
        mContext = context;
    }

    /**
     * 设置数据源
     *
     * @param mDataList 允许参数类型  String类型/实现了MiListInterface接口的bean对象
     */
    public void setList(ArrayList<T> mDataList) {
        mList.clear();
        mList.addAll(mDataList);
        notifyDataSetChanged();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View itemView = LayoutInflater.from(mContext).inflate(R.layout.item_bottom_list, null);
        TextView textView = itemView.findViewById(R.id.item_item_bottom_text);
        if (mList.get(position) instanceof MiListInterface) {
            textView.setText(((MiListInterface) mList.get(position)).getString());
        } else if (mList.get(position) instanceof String) {
            textView.setText((String) mList.get(position));
        } else {
            new RuntimeException("mList type must String or implements MiListInterface !");
        }
        return itemView;
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

}
