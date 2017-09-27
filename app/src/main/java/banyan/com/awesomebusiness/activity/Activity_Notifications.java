package banyan.com.awesomebusiness.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.sdsmdg.tastytoast.TastyToast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import banyan.com.awesomebusiness.R;
import banyan.com.awesomebusiness.adapter.List_BusinessProfiles_Adapter;
import banyan.com.awesomebusiness.adapter.Notification_Adapter;
import banyan.com.awesomebusiness.global.AppConfig;
import banyan.com.awesomebusiness.global.SessionManager;
import dmax.dialog.SpotsDialog;

/**
 * Created by Jo on 22-Jul-17.
 */

public class Activity_Notifications extends AppCompatActivity {

    public static RequestQueue queue;
    SpotsDialog dialog;
    String TAG = "";
    private Toolbar mToolbar;

    private ListView List;
    public Notification_Adapter adapter;

    static ArrayList<HashMap<String, String>> Notification_List;
    HashMap<String, String> params = new HashMap<String, String>();

    public static final String TAG_NOTIFICATION_ID = "notification_id";
    public static final String TAG_NOTIFICATION_KEY = "notification_key";
    public static final String TAG_NOTIFICATION_USER_ID = "notification_user_id";
    public static final String TAG_NOTIFICATION_CONTENT = "notification_content";
    public static final String TAG_NOTIFICATION_VIEW = "notification_view";
    public static final String TAG_NOTIFICATION_BUSINES_KEY = "notification_business_key";
    public static final String TAG_NOTIFICATION_INVESTOR_KEY = "notification_investor_key";
    public static final String TAG_NOTIFICATION_FRANCHISE_KEY = "notification_franchise_key";
    public static final String TAG_NOTIFICATION_NOTIFICATION_VIEW = "notification_advisor_notification_view";
    public static final String TAG_NOTIFICATION_CREATED_ON = "notification_created_on";
    public static final String TAG_NOTIFICATION_LOCATION = "notification_location";
    public static final String TAG_NOTIFICATION_ACT = "notification_act";

    SessionManager session;
    public static String str_user_id, str_user_name, str_user_email, str_user_photoo = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notifications);

        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        mToolbar.setNavigationIcon(getResources().getDrawable(R.drawable.ic_action_back));
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(getApplicationContext(), MainActivity.class);
                overridePendingTransition(R.anim.pull_in_right, R.anim.pull_in_left);
                startActivity(i);
                finish();
            }
        });

        session = new SessionManager(getApplicationContext());
        session.checkLogin();
        HashMap<String, String> user = session.getUserDetails();

        // name
        str_user_name = user.get(SessionManager.KEY_USER);
        str_user_email = user.get(SessionManager.KEY_USER_EMAIL);
        str_user_photoo = user.get(SessionManager.KEY_USER_PHOTO);
        str_user_id = user.get(SessionManager.KEY_USER_ID);

        List = (ListView) findViewById(R.id.notification_listview);

        // Hashmap for ListView
        Notification_List = new ArrayList<HashMap<String, String>>();

        try {
            dialog = new SpotsDialog(Activity_Notifications.this);
            dialog.show();
            queue = Volley.newRequestQueue(Activity_Notifications.this);
            Get_Notifications();
        } catch (Exception e) {
            // TODO: handle exception
        }

    }


    /*****************************
     * To get Business Profiles
     ***************************/

    public void Get_Notifications() {
        String tag_json_obj = "json_obj_req";
        StringRequest request = new StringRequest(Request.Method.POST,
                AppConfig.url_notifications, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d(TAG, response.toString());
                try {
                    JSONObject obj = new JSONObject(response);

                    System.out.println("RESPONSE OBJ : " + obj);

                    int success = obj.getInt("status");

                    if (success == 1) {

                        JSONArray arr;

                        arr = obj.getJSONArray("data");
                        for (int i = 0; arr.length() > i; i++) {
                            JSONObject obj1 = arr.getJSONObject(i);

                            String notification_id = obj1.getString(TAG_NOTIFICATION_ID);
                            String notification_key = obj1.getString(TAG_NOTIFICATION_KEY);
                            String notification_user_id = obj1.getString(TAG_NOTIFICATION_USER_ID);
                            String notification_content = obj1.getString(TAG_NOTIFICATION_CONTENT);
                            String notification_view= obj1.getString(TAG_NOTIFICATION_VIEW);
                            String notification_business_key = obj1.getString(TAG_NOTIFICATION_BUSINES_KEY);
                            String notification_investor_key = obj1.getString(TAG_NOTIFICATION_INVESTOR_KEY);
                            String notification_franchise_key = obj1.getString(TAG_NOTIFICATION_FRANCHISE_KEY);
                            String notification_advisor_notification_view = obj1.getString(TAG_NOTIFICATION_NOTIFICATION_VIEW);
                            String notification_created_on = obj1.getString(TAG_NOTIFICATION_CREATED_ON);
                            String notification_location = obj1.getString(TAG_NOTIFICATION_LOCATION);
                            String notification_act = obj1.getString(TAG_NOTIFICATION_ACT);

                            // creating new HashMap
                            HashMap<String, String> map = new HashMap<String, String>();
                            // adding each child node to HashMap key => value
                            map.put(TAG_NOTIFICATION_ID, notification_id);
                            map.put(TAG_NOTIFICATION_KEY, notification_key);
                            map.put(TAG_NOTIFICATION_USER_ID, notification_user_id);
                            map.put(TAG_NOTIFICATION_CONTENT, notification_content);
                            map.put(TAG_NOTIFICATION_VIEW, notification_view);
                            map.put(TAG_NOTIFICATION_BUSINES_KEY, notification_business_key);
                            map.put(TAG_NOTIFICATION_INVESTOR_KEY, notification_investor_key);
                            map.put(TAG_NOTIFICATION_FRANCHISE_KEY, notification_franchise_key);
                            map.put(TAG_NOTIFICATION_NOTIFICATION_VIEW, notification_advisor_notification_view);
                            map.put(TAG_NOTIFICATION_CREATED_ON, notification_created_on);
                            map.put(TAG_NOTIFICATION_LOCATION, notification_location);
                            map.put(TAG_NOTIFICATION_ACT, notification_act);

                            Notification_List.add(map);
                            adapter = new Notification_Adapter(Activity_Notifications.this,
                                    Notification_List);
                            List.setAdapter(adapter);
                            System.out.println("HASHMAP ARRAY" + Notification_List);
                        }

                        dialog.dismiss();
                    } else if (success == 0) {
                        TastyToast.makeText(getApplicationContext(), "Something Went Wrong :(", TastyToast.LENGTH_LONG, TastyToast.ERROR);
                    }

                    dialog.dismiss();
                } catch (JSONException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {

                dialog.dismiss();

            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();

                params.put("user_id", str_user_id);

                System.out.println("user_id" + str_user_id);

                return checkParams(params);
            }

            private Map<String, String> checkParams(Map<String, String> map) {
                Iterator<Map.Entry<String, String>> it = map.entrySet().iterator();
                while (it.hasNext()) {
                    Map.Entry<String, String> pairs = (Map.Entry<String, String>) it.next();
                    if (pairs.getValue() == null) {
                        map.put(pairs.getKey(), "");
                    }
                }
                return map;
            }
        };

        int socketTimeout = 60000;//30 seconds - change to what you want
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        request.setRetryPolicy(policy);
        // Adding request to request queue
        queue.add(request);
    }

}
