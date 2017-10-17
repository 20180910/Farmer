package com.zhizhong.farmer.module.my.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.github.androidtools.inter.MyOnClickListener;
import com.github.baseclass.adapter.LoadMoreAdapter;
import com.github.baseclass.adapter.LoadMoreViewHolder;
import com.github.baseclass.view.MyDialog;
import com.zhizhong.farmer.GetSign;
import com.zhizhong.farmer.R;
import com.zhizhong.farmer.base.BaseActivity;
import com.zhizhong.farmer.base.BaseObj;
import com.zhizhong.farmer.base.MySub;
import com.zhizhong.farmer.module.my.Constant;
import com.zhizhong.farmer.module.my.network.ApiRequest;
import com.zhizhong.farmer.module.my.network.request.ZuoWuItem;
import com.zhizhong.farmer.module.my.network.response.MyFarmerObj;
import com.zhizhong.farmer.module.order.network.request.XiaDingDanItem;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    @BindView(R.id.ll_my_farmer_add)
    LinearLayout ll_my_farmer_add;

    LoadMoreAdapter adapter;
    private String crops;
    private int type;

    @Override
    protected int getContentView() {
        setAppTitle("我的农户");
        setAppRightTitle("添加农户");
        return R.layout.act_my_farmer;
    }
    @Override
    protected void initView() {
        crops = getIntent().getStringExtra(com.zhizhong.farmer.module.order.Constant.IParam.crops);
        type = getIntent().getIntExtra(com.zhizhong.farmer.module.order.Constant.IParam.type,1);
        if(TextUtils.isEmpty(crops)){
            setAppTitle("我的农户");
            ll_my_farmer_add.setVisibility(View.GONE);
        }else{
            setAppTitle("选择农户");
            ll_my_farmer_add.setVisibility(View.VISIBLE);
        }
        adapter=new LoadMoreAdapter<MyFarmerObj>(mContext,R.layout.item_my_farmer,pageSize){
            @Override
            public void bindData(LoadMoreViewHolder holder, int position, MyFarmerObj bean) {
                holder.setText(R.id.tv_farmer_list_name,bean.getFarmers_name())
                        .setText(R.id.tv_farmer_list_phone,bean.getPhone_number()+"")
                        .setText(R.id.tv_farmer_list_address,bean.getFarmland_province()+""+bean.getFarmland_city()+""+bean.getFarmland_area()+""+bean.getFarmland_addresss());

                LinearLayout ll_farmer_list = (LinearLayout) holder.getView(R.id.ll_farmer_list);
                LinearLayout ll_farmer_list_check = (LinearLayout) holder.getView(R.id.ll_farmer_list_check);
                ImageView iv_farmer_list_check = holder.getImageView(R.id.iv_farmer_list_check);

                ll_farmer_list.setOnClickListener(new MyOnClickListener() {
                    @Override
                    protected void onNoDoubleClick(View view) {
                        Intent intent=new Intent();
                        intent.putExtra("type", Constant.IParam.editFarmer);
                        intent.putExtra("id",bean.getId()+"");
                        STActivityForResult(intent,AddFarmerActivity.class,100);
                    }
                });

                ll_farmer_list_check.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if(bean.isSelect()){
                            iv_farmer_list_check.setImageResource(R.drawable.img16_small_normal);
                        }else{
                            iv_farmer_list_check.setImageResource(R.drawable.img16_small);
                        }
                        mList.get(position).setSelect(!bean.isSelect());
                    }
                });
                if(TextUtils.isEmpty(crops)){
                    ll_farmer_list_check.setVisibility(View.GONE);
                }else{
                    ll_farmer_list_check.setVisibility(View.VISIBLE);
                }

                ll_farmer_list.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View view) {
                        MyDialog.Builder mDialog=new MyDialog.Builder(mContext);
                        mDialog.setMessage("是否确认删除当前农户?");
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
                        return true;
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
        Map<String,String> map=new HashMap<String,String>();
        map.put("user_id",getUserId());
        map.put("type",type+"");
        map.put("crops",crops==null?"":crops);
        map.put("page",page+"");
        map.put("pagesize",pageSize+"");
        map.put("sign", GetSign.getSign(map));
        addSubscription(ApiRequest.getMyFarmerList(map).subscribe(new MySub<List<MyFarmerObj>>(mContext,pcfl,pl_load) {
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

    @OnClick({R.id.tv_my_farmer_add,R.id.app_right_tv})
    protected void onViewClick(View v) {
        switch (v.getId()){
            case R.id.app_right_tv:
                STActivityForResult(AddFarmerActivity.class,100);
            break;
            case R.id.tv_my_farmer_add:
                if(notEmpty(adapter.getList())){
                    int count=0;
                    int cropsNum=0;
                    XiaDingDanItem item=new XiaDingDanItem();
                    List<XiaDingDanItem.BodyBean> body=new ArrayList<>();
                    for (int i = 0; i < adapter.getList().size(); i++) {
                        MyFarmerObj obj= (MyFarmerObj) adapter.getList().get(i);
                        if(obj.isSelect()){
                            for (int j = 0; j <obj.getCrops_list().size(); j++) {
                                ZuoWuItem crops = obj.getCrops_list().get(j);
                                if(crops.getCrops().equals(this.crops)){
                                    int num =  crops.getArea() ;
                                    cropsNum+=num;
                                }
                            }
                            count++;
                            XiaDingDanItem.BodyBean bodyBean = new XiaDingDanItem.BodyBean();
                            bodyBean.setFarmer_id(obj.getId());
                            body.add(bodyBean);
                        }
                    }
                    if(count>0){
                        item.setBody(body);
                        Intent intent=new Intent();
                        intent.putExtra(Constant.IParam.xiaDanBean,item);
                        intent.putExtra(Constant.IParam.xiaDanBeanMuShu,cropsNum);
                        intent.putExtra(Constant.IParam.xiaDanBeanCount,count);
                        setResult(RESULT_OK,intent);
                        finish();
                    }else{
                        showMsg("请选择农户");
                    }
                }else{
                    showMsg("请选择农户");
                }

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
