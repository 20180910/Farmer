package com.zhizhong.farmer.module.home.activity;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.github.baseclass.adapter.LoadMoreAdapter;
import com.github.baseclass.adapter.LoadMoreViewHolder;
import com.zhizhong.farmer.R;
import com.zhizhong.farmer.base.BaseActivity;
import com.zhizhong.farmer.tools.SpaceItemDecoration;

import butterknife.BindView;

/**
 * Created by administartor on 2017/8/8.
 */

public class CityActivity extends BaseActivity {
    @BindView(R.id.rv_hot_city)
    RecyclerView rv_hot_city;

    LoadMoreAdapter adapter;

    @Override
    public void again() {

    }

    @Override
    protected int getContentView() {
        setAppTitle("选择城市");
        return R.layout.act_city;
    }

    @Override
    protected void initView() {
        adapter=new LoadMoreAdapter(mContext,R.layout.item_hot_city,0) {
            @Override
            public void bindData(LoadMoreViewHolder loadMoreViewHolder, int i, Object o) {

            }
        };
        adapter.setTestListSize(11);
        rv_hot_city.setLayoutManager(new GridLayoutManager(mContext,3));
        rv_hot_city.addItemDecoration(new SpaceItemDecoration(10));
        rv_hot_city.setAdapter(adapter);

    }

    @Override
    protected void initData() {

    }

    @Override
    protected void onViewClick(View v) {

    }
}
