package com.zh.template.page;

import android.Manifest;
import android.view.View;
import android.widget.TextView;

import com.permissionx.guolindev.PermissionX;
import com.permissionx.guolindev.callback.RequestCallback;
import com.zh.template.R;
import com.zh.template.base.BaseActivity;
import com.zh.template.utils.DeviceUtil;
import com.zh.template.utils.SPUtil;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class VersionActivity extends BaseActivity {
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.versionNo)
    TextView versionNo;
    @BindView(R.id.version)
    TextView version;
    String url;//更新apk路径
    @Override
    protected int layoutId() {
        return R.layout.activity_version;
    }

    @Override
    protected void initView() {
        title.setText("版本信息");
        versionNo.setText("V" + DeviceUtil.getVersionName(this));
    }

    @Override
    protected void initData() {}

    @OnClick({R.id.back, R.id.version})
    public void OnClick(View view) {
        switch (view.getId()) {
            case R.id.back:
                onBackPressed();
                break;
            case R.id.version:
                PermissionX.init(this).permissions(Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE).request(new RequestCallback() {
                    @Override
                    public void onResult(boolean allGranted, List<String> grantedList, List<String> deniedList) {
                        if (allGranted) {
                            DeviceUtil.download(VersionActivity.this, true, SPUtil.SYSTEM.getString("ip", "") + url);
                        }
                    }
                });
                break;
        }
    }

}
