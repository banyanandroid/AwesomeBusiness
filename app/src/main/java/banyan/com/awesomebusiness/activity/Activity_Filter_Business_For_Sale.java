package banyan.com.awesomebusiness.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.MultiAutoCompleteTextView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.crystal.crystalrangeseekbar.interfaces.OnRangeSeekbarChangeListener;
import com.crystal.crystalrangeseekbar.interfaces.OnRangeSeekbarFinalValueListener;
import com.crystal.crystalrangeseekbar.widgets.CrystalRangeSeekbar;
import com.sdsmdg.tastytoast.TastyToast;
import com.toptoche.searchablespinnerlibrary.SearchableSpinner;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import banyan.com.awesomebusiness.R;
import banyan.com.awesomebusiness.global.AppConfig;
import dmax.dialog.SpotsDialog;

/**
 * Created by chandru on 21-Jul-17.
 */

public class Activity_Filter_Business_For_Sale extends AppCompatActivity {

    private Toolbar mToolbar;
    ProgressDialog pDialog;
    SpotsDialog dialog;
    public static RequestQueue queue;
    String TAG = "reg";
    TextView t1;
    String str_user_currency = "";

    Button btn_done, btn_clear_all;

    public static final String TAG_SECTOR_NAME = "name";
    public static final String TAG_SECTOR_KEY = "key";
    public static final String TAG_SECTOR_TYPE = "type";

    public static final String TAG_LOC_PLACE = "place";
    public static final String TAG_LOC_KEY = "key";
    public static final String TAG_LOC_TYPE = "type";

    public static final String TAG_BUSINESS_MONTH_SALES_MIN = "business_month_sales_min";
    public static final String TAG_BUSINESS_MONTH_SALES_MAX = "business_month_sales_max";
    public static final String TAG_BUSINESS_TENTATIVE_PRICE_MIN = "business_tentative_price_min";
    public static final String TAG_BUSINESS_TENTATIVE_PRICE_MAX = "business_tentative_price_max";
    public static final String TAG_BUSINESS_YEARLY_SALES_MIN = "business_yearly_sales_min";
    public static final String TAG_BUSINESS_YEARLY_SALES_MAX = "business_yearly_sales_max";
    public static final String TAG_MIN_USER_ESTABLISHED = "min_user_established";
    public static final String TAG_MAX_USER_ESTABLISHED = "max_user_established";
    public static final String TAG_MIN_ASSETS_PURCHASED = "min_assets_purchased";
    public static final String TAG_MAX_ASSETS_PURCHASED = "max_assets_purchased";
    public static final String TAG_MIN_SELL_LEASE_PRICE = "min_sell_lease_price";
    public static final String TAG_MAX_SELL_LEASE_PRICE = "max_sell_lease_price";
    public static final String TAG_BUSINESS_MONTH_EXPENSE_MIN = "business_month_expense_min";
    public static final String TAG_BUSINESS_MONTH_EXPENSE_MAX = "business_month_expense_max";
    public static final String TAG_BUSINESS_MONTH_CASHFLOW_MIN = "business_month_cashflow_min";
    public static final String TAG_BUSINESS_MONTH_CASHFLOW_MAX = "business_month_cashflow_max";
    public static final String TAG_INVESTOR_CURRENCY_FROM = "investor_currency_from";
    public static final String TAG_INVESTOR_CURRENCY_TO = "investor_currency_to";
    public static final String TAG_INVESTOR_ROI_MIN = "investor_roi_min";
    public static final String TAG_INVESTOR_ROI_MAX = "investor_roi_max";
    public static final String TAG_FRANCHISE_MIN_INVESTMENT = "franchise_min_investment";
    public static final String TAG_FRANCHISE_MAX_INVESTMENT = "franchise_max_investment";
    public static final String TAG_FRANCHISE_MIN_ESTABLISHED = "franchise_min_established";
    public static final String TAG_FRANCHISE_MAX_ESTABLISHED = "franchise_max_established";
    public static final String TAG_FRANCHISE_MIN_REVENUES = "franchise_min_revenues";
    public static final String TAG_FRANCHISE_MAX_REVENUES = "franchise_max_revenues";
    public static final String TAG_ADVISOR_MIN_INVESTMENT = "advisor_min_investment";
    public static final String TAG_ADVISOR_MAX_INVESTMENT = "advisor_max_investment";


    ArrayList<String> Arraylist_sector_name = null;
    ArrayList<String> Arraylist_sector_key = null;
    ArrayList<String> Arraylist_sector_type = null;

    /*Multi Select*/
    ArrayList<String> Arraylist_selected_sectorkey = null;
    ArrayList<String> Arraylist_selected_location = null;

    ArrayList<String> Arraylist_location_place = null;
    ArrayList<String> Arraylist_location_key = null;
    ArrayList<String> Arraylist_location_type = null;

    /* Arralist fetched Location list */
    ArrayList<String> Arraylist_fetched_location = null;
    ArrayList<String> Arraylist_selected_final_location = null;

    /* Arralist fetched indestries list */
    ArrayList<String> Arraylist_fetched_industries = null;
    ArrayList<String> Arraylist_selected_final_industry = null;

    private ArrayAdapter<String> adapter_transaction;
    private ArrayAdapter<String> adapter_investor_buyer_type;
    private ArrayAdapter<String> adapter_interested;

    String str_main_filter = "";

    String str_final_business_sector, str_final_Business_Location = "";
    String str_final_industry_update, str_final_location_update, str_Investor_location, str_franchise_headquaters_location = "";

    MultiAutoCompleteTextView auto_business_sectorlist, auto_bus_locationlist;

