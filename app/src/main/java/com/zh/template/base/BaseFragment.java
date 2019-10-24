package com.zh.template.base;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import com.trello.rxlifecycle2.components.support.RxFragment;
import com.zh.template.utils.StatusManager;

import androidx.annotation.DrawableRes;
import androidx.annotation.StringRes;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * BaseFragment简单封装
 */
public abstract class BaseFragment extends RxFragment {
    private Unbinder unbinder;
    private StatusManager mStatusManager;
    /**
     * 根布局
     */
    private View mRootView;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (mRootView == null && setLayout() > 0) {
            mRootView = inflater.inflate(setLayout(), null);
        }

        ViewGroup parent = (ViewGroup) mRootView.getParent();
        if (parent != null) {
            parent.removeView(mRootView);
        }
        mStatusManager = new StatusManager();
        unbinder = ButterKnife.bind(this, mRootView);
        return mRootView;
    }

    @Override
    public View getView() {
        return mRootView;
    }

    /**
     * 绑定布局
     */
    protected abstract int setLayout();

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView();
    }

    /**
     * 页面
     */
    protected abstract void initView();

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initData();
    }

    /**
     * 数据
     */
    protected abstract void initData();

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    /**
     * 显示空提示
     */
    public void showEmpty() {
        mStatusManager.showEmpty(getView());
    }

    /**
     * 显示错误提示
     */
    public void showError() {
        mStatusManager.showError(getView());
    }

    /**
     * 显示自定义提示
     */
    public void showLayout(@DrawableRes int drawableId, @StringRes int stringId) {
        mStatusManager.showLayout(getView(), drawableId, stringId);
    }
    /**
     * 隐藏提示
     */
    public void  hideLayout(){
        mStatusManager.hideLayout();
    }
}
