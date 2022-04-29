package com.jiuzhou.template.page;

import android.content.Intent;
import android.os.Handler;
import android.text.TextUtils;

import com.jiuzhou.template.R;
import com.jiuzhou.template.base.BaseActivity;
import com.jiuzhou.template.common.Constants;
import com.jiuzhou.template.utils.SpUtils;

public class SplashActivity extends BaseActivity {
    @Override
    protected int layoutId() {
        return R.layout.activity_splash;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {
        new Handler().postDelayed(() -> {
            startIntent();
        }, 3000);
    }

    private void startIntent() {
        String token = SpUtils.USER.getString(Constants.TOKEN);
        String sn = SpUtils.SYSTEM.getString(Constants.SNCODE);
        if (TextUtils.isEmpty(token) || TextUtils.isEmpty(sn)) {
            startActivity(new Intent(this, LoginActivity.class));
        } else {
            startActivity(new Intent(this, HomeActivity.class));
        }
        finish();
    }
}