package com.king.mvpwelfare.image.view;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.king.mvpwelfare.API.APIURL;
import com.king.mvpwelfare.R;
import com.king.mvpwelfare.image.model.ImageBean;


/**
 * Created by 16230 on 2016/9/28.
 */

public class DetailActivity extends AppCompatActivity implements MyImageView.saveImage{
    final Bitmap[] img = new Bitmap[1];
    public static Handler mHandler;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        Intent intent = this.getIntent();
        ImageBean bean = (ImageBean) intent.getSerializableExtra("image");
        final MyImageView view = (MyImageView) findViewById(R.id.big_image);
        mHandler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                if((int)msg.obj == 321){
                    Snackbar.make(view, "保存成功", Snackbar.LENGTH_SHORT).show();
                }
            }
        };
        view.setSaveImage(this);
        Glide.with(this)
                .load(APIURL.HUABAN_IMAGE+bean.getKey())
                .asBitmap()
                .priority(Priority.IMMEDIATE)
                .into(new SimpleTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                        view.setImageBitmap(resource);
                        img[0] = resource;
                    }
                });
    }
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onDestroy() {
        super.onDestroy();
        this.finishAfterTransition();
    }

    @Override
    public void saveImage() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        if(img[0]==null){
            return;
        }
        builder.setMessage("确定保存当前照片？");
        builder.setTitle("提示");
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                MediaStore.Images.Media.insertImage(getContentResolver(), img[0], System.currentTimeMillis()+"", "description");
                Message msg = new Message();
                msg.obj = 321;
                mHandler.sendMessage(msg);

            }
        });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        builder.create().show();
    }
}
