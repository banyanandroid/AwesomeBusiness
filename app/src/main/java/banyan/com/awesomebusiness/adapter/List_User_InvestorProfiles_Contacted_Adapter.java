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
import banyan.com.awesomebusiness.activity.Tab_User_BusinessProfile_Contacted;
import banyan.com.awesomebusiness.activity.Tab_User_InvestorProfile_Contacted;

/**
 * Created by Banyan on 07-Dec-17.
 */

public class List_User_InvestorProfiles_Contacted_Adapter extends BaseAdapter {
    private Activity activity;
    private Context context;
    private LinearLayout singleMessageContainer;

    private ArrayList<HashMap<String, String>> data;
    private static LayoutInflater inflater = null;

    private String[] bgColors;

    public List_User_InvestorProfiles_Contacted_Adapter(Activity a, ArrayList<HashMap<String, String>> d) {
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
            v = inflater.inflate(R.layout.list_userprofiles_contacted, null);

        ///////////
        TextView user_name = (TextView) v.findViewById(R.id.txt_user_name);
        TextView user_about = (TextView) v.findViewById(R.id.txt_user_about);

        HashMap<String, String> result = new HashMap<String, String>();
        result = data.get(position);

        ///////////
        String str_user_name = result.get(Tab_User_InvestorProfile_Contacted.TAG_NAME);
        String str_user_about = result.get(Tab_User_InvestorProfile_Contacted.TAG_ABOUT);

        /////////////
        user_name.setText(str_user_name);
        user_about.setText(str_user_about);


        return v;

    }

}