package com.zhizhong.farmer.module.my.adapter;

import android.content.Context;

import com.github.baseclass.adapter.LoadMoreAdapter;
import com.github.baseclass.adapter.LoadMoreViewHolder;
import com.zhizhong.farmer.R;
import com.zhizhong.farmer.module.my.network.response.MyFarmerObj;

/**
 * Created by administartor on 2017/8/2.
 */

public class MyFarmerAdapter extends LoadMoreAdapter<MyFarmerObj> {
    public MyFarmerAdapter(Context mContext, int layoutId, int pageSize) {
        super(mContext, layoutId, pageSize);
    }
    @Override
    public void bindData(LoadMoreViewHolder holder, int i, MyFarmerObj bean) {
        holder.setText(R.id.tv_my_farmer_address,bean.getAddresss()+bean.getFarmland_addresss())
                .setText(R.id.tv_my_farmer_ms,bean.getArea()+"亩")
                .setText(R.id.tv_my_farmer_phone,bean.getPhone_number())
                .setText(R.id.tv_my_farmer_name,bean.getFarmers_name())
                .setText(R.id.tv_my_farmer_zw,bean.getCrops());
    }

}
