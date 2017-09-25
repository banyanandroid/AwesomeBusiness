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
import android.widget.MultiAutoCompleteTextView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.sdsmdg.tastytoast.TastyToast;
import com.tapadoo.alerter.Alerter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import banyan.com.awesomebusiness.R;
import banyan.com.awesomebusiness.global.AppConfig;
import banyan.com.awesomebusiness.global.SessionManager;
import banyan.com.awesomebusiness.model.Image_Model;
import dmax.dialog.SpotsDialog;

/**
 * Created by CHANDRU on 24-Jul-17.
 */

public class Activity_DetailedView_Business_For_Sale extends AppCompatActivity {

    private Toolbar mToolbar;
    SpotsDialog dialog;
    public static RequestQueue queue;
    String TAG = "Auto_Res";
    SessionManager session;
    public static String str_user_id, str_user_name, str_user_email, str_user_photoo;
    String str_user_currency, str_business_key = "";

    public static final String TAG_BUSINESS_ID = "business_id";
    public static final String TAG_BUSINESS_KEY = "business_key";
    public static final String TAG_BUISNESS_SHORT_DESCRIPTION = "buisness_short_description";
    public static final String TAG_BUSINESS_YEARLY_SALES = "business_yearly_sales";
    public static final String TAG_BUSINESS_EBITDA = "business_ebitda";
    public static final String TAG_BUSINESS_EBITDA_RANGE = "business_ebitda_range";
    public static final String TAG_BUISNESS_INVESTMENT = "buisness_investment";
    public static final String TAG_BUISNESS_DESCRIPTION = "buisness_description";
    public static final String TAG_BUSINESS_INTEREST_NAME = "business_interest_name";
    public static final String TAG_BUSINESS_ROLE_NAME = "business_role_name";
    public static final String TAG_COUNTRY_CURRENCY = "country_currency";
    public static final String TAG_BUSINESS_CURRENCY = "business_currency";
    public static final String TAG_BUSINESS_SELL_LEASE = "business_sell_lease";
    public static final String TAG_BUSINESS_ASSETS_FEATURES = "business_assets_features";
    public static final String TAG_BUSINESS_SELL_LEASE_PRICE = "business_sell_lease_price";
    public static final String TAG_BUSINESS_SELL_LEASE_COST = "business_sell_lease_cost";
    public static final String TAG_BUSINESS_SELL_TYPE = "business_sell_type";
    public static final String TAG_BUSINESS_ASSETS_PURCHASED = "business_assets_purchased";
    public static final String TAG_BUSINESS_ASSETS_DESCRIPTION = "business_assets_description";
    public static final String TAG_BUSINESS_USER_ROLE = "business_user_role";
    public static final String TAG_BUSINESS_USER_INTEREST_IN = "business_user_interest_in";
    public static final String TAG_BUSINESS_USER_NAME = "business_user_name";
    public static final String TAG_BUSINESS_USER_MOBILE = "business_user_mobile";
    public static final String TAG_BUSINESS_USER_EMAIL = "business_user_email";
    public static final String TAG_BUSINESS_COMPANY_NAME = "business_company_name";
    public static final String TAG_BUSINESS_EMPLOYEE_COUNT = "business_employee_count";
    public static final String TAG_BUSINESS_ESTABLISHED = "business_established";
    public static final String TAG_BUISNESS_PRODUCTS_SERVICES = "buisness_products_services";
    public static final String TAG_BUISNESS_FACILITY = "buisness_facility";
    public static final String TAG_BUSINESS_ASSETS_REASON = "business_assets_reason";
    public static final String TAG_BUSINESS_MONTH_SALES = "business_month_sales";
    public static final String TAG_BUSINESS_TENTATIVE_PRICE = "business_tentative_price";
    public static final String TAG_BUSINESS_LEGAL_ENTITY_TYPE = "business_legal_entity_type";
    public static final String TAG_BUSINESS_REASON = "business_reason";
    public static final String TAG_BUSINESS_CONTACT_DETAILS = "business_contact_details";
    public static final String TAG_BUSINESS_COMPANY_DETAILS = "business_company_details";

    public static final String TAG_LOCATION_NAME = "location_name";
    public static final String TAG_LOCATION_KEY = "location_key";

    public static final String TAG_INDUSTRY_NAME = "industry_name";
    public static final String TAG_INDUSTRY_KEY = "industry_key";

    ArrayList<String> Arraylist_update_location = null;
    ArrayList<String> Arraylist_update_industries = null;


    Button btn_contact_business;

    ImageView img_business_for_sale;

    TextView txt_title, txt_established, txt_employees, txt_legal_entity, txt_reported_sales,
            txt_run_rate_ales, txt_ebitda_margin, txt_industries, txt_locations, txt_listed_by, txt_status,
            txt_seeking_investment_currency_type, txt_seeking_investment_amount, txt_reason,
            txt_business_overview, txt_products_services_overview, txt_facilities_overview;

