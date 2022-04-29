package com.jiuzhou.template.base;

import android.app.Activity;
import android.content.Context;
import android.graphics.Rect;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.RelativeLayout;

import com.gyf.immersionbar.ImmersionBar;
import com.jiuzhou.template.R;
import com.jiuzhou.template.utils.InputUtils;
import com.jiuzhou.template.utils.ScreenUtils;
import com.jiuzhou.template.widget.LoadingDialog;
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;

import androidx.annotation.Nullable;
import butterknife.ButterKnife;

public abstract class BaseActivity extends RxAppCompatActivity {
    private InputMethodManager manager;  //系统输入法相关
    public LoadingDialog loadingView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(layoutId());
        loadingView = new LoadingDialog(this);
        initImmersionBar();
        MyApplication.getAppContext().addActivity(this);
        manager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        ButterKnife.bind(this);
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
        MyApplication.getAppContext().finishActivity(this);
        InputUtils.fixInputMethodManagerLeak(this);
        closeDialog();
    }

    /**
     * 初始化沉浸式
     * Init immersion bar.
     */
    protected void initImmersionBar() {
        //设置共同沉浸式样式
        ImmersionBar.with(this).statusBarColor(R.color.white).navigationBarColor(R.color.black).statusBarDarkFont(true).fitsSystemWindows(true).init();
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

    //点击空白处消失
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            if (getCurrentFocus() != null && getCurrentFocus().getWindowToken() != null) {
                manager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
            }
        }
        return super.dispatchTouchEvent(ev);
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

    public static boolean isFastDoubleClick() {
        long time = System.currentTimeMillis();
        if (time - lastClickTime < 1000) {
            return true;
        }
        lastClickTime = time;
        return false;

    }


    public void KeyboardOcclusion(View view) {
        View decorView = getWindow().getDecorView();
        decorView.getViewTreeObserver().addOnGlobalLayoutListener(() -> {
            Rect rect = new Rect();
            //获取窗体的可视区域
            decorView.getWindowVisibleDisplayFrame(rect);
            //获取不可视区域高度，
            //在键盘没有弹起时，main.getRootView().getHeight()调节度应该和rect.bottom高度一样
            int mainInvisibleHeight = decorView.getRootView().getHeight() - rect.bottom;
            int screenHeight = decorView.getRootView().getHeight();//屏幕高度
            //不可见区域大于屏幕本身高度的1/4
            if (mainInvisibleHeight > screenHeight / 4) {
                if (view.getHeight() != 0) {//因为onGlobalLayout方法会频繁回调，这里要判断下，不重复设置
                    RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT,
                            0);
                    params.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
                    view.setLayoutParams(params);
                }
            } else {
                if (view.getHeight() == 0) {//因为onGlobalLayout方法会频繁回调，这里要判断下，不重复设置
                    RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT,
                            ScreenUtils.dip2px(this, 60));
                    params.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
                    view.setLayoutParams(params);
                }
            }
        });
    }
}
