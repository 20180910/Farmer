package com.zhizhong.farmer.module.my.network;


import com.zhizhong.farmer.base.BaseObj;
import com.zhizhong.farmer.base.ResponseObj;
import com.zhizhong.farmer.module.my.network.response.LoginObj;

import java.util.Map;

import retrofit2.http.GET;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;
import rx.Observable;

/**
 * Created by Administrator on 2017/6/28.
 */

public interface IRequest {
    //注册授权协议
    @GET("api/Farmer/GetFarmerAgreement")
    Observable<ResponseObj<BaseObj>> getRegisterXieYi(@Query("rnd") String rnd, @Query("sign") String sign);
    //获取验证码
    @GET("api/Lib/GetSMSCode")
    Observable<ResponseObj<BaseObj>> getMsgCode(@QueryMap Map<String,String> map);
    //农户注册
    @GET("api/Farmer/GetMemberRegister")
    Observable<ResponseObj<BaseObj>> register(@QueryMap Map<String,String>map);

    //农户登录
    @GET("api/FlyMember/GetUserLogin")
    Observable<ResponseObj<LoginObj>> login(@QueryMap Map<String,String>map);

}
