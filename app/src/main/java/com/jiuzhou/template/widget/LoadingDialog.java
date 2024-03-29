package com.jiuzhou.template.widget;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.widget.TextView;

import com.jiuzhou.template.R;
import com.wang.avi.AVLoadingIndicatorView;

public class LoadingDialog extends Dialog {
    private AVLoadingIndicatorView avi;
    private TextView loadingText;
    Activity mParentActivity;

    public LoadingDialog(Context context) {
        super(context, R.style.Loading);
        mParentActivity = (Activity) context;
        setContentView(R.layout.dialog_loading);
        avi = (AVLoadingIndicatorView) findViewById(R.id.AVLoadingIndicatorView);
        loadingText = (TextView) findViewById(R.id.id_tv_loading_dialog_text);
    }

    @Override
    public void show() {
        if (mParentActivity != null && !mParentActivity.isFinishing()) {
            super.show();
            avi.smoothToShow();
        }
    }

    /**
     * 设置提示文字
     *
     * @param msg
     */
    public void setText(String msg) {
        loadingText.setText(msg);
    }

    @Override
    public void dismiss() {
        if (mParentActivity != null && !mParentActivity.isFinishing()) {
            super.dismiss();    //调用超类对应方法
            avi.smoothToHide();
        }
    }
}
