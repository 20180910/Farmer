package com.zhizhong.farmer.module.zixun.fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.zhizhong.farmer.R;
import com.zhizhong.farmer.base.BaseFragment;
import com.zhizhong.farmer.module.zixun.adapter.ZiXunAdapter;

import butterknife.BindView;

/**
 * Created by administartor on 2017/8/1.
 */

public class ZiXunFragment extends BaseFragment {
    @BindView(R.id.rv_zi_xun)
    RecyclerView rv_zi_xun;

    ZiXunAdapter ziXunAdapter;

    @Override
    protected int getContentView() {
        return R.layout.frag_zi_xun;
    }

    public static ZiXunFragment newInstance() {


        Bundle args = new Bundle();

        ZiXunFragment fragment = new ZiXunFragment();
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    protected void initView() {
        ziXunAdapter=new ZiXunAdapter(mContext,R.layout.item_tgy_my_msg,0);
        ziXunAdapter.setTestListSize(10);

        rv_zi_xun.setLayoutManager(new LinearLayoutManager(mContext));
        rv_zi_xun.setAdapter(ziXunAdapter);

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
