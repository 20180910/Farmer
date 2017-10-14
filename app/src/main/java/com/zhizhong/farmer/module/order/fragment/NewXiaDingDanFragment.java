package com.zhizhong.farmer.module.order.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.BottomSheetDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.bigkoo.pickerview.TimePickerView;
import com.github.androidtools.DateUtils;
import com.github.androidtools.inter.MyOnClickListener;
import com.github.baseclass.adapter.LoadMoreAdapter;
import com.github.baseclass.adapter.LoadMoreViewHolder;
import com.github.baseclass.rx.MySubscriber;
import com.github.baseclass.rx.RxBus;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.zhizhong.farmer.GetSign;
import com.zhizhong.farmer.R;
import com.zhizhong.farmer.base.BaseFragment;
import com.zhizhong.farmer.base.BaseObj;
import com.zhizhong.farmer.base.MySub;
import com.zhizhong.farmer.module.home.event.GetPhoneEvent;
import com.zhizhong.farmer.module.my.activity.MyDataActivity;
import com.zhizhong.farmer.module.my.activity.MyVouchersActivity;
import com.zhizhong.farmer.module.my.network.response.VouchersObj;
import com.zhizhong.farmer.module.order.Constant;
import com.zhizhong.farmer.module.order.activity.NewSelectOhterFarmerActivity;
import com.zhizhong.farmer.module.order.network.ApiRequest;
import com.zhizhong.farmer.module.order.network.request.XiaDingDanItem;
import com.zhizhong.farmer.module.order.network.response.OrderDefaultDataObj;
import com.zhizhong.farmer.module.order.network.response.OtherFarmerObj;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

import static android.app.Activity.RESULT_OK;

/**
 * Created by administartor on 2017/8/1.
 */

public class NewXiaDingDanFragment extends BaseFragment {
    @BindView(R.id.app_title)
    TextView app_title;
    @BindView(R.id.tv_xiadan_voucher)
    TextView tv_xiadan_voucher;
    @BindView(R.id.tv_xia_order_name)
    TextView tv_xia_order_name;
    @BindView(R.id.tv_xia_order_phone)
    TextView tv_xia_order_phone;

    @BindView(R.id.tv_xia_order_weizhi)
    TextView tv_xia_order_weizhi;

    @BindView(R.id.tv_xiadan_zuowu)
    TextView tv_xiadan_zuowu;
    @BindView(R.id.tv_xiadan_ms)
    TextView tv_xiadan_ms;
    @BindView(R.id.tv_xiadan_start_time)
    TextView tv_xiadan_start_time;
    @BindView(R.id.tv_xiadan_end_time)
    TextView tv_xiadan_end_time;
    @BindView(R.id.tv_xia_order_farmer)
    TextView tv_xia_order_farmer;


    private Date startDate,endDate;
    private List<OrderDefaultDataObj.ListBean> zuoWuList;
    private List<OtherFarmerObj> otherFarmerList;
    private String couponsId="0";
    private XiaDingDanItem xiaDingDanItem;

    @Override
    protected int getContentView() {
        return R.layout.frag_xia_ding_dan_new;
    }

