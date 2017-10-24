package com.zhizhong.farmer.module.home.network;

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
    private static IRequest getStringClient(String url){
        return NetWorkManager.getStringClient(url).create(IRequest.class);
    }
    public static Observable getPayNotifyUrl(String payment_type,String sign){
        return getCommonClient().getPayNotifyUrl(payment_type,sign).compose(RxResult.appSchedulers()).compose(RxResult.handleResult());
    }
    public static Observable getZhiBaoList(Map map){
        return getCommonClient().getZhiBaoList(map).compose(RxResult.appSchedulers()).compose(RxResult.listResult());
    }
    public static Observable getZhiBaoDetail( String eppo_id,String sign){
        return getCommonClient().getZhiBaoDetail(eppo_id,sign).compose(RxResult.appSchedulers()).compose(RxResult.handleResult());
    }
    public static Observable getHomeData(Map map){
        return getCommonClient().getHomeData(map).compose(RxResult.appSchedulers()).compose(RxResult.handleResult());
    }
    public static Observable getHomeImg( String rnd,String sign){
        return getCommonClient().getHomeImg(rnd,sign).compose(RxResult.appSchedulers()).compose(RxResult.handleResult());
    }
    public static Observable getAllCity(String rnd,String sign){
        return getCommonClient().getAllCity(rnd, sign).compose(RxResult.appSchedulers()).compose(RxResult.listResult());
    }
    public static Observable getHotCity(String rnd,String sign){
        return getCommonClient().getHotCity(rnd, sign).compose(RxResult.appSchedulers()).compose(RxResult.listResult());
    }
    public static Observable getProvince(String rnd, String sign){
        return getCommonClient().getProvince(rnd, sign).compose(RxResult.appSchedulers()).compose(RxResult.listResult());
    }
    public static Observable getCityForProvince(String rnd,String sign){
        return getCommonClient().getCityForProvince(rnd, sign).compose(RxResult.appSchedulers()).compose(RxResult.listResult());
    }
    public static Observable getCityForSearch(String search,String sign){
        return getCommonClient().getCityForSearch(search, sign).compose(RxResult.appSchedulers()).compose(RxResult.listResult());
    }
}
