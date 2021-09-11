package com.zh.template.page;

import android.app.Dialog;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;

import com.alibaba.fastjson.JSONObject;
import com.gyf.immersionbar.ImmersionBar;
import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;
import com.mobsandgeeks.saripaar.annotation.Order;
import com.zh.template.R;
import com.zh.template.base.BaseActivity;
import com.zh.template.common.Constants;
import com.zh.template.net.service.AllService;
import com.zh.template.page.home.MainActivity;
import com.zh.template.utils.SPUtil;
import com.zh.template.utils.ToastUtil;
import com.zh.template.widget.ClearEditText;
import com.zh.template.widget.CustomDialog;
import com.zh.template.widget.PasswordEditText;

import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import butterknife.OnLongClick;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class LoginActivity extends BaseActivity implements Validator.ValidationListener {
    @Order(1)
    @NotEmpty(message = "账号不能为空")
    @BindView(R.id.name)
    ClearEditText name;
    @Order(2)
    @NotEmpty(message = "密码不能为空")
    @BindView(R.id.pwd)
    PasswordEditText pwd;
    @BindView(R.id.login)
    Button login;
    @BindView(R.id.img_logo)
    ImageView img_logo;
    @BindView(R.id.cb_pwd)
    CheckBox cb_pwd;

    private boolean autoSignIn;
    private Validator validator;

    @Override
    protected int layoutId() {
        return R.layout.activity_login;
    }

    @Override
    protected void initView() {
        ImmersionBar.with(this).reset().init();
        validator = new Validator(this);
        validator.setValidationListener(this);
        autoSignIn = SPUtil.USER.getBoolean("autoSignIn", false);
        cb_pwd.setChecked(autoSignIn);
        if (autoSignIn) {
            name.setText(SPUtil.USER.getString("name", ""));
            pwd.setText(SPUtil.USER.getString("pwd", ""));
            if (TextUtils.isEmpty(name.getText()) || TextUtils.isEmpty(pwd.getText())) {
                return;
            }
            onClick(login);
        }
    }

    @Override
    protected void initData() {
        cb_pwd.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {// 选中
                SPUtil.USER.putBoolean("autoSignIn", true);
            } else {
                SPUtil.USER.putBoolean("autoSignIn", false);
            }
        });
    }

    @OnClick({R.id.login, R.id.img_logo})
    void onClick(View v) {
        switch (v.getId()) {
            case R.id.img_logo:
                break;
            case R.id.login:
                validator.validate();
                break;
        }
    }

    Observable<JSONObject> Login(Map<String, String> map) {
        return AllService.getInstance().Login(map)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(this.bindToLifecycle());
    }

    @OnLongClick(R.id.img_logo)
    boolean onLongClick() {
        CustomDialog dialogUtil = CustomDialog.getInstance();
        dialogUtil.showEditTextDialog(this, "请输入密码");
        dialogUtil.setOnEditTextClickListener(new CustomDialog.OnEditTextClickListener() {
            @Override
            public void onConfirmButtonClick(Dialog dialog, String msg) {
                if (msg.equals(Constants.SetingPsd)) {
                    dialog.dismiss();
                    startActivity(new Intent(LoginActivity.this, ConfigActivity.class));
                } else {
                    ToastUtil.showShort("请输入正确的密码");
                }
            }

            @Override
            public void onCancelButtonClick(Dialog dialog) {
                dialog.dismiss();
            }
        });
        return true;
    }

    @Override
    public void onValidationSucceeded() {
        startActivity(new Intent(LoginActivity.this, MainActivity.class));
//        Map<String, String> map = new HashMap<>();
//        map.put("loginName", name.getText().toString());
//        map.put("password", pwd.getText().toString());
//        map.put("systemId", "2");
//        map.put("flag", "0");
//        map.put("authCode", "O0i9");
//        map.put("encrypt", "1");
//        Login(map).subscribe(new BaseObserver<JSONObject>(this, "登录中") {
//            @Override
//            public void onSuccess(JSONObject response) {
//
//            }
//        });
    }

    @Override
    public void onValidationFailed(List<ValidationError> errors) {
        for (ValidationError error : errors) {
            View view = error.getView();
            String message = error.getCollatedErrorMessage(this);
            ToastUtil.showShort(message);
        }
    }
}
