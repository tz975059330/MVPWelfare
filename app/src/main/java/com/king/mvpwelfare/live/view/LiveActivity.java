package com.king.mvpwelfare.live.view;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.king.mvpwelfare.R;
import com.king.mvpwelfare.live.model.LiveBean;
import com.superplayer.library.SuperPlayer;




/**
 * Created by 16230 on 2016/10/9.
 */

public class LiveActivity extends AppCompatActivity implements SuperPlayer.OnNetChangeListener {
    //流媒体播放器
    private SuperPlayer player;
    //流媒体地址
    private String url;
    //流媒体标题
    private String liveTitle;
    //在线观看人数
    private String personNum;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activitiy_live);
        Intent intent = this.getIntent();
        LiveBean data = (LiveBean) intent.getSerializableExtra("data");
        url = data.getLiveUrl();
        liveTitle = data.getLiveTitle();
        personNum = data.getPeople();
        play();
    }

    private void play() {
        player = (SuperPlayer) findViewById(R.id.view_super_player);
        //播放类型为直播
        player.setLive(true);
        //直播观看人数设置
        player.setNumOfLive(personNum);
        //默认直接全屏
        player.setFullScreenOnly(true);
        //设置全屏长宽比
        player.setScaleType(SuperPlayer.SCALETYPE_WRAPCONTENT);
        player.setNetChangeListener(true)//设置监听手机网络的变化
                .setOnNetChangeListener(this)//实现网络变化的回调
                .onError(new SuperPlayer.OnErrorListener() {
                    @Override
                    public void onError(int what, int extra) {
                        Toast.makeText(LiveActivity.this, "出了点小问题，请重新加载试试", Toast.LENGTH_SHORT).show();
                        onDestroy();

                    }
                }).setTitle(liveTitle);//设置直播间名字
        player.play(url);
    }

    /**
     * 网络链接监听类
     */
    @Override
    public void onWifi() {
        Toast.makeText(this, "当前网络环境是WIFI", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onMobile() {
        Toast.makeText(this, "当前网络环境是手机网络", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDisConnect() {
        Toast.makeText(this, "网络链接断开", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNoAvailable() {
        Toast.makeText(this, "无网络链接", Toast.LENGTH_SHORT).show();
    }

    /**
     * 下面的这几个Activity的生命状态很重要
     */
    @Override
    protected void onPause() {
        super.onPause();
        if (player != null) {
            player.onPause();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (player != null) {
            player.onResume();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (player != null) {
            player.onDestroy();
        }
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if (player != null) {
            player.onConfigurationChanged(newConfig);
        }
    }

    @Override
    public void onBackPressed() {
        if (player != null && player.onBackPressed()) {
            return;
        }
        super.onBackPressed();
    }

}
