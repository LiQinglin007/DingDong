package com.li.xiaomi.xiaomilibrary.ui.bottom;

/**
 * 作者：dell or Xiaomi Li
 * 时间： 2018/3/28
 * 内容：底部菜单
 * 最后修改：
 */

public final class BottomTabBean {
    private final String BottomTv;//文字
    private final int BottomImgChecked;//选中的图片
    private final int BottomImgUnChecked;//未选中的图片

    public BottomTabBean(String bottomTv, int bottomImgChecked, int bottomImgUnChecked) {
        BottomTv = bottomTv;
        BottomImgChecked = bottomImgChecked;
        BottomImgUnChecked = bottomImgUnChecked;
    }


    public String getBottomTv() {
        return BottomTv;
    }

    public int getBottomImgChecked() {
        return BottomImgChecked;
    }

    public int getBottomImgUnChecked() {
        return BottomImgUnChecked;
    }
}
