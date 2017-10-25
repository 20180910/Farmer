package com.zhizhong.farmer.module.order.network;

import com.zhizhong.farmer.base.BaseObj;
import com.zhizhong.farmer.base.ResponseObj;
import com.zhizhong.farmer.module.order.network.request.XiaDingDanItem;
import com.zhizhong.farmer.module.order.network.response.HaiChongObj;
import com.zhizhong.farmer.module.order.network.response.OrderDefaultDataObj;
import com.zhizhong.farmer.module.order.network.response.OtherFarmerObj;

import java.util.List;
import java.util.Map;

import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;
import rx.Observable;

/**
 * Created by Administrator on 2017/6/28.
 */

public interface IRequest {
    //下单-默认数据显示
    @GET("api/Farmer/GetOrderShow")
    Observable<ResponseObj<OrderDefaultDataObj>> getOrderDefaultData(@Query("user_id") String user_id, @Query("sign") String sign);

    //虫害列表
    @GET("api/Farmer/GetDiseasesPest")
    Observable<ResponseObj<List<HaiChongObj>>> getHaiChongList(@Query("crops") String crops, @Query("sign") String sign);
    //其他农户
    @GET("api/Farmer/GetOtherFarmer")
    Observable<ResponseObj<List<OtherFarmerObj>>> getOtherFarmerList(@QueryMap Map<String,String> map);

    //下订单
    @POST("api/Farmer/PostPlaceOrder")
    Observable<ResponseObj<BaseObj>> xiaDingDan(@QueryMap Map<String,String> map, @Body XiaDingDanItem item);

    //获取地况/障碍物列表
    @GET("api/Farmer/GetConditionObstacles")
    Observable<ResponseObj<List<HaiChongObj>>> getDiKuang(@QueryMap Map<String,String> map);

}
