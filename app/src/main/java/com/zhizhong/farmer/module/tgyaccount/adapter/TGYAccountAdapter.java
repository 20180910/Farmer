package com.zhizhong.farmer.module.tgyaccount.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.github.androidtools.ToastUtils;
import com.github.androidtools.inter.MyOnClickListener;
import com.github.baseclass.adapter.LoadMoreAdapter;
import com.github.baseclass.adapter.LoadMoreViewHolder;
import com.github.baseclass.rx.RxBus;
import com.github.baseclass.view.Loading;
import com.github.baseclass.view.MyDialog;
import com.zhizhong.farmer.GetSign;
import com.zhizhong.farmer.R;
import com.zhizhong.farmer.base.BaseObj;
import com.zhizhong.farmer.base.MySub;
import com.zhizhong.farmer.module.tgyaccount.event.DeleteTGYAccountEvent;
import com.zhizhong.farmer.module.tgyaccount.network.ApiRequest;
import com.zhizhong.farmer.module.tgyaccount.network.response.AccountObj;

/**
 * Created by administartor on 2017/8/2.
 */

public class TGYAccountAdapter extends LoadMoreAdapter<AccountObj> {
    private boolean isDelete;
    public TGYAccountAdapter(Context mContext, int layoutId, int pageSize) {
        super(mContext, layoutId, pageSize);
    }
    @Override
    public void bindData(LoadMoreViewHolder holder, int i, AccountObj bean) {
        holder.setText(R.id.tv_account_account,bean.getBank_card())
                .setText(R.id.tv_account_bank,bean.getBank_name());
        ImageView imageView = holder.getImageView(R.id.iv_account_icon);
        Glide.with(mContext).load(bean.getBank_image()).error(R.color.c_press).into(imageView);
        ImageView imageFlag = holder.getImageView(R.id.iv_account_flag);
        if(bean.getIs_default()==1){
            imageFlag.setVisibility(View.VISIBLE);
        }else{
            imageFlag.setVisibility(View.INVISIBLE);
        }
        TextView tv_account_delete = holder.getTextView(R.id.tv_account_delete);
        if(isDelete){
            tv_account_delete.setVisibility(View.VISIBLE);
            tv_account_delete.setOnClickListener(new MyOnClickListener() {
                @Override
                protected void onNoDoubleClick(View view) {
                    MyDialog.Builder mDialog=new MyDialog.Builder(mContext);
                    mDialog.setMessage("确认删除吗?");
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
                            Loading.show(mContext);
                            String accountId = bean.getId()+"";
                            ApiRequest.deleteAccount(accountId, GetSign.getSign("account_id",accountId)).subscribe(new MySub<BaseObj>(mContext) {
                                @Override
                                public void onMyNext(BaseObj obj) {
                                    ToastUtils.showToast(mContext,obj.getMsg());
                                    getList().remove(i);
                                    notifyDataSetChanged();
                                    RxBus.getInstance().post(new DeleteTGYAccountEvent(bean.getIs_default()==1));
                                }
                            });
                        }
                    });
                    mDialog.create().show();
                }
            });
        }else{
            tv_account_delete.setVisibility(View.GONE);
        }
    }

    public boolean isDelete() {
        return isDelete;
    }

    public void setDelete(boolean delete) {
        isDelete = delete;
    }
}
