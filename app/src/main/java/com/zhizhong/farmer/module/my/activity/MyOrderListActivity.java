package com.zhizhong.farmer.module.my.activity;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.zhizhong.farmer.R;
import com.zhizhong.farmer.base.BaseActivity;
import com.zhizhong.farmer.module.my.adapter.OrderFragmentAdapter;
import com.zhizhong.farmer.module.my.fragment.AllOrderFragment;
import com.zhizhong.farmer.module.my.fragment.CompleteOrderFragment;
import com.zhizhong.farmer.module.my.fragment.DaiJieDanOrderFragment;
import com.zhizhong.farmer.module.my.fragment.DaiQueRenOrderFragment;
import com.zhizhong.farmer.module.my.fragment.DaiWanShanOrderFragment;
import com.zhizhong.farmer.module.my.fragment.YiJieDanOrderFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by administartor on 2017/8/2.
 */

public class MyOrderListActivity extends BaseActivity {

    @BindView(R.id.tl_my_order)
    TabLayout tl_all_order;
    @BindView(R.id.vp_my_order)
    ViewPager vp_my_order;

    OrderFragmentAdapter adapter;
    List<Fragment> list;

    AllOrderFragment allOrderFragment;
    DaiWanShanOrderFragment daiWanShanOrderFragment;
    DaiQueRenOrderFragment daiQueRenOrderFragment;
    DaiJieDanOrderFragment daiJieDanOrderFragment;
    YiJieDanOrderFragment yiJieDanOrderFragment;
    CompleteOrderFragment completeOrderFragment;

    @Override
    public void again() {

    }

    @Override
    protected int getContentView() {
        setAppTitle("我的订单");
        return R.layout.act_my_order;
    }

    @Override
    protected void initView() {
        adapter = new OrderFragmentAdapter(getSupportFragmentManager());

        allOrderFragment = new AllOrderFragment();
        daiWanShanOrderFragment = new DaiWanShanOrderFragment();
        daiQueRenOrderFragment = new DaiQueRenOrderFragment();
        daiJieDanOrderFragment = new DaiJieDanOrderFragment();
        yiJieDanOrderFragment  = new YiJieDanOrderFragment();
        completeOrderFragment  = new CompleteOrderFragment();

        list = new ArrayList<>();
        list.add(allOrderFragment);
        list.add(daiWanShanOrderFragment);
        list.add(daiQueRenOrderFragment );
        list.add(daiJieDanOrderFragment );
        list.add(yiJieDanOrderFragment  );
        list.add(completeOrderFragment  );

        adapter.setList(list);
        vp_my_order.setAdapter(adapter);
        vp_my_order.setOffscreenPageLimit(list.size()-1);

        tl_all_order.setupWithViewPager(vp_my_order);
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void onViewClick(View v) {

    }


}
