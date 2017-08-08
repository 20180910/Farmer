package com.zhizhong.farmer.module.my.activity;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.zhizhong.farmer.R;
import com.zhizhong.farmer.base.BaseActivity;
import com.zhizhong.farmer.module.my.adapter.VouchersFragmentAdapter;
import com.zhizhong.farmer.module.my.fragment.VouchersFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by administartor on 2017/8/8.
 */

public class MyVouchersActivity extends BaseActivity {
    @BindView(R.id.tl_my_vouchers)
    TabLayout tl_my_vouchers;

    @BindView(R.id.vp_my_vouchers)
    ViewPager vp_my_vouchers;

    VouchersFragmentAdapter adapter;
    List<Fragment> list;

    VouchersFragment weiShiYongFragment;
    VouchersFragment yiShiYongFragment;
    VouchersFragment yiGuoQiFragment;

    @Override
    public void again() {

    }

    @Override
    protected int getContentView() {
        setAppTitle("我的抵用券");
        return R.layout.act_my_vouchers;
    }

    @Override
    protected void initView() {
        adapter = new VouchersFragmentAdapter(getSupportFragmentManager());

        weiShiYongFragment = new VouchersFragment();
        yiShiYongFragment = new VouchersFragment();
        yiGuoQiFragment = new VouchersFragment();

        list = new ArrayList<>();
        list.add(weiShiYongFragment);
        list.add(yiShiYongFragment);
        list.add(yiGuoQiFragment );

        adapter.setList(list);
        vp_my_vouchers.setAdapter(adapter);
        vp_my_vouchers.setOffscreenPageLimit(list.size()-1);

        tl_my_vouchers.setupWithViewPager(vp_my_vouchers);
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void onViewClick(View v) {

    }
}
