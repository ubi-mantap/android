package ubimantap.family_tracker.objects;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import ubimantap.family_tracker.R;

public class Member {
    private int pp;
    private String name;
    private String status;
    private double lat;
    private double lng;

    public Member(int pp, String name, String status, double lat, double lng) {
        this.pp = pp;
        this.name = name;
        this.status = status;
        this.lat = lat;
        this.lng = lng;
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

    @Override
    public boolean equals(Object object) {
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
