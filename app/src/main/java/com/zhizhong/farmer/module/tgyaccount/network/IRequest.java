package com.zhizhong.farmer.module.tgyaccount.network;


import com.zhizhong.farmer.base.BaseObj;
import com.zhizhong.farmer.base.ResponseObj;
import com.zhizhong.farmer.module.tgyaccount.network.response.AccountObj;
import com.zhizhong.farmer.module.tgyaccount.network.response.BankObj;

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
    //提现-显示账户余额
    @GET("api/Promoters/GetWithdShow")
    Observable<ResponseObj<BaseObj>> getAccountMoney(@Query("user_id") String user_id, @Query("sign") String sign);

    //申请提现
    @GET("api/Promoters/GetWithdrawals")
    Observable<ResponseObj<BaseObj>> tiXian(@QueryMap Map<String,String>map);

    //获取默认账户
    @GET("api/Promoters/GetAccountDefault")
    Observable<ResponseObj<List<AccountObj>>> getAccountDefault(@Query("user_id") String user_id, @Query("sign") String sign);

    //获取账户列表
    @GET("api/Promoters/GetAccount")
    Observable<ResponseObj<List<AccountObj>>> getAccount(@Query("user_id") String user_id, @Query("sign") String sign);

    //设置默认账户
    @GET("api/Promoters/GetEditDefalut")
    Observable<ResponseObj<BaseObj>> setDefaultAccount(@QueryMap Map<String,String> map);

    //删除账户
    @GET("api/Promoters/GetDelAccount")
    Observable<ResponseObj<BaseObj>> deleteAccount(@Query("account_id") String account_id, @Query("sign") String sign);

    //获取资金快捷收付及授权协议
    @GET("api/FlyMember/GetQuickCashPaymentLicensingAgreement")
    Observable<ResponseObj<BaseObj>> getTXXieYi(@Query("rnd") String rnd, @Query("sign") String sign);

    //获取银行列表
    @GET("api/Promoters/GetBankList")
    Observable<ResponseObj<List<BankObj>>> getBankList(@Query("rnd") String rnd, @Query("sign") String sign);

    //添加银行卡账户
    @GET("api/Promoters/GetAddAccount")
    Observable<ResponseObj<BaseObj>> addBank(@QueryMap Map<String,String> map);
}
