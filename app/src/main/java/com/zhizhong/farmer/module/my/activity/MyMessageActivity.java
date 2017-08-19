package com.zhizhong.farmer.module.my.activity;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.github.baseclass.adapter.LoadMoreAdapter;
import com.github.baseclass.adapter.LoadMoreViewHolder;
import com.zhizhong.farmer.GetSign;
import com.zhizhong.farmer.R;
import com.zhizhong.farmer.base.BaseActivity;
import com.zhizhong.farmer.base.MySub;
import com.zhizhong.farmer.module.my.network.ApiRequest;
import com.zhizhong.farmer.module.tuiguangyuan.Constant;
import com.zhizhong.farmer.module.tuiguangyuan.network.response.MessageObj;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;

/**
 * Created by administartor on 2017/8/4.
 */

public class MyMessageActivity extends BaseActivity implements LoadMoreAdapter.OnLoadMoreListener{
    @BindView(R.id.rv_my_message)
    RecyclerView rv_my_message;

    LoadMoreAdapter adapter;
    @Override
    public void again() {

    }

    @Override
    protected int getContentView() {
        setAppTitle("我的消息");
        return R.layout.act_my_message;
    }

    @Override
    protected void initView() {
        adapter=new LoadMoreAdapter<MessageObj>(mContext,R.layout.item_tgy_my_msg,pageSize) {
            @Override
            public void bindData(LoadMoreViewHolder holder, int i, MessageObj bean) {
                holder.setText(R.id.tv_tgy_msg_title,bean.getTitle())
                        .setText(R.id.tv_tgy_msg_content,bean.getZhaiyao())
                        .setText(R.id.tv_tgy_msg_date,bean.getAdd_time());
            }
        };
        adapter.setOnLoadMoreListener(this);
        adapter.setClickListener(new LoadMoreAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int i) {
                MessageObj obj = (MessageObj) adapter.getList().get(i);
                Intent intent=new Intent();
                intent.putExtra(Constant.IParam.msgId,obj.getId()+"");
                STActivity(intent,MyMessageDetailActivity.class);
            }
        });

        rv_my_message.setLayoutManager(new LinearLayoutManager(mContext));
        rv_my_message.setAdapter(adapter);
        pcfl.setPtrHandler(new PtrDefaultHandler() {
            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                getData(1,false);
            }
        });
    }

    @Override
    protected void initData() {
        showProgress();
        getData(1,false);
    }
    private void getData(int page, boolean isLoad) {
        Map<String,String> map=new HashMap<String,String>();
        map.put("user_id",getUserId());
        map.put("page",page+"");
        map.put("pagesize",pageSize+"");
        map.put("sign", GetSign.getSign(map));
        addSubscription(ApiRequest.getMsgList(map).subscribe(new MySub<List<MessageObj>>(mContext,pcfl,pl_load) {
            @Override
            public void onMyNext(List<MessageObj> list) {
                if(isLoad){
                    pageNum++;
                    adapter.addList(list,true);
                }else{
                    pageNum=2;
                    adapter.setList(list,true);
                }
            }
        }));
    }

    @Override
    protected void onViewClick(View v) {

    }

    @Override
    public void loadMore() {
        getData(pageNum,true);
    }
}
