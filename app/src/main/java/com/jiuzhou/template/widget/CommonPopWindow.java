package com.jiuzhou.template.widget;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.FloatRange;
import android.support.annotation.LayoutRes;
import android.support.annotation.StyleRes;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.PopupWindow;
public class CommonPopWindow {
    private static PopupWindow mPopupWindow;
    private static Builder mBuilder;
    private static View mContentView;
    private static Window mWindow;

    public interface ViewClickListener {
        void getChildView(PopupWindow mPopupWindow, View view, int mLayoutResId);
    }

    private CommonPopWindow() {
        mBuilder = new Builder();
    }

    public static Builder newBuilder() {
        if (mBuilder == null) {
            mBuilder = new Builder();
        }
        return mBuilder;
    }

    /**
     * 获取PopupWindow宽度
     *
     * @return
     */
    public int getWidth() {
        if (mPopupWindow != null) {
            return mContentView.getMeasuredWidth();
        }
        return 0;
    }


    /**
     * 获取PopupWindow高度
     *
     * @return
     */
    public int getHeight() {
        if (mPopupWindow != null) {
            return mContentView.getMeasuredHeight();
        }
        return 0;
    }

    /**
     * 显示在控件的下方
     */
    public CommonPopWindow showDownPop(View parent) {
        if (parent.getVisibility() == View.GONE) {
            mPopupWindow.showAtLocation(parent, Gravity.NO_GRAVITY, 0, 0);
        } else {
            int[] location = new int[2];
            parent.getLocationOnScreen(location);
            if (mPopupWindow != null) {
                mPopupWindow.showAtLocation(parent, Gravity.NO_GRAVITY, location[0], location[1] + parent.getHeight());
            }
        }
        return this;
    }

    /**
     * 显示在控件的上方
     */
    public CommonPopWindow showAsUp(View view) {
        if (view.getVisibility() == View.GONE) {
            mPopupWindow.showAtLocation(view, Gravity.NO_GRAVITY, 0, 0);
        } else {
            int[] location = new int[2];
            view.getLocationOnScreen(location);
            if (mPopupWindow != null) {
                mPopupWindow.showAtLocation(view, Gravity.NO_GRAVITY, location[0], location[1] - view.getHeight());
                //方式二
//                mPopupWindow.showAsDropDown(view, 0, -(getHeight() + view.getMeasuredHeight()));
            }
        }
        return this;
    }

    /**
     * 显示在控件的左边
     */
    public CommonPopWindow showAsLeft(View view) {
        if (view.getVisibility() == View.GONE) {
            mPopupWindow.showAtLocation(view, Gravity.NO_GRAVITY, 0, 0);
        } else {
            int[] location = new int[2];
            view.getLocationOnScreen(location);
            if (mPopupWindow != null) {
                mPopupWindow.showAtLocation(view, Gravity.NO_GRAVITY, location[0] - getWidth(), location[1]);
            }
        }
        return this;
    }

    /**
     * 显示在控件右边
     */
    public CommonPopWindow showAsRight(View view) {
        if (view.getVisibility() == View.GONE) {
            mPopupWindow.showAtLocation(view, Gravity.NO_GRAVITY, 0, 0);
        } else {
            int[] location = new int[2];
            view.getLocationOnScreen(location);
            if (mPopupWindow != null) {
                mPopupWindow.showAtLocation(view, Gravity.NO_GRAVITY, location[0] + view.getWidth(), location[1]);
            }
        }
        return this;
    }

    /**
     * 显示控件下方
     *
     * @param view
     * @return
     */
    public CommonPopWindow showAsDown(View view) {
        if (mPopupWindow != null) {
            mPopupWindow.showAsDropDown(view);
        }
        return this;
    }

    /**
     * 全屏弹出
     */
    public CommonPopWindow showAsBottom(View view) {
        if (view.getVisibility() == View.GONE) {
            mPopupWindow.showAtLocation(view, Gravity.NO_GRAVITY, 0, 0);
        } else {
            int[] location = new int[2];
            view.getLocationOnScreen(location);
            if (mPopupWindow != null) {
                mPopupWindow.showAtLocation(view, Gravity.BOTTOM, 0, 0);
            }
        }
        return this;
    }

    public CommonPopWindow showAtLocation(View anchor, int gravity, int x, int y) {
        if (mPopupWindow != null) {
            mPopupWindow.showAtLocation(anchor, gravity, x, y);
        }
        return this;
    }

    /**
     * 取消
     */
    public static void dismiss() {
        if (mWindow != null) {
            WindowManager.LayoutParams params = mWindow.getAttributes();
            params.alpha = 1.0f;
            mWindow.setAttributes(params);
        }
        if (mPopupWindow != null && mPopupWindow.isShowing())
            mPopupWindow.dismiss();
    }

