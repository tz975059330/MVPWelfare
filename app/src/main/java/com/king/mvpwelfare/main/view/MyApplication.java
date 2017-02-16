package com.king.mvpwelfare.main.view;

import android.app.Application;
import android.content.Context;

/**
 * Created by 16230 on 2016/11/1.
 */

public class MyApplication extends Application {
    public static Context context;
    @Override
    public void onCreate() {


        super.onCreate();
        context = this.getApplicationContext();
    }
}
