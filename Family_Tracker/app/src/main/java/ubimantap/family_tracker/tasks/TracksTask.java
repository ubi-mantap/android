package ubimantap.family_tracker.tasks;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

import java.net.HttpURLConnection;
import java.util.ArrayList;

import ubimantap.family_tracker.functions.Functions;

public class TracksTask extends AsyncTask<String, Void, ArrayList<String>> {
    private String tag = "TracksTask";
    private ArrayList<String> endpoints = new ArrayList<String>();
    private Context context;

    public TracksTask(Context context) {
        this.context = context;
        endpoints.add("https://family-tracker.herokuapp.com/trackers?username=");
        endpoints.add("https://family-tracker.herokuapp.com/trackings?username=");
    }

    @Override
    protected ArrayList<String> doInBackground(String... strings) {
        Log.d(tag, "doInBackground");
        String username = strings[0];

        ArrayList<String> results = new ArrayList<String>();

        for(int ii = 0; ii < endpoints.size(); ii++) {
            byte[] resultByte = null;

            HttpClient httpClient = new DefaultHttpClient();
            HttpGet httpGet = new HttpGet(endpoints.get(ii) + username);

            try {
                Log.d(tag, "try");
                HttpResponse httpResponse = httpClient.execute(httpGet);

                StatusLine statusLine = httpResponse.getStatusLine();
                Log.d(tag, "REQUEST :" + statusLine.toString() + " [" + endpoints.get(ii) + "]");
                if(statusLine.getStatusCode() == HttpURLConnection.HTTP_OK){
                    resultByte = EntityUtils.toByteArray(httpResponse.getEntity());
                    results.add(new String(resultByte, "UTF-8"));
                }
            } catch (Exception e) {
                Log.e(tag, e.getStackTrace().toString());
            }
        }

        return results;
    }

    @Override
    protected void onPostExecute(ArrayList<String> results) {
        try {
            if(results.size() == 2) {
                JSONObject trackersJSON = new JSONObject(results.get(0));
                JSONObject trackingsJSON = new JSONObject(results.get(1));

                if(trackersJSON.getString("ok").equals("true") && trackingsJSON.getString("ok").equals("true")) {
                    new Functions(context).setTrackers(trackersJSON);
                    new Functions(context).setTrackings(trackingsJSON);
                }
                else {
                    Log.d(tag, "FAILED");
                }
            }
            else {
                Log.d(tag, "FAILED");
            }
        } catch (Exception e) {
            Log.e(tag, e.getStackTrace().toString());
        }
    }
}
