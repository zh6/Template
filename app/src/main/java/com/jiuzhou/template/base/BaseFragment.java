package com.jiuzhou.template.base;

import android.graphics.Rect;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.jiuzhou.template.utils.ScreenUtils;
import com.trello.rxlifecycle4.components.support.RxFragment;

import androidx.annotation.Nullable;

/**
 * BaseFragment简单封装
 */
public abstract class BaseFragment extends RxFragment {
    private View mRootView;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (null != mRootView) {
            ViewGroup parent = (ViewGroup) mRootView.getParent();
            if (null != parent) {
                parent.removeView(mRootView);
            }
        } else {
            mRootView = inflater.inflate(setLayout(), null);
        }
        return mRootView;
    }

    @Override
    public View getView() {
        return mRootView;
    }

    protected abstract int setLayout();

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView();
        initData();
    }

    protected abstract void initView();

    protected abstract void initData();

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    public void KeyboardOcclusion(View view) {
        View decorView = getActivity().getWindow().getDecorView();
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
                            ScreenUtils.dip2px(getActivity(), 60));
                    params.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
                    view.setLayoutParams(params);
                }
            }
        });
    }
}
