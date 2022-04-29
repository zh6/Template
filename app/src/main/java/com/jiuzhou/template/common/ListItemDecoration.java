package com.jiuzhou.template.common;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;

import com.jiuzhou.template.R;


/**
 * <Pre>
 *     recycleview分割线
 * </Pre>
 */
public class ListItemDecoration extends RecyclerView.ItemDecoration {
    private Drawable mDrawable;
    private static final int DEFAULT_ORENTATION = LinearLayoutManager.VERTICAL;
    private int mOrentaion;

    public ListItemDecoration(Context context, int orentaion) {
        if (orentaion != LinearLayoutManager.HORIZONTAL && orentaion != LinearLayoutManager.VERTICAL) {
            this.mOrentaion = DEFAULT_ORENTATION;
        } else {
            this.mOrentaion = orentaion;
        }

        mDrawable = context.getDrawable(R.drawable.my_list_divider);
    }

    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        if (mOrentaion == LinearLayoutManager.HORIZONTAL) {
            drawHorizontal(c, parent);
        } else {
            drawVertical(c, parent);
        }
    }

    private void drawHorizontal(Canvas c, RecyclerView parent) {
        int top = parent.getPaddingTop();
        int bottom = parent.getHeight() - parent.getPaddingBottom();
        int childCount = parent.getChildCount();

        for (int i = 0; i < childCount; i++) {
            View chileView = parent.getChildAt(i);
            RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) chileView.getLayoutParams();

            int left = chileView.getRight() + params.rightMargin;
            int right = left + mDrawable.getIntrinsicHeight();
            mDrawable.setBounds(left, top, right, bottom);
            mDrawable.draw(c);

        }
    }

    private void drawVertical(Canvas c, RecyclerView parent) {
        int left = parent.getPaddingLeft();
        int right = parent.getWidth() - parent.getPaddingRight();

        int childCount = parent.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View child = parent.getChildAt(i);
            RecyclerView v = new RecyclerView(parent.getContext());
            RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child
                    .getLayoutParams();
            int top = child.getBottom() + params.bottomMargin;
            int bottom = top + mDrawable.getIntrinsicHeight();
            mDrawable.setBounds(left, top, right, bottom);
            mDrawable.draw(c);
        }
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        if (mOrentaion == LinearLayoutManager.VERTICAL) {
            outRect.set(0, 0, 0, mDrawable.getIntrinsicHeight());
        } else {
            outRect.set(0, 0, mDrawable.getIntrinsicWidth(), 0);
        }
    }
}
