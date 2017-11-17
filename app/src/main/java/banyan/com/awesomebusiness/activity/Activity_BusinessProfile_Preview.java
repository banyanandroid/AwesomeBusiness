package banyan.com.awesomebusiness.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.MultiAutoCompleteTextView;
import android.widget.Spinner;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.nguyenhoanglam.imagepicker.model.Image;
import com.toptoche.searchablespinnerlibrary.SearchableSpinner;

import java.util.ArrayList;
import java.util.HashMap;

import banyan.com.awesomebusiness.R;
import banyan.com.awesomebusiness.global.SessionManager;
import dmax.dialog.SpotsDialog;

/**
 * Created by Banyan on 16-Nov-17.
 */

public class Activity_BusinessProfile_Preview extends AppCompatActivity {

    private Toolbar mToolbar;
    SpotsDialog dialog;
    public static RequestQueue queue;
    Button btn_add_pic, btn_submit;
    String TAG = "Auto_Res";
    String str_user_currency = "";


    SessionManager session;
    public static String str_user_id, str_user_name, str_user_email, str_user_photoo;
    public static String str_get_user_name, str_get_user_email, str_get_user_mobile, str_get_user_desigination, str_get_user_company;

    // Condition String

    String str_profile_type = "";

    TextView txt_name, txt_mobile, txt_company_name, txt_official_email,
            txt_business_established_year, txt_no_of_permanent_employees, txt_business_des, txt_business_highlights,
            txt_business_all_prod_serv, txt_business_facility_desc, txt_avg_monthly_sales, txt_monthly_expenses, txt_latest_yearly_sales,
            txt_cashflow_profit, txt_physical_assests_value, txt_tentative_selling_price, txt_reason_for_sale;

    TextView txt_companydetails, txt_contatdetails;

    TextView txt_i_am, txt_interested_in;
    TextView txt_amount_fixed_for;
    TextView txt_business_legal_type;

    TextView txt_bus_busineeslist, txt_bus_locationlist, txt_industries_use_asset, txt_asset_loation;

