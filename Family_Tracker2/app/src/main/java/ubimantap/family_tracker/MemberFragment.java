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

import ubimantap.family_tracker.objects.Member;

public class MemberFragment extends Fragment implements AdapterView.OnItemClickListener {
    private String tag = "MemberFragment";

    private Context context;

    ArrayList<Integer> pp = new ArrayList<Integer>();
    ArrayList<String> name = new ArrayList<String>();
    ArrayList<String> status= new ArrayList<String>();
    ArrayList<String> position= new ArrayList<String>();
    ArrayList<Double> lat= new ArrayList<Double>();
    ArrayList<Double> lng= new ArrayList<Double>();

    Member memberAdapter;
    int posisinya;

    ListView memberlistView;
    ArrayList<Member> members;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        this.context = getActivity().getApplicationContext();
        //members = new Functions(this.context).getMember();
        //Log.d(tag, members.size() + "");

<<<<<<< HEAD
        pp.add(R.drawable.ic_launcher);
        pp.add(R.drawable.ic_launcher);
        name.add("tyas");
        name.add("kamila");
        status.add("no track");
        status.add("track");
        position.add("UI");
        position.add("Bogor");
        lat.add(0.1);
        lat.add(1.2);
        lng.add(0.3);
        lng.add(1.2);
=======
        members = new Functions(this.context).getMember();
<<<<<<< HEAD
>>>>>>> 370a60d233d2a295b85433bc45ac0810104903bd
=======
        Log.d(tag, members.size() + "");
>>>>>>> parent of 370a60d... location flow

        View rootView = inflater.inflate(R.layout.fragment_member, container, false);
        memberlistView = (ListView) rootView.findViewById(R.id.list_member);
        memberAdapter = new Member(this.context,pp,name,status,lat,lng, position);
        memberlistView.setAdapter(memberAdapter);
        memberlistView.setOnItemClickListener(this);

        return rootView;
    }


    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        Log.d("lala", "kamu");
    }

    /*
    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, final int i, long l) {
        posisinya = i;
        Toast.makeText(getActivity().getBaseContext(), "selected", Toast.LENGTH_SHORT).show();
        Log.e("track", "lalala");
        final Button bt_track = (Button) view.findViewById(R.id.btTrack);
        final Button bt_stop = (Button) view.findViewById(R.id.btStop);

        //if (memberAdapter.getStatus(i).equals("no track")) {
        bt_track.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bt_stop.setVisibility(View.VISIBLE);
                bt_track.setVisibility(View.INVISIBLE);
                memberAdapter.setStatus(i, "track");
                memberlistView.setAdapter(memberAdapter);
                Log.e("track", "lalala");
            }
        });
        // }
        //else {
        bt_stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bt_track.setVisibility(View.VISIBLE);
                bt_stop.setVisibility(View.INVISIBLE);
                memberAdapter.setStatus(i, "no track");
                memberlistView.setAdapter(memberAdapter);
            }
        });
        //}
    }

     */

}
