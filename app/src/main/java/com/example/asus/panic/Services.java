package com.example.asus.panic;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.IBinder;
import android.os.PowerManager;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.view.Window;
import android.view.WindowManager;

public class Services extends android.app.Service {
    private  MediaPlayer player;
    private Context context;
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    //public int onStartCommand(Intent intent, int flags, int startId) {
//    player = MediaPlayer.create(this, Settings.System.DEFAULT_RINGTONE_URI);
//    player.setLooping(true);
//    player.start();
    // addNotification();
    //return START_STICKY;
    //}
    public int onStartCommand(Intent intent, int flags, int startId) {
        final IntentFilter filter = new IntentFilter(Intent.ACTION_SCREEN_ON);
        filter.addAction(Intent.ACTION_SCREEN_OFF);
        filter.addAction(Intent.ACTION_USER_PRESENT);

        final BroadcastReceiver mReceiver = new ScreenReceiver(getApplicationContext());
        registerReceiver(mReceiver, filter);
        addNotification();
        return START_STICKY;
    }
    public class LocalBinder extends Binder {
        Service getService() {
            return Services.this;
        }
    }
    private void addNotification() {
        NotificationCompat.Builder builder =
                new NotificationCompat.Builder(this)
                        .setSmallIcon(R.drawable.small)
                        .setContentTitle("Panic is active")
                        .setContentText("This is notification");

        Intent notificationIntent = new Intent(this, MainActivity.class);
        PendingIntent contentIntent = PendingIntent.getActivity(this, 0, notificationIntent,
                PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setOngoing(true);
        builder.setContentIntent(contentIntent);

        // Add as notification
        NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        manager.notify(87657, builder.build());
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        //player.stop();
        NotificationManager nManager = ((NotificationManager) this.getSystemService(Context.NOTIFICATION_SERVICE));
        nManager.cancelAll();
    }
}
