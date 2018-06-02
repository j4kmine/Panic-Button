package com.example.asus.panic;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * Created by ASUS on 4/30/2018.
 */

public class autostart extends BroadcastReceiver
{
    public void onReceive(Context context, Intent arg1)
    {
        Intent intent = new Intent(context,Services.class);
        context.startService(intent);

    }
}
