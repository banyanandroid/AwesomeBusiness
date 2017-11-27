package banyan.com.awesomebusiness.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.MultiAutoCompleteTextView;
import android.widget.Spinner;
import android.widget.TextView;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.nguyenhoanglam.imagepicker.model.Image;
import com.sdsmdg.tastytoast.TastyToast;
import com.tapadoo.alerter.Alerter;
import com.toptoche.searchablespinnerlibrary.SearchableSpinner;

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
 * Created by Banyan on 20-Nov-17.
 */

public class Activity_AdvisorProfile_Preview extends AppCompatActivity {

    private Toolbar mToolbar;
    SpotsDialog dialog;
    public static RequestQueue queue;
    String TAG = "";
    TextView t1;
    String str_user_currency = "";

    Button btn_submit;

    TextView txt_name, txt_mobile_number, txt_email, txt_company_name, txt_adv_type, txt_add_line_one, txt_add_line_two,
            txt_bus_size_minimum, txt_bus_size_maximum, txt_phone_number, txt_company_summary,
            txt_zip_code, txt_user_location, txt_geo_locations, txt_special_sectors;

    String str_auth_person_name, str_email, str_mobile_num, str_company_name, str_advisor_type,
            str_selected_advisor_type, str_address_line_one, str_address_line_two, str_final_user_location_update,
            str_user_locations_text, str_zipcode, str_phone_number, str_summary, str_business_minimum, str_business_maximum,
            str_final_geo_area_location_update, str_locations_text, str_final_specialized_industry_update, str_industries_text,
            str_encoded_logo;

