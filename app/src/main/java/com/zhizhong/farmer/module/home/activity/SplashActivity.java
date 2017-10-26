package com.zhizhong.farmer.module.home.activity;

import android.view.View;
import android.widget.TextView;

import com.zhizhong.farmer.R;
import com.zhizhong.farmer.base.BaseActivity;

import butterknife.BindView;

/**
 * Created by Administrator on 2017/10/26.
 */

public class SplashActivity extends BaseActivity {
    @BindView(R.id.tv_splash)
    TextView tv_splash;
    @Override
    protected int getContentView() {
        return R.layout.act_splash;
    }

    @Override
    protected void initView() {
        tv_splash.postDelayed(new Runnable() {
            @Override
            public void run() {
                STActivity(SelectUserActivity.class);
                finish();
            }
        },1000);
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void onViewClick(View v) {

    }
}
