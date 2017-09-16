package banyan.com.awesomebusiness.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.text.TextUtils;
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
import banyan.com.awesomebusiness.adapter.BusinessProfiles_Adapter;
import banyan.com.awesomebusiness.adapter.FranchiseProfiles_Adapter;
import banyan.com.awesomebusiness.global.AppConfig;
import banyan.com.awesomebusiness.global.SessionManager;
import butterknife.ButterKnife;

/**
 * Created by Jo on 9/4/2017.
 */
public class Tab_Franchises_Profile extends Fragment implements SwipeRefreshLayout.OnRefreshListener {


    String str_user_name, str_user_id;

    public static RequestQueue queue;

    // Session Manager Class
    SessionManager session;

    String TAG = "add task";

    private ListView List;
    private SwipeRefreshLayout swipeRefreshLayout;


    public static final String TAG_FRANCHISE_ID = "franchise_id";
    public static final String TAG_FRANCHISE_KEY = "franchise_key";
    public static final String TAG_FRANCHISE_USER_NAME = "franchise_user_name";
    public static final String TAG_FRANCHISE_EMAIL = "franchise_email";
    public static final String TAG_FRANCHISE_MOBILE = "franchise_mobile";
    public static final String TAG_FRANCHISE_DESIGNATION = "franchise_designation";
    public static final String TAG_FRANCHISE_BRAND_NAME = "franchise_brand_name";
    public static final String TAG_FRANCHISE_BRAND_OFFERING = "franchise_brand_offering";
    public static final String TAG_FRANCHISE_BRAND_COMPANY = "franchise_brand_company";
    public static final String TAG_FRANCHISE_BRAND_SERVICES = "franchise_brand_services";
    public static final String TAG_FRANCHISE_BRAND_ESTABLISHED = "franchise_brand_established";
    public static final String TAG_FRANCHISE_BRAND_HEADQUATERS = "franchise_brand_headquaters";
    public static final String TAG_BRAND_SALEPARTNER_COUNT = "brand_salepartner_count";
    public static final String TAG_BRAND_SALEPARTNER_BEFORE_PARTNERING = "brand_salepartner_before_partnering";
    public static final String TAG_BRAND_SALEPARTNER_EXPECT = "brand_salepartner_expect";
    public static final String TAG_BRAND_SALEPARTNER_PROCEDURE = "brand_salepartner_procedure";
    public static final String TAG_FRANCHISE_FORMAT = "franchise_format";
    public static final String TAG_FRANCHISE_LOGO = "franchise_logo";
    public static final String TAG_FRANCHISE_CURRENCY = "franchise_currency";
    public static final String TAG_COUNTRY_CURRENCY = "country_currency";
    public static final String TAG_FRANCHISE_STATUS = "franchise_status";

    public static final String TAG_LOCATION_NAME = "location_name";
    public static final String TAG_LOCATION_KEY = "location_key";

    public static final String TAG_INDUSTRY_NAME = "industry_name";
    public static final String TAG_INDUSTRY_KEY = "industry_key";

    public static final String TAG_IMAGE_PATH = "image_path";

    public static final String TAG_DOCUMENT_PATH = "document_path";

    public static final String TAG_FRANCHISE_FORMAT_ID = "franchise_format_id";
    public static final String TAG_FRANCHISE_FORMAT_NAME = "franchise_format_name";
    public static final String TAG_FRANCHISE_FORMAT_MIN_INVESTMENT = "franchise_format_min_investment";
    public static final String TAG_FRANCHISE_FORMAT_MAX_INVESTMENT = "franchise_format_max_investment";
    public static final String TAG_FRANCHISE_FORMAT_FEE = "franchise_format_fee";
    public static final String TAG_FRANCHISE_FORMAT_NO_OF_STAFFS = "franchise_format_no_of_staffs";
    public static final String TAG_FRANCHISE_FORMAT_ROYALITY = "franchise_format_royality";
    public static final String TAG_FRANCHISE_FORMAT_REVENUE = "franchise_format_revenue";
    public static final String TAG_FRANCHISE_FORMAT_PROFIT = "franchise_format_profit";
    public static final String TAG_FRANCHISE_FORMAT_CREATED_ON = "franchise_format_created_on";
    public static final String TAG_FRANCHISE_FORMAT_ACT = "franchise_format_act";
    public static final String TAG_FRANCHISE_FORMAT_MIN_SQFT = "franchise_format_min_sqft";
    public static final String TAG_FRANCHISE_FORMAT_MAX_SQFT = "franchise_format_max_sqft";

