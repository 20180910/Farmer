package com.zhizhong.farmer.module.my.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.github.androidtools.SPUtils;
import com.zhizhong.farmer.Config;
import com.zhizhong.farmer.R;
import com.zhizhong.farmer.base.BaseFragment;
import com.zhizhong.farmer.module.my.Constant;
import com.zhizhong.farmer.module.my.activity.MyDataActivity;
import com.zhizhong.farmer.module.my.activity.MyFarmerActivity;
import com.zhizhong.farmer.module.my.activity.MyFenXiaoActivity;
import com.zhizhong.farmer.module.my.activity.MyOrderListActivity;
import com.zhizhong.farmer.module.my.activity.VIPLevelActivity;

import butterknife.BindView;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;


/**
 * Created by administartor on 2017/8/1.
 */

public class MyFragment extends BaseFragment {
    @BindView(R.id.civ_my_img)
    CircleImageView civ_my_img;
    @BindView(R.id.tv_my_name)
    TextView tv_my_name;
    @BindView(R.id.tv_my_level)
    TextView tv_my_level;
    private Intent intent;


    @Override
    protected int getContentView() {
        return R.layout.frag_my;
    }

    public static MyFragment newInstance() {

        Bundle args = new Bundle();

        MyFragment fragment = new MyFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected void initView() {

    }

    @Override
    public void onResume() {
        super.onResume();
        String userName = SPUtils.getPrefString(mContext, Config.nick_name, null);
        String avatar = SPUtils.getPrefString(mContext, Config.avatar, null);
        if (avatar != null) {
            Glide.with(mContext).load(avatar).error(R.drawable.people).into(civ_my_img);
        }
        int level = SPUtils.getPrefInt(mContext, Config.level, 0);

        tv_my_name.setText(userName);
        tv_my_level.setText(level + "");
    }

    @Override
    protected void initData() {

    }

    @Override
    public void again() {

    }

    @OnClick({R.id.ll_my_vip,R.id.iv_my_set, R.id.tv_my_data, R.id.tv_my_farmer, R.id.tv_my_fenxiao, R.id.ll_my_order,R.id.tv_my_dws,R.id.tv_my_dqr, R.id.tv_my_djd, R.id.tv_my_yjd, R.id.tv_my_complete})
    public void onViewClick(View view) {
        switch (view.getId()) {
            case R.id.ll_my_vip://VIP
                STActivity(VIPLevelActivity.class);
                break;
            case R.id.iv_my_set://设置
                STActivity(MyDataActivity.class);
                break;
            case R.id.tv_my_data://我的资料
                STActivity(MyDataActivity.class);
                break;
            case R.id.tv_my_farmer://我的农户
                STActivity(MyFarmerActivity.class);
                break;
            case R.id.tv_my_fenxiao://我的分销
                STActivity(MyFenXiaoActivity.class);
                break;
            case R.id.ll_my_order://全部
                intent = new Intent();
                intent.putExtra(Constant.type,Constant.type_0);
                STActivity(intent,MyOrderListActivity.class);
                break;
            case R.id.tv_my_dws://待完善
                intent = new Intent();
                intent.putExtra(Constant.type,Constant.type_1);
                STActivity(intent,MyOrderListActivity.class);
                break;
            case R.id.tv_my_dqr://待确认
                intent = new Intent();
                intent.putExtra(Constant.type,Constant.type_2);
                STActivity(intent,MyOrderListActivity.class);
                break;
            case R.id.tv_my_djd://待接单
                intent = new Intent();
                intent.putExtra(Constant.type,Constant.type_3);
                STActivity(intent,MyOrderListActivity.class);
                break;
            case R.id.tv_my_yjd://已接单
                intent = new Intent();
                intent.putExtra(Constant.type,Constant.type_4);
                STActivity(intent,MyOrderListActivity.class);
                break;
            case R.id.tv_my_complete://已完成
                intent = new Intent();
                intent.putExtra(Constant.type,Constant.type_5);
                STActivity(intent,MyOrderListActivity.class);
                break;
        }
    }

}
