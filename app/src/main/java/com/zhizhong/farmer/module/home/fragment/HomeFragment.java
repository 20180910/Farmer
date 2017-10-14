package com.zhizhong.farmer.module.home.fragment;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.github.baseclass.adapter.LoadMoreAdapter;
import com.github.baseclass.adapter.LoadMoreViewHolder;
import com.github.baseclass.rx.RxBus;
import com.github.customview.MyImageView;
import com.youth.banner.Banner;
import com.zhizhong.farmer.R;
import com.zhizhong.farmer.base.BaseFragment;
import com.zhizhong.farmer.base.MySub;
import com.zhizhong.farmer.module.home.Constant;
import com.zhizhong.farmer.module.home.activity.CityActivity;
import com.zhizhong.farmer.module.home.activity.ZhiBaoZhongXinDetailActivity;
import com.zhizhong.farmer.module.home.activity.ZhiBaoZhongXinListActivity;
import com.zhizhong.farmer.module.home.event.XiaDanEvent;
import com.zhizhong.farmer.module.home.event.ZiXunEvent;
import com.zhizhong.farmer.module.home.network.ApiRequest;
import com.zhizhong.farmer.module.home.network.response.HomeDataObj;
import com.zhizhong.farmer.module.home.network.response.HomeImgObj;
import com.zhizhong.farmer.module.my.activity.LoginActivity;
import com.zhizhong.farmer.module.my.activity.MyMessageActivity;
import com.zhizhong.farmer.module.my.activity.MyOrderListActivity;
import com.zhizhong.farmer.module.zixun.activity.ZiXunDetailActivity;
import com.zhizhong.farmer.tools.GlideLoader;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

import static android.app.Activity.RESULT_OK;

/**
 * Created by administartor on 2017/8/4.
 */

public class HomeFragment extends BaseFragment {
    @BindView(R.id.iv_home_msg)
    ImageView iv_home_msg;
    @BindView(R.id.app_title)
    TextView app_title;
    @BindView(R.id.rv_home_zixun)
    RecyclerView rv_home_zixun;
    @BindView(R.id.rv_home_zhibao)
    RecyclerView rv_home_zhibao;


    @BindView(R.id.bn_home)
    Banner bn_home;

    private List<String> bannerList;

    @BindView(R.id.iv_home_zixun)
    MyImageView iv_home_zixun;
    @BindView(R.id.iv_home_xiadan)
    MyImageView iv_home_xiadan;
    @BindView(R.id.iv_home_zhibao)
    MyImageView iv_home_zhibao;
    @BindView(R.id.iv_home_order)
    MyImageView iv_home_order;
    @BindView(R.id.iv_home_bigimg)
    ImageView iv_home_bigimg;
    @BindView(R.id.tv_home_city)
    TextView tv_home_city;

    LoadMoreAdapter ziXunAdapter, zhiBaoAdapter;
    @Override
    protected int getContentView() {
        return R.layout.frag_home;
    }

