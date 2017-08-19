package com.zhizhong.farmer.module.zixun.network;

import com.zhizhong.farmer.base.ResponseObj;
import com.zhizhong.farmer.module.zixun.network.response.ZiXunImgObj;
import com.zhizhong.farmer.module.zixun.network.response.ZiXunObj;

import java.util.List;
import java.util.Map;

import retrofit2.http.GET;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;
import rx.Observable;

/**
 * Created by Administrator on 2017/6/28.
 */

public interface IRequest {
    //资讯列表
    @GET("api/Farmer/GetInformationList")
    Observable<ResponseObj<List<ZiXunObj>>> getZiXunList(@QueryMap Map<String,String>map);

    //资讯轮播图
    @GET("api/Farmer/GetInformationImageList")
    Observable<ResponseObj<List<ZiXunImgObj>>> getZiXunImg(@Query("rnd") String rnd, @Query("sign") String sign);

    //资讯详情
    @GET("api/Farmer/GetInformationMore")
    Observable<ResponseObj<ZiXunObj>> getZiXunDetail(@Query("information_id") String information_id, @Query("sign") String sign);

}
