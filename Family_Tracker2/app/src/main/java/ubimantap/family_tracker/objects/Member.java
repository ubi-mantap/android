package ubimantap.family_tracker.objects;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import ubimantap.family_tracker.R;

public class Member {
    private String tag = "Member";

    private int pp;
    private String name;
    private String status;
    private double lat;
    private double lng;
    private String position;

    /*TextView nameView;
    TextView positionView;
    ImageView profpicView;
    Button bt_track_stop;
    Button bt_stop;
    Button bt_track;*/

    public Member(int pp, String name, String status, double lat, double lng, String position) {
        this.pp = pp;
        this.name = name;
        this.status = status;
        this.lat = lat;
        this.lng = lng;
        this.position = position;
    }

    public int getPp() {
        return pp;
    }

    public void setPp(int pp) {
        this.pp = pp;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    @Override
    public boolean equals(Object object) {
        Log.d(tag, "EQUALS");
        if(object == null) return false;
        if(object == this) return true;

        Member member = (Member) object;
        return member.getName().equals(this.getName());
    }

    @Override
    public String toString() {
        return getName();
    }

    /*public View getView(int viewType, View convertView, ViewGroup parent) {
        convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_member, parent, false); //Inflating the layout
        nameView = (TextView) convertView.findViewById(R.id.nameText);
        positionView = (TextView) convertView.findViewById(R.id.positionText);
        if (status.equals("no track")){
            bt_track_stop = (Button) convertView.findViewById(R.id.btTrack);
            bt_stop = (Button) convertView.findViewById(R.id.btStop);
            bt_stop.setVisibility(View.INVISIBLE);
            bt_track_stop.setVisibility(View.VISIBLE);
        }
        else {
            bt_track_stop = (Button) convertView.findViewById(R.id.btStop);
            bt_track = (Button) convertView.findViewById(R.id.btTrack);
            bt_track.setVisibility(View.INVISIBLE);
            bt_track_stop.setVisibility(View.VISIBLE);
        }
        profpicView = (ImageView) convertView.findViewById(R.id.profpicnya);// Creating ImageView object with the id of ImageView from item_row.xml                                               // setting holder id as 1 as the object being populated are of type item row
        nameView.setText(name);
        profpicView.setImageResource(pp);
        return convertView;
    }


    public void track(View view) {
        //status ganti dari "no track -> track"

    }

    public void stop(View view) {
        //status ganti dari "track -> no track"
    }*/
}