    public static NewXiaDingDanFragment newInstance() {
        Bundle args = new Bundle();
        NewXiaDingDanFragment fragment = new NewXiaDingDanFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected void initView() {
        app_title.setText("下单");
    }

    @Override
    protected void initRxBus() {
        super.initRxBus();
        RxBus.getInstance().getEvent(GetPhoneEvent.class, new MySubscriber() {
            @Override
            public void onMyNext(Object o) {
                showLoading();
                getData();
            }
        });
    }

    @Override
    protected void initData() {
        showProgress();
        getData();
    }

    private void getData() {
        addSubscription(ApiRequest.getOrderDefaultData(getUserId(),getSign()).subscribe(new MySub<OrderDefaultDataObj>(mContext,pl_load) {
            @Override
            public void onMyNext(OrderDefaultDataObj obj) {
                tv_xia_order_name.setText(obj.getFarmer_name());
                tv_xia_order_phone.setText(obj.getMobile());
                tv_xia_order_weizhi.setText(obj.getAddresss());
                zuoWuList = obj.getList();
                if(TextUtils.isEmpty(getSStr(tv_xia_order_phone))){
                    showMsg("请完善联系方式");
                    return;
                }
            }
        }));
    }

    @OnClick({R.id.ll_xiadan_vouchers,R.id.tv_xdd_commit,R.id.ll_xiadan_select_other_farmer,R.id.tv_xiadan_zuowu,R.id.tv_xiadan_start_time,R.id.tv_xiadan_end_time})
    protected void onViewClick(View v) {
        switch (v.getId()){
            case R.id.ll_xiadan_select_other_farmer:
                if(TextUtils.isEmpty(getSStr(tv_xiadan_zuowu))){
                    showMsg("请先选择作物");
                    return;
                }else if(TextUtils.isEmpty(getSStr(tv_xia_order_weizhi))){
                    showMsg("请完善位置信息");
                    STActivityForResult(MyDataActivity.class,300);
                    return;
                }
                Intent intent=new Intent();
                intent.putExtra(Constant.IParam.crops,getSStr(tv_xiadan_zuowu));
                if(notEmpty(otherFarmerList)){
                    intent.putExtra(Constant.IParam.otherFarmerBean,new Gson().toJson(otherFarmerList));
                }
                STActivityForResult(intent,NewSelectOhterFarmerActivity.class,100);
            break;
            case R.id.ll_xiadan_vouchers:
                Intent intentVoucher=new Intent(Constant.IParam.select_voucher);
                STActivityForResult(intentVoucher,MyVouchersActivity.class,1000);
            break;
            case R.id.tv_xiadan_zuowu:
                getZuoWu();
            break;
            case R.id.tv_xdd_commit:
                if(TextUtils.isEmpty(getSStr(tv_xia_order_phone))){
                    showMsg("请完善联系方式");
                    return;
                }else if(TextUtils.isEmpty(getSStr(tv_xia_order_farmer))){
                    showMsg("请选择其他农户");
                    return;
                }else if(startDate==null){
                    showMsg("请选择作业日期");
                    return;
                }else if(endDate==null){
                    showMsg("请选择结束日期");
                    return;
                }
                xiaDingDan();
            break;
            case R.id.tv_xiadan_start_time:
                setTime(0);
            break;
            case R.id.tv_xiadan_end_time:
                setTime(1);
            break;
        }
    }

    private void xiaDingDan() {
        showLoading();
        Map<String,String> map=new HashMap<String,String>();
        map.put("user_id",getUserId());
        map.put("coupons_id", couponsId);
        map.put("crops",getSStr(tv_xiadan_zuowu));
        map.put("area",getSStr(tv_xiadan_ms));
        map.put("begin_time",getSStr(tv_xiadan_start_time));
        map.put("end_time",getSStr(tv_xiadan_end_time));
        map.put("sign", GetSign.getSign(map));
        addSubscription(ApiRequest.xiaDingDan(map,xiaDingDanItem).subscribe(new MySub<BaseObj>(mContext) {
            @Override
            public void onMyNext(BaseObj obj) {
                showMsg(obj.getMsg());
                startDate=null;
                endDate=null;
                tv_xiadan_zuowu.setText(null);
                tv_xiadan_ms.setText(null);
                tv_xiadan_start_time.setText(null);
                tv_xiadan_end_time.setText(null);
                tv_xia_order_farmer.setText(null);
                couponsId="0";
                tv_xiadan_voucher.setText(null);
            }
        }));
    }

    public void setTime(int type){
        Calendar calendar = new GregorianCalendar();
        calendar.set(Calendar.getInstance().get(Calendar.YEAR),Calendar.getInstance().get(Calendar.MONTH),Calendar.getInstance().get(Calendar.DATE));
        Calendar calendarEnd = new GregorianCalendar();
        calendarEnd.set(Calendar.getInstance().get(Calendar.YEAR),Calendar.getInstance().get(Calendar.MONTH),Calendar.getInstance().get(Calendar.DATE));
        calendarEnd.add(calendar.MONTH,12);

        TimePickerView pvTime = new TimePickerView.Builder(mContext, new TimePickerView.OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {
                String time= DateUtils.dateToString(date);
                if(type==0){
                    startDate=date;
                    tv_xiadan_start_time.setText(time);
                }else{
                    endDate=date;
                    tv_xiadan_end_time.setText(time);
                }
            }
        }).setRangDate(calendar,calendarEnd).setType(new boolean[]{true, true, true, false, false, false}).build();
        //.setRange(Calendar.getInstance().get(Calendar.YEAR),Calendar.getInstance().get(Calendar.YEAR)+1)
        pvTime.show();
    }
    LoadMoreAdapter adapter;
    private void getZuoWu() {
        BottomSheetDialog zuoWuDialog = new BottomSheetDialog(mContext);
        adapter=new LoadMoreAdapter<OrderDefaultDataObj.ListBean>(mContext, R.layout.item_chonghai,0) {
            @Override
            public void bindData(LoadMoreViewHolder holder, int i, final OrderDefaultDataObj.ListBean bean) {
                holder.setText(R.id.tv_chonghai_name,bean.getCrop_name());
                TextView tv_chonghai_name = holder.getTextView(R.id.tv_chonghai_name);
                tv_chonghai_name.setOnClickListener(new MyOnClickListener() {
                    @Override
                    protected void onNoDoubleClick(View view) {
                        zuoWuDialog.dismiss();
                        if(!bean.getCrop_name().equals(getSStr(tv_xiadan_zuowu))){
                            tv_xia_order_farmer.setText(null);
                        }
                        tv_xiadan_zuowu.setText(bean.getCrop_name());
                        tv_xiadan_ms.setText(bean.getArea()+"");
                    }
                });
            }
        };
        adapter.setList(zuoWuList);
        View zuoWuView = LayoutInflater.from(mContext).inflate(R.layout.popu_chonghai, null);
        RecyclerView rv_zuowu = zuoWuView.findViewById(R.id.rv_chonghai);
        rv_zuowu.setLayoutManager(new LinearLayoutManager(mContext));
        rv_zuowu.setAdapter(adapter);
        TextView tv_zuowu_cancle = zuoWuView.findViewById(R.id.tv_chonghai_cancle);
        tv_zuowu_cancle.setOnClickListener(new MyOnClickListener() {
            @Override
            protected void onNoDoubleClick(View view) {
                zuoWuDialog.dismiss();
            }
        });
        zuoWuDialog.setContentView(zuoWuView);
        zuoWuDialog.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        zuoWuDialog.show();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode==RESULT_OK){
            switch (requestCode){
                case 100:
                    String str = (String) data.getSerializableExtra(Constant.IParam.otherFarmerBean);
                    otherFarmerList = new Gson().fromJson(str,new TypeToken<List<OtherFarmerObj>>(){}.getType());

                    xiaDingDanItem = (XiaDingDanItem) data.getSerializableExtra(Constant.IParam.xiaDanBean);
                    List<XiaDingDanItem.BodyBean>list = xiaDingDanItem.getBody();
                    int countMS=0;
                    StringBuffer farmer=new StringBuffer();
                    for (int i = 0; i < list.size(); i++) {
                        XiaDingDanItem.BodyBean bodyBean = list.get(i);
                        countMS=countMS+bodyBean.getMs();
                        farmer.append(bodyBean.getName()+",");
                    }
                    farmer.deleteCharAt(farmer.lastIndexOf(","));
                    tv_xia_order_farmer.setText(farmer.toString());
                    tv_xiadan_ms.setText(countMS+"");
                    break;
                case 1000:
                    VouchersObj voucher= (VouchersObj) data.getSerializableExtra(Constant.IParam.voucher);
                    couponsId=voucher.getCoupons_id()+"";
                    tv_xiadan_voucher.setText("抵用券"+voucher.getFace_value()+"元");
                    break;
                case 300:
                    showLoading();
                    getData();
                    break;
            }
        }
    }


}
