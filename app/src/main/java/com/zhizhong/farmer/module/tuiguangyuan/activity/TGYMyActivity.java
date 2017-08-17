package com.zhizhong.farmer.module.tuiguangyuan.activity;

import android.content.Intent;
import android.view.View;
import android.widget.LinearLayout;

import com.github.androidtools.StatusBarUtils;
import com.zhizhong.farmer.Config;
import com.zhizhong.farmer.R;
import com.zhizhong.farmer.base.BaseActivity;
import com.zhizhong.farmer.module.home.activity.SelectUserActivity;

import butterknife.BindView;

/**
 * Created by administartor on 2017/8/17.
 */

public class TGYMyActivity extends BaseActivity {
    @BindView(R.id.status_bar)
    View status_bar;
    @Override
    protected int getContentView() {
        return R.layout.act_tgy_my;
    }

    @Override
    protected void initView() {
        int statusBarHeight = StatusBarUtils.getStatusBarHeight(this);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        layoutParams.height = statusBarHeight;
        status_bar.setLayoutParams(layoutParams);
        status_bar.setBackgroundColor(getResources().getColor(R.color.white));
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        if (intent != null && Config.exitAPP.equals(intent.getAction())) {
            STActivity(SelectUserActivity.class);
            finish();
        }
    }

    @Override
    protected void onViewClick(View v) {

    }
    private long mExitTime;

    @Override
    public void onBackPressed() {
        if ((System.currentTimeMillis() - mExitTime) > 1500) {
            showToastS("再按一次退出程序");
            mExitTime = System.currentTimeMillis();
        } else {
            super.onBackPressed();
        }
    }
}
