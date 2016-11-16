
package com.xdja.ycm.studynote.ui;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhipeng on 16/1/13.
 */
public class MainActivity extends Activity {

    private HtmlEditText mEditText;
    private Button mButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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
                PicturePickUtils.selectPicFromLocal(this,888);//获取手机本地图片的代码，大家可以自行实现
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case 888:
                    if (data != null) {

                        Uri selectedImage = data.getData();
                        String imageurl = UriUtils.getImageAbsolutePath(this, selectedImage);

                        mEditText.insertBitmap(imageurl);



                    }
                default:
                    break;
            }
        }
    }
}

