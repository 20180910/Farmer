package com.zhizhong.farmer.module.order.activity;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.zhizhong.farmer.R;
import com.zhizhong.farmer.base.BaseActivity;
import com.zhizhong.farmer.module.order.adapter.SelectOtherFarmerAdapter;

import butterknife.BindView;

/**
 * Created by administartor on 2017/8/5.
 */

public class SelectOhterFarmerActivity extends BaseActivity {
    @BindView(R.id.rv_select_other_farmer)
    RecyclerView rv_select_other_farmer;

    SelectOtherFarmerAdapter adapter;
    @Override
    public void again() {

    }

    @Override
    protected int getContentView() {
        setAppTitle("选择其他农户");
        return R.layout.act_select_other_farmer;
    }

    @Override
    protected void initView() {
        adapter=new SelectOtherFarmerAdapter(mContext,R.layout.item_other_farmer,0);
        adapter.setTestListSize(10);

        rv_select_other_farmer.setLayoutManager(new LinearLayoutManager(mContext));
        rv_select_other_farmer.setAdapter(adapter);

    }

    @Override
    protected void initData() {

    }

    @Override
    protected void onViewClick(View v) {

    }
}
