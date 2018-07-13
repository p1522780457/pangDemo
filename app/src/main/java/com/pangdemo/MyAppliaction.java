package com.pangdemo;

import android.app.Application;
import android.content.res.Configuration;
import android.content.res.Resources;

import java.util.Locale;

import cn.jpush.android.api.JPushInterface;

/**
 * Created by pangw on 2018/5/9.
 */

public class MyAppliaction extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        initLocaleLanguage();
    }
    private void initLocaleLanguage() {
        Resources resources = getApplicationContext().getResources();
        Configuration configuration = resources.getConfiguration();
        configuration.locale = new Locale("en");
        resources.updateConfiguration(configuration, resources.getDisplayMetrics());//更新配置

        JPushInterface.setDebugMode(true);
        JPushInterface.init(this);
    }
}
