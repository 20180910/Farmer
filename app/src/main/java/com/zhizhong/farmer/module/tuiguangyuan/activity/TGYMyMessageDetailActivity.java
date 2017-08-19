package com.zhizhong.farmer.module.tuiguangyuan.activity;

import android.view.View;
import android.widget.TextView;

import com.zhizhong.farmer.R;
import com.zhizhong.farmer.base.BaseActivity;
import com.zhizhong.farmer.base.MySub;
import com.zhizhong.farmer.module.tuiguangyuan.Constant;
import com.zhizhong.farmer.module.tuiguangyuan.network.ApiRequest;
import com.zhizhong.farmer.module.tuiguangyuan.network.response.MessageDetailObj;

import butterknife.BindView;

/**
 * Created by administartor on 2017/8/4.
 */

public class TGYMyMessageDetailActivity extends BaseActivity {


    @BindView(R.id.tv_tgy_msgdetail_title)
    TextView tv_tgy_msgdetail_title;
    @BindView(R.id.tv_tgy_msgdetail_content)
    TextView tv_tgy_msgdetail_content;
    private String msgId;

    @Override
    protected int getContentView() {
        setAppTitle("我的消息");
        return R.layout.act_tgy_my_message_detail;
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
        addSubscription(ApiRequest.getTGYMsgDetail(msgId,getSign("news_id",msgId)).subscribe(new MySub<MessageDetailObj>(mContext) {
            @Override
            public void onMyNext(MessageDetailObj obj) {
                tv_tgy_msgdetail_title.setText(obj.getTitle());
                tv_tgy_msgdetail_content.setText(obj.getContent());
            }
        }));
    }

    @Override
    protected void onViewClick(View v) {

    }
}
