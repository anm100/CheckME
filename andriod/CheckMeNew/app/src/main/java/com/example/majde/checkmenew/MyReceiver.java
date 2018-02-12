package com.example.majde.checkmenew;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * Created by Majde on 4/27/2017.
 */

public class MyReceiver extends BroadcastReceiver {



    @Override
    public void onReceive(Context context, Intent intent)
    {
        Intent service1 = new Intent(context, MyAlarmService.class);
        context.startService(service1);

    }




}//class
