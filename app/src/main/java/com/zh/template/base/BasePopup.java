package com.zh.template.base;

import android.app.Activity;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.view.animation.Animation;
import android.widget.ImageView;
import android.widget.TextView;

import com.zh.template.R;
import com.zh.template.entity.BasePopEntity;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.MultiItemTypeAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.List;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import razerdp.basepopup.BasePopupWindow;
import razerdp.util.animation.AnimationHelper;
import razerdp.util.animation.TranslationConfig;

/**
 * *基本列表popup
 * *@author zhaohui
 * *@time 2019/8/2 13:33
 **/
public class BasePopup extends BasePopupWindow {
    private Activity context;
    private CommonAdapter<BasePopEntity> adapter;
    private List<BasePopEntity> list;
    private OnItemClickListener mOnItemClickListener;//条目点击监听

    public BasePopup(Activity context, List<BasePopEntity> list) {
        super(context);
        setContentView(createPopupById(R.layout.popup_base));
        setPopupGravity(Gravity.BOTTOM);
        this.context = context;
        this.list = list;
        initView();
    }

    //刷新数据
    public void refresh(List<BasePopEntity> newList) {
        if (list != null) {
            list.clear();
        }
        list.addAll(newList);
        adapter.notifyDataSetChanged();
    }

    void initView() {
        TextView tv_title = findViewById(R.id.tv_title);
        tv_title.setText("请选择");
        ImageView img_close = findViewById(R.id.img_close);
        img_close.setOnClickListener(onClick -> dismiss());
        RecyclerView mRecyclerView = findViewById(R.id.recyclerview);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(context));
        adapter = new CommonAdapter<BasePopEntity>(context, R.layout.item_base_text, list) {
            @Override
            protected void convert(ViewHolder holder, BasePopEntity basePopEntity, int position) {
                holder.setText(R.id.name, basePopEntity.getName());
                if (basePopEntity.isCheck()) {
                    holder.setTextColor(R.id.name, Color.parseColor("#00B95B"));
                } else {
                    holder.setTextColor(R.id.name, Color.parseColor("#333333"));
                }
            }
        };
        mRecyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
                dismiss();
                if (mOnItemClickListener != null) {
                    for (BasePopEntity item : adapter.getDatas()) {
                        item.setCheck(false);
                    }
                    adapter.getDatas().get(position).setCheck(true);
                    adapter.notifyDataSetChanged();
                    mOnItemClickListener.onItemClick(view, adapter.getDatas().get(position));
                }
            }

            @Override
            public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
                return false;
            }
        });
    }

    @Override
    protected Animation onCreateShowAnimation() {
        return AnimationHelper.asAnimation()
                .withTranslation(TranslationConfig.FROM_BOTTOM)
                .toShow();
    }

    @Override
    protected Animation onCreateDismissAnimation() {
        return AnimationHelper.asAnimation()
                .withTranslation(TranslationConfig.TO_BOTTOM)
                .toShow();
    }
    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.mOnItemClickListener = onItemClickListener;
    }

    //点击条目接口
    public interface OnItemClickListener {
        void onItemClick(View view, BasePopEntity entity);
    }
}
