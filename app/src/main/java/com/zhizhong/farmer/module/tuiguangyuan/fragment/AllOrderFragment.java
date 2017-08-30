package com.zhizhong.farmer.module.tuiguangyuan.fragment;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.zhizhong.farmer.R;
import com.zhizhong.farmer.base.BaseFragment;
import com.zhizhong.farmer.module.my.adapter.AllOrderAdapter;

import butterknife.BindView;

/**
 * Created by administartor on 2017/8/2.
 */

public class AllOrderFragment extends BaseFragment{
    @BindView(R.id.rv_all_order)
    RecyclerView rv_all_order;

    AllOrderAdapter allOrderAdapter;
    @Override
    public void again() {

    }

    @Override
    protected int getContentView() {
        return R.layout.frag_all_order;
    }

    @Override
    protected void initView() {
        allOrderAdapter=new AllOrderAdapter(mContext,1,0);
        allOrderAdapter.setTestListSize(10);

        rv_all_order.setLayoutManager(new LinearLayoutManager(mContext));
        rv_all_order.setAdapter(allOrderAdapter);

    }

    @Override
    protected void initData() {

    }

    @Override
    protected void onViewClick(View v) {

    }
}
