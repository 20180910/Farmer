package com.zhizhong.farmer.module.my.activity;

import android.view.View;

import com.zhizhong.farmer.R;
import com.zhizhong.farmer.base.BaseActivity;

/**
 * Created by administartor on 2017/8/1.
 */

public class LoginActivity extends BaseActivity {

    @Override
    protected int getContentView() {
        setAppTitle("登录");
        return R.layout.act_login;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {

    }

    protected void onViewClick(View v) {
        switch (v.getId()){
            case 1:

            break;
        }
    }

    @Override
    public void again() {

    }
}
