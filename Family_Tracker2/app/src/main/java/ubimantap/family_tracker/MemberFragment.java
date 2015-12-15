package ubimantap.family_tracker;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ubimantap.family_tracker.tasks.NotificationsTask;
import ubimantap.family_tracker.tasks.RegisterTask;
import ubimantap.family_tracker.tasks.TrackersTask;
import ubimantap.family_tracker.tasks.TrackingsInitTask;
import ubimantap.family_tracker.tasks.TrackingsStartTask;
import ubimantap.family_tracker.tasks.TrackingsStopTask;

public class MemberFragment extends Fragment {
    private String tag = "MemberFragment";

    public MemberFragment(){}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_member, container, false);

        /*Log.d("MemberFragment", "call RegisterTask()");
        new RegisterTask().execute("Kamila", "081385935613");

        Log.d("MemberFragment", "call RegisterTask()");
        new RegisterTask().execute("Bobby", "089688157020");*/

        /*Log.d(tag, "call TrackingsInitTask()");
        new TrackingsInitTask().execute("Kamila", "Bobby");*/

        /*Log.d(tag, "call TrackingsStartTask()");
        new TrackingsStartTask().execute("Kamila", "Bobby", "true");*/

        /*Log.d(tag, "call TrackingsStopTask()");
        new TrackingsStopTask().execute("Kamila", "Bobby");*/

        /*Log.d(tag, "call TrackersTask()");
        new TrackersTask().execute("Bobby");*/

        /*Log.d(tag, "call TrackingsTask()");
        new TrackersTask().execute("Kamila");*/

        /*Log.d(tag, "call NotificationsTask()");
        new NotificationsTask().execute("Bobby");*/

        return rootView;
    }
}
