package com.jiuzhou.template.base;

import android.content.Context;
import android.graphics.Rect;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.RelativeLayout;

import androidx.annotation.Nullable;

import com.gyf.immersionbar.ImmersionBar;
import com.jiuzhou.template.R;
import com.jiuzhou.template.utils.InputUtils;
import com.jiuzhou.template.utils.ScreenUtils;
import com.jiuzhou.template.widget.LoadingDialog;
import com.trello.rxlifecycle4.components.support.RxAppCompatActivity;

public abstract class BaseActivity extends RxAppCompatActivity {

    private InputMethodManager manager;  // 系统输入法
    protected LoadingDialog loadingView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(layoutId());
        loadingView = new LoadingDialog(this);
        initImmersionBar();
        MyApplication.getAppContext().addActivity(this);
        manager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        initView();
        initData();
    }

    // 返回布局ID
    protected abstract int layoutId();

    // 初始化控件
    protected abstract void initView();

    // 初始化数据
    protected abstract void initData();

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        MyApplication.getAppContext().finishActivity(this);
//        InputUtils.fixInputMethodManagerLeak(this);
        closeDialog();
    }

    /**
     * 初始化沉浸式
     */
    protected void initImmersionBar() {
        ImmersionBar.with(this)
                .statusBarColor(R.color.white)
                .navigationBarColor(R.color.black)
                .statusBarDarkFont(true)
                .fitsSystemWindows(true)
                .init();
    }

    // 处理点击空白区域隐藏软键盘的逻辑
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            // 获取当前焦点
            View view = getCurrentFocus();
            if (view != null && manager != null) {
                // 隐藏软键盘
                manager.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
            }
        }
        return super.dispatchTouchEvent(ev);
    }

    /**
     * 显示加载动画
     */
    public void showDialog() {
        if (loadingView != null && !loadingView.isShowing()) {
            loadingView.show();
        }
    }

    /**
     * 关闭加载动画
     */
    public void closeDialog() {
        if (loadingView != null && loadingView.isShowing()) {
            loadingView.dismiss();
        }
    }

    private static long lastClickTime;

    /**
     * 防止快速双击
     */
    public static boolean isFastDoubleClick() {
        long time = System.currentTimeMillis();
        if (time - lastClickTime < 1000) {
            return true;
        }
        lastClickTime = time;
        return false;
    }

    /**
     * 处理软键盘遮挡
     */
    public void KeyboardOcclusion(View view) {
        View decorView = getWindow().getDecorView();
        decorView.getViewTreeObserver().addOnGlobalLayoutListener(() -> {
            Rect rect = new Rect();
            // 获取窗体的可视区域
            decorView.getWindowVisibleDisplayFrame(rect);
            // 计算不可视区域高度
            int mainInvisibleHeight = decorView.getRootView().getHeight() - rect.bottom;
            int screenHeight = decorView.getRootView().getHeight();
            // 不可见区域大于屏幕高度的1/4时，即键盘弹出
            if (mainInvisibleHeight > screenHeight / 4) {
                if (view.getHeight() != 0) {
                    // 设置view的高度为0
                    RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, 0);
                    params.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
                    view.setLayoutParams(params);
                }
            } else {
                if (view.getHeight() == 0) {
                    // 恢复view的高度
                    RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, ScreenUtils.dip2px(this, 60));
                    params.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
                    view.setLayoutParams(params);
                }
            }
        });
    }
}