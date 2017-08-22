package com.zhizhong.farmer.module.my.fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.github.baseclass.adapter.LoadMoreAdapter;
import com.zhizhong.farmer.GetSign;
import com.zhizhong.farmer.R;
import com.zhizhong.farmer.base.BaseFragment;
import com.zhizhong.farmer.base.MySub;
import com.zhizhong.farmer.module.my.Constant;
import com.zhizhong.farmer.module.my.adapter.AllOrderAdapter;
import com.zhizhong.farmer.module.my.network.ApiRequest;
import com.zhizhong.farmer.module.my.network.response.OrderObj;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;

/**
 * Created by administartor on 2017/8/2.
 */

public class AllOrderFragment extends BaseFragment implements LoadMoreAdapter.OnLoadMoreListener{
    @BindView(R.id.rv_all_order)
    RecyclerView rv_all_order;

    AllOrderAdapter allOrderAdapter;
    private String type="0";

    @Override
    protected int getContentView() {
        return R.layout.frag_all_order;
    }

    public static AllOrderFragment newInstance(int type) {
        Bundle args = new Bundle();
        args.putString(Constant.type,type+"");
        AllOrderFragment fragment = new AllOrderFragment();
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    protected void initView() {
        allOrderAdapter=new AllOrderAdapter(mContext,R.layout.item_order_list,pageSize);
//        allOrderAdapter.setType(Integer.parseInt(getArguments().getString(Constant.type,"0")));
        allOrderAdapter.setOnLoadMoreListener(this);

        rv_all_order.setLayoutManager(new LinearLayoutManager(mContext));
        rv_all_order.setAdapter(allOrderAdapter);

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
        map.put("type",getArguments().getString(Constant.type,"0"));
        map.put("page",page+"");
        map.put("pagesize",pageSize+"");
        map.put("sign", GetSign.getSign(map));

        addSubscription(ApiRequest.getOrderList(map).subscribe(new MySub<List<OrderObj>>(mContext,pcfl,pl_load) {
            @Override
            public void onMyNext(List<OrderObj> list) {
                if(isLoad){
                    pageNum++;
                    allOrderAdapter.addList(list,true);
                }else{
                    pageNum=2;
                    allOrderAdapter.setList(list,true);
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
