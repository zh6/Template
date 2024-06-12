package com.jiuzhou.template.page;

import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.fastjson.JSON;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.gyf.immersionbar.ImmersionBar;
import com.jiuzhou.template.R;
import com.jiuzhou.template.base.BaseActivity;
import com.jiuzhou.template.base.MyApplication;
import com.jiuzhou.template.common.Constants;
import com.jiuzhou.template.entity.UserEntity;
import com.jiuzhou.template.utils.GlideUtils;
import com.jiuzhou.template.utils.LiveDataBus;
import com.jiuzhou.template.utils.SpUtils;
import com.jiuzhou.template.utils.ToastUtils;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends BaseActivity {
    TextView tv_name;
    TextView tv_mobile;
    TextView tv_total;
    ImageView img_head;
    RecyclerView mRecyclerView;//列表
    private BaseQuickAdapter<UserEntity, BaseViewHolder> adapter;//万能适配器
    List<UserEntity> list;

    @Override
    protected int layoutId() {
        return R.layout.activity_home;
    }

    @Override
    protected void initView() {
        ImmersionBar.with(this).reset().init();
        tv_name = findViewById(R.id.tv_name);
        tv_mobile = findViewById(R.id.tv_mobile);
        tv_total = findViewById(R.id.tv_total);
        img_head = findViewById(R.id.img_head);
        mRecyclerView = findViewById(R.id.recyclerview);
        mRecyclerView.setLayoutManager(new GridLayoutManager(this, 3));
        adapter = new BaseQuickAdapter<UserEntity, BaseViewHolder>(R.layout.item_home, list) {
            @Override
            protected void convert(@NotNull BaseViewHolder holder, UserEntity entity) {
            }
        };
        //绑定适配器
        mRecyclerView.setAdapter(adapter);
    }

    @Override
    protected void initData() {
        LiveDataBus.get().with("user").observe(this, o -> {
            tv_name.setText(SpUtils.USER.getString(Constants.NICKNAME));
            String headUrl = (SpUtils.USER.getString(Constants.FILEPATH));
            if (!TextUtils.isEmpty(headUrl)) {
                GlideUtils.loadCircleImage(HomeActivity.this, headUrl, img_head, 80, 80);
            } else {
                img_head.setImageResource(R.drawable.home_head_default);
            }
        });
        tv_name.setText(SpUtils.USER.getString(Constants.NICKNAME));
        tv_mobile.setText(SpUtils.USER.getString(Constants.MOBILE));
        String headUrl = (SpUtils.USER.getString(Constants.FILEPATH));
        if (!TextUtils.isEmpty(headUrl)) {
            GlideUtils.loadCircleImage(this, headUrl, img_head, 80, 80);
        }
        List<UserEntity> sys = JSON.parseArray(SpUtils.USER.getString(Constants.SYS), UserEntity.class);
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
