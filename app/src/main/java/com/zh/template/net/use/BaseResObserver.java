package com.zh.template.net.use;

import android.content.Context;
import android.text.TextUtils;

import com.google.gson.JsonParseException;
import com.zh.template.utils.ToastUtils;
import com.zh.template.widget.LoadingDialog;

import org.json.JSONException;

import java.io.InterruptedIOException;
import java.net.ConnectException;
import java.net.UnknownHostException;
import java.text.ParseException;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * 封装接口msg错误
 * @param <T>
 */
public abstract class BaseResObserver<T extends BaseResponse> implements Observer<T> {
    private String mMsg;
    private LoadingDialog loadingDialog;
    private Context mContext;
    private boolean mShowLoading = false;
    private static final String CONNECT_ERROR = "网络连接失败,请检查网络";
    private static final String CONNECT_TIMEOUT = "连接超时,请稍后再试";
    private static final String BAD_NETWORK = "服务器异常";
    private static final String PARSE_ERROR = "解析服务器响应数据失败";
    private static final String UNKNOWN_ERROR = "未知错误";
    private static final String RESPONSE_RETURN_ERROR = "服务器返回数据失败";

    public BaseResObserver() {
    }

    /**
     * 如果传入上下文，那么表示您将开启自定义的进度条
     *
     * @param context 上下文
     */
    public BaseResObserver(Context context, boolean isShow) {
        this.mContext = context;
        this.mShowLoading = isShow;
    }

    /**
     * 如果传入上下文，那么表示您将开启自定义的进度条
     *
     * @param context 上下文
     */
    public BaseResObserver(Context context, boolean isShow, String msg) {
        this.mContext = context;
        this.mShowLoading = isShow;
        this.mMsg = msg;
    }

    @Override
    public void onSubscribe(Disposable d) {
        onRequestStart();
    }


    @Override
    public void onNext(T response) {
        if (response.state == 0) {
            try {
                onSuccess(response);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            try {
                onFailing(response);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    @Override
    public void onError(Throwable e) {
        onRequestEnd();
        if (e instanceof retrofit2.HttpException) {
            //HTTP错误
            onException(ExceptionReason.BAD_NETWORK);
        } else if (e instanceof ConnectException || e instanceof UnknownHostException) {
            //连接错误
            onException(ExceptionReason.CONNECT_ERROR);
        } else if (e instanceof InterruptedIOException) {
            //连接超时
            onException(ExceptionReason.CONNECT_TIMEOUT);
        } else if (e instanceof JsonParseException || e instanceof JSONException || e instanceof ParseException) {
            //解析错误
            onException(ExceptionReason.PARSE_ERROR);
        } else {
            //其他错误
            onException(ExceptionReason.UNKNOWN_ERROR);
        }
    }

    private void onException(ExceptionReason reason) {
        switch (reason) {
            case CONNECT_ERROR:
                ToastUtils.showShort(CONNECT_ERROR);
                break;

            case CONNECT_TIMEOUT:
                ToastUtils.showShort(CONNECT_TIMEOUT);
                break;

            case BAD_NETWORK:
                ToastUtils.showShort(BAD_NETWORK);
                break;

            case PARSE_ERROR:
                ToastUtils.showShort(PARSE_ERROR);
                break;

            case UNKNOWN_ERROR:
            default:
                ToastUtils.showShort(UNKNOWN_ERROR);
                break;
        }
    }

    @Override
    public void onComplete() {
        onRequestEnd();
    }

    /**
     * 网络请求成功并返回正确值
     *
     * @param response 返回值
     */
    public abstract void onSuccess(T response);

    /**
     * 网络请求成功但是返回值是错误的
     *
     * @param response 返回值
     */
    public void onFailing(T response) {
        String message = response.msg;
        if (TextUtils.isEmpty(message)) {
            ToastUtils.showShort(RESPONSE_RETURN_ERROR);
        } else {
            ToastUtils.showShort(message);
        }
    }


    /**
     * 网络请求失败原因
     */
    public enum ExceptionReason {
        /**
         * 解析数据失败
         */
        PARSE_ERROR,
        /**
         * 网络问题
         */
        BAD_NETWORK,
        /**
         * 连接错误
         */
        CONNECT_ERROR,
        /**
         * 连接超时
         */
        CONNECT_TIMEOUT,
        /**
         * 未知错误
         */
        UNKNOWN_ERROR
    }

    /**
     * 网络请求开始
     */
    protected void onRequestStart() {
        if (mShowLoading) {
            showProgressDialog();
        }
    }

    /**
     * 网络请求结束
     */
    protected void onRequestEnd() {
        closeProgressDialog();
    }

    /**
     * 开启Dialog
     */
    private void showProgressDialog() {
        loadingDialog = new LoadingDialog(mContext);
        if (TextUtils.isEmpty(mMsg)) {
            loadingDialog.show();
        } else {
            loadingDialog.setText(mMsg);
            loadingDialog.show();
        }
    }

    /**
     * 关闭Dialog
     */
    private void closeProgressDialog() {
        if (loadingDialog != null) {
            loadingDialog.dismiss();
        }
    }

}
