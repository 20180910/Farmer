package com.zhizhong.farmer;


import android.app.Application;
import android.text.TextUtils;
import android.util.Log;

import com.baidu.mapapi.SDKInitializer;
import com.github.androidtools.SPUtils;
import com.github.retrofitutil.NetWorkManager;
import com.umeng.socialize.PlatformConfig;
import com.umeng.socialize.UMShareAPI;

import cn.jpush.android.api.JPushInterface;

/**
 * Created by administartor on 2017/8/8.
 */

public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        NetWorkManager.getInstance(getApplicationContext(),"http://121.40.186.118:5009/",BuildConfig.DEBUG).complete();
        SDKInitializer.initialize(getApplicationContext());
        PlatformConfig.setWeixin(Config.weixing_id, Config.weixing_AppSecret);
        PlatformConfig.setQQZone(Config.qq_id, Config.qq_key);
        PlatformConfig.setSinaWeibo("3921700954", "04b48b094faeb16683c32669824ebdad", "http://sns.whalecloud.com");
        JPushInterface.setDebugMode(BuildConfig.DEBUG); 	// 设置开启日志,发布时请关闭日志
        JPushInterface.init(this);     		// 初始化 JPush

        String registrationID = JPushInterface.getRegistrationID(getApplicationContext());
        Log.i("registrationID","registrationID====="+registrationID);
        if(!TextUtils.isEmpty(registrationID)){
            SPUtils.setPrefString(getApplicationContext(),Config.jiguangRegistrationId,registrationID);
        }
        UMShareAPI.get(this);
    }

}
