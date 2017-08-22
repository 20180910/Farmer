package com.zhizhong.farmer.module.home.activity;

import android.content.Intent;
import android.support.design.widget.BottomSheetDialog;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;

import com.github.androidtools.inter.MyOnClickListener;
import com.github.baseclass.adapter.LoadMoreAdapter;
import com.github.baseclass.adapter.LoadMoreViewHolder;
import com.zhizhong.farmer.R;
import com.zhizhong.farmer.base.BaseActivity;
import com.zhizhong.farmer.base.MySub;
import com.zhizhong.farmer.module.home.Constant;
import com.zhizhong.farmer.module.home.network.ApiRequest;
import com.zhizhong.farmer.module.home.network.response.CityObj;
import com.zhizhong.farmer.module.home.network.response.ProvinceObj;
import com.zhizhong.farmer.tools.SpaceItemDecoration;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by administartor on 2017/8/8.
 */

public class CityActivity extends BaseActivity {
    @BindView(R.id.rv_hot_city)
    RecyclerView rv_hot_city;

    @BindView(R.id.rv_all_city)
    RecyclerView rv_all_city;

    @BindView(R.id.et_city_search)
    EditText et_city_search;

    @BindView(R.id.tv_city_select_province)
    TextView tv_city_select_province;

    LoadMoreAdapter hotCityAdapter,allCityAdapter,provinceAdapter;

    protected BottomSheetDialog provinceDialog;
    @Override
    protected int getContentView() {
        setAppTitle("选择城市");
        return R.layout.act_city;
    }

    @Override
    protected void initView() {
        hotCityAdapter =new LoadMoreAdapter<CityObj>(mContext,R.layout.item_hot_city,pageSize) {
            @Override
            public void bindData(LoadMoreViewHolder holder, int i, CityObj bean) {
                holder.setText(R.id.tv_hotcity_name,bean.getTitle());
                holder.itemView.setOnClickListener(new MyOnClickListener() {
                    @Override
                    protected void onNoDoubleClick(View view) {
                        Intent intent=new Intent();
                        intent.putExtra(Constant.IParam.city,bean.getTitle());
                        setResult(RESULT_OK,intent);
                        finish();
                    }
                });
            }
        };
        rv_hot_city.setLayoutManager(new GridLayoutManager(mContext,3));
        rv_hot_city.addItemDecoration(new SpaceItemDecoration(10));
        rv_hot_city.setAdapter(hotCityAdapter);

        allCityAdapter=new LoadMoreAdapter<CityObj>(mContext,R.layout.item_all_city,pageSize) {
            @Override
            public void bindData(LoadMoreViewHolder holder, int i, CityObj bean) {
                holder.setText(R.id.tv_city_name,bean.getTitle());
                holder.itemView.setOnClickListener(new MyOnClickListener() {
                    @Override
                    protected void onNoDoubleClick(View view) {
                        Intent intent=new Intent();
                        intent.putExtra(Constant.IParam.city,bean.getTitle());
                        setResult(RESULT_OK,intent);
                        finish();
                    }
                });
            }
        };
        rv_all_city.setLayoutManager(new LinearLayoutManager(mContext));
        rv_all_city.setAdapter(allCityAdapter);


        et_city_search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                String search = editable.toString();
                if(TextUtils.isEmpty(search)){
                    getAllCity();
                }else{
                    addSubscription(ApiRequest.getCityForSearch(search,getSign("city",search)).subscribe(new MySub<List<CityObj>>(mContext) {
                        @Override
                        public void onMyNext(List<CityObj> list) {
                            allCityAdapter.setList(list,true);
                        }
                    }));
                }

            }
        });
    }

    @Override
    protected void initData() {
        showProgress();
        getAllCity();
        getHotCity();

    }

    private void getHotCity() {
        String rnd = getRnd();
        String sign = getSign("rnd", rnd);
        addSubscription(ApiRequest.getHotCity(rnd,sign).subscribe(new MySub<List<CityObj>>(mContext) {
            @Override
            public void onMyNext(List<CityObj> list) {
                hotCityAdapter.setList(list,true);
            }
        }));
    }

    private void getAllCity() {
        String rnd = getRnd();
        String sign = getSign("rnd", rnd);
        addSubscription(ApiRequest.getAllCity(rnd,sign).subscribe(new MySub<List<CityObj>>(mContext,pl_load) {
            @Override
            public void onMyNext(List<CityObj> list) {
                allCityAdapter.setList(list,true);
            }
        }));
    }

    @OnClick({R.id.tv_city_select_province})
    protected void onViewClick(View v) {
        switch (v.getId()){
            case R.id.tv_city_select_province:
                selectProvince();
            break;
        }
    }
    private void selectProvince() {
        showLoading();
        String rnd = getRnd();
        addSubscription(ApiRequest.getProvince(rnd,getSign("rnd",rnd)).subscribe(new MySub<List<ProvinceObj>>(mContext) {
            @Override
            public void onMyNext(List<ProvinceObj> list) {
                showProvince(list);
            }
        }));
    }
    private void showProvince(List<ProvinceObj> list) {
        if (provinceDialog == null) {
            provinceAdapter=new LoadMoreAdapter<ProvinceObj>(mContext,R.layout.item_select_province,0) {
                @Override
                public void bindData(LoadMoreViewHolder holder, int position, ProvinceObj bean) {
                    TextView textView = holder.getTextView(R.id.tv_province_name);
                    textView.setText(bean.getTitle());
                    textView.setOnClickListener(new MyOnClickListener() {
                        @Override
                        protected void onNoDoubleClick(View view) {
                            provinceDialog.dismiss();
                            tv_city_select_province.setText(bean.getTitle());
                            getCityForProvince(bean.getId()+"");
                        }
                    });
                }
            };
            provinceAdapter.setList(list);
            View province= LayoutInflater.from(mContext).inflate(R.layout.popu_province,null);
            TextView tv_province_cancle = (TextView) province.findViewById(R.id.tv_province_cancle);
            tv_province_cancle.setOnClickListener(new MyOnClickListener() {
                @Override
                protected void onNoDoubleClick(View view) {
                    provinceDialog.dismiss();
                }
            });
            RecyclerView rv_province = (RecyclerView) province.findViewById(R.id.rv_province);
            rv_province.setLayoutManager(new LinearLayoutManager(mContext));
            rv_province.setAdapter(provinceAdapter);

            provinceDialog=new BottomSheetDialog(mContext);
            provinceDialog.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            provinceDialog.setCanceledOnTouchOutside(true);
            provinceDialog.setContentView(province);
        }else{

        }
        provinceDialog.show();
    }
    private void getCityForProvince(String parentId) {
        et_city_search.setText(null);
        showLoading();
        String sign = getSign("parent_id", parentId);
        addSubscription(ApiRequest.getCityForProvince(parentId,sign).subscribe(new MySub<List<CityObj>>(mContext) {
            @Override
            public void onMyNext(List<CityObj> list) {
                allCityAdapter.setList(list,true);
            }
        }));
    }
}
