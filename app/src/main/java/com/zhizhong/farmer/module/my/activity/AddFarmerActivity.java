package com.zhizhong.farmer.module.my.activity;

import android.support.design.widget.BottomSheetDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.github.androidtools.inter.MyOnClickListener;
import com.github.baseclass.adapter.LoadMoreAdapter;
import com.github.baseclass.adapter.LoadMoreViewHolder;
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
import com.zhizhong.farmer.module.my.network.response.ZuoWuObj;

import java.util.HashMap;
import java.util.List;
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
    @BindView(R.id.tv_add_farmer_zw)
    TextView tv_add_farmer_zw;
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
                tv_add_farmer_zw.setText(obj.getCrops());
                et_add_farmer_ms.setText(obj.getArea());
            }
        }));
    }

    @Override
    protected void initData() {

    }

    @OnClick({R.id.tv_add_farmer_commit,R.id.tv_add_farmer_zw})
    protected void onViewClick(View v) {
        switch (v.getId()){
            case R.id.tv_add_farmer_commit:
                String name=getSStr(et_add_farmer_name);
                String phone=getSStr(et_add_farmer_phone);
                String juzhu=getSStr(et_add_farmer_juzhu);
                String nongtian=getSStr(et_add_farmer_nongtian);
                String zw=getSStr(tv_add_farmer_zw);
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
            case R.id.tv_add_farmer_zw:
                getZuoWu();
                break;
        }
    }

    private void getZuoWu() {
        showLoading();
        String rnd=getRnd();
        addSubscription(ApiRequest.getZuoWuList(rnd,getSign("rnd",rnd)).subscribe(new MySub<List<ZuoWuObj>>(mContext) {
            @Override
            public void onMyNext(List<ZuoWuObj> list) {
                showZuoWu(list);
            }
        }));
    }
    private LoadMoreAdapter adapter;
    private void showZuoWu(List<ZuoWuObj> list) {
        BottomSheetDialog zuoWuDialog = new BottomSheetDialog(mContext);
        adapter=new LoadMoreAdapter<ZuoWuObj>(mContext, R.layout.item_chonghai,0) {
            @Override
            public void bindData(LoadMoreViewHolder holder, int i, final ZuoWuObj bean) {
                TextView tv_chonghai_name = holder.getTextView(R.id.tv_chonghai_name);
                tv_chonghai_name.setText(bean.getCrop_name());
                tv_chonghai_name.setOnClickListener(new MyOnClickListener() {
                    @Override
                    protected void onNoDoubleClick(View view) {
                        zuoWuDialog.dismiss();
                        tv_add_farmer_zw.setText(bean.getCrop_name());
                    }
                });
            }
        };
        adapter.setList(list);
        View zuoWuView = LayoutInflater.from(mContext).inflate(R.layout.popu_chonghai, null);
        RecyclerView rv_zuowu = zuoWuView.findViewById(R.id.rv_chonghai);
        rv_zuowu.setLayoutManager(new LinearLayoutManager(mContext));
        rv_zuowu.setAdapter(adapter);
        TextView tv_zuowu_cancle = zuoWuView.findViewById(R.id.tv_chonghai_cancle);
        tv_zuowu_cancle.setOnClickListener(new MyOnClickListener() {
            @Override
            protected void onNoDoubleClick(View view) {
                zuoWuDialog.dismiss();
            }
        });
        zuoWuDialog.setContentView(zuoWuView);
        zuoWuDialog.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        zuoWuDialog.show();
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
