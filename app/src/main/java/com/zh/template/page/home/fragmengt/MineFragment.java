package com.zh.template.page.home.fragmengt;

import android.view.View;

import com.zh.template.R;
import com.zh.template.base.BaseFragment;

import butterknife.OnClick;

public class MineFragment extends BaseFragment {
    @Override
    protected int setLayout() {
        return R.layout.fragment_four;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {

    }

    @OnClick({R.id.btn_xc, R.id.btn_pz})
    void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_xc:

                break;
            case R.id.btn_pz:

                break;
        }
    }




}
