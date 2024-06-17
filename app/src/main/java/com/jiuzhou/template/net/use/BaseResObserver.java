package com.jiuzhou.template.net.use;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;

import com.google.gson.JsonParseException;
import com.jiuzhou.template.base.MyApplication;
import com.jiuzhou.template.common.Constants;
import com.jiuzhou.template.page.LoginActivity;
import com.jiuzhou.template.utils.LogUtils;
import com.jiuzhou.template.utils.SpUtils;
import com.jiuzhou.template.utils.ToastUtils;
import com.jiuzhou.template.widget.LoadingDialog;

import org.json.JSONException;

import java.io.InterruptedIOException;
import java.net.ConnectException;
import java.net.UnknownHostException;
import java.text.ParseException;

import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;


/**
 * 封装接口msg错误
 *
 * @param <T>
 */
public abstract class BaseResObserver<T extends BaseResponse> implements Observer<T> {
    private static final String TAG = BaseResObserver.class.getSimpleName();

    // Error Messages
    private static final String CONNECT_ERROR = "网络连接失败,请检查网络";
    private static final String CONNECT_TIMEOUT = "连接超时,请稍后再试";
    private static final String BAD_NETWORK = "服务器异常";
    private static final String PARSE_ERROR = "解析服务器响应数据失败";
    private static final String UNKNOWN_ERROR = "未知错误";
    private static final String RESPONSE_RETURN_ERROR = "服务器返回数据失败";

    private String mMsg;
    private LoadingDialog loadingDialog;
    private Context mContext;
    private boolean mShowLoading = false;

    public BaseResObserver(Context context) {
        this.mContext = context;
    }

    public BaseResObserver(Context context, boolean showLoading) {
        this.mContext = context;
        this.mShowLoading = showLoading;
    }

    public BaseResObserver(Context context, String msg) {
        this.mContext = context;
        this.mShowLoading = true;
        this.mMsg = msg;
    }

    @Override
    public void onSubscribe(Disposable d) {
        onRequestStart();
    }

    @Override
    public void onNext(T response) {
        onRequestEnd();
        try {
            if (response.getState() == 0) {
                onSuccess(response);
            } else {
                handleBusinessState(response);
            }
        } catch (Exception e) {
            onError(e);
        }
    }

    @Override
    public void onError(Throwable e) {
        onRequestEnd();
        handleError(e, null);
    }

    private void handleBusinessState(T response) {
        if (response.getState() == 97) {
            SpUtils.USER.removeKey(Constants.TOKEN);
            Intent intent = new Intent(MyApplication.getAppContext(), LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            MyApplication.getAppContext().startActivity(intent);
        } else {
            handleError(null, response.getMsg());
        }
    }

    private void handleError(Throwable e, String errorMsg) {
        if (e != null) {
            if (e instanceof retrofit2.HttpException) {
                onException(ExceptionReason.BAD_NETWORK);
            } else if (e instanceof ConnectException || e instanceof UnknownHostException) {
                onException(ExceptionReason.CONNECT_ERROR);
            } else if (e instanceof InterruptedIOException) {
                onException(ExceptionReason.CONNECT_TIMEOUT);
            } else if (e instanceof JsonParseException || e instanceof JSONException || e instanceof ParseException) {
                onException(ExceptionReason.PARSE_ERROR);
            } else {
                onException(ExceptionReason.UNKNOWN_ERROR);
            }
        } else {
            if (TextUtils.isEmpty(errorMsg)) {
                errorMsg = RESPONSE_RETURN_ERROR;
            }
            ToastUtils.toastNormal(mContext, errorMsg);
            LogUtils.e(errorMsg);
        }
    }

    private void onException(ExceptionReason reason) {
        String message;
        switch (reason) {
            case CONNECT_ERROR:
                message = CONNECT_ERROR;
                break;
            case CONNECT_TIMEOUT:
                message = CONNECT_TIMEOUT;
                break;
            case BAD_NETWORK:
                message = BAD_NETWORK;
                break;
            case PARSE_ERROR:
                message = PARSE_ERROR;
                break;
            case UNKNOWN_ERROR:
            default:
                message = UNKNOWN_ERROR;
                break;
        }
        ToastUtils.toastError(mContext, message);
        LogUtils.e(message);
    }

    @Override
    public void onComplete() {
        // 请求结束
    }

    /**
     * 网络请求成功并返回正确值
     *
     * @param response 返回值
     */
    public abstract void onSuccess(T response);

    /**
     * 网络请求失败原因
     */
    public enum ExceptionReason {
        PARSE_ERROR, BAD_NETWORK, CONNECT_ERROR, CONNECT_TIMEOUT, UNKNOWN_ERROR
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
