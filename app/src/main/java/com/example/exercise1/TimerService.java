package com.example.exercise1;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Binder;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;

import java.util.Locale;
import java.util.Timer;

public class TimerService extends Service {
    private final IBinder binder = new TimerBinder();
    public static int seconds11 = 0;
    public static int seconds12 = 0;
    public static int seconds21 = 0;
    public static int seconds22 = 0;
    public static final int NOTIFICATION_ID = 5453;
    private static final String TAG = "TimerService";

    public void setListener(OnTimeOutListener listener) {
        this.listener = listener;
    }

    private OnTimeOutListener listener;

    public interface OnTimeOutListener {
        void onTimerOut();
    }

    @Override
    public void onCreate() {
        super.onCreate();
//        runTimer(MainActivity.time1);
//        runTimer2(MainActivity.time2);
    }

    @Override
    public IBinder onBind(Intent intent) {

        return binder;
    }


    public class TimerBinder extends Binder {
        TimerService getTime() {
            return TimerService.this;
        }
    }

    public int getSeconds11() {
        return seconds11;
    }

    public int getSeconds12() {
        return seconds12;
    }

    public int getSeconds21() {
        return seconds21;
    }

    public int getSeconds22() {
        return seconds22;
    }



    protected void sendNotification() {
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        Notification.Builder builder = new Notification.Builder(this);
        Intent intent = new Intent(this, DisplayResult.class);
        if (listener != null) {
            listener.onTimerOut();
        }
        PendingIntent pendingIntent = PendingIntent.getActivity(this,0, intent, 0);
        builder.setSmallIcon(android.R.drawable.sym_def_app_icon)
                .setContentTitle(getString(R.string.title))
                .setContentText(getString(R.string.notification))
                .setVibrate(new long[] {0, 1000})
                .setContentIntent(pendingIntent)
                .setAutoCancel(true);

//        QuizApplication.failedTimes++;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            NotificationChannel notificationChannel = new NotificationChannel("AppTestNotificationId", "AppTestNotificationName", NotificationManager.IMPORTANCE_DEFAULT);
            notificationManager.createNotificationChannel(notificationChannel);
            builder.setChannelId("AppTestNotificationId");
        }
        notificationManager.notify(NOTIFICATION_ID, builder.build());
    }



    public void runTimer(final int timeLimit) {
        final Handler handler = new Handler(Looper.getMainLooper());

        if(!MainActivity.running) {
            MainActivity.running = true;
            handler.post(new Runnable() {
                @Override
                public void run() {
                    seconds11++;
                    if (MainActivity.running2) {
                        seconds12++;
                    }
                    if (seconds11 == timeLimit + 1) {
                        sendNotification();
                        Toast toast = Toast.makeText(getApplicationContext(), R.string.notification, Toast.LENGTH_SHORT);
                        toast.show();
                        MainActivity.running = false;
                        seconds11 = 0;
                        seconds12 = 0;
                    } else {
                        if (MainActivity.running) {
                            Log.d(TAG, "run: running =  true");
                            handler.postDelayed(this, 1000);
                        }
                    }
                }

            });
        }
    }





    public void runTimer2(final int timeLimit2) {
        final Handler handler = new Handler(Looper.getMainLooper());
        if (!question2.running) {
            question2.running = true;
            handler.post(new Runnable() {
                @Override
                public void run() {
                    seconds21++;
                    if (question2.running2) {
                        seconds22++;
                    }

//                if (question2.running == false) {
//                    seconds21 = 0;
//                    seconds22 = 0;
//                }
                    if (seconds21 == timeLimit2 + 1) {
                        sendNotification();
                        question2.running = false;
                        Toast toast = Toast.makeText(getApplicationContext(), R.string.notification, Toast.LENGTH_SHORT);
                        toast.show();
                        seconds21 = 0;
                        seconds22 = 0;
                    } else {
                        if (question2.running) {
                            handler.postDelayed(this, 1000);
                        }
                    }

                }
            });
        }
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
//        seconds11 = 0;
//        seconds12 = 0;


    }

}
