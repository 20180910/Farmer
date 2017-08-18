package com.zhizhong.farmer.module.home.activity;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.github.baseclass.adapter.LoadMoreAdapter;
import com.github.baseclass.adapter.LoadMoreViewHolder;
import com.zhizhong.farmer.GetSign;
import com.zhizhong.farmer.R;
import com.zhizhong.farmer.base.BaseActivity;
import com.zhizhong.farmer.base.MySub;
import com.zhizhong.farmer.module.home.Constant;
import com.zhizhong.farmer.module.home.network.ApiRequest;
import com.zhizhong.farmer.module.home.network.response.ZhiBaoObj;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;

/**
 * Created by administartor on 2017/8/8.
 */

public class ZhiBaoZhongXinListActivity extends BaseActivity implements LoadMoreAdapter.OnLoadMoreListener{
    @BindView(R.id.rv_zhi_bao)
    RecyclerView rv_zhi_bao;

    LoadMoreAdapter adapter;

    @Override
    protected int getContentView() {
        setAppTitle("植保中心");
        return R.layout.act_zhi_bao_zhong_xin_list;
    }
    @Override
    protected void initView() {
        adapter=new LoadMoreAdapter<ZhiBaoObj>(mContext,R.layout.item_zhi_bao,pageSize) {
            @Override
            public void bindData(LoadMoreViewHolder holder, int i, ZhiBaoObj bean) {
                holder.setText(R.id.tv_zhibao_title,bean.getTitle())
                        .setText(R.id.tv_zhibao_time,bean.getAdd_time());
                ImageView imageView = holder.getImageView(R.id.iv_zhibao_img);
                Glide.with(mContext).load(bean.getImage_url()).error(R.color.c_press).into(imageView);
            }
        };
        adapter.setOnLoadMoreListener(this);
        adapter.setClickListener(new LoadMoreAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int i) {
                ZhiBaoObj obj = (ZhiBaoObj) adapter.getList().get(i);
                Intent intent=new Intent();
                intent.putExtra(Constant.IParam.zhiBaoId,obj.getId()+"");
                STActivity(intent,ZhiBaoZhongXinDetailActivity.class);
            }
        });

        rv_zhi_bao.setLayoutManager(new LinearLayoutManager(mContext));
        rv_zhi_bao.setAdapter(adapter);

        pcfl.setPtrHandler(new PtrDefaultHandler() {
            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                getData(1,false);
            }
        });

    }

    private void getData(int page, boolean isLoad) {
        Map<String,String> map=new HashMap<String,String>();
        map.put("page",page+"");
        map.put("pagesize",pageSize+"");
        map.put("sign", GetSign.getSign(map));
        addSubscription(ApiRequest.getZhiBaoList(map).subscribe(new MySub<List<ZhiBaoObj>>(mContext,pcfl,pl_load) {
            @Override
            public void onMyNext(List<ZhiBaoObj> list) {
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
    protected void initData() {
        showProgress();
        getData(1,false);
    }

    @Override
    protected void onViewClick(View v) {

    }

    @Override
    public void loadMore() {
        getData(pageNum,true);
    }
}
