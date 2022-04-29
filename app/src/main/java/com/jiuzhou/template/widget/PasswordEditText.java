package com.jiuzhou.template.widget;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.jiuzhou.template.R;
import com.jiuzhou.template.utils.ScreenUtils;

import androidx.appcompat.widget.AppCompatEditText;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.drawable.DrawableCompat;

/**
 * 密码隐藏显示
 */
public final class PasswordEditText extends AppCompatEditText
        implements View.OnTouchListener,
        View.OnFocusChangeListener, TextWatcher {

    private static final int TYPE_VISIBLE = InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD;
    private static final int TYPE_INVISIBLE = InputType.TYPE_TEXT_VARIATION_PASSWORD;

    private Drawable mCurrentDrawable;
    private Drawable mVisibleDrawable;
    private Drawable mInvisibleDrawable;

    private View.OnTouchListener mOnTouchListener;
    private View.OnFocusChangeListener mOnFocusChangeListener;

    public PasswordEditText(Context context) {
        super(context);
        init(context,null);
    }

    public PasswordEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context,attrs);
    }

    public PasswordEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context,attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        // Wrap the drawable so that it can be tinted pre Lollipop
        mVisibleDrawable = DrawableCompat.wrap(ContextCompat.getDrawable(context, R.drawable.ic_input_show));
//        mVisibleDrawable.setBounds(0, 0, mVisibleDrawable.getIntrinsicWidth(), mVisibleDrawable.getIntrinsicHeight());
        mVisibleDrawable.setBounds(0, 0, ScreenUtils.dip2px(context,18), ScreenUtils.dip2px(context,18));
        mInvisibleDrawable = DrawableCompat.wrap(ContextCompat.getDrawable(context, R.drawable.ic_input_hide));
//        mInvisibleDrawable.setBounds(0, 0, mInvisibleDrawable.getIntrinsicWidth(), mInvisibleDrawable.getIntrinsicHeight());
        mInvisibleDrawable.setBounds(0, 0, ScreenUtils.dip2px(context,18), ScreenUtils.dip2px(context,18));
        mCurrentDrawable = mVisibleDrawable;

        // 密码不可见
        addInputType(TYPE_INVISIBLE);
        setDrawableVisible(false);
        super.setOnTouchListener(this);
        super.setOnFocusChangeListener(this);
        super.addTextChangedListener(this);
    }
    private void setDrawableVisible(final boolean visible) {
        if (mCurrentDrawable.isVisible() == visible) {
            return;
        }

        mCurrentDrawable.setVisible(visible, false);
        final Drawable[] drawables = getCompoundDrawables();
        setCompoundDrawables(
                drawables[0],
                drawables[1],
                visible ? mCurrentDrawable : null,
                drawables[3]);
    }

    private void refreshDrawableStatus() {
        final Drawable[] drawables = getCompoundDrawables();
        setCompoundDrawables(
                drawables[0],
                drawables[1],
                mCurrentDrawable,
                drawables[3]);
    }

    @Override
    public void setOnFocusChangeListener(final View.OnFocusChangeListener onFocusChangeListener) {
        mOnFocusChangeListener = onFocusChangeListener;
    }

    @Override
    public void setOnTouchListener(final View.OnTouchListener onTouchListener) {
        mOnTouchListener = onTouchListener;
    }

    /**
     * {@link View.OnFocusChangeListener}
     */

    @Override
    public void onFocusChange(final View view, final boolean hasFocus) {
        if (hasFocus && getText() != null) {
            setDrawableVisible(getText().length() > 0);
        } else {
            setDrawableVisible(false);
        }
        if (mOnFocusChangeListener != null) {
            mOnFocusChangeListener.onFocusChange(view, hasFocus);
        }
    }

    /**
     * {@link View.OnTouchListener}
     */

    @Override
    public boolean onTouch(final View view, final MotionEvent motionEvent) {
        final int x = (int) motionEvent.getX();
        if (mCurrentDrawable.isVisible() && x > getWidth() - getPaddingRight() - mCurrentDrawable.getIntrinsicWidth()) {
            if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                if (mCurrentDrawable == mVisibleDrawable) {
                    mCurrentDrawable = mInvisibleDrawable;
                    // 密码可见
                    removeInputType(TYPE_INVISIBLE);
                    addInputType(TYPE_VISIBLE);
                    refreshDrawableStatus();
                } else if (mCurrentDrawable == mInvisibleDrawable) {
                    mCurrentDrawable = mVisibleDrawable;
                    // 密码不可见
                    removeInputType(TYPE_VISIBLE);
                    addInputType(TYPE_INVISIBLE);
                    refreshDrawableStatus();
                }
                Editable editable = getText();
                if (editable != null) {
                    setSelection(editable.toString().length());
                }
            }
            return true;
        }
        return mOnTouchListener != null && mOnTouchListener.onTouch(view, motionEvent);
    }

    /**
     * {@link TextWatcher}
     */

    @Override
    public void onTextChanged(final CharSequence s, final int start, final int before, final int count) {
        if (isFocused()) {
            setDrawableVisible(s.length() > 0);
        }
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
    }

    @Override
    public void afterTextChanged(Editable s) {
    }
    /**
     * 添加一个输入标记
     */
    public void addInputType(int type) {
        setInputType(getInputType() | type);
    }

    /**
     * 移除一个输入标记
     */
    public void removeInputType(int type) {
        setInputType(getInputType() & ~type);
    }
}