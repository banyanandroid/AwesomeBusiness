package banyan.com.awesomebusiness.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.tapadoo.alerter.Alerter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import banyan.com.awesomebusiness.R;
import banyan.com.awesomebusiness.adapter.List_UserProfiles_Bookmarks_View_Adapter;
import banyan.com.awesomebusiness.global.AppConfig;
import banyan.com.awesomebusiness.global.SessionManager;

import static com.facebook.FacebookSdk.getApplicationContext;

/**
 * Created by Banyan on 07-Dec-17.
 */

public class Tab_User_Profile_Viewed extends Fragment implements SwipeRefreshLayout.OnRefreshListener {

    public Tab_User_Profile_Viewed() {
        // Required empty public constructor
    }

    String TAG = "";
    public static final String TAG_BRANDNAME = "brandname";
    public static final String TAG_USER_NAME = "user_name";
    public static final String TAG_USER_ID = "user_id";

    public static final String TAG_USER_EMAIL = "user_email";
    public static final String TAG_USER_TYPE = "user_type";
    public static final String TAG_USER_CONNECT = "connect";


    public static RequestQueue queue;
    // Session Manager Class
    SessionManager session;
    public static String str_user_id, str_user_name, str_user_email, str_user_photoo;

    private ListView List;
    private SwipeRefreshLayout swipeRefreshLayout;

    static ArrayList<HashMap<String, String>> User_Viewed_List;

    // private ListView List;
    public List_UserProfiles_Bookmarks_View_Adapter adapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootview = inflater.inflate(R.layout.tab_profile_viewed, container, false);

        session = new SessionManager(getApplicationContext());
        session.checkLogin();
        HashMap<String, String> user = session.getUserDetails();
        // name
        str_user_name = user.get(SessionManager.KEY_USER);
        str_user_email = user.get(SessionManager.KEY_USER_EMAIL);
        str_user_photoo = user.get(SessionManager.KEY_USER_PHOTO);
        str_user_id = user.get(SessionManager.KEY_USER_ID);

        List = (ListView) rootview.findViewById(R.id.tab_profile_viewed_list);
        swipeRefreshLayout = (SwipeRefreshLayout) rootview.findViewById(R.id.tab_prof_view_swipe);

        User_Viewed_List = new ArrayList<HashMap<String, String>>();

        List.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {

                String str_viewed_user_name = User_Viewed_List.get(position).get(TAG_USER_NAME);
                String str_viewed_user_id = User_Viewed_List.get(position).get(TAG_USER_ID);

                System.out.println("LIST CLICKEDDDDDDDDDDDDDD 11111");
                System.out.println("LIST CLICKEDDDDDDDDDDDDDD 11111");

            }

        });

        swipeRefreshLayout.setOnRefreshListener(this);

        swipeRefreshLayout.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        swipeRefreshLayout.setRefreshing(true);

                                        try {
                                            queue = Volley.newRequestQueue(getActivity());
                                            Get_Viewed_Users();
                                        } catch (Exception e) {
                                            // TODO: handle exceptions
                                        }
                                    }
                                }
        );


        return rootview;
    }

    @Override
    public void onRefresh() {
        try {
            User_Viewed_List.clear();
            queue = Volley.newRequestQueue(getActivity());
            Get_Viewed_Users();
        } catch (Exception e) {
            // TODO: handle exception
        }
    }

    /*****************************
     * GET Viewed Users
     ***************************/

    public void Get_Viewed_Users() {

        StringRequest request = new StringRequest(Request.Method.POST,
                AppConfig.url_viewed_user_business_profile, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d(TAG, response.toString());
                try {
                    JSONObject obj = new JSONObject(response);
                    int success = obj.getInt("status");

                    if (success == 1) {


                        JSONObject data = obj.getJSONObject("data");

                        String brand_name = data.getString("brandname");

                        String business_key = data.getString("business_key");

                        String type = data.getString("type");

                        JSONArray arr;
                        arr = data.getJSONArray("viewed");

                        for (int i = 0; arr.length() > i; i++) {
                            JSONObject obj_data = arr.getJSONObject(i);

                            String user_name = obj_data.getString(TAG_USER_NAME);
                            String user_id = obj_data.getString(TAG_USER_ID);

                            String user_email = obj_data.getString(TAG_USER_EMAIL);
                            String user_type = obj_data.getString(TAG_USER_TYPE);
                            String user_connect = obj_data.getString(TAG_USER_CONNECT);

                            HashMap<String, String> map = new HashMap<String, String>();

                            map.put(TAG_USER_NAME, user_name);
                            map.put(TAG_USER_ID, user_id);

                            map.put(TAG_USER_EMAIL, user_email);
                            map.put(TAG_USER_TYPE, user_type);
                            map.put(TAG_USER_CONNECT, user_connect);

                            User_Viewed_List.add(map);

                            adapter = new List_UserProfiles_Bookmarks_View_Adapter(getActivity(),
                                    User_Viewed_List);
                            List.setAdapter(adapter);

                        }
                    } else if (success == 0) {

                        Alerter.create(getActivity())
                                .setTitle("WORLD BUSINESSES FOR SALE")
                                .setText("Oops! Something went wrong :( \n Try Again")
                                .setBackgroundColor(R.color.red)
                                .show();
                    }
                    swipeRefreshLayout.setRefreshing(false);
                } catch (JSONException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }

                swipeRefreshLayout.setRefreshing(false);

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {

                swipeRefreshLayout.setRefreshing(false);
                Alerter.create(getActivity())
                        .setTitle("WORLD BUSINESSES FOR SALE")
                        .setText("Internal Error :(\n" + error.getMessage())
                        .setBackgroundColor(R.color.colorPrimaryDark)
                        .show();
            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();

                params.put("user_id", "60");
                params.put("business_id", "4");

                return params;
            }

        };

        // Adding request to request queue
        queue.add(request);
    }

}
