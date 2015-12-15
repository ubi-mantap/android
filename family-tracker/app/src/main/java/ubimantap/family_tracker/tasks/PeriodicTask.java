package ubimantap.family_tracker.tasks;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class PeriodicTask extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        context.startService(new Intent(context, ServiceTask.class));
    }
}
