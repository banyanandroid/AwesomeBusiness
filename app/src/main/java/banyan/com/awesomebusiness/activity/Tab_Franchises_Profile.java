package banyan.com.awesomebusiness.activity;

import android.os.Bundle;
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


    /*
     {
            "franchise_id": "1",
            "franchise_key": "2NtE7a",
            "franchise_user_name": "sdg",
            "franchise_email": "sdgs@dg.gj",
            "franchise_mobile": "12354689",
            "franchise_designation": "dfv",
            "franchise_brand_name": "gdg",
            "franchise_brand_offering": "1",
            "franchise_brand_company": "dgds",
            "franchise_brand_services": "fdh",
            "franchise_brand_established": "2017",
            "franchise_brand_headquaters": "1-continent",
            "brand_salepartner_count": "hgdf",
            "brand_salepartner_before_partnering": "gfhgd",
            "brand_salepartner_expect": "fdhgbd",
            "brand_salepartner_procedure": "dfgb",
            "franchise_format": "1",
            "franchise_logo": "62017_08_24_18_30_45.jpg",
            "franchise_currency": "1",
            "country_currency": "INR",
            "location": [
                {
                    "location_name": "Asia",
                    "location_key": "1111"
                }
            ],
            "industry": [
                {
                    "industry_name": "Building Construction and Maintenance",
                    "industry_key": "Ads123"
                }
            ],
            "images": [],
            "documents": [
                {
                    "document_path": "http://www.epictech.in/api_awesome/uploads/0"
                }
    */

    public static final String TAG_BUSINESS_ID = "business_id";


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


        View rootview = inflater.inflate(R.layout.tab_business_profile, container, false);
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

        // Hashmap for ListView
        Franchise_profile_list = new ArrayList<HashMap<String, String>>();


        // Hashmap for ListView
        Franchise_profile_list = new ArrayList<HashMap<String, String>>();

        List.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {

                System.out.println("ITEM CLICKED");


            }

        });

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.tab_franchises, container, false);
    }


    @Override
    public void onRefresh() {
        try {
            Franchise_profile_list.clear();
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
                        arr = obj.getJSONArray("enquiry");
                        for (int i = 0; arr.length() > i; i++) {
                            JSONObject obj1 = arr.getJSONObject(i);

                            String business_id = obj1.getString(TAG_BUSINESS_ID);


                            // creating new HashMap
                            HashMap<String, String> map = new HashMap<String, String>();

                            // adding each child node to HashMap key => value
                            map.put(TAG_BUSINESS_ID, business_id);


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
