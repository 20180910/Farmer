package com.zhizhong.farmer.module.tuiguangyuan.activity;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.github.baseclass.adapter.LoadMoreAdapter;
import com.github.baseclass.adapter.LoadMoreViewHolder;
import com.zhizhong.farmer.R;
import com.zhizhong.farmer.base.BaseActivity;

import butterknife.BindView;

/**
 * Created by administartor on 2017/8/1.
 */

public class TGYMyFarmerActivity extends BaseActivity {
    @BindView(R.id.rv_tgy_my_farmer)
    RecyclerView rv_tgy_my_farmer;


    LoadMoreAdapter adapter;
    @Override
    protected int getContentView() {
        setAppTitle("我的农户");
        return R.layout.act_tgy_my_farmer;
    }

    @Override
    protected void initView() {
        adapter=new LoadMoreAdapter(mContext,R.layout.item_tgy_my_farmer,0) {
            @Override
            public void bindData(LoadMoreViewHolder loadMoreViewHolder, int i, Object o) {

            }
        };
        adapter.setTestListSize(10);
        rv_tgy_my_farmer.setLayoutManager(new LinearLayoutManager(mContext));
        rv_tgy_my_farmer.setAdapter(adapter);

    }

    @Override
    protected void initData() {

    }

    @Override
    protected void onViewClick(View v) {

    }

    @Override
    public void again() {

    }
}
