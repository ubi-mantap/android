package ubimantap.family_tracker.tasks;

import android.content.Context;
import android.os.AsyncTask;
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
import org.json.JSONObject;

import java.net.HttpURLConnection;
import java.util.ArrayList;

import ubimantap.family_tracker.functions.Functions;
import ubimantap.family_tracker.objects.Member;

public class TrackingsTask extends AsyncTask<String, Void, String> {
    private String tag = "TrackingsTask";
    private String endpoint = "https://family-tracker.herokuapp.com/trackings?username=";
    private Context context;

    public TrackingsTask(Context context) {
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
                JSONArray jsonArray = jsonObject.getJSONArray("trackings");

                for(int ii = 0; ii < jsonArray.length(); ii++) {
                    JSONObject tracking = jsonArray.getJSONObject(ii);

                    Log.d(tag, "username :" + tracking.getString("username"));
                    Log.d(tag, "location :" + tracking.has("location"));

                    String username = tracking.getString("username");

                    if(tracking.has("location")) {
                        JSONObject location = tracking.getJSONObject("location");

                        double lat = location.getDouble("lat");
                        double lng = location.getDouble("long");
                        String position = location.getString("name");

                        Member member = new Member(0, username, "false", "false", lat, lng, position);

                        new Functions(context).updateMember("location", member);
                    }
                }
            }
            else {
                Log.d(tag, "FAILED");
            }
            Log.d(tag, result.toString());
        } catch (Exception e) {
            Log.e(tag, e.getStackTrace().toString());
        }

    }
}
