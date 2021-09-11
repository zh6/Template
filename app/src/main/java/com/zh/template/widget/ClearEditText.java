package com.zh.template.widget;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.zh.template.R;

import androidx.appcompat.widget.AppCompatEditText;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.drawable.DrawableCompat;

/**
 * 带清除按钮的 EditText
 */
public final class ClearEditText extends AppCompatEditText
        implements View.OnTouchListener,
        View.OnFocusChangeListener, TextWatcher {

    private Drawable mClearDrawable;

    private OnTouchListener mOnTouchListener;
    private OnFocusChangeListener mOnFocusChangeListener;

    public ClearEditText(Context context) {
        super(context);
        init(context,null);
    }

    public ClearEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context,attrs);
    }

    public ClearEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context,attrs);
    }
    private void init(Context context, AttributeSet attrs) {
        // Wrap the drawable so that it can be tinted pre Lollipop
        mClearDrawable = DrawableCompat.wrap(ContextCompat.getDrawable(context, R.drawable.my_icon_delete_gray));
        mClearDrawable.setBounds(0, 0, mClearDrawable.getIntrinsicWidth(), mClearDrawable.getIntrinsicHeight());
        setDrawableVisible(false);
        super.setOnTouchListener(this);
        super.setOnFocusChangeListener(this);
        super.addTextChangedListener(this);
    }
    private ClearListener clearListener;
    public interface ClearListener {
        void deleteState();
    }
    public void setClearListener(ClearListener l) {
        this.clearListener = l;
    }
    private void setDrawableVisible(final boolean visible) {
        if (mClearDrawable.isVisible() == visible) {
            return;
        }

        mClearDrawable.setVisible(visible, false);
        final Drawable[] drawables = getCompoundDrawables();
        setCompoundDrawables(
                drawables[0],
                drawables[1],
                visible ? mClearDrawable : null,
                drawables[3]);
    }

    @Override
    public void setOnFocusChangeListener(final OnFocusChangeListener onFocusChangeListener) {
        mOnFocusChangeListener = onFocusChangeListener;
    }

    @Override
    public void setOnTouchListener(final OnTouchListener onTouchListener) {
        mOnTouchListener = onTouchListener;
    }

    /**
     * {@link OnFocusChangeListener}
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
     * {@link OnTouchListener}
     */

    @Override
    public boolean onTouch(final View view, final MotionEvent motionEvent) {
        final int x = (int) motionEvent.getX();
        if (mClearDrawable.isVisible() && x > getWidth() - getPaddingRight() - mClearDrawable.getIntrinsicWidth()) {
            if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                setText("");
                if (clearListener != null) {
                    clearListener.deleteState();
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
}