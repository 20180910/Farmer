package com.zhizhong.farmer.module.tuiguangyuan.activity;

import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.github.customview.MyEditText;
import com.github.customview.MyTextView;
import com.github.rxjava.rxbus.RxUtils;
import com.zhizhong.farmer.Config;
import com.zhizhong.farmer.GetSign;
import com.zhizhong.farmer.R;
import com.zhizhong.farmer.base.BaseActivity;
import com.zhizhong.farmer.base.BaseObj;
import com.zhizhong.farmer.base.MySub;
import com.zhizhong.farmer.module.tuiguangyuan.network.ApiRequest;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.OnClick;
import rx.Observable;
import rx.Observer;
import rx.Subscription;

/**
 * Created by administartor on 2017/8/1.
 */

public class TGYForgetPWDActivity extends BaseActivity {

    @BindView(R.id.et_tgy_forget_phone)
    MyEditText et_tgy_forget_phone;
    @BindView(R.id.et_tgy_forget_code)
    EditText et_tgy_forget_code;
    @BindView(R.id.tv_tgy_forget_getcode)
    TextView tv_tgy_forget_getcode;
    @BindView(R.id.et_tgy_forget_newpwd)
    MyEditText et_tgy_forget_newpwd;
    @BindView(R.id.tv_tgy_forget_commit)
    MyTextView tv_tgy_forget_commit;


    private String smsCode;
    @Override
    protected int getContentView() {
        setAppTitle("忘记密码");
        return R.layout.act_tgy_forget_pwd;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {

    }



    @OnClick({R.id.tv_tgy_forget_getcode, R.id.tv_tgy_forget_commit})
    protected void onViewClick(View view) {
        String phone=getSStr(et_tgy_forget_phone);
        switch (view.getId()) {
            case R.id.tv_tgy_forget_getcode:
                if(TextUtils.isEmpty(phone)){
                    showMsg("手机号不能为空");
                    return;
                }else if(!GetSign.isMobile(phone)){
                    showMsg("手机号不正确");
                    return;
                }
                getMsgCode();
                break;
            case R.id.tv_tgy_forget_commit:
                String code=getSStr(et_tgy_forget_code);
                if(TextUtils.isEmpty(phone)){
                    showMsg("手机号不能为空");
                    return;
                }else if(!GetSign.isMobile(phone )){
                    showMsg("手机号不正确");
                    return;
                }else if(TextUtils.isEmpty(smsCode)||TextUtils.isEmpty(code)||!code.equals(smsCode)){
                    showMsg("请输入正确验证码");
                    return;
                }else if(TextUtils.isEmpty(getSStr(et_tgy_forget_newpwd))){
                    showMsg("密码不能为空");
                    return;
                }
                resetPwd(phone,getSStr(et_tgy_forget_newpwd));
                break;
        }
    }

    private void resetPwd(String phone, String pwd) {
        showLoading();
        Map<String,String> map=new HashMap<String,String>();
        map.put("username",phone);
        map.put("user_type", Config.userType_tgy+"");
        map.put("newPassword",pwd);
        map.put("sign",GetSign.getSign(map));
        addSubscription(ApiRequest.setForgetPasswordTGY(map).subscribe(new MySub<BaseObj>(mContext) {
            @Override
            public void onMyNext(BaseObj obj) {
                showMsg(obj.getMsg());
                finish();
            }
        }));

    }

    private void getMsgCode() {
        showLoading();
        Map<String, String> map = new HashMap<String, String>();
        map.put("mobile",getSStr(et_tgy_forget_phone));
        map.put("rnd",getRnd());
        String sign = GetSign.getSign(map);
        map.put("sign", sign);
        showLoading();
        addSubscription(ApiRequest.getMsgCode(map).subscribe(new MySub<BaseObj>(mContext) {
            @Override
            public void onMyNext(BaseObj item) {
                smsCode = item.getSMSCode();
                countDown();
            }
        }));
    }
    private void countDown() {
        tv_tgy_forget_getcode.setEnabled(false);
        final long count = 30;
        Subscription subscribe = Observable.interval(1, TimeUnit.SECONDS)
                .take(31)//计时次数
                .map(integer -> count - integer)
                .compose(RxUtils.appSchedulers())
                .subscribe(new Observer<Long>() {
                    @Override
                    public void onCompleted() {
                        tv_tgy_forget_getcode.setEnabled(true);
                        tv_tgy_forget_getcode.setText("获取验证码");
                    }

                    @Override
                    public void onNext(Long aLong) {
                        tv_tgy_forget_getcode.setText("剩下" + aLong + "s");
                    }

                    @Override
                    public void onError(Throwable e) {
                    }
                });
        addSubscription(subscribe);
    }
}
