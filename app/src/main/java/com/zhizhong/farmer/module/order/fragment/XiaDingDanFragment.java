package com.zhizhong.farmer.module.order.fragment;

import android.os.Bundle;
import android.view.View;

import com.zhizhong.farmer.base.BaseFragment;

/**
 * Created by administartor on 2017/8/1.
 */

public class XiaDingDanFragment extends BaseFragment {
    @Override
    protected int getContentView() {
        return 1;
    }

    public static XiaDingDanFragment newInstance() {


        Bundle args = new Bundle();

        XiaDingDanFragment fragment = new XiaDingDanFragment();
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
    protected void onViewClick(View v) {

    }

    @Override
    public void again() {

    }
}
