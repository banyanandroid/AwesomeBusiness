package banyan.com.awesomebusiness.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
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
import com.bumptech.glide.Glide;
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
import banyan.com.awesomebusiness.global.AppConfig;
import banyan.com.awesomebusiness.global.SessionManager;
import dmax.dialog.SpotsDialog;

/**
 * Created by Banyan on 9/27/2017.
 */

public class Activity_DetailedView_Franchise extends AppCompatActivity {

    private Toolbar mToolbar;
    SpotsDialog dialog;
    public static RequestQueue queue;
    String TAG = "Auto_Res";
    SessionManager session;
    public static String str_user_id, str_user_name, str_user_email, str_user_photoo;
    String str_user_currency, str_franchise_key = "";

    public static final String TAG_FRANCHISE_ID = "franchise_id";
    public static final String TAG_FRANCHISE_KEY = "franchise_key";
    public static final String TAG_FRANCHISE_USER_ID = "franchise_user_id";
    public static final String TAG_FRANCHISE_USER_NAME = "franchise_user_name";
    public static final String TAG_FRANCHISE_EMAIL = "franchise_email";
    public static final String TAG_FRANCHISE_MOBILE = "franchise_mobile";
    public static final String TAG_FRANCHISE_DESIGNATION = "franchise_designation";
    public static final String TAG_FRANCHISE_BRAND_NAME = "franchise_brand_name";
    public static final String TAG_FRANCHISE_BRAND_OFFERING = "franchise_brand_offering";
    public static final String TAG_FRANCHISE_BRAND_OFFERING_NAME = "franchise_brand_offering_name";
    public static final String TAG_FRANCHISE_BRAND_COMPANY = "franchise_brand_company";
    public static final String TAG_FRANCHISE_BRAND_SERVICES = "franchise_brand_services";
    public static final String TAG_FRANCHISE_BRAND_ESTABLISHED = "franchise_brand_established";
    public static final String TAG_FRANCHISE_BRAND_HEADQUATERS = "franchise_brand_headquaters";
    public static final String TAG_BRAND_SALEPARTNER_BEFORE_PARTNERING = "brand_salepartner_before_partnering";
    public static final String TAG_BRAND_SALEPARTNER_EXPECT = "brand_salepartner_expect";
    public static final String TAG_BRAND_SALEPARTNER_PROCEDURE = "brand_salepartner_procedure";

    public static final String TAG_FRANCHISE_INVESTMENT_MIN = "franchise_min_investment";
    public static final String TAG_FRANCHISE_INVESTMENT_MAX = "franchise_max_investment";
    public static final String TAG_FRANCHISE_REVENUE_MIN = "franchise_min_revenue";
    public static final String TAG_FRANCHISE_REVENUE_MAX = "franchise_max_revenue";

    public static final String TAG_FRANCHISE_LOGO = "franchise_logo";
    public static final String TAG_FRANCHISE_CURRENCY = "franchise_currency";

    public static final String TAG_LOCATION_NAME = "location_name";
    public static final String TAG_LOCATION_KEY = "location_key";

    public static final String TAG_INDUSTRY_NAME = "industry_name";
    public static final String TAG_INDUSTRY_KEY = "industry_key";

    public static final String TAG_IMAGE_PATH = "image_path";

    ArrayList<String> Arraylist_update_location = null;
    ArrayList<String> Arraylist_update_industries = null;
    ArrayList<String> Arraylist_update_images = null;

    TextView txt_brand_name, txt_name, txt_phone, txt_email, txt_company, txt_about_brand,
            txt_sales_partner_before_partnering, txt_brand_offering,
            txt_investment_size_from, txt_investment_size_to, txt_revenue_from, txt_revenue_to,
            txt_headquaters, txt_train_support,
            txt_preferred_locations, txt_industries;

    Button btn_contact_franchise, btn_bookmark_franchise;

    ImageView img_franchise_logo;

    TextView title, industries, locations;

    String str_final_location, str_final_industries, str_final_image = "";

