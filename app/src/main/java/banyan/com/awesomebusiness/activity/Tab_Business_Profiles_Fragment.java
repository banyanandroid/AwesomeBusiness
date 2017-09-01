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
import banyan.com.awesomebusiness.global.SessionManager;

/**
 * Created by User on 8/31/2017.
 */

public class Tab_Business_Profiles_Fragment extends Fragment implements SheetLayout.OnFabAnimationEndListener, SwipeRefreshLayout.OnRefreshListener {

    SheetLayout mSheetLayout;
    FloatingActionButton mFab;

    String str_user_name, str_user_id;
    String str_task_name, str_task_des;

    public static RequestQueue queue;

    // Session Manager Class
    SessionManager session;

    String TAG = "add task";

    private ListView List;
    private SwipeRefreshLayout swipeRefreshLayout;




    static ArrayList<HashMap<String, String>> enquiry_list;

    HashMap<String, String> params = new HashMap<String, String>();



    String str_select_task_id;

    private static final int REQUEST_CODE = 1;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootview = inflater.inflate(R.layout.tab_business_profile_layout, null);


        // setUpToolbarWithTitle(getString(R.string.INBOX), false);

        session = new SessionManager(getActivity());

        session.checkLogin();
        HashMap<String, String> user = session.getUserDetails();
        str_user_name = user.get(SessionManager.KEY_USER);
        str_user_id = user.get(SessionManager.KEY_USER_ID);


        mSheetLayout = (SheetLayout) rootview.findViewById(R.id.bottom_sheet);


        mSheetLayout.setFabAnimationEndListener(this);



        List = (ListView) rootview.findViewById(R.id.alloted_comp_listView);
        swipeRefreshLayout = (SwipeRefreshLayout) rootview.findViewById(R.id.alloted_comp_swipe_refresh_layout);

        swipeRefreshLayout.setOnRefreshListener(this);

        swipeRefreshLayout.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        swipeRefreshLayout.setRefreshing(true);

