package com.zhizhong.farmer.module.my.activity;

import android.Manifest;
import android.app.ActionBar;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.design.widget.BottomSheetDialog;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.github.androidtools.PhoneUtils;
import com.github.androidtools.SPUtils;
import com.github.androidtools.inter.MyOnClickListener;
import com.github.baseclass.rx.IOCallBack;
import com.github.baseclass.view.MyDialog;
import com.github.baseclass.view.pickerview.OptionsPopupWindow;
import com.github.customview.MyEditText;
import com.github.customview.MyTextView;
import com.zhizhong.farmer.Config;
import com.zhizhong.farmer.GetSign;
import com.zhizhong.farmer.R;
import com.zhizhong.farmer.base.BaseActivity;
import com.zhizhong.farmer.base.BaseObj;
import com.zhizhong.farmer.base.MySub;
import com.zhizhong.farmer.module.home.activity.SelectUserActivity;
import com.zhizhong.farmer.module.my.bean.CityBean;
import com.zhizhong.farmer.module.my.network.ApiRequest;
import com.zhizhong.farmer.module.my.network.request.UploadImgItem;
import com.zhizhong.farmer.tools.BitmapUtils;
import com.zhizhong.farmer.tools.ImageUtils;
import com.zhizhong.farmer.tools.StreamUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;
import rx.Subscriber;

/**
 * Created by administartor on 2017/8/3.
 */

public class MyDataActivity extends BaseActivity {
    @BindView(R.id.civ_info_img)
    CircleImageView civ_info_img;
    @BindView(R.id.ll_info_img)
    LinearLayout ll_info_img;
    @BindView(R.id.et_info_name)
    MyEditText et_info_name;
    @BindView(R.id.et_info_tel)
    MyEditText et_info_tel;
    @BindView(R.id.ll_info_updatepwd)
    LinearLayout ll_info_updatepwd;
    @BindView(R.id.tv_info_exit)
    TextView tv_info_exit;
    @BindView(R.id.tv_info_commit)
    MyTextView tv_info_commit;
    @BindView(R.id.et_info_address)
    TextView et_info_address;
    @BindView(R.id.tv_info_area)
    TextView tv_info_area;


    private BottomSheetDialog selectPhotoDialog;
    private String imgUrl="";
    private String address="";


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

    @Override
    protected int getContentView() {
        setAppTitle("我的资料");
        return R.layout.act_my_data;
    }

    @Override
    protected void initView() {
        String nickName = SPUtils.getPrefString(mContext, Config.nick_name, null);
        String avatar = SPUtils.getPrefString(mContext, Config.avatar, null);
        String mobile = SPUtils.getPrefString(mContext, Config.mobile, null);
        String address = SPUtils.getPrefString(mContext, Config.address, null);

        province = SPUtils.getPrefString(mContext, Config.province, null);
        city     = SPUtils.getPrefString(mContext, Config.city, null);
        area     = SPUtils.getPrefString(mContext, Config.area, null);
        areaId1 = SPUtils.getPrefInt(mContext, Config.province_id, 0);
        areaId2 = SPUtils.getPrefInt(mContext, Config.city_id, 0);
        areaId3 = SPUtils.getPrefInt(mContext, Config.area_id, 0);

        if(!TextUtils.isEmpty(province)){
            tv_info_area.setText(province +","+ city +","+ area);
        }else{
            tv_info_area.setText("请选择地区");
        }
        et_info_name.setText(nickName);
        et_info_tel.setText(mobile);
        et_info_address.setText(address);
        if (avatar != null) {
            Glide.with(mContext).load(avatar).error(R.drawable.people).into(civ_info_img);
        }
    }

    @Override
    protected void initData() {

    }

    @OnClick({R.id.tv_info_area,R.id.ll_info_img, R.id.ll_info_updatepwd, R.id.tv_info_exit, R.id.tv_info_commit})
    protected void onViewClick(View view) {
        switch (view.getId()) {
            case R.id.tv_info_area:
                PhoneUtils.hiddenKeyBoard(mContext);
                selectArea();
                break;
            case R.id.ll_info_img:
                PhoneUtils.hiddenKeyBoard(mContext);
                showSelectPhotoDialog();
                break;
            case R.id.ll_info_updatepwd:
                STActivity(UpdatePWDActivity.class);
                break;
            case R.id.tv_info_exit:
                mDialog = new MyDialog.Builder(mContext);
                mDialog.setMessage("是否确认退出登录?")
                        .setNegativeButton((dialog, which) -> dialog.dismiss())
                        .setPositiveButton((dialog, which) -> {
                            dialog.dismiss();
                            exitLogin();
                        });
                mDialog.create().show();
                break;
            case R.id.tv_info_commit:

                String name = getSStr(et_info_name);
                String mobile = getSStr(et_info_tel);

                if(TextUtils.isEmpty(name)){
                    showMsg("昵称不能为空");
                    return;
                }else if(TextUtils.isEmpty(mobile)){
                    showMsg("联系方式不能为空");
                    return;
                }else if(!GetSign.isMobile(mobile)){
                    showMsg("请输入正确的联系方式");
                    return;
                }
                updateInfo();
                break;
        }
    }



