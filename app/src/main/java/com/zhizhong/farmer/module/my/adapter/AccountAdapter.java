package com.zhizhong.farmer.module.my.adapter;

import android.content.Context;

import com.github.baseclass.adapter.LoadMoreAdapter;
import com.github.baseclass.adapter.LoadMoreViewHolder;

/**
 * Created by administartor on 2017/8/2.
 */

public class AccountAdapter extends LoadMoreAdapter {
    public AccountAdapter(Context mContext, int layoutId, int pageSize) {
        super(mContext, layoutId, pageSize);
    }
    @Override
    public void bindData(LoadMoreViewHolder loadMoreViewHolder, int i, Object o) {

    }

}
