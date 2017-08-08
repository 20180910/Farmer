package com.zhizhong.farmer.module.order.activity;

import android.view.View;

import com.zhizhong.farmer.R;
import com.zhizhong.farmer.base.BaseActivity;

/**
 * Created by administartor on 2017/8/7.
 */

public class PaySuccessActivity extends BaseActivity {

    @Override
    public void again() {

    }

    @Override
    protected int getContentView() {
        setAppTitle("支付成功");
        setAppTitleColor(getResources().getColor(R.color.white));
        setTitleBackgroud(getResources().getColor(R.color.blue));
        setBackIcon(R.drawable.img23);
        hiddenBottomLine(true);
        return R.layout.act_pay_success;
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
