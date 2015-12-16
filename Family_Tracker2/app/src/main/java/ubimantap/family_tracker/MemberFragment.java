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

import ubimantap.family_tracker.functions.Functions;
import ubimantap.family_tracker.receivers.NotificationsReceiver;
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

        // notification - action
        /*String type = "update";

        String title = "";
        String text = "";
        String ticker = "";

        String[] actions = new String[] {};

        switch (type) {
            case "update" :
                title = "Alert";
                text = "Son in rain";
                ticker = "New Alert !";
                actions = new String[] {"CALL", "SMS", "IGNORE"};
                break;
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

        Intent intent = new Intent(context, MapsActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);
        builder.setContentIntent(pendingIntent);

        Intent[] intents = new Intent[actions.length];
        PendingIntent[] pendingIntents = new PendingIntent[actions.length];

        for (int ii = 0; ii < actions.length; ii++) {
            intents[ii] = new Intent(context, NotificationsReceiver.class);
            intents[ii].setAction(actions[ii]);
            pendingIntents[ii] = PendingIntent.getBroadcast(context, 0, intents[ii], 0);
            builder.addAction(0, actions[ii], pendingIntents[ii]);
        }

        builder.setPriority(Notification.PRIORITY_MAX);
        builder.setAutoCancel(true);

        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(0, builder.build());*/

        return rootView;
    }
}
