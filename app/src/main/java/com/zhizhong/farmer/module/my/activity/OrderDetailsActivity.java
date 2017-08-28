package com.zhizhong.farmer.module.my.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.design.widget.BottomSheetDialog;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.github.androidtools.inter.MyOnClickListener;
import com.github.baseclass.view.MyDialog;
import com.zhizhong.farmer.R;
import com.zhizhong.farmer.base.BaseActivity;
import com.zhizhong.farmer.base.MySub;
import com.zhizhong.farmer.module.my.bean.OrderBean;
import com.zhizhong.farmer.module.my.bean.WXPay;
import com.zhizhong.farmer.module.my.Constant;
import com.zhizhong.farmer.module.my.network.ApiRequest;
import com.zhizhong.farmer.module.my.network.response.OrderDetailObj;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by administartor on 2017/8/3.
 */

public class OrderDetailsActivity extends BaseActivity {


    @BindView(R.id.tv_order_detail_name)
    TextView tv_order_detail_name;
    @BindView(R.id.tv_order_detail_phone)
    TextView tv_order_detail_phone;
    @BindView(R.id.tv_order_detail_address)
    TextView tv_order_detail_address;
    @BindView(R.id.tv_order_detail_type)
    TextView tv_order_detail_type;
    @BindView(R.id.tv_order_detail_no)
    TextView tv_order_detail_no;
    @BindView(R.id.tv_order_detail_date)
    TextView tv_order_detail_date;
    @BindView(R.id.tv_order_detail_zffs)
    TextView tv_order_detail_zffs;
    @BindView(R.id.tv_order_detail_zf_status)
    TextView tv_order_detail_zf_status;
    @BindView(R.id.tv_order_detail_fpxx)
    TextView tv_order_detail_fpxx;
    @BindView(R.id.tv_order_detail_zw)
    TextView tv_order_detail_zw;
    @BindView(R.id.tv_order_detail_dj)
    TextView tv_order_detail_dj;
    @BindView(R.id.tv_order_detail_ms)
    TextView tv_order_detail_ms;
    @BindView(R.id.tv_order_detail_zj)
    TextView tv_order_detail_zj;
    @BindView(R.id.tv_order_detail_time)
    TextView tv_order_detail_time;
    @BindView(R.id.tv_order_detail_zccs)
    TextView tv_order_detail_zccs;
    @BindView(R.id.tv_order_detail_zcsm)
    TextView tv_order_detail_zcsm;
    @BindView(R.id.tv_order_detail_dk)
    TextView tv_order_detail_dk;
    @BindView(R.id.tv_order_detail_zaw)
    TextView tv_order_detail_zaw;
    @BindView(R.id.tv_order_detail_remark)
    TextView tv_order_detail_remark;
    @BindView(R.id.tv_order_detail_tel)
    TextView tv_order_detail_tel;
    @BindView(R.id.tv_order_detail_youhui)
    TextView tv_order_detail_youhui;
    @BindView(R.id.ll_order_detail_youhui)
    LinearLayout ll_order_detail_youhui;

    @BindView(R.id.ll_order_detail_kefu)
    LinearLayout ll_order_detail_kefu;
    @BindView(R.id.ll_order_detail_commit)
    LinearLayout ll_order_detail_commit;

    private double totalPrice;
    private String orderNo;
    private int type;
    private OrderBean orderBean;
    @Override
    protected int getContentView() {
        setAppTitle("订单详情");
        return R.layout.act_order_detail;
    }

    @Override
    protected void initView() {
        orderNo = getIntent().getStringExtra(Constant.orderNo);
        type = getIntent().getIntExtra(Constant.type, Constant.type_0);
    }

    @Override
    protected void initData() {
        showProgress();
        getData();
    }

