package banyan.com.awesomebusiness.activity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
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
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
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
import java.util.Map;

import banyan.com.awesomebusiness.R;
import banyan.com.awesomebusiness.adapter.List_User_BusinessProfiles_Bookmark_View_Adapter;
import banyan.com.awesomebusiness.global.AppConfig;
import banyan.com.awesomebusiness.global.SessionManager;

import static com.facebook.FacebookSdk.getApplicationContext;

/**
 * Created by Banyan on 07-Dec-17.
 */

public class Tab_User_BusinessProfile_Viewed extends Fragment implements SwipeRefreshLayout.OnRefreshListener {

    public Tab_User_BusinessProfile_Viewed() {
        // Required empty public constructor
    }

    String TAG = "";
    public static final String TAG_BRANDNAME = "brandname";
    public static final String TAG_KEY = "business_key";
    public static final String TAG_TYPE = "type";
    public static final String TAG_USER_NAME = "user_name";
    public static final String TAG_USER_ID = "user_id";
    public static final String TAG_USER_EMAIL = "user_email";
    public static final String TAG_USER_TYPE = "user_type";
    public static final String TAG_USER_CONNECT = "connect";


    public static RequestQueue queue;
    // Session Manager Class
    SessionManager session;
    public static String str_user_id, str_user_name, str_user_email, str_user_photoo;

    // Popup
    final Context context = getActivity();
    TextView txt_popup_status;

    private ListView List;
    private SwipeRefreshLayout swipeRefreshLayout;

    static ArrayList<HashMap<String, String>> User_Viewed_List;

    // private ListView List;
    public List_User_BusinessProfiles_Bookmark_View_Adapter adapter;

    //String to get Viewers details
    String str_brand_name, str_key, str_type, user_name, user_id, user_email, user_type, user_connect = "";

    //String to get details of clicked item in Viewers list
    String str_bookmark_user_name, str_bookmark_user_id, str_bookmark_user_type, str_bookmark_user_connect = "";


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

                str_bookmark_user_name = User_Viewed_List.get(position).get(TAG_USER_NAME);
                str_bookmark_user_id = User_Viewed_List.get(position).get(TAG_USER_ID);
                str_bookmark_user_type = User_Viewed_List.get(position).get(TAG_USER_TYPE);
                str_bookmark_user_connect = User_Viewed_List.get(position).get(TAG_USER_CONNECT);

                Function_AlertDialog();


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
     * GET ALERT DIALOG (POPUP)
     ***************************/
    public void Function_AlertDialog() {

        LayoutInflater li = LayoutInflater.from(getActivity());
        View promptsView = li.inflate(R.layout.popup_custom_user_connect, null);

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                getActivity());
        alertDialogBuilder.setView(promptsView);

        txt_popup_status = (TextView) promptsView.findViewById(R.id.txt_popup_userconnect_status);

        try {

            if (str_bookmark_user_connect != null && !str_bookmark_user_connect.isEmpty() && !str_bookmark_user_connect.equals("null")) {

                if (str_bookmark_user_connect.equals("0")) {

                    txt_popup_status.setText("Do you want to connect with this user ?");

                } else if (str_bookmark_user_connect.equals("1") && str_bookmark_user_type.equals("1")) {

                    txt_popup_status.setText("Waiting for the user's approval ! ");

                } else if (str_bookmark_user_connect.equals("1") && str_bookmark_user_type.equals("2")) {

                    txt_popup_status.setText("Approve connection with this user ?");

                } else if (str_bookmark_user_connect.equals("2") && str_bookmark_user_type.equals("1")) {

                    txt_popup_status.setText("Connection Success !");

                } else if (str_bookmark_user_connect.equals("2") && str_bookmark_user_type.equals("2")) {

                    txt_popup_status.setText("Approved !");

                } else if (str_bookmark_user_connect.equals("")) {

                    txt_popup_status.setText("Unknown Status");

                }

            } else {

                txt_popup_status.setText("Unknown Status");
            }

        } catch (Exception e) {

        }


