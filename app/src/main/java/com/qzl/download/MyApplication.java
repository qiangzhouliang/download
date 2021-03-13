package com.qzl.download;

import android.app.Application;

import com.arialyy.aria.core.Aria;


/**
 * @author xushibin
 * @date 12/16/20
 * description：
 */
public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Aria.init(this);
    }
}
