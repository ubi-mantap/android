package ubimantap.family_tracker;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import ubimantap.family_tracker.functions.Functions;
import ubimantap.family_tracker.objects.Member;

public class MemberFragment extends Fragment {
    private String tag = "MemberFragment";

    private Context context;

    ListView memberlistView;
    ArrayList<Member> members;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        this.context = getActivity().getApplicationContext();

        members = new Functions(this.context).getMember();

        View rootView = inflater.inflate(R.layout.fragment_member, container, false);
        memberlistView = (ListView) rootView.findViewById(R.id.list_member);
        memberlistView.setAdapter(new ArrayAdapter<Member>(container.getContext(), R.layout.item_member, R.id.nameText, members));

        return rootView;
    }
}
