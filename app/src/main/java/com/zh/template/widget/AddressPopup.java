package com.zh.template.widget;

import android.app.Activity;
import android.view.Gravity;
import android.view.View;
import android.view.animation.Animation;
import android.widget.ImageView;

import com.google.android.material.tabs.TabLayout;
import com.zh.template.R;
import com.zh.template.common.ListItemDecoration;
import com.zh.template.entity.AddressEntity;
import com.zh.template.net.service.AllService;
import com.zh.template.net.use.BaseResponse;
import com.zh.template.utils.ToastUtil;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.MultiItemTypeAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.ArrayList;
import java.util.List;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import razerdp.basepopup.BasePopupWindow;
import razerdp.util.animation.AnimationHelper;
import razerdp.util.animation.TranslationConfig;

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
public class AddressPopup extends BasePopupWindow implements TabLayout.OnTabSelectedListener {
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

    public AddressPopup(Activity context) {
        super(context);
        setContentView(createPopupById(R.layout.popup_address));
        //设置显示底部
        setPopupGravity(Gravity.TOP);
        this.context = context;
        initView();
    }
    Observable<BaseResponse<List<AddressEntity>>> getAddressList(String parentAreaCode, String level) {
        return AllService.getInstance().getAreaList(parentAreaCode, level)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    void initView() {
        mCloseView = findViewById(R.id.img_close);
        mTabLayout = findViewById(R.id.tb_address_tab);
        mProvinceView = findViewById(R.id.rv_address_province);
        mCityView = findViewById(R.id.rv_address_city);
        mAreaView = findViewById(R.id.rv_address_area);
        provinceList = new ArrayList<>();
        cityList = new ArrayList<>();
        areaList = new ArrayList<>();
        mProvinceAdapter = new CommonAdapter<AddressEntity>(context, R.layout.item_base_text, provinceList) {
            @Override
            protected void convert(ViewHolder holder, AddressEntity entity, int position) {
                holder.setText(R.id.name, entity.getAreaName());
            }
        };
        mCityAdapter = new CommonAdapter<AddressEntity>(context, R.layout.item_base_text, cityList) {
            @Override
            protected void convert(ViewHolder holder, AddressEntity entity, int position) {
                holder.setText(R.id.name, entity.getAreaName());
            }
        };
        mAreaAdapter = new CommonAdapter<AddressEntity>(context, R.layout.item_base_text, areaList) {
            @Override
            protected void convert(ViewHolder holder, AddressEntity entity, int position) {
                holder.setText(R.id.name, entity.getAreaName());
            }
        };
        mProvinceAdapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
                // 记录当前选择的省份
                AddressEntity entity = mProvinceAdapter.getDatas().get(position);
                mProvince = entity.getAreaCode();
                saveId[0] = entity.getAreaCode();
                mTabLayout.getTabAt(mTabLayout.getSelectedTabPosition()).setText(mProvince);
                mTabLayout.addTab(mTabLayout.newTab().setText("请选择"), true);
                getAddressList(entity.getAreaCode(), "2").doOnNext(res -> {
                    if (cityList != null) {
                        cityList.clear();
                    }
                    cityList.addAll(res.getAaData());
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
                mCity = entity.getAreaName();
                saveId[1] = entity.getAreaCode();
                mTabLayout.getTabAt(mTabLayout.getSelectedTabPosition()).setText(mCity);
                mTabLayout.addTab(mTabLayout.newTab().setText("请选择"), true);
                getAddressList(entity.getAreaCode(), "3").doOnNext(res -> {
                    if (areaList != null) {
                        areaList.clear();
                    }
                    areaList.addAll(res.getAaData());
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
                mArea = entity.getAreaName();
                saveId[2] = entity.getAreaCode();
                mTabLayout.getTabAt(mTabLayout.getSelectedTabPosition()).setText(mArea);
                ToastUtil.showShort(mProvince + mCity + mArea);
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
            provinceList.addAll(res.getAaData());
            mProvinceAdapter.notifyDataSetChanged();
        }).subscribe();

    }
    @Override
    protected Animation onCreateShowAnimation() {
        return AnimationHelper.asAnimation()
                .withTranslation(TranslationConfig.FROM_BOTTOM)
                .toShow();
    }

    @Override
    protected Animation onCreateDismissAnimation() {
        return AnimationHelper.asAnimation()
                .withTranslation(TranslationConfig.TO_BOTTOM)
                .toShow();
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
        if (address[0] == null) {
            return;
        }
        saveId = address;
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
            provinceList.addAll(res.getAaData());

            for (AddressEntity item : provinceList) {
                if (item.getAreaCode().equals(saveId[0])) {
                    mProvince = item.getAreaName();
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
            cityList.addAll(res.getAaData());
            for (AddressEntity item : cityList) {
                if (item.getAreaCode().equals(saveId[1])) {
                    mCity = item.getAreaName();
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
            areaList.addAll(res.getAaData());
            for (AddressEntity item : areaList) {
                if (item.getAreaCode().equals(saveId[2])) {
                    mArea = item.getAreaName();
                    // 记录当前选择的区域
                    mTabLayout.getTabAt(2).setText(mArea);
                    break;
                }
            }
            mAreaAdapter.notifyDataSetChanged();
        }).subscribe();
    }
}