    LinearLayout LL_Slider_Selling_Price, LL_Slider_Yearly_Revenue,
            LL_Slider_Monthly_Cashflow, LL_Slider_Established, LL_Slider_Investment_size,
            LL_Slider_Return_Of_Investment, LL_Slider_Fran_Investment_size, LL_Slider_Fran_Established,
            LL_Slider_Expected_Return, LL_Advisor_Investment_Size;

    CrystalRangeSeekbar Seekbar_Selling_Price, Seekbar_Yearly_Revenue,
            Seekbar_Monthly_Cashflow, Seekbar_Established, Seekbar_Investment_size,
            Seekbar_Return_Of_Investment, Seekbar_Fran_Investment_size, Seekbar_Fran_Established,
            Seekbar_Expected_Return, Seekbar_Advisor_Investment;

    TextView txt_selling_price_minimum, txt_yearly_revenue_minimum,
            txt_monthly_cashflow_minimum, txt_established_minimum, txt_investment_size_minimum,
            txt_return_of_investment_minimum, txt_fran_investment_size_minimum, txt_fran_established_minimum,
            txt_expected_return_minimum, txt_adv_inves_size_minimum;

    TextView txt_selling_price_maximum, txt_yearly_revenue_maximum,
            txt_monthly_cashflow_maximum, txt_established_maximum, txt_investment_size_maximum,
            txt_return_of_investment_maximum, txt_fran_investment_size_maximum, txt_fran_established_maximum,
            txt_expected_return_maximum, txt_adv_inves_size_maximum;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter_menu);

        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("Filters");
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        mToolbar.setNavigationIcon(getResources().getDrawable(R.drawable.ic_action_close));
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(getApplicationContext(), MainActivity.class);
                overridePendingTransition(R.anim.pull_in_right, R.anim.pull_in_left);
                startActivity(i);
                finish();
            }
        });

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        // str_main_filter = sharedPreferences.getString("profile_type", "profile_type");
        str_user_currency = sharedPreferences.getString("str_selected_currency", "str_selected_currency");
        str_main_filter = sharedPreferences.getString("profile_type_from", "profile_type_from");

        ///////////////////////TEMP FOR TESTING PURPOSE////////////////////////////////////////////////////////////////
        //str_main_filter = "Franchises";

        System.out.println(" FILTER TYPE:::::::  " + str_main_filter);
        System.out.println(" USER CURRENCY TYPE:::::::  " + str_user_currency);

        txt_selling_price_minimum = (TextView) findViewById(R.id.activity_filter_selling_price_minValue);
        txt_selling_price_maximum = (TextView) findViewById(R.id.activity_filter_selling_price_maxValue);
        txt_yearly_revenue_minimum = (TextView) findViewById(R.id.activity_filter_yr_rev_minValue);
        txt_yearly_revenue_maximum = (TextView) findViewById(R.id.activity_filter_yr_rev_maxValue);

        txt_monthly_cashflow_minimum = (TextView) findViewById(R.id.activity_filter_monthly_cash_minValue);
        txt_monthly_cashflow_maximum = (TextView) findViewById(R.id.activity_filter_monthly_cash_maxValue);
        txt_established_minimum = (TextView) findViewById(R.id.activity_filter_established_minValue);
        txt_established_maximum = (TextView) findViewById(R.id.activity_filter_established_maxValue);
        txt_investment_size_minimum = (TextView) findViewById(R.id.activity_filter_inv_size_minValue);
        txt_investment_size_maximum = (TextView) findViewById(R.id.activity_filter_inv_size_maxValue);
        txt_return_of_investment_minimum = (TextView) findViewById(R.id.activity_filter_roi_minValue);
        txt_return_of_investment_maximum = (TextView) findViewById(R.id.activity_filter_roi_maxValue);
        txt_fran_investment_size_minimum = (TextView) findViewById(R.id.activity_filter_fran_inv_size_minValue);
        txt_fran_investment_size_maximum = (TextView) findViewById(R.id.activity_filter_fran_inv_size_maxValue);
        txt_fran_established_minimum = (TextView) findViewById(R.id.activity_filter_fran_established_minValue);
        txt_fran_established_maximum = (TextView) findViewById(R.id.activity_filter_fran_established_maxValue);
        txt_expected_return_minimum = (TextView) findViewById(R.id.activity_filter_expec_return_minValue);
        txt_expected_return_maximum = (TextView) findViewById(R.id.activity_filter_expec_return_maxValue);
        txt_adv_inves_size_minimum = (TextView) findViewById(R.id.activity_filter_adv_business_size_minValue);
        txt_adv_inves_size_maximum = (TextView) findViewById(R.id.activity_filter_adv_business_size_maxValue);

        LL_Slider_Selling_Price = (LinearLayout) findViewById(R.id.layout_slider_selling_price);
        LL_Slider_Yearly_Revenue = (LinearLayout) findViewById(R.id.layout_slider_yr_rev);
        LL_Slider_Monthly_Cashflow = (LinearLayout) findViewById(R.id.layout_slider_monthly_cashflow);

        LL_Slider_Established = (LinearLayout) findViewById(R.id.layout_slider_established);
        LL_Slider_Investment_size = (LinearLayout) findViewById(R.id.layout_slider_investment_size);
        LL_Slider_Return_Of_Investment = (LinearLayout) findViewById(R.id.layout_slider_roi);
        LL_Slider_Fran_Investment_size = (LinearLayout) findViewById(R.id.layout_slider_fran_inv_size);
        LL_Slider_Fran_Established = (LinearLayout) findViewById(R.id.layout_slider_fran_established);
        LL_Slider_Expected_Return = (LinearLayout) findViewById(R.id.layout_slider_expec_return);
        LL_Advisor_Investment_Size = (LinearLayout) findViewById(R.id.layout_slider_adv_business_size);


        LL_Slider_Selling_Price.setVisibility(View.GONE);
        LL_Slider_Yearly_Revenue.setVisibility(View.GONE);
        LL_Slider_Monthly_Cashflow.setVisibility(View.GONE);
        LL_Slider_Established.setVisibility(View.GONE);
        LL_Slider_Investment_size.setVisibility(View.GONE);
        LL_Slider_Return_Of_Investment.setVisibility(View.GONE);
        LL_Slider_Fran_Investment_size.setVisibility(View.GONE);
        LL_Slider_Fran_Established.setVisibility(View.GONE);
        LL_Slider_Expected_Return.setVisibility(View.GONE);
        LL_Advisor_Investment_Size.setVisibility(View.GONE);

        auto_bus_locationlist = (MultiAutoCompleteTextView) findViewById(R.id.filter_multi_business_locations);
        auto_business_sectorlist = (MultiAutoCompleteTextView) findViewById(R.id.filter_multi_business_industries);

        btn_clear_all = (Button) findViewById(R.id.btn_filter_clear);
        btn_done = (Button) findViewById(R.id.btn_filter_done);

        Seekbar_Selling_Price = (CrystalRangeSeekbar) findViewById(R.id.activity_filter_selling_price_slider);
        Seekbar_Yearly_Revenue = (CrystalRangeSeekbar) findViewById(R.id.activity_filter_yearly_revenue_slider);
        Seekbar_Monthly_Cashflow = (CrystalRangeSeekbar) findViewById(R.id.activity_filter_monthly_cashflow_slider);
        Seekbar_Established = (CrystalRangeSeekbar) findViewById(R.id.activity_filter_established_range_slider);
        Seekbar_Investment_size = (CrystalRangeSeekbar) findViewById(R.id.activity_filter_inv_size_range_slider);
        Seekbar_Return_Of_Investment = (CrystalRangeSeekbar) findViewById(R.id.activity_filter_roi_range_slider);
        Seekbar_Fran_Investment_size = (CrystalRangeSeekbar) findViewById(R.id.activity_filter_fran_inv_size_range_slider);
        Seekbar_Fran_Established = (CrystalRangeSeekbar) findViewById(R.id.activity_filter_fran_established_range_slider);
        Seekbar_Expected_Return = (CrystalRangeSeekbar) findViewById(R.id.activity_filter_expec_return_range_slider);
        Seekbar_Advisor_Investment = (CrystalRangeSeekbar) findViewById(R.id.activity_filter_adv_business_size_slider);


        Arraylist_sector_name = new ArrayList<String>();
        Arraylist_sector_key = new ArrayList<String>();
        Arraylist_sector_type = new ArrayList<String>();

        Arraylist_selected_sectorkey = new ArrayList<String>();
        Arraylist_selected_location = new ArrayList<String>();

        Arraylist_location_place = new ArrayList<String>();
        Arraylist_location_key = new ArrayList<String>();
        Arraylist_location_type = new ArrayList<String>();

        Arraylist_fetched_location = new ArrayList<String>();
        Arraylist_selected_final_location = new ArrayList<String>();

        Arraylist_fetched_industries = new ArrayList<String>();
        Arraylist_selected_final_industry = new ArrayList<String>();


        try {
            if (str_main_filter.equals("Business For sale")) {

                LL_Slider_Selling_Price.setVisibility(View.VISIBLE);
                LL_Slider_Yearly_Revenue.setVisibility(View.VISIBLE);
                LL_Slider_Monthly_Cashflow.setVisibility(View.VISIBLE);
                LL_Slider_Established.setVisibility(View.VISIBLE);

                LL_Slider_Investment_size.setVisibility(View.GONE);
                LL_Slider_Return_Of_Investment.setVisibility(View.GONE);
                LL_Slider_Fran_Investment_size.setVisibility(View.GONE);
                LL_Slider_Fran_Established.setVisibility(View.GONE);
                LL_Slider_Expected_Return.setVisibility(View.GONE);
                LL_Advisor_Investment_Size.setVisibility(View.GONE);


            } else if (str_main_filter.equals("Investment Oppourtinites")) {

                LL_Slider_Selling_Price.setVisibility(View.GONE);
                LL_Slider_Yearly_Revenue.setVisibility(View.GONE);
                LL_Slider_Monthly_Cashflow.setVisibility(View.GONE);
                LL_Slider_Established.setVisibility(View.GONE);

                LL_Slider_Investment_size.setVisibility(View.VISIBLE);
                LL_Slider_Return_Of_Investment.setVisibility(View.VISIBLE);

                LL_Slider_Fran_Investment_size.setVisibility(View.GONE);
                LL_Slider_Fran_Established.setVisibility(View.GONE);
                LL_Slider_Expected_Return.setVisibility(View.GONE);
                LL_Advisor_Investment_Size.setVisibility(View.GONE);

            } else if (str_main_filter.equals("Franchise Oppourtinites")) {

                LL_Slider_Selling_Price.setVisibility(View.GONE);
                LL_Slider_Yearly_Revenue.setVisibility(View.GONE);
                LL_Slider_Monthly_Cashflow.setVisibility(View.GONE);
                LL_Slider_Established.setVisibility(View.GONE);
                LL_Slider_Investment_size.setVisibility(View.GONE);
                LL_Slider_Return_Of_Investment.setVisibility(View.GONE);

                LL_Slider_Fran_Investment_size.setVisibility(View.VISIBLE);
                LL_Slider_Fran_Established.setVisibility(View.VISIBLE);
                LL_Slider_Expected_Return.setVisibility(View.VISIBLE);

                LL_Advisor_Investment_Size.setVisibility(View.GONE);
            } else if (str_main_filter.equals("Advisors")) {

                LL_Slider_Selling_Price.setVisibility(View.GONE);
                LL_Slider_Yearly_Revenue.setVisibility(View.GONE);
                LL_Slider_Monthly_Cashflow.setVisibility(View.GONE);
                LL_Slider_Established.setVisibility(View.GONE);
                LL_Slider_Investment_size.setVisibility(View.GONE);
                LL_Slider_Return_Of_Investment.setVisibility(View.GONE);
                LL_Slider_Fran_Investment_size.setVisibility(View.GONE);
                LL_Slider_Fran_Established.setVisibility(View.GONE);
                LL_Slider_Expected_Return.setVisibility(View.GONE);

                LL_Advisor_Investment_Size.setVisibility(View.VISIBLE);
            }

        } catch (Exception e) {

        }

        btn_clear_all.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


            }
        });


        btn_done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                System.out.println("SELECTED FILTER TYPE:-" + str_main_filter);

                /********************************************************
                 * Get Multiple Location Details From Searchable Spinner
                 * ******************************************************/
                String[] str_valid_location = auto_bus_locationlist.getText().toString().split(", ");

                if (!str_valid_location.equals("") && str_valid_location == null) {

                    String[] str_location = auto_bus_locationlist.getText().toString().split(", ");
                    Arraylist_fetched_location.clear();
                    for (int i = 0; i < str_location.length; i++) {
                        Arraylist_fetched_location.add(str_location[i]);
                    }
                    System.out.println("array : " + Arraylist_fetched_location);
                    Arraylist_selected_final_location.clear();
                    for (int i = 0; i < Arraylist_fetched_location.size(); i++) {

                        String get_location = Arraylist_fetched_location.get(i);
                        get_location = get_location.trim();
                        System.out.println("get_location : " + get_location);
                        int location_position = Arraylist_location_place.indexOf(get_location);
                        String select_location_id = Arraylist_location_key.get(location_position);
                        String select_location_type = Arraylist_location_type.get(location_position);
                        String location = select_location_id + "-" + select_location_type;
                        Arraylist_selected_final_location.add(location);
                        str_final_location_update = TextUtils.join(", ", Arraylist_selected_final_location);

                    }

                } else {

                }
                System.out.println("FINAL SELECTED LOCATION :: " + str_final_location_update);

                /********************************************************
                 * Get Multiple Industry Sector Details From Searchable Spinner
                 * ******************************************************/
                String[] str_valid_industries = auto_business_sectorlist.getText().toString().split(", ");

                if (!str_valid_industries.equals("") && str_valid_industries == null) {

                    String[] str_asset_industries = auto_business_sectorlist.getText().toString().split(", ");

                    Arraylist_fetched_industries.clear();
                    for (int i = 0; i < str_asset_industries.length; i++) {
                        Arraylist_fetched_industries.add(str_asset_industries[i]);
                    }
                    System.out.println("array : " + Arraylist_fetched_industries);

                    Arraylist_selected_final_industry.clear();
                    for (int i = 0; i < Arraylist_fetched_industries.size(); i++) {

                        String get_indestry = Arraylist_fetched_industries.get(i);
                        get_indestry = get_indestry.trim();
                        System.out.println("get_indestry : " + get_indestry);
                        int indus_position = Arraylist_sector_name.indexOf(get_indestry);
                        String select_sect_id = Arraylist_sector_key.get(indus_position);
                        String select_sect_type = Arraylist_sector_type.get(indus_position);

                        String sector = select_sect_id + "-" + select_sect_type;
                        Arraylist_selected_final_industry.add(sector);

                        str_final_industry_update = TextUtils.join(", ", Arraylist_selected_final_industry);

                    }
                    System.out.println("FINAL SELECTED INDUSTRY :: " + str_final_industry_update);


                }

                //IF STATEMENT TO POST VALUES ACCORING TO MAIN FILTER TYPE
                if (str_main_filter.equals("Business For sale")) {

                    //SEEKBAR VALUES TO STRING
                    String str_selling_price_minimum = txt_selling_price_minimum.getText().toString();
                    String str_selling_price_maximum = txt_selling_price_maximum.getText().toString();

                    String str_yearly_revenue_minimum = txt_yearly_revenue_minimum.getText().toString();
                    String str_yearly_revenue_maximum = txt_yearly_revenue_maximum.getText().toString();

                    String str_monthly_cashflow_minimum = txt_monthly_cashflow_minimum.getText().toString();
                    String str_monthly_cashflow_maximum = txt_monthly_cashflow_maximum.getText().toString();

                    String str_established_minimum = txt_established_minimum.getText().toString();
                    String str_established_maximum = txt_established_maximum.getText().toString();

                    //PUTTING THE FILTER VALUES IN SHARED PREFERENCES
                    SharedPreferences sharedPreferences_filter = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                    SharedPreferences.Editor editor_filter = sharedPreferences_filter.edit();
                    //FILTER TYPE AND MULTISELECT LOCATION AND INDUSTRY VALUES
                    editor_filter.putString("str_main_filter_type", str_main_filter);
                    editor_filter.putString("str_filter_location", str_final_location_update);
                    editor_filter.putString("str_filter_industry", str_final_industry_update);
                    //SEEKBAR VALUES
                    editor_filter.putString("str_selling_price_minimum", str_selling_price_minimum);
                    editor_filter.putString("str_selling_price_maximum", str_selling_price_maximum);
                    editor_filter.putString("str_yearly_revenue_minimum", str_yearly_revenue_minimum);
                    editor_filter.putString("str_yearly_revenue_maximum", str_yearly_revenue_maximum);
                    editor_filter.putString("str_monthly_cashflow_minimum", str_monthly_cashflow_minimum);
                    editor_filter.putString("str_monthly_cashflow_maximum", str_monthly_cashflow_maximum);
                    editor_filter.putString("str_established_minimum", str_established_minimum);
                    editor_filter.putString("str_established_maximum", str_established_maximum);

                    editor_filter.commit();

                    Intent i = new Intent(Activity_Filter_Business_For_Sale.this, MainActivity.class);
                    i.putExtra("type", str_main_filter);
                    startActivity(i);
                    overridePendingTransition(R.anim.pull_in_right, R.anim.pull_in_left);
                    finish();

                } else if (str_main_filter.equals("Investment Oppourtinites")) {

                    //SEEKBAR VALUES TO STRING
                    String str_investor_investment_size_minimum = txt_investment_size_minimum.getText().toString();
                    String str_investor_investment_size_maximum = txt_investment_size_maximum.getText().toString();

                    String str_return_of_investment_minimum = txt_return_of_investment_minimum.getText().toString();
                    String str_return_of_investment_maximum = txt_return_of_investment_maximum.getText().toString();

                    //PUTTING THE FILTER VALUES IN SHARED PREFERENCES
                    SharedPreferences sharedPreferences_filter = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                    SharedPreferences.Editor editor_filter = sharedPreferences_filter.edit();
                    //FILTER TYPE AND MULTISELECT LOCATION AND INDUSTRY VALUES
                    editor_filter.putString("str_main_filter_type", str_main_filter);
                    editor_filter.putString("str_filter_location", str_final_location_update);
                    editor_filter.putString("str_filter_industry", str_final_industry_update);
                    //SEEKBAR VALUES
                    editor_filter.putString("str_investor_investment_size_minimum", str_investor_investment_size_minimum);
                    editor_filter.putString("str_investor_investment_size_maximum", str_investor_investment_size_maximum);
                    editor_filter.putString("str_return_of_investment_minimum", str_return_of_investment_minimum);
                    editor_filter.putString("str_return_of_investment_maximum", str_return_of_investment_maximum);

                    editor_filter.commit();

                    Intent i = new Intent(Activity_Filter_Business_For_Sale.this, MainActivity.class);
                    i.putExtra("type", str_main_filter);
                    startActivity(i);
                    overridePendingTransition(R.anim.pull_in_right, R.anim.pull_in_left);
                    finish();


                } else if (str_main_filter.equals("Franchise Oppourtinites")) {

                    System.out.println(" Button Clicked & Came inside Franchises ");

                    //SEEKBAR VALUES TO STRING
                    String str_franchise_investment_size_minimum = txt_fran_investment_size_minimum.getText().toString();
                    String str_franchise_investment_size_maximum = txt_fran_investment_size_maximum.getText().toString();

                    String str_franchise_established_minimum = txt_fran_established_minimum.getText().toString();
                    String str_franchise_established_maximum = txt_fran_established_maximum.getText().toString();

                    String str_franchise_expected_return_minimum = txt_expected_return_minimum.getText().toString();
                    String str_franchise_expected_return_maximum = txt_expected_return_maximum.getText().toString();

                    //PUTTING THE FILTER VALUES IN SHARED PREFERENCES
                    SharedPreferences sharedPreferences_filter = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                    SharedPreferences.Editor editor_filter = sharedPreferences_filter.edit();
                    //FILTER TYPE AND MULTISELECT LOCATION AND INDUSTRY VALUES
                    editor_filter.putString("str_main_filter_type", str_main_filter);
                    editor_filter.putString("str_filter_location", str_final_location_update);
                    editor_filter.putString("str_filter_industry", str_final_industry_update);
                    //SEEKBAR VALUES
                    editor_filter.putString("str_franchise_investment_size_minimum", str_franchise_investment_size_minimum);
                    editor_filter.putString("str_franchise_investment_size_maximum", str_franchise_investment_size_maximum);
                    editor_filter.putString("str_franchise_established_minimum", str_franchise_established_minimum);
                    editor_filter.putString("str_franchise_established_maximum", str_franchise_established_maximum);
                    editor_filter.putString("str_franchise_expected_return_minimum", str_franchise_expected_return_minimum);
                    editor_filter.putString("str_franchise_expected_return_maximum", str_franchise_expected_return_maximum);

                    editor_filter.commit();

                    Intent i = new Intent(Activity_Filter_Business_For_Sale.this, MainActivity.class);
                    i.putExtra("type", str_main_filter);
                    startActivity(i);
                    overridePendingTransition(R.anim.pull_in_right, R.anim.pull_in_left);
                    finish();

                }

            }
        });

        try {
            dialog = new SpotsDialog(Activity_Filter_Business_For_Sale.this);
            dialog.show();
            queue = Volley.newRequestQueue(Activity_Filter_Business_For_Sale.this);
            Get_Sector_List();
        } catch (Exception e) {

        }
    }

    /**********************************
     * To get  Business sector List
     *********************************/

    public void Get_Sector_List() {
        String tag_json_obj = "json_obj_req";
        System.out.println("CAME 1");
        StringRequest request = new StringRequest(Request.Method.POST,
                AppConfig.url_business, new com.android.volley.Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d(TAG, response.toString());
                try {
                    JSONObject obj = new JSONObject(response);
                    int success = obj.getInt("status");

                    if (success == 1) {

                        JSONArray arr;

                        arr = obj.getJSONArray("datas");

                        for (int i = 0; arr.length() > i; i++) {
                            JSONObject obj1 = arr.getJSONObject(i);

                            String sector_name = obj1.optString(TAG_SECTOR_NAME);
                            String sector_key = obj1.optString(TAG_SECTOR_KEY);
                            String sector_type = obj1.optString(TAG_SECTOR_TYPE);

                            Arraylist_sector_name.add(sector_name);
                            Arraylist_sector_key.add(sector_key);
                            Arraylist_sector_type.add(sector_type);
                        }
                        try {
                            System.out.println("ARAAAAY :: " + Arraylist_sector_name);

                            ArrayAdapter<String> adapter_sector1 = new ArrayAdapter<String>(Activity_Filter_Business_For_Sale.this,
                                    android.R.layout.simple_list_item_1, Arraylist_sector_name);
                            auto_business_sectorlist.setAdapter(adapter_sector1);
                            auto_business_sectorlist
                                    .setTokenizer(new MultiAutoCompleteTextView.CommaTokenizer());
                            auto_business_sectorlist.setThreshold(1);

                        } catch (Exception e) {

                        }

                        try {
                            queue = Volley.newRequestQueue(getApplicationContext());
                            Get_Business_Location();
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
        }, new com.android.volley.Response.ErrorListener() {

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

    /************************************
     * To get  Business Location List
     ***********************************/

    public void Get_Business_Location() {
        String tag_json_obj = "json_obj_req";
        System.out.println("CAME 1");
        StringRequest request = new StringRequest(Request.Method.POST,
                AppConfig.url_business_location, new com.android.volley.Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d(TAG, response.toString());
                try {
                    JSONObject obj = new JSONObject(response);

                    System.out.println("CAME 2" + response);

                    int success = obj.getInt("status");

                    if (success == 1) {

                        JSONArray arr;

                        arr = obj.getJSONArray("datas");

                        for (int i = 0; arr.length() > i; i++) {
                            JSONObject obj1 = arr.getJSONObject(i);

                            String location_place = obj1.optString(TAG_LOC_PLACE);
                            String location_key = obj1.optString(TAG_LOC_KEY);
                            String location_type = obj1.optString(TAG_LOC_TYPE);

                            Arraylist_location_place.add(location_place);
                            Arraylist_location_key.add(location_key);
                            Arraylist_location_type.add(location_type);
                        }
                        try {
                            System.out.println("ARAAAAY :: " + Arraylist_location_place);

                            ArrayAdapter<String> adapter_process = new ArrayAdapter<String>(Activity_Filter_Business_For_Sale.this,
                                    android.R.layout.simple_list_item_1, Arraylist_location_place);

                            auto_bus_locationlist.setAdapter(adapter_process);
                            auto_bus_locationlist
                                    .setTokenizer(new MultiAutoCompleteTextView.CommaTokenizer());
                            auto_bus_locationlist.setThreshold(1);


                        } catch (Exception e) {

                        }

                        try {
                            queue = Volley.newRequestQueue(getApplicationContext());
                            Get_Filter_Seekbar_Values();
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
        }, new com.android.volley.Response.ErrorListener() {

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

    /*********************************
     * To get  Filter Seekbar Values
     *********************************/

    public void Get_Filter_Seekbar_Values() {
        String tag_json_obj = "json_obj_req";
        System.out.println("CAME 11111");
        StringRequest request = new StringRequest(Request.Method.POST,
                AppConfig.url_filter_seekbar_values, new com.android.volley.Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d(TAG, response.toString());
                try {
                    JSONObject obj = new JSONObject(response);
                    System.out.println("CAME 22222");
                    int success = obj.getInt("status");

                    if (success == 1) {

                        JSONObject obj1 = obj.getJSONObject("data");


                        String business_month_sales_min = obj1.getString(TAG_BUSINESS_MONTH_SALES_MIN);
                        String business_month_sales_max = obj1.getString(TAG_BUSINESS_MONTH_SALES_MAX);
                        String business_tentative_price_min = obj1.getString(TAG_BUSINESS_TENTATIVE_PRICE_MIN);
                        String business_tentative_price_max = obj1.getString(TAG_BUSINESS_TENTATIVE_PRICE_MAX);
                        String business_yearly_sales_min = obj1.getString(TAG_BUSINESS_YEARLY_SALES_MIN);
                        String business_yearly_sales_max = obj1.getString(TAG_BUSINESS_YEARLY_SALES_MAX);
                        String min_user_established = obj1.getString(TAG_MIN_USER_ESTABLISHED);
                        String max_user_established = obj1.getString(TAG_MAX_USER_ESTABLISHED);
                        String min_assets_purchased = obj1.getString(TAG_MIN_ASSETS_PURCHASED);
                        String max_assets_purchased = obj1.getString(TAG_MAX_ASSETS_PURCHASED);
                        String min_sell_lease_price = obj1.getString(TAG_MIN_SELL_LEASE_PRICE);
                        String max_sell_lease_price = obj1.getString(TAG_MAX_SELL_LEASE_PRICE);
                        String business_month_expense_min = obj1.getString(TAG_BUSINESS_MONTH_EXPENSE_MIN);
                        String business_month_expense_max = obj1.getString(TAG_BUSINESS_MONTH_EXPENSE_MAX);
                        String business_month_cashflow_min = obj1.getString(TAG_BUSINESS_MONTH_CASHFLOW_MIN);
                        String business_month_cashflow_max = obj1.getString(TAG_BUSINESS_MONTH_CASHFLOW_MAX);
                        String investor_currency_from = obj1.getString(TAG_INVESTOR_CURRENCY_FROM);
                        String investor_currency_to = obj1.getString(TAG_INVESTOR_CURRENCY_TO);
                        String investor_roi_min = obj1.getString(TAG_INVESTOR_ROI_MIN);
                        String investor_roi_max = obj1.getString(TAG_INVESTOR_ROI_MAX);
                        String franchise_min_investment = obj1.getString(TAG_FRANCHISE_MIN_INVESTMENT);
                        String franchise_max_investment = obj1.getString(TAG_FRANCHISE_MAX_INVESTMENT);
                        String franchise_min_established = obj1.getString(TAG_FRANCHISE_MIN_ESTABLISHED);
                        String franchise_max_established = obj1.getString(TAG_FRANCHISE_MAX_ESTABLISHED);
                        String franchise_min_revenues = obj1.getString(TAG_FRANCHISE_MIN_REVENUES);
                        String franchise_max_revenues = obj1.getString(TAG_FRANCHISE_MAX_REVENUES);
                        //   String advisor_min_investment = obj1.getString(TAG_ADVISOR_MIN_INVESTMENT);
                        //   String advisor_max_investment = obj1.getString(TAG_ADVISOR_MAX_INVESTMENT);


                        float flt_business_month_sales_min = Float.parseFloat(business_month_sales_min);
                        float flt_business_month_sales_max = Float.parseFloat(business_month_sales_max);
                        float flt_business_tentative_price_min = Float.parseFloat(business_tentative_price_min);
                        float flt_business_tentative_price_max = Float.parseFloat(business_tentative_price_max);
                        float flt_business_yearly_sales_min = Float.parseFloat(business_yearly_sales_min);
                        float flt_business_yearly_sales_max = Float.parseFloat(business_yearly_sales_max);
                        float flt_min_user_established = Float.parseFloat(min_user_established);
                        float flt_max_user_established = Float.parseFloat(max_user_established);
                        float flt_min_assets_purchased = Float.parseFloat(min_assets_purchased);
                        float flt_max_assets_purchased = Float.parseFloat(max_assets_purchased);
                        float flt_min_sell_lease_price = Float.parseFloat(min_sell_lease_price);
                        float flt_max_sell_lease_price = Float.parseFloat(max_sell_lease_price);
                        float flt_business_month_expense_min = Float.parseFloat(business_month_expense_min);
                        float flt_business_month_expense_max = Float.parseFloat(business_month_expense_max);
                        float flt_business_month_cashflow_min = Float.parseFloat(business_month_cashflow_min);
                        float flt_business_month_cashflow_max = Float.parseFloat(business_month_cashflow_max);
                        float flt_investor_currency_from = Float.parseFloat(investor_currency_from);
                        float flt_investor_currency_to = Float.parseFloat(investor_currency_to);
                        float flt_investor_roi_min = Float.parseFloat(investor_roi_min);
                        float flt_investor_roi_max = Float.parseFloat(investor_roi_max);
                        float flt_franchise_min_investment = Float.parseFloat(franchise_min_investment);
                        float flt_franchise_max_investment = Float.parseFloat(franchise_max_investment);
                        float flt_franchise_min_established = Float.parseFloat(franchise_min_established);
                        float flt_franchise_max_established = Float.parseFloat(franchise_max_established);
                        float flt_franchise_min_revenues = Float.parseFloat(franchise_min_revenues);
                        float flt_franchise_max_revenues = Float.parseFloat(franchise_max_revenues);

                        //   float flt_advisor_min_investment = Float.parseFloat(advisor_min_investment);
                        //   float flt_advisor_max_investment = Float.parseFloat(advisor_max_investment);


                        if (str_main_filter.equals("Business For sale")) {


                            // SELLING_PRICE
                            Seekbar_Selling_Price.setSteps(1);
                            Seekbar_Selling_Price.setMinStartValue(flt_business_tentative_price_min);
                            Seekbar_Selling_Price.setMaxValue(flt_business_tentative_price_max);
                            Seekbar_Selling_Price.setOnRangeSeekbarChangeListener(new OnRangeSeekbarChangeListener() {
                                @Override
                                public void valueChanged(Number minValue, Number maxValue) {
                                    txt_selling_price_minimum.setText(String.valueOf(minValue));
                                    txt_selling_price_maximum.setText(String.valueOf(maxValue));
                                }
                            });

                            // YEARLY_REVENUE
                            Seekbar_Yearly_Revenue.setSteps(1);
                            Seekbar_Yearly_Revenue.setMinStartValue(flt_business_month_sales_min);
                            Seekbar_Yearly_Revenue.setMaxValue(flt_business_month_sales_max);
                            Seekbar_Yearly_Revenue.setOnRangeSeekbarChangeListener(new OnRangeSeekbarChangeListener() {
                                @Override
                                public void valueChanged(Number minValue, Number maxValue) {
                                    txt_yearly_revenue_minimum.setText(String.valueOf(minValue));
                                    txt_yearly_revenue_maximum.setText(String.valueOf(maxValue));
                                }
                            });

                            // MONTHLY_CASHFLOW
                            Seekbar_Monthly_Cashflow.setSteps(1);
                            Seekbar_Monthly_Cashflow.setMinStartValue(flt_business_month_cashflow_min);
                            Seekbar_Monthly_Cashflow.setMaxValue(flt_business_month_cashflow_max);
                            Seekbar_Monthly_Cashflow.setOnRangeSeekbarChangeListener(new OnRangeSeekbarChangeListener() {
                                @Override
                                public void valueChanged(Number minValue, Number maxValue) {
                                    txt_monthly_cashflow_minimum.setText(String.valueOf(minValue));
                                    txt_monthly_cashflow_maximum.setText(String.valueOf(maxValue));
                                }
                            });

                            // ESTABLISED
                            Seekbar_Established.setSteps(1);
                            Seekbar_Established.setMinValue(flt_min_user_established);
                            Seekbar_Established.setMaxValue(flt_max_user_established);
                            Seekbar_Established.setOnRangeSeekbarChangeListener(new OnRangeSeekbarChangeListener() {
                                @Override
                                public void valueChanged(Number minValue, Number maxValue) {
                                    txt_established_minimum.setText(String.valueOf(minValue));
                                    txt_established_maximum.setText(String.valueOf(maxValue));
                                }
                            });

                        } else if (str_main_filter.equals("Investment Oppourtinites")) {


                            // INVESTMENT SIZE
                            Seekbar_Investment_size.setSteps(1);
                            Seekbar_Investment_size.setMinStartValue(flt_investor_currency_from);
                            Seekbar_Investment_size.setMaxValue(flt_investor_currency_to);
                            Seekbar_Investment_size.setOnRangeSeekbarChangeListener(new OnRangeSeekbarChangeListener() {
                                @Override
                                public void valueChanged(Number minValue, Number maxValue) {
                                    txt_investment_size_minimum.setText(String.valueOf(minValue));
                                    txt_investment_size_maximum.setText(String.valueOf(maxValue));
                                }
                            });

                            // RETURN_OF_INVESTMENT
                            Seekbar_Return_Of_Investment.setSteps(1);
                            Seekbar_Return_Of_Investment.setMinStartValue(flt_investor_roi_min);
                            Seekbar_Return_Of_Investment.setMaxValue(flt_investor_roi_max);
                            Seekbar_Return_Of_Investment.setOnRangeSeekbarChangeListener(new OnRangeSeekbarChangeListener() {
                                @Override
                                public void valueChanged(Number minValue, Number maxValue) {
                                    txt_return_of_investment_minimum.setText(String.valueOf(minValue));
                                    txt_return_of_investment_maximum.setText(String.valueOf(maxValue));
                                }
                            });


                        } else if (str_main_filter.equals("Franchise Oppourtinites")) {

                            System.out.println("CAME Inside Franchises");


                            //FRANCHISE INVESTMENT SIZE
                            Seekbar_Fran_Investment_size.setSteps(1);
                            Seekbar_Fran_Investment_size.setMinStartValue(flt_franchise_min_investment);
                            Seekbar_Fran_Investment_size.setMaxValue(flt_franchise_max_investment);
                            Seekbar_Fran_Investment_size.setOnRangeSeekbarChangeListener(new OnRangeSeekbarChangeListener() {
                                @Override
                                public void valueChanged(Number minValue, Number maxValue) {
                                    txt_fran_investment_size_minimum.setText(String.valueOf(minValue));
                                    txt_fran_investment_size_maximum.setText(String.valueOf(maxValue));
                                }
                            });

                            // FRANCHISE ESTABLISHED
                            Seekbar_Fran_Established.setSteps(1);
                            Seekbar_Fran_Established.setMinValue(flt_franchise_min_established);
                            Seekbar_Fran_Established.setMaxValue(flt_franchise_max_established);
                            Seekbar_Fran_Established.setOnRangeSeekbarChangeListener(new OnRangeSeekbarChangeListener() {
                                @Override
                                public void valueChanged(Number minValue, Number maxValue) {
                                    txt_fran_established_minimum.setText(String.valueOf(minValue));
                                    txt_fran_established_maximum.setText(String.valueOf(maxValue));
                                }
                            });

                            // EXPECTED_RETURN
                            Seekbar_Fran_Investment_size.setSteps(1);
                            Seekbar_Expected_Return.setMinStartValue(flt_franchise_min_revenues);
                            Seekbar_Expected_Return.setMaxValue(flt_franchise_max_revenues);
                            Seekbar_Expected_Return.setOnRangeSeekbarChangeListener(new OnRangeSeekbarChangeListener() {
                                @Override
                                public void valueChanged(Number minValue, Number maxValue) {
                                    txt_expected_return_minimum.setText(String.valueOf(minValue));
                                    txt_expected_return_maximum.setText(String.valueOf(maxValue));
                                }
                            });

                        } else if (str_main_filter.equals("Advisors")) {

                            System.out.println("CAME Inside Franchises");

                         /*
                            // ADVISOR INVESTMENT
                            Seekbar_Advisor_Investment.setSteps(1);
                            Seekbar_Advisor_Investment.setMinStartValue(flt_advisor_min_investment);
                            Seekbar_Advisor_Investment.setMaxValue(flt_advisor_max_investment);
                            Seekbar_Advisor_Investment.setOnRangeSeekbarChangeListener(new OnRangeSeekbarChangeListener() {
                                @Override
                                public void valueChanged(Number minValue, Number maxValue) {
                                    txt_adv_inves_size_minimum.setText(String.valueOf(minValue));
                                    txt_adv_inves_size_maximum.setText(String.valueOf(maxValue));
                                }
                            });
*/

                        }

                    } else if (success == 0) {
                        TastyToast.makeText(getApplicationContext(), "Something Went Wrong :(", TastyToast.LENGTH_LONG, TastyToast.ERROR);
                    }

                    System.out.println("CAME Outside");

                    dialog.dismiss();
                } catch (JSONException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }, new com.android.volley.Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {

                dialog.dismiss();

            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();

                params.put("currencye", str_user_currency);

                System.out.println("USER CURRENCY :::" + str_user_currency);

                return params;
            }

        };

        // Adding request to request queue
        queue.add(request);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        hidePDialog();
    }

    private void hidePDialog() {
        if (pDialog != null) {
            pDialog.dismiss();
            pDialog = null;
        }
    }
}
