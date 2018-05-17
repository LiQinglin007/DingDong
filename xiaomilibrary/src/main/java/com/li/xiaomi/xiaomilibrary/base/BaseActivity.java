package com.li.xiaomi.xiaomilibrary.base;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.gyf.barlibrary.ImmersionBar;
import com.li.xiaomi.xiaomilibrary.R;
import com.li.xiaomi.xiaomilibrary.ui.Loading.XiaomiLoader;

/**
 * 作者：dell or Xiaomi Li
 * 时间： 2018/3/31
 * 内容：
 * 最后修改：
 */

public abstract class BaseActivity<V, P extends BasePresenter<V>> extends AppCompatActivity implements PermissionChecker {
    protected final String TAG = this.getClass().getSimpleName();
    protected P mPersenter;
    private ImmersionBar mImmersionBar;//设置状态栏
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //这里可以用一个view来放一个布局   也可以用一个布局id来当一个布局
        if (setLayout() instanceof View) {
            if (((View) setLayout()) != null) {
                setContentView((View) setLayout());
                creatView(savedInstanceState);
            } else {
                throw new RuntimeException("setLayout type must be view or int !");
            }
        } else if (setLayout() instanceof Integer) {
            if (((Integer) setLayout()) != 0) {
                setContentView((Integer) setLayout());
                creatView(savedInstanceState);
            } else {
                throw new RuntimeException("setLayout type must be view or int !");
            }
        }

        if (translucentStatusBar()) {//设置透明(隐藏)状态栏
            //透明状态栏，不写默认透明色
            mImmersionBar = ImmersionBar.with(this).transparentStatusBar();
        } else {
            mImmersionBar = ImmersionBar.with(this)
                    .fitsSystemWindows(true)  //使用该属性,必须指定状态栏颜色
                    .statusBarColor(R.color.default_color);
        }
        mImmersionBar.init();   //所有子类都将继承这些相同的属性
    }

    private void creatView(@Nullable Bundle savedInstanceState) {
        initData();
        initView(savedInstanceState);
        if (createPersenter() != null) {
            mPersenter = createPersenter();
            mPersenter.attachView((V) this);
        }
    }

    /**
     * 设置布局/View
     *
     * @return
     */
    protected abstract Object setLayout();

    /**
     * 初始化Data
     */
    protected abstract void initData();


    /**
     * 初始化控件
     */
    protected abstract void initView(Bundle savedInstanceState);

    /**
     * 创建Persenter
     *
     * @return
     */
    protected abstract P createPersenter();

    /**
     * 时候隐藏顶部状态栏
     *
     * @return
     */
    protected abstract boolean translucentStatusBar();

    /**
     * 返回的切换动画
     */
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
        overridePendingTransition(R.anim.toleft, R.anim.infright);
    }

    /**
     * 处理切换动画
     *
     * @param intent
     */
    @Override
    public void startActivity(Intent intent) {
        super.startActivity(intent);
        overridePendingTransition(R.anim.in_from_right, R.anim.out_to_left);
    }


    @Override
    public void startActivityForResult(Intent intent, int requestCode) {
        super.startActivityForResult(intent, requestCode);
        overridePendingTransition(R.anim.toleft, R.anim.infright);
    }

    /**
     * 显示loading
     */
    protected void showLoading() {
        try {
            XiaomiLoader.showLoading(this, getResources().getColor(R.color.default_color));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 隐藏loading
     */
    protected void hineLoading() {
        XiaomiLoader.stopLoading();
    }

    /**
     * 解除引用
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mImmersionBar != null) {
            //必须调用该方法，防止内存泄漏
            mImmersionBar.destroy();
        }
        if (mPersenter != null) {
            mPersenter.detachView();
            mPersenter = null;
        }
    }
}
