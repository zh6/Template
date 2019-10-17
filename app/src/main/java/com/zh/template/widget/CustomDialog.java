package com.zh.template.widget;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.TextView;

import com.zh.template.R;

public class CustomDialog {
    public static CustomDialog getInstance() {
        return new CustomDialog();
    }

    /**
     * MessageDialog
     *
     * @param context 上下文
     * @param message 显示的信息
     */
    public void showConfirmDialog(Context context, String message) {
        Dialog dialog = new Dialog(context);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
        View view = View.inflate(context, R.layout.dialog_confirm, null);
        TextView tvMsg = (TextView) view.findViewById(R.id.tv_message_dialog);
        TextView tvCancel = (TextView) view.findViewById(R.id.tv_cancel_dialog);
        TextView tvConfirm = (TextView) view.findViewById(R.id.tv_confirm_dialog);
        tvMsg.setText(message);
        tvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onButtonClickListener.onCancelButtonClick(dialog);
            }
        });
        tvConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onButtonClickListener.onConfirmButtonClick(dialog);
            }
        });
        dialog.getWindow().setContentView(view);
    }

    private OnButtonClickListener onButtonClickListener;

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
        void onConfirmButtonClick(Dialog dialog);
        /**
         * 取消按钮点击回调方法
         *
         * @param dialog 当前AlertDialog
         */
        void onCancelButtonClick(Dialog dialog);
    }

    /**
     * InputDialog
     *
     * @param context 上下文
     * @param title   标题
     */
    public void showEditTextDialog(Context context, String title) {
        Dialog dialog = new Dialog(context);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
        View view = View.inflate(context, R.layout.dialog_confirm, null);
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
                onEditTextClickListener.onCancelButtonClick(dialog);
            }
        });
        tvConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String msg = editText.getText().toString();
                onEditTextClickListener.onConfirmButtonClick(dialog, msg);
            }
        });
        dialog.getWindow().setContentView(view);
    }

    private OnEditTextClickListener onEditTextClickListener;

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
        void onConfirmButtonClick(Dialog dialog, String msg);

        /**
         * 取消按钮点击回调方法
         *
         * @param dialog 当前AlertDialog
         */
        void onCancelButtonClick(Dialog dialog);
    }
}
