package com.zhizhong.farmer.module.my.activity;

import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.github.androidtools.inter.MyOnClickListener;
import com.github.customview.MyTextView;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.zhizhong.farmer.R;
import com.zhizhong.farmer.base.BaseActivity;
import com.zhizhong.farmer.base.MySub;
import com.zhizhong.farmer.module.my.network.ApiRequest;
import com.zhizhong.farmer.module.my.network.response.FenXiaoDetailObj;

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
            sexView.findViewById(R.id.iv_fenxiao_qq_space).setOnClickListener(getShareListener(2));
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
                        new ShareAction(mContext)
                                .setPlatform(SHARE_MEDIA.WEIXIN)//传入平台
                                .withText(getSStr(tv_fenxiao_detail_code))//分享内容
//                                .withSubject(getSStr(tv_fenxiao_detail_code))
//                                .withFollow(getSStr(tv_fenxiao_detail_content))
//                                .setCallback(umShareListener)//回调监听器
                                .share();
                        break;
                    case 1://微信-朋友圈
                        if (!UMShareAPI.get(mContext).isInstall(mContext, SHARE_MEDIA.WEIXIN)) {
                            showMsg("请安装微信之后再试");
                            return;
                        }
                        new ShareAction(mContext)
                                .setPlatform(SHARE_MEDIA.WEIXIN_CIRCLE)//传入平台
                                .withText("分销码:"+getSStr(tv_fenxiao_detail_code))//分享内容
                                .share();
                        break;
                    case 2://qq
                        if (!UMShareAPI.get(mContext).isInstall(mContext, SHARE_MEDIA.QQ)) {
                            showMsg("请安装QQ之后再试");
                            return;
                        }
                        new ShareAction(mContext)
                                .setPlatform(SHARE_MEDIA.QQ)//传入平台
                                .withText(getSStr(tv_fenxiao_detail_code))//分享内容
//                                .withSubject(getSStr(tv_fenxiao_detail_code))
//                                .withFollow(getSStr(tv_fenxiao_detail_content))
//                                .setCallback(umShareListener)//回调监听器
                                .share();
                        break;
                    case 3://qq-空间
                        if (!UMShareAPI.get(mContext).isInstall(mContext, SHARE_MEDIA.QQ)) {
                            showMsg("请安装QQ之后再试");
                            return;
                        }
                        new ShareAction(mContext)
                                .setPlatform(SHARE_MEDIA.QZONE)//传入平台
                                .withText("分销码:"+getSStr(tv_fenxiao_detail_code))//分享内容
                                .share();
                        break;
                    case 4://sina
                        if (!UMShareAPI.get(mContext).isInstall(mContext, SHARE_MEDIA.SINA)) {
                            showMsg("请安装sina之后再试");
                            return;
                        }
                        break;
                }
            }
        };
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        UMShareAPI.get(this).release();
    }
}
