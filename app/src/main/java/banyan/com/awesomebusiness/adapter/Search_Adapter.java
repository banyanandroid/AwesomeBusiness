package banyan.com.awesomebusiness.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.ArrayList;
import java.util.HashMap;

import banyan.com.awesomebusiness.R;
import banyan.com.awesomebusiness.activity.Activity_UserProfile;



public class Search_Adapter extends BaseAdapter {
    private Activity activity;
    private Context context;
    private LinearLayout singleMessageContainer;

    private ArrayList<HashMap<String, String>> data;
    private static LayoutInflater inflater = null;

    private String[] bgColors;

    public Search_Adapter(Activity a, ArrayList<HashMap<String, String>> d) {
        activity = a;
        data = d;
//        bgColors = activity.getApplicationContext().getResources().getStringArray(R.array.movie_serial_bg);
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

        TextView user_activity_title = (TextView) v.findViewById(R.id.useractivity_txtview_activity_title);
        TextView user_activity_desc = (TextView) v.findViewById(R.id.useractivity_txtview__activity_description);



        HashMap<String, String> result = new HashMap<String, String>();
        result = data.get(position);

        try {
           // user_activity_title.setText(result.get(Activity_UserProfile.TAG_COLLEGE_NAME));
          //  user_activity_desc.setText(result.get(Activity_UserProfile.TAG_COLLEGE_FOUNDED_YEAR));

        } catch (Exception e) {

        }

        return v;

    }

}