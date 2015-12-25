package ubimantap.family_tracker.objects;

<<<<<<< HEAD
<<<<<<< HEAD
import android.content.Context;
=======
>>>>>>> 370a60d233d2a295b85433bc45ac0810104903bd
import android.util.Log;
=======
>>>>>>> parent of 370a60d... location flow
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import ubimantap.family_tracker.R;

<<<<<<< HEAD
public class Member extends BaseAdapter implements View.OnClickListener {

    private Context context;
    private ArrayList<Integer> pp;
    private ArrayList<String> name;
    private ArrayList<String> status;
    private ArrayList<Double> lat;
    private ArrayList<Double> lng;
    private ArrayList<String> position;
=======
public class Member {
    private int pp;
    private String name;
    private String status;
    private double lat;
    private double lng;
    private String position;
>>>>>>> 370a60d233d2a295b85433bc45ac0810104903bd

    TextView nameView;
    TextView positionView;
    ImageView profpicView;
    Button bt_track_stop;
    Button bt_stop;
    Button bt_track;

    public Member(Context context, ArrayList<Integer> pp, ArrayList<String> name, ArrayList<String> status, ArrayList<Double> lat, ArrayList<Double> lng, ArrayList<String> position) {
        this.context = context;
        this.pp = pp;
        this.name = name;
        this.status = status;
        this.lat = lat;
        this.lng = lng;
        this.position = position;
    }

    @Override
    public int getCount() {
        return name.size();
    }

    @Override
    public Object getItem(int i) {
        return name.get(i)+ " " + position.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    public String getStatus(int i) {
        return status.get(i);
    }

<<<<<<< HEAD
    public void setStatus(int i, String str) {
        status.set(i, str);
=======
    @Override
    public boolean equals(Object object) {
        if(object == null) return false;
        if(object == this) return true;

        Member member = (Member) object;
        return member.getName().equals(this.getName());
>>>>>>> 370a60d233d2a295b85433bc45ac0810104903bd
    }

    @Override
    public View getView(int viewType, View convertView, ViewGroup parent) {
        convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_member, parent, false); //Inflating the layout
        nameView = (TextView) convertView.findViewById(R.id.nameText);
        positionView = (TextView) convertView.findViewById(R.id.positionText);
        bt_track = (Button) convertView.findViewById(R.id.btTrack);
        bt_stop = (Button) convertView.findViewById(R.id.btStop);

        if (status.get(viewType).equals("no track")){
            bt_stop.setVisibility(View.INVISIBLE);
            bt_track.setVisibility(View.VISIBLE);

        }
        else {
            bt_track.setVisibility(View.INVISIBLE);
            bt_stop.setVisibility(View.VISIBLE);
        }
        bt_track.setTag(viewType);
        bt_stop.setTag(viewType);
        //bt_track.setOnClickListener(this);
        //bt_stop.setOnClickListener(this);

        profpicView = (ImageView) convertView.findViewById(R.id.profpicnya);// Creating ImageView object with the id of ImageView from item_row.xml                                               // setting holder id as 1 as the object being populated are of type item row
        nameView.setText(name.get(viewType));
        positionView.setText(position.get(viewType));
        profpicView.setImageResource(pp.get(viewType));
        return convertView;
    }

    @Override
    public void onClick(View view) {
        Log.e("onclick", "onclick");
        Integer posisinya = (Integer) view.getTag();
        switch(view.getId())
        {
            case R.id.btTrack:
                status.set(posisinya, "track");
                bt_track.setVisibility(View.INVISIBLE);
                bt_stop.setVisibility(View.VISIBLE);
                Log.e("track", status.get(posisinya));
                break;
            case R.id.btStop:
                status.set(posisinya, "no track");
                bt_stop.setVisibility(View.INVISIBLE);
                bt_track.setVisibility(View.VISIBLE);
                Log.e("stop", status.get(posisinya));
                break;
        }
    }
}
