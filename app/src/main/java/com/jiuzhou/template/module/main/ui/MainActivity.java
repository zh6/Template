package com.jiuzhou.template.module.main.ui;

import android.app.ActivityManager;
import android.content.Intent;
import android.support.v4.app.Fragment;

import com.flyco.tablayout.CommonTabLayout;
import com.flyco.tablayout.listener.CustomTabEntity;
import com.flyco.tablayout.listener.OnTabSelectListener;
import com.jiuzhou.template.R;
import com.jiuzhou.template.base.BaseActivity;
import com.jiuzhou.template.common.MsgEvent;
import com.jiuzhou.template.module.main.entity.TabEntity;
import com.jiuzhou.template.module.main.fragment.HomeFragment;
import com.jiuzhou.template.module.main.fragment.MineFragment;
import com.jiuzhou.template.module.main.fragment.OrderFragment;
import com.jiuzhou.template.module.main.fragment.StatisticsFragment;
import com.jiuzhou.template.utils.DoubleClickExit;
import com.jiuzhou.template.utils.RxBus;
import com.jiuzhou.template.utils.ToastUtils;

import java.util.ArrayList;

import butterknife.BindView;

public class MainActivity extends BaseActivity {
    @BindView(R.id.tl)
    CommonTabLayout mTabLayout;
    private ArrayList<Fragment> mFragments = new ArrayList<>();
    private String[] mTitles = {"首页", "订单", "统计", "设置"};
    private int[] mIconUnselectIds = {
            R.drawable.home_tabicon_home, R.drawable.home_tabicon_list, R.drawable.home_tabicon_tongji, R.drawable.home_tabicon_setting};
    private int[] mIconSelectIds = {
            R.drawable.home_tabicon_home_a, R.drawable.home_tabicon_list_a, R.drawable.home_tabicon_tongji_a, R.drawable.home_tabicon_setting_a};
    private ArrayList<CustomTabEntity> mTabEntities = new ArrayList<>();
    private HomeFragment homeFragment = new HomeFragment();
    private OrderFragment orderFragment = new OrderFragment();
    private StatisticsFragment statisticsFragment = new StatisticsFragment();
    private MineFragment mineFragment = new MineFragment();

    @Override
    protected int layoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView() {
        mFragments.add(homeFragment);
        mFragments.add(orderFragment);
        mFragments.add(statisticsFragment);
        mFragments.add(mineFragment);
        for (int i = 0; i < mTitles.length; i++) {
            mTabEntities.add(new TabEntity(mTitles[i], mIconSelectIds[i], mIconUnselectIds[i]));
        }
        mTabLayout.setTabData(mTabEntities, this, R.id.fl_change, mFragments);
        int id = getIntent().getIntExtra("id", 0);
        mTabLayout.setCurrentTab(id);
        mTabLayout.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelect(int position) {
                switch (position) {
//                    case 0:
//                        RxBus.getDefault().post(new MsgEvent("task"));
//                        break;
//                    case 1:
//                        RxBus.getDefault().post(new MsgEvent("map"));
//                        break;
                }
            }

            @Override
            public void onTabReselect(int position) {

            }
        });

    }

    @Override
    protected void initData() {
        RxBus.getInstance().toObservable(this,MsgEvent.class).doOnNext(msgEvent -> {
            if (msgEvent.getMsg().equals("mine")) {
                mTabLayout.setCurrentTab(3);
            } else if (msgEvent.getMsg().equals("order")) {
                mTabLayout.setCurrentTab(1);
            }
        }).subscribe();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void onBackPressed() {
        if (!isFastDoubleClick()) {
            ToastUtils.showShort(getString(R.string.double_exit));
        } else {
            //彻底关闭整个APP
            int currentVersion = android.os.Build.VERSION.SDK_INT;
            if (currentVersion > android.os.Build.VERSION_CODES.ECLAIR_MR1) {
                Intent startMain = new Intent(Intent.ACTION_MAIN);
                startMain.addCategory(Intent.CATEGORY_HOME);
                startMain.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(startMain);
                System.exit(0);
            } else {// android2.1
                ActivityManager am = (ActivityManager) getSystemService(ACTIVITY_SERVICE);
                am.restartPackage(getPackageName());
            }
        }
    }
}