    SessionManager session;
    public static String str_user_id, str_user_name, str_user_email, str_user_photoo;
    public static String str_get_user_name, str_get_user_email, str_get_user_mobile, str_get_user_desigination, str_get_user_company;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_advisor_profile_preview);

        mToolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("Investor Profile");
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        mToolbar.setNavigationIcon(getResources().getDrawable(R.drawable.ic_action_back));
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(getApplicationContext(), Activity_AdvisorProfile.class);
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
        str_get_user_name = sharedPreferences.getString("str_profile_user_name", "str_profile_user_name");
        str_get_user_email = sharedPreferences.getString("str_profile_user_email", "str_profile_user_email");
        str_get_user_mobile = sharedPreferences.getString("str_user_mobile", "str_user_mobile");
        str_get_user_desigination = sharedPreferences.getString("str_user_designation", "str_user_designation");
        str_get_user_company = sharedPreferences.getString("str_user_company_name", "str_user_company_name");


        txt_name = (TextView) findViewById(R.id.txt_advisor_preview_name);
        txt_mobile_number = (TextView) findViewById(R.id.txt_advisor_preview_mobile_number);
        txt_email = (TextView) findViewById(R.id.txt_advisor_preview_official_email);
        txt_company_name = (TextView) findViewById(R.id.txt_advisor_preview_company_name);
        txt_adv_type = (TextView) findViewById(R.id.txt_advisor_preview_advisor_type);
        txt_add_line_one = (TextView) findViewById(R.id.txt_advisor_preview_address_line_one);
        txt_add_line_two = (TextView) findViewById(R.id.txt_advisor_preview_address_line_two);
        txt_bus_size_minimum = (TextView) findViewById(R.id.txt_advisor_preview_business_size_from);
        txt_bus_size_maximum = (TextView) findViewById(R.id.txt_advisor_preview_business_size_to);
        txt_phone_number = (TextView) findViewById(R.id.txt_advisor_preview_phone_number);
        txt_company_summary = (TextView) findViewById(R.id.txt_advisor_preview_summary);
        txt_zip_code = (TextView) findViewById(R.id.txt_advisor_preview_zip_code);
        txt_user_location = (TextView) findViewById(R.id.txt_advisor_preview_user_location);
        txt_geo_locations = (TextView) findViewById(R.id.txt_advisor_preview_geo_area_covered);
        txt_special_sectors = (TextView) findViewById(R.id.txt_advisor_preview_specialized_sector);

        btn_submit = (Button) findViewById(R.id.txt_advisor_preview_btn_submit);


        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dialog = new SpotsDialog(Activity_AdvisorProfile_Preview.this);
                dialog.show();
                queue = Volley.newRequestQueue(Activity_AdvisorProfile_Preview.this);
                Function_Submit_AdvisorProfile();


            }

        });


        try {

            SharedPreferences sharedPreferences_two = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

            str_auth_person_name = sharedPreferences.getString("prev_adv_prof_str_auth_person_name", "prev_adv_prof_str_auth_person_name");
            str_email = sharedPreferences.getString("prev_adv_prof_str_email", "prev_adv_prof_str_email");
            str_mobile_num = sharedPreferences.getString("prev_adv_prof_str_mobile_num", "prev_adv_prof_str_mobile_num");
            str_company_name = sharedPreferences.getString("prev_adv_prof_str_company_name", "prev_adv_prof_str_company_name");
            str_advisor_type = sharedPreferences.getString("prev_adv_prof_str_advisor_type", "prev_adv_prof_str_advisor_type");
            str_selected_advisor_type = sharedPreferences.getString("prev_adv_prof_str_advisor_type_text", "prev_adv_prof_str_advisor_type_text");
            str_address_line_one = sharedPreferences.getString("prev_adv_prof_str_address_line_one", "prev_adv_prof_str_address_line_one");
            str_address_line_two = sharedPreferences.getString("prev_adv_prof_str_address_line_two", "prev_adv_prof_str_address_line_two");
            str_final_user_location_update = sharedPreferences.getString("prev_adv_prof_str_final_user_location_update", "prev_adv_prof_str_final_user_location_update");
            str_user_locations_text = sharedPreferences.getString("prev_adv_prof_str_final_user_location_update_text", "prev_adv_prof_str_final_user_location_update_text");
            str_zipcode = sharedPreferences.getString("prev_adv_prof_str_zipcode", "prev_adv_prof_str_zipcode");
            str_phone_number = sharedPreferences.getString("prev_adv_prof_str_phone_number", "prev_adv_prof_str_phone_number");
            str_summary = sharedPreferences.getString("prev_adv_prof_str_summary", "prev_adv_prof_str_summary");
            str_business_minimum = sharedPreferences.getString("prev_adv_prof_str_business_minimum", "prev_adv_prof_str_business_minimum");
            str_business_maximum = sharedPreferences.getString("prev_adv_prof_str_business_maximum", "prev_adv_prof_str_business_maximum");
            str_final_geo_area_location_update = sharedPreferences.getString("prev_adv_prof_str_final_geo_area_location_update", "prev_adv_prof_str_final_geo_area_location_update");
            str_locations_text = sharedPreferences.getString("prev_adv_prof_str_final_geo_area_text", "prev_adv_prof_str_final_geo_area_text");
            str_final_specialized_industry_update = sharedPreferences.getString("prev_adv_prof_str_final_specialized_industry_update", "prev_adv_prof_str_final_specialized_industry_update");
            str_industries_text = sharedPreferences.getString("prev_adv_prof_str_final_specialized_industry_text", "prev_adv_prof_str_final_specialized_industry_text");
            str_encoded_logo = sharedPreferences.getString("prev_adv_prof_encoded_logo", "prev_adv_prof_encoded_logo");

            str_user_currency = sharedPreferences.getString("str_selected_currency", "str_selected_currency");


            txt_name.setText(str_auth_person_name);
            txt_mobile_number.setText(str_mobile_num);
            txt_email.setText(str_email);
            txt_company_name.setText(str_company_name);
            txt_adv_type.setText(str_selected_advisor_type);
            txt_add_line_one.setText(str_address_line_one);
            txt_add_line_two.setText(str_address_line_two);
            txt_bus_size_minimum.setText(str_business_minimum + " To ");
            txt_bus_size_maximum.setText(str_business_maximum);
            txt_phone_number.setText(str_phone_number);
            txt_company_summary.setText(str_summary);
            txt_zip_code.setText(str_zipcode);
            txt_user_location.setText(str_user_locations_text);
            txt_geo_locations.setText(str_locations_text);
            txt_special_sectors.setText(str_industries_text);


        } catch (Exception e) {


        }


    }


    /******************************************
     *    SUBMIT FRANCHISE PROFILE FORM
     * *******************************************/

    private void Function_Submit_AdvisorProfile() {

        StringRequest request = new StringRequest(Request.Method.POST,
                AppConfig.url_add_advisor_profile, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d(TAG, response.toString());

                try {
                    JSONObject obj = new JSONObject(response);
                    System.out.println("RESPONSE : " + response);
                    int success = obj.getInt("status");
                    if (success == 1) {
                        dialog.dismiss();

                        Alerter.create(Activity_AdvisorProfile_Preview.this)
                                .setTitle("Success")
                                .setText("Franchise Profile Added Successfully")
                                .setBackgroundColor(R.color.colorAccent)
                                .show();
                    } else {
                        dialog.dismiss();
                        TastyToast.makeText(getApplicationContext(), "Oops...! Registration Failed :(", TastyToast.LENGTH_LONG, TastyToast.ERROR);
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

                //GENERAL
                params.put("name", str_auth_person_name);
                params.put("email", str_email);
                params.put("mobile_no", str_mobile_num);
                params.put("company_name", str_company_name);
                params.put("advisor_type", str_advisor_type);
                params.put("addr_1", str_address_line_one);
                params.put("addr_2", str_address_line_two);
                params.put("city", str_final_user_location_update);
                params.put("zip", str_zipcode);
                params.put("ph_no", str_phone_number);
                params.put("business_description", str_summary);

                params.put("location", str_final_geo_area_location_update);
                params.put("sectors", str_final_specialized_industry_update);
                params.put("logo", str_encoded_logo);

                params.put("user_id", str_user_id);
                params.put("currency", str_user_currency);
                params.put("size_from", str_business_minimum);
                params.put("size_to", str_business_maximum);

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
