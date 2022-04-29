package com.jiuzhou.template.page;

import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.gyf.immersionbar.ImmersionBar;
import com.jiuzhou.template.R;
import com.jiuzhou.template.base.BaseActivity;
import com.jiuzhou.template.base.MyApplication;
import com.jiuzhou.template.common.Constants;
import com.jiuzhou.template.entity.SysModuleEntity;
import com.jiuzhou.template.utils.GlideUtils;
import com.jiuzhou.template.utils.LiveDataBus;
import com.jiuzhou.template.utils.SpUtils;
import com.jiuzhou.template.utils.ToastUtils;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.OnClick;

public class HomeActivity extends BaseActivity {
    @BindView(R.id.tv_name)
    TextView tv_name;
    @BindView(R.id.tv_mobile)
    TextView tv_mobile;
    @BindView(R.id.tv_total)
    TextView tv_total;
    @BindView(R.id.img_head)
    ImageView img_head;
    @BindView(R.id.recyclerview)
    RecyclerView mRecyclerView;//列表
    private BaseQuickAdapter<SysModuleEntity, BaseViewHolder> adapter;//万能适配器
    private List<SysModuleEntity> list = new ArrayList<>(); //数据源

    @Override
    protected int layoutId() {
        return R.layout.activity_home;
    }

    @Override
    protected void initView() {
        ImmersionBar.with(this).reset().init();
        mRecyclerView.setLayoutManager(new GridLayoutManager(this, 3));
        adapter = new BaseQuickAdapter<SysModuleEntity, BaseViewHolder>(R.layout.item_home, list) {
            @Override
            protected void convert(@NotNull BaseViewHolder holder, SysModuleEntity entity) {
                holder.setText(R.id.tv_name, entity.getName());
            }
        };
        //绑定适配器
        mRecyclerView.setAdapter(adapter);
    }

    @Override
    protected void initData() {
        LiveDataBus.get().with("user").observe(this, new Observer<Object>() {
            @Override
            public void onChanged(Object o) {
                tv_name.setText(SpUtils.USER.getString(Constants.NICKNAME));
                String headUrl = (SpUtils.USER.getString(Constants.FILEPATH));
                if (!TextUtils.isEmpty(headUrl)) {
                    GlideUtils.loadCircleImage(HomeActivity.this, headUrl, img_head, 80, 80);
                } else {
                    img_head.setImageResource(R.drawable.home_head_default);
                }
            }
        });
        tv_name.setText(SpUtils.USER.getString(Constants.NICKNAME));
        tv_mobile.setText(SpUtils.USER.getString(Constants.MOBILE));
        String headUrl = (SpUtils.USER.getString(Constants.FILEPATH));
        if (!TextUtils.isEmpty(headUrl)) {
            GlideUtils.loadCircleImage(this, headUrl, img_head, 80, 80);
        }
        List<SysModuleEntity> sys = JSON.parseArray(SpUtils.USER.getString(Constants.SYS), SysModuleEntity.class);
        if (sys != null) {
            adapter.setList(sys);
        }
        adapter.setOnItemClickListener((adapter1, view, position) -> {
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
    }


    @OnClick({R.id.fl_msg, R.id.fl_todolist, R.id.img_setting})
    public void onClick(View view) {

    }

    @Override
    public void onBackPressed() {
        if (!isFastDoubleClick()) {
            ToastUtils.showShort(getString(R.string.double_exit));
        } else {
            //彻底关闭整个APP
            MyApplication.getAppContext().AppExit();
        }
    }
}
