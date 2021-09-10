package com.zh.template.widget;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.zh.template.R;

public class CustomStyleDialog {
    public static CustomStyleDialog getInstance() {
        return new CustomStyleDialog();
    }

    /**
     * MessageDialog
     *
     * @param context    上下文
     * @param message    显示的信息
     * @param error      按钮背景
     * @param buttonText 按钮文字
     */
    public void showConfirmDialog(Context context, String message, boolean error, String buttonText, OnButtonClickListener onButtonClickListener) {
        View view = View.inflate(context, R.layout.dialog_confirm2, null);
        Dialog dialog = new Dialog(context, R.style.myDialog);
        dialog.setCanceledOnTouchOutside(false);
        dialog.setContentView(view);
        dialog.show();
        //设置自适应的方法：
        WindowManager.LayoutParams dialogParams = dialog.getWindow().getAttributes();
        dialogParams.width = (int) (context.getResources().getDisplayMetrics().widthPixels *0.8);//WindowManager.LayoutParams.MATCH_PARENT;
        dialogParams.height = WindowManager.LayoutParams.WRAP_CONTENT;
        //设置居中显示
        dialogParams.gravity = Gravity.CENTER;
        dialog.getWindow().setAttributes(dialogParams);
        TextView tv_message = view.findViewById(R.id.tv_message);
        TextView tv_cancel = view.findViewById(R.id.tv_cancel);
        TextView tv_confirm = view.findViewById(R.id.tv_confirm);
        tv_message.setText(message);
        tv_confirm.setText(buttonText);
        if (error) {
            tv_confirm.setBackground(context.getDrawable(R.drawable.my_btn_red_solid));
        }
        tv_cancel.setOnClickListener(v -> onButtonClickListener.onCancelButtonClick(dialog));
        tv_confirm.setOnClickListener(v -> onButtonClickListener.onConfirmButtonClick(dialog));


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
     * 带输入的弹窗
     *
     * @param context
     * @param title   标题
     * @param hint    输入框提示
     * @param error   是否显示红色按钮
     * @param error   按钮文字
     */
    public void showEditTextDialog(Context context, String title, String hint, boolean error, String buttonText, OnEditTextClickListener onEditTextClickListener) {
        View view = View.inflate(context, R.layout.dialog_edit, null);
        Dialog dialog = new Dialog(context, R.style.myDialog);
        dialog.setCanceledOnTouchOutside(false);
        dialog.setContentView(view);
        dialog.show();
        //设置自适应的方法：
        WindowManager.LayoutParams dialogParams = dialog.getWindow().getAttributes();
        dialogParams.width = (int) (context.getResources().getDisplayMetrics().widthPixels *0.8);//WindowManager.LayoutParams.MATCH_PARENT;
        dialogParams.height = WindowManager.LayoutParams.WRAP_CONTENT;
        //设置居中显示
        dialogParams.gravity = Gravity.CENTER;
        dialog.getWindow().setAttributes(dialogParams);
        TextView tv_title = (TextView) view.findViewById(R.id.tv_title);
        EditText et_message = (EditText) view.findViewById(R.id.et_message);
        et_message.setHint(hint);
        ImageView img_close = (ImageView) view.findViewById(R.id.img_close);
        TextView tv_submit = (TextView) view.findViewById(R.id.tv_submit);
        tv_submit.setText(buttonText);
        if (error) {
            tv_submit.setBackground(context.getDrawable(R.drawable.my_btn_red_solid));
        }
        tv_title.setText(title);
        img_close.setOnClickListener(v -> onEditTextClickListener.onCancelButtonClick(dialog));
        tv_submit.setOnClickListener(v -> {
            String msg = et_message.getText().toString();
            onEditTextClickListener.onConfirmButtonClick(dialog, msg);
        });


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
