package com.zhizhong.farmer.module.order.fragment;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.zhizhong.farmer.R;
import com.zhizhong.farmer.base.BaseFragment;
import com.zhizhong.farmer.base.MySub;
import com.zhizhong.farmer.module.order.network.ApiRequest;
import com.zhizhong.farmer.module.order.network.response.OrderDefaultDataObj;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by administartor on 2017/8/1.
 */

public class XiaDingDanFragment extends BaseFragment {
    @BindView(R.id.app_title)
    TextView app_title;
    @BindView(R.id.tv_xia_order_name)
    TextView tv_xia_order_name;
    @BindView(R.id.tv_xia_order_phone)
    TextView tv_xia_order_phone;
    @Override
    protected int getContentView() {
        return R.layout.frag_xia_ding_dan;
    }

    public static XiaDingDanFragment newInstance() {


        Bundle args = new Bundle();

        XiaDingDanFragment fragment = new XiaDingDanFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected void initView() {
        app_title.setText("下订单");


    }

    @Override
    protected void initData() {
        showProgress();
        getData();
    }

    private void getData() {
        addSubscription(ApiRequest.getOrderDefaultData(getUserId(),getSign()).subscribe(new MySub<OrderDefaultDataObj>(mContext) {
            @Override
            public void onMyNext(OrderDefaultDataObj obj) {
                tv_xia_order_name.setText(obj.getFarmer_name());
                tv_xia_order_phone.setText(obj.getMobile());
            }
        }));
    }

    @OnClick({R.id.tv_xdd_commit})
    protected void onViewClick(View v) {
        switch (v.getId()){
            case R.id.tv_xdd_commit:

            break;
        }
    }




}
