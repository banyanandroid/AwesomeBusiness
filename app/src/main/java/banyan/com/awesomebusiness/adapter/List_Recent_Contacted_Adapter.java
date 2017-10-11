package banyan.com.awesomebusiness.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

import banyan.com.awesomebusiness.R;
import banyan.com.awesomebusiness.activity.Tab_Profile_Contacted;
import banyan.com.awesomebusiness.activity.Tab_Profile_Viewed;

/**
 * Created by Banyan on 10/11/2017.
 */

public class List_Recent_Contacted_Adapter extends BaseAdapter {
    private Activity activity;
    private Context context;
    private LinearLayout singleMessageContainer;

    private ArrayList<HashMap<String, String>> data;
    private static LayoutInflater inflater = null;

    private String[] bgColors;

    public List_Recent_Contacted_Adapter(Activity a, ArrayList<HashMap<String, String>> d) {
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
            v = inflater.inflate(R.layout.list_user_contacted_activity, null);

        TextView title = (TextView) v.findViewById(R.id.useractivity_txtview_activity_title);
        TextView description = (TextView) v.findViewById(R.id.useractivity_txtview__activity_description);
        TextView date = (TextView) v.findViewById(R.id.useractivity_txtview__date);

        HashMap<String, String> result = new HashMap<String, String>();
        result = data.get(position);

        String str_title = result.get(Tab_Profile_Contacted.TAG_TYPE);
        String str_des = result.get(Tab_Profile_Contacted.TAG_DETAILS);
        String str_date = result.get(Tab_Profile_Contacted.TAG_CREATED_ON);

        title.setText("In - " + str_title);
        description.setText(str_des);
        date.setText(str_date);

        return v;

    }

}