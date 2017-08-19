package com.zhizhong.farmer.module.my.activity;

import android.view.View;
import android.widget.TextView;

import com.zhizhong.farmer.R;
import com.zhizhong.farmer.base.BaseActivity;
import com.zhizhong.farmer.base.MySub;
import com.zhizhong.farmer.module.my.Constant;
import com.zhizhong.farmer.module.my.network.ApiRequest;
import com.zhizhong.farmer.module.tuiguangyuan.network.response.MessageDetailObj;

import butterknife.BindView;

/**
 * Created by administartor on 2017/8/4.
 */

public class MyMessageDetailActivity extends BaseActivity {
    @BindView(R.id.tv_msgdetail_title)
    TextView tv_msgdetail_title;
    @BindView(R.id.tv_msgdetail_content)
    TextView tv_msgdetail_content;
    private String msgId;
    @Override
    public void again() {

    }

    @Override
    protected int getContentView() {
        setAppTitle("我的消息");
        return R.layout.act_my_message_detail;
    }

    @Override
    protected void initView() {
        msgId = getIntent().getStringExtra(Constant.IParam.msgId);
    }

    @Override
    protected void initData() {
        showProgress();
        getData();
    }
    private void getData() {
        addSubscription(ApiRequest.getMsgDetail(msgId,getSign("news_id",msgId)).subscribe(new MySub<MessageDetailObj>(mContext) {
            @Override
            public void onMyNext(MessageDetailObj obj) {
                tv_msgdetail_title.setText(obj.getTitle());
                tv_msgdetail_content.setText(obj.getContent());
            }
        }));
    }
    @Override
    protected void onViewClick(View v) {

    }
}
