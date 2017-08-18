package com.zhizhong.farmer.module.home.activity;

import android.view.View;
import android.widget.TextView;

import com.zhizhong.farmer.R;
import com.zhizhong.farmer.base.BaseActivity;
import com.zhizhong.farmer.base.MySub;
import com.zhizhong.farmer.module.home.Constant;
import com.zhizhong.farmer.module.home.network.ApiRequest;
import com.zhizhong.farmer.module.home.network.response.ZhiBaoObj;

import butterknife.BindView;

/**
 * Created by administartor on 2017/8/8.
 */

public class ZhiBaoZhongXinDetailActivity extends BaseActivity {

    @BindView(R.id.tv_zhibao_detail_title)
    TextView tv_zhibao_detail_title;
    @BindView(R.id.tv_zhibao_detail_time)
    TextView tv_zhibao_detail_time;
    @BindView(R.id.tv_zhibao_detail_content)
    TextView tv_zhibao_detail_content;
    private String zhiBaoId;

    @Override
    protected int getContentView() {
        setAppTitle("正文");
        return R.layout.act_zhi_bao_zhong_xin_detail;
    }

    @Override
    protected void initView() {
        zhiBaoId = getIntent().getStringExtra(Constant.IParam.zhiBaoId);
    }

    @Override
    protected void initData() {
        showProgress();
        getData();
    }

    private void getData() {
        addSubscription(ApiRequest.getZhiBaoDetail(zhiBaoId,getSign("eppo_id",zhiBaoId)).subscribe(new MySub<ZhiBaoObj>(mContext,pl_load) {
            @Override
            public void onMyNext(ZhiBaoObj obj) {
                tv_zhibao_detail_title.setText(obj.getTitle());
                tv_zhibao_detail_content.setText(obj.getContent());
                tv_zhibao_detail_time.setText(obj.getAdd_time());
            }
        }));
    }

    @Override
    protected void onViewClick(View v) {

    }
}
