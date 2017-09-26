package banyan.com.awesomebusiness.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.HashMap;

import banyan.com.awesomebusiness.R;
import banyan.com.awesomebusiness.activity.Fragment_Franchise;
import banyan.com.awesomebusiness.activity.Fragment_Home;

/**
 * Created by Jo on 9/7/2017.
 */

public class List_FranchiseProfiles_Adapter extends BaseAdapter {
    private Activity activity;
    private Context context;
    private LinearLayout singleMessageContainer;

    private ArrayList<HashMap<String, String>> data;
    private static LayoutInflater inflater = null;

    private String[] bgColors;

    public List_FranchiseProfiles_Adapter(Activity a, ArrayList<HashMap<String, String>> d) {
        activity = a;
        data = d;
        bgColors = activity.getApplicationContext().getResources().getStringArray(R.array.movie_serial_bg);
        inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public int getCount() {
        return data.size();
    }

    public Object getItem(int position) {
        return position;
    }

    public long getItemId(int position) {
        return position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;
        if (convertView == null)
            v = inflater.inflate(R.layout.list_item_franchise_profile, null);

        ImageView prod_prof_img = (ImageView) v.findViewById(R.id.list_franchise_prof_profile);
        TextView bus_prof_title = (TextView) v.findViewById(R.id.list_franchise_prof_title);
        TextView bus_prof_industry = (TextView) v.findViewById(R.id.list_franchise_prof_interest_type);
        TextView bus_prof_desc = (TextView) v.findViewById(R.id.list_franchise_prof_description);
        TextView bus_prof_location = (TextView) v.findViewById(R.id.list_franchise_prof_location);

        HashMap<String, String> result = new HashMap<String, String>();
        result = data.get(position);

        String str_bus_name = result.get(Fragment_Franchise.TAG_FRANCHISE_BRAND_NAME);
        String str_bus_type = result.get(Fragment_Franchise.TAG_INDUSTRY_NAME);
        String str_bus_desc = result.get(Fragment_Franchise.TAG_FRANCHISE_BRAND_SERVICES);
        String str_bus_location = result.get(Fragment_Franchise.TAG_LOCATION_NAME);

        bus_prof_title.setText(str_bus_name);
        bus_prof_industry.setText(str_bus_type);
        bus_prof_desc.setText(str_bus_desc);
        bus_prof_location.setText(str_bus_location);

        String impath = result.get(Fragment_Home.TAG_IMAGE_PATH);

        Glide.with(activity)
                .load(impath)
                .placeholder(R.drawable.placeholder)
                .into(prod_prof_img);

        return v;

    }

}