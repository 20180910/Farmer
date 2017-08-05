package com.zhizhong.farmer.module.home.fragment;

import android.view.View;
import android.widget.TextView;

import com.zhizhong.farmer.R;
import com.zhizhong.farmer.base.BaseFragment;

import butterknife.BindView;

/**
 * Created by administartor on 2017/8/4.
 */

public class HomeFragment extends BaseFragment {
    @BindView(R.id.app_title)
    TextView app_title;

    @Override
    public void again() {

    }

    @Override
    protected int getContentView() {
        return R.layout.frag_home;
    }

    @Override
    protected void initView() {
        app_title.setText("益农宝");

    }

    @Override
    protected void initData() {

    }

    @Override
    protected void onViewClick(View v) {

    }
}
