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

public class Activity_FranchiseProfile_Preview extends AppCompatActivity {

    private Toolbar mToolbar;
    SpotsDialog dialog;
    public static RequestQueue queue;
    String TAG = "";
    TextView t1;
    String str_user_currency = "";

    Button btn_submit;

    TextView txt_name, txt_mobile_number, txt_email, txt_designation, txt_barnd_name, txt_offering,
            txt_industries, txt_fran_type, txt_company_about, txt_train_support, txt_year_started,
            txt_company_headquaters, txt_ideal_candidate, txt_expand_locations, txt_inv_minimum,
            txt_inv_maximum, txt_return_minimum, txt_return_maximum;

    String str_name, str_mobile_number, str_email, str_designation, str_barnd_name, str_offering_id, str_offering_text,
            str_industries, str_fran_type, str_company_about, str_train_support, str_year_started,
            str_company_headquaters, str_ideal_candidate, str_expand_locations, str_inv_minimum,
            str_inv_maximum, str_return_minimum, str_return_maximum, Str_industries_text,
            Str_company_headquaters_text, Str_expand_locations_text, Str_logo, Str_images;


    SessionManager session;
    public static String str_user_id, str_user_name, str_user_email, str_user_photoo;
    public static String str_get_user_name, str_get_user_email, str_get_user_mobile, str_get_user_desigination, str_get_user_company;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_franchiseprofile_preview);

        mToolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("Investor Profile");
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        mToolbar.setNavigationIcon(getResources().getDrawable(R.drawable.ic_action_back));
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(getApplicationContext(), Activity_FranchiseProfile.class);
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

        btn_submit = (Button) findViewById(R.id.franchise_preview_btn_submit);

        txt_name = (TextView) findViewById(R.id.txt_franchise_preview_name);
        txt_mobile_number = (TextView) findViewById(R.id.txt_franchise_preview_mobile_number);
        txt_email = (TextView) findViewById(R.id.txt_franchise_preview_email);
        txt_designation = (TextView) findViewById(R.id.txt_franchise_preview_designation);
        txt_barnd_name = (TextView) findViewById(R.id.txt_franchise_preview_brandname);
        txt_offering = (TextView) findViewById(R.id.txt_franchise_preview_oppor_offered);
        txt_industries = (TextView) findViewById(R.id.txt_franchise_preview_busi_industry);
        txt_fran_type = (TextView) findViewById(R.id.txt_franchise_preview_franchise_type);
        txt_company_about = (TextView) findViewById(R.id.txt_franchise_preview_about_company);
        txt_train_support = (TextView) findViewById(R.id.txt_franchise_preview_training_support);
        txt_company_headquaters = (TextView) findViewById(R.id.txt_franchise_preview_company_headquaters);
        txt_year_started = (TextView) findViewById(R.id.txt_franchise_preview_year_company_started);
        txt_ideal_candidate = (TextView) findViewById(R.id.txt_franchise_preview_ideal_candidate);
        txt_expand_locations = (TextView) findViewById(R.id.txt_franchise_preview_busi_expand_locations);
        txt_inv_minimum = (TextView) findViewById(R.id.txt_franchise_preview_inv_from);
        txt_inv_maximum = (TextView) findViewById(R.id.txt_franchise_preview_inv_two);
        txt_return_minimum = (TextView) findViewById(R.id.txt_franchise_preview_return_from);
        txt_return_maximum = (TextView) findViewById(R.id.txt_franchise_preview_return_to);


        try {

            SharedPreferences sharedPreferences_two = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

            str_name = sharedPreferences.getString("prev_fran_str_auth_person_name", "prev_fran_str_auth_person_name");
            str_email = sharedPreferences.getString("prev_fran_str_email", "prev_fran_str_email");
            str_mobile_number = sharedPreferences.getString("prev_fran_str_mobile_num", "prev_fran_str_mobile_num");
            str_designation = sharedPreferences.getString("prev_fran_str_designation", "prev_fran_str_designation");
            str_barnd_name = sharedPreferences.getString("prev_fran_str_brand_name", "prev_fran_str_brand_name");
            str_offering_id = sharedPreferences.getString("prev_fran_str_opportunity_offered", "prev_fran_str_opportunity_offered");


            str_offering_text = sharedPreferences.getString("prev_fran_str_opportunity_offered_text", "prev_fran_str_opportunity_offered_text");


            str_industries = sharedPreferences.getString("prev_fran_str_final_industry_update", "prev_fran_str_final_industry_update");
            Str_industries_text = sharedPreferences.getString("prev_fran_str_final_industry_update_text", "prev_fran_str_final_industry_update_text");
            str_company_about = sharedPreferences.getString("prev_fran_str_about_company", "prev_fran_str_about_company");
            str_train_support = sharedPreferences.getString("prev_fran_str_train_support", "prev_fran_str_train_support");
            str_year_started = sharedPreferences.getString("prev_fran_str_year_company_opr_start", "prev_fran_str_year_company_opr_start");
            str_company_headquaters = sharedPreferences.getString("prev_fran_str_final_headquaters", "prev_fran_str_final_headquaters");
            Str_company_headquaters_text = sharedPreferences.getString("prev_fran_str_final_headquaters_text", "prev_fran_str_final_headquaters_text");
            str_ideal_candidate = sharedPreferences.getString("prev_fran_str_ideal_candidate", "prev_fran_str_ideal_candidate");
            str_expand_locations = sharedPreferences.getString("prev_fran_str_final_location_update", "prev_fran_str_final_location_update");
            Str_expand_locations_text = sharedPreferences.getString("prev_fran_str_final_location_update_text", "prev_fran_str_final_location_update_text");
            str_inv_minimum = sharedPreferences.getString("prev_fran_str_investment_needed_minimum", "prev_fran_str_investment_needed_minimum");
            str_inv_maximum = sharedPreferences.getString("prev_fran_str_investment_needed_maximum", "prev_fran_str_investment_needed_maximum");
            str_return_minimum = sharedPreferences.getString("prev_fran_str_exp_return_minimum", "prev_fran_str_exp_return_minimum");
            str_return_maximum = sharedPreferences.getString("prev_fran_str_exp_return_maximum", "prev_fran_str_exp_return_maximum");
            Str_logo = sharedPreferences.getString("prev_fran_encoded_logo", "prev_fran_encoded_logo");
            Str_images = sharedPreferences.getString("prev_fran_listString", "prev_fran_listString");
            str_fran_type = sharedPreferences.getString("prev_fran_str_franchise_type", "prev_fran_str_franchise_type");


            txt_name.setText(str_name);
            txt_mobile_number.setText(str_email);
            txt_email.setText(str_mobile_number);
            txt_designation.setText(str_designation);
            txt_barnd_name.setText(str_barnd_name);
            txt_offering.setText(str_offering_text);
            txt_industries.setText(Str_industries_text);

            txt_company_about.setText(str_company_about);
            txt_train_support.setText(str_train_support);
            txt_company_headquaters.setText(Str_company_headquaters_text);
            txt_year_started.setText(str_year_started);
            txt_ideal_candidate.setText(str_ideal_candidate);
            txt_expand_locations.setText(Str_expand_locations_text);
            txt_inv_minimum.setText(str_inv_minimum + " To ");
            txt_inv_maximum.setText(str_inv_maximum);
            txt_return_minimum.setText(str_return_minimum + " To ");
            txt_return_maximum.setText(str_return_maximum);


            if (str_fran_type.equals("1")) {

                txt_fran_type.setText("Master Franchise");

            } else if (str_fran_type.equals("0")) {

                txt_fran_type.setText("Unit Franchise");

            }


        } catch (Exception e) {


        }

        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog = new SpotsDialog(Activity_FranchiseProfile_Preview.this);
                dialog.show();
                queue = Volley.newRequestQueue(getApplicationContext());
                Function_Submit_FranchiseProfile();

            }
        });


    }


    /******************************************
     *    SUBMIT FRANCHISE PROFILE FORM
     * *******************************************/

    private void Function_Submit_FranchiseProfile() {

        StringRequest request = new StringRequest(Request.Method.POST,
                AppConfig.url_add_franchise_profile, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d(TAG, response.toString());

                try {

                    System.out.println("1111111111111111111111111111");
                    JSONObject obj = new JSONObject(response);
                    System.out.println("222222222222222");
                    System.out.println("222222222222222" + response);

                    System.out.println("RESPONSE : " + response);
                    int success = obj.getInt("status");
                    if (success == 1) {

                        System.out.println("3333333333333333333");
                        System.out.println("333333333333333" + response);
                        dialog.dismiss();

                        Alerter.create(Activity_FranchiseProfile_Preview.this)
                                .setTitle("Success")
                                .setText("Franchise Profile Added Successfully")
                                .setBackgroundColor(R.color.colorAccent)
                                .show();
                    } else {

                        System.out.println("44444444444");
                        System.out.println("44444444444444" + response);

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
                params.put("name", str_name);
                params.put("email", str_email);
                params.put("mobile_no", str_mobile_number);
                params.put("designation", str_designation);
                params.put("brand_name", str_barnd_name);
                params.put("offering", str_offering_id);
                params.put("industry", str_industries);
                params.put("about_company", str_company_about);
                params.put("products_services", str_train_support);
                params.put("years", str_year_started);
                params.put("headquarters", str_company_headquaters);
                params.put("salespartner_looking", str_ideal_candidate);
                params.put("location", str_expand_locations);
                params.put("currency_change", str_user_currency);
                params.put("min_invest", str_inv_minimum);
                params.put("max_invest", str_inv_maximum);
                params.put("monthly_revenue", str_return_minimum);
                params.put("max_revenue", str_return_maximum);
                params.put("logo", Str_logo);
                params.put("images", Str_images);
                params.put("doc_name", "");
                params.put("document_type", "");
                params.put("documentsname", "");
                params.put("user_id", str_user_id);
                params.put("franchise_type", str_fran_type);


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