    private void showSelectPhotoDialog() {
        if (selectPhotoDialog == null) {
            View sexView= LayoutInflater.from(mContext).inflate(R.layout.popu_select_photo,null);
            sexView.findViewById(R.id.tv_select_photo).setOnClickListener(new MyOnClickListener() {
                @Override
                protected void onNoDoubleClick(View view) {
                    selectPhotoDialog.dismiss();
                    selectPhoto();
                }
            });
            sexView.findViewById(R.id.tv_take_photo).setOnClickListener(new MyOnClickListener() {
                @Override
                protected void onNoDoubleClick(View view) {
                    selectPhotoDialog.dismiss();
                    takePhoto();
                }
            });
            sexView.findViewById(R.id.tv_photo_cancle).setOnClickListener(new MyOnClickListener() {
                @Override
                protected void onNoDoubleClick(View view) {
                    selectPhotoDialog.dismiss();
                }
            });
            selectPhotoDialog = new BottomSheetDialog(mContext);
            selectPhotoDialog.setCanceledOnTouchOutside(true);
            selectPhotoDialog.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            selectPhotoDialog.setContentView(sexView);
        }
        selectPhotoDialog.show();
    }
    //选择相册
    private void selectPhoto() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, 3000);
    }
    private String path = Environment.getExternalStorageDirectory() +
            File.separator + Environment.DIRECTORY_DCIM + File.separator;
    private String getPhotoFileName() {
        Date date = new Date(System.currentTimeMillis());
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd_HHmmss");
        return "IMG_" + dateFormat.format(date);
    }
    Uri photoUri;
    private String imgSaveName="";
    //拍照
    private void takePhoto() {
        if (ContextCompat.checkSelfPermission(mContext, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(mContext, new String[]{Manifest.permission.CAMERA}, 1);
        } else {
            String state = Environment.getExternalStorageState();
            if (state.equals(Environment.MEDIA_MOUNTED)) {
                File file = new File(path);
                if (!file.exists()) {
                    file.mkdir();
                }
                String fileName = getPhotoFileName() + ".jpg";
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                imgSaveName = path + fileName;
                photoUri = Uri.fromFile(new File(imgSaveName));
                intent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);
                startActivityForResult(intent, 2000);
            }
        }
    }
    private void uploadImg() {
        showLoading();
        Log.i("========","========"+imgSaveName);
        RXStart(new IOCallBack<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
                String newPath= ImageUtils.filePath;
                String name=ImageUtils.fileName;
                String smallBitmapPath = ImageUtils.getSmallBitmap(imgSaveName, newPath, name);
                FileInputStream fis = null;
                try {
                    fis = new FileInputStream(smallBitmapPath);
                    Bitmap bitmap  = BitmapFactory.decodeStream(fis);
                    String imgStr = BitmapUtils.bitmapToString(bitmap);
                    subscriber.onNext(imgStr);
                    subscriber.onCompleted();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                    subscriber.onError(e);
                }
            }
            @Override
            public void onMyNext(String baseImg) {
                UploadImgItem item=new UploadImgItem();
                item.setFile(baseImg);
                String rnd = getRnd();
                addSubscription(ApiRequest.uploadImg(rnd,getSign("rnd",rnd),item).subscribe(new MySub<BaseObj>(mContext,true) {
                    @Override
                    public void onMyNext(BaseObj obj) {
                        imgUrl = obj.getImg();
                        Glide.with(mContext).load(imgSaveName).error(R.drawable.people).into(civ_info_img);
                        updateUserImg();
                    }
                }));
            }
            @Override
            public void onMyError(Throwable e) {
                super.onMyError(e);
                dismissLoading();
                showToastS("图片处理失败");
            }
        });
    }

    private void updateUserImg() {
        Map<String,String>map=new HashMap<String,String>();
        map.put("user_id",getUserId());
        map.put("avatar",imgUrl);
        map.put("sign",GetSign.getSign(map));
        addSubscription(ApiRequest.uploadImgForInfo(map).subscribe(new MySub<BaseObj>(mContext) {
            @Override
            public void onMyNext(BaseObj obj) {
                showMsg(obj.getMsg());
                if(!TextUtils.isEmpty(imgUrl)){
                    SPUtils.setPrefString(mContext,Config.avatar,imgUrl);
                }
            }
        }));
    }

    private void updateInfo() {
        String name = getSStr(et_info_name);
        String mobile = getSStr(et_info_tel);
        String address=getSStr(et_info_address);
        Map<String,String> map=new HashMap<String,String>();
        map.put("user_id",getUserId());
        map.put("avatar",imgUrl);
        map.put("nickname",name);
        map.put("mobile",mobile);
        map.put("live_province",areaId1+"");
        map.put("live_city",areaId2+"");
        map.put("live_area",areaId3+"");
        map.put("address", address);
        map.put("sign", GetSign.getSign(map));
        addSubscription(ApiRequest.setFarmerInfo(map).subscribe(new MySub<BaseObj>(mContext) {
            @Override
            public void onMyNext(BaseObj obj) {
                showMsg(obj.getMsg());
                if(!TextUtils.isEmpty(imgUrl)){
                    SPUtils.setPrefString(mContext,Config.avatar,imgUrl);
                }
                if(!TextUtils.isEmpty(name)){
                    SPUtils.setPrefString(mContext,Config.nick_name,name);
                }
                if(!TextUtils.isEmpty(mobile)){
                    SPUtils.setPrefString(mContext,Config.mobile,mobile);
                }
                if(!TextUtils.isEmpty(address)){
                    SPUtils.setPrefString(mContext,Config.address,address);
                }
                SPUtils.setPrefString(mContext, Config.province,province);
                SPUtils.setPrefString(mContext, Config.city, city);
                SPUtils.setPrefString(mContext, Config.area,area);

                SPUtils.setPrefInt(mContext, Config.province_id, areaId1);
                SPUtils.setPrefInt(mContext, Config.city_id,areaId2);
                SPUtils.setPrefInt(mContext, Config.area_id, areaId3);

                setResult(RESULT_OK);
            }
        }));

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode!=RESULT_OK){
            return;
        }
        switch (requestCode){
            case 2000:
                uploadImg();
                break;
            case 3000:
                Uri uri = data.getData();
                Cursor cursor = getContentResolver().query(uri, null, null, null,null);
                if (cursor != null && cursor.moveToFirst()) {
                    imgSaveName = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA));
                    uploadImg();
                }
                break;
        }
    }

    private void exitLogin() {
        SPUtils.removeKey(mContext, Config.user_id);
        SPUtils.removeKey(mContext,Config.userType);
        Intent intent = new Intent(Config.Bro.operation);
        intent.putExtra(Config.Bro.flag, Config.Bro.exit_login);
        LocalBroadcastManager.getInstance(mContext).sendBroadcast(intent);

//        Intent intentSelect=new Intent(Config.IParam.selectUser);
        STActivity(SelectUserActivity.class);

        finish();
        /*Intent intent=new Intent(Config.exitAPP);
        STActivity(intent,TGYMyActivity.class);
        finish();*/
    }

    private void selectArea() {
        showLoading();
        String rnd=getRnd();
        String sign = getSign("rnd",rnd);
        addSubscription(ApiRequest.getAllCity(rnd,sign).subscribe(new MySub<List<CityBean>>(mContext) {
            @Override
            public void onMyNext(List<CityBean> list) {
                cityBean=list;
                options1Items = new ArrayList<String>();
                options2Items = new ArrayList<ArrayList<String>>();
                options3Items = new ArrayList<ArrayList<ArrayList<String>>>();
//                String areaJson = StreamUtils.get(mContext, R.raw.area);
                province(null);
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

                    areaId1 = MyDataActivity.this.cityBean.get(options1).getId();
                    areaId2 = MyDataActivity.this.cityBean.get(options1).getPca_list().get(option2).getId();
                    areaId3 = MyDataActivity.this.cityBean.get(options1).getPca_list().get(option2).getPca_list().get(options3).getId();
                    Log.i("areaId===", areaId1 +"-"+ areaId2 +"-"+ areaId3);
                    tv_info_area.setText(province+","+city+","+area);
                });
                pwOptions.showAtLocation(tv_info_area, Gravity.BOTTOM, ActionBar.LayoutParams.WRAP_CONTENT,PhoneUtils.getNavigationBarHeight(mContext));
            }
        }));
        /*RXStart(new IOCallBack<String>() {
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
                    *//*String area = options1Items.get(options1) + ","
                            + options2Items.get(options1).get(option2) + ","
                            + options3Items.get(options1).get(option2).get(options3);*//*
                    province=options1Items.get(options1);
                    city=options2Items.get(options1).get(option2) ;
                    area=options3Items.get(options1).get(option2).get(options3);

                    areaId1 = MyDataActivity.this.cityBean.get(options1).getId();
                    areaId2 = MyDataActivity.this.cityBean.get(options1).getPca_list().get(option2).getId();
                    areaId3 = MyDataActivity.this.cityBean.get(options1).getPca_list().get(option2).getPca_list().get(options3).getId();
                    Log.i("areaId===", areaId1 +"-"+ areaId2 +"-"+ areaId3);
                    tv_info_area.setText(province+","+city+","+area);
                });
                pwOptions.showAtLocation(tv_info_area, Gravity.BOTTOM, ActionBar.LayoutParams.WRAP_CONTENT,PhoneUtils.getNavigationBarHeight(mContext));
            }
        });*/
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
        /*cityBean = new Gson().fromJson(strJson,
                new TypeToken<List<CityBean>>() {
                }.getType());*/
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
