package com.zhizhong.farmer.module.zixun.activity;

import android.view.View;
import android.widget.TextView;

import com.zhizhong.farmer.R;
import com.zhizhong.farmer.base.BaseActivity;
import com.zhizhong.farmer.base.MySub;
import com.zhizhong.farmer.module.zixun.network.ApiRequest;
import com.zhizhong.farmer.module.zixun.network.response.ZiXunObj;

import butterknife.BindView;

/**
 * Created by administartor on 2017/8/1.
 */

public class ZiXunDetailActivity extends BaseActivity {

    @BindView(R.id.tv_zixun_detail_title)
    TextView tv_zixun_detail_title;
    @BindView(R.id.tv_zixun_detail_time)
    TextView tv_zixun_detail_time;
    @BindView(R.id.tv_zixun_detail_content)
    TextView tv_zixun_detail_content;

    private String id;

    @Override
    protected int getContentView() {
        setAppTitle("正文");
        return R.layout.act_zi_xun_detail;
    }


    @Override
    protected void initView() {
        id = getIntent().getStringExtra("id");
    }

    @Override
    protected void initData() {
        showProgress();
        getData();
    }

    private void getData() {
        addSubscription(ApiRequest.getZiXunDetail(id, getSign("information_id", id)).subscribe(new MySub<ZiXunObj>(mContext,pl_load) {
            @Override
            public void onMyNext(ZiXunObj obj) {
                tv_zixun_detail_title.setText(obj.getTitle());
                tv_zixun_detail_time.setText(obj.getAdd_time());
                tv_zixun_detail_content.setText(obj.getContent());
            }
        }));
    }

    @Override
    protected void onViewClick(View v) {

    }


}
