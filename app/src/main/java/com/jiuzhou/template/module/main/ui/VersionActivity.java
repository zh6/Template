package com.jiuzhou.template.module.main.ui;

import android.Manifest;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.TextView;

import com.jiuzhou.template.R;
import com.jiuzhou.template.base.BaseActivity;
import com.jiuzhou.template.component.RetrofitService;
import com.jiuzhou.template.utils.DeviceUtils;
import com.jiuzhou.template.utils.RxUtils;
import com.jiuzhou.template.utils.SharedPreferenceUtils;
import com.jiuzhou.template.utils.ToastUtils;

import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.Observable;
import permissions.dispatcher.NeedsPermission;
import permissions.dispatcher.RuntimePermissions;

@RuntimePermissions
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
        getDistributionAppNewVersion().doOnError(throwable -> ToastUtils.showShort(throwable.getMessage())).doOnNext(res -> {
            if (res.get("isUpdate").equals("true")) {
                version.setVisibility(View.VISIBLE);
                version.setText("升级到最新版本" + res.get("versionCode"));
                url = res.get("url");
            } else {
                version.setVisibility(View.GONE);
            }
        }).subscribe();
    }

    Observable<Map<String, String>> getDistributionAppNewVersion() {
        return RetrofitService.getInstance().getDistributionAppNewVersion(DeviceUtils.getVersionName(this)).compose(RxUtils.activityLifecycle(this));
    }

    @OnClick({R.id.back, R.id.version})
    public void OnClick(View view) {
        switch (view.getId()) {
            case R.id.back:
                onBackPressed();
                break;
            case R.id.version:
                VersionActivityPermissionsDispatcher.getPermissionsWithPermissionCheck(this);
                break;
        }
    }

    @NeedsPermission({Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE})
    void getPermissions() {
        DeviceUtils.download(this, true, SharedPreferenceUtils.getIp(this) + url);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        VersionActivityPermissionsDispatcher.onRequestPermissionsResult(this, requestCode, grantResults);
    }
}
