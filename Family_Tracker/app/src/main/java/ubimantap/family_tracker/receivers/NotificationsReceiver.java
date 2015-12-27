package ubimantap.family_tracker.receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.widget.Toast;

import ubimantap.family_tracker.MapsActivity;
import ubimantap.family_tracker.TrackerMemberFragment;
import ubimantap.family_tracker.functions.Functions;

public class NotificationsReceiver extends BroadcastReceiver {
    private String tag = "NotificationsReceiver";

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();

        String phone = "";
        String trackerUsername = "";
        String trackedUsername = "";

        switch (action) {
            case "CALL" :
                phone = intent.getStringExtra("phone");
                Log.d(tag, action + " :" + phone);

                Intent callIntent = new Intent();

                callIntent.setAction(Intent.ACTION_CALL);
                callIntent.setData(Uri.parse("tel:" + phone));
                callIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                context.startActivity(callIntent);

                break;
            case "SMS" :
                phone = intent.getStringExtra("phone");
                Log.d(tag, action + " :" + phone);

                Intent smsIntent = new Intent();

                smsIntent.setAction(Intent.ACTION_VIEW);
                smsIntent.setData(Uri.parse("sms:" + phone));
                smsIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                context.startActivity(smsIntent);

                break;
            case "IGNORE" :
                Log.d(tag, action);
                break;
            case "AGREE" :
                trackerUsername = intent.getStringExtra("trackerUsername");
                trackedUsername = intent.getStringExtra("trackedUsername");
                Log.d(tag, action + trackerUsername + " -> " + trackedUsername);

                new Functions(context).trackingsStart(trackerUsername, trackedUsername, "true");
                //do update sharedpref
                break;
            case "DISAGREE" :
                trackerUsername = intent.getStringExtra("trackerUsername");
                trackedUsername = intent.getStringExtra("trackedUsername");
                Log.d(tag, action + trackerUsername + " -> " + trackedUsername);

                new Functions(context).trackingsStart(trackerUsername, trackedUsername, "false");

                break;
            default :
                break;
        }
    }
}
