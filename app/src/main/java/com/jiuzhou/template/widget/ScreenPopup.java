package com.jiuzhou.template.widget;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.graphics.Color;
import android.view.Gravity;
import android.view.animation.Animation;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.jiuzhou.template.R;
import com.jiuzhou.template.entity.BasePopEntity;
import com.jiuzhou.template.utils.ScreenUtils;
import com.luck.picture.lib.decoration.GridSpacingItemDecoration;

import java.util.Calendar;
import java.util.List;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import razerdp.basepopup.BasePopupWindow;
import razerdp.util.animation.AnimationHelper;
import razerdp.util.animation.TranslationConfig;

/**
 * *基本列表popup
 * *@author zhaohui
 * *@time 2019/8/2 13:33
 **/
public class ScreenPopup extends BasePopupWindow {
    private Activity context;
    private BaseQuickAdapter<BasePopEntity, BaseViewHolder> adapter;
    private List<BasePopEntity> list;
    TextView tv_startDate, tv_endDate, tv_left, tv_right;
    private Long state;

    public ScreenPopup(Activity context, List<BasePopEntity> list) {
        super(context);
        setContentView(createPopupById(R.layout.popup_screen));
        setOverlayStatusbar(true);
        setPopupGravity(Gravity.RIGHT);
        this.context = context;
        this.list = list;
        initView();
    }

    void initView() {
        RecyclerView mRecyclerView = findViewById(R.id.recyclerview);
        mRecyclerView.setLayoutManager(new GridLayoutManager(context, 3));
        mRecyclerView.addItemDecoration(new GridSpacingItemDecoration(3,
                ScreenUtils.dip2px(context, 10), false));
        adapter = new BaseQuickAdapter<BasePopEntity, BaseViewHolder>(R.layout.item_status_text, list) {
            @Override
            protected void convert(BaseViewHolder holder, BasePopEntity basePopEntity) {
                holder.setText(R.id.name, basePopEntity.getName());
                if (basePopEntity.isCheck()) {
                    holder.setTextColor(R.id.name, Color.parseColor("#FFFFFF"));
                    holder.setBackgroundResource(R.id.name, R.drawable.my_btn_green_solid);
                } else {
                    holder.setTextColor(R.id.name, Color.parseColor("#333333"));
                    holder.setBackgroundResource(R.id.name, R.drawable.my_btn_gray_solid);
                }
            }
        };
        mRecyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener((adapters, view, position) -> {
            for (BasePopEntity item : adapter.getData()) {
                item.setCheck(false);
            }
            adapter.getData().get(position).setCheck(true);
            state = adapter.getData().get(position).getId();
            adapter.notifyDataSetChanged();
        });
        tv_startDate = findViewById(R.id.tv_startDate);
        tv_endDate = findViewById(R.id.tv_endDate);
        tv_startDate.setOnClickListener(v -> {
            //获取实例，包含当前年月日
            Calendar calendar = Calendar.getInstance();
            DatePickerDialog dialog = new DatePickerDialog(context, (view, year, month, dayOfMonth) -> {
                String desc = String.format("%d-%02d-%02d", year, month + 1, dayOfMonth);
                tv_startDate.setText(desc);
            },
                    calendar.get(Calendar.YEAR),
                    calendar.get(Calendar.MARCH),
                    calendar.get(Calendar.DAY_OF_MONTH));
            dialog.show();
        });
        tv_endDate.setOnClickListener(v -> {
            //获取实例，包含当前年月日
            Calendar calendar = Calendar.getInstance();
            DatePickerDialog dialog = new DatePickerDialog(context, (view, year, month, dayOfMonth) -> {
                String desc = String.format("%d-%02d-%02d", year, month + 1, dayOfMonth);
                tv_endDate.setText(desc);
            },
                    calendar.get(Calendar.YEAR),
                    calendar.get(Calendar.MARCH),
                    calendar.get(Calendar.DAY_OF_MONTH));
            dialog.show();
        });
        tv_left = findViewById(R.id.tv_left);
        tv_right = findViewById(R.id.tv_right);
        tv_left.setOnClickListener(v -> {
            tv_startDate.setText("开始日期");
            tv_endDate.setText("结束日期");
            for (BasePopEntity item : adapter.getData()) {
                item.setCheck(false);
            }
            adapter.notifyDataSetChanged();
            state = null;
            onButtonClickListener.onResetButtonClick();
        });
        tv_right.setOnClickListener(v -> {
            dismiss();
            String startDate = tv_startDate.getText().toString().equals("开始日期") ? "" : tv_startDate.getText().toString();
            String endDate = tv_endDate.getText().toString().equals("结束日期") ? "" : tv_endDate.getText().toString();
            onButtonClickListener.onConfirmButtonClick(state, startDate, endDate);
        });
    }

    @Override
    protected Animation onCreateShowAnimation() {
        return AnimationHelper.asAnimation()
                .withTranslation(TranslationConfig.FROM_RIGHT)
                .toShow();
    }

    @Override
    protected Animation onCreateDismissAnimation() {
        return AnimationHelper.asAnimation()
                .withTranslation(TranslationConfig.TO_RIGHT)
                .toShow();
    }

    public void setOnButtonClickListener(OnButtonClickListener onButtonClickListener) {
        this.onButtonClickListener = onButtonClickListener;
    }

    private OnButtonClickListener onButtonClickListener;

    public interface OnButtonClickListener {
        void onConfirmButtonClick(Long status, String startDate, String endDate);

        void onResetButtonClick();
    }
}