    private void getData() {
        addSubscription(ApiRequest.getOrderDetail(orderNo, getSign("order_no", orderNo)).subscribe(new MySub<OrderDetailObj>(mContext, pl_load) {
            @Override
            public void onMyNext(OrderDetailObj obj) {
                if(type== Constant.type_2){
                    double total=Double.parseDouble(obj.getTotal_price());
                    orderBean=new OrderBean();
                    orderBean.body="飞农宝订单";
                    orderBean.nonceStr=getRnd();
                    orderBean.out_trade_no=obj.getOrder_no();
//                    orderBean.totalFee=2*100/2;
                    orderBean.totalFee=total*100/2;
                    orderBean.IP="192.168.0.1";
                    totalPrice = total;
                }

                tv_order_detail_name.setText(obj.getFarmer_name());
                tv_order_detail_phone.setText(obj.getFarmer_phone());
                tv_order_detail_address.setText(obj.getSite());
                tv_order_detail_type.setText(obj.getOrder_status());
                tv_order_detail_no.setText(obj.getOrder_no());
                tv_order_detail_date.setText(obj.getAdd_time());
                tv_order_detail_zffs.setText(obj.getPay_way());
                tv_order_detail_zf_status.setText(obj.getPay_status());
                tv_order_detail_fpxx.setText(obj.getPay_message());
                tv_order_detail_zw.setText(obj.getCrops());
                tv_order_detail_dj.setText(obj.getPrice());
                tv_order_detail_ms.setText(obj.getArea());
                tv_order_detail_zj.setText(obj.getTotal_price() + "元");
                tv_order_detail_time.setText(obj.getRequest_time());
                tv_order_detail_zccs.setText(obj.getTransitions_number());
                tv_order_detail_zcsm.setText(obj.getTransitions_instructions());
                tv_order_detail_dk.setText(obj.getCondition());
                tv_order_detail_zaw.setText(obj.getObstacles());
                tv_order_detail_remark.setText(obj.getRemark());
                tv_order_detail_tel.setText(obj.getKefu_phone());
                tv_order_detail_youhui.setText("-¥" + obj.getYouhui());
                if (type == Constant.type_2) {
                    ll_order_detail_kefu.setVisibility(View.GONE);

                    ll_order_detail_youhui.setVisibility(View.VISIBLE);
                    ll_order_detail_commit.setVisibility(View.VISIBLE);
                } else {
                    ll_order_detail_kefu.setVisibility(View.VISIBLE);

                    ll_order_detail_youhui.setVisibility(View.GONE);
                    ll_order_detail_commit.setVisibility(View.GONE);


                    ll_order_detail_kefu.setOnClickListener(new MyOnClickListener() {
                        @Override
                        protected void onNoDoubleClick(View view) {
                            MyDialog.Builder mDialog = new MyDialog.Builder(mContext);
                            mDialog.setMessage("是否确定拨打客服热线?");
                            mDialog.setNegativeButton(new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            });
                            mDialog.setPositiveButton(new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                    Intent callIntent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + obj.getKefu_phone()));
                                    startActivity(callIntent);
                                }
                            });
                            mDialog.create().show();

                        }
                    });
                }

            }
        }));
    }

    @OnClick({R.id.tv_order_detail_commit})
    protected void onViewClick(View v) {
        switch (v.getId()) {
            case R.id.tv_order_detail_commit:
                selectPay();
                break;
        }
    }

    private void selectPay() {
        BottomSheetDialog payDialog = new BottomSheetDialog(mContext);
        payDialog.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        payDialog.setCancelable(false);
        payDialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                if(payDialog.isShowing()&&keyCode== KeyEvent.KEYCODE_BACK){
                    payDialog.dismiss();
                    return true;
                }
                return false;
            }
        });
        View payView = LayoutInflater.from(mContext).inflate(R.layout.popu_select_pay, null);
        ImageView iv_pay_cancle = payView.findViewById(R.id.iv_pay_cancle);
        iv_pay_cancle.setOnClickListener(new MyOnClickListener() {
            @Override
            protected void onNoDoubleClick(View view) {
                payDialog.dismiss();
            }
        });
        TextView tv_pay_total = payView.findViewById(R.id.tv_pay_total);
        TextView tv_pay_money = payView.findViewById(R.id.tv_pay_money);
        tv_pay_total.setText("¥" + totalPrice);
        tv_pay_money.setText("¥" + totalPrice / 2);
        RadioGroup rg_select_pay = payView.findViewById(R.id.rg_select_pay);
        TextView tv_pay_commit = payView.findViewById(R.id.tv_pay_commit);
        tv_pay_commit.setOnClickListener(new MyOnClickListener() {
            @Override
            protected void onNoDoubleClick(View view) {
                payDialog.dismiss();
                int checkedRadioButtonId = rg_select_pay.getCheckedRadioButtonId();
                switch (checkedRadioButtonId) {
                    case R.id.rb_pay_zhifubao:
                        zhiFuBaoPay();
                        break;
                    case R.id.rb_pay_weixin:
                        weiXinPay();
                        break;
                    case R.id.rb_pay_online:
                        onLinePay();
                        break;
                }
            }


        });
        payDialog.setContentView(payView);
        payDialog.show();
    }

    private void onLinePay() {

    }

    private void zhiFuBaoPay() {

    }

    private void weiXinPay() {
        new WXPay(mContext).pay(orderBean);
    }

}
