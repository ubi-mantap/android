package ubimantap.family_tracker;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by user on 25/12/2015.
 */

public class Member {
    private String name, status, position;
    private int profpic;

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

    public String getPosition() {
        return position;
    }

    public void setPosition(String time) {
        this.position = position;
    }

    public int getProfpic() {
        return profpic;
    }

    public void setId(int id) {
        this.profpic = profpic;
    }

    public Member(int profpic, String name, String status, String position) {
        this.profpic = profpic;
        this.name = name;
        this.status = status;
        this.position = position;
    }

    TextView nameView;
    TextView positionView;
    ImageView profpicView;
    Button bt_track_stop;
    Button bt_stop;
    Button bt_track;

    public View getView(int viewType, View convertView, ViewGroup parent) {
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
        profpicView.setImageResource(profpic);
        return convertView;
    }


    public void track(View view) {
        //status ganti dari "no track -> track"

    }

    public void stop(View view) {
        //status ganti dari "track -> no track"
    }

}
