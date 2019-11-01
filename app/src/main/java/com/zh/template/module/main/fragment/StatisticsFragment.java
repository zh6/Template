package com.zh.template.module.main.fragment;

import android.Manifest;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.jakewharton.rxbinding3.view.RxView;
import com.zh.template.R;
import com.zh.template.base.BaseFragment;
import com.zh.template.common.MsgEvent;
import com.zh.template.utils.DateUtils;
import com.zh.template.utils.DeviceUtils;
import com.zh.template.utils.GlideUtils;
import com.zh.template.utils.RxBus;
import com.zh.template.utils.ToastUtils;
import com.zh.template.widget.CustomDialog;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.OnClick;
import permissions.dispatcher.NeedsPermission;
import permissions.dispatcher.RuntimePermissions;

@RuntimePermissions
public class StatisticsFragment extends BaseFragment {
    @BindView(R.id.tv_rxbus)
    TextView tvRxBus;
    @BindView(R.id.img_one)
    ImageView imgOne;
    @BindView(R.id.img_two)
    ImageView imgTwo;
    @BindView(R.id.btn_six)
    Button btnSix;
    @Override
    protected int setLayout() {
        return R.layout.fragment_three;
    }

    @Override
    protected void initView() {

    }
    int i=0;
    @Override
    protected void initData() {
        //RxBus事件接收
        RxBus.getInstance().toObservable(this,MsgEvent.class).doOnNext(msgEvent -> {
            if (msgEvent.getMsg().equals("txt")) {
                tvRxBus.setText("接收到事件");
            }
        }).subscribe();
        RxView.clicks(btnSix)//返回(传入点击的view)
                .throttleFirst(2000, TimeUnit.MILLISECONDS)
                .subscribe((o -> {
                    i++;
                    ToastUtils.showShort("点击了:"+i);
                }));
    }

    @OnClick({R.id.btn_one, R.id.btn_two, R.id.btn_three,R.id.btn_four,R.id.btn_five})
    void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_one:
                RxBus.getInstance().post(new MsgEvent("txt"));
                break;
            case R.id.btn_two:
                StatisticsFragmentPermissionsDispatcher.callWithPermissionCheck(this);
                break;
            case R.id.btn_three:
               StatisticsFragmentPermissionsDispatcher.readAndWriteWithPermissionCheck(this);
                break;
            case R.id.btn_four:
                GlideUtils.loadImage(getContext(),"https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1571639480517&di=55e69012770fdd4b21bdfeae2ffe9129&imgtype=0&src=http%3A%2F%2Fimg2.biaoqingjia.com%2Fbiaoqing%2F201801%2F12f1d7bc0e4537057fbb3d54e72a8713.gif",imgOne);
                break;
            case R.id.btn_five:
                GlideUtils.loadCircleImage(getContext(),"https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1571639480517&di=55e69012770fdd4b21bdfeae2ffe9129&imgtype=0&src=http%3A%2F%2Fimg2.biaoqingjia.com%2Fbiaoqing%2F201801%2F12f1d7bc0e4537057fbb3d54e72a8713.gif",imgTwo);
                break;
        }
    }

    @NeedsPermission(Manifest.permission.CALL_PHONE)
    void call() {
        Intent intent = new Intent(Intent.ACTION_CALL);
        Uri data = Uri.parse("tel:"+10086);
        intent.setData(data);
        startActivity(intent);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        StatisticsFragmentPermissionsDispatcher.onRequestPermissionsResult(this, requestCode, grantResults);
    }

    @NeedsPermission({Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE})
    void readAndWrite() {
        DeviceUtils.download(getContext(),true,"http://gdown.baidu.com/data/wisegame/362cf2b94605405e/yingyongshichang_40008.apk");
    }
}
