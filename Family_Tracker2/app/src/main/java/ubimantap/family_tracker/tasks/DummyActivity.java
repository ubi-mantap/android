package ubimantap.family_tracker.tasks;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

import ubimantap.family_tracker.R;

public class DummyActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        TextView tv = new TextView(this);
        tv.setText("Yo!");

        setContentView(tv);
    }
}
