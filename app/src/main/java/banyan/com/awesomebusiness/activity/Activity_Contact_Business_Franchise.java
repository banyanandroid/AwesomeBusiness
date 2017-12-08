package banyan.com.awesomebusiness.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
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

public class Activity_Contact_Business_Franchise extends AppCompatActivity {

    private Toolbar mToolbar;
    SpotsDialog dialog;
    public static RequestQueue queue;
    String TAG = "Auto_Res";
    String str_user_currency = "";
    TextView t1;

    SessionManager session;
    public static String str_user_id, str_user_name, str_user_email;

    public static final String TAG_INTEREST_ID = "investor_interest_id";
    public static final String TAG_INTEREST_NAME = "investor_interest_name";

    ArrayList<String> Arraylist_investor_interest_id = null;
    ArrayList<String> Arraylist_investor_interest_name = null;

    private ArrayAdapter<String> adapter_interested;

    SearchableSpinner spn_interested_in;

    String str_selected_interest_id, str_selected_interest_name = "empty";

    EditText edt_name, edt_contact_number, edt_company_name, edt_designation,
            edt_about;

    String str_name, str_company_name, str_contact_number, str_interested_in, str_designation, str_about = "";

    String str_franchise_id;

    Button btn_submit;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_business_franchise);

        mToolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("Contact Franchise");
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

        Arraylist_investor_interest_id = new ArrayList<String>();
        Arraylist_investor_interest_name = new ArrayList<String>();

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        str_franchise_id = sharedPreferences.getString("str_franchise_id", "str_franchise_id");
        System.out.println("Fanchise_ID::: " + str_franchise_id);

        edt_name = (EditText) findViewById(R.id.activity_contact_business_franchise_edt_name);
        edt_contact_number = (EditText) findViewById(R.id.activity_contact_business_franchise_edt_mobile_number);
        edt_company_name = (EditText) findViewById(R.id.activity_contact_business_franchise_edt_company_name);
        edt_designation = (EditText) findViewById(R.id.activity_contact_business_franchise_edt_user_designation);
        edt_about = (EditText) findViewById(R.id.activity_contact_business_franchise_edt_about);

        spn_interested_in = (SearchableSpinner) findViewById(R.id.activity_contact_business_franchise_spn_intersted);
        spn_interested_in.setTitle("Select Your Interest");

        btn_submit = (Button) findViewById(R.id.activity_contact_business_franchise_btn_submit);
        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                str_name = edt_name.getText().toString();
                str_company_name = edt_company_name.getText().toString();
                str_contact_number = edt_contact_number.getText().toString();
                str_designation = edt_designation.getText().toString();
                str_about = edt_about.getText().toString();


                if (str_name.equals("")) {
                    edt_name.setError("Enter Name");
                    edt_name.setFocusable(true);
                    TastyToast.makeText(getApplicationContext(), "Name Cannot be empty", TastyToast.LENGTH_LONG, TastyToast.WARNING);
                } else if (str_company_name.equals("")) {
                    edt_company_name.setError("Enter Company Name");
                    edt_company_name.setFocusable(true);
                    TastyToast.makeText(getApplicationContext(), "Company Cannot be Empty", TastyToast.LENGTH_LONG, TastyToast.WARNING);
                } else if (str_contact_number.equals("")) {
                    edt_contact_number.setError("Enter Contact Number");
                    edt_contact_number.setFocusable(true);
                    TastyToast.makeText(getApplicationContext(), "Contact Number Cannot be Empty", TastyToast.LENGTH_LONG, TastyToast.WARNING);
                } else if (str_designation.equals("")) {
                    edt_designation.setError("Enter Designation");
                    edt_designation.setFocusable(true);
                    TastyToast.makeText(getApplicationContext(), "Designation Cannot be Empty", TastyToast.LENGTH_LONG, TastyToast.WARNING);
                } else if (str_about.equals("")) {
                    edt_about.setError("Enter About You");
                    edt_about.setFocusable(true);
                    TastyToast.makeText(getApplicationContext(), "This Cannot be Empty", TastyToast.LENGTH_LONG, TastyToast.WARNING);
                } else if (str_selected_interest_id == null || str_selected_interest_id.isEmpty()) {
                    spn_interested_in.setFocusable(true);
                    spn_interested_in.setFocusableInTouchMode(true);
                    spn_interested_in.requestFocus();
                    spn_interested_in.performClick();
                    TastyToast.makeText(getApplicationContext(), "Select Your Interest Type", TastyToast.LENGTH_LONG, TastyToast.WARNING);
                } else {
                    dialog = new SpotsDialog(Activity_Contact_Business_Franchise.this);
                    dialog.show();
                    queue = Volley.newRequestQueue(Activity_Contact_Business_Franchise.this);
                    Function_Contact_Business();
                }


            }
        });


        try {
            dialog = new SpotsDialog(Activity_Contact_Business_Franchise.this);
            dialog.show();
            queue = Volley.newRequestQueue(getApplicationContext());
            Get_Interested();

        } catch (Exception e) {
            // TODO: handle exception
        }


    }


    /*****************************
     * To get  Interested in  Details
     ***************************/

    public void Get_Interested() {
        String tag_json_obj = "json_obj_req";
        StringRequest request = new StringRequest(Request.Method.POST,
                AppConfig.url_investor_interested, new Response.Listener<String>() {

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

                            String interest_key = obj1.getString(TAG_INTEREST_ID);
                            String interest_name = obj1.getString(TAG_INTEREST_NAME);

                            Arraylist_investor_interest_id.add(interest_key);
                            Arraylist_investor_interest_name.add(interest_name);
                        }
                        try {
                            adapter_interested = new ArrayAdapter<String>(Activity_Contact_Business_Franchise.this,
                                    android.R.layout.simple_list_item_1, Arraylist_investor_interest_name);
                            spn_interested_in.setAdapter(adapter_interested);

                            spn_interested_in.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                @Override
                                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                                    t1 = (TextView) view;
                                    str_selected_interest_name = t1.getText().toString();
                                    str_selected_interest_id = Arraylist_investor_interest_id.get(position);

                                }

                                @Override
                                public void onNothingSelected(AdapterView<?> parent) {

                                }
                            });


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

    private void Function_Contact_Business() {

        StringRequest request = new StringRequest(Request.Method.POST,
                AppConfig.url_contact_business_franchise, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d(TAG, response.toString());

                try {
                    JSONObject obj = new JSONObject(response);
                    System.out.println("RESPONSE::: " + response);
                    int success = obj.getInt("status");
                    if (success == 1) {
                        dialog.dismiss();
                        Alerter.create(Activity_Contact_Business_Franchise.this)
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
                params.put("inter_u", str_selected_interest_id);
                params.put("company", str_company_name);
                params.put("designation", str_designation);
                params.put("yourself", str_about);
                params.put("franchise_id", str_franchise_id);
                params.put("franchise_contact_user_id", str_user_id);
                params.put("email_id", str_user_email);

                ////////////////
                System.out.println("user_name" + str_name);
                System.out.println("Contact_number" + str_contact_number);
                System.out.println("interest_in" + str_selected_interest_id);
                System.out.println("Company_name" + str_company_name);
                System.out.println("Designation" + str_designation);
                System.out.println("About" + str_about);
                System.out.println("franchise_id" + str_franchise_id);
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
