package com.zh.template.module.main.ui;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import com.zh.template.R;
import com.zh.template.base.BaseActivity;
import com.zh.template.network.RetrofitService;
import com.zh.template.utils.RxUtils;
import com.zh.template.utils.SharedPreferenceUtils;
import com.zh.template.utils.ToastUtils;
import com.zh.template.widget.CustomDialog;

import java.util.regex.Pattern;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.Observable;

public class SettingsActivity extends BaseActivity {
    @BindView(R.id.title)
    TextView title;

    @Override
    protected int layoutId() {
        return R.layout.activity_settings;
    }

    @Override
    protected void initView() {
        title.setText("账号设置");
    }

    @Override
    protected void initData() {

    }

    @OnClick({R.id.back, R.id.logout, R.id.rl_pwd, R.id.rl_version, R.id.rl_personal, R.id.rl_store})
    public void OnClick(View view) {
        CustomDialog dialogUtil = CustomDialog.getInstance();
        switch (view.getId()) {
            case R.id.back:
                onBackPressed();
                break;
            case R.id.rl_version:
                startActivity(new Intent(SettingsActivity.this, VersionActivity.class));
                break;
            case R.id.rl_personal:
                break;
            case R.id.rl_store:
                break;
            case R.id.logout:
                dialogUtil.showConfirmDialog(this, "确定要退出当前登录账号吗？");
                //按钮点击监听
                dialogUtil.setOnButtonClickListener(new CustomDialog.OnButtonClickListener() {
                    @Override
                    public void onConfirmButtonClick(Dialog dialog) {
                        dialog.dismiss();
                        SharedPreferenceUtils.saveUserInfo(SettingsActivity.this, SharedPreferenceUtils.getUserInfo(SettingsActivity.this).get(0), "");
                        Intent intent = new Intent(SettingsActivity.this, LoginActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP); //关键的一句，将新的activity置为栈顶
                        startActivity(intent);
                        finish();
                    }

                    @Override
                    public void onCancelButtonClick(Dialog dialog) {
                        dialog.dismiss();
                    }
                });
                break;
//            case R.id.rl_username:
//                dialogUtil.showEditTextDialog(this, "请输入用户名");
//                dialogUtil.setOnEditTextClickListener(new CustomDialog.OnEditTextClickListener() {
//                    @Override
//                    public void onPositiveButtonClick(Dialog dialog, String msg) {
//                        if ("".equals(msg)) {
//                            ToastUtils.showShort("用户名不能为空！");
//                            return;
//                        }
//                        if(msg.length()>16){
//                            ToastUtils.showShort("用户名长度最多16位！");
//                            return;
//                        }
//                        update(null, null, null, msg).doOnError(throwable ->{
//                                ToastUtils.showShort(throwable.getMessage());
//                        }).doOnNext(res -> {
//                            dialog.dismiss();
//                            ToastUtils.showShort("修改成功！");
//                            username.setText(msg);
//                            SharedPreferenceUtils.saveUser(SettingsActivity.this, msg, "", "", "");
//                            RxBus.getDefault().post(new MsgEvent("user"));
//                        }).subscribe();
//                    }
//                });
//
//                break;
//            case R.id.rl_phone:
//                dialogUtil.showEditTextDialog(this, "请输入手机号码");
//                dialogUtil.setOnEditTextClickListener(new CustomDialog.OnEditTextClickListener() {
//                    @Override
//                    public void onPositiveButtonClick(Dialog dialog, String msg) {
//                        if(!Pattern.matches("^((13[0-9])|(14[5|7])|(15([0-3]|[5-9]))|(18[0,5-9]))\\\\d{8}$",msg)){
//                            ToastUtils.showShort("请输入正确的手机号！");
//                            return;
//                        }
//                        update(msg, null, null, null).doOnError(throwable -> {
//                            ToastUtils.showShort(throwable.getMessage());
//                        }).doOnNext(res -> {
//                            dialog.dismiss();
//                            ToastUtils.showShort("修改成功！");
//                            phone.setText(msg);
//                            SharedPreferenceUtils.saveUserInfo(SettingsActivity.this, msg, SharedPreferenceUtils.getUserInfo(SettingsActivity.this).get(1));
//                            SharedPreferenceUtils.saveUser(SettingsActivity.this, "", "", "", msg);
//
//                        }).subscribe();
//                    }
//                });
//                break;
            case R.id.rl_pwd:
                dialogUtil.showEditTextDialog(this, "请输入密码");
                dialogUtil.setOnEditTextClickListener(new CustomDialog.OnEditTextClickListener() {
                    @Override
                    public void onConfirmButtonClick(Dialog dialog, String msg) {
                        if (!Pattern.matches("^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{6,16}$", msg)) {
                            ToastUtils.showShort("密码由6-16位数字和字母的组合！");
                            return;
                        }
                        updatePwd(msg).doOnError(throwable -> {
                            ToastUtils.showShort(throwable.getMessage());
                        }).doOnNext(res -> {
                            dialog.dismiss();
                            ToastUtils.showShort("修改成功！");
                            SharedPreferenceUtils.saveUserInfo(SettingsActivity.this, SharedPreferenceUtils.getUserInfo(SettingsActivity.this).get(0), msg);
                            startActivity(new Intent(SettingsActivity.this, LoginActivity.class));

                        }).subscribe();
                    }

                    @Override
                    public void onCancelButtonClick(Dialog dialog) {
                        dialog.dismiss();
                    }
                });
                break;
        }
    }

    Observable<String> updatePwd(String password) {
        return RetrofitService.getInstance().updatePwd("", password).compose(RxUtils.activityLifecycle(this));
    }
}
