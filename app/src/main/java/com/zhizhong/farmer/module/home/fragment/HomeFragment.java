package com.zhizhong.farmer.module.home.fragment;

import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.zhizhong.farmer.R;
import com.zhizhong.farmer.base.BaseFragment;
import com.zhizhong.farmer.module.home.activity.ZhiBaoZhongXinListActivity;
import com.zhizhong.farmer.module.my.activity.LoginActivity;
import com.zhizhong.farmer.module.my.activity.MyMessageActivity;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by administartor on 2017/8/4.
 */

public class HomeFragment extends BaseFragment {
    @BindView(R.id.iv_home_msg)
    ImageView iv_home_msg;
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

    @OnClick({R.id.iv_home_msg,R.id.tv_home_zhibao})
    protected void onViewClick(View v) {
        switch (v.getId()){
            case R.id.tv_home_zhibao:
                STActivity(ZhiBaoZhongXinListActivity.class);
                break;
            case R.id.iv_home_msg:
                if(TextUtils.isEmpty(getUserId())){
                    STActivity(LoginActivity.class);
                }else{
                    STActivity(MyMessageActivity.class);
                }
            break;
        }
    }
}
