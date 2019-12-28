package com.example.exercise1;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Handler;
import android.util.Log;
import android.widget.TextView;
import android.os.Looper;


import androidx.core.app.NotificationCompat;

import java.util.Locale;


/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p>
 * TODO: Customize class - update intent actions and extra parameters.
 */
public class TimingService extends IntentService {
    public static final String EXTRA_MESSAGE = "message";
    private int seconds = 0;
    private int seconds2 = 0;
    private Boolean pass = false;
    public static final int NOTIFICATION_ID = 5453;

    public TimingService() {
        super("TimingService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        int time = intent.getIntExtra(EXTRA_MESSAGE, -1);
        runTimer(time);

    }

    protected void sendNotification() {
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        Notification.Builder builder = new Notification.Builder(this);
        Intent intent = new Intent(this, DisplayResult.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this,0, intent, 0);
        builder.setSmallIcon(android.R.drawable.sym_def_app_icon)
                .setContentTitle(getString(R.string.title))
                .setContentText(getString(R.string.notification))
                .setVibrate(new long[] {0, 1000})
                .setAutoCancel(true);


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            NotificationChannel notificationChannel = new NotificationChannel("AppTestNotificationId", "AppTestNotificationName", NotificationManager.IMPORTANCE_DEFAULT);
            notificationManager.createNotificationChannel(notificationChannel);
            builder.setChannelId("AppTestNotificationId");
        }
        notificationManager.notify(NOTIFICATION_ID, builder.build());
    }

    private void runTimer(final int timeLimit) {
        final Handler handler = new Handler(Looper.getMainLooper());
        if (timeLimit == -1){
            handler.post(new Runnable() {
                @Override
                public void run() {
                    seconds++;
                    handler.postDelayed(this, 1000);
                }
            });
        }else {
            handler.post(new Runnable() {
                @Override
                public void run() {
                    seconds++;
                    if (seconds == timeLimit && pass == false) {
                        Log.d("TimingService", "The message is: " + getResources().getString(R.string.notification));
                        sendNotification();
                    }

                    handler.postDelayed(this, 1000);
                }
            });
        }

    }


}
