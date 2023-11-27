package com.android.almufeed.ui.services;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import com.android.almufeed.ui.launchpad.DashboardActivity;

public class BootUpReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Intent intent1 = new Intent(context, AlarmBrodcast.class);
        intent1.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent1);
    }
}
