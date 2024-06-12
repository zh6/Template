package com.jiuzhou.template.page;

import com.alibaba.fastjson.JSONObject;
import com.gyf.immersionbar.ImmersionBar;
import com.jiuzhou.template.R;
import com.jiuzhou.template.base.BaseActivity;
import com.jiuzhou.template.common.Constants;
import com.jiuzhou.template.net.service.BaseService;
import com.jiuzhou.template.net.use.BaseObserver;
import com.jiuzhou.template.net.use.BaseResponse;
import com.jiuzhou.template.utils.RxUtils;
import com.jiuzhou.template.utils.SpUtils;

public class LoginActivity extends BaseActivity {

    private boolean autoSignIn;

    @Override
    protected int layoutId() {
        return R.layout.activity_login;
    }

    @Override
    protected void initView() {
//        tv_version.setText("V" + DeviceUtils.getVersionName(this));
        ImmersionBar.with(this).reset().init();
        autoSignIn = SpUtils.USER.getBoolean(Constants.AUTOLOGIN, false);
//        cb_pwd.setChecked(autoSignIn);
//        if (autoSignIn) {
//            name.setText(SpUtils.USER.getString(Constants.NAME));
//            pwd.setText(SpUtils.USER.getString(Constants.PWD));
//            if (TextUtils.isEmpty(name.getText()) || TextUtils.isEmpty(pwd.getText())) {
//                return;
//            }
//            onClick(login);
//        }

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
