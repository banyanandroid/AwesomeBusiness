package banyan.com.awesomebusiness.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.MultiAutoCompleteTextView;
import android.widget.TextView;

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
import com.toptoche.searchablespinnerlibrary.SearchableSpinner;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import banyan.com.awesomebusiness.R;
import banyan.com.awesomebusiness.global.AppConfig;
import banyan.com.awesomebusiness.global.SessionManager;
import dmax.dialog.SpotsDialog;

/**
 * Created by Banyan on 10/4/2017.
 */

public class Activity_Contact_Business_Investors_Buyers extends AppCompatActivity {

    private Toolbar mToolbar;
    SpotsDialog dialog;
    public static RequestQueue queue;
    String TAG = "Auto_Res";
    TextView t1;

    SessionManager session;
    public static String str_user_id, str_user_name, str_user_email;

    public static final String TAG_SECTOR_NAME = "name";
    public static final String TAG_SECTOR_KEY = "key";
    public static final String TAG_SECTOR_TYPE = "type";

    ArrayList<String> Arraylist_sector_name = null;
    ArrayList<String> Arraylist_sector_key = null;
    ArrayList<String> Arraylist_sector_type = null;

    /* Arralist fetched indestries list */
    ArrayList<String> Arraylist_fetched_industries = null;
    ArrayList<String> Arraylist_selected_final_industry = null;

    String str_final_industry_update = "";

    MultiAutoCompleteTextView auto_industries;

    EditText edt_name, edt_contact_number, edt_business_name,
            edt_business_desc;

    String str_name, str_business_name, str_contact_number, str_business_desc = "";

    String str_investor_id = "";

