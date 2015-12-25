package ubimantap.family_tracker;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;

public class LoginActivity extends FragmentActivity {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }

    public void signIn(View view) {
        //if database contains username
        finish();
        Intent intent = new Intent(this, MapsActivity.class);
        startActivity(intent);
    }

    public void signUp(View view) {
        //if user does not have account, prefer to make new account
        finish();
        Intent intent = new Intent(this, MapsActivity.class);
        startActivity(intent);
    }

}
