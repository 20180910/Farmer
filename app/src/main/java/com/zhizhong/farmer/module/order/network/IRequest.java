package com.zhizhong.farmer.module.order.network;

import com.zhizhong.farmer.base.ResponseObj;
import com.zhizhong.farmer.module.order.network.response.OrderDefaultDataObj;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by Administrator on 2017/6/28.
 */

public interface IRequest {
    //注册授权协议
    @GET("api/Farmer/GetOrderShow")
    Observable<ResponseObj<OrderDefaultDataObj>> getOrderDefaultData(@Query("user_id") String user_id, @Query("sign") String sign);
}
