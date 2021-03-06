package ubimantap.family_tracker.functions;

import android.content.Context;
import android.content.SharedPreferences;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

import ubimantap.family_tracker.R;
import ubimantap.family_tracker.objects.Member;
import ubimantap.family_tracker.objects.Owner;
import ubimantap.family_tracker.tasks.NotificationsTask;
import ubimantap.family_tracker.tasks.RegisterTask;
import ubimantap.family_tracker.tasks.TrackingsInitTask;
import ubimantap.family_tracker.tasks.TrackingsLogTask;
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
        String dummy = "";
        if(username.length() > "Dummy".length() && username.substring(username.length() - "Dummy".length()).equals("Dummy")) {
            dummy = "Dummy";
        }

        SharedPreferences sharedPreferences = context.getSharedPreferences(context.getString(R.string.preferences_key), context.MODE_PRIVATE);

        ArrayList<String> usernames = new ArrayList<>();
        usernames.add("Kamila" + dummy);
        usernames.add("Tyas" + dummy);
        usernames.add("Bobby" + dummy);
        usernames.add("Mukhlis" + dummy);
        usernames.add("Eteng" + dummy);
        usernames.add("Irvi" + dummy);

        Log.d(tag, "remove :" + username);
        usernames.remove(username);

        ArrayList<Member> members = new ArrayList<>();
        for(int ii = 0; ii < usernames.size(); ii++) {
           members.add(new Member(R.drawable.ic_person, usernames.get(ii), "false", "false", 0, 0, ""));
        }

        JSONArray jsonArray = new JSONArray();
        for(int ii = 0; ii < members.size(); ii++) {
            try {
                JSONObject jsonObject = new JSONObject();

                jsonObject.put("pp", members.get(ii).getPp());
                jsonObject.put("name", members.get(ii).getName());
                jsonObject.put("tracker", members.get(ii).getTracker());
                jsonObject.put("tracking", members.get(ii).getTracking());
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
        ArrayList<Member> members = new ArrayList<>();
        SharedPreferences sharedPreferences = this.context.getSharedPreferences(this.context.getString(R.string.preferences_key), this.context.MODE_PRIVATE);

        try {
            JSONArray jsonArray = new JSONArray(sharedPreferences.getString("member", ""));
            for(int ii = 0; ii < jsonArray.length(); ii++) {
                JSONObject jsonObject = jsonArray.getJSONObject(ii);

                int pp = jsonObject.getInt("pp");
                String name = jsonObject.getString("name");
                String tracker = jsonObject.getString("tracker");
                String tracking = jsonObject.getString("tracking");
                double lat = jsonObject.getDouble("lat");
                double lng = jsonObject.getDouble("lng");
                String position = jsonObject.getString("position");

                Member member = new Member(pp, name, tracker, tracking, lat, lng, position);

                members.add(member);
            }
        } catch (JSONException e) {
            Log.d(tag, e.getMessage());
        }

        return members;
    }

    public void updateMember(String action, Member member) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(context.getString(R.string.preferences_key), context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        String data = sharedPreferences.getString("member", "");
        try {
            JSONArray jsonArray = new JSONArray(data);

            for(int ii = 0; ii < jsonArray.length(); ii++) {
                JSONObject jsonObject = jsonArray.getJSONObject(ii);

                String username = jsonObject.getString("name");
                if(username.equals(member.getName())) {
                    JSONObject newMember = new JSONObject();

                    newMember.put("pp", jsonObject.getString("pp"));
                    newMember.put("name", jsonObject.getString("name"));
                    newMember.put("tracker", jsonObject.getString("tracker"));
                    newMember.put("tracking", jsonObject.getString("tracking"));
                    newMember.put("lat", jsonObject.getString("lat"));
                    newMember.put("lat", jsonObject.getString("lng"));
                    newMember.put("position", jsonObject.getString("position"));

                    switch(action) {
                        case "trackers" :
                            Log.d(tag, "update trackers");
                            break;
                        case "trackings" :
                            Log.d(tag, "update trackings");
                            break;
                        case "location" :
                            Log.d(tag, "update locations");
                            newMember.put("lat", member.getLat());
                            newMember.put("lng", member.getLng());
                            newMember.put("position", member.getPosition());
                            Log.d(tag, "member :" + member.toString());
                            break;
                        default :
                            break;
                    }

                    jsonArray.put(ii, newMember);
                    break;
                }
            }

            editor.putString("member", jsonArray.toString());
            editor.commit();
        }
        catch (JSONException e) {
            Log.e(tag, e.getMessage());
        }
    }

    public void debugMember() {
        SharedPreferences sharedPreferences = context.getSharedPreferences(context.getString(R.string.preferences_key), context.MODE_PRIVATE);
        Log.d(tag, "debugMember : " + sharedPreferences.getString("member", ""));
    }

    public void trackingsInit(String trackerUsername, String trackedUsername) {
        Log.d(tag, "trackingsInit : " + trackerUsername + " -> " + trackedUsername);
        new TrackingsInitTask().execute(trackerUsername, trackedUsername);
    }

    public void trackingsStart(String trackerUsername, String trackedUsername, String status) {
        Log.d(tag, "trackingsStartTask : " + trackerUsername + " -> " + trackedUsername + " (" + status + ")");
        new TrackingsStartTask().execute(trackerUsername, trackedUsername, status);
    }

    public void trackingsStop(String trackerUsername, String trackedUsername) {
        Log.d(tag, "trackingsStopTask : " + trackerUsername + " -> " + trackedUsername);
        new TrackingsStopTask().execute(trackerUsername, trackedUsername);
    }

    public void trackingsLog(String username, String lat, String lng) {
        Log.d(tag, "trackingsLog : " + username + " [" + lat + ", " + lng + "]");
        new TrackingsLogTask().execute(username, lat, lng);
    }

    public void trackings(String username) {
        Log.d(tag, "trackings : " + username);
        new TrackingsTask(context).execute(username);
    }

    public void mapTracks(String username) {
        Owner owner = getOwner();

        SharedPreferences sharedPreferences = context.getSharedPreferences(context.getString(R.string.preferences_key), context.MODE_PRIVATE);

        String trackers = sharedPreferences.getString("trackers", "");
        String trackings = sharedPreferences.getString("trackings", "");

        if(trackers.equals("true")) {
            LocationManager locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
            String provider = locationManager.getBestProvider(new Criteria(), true);
            Location location = locationManager.getLastKnownLocation(provider);

            if(location != null) {
                double lat = location.getLatitude();
                double lng = location.getLongitude();

                Log.d(tag, "SEND : " + owner.getUsername() + " [" + lat + ", " + lng + "]");
                trackingsLog(owner.getUsername(), "" + lat, "" + lng);
            }
        }

        if(trackings.equals("true")) {
            Log.d(tag, "GET : " + owner.getUsername());
            trackings(username);
        }
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
                    member.setTracker("true");
                    trackers = "true";
                }
                else {
                    member.setTracker("false");
                }
                members.set(ii, member);
            }

            JSONArray membersNew = new JSONArray();
            for(int ii = 0; ii < members.size(); ii++) {
                try {
                    JSONObject memberNew = new JSONObject();

                    memberNew.put("pp", members.get(ii).getPp());
                    memberNew.put("name", members.get(ii).getName());
                    memberNew.put("tracker", members.get(ii).getTracker());
                    memberNew.put("tracking", members.get(ii).getTracking());
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
        } catch (JSONException e) {
            Log.d(tag, e.getMessage());
        }
    }

    public void setTrackings(JSONObject jsonObject) {
        try {
            Log.d(tag, "setTrackings : " + jsonObject.toString());
            HashSet<String> set = new HashSet<>();

            JSONArray jsonArray = jsonObject.getJSONArray("trackings");
            for(int ii = 0; ii < jsonArray.length(); ii++) {
                JSONObject tracking = jsonArray.getJSONObject(ii);
                set.add(tracking.getString("username"));
            }

            String trackings = "false";
            ArrayList<Member> members = getMember();
            for(int ii = 0; ii < members.size(); ii++) {
                Member member = members.get(ii);
                if(set.contains(member.getName())) {
                    member.setTracking("true");
                    trackings = "true";
                }
                else {
                    member.setTracking("false");
                }
                members.set(ii, member);
            }

            JSONArray membersNew = new JSONArray();
            for(int ii = 0; ii < members.size(); ii++) {
                try {
                    JSONObject memberNew = new JSONObject();

                    memberNew.put("pp", members.get(ii).getPp());
                    memberNew.put("name", members.get(ii).getName());
                    memberNew.put("tracker", members.get(ii).getTracker());
                    memberNew.put("tracking", members.get(ii).getTracking());
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
        } catch (JSONException e) {
            Log.d(tag, e.getMessage());
        }
    }

    public void notifications(String username) {
        Log.d(tag, "notificationsTask : " + username);
        new NotificationsTask(context).execute(username);
    }

    public void debug(String username) {
        LocationManager locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        String provider = locationManager.getBestProvider(new Criteria(), true);
        Location location = locationManager.getLastKnownLocation(provider);

        if(location != null) {
            Log.d(tag, "location :" + location);
        }
    }
}