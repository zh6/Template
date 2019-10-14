package com.jiuzhou.template.module.main.ui;

import android.app.Dialog;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.jiuzhou.template.R;
import com.jiuzhou.template.base.BaseActivity;
import com.jiuzhou.template.common.Constants;
import com.jiuzhou.template.component.RetrofitService;
import com.jiuzhou.template.utils.AlertDialogUtils;
import com.jiuzhou.template.utils.RxUtils;
import com.jiuzhou.template.utils.SharedPreferenceUtils;
import com.jiuzhou.template.utils.ToastUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import butterknife.OnLongClick;
import io.reactivex.Observable;

public class LoginActivity extends BaseActivity {
    @BindView(R.id.name)
    EditText name;
    @BindView(R.id.pwd)
    EditText pwd;
    @BindView(R.id.login)
    Button login;

    @Override
    protected int layoutId() {
        return R.layout.activity_login;
    }

    @Override
    protected void initView() {
        List<String> userInfo = SharedPreferenceUtils.getUserInfo(this);
        name.setText(userInfo.get(0));
        pwd.setText(userInfo.get(1));
        if (TextUtils.isEmpty(name.getText()) || TextUtils.isEmpty(pwd.getText())) {
            return;
        }
        onClick(login);
    }

    @Override
    protected void initData() {

    }

    @OnClick({R.id.login})
    void onClick(View v) {
        switch (v.getId()) {
            case R.id.login:
                if (TextUtils.isEmpty(name.getText())) {
                    ToastUtils.showShort("请输入账号");
                    return;
                }
                if (TextUtils.isEmpty(pwd.getText())) {
                    ToastUtils.showShort("请输入密码");
                    return;
                }
                Map mao = new HashMap();
                RetrofitService.getInstance().Login(name.getText().toString(), pwd.getText().toString()).doOnSubscribe(aLong -> showDialog(loadingView))
                        .doOnNext(res -> {
                            closeDialog(loadingView);
                            JSONObject obj = JSON.parseObject(JSON.toJSONString(res));
                            if (obj.getIntValue("state") == 0) {  //表示登录成功
                                SharedPreferenceUtils.saveToken(this, obj.getString("token"));  //存token
                                SharedPreferenceUtils.saveUserInfo(this, name.getText().toString(), pwd.getText().toString());  //存用户账号密码信息
                                startActivity(new Intent(this, HomeActivity.class));
                            } else {
                                ToastUtils.showShort(obj.getString("msg"));
                            }

                        })
                        .doOnError(throwable -> {
                            ToastUtils.showShort(throwable.getMessage());
                            closeDialog(loadingView);
                        }).compose(RxUtils.activityLifecycle(this)).subscribe();
                break;
        }
    }

    @OnLongClick(R.id.seting)
    boolean onLongClick() {
        AlertDialogUtils dialogUtil = AlertDialogUtils.getInstance();
        dialogUtil.showEditTextDialog(this, "请输入密码");
        dialogUtil.setOnEditTextClickListener(new AlertDialogUtils.OnEditTextClickListener() {
            @Override
            public void onPositiveButtonClick(Dialog dialog, String msg) {
                if (msg.equals(Constants.SetingPsd)) {
                    dialog.dismiss();
                    startActivity(new Intent(LoginActivity.this, ConfigActivity.class));
                } else {
                    ToastUtils.showShort("请输入正确的密码");
                }
            }

            @Override
            public void onNegativeButtonClick(Dialog dialog) {

            }
        });
        return true;
    }
}
