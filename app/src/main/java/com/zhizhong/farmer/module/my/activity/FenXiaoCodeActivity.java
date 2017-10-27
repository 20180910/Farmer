package com.zhizhong.farmer.module.my.activity;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.github.androidtools.inter.MyOnClickListener;
import com.github.customview.MyTextView;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.media.UMWeb;
import com.zhizhong.farmer.R;
import com.zhizhong.farmer.base.BaseActivity;
import com.zhizhong.farmer.base.MySub;
import com.zhizhong.farmer.module.my.network.ApiRequest;
import com.zhizhong.farmer.module.my.network.response.FenXiaoDetailObj;
import com.zhizhong.farmer.module.my.network.response.ShareDataObj;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by administartor on 2017/8/8.
 */

public class FenXiaoCodeActivity extends BaseActivity {


    @BindView(R.id.tv_fenxiao_detail_content)
    TextView tv_fenxiao_detail_content;
    @BindView(R.id.tv_fenxiao_detail_code)
    MyTextView tv_fenxiao_detail_code;

    private BottomSheetDialog shareDialog;
    @Override
    protected int getContentView() {
        setAppTitle("分销码");
        setAppRightImg(R.drawable.img42);
        return R.layout.act_fen_xiao_code;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {
        showProgress();
        getData();
    }

    private void getData() {
        addSubscription(ApiRequest.getFenXiaoDetail(getUserId(),getSign()).subscribe(new MySub<FenXiaoDetailObj>(mContext,pl_load) {
            @Override
            public void onMyNext(FenXiaoDetailObj obj) {
                tv_fenxiao_detail_content.setText(obj.getContent());
                tv_fenxiao_detail_code.setText(obj.getDistribution_yard());
            }
        }));
    }

    @OnClick({R.id.app_right_iv})
    protected void onViewClick(View v) {
        switch (v.getId()) {
            case R.id.app_right_iv:
                selectShare();
                break;
        }
    }
    private void selectShare() {
        if (shareDialog == null) {
            View sexView= LayoutInflater.from(mContext).inflate(R.layout.popu_fenxiaocode_share,null);
            sexView.findViewById(R.id.iv_fenxiao_weixin).setOnClickListener(getShareListener(0));
            sexView.findViewById(R.id.iv_fenxiao_weixin_friend).setOnClickListener(getShareListener(1));
            sexView.findViewById(R.id.iv_fenxiao_qq).setOnClickListener(getShareListener(2));
            sexView.findViewById(R.id.iv_fenxiao_qq_space).setOnClickListener(getShareListener(3));
            sexView.findViewById(R.id.iv_fenxiao_sina).setOnClickListener(getShareListener(4));
            sexView.findViewById(R.id.tv_fenxiao_cancle).setOnClickListener(new MyOnClickListener() {
                @Override
                protected void onNoDoubleClick(View view) {
                    shareDialog.dismiss();
                }
            });
            shareDialog = new BottomSheetDialog(mContext);
            shareDialog.setCanceledOnTouchOutside(true);
            shareDialog.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            shareDialog.setContentView(sexView);
        }
        shareDialog.show();
    }

    @NonNull
    private MyOnClickListener getShareListener(int type) {
        return new MyOnClickListener() {
            @Override
            protected void onNoDoubleClick(View view) {
                shareDialog.dismiss();
                switch (type){
                    case 0://微信
                        if (!UMShareAPI.get(mContext).isInstall(mContext, SHARE_MEDIA.WEIXIN)) {
                            showMsg("请安装微信之后再试");
                            return;
                        }
                        getShareData(SHARE_MEDIA.WEIXIN);
                        break;
                    case 1://微信-朋友圈
                        if (!UMShareAPI.get(mContext).isInstall(mContext, SHARE_MEDIA.WEIXIN)) {
                            showMsg("请安装微信之后再试");
                            return;
                        }
                        getShareData(SHARE_MEDIA.WEIXIN_CIRCLE);
                        break;
                    case 2://qq
                        if (!UMShareAPI.get(mContext).isInstall(mContext, SHARE_MEDIA.QQ)) {
                            showMsg("请安装QQ之后再试");
                            return;
                        }
                        getShareData(SHARE_MEDIA.QQ);
                        break;
                    case 3://qq-空间
                        if (!UMShareAPI.get(mContext).isInstall(mContext, SHARE_MEDIA.QQ)) {
                            showMsg("请安装QQ之后再试");
                            return;
                        }
                        getShareData(SHARE_MEDIA.QZONE);
                        break;
                    case 4://sina
                        if (!UMShareAPI.get(mContext).isInstall(mContext, SHARE_MEDIA.SINA)) {
                            showMsg("请安装sina之后再试");
                            return;
                        }
                        showMsg("正在开发");
                        break;
                }
            }
        };
    }

    private void getShareData(SHARE_MEDIA shareMedia) {
        showLoading();
        addSubscription(ApiRequest.getShareData("2",getSign("type","2")).subscribe(new MySub<ShareDataObj>(mContext,true) {
            @Override
            public void onMyNext(ShareDataObj obj) {
                UMWeb web = new UMWeb(obj.getShare_link());
                UMImage image=new UMImage(mContext,R.drawable.default_img);
                web.setTitle(obj.getTitle());//标题
                web.setThumb(image);  //缩略图
                web.setDescription(obj.getContent());//描述
                new ShareAction(mContext)
                        .setPlatform(shareMedia)//传入平台
                        .withMedia(web)
//                        .withText(getSStr(tv_fenxiao_detail_code))//分享内容
                        .setCallback(getListener())
                        .share();
                dismissLoading();
            }
        }));
    }

    @NonNull
    private UMShareListener getListener() {
        return new UMShareListener() {
            @Override
            public void onStart(SHARE_MEDIA share_media) {
                Log.i("============","============onStart");
            }

            @Override
            public void onResult(SHARE_MEDIA share_media) {
                dismissLoading();
                Log.i("============","============onResult");
            }

            @Override
            public void onError(SHARE_MEDIA share_media, Throwable throwable) {
                dismissLoading();
                showMsg(throwable.getMessage());
                Log.i("============","============onError");
            }

            @Override
            public void onCancel(SHARE_MEDIA share_media) {
                dismissLoading();
                Log.i("============","============onCancel");
            }
        };
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        UMShareAPI.get(this).release();
    }
}
