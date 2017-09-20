package com.zhizhong.farmer.module.tuiguangyuan.network;


import com.zhizhong.farmer.base.BaseObj;
import com.zhizhong.farmer.base.ResponseObj;
import com.zhizhong.farmer.module.tuiguangyuan.network.request.UploadImgItem;
import com.zhizhong.farmer.module.tuiguangyuan.network.response.TGYFarmerObj;
import com.zhizhong.farmer.module.tuiguangyuan.network.response.TGYLoginObj;
import com.zhizhong.farmer.module.tuiguangyuan.network.response.MessageDetailObj;
import com.zhizhong.farmer.module.tuiguangyuan.network.response.MessageObj;
import com.zhizhong.farmer.module.tuiguangyuan.network.response.TGYYongJinObj;

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
    //注册授权协议
    @GET("api/Promoters/GetFarmerAgreement")
    Observable<ResponseObj<BaseObj>> getRegisterXieYi(@Query("rnd") String rnd, @Query("sign") String sign);

    //获取验证码
    @GET("api/Lib/GetSMSCode")
    Observable<ResponseObj<BaseObj>> getMsgCode(@QueryMap Map<String,String> map);

    //推广员注册
    @GET("api/Farmer/GetMemberRegister")
    Observable<ResponseObj<BaseObj>> registerTGY(@QueryMap Map<String,String> map);

    //推广员单独修改图片
    @GET("api/Promoters/GetSetUserAvatar")
    Observable<ResponseObj<BaseObj>> uploadImgForInfo(@QueryMap Map<String,String> map);

    //推广员用户登录
    @GET("api/Promoters/GetUserLogin")
    Observable<ResponseObj<TGYLoginObj>> loginTGY(@QueryMap Map<String,String> map);

    //上传图片
    @POST("api/Lib/PostUploadFileBase64")
    Observable<ResponseObj<BaseObj>> uploadImg(@Query("rnd") String rnd, @Query("sign") String sign, @Body UploadImgItem item);

    //TGY用户资料修改
    @GET("api/Promoters/GetSetFarmerInfo")
    Observable<ResponseObj<BaseObj>> updateInfoTGY(@QueryMap Map<String,String> map);

    //TGY密码修改
    @GET("api/Promoters/GetSetNewPassword")
    Observable<ResponseObj<BaseObj>> setNewPasswordTGY(@QueryMap Map<String,String> map);

    //TGY忘记密码重置
    @GET("api/Promoters/GetSetPassword")
    Observable<ResponseObj<BaseObj>> setForgetPasswordTGY(@QueryMap Map<String,String> map);

    //TGY消息列表
    @GET("api/Promoters/GetNewsList")
    Observable<ResponseObj<List<MessageObj>>> getTGYMsgList(@QueryMap Map<String,String> map);

    //TGY消息详情
    @GET("api/Promoters/GetNewsDetail")
    Observable<ResponseObj<MessageDetailObj>> getTGYMsgDetail(@Query("news_id") String news_id, @Query("sign") String sign);

    //TGY我的农户
    @GET("api/Promoters/GetMyFaemer")
    Observable<ResponseObj<List<TGYFarmerObj>>> getFarmerList(@QueryMap Map<String,String> map);

    //TGY我的佣金
    @GET("api/Promoters/GetMyCommission")
    Observable<ResponseObj<TGYYongJinObj>> getTGYYongJin(@QueryMap Map<String,String> map);

    //农户-我的佣金
    @GET("api/Farmer/GetMyCommission")
    Observable<ResponseObj<TGYYongJinObj>> getYongJin(@QueryMap Map<String,String> map);

}
