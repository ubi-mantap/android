package ubimantap.family_tracker.receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import ubimantap.family_tracker.functions.Functions;

public class ApplicationsReceiver extends BroadcastReceiver {
    private String tag = "ApplicationsReceiver";

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        String username = intent.getStringExtra("username");

        switch (action) {
            case "NOTIFICATIONS" :
                Log.d(tag, "NOTIFICATIONS : " + username);
                new Functions(context).notifications(username);
                break;
            case "TRACKINGS" :
                break;
            case "TRACKERS" :
                break;
            default :
                break;
        }
    }
}