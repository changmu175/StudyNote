
package com.xdja.ycm.studynote.ui;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import com.xdja.ycm.studynote.R;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by zhipeng on 16/1/13.
 */
public class NoteActivity extends Activity {

    private HtmlEditText mEditText;
    private Button mButton;
    private String imageName;
    private static final int PHOTO_REQUEST_TAKEPHOTO = 1;// 拍照
    private static final int PHOTO_REQUEST_GALLERY = 2;// 从相册中选择
    private static final int PHOTO_REQUEST_CUT = 3;//照片剪切结果
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ggg);

        mEditText = (HtmlEditText) findViewById(R.id.edit_text);
        mButton = (Button) findViewById(R.id.button_add_picture);
        ///storage/emulated/0/Mob/cn.xfz.app/cache/images/1450915925_4457.jpg
        ///storage/emulated/0/Pictures/1450770237621.jpg
        ///storage/emulated/0/Pictures/1450769835187.jpg
        ///storage/emulated/0/Mob/cn.xfz.app/cache/images/1450684805_82970.jpg
        List<String> list = new ArrayList<>();//这里是测试用的，对于图片地址，各位还是要自己设置一下
        list.add("你说");
        list.add(HtmlEditText.mBitmapTag+"/storage/emulated/0/Mob/cn.xfz.app/cache/images/1450915925_4457.jpg");
        list.add("我在哪");
        list.add(HtmlEditText.mBitmapTag+"/storage/emulated/0/Pictures/1450770237621.jpg");
        list.add("不告诉你");
        list.add(HtmlEditText.mBitmapTag+"/storage/emulated/0/Pictures/1450769835187.jpg");
        list.add(HtmlEditText.mBitmapTag+"/storage/emulated/0/Mob/cn.xfz.app/cache/images/1450684805_82970.jpg");
        list.add("嘿嘿");
        list.add(HtmlEditText.mBitmapTag+"/storage/emulated/0/Mob/cn.xfz.app/cache/images/1450915925_4457.jpg");

//        mEditText.setmContentList(list);


        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                PicturePickUtils.selectPicFromLocal(this,888);//获取手机本地图片的代码，大家可以自行实现
                showCamera();
            }
        });

        mEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                Log.i("EditActivity",mEditText.getmContentList().toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private String getImagePathFormUri(final Context context, Uri uri) {
        String path = null;
        Uri selectUri = uri;
        final String scheme = uri.getScheme();
        if (uri != null && scheme != null) {
            if (scheme.equals(ContentResolver.SCHEME_CONTENT)) {
                Cursor cursor = context.getContentResolver().query(uri, null, null, null, null);
                if (cursor != null && cursor.moveToFirst()) {
                    int columnIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                    path = cursor.getString(columnIndex);

                    if (!path.startsWith("/storage") && !path.startsWith("/mnt")) {
                        path = "/mnt" + path;
                    }
                    cursor.close();
                }
            } else if (scheme.equals(ContentResolver.SCHEME_FILE)) {
                path = selectUri.toString().replace("file://", "");
                int index = path.indexOf("/sdcard");
                path = index == -1 ? path : path.substring(index);
                if (!path.startsWith("/mnt")) {
                    path = "/mnt" + path;
                }
            }
        }

        return path;
    }
    /**
     * 拍照部分
     */
    private void showCamera() {
        final AlertDialog dlg = new AlertDialog.Builder(this).create();
        dlg.show();
        Window window = dlg.getWindow();
        //主要就是在这里实现这种效果的.
        //设置窗口的内容页面,shrew_exit_dialog.xml文件中定义view内容
        window.setContentView(R.layout.alertdialog);
        // 为确认按钮添加事件,执行退出应用操作
        TextView tv_paizhao = (TextView) window.findViewById(R.id.tv_content1);
        tv_paizhao.setText("拍照");
        tv_paizhao.setOnClickListener(new View.OnClickListener() {
            @Override
            @SuppressLint("SdCardPath")
            public void onClick(View v) {

                imageName = getNowTime() + ".png";
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                // 指定调用相机拍照后照片的储存路径
                intent.putExtra(MediaStore.EXTRA_OUTPUT,
                        Uri.fromFile(new File(Environment.getExternalStorageDirectory().getPath(), imageName)));
                startActivityForResult(intent, PHOTO_REQUEST_TAKEPHOTO);
                dlg.cancel();
            }
        });
        TextView tv_xiangce = (TextView) window.findViewById(R.id.tv_content2);
        tv_xiangce.setText("相册");
        tv_xiangce.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                getNowTime();
                imageName = getNowTime() + ".png";
                Intent intent = new Intent(Intent.ACTION_PICK, null);
                intent.setDataAndType(
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
                startActivityForResult(intent, PHOTO_REQUEST_GALLERY);

                dlg.cancel();
            }
        });

    }


    private String getNowTime() {
        Date date = new Date(System.currentTimeMillis());
        SimpleDateFormat dateFormat = new SimpleDateFormat("MMddHHmmssSS");
        return dateFormat.format(date);
    }


    //裁剪图片
    private void startPhotoZoom(Uri uri1, int size) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri1, "image/*");
        // crop为true是设置在开启的intent中设置显示的view可以剪裁
        intent.putExtra("crop", "true");

        // aspectX aspectY 是宽高的比例
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);

        // outputX,outputY 是剪裁图片的宽高
        intent.putExtra("outputX", size);
        intent.putExtra("outputY", size);
        intent.putExtra("return-data", false);

        intent.putExtra(MediaStore.EXTRA_OUTPUT,
                Uri.fromFile(new File(Environment.getExternalStorageDirectory().getPath(), imageName)));
        intent.putExtra("outputFormat", Bitmap.CompressFormat.PNG.toString());
        intent.putExtra("noFaceDetection", true); // no face detection
        startActivityForResult(intent, PHOTO_REQUEST_CUT);
    }
    //裁剪图片
    private void startConfirm(Uri uri1) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri1, "image/*");
