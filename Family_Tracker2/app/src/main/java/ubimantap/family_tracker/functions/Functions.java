package ubimantap.family_tracker.functions;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import ubimantap.family_tracker.R;
import ubimantap.family_tracker.tasks.NotificationsTask;
import ubimantap.family_tracker.tasks.RegisterTask;
import ubimantap.family_tracker.tasks.TrackersTask;
import ubimantap.family_tracker.tasks.TrackingsInitTask;
import ubimantap.family_tracker.tasks.TrackingsStartTask;
import ubimantap.family_tracker.tasks.TrackingsStopTask;
import ubimantap.family_tracker.tasks.TrackingsTask;

public class Functions {
    private String tag = "Functions";
    private Context context;

    public Functions(Context context) {
        this.context = context;
    }

    public void register(String username, String phone) {
        Log.d(tag, "register : " + username + " (" + phone + ")");

        SharedPreferences sharedPreferences = context.getSharedPreferences(context.getString(R.string.preferences_key), context.MODE_PRIVATE);

        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.putString(context.getResources().getString(R.string.preferences_username), username);
        editor.putString(context.getResources().getString(R.string.preferences_phone), phone);
        editor.commit();

        new RegisterTask().execute(username, phone);

        Log.d(tag, username + " : " + phone);
    }

    public void trackingsInit(String trackerUsername, String trackedUsername) {
        Log.d(tag, "trackingsInit : " + trackedUsername + " -> " + trackedUsername);
        new TrackingsInitTask().execute(trackerUsername, trackedUsername);
    }

    public void trackingsStart(String trackerUsername, String trackedUsername, String status) {
        Log.d(tag, "trackingsStartTask : " + trackedUsername + " -> " + trackedUsername + " (" + status + ")");
        new TrackingsStartTask().execute(trackerUsername, trackedUsername, status);
    }

    public void trackingsStop(String trackerUsername, String trackedUsername) {
        Log.d(tag, "trackingsStopTask : " + trackedUsername + " -> " + trackedUsername);
        new TrackingsStopTask().execute(trackerUsername, trackedUsername);
    }

    public void trackers(String username) {
        Log.d(tag, "trackersTask : " + username);
        new TrackersTask().execute(username);
    }

    public void trackings(String username) {
        Log.d(tag, "trackingsTask : " + username);
        new TrackingsTask().execute(username);
    }

    public void notifications(String username) {
        Log.d(tag, "notificationsTask : " + username);
        new NotificationsTask().execute(username);
    }
}