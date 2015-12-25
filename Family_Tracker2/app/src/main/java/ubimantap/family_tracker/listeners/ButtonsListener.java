package ubimantap.family_tracker.listeners;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import ubimantap.family_tracker.MapsActivity;
import ubimantap.family_tracker.R;
import ubimantap.family_tracker.functions.Functions;

public class ButtonsListener implements View.OnClickListener {
    private String tag = "ButtonsListener";
    private Context context;
    private View view;

    public ButtonsListener(Context context, View view) {
        this.context = context;
        this.view = view;
    }

    @Override
    public void onClick(View view) {
        EditText editTextUsername = (EditText) this.view.findViewById(R.id.username);
        EditText editTextPhone = (EditText) this.view.findViewById(R.id.phone);

        String username = editTextUsername.getText().toString();
        String phone = editTextPhone.getText().toString();

        // new Functions(this.context).login(username, phone);
        new Functions(this.context).register(username, phone);
        new Functions(this.context).setMember(username, phone);

        ((Activity) this.context).finish();
        Intent intent = new Intent(this.context, MapsActivity.class);
        this.context.startActivity(intent);
    }
}
