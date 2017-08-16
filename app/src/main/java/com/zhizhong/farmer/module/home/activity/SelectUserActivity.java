package com.zhizhong.farmer.module.home.activity;

import android.os.Bundle;
import android.view.View;

import com.github.androidtools.SPUtils;
import com.github.customview.MyTextView;
import com.zhizhong.farmer.Config;
import com.zhizhong.farmer.R;
import com.zhizhong.farmer.base.BaseActivity;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by administartor on 2017/8/16.
 */

public class SelectUserActivity extends BaseActivity {
    @BindView(R.id.tv_select_user_farmer)
    MyTextView tvSelectUserFarmer;
    @BindView(R.id.tv_select_user_tgy)
    MyTextView tvSelectUserTgy;

    String action;
    @Override
    protected int getContentView() {
        hiddenBackIcon();
        return R.layout.act_select_user;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        action=getIntent().getAction();
        //第一次进来，或者退出登录
        if (Config.IParam.selectUser.equals(action)||SPUtils.getPrefBoolean(this,Config.isFirstIntoApp,true)){

        }else{
            int userType = SPUtils.getPrefInt(this, Config.userType,-1);
            if(userType!=-1){
                STActivity(MainActivity.class);
                finish();
            }
        }
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {

    }

    @OnClick({R.id.tv_select_user_farmer, R.id.tv_select_user_tgy})
    protected void onViewClick(View view) {
        switch (view.getId()) {
            case R.id.tv_select_user_farmer:
                SPUtils.setPrefBoolean(mContext, Config.isFirstIntoApp,false);

                SPUtils.setPrefInt(mContext, Config.userType,Config.userType_farmer);
                STActivity(MainActivity.class);
                finish();
                break;
            case R.id.tv_select_user_tgy:
                SPUtils.setPrefBoolean(mContext, Config.isFirstIntoApp,false);

                SPUtils.setPrefInt(mContext, Config.userType,Config.userType_tgy);
                STActivity(MainActivity.class);
                finish();
                break;
        }
    }
}
