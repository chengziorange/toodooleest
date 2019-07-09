package top.orange233.toodooleest;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.widget.Toast;

import java.util.Date;

public class MyReceiver extends BroadcastReceiver {


    private boolean isNotification;
    private int hours, minutes;

    public MyReceiver(boolean isNotification, int hours, int minutes) { //构造方法
        this.isNotification = isNotification;
        this.hours = hours;
        this.minutes = minutes;
    }

    @Override
    public void onReceive(Context context, Intent intent) {

        Date date = new Date();

        if (intent.getAction() != null && intent.getAction().equals(Intent.ACTION_TIME_TICK) && isNotification) {
            if (hours == date.getHours() && minutes == date.getMinutes()) {//判断当前时间是否为设定的时间

                Toast.makeText(context, "任务提醒", Toast.LENGTH_LONG).show();
                NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
                Notification.Builder builder = new Notification.Builder(context.getApplicationContext());
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    String id = "my_channel_01";
                    CharSequence name = "ToDoList";
                    int importance = NotificationManager.IMPORTANCE_HIGH;
                    NotificationChannel mChannel = new NotificationChannel(id, name, importance);
                    mChannel.enableLights(true);
                    mChannel.setLightColor(Color.RED);
                    mChannel.enableVibration(true);
                    mChannel.setVibrationPattern(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});
                    notificationManager.createNotificationChannel(mChannel);
                    builder.setChannelId(id);
                }
                builder.setContentInfo("补充内容");
                builder.setContentText("快来看看今天还有什么事没做吧！点击进入TooDooLeest");
                builder.setContentTitle("TooDooLeest");
                builder.setSmallIcon(R.drawable.ic_launcher_foreground);
                builder.setTicker("新消息");
                builder.setAutoCancel(true);
                builder.setWhen(System.currentTimeMillis());
                Intent intent1 = new Intent(context, MainActivity.class);
                PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent1, PendingIntent.FLAG_CANCEL_CURRENT);
                builder.setContentIntent(pendingIntent);
                Notification notification = builder.getNotification();
                notificationManager.notify(1, notification);
            }
        }
    }
}
