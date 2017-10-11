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
import banyan.com.awesomebusiness.activity.Tab_Profile_Bookmarks;
import banyan.com.awesomebusiness.activity.Tab_Profile_Viewed;

/**
 * Created by Banyan on 10/10/2017.
 */

public class List_Bookmarks_Adapter extends BaseAdapter {
    private Activity activity;
    private Context context;
    private LinearLayout singleMessageContainer;

    private ArrayList<HashMap<String, String>> data;
    private static LayoutInflater inflater = null;

    private String[] bgColors;

    public List_Bookmarks_Adapter(Activity a, ArrayList<HashMap<String, String>> d) {
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
            v = inflater.inflate(R.layout.list_user_bookmarks, null);

        TextView details = (TextView) v.findViewById(R.id.userbookmarks_txt_details);
        TextView type = (TextView) v.findViewById(R.id.userbookmarks_txt_type);
        TextView created_on = (TextView) v.findViewById(R.id.userbookmarks_txt_created_on);

        HashMap<String, String> result = new HashMap<String, String>();
        result = data.get(position);

        String str_details = result.get(Tab_Profile_Bookmarks.TAG_TYPE);
        String str_type = result.get(Tab_Profile_Bookmarks.TAG_DETAILS);
        String str_created_on = result.get(Tab_Profile_Bookmarks.TAG_CREATED_ON);

        details.setText("In - " + str_details);
        type.setText(str_type);
        created_on.setText(str_created_on);

        return v;

    }

}