    ArrayList<String> Arraylist_update_location = null;
    ArrayList<String> Arraylist_update_industries = null;
    ArrayList<String> Arraylist_update_images = null;

    String str_final_location, str_final_industry, str_final_image = "";

    static ArrayList<HashMap<String, String>> Franchise_profile_list;

    HashMap<String, String> params = new HashMap<String, String>();

    public FranchiseProfiles_Adapter adapter;

    private static final int REQUEST_CODE = 1;

    public Tab_Franchises_Profile() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View rootview = inflater.inflate(R.layout.tab_franchises, container, false);
        ButterKnife.bind(getActivity());

        session = new SessionManager(getActivity());

        session.checkLogin();
        HashMap<String, String> user = session.getUserDetails();
        str_user_name = user.get(SessionManager.KEY_USER);
        str_user_id = user.get(SessionManager.KEY_USER_ID);


        List = (ListView) rootview.findViewById(R.id.my_franchises_profile);

        swipeRefreshLayout = (SwipeRefreshLayout) rootview.findViewById(R.id.franchise_profiles_swipe_refresh_layout);

        swipeRefreshLayout.setOnRefreshListener(this);

        swipeRefreshLayout.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        swipeRefreshLayout.setRefreshing(true);

                                        try {
                                            queue = Volley.newRequestQueue(getActivity());
                                            Get_Franchise_Profiles();

                                        } catch (Exception e) {
                                            // TODO: handle exceptions
                                        }
                                    }
                                }
        );


        Arraylist_update_location = new ArrayList<String>();
        Arraylist_update_industries = new ArrayList<String>();
        Arraylist_update_images = new ArrayList<String>();


        // Hashmap for ListView
        Franchise_profile_list = new ArrayList<HashMap<String, String>>();

        List.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {

                System.out.println("ITEM CLICKED");

                String franchise_id = Franchise_profile_list.get(position).get(TAG_FRANCHISE_ID);
                String franchise_key = Franchise_profile_list.get(position).get(TAG_FRANCHISE_KEY);
                String franchise_user_name = Franchise_profile_list.get(position).get(TAG_FRANCHISE_USER_NAME);
                String franchise_email = Franchise_profile_list.get(position).get(TAG_FRANCHISE_EMAIL);
                String franchise_mobile = Franchise_profile_list.get(position).get(TAG_FRANCHISE_MOBILE);
                String franchise_designation = Franchise_profile_list.get(position).get(TAG_FRANCHISE_DESIGNATION);
                String franchise_brand_name = Franchise_profile_list.get(position).get(TAG_FRANCHISE_BRAND_NAME);
                String franchise_brand_offering = Franchise_profile_list.get(position).get(TAG_FRANCHISE_BRAND_OFFERING);
                String franchise_brand_company = Franchise_profile_list.get(position).get(TAG_FRANCHISE_BRAND_COMPANY);
                String franchise_brand_services = Franchise_profile_list.get(position).get(TAG_FRANCHISE_BRAND_SERVICES);
                String franchise_brand_established = Franchise_profile_list.get(position).get(TAG_FRANCHISE_BRAND_ESTABLISHED);
                String franchise_brand_headquaters = Franchise_profile_list.get(position).get(TAG_FRANCHISE_BRAND_HEADQUATERS);
                String brand_salepartner_count = Franchise_profile_list.get(position).get(TAG_BRAND_SALEPARTNER_COUNT);
                String brand_salepartner_before_partnering = Franchise_profile_list.get(position).get(TAG_BRAND_SALEPARTNER_BEFORE_PARTNERING);
                String brand_salepartner_expect = Franchise_profile_list.get(position).get(TAG_BRAND_SALEPARTNER_EXPECT);
                String brand_salepartner_procedure = Franchise_profile_list.get(position).get(TAG_BRAND_SALEPARTNER_PROCEDURE);
                String franchise_format = Franchise_profile_list.get(position).get(TAG_FRANCHISE_FORMAT);
                String franchise_logo = Franchise_profile_list.get(position).get(TAG_FRANCHISE_LOGO);
                String franchise_currency = Franchise_profile_list.get(position).get(TAG_FRANCHISE_CURRENCY);
                String country_currency = Franchise_profile_list.get(position).get(TAG_COUNTRY_CURRENCY);

                SharedPreferences sharedPreferences = PreferenceManager
                        .getDefaultSharedPreferences(getActivity());
                SharedPreferences.Editor editor = sharedPreferences.edit();

                editor.putString("franchise_id", franchise_id);
                editor.putString("franchise_key", franchise_key);
                editor.putString("franchise_user_name", franchise_user_name);
                editor.putString("franchise_email", franchise_email);
                editor.putString("franchise_mobile", franchise_mobile);
                editor.putString("franchise_designation", franchise_designation);
                editor.putString("franchise_brand_name", franchise_brand_name);
                editor.putString("franchise_brand_offering", franchise_brand_offering);
                editor.putString("franchise_brand_company", franchise_brand_company);
                editor.putString("franchise_brand_services", franchise_brand_services);
                editor.putString("franchise_brand_established", franchise_brand_established);
                editor.putString("franchise_brand_headquaters", franchise_brand_headquaters);
                editor.putString("brand_salepartner_count", brand_salepartner_count);
                editor.putString("brand_salepartner_before_partnering", brand_salepartner_before_partnering);
                editor.putString("brand_salepartner_expect", brand_salepartner_expect);
                editor.putString("brand_salepartner_procedure", brand_salepartner_procedure);
                editor.putString("franchise_format", franchise_format);
                editor.putString("franchise_logo", franchise_logo);
                editor.putString("franchise_currency", franchise_currency);
                editor.putString("country_currency", country_currency);

                editor.commit();

                Intent i = new Intent(getActivity(), Activity_FranchiseProfile_Update.class);
                startActivity(i);


            }

        });

        // Inflate the layout for this fragment
        return rootview;
    }


    @Override
    public void onRefresh() {
        try {
            Franchise_profile_list.clear();
            System.out.println("USER IDDDD" + str_user_id);
            queue = Volley.newRequestQueue(getActivity());
            Get_Franchise_Profiles();

        } catch (Exception e) {
            // TODO: handle exception
        }
    }


    /*****************************
     * GET Business Profiles
     ***************************/

    public void Get_Franchise_Profiles() {

        String tag_json_obj = "json_obj_req";
        System.out.println("CAME 1");
        StringRequest request = new StringRequest(Request.Method.POST,
                AppConfig.url_user_franchise_profiles, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d(TAG, response.toString());
                try {
                    JSONObject obj = new JSONObject(response);
                    int success = obj.getInt("status");

                    if (success == 1) {

                        JSONArray arr;
                        JSONArray arr_location;
                        JSONArray arr_industry;
                        JSONArray arr_images;

                        arr = obj.getJSONArray("data");
                        for (int i = 0; arr.length() > i; i++) {
                            JSONObject obj1 = arr.getJSONObject(i);

                            String franchise_id = obj1.getString(TAG_FRANCHISE_ID);
                            String franchise_key = obj1.getString(TAG_FRANCHISE_KEY);
                            String franchise_user_name = obj1.getString(TAG_FRANCHISE_USER_NAME);
                            String franchise_email = obj1.getString(TAG_FRANCHISE_EMAIL);
                            String franchise_mobile = obj1.getString(TAG_FRANCHISE_MOBILE);
                            String franchise_designation = obj1.getString(TAG_FRANCHISE_DESIGNATION);
                            String franchise_brand_name = obj1.getString(TAG_FRANCHISE_BRAND_NAME);
                            String franchise_brand_offering = obj1.getString(TAG_FRANCHISE_BRAND_OFFERING);
                            String franchise_brand_company = obj1.getString(TAG_FRANCHISE_BRAND_COMPANY);
                            String franchise_brand_services = obj1.getString(TAG_FRANCHISE_BRAND_SERVICES);
                            String franchise_brand_established = obj1.getString(TAG_FRANCHISE_BRAND_ESTABLISHED);
                            String franchise_brand_headquaters = obj1.getString(TAG_FRANCHISE_BRAND_HEADQUATERS);
                            String brand_salepartner_count = obj1.getString(TAG_BRAND_SALEPARTNER_COUNT);
                            String brand_salepartner_before_partnering = obj1.getString(TAG_BRAND_SALEPARTNER_BEFORE_PARTNERING);
                            String brand_salepartner_expect = obj1.getString(TAG_BRAND_SALEPARTNER_EXPECT);
                            String brand_salepartner_procedure = obj1.getString(TAG_BRAND_SALEPARTNER_PROCEDURE);
                            String franchise_format = obj1.getString(TAG_FRANCHISE_FORMAT);
                            String franchise_logo = obj1.getString(TAG_FRANCHISE_LOGO);
                            String franchise_currency = obj1.getString(TAG_FRANCHISE_CURRENCY);
                            String country_currency = obj1.getString(TAG_COUNTRY_CURRENCY);
                            String franchise_status = obj1.getString(TAG_FRANCHISE_STATUS);

                            arr_location = obj1.getJSONArray("location");
                            if (arr_location != null) {
                                Arraylist_update_location.clear();
                                for (int j = 0; arr_location.length() > j; j++) {
                                    JSONObject obj_location = arr_location.getJSONObject(j);

                                    String location_name = obj_location.getString(TAG_LOCATION_NAME);
                                    String location_key = obj_location.getString(TAG_LOCATION_KEY);

                                    Arraylist_update_location.add(location_name);

                                }
                                str_final_location = TextUtils.join(", ", Arraylist_update_location);
                            }

                            arr_industry = obj1.getJSONArray("industry");
                            if (arr_industry != null) {
                                System.out.println("Length Industry:: " + arr_industry.length());
                                Arraylist_update_industries.clear();
                                for (int k = 0; arr_industry.length() > k; k++) {
                                    JSONObject obj_indus = arr_industry.getJSONObject(k);
                                    System.out.println("INDUS :: " + obj_indus);
                                    String industry_name = obj_indus.getString(TAG_INDUSTRY_NAME);
                                    String industry_key = obj_indus.getString(TAG_INDUSTRY_KEY);

                                    Arraylist_update_industries.add(industry_name);
                                }
                                str_final_industry = TextUtils.join(", ", Arraylist_update_industries);
                            }

                            arr_images = obj1.getJSONArray("images");
                            if (arr_images != null) {
                                System.out.println("Length images:: " + arr_images.length());
                                Arraylist_update_images.clear();
                                for (int l = 0; arr_images.length() > l; l++) {
                                    JSONObject obj_image = arr_images.getJSONObject(l);
                                    String image_path = obj_image.getString(TAG_IMAGE_PATH);

                                    Arraylist_update_images.add(image_path);
                                }

                                if (Arraylist_update_images.size() > 0){
                                    str_final_image = Arraylist_update_images.get(0);
                                    System.out.println("IMAGE : " + str_final_image);
                                }
                            }

                            /*
                            String location_name = obj1.getString(TAG_LOCATION_NAME);
                            String location_key = obj1.getString(TAG_LOCATION_KEY);
                            String industry_name = obj1.getString(TAG_INDUSTRY_NAME);
                            String industry_key = obj1.getString(TAG_INDUSTRY_KEY);
                            String document_path = obj1.getString(TAG_DOCUMENT_PATH);

                            String franchise_format_id = obj1.getString(TAG_FRANCHISE_FORMAT_ID);
                            String franchise_format_name = obj1.getString(TAG_FRANCHISE_FORMAT_NAME);
                            String franchise_format_min_investment = obj1.getString(TAG_FRANCHISE_FORMAT_MIN_INVESTMENT);
                            String franchise_format_max_investment = obj1.getString(TAG_FRANCHISE_FORMAT_MAX_INVESTMENT);
                            String franchise_format_fee = obj1.getString(TAG_FRANCHISE_FORMAT_FEE);
                            String franchise_format_no_of_staffs = obj1.getString(TAG_FRANCHISE_FORMAT_NO_OF_STAFFS);
                            String franchise_format_royality = obj1.getString(TAG_FRANCHISE_FORMAT_ROYALITY);
                            String franchise_format_revenue = obj1.getString(TAG_FRANCHISE_FORMAT_REVENUE);
                            String franchise_format_profit = obj1.getString(TAG_FRANCHISE_FORMAT_PROFIT);
                            String franchise_format_created_on = obj1.getString(TAG_FRANCHISE_FORMAT_CREATED_ON);
                            String franchise_format_act = obj1.getString(TAG_FRANCHISE_FORMAT_ACT);
                            String franchise_format_min_sqft = obj1.getString(TAG_FRANCHISE_FORMAT_MIN_SQFT);
                            String franchise_format_max_sqft = obj1.getString(TAG_FRANCHISE_FORMAT_MAX_SQFT);
                            */


                            // creating new HashMap
                            HashMap<String, String> map = new HashMap<String, String>();

                            // adding each child node to HashMap key => value
                            map.put(TAG_FRANCHISE_ID, franchise_id);
                            map.put(TAG_FRANCHISE_KEY, franchise_key);
                            map.put(TAG_FRANCHISE_USER_NAME, franchise_user_name);
                            map.put(TAG_FRANCHISE_EMAIL, franchise_email);
                            map.put(TAG_FRANCHISE_MOBILE, franchise_mobile);
                            map.put(TAG_FRANCHISE_DESIGNATION, franchise_designation);
                            map.put(TAG_FRANCHISE_BRAND_NAME, franchise_brand_name);
                            map.put(TAG_FRANCHISE_BRAND_OFFERING, franchise_brand_offering);
                            map.put(TAG_FRANCHISE_BRAND_COMPANY, franchise_brand_company);
                            map.put(TAG_FRANCHISE_BRAND_SERVICES, franchise_brand_services);
                            map.put(TAG_FRANCHISE_BRAND_ESTABLISHED, franchise_brand_established);
                            map.put(TAG_FRANCHISE_BRAND_HEADQUATERS, franchise_brand_headquaters);
                            map.put(TAG_BRAND_SALEPARTNER_COUNT, brand_salepartner_count);
                            map.put(TAG_BRAND_SALEPARTNER_BEFORE_PARTNERING, brand_salepartner_before_partnering);
                            map.put(TAG_BRAND_SALEPARTNER_EXPECT, brand_salepartner_expect);
                            map.put(TAG_BRAND_SALEPARTNER_PROCEDURE, brand_salepartner_procedure);
                            map.put(TAG_FRANCHISE_FORMAT, franchise_format);
                            map.put(TAG_FRANCHISE_LOGO, franchise_logo);
                            map.put(TAG_FRANCHISE_CURRENCY, franchise_currency);
                            map.put(TAG_COUNTRY_CURRENCY, country_currency);
                            map.put(TAG_FRANCHISE_STATUS, franchise_status);

                            map.put(TAG_LOCATION_NAME, str_final_location);
                            map.put(TAG_INDUSTRY_NAME, str_final_industry);
                            map.put(TAG_IMAGE_PATH, str_final_image);

                            /*
                            map.put(TAG_LOCATION_NAME, location_name);
                            map.put(TAG_LOCATION_KEY, location_key);
                            map.put(TAG_INDUSTRY_NAME, industry_name);
                            map.put(TAG_INDUSTRY_KEY, industry_key);
                            map.put(TAG_DOCUMENT_PATH, document_path);

                            map.put(TAG_FRANCHISE_FORMAT_ID, franchise_format_id);
                            map.put(TAG_FRANCHISE_FORMAT_NAME, franchise_format_name);
                            map.put(TAG_FRANCHISE_FORMAT_MIN_INVESTMENT, franchise_format_min_investment);
                            map.put(TAG_FRANCHISE_FORMAT_MAX_INVESTMENT, franchise_format_max_investment);
                            map.put(TAG_FRANCHISE_FORMAT_FEE, franchise_format_fee);
                            map.put(TAG_FRANCHISE_FORMAT_NO_OF_STAFFS, franchise_format_no_of_staffs);
                            map.put(TAG_FRANCHISE_FORMAT_ROYALITY, franchise_format_royality);
                            map.put(TAG_FRANCHISE_FORMAT_REVENUE, franchise_format_revenue);
                            map.put(TAG_FRANCHISE_FORMAT_PROFIT, franchise_format_profit);
                            map.put(TAG_FRANCHISE_FORMAT_CREATED_ON, franchise_format_created_on);
                            map.put(TAG_FRANCHISE_FORMAT_ACT, franchise_format_act);
                            map.put(TAG_FRANCHISE_FORMAT_MIN_SQFT, franchise_format_min_sqft);
                            map.put(TAG_FRANCHISE_FORMAT_MAX_SQFT, franchise_format_max_sqft);

                            */


                            Franchise_profile_list.add(map);

                            System.out.println("HASHMAP ARRAY" + Franchise_profile_list);


                            adapter = new FranchiseProfiles_Adapter(getActivity(),
                                    Franchise_profile_list);
                            List.setAdapter(adapter);

                        }

                    } else if (success == 0) {

                        adapter = new FranchiseProfiles_Adapter(getActivity(),
                                Franchise_profile_list);
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
