package hr.fer.wpu.simplestopwatch;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.Build;
import android.os.IBinder;

import androidx.core.app.NotificationCompat;

public class StopWatchService extends Service {

    private final String NOTIFICATION_CHANNEL_ID = "hr.fer.wpu.simplestopwatch";
    private SimpleStopWatch stopWatch;

    private final IBinder binder = new SWSBinder();

    public class SWSBinder extends Binder {
        StopWatchService getService() {
            return StopWatchService.this;
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        return binder;
    }

    public SimpleStopWatch getStopWatch() {
        return stopWatch;
    }

    @Override
    public void onCreate() {
        //start stopwatch
        stopWatch = new SimpleStopWatch();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return Service.START_NOT_STICKY;
    }

    private static final int NOTIFICATION_ID = 1;

    //place the service into the foreground
    public void foreground() {
        startForeground(NOTIFICATION_ID, createNotification());
    }

    //return the service to the background
    public void background() {
        stopForeground(true);
    }

    //notifications are required since Android 4.3 (API level 18)
    private Notification createNotification() {
        // create the NotificationChannel but only on API 26+
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "StopWatch Channel";
            String description = "Channel for StopWatch App";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(NOTIFICATION_CHANNEL_ID, name, importance);
            channel.setDescription(description);

            // register the channel with the system
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, NOTIFICATION_CHANNEL_ID);
        Notification notification = builder.setOngoing(true)
                .setSmallIcon(R.drawable.ic_launcher)
                .setContentTitle("StopWatch service is running in background")
                .setPriority(NotificationManager.IMPORTANCE_MIN)
                .setCategory(Notification.CATEGORY_SERVICE)
                .build();
        startForeground(2, notification);

        Intent resultIntent = new Intent(this, StopWatchActivity.class);
        PendingIntent resultPendingIntent = PendingIntent.getActivity(this, 0, resultIntent,
                PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(resultPendingIntent);

        return builder.build();
    }
}

