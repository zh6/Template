package com.jiuzhou.template.widget;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.wang.avi.AVLoadingIndicatorView;
import com.jiuzhou.template.R;

public class LoadingView extends Dialog {
    private AVLoadingIndicatorView avLoadingIndicatorView;
    private TextView loadingText;
    Activity mParentActivity;

    public LoadingView(Context context) {
        super(context, R.style.Loading);
        mParentActivity = (Activity) context;
        setContentView(R.layout.layout_loading_dialog);
        avLoadingIndicatorView = (AVLoadingIndicatorView) findViewById(R.id.AVLoadingIndicatorView);
        loadingText = (TextView) findViewById(R.id.id_tv_loading_dialog_text);
        // 设置Dialog参数
        Window window = getWindow();
        WindowManager.LayoutParams params = window.getAttributes();
        params.gravity = Gravity.CENTER;
        window.setAttributes(params);
    }

    @Override
    public void show() {
        if (mParentActivity != null && !mParentActivity.isFinishing()) {
            super.show();
            avLoadingIndicatorView.smoothToShow();
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
            avLoadingIndicatorView.smoothToHide();
        }
    }
}
