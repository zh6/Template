package com.zh.template.widget;

import android.app.Activity;
import android.support.design.widget.TabLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.view.animation.Animation;
import android.widget.ImageView;

import com.zh.template.R;
import com.zh.template.common.ListItemDecoration;
import com.zh.template.module.main.entity.AddressEntity;
import com.zh.template.network.RetrofitService;
import com.zh.template.utils.ToastUtils;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.MultiItemTypeAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import razerdp.basepopup.BasePopupWindow;

/**
 * *用户选择器
 * *@author zhaohui
 * *@time 2019/8/2 13:33
 * * ----------Dragon be here!----------/
 * * 　　　┏┓　　 ┏┓
 * * 　　┏┛┻━━━┛┻┓━━━
 * * 　　┃　　　　　 ┃
 * * 　　┃　　　━　  ┃
 * * 　　┃　┳┛　┗┳
 * * 　　┃　　　　　 ┃
 * * 　　┃　　　┻　  ┃
 * * 　　┃　　　　   ┃
 * * 　　┗━┓　　　┏━┛Code is far away from bug with the animal protecting
 * * 　　　　┃　　　┃    神兽保佑,代码无bug
 * * 　　　　┃　　　┃
 * * 　　　　┃　　　┗━━━┓
 * * 　　　　┃　　　　　　┣┓
 * * 　　　　┃　　　　　　　┏┛
 * * 　　　　┗┓┓┏━┳┓┏┛━━━━━
 * * 　　　　　┃┫┫　┃┫┫
 * * 　　　　　┗┻┛　┗┻┛
 * * ━━━━━━━━━━━神兽出没━━━━━━━━━━━━━━
 */
public class AddressFormPopup extends BasePopupWindow implements TabLayout.OnTabSelectedListener {
    private Activity context;
    private List<AddressEntity> provinceList;
    private List<AddressEntity> cityList;
    private List<AddressEntity> areaList;
    private ImageView mCloseView;
    private TabLayout mTabLayout;
    private RecyclerView mProvinceView;
    private RecyclerView mCityView;
    private RecyclerView mAreaView;

    private CommonAdapter<AddressEntity> mProvinceAdapter;
    private CommonAdapter<AddressEntity> mCityAdapter;
    private CommonAdapter<AddressEntity> mAreaAdapter;
    private OnItemClickListener mOnItemClickListener;//条目点击监听
    private String mProvince = null;
    private String mCity = null;
    private String mArea = null;
    private String[] saveId = new String[3];

    public AddressFormPopup(Activity context) {
        super(context);
        //设置显示底部
        setPopupGravity(Gravity.BOTTOM);
        this.context = context;
        initView();
    }

    Observable<List<AddressEntity>> getAddressList(String parentAreaCode, String level) {
        return RetrofitService.getInstance().getAreaList(parentAreaCode, level);
    }

    void initView() {
        provinceList = new ArrayList<>();
        cityList = new ArrayList<>();
        areaList = new ArrayList<>();
        mCloseView = findViewById(R.id.ic_close);
        mTabLayout = findViewById(R.id.tb_address_tab);
        mProvinceView = findViewById(R.id.rv_address_province);
        mCityView = findViewById(R.id.rv_address_city);
        mAreaView = findViewById(R.id.rv_address_area);
        mProvinceAdapter = new CommonAdapter<AddressEntity>(context, R.layout.item_base_text, provinceList) {
            @Override
            protected void convert(ViewHolder holder, AddressEntity entity, int position) {
                holder.setText(R.id.name, entity.areaName);
            }
        };
        mCityAdapter = new CommonAdapter<AddressEntity>(context, R.layout.item_base_text, cityList) {
            @Override
            protected void convert(ViewHolder holder, AddressEntity entity, int position) {
                holder.setText(R.id.name, entity.areaName);
            }
        };
        mAreaAdapter = new CommonAdapter<AddressEntity>(context, R.layout.item_base_text, areaList) {
            @Override
            protected void convert(ViewHolder holder, AddressEntity entity, int position) {
                holder.setText(R.id.name, entity.areaName);
            }
        };
        mProvinceAdapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
                // 记录当前选择的省份
                AddressEntity entity = mProvinceAdapter.getDatas().get(position);
                mProvince = entity.areaName;
                saveId[0] = entity.areaCode;
                mTabLayout.getTabAt(mTabLayout.getSelectedTabPosition()).setText(mProvince);
                mTabLayout.addTab(mTabLayout.newTab().setText("请选择"), true);
                getAddressList(entity.areaCode, "2").doOnNext(res -> {
                    if (cityList != null) {
                        cityList.clear();
                    }
                    cityList.addAll(res);
                    mCityAdapter.notifyDataSetChanged();
                }).subscribe();
                mProvinceView.setVisibility(View.GONE);
                mCityView.setVisibility(View.VISIBLE);
            }

