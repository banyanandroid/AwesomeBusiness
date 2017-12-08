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
import banyan.com.awesomebusiness.adapter.AdvisorProfiles_Adapter;
import banyan.com.awesomebusiness.adapter.BusinessProfiles_Adapter;
import banyan.com.awesomebusiness.global.AppConfig;
import banyan.com.awesomebusiness.global.SessionManager;

/**
 * Created by Jo on 9/4/2017.
 */
public class Tab_Advisor_Profile extends Fragment implements SwipeRefreshLayout.OnRefreshListener {

    String str_user_name, str_user_id;

    public static RequestQueue queue;

    // Session Manager Class
    SessionManager session;

    String TAG = "add task";

    private ListView List;
    private SwipeRefreshLayout swipeRefreshLayout;


    public static final String TAG_ADVISOR_ID = "advisor_id";
    public static final String TAG_ADVISOR_KEY = "advisor_key";
    public static final String TAG_ADVISOR_USER_ID = "advisor_user_id";
    public static final String TAG_ADVISOR_NAME = "advisor_name";
    public static final String TAG_ADVISOR_EMAIL = "advisor_email";
    public static final String TAG_ADVISOR_MOBILE = "advisor_mobile";
    public static final String TAG_ADVISOR_COMPANY_NAME = "advisor_company_name";
    public static final String TAG_ADVISOR_ADDRESS_1 = "advisor_address_1";
    public static final String TAG_ADVISOR_ADDRESS_2 = "advisor_address_2";
    public static final String TAG_ADVISOR_CITY_ID = "advisor_city_id";
    public static final String TAG_ADVISOR_TYPE = "advisor_type";
    public static final String TAG_ADVISOR_ZIPCODE = "advisor_zipcode";
    public static final String TAG_ADVISOR_PHONE_NO = "advisor_phone_no";
    public static final String TAG_ADVISOR_ABOUT_COMPANY = "advisor_about_company";
    public static final String TAG_ADVISOR_IP_ADDRESS = "advisor_ip_address";
    public static final String TAG_ADVISOR_CREATED_ON = "advisor_created_on";
    public static final String TAG_ADVISOR_FORMAT_ACT = "advisor_format_act";
    public static final String TAG_ADVISOR_APPEARENCE = "advisor_appearence";
    public static final String TAG_RATING = "rating";
    public static final String TAG_ADVISOR_STATUS = "advisor_status";

    public static final String TAG_LOCATION_NAME = "location_name";
    public static final String TAG_LOCATION_KEY = "location_key";

    public static final String TAG_INDUSTRY_NAME = "industry_name";
    public static final String TAG_INDUSTRY_KEY = "industry_key";

    public static final String TAG_IMAGE_PATH = "image_path";

    ArrayList<String> Arraylist_update_location = null;
    ArrayList<String> Arraylist_update_industries = null;
    ArrayList<String> Arraylist_update_images = null;

    String str_final_location, str_final_industry, str_final_image = "";

    static ArrayList<HashMap<String, String>> Advisor_profile_list;

    HashMap<String, String> params = new HashMap<String, String>();

    public AdvisorProfiles_Adapter adapter;


    public Tab_Advisor_Profile() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootview = inflater.inflate(R.layout.tab_advisor_profile, null);

        session = new SessionManager(getActivity());
        session.checkLogin();
        HashMap<String, String> user = session.getUserDetails();
        str_user_name = user.get(SessionManager.KEY_USER);
        str_user_id = user.get(SessionManager.KEY_USER_ID);

        List = (ListView) rootview.findViewById(R.id.my_advisor_profile);
        swipeRefreshLayout = (SwipeRefreshLayout) rootview.findViewById(R.id.my_advisorprofile_swipe_refresh_layout);

        swipeRefreshLayout.setOnRefreshListener(this);

        swipeRefreshLayout.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        swipeRefreshLayout.setRefreshing(true);

