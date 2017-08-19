package com.zhizhong.farmer.module.tuiguangyuan.network;

import com.github.retrofitutil.NetWorkManager;
import com.zhizhong.farmer.module.tuiguangyuan.network.request.UploadImgItem;
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
    public static Observable registerTGY(Map map){
        return getCommonClient().registerTGY(map).compose(RxResult.appSchedulers()).compose(RxResult.handleResult());
    }

    public static Observable loginTGY(Map map){
        return getCommonClient().loginTGY(map).compose(RxResult.appSchedulers()).compose(RxResult.handleResult());
    }
    public static Observable updateInfoTGY(Map map){
        return getCommonClient().updateInfoTGY(map).compose(RxResult.appSchedulers()).compose(RxResult.handleResult());
    }
    public static Observable uploadImg(String rnd,String sign, UploadImgItem imgItem){
        return getCommonClient().uploadImg(rnd,sign,imgItem).compose(RxResult.appSchedulers()).compose(RxResult.handleResult());
    }
    public static Observable setNewPasswordTGY(Map map){
        return getCommonClient().setNewPasswordTGY(map).compose(RxResult.appSchedulers()).compose(RxResult.handleResult());
    }
    public static Observable setForgetPasswordTGY(Map map){
        return getCommonClient().setForgetPasswordTGY(map).compose(RxResult.appSchedulers()).compose(RxResult.handleResult());
    }
    public static Observable getTGYMsgList(Map map){
        return getCommonClient().getTGYMsgList(map).compose(RxResult.appSchedulers()).compose(RxResult.listResult());
    }
    public static Observable getTGYMsgDetail(String msgId, String sign){
        return getCommonClient().getTGYMsgDetail(msgId, sign).compose(RxResult.appSchedulers()).compose(RxResult.handleResult());
    }
    public static Observable getFarmerList(Map map){
        return getCommonClient().getFarmerList(map).compose(RxResult.appSchedulers()).compose(RxResult.listResult());
    }
    public static Observable getTGYYongJin(Map map){
        return getCommonClient().getTGYYongJin(map).compose(RxResult.appSchedulers()).compose(RxResult.handleResult());
    }
    public static Observable getYongJin(Map map){
        return getCommonClient().getYongJin(map).compose(RxResult.appSchedulers()).compose(RxResult.handleResult());
    }






}