    /*
     * ---------------------Builder-------------------------
     */
    public static class Builder implements PopupWindow.OnDismissListener {
        private Context mContext;
        private int mLayoutResId;//布局ID
        private int mWidth, mHeight;//弹窗宽高
        private int mAnimationStyle;//动画样式
        private ViewClickListener mListener;//子View监听回调
        private Drawable mDrawable;//背景Drawable
        private boolean mTouchable = true;//是否相应touch事件
        private boolean mFocusable = true;//是否获取焦点
        private boolean mOutsideTouchable = true;//设置外部是否点击
        private boolean mBackgroundDarkEnable = false;//是否背景窗体变暗
        private float mDarkAlpha = 1.0f;//透明值

        public CommonPopWindow build(Context context) {
            this.mContext = context;
            CommonPopWindow popWindow = new CommonPopWindow();
            apply();
            if (mListener != null && mLayoutResId != 0) {
                mListener.getChildView(mPopupWindow, mContentView, mLayoutResId);
            }
            return popWindow;
        }

        private void apply() {
            if (mLayoutResId != 0) {
                mContentView = LayoutInflater.from(mContext).inflate(mLayoutResId, null);
            }
            if (mWidth != 0 && mHeight != 0) {
                mPopupWindow = new PopupWindow(mContentView, mWidth, mHeight);
            } else {
                mPopupWindow = new PopupWindow(mContentView, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            }

            mPopupWindow.setTouchable(mTouchable);
            mPopupWindow.setFocusable(mFocusable);
            mPopupWindow.setOutsideTouchable(mOutsideTouchable);

            if (mDrawable != null) {
                mPopupWindow.setBackgroundDrawable(mDrawable);
            } else {
                mPopupWindow.setBackgroundDrawable(new ColorDrawable());
            }
            if (mAnimationStyle != -1) {
                mPopupWindow.setAnimationStyle(mAnimationStyle);
            }
            if (mWidth == 0 || mHeight == 0) {
                measureWidthAndHeight(mContentView);
                //如果没有设置高度的情况下，设置宽高并赋值
                mWidth = mPopupWindow.getContentView().getMeasuredWidth();
                mHeight = mPopupWindow.getContentView().getMeasuredHeight();
            }

            Activity activity = (Activity) mContext;
            if (activity != null && mBackgroundDarkEnable) {
                float alpha = (mDarkAlpha >= 0f || mDarkAlpha <= 1f) ? mDarkAlpha : 0.7f;
                mWindow = activity.getWindow();
                WindowManager.LayoutParams params = mWindow.getAttributes();
                params.alpha = alpha;
                mWindow.setAttributes(params);
            }

            mPopupWindow.setOnDismissListener(this);

            mPopupWindow.update();
        }

        @Override
        public void onDismiss() {
            dismiss();
        }

        /**
         * 测量View的宽高
         *
         * @param mContentView
         */
        private void measureWidthAndHeight(View mContentView) {
            int widthMeasureSpec = View.MeasureSpec.makeMeasureSpec((1 << 30) - 1, View.MeasureSpec.AT_MOST);
            int heightMeasureSpec = View.MeasureSpec.makeMeasureSpec((1 << 30) - 1, View.MeasureSpec.AT_MOST);
            mContentView.measure(widthMeasureSpec, heightMeasureSpec);
        }

        /**
         * 设置布局ID
         *
         * @param layoutResId
         * @return
         */
        public Builder setView(@LayoutRes int layoutResId) {
            mContentView = null;
            this.mLayoutResId = layoutResId;
            return this;
        }

        /**
         * 设置宽高
         *
         * @param width
         * @param height
         * @return
         */
        public Builder setSize(int width, int height) {
            mWidth = width;
            mHeight = height;
            return this;
        }

        /**
         * 设置背景
         *
         * @param drawable
         * @return
         */
        public Builder setBackgroundDrawable(Drawable drawable) {
            mDrawable = drawable;
            return this;
        }

        /**
         * 设置背景是否变暗
         *
         * @param darkEnable
         * @return
         */
        public Builder setBackgroundDarkEnable(boolean darkEnable) {
            mBackgroundDarkEnable = darkEnable;
            return this;
        }

        /**
         * 设置背景透明度
         *
         * @param dackAlpha
         * @return
         */
        public Builder setBackgroundAlpha(@FloatRange(from = 0.0, to = 1.0) float dackAlpha) {
            mDarkAlpha = dackAlpha;
            return this;
        }

        /**
         * 是否点击Outside消失
         *
         * @param touchable
         * @return
         */
        public Builder setOutsideTouchable(boolean touchable) {
            mOutsideTouchable = touchable;
            return this;
        }

        /**
         * 是否设置Touch事件
         *
         * @param touchable
         * @return
         */
        public Builder setTouchable(boolean touchable) {
            mTouchable = touchable;
            return this;
        }

        /**
         * 设置动画
         *
         * @param animationStyle
         * @return
         */
        public Builder setAnimationStyle(@StyleRes int animationStyle) {
            mAnimationStyle = animationStyle;
            return this;
        }

        /**
         * 是否设置获取焦点
         *
         * @param focusable
         * @return
         */
        public Builder setFocusable(boolean focusable) {
            mFocusable = focusable;
            return this;
        }

        /**
         * 设置子View点击事件回调
         *
         * @param listener
         * @return
         */
        public Builder setViewOnClickListener(ViewClickListener listener) {
            this.mListener = listener;
            return this;
        }
    }
}
