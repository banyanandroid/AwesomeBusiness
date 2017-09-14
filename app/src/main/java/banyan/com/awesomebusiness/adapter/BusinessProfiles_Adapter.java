package banyan.com.awesomebusiness.adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
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
import banyan.com.awesomebusiness.activity.Tab_Business_Profile;
import banyan.com.awesomebusiness.activity.Tab_Investor_Profile;

/**
 * Created by User on 9/7/2017.
 */

public class BusinessProfiles_Adapter extends BaseAdapter {
    private Activity activity;
    private Context context;
    private LinearLayout singleMessageContainer;

    private ArrayList<HashMap<String, String>> data;
    private static LayoutInflater inflater = null;

    private String[] bgColors;

    public BusinessProfiles_Adapter(Activity a, ArrayList<HashMap<String, String>> d) {
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
            v = inflater.inflate(R.layout.list_user_business_profiles, null);

        TextView bus_desc = (TextView) v.findViewById(R.id.list_bus_des);
        TextView bus_type = (TextView) v.findViewById(R.id.list_bus_interest_type);
        TextView bus_location = (TextView) v.findViewById(R.id.list_bus_location);
        TextView bus_status = (TextView) v.findViewById(R.id.list_bus_status);
        ImageView prod_img = (ImageView) v.findViewById(R.id.list_business_profile);

        HashMap<String, String> result = new HashMap<String, String>();
        result = data.get(position);

        String str_bus_name = result.get(Tab_Business_Profile.TAG_BUSINESS_SHORT_DES);
        String str_bus_type = result.get(Tab_Business_Profile.TAG_BUSINESS_INTEREST_NAME);
        String str_bus_location = result.get(Tab_Business_Profile.TAG_LOCATION_NAME);
        String str_bus_status = result.get(Tab_Business_Profile.TAG_BUSINESS_STATUS);

        bus_desc.setText(str_bus_name);
        bus_type.setText(str_bus_type);
        bus_location.setText(str_bus_location);
        bus_status.setText(str_bus_status);

        String impath = result.get(Tab_Business_Profile.TAG_IMAGE_PATH);

        Glide.with(activity)
                .load(impath)
                .placeholder(R.mipmap.ic_launcher)
                .into(prod_img);


        return v;

    }

}