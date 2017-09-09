package banyan.com.awesomebusiness.adapter;


import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.HashMap;

import banyan.com.awesomebusiness.R;
import banyan.com.awesomebusiness.activity.Tab_Business_Profile;

/**
 * Created by JoSiva on 5/25/2016.
 */

public class CartAdapter extends BaseAdapter {
    private Activity activity;
    private ArrayList<HashMap<String, String>> data;
    HashMap<String, String> result;
    int pos;
    private static LayoutInflater inflater = null;
    ViewHolder holder = null;
    Context context;
//    ImageLoader imageLoader = AppController.getInstance().getImageLoader();

    public CartAdapter(Activity a, ArrayList<HashMap<String, String>> d) {
        activity = a;
        data = d;
        inflater = (LayoutInflater) activity
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
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

    public View getView(final int position, View convertView, ViewGroup parent) {
        View v = convertView;
        if (convertView == null)
            v = inflater.inflate(R.layout.list_user_business_profiles, null);

        holder = new ViewHolder();
        pos = position;

        holder.business_desc = (TextView) v.findViewById(R.id.list_business_profile_description);

        result = new HashMap<String, String>();
        result = data.get(position);

        // no.setText(result.get(ResultFragment.TAG_TEAM_POSITION));
        holder.business_desc.setText(result.get(Tab_Business_Profile.TAG_BUSINESS_KEY));

        return v;

    }



    private class ViewHolder {
        // public TextView textViewItem;
        public TextView business_desc;
    }

}