            @Override
            public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
                return false;
            }
        });

        mCityAdapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
                // 记录当前选择的城市
                AddressEntity entity = mCityAdapter.getDatas().get(position);
                mCity = entity.areaName;
                saveId[1] = entity.areaCode;
                mTabLayout.getTabAt(mTabLayout.getSelectedTabPosition()).setText(mCity);
                mTabLayout.addTab(mTabLayout.newTab().setText("请选择"), true);
                getAddressList(entity.areaCode, "3").doOnNext(res -> {
                    if (areaList != null) {
                        areaList.clear();
                    }
                    areaList.addAll(res);
                    mAreaAdapter.notifyDataSetChanged();
                }).subscribe();
                mCityView.setVisibility(View.GONE);
                mAreaView.setVisibility(View.VISIBLE);
            }

            @Override
            public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
                return false;
            }
        });

        mAreaAdapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
                // 记录当前选择的区域
                AddressEntity entity = mAreaAdapter.getDatas().get(position);
                mArea = entity.areaName;
                saveId[2] = entity.areaCode;
                mTabLayout.getTabAt(mTabLayout.getSelectedTabPosition()).setText(mArea);
                ToastUtils.showShort(mProvince + mCity + mArea);
                if (mOnItemClickListener != null) {
                    mOnItemClickListener.onSelected(saveId);
                }
                dismiss();
            }

            @Override
            public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
                return false;
            }
        });
        mCloseView.setOnClickListener(c -> dismiss());
        //设置分割线
        mProvinceView.addItemDecoration(new ListItemDecoration(context, LinearLayoutManager.VERTICAL));
        mProvinceView.setLayoutManager(new LinearLayoutManager(context));
        mCityView.addItemDecoration(new ListItemDecoration(context, LinearLayoutManager.VERTICAL));
        mCityView.setLayoutManager(new LinearLayoutManager(context));
        mAreaView.addItemDecoration(new ListItemDecoration(context, LinearLayoutManager.VERTICAL));
        mAreaView.setLayoutManager(new LinearLayoutManager(context));
        mProvinceView.setAdapter(mProvinceAdapter);
        mCityView.setAdapter(mCityAdapter);
        mAreaView.setAdapter(mAreaAdapter);
        mTabLayout.addOnTabSelectedListener(this);
        mTabLayout.addTab(mTabLayout.newTab().setText("请选择"), true);
        // 显示省份列表
        getAddressList("", "1").doOnNext(res -> {
            provinceList.addAll(res);
            mProvinceAdapter.notifyDataSetChanged();
        }).subscribe();

    }

    @Override
    protected Animation onCreateShowAnimation() {
        return getTranslateVerticalAnimation(1f, 0, 300);
    }

    @Override
    protected Animation onCreateDismissAnimation() {
        return getTranslateVerticalAnimation(0, 1f, 300);
    }

    @Override
    public View onCreateContentView() {
        return createPopupById(R.layout.popup_address);
    }

    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        tab.setText("请选择");
        switch (tab.getPosition()) {
            case 0:
                mProvince = mCity = mArea = null;
                if (mTabLayout.getTabAt(2) != null) {
                    mTabLayout.removeTabAt(2);
                }
                if (mTabLayout.getTabAt(1) != null) {
                    mTabLayout.removeTabAt(1);
                }
                mProvinceView.setVisibility(View.VISIBLE);
                mCityView.setVisibility(View.GONE);
                mAreaView.setVisibility(View.GONE);
                break;
            case 1:
                mCity = mArea = null;
                if (mTabLayout.getTabAt(2) != null) {
                    mTabLayout.removeTabAt(2);
                }
                mProvinceView.setVisibility(View.GONE);
                mCityView.setVisibility(View.VISIBLE);
                mAreaView.setVisibility(View.GONE);
                break;
            case 2:
                mArea = null;
                mProvinceView.setVisibility(View.GONE);
                mCityView.setVisibility(View.GONE);
                mAreaView.setVisibility(View.VISIBLE);
                break;
            default:
                break;
        }
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.mOnItemClickListener = onItemClickListener;
    }

    //点击条目接口
    public interface OnItemClickListener {
        void onSelected(String[] saveId);
    }

    /**
     * 设置默认区域
     */
    public void setCity(String[] address) {
        if(address[0]==null){
            return;
        }
        saveId=address;
        mTabLayout.removeAllTabs();
        mTabLayout.addTab(mTabLayout.newTab());
        mTabLayout.addTab(mTabLayout.newTab());
        mTabLayout.addTab(mTabLayout.newTab());
        mTabLayout.getTabAt(2).select();
        mProvinceView.setVisibility(View.GONE);
        mCityView.setVisibility(View.GONE);
        mAreaView.setVisibility(View.VISIBLE);
        // 显示省份列表
        getAddressList("", "1").doOnNext(res -> {
            if (provinceList != null) {
                provinceList.clear();
            }
            provinceList.addAll(res);

            for (AddressEntity item : provinceList) {
                if (item.areaCode.equals(saveId[0])) {
                    mProvince = item.areaName;
                    mTabLayout.getTabAt(0).setText(mProvince);
                    break;
                }
            }
            mProvinceAdapter.notifyDataSetChanged();
        }).subscribe();
        getAddressList(saveId[0], "2").doOnNext(res -> {
            if (cityList != null) {
                cityList.clear();
            }
            cityList.addAll(res);
            for (AddressEntity item : cityList) {
                if (item.areaCode.equals(saveId[1])) {
                    mCity = item.areaName;
                    mTabLayout.getTabAt(1).setText(mCity);
                    break;
                }
            }
            mCityAdapter.notifyDataSetChanged();
        }).subscribe();
        getAddressList(saveId[1], "3").doOnNext(res -> {
            if (areaList != null) {
                areaList.clear();
            }
            areaList.addAll(res);
            for (AddressEntity item : areaList) {
                if (item.areaCode.equals(saveId[2])) {
                    mArea = item.areaName;
                    // 记录当前选择的区域
                    mTabLayout.getTabAt(2).setText(mArea);
                    break;
                }
            }
            mAreaAdapter.notifyDataSetChanged();
        }).subscribe();
    }
}
