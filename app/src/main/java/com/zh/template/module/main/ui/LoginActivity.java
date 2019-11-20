package com.zh.template.module.main.ui;

import android.app.Dialog;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.zh.template.R;
import com.zh.template.base.BaseActivity;
import com.zh.template.common.Constants;
import com.zh.template.network.RetrofitService;
import com.zh.template.utils.RxUtils;
import com.zh.template.utils.SharedPreferenceUtils;
import com.zh.template.utils.ToastUtils;
import com.zh.template.widget.AddressFormPopup;
import com.zh.template.widget.ClearEditText;
import com.zh.template.widget.CustomDialog;
import com.zh.template.widget.PasswordEditText;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import butterknife.OnLongClick;

public class LoginActivity extends BaseActivity {
    @BindView(R.id.name)
    ClearEditText name;
    @BindView(R.id.pwd)
    PasswordEditText pwd;
    @BindView(R.id.login)
    Button login;
    AddressFormPopup addressFormPopup;
    String[] address = new String[3];

    @Override
    protected int layoutId() {
        return R.layout.activity_login;
    }

    @Override
    protected void initView() {
        addressFormPopup = new AddressFormPopup(this);
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
        addressFormPopup.setOnItemClickListener(new AddressFormPopup.OnItemClickListener() {
            @Override
            public void onSelected(String[] saveId) {
                address = saveId;
            }
        });
    }

    @OnClick({R.id.login, R.id.seting, R.id.tv_application})
    void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_application:
                break;
            case R.id.seting:
                addressFormPopup.showPopupWindow();
                addressFormPopup.setCity(address);
                break;
            case R.id.login:
                if (TextUtils.isEmpty(name.getText())) {
                    ToastUtils.showShort("请输入账号");
                    return;
                }
                if (TextUtils.isEmpty(pwd.getText())) {
                    ToastUtils.showShort("请输入密码");
                    return;
                }
                RetrofitService.getInstance().Login(name.getText().toString(), pwd.getText().toString()).doOnSubscribe(aLong -> showDialog(loadingView))
                        .doOnNext(res -> {
                            JSONObject obj = JSON.parseObject(JSON.toJSONString(res));
                            if (obj.getIntValue("state") == 0) {  //表示登录成功
                                SharedPreferenceUtils.saveToken(this, obj.getString("token"));  //存token
                                SharedPreferenceUtils.saveUserInfo(this, name.getText().toString(), pwd.getText().toString());  //存用户账号密码信息
//                                startActivity(new Intent(this, MainActivity.class));
                            } else {
                                ToastUtils.showShort(obj.getString("msg"));
                            }
                        })
                        .doOnTerminate(() -> closeDialog(loadingView))
                        .doOnError(throwable -> ToastUtils.showShort(throwable.getMessage())).compose(RxUtils.activityLifecycle(this)).subscribe();
                break;
        }
    }

    @OnLongClick(R.id.seting)
    boolean onLongClick() {
        CustomDialog dialogUtil = CustomDialog.getInstance().getInstance();
        dialogUtil.showEditTextDialog(this, "请输入密码");
        dialogUtil.setOnEditTextClickListener(new CustomDialog.OnEditTextClickListener() {
            @Override
            public void onConfirmButtonClick(Dialog dialog, String msg) {
                if (msg.equals(Constants.SetingPsd)) {
                    dialog.dismiss();
                    startActivity(new Intent(LoginActivity.this, ConfigActivity.class));
                } else {
                    ToastUtils.showShort("请输入正确的密码");
                }
            }

            @Override
            public void onCancelButtonClick(Dialog dialog) {
                dialog.dismiss();
            }
        });
        return true;
    }
}
