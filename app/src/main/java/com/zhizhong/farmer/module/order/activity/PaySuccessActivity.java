package com.zhizhong.farmer.module.order.activity;

import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import com.zhizhong.farmer.Config;
import com.zhizhong.farmer.R;
import com.zhizhong.farmer.base.BaseActivity;
import com.zhizhong.farmer.module.home.activity.MainActivity;
import com.zhizhong.farmer.module.my.activity.MyOrderListActivity;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by administartor on 2017/8/7.
 */

public class PaySuccessActivity extends BaseActivity {
    @BindView(R.id.tv_pay_result)
    TextView tv_pay_result;
    @BindView(R.id.tv_pay_txt)
    TextView tv_pay_txt;

    private boolean isSuccess;
    private Intent intent;
    @Override
    public void again() {

    }

    @Override
    protected int getContentView() {
        setAppTitle("支付结果");
        /*setAppTitleColor(getResources().getColor(R.color.white));
        setTitleBackgroud(getResources().getColor(R.color.blue));
        setBackIcon(R.drawable.img23);
        hiddenBottomLine(true);*/
        return R.layout.act_pay_success;
    }

    @Override
    protected void initView() {
        Intent intent=getIntent();
        if(intent.getBooleanExtra(Config.IParam.alipay,false)){
            isSuccess=true;
            tv_pay_result.setText("支付成功");
            tv_pay_txt.setText("您已成功支付，等待飞首接单");
        }else{
            isSuccess=false;
            tv_pay_result.setText("支付失败");
            tv_pay_txt.setText("支付失败，请稍后再试");
        }
    }

    @Override
    protected void initData() {

    }
    @Override
    public void finish() {
        if(isSuccess){
            intent = new Intent();
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            STActivity(intent,MyOrderListActivity.class);
        }
        super.finish();
    }
    @OnClick({R.id.tv_pay_lookorder, R.id.tv_pay_back})
    protected void onViewClick(View view) {
        switch (view.getId()) {
            case R.id.tv_pay_lookorder:
                intent = new Intent();
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                STActivity(intent,MyOrderListActivity.class);
                break;
            case R.id.tv_pay_back:
                intent = new Intent(Config.backHome);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_SINGLE_TOP);
                STActivity(intent,MainActivity.class);
                break;
        }
    }

}
