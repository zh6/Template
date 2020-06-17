package com.zh.template.module.main.fragment;

import android.Manifest;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.content.FileProvider;
import android.view.View;
import android.widget.Button;

import com.alibaba.fastjson.JSON;
import com.zh.template.R;
import com.zh.template.base.BaseFragment;
import com.zh.template.utils.ImageUtils;
import com.zh.template.utils.ToastUtils;

import java.io.File;
import java.io.IOException;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.internal.observers.BlockingBaseObserver;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import permissions.dispatcher.NeedsPermission;
import permissions.dispatcher.OnShowRationale;
import permissions.dispatcher.PermissionRequest;
import permissions.dispatcher.RuntimePermissions;

import static android.app.Activity.RESULT_OK;

@RuntimePermissions
public class MineFragment extends BaseFragment {
    @BindView(R.id.btn_xc)
    Button btnXc;
    @BindView(R.id.btn_pz)
    Button btnPz;
    Uri photoUri;
    @Override
    protected int setLayout() {
        return R.layout.fragment_four;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {

    }

    @OnClick({R.id.btn_xc, R.id.btn_pz})
    void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_xc:
                MineFragmentPermissionsDispatcher.openPicWithPermissionCheck(this);
                break;
            case R.id.btn_pz:
                MineFragmentPermissionsDispatcher.openCameraWithPermissionCheck(this);
                break;
        }
    }

    @NeedsPermission(Manifest.permission.CAMERA)
    void openCamera() {
        File file = new File(getContext().getExternalCacheDir(), "image.jpg");
        try {
            if (file.exists()) {
                file.delete();
            }
            file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        photoUri = Uri.fromFile(file);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N)
            photoUri = FileProvider.getUriForFile(getContext(), "com.zh.template.fileprovider", file);
        //调用系统相机
        Intent intentCamera = new Intent();
        intentCamera.setAction(MediaStore.ACTION_IMAGE_CAPTURE);
        //将拍照结果保存至photo_file的Uri中，不保留在相册中
        intentCamera.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);
        startActivityForResult(intentCamera, 1);
    }


    @NeedsPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
    void openPic() {
        Intent photoPickerIntent = new Intent(Intent.ACTION_GET_CONTENT);
        photoPickerIntent.setType("image/*");
        startActivityForResult(photoPickerIntent, 2);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode==RESULT_OK) {
            switch (requestCode) {
                case 1:
                    cropPhoto(photoUri);
                    break;
                case 2:
                    cropPhoto(data.getData());
                    break;
                // 裁剪图片
                case 3:
                    File file = new File(photoUri.getPath());
                    if (file.exists()) {
                        String  baseStr= ImageUtils.imageToBase64(file.getPath());
                        Map<String,String> map=new HashMap<>();
                        map.put("image",baseStr);
                        map.put("image_type","BASE64");
                    } else {
                        ToastUtils.showShort("找不到照片");
                    }
                    break;
            }
        }
    }
    /**
     * 裁剪图片
     */
    private void cropPhoto(Uri inputUri) {
        // 调用系统裁剪图片的 Action
        Intent cropPhotoIntent = new Intent("com.android.camera.action.CROP");
        // 设置数据Uri 和类型
        cropPhotoIntent.setDataAndType(inputUri, "image/*");
        // 授权应用读取 Uri，这一步要有，不然裁剪程序会崩溃
        cropPhotoIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        // 设置图片的最终输出目录
        cropPhotoIntent.putExtra(MediaStore.EXTRA_OUTPUT,
                photoUri = Uri.parse("file:////sdcard/image_output.jpg"));
        startActivityForResult(cropPhotoIntent, 3);
    }


}