    Button btn_submit;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_business_investors_buyers);

        mToolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("Contact Investor/Buyer");
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        mToolbar.setNavigationIcon(getResources().getDrawable(R.drawable.ic_action_back));
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(getApplicationContext(), MainActivity.class);
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
        str_user_id = user.get(SessionManager.KEY_USER_ID);

        Arraylist_sector_name = new ArrayList<String>();
        Arraylist_sector_key = new ArrayList<String>();
        Arraylist_sector_type = new ArrayList<String>();

        Arraylist_fetched_industries = new ArrayList<String>();
        Arraylist_selected_final_industry = new ArrayList<String>();

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        str_investor_id = sharedPreferences.getString("str_investor_id", "str_investor_id");
        System.out.println("Investor_ID::: " + str_investor_id);

        edt_name = (EditText) findViewById(R.id.activity_contact_investors_buyers_edt_name);
        edt_contact_number = (EditText) findViewById(R.id.activity_contact_investors_buyers_edt_mobile_number);
        edt_business_name = (EditText) findViewById(R.id.activity_contact_investors_buyers_edt_business_name);
        edt_business_desc = (EditText) findViewById(R.id.activity_contact_investors_buyers_edt_business_desc);

        auto_industries = (MultiAutoCompleteTextView) findViewById(R.id.activity_contact_investors_buyers_multi_busi_industry);

        btn_submit = (Button) findViewById(R.id.activity_contact_investors_buyers_btn_submit);
        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                str_name = edt_name.getText().toString();
                str_business_name = edt_business_name.getText().toString();
                str_contact_number = edt_contact_number.getText().toString();
                str_business_desc = edt_business_desc.getText().toString();

                /*****************************
                 * Get Multi Sector Details
                 * ************************/

                String[] str_industries = auto_industries.getText().toString().split(", ");

                Arraylist_fetched_industries.clear();
                for (int i = 0; i < str_industries.length; i++) {
                    Arraylist_fetched_industries.add(str_industries[i]);
                }
                System.out.println("array : " + Arraylist_fetched_industries);

                Arraylist_selected_final_industry.clear();
                for (int i = 0; i < Arraylist_fetched_industries.size(); i++) {

                    String get_indestry = Arraylist_fetched_industries.get(i);
                    get_indestry = get_indestry.trim();
                    System.out.println("get_indestry : " + get_indestry);
                    int indus_position = Arraylist_sector_name.indexOf(get_indestry);
                    String select_sect_id = Arraylist_sector_key.get(indus_position);
                    String select_sect_type = Arraylist_sector_type.get(indus_position);

                    String sector = select_sect_id + "-" + select_sect_type;
                    Arraylist_selected_final_industry.add(sector);

                    str_final_industry_update = TextUtils.join(", ", Arraylist_selected_final_industry);

                }
                System.out.println("FINAL SELECTED INDUSTRY :: " + str_final_industry_update);


                if (str_name.equals("")) {
                    edt_name.setError("Enter Name");
                    edt_name.setFocusable(true);
                    TastyToast.makeText(getApplicationContext(), "Name Cannot be empty", TastyToast.LENGTH_LONG, TastyToast.WARNING);
                } else if (str_business_name.equals("")) {
                    edt_business_name.setError("Enter Business Name");
                    edt_business_name.setFocusable(true);
                    TastyToast.makeText(getApplicationContext(), "Business Name Cannot be Empty", TastyToast.LENGTH_LONG, TastyToast.WARNING);
                } else if (str_contact_number.equals("")) {
                    edt_contact_number.setError("Enter Contact Number");
                    edt_contact_number.setFocusable(true);
                    TastyToast.makeText(getApplicationContext(), "Contact Number Cannot be Empty", TastyToast.LENGTH_LONG, TastyToast.WARNING);
                } else if (str_final_industry_update.equals("") || str_final_industry_update.equals("null")) {
                    TastyToast.makeText(getApplicationContext(), "Select Business Sector", TastyToast.LENGTH_LONG, TastyToast.WARNING);
                } else if (str_business_desc.equals("")) {
                    edt_business_desc.setError("Enter Business Description");
                    edt_business_desc.setFocusable(true);
                    TastyToast.makeText(getApplicationContext(), "This Cannot be Empty", TastyToast.LENGTH_LONG, TastyToast.WARNING);
                } else {

                    TastyToast.makeText(getApplicationContext(), "Button Clicked", TastyToast.LENGTH_LONG, TastyToast.SUCCESS);
                    dialog = new SpotsDialog(Activity_Contact_Business_Investors_Buyers.this);
                    dialog.show();
                    queue = Volley.newRequestQueue(Activity_Contact_Business_Investors_Buyers.this);
                    Function_Contact_Business_Investors_Buyers();

                }


            }
        });


        try {
            dialog = new SpotsDialog(Activity_Contact_Business_Investors_Buyers.this);
            dialog.show();
            queue = Volley.newRequestQueue(getApplicationContext());
            Get_Sector_List();

        } catch (Exception e) {
            // TODO: handle exception
        }


    }


    /*****************************
     * To get  Business sector List
     ***************************/
    public void Get_Sector_List() {
        String tag_json_obj = "json_obj_req";
        System.out.println("CAME 1");
        StringRequest request = new StringRequest(Request.Method.POST,
                AppConfig.url_business, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d(TAG, response.toString());
                try {
                    JSONObject obj = new JSONObject(response);
                    int success = obj.getInt("status");

                    if (success == 1) {

                        JSONArray arr;

                        arr = obj.getJSONArray("datas");

                        for (int i = 0; arr.length() > i; i++) {
                            JSONObject obj1 = arr.getJSONObject(i);

                            String sector_name = obj1.optString(TAG_SECTOR_NAME);
                            String sector_key = obj1.optString(TAG_SECTOR_KEY);
                            String sector_type = obj1.optString(TAG_SECTOR_TYPE);

                            Arraylist_sector_name.add(sector_name);
                            Arraylist_sector_key.add(sector_key);
                            Arraylist_sector_type.add(sector_type);
                        }
                        try {

                            System.out.println("ARAAAAY :: " + Arraylist_sector_name);

                            ArrayAdapter<String> adapter_process = new ArrayAdapter<String>(Activity_Contact_Business_Investors_Buyers.this,
                                    android.R.layout.simple_list_item_1, Arraylist_sector_name);
                            auto_industries.setAdapter(adapter_process);
                            auto_industries
                                    .setTokenizer(new MultiAutoCompleteTextView.CommaTokenizer());
                            auto_industries.setThreshold(1);


                        } catch (Exception e) {

                        }

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

                return params;
            }

        };

        // Adding request to request queue
        queue.add(request);
    }


    /******************************************
     *    CONTACT BUSINESS
     * *******************************************/

    private void Function_Contact_Business_Investors_Buyers() {

        StringRequest request = new StringRequest(Request.Method.POST,
                AppConfig.url_contact_business_investors_buyers, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d(TAG, response.toString());

                try {
                    JSONObject obj = new JSONObject(response);
                    System.out.println("RESPONSE::: " + response);
                    int success = obj.getInt("status");
                    if (success == 1) {
                        dialog.dismiss();
                        Alerter.create(Activity_Contact_Business_Investors_Buyers.this)
                                .setTitle("Success")
                                .setText("Email has been sent Successfully")
                                .setBackgroundColor(R.color.colorAccent)
                                .show();
                    } else {
                        dialog.dismiss();
                        TastyToast.makeText(getApplicationContext(), "Oops...! Unable to contact :(", TastyToast.LENGTH_LONG, TastyToast.ERROR);
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

                params.put("fname", str_name);
                params.put("contact", str_contact_number);
                params.put("busname", str_business_name);
                params.put("industry", str_final_industry_update);
                params.put("yourself", str_business_desc);
                params.put("investor_id", str_investor_id);
                params.put("investor_proposal_user_id", str_user_id);
                params.put("email_id", str_user_email);

                ////////////////
                System.out.println("user_name" + str_name);
                System.out.println("contact number" + str_contact_number);
                System.out.println("business name" + str_business_name);
                System.out.println("industries" + str_final_industry_update);
                System.out.println("description" + str_business_desc);
                System.out.println("Investor Id" + str_investor_id);
                System.out.println("investor_proposal_user_id" + str_user_id);

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