package com.jiuzhou.template.page;

import android.Manifest;
import android.view.View;
import android.widget.TextView;

import com.jiuzhou.template.R;
import com.jiuzhou.template.base.BaseActivity;
import com.jiuzhou.template.common.Constants;
import com.jiuzhou.template.utils.DeviceUtils;
import com.jiuzhou.template.utils.SpUtils;
import com.permissionx.guolindev.PermissionX;
import com.permissionx.guolindev.callback.RequestCallback;

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
        versionNo.setText("V" + DeviceUtils.getVersionName(this));
    }

    @Override
    protected void initData() {
    }

    @OnClick({R.id.back, R.id.version})
    public void onClick(View view) {
        if (view.getId() == R.id.back) {
            onBackPressed();
        } else if (view.getId() == R.id.version) {
            PermissionX.init(this).permissions(Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE).request(new RequestCallback() {
                @Override
                public void onResult(boolean allGranted, List<String> grantedList, List<String> deniedList) {
                    if (allGranted) {
                        DeviceUtils.download(VersionActivity.this, true, SpUtils.SYSTEM.getStringDef(Constants.IP, Constants.API_URL) + url);
                    }
                }
            });
        }
    }

}
