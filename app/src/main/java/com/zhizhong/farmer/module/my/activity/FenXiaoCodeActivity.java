package com.zhizhong.farmer.module.my.activity;

import android.view.View;
import android.widget.TextView;

import com.github.customview.MyTextView;
import com.zhizhong.farmer.R;
import com.zhizhong.farmer.base.BaseActivity;
import com.zhizhong.farmer.base.MySub;
import com.zhizhong.farmer.module.my.network.ApiRequest;
import com.zhizhong.farmer.module.my.network.response.FenXiaoDetailObj;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by administartor on 2017/8/8.
 */

public class FenXiaoCodeActivity extends BaseActivity {


    @BindView(R.id.tv_fenxiao_detail_content)
    TextView tv_fenxiao_detail_content;
    @BindView(R.id.tv_fenxiao_detail_code)
    MyTextView tv_fenxiao_detail_code;

    @Override
    protected int getContentView() {
        setAppTitle("分销码");
        setAppRightImg(R.drawable.img42);
        return R.layout.act_fen_xiao_code;
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
        addSubscription(ApiRequest.getFenXiaoDetail(getUserId(),getSign()).subscribe(new MySub<FenXiaoDetailObj>(mContext,pl_load) {
            @Override
            public void onMyNext(FenXiaoDetailObj obj) {
                tv_fenxiao_detail_content.setText(obj.getContent());
                tv_fenxiao_detail_code.setText(obj.getDistribution_yard());
            }
        }));
    }

    @OnClick({R.id.app_right_iv})
    protected void onViewClick(View v) {
        switch (v.getId()) {
            case R.id.app_right_iv:

                break;
        }
    }
}
