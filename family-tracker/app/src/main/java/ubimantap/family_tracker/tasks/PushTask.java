package ubimantap.family_tracker.tasks;

import android.location.Location;
import android.os.AsyncTask;
import android.util.Log;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.InputStream;
import java.sql.Timestamp;

public class PushTask extends AsyncTask<Location, Void, String> {
    @Override
    protected String doInBackground(Location... locations) {
        Log.d("PushTask", "doInBackground");
        String result = "";
        Location location = locations[0];
        Log.d("PushTask", location.toString());
        Timestamp timeStamp = new Timestamp((int) (System.currentTimeMillis()));

        try {
            String url = "http://the-angkoters-depok.com/ft.php?action=push&lat=" + location.getLatitude() + "&lng=" + location.getLongitude() + "&ts=" + 0;
            Log.d("PushTask", url);
            HttpClient httpClient = new DefaultHttpClient();
            HttpGet httpGet = new HttpGet(url);
            HttpResponse httpResponse = httpClient.execute(httpGet);

            Log.d("PushTask", "DONE [" + location.toString() + "] " + timeStamp.toString());
        }
        catch(Exception e) {
            Log.e("PushTask", e.toString());
        }
        return result;
    }

    @Override
    protected void onPostExecute(String result) {

    }
}
