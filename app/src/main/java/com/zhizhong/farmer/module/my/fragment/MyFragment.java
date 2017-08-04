package com.zhizhong.farmer.module.my.fragment;

import android.os.Bundle;
import android.view.View;

import com.zhizhong.farmer.R;
import com.zhizhong.farmer.base.BaseFragment;
import com.zhizhong.farmer.module.my.activity.MyOrderActivity;
import com.zhizhong.farmer.module.my.activity.MyWalletActivity;
import com.zhizhong.farmer.module.my.activity.MyToolListActivity;

import butterknife.OnClick;


/**
 * Created by administartor on 2017/8/1.
 */

public class MyFragment extends BaseFragment {

    @Override
    protected int getContentView() {
        return R.layout.frag_my;
    }

    public static MyFragment newInstance() {

        Bundle args = new Bundle();

        MyFragment fragment = new MyFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {

    }

    @Override
    public void again() {

    }

    @OnClick({R.id.iv_my_set,R.id.tv_my_wallet,R.id.tv_my_all, R.id.tv_my_djd, R.id.tv_my_yjd, R.id.tv_my_complete})
    public void onViewClick(View view) {
        switch (view.getId()) {
            case R.id.iv_my_set://设置
                STActivity(MyToolListActivity.class);
//                STActivity(LoginActivity.class);
                break;
            case R.id.tv_my_wallet://我的钱包
                STActivity(MyWalletActivity.class);
                break;
            case R.id.tv_my_all:
                STActivity(MyOrderActivity.class);
                break;
            case R.id.tv_my_djd:
                STActivity(MyOrderActivity.class);
                break;
            case R.id.tv_my_yjd:
                STActivity(MyOrderActivity.class);
                break;
            case R.id.tv_my_complete:
                STActivity(MyOrderActivity.class);
                break;
        }
    }
}
