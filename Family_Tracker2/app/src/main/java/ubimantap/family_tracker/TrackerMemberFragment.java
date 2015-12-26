package ubimantap.family_tracker;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

import ubimantap.family_tracker.functions.Functions;
import ubimantap.family_tracker.objects.Member;
import ubimantap.family_tracker.adapters.TrackerMemberAdapter;

public class TrackerMemberFragment extends Fragment{
    private String tag = "TrackerMemberFragment";

    private Context context;

    ArrayList<Integer> pp = new ArrayList<Integer>();
    ArrayList<String> name = new ArrayList<String>();
    ArrayList<String> status = new ArrayList<String>();
    ArrayList<String> position = new ArrayList<String>();
    ArrayList<Double> lat = new ArrayList<Double>();
    ArrayList<Double> lng = new ArrayList<Double>();

    TrackerMemberAdapter memberAdapter;
    int posisinya;

    ListView memberlistView;
    ArrayList<Member> members;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        this.context = getActivity().getApplicationContext();
        members = new Functions(this.context).getMember();

        for(int ii = 0; ii < members.size(); ii++) {
            Member member = members.get(ii);

            pp.add(member.getPp());
            name.add(member.getName());
            status.add(member.getTracker());
            lat.add(member.getLat());
            lng.add(member.getLng());
            position.add(member.getPosition());
        }

        View rootView = inflater.inflate(R.layout.fragment_tracker_member, container, false);
        memberlistView = (ListView) rootView.findViewById(R.id.list_member);
        memberAdapter = new TrackerMemberAdapter(this.context, pp, name, status, lat, lng, position);
        memberlistView.setAdapter(memberAdapter);

        return rootView;
    }
}
