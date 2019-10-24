package com.zh.template.base;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;

import com.zh.template.utils.StatusManager;
import com.zh.template.widget.LoadingDialog;
import com.zh.template.utils.InputUtils;
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;

import androidx.annotation.DrawableRes;
import androidx.annotation.StringRes;
import butterknife.ButterKnife;

public abstract class BaseActivity extends RxAppCompatActivity {
    private InputMethodManager manager;  //系统输入法相关
    private static String TAG = BaseActivity.class.getSimpleName();
    private long lastClickTime;
    public LoadingDialog loadingView;
    private StatusManager mStatusManager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(layoutId());
        manager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        ButterKnife.bind(this);
        loadingView = new LoadingDialog(this);
        mStatusManager = new StatusManager();
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
    public void showDialog(LoadingDialog loadingView) {
        if (loadingView != null && !loadingView.isShowing()) {
            loadingView.show();
        }
    }

    /**
     * 关闭加载动画
     *
     * @param loadingView
     */
    public void closeDialog(LoadingDialog loadingView) {
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

    /**
     * 显示空提示
     */
    public void showEmpty() {
        mStatusManager.showEmpty(getContentView());
    }

    /**
     * 显示错误提示
     */
    public void showError() {
        mStatusManager.showError(getContentView());
    }

    /**
     * 显示自定义提示
     */
    public void showLayout(@DrawableRes int drawableId, @StringRes int stringId) {
        mStatusManager.showLayout(getContentView(), drawableId, stringId);
    }
    /**
     * 隐藏提示
     */
    public void  hideLayout(){
        mStatusManager.hideLayout();
    }
    /**
     * 和 setContentView 对应的方法
     */
    public ViewGroup getContentView() {
        return findViewById(Window.ID_ANDROID_CONTENT);
    }
}
