package com.zhizhong.farmer.module.my.activity;

import android.app.ActionBar;
import android.content.DialogInterface;
import android.support.design.widget.BottomSheetDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.github.androidtools.PhoneUtils;
import com.github.androidtools.inter.MyOnClickListener;
import com.github.baseclass.adapter.BaseRecyclerAdapter;
import com.github.baseclass.adapter.LoadMoreAdapter;
import com.github.baseclass.adapter.LoadMoreViewHolder;
import com.github.baseclass.adapter.RecyclerViewHolder;
import com.github.baseclass.rx.IOCallBack;
import com.github.baseclass.view.MyDialog;
import com.github.baseclass.view.pickerview.OptionsPopupWindow;
import com.github.customview.MyEditText;
import com.github.customview.MyTextView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.zhizhong.farmer.GetSign;
import com.zhizhong.farmer.R;
import com.zhizhong.farmer.base.BaseActivity;
import com.zhizhong.farmer.base.BaseObj;
import com.zhizhong.farmer.base.MySub;
import com.zhizhong.farmer.module.my.Constant;
import com.zhizhong.farmer.module.my.bean.CityBean;
import com.zhizhong.farmer.module.my.network.ApiRequest;
import com.zhizhong.farmer.module.my.network.request.AddFarmerItem;
import com.zhizhong.farmer.module.my.network.request.ZuoWuItem;
import com.zhizhong.farmer.module.my.network.response.MyFarmerObj;
import com.zhizhong.farmer.module.my.network.response.ZuoWuObj;
import com.zhizhong.farmer.tools.StreamUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import rx.Subscriber;

/**
 * Created by administartor on 2017/8/5.
 */

public class AddFarmerActivity extends BaseActivity{
    @BindView(R.id.et_add_farmer_name)
    MyEditText et_add_farmer_name;
    @BindView(R.id.et_add_farmer_phone)
    MyEditText et_add_farmer_phone;
    @BindView(R.id.tv_add_farmer_area)
    TextView tv_add_farmer_area;
    @BindView(R.id.et_add_farmer_nongtian)
    MyEditText et_add_farmer_nongtian;
//    @BindView(R.id.tv_add_farmer_zw)
//    TextView tv_add_farmer_zw;
//    @BindView(R.id.et_add_farmer_ms)
//    MyEditText et_add_farmer_ms;
    @BindView(R.id.tv_add_farmer_commit)
    MyTextView tv_add_farmer_commit;
    @BindView(R.id.rv_add_farmer)
    RecyclerView rv_add_farmer;
    private String id;
    private String type;
    private String editOrAdd="1";

    private BaseRecyclerAdapter adapter;
    @Override
    protected int getContentView() {
        setAppTitle("添加农户");
        return R.layout.act_add_farmer;
    }

