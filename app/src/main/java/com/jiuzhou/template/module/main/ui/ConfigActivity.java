package com.jiuzhou.template.module.main.ui;

import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.jiuzhou.template.R;
import com.jiuzhou.template.base.BaseActivity;
import com.jiuzhou.template.utils.SharedPreferenceUtils;
import com.jiuzhou.template.utils.ToastUtils;

import butterknife.BindView;
import butterknife.OnClick;
import me.jessyan.retrofiturlmanager.RetrofitUrlManager;
/*  *
 *  *配置界面
 *  *@author zhaohui
 *  *@time 2019/8/2 11:37
 *  * ----------Dragon be here!----------/
 *  * 　　　┏┓　　 ┏┓
 *  * 　　┏┛┻━━━┛┻┓━━━
 *  * 　　┃　　　　　 ┃
 *  * 　　┃　　　━　  ┃
 *  * 　　┃　┳┛　┗┳
 *  * 　　┃　　　　　 ┃
 *  * 　　┃　　　┻　  ┃
 *  * 　　┃　　　　   ┃
 *  * 　　┗━┓　　　┏━┛Code is far away from bug with the animal protecting
 *  * 　　　　┃　　　┃    神兽保佑,代码无bug
 *  * 　　　　┃　　　┃
 *  * 　　　　┃　　　┗━━━┓
 *  * 　　　　┃　　　　　　┣┓
 *  * 　　　　┃　　　　　　　┏┛
 *  * 　　　　┗┓┓┏━┳┓┏┛━━━━━
 *  * 　　　　　┃┫┫　┃┫┫
 *  * 　　　　　┗┻┛　┗┻┛
 *  * ━━━━━━━━━━━神兽出没━━━━━━━━━━━━━━
 *  *
 *  */
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
        ip.setText(SharedPreferenceUtils.getIp(this) + "");
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
                    ToastUtils.showShort("请输入接口地址");
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
                    ToastUtils.showShort("保存失败！请检查接口地址是否错误");
                    return;
                }
                // 全局 BaseUrl 的优先级低于 Domain-Name header 中单独配置的,其他未配置的接口将受全局 BaseUrl 的影响
                RetrofitUrlManager.getInstance().setGlobalDomain(SharedPreferenceUtils.getIp(this));
                ToastUtils.showShort("保存成功！接口地址为：" + SharedPreferenceUtils.getIp(this));
                SharedPreferenceUtils.saveIp(this, ipStr);
                break;
        }
    }
}
