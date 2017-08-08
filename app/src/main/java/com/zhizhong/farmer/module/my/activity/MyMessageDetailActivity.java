package com.zhizhong.farmer.module.my.activity;

import android.view.View;

import com.github.baseclass.adapter.LoadMoreAdapter;
import com.zhizhong.farmer.R;
import com.zhizhong.farmer.base.BaseActivity;

/**
 * Created by administartor on 2017/8/4.
 */

public class MyMessageDetailActivity extends BaseActivity {

    LoadMoreAdapter adapter;
    @Override
    public void again() {

    }

    @Override
    protected int getContentView() {
        setAppTitle("我的消息");
        return R.layout.act_my_message_detail;
    }

    @Override
    protected void initView() {
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void onViewClick(View v) {

    }
}