    @Override
    protected void initView() {
        app_title.setText("飞农宝");
        ziXunAdapter = new LoadMoreAdapter<HomeDataObj.InformationListBean>(mContext, R.layout.item_zi_xun, 0) {
            @Override
            public void bindData(LoadMoreViewHolder holder, int i, HomeDataObj.InformationListBean bean) {
                holder.setText(R.id.tv_home_zixun_title, bean.getTitle())
                        .setText(R.id.tv_home_zixun_time, bean.getAdd_time())
                        .setText(R.id.tv_home_zixun_num, bean.getPage_view() + "");
                ImageView imageView = holder.getImageView(R.id.iv_home_zixun_img);
                Glide.with(mContext).load(bean.getImage_url()).error(R.color.c_press).into(imageView);
            }
        };
        ziXunAdapter.setClickListener(new LoadMoreAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int i) {
                HomeDataObj.InformationListBean obj = (HomeDataObj.InformationListBean) ziXunAdapter.getList().get(i);
                Intent intent=new Intent();
                intent.putExtra(Constant.IParam.id,obj.getId()+"");
                STActivity(intent,ZiXunDetailActivity.class);
            }
        });

        zhiBaoAdapter = new LoadMoreAdapter<HomeDataObj.EppocenterListBean>(mContext, R.layout.item_zhi_bao, 0) {
            @Override
            public void bindData(LoadMoreViewHolder holder, int i, HomeDataObj.EppocenterListBean bean) {
                ImageView imageView = holder.getImageView(R.id.iv_zhibao_img);
                Glide.with(mContext).load(bean.getImage_url()).error(R.color.c_press).into(imageView);

                holder.setText(R.id.tv_zhibao_title, bean.getTitle())
                        .setText(R.id.tv_zhibao_time, bean.getAdd_time());
            }
        };
        zhiBaoAdapter.setClickListener(new LoadMoreAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int i) {
                HomeDataObj.EppocenterListBean obj = (HomeDataObj.EppocenterListBean) zhiBaoAdapter.getList().get(i);
                Intent intent=new Intent();
                intent.putExtra(Constant.IParam.zhiBaoId,obj.getId()+"");
                STActivity(intent,ZhiBaoZhongXinDetailActivity.class);
            }
        });


        rv_home_zixun.setNestedScrollingEnabled(false);
        rv_home_zixun.setLayoutManager(new LinearLayoutManager(mContext));
        rv_home_zixun.setAdapter(ziXunAdapter);

        rv_home_zhibao.setNestedScrollingEnabled(false);
        rv_home_zhibao.setLayoutManager(new LinearLayoutManager(mContext));
        rv_home_zhibao.setAdapter(zhiBaoAdapter);
    }

    @Override
    protected void initData() {
        showProgress();
        getHomeImg();
        getHomeData();
    }

    private void getHomeImg() {
        String rnd = getRnd();
        addSubscription(ApiRequest.getHomeImg(rnd, getSign("rnd", rnd)).subscribe(new MySub<HomeImgObj>(mContext) {
            @Override
            public void onMyNext(HomeImgObj obj) {
                Glide.with(mContext).load(obj.getImage()).error(R.color.c_press).into(iv_home_bigimg);
                List<HomeImgObj.TypeListBean> type_list = obj.getType_list();
                if(notEmpty(type_list)){
                    Glide.with(mContext).load(type_list.get(0).getImg_url()).error(R.color.c_press).into(iv_home_zixun);
                    Glide.with(mContext).load(type_list.get(1).getImg_url()).error(R.color.c_press).into(iv_home_xiadan);
                    Glide.with(mContext).load(type_list.get(2).getImg_url()).error(R.color.c_press).into(iv_home_zhibao);
                    Glide.with(mContext).load(type_list.get(3).getImg_url()).error(R.color.c_press).into(iv_home_order);
                }
                if(notEmpty(obj.getRoasting_list())){
                    bannerList = new ArrayList<String>();
                    for (int i = 0; i < obj.getRoasting_list().size(); i++) {
                        bannerList.add(obj.getRoasting_list().get(i).getImg_url());
                    }
                    bn_home.setImages(bannerList);
                    bn_home.setImageLoader(new GlideLoader());
                    bn_home.start();
                }
            }
        }));
    }

    private void getHomeData() {
        String rnd = getRnd();
        addSubscription(ApiRequest.getHomeData(rnd, getSign("rnd", rnd)).subscribe(new MySub<HomeDataObj>(mContext, pl_load) {
            @Override
            public void onMyNext(HomeDataObj obj) {
                ziXunAdapter.setList(obj.getInformation_list(), true);
                zhiBaoAdapter.setList(obj.getEppocenter_list(), true);
            }
        }));
    }

    @OnClick({R.id.iv_home_msg, R.id.ll_home_zhibao,R.id.ll_home_zixun, R.id.ll_home_xiadan,R.id.ll_home_order,R.id.tv_home_city})
    protected void onViewClick(View v) {
        switch (v.getId()) {
            case R.id.ll_home_order:
                STActivity(MyOrderListActivity.class);
                break;
            case R.id.ll_home_zhibao:
                STActivity(ZhiBaoZhongXinListActivity.class);
                break;
            case R.id.ll_home_zixun:
                RxBus.getInstance().post(new ZiXunEvent());
                break;
            case R.id.ll_home_xiadan:
                RxBus.getInstance().post(new XiaDanEvent());
                break;
            case R.id.iv_home_msg:
                if (TextUtils.isEmpty(getUserId())) {
                    STActivity(LoginActivity.class);
                } else {
                    STActivity(MyMessageActivity.class);
                }
                break;
            case R.id.tv_home_city:
                STActivityForResult(CityActivity.class,1000);
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode==RESULT_OK){
            switch (requestCode){
                case 1000:
                    String city = data.getStringExtra(Constant.IParam.city);
                    tv_home_city.setText(city);
                    break;
            }
        }

    }

    @Override
    public void onStop() {
        super.onStop();
        if (bn_home != null&&bannerList!=null) {
            bn_home.stopAutoPlay();
        }
    }
    @Override
    public void onResume() {
        super.onResume();
        if (bn_home != null&&bannerList!=null) {
            bn_home.startAutoPlay();
        }
    }
}
