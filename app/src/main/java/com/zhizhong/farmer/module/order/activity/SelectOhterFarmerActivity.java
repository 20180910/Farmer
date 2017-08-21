package com.zhizhong.farmer.module.order.activity;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;

import com.github.baseclass.adapter.LoadMoreAdapter;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.zhizhong.farmer.GetSign;
import com.zhizhong.farmer.R;
import com.zhizhong.farmer.base.BaseActivity;
import com.zhizhong.farmer.base.MySub;
import com.zhizhong.farmer.module.order.Constant;
import com.zhizhong.farmer.module.order.adapter.SelectOtherFarmerAdapter;
import com.zhizhong.farmer.module.order.network.ApiRequest;
import com.zhizhong.farmer.module.order.network.response.OtherFarmerObj;
import com.zhizhong.farmer.module.order.network.response.XiaDingDanObj;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by administartor on 2017/8/5.
 */

public class SelectOhterFarmerActivity extends BaseActivity implements LoadMoreAdapter.OnLoadMoreListener{
    @BindView(R.id.rv_select_other_farmer)
    RecyclerView rv_select_other_farmer;

    SelectOtherFarmerAdapter adapter;
    private String crops;

    private List<OtherFarmerObj> otherFarmerList;

    @Override
    protected int getContentView() {
        setAppTitle("选择其他农户");
        return R.layout.act_select_other_farmer;
    }

    @Override
    protected void initView() {
        crops=getIntent().getStringExtra(Constant.IParam.crops);

        String str = (String) getIntent().getSerializableExtra(Constant.IParam.otherFarmerBean);
        if(!TextUtils.isEmpty(str)){
            otherFarmerList = new Gson().fromJson(str,new TypeToken<List<OtherFarmerObj>>(){}.getType());
        }

        adapter=new SelectOtherFarmerAdapter(mContext,R.layout.item_other_farmer,pageSize);
        adapter.setCrops(crops);

        rv_select_other_farmer.setLayoutManager(new LinearLayoutManager(mContext));
        rv_select_other_farmer.setAdapter(adapter);

    }

    @Override
    protected void initData() {
        showProgress();
        getData(1,false);
    }

    private void getData(int page, boolean isLoad) {
        Map<String,String> map=new HashMap<String,String>();
        map.put("user_id",getUserId());
        map.put("crops",crops);
        map.put("sign", GetSign.getSign(map));
        addSubscription(ApiRequest.getOtherFarmerList(map).subscribe(new MySub<List<OtherFarmerObj>>(mContext,pl_load) {
            @Override
            public void onMyNext(List<OtherFarmerObj> list) {
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


    @OnClick({R.id.tv_other_farmer_commit})
    protected void onViewClick(View v) {
        switch (v.getId()){
            case R.id.tv_other_farmer_commit:
                List<OtherFarmerObj> list = adapter.getList();
                if(isEmpty(list)){
                    showMsg("请选择农户");
                    return;
                }
                XiaDingDanObj obj=new XiaDingDanObj();
                XiaDingDanObj.BodyBean bodyBean ;
                List<XiaDingDanObj.BodyBean>orderList=new ArrayList<>();
                for (int i = 0; i < list.size(); i++) {
                    OtherFarmerObj item = list.get(i);
                    if(item.isSelect()&&item.isSelectHaiChong()){
                        bodyBean= new XiaDingDanObj.BodyBean();
                        bodyBean.setFarmer_id(item.getId());
                        bodyBean.setMs(item.getArea());
                        bodyBean.setName(item.getFarmers_name());
                        bodyBean.setDiseases_pests(item.getHaiChong());
                        if(item.isNeedZhuJi()){
                            bodyBean.setZhuji("需要");
                        }else{
                            bodyBean.setZhuji("自购");
                        }
                        if(item.isNeedWeiFei()){
                            bodyBean.setWeifei("需要");
                        }else{
                            bodyBean.setWeifei("自购");
                        }
                        if(item.isNeedNongYao()){
                            bodyBean.setLongyao("需要");
                        }else{
                            bodyBean.setLongyao("自购");
                        }
                        orderList.add(bodyBean);
                    }
                }
                obj.setBody(orderList);
                if(orderList.size()==0||orderList==null){
                    showMsg("请选择农户和病虫害");
                    return;
                }
                Intent intent=new Intent();
                intent.putExtra(Constant.IParam.otherFarmerBean,new Gson().toJson(list));
                intent.putExtra(Constant.IParam.xiaDanBean,obj);
                setResult(RESULT_OK,intent);
                finish();
                break;
        }
    }

    @Override
    public void loadMore() {
        getData(pageNum,true);
    }
}
