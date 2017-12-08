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

public class Activity_InvestorProfile_Preview extends AppCompatActivity {

    private Toolbar mToolbar;
    SpotsDialog dialog;
    public static RequestQueue queue;
    String TAG = "";
    TextView t1;
    String str_user_currency = "";

    Button btn_submit;

    TextView txt_name, txt_mobile_number, txt_email, txt_investment_range_minimum,
            txt_investment_range_maximum, txt_roi_minimum, txt_roi_maximum, txt_kind_business_interested,
            txt_company_about, txt_interested_businees, txt_interested_location, txt_company_headquaters,
            txt_company_sector, txt_i_am, txt_interested_in, txt_business_stages;

    String str_name, str_mobile_number, str_email, str_investment_range_minimum,
            str_investment_range_maximum, str_roi_minimum, str_roi_maximum, str_kind_business_interested,
            str_company_about, str_interested_business, str_interested_location, str_company_headquaters,
            str_company_sector, str_i_am, str_interested_in, str_business_stages, str_interested_in_text,
            str_i_am_text, str_interested_business_text, str_interested_location_text,
            str_company_headquaters_text, str_company_sector_text, str_images;

    SessionManager session;
    public static String str_user_id, str_user_name, str_user_email, str_user_photoo;
    public static String str_get_user_name, str_get_user_email, str_get_user_mobile, str_get_user_desigination, str_get_user_company;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_investor_profile_preview);

        mToolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("Investor Preview");
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        mToolbar.setNavigationIcon(getResources().getDrawable(R.drawable.ic_action_back));
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(getApplicationContext(), Activity_InvestorProfile.class);
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

        btn_submit = (Button) findViewById(R.id.btn_submit);

        txt_name = (TextView) findViewById(R.id.txt_investor_preview_name);
        txt_mobile_number = (TextView) findViewById(R.id.txt_investor_preview_mobile_number);
        txt_email = (TextView) findViewById(R.id.txt_investor_preview_official_email);
        txt_investment_range_minimum = (TextView) findViewById(R.id.txt_investor_preview_investment_range_minimum);
        txt_investment_range_maximum = (TextView) findViewById(R.id.txt_investor_preview_investment_range_maximum);
        txt_roi_minimum = (TextView) findViewById(R.id.txt_investor_preview_roi_minimum);
        txt_roi_maximum = (TextView) findViewById(R.id.txt_investor_preview_roi_maximum);
        txt_kind_business_interested = (TextView) findViewById(R.id.txt_investor_preview_kindOf_business_interested);
        txt_company_about = (TextView) findViewById(R.id.txt_investor_preview_about_yourself);
        txt_interested_businees = (TextView) findViewById(R.id.txt_investor_preview_investor_industry);
        txt_interested_location = (TextView) findViewById(R.id.txt_investor_preview_interested_locations);
        txt_company_headquaters = (TextView) findViewById(R.id.txt_investor_preview_user_location);
        txt_company_sector = (TextView) findViewById(R.id.txt_investor_preview_company_sector);
        txt_i_am = (TextView) findViewById(R.id.txt_investor_preview_i_am);
        txt_interested_in = (TextView) findViewById(R.id.txt_investor_preview_intersted);
        txt_business_stages = (TextView) findViewById(R.id.txt_investor_preview_business_stages);

        try {

            SharedPreferences sharedPreferences_two = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

            str_name = sharedPreferences.getString("prev_inv_str_name", "prev_inv_str_name");
            str_mobile_number = sharedPreferences.getString("prev_inv_str_mobile", "prev_inv_str_mobile");
            str_email = sharedPreferences.getString("prev_inv_str_email", "prev_inv_str_email");
            str_interested_in = sharedPreferences.getString("prev_inv_str_selected_interest_id", "prev_inv_str_selected_interest_id");
            str_interested_in_text = sharedPreferences.getString("prev_inv_str_selected_interest_name", "prev_inv_str_selected_interest_name");
            str_i_am = sharedPreferences.getString("prev_inv_str_selected_role_id", "prev_inv_str_selected_role_id");
            str_i_am_text = sharedPreferences.getString("prev_inv_str_selected_role_name", "prev_inv_str_selected_role_name");
            str_interested_business = sharedPreferences.getString("prev_inv_str_final_industry_update", "prev_inv_str_final_industry_update");
            str_interested_business_text = sharedPreferences.getString("prev_str_final_industry_names", "prev_str_final_industry_names");
            str_interested_location = sharedPreferences.getString("prev_inv_str_final_location_update", "prev_inv_str_final_location_update");
            str_interested_location_text = sharedPreferences.getString("prev_str_final_location_names", "prev_str_final_location_names");
            str_investment_range_minimum = sharedPreferences.getString("prev_inv_str_inv_range_minimum", "prev_inv_str_inv_range_minimum");
            str_investment_range_maximum = sharedPreferences.getString("prev_inv_str_inv_range_maximum", "prev_inv_str_inv_range_maximum");
            str_roi_minimum = sharedPreferences.getString("prev_inv_str_roi_minimum", "prev_inv_str_roi_minimum");
            str_roi_maximum = sharedPreferences.getString("prev_inv_str_roi_maximum", "prev_inv_str_roi_maximum");
            str_company_headquaters_text = sharedPreferences.getString("prev_inv_str_company_location_text", "prev_inv_str_company_location_text");
            str_company_sector_text = sharedPreferences.getString("prev_inv_str_company_sector_text", "prev_inv_str_company_sector_text");
            str_company_headquaters = sharedPreferences.getString("prev_inv_str_company_location_id", "prev_inv_str_company_location_id");
            str_company_sector = sharedPreferences.getString("prev_inv_str_company_sector_id", "prev_inv_str_company_sector_id");
            str_kind_business_interested = sharedPreferences.getString("prev_inv_str_kind_of_business_interested", "prev_inv_str_kind_of_business_interested");
            str_business_stages = sharedPreferences.getString("prev_inv_str_business_stages", "prev_inv_str_business_stages");
            str_company_about = sharedPreferences.getString("prev_inv_str_about", "prev_inv_str_about");
            str_images = sharedPreferences.getString("prev_inv_str_images", "prev_inv_str_images");

            txt_name.setText(str_name);
            txt_mobile_number.setText(str_mobile_number);
            txt_email.setText(str_email);
            txt_investment_range_minimum.setText(str_investment_range_minimum + " To ");
            txt_investment_range_maximum.setText(str_investment_range_maximum);
            txt_roi_minimum.setText(str_roi_minimum + " To ");
            txt_roi_maximum.setText(str_roi_maximum);
            txt_kind_business_interested.setText(str_kind_business_interested);
            txt_company_about.setText(str_company_about);
            txt_interested_businees.setText(str_interested_business_text);
            txt_interested_location.setText(str_interested_location_text);
            txt_company_headquaters.setText(str_company_headquaters_text);
            txt_company_sector.setText(str_company_sector_text);
            txt_i_am.setText(str_i_am_text);
            txt_interested_in.setText(str_interested_in_text);


            if (str_business_stages.equals("1")) {

                txt_business_stages.setText("Profitable");

            } else if (str_business_stages.equals("2")) {

                txt_business_stages.setText("Breaking Even");

            } else if (str_business_stages.equals("3")) {

                txt_business_stages.setText("Pre-Startup");

            } else if (str_business_stages.equals("4")) {

                txt_business_stages.setText("Startup");

            } else if (str_business_stages.equals("5")) {

                txt_business_stages.setText("Existing Business");

            } else if (str_business_stages.equals("6")) {

                txt_business_stages.setText("Any");

            }


        } catch (Exception e) {


        }

        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dialog = new SpotsDialog(Activity_InvestorProfile_Preview.this);
                dialog.show();
                queue = Volley.newRequestQueue(Activity_InvestorProfile_Preview.this);
                Function_Submit_InvestorProfile();


            }

        });

    }


    /******************************************
     *    SUBMIT INVESTOR PROFILE FORM
     * *******************************************/

    private void Function_Submit_InvestorProfile() {

        StringRequest request = new StringRequest(Request.Method.POST,
                AppConfig.url_investor_profile_add, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                System.out.println("CAMEEEE INNNNNN");
                Log.d(TAG, response.toString());

                try {
                    JSONObject obj = new JSONObject(response);
                    System.out.println("RESPONSE : " + response);
                    int success = obj.getInt("status");
                    if (success == 1) {
                        dialog.dismiss();

                        Alerter.create(Activity_InvestorProfile_Preview.this)
                                .setTitle("Success")
                                .setText("Investor Profile Added Successfully")
                                .setBackgroundColor(R.color.colorAccent)
                                .show();
                    } else {
                        dialog.dismiss();

                        TastyToast.makeText(getApplicationContext(), "Oops...! Profile Creation Failed :(", TastyToast.LENGTH_LONG, TastyToast.ERROR);
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
                System.out.println("VOLLLEYYYYYYY ERRRROORRRRR");
                dialog.dismiss();

            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();


                params.put("name_u", str_name);
                params.put("mob_u", str_mobile_number);
                params.put("email_u", str_email);
                params.put("inter_u", str_interested_in);
                params.put("am_an", str_i_am);
                params.put("indust", str_interested_business);
                params.put("location_u", str_interested_location);
                params.put("user_currency", str_user_currency);
                params.put("invest_inr", str_investment_range_minimum);
                params.put("invest_to", str_investment_range_maximum);
                params.put("location", str_company_headquaters);
                params.put("com_s", str_company_sector);
                params.put("busi_in", str_kind_business_interested);
                params.put("abt_you", str_company_about);
                params.put("user_id", str_user_id);
                params.put("roi", str_roi_minimum);
                params.put("roi_to", str_roi_maximum);
                params.put("stages", str_business_stages);
                params.put("logo_file", str_images);
                params.put("profile_img", "IMAGEEEEEEEEEEE emptyyy");
                params.put("profile_document", "DOCUMENTTTTTTT emptyyy");
                params.put("profile_docs", "PROFILE DOCS emptyyy");
                params.put("documentsname", "DOCUMENTSNAME emptyyy");
                params.put("com_y", "comyyyyy");
                params.put("desig", "desigggggggggg");
                params.put("linked", "linkedddddd");

                //////////////////////////////


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
