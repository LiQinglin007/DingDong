package com.li.xiaomi.xiaomilibrary.base;

import java.lang.ref.Reference;
import java.lang.ref.WeakReference;

/**
 * 作者：dell or Xiaomi Li
 * 时间： 2018/4/1
 * 内容：
 * 最后修改：
 */

public abstract class BasePresenter<T> {
    protected final String TAG=this.getClass().getSimpleName();
    protected Reference<T> mViewRefer;//View接口类型的弱引用

    //建立连接
    public void attachView(T view) {
        mViewRefer = new WeakReference<T>(view);
    }

    protected T getView() {//在Presenter中获取View接口的引用
        return mViewRefer.get();
    }

    /**
     * 判断有没有引用view
     *
     * @return
     */
    public boolean isViewAttached() {
        return mViewRefer != null && mViewRefer.get() != null;
    }

    /**
     * 解除引用
     */
    public void detachView() {
        if (mViewRefer != null) {
            mViewRefer.clear();
            mViewRefer = null;
        }
    }


}
