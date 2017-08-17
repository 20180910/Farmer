package com.zhizhong.farmer.module.my.activity;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.zhizhong.farmer.R;
import com.zhizhong.farmer.base.BaseActivity;
import com.zhizhong.farmer.module.my.adapter.MyFarmerAdapter;

import butterknife.BindView;

/**
 * Created by administartor on 2017/8/7.
 */

public class MyFarmerActivity extends BaseActivity {
    @BindView(R.id.rv_my_farmer)
    RecyclerView rv_my_farmer;

    MyFarmerAdapter adapter;
    @Override
    public void again() {

    }

    @Override
    protected int getContentView() {
        setAppTitle("我的农户");
        return R.layout.act_my_farmer;
    }

    @Override
    protected void initView() {
        adapter=new MyFarmerAdapter(mContext,R.layout.item_my_farmer,0);

        rv_my_farmer.setLayoutManager(new LinearLayoutManager(mContext));
        rv_my_farmer.setAdapter(adapter);


    }

    @Override
    protected void initData() {

    }

    @Override
    protected void onViewClick(View v) {

    }
}
