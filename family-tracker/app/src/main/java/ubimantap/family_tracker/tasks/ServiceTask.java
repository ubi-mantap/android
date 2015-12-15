package ubimantap.family_tracker.tasks;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

public class ServiceTask extends Service{
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
