package com.zhizhong.farmer.module.my.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.github.androidtools.SPUtils;
import com.github.androidtools.ToastUtils;
import com.github.androidtools.inter.MyOnClickListener;
import com.github.baseclass.adapter.LoadMoreAdapter;
import com.github.baseclass.adapter.LoadMoreViewHolder;
import com.github.baseclass.rx.RxBus;
import com.github.baseclass.utils.ActUtils;
import com.github.baseclass.view.Loading;
import com.github.baseclass.view.MyDialog;
import com.zhizhong.farmer.Config;
import com.zhizhong.farmer.GetSign;
import com.zhizhong.farmer.R;
import com.zhizhong.farmer.base.BaseObj;
import com.zhizhong.farmer.base.MySub;
import com.zhizhong.farmer.module.my.Constant;
import com.zhizhong.farmer.module.my.activity.OrderDetailsActivity;
import com.zhizhong.farmer.module.my.event.GetOrderEvent;
import com.zhizhong.farmer.module.my.network.ApiRequest;
import com.zhizhong.farmer.module.my.network.response.OrderObj;

import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by administartor on 2017/8/2.
 */

public class AllOrderAdapter extends LoadMoreAdapter<OrderObj> {
    public AllOrderAdapter(Context mContext, int layoutId, int pageSize) {
        super(mContext, layoutId, pageSize);
    }

    @Override
    public void bindData(LoadMoreViewHolder holder, int i, OrderObj bean) {
        CircleImageView imageView = (CircleImageView) holder.getView(R.id.civ_order_img);
        Glide.with(mContext).load(bean.getPhoto()).error(R.drawable.people).into(imageView);
        holder.setText(R.id.tv_order_name,bean.getFarmer_name())
                .setText(R.id.tv_order_date,bean.getAdd_time())
                .setText(R.id.tv_order_time,bean.getRequest_time())
                .setText(R.id.tv_order_address,bean.getAddress())
                .setText(R.id.tv_order_dq,bean.getRegion())
                .setText(R.id.tv_order_nzw,bean.getCrops())
                .setText(R.id.tv_order_dj,bean.getPrice())
                .setText(R.id.tv_order_ms,bean.getArea())
                .setText(R.id.tv_order_no,bean.getNh_order_no());
        TextView tv_order_type = holder.getTextView(R.id.tv_order_type);
        TextView tv_order_type_complete = holder.getTextView(R.id.tv_order_type_complete);
        tv_order_type_complete.setOnClickListener(new MyOnClickListener() {
            @Override
            protected void onNoDoubleClick(View view) {
                MyDialog.Builder mDialog=new MyDialog.Builder(mContext);
                mDialog.setMessage("是否确认完成?");
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
                        sureComplete();
                    }
                    private void sureComplete() {
                        Loading.show(mContext);
                        Map<String,String> map=new HashMap<String,String>();
                        map.put("user_id", SPUtils.getPrefString(mContext, Config.user_id,null));
                        map.put("order_no",bean.getNh_order_no());
                        map.put("sign", GetSign.getSign(map));

                        ApiRequest.sureOrder(map).subscribe(new MySub<BaseObj>(mContext) {
                            @Override
                            public void onMyNext(BaseObj obj) {
                                ToastUtils.showToast(mContext,obj.getMsg());
                                RxBus.getInstance().post(new GetOrderEvent());
                            }
                        });
                    }
                });
                mDialog.create().show();
            }
        });
        holder.itemView.setOnClickListener(new MyOnClickListener() {
            @Override
            protected void onNoDoubleClick(View view) {
                Intent intent=new Intent();
                intent.putExtra(Constant.orderNo,bean.getNh_order_no());
                intent.putExtra(Constant.type,bean.getNh_order_status());
                ActUtils.STActivity((Activity)mContext,intent,OrderDetailsActivity.class);
            }
        });
        switch (bean.getNh_order_status()){
            case 0:
                tv_order_type.setText("全部");
                tv_order_type.setTextColor(ContextCompat.getColor(mContext,R.color.blue));
                tv_order_type.setVisibility(View.VISIBLE);
                tv_order_type_complete.setVisibility(View.GONE);
            break;
            case 1:
                tv_order_type.setText("待完善");
                tv_order_type.setTextColor(ContextCompat.getColor(mContext,R.color.blue));
                tv_order_type.setVisibility(View.VISIBLE);
                tv_order_type_complete.setVisibility(View.GONE);
            break;
            case 2:
                tv_order_type.setText("待确认");
                tv_order_type.setTextColor(ContextCompat.getColor(mContext,R.color.blue));
                tv_order_type.setVisibility(View.VISIBLE);
                tv_order_type_complete.setVisibility(View.GONE);
            break;
            case 3:
                tv_order_type.setText("待接单");
                tv_order_type.setTextColor(ContextCompat.getColor(mContext,R.color.blue));
                tv_order_type.setVisibility(View.VISIBLE);
                tv_order_type_complete.setVisibility(View.GONE);
            break;
            case 4:
                tv_order_type.setText("已接单");
                tv_order_type.setTextColor(ContextCompat.getColor(mContext,R.color.blue));
                tv_order_type.setVisibility(View.VISIBLE);
                tv_order_type_complete.setVisibility(View.GONE);
            break;
            case 5:
                tv_order_type.setText("确认完成");
                tv_order_type.setTextColor(ContextCompat.getColor(mContext,R.color.blue));
                tv_order_type.setVisibility(View.GONE);
                tv_order_type_complete.setVisibility(View.VISIBLE);
            break;
            case 6:
                tv_order_type.setText("已完成");
                tv_order_type.setTextColor(ContextCompat.getColor(mContext,R.color.gray_99));
                tv_order_type.setVisibility(View.VISIBLE);
                tv_order_type_complete.setVisibility(View.GONE);
            break;
        }


    }
}
