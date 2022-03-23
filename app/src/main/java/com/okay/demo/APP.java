package com.okay.demo;

import android.app.Application;

import com.github.moduth.blockcanary.BlockCanary;

/**
 * @author wzj
 * @date 2022/3/15 3:55 下午
 */
public class APP extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
//        BlockCanary.install(this);
    }
}