    //Selling or Leasing out business
    TextView txt_year_asset_purchased, txt_asset_seeking_to_sell, txt_asset_features, txt_asset_selling_leasing_price, str_asset_selling_reason;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_business_profile_preview);

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
        str_asset_selling_reason = (TextView) findViewById(R.id.txt_reason_for_sell_asset);

        try {

            SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
            str_user_currency = sharedPreferences.getString("str_selected_currency", "str_selected_currency");

            String str_name = sharedPreferences.getString("prev_str_name", "prev_str_name");
            String str_company_name = sharedPreferences.getString("prev_str_company_name", "prev_str_company_name");
            String str_mobile = sharedPreferences.getString("prev_str_mobile", "prev_str_mobile");
            String str_official_email = sharedPreferences.getString("prev_str_official_email", "prev_str_official_email");
            String str_ch_companydetails = sharedPreferences.getString("prev_str_ch_companydetails", "prev_str_ch_companydetails");
            String str_ch_contactdetails = sharedPreferences.getString("prev_str_ch_contactdetails", "prev_str_ch_contactdetails");
            String str_selected_role_name = sharedPreferences.getString("prev_str_selected_role_name", "prev_str_selected_role_name");
            String str_selected_role_id = sharedPreferences.getString("prev_str_selected_role_id", "prev_str_selected_role_id");
            String str_selected_interest_name = sharedPreferences.getString("prev_str_selected_interest_name", "prev_str_selected_interest_name");
            String str_selected_interest_id = sharedPreferences.getString("prev_str_selected_interest_id", "prev_str_selected_interest_id");
            String str_final_industry_update = sharedPreferences.getString("prev_str_final_industry_update", "prev_str_final_industry_update");
            String str_final_location_update = sharedPreferences.getString("prev_str_final_location_update", "prev_str_final_location_update");
            String str_business_established_year = sharedPreferences.getString("prev_str_business_established_year", "prev_str_business_established_year");
            String str_no_of_permanent_employees = sharedPreferences.getString("prev_str_no_of_permanent_employees", "prev_str_no_of_permanent_employees");
            String str_spn_business_legal_type = sharedPreferences.getString("prev_str_spn_business_legal_type", "prev_str_spn_business_legal_type");
            String str_business_desc = sharedPreferences.getString("prev_str_business_desc", "prev_str_business_desc");
            String str_business_highlights = sharedPreferences.getString("prev_str_business_highlights", "prev_str_business_highlights");
            String str_business_all_prod_serv = sharedPreferences.getString("prev_str_business_all_prod_serv", "prev_str_business_all_prod_serv");
            String str_business_facility_desc = sharedPreferences.getString("prev_str_business_facility_desc", "prev_str_business_facility_desc");
            String str_avg_monthly_sales = sharedPreferences.getString("prev_str_avg_monthly_sales", "prev_str_avg_monthly_sales");
            String str_avg_monthly_expenses = sharedPreferences.getString("prev_str_avg_monthly_expenses", "prev_str_avg_monthly_expenses");
            String str_latest_yearly_sales = sharedPreferences.getString("prev_str_latest_yearly_sales", "prev_str_latest_yearly_sales");
            String str_cashflow_profit = sharedPreferences.getString("prev_str_cashflow_profit", "prev_str_cashflow_profit");
            String str_physical_assests_value = sharedPreferences.getString("prev_str_physical_assests_value", "prev_str_physical_assests_value");
            String str_tentative_selling_price = sharedPreferences.getString("prev_str_tentative_selling_price", "prev_str_tentative_selling_price");
            String str_reason_for_sale = sharedPreferences.getString("prev_str_reason_for_sale", "prev_str_reason_for_sale");
            String str_year_asset_purchased = sharedPreferences.getString("prev_str_year_asset_purchased", "prev_str_year_asset_purchased");
            String str_asset_seeking_to_sell = sharedPreferences.getString("prev_str_asset_seeking_to_sell", "prev_str_asset_seeking_to_sell");
            String str_asset_industry = sharedPreferences.getString("prev_str_asset_industry_update", "prev_str_asset_industry_update");
            String str_asset_location = sharedPreferences.getString("prev_str_asset_location_update", "prev_str_asset_location_update");
            String str_asset_features = sharedPreferences.getString("prev_str_asset_features", "prev_str_asset_features");
            String str_asset_selling_leasing_price = sharedPreferences.getString("prev_str_asset_selling_leasing_price", "prev_str_asset_selling_leasing_price");
            String str_asset_selling_eason = sharedPreferences.getString("prev_str_asset_selling_eason", "prev_str_asset_selling_eason");
            String str_amount_fixed_for = sharedPreferences.getString("prev_str_amount_fixed_for", "prev_str_amount_fixed_for");
            String listString = sharedPreferences.getString("prev_str_image", "prev_str_image");


            txt_name.setText(str_name);
            txt_mobile.setText(str_mobile);
            txt_company_name.setText(str_company_name);
            txt_official_email.setText(str_official_email);
            txt_i_am.setText(str_selected_role_name);
            txt_interested_in.setText(str_selected_interest_name);
            txt_business_established_year.setText(str_business_established_year);
            txt_bus_busineeslist.setText(str_final_industry_update);
            txt_bus_locationlist.setText(str_final_location_update);
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
            txt_industries_use_asset.setText(str_asset_industry);
            txt_asset_loation.setText(str_asset_location);
            txt_asset_seeking_to_sell.setText(str_asset_seeking_to_sell);
            txt_asset_features.setText(str_asset_features);
            txt_asset_selling_leasing_price.setText(str_asset_selling_leasing_price);
            txt_amount_fixed_for.setText(str_amount_fixed_for);
            str_asset_selling_reason.setText(str_asset_selling_eason);

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


        } catch (Exception e) {


        }


    }
}