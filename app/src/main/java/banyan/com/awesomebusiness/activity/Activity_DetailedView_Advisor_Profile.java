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

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
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
import java.util.Map;

import banyan.com.awesomebusiness.R;
import banyan.com.awesomebusiness.global.AppConfig;
import banyan.com.awesomebusiness.global.SessionManager;
import dmax.dialog.SpotsDialog;

/**
 * Created by Banyan on 9/26/2017.
 */

public class Activity_DetailedView_Advisor_Profile extends AppCompatActivity {

    private Toolbar mToolbar;
    SpotsDialog dialog;
    public static RequestQueue queue;
    String TAG = "Auto_Res";
    SessionManager session;
    public static String str_user_id, str_user_name, str_user_email, str_user_photoo;
    String str_user_currency, str_advisor_key = "";

    public static final String TAG_ADVISOR_ID = "advisor_id";
    public static final String TAG_ADVISOR_KEY = "advisor_key";
    public static final String TAG_ADVISOR_NAME = "advisor_name";
    public static final String TAG_ADVISOR_EMAIL = "advisor_email";
    public static final String TAG_ADVISOR_PHONE = "advisor_mobile";
    public static final String TAG_ADVISOR_COMPANY_NAME = "advisor_company_name";
    public static final String TAG_ADVISOR_TYPE = "advisor_type";
    public static final String TAG_ADVISOR_ABOUT_COMPANY = "advisor_about_company";

    public static final String TAG_LOCATION_NAME = "location_name";
    public static final String TAG_LOCATION_KEY = "location_key";
    public static final String TAG_INDUSTRY_NAME = "industry_name";
    public static final String TAG_INDUSTRY_KEY = "industry_key";

    ArrayList<String> Arraylist_update_location = null;
    ArrayList<String> Arraylist_update_industries = null;


    Button btn_contact_business;

    ImageView img_investor;

    TextView txt_name, txt_phone, txt_email, txt_company, txt_advisor_type, txt_about_company, txt_locations, txt_industries;

    String str_final_location, str_final_industries = "";

    String advisor_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed_view_advisor_profile);

        mToolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("Advisor Details");
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
        str_advisor_key = sharedPreferences.getString("advisor_key", "advisor_key");

        System.out.println("USER_ID-----" + str_user_id);
        System.out.println("ADVISOR KEY-----" + str_advisor_key);
        System.out.println("USER CURRENCY-----" + str_user_currency);

        Arraylist_update_location = new ArrayList<String>();
        Arraylist_update_industries = new ArrayList<String>();


        img_investor = (ImageView) findViewById(R.id.activity_advisor_details_image_view);

        txt_name = (TextView) findViewById(R.id.activity_advisor_details_name);
        txt_phone = (TextView) findViewById(R.id.activity_advisor_details_mobilenumber);
        txt_email = (TextView) findViewById(R.id.activity_advisor_details_email);
        txt_company = (TextView) findViewById(R.id.activity_advisor_details_company);
        txt_advisor_type = (TextView) findViewById(R.id.activity_advisor_details_advisor_type);
        txt_about_company = (TextView) findViewById(R.id.activity_advisor_details_about_company);
        txt_industries = (TextView) findViewById(R.id.activity_advisor_details_prefered_industries);
        txt_locations = (TextView) findViewById(R.id.activity_advisor_details_prefered_locations);


        btn_contact_business = (Button) findViewById(R.id.btn_activity_advisor_details_contact_business);

        btn_contact_business.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("str_advisor_id", advisor_id);
                editor.commit();

                Intent i = new Intent(getApplicationContext(), Activity_Contact_Business_Investors_Buyers.class);
                startActivity(i);
                finish();

            }

        });

        try {
            dialog = new SpotsDialog(Activity_DetailedView_Advisor_Profile.this);
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
                AppConfig.url_dashboard_search_result_advisor_profile_detail, new Response.Listener<String>() {
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
                            advisor_id = obj_data.getString(TAG_ADVISOR_ID);
                            String advisor_key = obj_data.getString(TAG_ADVISOR_KEY);
                            String advisor_name = obj_data.getString(TAG_ADVISOR_NAME);
                            String advisor_email = obj_data.getString(TAG_ADVISOR_EMAIL);
                            String advisor_phone = obj_data.getString(TAG_ADVISOR_PHONE);
                            String advisor_company_name = obj_data.getString(TAG_ADVISOR_COMPANY_NAME);
                            String advisor_type = obj_data.getString(TAG_ADVISOR_TYPE);
                            String advisor_about_company = obj_data.getString(TAG_ADVISOR_ABOUT_COMPANY);


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

                                txt_name.setText("" + advisor_name);
                                txt_phone.setText("" + advisor_phone);
                                txt_email.setText("" + advisor_email);
                                txt_company.setText("" + advisor_company_name);
                                txt_advisor_type.setText("" + advisor_type);
                                txt_about_company.setText("" + advisor_about_company);
                                txt_industries.setText("" + str_final_industries + ", ");
                                txt_locations.setText("" + str_final_location + ", ");

                                dialog.dismiss();

                            } catch (Exception e) {

                            }
                            dialog.dismiss();
                        }

                        dialog.dismiss();

                    } else if (success == 0) {

                        dialog.dismiss();

                        Alerter.create(Activity_DetailedView_Advisor_Profile.this)
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
                Alerter.create(Activity_DetailedView_Advisor_Profile.this)
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
                params.put("advisor_key", str_advisor_key);
                params.put("currency", str_user_currency);

                System.out.println("USER_ID ::: " + str_user_id);
                System.out.println("ADVISOR KEY ::: " + str_advisor_key);
                System.out.println("ADVISOR CURRENCY ::: " + str_user_currency);

                return params;
            }

        };

        // Adding request to request queue
        queue.add(request);
    }


}


