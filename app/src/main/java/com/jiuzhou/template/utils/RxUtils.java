package com.jiuzhou.template.utils;

import com.trello.rxlifecycle4.LifecycleProvider;
import com.trello.rxlifecycle4.android.ActivityEvent;
import com.trello.rxlifecycle4.android.FragmentEvent;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class RxUtils {
    // 在非 Activity/Fragment 环境中应用调度
    public static <T> Observable<T> applySchedulers(Observable<T> observable) {
        return observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    // 在 Activity 环境中应用调度并绑定生命周期
    public static <T> Observable<T> applySchedulersWithActivityLifecycle(Observable<T> observable, LifecycleProvider<ActivityEvent> lifecycleProvider) {
        return observable.compose(lifecycleProvider.bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    // 在 Fragment 环境中应用调度并绑定生命周期
    public static <T> Observable<T> applySchedulersWithFragmentLifecycle(Observable<T> observable, LifecycleProvider<FragmentEvent> lifecycleProvider) {
        return observable.compose(lifecycleProvider.bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
}