                                        try {
                                            queue = Volley.newRequestQueue(getActivity());
                                            Get_Advisor_Profiles();

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
        Advisor_profile_list = new ArrayList<HashMap<String, String>>();


        List.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {

                System.out.println("ITEM CLICKED");

                String advisor_id = Advisor_profile_list.get(position).get(TAG_ADVISOR_ID);
                String advisor_key = Advisor_profile_list.get(position).get(TAG_ADVISOR_KEY);
                String advisor_user_id = Advisor_profile_list.get(position).get(TAG_ADVISOR_USER_ID);
                String advisor_name = Advisor_profile_list.get(position).get(TAG_ADVISOR_NAME);
                String advisor_email = Advisor_profile_list.get(position).get(TAG_ADVISOR_EMAIL);
                String advisor_mobile = Advisor_profile_list.get(position).get(TAG_ADVISOR_MOBILE);
                String advisor_company_name = Advisor_profile_list.get(position).get(TAG_ADVISOR_COMPANY_NAME);
                String advisor_address_1 = Advisor_profile_list.get(position).get(TAG_ADVISOR_ADDRESS_1);
                String advisor_address_2 = Advisor_profile_list.get(position).get(TAG_ADVISOR_ADDRESS_2);
                String advisor_city_id = Advisor_profile_list.get(position).get(TAG_ADVISOR_CITY_ID);
                String advisor_type = Advisor_profile_list.get(position).get(TAG_ADVISOR_TYPE);
                String advisor_zipcode = Advisor_profile_list.get(position).get(TAG_ADVISOR_ZIPCODE);
                String advisor_phone_no = Advisor_profile_list.get(position).get(TAG_ADVISOR_PHONE_NO);
                String advisor_about_company = Advisor_profile_list.get(position).get(TAG_ADVISOR_ABOUT_COMPANY);
                String advisor_ip_address = Advisor_profile_list.get(position).get(TAG_ADVISOR_IP_ADDRESS);
                String advisor_created_on = Advisor_profile_list.get(position).get(TAG_ADVISOR_CREATED_ON);
                String advisor_format_act = Advisor_profile_list.get(position).get(TAG_ADVISOR_FORMAT_ACT);
                String advisor_appearence = Advisor_profile_list.get(position).get(TAG_ADVISOR_APPEARENCE);
                String advisor_rating = Advisor_profile_list.get(position).get(TAG_RATING);
                String advisor_status = Advisor_profile_list.get(position).get(TAG_ADVISOR_STATUS);


                String advisor_location_name = Advisor_profile_list.get(position).get(TAG_LOCATION_NAME);
                String advisor_location_key = Advisor_profile_list.get(position).get(TAG_LOCATION_KEY);

                String advisor_industry_name = Advisor_profile_list.get(position).get(TAG_INDUSTRY_NAME);
                String advisor_industry_key = Advisor_profile_list.get(position).get(TAG_INDUSTRY_KEY);

                SharedPreferences sharedPreferences = PreferenceManager
                        .getDefaultSharedPreferences(getActivity());
                SharedPreferences.Editor editor = sharedPreferences.edit();

                editor.putString("advisor_id", advisor_id);
                editor.putString("advisor_key", advisor_key);
                editor.putString("advisor_user_id", advisor_user_id);
                editor.putString("advisor_name", advisor_name);
                editor.putString("advisor_email", advisor_email);
                editor.putString("advisor_mobile", advisor_mobile);
                editor.putString("advisor_company_name", advisor_company_name);
                editor.putString("advisor_address_1", advisor_address_1);
                editor.putString("advisor_address_2", advisor_address_2);
                editor.putString("advisor_city_id", advisor_city_id);
                editor.putString("advisor_type", advisor_type);
                editor.putString("advisor_zipcode", advisor_zipcode);
                editor.putString("advisor_phone_no", advisor_phone_no);
                editor.putString("advisor_about_company", advisor_about_company);
                editor.putString("advisor_ip_address", advisor_ip_address);
                editor.putString("advisor_created_on", advisor_created_on);
                editor.putString("advisor_format_act", advisor_format_act);
                editor.putString("advisor_appearence", advisor_appearence);
                editor.putString("advisor_rating", advisor_rating);
                editor.putString("advisor_status", advisor_status);

                editor.putString("advisor_location_name", advisor_location_name);
                editor.putString("advisor_location_key", advisor_location_key);
                editor.putString("advisor_industry_name", advisor_industry_name);
                editor.putString("advisor_industry_key", advisor_industry_key);


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
            Advisor_profile_list.clear();
            queue = Volley.newRequestQueue(getActivity());
            Get_Advisor_Profiles();
        } catch (Exception e) {
            // TODO: handle exception
        }
    }

    /*****************************
     * GET Advisor Profiles
     ***************************/

