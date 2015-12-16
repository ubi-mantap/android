package ubimantap.family_tracker;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RemoteViews;

import java.lang.reflect.Array;

import ubimantap.family_tracker.receivers.NotificationsReceiver;
import ubimantap.family_tracker.tasks.DummyActivity;
import ubimantap.family_tracker.tasks.NotificationsTask;
import ubimantap.family_tracker.tasks.RegisterTask;
import ubimantap.family_tracker.tasks.TrackersTask;
import ubimantap.family_tracker.tasks.TrackingsInitTask;
import ubimantap.family_tracker.tasks.TrackingsStartTask;
import ubimantap.family_tracker.tasks.TrackingsStopTask;

public class MemberFragment extends Fragment {
    private String tag = "MemberFragment";

    public MemberFragment(){}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_member, container, false);

        /*Log.d("MemberFragment", "call RegisterTask()");
        new RegisterTask().execute("Kamila", "081385935613");

        Log.d("MemberFragment", "call RegisterTask()");
        new RegisterTask().execute("Bobby", "089688157020");*/

        /*Log.d(tag, "call TrackingsInitTask()");
        new TrackingsInitTask().execute("Kamila", "Bobby");*/

        /*Log.d(tag, "call TrackingsStartTask()");
        new TrackingsStartTask().execute("Kamila", "Bobby", "true");*/

        /*Log.d(tag, "call TrackingsStopTask()");
        new TrackingsStopTask().execute("Kamila", "Bobby");*/

        /*Log.d(tag, "call TrackersTask()");
        new TrackersTask().execute("Bobby");*/

        /*Log.d(tag, "call TrackingsTask()");
        new TrackersTask().execute("Kamila");*/

        /*Log.d(tag, "call NotificationsTask()");
        new NotificationsTask().execute("Bobby");*/

        /*Context context = getActivity().getApplicationContext();
        SharedPreferences sharedPreferences = context.getSharedPreferences(getString(R.string.preferences_key), context.MODE_PRIVATE);

        String member = sharedPreferences.getString(getString(R.string.preferences_member), "");
        String phone = sharedPreferences.getString(getString(R.string.preferences_phone), "");
        if(member.equals("") || phone.equals("")) {
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.clear();
            editor.putString(context.getResources().getString(R.string.preferences_member), "Kamila");
            editor.putString(context.getResources().getString(R.string.preferences_phone), "081385935613");
            editor.commit();

            member = sharedPreferences.getString(getString(R.string.preferences_member), "");
            phone = sharedPreferences.getString(getString(R.string.preferences_phone), "");
        }
        Log.d(tag, member + " : " + phone);*/

        String type = "update";

        String title = "";
        String text = "";
        String ticker = "";

        String[] actions = new String[] {};

        switch (type) {
            case "trackRequest" :
                title = "Track Request";
                text = "Kamila want to track you";
                ticker = "New Track Request !";
                actions = new String[] {"AGREE", "DISAGREE"};
                break;
            case "trackResponse" :
                title = "Track Response";
                text = "Bobby accept your track request";
                ticker = "New Track Response !";
                break;
            case "update" :
                title = "Alert";
                text = "Son in rain";
                ticker = "New Alert !";
                actions = new String[] {"CALL", "SMS", "IGNORE"};
                break;
            default :
                break;
        }

        Context context = getActivity().getApplicationContext();
        NotificationCompat.Builder builder =
            new NotificationCompat.Builder(context)
                .setSmallIcon(R.drawable.ic_launcher)
                .setContentTitle(title)
                .setContentText(text)
                .setTicker(ticker);

        Intent[] intents = new Intent[actions.length];
        PendingIntent[] pendingIntents = new PendingIntent[actions.length];

        for (int ii = 0; ii < actions.length; ii++) {
            intents[ii] = new Intent(context, NotificationsReceiver.class);
            //intents[ii] = new Intent(context, MapsActivity.class);
            intents[ii].setAction(actions[ii]);
            pendingIntents[ii] = PendingIntent.getBroadcast(context, 0, intents[ii], 0);
            //pendingIntents[ii] = PendingIntent.getActivity(context, 0, intents[ii], 0);
            builder.addAction(0, actions[ii], pendingIntents[ii]);

            builder.setAutoCancel(true);
            builder.setPriority(Notification.PRIORITY_MAX);
        }

        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(0, builder.build());

        return rootView;
    }
}
