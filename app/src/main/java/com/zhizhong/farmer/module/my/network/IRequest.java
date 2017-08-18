package com.zhizhong.farmer.module.my.network;


import com.zhizhong.farmer.base.BaseObj;
import com.zhizhong.farmer.base.ResponseObj;
import com.zhizhong.farmer.module.my.network.response.VouchersNumObj;
import com.zhizhong.farmer.module.my.network.response.VouchersObj;
import com.zhizhong.farmer.module.my.network.response.XiaJiObj;
import com.zhizhong.farmer.module.tgyaccount.network.response.AccountObj;
import com.zhizhong.farmer.module.tgyaccount.network.response.BankObj;
import com.zhizhong.farmer.module.my.network.request.UploadImgItem;
import com.zhizhong.farmer.module.my.network.response.FenXiaoDetailObj;
import com.zhizhong.farmer.module.my.network.response.FenXiaoObj;
import com.zhizhong.farmer.module.my.network.response.LoginObj;

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

    //农户资料修改
    @GET("api/Farmer/GetSetFarmerInfo")
    Observable<ResponseObj<BaseObj>> setFarmerInfo(@QueryMap Map<String,String>map);
    //上传图片
    @POST("api/Lib/PostUploadFileBase64")
    Observable<ResponseObj<BaseObj>> uploadImg(@Query("rnd") String rnd, @Query("sign") String sign, @Body UploadImgItem item);

    //农户密码修改
    @GET("api/Farmer/GetSetNewPassword")
    Observable<ResponseObj<BaseObj>> setNewPassword(@QueryMap Map<String,String> map);

    //农户忘记密码重置
    @GET("api/Farmer/GetSetPassword")
    Observable<ResponseObj<BaseObj>> setForgetPassword(@QueryMap Map<String,String> map);

    //我的分销
    @GET("api/Farmer/GetMyDistribution")
    Observable<ResponseObj<FenXiaoObj>> getFenXiao(@Query("user_id") String user_id, @Query("sign") String sign);

    //我的分销-详情
    @GET("api/Farmer/GetMyDistributionYard")
    Observable<ResponseObj<FenXiaoDetailObj>> getFenXiaoDetail(@Query("user_id") String user_id, @Query("sign") String sign);


    //获取银行列表
    @GET("api/Farmer/GetBankList")
    Observable<ResponseObj<List<BankObj>>> getBankList(@Query("rnd") String rnd, @Query("sign") String sign);

    //添加银行卡
    @GET("api/Farmer/GetAddAccount")
    Observable<ResponseObj<BaseObj>> addBank(@QueryMap Map<String,String> map);

    //获取账户列表
    @GET("api/Farmer/GetAccount")
    Observable<ResponseObj<List<AccountObj>>> getAccount(@Query("user_id") String user_id, @Query("sign") String sign);

    //删除账户
    @GET("api/Farmer/GetDelAccount")
    Observable<ResponseObj<BaseObj>> deleteAccount(@Query("account_id") String account_id, @Query("sign") String sign);

    //设置默认账户
    @GET("api/Farmer/GetEditDefalut")
    Observable<ResponseObj<BaseObj>> setDefaultAccount(@QueryMap Map<String,String> map);

    //获取默认账户
    @GET("api/Farmer/GetAccountDefault")
    Observable<ResponseObj<List<AccountObj>>> getDefaultAccount(@Query("user_id") String user_id, @Query("sign") String sign);

    //获取所有金额(全部转出)
    @GET("api/Farmer/GetAllMoney")
    Observable<ResponseObj<BaseObj>> getAllMoney(@Query("user_id") String user_id, @Query("sign") String sign);

    //提现申请
    @GET("api/Farmer/GetWithdrawals")
    Observable<ResponseObj<BaseObj>> tiXian(@QueryMap Map<String,String> map);

    //我的下级
    @GET("api/Farmer/GetMyLowerLevel")
    Observable<ResponseObj<List<XiaJiObj>>> xiaJi(@Query("user_id") String user_id, @Query("sign") String sign);

    //抵用券
    @GET("api/Farmer/GetMyCoupons")
    Observable<ResponseObj<List<VouchersObj>>> getVouchersList(@QueryMap Map<String,String> map);

    //抵用券数量
    @GET("api/Farmer/GetMyCouponsNum")
    Observable<ResponseObj<VouchersNumObj>> getVouchersNum(@Query("user_id") String user_id, @Query("sign") String sign);


}

