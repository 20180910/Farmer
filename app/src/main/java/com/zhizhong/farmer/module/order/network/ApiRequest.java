package com.zhizhong.farmer.module.order.network;

import com.github.retrofitutil.NetWorkManager;
import com.zhizhong.farmer.tools.RxResult;

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

    public static Observable getOrderDefaultData(String userId,String sign){
        return getCommonClient().getOrderDefaultData(userId, sign).compose(RxResult.appSchedulers()).compose(RxResult.handleResult());
    }




}
