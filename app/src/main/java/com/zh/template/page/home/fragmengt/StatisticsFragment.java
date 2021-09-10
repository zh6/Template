package com.zh.template.page.home.fragmengt;

import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.zh.template.R;
import com.zh.template.base.BaseFragment;

import butterknife.BindView;

public class StatisticsFragment extends BaseFragment {
    @BindView(R.id.tv_rxbus)
    TextView tvRxBus;
    @BindView(R.id.img_one)
    ImageView imgOne;
    @BindView(R.id.img_two)
    ImageView imgTwo;
    @BindView(R.id.btn_six)
    Button btnSix;

    @Override
    protected int setLayout() {
        return R.layout.fragment_three;
    }

    @Override
    protected void initView() {

    }
    @Override
    protected void initData() {

    }


}
