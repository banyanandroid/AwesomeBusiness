package banyan.com.awesomebusiness.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import banyan.com.awesomebusiness.adapter.List_RecentActivities_Adapter;
import banyan.com.awesomebusiness.global.AppConfig;
import banyan.com.awesomebusiness.global.SessionManager;
import dmax.dialog.SpotsDialog;

import static com.facebook.FacebookSdk.getApplicationContext;

/**
 * Created by Jo on 9/4/2017.
 */
public class Tab_Profile_Viewed extends Fragment implements SwipeRefreshLayout.OnRefreshListener {

    public Tab_Profile_Viewed() {
        // Required empty public constructor
    }


    String TAG = "";
    public static final String TAG_DETAILS = "details";
    public static final String TAG_TYPE = "type";
    public static final String TAG_KEY = "key";
    public static final String TAG_CREATED_ON = "created_on";

    public static RequestQueue queue;
    // Session Manager Class
    SessionManager session;
    public static String str_user_id, str_user_name, str_user_email, str_user_photoo;

    private ListView List;
    private SwipeRefreshLayout swipeRefreshLayout;

    static ArrayList<HashMap<String, String>> Recent_Activities_List;

    // private ListView List;
    public List_RecentActivities_Adapter adapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootview =  inflater.inflate(R.layout.tab_profile_viewed, container, false);

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

        Recent_Activities_List = new ArrayList<HashMap<String, String>>();

        swipeRefreshLayout.setOnRefreshListener(this);

        swipeRefreshLayout.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        swipeRefreshLayout.setRefreshing(true);

                                        try {
                                            queue = Volley.newRequestQueue(getActivity());
                                            Get_Recent_Activities();
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
            Recent_Activities_List.clear();
            queue = Volley.newRequestQueue(getActivity());
            Get_Recent_Activities();
        } catch (Exception e) {
            // TODO: handle exception
        }
    }

    /*****************************
     * GET User Profile
     ***************************/

    public void Get_Recent_Activities() {

        StringRequest request = new StringRequest(Request.Method.POST,
                AppConfig.url_recent_activities, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d(TAG, response.toString());

                try {
                    JSONObject obj = new JSONObject(response);
                    int success = obj.getInt("status");

                    if (success == 1) {

                        JSONArray arr;
                        arr = obj.getJSONArray("data");
                        for (int i = 0; arr.length() > i; i++) {
                            JSONObject obj_data = arr.getJSONObject(i);

                            String str_details = obj_data.getString(TAG_DETAILS);
                            String str_type = obj_data.getString(TAG_TYPE);
                            String str_key = obj_data.getString(TAG_KEY);
                            String str_created_on = obj_data.getString(TAG_CREATED_ON);

                            // creating new HashMap
                            HashMap<String, String> map = new HashMap<String, String>();
                            // adding each child node to HashMap key => value
                            map.put(TAG_DETAILS, str_details);
                            map.put(TAG_TYPE, str_type);
                            map.put(TAG_KEY, str_key);
                            map.put(TAG_CREATED_ON, str_created_on);

                            Recent_Activities_List.add(map);

                            adapter = new List_RecentActivities_Adapter(getActivity(),
                                    Recent_Activities_List);
                              List.setAdapter(adapter);
                            System.out.println("hgjhghjgjhgjhgjhg ARRAY" + Recent_Activities_List);
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

                params.put("user_id", str_user_id);

                System.out.println("cameeeeeeeeeeeeeeeeeeeee ::: " + str_user_id);

                return params;
            }

        };

        // Adding request to request queue
        queue.add(request);
    }

}
