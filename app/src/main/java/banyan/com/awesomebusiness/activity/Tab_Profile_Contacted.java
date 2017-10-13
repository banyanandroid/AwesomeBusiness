package banyan.com.awesomebusiness.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
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
import android.widget.CheckBox;
import android.widget.EditText;
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
import com.tapadoo.alerter.Alerter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import banyan.com.awesomebusiness.R;
import banyan.com.awesomebusiness.adapter.List_RecentActivities_Adapter;
import banyan.com.awesomebusiness.adapter.List_Recent_Contacted_Adapter;
import banyan.com.awesomebusiness.global.AppConfig;
import banyan.com.awesomebusiness.global.SessionManager;
import dmax.dialog.SpotsDialog;

import static com.facebook.FacebookSdk.getApplicationContext;

/**
 * Created by Jo on 9/4/2017.
 */
public class Tab_Profile_Contacted extends Fragment implements SwipeRefreshLayout.OnRefreshListener {

    public Tab_Profile_Contacted() {
        // Required empty public constructor
    }

    SpotsDialog dialog1;
    SpotsDialog dialog2;
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

    static ArrayList<HashMap<String, String>> Recent_Contacted_List;

    // private ListView List;
    public List_Recent_Contacted_Adapter adapter;

    // Popup
    EditText edt_message;
    String str_message = "";

    CheckBox chb_share_data, chb_share_email;
    String str_share_data, str_share_email = "";

    String str_type, str_key = "";


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootview = inflater.inflate(R.layout.tab_profile_contacted, container, false);

        session = new SessionManager(getApplicationContext());
        session.checkLogin();
        HashMap<String, String> user = session.getUserDetails();
        // name
        str_user_name = user.get(SessionManager.KEY_USER);
        str_user_email = user.get(SessionManager.KEY_USER_EMAIL);
        str_user_photoo = user.get(SessionManager.KEY_USER_PHOTO);
        str_user_id = user.get(SessionManager.KEY_USER_ID);

        List = (ListView) rootview.findViewById(R.id.tab_profile_contacted_list);
        swipeRefreshLayout = (SwipeRefreshLayout) rootview.findViewById(R.id.tab_prof_contacted_swipe);

        Recent_Contacted_List = new ArrayList<HashMap<String, String>>();


        List.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {

                str_type = Recent_Contacted_List.get(position).get(TAG_TYPE);
                str_key = Recent_Contacted_List.get(position).get(TAG_KEY);

                System.out.println("user_iddddddddddddd" + str_user_id);
                System.out.println("email_idddddddddddddddd" + str_user_email);
                System.out.println("typeeeeeeeeeeeeeee" + str_type);
                System.out.println("keyyyyyyyyyyyyyyyyyy" + str_key);


                try {
                    Function_AlertDialog();
                } catch (Exception e) {
                    // TODO: handle exceptions
                }

            }

        });


        swipeRefreshLayout.setOnRefreshListener(this);

        swipeRefreshLayout.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        swipeRefreshLayout.setRefreshing(true);

                                        try {
                                            queue = Volley.newRequestQueue(getActivity());
                                            Get_Contacted_Activities();
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
            Recent_Contacted_List.clear();
            queue = Volley.newRequestQueue(getActivity());
            Get_Contacted_Activities();
        } catch (Exception e) {
            // TODO: handle exception
        }
    }

    /*****************************
     * GET Contacted Profile
     ***************************/

    public void Get_Contacted_Activities() {

        StringRequest request = new StringRequest(Request.Method.POST,
                AppConfig.url_recent_contacted_activities, new Response.Listener<String>() {
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

                            Recent_Contacted_List.add(map);

                            adapter = new List_Recent_Contacted_Adapter(getActivity(),
                                    Recent_Contacted_List);
                            List.setAdapter(adapter);
                            System.out.println("Contacted ARRAY" + Recent_Contacted_List);
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

                System.out.println("User iddddddddd ::: " + str_user_id);

                return params;
            }

        };

        // Adding request to request queue
        queue.add(request);
    }


    /*****************************
     * Alert Dialog Function
     ***************************/
    public void Function_AlertDialog() {

        LayoutInflater li = LayoutInflater.from(getActivity());
        View promptsView = li.inflate(R.layout.popup_contacted_business_message, null);

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                getActivity());
        alertDialogBuilder.setView(promptsView);

        edt_message = (EditText) promptsView.findViewById(R.id.popup_contact_business_edt_message);
        chb_share_data = (CheckBox) promptsView.findViewById(R.id.popup_chbx_share_data);
        chb_share_email = (CheckBox) promptsView.findViewById(R.id.popup_chbx_share_email);


        // String Values According to checkbox state
        if (chb_share_data.isChecked()) {
            str_share_data = "1";
        } else {
            str_share_data = "0";
        }
        if (chb_share_email.isChecked()) {
            str_share_email = "1";
        } else {
            str_share_email = "0";
        }

        // set dialog message
        alertDialogBuilder
                .setCancelable(false)
                .setPositiveButton("Send",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {

                                str_message = edt_message.getText().toString().trim();
                                // get user input and set it to result
                                // edit text
                                try {
                                    dialog1 = new SpotsDialog(getActivity());
                                    dialog1.show();
                                    queue = Volley.newRequestQueue(getActivity());
                                    Function_Contact_Business_Again();
                                } catch (Exception e) {
                                    // TODO: handle exceptions
                                }

                            }
                        })
                .setNegativeButton("Cancel",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });

        // create alert dialog
        AlertDialog alertDialog = alertDialogBuilder.create();

        // show it
        alertDialog.show();
    }


    /*****************************
     * CONTACT_BUSINESS_AGAIN
     ***************************/

    private void Function_Contact_Business_Again() {

        StringRequest request = new StringRequest(Request.Method.POST,
                AppConfig.url_popup_contact_business_again, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d(TAG, response.toString());

                try {
                    JSONObject obj = new JSONObject(response);
                    System.out.println("RESPONSE::: " + response);
                    System.out.println("CAMEEEEEEE CONTACT BUSINESS FUNCTION");
                    int success = obj.getInt("status");
                    if (success == 1) {
                        dialog1.dismiss();
                        Alerter.create(getActivity())
                                .setTitle("Success")
                                .setText("Message Sent")
                                .setBackgroundColor(R.color.colorAccent)
                                .show();

                    } else {
                        dialog1.dismiss();
                        TastyToast.makeText(getApplicationContext(), "Oops! Try Again", TastyToast.LENGTH_LONG, TastyToast.ERROR);
                    }

                    dialog1.dismiss();
                } catch (JSONException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }

                dialog1.dismiss();

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {

                dialog1.dismiss();

            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();

                params.put("user_id", str_user_id);
                params.put("email_id", str_user_email);
                params.put("type", str_type);
                params.put("key", str_key);
                params.put("yourself", str_message);
                params.put("share_email", str_share_email);
                params.put("share_contact", str_share_data);

                ////////////////

                System.out.println("user_id" + str_user_id);
                System.out.println("email_id" + str_user_email);
                System.out.println("type" + str_type);
                System.out.println("key" + str_key);
                System.out.println("yourself" + str_message);
                System.out.println("share_email" + str_share_email);
                System.out.println("share_contact" + str_share_data);


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
