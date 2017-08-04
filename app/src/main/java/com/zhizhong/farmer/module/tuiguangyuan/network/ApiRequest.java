package com.zhizhong.farmer.module.tuiguangyuan.network;

import com.github.retrofitutil.NetWorkManager;

/**
 * Created by Administrator on 2017/6/28.
 */

public class ApiRequest {
    //返回对象-rxjava无缓存
    private static com.zhizhong.farmer.module.my.network.IRequest getCommonClient(){
        return NetWorkManager.getCommonClient().create(com.zhizhong.farmer.module.my.network.IRequest.class);
    }
    //返回对象无缓存
    private static com.zhizhong.farmer.module.my.network.IRequest getGeneralClient(){
        return NetWorkManager.getGeneralClient().create(com.zhizhong.farmer.module.my.network.IRequest.class);
    }
    //返回String无缓存
    private static com.zhizhong.farmer.module.my.network.IRequest getGeneralStringClient(){
        return NetWorkManager.getGeneralStringClient().create(com.zhizhong.farmer.module.my.network.IRequest.class);
    }
}
