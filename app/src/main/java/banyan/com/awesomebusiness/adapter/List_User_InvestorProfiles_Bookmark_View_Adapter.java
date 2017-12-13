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
import banyan.com.awesomebusiness.activity.Tab_User_BusinessProfile_Bookmarked;
import banyan.com.awesomebusiness.activity.Tab_User_InvestorProfile_Bookmarked;

/**
 * Created by Banyan on 07-Dec-17.
 */

public class List_User_InvestorProfiles_Bookmark_View_Adapter extends BaseAdapter {
    private Activity activity;
    private Context context;
    private LinearLayout singleMessageContainer;

    private ArrayList<HashMap<String, String>> data;
    private static LayoutInflater inflater = null;

    private String[] bgColors;

    public List_User_InvestorProfiles_Bookmark_View_Adapter(Activity a, ArrayList<HashMap<String, String>> d) {
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
        TextView txt_user_connect_status = (TextView) v.findViewById(R.id.txt_user_connect_status);

        HashMap<String, String> result = new HashMap<String, String>();
        result = data.get(position);

        ///////////
        String str_username = result.get(Tab_User_InvestorProfile_Bookmarked.TAG_USER_NAME);
        String str_user_connect_status = result.get(Tab_User_InvestorProfile_Bookmarked.TAG_USER_CONNECT);
        String str_user_type = result.get(Tab_User_InvestorProfile_Bookmarked.TAG_USER_TYPE);

        /////////////
        txt_username.setText(str_username);

        try {

            if (str_user_connect_status != null && !str_user_connect_status.isEmpty() && !str_user_connect_status.equals("null")) {

                if (str_user_connect_status.equals("0")) {

                    txt_user_connect_status.setText("Tap to Connect with this user");

                } else if (str_user_connect_status.equals("1") && str_user_type.equals("1")) {

                    txt_user_connect_status.setText("Waiting For Approval");

                } else if (str_user_connect_status.equals("1") && str_user_type.equals("2")) {

                    txt_user_connect_status.setText("Approve Connect");

                } else if (str_user_connect_status.equals("2") && str_user_type.equals("1")) {

                    txt_user_connect_status.setText("Connect Successful");

                } else if (str_user_connect_status.equals("2") && str_user_type.equals("2")) {

                    txt_user_connect_status.setText("Approved");

                } else {

                    txt_user_connect_status.setText("Unknown Status");

                }
            } else {

                txt_user_connect_status.setText("Unkonwn Status");

            }

        } catch (Exception e) {

        }

        return v;

    }

}