package com.zhizhong.farmer.module.my.activity;

import android.view.View;
import android.widget.TextView;

import com.zhizhong.farmer.R;
import com.zhizhong.farmer.base.BaseActivity;
import com.zhizhong.farmer.base.MySub;
import com.zhizhong.farmer.module.tuiguangyuan.activity.TGYMyYongJinActivity;
import com.zhizhong.farmer.module.my.network.ApiRequest;
import com.zhizhong.farmer.module.my.network.response.FenXiaoObj;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by administartor on 2017/8/8.
 */

public class MyFenXiaoActivity extends BaseActivity {


    @BindView(R.id.tv_fenxiao_code)
    TextView tv_fenxiao_code;
    @BindView(R.id.tv_fenxiao_yongjin)
    TextView tv_fenxiao_yongjin;
    @BindView(R.id.tv_fenxiao_xiaji)
    TextView tv_fenxiao_xiaji;
    @BindView(R.id.tv_fenxiao_vouchers)
    TextView tv_fenxiao_vouchers;

    @Override
    protected int getContentView() {
        setAppTitle("我的分销");
        return R.layout.act_my_fen_xiao;
    }

    @Override
    protected void initView() {
    }
    @Override
    protected void initData() {
        showProgress();
        getData();
    }

    private void getData() {
        addSubscription(ApiRequest.getFenXiao(getUserId(),getSign()).subscribe(new MySub<FenXiaoObj>(mContext,pl_load) {
            @Override
            public void onMyNext(FenXiaoObj obj) {
                tv_fenxiao_code.setText(obj.getDistribution_yard());
                tv_fenxiao_yongjin.setText("¥"+obj.getCommission().replace("￥",""));
                tv_fenxiao_xiaji.setText(obj.getLower_level());
                tv_fenxiao_vouchers.setText(obj.getVoucher());
            }
        }));
    }
    @OnClick({R.id.ll_fenxiao_code, R.id.ll_fenxiao_xiaji, R.id.ll_fenxiao_yongjin, R.id.ll_fenxiao_vouchers})
    protected void onViewClick(View view) {
        switch (view.getId()) {
            case R.id.ll_fenxiao_code:
                STActivity(FenXiaoCodeActivity.class);
                break;
            case R.id.ll_fenxiao_xiaji:
                STActivity(MyXiaJiActivity.class);
                break;
            case R.id.ll_fenxiao_yongjin:
                STActivity(TGYMyYongJinActivity.class);
                break;
            case R.id.ll_fenxiao_vouchers:
                STActivity(MyVouchersActivity.class);
                break;
        }
    }
}
