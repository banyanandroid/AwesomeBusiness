package banyan.com.awesomebusiness.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
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

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import banyan.com.awesomebusiness.R;
import banyan.com.awesomebusiness.global.AppConfig;
import banyan.com.awesomebusiness.global.SessionManager;
import dmax.dialog.SpotsDialog;

/**
 * Created by Banyan on 16-Nov-17.
 */

public class Activity_BusinessProfile_Preview extends AppCompatActivity {

    private Toolbar mToolbar;
    SpotsDialog dialog;
    public static RequestQueue queue;
    Button btn_submit;
    String TAG = "Auto_Res";
    String str_user_currency = "";

    SessionManager session;
    public static String str_user_id, str_user_name, str_user_email, str_user_photoo;
    public static String str_get_user_name, str_get_user_email, str_get_user_mobile, str_get_user_desigination, str_get_user_company;

    // Condition String

    String str_profile_type = "";


    TextView txt_name, txt_mobile, txt_company_name, txt_official_email,
            txt_business_established_year, txt_no_of_permanent_employees, txt_business_des, txt_business_highlights,
            txt_business_all_prod_serv, txt_business_facility_desc, txt_avg_monthly_sales, txt_monthly_expenses,
            txt_latest_yearly_sales, txt_cashflow_profit, txt_physical_assests_value, txt_tentative_selling_price,
            txt_reason_for_sale, txt_companydetails, txt_contatdetails, txt_i_am, txt_interested_in, txt_amount_fixed_for,
            txt_business_legal_type, txt_bus_busineeslist, txt_bus_locationlist, txt_industries_use_asset, txt_asset_loation;

    //Text view for Titles
    TextView txt_title_business_established_year, txt_title_no_of_permanent_employees, txt_title_business_des,
            txt_title_business_highlights, txt_title_business_all_prod_serv, txt_title_business_facility_desc,
            txt_title_avg_monthly_sales, txt_title_monthly_expen_present, txt_title_latest_yearly_sales,
            txt_title_avg_monthly_cashflow, txt_title_val_phy_assets, txt_title_tentative_sprice, txt_title_reason_for_sale;

    //Selling or Leasing out business
    TextView txt_year_asset_purchased, txt_asset_seeking_to_sell, txt_asset_features, txt_asset_selling_leasing_price, txt_asset_selling_reason;

    //Selling or Leasing out business  Text view for Titles
    TextView txt_title_year_asset_purchased, txt_title_asset_seeking_to_sell, txt_title_asset_features, txt_title_asset_selling_leasing_price, txt_sale_type, txt_title_asset_selling_reason;


