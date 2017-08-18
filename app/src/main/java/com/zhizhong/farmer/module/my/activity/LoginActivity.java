package com.zhizhong.farmer.module.my.activity;

import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.github.androidtools.SPUtils;
import com.github.customview.MyEditText;
import com.github.customview.MyTextView;
import com.zhizhong.farmer.Config;
import com.zhizhong.farmer.GetSign;
import com.zhizhong.farmer.R;
import com.zhizhong.farmer.base.BaseActivity;
import com.zhizhong.farmer.base.MySub;
import com.zhizhong.farmer.module.my.Constant;
import com.zhizhong.farmer.module.my.network.ApiRequest;
import com.zhizhong.farmer.module.my.network.response.LoginObj;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by administartor on 2017/8/1.
 */

public class LoginActivity extends BaseActivity {

    @BindView(R.id.et_login_phone)
    MyEditText et_login_phone;
    @BindView(R.id.et_login_pwd)
    MyEditText et_login_pwd;
    @BindView(R.id.tv_my_login)
    MyTextView tv_my_login;
    @BindView(R.id.tv_go_register)
    TextView tv_go_register;
    @BindView(R.id.tv_login_forget)
    TextView tv_login_forget;
    
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

    @OnClick({R.id.tv_my_login,R.id.tv_go_register,R.id.tv_login_forget})
    protected void onViewClick(View view) {
        switch (view.getId()) {
            case R.id.tv_login_forget:
                STActivity(ForgetPWDActivity.class);
                break;
            case R.id.tv_my_login:
                String phone=getSStr(et_login_phone);
                String pwd=getSStr(et_login_pwd);
                if(TextUtils.isEmpty(phone)){
                    showMsg("手机号不能为空");
                    return;
                }else if(TextUtils.isEmpty(pwd)){
                    showMsg("密码不能为空");
                    return;
                }
                login(phone,pwd);
                break;
            case R.id.tv_go_register:
                STActivityForResult(RegisterActivity.class,1000);
                break;
        }
    }private void login(String phone, String pwd) {
        showLoading();
        SPUtils.setPrefString(mContext, Config.pwd,pwd);
        Map<String,String> map=new HashMap<String,String>();
        map.put("username",phone);
        map.put("password",pwd);
        map.put("user_type", Config.userType_farmer+"");
        map.put("RegistrationID","11");
        map.put("sign", GetSign.getSign(map));
        addSubscription(ApiRequest.login(map).subscribe(new MySub<LoginObj>(mContext) {
            @Override
            public void onMyNext(LoginObj obj) {
                SPUtils.setPrefInt(mContext, Config.userType,Config.userType_farmer);

                SPUtils.setPrefString(mContext,Config.user_id,obj.getUser_id()+"");
                SPUtils.setPrefString(mContext,Config.avatar,obj.getAvatar());
                SPUtils.setPrefString(mContext,Config.sex,obj.getSex());
                SPUtils.setPrefString(mContext,Config.birthday,obj.getBirthday());
                SPUtils.setPrefString(mContext,Config.distribution_yard,obj.getDistribution_yard());
                SPUtils.setPrefString(mContext,Config.user_name,obj.getUser_name());
                SPUtils.setPrefString(mContext,Config.nick_name,obj.getNick_name());
                SPUtils.setPrefString(mContext,Config.address,obj.getAddress());
                SPUtils.setPrefString(mContext,Config.mobile,obj.getMobile());
                SPUtils.setPrefInt(mContext,Config.level,obj.getUser_level());
                SPUtils.setPrefInt(mContext,Config.authentication,obj.getIs_authentication());

                LocalBroadcastManager.getInstance(mContext).sendBroadcast(new Intent(Config.Bro.operation));
//                STActivity(MainActivity.class);
                finish();
            }
        }));
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode==RESULT_OK){
            switch (requestCode){
                case 1000:
                    et_login_phone.setText(data.getStringExtra(Constant.IParam.phone));
                    et_login_pwd.setText(null);
                    et_login_pwd.findFocus();
                    break;
            }
        }
    }
 
}
