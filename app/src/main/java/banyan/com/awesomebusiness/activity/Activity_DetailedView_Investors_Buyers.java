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
import android.widget.Button;
import android.widget.ImageView;
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
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import banyan.com.awesomebusiness.R;
import banyan.com.awesomebusiness.global.AppConfig;
import banyan.com.awesomebusiness.global.SessionManager;
import dmax.dialog.SpotsDialog;

/**
 * Created by Banyan on 9/26/2017.
 */

public class Activity_DetailedView_Investors_Buyers extends AppCompatActivity {

    private Toolbar mToolbar;
    SpotsDialog dialog;
    public static RequestQueue queue;
    String TAG = "Auto_Res";
    SessionManager session;
    public static String str_user_id, str_user_name, str_user_email, str_user_photoo;
    String str_user_currency, str_investor_key = "";

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
    public static final String TAG_INVESTOR_AN_NAME = "investor_an_name";
    public static final String TAG_INVESTOR_INTEREST_NAME = "investor_interest_name";
    public static final String TAG_BUSINESS_KIND = "business_kind";
    public static final String TAG_INVESTOR_SHORT_DESCRIPTION = "investor_short_description";
    public static final String TAG_INVESTOR_ABOUT_USER = "investor_about_user";
    public static final String TAG_INVESTOR_ROI_FROM = "investor_roi";
    public static final String TAG_INVESTOR_ROI_TO = "investor_roi_to";
    public static final String TAG_INVESTOR_STAGES = "investor_stages";
    // public static final String TAG_COUNTRY_CURRENCY = "country_currency";
    //  public static final String TAG_INVESTO_PROFILE_URL = "investo_profile_url";
    // public static final String TAG_INVESTOR_LOGO = "investor_logo";
    public static final String TAG_LOCATION_NAME = "location_name";
    public static final String TAG_LOCATION_KEY = "location_key";
    public static final String TAG_INDUSTRY_NAME = "industry_name";
    public static final String TAG_INDUSTRY_KEY = "industry_key";

    ArrayList<String> Arraylist_update_location = null;
    ArrayList<String> Arraylist_update_industries = null;


    Button btn_contact_business, btn_bookmark_investor;

    ImageView img_investor;

    TextView txt_designation, txt_name, txt_phone, txt_email, txt_company, txt_investor_interest, txt_about_investor, txt_stages,
            txt_roi_from, txt_roi_to, txt_professonal_summary, txt_transaction_preference,
            txt_investment_size_from, txt_investment_size_to, txt_locations, txt_industries, txt_local_time, txt_status;

    String str_final_location, str_final_industries = "";

