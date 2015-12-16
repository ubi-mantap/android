package ubimantap.family_tracker;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RemoteViews;

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

        /*Context context = getActivity().getApplicationContext();
        NotificationCompat.Builder builder =
            new NotificationCompat.Builder(context)
                .setSmallIcon(R.drawable.common_signin_btn_icon_dark)
                .setContentTitle("My notification")
                .setContentText("Hello World!");

        NotificationCompat.InboxStyle inboxStyle = new NotificationCompat.InboxStyle();
        String[] events = new String[6];
        inboxStyle.setBigContentTitle("Event tracker details:");
        for (int i=0; i < events.length; i++) {
            inboxStyle.addLine(events[i]);
        }
        builder.setStyle(inboxStyle);

        Intent intent = new Intent(context, MapsActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);

        builder.setContentIntent(pendingIntent);

        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(1, builder.build());*/

        return rootView;
    }
}
