package com.zhizhong.farmer.module.tuiguangyuan.activity;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.github.baseclass.adapter.LoadMoreAdapter;
import com.github.baseclass.adapter.LoadMoreViewHolder;
import com.zhizhong.farmer.GetSign;
import com.zhizhong.farmer.R;
import com.zhizhong.farmer.base.BaseActivity;
import com.zhizhong.farmer.base.MySub;
import com.zhizhong.farmer.module.tuiguangyuan.network.ApiRequest;
import com.zhizhong.farmer.module.tuiguangyuan.network.response.TGYFarmerObj;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;

/**
 * Created by administartor on 2017/8/1.
 */

public class TGYMyFarmerActivity extends BaseActivity implements LoadMoreAdapter.OnLoadMoreListener{
    @BindView(R.id.rv_tgy_my_farmer)
    RecyclerView rv_tgy_my_farmer;


    LoadMoreAdapter adapter;
    @Override
    protected int getContentView() {
        setAppTitle("我的农户");
        return R.layout.act_tgy_my_farmer;
    }

    @Override
    protected void initView() {
        adapter=new LoadMoreAdapter<TGYFarmerObj>(mContext,R.layout.item_tgy_my_farmer,pageSize) {
            @Override
            public void bindData(LoadMoreViewHolder holder, int i, TGYFarmerObj bean) {
                holder.setText(R.id.tv_tgy_farmer_name,bean.getNick_name())
                        .setText(R.id.tv_tgy_farmer_date,bean.getAdd_time())
                        .setText(R.id.tv_tgy_farmer_phone,bean.getMobile()+"");
            }
        };
        adapter.setOnLoadMoreListener(this);
        rv_tgy_my_farmer.setLayoutManager(new LinearLayoutManager(mContext));
        rv_tgy_my_farmer.setAdapter(adapter);

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
//        map.put("pagesize",pageSize+"");
//        map.put("page",page+"");
        map.put("sign", GetSign.getSign(map));
        addSubscription(ApiRequest.getFarmerList(map).subscribe(new MySub<List<TGYFarmerObj>>(mContext,pcfl,pl_load) {
            @Override
            public void onMyNext(List<TGYFarmerObj> list) {
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
