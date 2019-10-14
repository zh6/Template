package com.zh.template.base;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.zh.template.widget.LoadingView;
import com.zh.template.utils.InputUtils;
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;

import butterknife.ButterKnife;

public abstract class BaseActivity extends RxAppCompatActivity {
    private InputMethodManager manager;  //系统输入法相关
    private static String TAG = BaseActivity.class.getSimpleName();
    private long lastClickTime;
    public LoadingView loadingView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(layoutId());
        manager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        ButterKnife.bind(this);
        loadingView = new LoadingView(this);
        initView();
        initData();
    }

    protected abstract int layoutId();

    //初始化控件
    protected abstract void initView();

    //初始化数据
    protected abstract void initData();

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        InputUtils.fixInputMethodManagerLeak(this);
        closeDialog(loadingView);
    }

    /**
     * 隐藏软键盘
     */
    public void hideSoftKeyBord() {
        View v = getCurrentFocus();
        if (v == null)
            return;
        InputMethodManager imm = (InputMethodManager) this
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(v.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
    }

    /**
     * 点击空白处，软键盘关闭
     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            if (getCurrentFocus() != null && getCurrentFocus().getWindowToken() != null) {
                manager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
            }
        }
        return super.onTouchEvent(event);
    }

    /**
     * 管理输入软键盘（如果仍然显示，就将其关掉）
     */
    protected void dissmissSoftKeyboard(Activity activity) {
        try {
            InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 显示加载动画
     *
     * @param loadingView
     */
    public void showDialog(LoadingView loadingView) {
        if (loadingView != null && !loadingView.isShowing()) {
            loadingView.show();
        }
    }

    /**
     * 关闭加载动画
     *
     * @param loadingView
     */
    public void closeDialog(LoadingView loadingView) {
        if (loadingView != null && loadingView.isShowing()) {
            loadingView.dismiss();
        }
    }

    /**
     * 判断事件出发时间间隔是否超过预定值
     *
     * @return
     */
    public boolean isFastDoubleClick() {
        long time = System.currentTimeMillis();
        long timeD = time - lastClickTime;
        if (0 < timeD && timeD < 1000) {
            return true;
        }
        lastClickTime = time;
        return false;
    }
}
