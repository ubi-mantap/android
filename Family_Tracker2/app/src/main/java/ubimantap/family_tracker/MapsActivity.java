package ubimantap.family_tracker;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.HashMap;

import ubimantap.family_tracker.functions.Functions;
import ubimantap.family_tracker.objects.Member;
import ubimantap.family_tracker.objects.Owner;
import ubimantap.family_tracker.receivers.ApplicationsReceiver;

public class MapsActivity extends FragmentActivity implements GoogleMap.OnMarkerClickListener {
    private String tag = "MapsActivity";
    private Owner owner;
    private ArrayList<Member> members;

    private int TITLES[] = {R.string.tracker_map, R.string.tracking_member, R.string.tracker_member,R.string.tracker_member};
    private int ICONS[] = {R.drawable.ic_marker, R.drawable.ic_member, R.drawable.ic_member, R.drawable.ic_member};
    private int HEADER[] = {R.drawable.ic_marker, R.drawable.ic_member, R.drawable.ic_member, R.drawable.ic_member };
    private String NAME = "Family Tracker";
    private int PROFILE = R.drawable.ic_track;

    private DrawerLayout Drawer;
    private ActionBarDrawerToggle mDrawerToggle;

    private Toolbar toolbar;
    private ListView mListView;
    private ListAdapter mAdapter;

    private TextView textAction;
    private ImageView imgAction;

    private GoogleMap googleMap;
    private MapFragment mapFragment;
    private MarkerOptions markerOptions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        toolbar = (Toolbar) findViewById(R.id.tool_bar);
        imgAction = (ImageView) findViewById(R.id.iconact);
        textAction = (TextView) findViewById(R.id.txtact);
        setTitle(TITLES[0], HEADER[0]);

        mListView = (ListView) findViewById(R.id.list_slidermenu);
        mAdapter = new ListAdapter(getApplicationContext(),TITLES,ICONS,NAME,PROFILE);
        mListView.setAdapter(mAdapter);
        mListView.setOnItemClickListener(new SlideMenuClickListener());


        Drawer = (DrawerLayout) findViewById(R.id.DrawerLayout);
        mDrawerToggle = new ActionBarDrawerToggle(this,Drawer,toolbar,R.string.openDrawer,R.string.closeDrawer){
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
            }
        };
        Drawer.setDrawerListener(mDrawerToggle);
        mDrawerToggle.syncState();

        mapFragment = (MapFragment) getFragmentManager().findFragmentById(R.id.map);
        googleMap = mapFragment.getMap();
        googleMap.setMyLocationEnabled(true);

        googleMap.setOnMarkerClickListener(this);

        CameraUpdate center = CameraUpdateFactory.newLatLng(new LatLng(-6.3994934, 106.8192634));
        CameraUpdate zoom =  CameraUpdateFactory.zoomTo(15);
        googleMap.moveCamera(center);
        googleMap.animateCamera(zoom);

        owner = new Functions(this).getOwner();
        members = new Functions(this).getMember();

        scheduling("NOTIFICATIONS", 5 * 1000);
        scheduling("TRACKS", 5 * 60 * 1000);
        //scheduling("DEBUG", 5 * 1000);
        setMemberOnMap();
    }

    public void setMemberOnMap() {
        Log.d(tag, "setMemberOnMap");
        googleMap.clear();

        for(int ii = 0; ii < members.size(); ii++) {
            Member member = members.get(ii);
            Log.d(tag, "map :" + member.toString());
            if(member.getTracking().equals("true") && !member.getPosition().equals("")) {
                markerOptions = new MarkerOptions();
                markerOptions.position(new LatLng(member.getLat(), member.getLng()));
                markerOptions.title(member.getName());

                googleMap.addMarker(markerOptions);

                CameraUpdate center = CameraUpdateFactory.newLatLng(new LatLng(member.getLat(), member.getLng()));
                CameraUpdate zoom =  CameraUpdateFactory.zoomTo(15);

                googleMap.moveCamera(center);
                googleMap.animateCamera(zoom);
            }
        }
    }

    /*
    @Override
    protected void onResume() {
        super.onResume();
        setUpMapIfNeeded();
    }

    private void setUpMapIfNeeded() {
        if (mMap == null) {
            mMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map)).getMap();
            if (mMap != null) {
                setUpMap();
            }
        }
    }

    private void setUpMap() {
        mMap.addMarker(new MarkerOptions().position(new LatLng(0, 0)).title("Marker"));
    }
    */

    @Override
    public boolean onMarkerClick(Marker marker) {
        if(marker.isInfoWindowShown()) {
            marker.hideInfoWindow();
        }
        else {
            marker.showInfoWindow();
        }

        return false;
    }

    private class SlideMenuClickListener implements ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            displayView(position);
        }
    }

    private void displayView(int position) {
        Fragment fragment = null;
        switch (position) {
            case 0:
                break;
            case 1:
                finish();
                Intent i = new Intent(MapsActivity.this,MapsActivity.class);
                startActivity(i);
                break;
            case 2:
                fragment = new TrackingMemberFragment();
                break;
            case 3:
                fragment = new TrackerMemberFragment();
                break;
            default:
                break;
        }

        if (fragment != null) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.frame_container , fragment).commit();

            mListView.setItemChecked(position, true);
            mListView.setSelection(position);
            setTitle(TITLES[position-1],HEADER[position-1]);
            Drawer.closeDrawer(mListView);
        } else {
            Log.e(tag, "[ERROR] in creating fragment");
        }
    }

    public void setTitle(int txt, int img) {
        imgAction.setImageResource(img);
        textAction.setText(txt);
    }

    public void scheduling(String action, int delay) {
        Log.d(tag, "scheduleNotification : " + action + " [" + delay + "]");

        Intent intent = new Intent(this, ApplicationsReceiver.class);
        intent.setAction(action);
        intent.putExtra("username", this.owner.getUsername());
        intent.putExtra("phone", this.owner.getPhone());

        Log.d(tag, this.owner.getUsername() + " [" + this.owner.getPhone() + "]");

        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        alarmManager.setRepeating(AlarmManager.ELAPSED_REALTIME, SystemClock.elapsedRealtime(), delay, pendingIntent);
    }
}
