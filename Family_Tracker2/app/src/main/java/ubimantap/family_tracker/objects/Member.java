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
    private String tracker;
    private String tracking;
    private double lat;
    private double lng;
    private String position;

    public Member(int pp, String name, String tracker, String tracking, double lat, double lng, String position) {
        this.pp = pp;
        this.name = name;
        this.tracker = tracker;
        this.tracking = tracking;
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

    public String getTracking() {
        return tracking;
    }

    public void setTracking(String tracking) {
        this.tracking = tracking;
    }

    public String getTracker() {
        return tracker;
    }

    public void setTracker(String tracker) {
        this.tracker = tracker;
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
}
