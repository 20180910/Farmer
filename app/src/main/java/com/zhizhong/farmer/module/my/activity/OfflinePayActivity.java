package com.zhizhong.farmer.module.my.activity;

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
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.github.androidtools.inter.MyOnClickListener;
import com.github.baseclass.rx.IOCallBack;
import com.github.customview.MyTextView;
import com.zhizhong.farmer.GetSign;
import com.zhizhong.farmer.R;
import com.zhizhong.farmer.base.BaseActivity;
import com.zhizhong.farmer.base.BaseObj;
import com.zhizhong.farmer.base.MySub;
import com.zhizhong.farmer.module.my.Constant;
import com.zhizhong.farmer.module.my.network.ApiRequest;
import com.zhizhong.farmer.module.my.network.request.UploadImgItem;
import com.zhizhong.farmer.module.my.network.response.OnlineAccountObj;
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
import rx.Subscriber;

/**
 * Created by administartor on 2017/8/7.
 */

public class OfflinePayActivity extends BaseActivity {


    @BindView(R.id.tv_online_khdw)
    TextView tv_online_khdw;
    @BindView(R.id.tv_online_zh)
    TextView tv_online_zh;
    @BindView(R.id.tv_online_khh)
    TextView tv_online_khh;
    @BindView(R.id.iv_online_img)
    ImageView iv_online_img;
    @BindView(R.id.fl_online_img)
    FrameLayout fl_online_img;
    @BindView(R.id.tv_offline_pay_commit)
    MyTextView tv_offline_pay_commit;

    private BottomSheetDialog selectPhotoDialog;
    private String imgUrl="";
    private String orderNo;

    @Override
    protected int getContentView() {
        setAppTitle("线下付款");
        return R.layout.act_offline_pay;
    }

    @Override
    protected void initView() {

        orderNo = getIntent().getStringExtra(Constant.orderNo);
    }

    @Override
    protected void initData() {
        showProgress();
        getData();
    }

    private void getData() {
        String rnd=getRnd();
        addSubscription(ApiRequest.getOnlineAccount(rnd,getSign("rnd",rnd)).subscribe(new MySub<OnlineAccountObj>(mContext,pl_load) {
            @Override
            public void onMyNext(OnlineAccountObj obj) {
                tv_online_khdw.setText(obj.getAccount_opener());
                tv_online_zh.setText(obj.getAccount_number());
                tv_online_khh.setText(obj.getOpening_bank());
            }
        }));
    }

    @OnClick({R.id.fl_online_img, R.id.tv_offline_pay_commit})
    protected void onViewClick(View view) {
        switch (view.getId()) {
            case R.id.fl_online_img:
                showSelectPhotoDialog();
                break;
            case R.id.tv_offline_pay_commit:
                if(TextUtils.isEmpty(imgUrl)){
                    showMsg("请上传支付凭证");
                    return;
                }
                onlinePay();
                break;
        }
    }

    private void onlinePay() {
        showLoading();
        Map<String,String> map=new HashMap<String,String>();
        map.put("order_no",orderNo);
        map.put("credential",imgUrl);
        map.put("sign", GetSign.getSign(map));
        addSubscription(ApiRequest.onLinePay(map).subscribe(new MySub<BaseObj>(mContext) {
            @Override
            public void onMyNext(BaseObj obj) {
                showMsg(obj.getMsg());
                Intent intent = new Intent();
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                STActivity(intent,MyOrderListActivity.class);
                finish();
            }
        }));
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
                        iv_online_img.setVisibility(View.VISIBLE);
                        Glide.with(mContext).load(imgSaveName).into(iv_online_img);
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
}
