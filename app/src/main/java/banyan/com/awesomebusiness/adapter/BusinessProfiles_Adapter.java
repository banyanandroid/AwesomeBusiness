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

        System.out.println("CAMEEEEEEEE : " + Tab_Business_Profile.TAG_BUSINESS_SHORT_DES);

        TextView txt_business_profile_desc = (TextView) v.findViewById(R.id.list_business_profile_description);

        HashMap<String, String> result = new HashMap<String, String>();
        result = data.get(position);

        System.out.println("SHORT DESCCCCCCC : " + Tab_Business_Profile.TAG_BUSINESS_SHORT_DES);

        txt_business_profile_desc.setText(result.get(Tab_Business_Profile.TAG_BUSINESS_SHORT_DES));

        String color = bgColors[position % bgColors.length];
        txt_business_profile_desc.setBackgroundColor(Color.parseColor(color));

        return v;

    }

}