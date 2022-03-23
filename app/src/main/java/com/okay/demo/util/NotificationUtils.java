package com.okay.demo.util;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.ContextWrapper;
import android.os.Build;

import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;

/**
 * 通知适配8.0
 * https://www.jianshu.com/p/3abde1a11258
 */
 
public class NotificationUtils extends ContextWrapper {
 
    private NotificationManager manager;
    public static final String id = "channel_1";
    public static final String name = "channel_name_1";
 
    public NotificationUtils(Context context){
        super(context);
    }
 
    @RequiresApi(api = Build.VERSION_CODES.O)
    private void createNotificationChannel(){
        NotificationChannel channel = new NotificationChannel(id, name, NotificationManager.IMPORTANCE_HIGH);
        channel.setImportance(NotificationManager.IMPORTANCE_HIGH);
        getManager().createNotificationChannel(channel);
    }
    private NotificationManager getManager(){
        if (manager == null){
            manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        }
        return manager;
    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    private Notification.Builder getChannelNotification(String title, String content){
        return new Notification.Builder(getApplicationContext())
                .setContentTitle(title)
                .setContentText(content)
                .setSmallIcon(android.R.drawable.stat_notify_more)
                .setAutoCancel(true);
    }

    private NotificationCompat.Builder getNotification_25(String title, String content){
        return new NotificationCompat.Builder(getApplicationContext())
                .setContentTitle(title)
                .setContentText(content)
                .setSmallIcon(android.R.drawable.stat_notify_more)
                .setAutoCancel(true);
    }

    public void sendNotification(String title, String content){
        if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.O){
            //8.0发送通知需要设置渠道
            createNotificationChannel();
            Notification.Builder channelNotification = getChannelNotification
                    (title, content);
            channelNotification.setPriority(Notification.PRIORITY_HIGH);
            channelNotification.setChannelId(id);
            Notification notification = channelNotification.build();
            int id = (int)System.currentTimeMillis();
            getManager().notify(id,notification);
        }else{
            Notification notification = getNotification_25(title, content).build();
            getManager().notify(1,notification);
        }
    }
}