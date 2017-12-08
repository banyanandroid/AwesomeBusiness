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
import banyan.com.awesomebusiness.activity.Tab_User_Profile_Bookmarked;

/**
 * Created by Banyan on 07-Dec-17.
 */

public class List_UserProfiles_Bookmarks_View_Adapter extends BaseAdapter {
    private Activity activity;
    private Context context;
    private LinearLayout singleMessageContainer;

    private ArrayList<HashMap<String, String>> data;
    private static LayoutInflater inflater = null;

    private String[] bgColors;

    public List_UserProfiles_Bookmarks_View_Adapter(Activity a, ArrayList<HashMap<String, String>> d) {
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

            ///////////
            v = inflater.inflate(R.layout.list_userprofiles_bookmarks_view, null);

        ///////////
        TextView txt_username = (TextView) v.findViewById(R.id.txt_user_name);

        HashMap<String, String> result = new HashMap<String, String>();
        result = data.get(position);

        ///////////
        String str_username = result.get(Tab_User_Profile_Bookmarked.TAG_USER_NAME);

        /////////////
        txt_username.setText(str_username);


        return v;

    }

}