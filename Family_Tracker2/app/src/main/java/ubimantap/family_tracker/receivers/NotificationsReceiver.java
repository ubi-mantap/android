package ubimantap.family_tracker.receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.widget.Toast;

import ubimantap.family_tracker.MapsActivity;
import ubimantap.family_tracker.MemberFragment;
import ubimantap.family_tracker.functions.Functions;

public class NotificationsReceiver extends BroadcastReceiver {
    private String tag = "NotificationsReceiver";

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();

        switch (action) {
            case "CALL" :
                Log.d(tag, action);
                Intent callIntent = new Intent();

                callIntent.setAction(Intent.ACTION_CALL);
                callIntent.setData(Uri.parse("tel:081385935613"));
                callIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                context.startActivity(callIntent);

                break;
            case "SMS" :
                Log.d(tag, action);
                break;
            case "AGREE" :
                Log.d(tag, action);
                break;
            case "DISAGREE" :
                Log.d(tag, action);
                break;
            case "IGNORE" :
                Log.d(tag, action);
                break;
            default :
                break;
        }
    }
}
