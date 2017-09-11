package com.zhizhong.farmer.module.tuiguangyuan.fragment;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.github.androidtools.SPUtils;
import com.github.customview.MyTextView;
import com.zhizhong.farmer.Config;
import com.zhizhong.farmer.R;
import com.zhizhong.farmer.base.BaseFragment;
import com.zhizhong.farmer.module.tuiguangyuan.activity.TGYMyYongJinActivity;
import com.zhizhong.farmer.module.tuiguangyuan.activity.TGYMyDataActivity;
import com.zhizhong.farmer.module.tuiguangyuan.activity.TGYMyFarmerActivity;
import com.zhizhong.farmer.module.tuiguangyuan.activity.TGYMyMessageActivity;

import butterknife.BindView;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;


/**
 * Created by administartor on 2017/8/1.
 */

public class TGYMyFragment extends BaseFragment {

    @BindView(R.id.iv_tgy_my_set)
    ImageView iv_tgy_my_set;
    @BindView(R.id.civ_tgy_my_img)
    CircleImageView civ_tgy_my_img;
    @BindView(R.id.tv_tgy_my_name)
    TextView tv_tgy_my_name;
    @BindView(R.id.tv_info_tgm)
    TextView tv_info_tgm;
    @BindView(R.id.tv_tgy_my_yongjin)
    MyTextView tv_tgy_my_yongjin;
    @BindView(R.id.tv_tgy_my_farmer)
    MyTextView tv_tgy_my_farmer;
    @BindView(R.id.tv_tgy_my_msg)
    MyTextView tv_tgy_my_msg;

    @Override
    protected int getContentView() {
        return R.layout.frag_tgy_my;
    }

    public static TGYMyFragment newInstance() {

        Bundle args = new Bundle();

        TGYMyFragment fragment = new TGYMyFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {

    }

    @Override
    public void onResume() {
        super.onResume();
        String userName = SPUtils.getPrefString(mContext, Config.nick_name, null);
        String avatar = SPUtils.getPrefString(mContext, Config.avatar, null);
        String tgm = SPUtils.getPrefString(mContext, Config.distribution_yard, null);
        if (avatar != null) {
            Glide.with(mContext).load(avatar).error(R.drawable.people).into(civ_tgy_my_img);
        }
        tv_tgy_my_name.setText(userName);
        tv_info_tgm.setText(tgm);
    }


    @OnClick({R.id.iv_tgy_my_set, R.id.tv_tgy_my_name, R.id.tv_tgy_my_yongjin, R.id.tv_tgy_my_farmer, R.id.tv_tgy_my_msg})
    public void onViewClick(View view) {
        switch (view.getId()) {
            case R.id.iv_tgy_my_set:
                STActivity(TGYMyDataActivity.class);
                break;
            case R.id.tv_tgy_my_name:
                STActivity(TGYMyDataActivity.class);
                break;
            case R.id.tv_tgy_my_yongjin:
                STActivity(TGYMyYongJinActivity.class);
                break;
            case R.id.tv_tgy_my_farmer:
                STActivity(TGYMyFarmerActivity.class);
                break;
            case R.id.tv_tgy_my_msg:
                STActivity(TGYMyMessageActivity.class);
                break;
        }
    }
}
