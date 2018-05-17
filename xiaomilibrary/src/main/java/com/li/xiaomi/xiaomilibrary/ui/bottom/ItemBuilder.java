package com.li.xiaomi.xiaomilibrary.ui.bottom;

import android.support.v4.app.Fragment;

import java.util.LinkedHashMap;

/**
 * 作者：dell or Xiaomi Li
 * 时间： 2018/3/28
 * 内容：添加bottomTabBean的工具类
 * 最后修改：
 */

public class ItemBuilder {

    private final LinkedHashMap<BottomTabBean, Fragment> ITEMS = new LinkedHashMap<>();

    static ItemBuilder builder() {
        return new ItemBuilder();
    }

    public final ItemBuilder addItem(BottomTabBean bottomTabBean, Fragment latteDelegate) {
        ITEMS.put(bottomTabBean, latteDelegate);
        return this;
    }

    public final ItemBuilder addItems(LinkedHashMap<BottomTabBean, Fragment> items) {
        ITEMS.putAll(items);
        return this;
    }

    public final LinkedHashMap<BottomTabBean, Fragment> build() {
        return ITEMS;
    }

}
