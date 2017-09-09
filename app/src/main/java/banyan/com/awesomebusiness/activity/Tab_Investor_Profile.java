package banyan.com.awesomebusiness.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
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
import com.github.fabtransitionactivity.SheetLayout;
import com.tapadoo.alerter.Alerter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import banyan.com.awesomebusiness.R;
import banyan.com.awesomebusiness.adapter.BusinessProfiles_Adapter;
import banyan.com.awesomebusiness.adapter.InvestorProfiles_Adapter;
import banyan.com.awesomebusiness.global.AppConfig;
import banyan.com.awesomebusiness.global.SessionManager;
import butterknife.ButterKnife;

/**
 * Created by Jo on 9/4/2017.
 */
public class Tab_Investor_Profile extends Fragment implements SwipeRefreshLayout.OnRefreshListener {


    String str_user_name, str_user_id;

    public static RequestQueue queue;

    // Session Manager Class
    SessionManager session;

    String TAG = "add task";

    private ListView List;
    private SwipeRefreshLayout swipeRefreshLayout;

    public static final String TAG_INVESTOR_ID = "investor_id";
    public static final String TAG_INVESTOR_KEY = "investor_key";
    public static final String TAG_INVESTOR_NAME = "investor_name";
    public static final String TAG_INVESTOR_CONFIDENTIAL_EMAIL = "investor_confidential_email";
    public static final String TAG_INVESTOR_CONFIDENTIAL_MOBILE = "investor_confidential_mobile";
    public static final String TAG_INVESTOR_USER_ROLE = "investor_user_role";
    public static final String TAG_INVESTOR_CURRENCY_FROM = "investor_currency_from";
    public static final String TAG_INVESTOR_CURRENCY_TO = "investor_currency_to";
    public static final String TAG_INVESTOR_CURRENCY = "investor_currency";
    public static final String TAG_INVESTOR_COMPANY_NAME = "investor_company_name";
    public static final String TAG_INVESTOR_DESIGNATION = "investor_designation";
    public static final String TAG_INVESTO_PROFILE_URL = "investo_profile_url";
    public static final String TAG_INVESTOR_SHORT_DESCRIPTION = "investor_short_description";
    public static final String TAG_INVESTOR_ABOUT_USER = "investor_about_user";
    public static final String TAG_INVESTOR_AN_NAME = "investor_an_name";
    public static final String TAG_INVESTOR_INTEREST_NAME = "investor_interest_name";

    public static final String TAG_LOCATION_NAME = "location_name";
    public static final String TAG_LOCATION_KEY = "location_key";
    public static final String TAG_INDUSTRY_NAME = "industry_name";
    public static final String TAG_INDUSTRY_KEY = "industry_key";


    static ArrayList<HashMap<String, String>> Investor_profile_list;

    HashMap<String, String> params = new HashMap<String, String>();

    public InvestorProfiles_Adapter adapter;


    private static final int REQUEST_CODE = 1;

    public Tab_Investor_Profile() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootview = inflater.inflate(R.layout.tab_investor_profile, container, false);
        ButterKnife.bind(getActivity());

        session = new SessionManager(getActivity());

        session.checkLogin();
        HashMap<String, String> user = session.getUserDetails();
        str_user_name = user.get(SessionManager.KEY_USER);
        str_user_id = user.get(SessionManager.KEY_USER_ID);


        List = (ListView) rootview.findViewById(R.id.my_investor_profile);
        swipeRefreshLayout = (SwipeRefreshLayout) rootview.findViewById(R.id.investor_profiles_swipe_refresh_layout);

        swipeRefreshLayout.setOnRefreshListener(this);

        swipeRefreshLayout.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        swipeRefreshLayout.setRefreshing(true);

