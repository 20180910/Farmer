package com.zhizhong.farmer.module.tuiguangyuan.activity;

import android.Manifest;
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
import android.text.TextUtils;
import android.util.Log;
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
import com.github.customview.MyEditText;
import com.github.customview.MyTextView;
import com.zhizhong.farmer.Config;
import com.zhizhong.farmer.GetSign;
import com.zhizhong.farmer.R;
import com.zhizhong.farmer.base.BaseActivity;
import com.zhizhong.farmer.base.BaseObj;
import com.zhizhong.farmer.base.MySub;
import com.zhizhong.farmer.module.tuiguangyuan.network.ApiRequest;
import com.zhizhong.farmer.module.tuiguangyuan.network.request.UploadImgItem;
import com.zhizhong.farmer.tools.BitmapUtils;
import com.zhizhong.farmer.tools.ImageUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;
import rx.Subscriber;

/**
 * Created by administartor on 2017/8/3.
 */

public class TGYMyDataActivity extends BaseActivity {
    @BindView(R.id.civ_tgy_info_img)
    CircleImageView civ_tgy_info_img;
    @BindView(R.id.ll_tgy_info_img)
    LinearLayout ll_tgy_info_img;
    @BindView(R.id.et_tgy_info_name)
    MyEditText et_tgy_info_name;
    @BindView(R.id.et_tgy_info_tel)
    MyEditText et_tgy_info_tel;
    @BindView(R.id.tv_tgy_info_updatepwd)
    LinearLayout tv_tgy_info_updatepwd;
    @BindView(R.id.tv_tgy_tgy_info_exit)
    TextView tv_tgy_tgy_info_exit;
    @BindView(R.id.tv_tgy_info_commit)
    MyTextView tv_tgy_info_commit;




    private BottomSheetDialog selectPhotoDialog;
    private String imgUrl="";

    @Override
    protected int getContentView() {
        setAppTitle("我的资料");
        return R.layout.act_tgy_my_data;
    }

    @Override
    protected void initView() {
        String nickName = SPUtils.getPrefString(mContext, Config.nick_name, null);
        String avatar = SPUtils.getPrefString(mContext, Config.avatar, null);
        String mobile = SPUtils.getPrefString(mContext, Config.mobile, null);
        et_tgy_info_name.setText(nickName);
        et_tgy_info_tel.setText(mobile);
        if (avatar != null) {
            Glide.with(mContext).load(avatar).error(R.color.c_press).into(civ_tgy_info_img);
        }
    }

    @Override
    protected void initData() {

    }

    @OnClick({R.id.ll_tgy_info_img, R.id.tv_tgy_info_updatepwd, R.id.tv_tgy_tgy_info_exit, R.id.tv_tgy_info_commit})
    protected void onViewClick(View view) {
        switch (view.getId()) {
            case R.id.ll_tgy_info_img:
                PhoneUtils.hiddenKeyBoard(mContext);
                showSelectPhotoDialog();
                break;
            case R.id.tv_tgy_info_updatepwd:
                STActivity(TGYUpdatePWDActivity.class);
                break;
            case R.id.tv_tgy_tgy_info_exit:
                mDialog = new MyDialog.Builder(mContext);
                mDialog.setMessage("是否确认退出登录?")
                        .setNegativeButton((dialog, which) -> dialog.dismiss())
                        .setPositiveButton((dialog, which) -> {
                            dialog.dismiss();
                            exitLogin();
                        });
                mDialog.create().show();
                break;
            case R.id.tv_tgy_info_commit:

                String name = getSStr(et_tgy_info_name);
                String mobile = getSStr(et_tgy_info_tel);

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
                addSubscription(ApiRequest.uploadImg(rnd,getSign("rnd",rnd),item).subscribe(new MySub<BaseObj>(mContext) {
                    @Override
                    public void onMyNext(BaseObj obj) {
                        imgUrl = obj.getImg();
                        Glide.with(mContext).load(imgSaveName).into(civ_tgy_info_img);
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

    private void updateInfo() {
        String name = getSStr(et_tgy_info_name);
        String mobile = getSStr(et_tgy_info_tel);

        Map<String,String> map=new HashMap<String,String>();
        map.put("user_id",getUserId());
        map.put("avatar",imgUrl);
        map.put("nickname",name);
        map.put("mobile",mobile);
        map.put("sign", GetSign.getSign(map));
        addSubscription(ApiRequest.updateInfoTGY(map).subscribe(new MySub<BaseObj>(mContext) {
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
       /* Intent intent = new Intent(Config.Bro.operation);
        intent.putExtra(Config.Bro.flag, Config.Bro.exit_login);
        LocalBroadcastManager.getInstance(mContext).sendBroadcast(intent);


        Intent intentSelect=new Intent(Config.IParam.selectUser);
        STActivity(intentSelect,SelectUserActivity.class);

        finish();*/
        Intent intent=new Intent(Config.exitAPP);
        STActivity(intent,TGYMyActivity.class);
        finish();
    }


}
