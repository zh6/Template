package com.jiuzhou.template.net.use;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.google.gson.JsonParseException;
import com.jiuzhou.template.base.MyApplication;
import com.jiuzhou.template.common.Constants;
import com.jiuzhou.template.page.LoginActivity;
import com.jiuzhou.template.utils.LogUtils;
import com.jiuzhou.template.utils.SpUtils;
import com.jiuzhou.template.utils.ToastUtils;
import com.jiuzhou.template.widget.LoadingDialog;

import java.io.InterruptedIOException;
import java.net.ConnectException;
import java.net.UnknownHostException;
import java.text.ParseException;

import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;

public abstract class BaseObserver<T> implements Observer<T> {
    private static final String TAG = BaseObserver.class.getSimpleName();

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

    public BaseObserver() {}

    public BaseObserver(Context context) {
        this.mContext = context;
    }

    public BaseObserver(Context context, boolean mShowLoading) {
        this.mContext = context;
        this.mShowLoading = mShowLoading;
    }

    public BaseObserver(Context context, String msg) {
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
            JSONObject json = JSONObject.parseObject(JSON.toJSONString(response));
            handleBusinessState(json);
            onSuccess(response);
        } catch (Exception e) {
            handleError(e);
        }
    }

    @Override
    public void onError(Throwable e) {
        onRequestEnd();
        handleError(e);
    }

    private void handleBusinessState(JSONObject json) {
        if (json.getIntValue("state") == 97) {
            SpUtils.USER.removeKey(Constants.TOKEN);
            Intent intent = new Intent(MyApplication.getAppContext(), LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            MyApplication.getAppContext().startActivity(intent);
        }
    }

    private void handleError(Throwable e) {
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
        Log.e(TAG, "Error occurred: ", e);
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
    public void onComplete() {}

    public abstract void onSuccess(T response);

    public enum ExceptionReason {
        PARSE_ERROR,
        BAD_NETWORK,
        CONNECT_ERROR,
        CONNECT_TIMEOUT,
        UNKNOWN_ERROR
    }

    protected void onRequestStart() {
        if (mShowLoading) {
            showProgressDialog();
        }
    }

    protected void onRequestEnd() {
        closeProgressDialog();
    }

    private void showProgressDialog() {
        loadingDialog = new LoadingDialog(mContext);
        if (TextUtils.isEmpty(mMsg)) {
            loadingDialog.show();
        } else {
            loadingDialog.setText(mMsg);
            loadingDialog.show();
        }
    }

    private void closeProgressDialog() {
        if (loadingDialog != null) {
            loadingDialog.dismiss();
        }
    }
}