                                        try {
                                            queue = Volley.newRequestQueue(getActivity());
                                            Get_Investor_Profiles();

                                        } catch (Exception e) {
                                            // TODO: handle exceptions
                                        }
                                    }
                                }
        );

        // Hashmap for ListView
        Investor_profile_list = new ArrayList<HashMap<String, String>>();


        // Hashmap for ListView
        Investor_profile_list = new ArrayList<HashMap<String, String>>();

        List.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {

                System.out.println("ITEM CLICKED");


            }

        });

        return inflater.inflate(R.layout.tab_investor_profile, container, false);
    }


    @Override
    public void onRefresh() {
        try {
            Investor_profile_list.clear();
            queue = Volley.newRequestQueue(getActivity());
            Get_Investor_Profiles();
        } catch (Exception e) {
            // TODO: handle exception
        }
    }

    /*****************************
     * GET Investor Profiles
     ***************************/

    public void Get_Investor_Profiles() {

        String tag_json_obj = "json_obj_req";
        System.out.println("CAME 1");
        StringRequest request = new StringRequest(Request.Method.POST,
                AppConfig.url_user_investor_profiles, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d(TAG, response.toString());
                try {
                    JSONObject obj = new JSONObject(response);
                    int success = obj.getInt("status");

                    if (success == 1) {

                        JSONArray arr;
                        arr = obj.getJSONArray("enquiry");
                        for (int i = 0; arr.length() > i; i++) {
                            JSONObject obj1 = arr.getJSONObject(i);


                            String investor_id = obj1.getString(TAG_INVESTOR_ID);
                            String investor_key = obj1.getString(TAG_INVESTOR_KEY);
                            String investor_name = obj1.getString(TAG_INVESTOR_NAME);
                            String investor_confidential_email = obj1.getString(TAG_INVESTOR_CONFIDENTIAL_EMAIL);
                            String investor_confidential_mobile = obj1.getString(TAG_INVESTOR_CONFIDENTIAL_MOBILE);
                            String investor_user_role = obj1.getString(TAG_INVESTOR_USER_ROLE);
                            String investor_currency_from = obj1.getString(TAG_INVESTOR_CURRENCY_FROM);
                            String investor_currency_to = obj1.getString(TAG_INVESTOR_CURRENCY_TO);
                            String investor_currency = obj1.getString(TAG_INVESTOR_CURRENCY);
                            String investor_company_name = obj1.getString(TAG_INVESTOR_COMPANY_NAME);
                            String investor_designation = obj1.getString(TAG_INVESTOR_DESIGNATION);
                            String investo_profile_url = obj1.getString(TAG_INVESTO_PROFILE_URL);
                            String investor_short_description = obj1.getString(TAG_INVESTOR_SHORT_DESCRIPTION);
                            String investor_about_user = obj1.getString(TAG_INVESTOR_ABOUT_USER);
                            String investor_an_name = obj1.getString(TAG_INVESTOR_AN_NAME);
                            String investor_interest_name = obj1.getString(TAG_INVESTOR_INTEREST_NAME);

                            //  String location_name = obj1.getString(TAG_LOCATION_NAME);
                            //  String location_key = obj1.getString(TAG_LOCATION_KEY);
                            //   String industry_name = obj1.getString(TAG_INDUSTRY_NAME);
                            //   String industry_key = obj1.getString(TAG_INDUSTRY_KEY);


                            // creating new HashMap
                            HashMap<String, String> map = new HashMap<String, String>();

                            // adding each child node to HashMap key => value
                            map.put(TAG_INVESTOR_ID, investor_id);
                            map.put(TAG_INVESTOR_KEY, investor_key);
                            map.put(TAG_INVESTOR_NAME, investor_name);
                            map.put(TAG_INVESTOR_CONFIDENTIAL_EMAIL, investor_confidential_email);
                            map.put(TAG_INVESTOR_CONFIDENTIAL_MOBILE, investor_confidential_mobile);
                            map.put(TAG_INVESTOR_USER_ROLE, investor_user_role);
                            map.put(TAG_INVESTOR_CURRENCY_FROM, investor_currency_from);
                            map.put(TAG_INVESTOR_CURRENCY_TO, investor_currency_to);
                            map.put(TAG_INVESTOR_CURRENCY, investor_currency);
                            map.put(TAG_INVESTOR_COMPANY_NAME, investor_company_name);
                            map.put(TAG_INVESTOR_DESIGNATION, investor_designation);
                            map.put(TAG_INVESTO_PROFILE_URL, investo_profile_url);
                            map.put(TAG_INVESTOR_SHORT_DESCRIPTION, investor_short_description);
                            map.put(TAG_INVESTOR_ABOUT_USER, investor_about_user);
                            map.put(TAG_INVESTOR_AN_NAME, investor_an_name);
                            map.put(TAG_INVESTOR_INTEREST_NAME, investor_interest_name);

                            //    map.put(TAG_LOCATION_NAME, location_name);
                            //    map.put(TAG_LOCATION_KEY, location_key);
                            //   map.put(TAG_INDUSTRY_NAME, industry_name);
                            //   map.put(TAG_INDUSTRY_KEY, industry_key);

                            Investor_profile_list.add(map);

                            System.out.println("HASHMAP ARRAY" + Investor_profile_list);


                            adapter = new InvestorProfiles_Adapter(getActivity(),
                                    Investor_profile_list);
                            List.setAdapter(adapter);

                        }

                    } else if (success == 0) {

                        adapter = new InvestorProfiles_Adapter(getActivity(),
                                Investor_profile_list);
                        List.setAdapter(adapter);

                        Alerter.create(getActivity())
                                .setTitle("Awesome Businesses")
                                .setText("Data Not Found \n Try Again")
                                .setBackgroundColor(R.color.Alert_Fail)
                                .show();
                    }

                    swipeRefreshLayout.setRefreshing(false);

                } catch (JSONException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                swipeRefreshLayout.setRefreshing(false);
                // dialog.dismiss();
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                swipeRefreshLayout.setRefreshing(false);

                Alerter.create(getActivity())
                        .setTitle("Awesome Businesses")
                        .setText("Internal Error !")
                        .setBackgroundColor(R.color.Alert_Warning)
                        .show();
            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();

                params.put("user_id", str_user_id);

                System.out.println("user_id" + str_user_id);

                return params;
            }

        };

        // Adding request to request queue
        queue.add(request);
    }


}
