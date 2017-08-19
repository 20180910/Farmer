package com.zhizhong.farmer.module.my.activity;

import android.text.TextUtils;
import android.view.View;

import com.github.customview.MyEditText;
import com.github.customview.MyTextView;
import com.zhizhong.farmer.GetSign;
import com.zhizhong.farmer.R;
import com.zhizhong.farmer.base.BaseActivity;
import com.zhizhong.farmer.base.BaseObj;
import com.zhizhong.farmer.base.MySub;
import com.zhizhong.farmer.module.my.Constant;
import com.zhizhong.farmer.module.my.network.ApiRequest;
import com.zhizhong.farmer.module.my.network.response.MyFarmerObj;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by administartor on 2017/8/5.
 */

public class AddFarmerActivity extends BaseActivity {
    @BindView(R.id.et_add_farmer_name)
    MyEditText et_add_farmer_name;
    @BindView(R.id.et_add_farmer_phone)
    MyEditText et_add_farmer_phone;
    @BindView(R.id.et_add_farmer_juzhu)
    MyEditText et_add_farmer_juzhu;
    @BindView(R.id.et_add_farmer_nongtian)
    MyEditText et_add_farmer_nongtian;
    @BindView(R.id.et_add_farmer_zw)
    MyEditText et_add_farmer_zw;
    @BindView(R.id.et_add_farmer_ms)
    MyEditText et_add_farmer_ms;
    @BindView(R.id.tv_add_farmer_commit)
    MyTextView tv_add_farmer_commit;
    private String id;
    private String type;


    @Override
    protected int getContentView() {
        setAppTitle("添加农户");
        return R.layout.act_add_farmer;
    }

    @Override
    protected void initView() {
        type = getIntent().getStringExtra("type");
        if(Constant.IParam.editFarmer.equals(type)){
            id = getIntent().getStringExtra("id");
            showProgress();
            getData();
        }
    }

    private void getData() {
        addSubscription(ApiRequest.getMyFarmer(id,getSign("mf_id",id)).subscribe(new MySub<MyFarmerObj>(mContext) {
            @Override
            public void onMyNext(MyFarmerObj obj) {
                et_add_farmer_name.setText(obj.getFarmers_name());
                et_add_farmer_phone.setText(obj.getPhone_number());
                et_add_farmer_juzhu.setText(obj.getAddresss());
                et_add_farmer_nongtian.setText(obj.getFarmland_addresss());
                et_add_farmer_zw.setText(obj.getCrops());
                et_add_farmer_ms.setText(obj.getArea());
            }
        }));
    }

    @Override
    protected void initData() {

    }

    @OnClick({R.id.tv_add_farmer_commit})
    protected void onViewClick(View v) {
        switch (v.getId()){
            case R.id.tv_add_farmer_commit:
                String name=getSStr(et_add_farmer_name);
                String phone=getSStr(et_add_farmer_phone);
                String juzhu=getSStr(et_add_farmer_juzhu);
                String nongtian=getSStr(et_add_farmer_nongtian);
                String zw=getSStr(et_add_farmer_zw);
                String ms=getSStr(et_add_farmer_ms);
                if(TextUtils.isEmpty(name)){
                    showMsg("农户姓名不能为空");
                    return;
                }else if(TextUtils.isEmpty(phone)){
                    showMsg("手机号不能为空");
                    return;
                }else if(TextUtils.isEmpty(juzhu)){
                    showMsg("居住地址不能为空");
                    return;
                }else if(TextUtils.isEmpty(nongtian )){
                    showMsg("农田地址不能为空");
                    return;
                }else if(TextUtils.isEmpty(zw )){
                    showMsg("作物不能为空");
                    return;
                }else if(TextUtils.isEmpty( ms)){
                    showMsg("亩数不能为空");
                    return;
                }
                if(Constant.IParam.editFarmer.equals(type)){
                    editFarmer(name,phone,juzhu,nongtian,zw,ms);
                }else{
                    addFarmer(name,phone,juzhu,nongtian,zw,ms);
                }
            break;
        }
    }

    private void editFarmer(String name, String phone, String juzhu, String nongtian, String zw, String ms) {
        showLoading();
        Map<String,String> map=new HashMap<String,String>();
        map.put("mf_id",id);
        map.put("farmers_name",name);
        map.put("phone_number",phone);
        map.put("crops",zw);
        map.put("area",ms);
        map.put("address",juzhu);
        map.put("farmland_addresss",nongtian);
        map.put("sign", GetSign.getSign(map));
        addSubscription(ApiRequest.updateMyFarmer(map).subscribe(new MySub<BaseObj>(mContext) {
            @Override
            public void onMyNext(BaseObj obj) {
                showMsg(obj.getMsg());
                setResult(RESULT_OK);
                finish();
            }
        }));
    }

    private void addFarmer(String name, String phone, String juzhu, String nongtian, String zw, String ms) {
        showLoading();
        Map<String,String> map=new HashMap<String,String>();
        map.put("user_id",getUserId());
        map.put("farmers_name",name);
        map.put("phone_number",phone);
        map.put("crops",zw);
        map.put("area",ms);
        map.put("address",juzhu);
        map.put("farmland_addresss",nongtian);
        map.put("sign", GetSign.getSign(map));
        addSubscription(ApiRequest.addMyFarmer(map).subscribe(new MySub<BaseObj>(mContext) {
            @Override
            public void onMyNext(BaseObj obj) {
                showMsg(obj.getMsg());
                setResult(RESULT_OK);
                finish();
            }
        }));
    }
}
