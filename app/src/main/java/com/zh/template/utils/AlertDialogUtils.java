package com.zh.template.utils;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;

import com.zh.template.R;

public class AlertDialogUtils {
    public static AlertDialogUtils getInstance() {
        return new AlertDialogUtils();
    }
    /**
     * 带有确认取消按钮的自定义dialog
     *
     * @param context 上下文
     * @param message 显示的信息
     */
    public  void showConfirmDialog(Context context, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        final AlertDialog alertDialog = builder.create();
        Window window = alertDialog.getWindow();
        WindowManager.LayoutParams lp = window.getAttributes();
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        alertDialog.setCanceledOnTouchOutside(false);
        alertDialog.show();
        View view = View.inflate(context, R.layout.view_confirm_dialog, null);
        TextView tvMsg = (TextView) view.findViewById(R.id.tv_message_dialog);
        TextView tvCancel = (TextView) view.findViewById(R.id.tv_cancel_dialog);
        TextView tvConfirm = (TextView) view.findViewById(R.id.tv_confirm_dialog);
        tvMsg.setText(message);
        tvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onButtonClickListener.onNegativeButtonClick(alertDialog);
            }
        });
        tvConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onButtonClickListener.onPositiveButtonClick(alertDialog);
            }
        });
        alertDialog.getWindow().setContentView(view);
    }

    private  OnButtonClickListener onButtonClickListener;

    public void setOnButtonClickListener(OnButtonClickListener onButtonClickListener) {
        this.onButtonClickListener = onButtonClickListener;
    }

    /**
     * 按钮点击回调接口
     */
    public interface OnButtonClickListener {
        /**
         * 确定按钮点击回调方法
         *
         * @param dialog 当前 AlertDialog，传入它是为了在调用的地方对 dialog 做操作，比如 dismiss()
         *               也可以在该工具类中直接  dismiss() 掉，就不用将 AlertDialog 对象传出去了
         */
        void onPositiveButtonClick(AlertDialog dialog);

        /**
         * 取消按钮点击回调方法
         *
         * @param dialog 当前AlertDialog
         */
        void onNegativeButtonClick(AlertDialog dialog);
    }

    /**
     * 带有输入框的自定义dialog
     *
     * @param context 上下文
     * @param title   标题
     */
    public  void showEditTextDialog(Context context, String title) {
        Dialog dialog = new Dialog(context);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
        View view = View.inflate(context, R.layout.view_confirm_dialog, null);
        TextView tvTitle = (TextView) view.findViewById(R.id.tv_title_dialog);
        EditText editText = (EditText) view.findViewById(R.id.et_message_dialog);
        tvTitle.setVisibility(View.VISIBLE);
        editText.setVisibility(View.VISIBLE);
        TextView tvMsg = (TextView) view.findViewById(R.id.tv_message_dialog);
        tvMsg.setVisibility(View.GONE);
        TextView tvCancel = (TextView) view.findViewById(R.id.tv_cancel_dialog);
        TextView tvConfirm = (TextView) view.findViewById(R.id.tv_confirm_dialog);
        tvTitle.setText(title);
        tvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        tvConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String msg = editText.getText().toString();
                onEditTextClickListener.onPositiveButtonClick(dialog, msg);
            }
        });
        dialog.getWindow().setContentView(view);
    }

    private  OnEditTextClickListener onEditTextClickListener;

    public void setOnEditTextClickListener(OnEditTextClickListener onEditTextClickListener) {
        this.onEditTextClickListener = onEditTextClickListener;
    }

    /**
     * 按钮点击回调接口
     */
    public interface OnEditTextClickListener {
        /**
         * 确定按钮点击回调方法
         *
         * @param dialog 当前 AlertDialog，传入它是为了在调用的地方对 dialog 做操作，比如 dismiss()
         *               也可以在该工具类中直接  dismiss() 掉，就不用将 AlertDialog 对象传出去了
         */
        void onPositiveButtonClick(Dialog dialog, String msg);
        /**
         * 取消按钮点击回调方法
         *
         * @param dialog 当前AlertDialog
         */
        void onNegativeButtonClick(Dialog dialog);
    }
}
