package com.zh.template.base;

import android.app.Activity;
import android.graphics.Color;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.view.animation.Animation;
import android.widget.ImageView;
import android.widget.TextView;

import com.zh.template.R;
import com.zh.template.common.ListItemDecoration;
import com.zh.template.module.main.entity.BasePopEntity;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.MultiItemTypeAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.List;

import razerdp.basepopup.BasePopupWindow;

/**
 * *基本列表popup
 * *@author zhaohui
 * *@time 2019/8/2 13:33
 * * ----------Dragon be here!----------/
 * * 　　　┏┓　　 ┏┓
 * * 　　┏┛┻━━━┛┻┓━━━
 * * 　　┃　　　　　 ┃
 * * 　　┃　　　━　  ┃
 * * 　　┃　┳┛　┗┳
 * * 　　┃　　　　　 ┃
 * * 　　┃　　　┻　  ┃
 * * 　　┃　　　　   ┃
 * * 　　┗━┓　　　┏━┛Code is far away from bug with the animal protecting
 * * 　　　　┃　　　┃    神兽保佑,代码无bug
 * * 　　　　┃　　　┃
 * * 　　　　┃　　　┗━━━┓
 * * 　　　　┃　　　　　　┣┓
 * * 　　　　┃　　　　　　　┏┛
 * * 　　　　┗┓┓┏━┳┓┏┛━━━━━
 * * 　　　　　┃┫┫　┃┫┫
 * * 　　　　　┗┻┛　┗┻┛
 * * ━━━━━━━━━━━神兽出没━━━━━━━━━━━━━━
 */
public class BaseFormPopup extends BasePopupWindow {
    private Activity context;
    private CommonAdapter<BasePopEntity> adapter;
    private List<BasePopEntity> list;
    private OnItemClickListener mOnItemClickListener;//条目点击监听
    private int thisPosition = 0;
    public BaseFormPopup(Activity context, List<BasePopEntity> list) {
        super(context);
        //设置显示底部
        setPopupGravity(Gravity.BOTTOM);
        //设置点击屏幕外部不消失
//        setOutSideDismiss(false);
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
        TextView title = findViewById(R.id.tv_title);
        title.setText("请选择");
        ImageView close = findViewById(R.id.ic_close);
        close.setOnClickListener(onClick->dismiss());
        RecyclerView mRecyclerView = findViewById(R.id.recyclerview);
        //设置分割线
        mRecyclerView.addItemDecoration(new ListItemDecoration(context, LinearLayoutManager.VERTICAL));
        mRecyclerView.setLayoutManager(new LinearLayoutManager(context));
        adapter = new CommonAdapter<BasePopEntity>(context, R.layout.item_base_text, list) {
            @Override
            protected void convert(ViewHolder holder, BasePopEntity basePopEntity, int position) {
                holder.setText(R.id.name, basePopEntity.name);
                if (position == thisPosition) {
                    holder.setTextColor(R.id.name,Color.parseColor("#23A389"));
                } else {
                    holder.setTextColor(R.id.name,Color.parseColor("#333333"));
                }
            }
        };
        mRecyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
                dismiss();
                if (mOnItemClickListener != null) {
                    thisPosition = position;
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
        return getTranslateVerticalAnimation(1f, 0, 500);
    }

    @Override
    protected Animation onCreateDismissAnimation() {
        return getTranslateVerticalAnimation(0, 1f, 300);
    }

    @Override
    public View onCreateContentView() {
        return createPopupById(R.layout.popup_base);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.mOnItemClickListener = onItemClickListener;
    }

    //点击条目接口
    public interface OnItemClickListener {
        void onItemClick(View view, BasePopEntity entity);
    }
}