    String investor_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed_view_investors_buyers);

        mToolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("Investor/Buyer Details");
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
        str_investor_key = sharedPreferences.getString("investor_key", "investor_key");

        System.out.println("USER_ID-----" + str_user_id);
        System.out.println("INVESTOR KEY-----" + str_investor_key);
        System.out.println("INVESTOR CURRENCY-----" + str_user_currency);

        Arraylist_update_location = new ArrayList<String>();
        Arraylist_update_industries = new ArrayList<String>();

        img_investor = (ImageView) findViewById(R.id.activity_investor_details_image_view);

        txt_designation = (TextView) findViewById(R.id.activity_investor_details_txt_designation);
        txt_name = (TextView) findViewById(R.id.activity_investor_details_name);
        txt_phone = (TextView) findViewById(R.id.activity_investor_details_mobilenumber);
        txt_email = (TextView) findViewById(R.id.activity_investor_details_email);
        txt_company = (TextView) findViewById(R.id.activity_investor_details_company);
        txt_investor_interest = (TextView) findViewById(R.id.activity_investor_interest);

        txt_professonal_summary = (TextView) findViewById(R.id.activity_investor_details_professionalsummary);
        txt_transaction_preference = (TextView) findViewById(R.id.activity_investor_details_transaction_preference);
        txt_investment_size_from = (TextView) findViewById(R.id.activity_investor_details_investment_size_from);
        txt_investment_size_to = (TextView) findViewById(R.id.activity_investor_details_investment_size_to);
        txt_local_time = (TextView) findViewById(R.id.activity_investor_details_local_time);
        txt_status = (TextView) findViewById(R.id.activity_investor_details_status);
        txt_industries = (TextView) findViewById(R.id.activity_investor_details_prefered_industries);
        txt_locations = (TextView) findViewById(R.id.activity_investor_details_prefered_locations);

        txt_about_investor = (TextView) findViewById(R.id.activity_about_investor);
        txt_stages = (TextView) findViewById(R.id.activity_investor_details_stages);
        txt_roi_from = (TextView) findViewById(R.id.activity_investor_details_roi_from);
        txt_roi_to = (TextView) findViewById(R.id.activity_investor_details_roi_to);


        btn_contact_business = (Button) findViewById(R.id.btn_activity_investor_details_contact_business);

        btn_bookmark_investor = (Button) findViewById(R.id.btn_activity_investor_details_bookmark_business);

        btn_contact_business.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("str_investor_id", investor_id);
                editor.commit();

                Intent i = new Intent(getApplicationContext(), Activity_Contact_Business_Investors_Buyers.class);
                startActivity(i);
                finish();

            }

        });


        btn_bookmark_investor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                dialog = new SpotsDialog(Activity_DetailedView_Investors_Buyers.this);
                dialog.show();
                queue = Volley.newRequestQueue(getApplicationContext());
                Function_Bookmark();

            }

        });


        try {
            dialog = new SpotsDialog(Activity_DetailedView_Investors_Buyers.this);
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
                AppConfig.url_dashboard_search_result_investor_profile_detail, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d(TAG, response.toString());
                System.out.println("CAME RESPONSE ::: " + response.toString());

                try {

                    JSONObject obj = new JSONObject(response);
                    int success = obj.getInt("status");

                    if (success == 1) {

                        JSONArray arr_data;
                        JSONArray arr_location;
                        JSONArray arr_industry;
                        JSONArray arr_images;

                        arr_data = obj.getJSONArray("data");

                        for (int i = 0; arr_data.length() > i; i++) {

                            JSONObject obj_data = arr_data.getJSONObject(i);

                            //only this business_id string is declared above inorder to put it in shared preferences
                            investor_id = obj_data.getString(TAG_INVESTOR_ID);
                            String investor_key = obj_data.getString(TAG_INVESTOR_KEY);
                            String investor_name = obj_data.getString(TAG_INVESTOR_NAME);
                            String investor_confidential_email = obj_data.getString(TAG_INVESTOR_CONFIDENTIAL_EMAIL);
                            String investor_confidential_mobile = obj_data.getString(TAG_INVESTOR_CONFIDENTIAL_MOBILE);
                            String investor_user_role = obj_data.getString(TAG_INVESTOR_USER_ROLE);
                            String investor_currency_from = obj_data.getString(TAG_INVESTOR_CURRENCY_FROM);
                            String investor_currency_to = obj_data.getString(TAG_INVESTOR_CURRENCY_TO);
                            String investor_currency = obj_data.getString(TAG_INVESTOR_CURRENCY);
                            String investor_company_name = obj_data.getString(TAG_INVESTOR_COMPANY_NAME);
                            String investor_interest = obj_data.getString(TAG_BUSINESS_KIND);
                            String investor_designation = obj_data.getString(TAG_INVESTOR_DESIGNATION);
                            String investor_short_description = obj_data.getString(TAG_INVESTOR_SHORT_DESCRIPTION);
                            String investor_an_name = obj_data.getString(TAG_INVESTOR_AN_NAME);

                            String investor_about_user = obj_data.getString(TAG_INVESTOR_ABOUT_USER);
                            String investor_roi_from = obj_data.getString(TAG_INVESTOR_ROI_FROM);
                            String investor_roi_to = obj_data.getString(TAG_INVESTOR_ROI_TO);
                            String roi_from = obj_data.getString(TAG_INVESTOR_ROI_FROM);
                            String roi_to = obj_data.getString(TAG_INVESTOR_ROI_TO);

                            String investor_stages = obj_data.getString(TAG_INVESTOR_STAGES);
                            String investor_interest_name = obj_data.getString(TAG_INVESTOR_INTEREST_NAME);
                            String business_kind = obj_data.getString(TAG_BUSINESS_KIND);


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

                            System.out.println("INDUSTRIES ::: " + str_final_industries);

                            try {

                                txt_designation.setText("" + investor_designation);
                                txt_name.setText("" + investor_name);
                                txt_phone.setText("" + investor_confidential_mobile);
                                txt_email.setText("" + investor_confidential_email);
                                txt_company.setText("" + investor_company_name);
                                txt_investor_interest.setText("" + investor_interest);
                                txt_professonal_summary.setText("" + business_kind);
                                txt_transaction_preference.setText("" + investor_interest_name);
                                txt_investment_size_from.setText("" + investor_currency_from);
                                txt_investment_size_to.setText("" + investor_currency_to);
                                //txt_local_time.setText(dt.toString());
                                // txt_status.setText("" + fgdgfdgdfgdfgdfgf);
                                txt_industries.setText("" + str_final_industries + ", ");
                                txt_locations.setText("" + str_final_location + ", ");

                                txt_about_investor.setText("" + investor_about_user);
                                txt_stages.setText("" + investor_stages);
                                txt_roi_from.setText("" + investor_roi_from);
                                txt_roi_to.setText("" + investor_roi_to);


                                dialog.dismiss();

                            } catch (Exception e) {

                            }
                            dialog.dismiss();
                        }

                        dialog.dismiss();

                    } else if (success == 0) {

                        dialog.dismiss();

                        Alerter.create(Activity_DetailedView_Investors_Buyers.this)
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
                Alerter.create(Activity_DetailedView_Investors_Buyers.this)
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
                params.put("investor_key", str_investor_key);
                params.put("currency", str_user_currency);

                System.out.println("USER_ID ::: " + str_user_id);
                System.out.println("INVESTOR KEY ::: " + str_investor_key);
                System.out.println("INVESTOR CURRENCY ::: " + str_user_currency);

                return params;
            }

        };

        // Adding request to request queue
        queue.add(request);
    }


    /*****************************
     * Bookmark Investor
     ***************************/

    private void Function_Bookmark() {

        StringRequest request = new StringRequest(Request.Method.POST,
                AppConfig.url_add_bookmark_InvestorProfile, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d(TAG, response.toString());

                try {
                    JSONObject obj = new JSONObject(response);
                    System.out.println("RESPONSE::: " + response);
                    int success = obj.getInt("status");

                    if (success == 1) {
                        dialog.dismiss();
                        Alerter.create(Activity_DetailedView_Investors_Buyers.this)
                                .setTitle("Success")
                                .setText("Profile Bookmarked")
                                .setBackgroundColor(R.color.colorAccent)
                                .show();
                        btn_bookmark_investor.setText("Bookmarked");
                        btn_bookmark_investor.setBackgroundColor(getResources().getColor(R.color.Alert_Success));

                    } else if (success == 2) {
                        dialog.dismiss();
                        Alerter.create(Activity_DetailedView_Investors_Buyers.this)
                                .setTitle("Already Exists")
                                .setText("Profile Already Bookmarked")
                                .setBackgroundColor(R.color.Alert_Warning)
                                .show();
                        btn_bookmark_investor.setText("Bookmarked");
                        btn_bookmark_investor.setBackgroundColor(getResources().getColor(R.color.Alert_Success));

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
                params.put("investor_id", investor_id);

                ////////////////

                System.out.println("user_id" + str_user_id);
                System.out.println("investor_id" + investor_id);

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


