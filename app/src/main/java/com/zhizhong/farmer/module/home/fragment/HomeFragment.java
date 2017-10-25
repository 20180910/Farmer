package com.zhizhong.farmer.module.home.fragment;

import android.content.Intent;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.model.LatLng;
import com.bumptech.glide.Glide;
import com.github.androidtools.SPUtils;
import com.github.baseclass.adapter.LoadMoreAdapter;
import com.github.baseclass.adapter.LoadMoreViewHolder;
import com.github.baseclass.rx.RxBus;
import com.github.customview.MyImageView;
import com.youth.banner.Banner;
import com.zhizhong.farmer.Config;
import com.zhizhong.farmer.GetSign;
import com.zhizhong.farmer.R;
import com.zhizhong.farmer.base.BaseFragment;
import com.zhizhong.farmer.base.MySub;
import com.zhizhong.farmer.module.home.Constant;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;

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
    @BindView(R.id.nsv_home)
    NestedScrollView nsv_home;


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
    public int scrollY;
    private boolean isFirstLoc=true;
    private String city="";
    private String area="";

    @Override
    protected int getContentView() {
        return R.layout.frag_home;
    }

    @Override
    protected void initView() {
        baiDuMap();
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


        pcfl.setPtrHandler(new PtrHandler() {
            @Override
            public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
                if(scrollY<=0){
                    return true;
                }else{
                    return false;
                }
            }
            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                getHomeImg();
                getHomeData();
            }
        });
        nsv_home.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                HomeFragment.this.scrollY = scrollY;
            }
        });
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
        Map<String,String> map=new HashMap<String,String>();
        map.put("city", city==null?"":city);
        map.put("area", area==null?"":area);
        map.put("sign", GetSign.getSign(map));
        addSubscription(ApiRequest.getHomeData(map).subscribe(new MySub<HomeDataObj>(mContext, pl_load,pcfl) {
            @Override
            public void onMyNext(HomeDataObj obj) {
                ziXunAdapter.setList(obj.getInformation_list(),true);
                zhiBaoAdapter.setList(obj.getEppocenter_list(),true);
            }
        }));
    }

    @OnClick({R.id.iv_home_msg, R.id.ll_home_zhibao,R.id.ll_home_zixun, R.id.ll_home_xiadan,R.id.ll_home_order,R.id.tv_home_city})
    protected void onViewClick(View v) {
        switch (v.getId()) {
            case R.id.ll_home_order:
                if (TextUtils.isEmpty(getUserId())) {
                    STActivity(LoginActivity.class);
                } else {
                    STActivity(MyOrderListActivity.class);
                }
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
//                STActivityForResult(CityActivity.class,1000);
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

    public MyLocationListenner myListener = new MyLocationListenner();

    private void baiDuMap() {
        // 定位初始化
        LocationClient mLocClient = new LocationClient(mContext);
        mLocClient.registerLocationListener(myListener);
        LocationClientOption option = new LocationClientOption();
        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);
        //可选，默认高精度，设置定位模式，高精度，低功耗，仅设备
        option.setCoorType("bd09ll");
        //可选，默认gcj02，设置返回的定位结果坐标系
        int span=1000;
        option.setScanSpan(span);
        //可选，默认0，即仅定位一次，设置发起定位请求的间隔需要大于等于1000ms才是有效的
        option.setIsNeedAddress(true);
        //可选，设置是否需要地址信息，默认不需要
        option.setOpenGps(true);
        //可选，默认false,设置是否使用gps
        option.setLocationNotify(true);
        //可选，默认false，设置是否当GPS有效时按照1S/1次频率输出GPS结果
        option.setIsNeedLocationDescribe(true);
        //可选，默认false，设置是否需要位置语义化结果，可以在BDLocation.getLocationDescribe里得到，结果类似于“在北京天安门附近”
        option.setIsNeedLocationPoiList(true);
        //可选，默认false，设置是否需要POI结果，可以在BDLocation.getPoiList里得到
        option.setIgnoreKillProcess(false);
        //可选，默认true，定位SDK内部是一个SERVICE，并放到了独立进程，设置是否在stop的时候杀死这个进程，默认不杀死
        option.setEnableSimulateGps(false);
        //可选，默认false，设置是否需要过滤GPS仿真结果，默认需要
        mLocClient.setLocOption(option);
        mLocClient.start();
    }
    public class MyLocationListenner extends BDAbstractLocationListener {

        @Override
        public void onReceiveLocation(BDLocation location) {
            // map view 销毁后不在处理新接收的位置
            if (location == null  )
                return;

//            mBaiduMap.setMapStatus(MapStatusUpdateFactory.newMapStatus(new MapStatus.Builder().zoom(15).build()));
            if (isFirstLoc) {
                /*MyLocationData locData = new MyLocationData.Builder()
                        .accuracy(location.getRadius())
                        // 此处设置开发者获取到的方向信息，顺时针0-360
                        .direction(100).latitude(location.getLatitude())
                        .longitude(location.getLongitude()).build();*/
                isFirstLoc = false;
                LatLng ll = new LatLng(location.getLatitude(),
                        location.getLongitude());

                Log.i("===",location.getAddrStr()+"=="+location.getCity()+"==="+location.getDistrict());
                city=location.getCity();
                area=location.getDistrict();
                SPUtils.setPrefString(mContext, Config.city,city);
                SPUtils.setPrefString(mContext, Config.area,area);
                tv_home_city.setText(location.getCity());
                getHomeData();
            }
        }

        public void onReceivePoi(BDLocation poiLocation) {
        }
    }
}
