package com.li.xiaomi.xiaomilibrary.utils.recycler;

import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.li.xiaomi.xiaomilibrary.R;


/**
 * 类描述：最后一个item的加载动画
 * 作  者：李清林
 * 时  间：
 * 使  用：1、private ItemAnimHelper itemAnimhelper = new ItemAnimHelper();
 *         2、itemAnimhelper.showItemAnim(holder.linearLayout, position);
 * 修改备注：
 */
public class ItemAnimHelper {

    private static int mLastPosition = -1;

    public static void showItemAnim(final View view, final int position) {
        if (position > mLastPosition) {
            Animation animation = AnimationUtils.loadAnimation(view.getContext(), R.anim.recycler_item_bottom_in);
            animation.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {
                    view.setAlpha(1);
                }

                @Override
                public void onAnimationEnd(Animation animation) {
                }

                @Override
                public void onAnimationRepeat(Animation animation) {
                }
            });
            view.startAnimation(animation);
            mLastPosition = position;
        }
    }
}
