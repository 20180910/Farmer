package com.zhizhong.farmer.module.my.activity;

import android.view.View;

import com.zhizhong.farmer.R;
import com.zhizhong.farmer.base.BaseActivity;

import butterknife.OnClick;

/**
 * Created by administartor on 2017/8/8.
 */

public class FenXiaoCodeActivity extends BaseActivity {
    @Override
    public void again() {

    }

    @Override
    protected int getContentView() {
        setAppTitle("分销码");
        setAppRightImg(R.drawable.img42);
        return R.layout.act_fen_xiao_code;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {

    }

    @OnClick({R.id.app_right_iv})
    protected void onViewClick(View v) {
        switch (v.getId()){
            case R.id.app_right_iv:

            break;
        }
    }
}
