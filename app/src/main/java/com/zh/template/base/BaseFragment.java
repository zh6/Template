package com.zh.template.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.trello.rxlifecycle2.components.support.RxFragment;
import butterknife.ButterKnife;
import butterknife.Unbinder;
/**
 * BaseFragment简单封装
 */
public abstract class BaseFragment extends RxFragment {
    private Unbinder unbinder;
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(setLayout(), null);
        unbinder = ButterKnife.bind(this, view);
        return view;
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
}