//        // crop为true是设置在开启的intent中设置显示的view可以剪裁
//        intent.putExtra("crop", "true");
//
//        // aspectX aspectY 是宽高的比例
//        intent.putExtra("aspectX", 1);
//        intent.putExtra("aspectY", 1);
//
//        // outputX,outputY 是剪裁图片的宽高
//        intent.putExtra("outputX", size);
//        intent.putExtra("outputY", size);
//        intent.putExtra("return-data", false);

//        intent.putExtra(MediaStore.EXTRA_OUTPUT,
//                Uri.fromFile(new File(Environment.getExternalStorageDirectory().getPath(), imageName)));
//        intent.putExtra("outputFormat", Bitmap.CompressFormat.PNG.toString());
//        intent.putExtra("noFaceDetection", true); // no face detection
        startActivityForResult(intent, PHOTO_REQUEST_CUT);
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case PHOTO_REQUEST_TAKEPHOTO:

                    startPhotoZoom(
                            Uri.fromFile(new File(Environment.getExternalStorageDirectory().getPath(), imageName)),
                            480);
                    break;

                case PHOTO_REQUEST_GALLERY:
                    String path =null;
                    if (data != null){
                        Uri uri = data.getData();
                        path = getImagePathFormUri(this, uri);
                    }
//                    Bitmap bitmap = BitmapFactory.decodeFile(Environment.getExternalStorageDirectory().getPath()+"/"+imageName);
                    mEditText.insertBitmap(path);
//                        startPhotoZoom(data.getData(), 480);
                    break;

                case PHOTO_REQUEST_CUT:
//                BitmapFactory.Options options = new BitmapFactory.Options();
//
//                /**
//                 * 最关键在此，把options.inJustDecodeBounds = true;
//                 * 这里再decodeFile()，返回的bitmap为空
//                 * ，但此时调用options.outHeight时，已经包含了图片的高了
//                 */
//                options.inJustDecodeBounds = true;
                    Bitmap bitmap = BitmapFactory.decodeFile(Environment.getExternalStorageDirectory().getPath()+"/"+imageName);
                    mEditText.insertBitmap(Environment.getExternalStorageDirectory().getPath()+"/"+imageName);
//                    iv_photo.setImageBitmap(bitmap);
                    break;

            }
            super.onActivityResult(requestCode, resultCode, data);

        }
    }
}

