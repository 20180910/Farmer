package com.zhizhong.farmer.module.my.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.github.androidtools.inter.MyOnClickListener;
import com.github.baseclass.adapter.LoadMoreAdapter;
import com.github.baseclass.adapter.LoadMoreViewHolder;
import com.github.baseclass.view.MyDialog;
import com.zhizhong.farmer.R;
import com.zhizhong.farmer.base.BaseActivity;
import com.zhizhong.farmer.base.BaseObj;
import com.zhizhong.farmer.base.MySub;
import com.zhizhong.farmer.module.my.Constant;
import com.zhizhong.farmer.module.my.network.ApiRequest;
import com.zhizhong.farmer.module.my.network.response.MyFarmerObj;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;

/**
 * Created by administartor on 2017/8/7.
 */

public class MyFarmerActivity extends BaseActivity implements LoadMoreAdapter.OnLoadMoreListener{
    @BindView(R.id.rv_my_farmer)
    RecyclerView rv_my_farmer;

    LoadMoreAdapter adapter;

    @Override
    protected int getContentView() {
        setAppTitle("我的农户");
        return R.layout.act_my_farmer;
    }

    @Override
    protected void initView() {
        adapter=new LoadMoreAdapter<MyFarmerObj>(mContext,R.layout.item_my_farmer,pageSize){
            @Override
            public void bindData(LoadMoreViewHolder holder, int position, MyFarmerObj bean) {
                holder.setText(R.id.tv_my_farmer_address,bean.getAddresss()+bean.getFarmland_addresss())
                        .setText(R.id.tv_my_farmer_ms,bean.getArea()+"亩")
                        .setText(R.id.tv_my_farmer_phone,bean.getPhone_number())
                        .setText(R.id.tv_my_farmer_name,bean.getFarmers_name())
                        .setText(R.id.tv_my_farmer_zw,bean.getCrops());

                TextView tv_my_farmer_edit = holder.getTextView(R.id.tv_my_farmer_edit);
                TextView tv_my_farmer_delete = holder.getTextView(R.id.tv_my_farmer_delete);
                tv_my_farmer_delete.setOnClickListener(new MyOnClickListener() {
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
                                deleteMyFarmer(position,bean.getId());
                            }
                        });
                        mDialog.create().show();
                    }
                });
                tv_my_farmer_edit.setOnClickListener(new MyOnClickListener() {
                    @Override
                    protected void onNoDoubleClick(View view) {
                        Intent intent=new Intent();
                        intent.putExtra("type", Constant.IParam.editFarmer);
                        intent.putExtra("id",bean.getId()+"");
                        STActivityForResult(intent,AddFarmerActivity.class,100);
                    }
                });
            }
        };
        adapter.setOnLoadMoreListener(this);
        rv_my_farmer.setLayoutManager(new LinearLayoutManager(mContext));
        rv_my_farmer.setAdapter(adapter);

        pcfl.setPtrHandler(new PtrDefaultHandler() {
            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                getData(1,false);
            }
        });
    }
    private void deleteMyFarmer(int position, int id) {
        showLoading();
        addSubscription(ApiRequest.deleteMyFarmer(id+"",getSign("mf_id",id+"")).subscribe(new MySub<BaseObj>(mContext) {
            @Override
            public void onMyNext(BaseObj obj) {
                showMsg(obj.getMsg());
                adapter.getList().remove(position);
                adapter.notifyDataSetChanged();
            }
        }));
    }
    @Override
    protected void initData() {
        showProgress();
        getData(1,false);
    }

    private void getData(int page, boolean isLoad) {
        addSubscription(ApiRequest.getMyFarmerList(getUserId(),getSign()).subscribe(new MySub<List<MyFarmerObj>>(mContext,pcfl,pl_load) {
            @Override
            public void onMyNext(List<MyFarmerObj> list) {
                if(isLoad){
                    pageNum++;
                    adapter.addList(list,true);
                }else{
                    pageNum=2;
                    adapter.setList(list,true);
                }
            }
        }));
    }

    @OnClick({R.id.tv_my_farmer_add})
    protected void onViewClick(View v) {
        switch (v.getId()){
            case R.id.tv_my_farmer_add:
                STActivityForResult(AddFarmerActivity.class,100);
            break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode==RESULT_OK){
            switch (requestCode){
                case 100:
                    showLoading();
                    getData(1,false);
                break;
            }
        }
    }

    @Override
    public void loadMore() {
        getData(pageNum,true);
    }
}