    String franchise_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed_view_franchise);

        mToolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("Franchise Details");
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
        str_user_photoo = user.get(SessionManager.KEY_USER_PHOTO);
        str_user_id = user.get(SessionManager.KEY_USER_ID);

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        str_user_currency = sharedPreferences.getString("str_selected_currency", "str_selected_currency");
        str_franchise_key = sharedPreferences.getString("franchise_key", "franchise_key");

        System.out.println("USER_ID----" + str_user_id);
        System.out.println("FRANCHISE KEY-----" + str_franchise_key);
        System.out.println("CURRENCY-----" + str_user_currency);

        Arraylist_update_location = new ArrayList<String>();
        Arraylist_update_industries = new ArrayList<String>();
        Arraylist_update_images = new ArrayList<String>();

        txt_brand_name = (TextView) findViewById(R.id.activity_franchise_profile_details_txt_brandname);
        txt_name = (TextView) findViewById(R.id.activity_franchise_profile_details_name);
        txt_phone = (TextView) findViewById(R.id.activity_franchise_profile_details_mobilenumber);
        txt_email = (TextView) findViewById(R.id.activity_franchise_profile_details_email);
        txt_company = (TextView) findViewById(R.id.activity_franchise_profile_details_company);
        txt_brand_offering = (TextView) findViewById(R.id.activity_franchise_profile_brand_offering);
        txt_about_brand = (TextView) findViewById(R.id.activity_franchise_details_txt_about_brand_name);
        txt_sales_partner_before_partnering = (TextView) findViewById(R.id.activity_franchise_profile_details_salepartnerexpectation);
        txt_investment_size_from = (TextView) findViewById(R.id.activity_franchise_profile_details_investment_size_from);
        txt_investment_size_to = (TextView) findViewById(R.id.activity_franchise_profile_details_investment_size_to);
        txt_revenue_from = (TextView) findViewById(R.id.activity_franchise_profile_details_expected_return_from);
        txt_revenue_to = (TextView) findViewById(R.id.activity_franchise_profile_details_expected_return_to);
        txt_preferred_locations = (TextView) findViewById(R.id.activity_franchise_profile_details_prefered_locations);
        txt_headquaters = (TextView) findViewById(R.id.activity_franchise_profile_details_headquaters_locations);
        txt_train_support = (TextView) findViewById(R.id.activity_franchise_profile_details_trainingsupport);
        txt_industries = (TextView) findViewById(R.id.activity_franchise_profile_details_prefered_industries);

        img_franchise_logo = (ImageView) findViewById(R.id.activity_franchise_profile_details_image_view);

        btn_contact_franchise = (Button) findViewById(R.id.btn_activity_franchise_profile_details_contact_franchise);

        btn_bookmark_franchise = (Button) findViewById(R.id.btn_activity_franchise_profile_details_bookmark);

        btn_contact_franchise.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("str_franchise_id", franchise_id);
                editor.commit();

                Intent i = new Intent(getApplicationContext(), Activity_Contact_Business_Franchise.class);
                startActivity(i);
                finish();


            }

        });


        btn_bookmark_franchise.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                dialog = new SpotsDialog(Activity_DetailedView_Franchise.this);
                dialog.show();
                queue = Volley.newRequestQueue(getApplicationContext());
                Function_Bookmark();

            }
        });


        try {
            dialog = new SpotsDialog(Activity_DetailedView_Franchise.this);
            dialog.show();
            queue = Volley.newRequestQueue(getApplicationContext());
            Get_Details();

        } catch (Exception e) {
            // TODO: handle exception
        }
    }

    /*****************************
     * GET Details
     ***************************/

    public void Get_Details() {

        StringRequest request = new StringRequest(Request.Method.POST,
                AppConfig.url_dashboard_search_result_franchise_profile_detail, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d(TAG, response.toString());
                System.out.println("CAME RESPONSE ::: " + response.toString());

                try {

                    JSONObject obj = new JSONObject(response);
                    int success = obj.getInt("status");

                    if (success == 1) {

                        JSONArray arr_main;
                        JSONArray arr_location;
                        JSONArray arr_industry;
                        JSONArray arr_formats;

                        arr_main = obj.getJSONArray("data");

                        for (int i = 0; arr_main.length() > i; i++) {

                            JSONObject obj_data = arr_main.getJSONObject(i);
                            //only this business_id string is declared above inorder to put it in shared preferences
                            franchise_id = obj_data.getString(TAG_FRANCHISE_ID);
                            String franchise_key = obj_data.getString(TAG_FRANCHISE_KEY);
                            String franchise_user_name = obj_data.getString(TAG_FRANCHISE_USER_NAME);
                            String franchise_email = obj_data.getString(TAG_FRANCHISE_EMAIL);
                            String franchise_mobile = obj_data.getString(TAG_FRANCHISE_MOBILE);
                            String franchise_designation = obj_data.getString(TAG_FRANCHISE_DESIGNATION);
                            String franchise_brand_name = obj_data.getString(TAG_FRANCHISE_BRAND_NAME);
                            String franchise_brand_offering = obj_data.getString(TAG_FRANCHISE_BRAND_OFFERING);
                            String franchise_brand_company = obj_data.getString(TAG_FRANCHISE_BRAND_COMPANY);
                            String franchise_brand_services = obj_data.getString(TAG_FRANCHISE_BRAND_SERVICES);
                            String franchise_brand_established = obj_data.getString(TAG_FRANCHISE_BRAND_ESTABLISHED);
                            String franchise_brand_headquaters = obj_data.getString(TAG_FRANCHISE_BRAND_HEADQUATERS);
                            String brand_salepartner_before_partnering = obj_data.getString(TAG_BRAND_SALEPARTNER_BEFORE_PARTNERING);
                            String brand_salepartner_expect = obj_data.getString(TAG_BRAND_SALEPARTNER_EXPECT);
                            String brand_salepartner_procedure = obj_data.getString(TAG_BRAND_SALEPARTNER_PROCEDURE);
                            String franchise_logo = obj_data.getString(TAG_FRANCHISE_LOGO);
                            String franchise_currency = obj_data.getString(TAG_FRANCHISE_CURRENCY);

                            String franchise_investment_from = obj_data.getString(TAG_FRANCHISE_INVESTMENT_MIN);
                            String franchise_investment_to = obj_data.getString(TAG_FRANCHISE_INVESTMENT_MAX);
                            String franchise_return_from = obj_data.getString(TAG_FRANCHISE_REVENUE_MIN);
                            String franchise_return_to = obj_data.getString(TAG_FRANCHISE_REVENUE_MAX);


                            arr_location = obj_data.getJSONArray("location");
                            Arraylist_update_location.clear();
                            for (int j = 0; arr_location.length() > j; j++) {
                                JSONObject obj_location = arr_location.getJSONObject(j);

                                String location_name = obj_location.optString(TAG_LOCATION_NAME);
                                String location_key = obj_location.optString(TAG_LOCATION_KEY);

                                Arraylist_update_location.add(location_name);

                            }
                            str_final_location = TextUtils.join(", ", Arraylist_update_location);

                            System.out.println("LOCATION ::: " + str_final_location);

                            arr_industry = obj_data.getJSONArray("industry");
                            Arraylist_update_industries.clear();
                            for (int k = 0; arr_industry.length() > k; k++) {
                                JSONObject obj_industry = arr_industry.getJSONObject(k);

                                String industry_name = obj_industry.optString(TAG_INDUSTRY_NAME);
                                String industry_key = obj_industry.optString(TAG_INDUSTRY_KEY);

                                Arraylist_update_industries.add(industry_name);

                            }
                            str_final_industries = TextUtils.join(", ", Arraylist_update_industries);

                            try {

                                txt_name.setText("Availble after connect");
                                txt_name.setTextColor(getResources().getColor(R.color.colorAccent1));
                                txt_phone.setText("Availble after connect");
                                txt_phone.setTextColor(getResources().getColor(R.color.colorAccent1));
                                txt_email.setText("Availble after connect");
                                txt_email.setTextColor(getResources().getColor(R.color.colorAccent1));
                                txt_company.setText("Availble after connect");
                                txt_company.setTextColor(getResources().getColor(R.color.colorAccent1));
                                txt_sales_partner_before_partnering.setText("" + brand_salepartner_before_partnering);
                                txt_investment_size_from.setText("" + franchise_investment_from);
                                txt_investment_size_to.setText("" + franchise_investment_to);
                                txt_revenue_from.setText("" + franchise_return_from);
                                txt_revenue_to.setText("" + franchise_return_to);
                                txt_headquaters.setText("" + franchise_brand_headquaters);
                                txt_industries.setText("" + str_final_industries + ", ");
                                txt_brand_name.setText("" + franchise_brand_name);
                                txt_about_brand.setText("" + franchise_brand_company);
                                txt_train_support.setText("" + franchise_brand_services);
                                txt_preferred_locations.setText("" + str_final_location + ", ");


                                //Setting selection for spinner - (OPPORTUNITIES OFFERED)
                                if (franchise_brand_offering.equals("1")) {

                                    txt_brand_offering.setText("Franchise Opportunity");

                                } else if (franchise_brand_offering.equals("2")) {

                                    txt_brand_offering.setText("Dealership Opportunity");

                                } else if (franchise_brand_offering.equals("3")) {

                                    txt_brand_offering.setText("Reseller Opportunity");

                                } else if (franchise_brand_offering.equals("4")) {

                                    txt_brand_offering.setText("Distributor Opportunity");

                                } else if (franchise_brand_offering.equals("5")) {

                                    txt_brand_offering.setText("Sales Opportunity");

                                }

                                Glide.with(getApplicationContext())
                                        .load(franchise_logo)
                                        .placeholder(R.drawable.placeholder)
                                        .into(img_franchise_logo);

                                dialog.dismiss();
                            } catch (Exception e) {

                            }
                            dialog.dismiss();
                        }

                        dialog.dismiss();

                    } else if (success == 0) {

                        dialog.dismiss();

                        Alerter.create(Activity_DetailedView_Franchise.this)
                                .setTitle("WORLD BUSINESSES FOR SALE")
                                .setText("Oops! Something went wrong :( \n Try Again")
                                .setBackgroundColor(R.color.red)
                                .show();
                    }


                } catch (JSONException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {

                dialog.dismiss();
                Alerter.create(Activity_DetailedView_Franchise.this)
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
                params.put("franchise_key", str_franchise_key);
                params.put("currency", str_user_currency);

                System.out.println("USER_ID ::: " + str_user_id);
                System.out.println("FRANCHISE KEY ::: " + str_franchise_key);
                return params;
            }

        };

        // Adding request to request queue
        queue.add(request);
    }


    /*****************************
     * Bookmark Franchise
     ***************************/

    private void Function_Bookmark() {

        StringRequest request = new StringRequest(Request.Method.POST,
                AppConfig.url_add_bookmark_FranchiseProfile, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d(TAG, response.toString());

                try {
                    JSONObject obj = new JSONObject(response);
                    System.out.println("RESPONSE::: " + response);
                    int success = obj.getInt("status");

                    if (success == 1) {
                        dialog.dismiss();
                        Alerter.create(Activity_DetailedView_Franchise.this)
                                .setTitle("Success")
                                .setText("Business Bookmarked")
                                .setBackgroundColor(R.color.colorAccent)
                                .show();
                        btn_bookmark_franchise.setText("Bookmarked");
                        btn_bookmark_franchise.setBackgroundColor(getResources().getColor(R.color.Alert_Success));

                    } else if (success == 2) {
                        dialog.dismiss();
                        Alerter.create(Activity_DetailedView_Franchise.this)
                                .setTitle("Already Exists")
                                .setText("Franchise Already Bookmarked")
                                .setBackgroundColor(R.color.Alert_Warning)
                                .show();
                        btn_bookmark_franchise.setText("Bookmarked");
                        btn_bookmark_franchise.setBackgroundColor(getResources().getColor(R.color.Alert_Success));

                    } else {
                        dialog.dismiss();
                        TastyToast.makeText(getApplicationContext(), "Unable to Bookmark", TastyToast.LENGTH_LONG, TastyToast.ERROR);
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

                params.put("user_id", str_user_id);
                params.put("franchise_id", franchise_id);

                ////////////////

                System.out.println("user_id" + str_user_id);
                System.out.println("franchise_id" + franchise_id);

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