    @Override
    protected void initView() {
        type = getIntent().getStringExtra("type");
        if(Constant.IParam.editFarmer.equals(type)){
            editOrAdd="1";
            setAppTitle("编辑农户");
            tv_add_farmer_commit.setText("保存");
        }else{
            editOrAdd="2";
            setAppTitle("添加农户");
            tv_add_farmer_commit.setText("添加");
        }


        adapter=new BaseRecyclerAdapter<ZuoWuItem>(mContext,R.layout.item_add_farmer_zuo_wu) {
            @Override
            public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                RecyclerViewHolder holder = super.onCreateViewHolder(parent, viewType);
/*tv_add_farmer_item_zw
et_add_farmer_item_ms*/
                EditText et_add_farmer_item_ms = holder.getEditText(R.id.et_add_farmer_item_ms);
                et_add_farmer_item_ms.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    }
                    @Override
                    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    }
                    @Override
                    public void afterTextChanged(Editable editable) {
                        Log.i("afterTextChanged","afterTextChanged====");
                        String num = editable.toString();
                        int ms=0;
                        if(!TextUtils.isEmpty(num)){
                            ms = Integer.parseInt(num);
                        }
                        mList.get(holder.getAdapterPosition()).setArea(ms);
                    }
                });

                TextView tv_add_farmer_item_zw = holder.getTextView(R.id.tv_add_farmer_item_zw);
                tv_add_farmer_item_zw.setOnClickListener(new MyOnClickListener() {
                    @Override
                    protected void onNoDoubleClick(View view) {
                        getZuoWu(holder.getAdapterPosition());
                    }
                });
                return holder;
            }

            @Override
            public void bindData(RecyclerViewHolder holder, int position, ZuoWuItem bean) {
                        holder.setText(R.id.tv_add_farmer_item_zw,bean.getCrops())
                                .setText(R.id.et_add_farmer_item_ms,bean.getArea()+"");

                LinearLayout ll_add_farmer_item_delete = (LinearLayout) holder.getView(R.id.ll_add_farmer_item_delete);
                if(position==0){
                    ll_add_farmer_item_delete.setVisibility(View.INVISIBLE);
                }else{
                    ll_add_farmer_item_delete.setVisibility(View.VISIBLE);
                }
                ll_add_farmer_item_delete.setOnClickListener(new MyOnClickListener() {
                    @Override
                    protected void onNoDoubleClick(View view) {
                        MyDialog.Builder mDialog=new MyDialog.Builder(mContext);
                        mDialog.setMessage("是否删除当前作物?");
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
                                if(bean.getType()==1){
                                    mList.remove(position);
                                    notifyDataSetChanged();
                                }else{
                                  deleteZuoWu(position,bean.getMy_farmers_crops_id());
                                }
                            }
                        });
                        mDialog.create().show();
                    }

                    private void deleteZuoWu(int position,int my_farmers_crops_id) {
                        showLoading();
                        Map<String,String>map=new HashMap<String,String>();
                        map.put("user_id",getUserId());
                        map.put("mf_coprs_id",my_farmers_crops_id+"");
                        map.put("mf_id",id);
                        map.put("sign",GetSign.getSign(map));
                        addSubscription(ApiRequest.deleteMyFarmerZuoWu(map).subscribe(new MySub<BaseObj>(mContext) {
                            @Override
                            public void onMyNext(BaseObj obj) {
                                showMsg(obj.getMsg());
                                mList.remove(position);
                                notifyDataSetChanged();
                            }
                        }));
                    }
                });
            }
        };
        ZuoWuItem item=new ZuoWuItem();
        item.setType(1);
        List<ZuoWuItem> list = new ArrayList();
        list.add(item);
        adapter.setList(list);
        rv_add_farmer.setNestedScrollingEnabled(false);
        rv_add_farmer.setLayoutManager(new LinearLayoutManager(mContext));
        rv_add_farmer.setAdapter(adapter);

    }

    private void getData() {
        addSubscription(ApiRequest.getMyFarmer(id,getSign("mf_id",id)).subscribe(new MySub<MyFarmerObj>(mContext) {
            @Override
            public void onMyNext(MyFarmerObj obj) {
                et_add_farmer_name.setText(obj.getFarmers_name());
                et_add_farmer_phone.setText(obj.getPhone_number());
                tv_add_farmer_area.setText(obj.getFarmland_province()+","+obj.getFarmland_city()+","+obj.getFarmland_area());
                et_add_farmer_nongtian.setText(obj.getFarmland_addresss());
                List<ZuoWuItem> crops_list = obj.getCrops_list();
                adapter.setList(crops_list,true);
                areaId1=obj.getFarmland_province_id();
                areaId2=obj.getFarmland_city_id();
                areaId3=obj.getFarmland_area_id();
//                tv_add_farmer_zw.setText(obj.get());
//                et_add_farmer_ms.setText(obj.getArea());
            }
        }));
    }

    @Override
    protected void initData() {
        if(Constant.IParam.editFarmer.equals(type)){
            id = getIntent().getStringExtra("id");
            showProgress();
            getData();
        }
    }

    @OnClick({R.id.ll_add_farmer_zuo_wu,R.id.tv_add_farmer_area,R.id.tv_add_farmer_commit})
    protected void onViewClick(View v) {
        switch (v.getId()){
            case R.id.ll_add_farmer_zuo_wu:
                ZuoWuItem item=new ZuoWuItem();
                item.setType(1);
                List<ZuoWuItem> list = new ArrayList();
                list.add(item);
                adapter.addList(list,true);
                break;
            case R.id.tv_add_farmer_commit:
                String name=getSStr(et_add_farmer_name);
                String phone=getSStr(et_add_farmer_phone);
                String juzhu=getSStr(tv_add_farmer_area);
                String nongtian=getSStr(et_add_farmer_nongtian);
                if(TextUtils.isEmpty(name)){
                    showMsg("农户姓名不能为空");
                    return;
                }else if(TextUtils.isEmpty(phone)){
                    showMsg("联系方式不能为空");
                    return;
                }else if(TextUtils.isEmpty(juzhu)){
                    showMsg("所属地区不能为空");
                    return;
                }else if(TextUtils.isEmpty(nongtian )){
                    showMsg("农田地址不能为空");
                    return;
                }
                if(adapter.getList()!=null&&adapter.getList().size()>0){
                    boolean isNull=false;
                    for (int i = 0; i < adapter.getList().size(); i++) {
                        ZuoWuItem bean = (ZuoWuItem) adapter.getList().get(i);
                        if(TextUtils.isEmpty(bean.getCrops())){
                            showMsg("作物不能为空");
                            isNull=true;
                            break;
                        }else if(bean.getArea()<=0){
                            showMsg("亩数不能为空");
                            isNull=true;
                            break;
                        }
                    }
                    if(isNull){
                        return;
                    }
                }

                addOrEditFarmer(name,phone,juzhu,nongtian );
            break;
           /* case R.id.tv_add_farmer_zw:
//                getZuoWu(-1);
                break;*/
            case R.id.tv_add_farmer_area:
//                getZuoWu();
                PhoneUtils.hiddenKeyBoard(mContext);
                selectArea();
                break;
        }
    }

    private void getZuoWu(int position) {
        showLoading();
        String rnd=getRnd();
        addSubscription(ApiRequest.getZuoWuList(rnd,getSign("rnd",rnd)).subscribe(new MySub<List<ZuoWuObj>>(mContext) {
            @Override
            public void onMyNext(List<ZuoWuObj> list) {
                showZuoWu(position,list);
            }
        }));
    }
    private LoadMoreAdapter zuoWuAdapter;
    private void showZuoWu(int position,List<ZuoWuObj> list) {
        BottomSheetDialog zuoWuDialog = new BottomSheetDialog(mContext);
        zuoWuAdapter =new LoadMoreAdapter<ZuoWuObj>(mContext, R.layout.item_chonghai,0) {
            @Override
            public void bindData(LoadMoreViewHolder holder, int i, final ZuoWuObj bean) {
                TextView tv_chonghai_name = holder.getTextView(R.id.tv_chonghai_name);
                tv_chonghai_name.setText(bean.getCrop_name());
                tv_chonghai_name.setOnClickListener(new MyOnClickListener() {
                    @Override
                    protected void onNoDoubleClick(View view) {
                        zuoWuDialog.dismiss();
                        boolean flag=true;//判断是否添加了相同作物
                        if(notEmpty(adapter.getList())){
                            for (int j = 0; j <adapter.getList().size(); j++) {
                                if(bean.getCrop_name().equals(((ZuoWuItem)adapter.getList().get(j)).getCrops())){
                                    flag=false;
                                    showMsg("不能添加相同的作物");
                                    break;
                                }
                            }
                        }
                        if(flag){
                            ((ZuoWuItem)adapter.getList().get(position)).setCrops(bean.getCrop_name());
                            adapter.notifyDataSetChanged();
                        }

                    }
                });
            }
        };
        zuoWuAdapter.setList(list);
        View zuoWuView = LayoutInflater.from(mContext).inflate(R.layout.popu_chonghai, null);
        RecyclerView rv_zuowu = zuoWuView.findViewById(R.id.rv_chonghai);
        rv_zuowu.setLayoutManager(new LinearLayoutManager(mContext));
        rv_zuowu.setAdapter(zuoWuAdapter);
        TextView tv_zuowu_cancle = zuoWuView.findViewById(R.id.tv_chonghai_cancle);
        tv_zuowu_cancle.setOnClickListener(new MyOnClickListener() {
            @Override
            protected void onNoDoubleClick(View view) {
                zuoWuDialog.dismiss();
            }
        });
        zuoWuDialog.setContentView(zuoWuView);
        zuoWuDialog.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        zuoWuDialog.show();
    }


    private void addOrEditFarmer(String name, String phone, String juzhu, String nongtian ) {
        showLoading();
        Map<String,String> map=new HashMap<String,String>();
        map.put("type",editOrAdd);
        if(TextUtils.isEmpty(id)){//添加
            map.put("mf_id","0");
        }else{//编辑
            map.put("mf_id",id);
        }
        map.put("user_id",getUserId());
        map.put("farmers_name",name);
        map.put("phone_number",phone);

        map.put("farmland_province",areaId1+"");
        map.put("farmland_city",areaId2+"");
        map.put("farmland_area",areaId3+"");

        map.put("farmland_addresss",nongtian);
        map.put("sign", GetSign.getSign(map));
        AddFarmerItem item=new AddFarmerItem();
        List<ZuoWuItem> body=new ArrayList<>();
        if(adapter.getList()!=null&&adapter.getList().size()>0){
            for (int i = 0; i < adapter.getList().size(); i++) {
                ZuoWuItem zuoWu= (ZuoWuItem) adapter.getList().get(i);
                body.add(zuoWu);
            }
        }
        item.setBody(body);
        addSubscription(ApiRequest.addMyFarmer(map,item).subscribe(new MySub<BaseObj>(mContext) {
            @Override
            public void onMyNext(BaseObj obj) {
                showMsg(obj.getMsg());
                setResult(RESULT_OK);
                finish();
            }
        }));
    }


    private OptionsPopupWindow pwOptions;
    private ArrayList<String> options1Items;
    private ArrayList<ArrayList<String>> options2Items;
    private ArrayList<ArrayList<ArrayList<String>>> options3Items;
    private List<CityBean> cityBean;
    private int areaId1;
    private int areaId2;
    private int areaId3;
    private String province;
    private String city;
    private String area;
    private void selectArea() {
        showLoading();
        RXStart(new IOCallBack<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
                initAddressDialog();
                subscriber.onNext(null);
                subscriber.onCompleted();
            }
            @Override
            public void onMyNext(String s) {
                dismissLoading();
                pwOptions = new OptionsPopupWindow(mContext, "选择地区");
                // 三级联动效果
                pwOptions.setPicker(options1Items, options2Items, options3Items, true);
                // 设置默认选中的三级项目
                pwOptions.setSelectOptions(0, 0, 0);
                // 监听确定选择按钮
                pwOptions.setOnoptionsSelectListener((options1, option2, options3) -> {
                    // 返回的分别是三个级别的选中位置
                    /*String area = options1Items.get(options1) + ","
                            + options2Items.get(options1).get(option2) + ","
                            + options3Items.get(options1).get(option2).get(options3);*/
                    province=options1Items.get(options1);
                    city=options2Items.get(options1).get(option2) ;
                    area=options3Items.get(options1).get(option2).get(options3);

                    areaId1 = cityBean.get(options1).getId();
                    areaId2 = cityBean.get(options1).getPca_list().get(option2).getId();
                    areaId3 = cityBean.get(options1).getPca_list().get(option2).getPca_list().get(options3).getId();
                    Log.i("areaId===", areaId1 +"-"+ areaId2 +"-"+ areaId3);
                    tv_add_farmer_area.setText(province+","+city+","+area);
                });
                pwOptions.showAtLocation(tv_add_farmer_area, Gravity.BOTTOM, ActionBar.LayoutParams.WRAP_CONTENT,PhoneUtils.getNavigationBarHeight(mContext));
            }
        });
    }

    private void initAddressDialog() {
        options1Items = new ArrayList<String>();
        options2Items = new ArrayList<ArrayList<String>>();
        options3Items = new ArrayList<ArrayList<ArrayList<String>>>();
        String areaJson = StreamUtils.get(this, R.raw.area);
        province(areaJson);
        // 地址选择器
    }
    private void province(String strJson) {
        cityBean = new Gson().fromJson(strJson,
                new TypeToken<List<CityBean>>() {
                }.getType());
        ArrayList<String> item2;
        ArrayList<ArrayList<String>> item3;
        for (int i = 0; i < cityBean.size(); i++) {
            CityBean city= cityBean.get(i);
            options1Items.add(city.getTitle());
            item2=new ArrayList<>();
            item3=new ArrayList<ArrayList<String>>();
            for (int j = 0; j < city.getPca_list().size(); j++) {
                CityBean citySecond=city.getPca_list().get(j);
                item2.add(citySecond.getTitle());
                ArrayList<String> lastItem = new ArrayList<String>();
                for (int k = 0; k < citySecond.getPca_list().size(); k++) {
                    CityBean cityThird=citySecond.getPca_list().get(k);
                    lastItem.add(cityThird.getTitle());
                }
                item3.add(lastItem);
            }
            options2Items.add(item2);
            options3Items.add(item3);
        }
    }

}
