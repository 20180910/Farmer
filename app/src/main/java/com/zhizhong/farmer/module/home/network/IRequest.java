package com.zhizhong.farmer.module.home.network;

import com.zhizhong.farmer.base.ResponseObj;
import com.zhizhong.farmer.module.home.network.response.CityObj;
import com.zhizhong.farmer.module.home.network.response.HomeDataObj;
import com.zhizhong.farmer.module.home.network.response.HomeImgObj;
import com.zhizhong.farmer.module.home.network.response.ProvinceObj;
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

    //首页
    @GET("api/Farmer/GetHomePageBottom")
    Observable<ResponseObj<HomeDataObj>> getHomeData(@Query("rnd") String rnd, @Query("sign") String sign);

    //首页-图片
    @GET("api/Farmer/GetHomePageTop")
    Observable<ResponseObj<HomeImgObj>> getHomeImg(@Query("rnd") String rnd, @Query("sign") String sign);

    //获取中国所有的城市
    @GET("api/Lib/GetAllCity")
    Observable<ResponseObj<List<CityObj>>> getAllCity(@Query("rnd") String rnd, @Query("sign") String sign);


    //获取中国热门城市
    @GET("api/Lib/GetCityHot")
    Observable<ResponseObj<List<CityObj>>> getHotCity(@Query("rnd") String rnd, @Query("sign") String sign);

    //获取省份
    @GET("api/Lib/GetProvince")
    Observable<ResponseObj<List<ProvinceObj>>> getProvince(@Query("rnd") String rnd, @Query("sign") String sign);

    //获取城市
    @GET("api/Lib/GetCity")
    Observable<ResponseObj<List<CityObj>>> getCityForProvince(@Query("parent_id") String parent_id, @Query("sign") String sign);

    //城市模糊查询
    @GET("api/Lib/GetCityByKeyword")
    Observable<ResponseObj<List<CityObj>>> getCityForSearch(@Query("city") String city, @Query("sign") String sign);


}