    String str_name, str_company_name, str_mobile, str_official_email,
            str_ch_companydetails, str_ch_contactdetails, str_selected_role_name, str_selected_role_id, str_selected_interest_name, str_selected_interest_id, str_final_industry_update,
            str_industries_text, str_final_location_update, str_locations_text,
            str_business_established_year, str_no_of_permanent_employees, str_business_desc,
            str_business_highlights, str_business_all_prod_serv, str_business_facility_desc,
            str_avg_monthly_sales, str_avg_monthly_expenses, str_latest_yearly_sales, str_cashflow_profit,
            str_physical_assests_value, str_tentative_selling_price, str_reason_for_sale,
            str_spn_business_legal_type, str_year_asset_purchased,
            str_asset_seeking_to_sell, str_asset_industry,
            str_asset_location, str_asset_features, str_asset_selling_leasing_price,
            str_asset_selling_eason, str_amount_fixed_for, listString = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_business_profile_preview);

        mToolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("Business Preview");
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        mToolbar.setNavigationIcon(getResources().getDrawable(R.drawable.ic_action_back));
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(getApplicationContext(), Activity_BusinessProfile.class);
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

        //user details
        txt_name = (TextView) findViewById(R.id.txt_name);
        txt_mobile = (TextView) findViewById(R.id.txt_mobile_number);
        txt_company_name = (TextView) findViewById(R.id.txt_company_name);
        txt_official_email = (TextView) findViewById(R.id.txt_official_email);
        txt_companydetails = (TextView) findViewById(R.id.txt_display_company_details);
        txt_contatdetails = (TextView) findViewById(R.id.txt_display_contact_details);
        //commom
        txt_i_am = (TextView) findViewById(R.id.txt_business_profile_i_am);
        txt_interested_in = (TextView) findViewById(R.id.txt_business_profile_intersted);
        txt_business_established_year = (TextView) findViewById(R.id.txt_when_established);
        txt_bus_busineeslist = (TextView) findViewById(R.id.txt_business_profile_busi_industry);
        txt_bus_locationlist = (TextView) findViewById(R.id.txt_business_profile_busi_loca_at);
        txt_no_of_permanent_employees = (TextView) findViewById(R.id.txt_permanant_employees);
        txt_business_legal_type = (TextView) findViewById(R.id.txt_business_legel_entity);
        txt_business_des = (TextView) findViewById(R.id.txt_business_desc);
        txt_business_highlights = (TextView) findViewById(R.id.txt_business_highlights);
        txt_business_all_prod_serv = (TextView) findViewById(R.id.txt_list_product_services);
        txt_business_facility_desc = (TextView) findViewById(R.id.txt_facility_desc);
        txt_avg_monthly_sales = (TextView) findViewById(R.id.txt_avg_mnthly_sales);
        txt_monthly_expenses = (TextView) findViewById(R.id.txt_avg_mnthly_expenses);
        txt_latest_yearly_sales = (TextView) findViewById(R.id.txt_latest_yearly_sales);
        txt_cashflow_profit = (TextView) findViewById(R.id.txt_EBITDA_operating_profit_margin);
        txt_physical_assests_value = (TextView) findViewById(R.id.txt_phy_assests_value);
        txt_tentative_selling_price = (TextView) findViewById(R.id.txt_tentative_selling_price);
        txt_reason_for_sale = (TextView) findViewById(R.id.txt_reason_for_sale);

        //Selling or Leasing out business
        txt_year_asset_purchased = (TextView) findViewById(R.id.txt_loan_when_asset_purchased);
        txt_industries_use_asset = (TextView) findViewById(R.id.txt_business_profile_Industries_use_asset);
        txt_asset_loation = (TextView) findViewById(R.id.txt_business_profile_asset_loca_at);
        txt_asset_seeking_to_sell = (TextView) findViewById(R.id.txt_wt_asset_sell);
        txt_asset_features = (TextView) findViewById(R.id.txt_asset_features);
        txt_asset_selling_leasing_price = (TextView) findViewById(R.id.txt_price_selling_leasing);
        txt_amount_fixed_for = (TextView) findViewById(R.id.txt_amount_for);
        txt_asset_selling_reason = (TextView) findViewById(R.id.txt_reason_for_sell_asset);

        //Textview titles that have to be hidden during  Businesses For Sale  type
        txt_title_year_asset_purchased = (TextView) findViewById(R.id.txt_year_asset_purchased_title);
        txt_title_asset_seeking_to_sell = (TextView) findViewById(R.id.txt_wt_asset_sell_title);
        txt_title_asset_features = (TextView) findViewById(R.id.txt_asset_features_title);
        txt_title_asset_selling_leasing_price = (TextView) findViewById(R.id.txt_price_selling_leasing_title);
        txt_sale_type = (TextView) findViewById(R.id.txt_amount_for_title);
        txt_title_asset_selling_reason = (TextView) findViewById(R.id.txt_reason_for_sell_asset_title);


        //Textview titles that have to be hidden during  Selling / Leasing  Assets  type
        txt_title_business_established_year = (TextView) findViewById(R.id.txt_when_established_title);
        txt_title_no_of_permanent_employees = (TextView) findViewById(R.id.txt_permanant_employees_title);
        txt_title_business_des = (TextView) findViewById(R.id.txt_business_desc_title);
        txt_title_business_highlights = (TextView) findViewById(R.id.txt_business_highlights_title);
        txt_title_business_all_prod_serv = (TextView) findViewById(R.id.txt_list_product_services_title);
        txt_title_business_facility_desc = (TextView) findViewById(R.id.txt_facility_desc_title);
        txt_title_avg_monthly_sales = (TextView) findViewById(R.id.txt_avg_mnthly_sales_title);
        txt_title_monthly_expen_present = (TextView) findViewById(R.id.txt_avg_mnthly_expenses_title);
        txt_title_latest_yearly_sales = (TextView) findViewById(R.id.txt_latest_yearly_sales_title);
        txt_title_avg_monthly_cashflow = (TextView) findViewById(R.id.txt_avg_mnthly_cashflow_title);
        txt_title_val_phy_assets = (TextView) findViewById(R.id.txt_phy_assests_value_title);
        txt_title_tentative_sprice = (TextView) findViewById(R.id.txt_tentative_selling_price_title);
        txt_title_reason_for_sale = (TextView) findViewById(R.id.txt_reason_for_sale_title);


        btn_submit = (Button) findViewById(R.id.btn_submit);

        try {

            SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

            str_user_currency = sharedPreferences.getString("str_selected_currency", "str_selected_currency");
            str_name = sharedPreferences.getString("prev_str_name", "prev_str_name");
            str_company_name = sharedPreferences.getString("prev_str_company_name", "prev_str_company_name");
            str_mobile = sharedPreferences.getString("prev_str_mobile", "prev_str_mobile");
            str_official_email = sharedPreferences.getString("prev_str_official_email", "prev_str_official_email");
            str_ch_companydetails = sharedPreferences.getString("prev_str_ch_companydetails", "prev_str_ch_companydetails");
            str_ch_contactdetails = sharedPreferences.getString("prev_str_ch_contactdetails", "prev_str_ch_contactdetails");
            str_selected_role_name = sharedPreferences.getString("prev_str_selected_role_name", "prev_str_selected_role_name");
            str_selected_role_id = sharedPreferences.getString("prev_str_selected_role_id", "prev_str_selected_role_id");
            str_selected_interest_name = sharedPreferences.getString("prev_str_selected_interest_name", "prev_str_selected_interest_name");
            str_selected_interest_id = sharedPreferences.getString("prev_str_selected_interest_id", "prev_str_selected_interest_id");
            str_final_industry_update = sharedPreferences.getString("prev_str_final_industry_update", "prev_str_final_industry_update");
            str_industries_text = sharedPreferences.getString("prev_str_final_industry_names", "prev_str_final_industry_names");
            str_final_location_update = sharedPreferences.getString("prev_str_final_location_update", "prev_str_final_location_update");
            str_locations_text = sharedPreferences.getString("prev_str_final_location_names", "prev_str_final_location_names");
            str_business_established_year = sharedPreferences.getString("prev_str_business_established_year", "prev_str_business_established_year");
            str_no_of_permanent_employees = sharedPreferences.getString("prev_str_no_of_permanent_employees", "prev_str_no_of_permanent_employees");
            str_spn_business_legal_type = sharedPreferences.getString("prev_str_spn_business_legal_type", "prev_str_spn_business_legal_type");
            str_business_desc = sharedPreferences.getString("prev_str_business_desc", "prev_str_business_desc");
            str_business_highlights = sharedPreferences.getString("prev_str_business_highlights", "prev_str_business_highlights");
            str_business_all_prod_serv = sharedPreferences.getString("prev_str_business_all_prod_serv", "prev_str_business_all_prod_serv");
            str_business_facility_desc = sharedPreferences.getString("prev_str_business_facility_desc", "prev_str_business_facility_desc");
            str_avg_monthly_sales = sharedPreferences.getString("prev_str_avg_monthly_sales", "prev_str_avg_monthly_sales");
            str_avg_monthly_expenses = sharedPreferences.getString("prev_str_avg_monthly_expenses", "prev_str_avg_monthly_expenses");
            str_latest_yearly_sales = sharedPreferences.getString("prev_str_latest_yearly_sales", "prev_str_latest_yearly_sales");
            str_cashflow_profit = sharedPreferences.getString("prev_str_cashflow_profit", "prev_str_cashflow_profit");
            str_physical_assests_value = sharedPreferences.getString("prev_str_physical_assests_value", "prev_str_physical_assests_value");
            str_tentative_selling_price = sharedPreferences.getString("prev_str_tentative_selling_price", "prev_str_tentative_selling_price");
            str_reason_for_sale = sharedPreferences.getString("prev_str_reason_for_sale", "prev_str_reason_for_sale");
            str_year_asset_purchased = sharedPreferences.getString("prev_str_year_asset_purchased", "prev_str_year_asset_purchased");
            str_asset_seeking_to_sell = sharedPreferences.getString("prev_str_asset_seeking_to_sell", "prev_str_asset_seeking_to_sell");
            str_asset_industry = sharedPreferences.getString("prev_str_asset_industry_update", "prev_str_asset_industry_update");
            str_asset_location = sharedPreferences.getString("prev_str_asset_location_update", "prev_str_asset_location_update");
            str_asset_features = sharedPreferences.getString("prev_str_asset_features", "prev_str_asset_features");
            str_asset_selling_leasing_price = sharedPreferences.getString("prev_str_asset_selling_leasing_price", "prev_str_asset_selling_leasing_price");
            str_asset_selling_eason = sharedPreferences.getString("prev_str_asset_selling_eason", "prev_str_asset_selling_eason");
            str_amount_fixed_for = sharedPreferences.getString("prev_str_amount_fixed_for", "prev_str_amount_fixed_for");
            listString = sharedPreferences.getString("prev_str_image", "prev_str_image");


            txt_name.setText(str_name);
            txt_mobile.setText(str_mobile);
            txt_company_name.setText(str_company_name);
            txt_official_email.setText(str_official_email);
            txt_i_am.setText(str_selected_role_name);
            txt_interested_in.setText(str_selected_interest_name);
            txt_business_established_year.setText(str_business_established_year);
            txt_bus_busineeslist.setText(str_industries_text);
            txt_bus_locationlist.setText(str_locations_text);
            txt_no_of_permanent_employees.setText(str_no_of_permanent_employees);
            txt_business_legal_type.setText(str_spn_business_legal_type);
            txt_business_des.setText(str_business_desc);
            txt_business_highlights.setText(str_business_highlights);
            txt_business_all_prod_serv.setText(str_business_all_prod_serv);
            txt_business_facility_desc.setText(str_business_facility_desc);
            txt_avg_monthly_sales.setText(str_avg_monthly_sales);
            txt_monthly_expenses.setText(str_avg_monthly_expenses);
            txt_latest_yearly_sales.setText(str_latest_yearly_sales);
            txt_cashflow_profit.setText(str_cashflow_profit);
            txt_physical_assests_value.setText(str_physical_assests_value);
            txt_tentative_selling_price.setText(str_tentative_selling_price);
            txt_reason_for_sale.setText(str_reason_for_sale);
            txt_year_asset_purchased.setText(str_year_asset_purchased);
            txt_industries_use_asset.setText(str_industries_text);
            txt_asset_loation.setText(str_locations_text);
            txt_asset_seeking_to_sell.setText(str_asset_seeking_to_sell);
            txt_asset_features.setText(str_asset_features);
            txt_asset_selling_leasing_price.setText(str_asset_selling_leasing_price);
            txt_amount_fixed_for.setText(str_amount_fixed_for);
            txt_asset_selling_reason.setText(str_asset_selling_eason);


            if (str_ch_companydetails.equals("1")) {
                txt_companydetails.setText("Company Details");
            } else {
                txt_companydetails.setText("");
            }

            if (str_ch_contactdetails.equals("1")) {
                txt_contatdetails.setText("Contact Details");
            } else {
                txt_companydetails.setText("");
            }


            if (str_selected_interest_name.equals("Selling / Leasing  Assets")) {

                //Textview titles that have to be hidden during  Selling / Leasing  Assets  type
                txt_title_business_established_year.setVisibility(View.GONE);
                txt_business_established_year.setVisibility(View.GONE);

                txt_title_no_of_permanent_employees.setVisibility(View.GONE);
                txt_no_of_permanent_employees.setVisibility(View.GONE);

                txt_title_business_des.setVisibility(View.GONE);
                txt_business_des.setVisibility(View.GONE);

                txt_title_business_highlights.setVisibility(View.GONE);
                txt_business_highlights.setVisibility(View.GONE);

                txt_title_business_all_prod_serv.setVisibility(View.GONE);
                txt_business_all_prod_serv.setVisibility(View.GONE);

                txt_title_business_facility_desc.setVisibility(View.GONE);
                txt_business_facility_desc.setVisibility(View.GONE);

                txt_title_avg_monthly_sales.setVisibility(View.GONE);
                txt_avg_monthly_sales.setVisibility(View.GONE);

                txt_title_monthly_expen_present.setVisibility(View.GONE);
                txt_monthly_expenses.setVisibility(View.GONE);

                txt_title_latest_yearly_sales.setVisibility(View.GONE);
                txt_latest_yearly_sales.setVisibility(View.GONE);

                txt_title_avg_monthly_cashflow.setVisibility(View.GONE);
                txt_cashflow_profit.setVisibility(View.GONE);

                txt_title_val_phy_assets.setVisibility(View.GONE);
                txt_physical_assests_value.setVisibility(View.GONE);

                txt_title_tentative_sprice.setVisibility(View.GONE);
                txt_tentative_selling_price.setVisibility(View.GONE);

                txt_title_reason_for_sale.setVisibility(View.GONE);
                txt_reason_for_sale.setVisibility(View.GONE);


            } else if (str_selected_interest_name.equals("Businesses For Sale")) {

                //Textview titles that have to be hidden during  Businesses For Sale  type
                txt_title_year_asset_purchased.setVisibility(View.GONE);
                txt_year_asset_purchased.setVisibility(View.GONE);

                txt_title_asset_seeking_to_sell.setVisibility(View.GONE);
                txt_asset_seeking_to_sell.setVisibility(View.GONE);

                txt_title_asset_features.setVisibility(View.GONE);
                txt_asset_features.setVisibility(View.GONE);

                txt_title_asset_selling_leasing_price.setVisibility(View.GONE);
                txt_asset_selling_leasing_price.setVisibility(View.GONE);

                txt_sale_type.setVisibility(View.GONE);
                txt_amount_fixed_for.setVisibility(View.GONE);

                txt_title_asset_selling_reason.setVisibility(View.GONE);
                txt_asset_selling_reason.setVisibility(View.GONE);


            }


        } catch (Exception e) {


        }


        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dialog = new SpotsDialog(Activity_BusinessProfile_Preview.this);
                dialog.show();
                queue = Volley.newRequestQueue(Activity_BusinessProfile_Preview.this);
                Function_Submit_BusinessProfile();


            }

        });
    }


    /******************************************
     *    SUBMIT BUSINESS PROFILE FORM
     * *******************************************/
    private void Function_Submit_BusinessProfile() {

        StringRequest request = new StringRequest(Request.Method.POST,
                AppConfig.url_add_business_profile, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d(TAG, response.toString());

                try {
                    JSONObject obj = new JSONObject(response);
                    System.out.println("RESPONSEEEEEEEEEEEEEEEEEEE : " + response);
                    int success = obj.getInt("status");
                    if (success == 1) {
                        dialog.dismiss();

                        Alerter.create(Activity_BusinessProfile_Preview.this)
                                .setTitle("Success")
                                .setText("Bussiness Profile Added Successfully")
                                .setBackgroundColor(R.color.colorAccent)
                                .show();
                    } else {
                        dialog.dismiss();
                        TastyToast.makeText(getApplicationContext(), "Oops...! Profile Submission Failed :(", TastyToast.LENGTH_LONG, TastyToast.ERROR);
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

                params.put("user_name", str_name);
                params.put("company_name", str_company_name);
                params.put("mobile_no", str_mobile);
                params.put("email", str_official_email);
                params.put("role", "");
                params.put("interest_in", str_selected_interest_id);
                params.put("assets_purchased", str_year_asset_purchased);
                params.put("assets_description", str_asset_seeking_to_sell);
                params.put("assets_features", str_asset_features);
                params.put("sellingprice", str_asset_selling_leasing_price);
                params.put("sellingtype", str_amount_fixed_for);
                params.put("reasonassets", str_asset_selling_eason);
                params.put("locations", str_final_location_update);
                params.put("industry", str_final_industry_update);
                params.put("year", str_business_established_year);
                params.put("employee", str_no_of_permanent_employees);
                params.put("entitys", str_spn_business_legal_type);
                params.put("business_description", str_business_desc);
                params.put("highlights", str_business_highlights);
                params.put("services", str_business_all_prod_serv);
                params.put("business_facility", str_business_facility_desc);
                params.put("user_currency", str_user_currency);
                params.put("monthly_expected_sales", str_avg_monthly_sales);
                params.put("yearly_expected_sales", str_latest_yearly_sales);
                params.put("monthly_expenses_amount", str_avg_monthly_expenses);
                params.put("ebitda", str_cashflow_profit);
                params.put("physical_assets", str_physical_assests_value);
                params.put("tentative_price", str_tentative_selling_price);
                params.put("reason", str_reason_for_sale);
                params.put("images", listString);
                params.put("user_id", str_user_id);
                params.put("documents", "");
                params.put("documentsname", "");
                params.put("documentstype", "");
                params.put("user_contact", "");
                params.put("user_company", "");
                params.put("yearly_sales_range", "");
                params.put("display", "");

                ////////////////

                System.out.println("user_name" + str_name);
                System.out.println("company_name" + str_company_name);
                System.out.println("mobile_no" + str_mobile);
                System.out.println("email" + str_official_email);
                System.out.println("user_contact" + str_ch_contactdetails);
                System.out.println("user_company" + str_ch_companydetails);
                System.out.println("role" + str_selected_role_id);
                System.out.println("interest_in" + str_selected_interest_id);
                System.out.println("year" + str_business_established_year);
                System.out.println("employee" + str_no_of_permanent_employees);
                System.out.println("entitys" + str_spn_business_legal_type);
                System.out.println("business_description" + str_business_desc);
                System.out.println("highlights" + str_business_highlights);
                System.out.println("services" + str_business_all_prod_serv);
                System.out.println("business_facility" + str_business_facility_desc);
                System.out.println("monthly_expected_sales" + str_avg_monthly_sales);
                System.out.println("monthly_expenses_amount" + str_avg_monthly_expenses);
                System.out.println("yearly_expected_sales" + str_latest_yearly_sales);
                System.out.println("ebitda" + str_cashflow_profit);
                System.out.println("physical_assets" + str_physical_assests_value);
                System.out.println("tentative_price" + str_tentative_selling_price);
                System.out.println("reason" + str_reason_for_sale);
                System.out.println("assets_purchased" + str_year_asset_purchased);
                System.out.println("assets_description" + str_asset_seeking_to_sell);
                System.out.println("assets_features" + str_asset_features);
                System.out.println("sellingprice" + str_asset_selling_leasing_price);
                System.out.println("sellingtype" + str_amount_fixed_for);
                System.out.println("reasonassets" + str_asset_selling_eason);
                System.out.println("locations" + str_final_location_update);
                System.out.println("industry" + str_final_industry_update);
                System.out.println("images" + listString);
                System.out.println("documents" + " documents emptyyyyyyyy");
                System.out.println("documentstype" + "documents typeee emptyyyyyyyy");
                System.out.println("user_currency" + str_user_currency);
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