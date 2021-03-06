package com.zhizhong.farmer.module.zixun.network;

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

    public static Observable getZiXunList(Map map){
        return getCommonClient().getZiXunList(map).compose(RxResult.appSchedulers()).compose(RxResult.listResult());
    }
    public static Observable getZiXunImg(String rnd,String sign){
        return getCommonClient().getZiXunImg(rnd,sign).compose(RxResult.appSchedulers()).compose(RxResult.listResult());
    }
    public static Observable getZiXunDetail(String information_id,String sign){
        return getCommonClient().getZiXunDetail(information_id,sign).compose(RxResult.appSchedulers()).compose(RxResult.handleResult());
    }

}
