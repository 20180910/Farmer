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

public class ZhiBaoZhongXinListActivity extends BaseActivity {
    @BindView(R.id.rv_zhi_bao)
    RecyclerView rv_zhi_bao;

    LoadMoreAdapter adapter;

    @Override
    public void again() {

    }

    @Override
    protected int getContentView() {
        setAppTitle("植保中心");
        return R.layout.act_zhi_bao_zhong_xin_list;
    }
    @Override
    protected void initView() {
        adapter=new LoadMoreAdapter(mContext,R.layout.item_zhi_bao,0) {
            @Override
            public void bindData(LoadMoreViewHolder loadMoreViewHolder, int i, Object o) {

            }
        };
        adapter.setTestListSize(10);

        rv_zhi_bao.setLayoutManager(new LinearLayoutManager(mContext));
        rv_zhi_bao.setAdapter(adapter);

    }

    @Override
    protected void initData() {

    }

    @Override
    protected void onViewClick(View v) {

    }
}
