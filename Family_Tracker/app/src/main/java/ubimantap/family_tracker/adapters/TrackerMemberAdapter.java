package ubimantap.family_tracker.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import ubimantap.family_tracker.R;
import ubimantap.family_tracker.functions.Functions;
import ubimantap.family_tracker.objects.Owner;

public class TrackerMemberAdapter extends BaseAdapter implements View.OnClickListener {
    private String tag = "TrackerMemberAdapater";

    private Context context;
    private ArrayList<Integer> pp;
    private ArrayList<String> name;
    private ArrayList<String> status;
    private ArrayList<Double> lat;
    private ArrayList<Double> lng;
    private ArrayList<String> position;

    TextView nameView;
    TextView positionView;
    ImageView ppView;
    Button btActive;
    Button btPassive;

    public TrackerMemberAdapter(Context context, ArrayList<Integer> pp, ArrayList<String> name, ArrayList<String> status, ArrayList<Double> lat, ArrayList<Double> lng, ArrayList<String> position) {
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

    public void setStatus(int i, String str) {
        status.set(i, str);
    }

    @Override
    public View getView(int viewType, View convertView, ViewGroup parent) {
        convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.tracker_item_member, parent, false);
        nameView = (TextView) convertView.findViewById(R.id.nameText);
        positionView = (TextView) convertView.findViewById(R.id.positionText);
        btActive = (Button) convertView.findViewById(R.id.btActive);
        btPassive = (Button) convertView.findViewById(R.id.btPassive);

        if (status.get(viewType).equals("true")){
            btActive.setVisibility(View.VISIBLE);
            btPassive.setVisibility(View.INVISIBLE);

        }
        else {
            btPassive.setVisibility(View.VISIBLE);
            btActive.setVisibility(View.INVISIBLE);
        }

        btActive.setTag(viewType);
        btPassive.setTag(viewType);

        btActive.setOnClickListener(this);

        ppView = (ImageView) convertView.findViewById(R.id.profpicnya);
        nameView.setText(name.get(viewType));
        positionView.setText(position.get(viewType));
        ppView.setImageResource(pp.get(viewType));
        return convertView;
    }

    @Override
    public void onClick(View view) {
        Integer positionItem = (Integer) view.getTag();

        notifyDataSetChanged();
        Log.d(tag, "TRACKINGS/STOP");

        Owner owner = new Functions(context).getOwner();
        new Functions(context).trackingsStop(name.get(positionItem), owner.getUsername());
    }
}