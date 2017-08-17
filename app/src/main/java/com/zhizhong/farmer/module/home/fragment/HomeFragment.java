package com.zhizhong.farmer.module.home.fragment;

import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.zhizhong.farmer.Config;
import com.zhizhong.farmer.R;
import com.zhizhong.farmer.base.BaseFragment;
import com.zhizhong.farmer.module.my.activity.LoginActivity;
import com.zhizhong.farmer.module.my.activity.MyMessageActivity;
import com.zhizhong.farmer.module.tuiguangyuan.activity.TGYLoginActivity;
import com.zhizhong.farmer.module.tuiguangyuan.activity.TGYMyMessageActivity;

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

    @OnClick({R.id.iv_home_msg})
    protected void onViewClick(View v) {
        switch (v.getId()){
            case R.id.iv_home_msg:
                if(TextUtils.isEmpty(getUserId())){
                    if(getUserType()== Config.userType_farmer){
                        STActivity(LoginActivity.class);
                    }else if(getUserType()==Config.userType_tgy){
                        STActivity(TGYLoginActivity.class);
                    }
                    return;
                }
                if(getUserType()== Config.userType_farmer){
                    STActivity(MyMessageActivity.class);
                }else if(getUserType()==Config.userType_tgy){
                    STActivity(TGYMyMessageActivity.class);
                }
            break;
        }
    }
}
