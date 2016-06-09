package info.androidhive.project.controller;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

/**
 * Created by VietAnh on 6/7/2016.
 */
public class FeedReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Log.e("Broadcast", "__________GCM Broadcast");

        Bundle extras = intent.getExtras();
        Intent i = new Intent("broadCastName");
        // Data you need to pass to activity
        i.putExtra("message", extras.getString("TEST New Feed"));
        context.sendBroadcast(i);
    }
}
