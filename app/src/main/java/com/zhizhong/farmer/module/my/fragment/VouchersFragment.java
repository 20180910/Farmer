package com.zhizhong.farmer.module.my.fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.github.baseclass.adapter.LoadMoreAdapter;
import com.github.baseclass.rx.RxBus;
import com.zhizhong.farmer.GetSign;
import com.zhizhong.farmer.R;
import com.zhizhong.farmer.base.BaseFragment;
import com.zhizhong.farmer.base.MySub;
import com.zhizhong.farmer.module.my.adapter.VouchersAdapter;
import com.zhizhong.farmer.module.my.event.VoucherEvent;
import com.zhizhong.farmer.module.my.network.ApiRequest;
import com.zhizhong.farmer.module.my.network.response.VouchersObj;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;

/**
 * Created by administartor on 2017/8/8.
 */

public class VouchersFragment extends BaseFragment implements LoadMoreAdapter.OnLoadMoreListener{
    @BindView(R.id.rv_vouchers)
    RecyclerView rv_vouchers;

    VouchersAdapter adapter;
    private String vouchersType;
    private boolean selectVoucher;

    @Override
    protected int getContentView() {
        return R.layout.frag_vouchers;
    }
    @Override
    protected void initView() {
        vouchersType=getArguments().getString("type");
        adapter=new VouchersAdapter(mContext, R.layout.item_vouchers,pageSize);
        adapter.setOnLoadMoreListener(this);
        adapter.setType(vouchersType);
        adapter.setClickListener(new LoadMoreAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int i) {
                if(selectVoucher){
                    VouchersObj vouchersObj = adapter.getList().get(i);
                    RxBus.getInstance().post(new VoucherEvent(vouchersObj));
                }
            }
        });
        rv_vouchers.setLayoutManager(new LinearLayoutManager(mContext));
        rv_vouchers.setAdapter(adapter);
        pcfl.setPtrHandler(new PtrDefaultHandler() {
            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                getData(1,false);
            }
        });
    }

    public static VouchersFragment newInstance(String type) {
        Bundle args = new Bundle();
        args.putString("type", type);
        VouchersFragment fragment = new VouchersFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected void initData() {
        showProgress();
        getData(1,false);
    }
    private void getData(int page, boolean isLoad) {
        Map<String,String> map=new HashMap<String,String>();
        map.put("user_id",getUserId());
        map.put("type",getArguments().getString("type"));
        map.put("page",page+"");
        map.put("pagesize",pageSize+"");
        map.put("sign", GetSign.getSign(map));
        addSubscription(ApiRequest.getVouchersList(map).subscribe(new MySub<List<VouchersObj>>(mContext,pcfl,pl_load) {
            @Override
            public void onMyNext(List<VouchersObj> list) {
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

    public void setSelectVoucher(boolean selectVoucher) {
        this.selectVoucher=selectVoucher;
    }
}
