package ubimantap.family_tracker.tasks;

import android.location.Location;
import android.os.Bundle;
import android.util.Log;

public class LocationTask implements android.location.LocationListener {
    @Override
    public void onLocationChanged(Location location) {
        Log.d("LocationTask", location.toString());
        new PushTask().execute(location);
    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }

    @Override
    public void onProviderEnabled(String s) {

    }

    @Override
    public void onProviderDisabled(String s) {

    }
}
