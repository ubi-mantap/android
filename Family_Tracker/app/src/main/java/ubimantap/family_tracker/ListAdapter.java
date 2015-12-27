package ubimantap.family_tracker;

/**
 * Created by user on 09/12/2015.
 */
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class ListAdapter extends BaseAdapter {

    private static final int TYPE_HEADER = 0;  // Declaring Variable to Understand which View is being worked on
    // IF the view under inflation and population is header or Item
    private static final int TYPE_ITEM = 1;

    private static int[] mNavTitles; // String Array to store the passed titles Value from MainActivity.java
    private int mIcons[];       // Int Array to store the passed icons resource value from MainActivity.java
    private Context context;
    private String name;        //String Resource for header View Name
    private int profilee;
    // Creating a ViewHolder which extends the RecyclerView View Holder
    // ViewHolder are used to to store the inflated views in order to recycle them

    ListAdapter(Context context, int Titles[],int Icons[],String Name,int Profile){ // MyAdapter Constructor with titles and icons parameter
        // titles, icons, name, email, profile pic are passed from the main activity as we
        this.context = context;
        mNavTitles = Titles;                //have seen earlier
        mIcons = Icons;
        name = Name;
        profilee = Profile;                     //here we assign those passed values to the values we declared here
        //in adapter
    }

    @Override
    public int getCount() {
        return mNavTitles.length;
    }

    @Override
    public Object getItem(int position) {
        return mNavTitles[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    TextView textView;
    ImageView imageView;
    TextView Name;
    ImageView profile;
    int Holderid;

    @Override
    public View getView(int viewType, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_row, parent, false);
        }

        if (viewType == TYPE_HEADER) {
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.header, parent, false); //Inflating the layout
            Name = (TextView) convertView.findViewById(R.id.name);         // Creating Text View object from header.xml for name
            profile = (ImageView) convertView.findViewById(R.id.circleView);// Creating Image view object from header.xml for profile pic
            Holderid = 0;                                                // Setting holder id = 0 as the object being populated are of type header view
            profile.setImageResource(profilee);           // Similarly we set the resources for header view
            Name.setText(name);
        }
        else {
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_row, parent, false); //Inflating the layout
            textView = (TextView) convertView.findViewById(R.id.rowText); // Creating TextView object with the id of textView from item_row.xml
            imageView = (ImageView) convertView.findViewById(R.id.rowIcon);// Creating ImageView object with the id of ImageView from item_row.xml
            Holderid = 1;                                               // setting holder id as 1 as the object being populated are of type item row
            textView.setText(mNavTitles[viewType - 1]); // Setting the Text with the array of our Titles
            imageView.setImageResource(mIcons[viewType - 1]);// Settimg the image with array of our icons
            //inflate your layout and pass it to view holder
        }
        return convertView;
    }
}