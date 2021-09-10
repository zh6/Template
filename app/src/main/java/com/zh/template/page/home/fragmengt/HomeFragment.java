package com.zh.template.page.home.fragmengt;

import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.zh.template.R;
import com.zh.template.base.BaseFragment;
import com.zh.template.common.ListItemDecoration;
import com.zh.template.entity.AddressEntity;
import com.zhy.adapter.recyclerview.CommonAdapter;

import java.util.ArrayList;
import java.util.List;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
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


    @Override
    protected void initData() {


    }
}
