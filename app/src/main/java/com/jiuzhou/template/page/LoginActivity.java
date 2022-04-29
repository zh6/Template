package com.jiuzhou.template.page;

import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.gyf.immersionbar.ImmersionBar;
import com.jiuzhou.template.R;
import com.jiuzhou.template.base.BaseActivity;
import com.jiuzhou.template.common.Constants;
import com.jiuzhou.template.entity.LoginRespEntity;
import com.jiuzhou.template.entity.SysModuleEntity;
import com.jiuzhou.template.net.service.BaseService;
import com.jiuzhou.template.net.use.BaseObserver;
import com.jiuzhou.template.net.use.BaseResponse;
import com.jiuzhou.template.utils.DeviceUtils;
import com.jiuzhou.template.utils.GlideUtils;
import com.jiuzhou.template.utils.SpUtils;
import com.jiuzhou.template.utils.ToastUtils;
import com.jiuzhou.template.widget.ClearEditText;
import com.jiuzhou.template.widget.PasswordEditText;
import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;
import com.mobsandgeeks.saripaar.annotation.Order;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
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
    @BindView(R.id.tv_version)
    TextView tv_version;
    private boolean autoSignIn;
    private Validator validator;

    @Override
    protected int layoutId() {
        return R.layout.activity_login;
    }

    @Override
    protected void initView() {
        tv_version.setText("V" + DeviceUtils.getVersionName(this));
        ImmersionBar.with(this).reset().init();
        validator = new Validator(this);
        validator.setValidationListener(this);
        autoSignIn = SpUtils.USER.getBoolean(Constants.AUTOLOGIN, false);
        cb_pwd.setChecked(autoSignIn);
        if (autoSignIn) {
            name.setText(SpUtils.USER.getString(Constants.NAME));
            pwd.setText(SpUtils.USER.getString(Constants.PWD));
            if (TextUtils.isEmpty(name.getText()) || TextUtils.isEmpty(pwd.getText())) {
                return;
            }
            onClick(login);
        }

    }

    @Override
    protected void initData() {
        getSysLogo().subscribe(new BaseObserver<BaseResponse<JSONObject>>() {
            @Override
            public void onSuccess(BaseResponse<JSONObject> response) {
                if (response.getAaData() != null) {
                    GlideUtils.loadImage(LoginActivity.this, response.getAaData().getString("dataValue"), img_logo);
                }
            }
        });
        cb_pwd.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {// 选中
                SpUtils.USER.putBoolean(Constants.AUTOLOGIN, true);
            } else {
                SpUtils.USER.putBoolean(Constants.AUTOLOGIN, false);
            }
        });
    }

    Observable<BaseResponse<JSONObject>> getSysLogo() {
        return BaseService.getInstance().getSysLogo("PDA_CONFIG", "main_logo")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(this.bindToLifecycle());
    }

    @OnClick({R.id.login, R.id.img_logo})
    public void onClick(View view) {
        if (view.getId() == R.id.img_logo) {
        } else if (view.getId() == R.id.login) {
            if (TextUtils.isEmpty(SpUtils.SYSTEM.getString(Constants.SNCODE))) {
                ToastUtils.showShort("请先绑定手持机区域！");
                return;
            }
            validator.validate();
        }
    }

    Observable<LoginRespEntity> Login(Map<String, String> map) {
        return BaseService.getInstance().Login(map)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(this.bindToLifecycle());
    }
    @Override
    public void onValidationSucceeded() {
        Map<String, String> map = new HashMap<>();
        map.put("loginName", name.getText().toString().trim());
        map.put("password", pwd.getText().toString().trim());
        map.put("systemId", "2");
        map.put("flag", "0");
        map.put("authCode", "O0i9");
        map.put("encrypt", "1");
        Login(map).subscribe(new BaseObserver<LoginRespEntity>(this, "登录中") {
            @Override
            public void onSuccess(LoginRespEntity response) {
                if (response.getState() == 0) {  //表示登录成功
                    //处理排序
                    List<SysModuleEntity> chilModulList = new ArrayList<>();
                    try {
                        chilModulList = response.getSysModuleVOList().get(0).getChilModulList();
                        Collections.sort(chilModulList, (bean1, bean2) -> bean1.getSort().compareTo(bean2.getSort()));
                    } catch (Exception e) {
                        Log.e("sys", "没有模块权限！");
                    }
                    SpUtils.USER.putString(Constants.NAME, name.getText().toString().trim())
                            .putString(Constants.PWD, pwd.getText().toString().trim())
                            .putString(Constants.NICKNAME, response.getUserVO().getNickName())
                            .putString(Constants.MOBILE, response.getUserVO().getMobile())
                            .putString(Constants.FILEPATH, response.getUserVO().getFilePath())
                            .putString(Constants.TOKEN, response.getToken())
                            .putLong(Constants.USERID, response.getUserVO().getId())
                            .putString(Constants.SYS, JSON.toJSONString(chilModulList));
                    startActivity(new Intent(LoginActivity.this, HomeActivity.class));
                } else {
                    ToastUtils.showShort(response.getMsg());
                }
            }
        });
    }

    @Override
    public void onValidationFailed(List<ValidationError> errors) {
        for (ValidationError error : errors) {
            String message = error.getCollatedErrorMessage(this);
            ToastUtils.showShort(message);
        }
    }
}