        alertDialogBuilder
                .setCancelable(false)
                .setPositiveButton("Ok",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {

                                try {

                                    if (str_bookmark_user_connect != null && !str_bookmark_user_connect.isEmpty() && !str_bookmark_user_connect.equals("null")) {

                                        if (str_bookmark_user_connect.equals("0")) {

                                            try {
                                                queue = Volley.newRequestQueue(getActivity());
                                                Function_Connect_With_User();
                                            } catch (Exception e) {
                                                // TODO: handle exceptions
                                            }

                                        } else if (str_bookmark_user_connect.equals("1") && str_bookmark_user_type.equals("1")) {


                                            try {
                                                queue = Volley.newRequestQueue(getActivity());
                                                Function_Approve_Connection();
                                            } catch (Exception e) {
                                                // TODO: handle exceptions
                                            }


                                        } else if (str_bookmark_user_connect.equals("1") && str_bookmark_user_type.equals("2")) {

                                            dialog.cancel();

                                        } else if (str_bookmark_user_connect.equals("2") && str_bookmark_user_type.equals("1")) {

                                            dialog.cancel();

                                        } else if (str_bookmark_user_connect.equals("2") && str_bookmark_user_type.equals("2")) {

                                            dialog.cancel();

                                        }

                                    } else {

                                        txt_popup_status.setText("Unknown Status");
                                    }

                                } catch (Exception e) {

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


    /******************************************
     *    USER REQUEST TO CONNECT WITH ANOTHER USER
     * *******************************************/
    private void Function_Connect_With_User() {

        StringRequest request = new StringRequest(Request.Method.POST,
                AppConfig.url_user_request_connection, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d("USER_CONNECT ::", response.toString());
                try {
                    JSONObject obj = new JSONObject(response);
                    System.out.println("REG 00" + obj);

                    int success = obj.getInt("status");

                    System.out.println("REG" + success);

                    if (success == 1) {

                        TastyToast.makeText(getApplicationContext(), "Connection Request Sent !", TastyToast.LENGTH_LONG, TastyToast.SUCCESS);

                    } else {
                        TastyToast.makeText(getApplicationContext(), "Oops...! Unable To send Connection request :(", TastyToast.LENGTH_LONG, TastyToast.ERROR);
                    }

                } catch (JSONException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();

                params.put("key", str_key);
                params.put("user_id", str_user_id);
                params.put("description", str_brand_name);
                params.put("type", str_type);
                params.put("approve_user_id", user_id);

                System.out.println("key" + str_key);
                System.out.println("user_id" + str_user_id);
                System.out.println("description" + str_brand_name);
                System.out.println("type" + str_type);
                System.out.println("approve_user_id" + user_id);

                return params;
            }

        };

        // Adding request to request queue
        queue.add(request);
    }


    /******************************************
     *    USER APPROVES A REQUEST FOR CONNECTION
     * *******************************************/
    private void Function_Approve_Connection() {

        StringRequest request = new StringRequest(Request.Method.POST,
                AppConfig.url_user_approve_connection_request, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d("USER_CONNECT ::", response.toString());
                try {
                    JSONObject obj = new JSONObject(response);
                    System.out.println("REG 00" + obj);

                    int success = obj.getInt("status");

                    System.out.println("REG" + success);

                    if (success == 1) {

                        TastyToast.makeText(getApplicationContext(), "Connection Approved!", TastyToast.LENGTH_LONG, TastyToast.SUCCESS);

                    } else {
                        TastyToast.makeText(getApplicationContext(), "Oops...! Unable To Approve Connection  :(", TastyToast.LENGTH_LONG, TastyToast.ERROR);
                    }

                } catch (JSONException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();

                params.put("key", str_key);
                params.put("user_id", str_user_id);
                params.put("description", str_brand_name);
                params.put("type", str_type);
                params.put("connect_user_id", user_id);

                System.out.println("KEY ::::::::::::" + str_key);
                System.out.println("USER_ID ::::::::::::" + str_user_id);
                System.out.println("DESCRIPTION ::::::::::" + str_brand_name);
                System.out.println("TYPE ::::::::::" + str_type);
                System.out.println("CONNECT_USER_ID ::::::::::" + user_id);

                return params;
            }

        };

        // Adding request to request queue
        queue.add(request);
    }


    /*****************************
     * GET Viewed Users
     ***************************/

    public void Get_Viewed_Users() {

        StringRequest request = new StringRequest(Request.Method.POST,
                AppConfig.url_user_businessprofile_viewed, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d(TAG, response.toString());
                try {
                    JSONObject obj = new JSONObject(response);
                    int success = obj.getInt("status");

                    if (success == 1) {


                        JSONObject data = obj.getJSONObject("data");

                        str_brand_name = data.getString(TAG_BRANDNAME);
                        str_key = data.getString(TAG_KEY);
                        str_type = data.getString(TAG_TYPE);

                        JSONArray arr;
                        arr = data.getJSONArray("viewed");

                        for (int i = 0; arr.length() > i; i++) {
                            JSONObject obj_data = arr.getJSONObject(i);

                            user_name = obj_data.getString(TAG_USER_NAME);
                            user_id = obj_data.getString(TAG_USER_ID);

                            user_email = obj_data.getString(TAG_USER_EMAIL);
                            user_type = obj_data.getString(TAG_USER_TYPE);
                            user_connect = obj_data.getString(TAG_USER_CONNECT);

                            HashMap<String, String> map = new HashMap<String, String>();

                            map.put(TAG_BRANDNAME, str_brand_name);
                            map.put(TAG_KEY, str_key);
                            map.put(TAG_TYPE, str_type);

                            map.put(TAG_USER_NAME, user_name);
                            map.put(TAG_USER_ID, user_id);
                            map.put(TAG_USER_EMAIL, user_email);
                            map.put(TAG_USER_TYPE, user_type);
                            map.put(TAG_USER_CONNECT, user_connect);


                            User_Viewed_List.add(map);

                            adapter = new List_User_BusinessProfiles_Bookmark_View_Adapter(getActivity(),
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
