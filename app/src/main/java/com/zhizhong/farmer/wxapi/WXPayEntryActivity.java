package com.zhizhong.farmer.wxapi;


import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.tencent.mm.opensdk.constants.ConstantsAPI;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.zhizhong.farmer.Config;
import com.zhizhong.farmer.R;
import com.zhizhong.farmer.base.BaseActivity;
import com.zhizhong.farmer.module.home.activity.MainActivity;
import com.zhizhong.farmer.module.my.activity.MyOrderListActivity;

import butterknife.BindView;
import butterknife.OnClick;

public class WXPayEntryActivity extends BaseActivity implements IWXAPIEventHandler {

    @BindView(R.id.tv_pay_result)
    TextView tv_pay_result;
    @BindView(R.id.tv_pay_txt)
    TextView tv_pay_txt;

    private IWXAPI api;
    private Intent intent;
    private Intent intent1;

    private boolean isSuccess;

    @Override
    protected int getContentView() {
        setAppTitle("支付结果");
        return R.layout.act_pay_success;
    }

    @Override
    protected void initView() {
        api = WXAPIFactory.createWXAPI(this, Config.weixing_id);
        api.handleIntent(getIntent(), this);
    }

    @Override
    protected void initData() {

    }

    @OnClick({R.id.tv_pay_lookorder, R.id.tv_pay_back})
    protected void onViewClick(View view) {
        switch (view.getId()) {
            case R.id.tv_pay_lookorder:
                finish();
                break;
            case R.id.tv_pay_back:
                intent = new Intent(Config.backHome);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_SINGLE_TOP);
                STActivity(intent,MainActivity.class);
                break;
        }
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

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        api.handleIntent(intent, this);
    }

    @Override
    public void onReq(BaseReq req) {

    }

    @Override
    public void onResp(BaseResp resp) {
        Log.e("tag", "onPayFinish, errCode = " + resp.errCode + "---" + resp.getType());
        if (resp.getType() == ConstantsAPI.COMMAND_PAY_BY_WX) {
            Log.e("tag", "支付结果:" + resp.errCode);
            if (resp.errCode == 0) {// 支付成功
                isSuccess=true;
//                ToastUtils.showToast(this, "支付成功");
                tv_pay_result.setText("支付成功");
                tv_pay_txt.setText("您已成功支付，等待飞首接单");
//				startActivity(new Intent(this, PaySuccessActivity.class));
//                DialogUtil.showInfoDialog(this, "温馨提示", "支付成功").show();
            } else if (resp.errCode == -2) {
//                ToastUtils.showToast(this, "取消支付");
                tv_pay_result.setText("取消支付");
                tv_pay_txt.setText("您已取消支付");
//                ToastUtil.showShort(this,"取消支付");
//                DialogUtil.showInfoDialog(this, "温馨提示", "取消支付").show();
            } else if (resp.errCode == -1) {
//                isSuccess=true;
//                ToastUtils.showToast(this, "支付失败");
                tv_pay_result.setText("支付失败");
                tv_pay_txt.setText("支付失败，请稍后再试");
//                ToastUtil.showShort(this,"支付失败");
//                DialogUtil.showInfoDialog(this, "温馨提示", "支付失败").show();
            }
        }
//		finish();
    }


}


/*extends Activity implements IWXAPIEventHandler {

	private static final String TAG = "WXPayEntryActivity";

    private IWXAPI api;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_pay_success);
    	api = WXAPIFactory.createWXAPI(this, Config.weixing_id);
        api.handleIntent(getIntent(), this);
    }

	@Override
	protected void onNewIntent(Intent intent) {
		super.onNewIntent(intent);
		Log.d("onNewIntent===", "onNewIntent==== ");
		setIntent(intent);
        api.handleIntent(intent, this);
	}

	@Override
	public void onReq(BaseReq baseReq) {

		Log.d("onResp===", "baseReq, errCode = " + baseReq.getType());
	}

	@Override
	public void onResp(BaseResp baseResp) {
		Log.d("onResp===", "onPayFinish, errCode = " + baseResp.errCode);
		if (baseResp.getType() == ConstantsAPI.COMMAND_PAY_BY_WX) {
			Log.d("onResp22===", "onPayFinish, errCode = " + baseResp.errCode);
		}
	}
}*/