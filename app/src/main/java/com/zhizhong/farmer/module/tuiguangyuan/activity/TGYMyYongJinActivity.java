package com.zhizhong.farmer.module.tuiguangyuan.activity;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.github.baseclass.adapter.LoadMoreAdapter;
import com.github.baseclass.adapter.LoadMoreViewHolder;
import com.github.customview.MyTextView;
import com.zhizhong.farmer.GetSign;
import com.zhizhong.farmer.R;
import com.zhizhong.farmer.base.BaseActivity;
import com.zhizhong.farmer.base.MySub;
import com.zhizhong.farmer.module.tuiguangyuan.network.ApiRequest;
import com.zhizhong.farmer.module.tuiguangyuan.network.response.TGYYongJinObj;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;

/**
 * Created by administartor on 2017/8/4.
 */

public class TGYMyYongJinActivity extends BaseActivity implements LoadMoreAdapter.OnLoadMoreListener {
    @BindView(R.id.rv_tgy_yongjin)
    RecyclerView rv_tgy_yongjin;

    LoadMoreAdapter adapter;
    @BindView(R.id.tg_tgy_yongjin_money)
    MyTextView tg_tgy_yongjin_money;
    @BindView(R.id.tg_tgy_yongjin_tx)
    MyTextView tg_tgy_yongjin_tx;

    @Override
    protected int getContentView() {
        setAppTitle("我的佣金");
        return R.layout.act_tgy_my_yong_jin;
    }

    @Override
    protected void initView() {
        adapter = new LoadMoreAdapter<TGYYongJinObj.CommissionDetailListBean>(mContext, R.layout.item_tgy_yong_jin_mingxi, pageSize) {
            @Override
            public void bindData(LoadMoreViewHolder holder, int i, TGYYongJinObj.CommissionDetailListBean bean) {
                holder.setText(R.id.tv_tgy_yongjin_remark,bean.getRemark())
                        .setText(R.id.tv_tgy_yongjin_money,bean.getValue());
            }
        };
        adapter.setOnLoadMoreListener(this);
        rv_tgy_yongjin.setLayoutManager(new LinearLayoutManager(mContext));
        rv_tgy_yongjin.setAdapter(adapter);

        pcfl.setPtrHandler(new PtrDefaultHandler() {
            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                getData(1, false);
            }
        });
    }

    @Override
    protected void initData() {
        showProgress();
        getData(1, false);
    }

    private void getData(int page, boolean isLoad) {
        Map<String, String> map = new HashMap<String, String>();
        map.put("user_id", getUserId());
        map.put("pagesize", pageSize + "");
        map.put("page", page + "");
        map.put("sign", GetSign.getSign(map));
        addSubscription(ApiRequest.getTGYYongJin(map).subscribe(new MySub<TGYYongJinObj>(mContext,pcfl,pl_load) {
            @Override
            public void onMyNext(TGYYongJinObj obj) {
                tg_tgy_yongjin_money.setText("¥"+obj.getCommission());
                if (isLoad) {
                    pageNum++;
                    adapter.addList(obj.getCommission_detail_list(), true);
                } else {
                    pageNum = 2;
                    adapter.setList(obj.getCommission_detail_list(), true);
                }
            }
        }));
    }

    @OnClick({R.id.tg_tgy_yongjin_tx})
    protected void onViewClick(View v) {
        switch (v.getId()){
            case R.id.tg_tgy_yongjin_tx:
                STActivityForResult(TGYTiXianActivity.class,1000);
            break;
        }
    }

    @Override
    public void loadMore() {
        getData(pageNum, true);
    }

    @OnClick(R.id.tg_tgy_yongjin_tx)
    public void onClick() {
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode==RESULT_OK){
            switch (requestCode){
                case 1000:
                    initData();
                    break;
            }
        }

    }
}
