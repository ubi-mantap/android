package ubimantap.family_tracker.receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import ubimantap.family_tracker.MapsActivity;
import ubimantap.family_tracker.tasks.DummyActivity;

public class NotificationsReceiver extends BroadcastReceiver {
    private String tag = "NotificationsReceiver";

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();

        Log.d(tag, action);
    }
}
