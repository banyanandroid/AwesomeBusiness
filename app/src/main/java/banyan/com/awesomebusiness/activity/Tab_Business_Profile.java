package banyan.com.awesomebusiness.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
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
import banyan.com.awesomebusiness.adapter.CartAdapter;
import banyan.com.awesomebusiness.global.AppConfig;
import banyan.com.awesomebusiness.global.SessionManager;
import butterknife.ButterKnife;

/**
 * Created by Jo on 9/4/2017.
 */
public class Tab_Business_Profile extends Fragment implements SwipeRefreshLayout.OnRefreshListener {


    String str_user_name, str_user_id;

    public static RequestQueue queue;

    // Session Manager Class
    SessionManager session;

    String TAG = "add task";

    private ListView List;
    private SwipeRefreshLayout swipeRefreshLayout;

    public static final String TAG_BUSINESS_ID = "business_id";
    public static final String TAG_BUSINESS_KEY = "business_key";
    public static final String TAG_BUSINESS_SHORT_DES = "buisness_short_description";
    public static final String TAG_BUSINESS_YEARLY_SALES = "business_yearly_sales";
    public static final String TAG_BUSINESS_EBITDA = "business_ebitda";
    public static final String TAG_BUSINESS_EBITDA_RANGE = "business_ebitda_range";
    public static final String TAG_BUISNESS_INVESTMENT = "buisness_investment";
    public static final String TAG_BUISNESS_DESCRIPTION = "buisness_description";
    public static final String TAG_BUSINESS_INTEREST_NAME = "business_interest_name";
    public static final String TAG_BUSINESS_ROLE_NAME = "business_role_name";
    public static final String TAG_COUNTRY_CURRENCY = "country_currency";
    public static final String TAG_BUSINESS_CURRENCY = "business_currency";
    public static final String TAG_BUSINESS_SELL_LEASE = "business_sell_lease";
    public static final String TAG_BUSINESS_ASSETS_FEATURES = "business_assets_features";
    public static final String TAG_BUSINESS_SELL_LEASE_PRICE = "business_sell_lease_price";
    public static final String TAG_BUSINESS_SELL_LEASE_COST = "business_sell_lease_cost";
    public static final String TAG_BUSINESS_SELL_TYPE = "business_sell_type";
    public static final String TAG_BUSINESS_ASSETS_PURCHASED = "business_assets_purchased";
    public static final String TAG_BUSINESS_ASSETS_DESCRIPTION = "business_assets_description";
    public static final String TAG_BUSINESS_USER_ROLE = "business_user_role";
    public static final String TAG_BUSINESS_USER_INTEREST_IN = "business_user_interest_in";
    public static final String TAG_BUSINESS_MOBILE_CODE = "business_mobile_code";
    public static final String TAG_BUSINESS_USER_NAME = "business_user_name";
    public static final String TAG_BUSINESS_USER_MOBILE = "business_user_mobile";
    public static final String TAG_BUSINESS_USER_EMAIL = "business_user_email";
    public static final String TAG_BUSINESS_COMPANY_NAME = "business_company_name";
    public static final String TAG_BUSINESS_EMPLOYEE_COUNT = "business_employee_count";
    public static final String TAG_BUSINESS_ESTABLISHED = "business_established";
    public static final String TAG_BUISNESS_PRODUCTS_SERVICES = "buisness_products_services";
    public static final String TAG_BUISNESS_FACILITY = "buisness_facility";
    public static final String TAG_BUSINESS_ASSETS_REASON = "business_assets_reason";
    public static final String TAG_BUSINESS_MONTH_SALES = "business_month_sales";
    public static final String TAG_BUSINESS_TENTATIVE_PRICE = "business_tentative_price";
    public static final String TAG_BUSINESS_LEGAL_ENTITY_TYPE = "business_legal_entity_type";

    public static final String TAG_LOCATION_NAME = "location_name";
    public static final String TAG_LOCATION_KEY = "location_key";


