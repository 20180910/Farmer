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
 * Created by administartor on 2017/8/4.
 */

public class TGYMyYongJinActivity extends BaseActivity {
    @BindView(R.id.rv_tgy_my_yong_jin)
    RecyclerView rv_tgy_my_yong_jin;

    LoadMoreAdapter adapter;


    @Override
    public void again() {

    }

    @Override
    protected int getContentView() {
        setAppTitle("我的佣金");
        return R.layout.act_my_yong_jin;
    }

    @Override
    protected void initView() {
        adapter=new LoadMoreAdapter(mContext,R.layout.item_tgy_yong_jin_mingxi,0) {
            @Override
            public void bindData(LoadMoreViewHolder loadMoreViewHolder, int i, Object o) {

            }
        };
        adapter.setTestListSize(10);
        rv_tgy_my_yong_jin.setLayoutManager(new LinearLayoutManager(mContext));

    }

    @Override
    protected void initData() {

    }

    @Override
    protected void onViewClick(View v) {

    }
}
