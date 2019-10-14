package com.zh.template.utils;

import android.annotation.SuppressLint;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class DownLoadManage {

    private static final int DOWNLOAD_ING = 1;      //下载中
    private static final int DOWNLOAD_FAILED = 2;   //下载失败
    private static final int DOWNLOAD_FINISH = 3;   //下载结束

    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case DOWNLOAD_ING: // 正在下载
                    if (mOnDownloadListener != null)
                        mOnDownloadListener.downloading(mDownloadProgress);

                    break;
                case DOWNLOAD_FAILED: // 下载失败
                    if (mOnDownloadListener != null)
                        mOnDownloadListener.downloadFailed();
                    break;
                case DOWNLOAD_FINISH:// 下载完成
                    if (mOnDownloadListener != null)
                        mOnDownloadListener.downloadFinish(mSaveDir);
                    break;
                default:
                    break;
            }
        }
    };


    private Call call;
    private String mSaveDir;
    private int mDownloadProgress;
    private OnDownloadListener mOnDownloadListener;

    public void startDownload(String url, String saveDir, OnDownloadListener onDownloadListener) {
        this.mSaveDir = saveDir;
        this.mOnDownloadListener = onDownloadListener;
        downloadFile(url, saveDir);
    }

    public void stopDownload() {
        if (call != null)
            call.cancel();
    }

    private void downloadFile(String url, final String saveDir) {
        final long startTime = System.currentTimeMillis();
        Log.i("DOWNLOAD FILE", "startTime=" + startTime);
        OkHttpClient okHttpClient = new OkHttpClient();
        Request request = new Request.Builder().url(url).build();
        call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                e.printStackTrace();
                mHandler.sendEmptyMessage(DOWNLOAD_FAILED); // 下载失败
                Log.i("DOWNLOAD FILE", "download failed");
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) {
                InputStream is = null;
                byte[] buf = new byte[2048];
                int len;
                FileOutputStream fos = null;

                try {
                    ResponseBody body = response.body();
                    if (body == null)
                        throw new NullPointerException("ResponseBody is null!!!");
                    is = body.byteStream();
                    long total = body.contentLength();

                    File file = new File(saveDir);
                    fos = new FileOutputStream(file);
                    long sum = 0;
                    while ((len = is.read(buf)) != -1) {
                        fos.write(buf, 0, len);
                        sum += len;
                        mDownloadProgress = (int) (sum * 1.0f / total * 100);
                        mHandler.sendEmptyMessage(DOWNLOAD_ING);   // 下载中// 更新进度
                        Log.i("DOWNLOAD FILE", "downloading " + mDownloadProgress + "%");
                    }
                    fos.flush();
                    mHandler.sendEmptyMessage(DOWNLOAD_FINISH); // 下载完成
                    Log.i("DOWNLOAD FILE", "download success");
                    Log.i("DOWNLOAD FILE", "totalTime=" + (System.currentTimeMillis() - startTime));
                } catch (Exception e) {
                    e.printStackTrace();
                    mHandler.sendEmptyMessage(DOWNLOAD_FAILED);
                    Log.i("DOWNLOAD FILE", "download failed");
                } finally {
                    try {
                        if (is != null)
                            is.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    try {
                        if (fos != null)
                            fos.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    public interface OnDownloadListener {

        void downloading(int progress);

        void downloadFailed();

        void downloadFinish(String path);
    }

}
