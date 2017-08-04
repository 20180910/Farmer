package com.zhizhong.farmer.module.my.fragment;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.zhizhong.farmer.R;
import com.zhizhong.farmer.base.BaseFragment;
import com.zhizhong.farmer.module.my.adapter.DaiJieDanOrderAdapter;

import butterknife.BindView;

/**
 * Created by administartor on 2017/8/2.
 */

public class DaiJieDanOrderFragment extends BaseFragment{
    @BindView(R.id.rv_djd_order)
    RecyclerView rv_djd_order;

    DaiJieDanOrderAdapter adapter;
    @Override
    public void again() {

    }

    @Override
    protected int getContentView() {
        return R.layout.frag_daijiedan_order;
    }

    @Override
    protected void initView() {
        adapter=new DaiJieDanOrderAdapter(mContext,1,0);
        adapter.setTestListSize(10);

        rv_djd_order.setLayoutManager(new LinearLayoutManager(mContext));
        rv_djd_order.setAdapter(adapter);

    }

    @Override
    protected void initData() {

    }

    @Override
    protected void onViewClick(View v) {

    }
}
