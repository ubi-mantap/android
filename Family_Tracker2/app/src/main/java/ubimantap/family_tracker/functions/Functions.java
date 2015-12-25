package ubimantap.family_tracker.functions;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import ubimantap.family_tracker.objects.Member;
import ubimantap.family_tracker.R;
import ubimantap.family_tracker.objects.Owner;
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

    public void login(String username, String phone) {
        Log.d(tag, "login : " + username + " (" + phone + ")");
        setOwner(username, phone);
    }

    public void register(String username, String phone) {
        Log.d(tag, "register : " + username + " (" + phone + ")");
        setOwner(username, phone);

        new RegisterTask().execute(username, phone);
    }

    public void setOwner(String username, String phone) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(context.getString(R.string.preferences_key), context.MODE_PRIVATE);

        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.putString(context.getResources().getString(R.string.preferences_username), username);
        editor.putString(context.getResources().getString(R.string.preferences_phone), phone);
        editor.commit();
    }

    public Owner getOwner() {
        SharedPreferences sharedPreferences = context.getSharedPreferences(context.getString(R.string.preferences_key), context.MODE_PRIVATE);

        String username = sharedPreferences.getString("username", "");
        String phone = sharedPreferences.getString("phone", "");

        return new Owner(username, phone);
    }

    public void setMember(String username, String phone) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(context.getString(R.string.preferences_key), context.MODE_PRIVATE);

        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();

        ArrayList<Member> members = new ArrayList<Member>();
        members.add(new Member(0, "Kamila", "", 0, 0));
        members.add(new Member(0, "Tyas", "", 0, 0));
        members.add(new Member(0, "Bobby", "", 0, 0));
        members.add(new Member(0, "Mukhlis", "", 0, 0));
        members.add(new Member(0, "Eteng", "", 0, 0));
        members.remove(username);

        JSONArray jsonArray = new JSONArray();
        for(int ii = 0; ii < members.size(); ii++) {
            try {
                JSONObject jsonObject = new JSONObject();

                jsonObject.put("pp", members.get(ii).getPp());
                jsonObject.put("name", members.get(ii).getName());
                jsonObject.put("status", members.get(ii).getStatus());
                jsonObject.put("lat", members.get(ii).getLat());
                jsonObject.put("lng", members.get(ii).getLng());

                jsonArray.put(jsonObject);
            } catch (JSONException e) {
                Log.d(tag, e.getMessage());
            }
        }

        editor.putString("member", jsonArray.toString());
        editor.commit();
    }

    public ArrayList<Member> getMember() {
        ArrayList<Member> members = new ArrayList<>();
        SharedPreferences sharedPreferences = this.context.getSharedPreferences(this.context.getString(R.string.preferences_key), this.context.MODE_PRIVATE);

        try {
            JSONArray jsonArray = new JSONArray(sharedPreferences.getString("member", ""));
            for(int ii = 0; ii < jsonArray.length(); ii++) {
                JSONObject jsonObject = jsonArray.getJSONObject(ii);

                int pp = jsonObject.getInt("pp");
                String name = jsonObject.getString("name");
                String status = jsonObject.getString("status");
                double lat = jsonObject.getDouble("lat");
                double lng = jsonObject.getDouble("lng");
                Member member = new Member(pp, name, status, lat, lng);

                members.add(member);
            }
        } catch (JSONException e) {
            Log.d(tag, e.getMessage());
        }

        return members;
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
        new NotificationsTask(context).execute(username);
    }
}