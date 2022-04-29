package com.jiuzhou.template.widget;

import android.app.Activity;
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

import java.util.List;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import razerdp.basepopup.BasePopupWindow;
import razerdp.util.animation.AnimationHelper;
import razerdp.util.animation.TranslationConfig;

/**
 * *租控图popup
 * *@author zhaohui
 * *@time 2019/8/2 13:33
 **/
public class RentControlPopup extends BasePopupWindow {
    private Activity context;
    private BaseQuickAdapter<BasePopEntity, BaseViewHolder> adapter1,adapter2,adapter3;
    private List<BasePopEntity> list1,list2,list3;
    TextView tv_left, tv_right;//tv_startDate, tv_endDate,
    private Long lease,temporary,useStatus;

    public RentControlPopup(Activity context, List<BasePopEntity> list1,List<BasePopEntity> list2,List<BasePopEntity> list3) {
        super(context);
        setContentView(createPopupById(R.layout.popup_rent_control));
        setPopupGravity(Gravity.RIGHT);
        this.context = context;
        this.list1 = list1;
        this.list2 = list2;
        this.list3 = list3;
        initView();
    }

    void initView() {
        RecyclerView mRecyclerView1 = findViewById(R.id.recyclerview1);
        mRecyclerView1.setLayoutManager(new GridLayoutManager(context, 3));
        mRecyclerView1.addItemDecoration(new GridSpacingItemDecoration(3,
                ScreenUtils.dip2px(context, 10), false));

        adapter1 = new BaseQuickAdapter<BasePopEntity, BaseViewHolder>(R.layout.item_status_text, list1) {
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
        mRecyclerView1.setAdapter(adapter1);
        adapter1.setOnItemClickListener((adapters, view, position) -> {
            for (BasePopEntity item : adapter1.getData()) {
                item.setCheck(false);
            }
            adapter1.getData().get(position).setCheck(true);
            lease = adapter1.getData().get(position).getId();
            adapter1.notifyDataSetChanged();
        });
        RecyclerView mRecyclerView2 = findViewById(R.id.recyclerview2);
        mRecyclerView2.setLayoutManager(new GridLayoutManager(context, 3));
        mRecyclerView2.addItemDecoration(new GridSpacingItemDecoration(3,
                ScreenUtils.dip2px(context, 10), false));

        adapter2 = new BaseQuickAdapter<BasePopEntity, BaseViewHolder>(R.layout.item_status_text, list2) {
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
        mRecyclerView2.setAdapter(adapter2);
        adapter2.setOnItemClickListener((adapters, view, position) -> {
            for (BasePopEntity item : adapter2.getData()) {
                item.setCheck(false);
            }
            adapter2.getData().get(position).setCheck(true);
            temporary = adapter2.getData().get(position).getId();
            adapter2.notifyDataSetChanged();
        });
        RecyclerView mRecyclerView3 = findViewById(R.id.recyclerview3);
        mRecyclerView3.setLayoutManager(new GridLayoutManager(context, 3));
        mRecyclerView3.addItemDecoration(new GridSpacingItemDecoration(3,
                ScreenUtils.dip2px(context, 10), false));

        adapter3 = new BaseQuickAdapter<BasePopEntity, BaseViewHolder>(R.layout.item_status_text, list3) {
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
        mRecyclerView3.setAdapter(adapter3);
        adapter3.setOnItemClickListener((adapters, view, position) -> {
            for (BasePopEntity item : adapter3.getData()) {
                item.setCheck(false);
            }
            adapter3.getData().get(position).setCheck(true);
            useStatus = adapter3.getData().get(position).getId();
            adapter3.notifyDataSetChanged();
        });
//        tv_startDate = findViewById(R.id.tv_startDate);
//        tv_endDate = findViewById(R.id.tv_endDate);
//        tv_startDate.setOnClickListener(v -> {
//            //获取实例，包含当前年月日
//            Calendar calendar = Calendar.getInstance();
//            DatePickerDialog dialog = new DatePickerDialog(context, (view, year, month, dayOfMonth) -> {
//                String desc = String.format("%d-%02d-%02d", year, month + 1, dayOfMonth);
//                tv_startDate.setText(desc);
//            },
//                    calendar.get(Calendar.YEAR),
//                    calendar.get(Calendar.MARCH),
//                    calendar.get(Calendar.DAY_OF_MONTH));
//            dialog.show();
//        });
//        tv_endDate.setOnClickListener(v -> {
//            //获取实例，包含当前年月日
//            Calendar calendar = Calendar.getInstance();
//            DatePickerDialog dialog = new DatePickerDialog(context, (view, year, month, dayOfMonth) -> {
//                String desc = String.format("%d-%02d-%02d", year, month + 1, dayOfMonth);
//                tv_endDate.setText(desc);
//            },
//                    calendar.get(Calendar.YEAR),
//                    calendar.get(Calendar.MARCH),
//                    calendar.get(Calendar.DAY_OF_MONTH));
//            dialog.show();
//        });
        tv_left = findViewById(R.id.tv_left);
        tv_right = findViewById(R.id.tv_right);
        tv_left.setOnClickListener(v -> {
//            tv_startDate.setText("开始日期");
//            tv_endDate.setText("结束日期");
            for (BasePopEntity item : adapter1.getData()) {
                item.setCheck(false);
            }
            for (BasePopEntity item : adapter2.getData()) {
                item.setCheck(false);
            }
            for (BasePopEntity item : adapter3.getData()) {
                item.setCheck(false);
            }
            lease = null;
            temporary = null;
            useStatus = null;
            adapter1.notifyDataSetChanged();
            adapter2.notifyDataSetChanged();
            adapter3.notifyDataSetChanged();
            onButtonClickListener.onResetButtonClick();
        });
        tv_right.setOnClickListener(v -> {
            dismiss();
//            String startDate = tv_startDate.getText().toString().equals("开始日期") ? "" : tv_startDate.getText().toString();
//            String endDate = tv_endDate.getText().toString().equals("结束日期") ? "" : tv_endDate.getText().toString();
            onButtonClickListener.onConfirmButtonClick(lease, temporary, useStatus);
        });
    }
    public  void  reset(){
        for (BasePopEntity item : adapter1.getData()) {
            item.setCheck(false);
        }
        for (BasePopEntity item : adapter2.getData()) {
            item.setCheck(false);
        }
        for (BasePopEntity item : adapter3.getData()) {
            item.setCheck(false);
        }
        adapter1.notifyDataSetChanged();
        adapter2.notifyDataSetChanged();
        adapter3.notifyDataSetChanged();
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
        void onConfirmButtonClick(Long status1, Long status2, Long status3);

        void onResetButtonClick();
    }
}
