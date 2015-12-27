package ubimantap.family_tracker.tasks;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.HttpURLConnection;
import java.util.ArrayList;

import ubimantap.family_tracker.MapsActivity;
import ubimantap.family_tracker.R;
import ubimantap.family_tracker.receivers.NotificationsReceiver;

public class NotificationsTask extends AsyncTask<String, Void, String> {
    private String tag = "NotificationsTask";
    private String endpoint = "https://family-tracker.herokuapp.com/notifications?username=";
    private Context context;

    public NotificationsTask(Context context) {
        this.context = context;
    }

    @Override
    protected String doInBackground(String... strings) {
        Log.d(tag, "doInBackground");
        String username = strings[0];
        endpoint += username;
        Log.d(tag, username);

        byte[] resultByte = null;
        String resultString = "";

        HttpClient httpClient = new DefaultHttpClient();
        HttpGet httpGet = new HttpGet(endpoint);

        try {
            Log.d(tag, "try");
            HttpResponse httpResponse = httpClient.execute(httpGet);

            StatusLine statusLine = httpResponse.getStatusLine();
            Log.d(tag, "REQUEST :" + statusLine.toString() + " [" + endpoint + "]");
            if(statusLine.getStatusCode() == HttpURLConnection.HTTP_OK){
                resultByte = EntityUtils.toByteArray(httpResponse.getEntity());
                resultString = new String(resultByte, "UTF-8");
            }
        } catch (Exception e) {
            Log.e(tag, e.getStackTrace().toString());
        }

        return resultString;
    }

    @Override
    protected void onPostExecute(String result) {
        try {
            JSONObject jsonObject = new JSONObject(result);
            if(jsonObject.getString("ok").equals("true")) {
                Log.d(tag, "SUCCESS");
                createNotifications(jsonObject);
            }
            else {
                Log.d(tag, "FAILED");
            }
            Log.d(tag, result.toString());
        } catch (Exception e) {
            Log.e(tag, e.getStackTrace().toString());
        }
    }

    public void createNotifications(JSONObject jsonObject) {
        try {
            JSONArray jsonArray = jsonObject.getJSONArray("notifications");
            for (int ii = 0; ii < jsonArray.length(); ii++) {
                JSONObject notification = (JSONObject) jsonArray.get(ii);

                String type = notification.getString("type");
                String message = notification.getString("message");
                JSONObject data = (JSONObject) notification.get("data");

                String title = "";
                String ticker = "";

                String[] actions = new String[] {};

                switch (type) {
                    case "update" :
                        title = "Alert";
                        ticker = "New Alert !";
                        actions = new String[] {"CALL", "SMS", "IGNORE"};
                        break;
                    case "trackRequest" :
                        title = "Track Request";
                        ticker = "New Track Request !";
                        actions = new String[] {"AGREE", "DISAGREE"};
                        break;
                    case "trackResponse" :
                        title = "Track Response";
                        ticker = "New Track Response !";
                        //do get status == true ? update sharedpref : break
                        break;
                    case "trackStop" :
                        title = "Track Stop";
                        ticker = "New Track Stop !";
                        //do update sharedpref
                        break;
                    default :
                        break;
                }

                NotificationCompat.Builder builder =
                    new NotificationCompat.Builder(this.context)
                        .setSmallIcon(R.drawable.ic_track)
                        .setContentTitle(title)
                        .setContentText(message)
                        .setTicker(ticker);

                Intent intent = new Intent(this.context, MapsActivity.class);
                PendingIntent pendingIntent = PendingIntent.getActivity(this.context, 0, intent, 0);
                builder.setContentIntent(pendingIntent);

                Intent[] intents = new Intent[actions.length];
                PendingIntent[] pendingIntents = new PendingIntent[actions.length];

                for (int jj = 0; jj < actions.length; jj++) {
                    intents[jj] = new Intent(this.context, NotificationsReceiver.class);
                    intents[jj].setAction(actions[jj]);
                    switch(type) {
                        case "update" :
                            intents[jj].putExtra("phone", data.getString("phone"));
                            break;
                        case "trackRequest" :
                            intents[jj].putExtra("trackerUsername", data.getString("trackerUsername"));
                            intents[jj].putExtra("trackedUsername", data.getString("trackedUsername"));
                            break;
                        default :
                            break;
                    }
                    pendingIntents[jj] = PendingIntent.getBroadcast(this.context, 0, intents[jj], 0);
                    builder.addAction(0, actions[jj], pendingIntents[jj]);
                }

                builder.setPriority(Notification.PRIORITY_MAX);
                builder.setAutoCancel(true);

                NotificationManager notificationManager = (NotificationManager) this.context.getSystemService(Context.NOTIFICATION_SERVICE);
                notificationManager.notify(0, builder.build());
            }
        } catch (JSONException e) {
            Log.e(tag, e.getMessage());
        }
    }
}
