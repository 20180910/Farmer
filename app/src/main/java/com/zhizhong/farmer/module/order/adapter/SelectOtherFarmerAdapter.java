package com.zhizhong.farmer.module.order.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetDialog;
import android.support.v4.util.SparseArrayCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

import com.github.androidtools.inter.MyOnClickListener;
import com.github.baseclass.adapter.LoadMoreAdapter;
import com.github.baseclass.adapter.LoadMoreViewHolder;
import com.github.baseclass.view.Loading;
import com.zhizhong.farmer.GetSign;
import com.zhizhong.farmer.R;
import com.zhizhong.farmer.base.MySub;
import com.zhizhong.farmer.module.order.network.ApiRequest;
import com.zhizhong.farmer.module.order.network.response.HaiChongObj;
import com.zhizhong.farmer.module.order.network.response.OtherFarmerObj;

import java.util.List;

/**
 * Created by administartor on 2017/8/5.
 */

public class SelectOtherFarmerAdapter extends LoadMoreAdapter<OtherFarmerObj> {
    LoadMoreAdapter adapter;
    private String crops;

    public String getCrops() {
        return crops;
    }

    public void setCrops(String crops) {
        this.crops = crops;
    }

    public SelectOtherFarmerAdapter(Context mContext, int layoutId, int pageSize) {
        super(mContext, layoutId, pageSize);
    }
    public void bindData(LoadMoreViewHolder holder, int i, OtherFarmerObj bean) {
        holder.setText(R.id.tv_other_farmer_name,bean.getFarmers_name())
                .setText(R.id.tv_other_farmer_phone,bean.getPhone_number());
        RadioButton rb_other_farmer_zhuji = (RadioButton) holder.getView(R.id.rb_other_farmer_zhuji);
        RadioButton rb_other_farmer_zhuji2 = (RadioButton) holder.getView(R.id.rb_other_farmer_zhuji2);
        RadioButton rb_other_farmer_weifei = (RadioButton) holder.getView(R.id.rb_other_farmer_weifei);
        RadioButton rb_other_farmer_weifei2 = (RadioButton) holder.getView(R.id.rb_other_farmer_weifei2);
        RadioButton rb_other_farmer_nongyao = (RadioButton) holder.getView(R.id.rb_other_farmer_nongyao);
        RadioButton rb_other_farmer_nongyao2 = (RadioButton) holder.getView(R.id.rb_other_farmer_nongyao2);
        rb_other_farmer_zhuji.setOnClickListener(getClickListener(0,0,i));
        rb_other_farmer_zhuji2.setOnClickListener(getClickListener(0,1,i));

        rb_other_farmer_weifei.setOnClickListener(getClickListener(1,0,i));
        rb_other_farmer_weifei2.setOnClickListener(getClickListener(1,1,i));

        rb_other_farmer_nongyao.setOnClickListener(getClickListener(2,0,i));
        rb_other_farmer_nongyao2.setOnClickListener(getClickListener(2,1,i));

        if(bean.isNeedZhuJi()){
            rb_other_farmer_zhuji.setChecked(true);
        }else{
            rb_other_farmer_zhuji2.setChecked(true);
        }
        if(bean.isNeedWeiFei()){
            rb_other_farmer_weifei.setChecked(true);
        }else{
            rb_other_farmer_weifei2.setChecked(true);
        }
        if(bean.isNeedNongYao()){
            rb_other_farmer_nongyao.setChecked(true);
        }else{
            rb_other_farmer_nongyao2.setChecked(true);
        }

        CheckBox cb_other_farmer = (CheckBox) holder.getView(R.id.cb_other_farmer);
        TextView tv_other_farmer_chong = (TextView) holder.getView(R.id.tv_other_farmer_chong);
        LinearLayout ll_other_farmer = (LinearLayout) holder.getView(R.id.ll_other_farmer);
        LinearLayout ll_other_farmer_chonghai = (LinearLayout) holder.getView(R.id.ll_other_farmer_chonghai);
        LinearLayout ll_other_farmer_yao = (LinearLayout) holder.getView(R.id.ll_other_farmer_yao);

        if(bean.isSelect()){
            showChongHai(cb_other_farmer, ll_other_farmer_chonghai);
        }else{
            hiddenChongHai(cb_other_farmer, ll_other_farmer_chonghai);
        }
        if(bean.isSelectHaiChong()){
            ll_other_farmer_yao.setVisibility(View.VISIBLE);
        }else{
            ll_other_farmer_yao.setVisibility(View.GONE);
        }

        ll_other_farmer_chonghai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Loading.show(mContext);
                ApiRequest.getHaiChongList(getCrops(), GetSign.getSign("crops",getCrops())).subscribe(new MySub<List<HaiChongObj>>(mContext) {
                    @Override
                    public void onMyNext(List<HaiChongObj> list) {
                        getChongHaiList(list,tv_other_farmer_chong,ll_other_farmer_yao,i);
                    }
                });

            }
        });

        ll_other_farmer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mList.get(i).setSelect(!mList.get(i).isSelect());
                if(mList.get(i).isSelect()){
                    showChongHai(cb_other_farmer, ll_other_farmer_chonghai);
                }else{
                    mList.get(i).setSelectHaiChong(false);
                    hiddenChongHai(cb_other_farmer, ll_other_farmer_chonghai);
                    ll_other_farmer_yao.setVisibility(View.GONE);
                    tv_other_farmer_chong.setText(null);
                }
            }
        });
    }

    @NonNull
    private View.OnClickListener getClickListener(int type,int index,int position) {
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (type){
                    case 0:
                        if(index==0){
                            mList.get(position).setNeedZhuJi(true);
                        }else{
                            mList.get(position).setNeedZhuJi(false);
                        }
                    break;
                    case 1:
                        if(index==0){
                            mList.get(position).setNeedWeiFei(true);
                        }else{
                            mList.get(position).setNeedWeiFei(false);
                        }
                    break;
                    case 2:
                        if(index==0){
                            mList.get(position).setNeedNongYao(true);
                        }else{
                            mList.get(position).setNeedNongYao(false);
                        }
                    break;
                }
            }
        };
    }

    private void getChongHaiList(List<HaiChongObj> list,TextView tv_other_farmer_chong,LinearLayout rb_other_farmer_yao,int position) {
        BottomSheetDialog dialog = new BottomSheetDialog(mContext);
        SparseArrayCompat<HaiChongObj> sparseArray=new SparseArrayCompat();
        adapter=new LoadMoreAdapter<HaiChongObj>(mContext, R.layout.item_chonghai,0) {
            @Override
            public void bindData(LoadMoreViewHolder holder, int i,final HaiChongObj bean) {
                holder.setText(R.id.tv_chonghai_name,bean.getTitle());
                TextView tv_chonghai_name = holder.getTextView(R.id.tv_chonghai_name);
                ImageView iv_chonghai_img = holder.getImageView(R.id.iv_chonghai_img);
                iv_chonghai_img.setVisibility(View.VISIBLE);
                if(sparseArray.get(i)==null){
                    iv_chonghai_img.setImageResource(R.drawable.img17);
                }else{
                    iv_chonghai_img.setImageResource(R.drawable.img16);
                }
                tv_chonghai_name.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if(sparseArray.get(i)==null){
                            sparseArray.put(i,bean);
                            iv_chonghai_img.setImageResource(R.drawable.img16);
                        }else{
                            sparseArray.remove(i);
                            iv_chonghai_img.setImageResource(R.drawable.img17);
                        }
                        /*dialog.dismiss();
                        tv_other_farmer_chong.setText(bean.getTitle());
                        SelectOtherFarmerAdapter.this.mList.get(position).setSelectHaiChong(true);
                        SelectOtherFarmerAdapter.this.mList.get(position).setHaiChong(bean.getTitle());
                        rb_other_farmer_yao.setVisibility(View.VISIBLE);*/
                    }
                });
            }
        };
        adapter.setList(list);
        View chonghaiView = LayoutInflater.from(mContext).inflate(R.layout.popu_chonghai, null);
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
                if(sparseArray.size()>0){
                    StringBuffer sb=new StringBuffer();
                    for (int i = 0; i < sparseArray.size(); i++) {
                        if(sparseArray.get(i)!=null){
                            sb.append(sparseArray.get(i).getTitle()+",");
                        }
                    }
                    String title = sb.deleteCharAt(sb.lastIndexOf(",")).toString();
                    tv_other_farmer_chong.setText(title);
                    SelectOtherFarmerAdapter.this.mList.get(position).setSelectHaiChong(true);
                    SelectOtherFarmerAdapter.this.mList.get(position).setHaiChong(title);
                    rb_other_farmer_yao.setVisibility(View.VISIBLE);
                }

            }
        });
        dialog.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        dialog.setContentView(chonghaiView);
        dialog.show();
    }

    private void hiddenChongHai(CheckBox cb_other_farmer, LinearLayout ll_other_farmer_chonghai) {
        cb_other_farmer.setChecked(false);
        ll_other_farmer_chonghai.setVisibility(View.GONE);
    }

    private void showChongHai(CheckBox cb_other_farmer, LinearLayout ll_other_farmer_chonghai) {
        cb_other_farmer.setChecked(true);
        ll_other_farmer_chonghai.setVisibility(View.VISIBLE);
    }


}
