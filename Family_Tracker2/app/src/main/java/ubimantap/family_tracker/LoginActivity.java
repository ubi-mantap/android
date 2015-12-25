package ubimantap.family_tracker;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import ubimantap.family_tracker.functions.Functions;
import ubimantap.family_tracker.listeners.ButtonsListener;

import static android.view.View.inflate;

public class LoginActivity extends FragmentActivity {
    private String tag = "LoginActivity";
    private Context context;
    private View view;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        this.context = this;
        this.view = this.getWindow().getDecorView().findViewById(android.R.id.content);

        Button button = (Button) findViewById(R.id.btlogin);
        button.setOnClickListener(new ButtonsListener(this.context, this.view));
    }
}