    static ArrayList<HashMap<String, String>> Business_profile_list;

    HashMap<String, String> params = new HashMap<String, String>();

    public BusinessProfiles_Adapter adapter;

    public Tab_Business_Profile() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootview = inflater.inflate(R.layout.tab_business_profile, null);

        session = new SessionManager(getActivity());
        session.checkLogin();
        HashMap<String, String> user = session.getUserDetails();
        str_user_name = user.get(SessionManager.KEY_USER);
        str_user_id = user.get(SessionManager.KEY_USER_ID);

        List = (ListView) rootview.findViewById(R.id.my_business_profile);
        swipeRefreshLayout = (SwipeRefreshLayout) rootview.findViewById(R.id.business_profiles_swipe_refresh_layout);

        swipeRefreshLayout.setOnRefreshListener(this);

        swipeRefreshLayout.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        swipeRefreshLayout.setRefreshing(true);

                                        try {
                                            queue = Volley.newRequestQueue(getActivity());
                                            Get_Business_Profiles();

                                        } catch (Exception e) {
                                            // TODO: handle exceptions
                                        }
                                    }
                                }
        );

        // Hashmap for ListView
        Business_profile_list = new ArrayList<HashMap<String, String>>();

        List.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {

                System.out.println("ITEM CLICKED");

                String business_id = Business_profile_list.get(position).get(TAG_BUSINESS_ID);
                String business_key = Business_profile_list.get(position).get(TAG_BUSINESS_KEY);
                String business_short_des = Business_profile_list.get(position).get(TAG_BUSINESS_SHORT_DES);
                String business_yearly_sales = Business_profile_list.get(position).get(TAG_BUSINESS_YEARLY_SALES);
                String business_ebitda = Business_profile_list.get(position).get(TAG_BUSINESS_EBITDA);
                String business_ebitda_range = Business_profile_list.get(position).get(TAG_BUSINESS_EBITDA_RANGE);
                String business_investment = Business_profile_list.get(position).get(TAG_BUISNESS_INVESTMENT);
                String business_description = Business_profile_list.get(position).get(TAG_BUISNESS_DESCRIPTION);
                String business_interest_name = Business_profile_list.get(position).get(TAG_BUSINESS_INTEREST_NAME);
                String business_role_name = Business_profile_list.get(position).get(TAG_BUSINESS_ROLE_NAME);
                String business_country_currency = Business_profile_list.get(position).get(TAG_COUNTRY_CURRENCY);
                String business_business_currency = Business_profile_list.get(position).get(TAG_BUSINESS_CURRENCY);
                String business_sell_lease = Business_profile_list.get(position).get(TAG_BUSINESS_SELL_LEASE);
                String business_assets_features = Business_profile_list.get(position).get(TAG_BUSINESS_ASSETS_FEATURES);
                String business_sell_lease_price = Business_profile_list.get(position).get(TAG_BUSINESS_SELL_LEASE_PRICE);
                String business_sell_lease_cost = Business_profile_list.get(position).get(TAG_BUSINESS_SELL_LEASE_COST);
                String business_sell_type = Business_profile_list.get(position).get(TAG_BUSINESS_SELL_TYPE);
                String business_assets_purchased = Business_profile_list.get(position).get(TAG_BUSINESS_ASSETS_PURCHASED);
                String business_assets_description = Business_profile_list.get(position).get(TAG_BUSINESS_ASSETS_DESCRIPTION);
                String business_user_role = Business_profile_list.get(position).get(TAG_BUSINESS_USER_ROLE);
                String business_user_interest_in = Business_profile_list.get(position).get(TAG_BUSINESS_USER_INTEREST_IN);
                String business_mobile_code = Business_profile_list.get(position).get(TAG_BUSINESS_MOBILE_CODE);
                String business_user_name = Business_profile_list.get(position).get(TAG_BUSINESS_USER_NAME);
                String business_user_mobile = Business_profile_list.get(position).get(TAG_BUSINESS_USER_MOBILE);
                String business_user_email = Business_profile_list.get(position).get(TAG_BUSINESS_USER_EMAIL);
                String business_company_name = Business_profile_list.get(position).get(TAG_BUSINESS_COMPANY_NAME);
                String business_employee_count = Business_profile_list.get(position).get(TAG_BUSINESS_EMPLOYEE_COUNT);
                String business_established = Business_profile_list.get(position).get(TAG_BUSINESS_ESTABLISHED);
                String business_products_services = Business_profile_list.get(position).get(TAG_BUISNESS_PRODUCTS_SERVICES);
                String business_facility = Business_profile_list.get(position).get(TAG_BUISNESS_FACILITY);
                String business_assets_reason = Business_profile_list.get(position).get(TAG_BUSINESS_ASSETS_REASON);
                String business_month_sales = Business_profile_list.get(position).get(TAG_BUSINESS_MONTH_SALES);
                String business_tentative_price = Business_profile_list.get(position).get(TAG_BUSINESS_TENTATIVE_PRICE);
                String business_legal_entity_type = Business_profile_list.get(position).get(TAG_BUSINESS_LEGAL_ENTITY_TYPE);

                String business_location_name = Business_profile_list.get(position).get(TAG_LOCATION_NAME);
                String business_location_key = Business_profile_list.get(position).get(TAG_LOCATION_KEY);


                SharedPreferences sharedPreferences = PreferenceManager
                        .getDefaultSharedPreferences(getActivity());
                SharedPreferences.Editor editor = sharedPreferences.edit();

                editor.putString("business_id", business_id);
                editor.putString("business_key", business_key);
                editor.putString("business_short_des", business_short_des);
                editor.putString("business_yearly_sales", business_yearly_sales);
                editor.putString("business_ebitda", business_ebitda);
                editor.putString("business_ebitda_range", business_ebitda_range);
                editor.putString("business_investment", business_investment);
                editor.putString("business_description", business_description);
                editor.putString("business_interest_name", business_interest_name);
                editor.putString("business_role_name", business_role_name);
                editor.putString("business_country_currency", business_country_currency);
                editor.putString("business_business_currency", business_business_currency);
                editor.putString("business_sell_lease", business_sell_lease);
                editor.putString("business_assets_features", business_assets_features);
                editor.putString("business_sell_lease_price", business_sell_lease_price);
                editor.putString("business_sell_lease_cost", business_sell_lease_cost);
                editor.putString("business_sell_type", business_sell_type);
                editor.putString("business_assets_purchased", business_assets_purchased);
                editor.putString("business_assets_description", business_assets_description);
                editor.putString("business_user_role", business_user_role);
                editor.putString("business_user_interest_in", business_user_interest_in);
                editor.putString("business_mobile_code", business_mobile_code);
                editor.putString("business_user_name", business_user_name);
                editor.putString("business_user_mobile", business_user_mobile);
                editor.putString("business_user_email", business_user_email);
                editor.putString("business_company_name", business_company_name);
                editor.putString("business_employee_count", business_employee_count);
                editor.putString("business_established", business_established);
                editor.putString("business_products_services", business_products_services);
                editor.putString("business_facility", business_facility);
                editor.putString("business_assets_reason", business_assets_reason);
                editor.putString("business_month_sales", business_month_sales);
                editor.putString("business_tentative_price", business_tentative_price);
                editor.putString("business_legal_entity_type", business_legal_entity_type);
                editor.putString("business_location_name", business_location_name);
                editor.putString("business_location_key", business_location_key);
                editor.commit();

                Intent i = new Intent(getActivity(), Activity_BusinessProfile_Update.class);
                startActivity(i);

            }

        });


        // Inflate the layout for this fragment
        return rootview;


    }

    @Override
    public void onRefresh() {
        try {
            queue = Volley.newRequestQueue(getActivity());
            Get_Business_Profiles();

        } catch (Exception e) {
            // TODO: handle exception
        }
    }


    /*****************************
     * GET Business Profiles
     ***************************/

    public void Get_Business_Profiles() {

        String tag_json_obj = "json_obj_req";
        System.out.println("CAME 1");
        StringRequest request = new StringRequest(Request.Method.POST,
                AppConfig.url_user_business_profiles, new Response.Listener<String>() {

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
                            JSONObject obj1 = arr.getJSONObject(i);

                            String business_id = obj1.getString(TAG_BUSINESS_ID);
                            String business_key = obj1.getString(TAG_BUSINESS_KEY);
                            String buisness_short_description = obj1.getString(TAG_BUSINESS_SHORT_DES);
                            String business_yearly_sales = obj1.getString(TAG_BUSINESS_YEARLY_SALES);
                            String business_ebitda = obj1.getString(TAG_BUSINESS_EBITDA);
                            String business_ebitda_range = obj1.getString(TAG_BUSINESS_EBITDA_RANGE);
                            String buisness_investment = obj1.getString(TAG_BUISNESS_INVESTMENT);
                            String buisness_description = obj1.getString(TAG_BUISNESS_DESCRIPTION);
                            String business_interest_name = obj1.getString(TAG_BUSINESS_INTEREST_NAME);
                            String business_role_name = obj1.getString(TAG_BUSINESS_ROLE_NAME);
                            String country_currency = obj1.getString(TAG_COUNTRY_CURRENCY);
                            String business_currency = obj1.getString(TAG_BUSINESS_CURRENCY);
                            String business_sell_lease = obj1.getString(TAG_BUSINESS_SELL_LEASE);
                            String business_assets_features = obj1.getString(TAG_BUSINESS_ASSETS_FEATURES);
                            String business_sell_lease_price = obj1.getString(TAG_BUSINESS_SELL_LEASE_PRICE);
                            String business_sell_lease_cost = obj1.getString(TAG_BUSINESS_SELL_LEASE_COST);
                            String business_sell_type = obj1.getString(TAG_BUSINESS_SELL_TYPE);
                            String business_assets_purchased = obj1.getString(TAG_BUSINESS_ASSETS_PURCHASED);
                            String business_assets_description = obj1.getString(TAG_BUSINESS_ASSETS_DESCRIPTION);
                            String business_user_role = obj1.getString(TAG_BUSINESS_USER_ROLE);
                            String business_user_interest_in = obj1.getString(TAG_BUSINESS_USER_INTEREST_IN);
                            String business_mobile_code = obj1.getString(TAG_BUSINESS_MOBILE_CODE);
                            String business_user_name = obj1.getString(TAG_BUSINESS_USER_NAME);
                            String business_user_mobile = obj1.getString(TAG_BUSINESS_USER_MOBILE);
                            String business_user_email = obj1.getString(TAG_BUSINESS_USER_EMAIL);
                            String business_company_name = obj1.getString(TAG_BUSINESS_COMPANY_NAME);
                            String business_employee_count = obj1.getString(TAG_BUSINESS_EMPLOYEE_COUNT);
                            String business_established = obj1.getString(TAG_BUSINESS_ESTABLISHED);
                            String buisness_products_services = obj1.getString(TAG_BUISNESS_PRODUCTS_SERVICES);
                            String buisness_facility = obj1.getString(TAG_BUISNESS_FACILITY);
                            String business_assets_reason = obj1.getString(TAG_BUSINESS_ASSETS_REASON);
                            String business_month_sales = obj1.getString(TAG_BUSINESS_MONTH_SALES);
                            String business_tentative_price = obj1.getString(TAG_BUSINESS_TENTATIVE_PRICE);
                            String business_legal_entity_type = obj1.getString(TAG_BUSINESS_LEGAL_ENTITY_TYPE);
                            //  String location_name = obj1.getString(TAG_LOCATION_NAME);
                            //   String location_key = obj1.getString(TAG_LOCATION_KEY);


                            // creating new HashMap
                            HashMap<String, String> map = new HashMap<String, String>();

                            // adding each child node to HashMap key => value
                            map.put(TAG_BUSINESS_ID, business_id);
                            map.put(TAG_BUSINESS_KEY, business_key);
                            map.put(TAG_BUSINESS_SHORT_DES, buisness_short_description);
                            map.put(TAG_BUSINESS_YEARLY_SALES, business_yearly_sales);
                            map.put(TAG_BUSINESS_EBITDA, business_ebitda);
                            map.put(TAG_BUSINESS_EBITDA_RANGE, business_ebitda_range);
                            map.put(TAG_BUISNESS_INVESTMENT, buisness_investment);
                            map.put(TAG_BUISNESS_DESCRIPTION, buisness_description);
                            map.put(TAG_BUSINESS_INTEREST_NAME, business_interest_name);
                            map.put(TAG_BUSINESS_ROLE_NAME, business_role_name);
                            map.put(TAG_COUNTRY_CURRENCY, country_currency);
                            map.put(TAG_BUSINESS_CURRENCY, business_currency);
                            map.put(TAG_BUSINESS_SELL_LEASE, business_sell_lease);
                            map.put(TAG_BUSINESS_ASSETS_FEATURES, business_assets_features);
                            map.put(TAG_BUSINESS_SELL_LEASE_PRICE, business_sell_lease_price);
                            map.put(TAG_BUSINESS_SELL_LEASE_COST, business_sell_lease_cost);
                            map.put(TAG_BUSINESS_SELL_TYPE, business_sell_type);
                            map.put(TAG_BUSINESS_ASSETS_PURCHASED, business_assets_purchased);
                            map.put(TAG_BUSINESS_ASSETS_DESCRIPTION, business_assets_description);
                            map.put(TAG_BUSINESS_USER_ROLE, business_user_role);
                            map.put(TAG_BUSINESS_USER_INTEREST_IN, business_user_interest_in);
                            map.put(TAG_BUSINESS_MOBILE_CODE, business_mobile_code);
                            map.put(TAG_BUSINESS_USER_NAME, business_user_name);
                            map.put(TAG_BUSINESS_USER_MOBILE, business_user_mobile);
                            map.put(TAG_BUSINESS_USER_EMAIL, business_user_email);
                            map.put(TAG_BUSINESS_COMPANY_NAME, business_company_name);
                            map.put(TAG_BUSINESS_EMPLOYEE_COUNT, business_employee_count);
                            map.put(TAG_BUSINESS_ESTABLISHED, business_established);
                            map.put(TAG_BUISNESS_PRODUCTS_SERVICES, buisness_products_services);
                            map.put(TAG_BUISNESS_FACILITY, buisness_facility);
                            map.put(TAG_BUSINESS_ASSETS_REASON, business_assets_reason);
                            map.put(TAG_BUSINESS_MONTH_SALES, business_month_sales);
                            map.put(TAG_BUSINESS_TENTATIVE_PRICE, business_tentative_price);
                            map.put(TAG_BUSINESS_LEGAL_ENTITY_TYPE, business_legal_entity_type);

                            // map.put(TAG_LOCATION_NAME, business_id);
                            //  map.put(TAG_LOCATION_KEY, business_id);

                            Business_profile_list.add(map);

                            adapter = new BusinessProfiles_Adapter(getActivity(),
                                    Business_profile_list);
                            List.setAdapter(adapter);

                            System.out.println("HASHMAP ARRAY" + Business_profile_list);

                        }

                    } else if (success == 0) {

                        swipeRefreshLayout.setRefreshing(false);
                        adapter = new BusinessProfiles_Adapter(getActivity(),
                                Business_profile_list);
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

                System.out.println("user_id" + "1");

                return params;
            }

        };

        // Adding request to request queue
        queue.add(request);
    }


}