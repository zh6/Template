package com.jiuzhou.template.module.main.ui;

import android.app.ActivityManager;
import android.content.Intent;

import com.jiuzhou.template.R;
import com.jiuzhou.template.base.BaseActivity;
import com.jiuzhou.template.common.MsgEvent;
import com.jiuzhou.template.utils.RxBus;
import com.jiuzhou.template.utils.ToastUtils;

public class HomeActivity extends BaseActivity {
    @Override
    protected int layoutId() {
        return R.layout.activity_home;
    }

    @Override
    protected void initView() {
        RxBus.getInstance().toObservable(this,MsgEvent.class).doOnNext(msgEvent -> {
            if (msgEvent.getMsg().equals("newUser")) {
                initData();
            }
        }).subscribe();
    }

    @Override
    protected void initData() {

    }
    @Override
    public void onBackPressed() {
        if (!isFastDoubleClick()) {
            ToastUtils.showShort(getString(R.string.double_exit));
        } else {
            //彻底关闭整个APP
            int currentVersion = android.os.Build.VERSION.SDK_INT;
            if (currentVersion > android.os.Build.VERSION_CODES.ECLAIR_MR1) {
                Intent startMain = new Intent(Intent.ACTION_MAIN);
                startMain.addCategory(Intent.CATEGORY_HOME);
                startMain.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(startMain);
                System.exit(0);
            } else {// android2.1
                ActivityManager am = (ActivityManager) getSystemService(ACTIVITY_SERVICE);
                am.restartPackage(getPackageName());
            }
        }
    }
}
