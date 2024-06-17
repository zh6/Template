package com.jiuzhou.template.page;

import com.alibaba.fastjson.JSONObject;
import com.gyf.immersionbar.ImmersionBar;
import com.jiuzhou.template.R;
import com.jiuzhou.template.base.BaseActivity;
import com.jiuzhou.template.net.service.BaseService;
import com.jiuzhou.template.net.use.BaseObserver;
import com.jiuzhou.template.net.use.BaseResponse;
import com.jiuzhou.template.utils.RxUtils;

public class LoginActivity extends BaseActivity {
    @Override
    protected int layoutId() {
        return R.layout.activity_login;
    }

    @Override
    protected void initView() {
        ImmersionBar.with(this).reset().init();
    }

    @Override
    protected void initData() {
        RxUtils.applySchedulersWithActivityLifecycle(BaseService.getInstance().getSysLogo("PDA_CONFIG", "main_logo"), this).subscribe(new BaseObserver<BaseResponse<JSONObject>>() {
            @Override
            public void onSuccess(BaseResponse<JSONObject> response) {

            }
        });
    }

}
