package com.zhizhong.farmer.module.my.activity;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.github.baseclass.adapter.LoadMoreAdapter;
import com.github.baseclass.adapter.LoadMoreViewHolder;
import com.zhizhong.farmer.R;
import com.zhizhong.farmer.base.BaseActivity;

import butterknife.BindView;

/**
 * Created by administartor on 2017/8/8.
 */

public class MyXiaJiActivity extends BaseActivity {
    @BindView(R.id.rv_my_xia_ji)
    RecyclerView rv_my_xia_ji;

    LoadMoreAdapter adapter;

    @Override
    public void again() {

    }

    @Override
    protected int getContentView() {
        setAppTitle("我的下级");
        return R.layout.act_my_xia_ji;
    }

    @Override
    protected void initView() {
        adapter=new LoadMoreAdapter(mContext,R.layout.item_my_xia_ji,0) {
            @Override
            public void bindData(LoadMoreViewHolder loadMoreViewHolder, int i, Object o) {
            }
        };
        adapter.setTestListSize(10);
        rv_my_xia_ji.setLayoutManager(new LinearLayoutManager(mContext));
        rv_my_xia_ji.setAdapter(adapter);

    }

    @Override
    protected void initData() {

    }

    @Override
    protected void onViewClick(View v) {

    }
}
