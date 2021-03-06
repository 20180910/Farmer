package com.zhizhong.farmer.module.my.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.List;

/**
 * Created by administartor on 2017/8/2.
 */

public class OrderFragmentAdapter extends FragmentStatePagerAdapter {
    String[]title={"全部","待完善","待支付","待接单","已接单","已完成"};
    List<Fragment> list;

    public void setList(List<Fragment> list) {
        this.list = list;
    }

    public OrderFragmentAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return list.get(position);
    }

    @Override
    public int getCount() {
        return list==null?0:list.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return title[position];
//        return super.getPageTitle(position);
    }
}
