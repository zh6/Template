package com.zh.template.utils;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.view.ViewGroup;
import com.zh.template.R;
import com.zh.template.widget.HintLayout;
import androidx.annotation.DrawableRes;
import androidx.annotation.RequiresPermission;
import androidx.annotation.StringRes;

import static android.Manifest.permission.ACCESS_NETWORK_STATE;

/**
 *  *界面状态管理类
 *  *@author zhaohui
 *  *@time 2019/10/24 10:10
 *  * ----------Dragon be here!----------/
 *  * 　　　┏┓　　 ┏┓
 *  * 　　┏┛┻━━━┛┻┓━━━
 *  * 　　┃　　　　　 ┃
 *  * 　　┃　　　━　  ┃
 *  * 　　┃　┳┛　┗┳
 *  * 　　┃　　　　　 ┃
 *  * 　　┃　　　┻　  ┃
 *  * 　　┃　　　　   ┃
 *  * 　　┗━┓　　　┏━┛Code is far away from bug with the animal protecting
 *  * 　　　　┃　　　┃    神兽保佑,代码无bug
 *  * 　　　　┃　　　┃
 *  * 　　　　┃　　　┗━━━┓
 *  * 　　　　┃　　　　　　┣┓
 *  * 　　　　┃　　　　　　　┏┛
 *  * 　　　　┗┓┓┏━┳┓┏┛━━━━━
 *  * 　　　　　┃┫┫　┃┫┫
 *  * 　　　　　┗┻┛　┗┻┛
 *  * ━━━━━━━━━━━神兽出没━━━━━━━━━━━━━━
 *  */
public final class StatusManager {
    /** 提示布局 */
    private HintLayout mHintLayout;

    /**
     * 显示空提示
     */
    public void showEmpty(View view) {
        showLayout(view, R.drawable.my_icon_hint_empty, R.string.hint_layout_no_data);
    }
    public void hideLayout(){
        mHintLayout.hide();
    }
    /**
     * 显示错误提示
     */
    public void showError(View view) {
        // 判断当前网络是否可用
        if (isNetworkAvailable(view.getContext())) {
            showLayout(view, R.drawable.my_icon_hint_request, R.string.hint_layout_error_request);
        } else {
            showLayout(view, R.drawable.my_icon_hint_nerwork, R.string.hint_layout_error_network);
        }
    }

    /**
     * 显示自定义提示
     */
    public void showLayout(View view, @DrawableRes int drawableId, @StringRes int stringId) {
        showLayout(view, ContextCompat.getDrawable(view.getContext(), drawableId), view.getResources().getString(stringId));
    }

    public void showLayout(View view, Drawable drawable, CharSequence hint) {
        if (mHintLayout == null) {

            if (view instanceof HintLayout) {
                mHintLayout = (HintLayout) view;
            } else if (view instanceof ViewGroup) {
                mHintLayout = findHintLayout((ViewGroup) view);
            }

            if (mHintLayout == null) {
                // 必须在布局中定义一个 HintLayout
                throw new IllegalStateException("You didn't add this HintLayout to your layout");
            }
        }
        mHintLayout.show();
        mHintLayout.setIcon(drawable);
        mHintLayout.setHint(hint);
    }

    /**
     * 智能获取布局中的 HintLayout 对象
     */
    private static HintLayout findHintLayout(ViewGroup group) {
        for (int i = 0; i < group.getChildCount(); i++) {
            View view = group.getChildAt(i);
            if ((view instanceof HintLayout)) {
                return (HintLayout) view;
            } else if (view instanceof ViewGroup) {
                HintLayout layout = findHintLayout((ViewGroup) view);
                if (layout != null) {
                    return layout;
                }
            }
        }
        return null;
    }

    /**
     * 判断网络功能是否可用
     */
    @RequiresPermission(ACCESS_NETWORK_STATE)
    private static boolean isNetworkAvailable(Context context){
        NetworkInfo info = ((ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE)).getActiveNetworkInfo();
        return (info != null && info.isConnected());
    }
}
