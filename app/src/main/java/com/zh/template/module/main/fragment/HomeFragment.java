package com.zh.template.module.main.fragment;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.zh.template.R;
import com.zh.template.base.BaseFragment;
import com.zh.template.common.ListItemDecoration;
import com.zh.template.module.main.entity.AddressEntity;
import com.zh.template.network.RetrofitService;
import com.zh.template.utils.RxUtils;
import com.zh.template.utils.ToastUtils;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.MultiItemTypeAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import io.reactivex.Observable;

public class HomeFragment extends BaseFragment {
    @BindView(R.id.recyclerview)
    RecyclerView mRecyclerView;//列表
    @BindView(R.id.refreshLayout)
    RefreshLayout refreshLayout;//下拉刷新控件
    private CommonAdapter<AddressEntity> adapter;//万能适配器
    private List<AddressEntity> list = new ArrayList<>(); //数据源
    private int pageNum = 1;//当前页

    @Override
    protected int setLayout() {
        return R.layout.fragment_one;
    }

    @Override
    protected void initView() {
        //设置分割线
        mRecyclerView.addItemDecoration(new ListItemDecoration(getContext(), LinearLayoutManager.VERTICAL));
        //设置纵向布局（必须设置）
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
    }


    Observable<List<AddressEntity>> getAddressList(String parentAreaCode, String level) {
        return RetrofitService.getInstance().getAreaList(parentAreaCode, level).compose(RxUtils.fragmentLifecycle(this));
    }
    @Override
    protected void initData() {
        //初始化数据
        getAddressList("", "1").doOnError(throwable -> {
            ToastUtils.showShort(throwable.getMessage());
        }).doOnNext(res -> {
            if (list != null) {
                list.clear();
            }
            list.addAll(res);
        }).subscribe();
        //adapter初始化需要三个参数（context，布局，数据）
        adapter = new CommonAdapter<AddressEntity>(getContext(), R.layout.item_base_text, list) {
            @Override
            protected void convert(ViewHolder holder, AddressEntity entity, int position) {
                //绑定View
                //1.第一种方式，使用封装好的方法
                holder.setText(R.id.name, entity.areaName);
                //2.第二种方式，先获取控件
//                TextView textView=holder.getView(R.id.name);
//                textView.setText(o.toString());
            }
        };
        //绑定适配器
        mRecyclerView.setAdapter(adapter);
        //下拉刷新操作
        refreshLayout.setOnRefreshListener(v -> {
            //将当前页置1
            pageNum = 1;
            //重新加载第一页的数据，先clear再addall
            getAddressList("", "1").doOnError(throwable -> {
                showError();
                v.finishRefresh();
                ToastUtils.showShort(throwable.getMessage());
            }).doOnNext(res -> {
                hideLayout();
                if (list != null) {
                    list.clear();
                }
                list.addAll(res);
                adapter.notifyDataSetChanged();
                //关闭刷新
                v.finishRefresh();
            }).subscribe();


        });
        //加载更多操作
        refreshLayout.setOnLoadMoreListener(v -> {
            //加载就是页数累加
            pageNum++;
            getAddressList("", "1").doOnError(throwable -> {
                v.finishRefresh();
                ToastUtils.showShort(throwable.getMessage());
            }).doOnNext(res -> {
                list.addAll(res);
                adapter.notifyDataSetChanged();
                //关闭加载
                v.finishLoadMore();
            }).subscribe();
        });
        //list点击事件
        adapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
                ToastUtils.showShort("点击了" + adapter.getDatas().get(position).areaName);
            }

            @Override
            public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
                return false;
            }
        });

    }
}
