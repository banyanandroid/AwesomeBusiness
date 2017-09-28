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
import banyan.com.awesomebusiness.activity.Activity_UserProfile;
import banyan.com.awesomebusiness.activity.Fragment_Home;

/**
 * Created by Jo on 9/7/2017.
 */

public class List_RecentActivities_Adapter extends BaseAdapter {
    private Activity activity;
    private Context context;
    private LinearLayout singleMessageContainer;

    private ArrayList<HashMap<String, String>> data;
    private static LayoutInflater inflater = null;

    private String[] bgColors;

    public List_RecentActivities_Adapter(Activity a, ArrayList<HashMap<String, String>> d) {
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
            v = inflater.inflate(R.layout.list_user_latest_activity, null);

        TextView title = (TextView) v.findViewById(R.id.useractivity_txtview_activity_title);
        TextView description = (TextView) v.findViewById(R.id.useractivity_txtview__activity_description);
        TextView date = (TextView) v.findViewById(R.id.useractivity_txtview__date);



        HashMap<String, String> result = new HashMap<String, String>();
        result = data.get(position);

        String str_title = result.get(Activity_UserProfile.TAG_TYPE);
        String str_des = result.get(Activity_UserProfile.TAG_DETAILS);
        String str_date = result.get(Activity_UserProfile.TAG_CREATED_ON);

        title.setText("New activity in Place and Search " + str_title);
        description.setText(str_des);
        date.setText(str_date);

        return v;

    }

}