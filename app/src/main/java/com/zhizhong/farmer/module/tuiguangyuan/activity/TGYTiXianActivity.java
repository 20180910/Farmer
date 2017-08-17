package com.zhizhong.farmer.module.tuiguangyuan.activity;

import android.content.Intent;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.zhizhong.farmer.GetSign;
import com.zhizhong.farmer.R;
import com.zhizhong.farmer.base.BaseActivity;
import com.zhizhong.farmer.base.BaseObj;
import com.zhizhong.farmer.base.MySub;
import com.zhizhong.farmer.module.account.Constant;
import com.zhizhong.farmer.module.account.activity.AccountListActivity;
import com.zhizhong.farmer.module.account.network.ApiRequest;
import com.zhizhong.farmer.module.account.network.response.AccountObj;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by administartor on 2017/8/4.
 */

public class TGYTiXianActivity extends BaseActivity {
    @BindView(R.id.ll_tgy_tixian)
    LinearLayout ll_tgy_tixian;
    @BindView(R.id.tv_tgy_tx_accout)
    TextView tv_tgy_tx_accout;
    @BindView(R.id.tv_tgy_tx_commit)
    TextView tv_tgy_tx_commit;
    @BindView(R.id.iv_tgy_tx_bank)
    ImageView iv_tgy_tx_bank;
    @BindView(R.id.et_tgy_tx_money)
    EditText et_tgy_tx_money;
    @BindView(R.id.et_tgy_tx_account_money)
    TextView et_tgy_tx_account_money;

    private String accountId;

    @Override
    protected int getContentView() {
        setAppTitle("提现");
        return R.layout.act_tgy_ti_xian;
    }

    @Override
    protected void initView() {
        et_tgy_tx_money.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                int selectionStart = et_tgy_tx_money.getSelectionStart();
                int selectionEnd = et_tgy_tx_money.getSelectionEnd();
                if (!isOnlyPointNumber(et_tgy_tx_money.getText().toString())){
                    s.delete(selectionStart - 1, selectionEnd);
                }
            }
        });
    }

    @Override
    protected void initData() {
        showLoading();
        getAllMoney();
        getDefaultAccount();
    }

    @OnClick({R.id.ll_tgy_tixian,R.id.tv_tgy_tx_commit})
    protected void onViewClick(View v) {
        switch (v.getId()){
            case R.id.ll_tgy_tixian:
                STActivityForResult(AccountListActivity.class,100);
                break;
            case R.id.tv_tgy_tx_commit:
                String money = getSStr(et_tgy_tx_money);
                if(TextUtils.isEmpty(getSStr(tv_tgy_tx_accout))){
                    showMsg("请选择到账账户");
                    return;
                }else if(TextUtils.isEmpty(money)){
                    showMsg("请输入金额");
                    return;
                }else if(money.length()==1&&money.indexOf(".")==0){
                    showMsg("请输入金额");
                    return;
                }
                if(money.indexOf(".")==0){
                    money="0"+money;
                }
                if(money.indexOf(".")==money.length()-1){
                    money=money.replace(".","");
                }
                double resultMoney = Double.parseDouble(money);
                if(resultMoney<=0){
                    showMsg("请输入金额");
                    return;
                }
                TiXian(resultMoney);
                break;
        }
    }
    private void getAllMoney() {
        addSubscription(ApiRequest.getAccountMoney(getUserId(),getSign()).subscribe(new MySub<BaseObj>(mContext,true) {
            @Override
            public void onMyNext(BaseObj obj) {
                et_tgy_tx_account_money.setText("¥"+obj.getAccount_balance());
                et_tgy_tx_money.setHint("当前最多提现¥"+obj.getAccount_balance());
            }
        }));
    }
    private void getDefaultAccount() {
        addSubscription(ApiRequest.getAccountDefault(getUserId(),getSign()).subscribe(new MySub<List<AccountObj>>(mContext) {
            @Override
            public void onMyNext(List<AccountObj> list) {
                AccountObj account = list.get(0);
                accountId=account.getId()+"";
                tv_tgy_tx_accout.setText(account.getBank_name());
                Glide.with(mContext).load(account.getBank_image()).error(R.color.c_press).into(iv_tgy_tx_bank);
            }
        }));
    }
    private void TiXian(double money) {
        showLoading();
        Map<String,String> map=new HashMap<String,String>();
        map.put("user_id",getUserId());
        map.put("account_id",accountId);
        map.put("amount",money+"");
        map.put("sign", GetSign.getSign(map));
        addSubscription(ApiRequest.tiXian(map).subscribe(new MySub<BaseObj>(mContext) {
            @Override
            public void onMyNext(BaseObj obj) {
                showMsg(obj.getMsg());
                setResult(RESULT_OK);
                finish();
            }
        }));
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode==RESULT_OK){
            switch (requestCode){
                case 100:
                    AccountObj account = (AccountObj) data.getSerializableExtra(Constant.IParam.account);
                    accountId=account.getId()+"";
                    tv_tgy_tx_accout.setText(account.getBank_card());
                    Glide.with(mContext).load(account.getBank_image()).error(R.color.c_press).into(iv_tgy_tx_bank);
                    break;
            }
        }else if(resultCode==Constant.RCode.deleteDefaultAccount){
            switch (requestCode){
                case 100:
                    tv_tgy_tx_accout.setText(null);
                    iv_tgy_tx_bank.setImageDrawable(null);
                    initData();
                    break;
            }
        }
    }
    public boolean isOnlyPointNumber(String number) {//保留两位小数正则
        if(number.indexOf(".")==-1){
            return true;
        }else if(number.indexOf(".")==0){
            number="0"+number;
        }
        Pattern pattern = Pattern.compile("^\\d+\\.?\\d{0,2}$");
        Matcher matcher = pattern.matcher(number);
        return matcher.matches();
    }
}
