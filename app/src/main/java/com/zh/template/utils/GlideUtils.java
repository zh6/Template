package com.zh.template.utils;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.zh.template.R;

/**
 * glide图片加载工具类
 */
public class GlideUtils {
    /**
     * 基本图片加载
      * @param context
     * @param url
     * @param imageView
     */
    public static void loadImage(Context context, String url, ImageView imageView) {
        Glide.with(context)
                .load(url)
                .error(R.drawable.ic_launcher_background)
                .diskCacheStrategy(DiskCacheStrategy.RESULT)
                .into(imageView);
    }

    /**
     * 圆形图片加载
     * @param context
     * @param url
     * @param imageView
     */
    public static void loadCircleImage(Context context, String url, ImageView imageView) {
        Glide.with(context)
                .load(url)
                .error(R.drawable.ic_launcher_background)
                .transform(new GlideCircleTransform(context))
                .into(imageView);
    }
}
