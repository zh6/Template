package com.jiuzhou.template.widget;

import android.app.Activity;
import android.os.Environment;
import android.view.Gravity;
import android.view.animation.Animation;
import android.widget.ImageView;
import android.widget.TextView;

import com.f1reking.signatureview.SignatureView;
import com.jiuzhou.template.R;
import com.jiuzhou.template.utils.ToastUtils;

import java.io.IOException;

import razerdp.basepopup.BasePopupWindow;
import razerdp.util.animation.AnimationHelper;
import razerdp.util.animation.TranslationConfig;

/**
 * *基本列表popup
 * *@author zhaohui
 * *@time 2019/8/2 13:33
 **/
public class SignaturePopup extends BasePopupWindow {
    private Activity context;
    private SignatureView mSignatureView;

    public SignaturePopup(Activity context) {
        super(context);
        setOverlayStatusbar(true);
        setContentView(createPopupById(R.layout.popup_signature));
        setPopupGravity(Gravity.RIGHT);
        this.context = context;
        initView();
    }

    void initView() {
        mSignatureView = findViewById(R.id.view_signature);
        TextView title = findViewById(R.id.title);
        title.setText("手写签名");
        TextView tv_left = findViewById(R.id.tv_left);
        TextView tv_right = findViewById(R.id.tv_right);
        ImageView back = findViewById(R.id.back);
        back.setOnClickListener(v -> dismiss());
        tv_left.setOnClickListener(v -> {
            mSignatureView.clear();
        });
        tv_right.setOnClickListener(v -> {
            if (mSignatureView.getTouched()) {
                try {
                    String savePath = Environment.getExternalStorageDirectory() + "/Download/sign.png";// 储存下载文件的目录
                    mSignatureView.save(savePath, false, 10);
                    dismiss();
                    onButtonClickListener.onConfirmButtonClick(mSignatureView.getSavePath());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                ToastUtils.showShort("请先签名!");
            }
        });
    }

    @Override
    protected Animation onCreateShowAnimation() {
        return AnimationHelper.asAnimation()
                .withTranslation(TranslationConfig.FROM_RIGHT)
                .toShow();
    }

    @Override
    protected Animation onCreateDismissAnimation() {
        return AnimationHelper.asAnimation()
                .withTranslation(TranslationConfig.TO_RIGHT)
                .toShow();
    }

    public void setOnButtonClickListener(OnButtonClickListener onButtonClickListener) {
        this.onButtonClickListener = onButtonClickListener;
    }

    private OnButtonClickListener onButtonClickListener;

    public interface OnButtonClickListener {
        void onConfirmButtonClick(String picPath);
    }
}
