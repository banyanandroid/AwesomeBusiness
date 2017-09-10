package banyan.com.awesomebusiness.adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

import banyan.com.awesomebusiness.R;
import banyan.com.awesomebusiness.activity.Tab_Business_Profile;
import banyan.com.awesomebusiness.activity.Tab_Franchises_Profile;

/**
 * Created by User on 9/7/2017.
 */

public class FranchiseProfiles_Adapter extends BaseAdapter {
    private Activity activity;
    private Context context;
    private LinearLayout singleMessageContainer;

    private ArrayList<HashMap<String, String>> data;
    private static LayoutInflater inflater = null;

    private String[] bgColors;

    public FranchiseProfiles_Adapter(Activity a, ArrayList<HashMap<String, String>> d) {
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
            v = inflater.inflate(R.layout.list_user_franchise_profiles, null);

        TextView txt_franchise_profile_desc = (TextView) v.findViewById(R.id.list_franchise_profile_description);

        HashMap<String, String> result = new HashMap<String, String>();
        result = data.get(position);

        System.out.println("SHORT DESCCCCCCC : " + Tab_Franchises_Profile.TAG_FRANCHISE_BRAND_NAME);

        txt_franchise_profile_desc.setText(result.get(Tab_Franchises_Profile.TAG_FRANCHISE_BRAND_NAME));


        String color = bgColors[position % bgColors.length];
        txt_franchise_profile_desc.setBackgroundColor(Color.parseColor(color));

        return v;

    }

}