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
import banyan.com.awesomebusiness.activity.Tab_Advisor_Profile;
import banyan.com.awesomebusiness.activity.Tab_Business_Profile;

/**
 * Created by Banyan on 06-Dec-17.
 */

public class AdvisorProfiles_Adapter extends BaseAdapter {
    private Activity activity;
    private Context context;
    private LinearLayout singleMessageContainer;

    private ArrayList<HashMap<String, String>> data;
    private static LayoutInflater inflater = null;

    private String[] bgColors;

    public AdvisorProfiles_Adapter(Activity a, ArrayList<HashMap<String, String>> d) {
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
            v = inflater.inflate(R.layout.list_user_advisor_profiles, null);

        TextView adv_desc = (TextView) v.findViewById(R.id.list_adv_des);
        TextView adv_type = (TextView) v.findViewById(R.id.list_adv_interest_type);
        TextView adv_location = (TextView) v.findViewById(R.id.list_adv_location);
        TextView adv_status = (TextView) v.findViewById(R.id.list_adv_status);
        TextView adv_rating = (TextView) v.findViewById(R.id.list_adv_rating);
        ImageView prod_img = (ImageView) v.findViewById(R.id.list_advisor_profile);


        TextView adv_appeared = (TextView) v.findViewById(R.id.list_adv_appear);


        HashMap<String, String> result = new HashMap<String, String>();
        result = data.get(position);

        String str_adv_name = result.get(Tab_Advisor_Profile.TAG_ADVISOR_NAME);
        String str_adv_type = result.get(Tab_Advisor_Profile.TAG_ADVISOR_TYPE);
        String str_adv_location = result.get(Tab_Advisor_Profile.TAG_LOCATION_NAME);
        String str_adv_status = result.get(Tab_Advisor_Profile.TAG_ADVISOR_STATUS);
        String str_adv_rating = result.get(Tab_Advisor_Profile.TAG_RATING);


        String str_adv_appeared = result.get(Tab_Advisor_Profile.TAG_ADVISOR_APPEARENCE);

        adv_desc.setText(str_adv_name);
        adv_type.setText(str_adv_type);
        adv_location.setText(str_adv_location);
        adv_status.setText(str_adv_status);
        adv_rating.setText(str_adv_rating + "/5");


        adv_appeared.setText(str_adv_appeared);

        String impath = result.get(Tab_Business_Profile.TAG_IMAGE_PATH);

        Glide.with(activity)
                .load(impath)
                .placeholder(R.mipmap.ic_launcher)
                .into(prod_img);

        return v;

    }

}