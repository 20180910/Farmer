package com.zhizhong.farmer.module.order.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.BottomSheetDialog;
import android.support.v4.util.SparseArrayCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bigkoo.pickerview.TimePickerView;
import com.github.androidtools.DateUtils;
import com.github.androidtools.inter.MyOnClickListener;
import com.github.baseclass.adapter.LoadMoreAdapter;
import com.github.baseclass.adapter.LoadMoreViewHolder;
import com.github.baseclass.rx.MySubscriber;
import com.github.baseclass.rx.RxBus;
import com.github.baseclass.view.Loading;
import com.github.customview.MyCheckBox;
import com.github.customview.MyEditText;
import com.zhizhong.farmer.GetSign;
import com.zhizhong.farmer.R;
import com.zhizhong.farmer.base.BaseFragment;
import com.zhizhong.farmer.base.BaseObj;
import com.zhizhong.farmer.base.MySub;
import com.zhizhong.farmer.module.home.event.GetPhoneEvent;
import com.zhizhong.farmer.module.my.activity.MyDataActivity;
import com.zhizhong.farmer.module.my.activity.MyFarmerActivity;
import com.zhizhong.farmer.module.my.activity.MyOrderListActivity;
import com.zhizhong.farmer.module.my.activity.MyVouchersActivity;
import com.zhizhong.farmer.module.my.network.response.VouchersObj;
import com.zhizhong.farmer.module.order.Constant;
import com.zhizhong.farmer.module.order.network.ApiRequest;
import com.zhizhong.farmer.module.order.network.request.XiaDingDanItem;
import com.zhizhong.farmer.module.order.network.response.HaiChongObj;
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
    @BindView(R.id.et_xiadan_zhuan_chang_num)
    EditText et_xiadan_zhuan_chang_num;

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
    @BindView(R.id.tv_xiadan_fang_zhi)
    TextView tv_xiadan_fang_zhi;
    @BindView(R.id.cb_xiadan_nong_yao)
    MyCheckBox cb_xiadan_nong_yao;
    @BindView(R.id.cb_xiadan_zhu_ji)
    MyCheckBox cb_xiadan_zhu_ji;
    @BindView(R.id.cb_xiadan_wei_fei)
    MyCheckBox cb_xiadan_wei_fei;
    @BindView(R.id.et_xiadan_dk)
    MyEditText et_xiadan_dk;
    @BindView(R.id.et_xiadan_zhang_ai_wu)
    MyEditText et_xiadan_zhang_ai_wu;
    @BindView(R.id.cb_xiadan_bao_chi)
    MyCheckBox cb_xiadan_bao_chi;
    @BindView(R.id.cb_xiadan_bao_zhu)
    MyCheckBox cb_xiadan_bao_zhu;
    @BindView(R.id.cb_xiadan_chong_dian)
    MyCheckBox cb_xiadan_chong_dian;
    @BindView(R.id.cb_xiadan_qu_shui)
    MyCheckBox cb_xiadan_qu_shui;


    private Date startDate, endDate;
    private List<OrderDefaultDataObj.ListBean> zuoWuList;
    private List<OtherFarmerObj> otherFarmerList;
    private String couponsId = "0";
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
    private OrderDefaultDataObj orderDefaultDataObj;
    private void getData() {
        addSubscription(ApiRequest.getOrderDefaultData(getUserId(), getSign()).subscribe(new MySub<OrderDefaultDataObj>(mContext, pl_load) {
            @Override
            public void onMyNext(OrderDefaultDataObj obj) {
                orderDefaultDataObj = obj;
                tv_xia_order_name.setText(obj.getFarmer_name());
                tv_xia_order_phone.setText(obj.getMobile());
                tv_xia_order_weizhi.setText(obj.getAddresss());
                zuoWuList = obj.getList();
                if (TextUtils.isEmpty(getSStr(tv_xia_order_phone))) {
                    showMsg("请完善联系方式");
                    return;
                }
                if(obj.getPesticide()==null){
                    cb_xiadan_nong_yao.setText("无");
                    cb_xiadan_nong_yao.setEnabled(false);
                }else{
                    cb_xiadan_nong_yao.setText(obj.getPesticide().getTitle());
                    cb_xiadan_nong_yao.setEnabled(true);
                }
                if(obj.getAdditives()==null){
                    cb_xiadan_zhu_ji.setText("无");
                    cb_xiadan_zhu_ji.setEnabled(false);
                }else{
                    cb_xiadan_zhu_ji.setText(obj.getAdditives().getTitle());
                    cb_xiadan_zhu_ji.setEnabled(true);
                }
                if(obj.getFertilizer()==null){
                    cb_xiadan_wei_fei.setText("无");
                    cb_xiadan_wei_fei.setEnabled(false);
                }else{
                    cb_xiadan_wei_fei.setText(obj.getFertilizer().getTitle());
                    cb_xiadan_wei_fei.setEnabled(true);
                }

            }
        }));
    }

    @OnClick({R.id.tv_xiadan_fang_zhi, R.id.ll_xiadan_vouchers, R.id.tv_xdd_commit, R.id.ll_xiadan_select_other_farmer, R.id.tv_xiadan_zuowu, R.id.tv_xiadan_start_time, R.id.tv_xiadan_end_time})
    protected void onViewClick(View v) {
        switch (v.getId()) {
            case R.id.ll_xiadan_select_other_farmer:
                if (TextUtils.isEmpty(getSStr(tv_xiadan_zuowu))) {
                    showMsg("请先选择作物");
                    return;
                } else if (TextUtils.isEmpty(getSStr(tv_xia_order_weizhi))) {
                    showMsg("请完善位置信息");
                    STActivityForResult(MyDataActivity.class, 300);
                    return;
                }
                Intent intent = new Intent();
                intent.putExtra(Constant.IParam.crops, getSStr(tv_xiadan_zuowu));
                intent.putExtra(Constant.IParam.type, 2);//2 根据作物查询农户，1查询全部农户
                STActivityForResult(intent, MyFarmerActivity.class, 100);
                break;
            case R.id.ll_xiadan_vouchers:
                Intent intentVoucher = new Intent(Constant.IParam.select_voucher);
                STActivityForResult(intentVoucher, MyVouchersActivity.class, 1000);
                break;
            case R.id.tv_xiadan_fang_zhi:
                if (TextUtils.isEmpty(getSStr(tv_xiadan_zuowu))) {
                    showMsg("请先选择作物");
                    return;
                }
                getChongHai();
                break;
            case R.id.tv_xiadan_zuowu:
                getZuoWu();
                break;
            case R.id.tv_xdd_commit:
                if (TextUtils.isEmpty(getSStr(tv_xia_order_phone))) {
                    showMsg("请完善联系方式");
                    return;
                } else if (TextUtils.isEmpty(getSStr(tv_xia_order_farmer))||xiaDingDanItem==null) {
                    showMsg("请选择其他农户");
                    return;
                } else if (TextUtils.isEmpty(getSStr(tv_xiadan_fang_zhi))) {
                    showMsg("请选择防治内容");
                    return;
                }else if (startDate == null) {
                    showMsg("请选择作业日期");
                    return;
                } else if (endDate == null) {
                    showMsg("请选择结束日期");
                    return;
                }else if (TextUtils.isEmpty(getSStr(et_xiadan_dk))) {
                    showMsg("请填写地况");
                    return;
                }else if (TextUtils.isEmpty(getSStr(et_xiadan_zhang_ai_wu))) {
                    showMsg("请填写障碍物");
                    return;
                }else if (TextUtils.isEmpty(getSStr(et_xiadan_zhuan_chang_num))) {
                    showMsg("请填写转场次数");
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

    private void getChongHai() {
        Loading.show(mContext);
        ApiRequest.getHaiChongList(getSStr(tv_xiadan_zuowu), GetSign.getSign("crops", getSStr(tv_xiadan_zuowu))).subscribe(new MySub<List<HaiChongObj>>(mContext) {
            @Override
            public void onMyNext(List<HaiChongObj> list) {
                getChongHaiList(list);
            }
        });
    }

    private void getChongHaiList(List<HaiChongObj> list) {
        BottomSheetDialog dialog = new BottomSheetDialog(mContext);
        SparseArrayCompat<HaiChongObj> sparseArray = new SparseArrayCompat();
        adapter = new LoadMoreAdapter<HaiChongObj>(mContext, R.layout.item_chonghai_other_farmer, 0) {
            @Override
            public void bindData(LoadMoreViewHolder holder, int i, final HaiChongObj bean) {
                holder.setText(R.id.tv_chonghai_name, bean.getTitle());
                TextView tv_chonghai_name = holder.getTextView(R.id.tv_chonghai_name);
                ImageView iv_chonghai_img = holder.getImageView(R.id.iv_chonghai_img);
                iv_chonghai_img.setVisibility(View.VISIBLE);
                if (sparseArray.get(i) == null) {
                    iv_chonghai_img.setImageResource(R.drawable.img17);
                } else {
                    iv_chonghai_img.setImageResource(R.drawable.img16);
                }
                tv_chonghai_name.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (sparseArray.get(i) == null) {
                            sparseArray.put(i, bean);
                            iv_chonghai_img.setImageResource(R.drawable.img16);
                        } else {
                            sparseArray.remove(i);
                            iv_chonghai_img.setImageResource(R.drawable.img17);
                        }
                    }
                });
            }
        };
        adapter.setList(list);
        View chonghaiView = LayoutInflater.from(mContext).inflate(R.layout.popu_chonghai_other_farmer, null);
        RecyclerView rv_chonghai = chonghaiView.findViewById(R.id.rv_chonghai);
        rv_chonghai.setLayoutManager(new LinearLayoutManager(mContext));
        rv_chonghai.setAdapter(adapter);
        TextView tv_chonghai_cancle = chonghaiView.findViewById(R.id.tv_chonghai_cancle);
        TextView tv_chonghai_sure = chonghaiView.findViewById(R.id.tv_chonghai_sure);
        tv_chonghai_sure.setVisibility(View.VISIBLE);
        tv_chonghai_cancle.setOnClickListener(new MyOnClickListener() {
            @Override
            protected void onNoDoubleClick(View view) {
                dialog.dismiss();
            }
        });
        tv_chonghai_sure.setOnClickListener(new MyOnClickListener() {
            @Override
            protected void onNoDoubleClick(View view) {
                dialog.dismiss();
                if (sparseArray.size() > 0) {
                    StringBuffer sb = new StringBuffer();
                    for (int i = 0; i < sparseArray.size(); i++) {
                        if (sparseArray.get(i) != null) {
                            sb.append(sparseArray.get(i).getTitle() + ",");
                        }
                    }
                    String title = sb.toString();
                    int indexOf = sb.lastIndexOf(",");
                    if (indexOf != -1) {
                        title = sb.deleteCharAt(indexOf).toString();
                    }
                    tv_xiadan_fang_zhi.setText(title);
                }
            }
        });
        dialog.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        dialog.setContentView(chonghaiView);
        dialog.show();
    }

    private void xiaDingDan() {
        showLoading();
        Map<String, String> map = new HashMap<String, String>();
        map.put("user_id", getUserId());
        map.put("coupons_id", couponsId);
        map.put("crops", getSStr(tv_xiadan_zuowu));
        map.put("area", getSStr(tv_xiadan_ms));
        map.put("diseasespest", getSStr(tv_xiadan_fang_zhi));
        map.put("begin_time", getSStr(tv_xiadan_start_time));
        map.put("end_time", getSStr(tv_xiadan_end_time));

        map.put("pesticide_id", cb_xiadan_nong_yao.isChecked()?orderDefaultDataObj.getPesticide().getId()+"":"0");//农药ID(不用传0)
        map.put("additives_id", cb_xiadan_zhu_ji.isChecked()?orderDefaultDataObj.getAdditives().getId()+"":"0");//助剂ID(不用传0)
        map.put("fertilizer_id",cb_xiadan_wei_fei.isChecked()?orderDefaultDataObj.getFertilizer().getId()+"":"0");//微肥ID(不用传0)

        map.put("condition", getSStr(et_xiadan_dk));//地况
        map.put("obstacles", getSStr(et_xiadan_zhang_ai_wu));//障碍物

        map.put("is_andy", cb_xiadan_bao_chi.isChecked()?"1":"0");//包吃(1是 0否)
        map.put("is_encase", cb_xiadan_bao_zhu.isChecked()?"1":"0");//包住(1是 0否)
        map.put("is_rechargeable", cb_xiadan_chong_dian.isChecked()?"1":"0");//可充电(1是 0否)
        map.put("is_getwater", cb_xiadan_qu_shui.isChecked()?"1":"0");//可取水(1是 0否)
        map.put("transitions_number", getSStr(et_xiadan_zhuan_chang_num));//转场次数

        map.put("sign", GetSign.getSign(map));
        addSubscription(ApiRequest.xiaDingDan(map, xiaDingDanItem).subscribe(new MySub<BaseObj>(mContext) {
            @Override
            public void onMyNext(BaseObj obj) {
                showMsg(obj.getMsg());
                startDate = null;
                endDate = null;
                tv_xiadan_zuowu.setText(null);
                tv_xia_order_farmer.setText(null);
                tv_xiadan_ms.setText(null);
                tv_xiadan_start_time.setText(null);
                tv_xiadan_end_time.setText(null);
                tv_xiadan_fang_zhi.setText(null);
                couponsId = "0";
                tv_xiadan_voucher.setText(null);

                et_xiadan_zhuan_chang_num.setText(null);
                et_xiadan_dk.setText(null);
                et_xiadan_zhang_ai_wu.setText(null);

                STActivity(MyOrderListActivity.class);

            }
        }));
    }

    public void setTime(int type) {
        Calendar calendar = new GregorianCalendar();
        calendar.set(Calendar.getInstance().get(Calendar.YEAR), Calendar.getInstance().get(Calendar.MONTH), Calendar.getInstance().get(Calendar.DATE));
        Calendar calendarEnd = new GregorianCalendar();
        calendarEnd.set(Calendar.getInstance().get(Calendar.YEAR), Calendar.getInstance().get(Calendar.MONTH), Calendar.getInstance().get(Calendar.DATE));
        calendarEnd.add(calendar.MONTH, 12);

        TimePickerView pvTime = new TimePickerView.Builder(mContext, new TimePickerView.OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {
                String time = DateUtils.dateToString(date);
                if (type == 0) {
                    startDate = date;
                    tv_xiadan_start_time.setText(time);
                } else {
                    endDate = date;
                    tv_xiadan_end_time.setText(time);
                }
            }
        }).setRangDate(calendar, calendarEnd).setType(new boolean[]{true, true, true, false, false, false}).build();
        //.setRange(Calendar.getInstance().get(Calendar.YEAR),Calendar.getInstance().get(Calendar.YEAR)+1)
        pvTime.show();
    }

    LoadMoreAdapter adapter;

    private void getZuoWu() {
        BottomSheetDialog zuoWuDialog = new BottomSheetDialog(mContext);
        adapter = new LoadMoreAdapter<OrderDefaultDataObj.ListBean>(mContext, R.layout.item_chonghai, 0) {
            @Override
            public void bindData(LoadMoreViewHolder holder, int i, final OrderDefaultDataObj.ListBean bean) {
                holder.setText(R.id.tv_chonghai_name, bean.getCrop_name());
                TextView tv_chonghai_name = holder.getTextView(R.id.tv_chonghai_name);
                tv_chonghai_name.setOnClickListener(new MyOnClickListener() {
                    @Override
                    protected void onNoDoubleClick(View view) {
                        zuoWuDialog.dismiss();
                        if (!bean.getCrop_name().equals(getSStr(tv_xiadan_zuowu))) {
                            tv_xia_order_farmer.setText(null);
                            tv_xiadan_zuowu.setText(bean.getCrop_name());
                            tv_xiadan_ms.setText("0");
                            xiaDingDanItem=null;
                        }

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
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case 100:
                    xiaDingDanItem = (XiaDingDanItem) data.getSerializableExtra(Constant.IParam.xiaDanBean);
                    int muShuNum = data.getIntExtra(Constant.IParam.xiaDanBeanMuShu,0);
                    int farmerCount = data.getIntExtra(Constant.IParam.xiaDanBeanCount,0);
                    tv_xia_order_farmer.setText(farmerCount+"家农户");
                    /*String str = (String) data.getSerializableExtra(Constant.IParam.otherFarmerBean);
                    otherFarmerList = new Gson().fromJson(str,new TypeToken<List<OtherFarmerObj>>(){}.getType());
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
                    tv_xiadan_ms.setText(countMS+"");*/
                    tv_xiadan_ms.setText(muShuNum+"");
                    break;
                case 1000:
                    VouchersObj voucher = (VouchersObj) data.getSerializableExtra(Constant.IParam.voucher);
                    couponsId = voucher.getCoupons_id() + "";
                    tv_xiadan_voucher.setText("抵用券" + voucher.getFace_value() + "元");
                    break;
                case 300:
                    showLoading();
                    getData();
                    break;
            }
        }
    }


}
