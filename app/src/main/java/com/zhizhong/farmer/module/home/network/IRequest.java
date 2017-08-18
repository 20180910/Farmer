package com.zhizhong.farmer.module.home.network;

import com.zhizhong.farmer.base.ResponseObj;
import com.zhizhong.farmer.module.home.network.response.ZhiBaoObj;

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
    //植保中心列表
    @GET("api/Farmer/GetEppoCenterList")
    Observable<ResponseObj<List<ZhiBaoObj>>> getZhiBaoList(@QueryMap Map<String,String> map);

    //植保详情
    @GET("api/Farmer/GetEppoCenterMore")
    Observable<ResponseObj<ZhiBaoObj>> getZhiBaoDetail(@Query("eppo_id") String eppo_id,@Query("sign") String sign);

}
