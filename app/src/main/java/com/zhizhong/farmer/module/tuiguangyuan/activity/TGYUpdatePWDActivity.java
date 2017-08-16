package com.zhizhong.farmer.module.tuiguangyuan.activity;

import android.text.TextUtils;
import android.view.View;

import com.github.customview.MyEditText;
import com.github.customview.MyTextView;
import com.zhizhong.farmer.GetSign;
import com.zhizhong.farmer.R;
import com.zhizhong.farmer.base.BaseActivity;
import com.zhizhong.farmer.base.BaseObj;
import com.zhizhong.farmer.base.MySub;
import com.zhizhong.farmer.module.tuiguangyuan.network.ApiRequest;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by administartor on 2017/8/1.
 */

public class TGYUpdatePWDActivity extends BaseActivity {

    @BindView(R.id.et_tgy_info_pwd)
    MyEditText et_tgy_info_pwd;
    @BindView(R.id.et_tgy_info_newpwd)
    MyEditText et_tgy_info_newpwd;
    @BindView(R.id.et_tgy_info_repwd)
    MyEditText et_tgy_info_repwd;
    @BindView(R.id.et_tgy_info_update)
    MyTextView et_tgy_info_update;

    @Override
    protected int getContentView() {
        setAppTitle("修改密码");
        return R.layout.act_tgy_update_pwd;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {

    }

    @OnClick({R.id.et_tgy_info_update})
    protected void onViewClick(View v) {
        switch (v.getId()){
            case R.id.et_tgy_info_update:
                String oldPwd=getSStr(et_tgy_info_pwd);
                String newPwd=getSStr(et_tgy_info_newpwd);
                String rePwd=getSStr(et_tgy_info_repwd);
                if(TextUtils.isEmpty(oldPwd)){
                    showMsg("原密码不能为空");
                    return;
                }else if(TextUtils.isEmpty(newPwd)){
                    showMsg("新密码不能为空");
                    return;
                }else if(!newPwd.equals(rePwd)){
                    showMsg("两次密码不一样");
                    return;
                }
                updatePwd(oldPwd,newPwd);
            break;
        }
    }

    private void updatePwd(String oldPwd, String newPwd) {
        showLoading();
        Map<String,String> map=new HashMap<String,String>();
        map.put("user_id",getUserId());
        map.put("oldPassword",oldPwd);
        map.put("newPassword",newPwd);
        map.put("sign", GetSign.getSign(map));
        addSubscription(ApiRequest.setNewPasswordTGY(map).subscribe(new MySub<BaseObj>(mContext) {
            @Override
            public void onMyNext(BaseObj obj) {
                showMsg(obj.getMsg());
                finish();
            }
        }));
    }




}