    String str_final_location, str_final_industries = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed_view_business_for_sale);

        mToolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("Business Profile");
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
        str_business_key = sharedPreferences.getString("business_key", "business_key");

        Arraylist_update_location = new ArrayList<String>();
        Arraylist_update_industries = new ArrayList<String>();

        img_business_for_sale = (ImageView) findViewById(R.id.ativity_details_image_view);

        txt_title = (TextView) findViewById(R.id.ativity_details_txt_title);
        txt_established = (TextView) findViewById(R.id.ativity_details_txt_established_year);
        txt_employees = (TextView) findViewById(R.id.ativity_details_txt_employees);
        txt_legal_entity = (TextView) findViewById(R.id.ativity_details_legal_entity);
        txt_reported_sales = (TextView) findViewById(R.id.ativity_details_reported_sales);
        txt_run_rate_ales = (TextView) findViewById(R.id.ativity_details_run_rate_sales);
        txt_ebitda_margin = (TextView) findViewById(R.id.ativity_details_ebitda_margin);
        txt_industries = (TextView) findViewById(R.id.ativity_details_industries);
        txt_locations = (TextView) findViewById(R.id.ativity_details_locations);
        txt_listed_by = (TextView) findViewById(R.id.ativity_details_listed_by);
        txt_status = (TextView) findViewById(R.id.ativity_details_status);
        txt_seeking_investment_currency_type = (TextView) findViewById(R.id.ativity_details_txt_seeking_amount_currency_type);
        txt_seeking_investment_amount = (TextView) findViewById(R.id.ativity_details_txt_seeking_amount);
        txt_reason = (TextView) findViewById(R.id.ativity_details_txt_business_reason);
        txt_business_overview = (TextView) findViewById(R.id.ativity_details_txt_business_overview);
        txt_products_services_overview = (TextView) findViewById(R.id.ativity_details_txt_prod_serv_overview);
        txt_facilities_overview = (TextView) findViewById(R.id.ativity_details_txt_facilities_overview);

        btn_contact_business = (Button) findViewById(R.id.btn_contact_business);

        btn_contact_business.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                TastyToast.makeText(getApplicationContext(), "Button Clicked...!", TastyToast.LENGTH_LONG, TastyToast.SUCCESS);

            }

        });

        try {
            dialog = new SpotsDialog(Activity_DetailedView_Business_For_Sale.this);
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
                AppConfig.url_dashboard_search_result_business_profile_detail, new Response.Listener<String>() {
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

                        arr_data = obj.getJSONArray("data");

                        for (int i = 0; arr_data.length() > i; i++) {

                            JSONObject obj_data = arr_data.getJSONObject(i);


                            String business_id = obj_data.getString(TAG_BUSINESS_ID);
                            String business_key = obj_data.getString(TAG_BUSINESS_KEY);
                            String buisness_short_description = obj_data.getString(TAG_BUISNESS_SHORT_DESCRIPTION);
                            String business_yearly_sales = obj_data.getString(TAG_BUSINESS_YEARLY_SALES);
                            String business_ebitda = obj_data.getString(TAG_BUSINESS_EBITDA);
                            String business_ebitda_range = obj_data.getString(TAG_BUSINESS_EBITDA_RANGE);
                            String buisness_investment = obj_data.getString(TAG_BUISNESS_INVESTMENT);
                            String buisness_description = obj_data.getString(TAG_BUISNESS_DESCRIPTION);
                            String business_interest_name = obj_data.getString(TAG_BUSINESS_INTEREST_NAME);
                            String business_role_name = obj_data.getString(TAG_BUSINESS_ROLE_NAME);
                            String country_currency = obj_data.getString(TAG_COUNTRY_CURRENCY);
                            String business_currency = obj_data.getString(TAG_BUSINESS_CURRENCY);
                            String business_sell_lease = obj_data.getString(TAG_BUSINESS_SELL_LEASE);
                            String business_assets_features = obj_data.getString(TAG_BUSINESS_ASSETS_FEATURES);
                            String business_sell_lease_price = obj_data.getString(TAG_BUSINESS_SELL_LEASE_PRICE);
                            String business_sell_lease_cost = obj_data.getString(TAG_BUSINESS_SELL_LEASE_COST);
                            String business_sell_type = obj_data.getString(TAG_BUSINESS_SELL_TYPE);
                            String business_assets_purchased = obj_data.getString(TAG_BUSINESS_ASSETS_PURCHASED);
                            String business_assets_description = obj_data.getString(TAG_BUSINESS_ASSETS_DESCRIPTION);
                            String business_user_role = obj_data.getString(TAG_BUSINESS_USER_ROLE);
                            String business_user_interest_in = obj_data.getString(TAG_BUSINESS_USER_INTEREST_IN);
                            String business_user_name = obj_data.getString(TAG_BUSINESS_USER_NAME);
                            String business_user_mobile = obj_data.getString(TAG_BUSINESS_USER_MOBILE);
                            String business_user_email = obj_data.getString(TAG_BUSINESS_USER_EMAIL);
                            String business_company_name = obj_data.getString(TAG_BUSINESS_COMPANY_NAME);
                            String business_employee_count = obj_data.getString(TAG_BUSINESS_EMPLOYEE_COUNT);
                            String business_established = obj_data.getString(TAG_BUSINESS_ESTABLISHED);
                            String buisness_products_services = obj_data.getString(TAG_BUISNESS_PRODUCTS_SERVICES);
                            String buisness_facility = obj_data.getString(TAG_BUISNESS_FACILITY);
                            String business_assets_reason = obj_data.getString(TAG_BUSINESS_ASSETS_REASON);
                            String business_month_sales = obj_data.getString(TAG_BUSINESS_MONTH_SALES);
                            String business_tentative_price = obj_data.getString(TAG_BUSINESS_TENTATIVE_PRICE);
                            String business_legal_entity_type = obj_data.getString(TAG_BUSINESS_LEGAL_ENTITY_TYPE);
                            String business_reason = obj_data.getString(TAG_BUSINESS_REASON);
                            String business_contact_details = obj_data.getString(TAG_BUSINESS_CONTACT_DETAILS);
                            String business_company_details = obj_data.getString(TAG_BUSINESS_COMPANY_DETAILS);

                            arr_location = obj_data.getJSONArray("location");
                            Arraylist_update_location.clear();
                            for (int j = 0; arr_location.length() > j; j++) {
                                JSONObject obj_location = arr_location.getJSONObject(j);

                                String location_name = obj_location.getString(TAG_LOCATION_NAME);
                                String location_key = obj_location.getString(TAG_LOCATION_KEY);

                                Arraylist_update_location.add(location_name);

                            }
                            str_final_location = TextUtils.join(", ", Arraylist_update_location);

                            System.out.println("LOCATION ::: " + str_final_location);

                            arr_industry = obj_data.getJSONArray("industry");
                            Arraylist_update_industries.clear();
                            for (int k = 0; arr_industry.length() > k; k++) {
                                JSONObject obj_industry = arr_industry.getJSONObject(k);

                                String industry_name = obj_industry.getString(TAG_INDUSTRY_NAME);
                                String industry_key = obj_industry.getString(TAG_INDUSTRY_KEY);

                                Arraylist_update_industries.add(industry_name);

                            }
                            str_final_industries = TextUtils.join(", ", Arraylist_update_industries);

                            System.out.println("INDUSTRIES ::: " + str_final_industries);

                            try {

                                txt_title.setText("" + buisness_short_description);
                                txt_established.setText("" + business_established);
                                txt_employees.setText("" + business_employee_count);
                                txt_legal_entity.setText("" + business_legal_entity_type);
                                txt_reported_sales.setText("" + business_yearly_sales);
                                txt_run_rate_ales.setText("" + business_month_sales);
                                txt_ebitda_margin.setText("" + business_ebitda);
                                txt_listed_by.setText("" + business_role_name);
                                txt_seeking_investment_currency_type.setText("" + country_currency);
                                txt_seeking_investment_amount.setText("" + business_tentative_price);
                                txt_reason.setText("" + business_reason);
                                txt_business_overview.setText("" + buisness_products_services);
                                txt_products_services_overview.setText("" + buisness_products_services);
                                txt_facilities_overview.setText("" + buisness_facility);

                                txt_industries.setText("" + str_final_industries + ", ");
                                txt_locations.setText("" + str_final_location + ", ");


                            } catch (Exception e) {

                            }
                        }

                    } else if (success == 0) {

                        dialog.dismiss();

                        Alerter.create(Activity_DetailedView_Business_For_Sale.this)
                                .setTitle("AWESOME BUSINESSES")
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
                Alerter.create(Activity_DetailedView_Business_For_Sale.this)
                        .setTitle("AWESOME BUSINESSES")
                        .setText("Internal Error :(\n" + error.getMessage())
                        .setBackgroundColor(R.color.colorPrimaryDark)
                        .show();
            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();

                params.put("user_id", str_user_id);
                params.put("business_key", str_business_key);

                System.out.println("USER_IDDDDDDDDDDDDDDDDDD ::: " + str_user_id);
                System.out.println("BUSINESS KEYYYYYYYYYYYYY ::: " + str_business_key);

                return params;
            }

        };

        // Adding request to request queue
        queue.add(request);
    }


}