                                        try {
                                           /* queue = Volley.newRequestQueue(getActivity());
                                            GetMyAllotedEnquiries();*/

                                        } catch (Exception e) {
                                            // TODO: handle exceptions
                                        }
                                    }
                                }
        );

        // Hashmap for ListView
        enquiry_list = new ArrayList<HashMap<String, String>>();

        return rootview;

    }

    /**
     * This method is called when swipe refresh is pulled down
     */
    @Override
    public void onRefresh() {
        try {
          /*  enquiry_list.clear();
            queue = Volley.newRequestQueue(getActivity());
            GetMyAllotedEnquiries();*/

        } catch (Exception e) {
            // TODO: handle exception
        }
    }




    @Override
    public void onFabAnimationEnd() {
        Intent intent = new Intent(getActivity(), Activity_InvestorProfile.class);
        startActivityForResult(intent, REQUEST_CODE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE) {
            mSheetLayout.contractFab();
        }
    }


    /*****************************
     * GET My Task
     ***************************/

    /*public void GetMyAllotedEnquiries() {

        String tag_json_obj = "json_obj_req";
        System.out.println("CAME 1");
        StringRequest request = new StringRequest(Request.Method.POST,
                AppConfig.url_get_alloted_enquiry, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d(TAG, response.toString());
                try {
                    JSONObject obj = new JSONObject(response);
                    int success = obj.getInt("success");

                    if (success == 1) {

                        JSONArray arr;

                        arr = obj.getJSONArray("enquiry");

                        for (int i = 0; arr.length() > i; i++) {
                            JSONObject obj1 = arr.getJSONObject(i);

                            String id = obj1.getString(TAG_ENQ_ID);
                            String year_id = obj1.getString(TAG_ENQ_YEAR_ID);
                            String month_id = obj1.getString(TAG_ENQ_START_MONTH);
                            String comp_name = obj1.getString(TAG_ENQ_END_COMP_NAME);
                            String comp_email = obj1.getString(TAG_ENQ_END_COMP_EMAIL);
                            String addon_email = obj1.getString(TAG_ENQ_ADDON_EMAIL);
                            String addon_email2 = obj1.getString(TAG_ENQ_ADDON2_EMAIL);
                            String addon_email3 = obj1.getString(TAG_ENQ_ADDON3_EMAIL);
                            String comp_phone = obj1.getString(TAG_ENQ_PHONENO);
                            String comp_address = obj1.getString(TAG_ENQ_COMP_ADDRESS);
                            String comp_pin = obj1.getString(TAG_ENQ_PIN);
                            String comp_contact_person_name = obj1.getString(TAG_ENQ_CON_PERSON_NAME);
                            String comp_contactperson_phone = obj1.getString(TAG_ENQ_CON_PERSON_PHONE);
                            String comp_product_id = obj1.getString(TAG_ENQ_PRODUCT_ID);
                            String enq_product_series = obj1.getString(TAG_ENQ_PRODUCT_SERIES);
                            String enq_product_model = obj1.getString(TAG_ENQ_PRODUCT_MODEL);
                            String enq_product_model_no = obj1.getString(TAG_ENQ_PRODUCT_MODEL_NO);
                            String enq_product_type = obj1.getString(TAG_ENQ_PRODUCT_TYPE);
                            String enq_product_qty = obj1.getString(TAG_ENQ_PRODUCT_QTY);
                            String enq_product_price = obj1.getString(TAG_ENQ_PRODUCT_PRICE);
                            String enq_alloted_to = obj1.getString(TAG_ENQ_ALLOTED_TO);
                            String enq_team_id = obj1.getString(TAG_ENQ_TEAM_ID);
                            String enq_discount = obj1.getString(TAG_ENQ_DISCOUNT);
                            String enq_description = obj1.getString(TAG_ENQ_DESC);
                            String enquiry_through = obj1.getString(TAG_ENQ_THROUGH);
                            String enquiry_through_description = obj1.getString(TAG_ENQ_THROUGH_DESC);
                            String enq_status = obj1.getString(TAG_ENQ_STATUS);
                            String enq_remarks = obj1.getString(TAG_ENQ_REMARK);
                            String enq_created_on = obj1.getString(TAG_ENQ_CREAATED_ON);
                            String enq_completed_on = obj1.getString(TAG_ENQ_COMPLEED_ON);

                            System.out.println("enq_created_on" + enq_created_on);
                            System.out.println("enq_created_on" + enq_created_on);
                            System.out.println("enq_created_on" + enq_created_on);
                            System.out.println("enq_created_on" + enq_created_on);

                            // creating new HashMap
                            HashMap<String, String> map = new HashMap<String, String>();

                            // adding each child node to HashMap key => value
                            map.put(TAG_ENQ_ID, id);
                            map.put(TAG_ENQ_YEAR_ID, year_id);
                            map.put(TAG_ENQ_START_MONTH, month_id);
                            map.put(TAG_ENQ_END_COMP_NAME, comp_name);
                            map.put(TAG_ENQ_END_COMP_EMAIL, comp_email);
                            map.put(TAG_ENQ_ADDON_EMAIL, addon_email);
                            map.put(TAG_ENQ_ADDON2_EMAIL, addon_email2);
                            map.put(TAG_ENQ_ADDON3_EMAIL, addon_email3);
                            map.put(TAG_ENQ_PHONENO, comp_phone);
                            map.put(TAG_ENQ_COMP_ADDRESS, comp_address);
                            map.put(TAG_ENQ_PIN, comp_pin);
                            map.put(TAG_ENQ_CON_PERSON_NAME, comp_contact_person_name);
                            map.put(TAG_ENQ_CON_PERSON_PHONE, comp_contactperson_phone);
                            map.put(TAG_ENQ_PRODUCT_ID, comp_product_id);
                            map.put(TAG_ENQ_PRODUCT_SERIES, enq_product_series);
                            map.put(TAG_ENQ_PRODUCT_MODEL, enq_product_model);
                            map.put(TAG_ENQ_PRODUCT_MODEL_NO, enq_product_model_no);
                            map.put(TAG_ENQ_PRODUCT_TYPE, enq_product_type);
                            map.put(TAG_ENQ_PRODUCT_QTY, enq_product_qty);
                            map.put(TAG_ENQ_PRODUCT_PRICE, enq_product_price);
                            map.put(TAG_ENQ_ALLOTED_TO, enq_alloted_to);
                            map.put(TAG_ENQ_TEAM_ID, enq_team_id);
                            map.put(TAG_ENQ_DISCOUNT, enq_discount);
                            map.put(TAG_ENQ_DESC, enq_description);
                            map.put(TAG_ENQ_THROUGH, enquiry_through);
                            map.put(TAG_ENQ_THROUGH_DESC, enquiry_through_description);
                            map.put(TAG_ENQ_STATUS, enq_status);
                            map.put(TAG_ENQ_REMARK, enq_remarks);
                            map.put(TAG_ENQ_CREAATED_ON, enq_created_on);
                            map.put(TAG_ENQ_COMPLEED_ON, enq_completed_on);

                            enquiry_list.add(map);

                            System.out.println("HASHMAP ARRAY" + enquiry_list);


                            adapter = new Alloted_Complaints_Adapter(getActivity(),
                                    enquiry_list);
                            List.setAdapter(adapter);

                        }

                    } else if (success == 0) {

                        adapter = new Alloted_Complaints_Adapter(getActivity(),
                                enquiry_list);
                        List.setAdapter(adapter);

                        Alerter.create(getActivity())
                                .setTitle("GEM CRM")
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

                *//*Alerter.create(getActivity())
                        .setTitle("GEM CRM")
                        .setText("Internal Error !")
                        .setBackgroundColor(R.color.Alert_Warning)
                        .show();*//*
            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();

                params.put("user", str_user_id); // replace as str_id

                System.out.println("user_id" + str_user_id);

                return params;
            }

        };

        // Adding request to request queue
        queue.add(request);
    }*/
}
