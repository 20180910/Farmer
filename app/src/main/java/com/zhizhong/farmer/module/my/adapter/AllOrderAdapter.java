package com.zhizhong.farmer.module.my.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.github.androidtools.inter.MyOnClickListener;
import com.github.baseclass.adapter.LoadMoreAdapter;
import com.github.baseclass.adapter.LoadMoreViewHolder;
import com.github.baseclass.utils.ActUtils;
import com.zhizhong.farmer.R;
import com.zhizhong.farmer.module.my.Constant;
import com.zhizhong.farmer.module.my.activity.OrderDetailsActivity;
import com.zhizhong.farmer.module.my.network.response.OrderObj;

/**
 * Created by administartor on 2017/8/2.
 */

public class AllOrderAdapter extends LoadMoreAdapter<OrderObj> {
    public AllOrderAdapter(Context mContext, int layoutId, int pageSize) {
        super(mContext, layoutId, pageSize);
    }

    @Override
    public void bindData(LoadMoreViewHolder holder, int i, OrderObj bean) {
        ImageView imageView = holder.getImageView(R.id.iv_order_img);
        Glide.with(mContext).load(bean.getPhoto()).error(R.color.c_press).into(imageView);
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
            break;
            case 1:
                tv_order_type.setText("待完善");
            break;
            case 2:
                tv_order_type.setText("待确认 ");
            break;
            case 3:
                tv_order_type.setText("待接单 ");
            break;
            case 4:
                tv_order_type.setText("已接单");
            break;
            case 5:
                tv_order_type.setText("已完成 ");
            break;
        }


    }
}
