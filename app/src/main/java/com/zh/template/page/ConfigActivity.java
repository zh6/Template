package com.zh.template.page;

import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.zh.template.R;
import com.zh.template.base.BaseActivity;
import com.zh.template.base.MyApplication;
import com.zh.template.utils.SPUtil;
import com.zh.template.utils.ToastUtil;

import butterknife.BindView;
import butterknife.OnClick;

public class ConfigActivity extends BaseActivity {
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.ip)
    EditText ip;

    @Override
    protected int layoutId() {
        return R.layout.activity_config;
    }

    @Override
    protected void initView() {
        title.setText("基础配置");
        ip.setText(SPUtil.SYSTEM.getString("ip", ""));
    }

    @Override
    protected void initData() {

    }

    @OnClick({R.id.back, R.id.save})
    void onClick(View v) {
        switch (v.getId()) {
            case R.id.back:
                onBackPressed();
                break;
            case R.id.save:
                if (TextUtils.isEmpty(ip.getText())) {
                    ToastUtil.showShort("请输入接口地址");
                    return;
                }
                String ipStr = ip.getText().toString();
                if (!ipStr.contains("http://")) {
                    ipStr = "http://" + ipStr;
                }
                if (!ipStr.endsWith("/")) {
                    ipStr = ipStr + "/";
                }
                if (!Patterns.WEB_URL.matcher(ipStr).matches()) {
                    ToastUtil.showShort("保存失败！请检查接口地址是否错误");
                    return;
                }
                SPUtil.SYSTEM.putString("ip", ipStr);
                ToastUtil.showShort("保存成功！请重新打开！");
                MyApplication.getAppContext().AppExit();
                break;
        }
    }
}
