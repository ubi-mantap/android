package ubimantap.family_tracker.functions;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

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
import ubimantap.family_tracker.tasks.TracksTask;

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

    public void setMember(String username) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(context.getString(R.string.preferences_key), context.MODE_PRIVATE);

        ArrayList<String> usernames = new ArrayList<>();
        usernames.add("Kamila");
        usernames.add("Tyas");
        usernames.add("Bobby");
        usernames.add("Mukhlis");
        usernames.add("Eteng");
        Log.d(tag, "remove :" + username);
        usernames.remove(username);

        ArrayList<Member> members = new ArrayList<>();
        for(int ii = 0; ii < usernames.size(); ii++) {
            members.add(new Member(0, usernames.get(ii), "", 0, 0, ""));
        }

        JSONArray jsonArray = new JSONArray();
        for(int ii = 0; ii < members.size(); ii++) {
            try {
                JSONObject jsonObject = new JSONObject();

                jsonObject.put("pp", members.get(ii).getPp());
                jsonObject.put("name", members.get(ii).getName());
                jsonObject.put("status", members.get(ii).getStatus());
                jsonObject.put("lat", members.get(ii).getLat());
                jsonObject.put("lng", members.get(ii).getLng());
                jsonObject.put("position", members.get(ii).getPosition());

                jsonArray.put(jsonObject);
            } catch (JSONException e) {
                Log.d(tag, e.getMessage());
            }
        }

        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("member", jsonArray.toString());
        editor.commit();

        Log.d(tag, "setMember : done set member");
    }

    public ArrayList<Member> getMember() {
        Log.d(tag, "in getMember");
        ArrayList<Member> members = new ArrayList<>();
        SharedPreferences sharedPreferences = this.context.getSharedPreferences(this.context.getString(R.string.preferences_key), this.context.MODE_PRIVATE);

        try {
            JSONArray jsonArray = new JSONArray(sharedPreferences.getString("member", ""));
            Log.d(tag, jsonArray.toString());
            for(int ii = 0; ii < jsonArray.length(); ii++) {
                JSONObject jsonObject = jsonArray.getJSONObject(ii);

                int pp = jsonObject.getInt("pp");
                String name = jsonObject.getString("name");
                String status = jsonObject.getString("status");
                double lat = jsonObject.getDouble("lat");
                double lng = jsonObject.getDouble("lng");
                String position = jsonObject.getString("position");

                Member member = new Member(pp, name, status, lat, lng, position);

                members.add(member);
            }
        } catch (JSONException e) {
            Log.d(tag, e.getMessage());
        }

        return members;
    }

    public void updateMemberStatus(String username, String status) {

    }

    public void debugMember() {
        SharedPreferences sharedPreferences = context.getSharedPreferences(context.getString(R.string.preferences_key), context.MODE_PRIVATE);
        Log.d(tag, "debugMember : " + sharedPreferences.getString("member", ""));
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

    public void tracks(String username) {
        new TracksTask(context).execute(username);
    }

    public void setTrackers(JSONObject jsonObject) {
        try {
            Log.d(tag, "setTrackers : " + jsonObject.toString());
            HashSet<String> set = new HashSet<>();

            JSONArray jsonArray = jsonObject.getJSONArray("trackers");
            for(int ii = 0; ii < jsonArray.length(); ii++) {
                JSONObject tracker = jsonArray.getJSONObject(ii);
                set.add(tracker.getString("username"));
            }

            String trackers = "false";
            ArrayList<Member> members = getMember();
            for(int ii = 0; ii < members.size(); ii++) {
                Member member = members.get(ii);
                if(set.contains(member.getName())) {
                    member.setStatus("trackers");
                    members.set(ii, member);

                    trackers = "true";
                }
            }

            JSONArray membersNew = new JSONArray();
            for(int ii = 0; ii < members.size(); ii++) {
                try {
                    JSONObject memberNew = new JSONObject();

                    memberNew.put("pp", members.get(ii).getPp());
                    memberNew.put("name", members.get(ii).getName());
                    memberNew.put("status", members.get(ii).getStatus());
                    memberNew.put("lat", members.get(ii).getLat());
                    memberNew.put("lng", members.get(ii).getLng());
                    memberNew.put("position", members.get(ii).getPosition());

                    membersNew.put(memberNew);
                } catch (JSONException e) {
                    Log.d(tag, e.getMessage());
                }
            }

            SharedPreferences sharedPreferences = context.getSharedPreferences(context.getString(R.string.preferences_key), context.MODE_PRIVATE);

            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString(context.getResources().getString(R.string.preferences_trackers), trackers);
            editor.putString(context.getResources().getString(R.string.preferences_member), membersNew.toString());
            editor.commit();

            if(trackers.equals("true")) {
                //do scheduling push location to server
                Log.d(tag, "setTrackers : SCHEDULING PUSH LAT-LNG TO SERVER");
            }
        } catch (JSONException e) {
            Log.d(tag, e.getMessage());
        }
    }

    public void setTrackings(JSONObject jsonObject) {
        try {
            Log.d(tag, "setTrackings : " + jsonObject.toString());

            HashMap<String, JSONObject> map = new HashMap<>();

            JSONArray jsonArray = jsonObject.getJSONArray("trackings");
            for(int ii = 0; ii < jsonArray.length(); ii++) {
                JSONObject tracking = jsonArray.getJSONObject(ii);

                String username = tracking.getString("username");
                map.put(username, tracking);
            }

            ArrayList<Member> members = getMember();
            String trackings = "false";
            for(int ii = 0; ii < members.size(); ii++) {
                Member member = members.get(ii);
                if(map.containsKey(member.getName())) {
                    JSONObject trackingJSON = map.get(member.getName());

                    JSONObject locationJSON = trackingJSON.getJSONObject("location");
                    String lat = locationJSON.getString("lat");
                    String lng = locationJSON.getString("long");
                    String position = locationJSON.getString("name");

                    member.setLat(Double.parseDouble(lat));
                    member.setLng(Double.parseDouble(lng));
                    member.setPosition(position);

                    member.setStatus("trackings");

                    trackings = "true";
                }
            }

            JSONArray membersNew = new JSONArray();
            for(int ii = 0; ii < members.size(); ii++) {
                try {
                    JSONObject memberNew = new JSONObject();

                    memberNew.put("pp", members.get(ii).getPp());
                    memberNew.put("name", members.get(ii).getName());
                    memberNew.put("status", members.get(ii).getStatus());
                    memberNew.put("lat", members.get(ii).getLat());
                    memberNew.put("lng", members.get(ii).getLng());
                    memberNew.put("position", members.get(ii).getPosition());

                    membersNew.put(memberNew);
                } catch (JSONException e) {
                    Log.d(tag, e.getMessage());
                }
            }

            SharedPreferences sharedPreferences = context.getSharedPreferences(context.getString(R.string.preferences_key), context.MODE_PRIVATE);

            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString(context.getResources().getString(R.string.preferences_trackings), trackings);
            editor.putString(context.getResources().getString(R.string.preferences_member), membersNew.toString());
            editor.commit();

            if(trackings.equals("true")) {
                // do scheduling pull location from server + show in map
                Log.d(tag, "setTrackings : SCHEDULING PULL LAT-LNG FROM SERVER + SHOW IN MAP");
            }
        } catch (JSONException e) {
            Log.d(tag, e.getMessage());
        }
    }

    public void notifications(String username) {
        Log.d(tag, "notificationsTask : " + username);
        new NotificationsTask(context).execute(username);
    }
}