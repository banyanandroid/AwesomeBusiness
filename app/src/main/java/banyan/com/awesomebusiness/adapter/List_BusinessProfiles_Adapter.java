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
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.ArrayList;
import java.util.HashMap;

import banyan.com.awesomebusiness.R;
import banyan.com.awesomebusiness.activity.Fragment_Home;
import banyan.com.awesomebusiness.activity.Tab_Business_Profile;

/**
 * Created by Jo on 9/7/2017.
 */

public class List_BusinessProfiles_Adapter extends BaseAdapter {
    private Activity activity;
    private Context context;
    private LinearLayout singleMessageContainer;

    private ArrayList<HashMap<String, String>> data;
    private static LayoutInflater inflater = null;

    private String[] bgColors;

    public List_BusinessProfiles_Adapter(Activity a, ArrayList<HashMap<String, String>> d) {
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
            v = inflater.inflate(R.layout.list_item_business_profile, null);

        ImageView prod_prof_img = (ImageView) v.findViewById(R.id.list_business_prof_profile);
        TextView bus_prof_title = (TextView) v.findViewById(R.id.list_bus_prof_title);
        TextView bus_prof_industry = (TextView) v.findViewById(R.id.list_bus_prof_interest_type);
        TextView bus_prof_desc = (TextView) v.findViewById(R.id.list_bus_prof_description);
        TextView bus_prof_location = (TextView) v.findViewById(R.id.list_bus_prof_location);
        TextView bus_prof_rating = (TextView) v.findViewById(R.id.list_bus_prof_rating);
        TextView bus__prof_runrate = (TextView) v.findViewById(R.id.list_bus_prof_runrate);
        TextView bus_prof_ebitda = (TextView) v.findViewById(R.id.list_bus_prof_ebitda);
        TextView bus_prof_financial_invest = (TextView) v.findViewById(R.id.list_bus_prof_financial_inves);


        HashMap<String, String> result = new HashMap<String, String>();
        result = data.get(position);

        String str_bus_name = result.get(Fragment_Home.TAG_BUSINESS_PROF_SHORT_DES);
        String str_bus_type = result.get(Fragment_Home.TAG_BUSINESS_PROF_INTEREST_NAME);
        String str_bus_desc = result.get(Fragment_Home.TAG_BUISNESS_PROF_DESCRIPTION);
        String str_bus_location = result.get(Fragment_Home.TAG_LOCATION_NAME);
        String str_bus_rating = result.get(Fragment_Home.TAG_BUSINESS_PROF_RATING);
        String str_bus_runrate = result.get(Fragment_Home.TAG_BUSINESS_PROF_YEARLY_SALES);
        String str_bus_ebitda = result.get(Fragment_Home.TAG_BUSINESS_PROF_EBITDA);
        String str_bus_financial_invest = result.get(Fragment_Home.TAG_BUSINESS_PROF_TENTATIVE_PRICE);

        bus_prof_title.setText(str_bus_name);
        bus_prof_industry.setText(str_bus_type);
        bus_prof_desc.setText(str_bus_desc);
        bus_prof_location.setText(str_bus_location);
        bus_prof_rating.setText(str_bus_rating);
        bus__prof_runrate.setText(str_bus_runrate);
        bus_prof_ebitda.setText(str_bus_ebitda);
        bus_prof_financial_invest.setText(str_bus_financial_invest);

        String impath = result.get(Fragment_Home.TAG_IMAGE_PATH);

        Glide.with(activity)
                .load(impath)
                .placeholder(R.drawable.placeholder)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(prod_prof_img);

        return v;

    }

}