package com.zhizhong.farmer.module.my.fragment;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.zhizhong.farmer.R;
import com.zhizhong.farmer.base.BaseFragment;
import com.zhizhong.farmer.module.my.adapter.CompleteOrderAdapter;

import butterknife.BindView;

/**
 * Created by administartor on 2017/8/2.
 */

public class CompleteOrderFragment extends BaseFragment{
    @BindView(R.id.rv_complete_order)
    RecyclerView rv_complete_order;

    CompleteOrderAdapter adapter;
    @Override
    public void again() {

    }

    @Override
    protected int getContentView() {
        return R.layout.frag_complete_order;
    }

    @Override
    protected void initView() {
        adapter=new CompleteOrderAdapter(mContext,R.layout.item_complete_order,0);
        adapter.setTestListSize(10);

        rv_complete_order.setLayoutManager(new LinearLayoutManager(mContext));
        rv_complete_order.setAdapter(adapter);

    }

    @Override
    protected void initData() {

    }

    @Override
    protected void onViewClick(View v) {

    }
}
