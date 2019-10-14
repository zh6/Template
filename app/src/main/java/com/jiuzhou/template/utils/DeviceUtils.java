package com.jiuzhou.template.utils;

import android.app.DownloadManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.support.v4.content.FileProvider;

import com.jiuzhou.template.BuildConfig;
import com.jiuzhou.template.base.MyApplication;

import java.io.File;

/**
 * <Pre>
 * 获取设备信息的工具类
 * </Pre>
 * Create by 2016/3/21 11:41
 */
public class DeviceUtils {
    /**
     * 获取当前应用的版本号
     */
    public static String getVersionName(Context context) {
        PackageManager packageManager = context.getPackageManager();
        PackageInfo packageInfo = null;
        try {
            packageInfo = packageManager.getPackageInfo(context.getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return "V0.0.0.0";
        }
        String versionName = packageInfo.versionName;
        return versionName;
    }

    /**
     * 下载apk
     *
     * @param context
     * @param isForce
     * @param url
     */
    public static void download(Context context, boolean isForce, String url) {
        String savePath = Environment.getExternalStorageDirectory() + "/Download/yshfresh_ps.apk";// 储存下载文件的目录
        File file = new File(savePath);
        final DownLoadManage downLoadManage = new DownLoadManage();
        final ProgressDialog pDialog = new ProgressDialog(context);
        // 设置进度条风格，风格为长形
        pDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        pDialog.setCanceledOnTouchOutside(false);// 设置在点击Dialog外是否取消Dialog进度条
        // 设置ProgressDialog 标题
        pDialog.setTitle("版本更新");
        // 设置ProgressDialog 提示信息
        pDialog.setMessage("正在下载,请稍后......");
        // 设置ProgressDialog 标题图标
//        pDialog.setIcon(R.drawable.ic_launcher);
        // 设置ProgressDialog 的进度条是否不明确
        pDialog.setIndeterminate(false);
        // 让ProgressDialog显示
        pDialog.show();
        if (!isForce) {
            // 设置ProgressDialog 是否可以按退回按键取消
            pDialog.setCancelable(true);
            pDialog.setOnCancelListener(new ProgressDialog.OnCancelListener() {
                @Override
                public void onCancel(DialogInterface dialog) {
                    downLoadManage.stopDownload();
                }
            });
        } else {
            pDialog.setCancelable(false);
        }
        downLoadManage.startDownload(
                url,
                file.getAbsolutePath(),
                new DownLoadManage.OnDownloadListener() {
                    @Override
                    public void downloading(int progress) {
                        pDialog.show();
                        pDialog.setProgress(progress);
                    }

                    @Override
                    public void downloadFailed() {
                        pDialog.dismiss();
                    }

                    @Override
                    public void downloadFinish(String path) {
                        pDialog.dismiss();
                        installApk(context, path);
                    }
                }
        );
    }

    /**
     * 安装 APK
     */
    public static void installApk(Context context, String fileName) {
        File apkFile = new File(fileName);
        if (!apkFile.exists()) ToastUtils.showShort("安装文件不存在");
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);// 由于没有在Activity环境下启动Activity,设置下面的标签
        if (Build.VERSION.SDK_INT >= 24) { // 判读版本是否在7.0以上
            // 参数1:上下文
            // 参数2:Provider主机地址和配置文件中保持一致
            // 参数3:共享的文件
            Uri apkUri = FileProvider.getUriForFile(context, BuildConfig.APPLICATION_ID + ".fileprovider", apkFile);
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);// 添加这一句表示对目标应用临时授权该Uri所代表的文件
            intent.setDataAndType(apkUri, "application/vnd.android.package-archive");
        } else {
            intent.setDataAndType(Uri.fromFile(apkFile), "application/vnd.android.package-archive");
        }
        context.startActivity(intent);
    }

    /**
     * 更新下载apk
     * @param context 上下文对象
     * @param title   程序的名字
     * @param url     下载的url地址
     */
    public static long downLoadApk(Context context, String title, String url) {
        DownloadManager.Request request = new DownloadManager.Request(Uri.parse(url));
        request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI);
        request.setDestinationInExternalPublicDir("download", "updata.apk");
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
        // 设置 Notification 信息
        request.setTitle(title);
        request.setDescription("下载完成后请点击打开");
        request.setVisibleInDownloadsUi(true);
        request.allowScanningByMediaScanner();
        request.setMimeType("application/vnd.android.package-archive");
        // 实例化DownloadManager 对象
        DownloadManager downloadManager = (DownloadManager) MyApplication.getAppContext().getSystemService(Context.DOWNLOAD_SERVICE);
        final long refrence = downloadManager.enqueue(request);
        return refrence;
    }

    /**
     * 安装apk
     *
     * @param fileSavePath
     */
    public static void openAPK(Context context, String fileSavePath) {
        File file = new File(Uri.parse(fileSavePath).getPath());
        String filePath = file.getAbsolutePath();
        Intent intent = new Intent(Intent.ACTION_VIEW);
        Uri data = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {//判断版本大于等于7.0
            data = FileProvider.getUriForFile(context, BuildConfig.APPLICATION_ID + ".fileprovider", new File(filePath));
            intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);// 给目标应用一个临时授权
        } else {
            data = Uri.fromFile(file);
        }
        intent.setDataAndType(data, "application/vnd.android.package-archive");
        context.startActivity(intent);
    }
}
