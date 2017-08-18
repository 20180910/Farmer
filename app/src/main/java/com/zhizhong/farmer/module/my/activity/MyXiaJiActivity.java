package com.zhizhong.farmer.module.my.activity;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.github.baseclass.adapter.LoadMoreAdapter;
import com.github.baseclass.adapter.LoadMoreViewHolder;
import com.zhizhong.farmer.R;
import com.zhizhong.farmer.base.BaseActivity;
import com.zhizhong.farmer.base.MySub;
import com.zhizhong.farmer.module.my.network.ApiRequest;
import com.zhizhong.farmer.module.my.network.response.XiaJiObj;

import java.util.List;

import butterknife.BindView;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;

/**
 * Created by administartor on 2017/8/8.
 */

public class MyXiaJiActivity extends BaseActivity implements LoadMoreAdapter.OnLoadMoreListener{
    @BindView(R.id.rv_my_xia_ji)
    RecyclerView rv_my_xia_ji;

    LoadMoreAdapter adapter;

    @Override
    protected int getContentView() {
        setAppTitle("我的下级");
        return R.layout.act_my_xia_ji;
    }

    @Override
    protected void initView() {
        adapter=new LoadMoreAdapter<XiaJiObj>(mContext,R.layout.item_my_xia_ji,pageSize) {
            @Override
            public void bindData(LoadMoreViewHolder holder, int i, XiaJiObj bean) {
                holder.setText(R.id.tv_xiaji_name,bean.getNick_name())
                        .setText(R.id.tv_xiaji_phone,bean.getMobile());
            }
        };
        adapter.setOnLoadMoreListener(this);
        rv_my_xia_ji.setLayoutManager(new LinearLayoutManager(mContext));
        rv_my_xia_ji.setAdapter(adapter);
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
        addSubscription(ApiRequest.xiaJi(getUserId(),getSign()).subscribe(new MySub<List<XiaJiObj>>(mContext,pcfl,pl_load) {
            @Override
            public void onMyNext(List<XiaJiObj> list) {
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
