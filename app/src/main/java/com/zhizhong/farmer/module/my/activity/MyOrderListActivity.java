package com.zhizhong.farmer.module.my.activity;

import android.content.Intent;
import android.os.Handler;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;

import com.zhizhong.farmer.R;
import com.zhizhong.farmer.base.BaseActivity;
import com.zhizhong.farmer.module.my.Constant;
import com.zhizhong.farmer.module.my.adapter.OrderFragmentAdapter;
import com.zhizhong.farmer.module.my.fragment.AllOrderFragment;

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
    AllOrderFragment  daiWanShanOrderFragment;
    AllOrderFragment  daiQueRenOrderFragment;
    AllOrderFragment  daiJieDanOrderFragment;
    AllOrderFragment yiJieDanOrderFragment;
    AllOrderFragment completeOrderFragment;
    private int type;

    /*  DaiWanShanOrderFragment daiWanShanOrderFragment;
      DaiQueRenOrderFragment  daiQueRenOrderFragment;
      DaiJieDanOrderFragment  daiJieDanOrderFragment;
      YiJieDanOrderFragment  yiJieDanOrderFragment;
      CompleteOrderFragment  completeOrderFragment;*/
    @Override
    protected int getContentView() {
        setAppTitle("我的订单");
        return R.layout.act_my_order;
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        Log.i("====","====onNewIntent====initView");
    }

    @Override
    protected void initView() {
        Log.i("====","========initView");
        type = getIntent().getIntExtra(Constant.type, Constant.type_0);
        adapter = new OrderFragmentAdapter(getSupportFragmentManager());

        allOrderFragment = AllOrderFragment.newInstance(Constant.type_0);
        daiWanShanOrderFragment =   AllOrderFragment.newInstance(Constant.type_1);
        daiQueRenOrderFragment =   AllOrderFragment.newInstance(Constant.type_2);
        daiJieDanOrderFragment =   AllOrderFragment.newInstance(Constant.type_3);
        yiJieDanOrderFragment  =   AllOrderFragment.newInstance(Constant.type_4);
        completeOrderFragment  =   AllOrderFragment.newInstance(Constant.type_5);

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
        Handler handler=new Handler(getMainLooper());
        handler.post(new Runnable() {
            @Override
            public void run() {
                vp_my_order.setCurrentItem(type);
            }
        });
       /* vp_my_order.postDelayed(new Runnable() {
            @Override
            public void run() {

            }
        },200);*/
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void onViewClick(View v) {

    }


}
