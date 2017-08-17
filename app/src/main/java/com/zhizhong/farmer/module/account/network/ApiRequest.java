package com.zhizhong.farmer.module.account.network;

import com.github.retrofitutil.NetWorkManager;
import com.zhizhong.farmer.tools.RxResult;

import java.util.Map;

import rx.Observable;

/**
 * Created by Administrator on 2017/6/28.
 */

public class ApiRequest {
    //返回对象-rxjava无缓存
    private static IRequest getCommonClient(){
        return NetWorkManager.getCommonClient().create(IRequest.class);
    }
    //返回对象无缓存
    private static IRequest getGeneralClient(){
        return NetWorkManager.getGeneralClient().create(IRequest.class);
    }
    //返回String无缓存
    private static IRequest getGeneralStringClient(){
        return NetWorkManager.getGeneralStringClient().create(IRequest.class);
    }


    public static Observable getAccountMoney(String userId, String sign){
        return getCommonClient().getAccountMoney(userId,sign).compose(RxResult.appSchedulers()).compose(RxResult.handleResult());
    }
    public static Observable tiXian(Map map){
        return getCommonClient().tiXian(map).compose(RxResult.appSchedulers()).compose(RxResult.handleResult());
    }
    public static Observable getAccountDefault(String userId, String sign){
        return getCommonClient().getAccountDefault(userId,sign).compose(RxResult.appSchedulers()).compose(RxResult.listResult());
    }
    public static Observable getAccount(String userId, String sign){
        return getCommonClient().getAccount(userId,sign).compose(RxResult.appSchedulers()).compose(RxResult.listResult());
    }
    public static Observable setDefaultAccount(Map map){
        return getCommonClient().setDefaultAccount(map).compose(RxResult.appSchedulers()).compose(RxResult.handleResult());
    }
    public static Observable deleteAccount(String accountId, String sign){
        return getCommonClient().deleteAccount(accountId,sign).compose(RxResult.appSchedulers()).compose(RxResult.handleResult());
    }

    public static Observable getTXXieYi(String rnd,String sign){
        return getCommonClient().getTXXieYi(rnd,sign).compose(RxResult.appSchedulers()).compose(RxResult.handleResult());
    }

    public static Observable getBankList(String rnd,String sign){
        return getCommonClient().getBankList(rnd,sign).compose(RxResult.appSchedulers()).compose(RxResult.listResult());
    }
    public static Observable addBank(Map map){
        return getCommonClient().addBank(map).compose(RxResult.appSchedulers()).compose(RxResult.handleResult());
    }




}
