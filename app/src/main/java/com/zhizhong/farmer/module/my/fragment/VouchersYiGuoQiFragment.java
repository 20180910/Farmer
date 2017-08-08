package com.zhizhong.farmer.module.my.fragment;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.zhizhong.farmer.R;
import com.zhizhong.farmer.base.BaseFragment;
import com.zhizhong.farmer.module.my.adapter.VouchersAdapter;

import butterknife.BindView;

/**
 * Created by administartor on 2017/8/8.
 */

public class VouchersYiGuoQiFragment extends BaseFragment {
    @BindView(R.id.rv_vouchers)
    RecyclerView rv_vouchers;

    VouchersAdapter adapter;
    @Override
    public void again() {

    }

    @Override
    protected int getContentView() {
        return R.layout.frag_vouchers;
    }

    @Override
    protected void initView() {
        adapter=new VouchersAdapter(mContext,R.layout.item_vouchers,0);
        rv_vouchers.setLayoutManager(new LinearLayoutManager(mContext));
        rv_vouchers.setAdapter(adapter);

    }

    @Override
    protected void initData() {

    }

    @Override
    protected void onViewClick(View v) {

    }
}