    public void Get_Advisor_Profiles() {

        String tag_json_obj = "json_obj_req";
        System.out.println("CAME 1");
        StringRequest request = new StringRequest(Request.Method.POST,
                AppConfig.url_user_advisor_profiles, new Response.Listener<String>() {

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

                            String advisor_id = obj1.getString(TAG_ADVISOR_ID);
                            String advisor_key = obj1.getString(TAG_ADVISOR_KEY);
                            String advisor_user_id = obj1.getString(TAG_ADVISOR_USER_ID);
                            String advisor_name = obj1.getString(TAG_ADVISOR_NAME);
                            String advisor_email = obj1.getString(TAG_ADVISOR_EMAIL);
                            String advisor_mobile = obj1.getString(TAG_ADVISOR_MOBILE);
                            String advisor_company_name = obj1.getString(TAG_ADVISOR_COMPANY_NAME);
                            String advisor_address_1 = obj1.getString(TAG_ADVISOR_ADDRESS_1);
                            String advisor_address_2 = obj1.getString(TAG_ADVISOR_ADDRESS_2);
                            String advisor_city_id = obj1.getString(TAG_ADVISOR_CITY_ID);
                            String advisor_type = obj1.getString(TAG_ADVISOR_TYPE);
                            String advisor_zipcode = obj1.getString(TAG_ADVISOR_ZIPCODE);
                            String advisor_phone_no = obj1.getString(TAG_ADVISOR_PHONE_NO);
                            String advisor_about_company = obj1.getString(TAG_ADVISOR_ABOUT_COMPANY);
                            String advisor_ip_address = obj1.getString(TAG_ADVISOR_IP_ADDRESS);
                            String advisor_created_on = obj1.getString(TAG_ADVISOR_CREATED_ON);
                            String advisor_format_act = obj1.getString(TAG_ADVISOR_FORMAT_ACT);
                            String advisor_appearence = obj1.getString(TAG_ADVISOR_APPEARENCE);
                            String advisor_rating = obj1.getString(TAG_RATING);
                            String advisor_status = obj1.getString(TAG_ADVISOR_STATUS);

                            arr_location = obj1.getJSONArray("location");
                            if (arr_location != null) {
                                Arraylist_update_location.clear();
                                for (int j = 0; arr_location.length() > j; j++) {
                                    JSONObject obj_location = arr_location.getJSONObject(j);

                                    String location_name = obj_location.optString(TAG_LOCATION_NAME);
                                    String location_key = obj_location.optString(TAG_LOCATION_KEY);

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
                                    String industry_name = obj_indus.optString(TAG_INDUSTRY_NAME);
                                    String industry_key = obj_indus.optString(TAG_INDUSTRY_KEY);

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

                                if (Arraylist_update_images.size() > 0) {
                                    str_final_image = Arraylist_update_images.get(0);
                                    System.out.println("IMAGE : " + str_final_image);
                                }
                            }

                            // creating new HashMap
                            HashMap<String, String> map = new HashMap<String, String>();
                            // adding each child node to HashMap key => value
                            map.put(TAG_ADVISOR_ID, advisor_id);
                            map.put(TAG_ADVISOR_KEY, advisor_key);
                            map.put(TAG_ADVISOR_USER_ID, advisor_user_id);
                            map.put(TAG_ADVISOR_NAME, advisor_name);
                            map.put(TAG_ADVISOR_EMAIL, advisor_email);
                            map.put(TAG_ADVISOR_MOBILE, advisor_mobile);
                            map.put(TAG_ADVISOR_COMPANY_NAME, advisor_company_name);
                            map.put(TAG_ADVISOR_ADDRESS_1, advisor_address_1);
                            map.put(TAG_ADVISOR_ADDRESS_2, advisor_address_2);
                            map.put(TAG_ADVISOR_CITY_ID, advisor_city_id);
                            map.put(TAG_ADVISOR_TYPE, advisor_type);
                            map.put(TAG_ADVISOR_ZIPCODE, advisor_zipcode);
                            map.put(TAG_ADVISOR_PHONE_NO, advisor_phone_no);
                            map.put(TAG_ADVISOR_ABOUT_COMPANY, advisor_about_company);
                            map.put(TAG_ADVISOR_IP_ADDRESS, advisor_ip_address);
                            map.put(TAG_ADVISOR_CREATED_ON, advisor_created_on);
                            map.put(TAG_ADVISOR_FORMAT_ACT, advisor_format_act);
                            map.put(TAG_ADVISOR_APPEARENCE, advisor_appearence);
                            map.put(TAG_RATING, advisor_rating);
                            map.put(TAG_ADVISOR_STATUS, advisor_status);

                            map.put(TAG_LOCATION_NAME, str_final_location);
                            map.put(TAG_INDUSTRY_NAME, str_final_industry);
                            map.put(TAG_IMAGE_PATH, str_final_image);


                            Advisor_profile_list.add(map);
                            adapter = new AdvisorProfiles_Adapter(getActivity(),
                                    Advisor_profile_list);
                            List.setAdapter(adapter);
                            System.out.println("HASHMAP ARRAY" + Advisor_profile_list);

                        }

                    } else if (success == 0) {

                        swipeRefreshLayout.setRefreshing(false);
                        adapter = new AdvisorProfiles_Adapter(getActivity(),
                                Advisor_profile_list);
                        List.setAdapter(adapter);

                        Alerter.create(getActivity())
                                .setTitle("World Businesses For Sale")
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
                        .setTitle("World Businesses For Sale")
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
