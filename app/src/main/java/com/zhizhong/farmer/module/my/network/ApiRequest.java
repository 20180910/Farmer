package com.zhizhong.farmer.module.my.network;

import com.github.retrofitutil.NetWorkManager;
import com.zhizhong.farmer.module.my.network.request.UploadImgItem;
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
    public static Observable getRegisterXieYi(String rnd, String sign){
        return getCommonClient().getRegisterXieYi(rnd,sign).compose(RxResult.appSchedulers()).compose(RxResult.handleResult());
    }
    public static Observable getMsgCode(Map map){
        return getCommonClient().getMsgCode(map).compose(RxResult.appSchedulers()).compose(RxResult.handleResult());
    }
    public static Observable register(Map map){
        return getCommonClient().register(map).compose(RxResult.appSchedulers()).compose(RxResult.handleResult());
    }
    public static Observable login(Map map){
        return getCommonClient().login(map).compose(RxResult.appSchedulers()).compose(RxResult.handleResult());
    }
    public static Observable setFarmerInfo(Map map){
        return getCommonClient().setFarmerInfo(map).compose(RxResult.appSchedulers()).compose(RxResult.handleResult());
    }
    public static Observable uploadImg(String rnd,String sign, UploadImgItem imgItem){
        return getCommonClient().uploadImg(rnd,sign,imgItem).compose(RxResult.appSchedulers()).compose(RxResult.handleResult());
    }
    public static Observable setNewPassword(Map map){
        return getCommonClient().setNewPassword(map).compose(RxResult.appSchedulers()).compose(RxResult.handleResult());
    }
    public static Observable setForgetPassword(Map map){
        return getCommonClient().setForgetPassword(map).compose(RxResult.appSchedulers()).compose(RxResult.handleResult());
    }
    public static Observable getFenXiao(String userId,String sign){
        return getCommonClient().getFenXiao(userId, sign).compose(RxResult.appSchedulers()).compose(RxResult.handleResult());
    }
    public static Observable getFenXiaoDetail(String userId,String sign){
        return getCommonClient().getFenXiaoDetail(userId, sign).compose(RxResult.appSchedulers()).compose(RxResult.handleResult());
    }
    public static Observable getBankList(String rnd,String sign){
        return getCommonClient().getBankList(rnd, sign).compose(RxResult.appSchedulers()).compose(RxResult.listResult());
    }
    public static Observable addBank(Map map){
        return getCommonClient().addBank(map).compose(RxResult.appSchedulers()).compose(RxResult.handleResult());
    }
    public static Observable getAccount(String userId,String sign){
        return getCommonClient().getAccount(userId, sign).compose(RxResult.appSchedulers()).compose(RxResult.listResult());
    }
    public static Observable deleteAccount(String accountId, String sign){
        return getCommonClient().deleteAccount(accountId,sign).compose(RxResult.appSchedulers()).compose(RxResult.handleResult());
    }
    public static Observable getDefaultAccount(String userId,String sign){
        return getCommonClient().getDefaultAccount(userId, sign).compose(RxResult.appSchedulers()).compose(RxResult.listResult());
    }
    public static Observable setDefaultAccount(Map map){
        return getCommonClient().setDefaultAccount(map).compose(RxResult.appSchedulers()).compose(RxResult.handleResult());
    }
    public static Observable getAllMoney(String userId,String sign){
        return getCommonClient().getAllMoney(userId, sign).compose(RxResult.appSchedulers()).compose(RxResult.handleResult());
    }
    public static Observable tiXian(Map map){
        return getCommonClient().tiXian(map).compose(RxResult.appSchedulers()).compose(RxResult.handleResult());
    }
    public static Observable xiaJi(String userId,String sign){
        return getCommonClient().xiaJi(userId,sign).compose(RxResult.appSchedulers()).compose(RxResult.listResult());
    }
    public static Observable getVouchersList(Map map){
        return getCommonClient().getVouchersList(map).compose(RxResult.appSchedulers()).compose(RxResult.listResult());
    }
    public static Observable getVouchersNum(String userId,String sign){
        return getCommonClient().getVouchersNum(userId, sign).compose(RxResult.appSchedulers()).compose(RxResult.handleResult());
    }
}
