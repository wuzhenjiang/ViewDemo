package com.okay.demo.web;

import android.app.Activity;
import android.os.Bundle;
import android.os.Debug;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;

import com.okay.demo.R;
import com.okay.demo.util.NotificationUtils;
import com.okay.demo.util.NotificationUtils2;
import com.okay.demo.util.NotificationUtils3;

import java.io.File;

import leakcanary.ObjectWatcher;

/**
 * @author wzj
 * @date 2022/2/24 5:23 下午
 */
public class NotificationActivity extends Activity {
    private static final String FILENAME = "out-of-memory.hprof";
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);
    }

    public void clickNotify(View view){
        NotificationUtils notificationUtils = new NotificationUtils(this);
        notificationUtils.sendNotification("重要title","content");

        File heapDumpFile = new File(getExternalCacheDir(), FILENAME);
        try {
            Log.e("NotificationActivity","path = "+heapDumpFile.getAbsolutePath());
            Debug.dumpHprofData(heapDumpFile.getAbsolutePath());
        } catch (Throwable ignored) {
            Log.e("NotificationActivity",ignored.getMessage());
        }
    }


    public void clickNotify2(View view){
        NotificationUtils2 notificationUtils = new NotificationUtils2(this);
        notificationUtils.sendNotification("默认title","content");
    }

    public void clickNotify3(View view){
        NotificationUtils3 notificationUtils = new NotificationUtils3(this);
        notificationUtils.sendNotification("低等级title","content");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
