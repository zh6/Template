package com.zh.template.module.main.fragment;

import android.app.Dialog;
import android.view.View;
import android.widget.Button;

import com.zh.template.R;
import com.zh.template.base.BaseFormPopup;
import com.zh.template.base.BaseFragment;
import com.zh.template.module.main.entity.BasePopEntity;
import com.zh.template.utils.ToastUtils;
import com.zh.template.widget.AddressFormPopup;
import com.zh.template.widget.CustomDialog;
import com.zh.template.widget.LoadingDialog;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class OrderFragment extends BaseFragment {
    //地址选择
    AddressFormPopup addressFormPopup;
    //加载动画
    LoadingDialog loadingDialog;
    String[] address = new String[3];
    //列表窗口
    BaseFormPopup baseFormPopup;

    @Override
    protected int setLayout() {
        return R.layout.fragment_two;
    }

    @Override
    protected void initView() {
        loadingDialog = new LoadingDialog(getContext());
        loadingDialog.setCancelable(true);
        addressFormPopup = new AddressFormPopup(getActivity());
        List<BasePopEntity> list = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            BasePopEntity entity = new BasePopEntity();
            entity.code = i + "";
            entity.name = "item" + i;
            list.add(entity);
        }
        baseFormPopup = new BaseFormPopup(getActivity(), list);
    }

    @Override
    protected void initData() {
        addressFormPopup.setOnItemClickListener(new AddressFormPopup.OnItemClickListener() {
            @Override
            public void onSelected(String[] saveId) {
                address = saveId;
            }
        });
        baseFormPopup.setOnItemClickListener(new BaseFormPopup.OnItemClickListener() {
            @Override
            public void onItemClick(View view, BasePopEntity entity) {
                baseFormPopup.dismiss();
                ToastUtils.showShort("你选择了：" + entity.name);
            }
        });
    }

    @OnClick({R.id.btn_one, R.id.btn_two, R.id.btn_three, R.id.btn_four, R.id.btn_five})
    void onClick(View v) {
        CustomDialog dialog = CustomDialog.getInstance();
        switch (v.getId()) {
            case R.id.btn_one:
                loadingDialog.show();
                break;
            case R.id.btn_two:
                addressFormPopup.showPopupWindow();
                addressFormPopup.setCity(address);
                break;
            case R.id.btn_three:
                dialog.showConfirmDialog(getContext(), "基本弹窗");
                dialog.setOnButtonClickListener(new CustomDialog.OnButtonClickListener() {
                    @Override
                    public void onConfirmButtonClick(Dialog dialog) {
                        dialog.dismiss();
                        ToastUtils.showShort("你点了确定");
                    }

                    @Override
                    public void onCancelButtonClick(Dialog dialog) {
                        dialog.dismiss();
                        ToastUtils.showShort("你点了取消");
                    }
                });
                break;
            case R.id.btn_four:
                dialog.showEditTextDialog(getContext(), "带输入框弹窗");
                dialog.setOnEditTextClickListener(new CustomDialog.OnEditTextClickListener() {
                    @Override
                    public void onConfirmButtonClick(Dialog dialog, String msg) {
                        dialog.dismiss();
                        ToastUtils.showShort("你输入的内容是：" + msg);
                    }

                    @Override
                    public void onCancelButtonClick(Dialog dialog) {
                        dialog.dismiss();
                        ToastUtils.showShort("你点了取消");
                    }
                });
                break;
            case R.id.btn_five:
                baseFormPopup.showPopupWindow();
                break;
        }
    }
}
