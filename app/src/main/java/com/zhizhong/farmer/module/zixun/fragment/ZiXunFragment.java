package com.zhizhong.farmer.module.zixun.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.github.baseclass.adapter.LoadMoreAdapter;
import com.github.baseclass.adapter.LoadMoreViewHolder;
import com.youth.banner.Banner;
import com.zhizhong.farmer.GetSign;
import com.zhizhong.farmer.R;
import com.zhizhong.farmer.base.BaseFragment;
import com.zhizhong.farmer.base.MySub;
import com.zhizhong.farmer.module.zixun.activity.ZiXunDetailActivity;
import com.zhizhong.farmer.module.zixun.network.ApiRequest;
import com.zhizhong.farmer.module.zixun.network.response.ZiXunImgObj;
import com.zhizhong.farmer.module.zixun.network.response.ZiXunObj;
import com.zhizhong.farmer.tools.GlideLoader;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;

/**
 * Created by administartor on 2017/8/1.
 */

public class ZiXunFragment extends BaseFragment implements LoadMoreAdapter.OnLoadMoreListener{
    @BindView(R.id.rg_zixun)
    RadioGroup rg_zixun;
    @BindView(R.id.app_title)
    TextView app_title;
    @BindView(R.id.rv_zi_xun)
    RecyclerView rv_zi_xun;

    LoadMoreAdapter ziXunAdapter;

    private String type="1";

    @BindView(R.id.bn_zixun)
    Banner bn_zixun;

    private List<String> bannerList;
    @Override
    protected int getContentView() {
        return R.layout.frag_zi_xun;
    }

    public static ZiXunFragment newInstance() {
        Bundle args = new Bundle();

        ZiXunFragment fragment = new ZiXunFragment();
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    protected void initView() {
        app_title.setText("资讯");
        ziXunAdapter=new LoadMoreAdapter<ZiXunObj>(mContext,R.layout.item_zi_xun,pageSize){
            public void bindData(LoadMoreViewHolder holder, int i, ZiXunObj bean) {
                holder.setText(R.id.tv_home_zixun_title, bean.getTitle())
                        .setText(R.id.tv_home_zixun_time, bean.getAdd_time())
                        .setText(R.id.tv_home_zixun_num, bean.getPage_view() + "");
                ImageView imageView = holder.getImageView(R.id.iv_home_zixun_img);
                Glide.with(mContext).load(bean.getImage_url()).error(R.color.c_press).into(imageView);
            }
        };
        ziXunAdapter.setOnLoadMoreListener(this);
        ziXunAdapter.setClickListener(new LoadMoreAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int i) {
                ZiXunObj item = (ZiXunObj) ziXunAdapter.getList().get(i);
                Intent intent=new Intent();
                intent.putExtra("id",item.getId()+"");
                STActivity(intent,ZiXunDetailActivity.class);
            }
        });

        rv_zi_xun.setLayoutManager(new LinearLayoutManager(mContext));
        rv_zi_xun.setAdapter(ziXunAdapter);
        pcfl.setPtrHandler(new PtrDefaultHandler() {
            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                getData(1,false);
            }
        });


        rg_zixun.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, @IdRes int checkView) {
                switch (checkView){
                    case R.id.rb_zixun_nongyao:
                        type="1";
                        showLoading();
                        getData(1,false);
                    break;
                    case R.id.rb_zixun_zhongzi:
                        type="2";
                        showLoading();
                        getData(1,false);
                    break;
                    case R.id.rb_zixun_huafei:
                        type="3";
                        showLoading();
                        getData(1,false);
                    break;
                    case R.id.rb_zixun_jixie:
                        type="4";
                        showLoading();
                        getData(1,false);
                    break;
                }
            }
        });
    }

    @Override
    protected void initData() {
        showProgress();
        getImg();
        getData(1,false);
    }

    private void getImg() {
        String rnd = getRnd();
        addSubscription(ApiRequest.getZiXunImg(rnd,getSign("rnd",rnd)).subscribe(new MySub<List<ZiXunImgObj>>(mContext) {
            @Override
            public void onMyNext(List<ZiXunImgObj> list) {
                if(notEmpty(list)){
                    bannerList = new ArrayList<String>();
                    for (int i = 0; i < list.size(); i++) {
                        bannerList.add(list.get(i).getImg_url());
                    }
                    bn_zixun.setImages(bannerList);
                    bn_zixun.setImageLoader(new GlideLoader());
                    bn_zixun.start();
                }
            }
        }));
    }

    private void getData(int page, boolean isLoad) {
        Map<String,String> map=new HashMap<String,String>();
        map.put("type",type);
        map.put("page",page+"");
        map.put("pagesize",pageSize+"");
        map.put("sign", GetSign.getSign(map));
        addSubscription(ApiRequest.getZiXunList(map).subscribe(new MySub<List<ZiXunObj>>(mContext,pcfl,pl_load) {
            @Override
            public void onMyNext(List<ZiXunObj> list) {
                if(isLoad){
                    pageNum++;
                    ziXunAdapter.addList(list,true);
                }else{
                    pageNum=2;
                    ziXunAdapter.setList(list,true);
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
    @Override
    public void onStop() {
        super.onStop();
        if (bn_zixun != null&&bannerList!=null) {
            bn_zixun.stopAutoPlay();
        }
    }
    @Override
    public void onResume() {
        super.onResume();
        if (bn_zixun != null&&bannerList!=null) {
            bn_zixun.startAutoPlay();
        }
    }
}
