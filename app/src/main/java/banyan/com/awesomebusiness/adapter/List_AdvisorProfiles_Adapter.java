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
import banyan.com.awesomebusiness.activity.Fragment_Advisor;
import banyan.com.awesomebusiness.activity.Fragment_Home;

/**
 * Created by Banyan on 21-Nov-17.
 */

public class List_AdvisorProfiles_Adapter extends BaseAdapter {
    private Activity activity;
    private Context context;
    private LinearLayout singleMessageContainer;

    private ArrayList<HashMap<String, String>> data;
    private static LayoutInflater inflater = null;

    private String[] bgColors;

    public List_AdvisorProfiles_Adapter(Activity a, ArrayList<HashMap<String, String>> d) {
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
            v = inflater.inflate(R.layout.list_item_advisor_profile, null);

        ImageView prod_prof_img = (ImageView) v.findViewById(R.id.list_advisor_prof_profile);
        TextView adv_prof_name = (TextView) v.findViewById(R.id.list_advisor_prof_name);
        TextView adv_prof_type = (TextView) v.findViewById(R.id.list_advisor_prof_advisor_type);

        TextView adv_prof_location = (TextView) v.findViewById(R.id.list_advisor_prof_location);
        TextView adv_prof_rating = (TextView) v.findViewById(R.id.list_adv_prof_rating);


        HashMap<String, String> result = new HashMap<String, String>();
        result = data.get(position);

        String str_adv_name = result.get(Fragment_Advisor.TAG_ADVISOR_NAME);
        String str_adv_type = result.get(Fragment_Advisor.TAG_ADVISOR_TYPE);
        String str_adv_city = result.get(Fragment_Advisor.TAG_ADVISOR_CITY_ID);
        String str_adv_rating = result.get(Fragment_Advisor.TAG_RATING);

        adv_prof_name.setText(str_adv_name);
        adv_prof_type.setText(str_adv_type);
        adv_prof_location.setText(str_adv_city);
        adv_prof_rating.setText(str_adv_rating);

        String impath = result.get(Fragment_Home.TAG_IMAGE_PATH);

        Glide.with(activity)
                .load(impath)
                .placeholder(R.drawable.placeholder)
                .into(prod_prof_img);

        return v;

    }

}