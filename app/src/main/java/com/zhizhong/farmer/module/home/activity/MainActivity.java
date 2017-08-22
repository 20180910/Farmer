package com.zhizhong.farmer.module.home.activity;

import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.content.LocalBroadcastManager;
import android.text.TextUtils;
import android.view.View;
import android.widget.RelativeLayout;

import com.github.androidtools.StatusBarUtils;
import com.github.baseclass.rx.MySubscriber;
import com.github.customview.MyRadioButton;
import com.zhizhong.farmer.Config;
import com.zhizhong.farmer.R;
import com.zhizhong.farmer.base.BaseActivity;
import com.zhizhong.farmer.broadcast.MyOperationBro;
import com.zhizhong.farmer.module.home.event.XiaDanEvent;
import com.zhizhong.farmer.module.home.event.ZiXunEvent;
import com.zhizhong.farmer.module.home.fragment.HomeFragment;
import com.zhizhong.farmer.module.my.activity.LoginActivity;
import com.zhizhong.farmer.module.my.fragment.MyFragment;
import com.zhizhong.farmer.module.order.fragment.XiaDingDanFragment;
import com.zhizhong.farmer.module.zixun.fragment.ZiXunFragment;

import butterknife.BindView;
import butterknife.OnClick;

public class MainActivity extends BaseActivity {

    @BindView(R.id.status_bar)
    View status_bar;


    HomeFragment homeFragment;
    ZiXunFragment ziXunFragment;
    XiaDingDanFragment xiaDingDanFragment;
    MyFragment myFragment;
    @BindView(R.id.rb_home_shouye)
    MyRadioButton rb_home_shouye;
    @BindView(R.id.rb_home_zixun)
    MyRadioButton rb_home_zixun;
    @BindView(R.id.rb_home_xdd)
    MyRadioButton rb_home_xdd;
    @BindView(R.id.rb_home_my)
    MyRadioButton rb_home_my;


    private MyRadioButton selectButton;

    private LocalBroadcastManager localBroadcastManager;
    private MyOperationBro myOperationBro;
    @Override
    protected int getContentView() {
        return R.layout.act_home;
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        if (intent != null && Config.exitAPP.equals(intent.getAction())) {
            STActivity(SelectUserActivity.class);
            finish();
//            selectPerson();
//            selectButton.setChecked(true);
        }
    }

    @Override
    protected void initRxBus() {
        super.initRxBus();
        getRxBusEvent(ZiXunEvent.class, new MySubscriber() {
            @Override
            public void onMyNext(Object o) {
                ziXun();
                selectButton.setChecked(true);
            }
        });
        getRxBusEvent(XiaDanEvent.class, new MySubscriber() {
            @Override
            public void onMyNext(Object o) {
                xiaDan();
                selectButton.setChecked(true);
            }
        });
    }

    @Override
    protected void initView() {
        setBroadcast();
        int statusBarHeight = StatusBarUtils.getStatusBarHeight(this);
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        layoutParams.height = statusBarHeight;
        status_bar.setLayoutParams(layoutParams);
        status_bar.setBackgroundColor(getResources().getColor(R.color.white));

        selectButton = rb_home_shouye;
        homeFragment = new HomeFragment();
        getSupportFragmentManager().beginTransaction().add(R.id.layout_main_content, homeFragment).commitAllowingStateLoss();

    }

    private void setBroadcast() {
        localBroadcastManager = LocalBroadcastManager.getInstance( this );
        myOperationBro =new MyOperationBro(new MyOperationBro.LoginBroInter() {
            @Override
            public void loginSuccess() {
                selectMy();
                selectButton.setChecked(true);
            }

            @Override
            public void exitLogin() {
                finish();

            }
            @Override
            public void addHomeworkSuccess() {

            }
        });
        localBroadcastManager.registerReceiver(myOperationBro,new IntentFilter(Config.Bro.operation));
    }

    private void selectHome() {
        selectButton = rb_home_shouye;
        if (homeFragment == null) {
            homeFragment = new HomeFragment();
            getSupportFragmentManager().beginTransaction().add(R.id.layout_main_content, homeFragment).commitAllowingStateLoss();
        } else {
            showFragment(homeFragment);
        }
        hideFragment(ziXunFragment);
        hideFragment(xiaDingDanFragment);
        hideFragment(myFragment);

    }




    @Override
    protected void initData() {
    }

    @OnClick({R.id.rb_home_shouye, R.id.rb_home_zixun, R.id.rb_home_xdd,R.id.rb_home_my})
    protected void onViewClick(View v) {
        switch (v.getId()) {
            case R.id.rb_home_shouye:
                selectHome();
                break;
            case R.id.rb_home_zixun:
                ziXun();
                break;
            case R.id.rb_home_xdd:
                if(TextUtils.isEmpty(getUserId())){
                    selectButton.setChecked(true);
                    STActivity(LoginActivity.class);
                    return;
                }
                xiaDan();
                break;
            case R.id.rb_home_my:
                if(TextUtils.isEmpty(getUserId())){
                    selectButton.setChecked(true);
                    STActivity(LoginActivity.class);
                    return;
                }
                selectMy();
                break;
        }
    }

    private void ziXun() {
        selectButton = rb_home_zixun;
        if (ziXunFragment == null) {
            ziXunFragment = new ZiXunFragment();
            getSupportFragmentManager().beginTransaction().add(R.id.layout_main_content, ziXunFragment).commitAllowingStateLoss();
        } else {
            showFragment(ziXunFragment);
        }
        hideFragment(homeFragment);
        hideFragment(xiaDingDanFragment);
        hideFragment(myFragment);
    }

    private void xiaDan() {
        selectButton = rb_home_xdd;
        if (xiaDingDanFragment == null) {
            xiaDingDanFragment = new XiaDingDanFragment();
            getSupportFragmentManager().beginTransaction().add(R.id.layout_main_content, xiaDingDanFragment).commitAllowingStateLoss();
        } else {
            showFragment(xiaDingDanFragment);
        }
        hideFragment(ziXunFragment);
        hideFragment(homeFragment);
        hideFragment(myFragment);
    }

    public void selectMy(){
        selectButton = rb_home_my;
        if (myFragment == null) {
            myFragment = new MyFragment();
            getSupportFragmentManager().beginTransaction().add(R.id.layout_main_content, myFragment).commitAllowingStateLoss();
        } else {
            showFragment(myFragment);
        }

        hideFragment(ziXunFragment);
        hideFragment(xiaDingDanFragment);
        hideFragment(homeFragment);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (localBroadcastManager != null) {
            localBroadcastManager.unregisterReceiver(myOperationBro);
        }
    }
    private long mExitTime;

    @Override
    public void onBackPressed() {
        if ((System.currentTimeMillis() - mExitTime) > 1500) {
            showToastS("再按一次退出程序");
            mExitTime = System.currentTimeMillis();
        } else {
            super.onBackPressed();
        }
    }



}
