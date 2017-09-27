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
 * Created by SJR on 21-Jul-17.
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

    public static final String TAG_BUSINESS_INTEREST_ID = "business_interest_id";
    public static final String TAG_BUSINESS_INTEREST_NAME = "business_interest_name";

    public static final String TAG_INVESTOR_BUYER_TYPE_ID = "investor_an_id";
    public static final String TAG_INVESTOR_BUYER_TYPE_NAME = "investor_an_name";

    public static final String TAG_SECTOR_NAME = "name";
    public static final String TAG_SECTOR_KEY = "key";
    public static final String TAG_SECTOR_TYPE = "type";

    public static final String TAG_LOC_PLACE = "place";
    public static final String TAG_LOC_KEY = "key";
    public static final String TAG_LOC_TYPE = "type";

    public static final String TAG_INTEREST_ID = "investor_interest_id";
    public static final String TAG_INTEREST_NAME = "investor_interest_name";

    public static final String TAG_MIN_BUISNESS_INVESTMENT = "min_buisness_investment";
    public static final String TAG_MAX_BUISNESS_INVESTMENT = "max_buisness_investment";
    public static final String TAG_MIN_BUSINESS_EBITDA = "min_business_ebitda";
    public static final String TAG_MAX_BUSINESS_EBITDA = "max_business_ebitda";
    public static final String TAG_MIN_USER_ESTABLISHED = "min_user_established";
    public static final String TAG_MAX_USER_ESTABLISHED = "max_user_established";
    public static final String TAG_MIN_ASSETS_PURCHASED = "min_assets_purchased";
    public static final String TAG_MAX_ASSETS_PURCHASED = "max_assets_purchased";
    public static final String TAG_MIN_SELL_LEASE_PRICE = "min_sell_lease_price";
    public static final String TAG_MAX_SELL_LEASE_PRICE = "max_sell_lease_price";
    public static final String TAG_MIN_SALES = "min_sales";
    public static final String TAG_MAX_SALES = "max_sales";
    public static final String TAG_INVESTOR_CURRENCY_FROM = "investor_currency_from";
    public static final String TAG_INVESTOR_CURRENCY_TO = "investor_currency_to";
    public static final String TAG_FRANCHISE_MIN_INVESTMENT = "franchise_min_investment";
    public static final String TAG_FRANCHISE_MAX_INVESTMENT = "franchise_max_investment";
    public static final String TAG_FRANCHISE_MIN_REVENUE = "franchise_min_revenue";
    public static final String TAG_FRANCHISE_MAX_REVENUE = "franchise_max_revenue";
    public static final String TAG_FRANCHISE_MIN_ESTABLISHED = "franchise_min_established";
    public static final String TAG_FRANCHISE_MAX_ESTABLISHED = "franchise_max_established";

    ArrayList<String> Arraylist_sector_name = null;
    ArrayList<String> Arraylist_sector_key = null;
    ArrayList<String> Arraylist_sector_type = null;

    /*Multi Select*/
    ArrayList<String> Arraylist_selected_sectorkey = null;
    ArrayList<String> Arraylist_selected_location = null;

    ArrayList<String> Arraylist_location_place = null;
    ArrayList<String> Arraylist_location_key = null;
    ArrayList<String> Arraylist_location_type = null;

    ArrayList<String> Arraylist_business_interest_id = null;
    ArrayList<String> Arraylist_business_interest_name = null;

    ArrayList<String> Arraylist_investor_buyer_id = null;
    ArrayList<String> Arraylist_investor_buyer_name = null;

    ArrayList<String> Arraylist_investor_interest_id = null;
    ArrayList<String> Arraylist_investor_interest_name = null;

    /* Arralist fetched Location list */
    ArrayList<String> Arraylist_fetched_location = null;
    ArrayList<String> Arraylist_selected_final_location = null;

    /* Arralist fetched indestries list */
    ArrayList<String> Arraylist_fetched_industries = null;
    ArrayList<String> Arraylist_selected_final_industry = null;

    private ArrayAdapter<String> adapter_transaction;
    private ArrayAdapter<String> adapter_investor_buyer_type;
    private ArrayAdapter<String> adapter_interested;

    SearchableSpinner spn_business_for_sale_type, spn_investor_buyer_type, spn_investor_buyer_interested_in;
    String str_main_filter = "";
    String str_selected_interest_id, str_selected_interest_name = "";
    String str_selected_transaction_id, str_selected_transaction_type_name = "";
    String str_selected_investor_buyer_type_id, str_selected_investor_buyer_type_name = "";

    MultiAutoCompleteTextView auto_bus_busineeslist, auto_bus_locationlist, auto_bus_hq_location, auto_investor_location;

    String str_final_business_sector, str_final_Business_Location = "";
    String str_final_industry_update, str_final_location_update, str_Investor_location, str_franchise_headquaters_location = "";

    LinearLayout LL_business_for_sale_type, LL_investor_buyer_type, LL_franchise_headquaters_location,
            LL_interested_business_locations, LL_interested_industry_sectors, LL_Slider_Investment_size,
            LL_Slider_Asset_price, LL_Slider_Run_Rate_Sales, LL_Slider_EBITA, LL_Slider_Established, LL_Slider_MonthlySales,
            LL_Checkboxes, LL_Investor_Buyer_Investor_Location, LL_Investor_Buyer_Investor_Interested_In;

    CrystalRangeSeekbar seekbar_investment_size, seekbar_asset_price,
            seekbar_runrate_sales, seekbar_monthly_sales, seekbar_ebitda, seekbar_established;

    //LEGAL ENTITY CHECKBOXES
    CheckBox chb_limited_liability_company, chb_public_limited_company, chb_partnership,
            chb_S_corporation, chb_private_limited_company, chb_C_corporation, chb_limited_liability_partnership,
            chb_sole_proprietorship, chb_others;
    //LEGAL ENTITY CHECKBOX STRINGS
    String str_limited_liability_company, str_public_limited_company, str_partnership,
            str_S_corporation, str_private_limited_company, str_C_corporation, str_limited_liability_partnership,
            str_sole_proprietorship, str_others = "0";

    TextView txt_investment_size_minimum, txt_investment_size_maximum, txt_asset_price_minimum, txt_asset_price_maximum,
            txt_runrate_sales_minimum, txt_runrate_sales_maximum, txt_monthly_sales_minimum, txt_monthly_sales_maximum,
            txt_ebitda_minimum, txt_ebitda_maximum,
            txt_established_minimum, txt_established_maximum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter_menu);

        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
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

        chb_limited_liability_company = (CheckBox) findViewById(R.id.chbx_Limited_Liability_Company);
        chb_public_limited_company = (CheckBox) findViewById(R.id.chbx_Public_Limited_Company);
        chb_partnership = (CheckBox) findViewById(R.id.chbx_Partnership);
        chb_S_corporation = (CheckBox) findViewById(R.id.chbx_SCorporation);
        chb_private_limited_company = (CheckBox) findViewById(R.id.chbx_private_limited_company);
        chb_C_corporation = (CheckBox) findViewById(R.id.chbx_C_Corporation);
        chb_limited_liability_partnership = (CheckBox) findViewById(R.id.chbx_Limited_Liability_Partnership);
        chb_sole_proprietorship = (CheckBox) findViewById(R.id.chbx_sole_proprietorship);
        chb_others = (CheckBox) findViewById(R.id.chbx_others);

        txt_investment_size_minimum = (TextView) findViewById(R.id.activity_filter_investment_minValue);
        txt_investment_size_maximum = (TextView) findViewById(R.id.activity_filter_investment_maxValue);
        txt_asset_price_minimum = (TextView) findViewById(R.id.activity_filter_assetprice_minValue);
        txt_asset_price_maximum = (TextView) findViewById(R.id.activity_filter_assetprice_maxValue);
        txt_runrate_sales_minimum = (TextView) findViewById(R.id.activity_filter_runratesales_minValue);
        txt_runrate_sales_maximum = (TextView) findViewById(R.id.activity_filter_runratesales_maxValue);
        txt_monthly_sales_minimum = (TextView) findViewById(R.id.activity_filter_monthlysales_minValue);
        txt_monthly_sales_maximum = (TextView) findViewById(R.id.activity_filter_monthlysales_maxValue);
        txt_ebitda_minimum = (TextView) findViewById(R.id.activity_filter_ebitda_minValue);
        txt_ebitda_maximum = (TextView) findViewById(R.id.activity_filter_ebitda_maxValue);
        txt_established_minimum = (TextView) findViewById(R.id.activity_filter_established_minValue);
        txt_established_maximum = (TextView) findViewById(R.id.activity_filter_established_maxValue);

        LL_business_for_sale_type = (LinearLayout) findViewById(R.id.layout_business_for_sale_type);
        LL_investor_buyer_type = (LinearLayout) findViewById(R.id.layout_investor_buyer_type);
        LL_franchise_headquaters_location = (LinearLayout) findViewById(R.id.layout_franchise_headquaters_location);
        LL_interested_business_locations = (LinearLayout) findViewById(R.id.layout_interested_business_locations);
        LL_interested_industry_sectors = (LinearLayout) findViewById(R.id.layout_interested_business_industries);
        LL_Slider_Investment_size = (LinearLayout) findViewById(R.id.layout_slider_investment_size);
        LL_Slider_Asset_price = (LinearLayout) findViewById(R.id.layout_slider_asset_price);
        LL_Slider_Run_Rate_Sales = (LinearLayout) findViewById(R.id.layout_slider_run_rate_sales);
        LL_Slider_EBITA = (LinearLayout) findViewById(R.id.layout_slider_EBITDA);
        LL_Slider_Established = (LinearLayout) findViewById(R.id.layout_slider_established);
        LL_Slider_MonthlySales = (LinearLayout) findViewById(R.id.layout_slider_monthly_sales);
        LL_Checkboxes = (LinearLayout) findViewById(R.id.layout_checkboxes);
        LL_Investor_Buyer_Investor_Location = (LinearLayout) findViewById(R.id.layout_investor_buyer_investor_location);
        LL_Investor_Buyer_Investor_Interested_In = (LinearLayout) findViewById(R.id.layout_investor_buyer_investor_interested_in);

        spn_business_for_sale_type = (SearchableSpinner) findViewById(R.id.spn_filter_transtype);
        spn_investor_buyer_type = (SearchableSpinner) findViewById(R.id.spn_filter_investor_buyer_type);
        spn_investor_buyer_interested_in = (SearchableSpinner) findViewById(R.id.spn_filter_investor_interested);

        LL_business_for_sale_type.setVisibility(View.GONE);
        LL_interested_business_locations.setVisibility(View.GONE);
        LL_interested_industry_sectors.setVisibility(View.GONE);
        LL_Slider_Investment_size.setVisibility(View.GONE);
        LL_Slider_Asset_price.setVisibility(View.GONE);
        LL_Slider_Run_Rate_Sales.setVisibility(View.GONE);
        LL_Slider_EBITA.setVisibility(View.GONE);
        LL_Slider_Established.setVisibility(View.GONE);
        LL_Checkboxes.setVisibility(View.GONE);
        LL_Slider_MonthlySales.setVisibility(View.GONE);
        LL_investor_buyer_type.setVisibility(View.GONE);
        LL_franchise_headquaters_location.setVisibility(View.GONE);
        LL_Investor_Buyer_Investor_Location.setVisibility(View.GONE);
        LL_Investor_Buyer_Investor_Interested_In.setVisibility(View.GONE);

        auto_bus_hq_location = (MultiAutoCompleteTextView) findViewById(R.id.filter_profile_multi_businesheadquaters__location);
        auto_bus_locationlist = (MultiAutoCompleteTextView) findViewById(R.id.business_profile_multi_business_location);
        auto_bus_busineeslist = (MultiAutoCompleteTextView) findViewById(R.id.business_profile_multi_business_industry);
        auto_investor_location = (MultiAutoCompleteTextView) findViewById(R.id.filter_profile_multi_auto_investor_location);

        btn_clear_all = (Button) findViewById(R.id.btn_filter_clear);
        btn_done = (Button) findViewById(R.id.btn_filter_done);

        seekbar_investment_size = (CrystalRangeSeekbar) findViewById(R.id.activity_filter_investment_range_slider);
        seekbar_asset_price = (CrystalRangeSeekbar) findViewById(R.id.activity_filter_assetprice_range_slider);
        seekbar_runrate_sales = (CrystalRangeSeekbar) findViewById(R.id.activity_filter_runratesales_range_slider);
        seekbar_monthly_sales = (CrystalRangeSeekbar) findViewById(R.id.activity_filter_monthlysales_range_slider);
        seekbar_ebitda = (CrystalRangeSeekbar) findViewById(R.id.activity_filter_ebitda_range_slider);
        seekbar_established = (CrystalRangeSeekbar) findViewById(R.id.activity_filter_established_range_slider);

        Arraylist_sector_name = new ArrayList<String>();
        Arraylist_sector_key = new ArrayList<String>();
        Arraylist_sector_type = new ArrayList<String>();

        Arraylist_selected_sectorkey = new ArrayList<String>();
        Arraylist_selected_location = new ArrayList<String>();

        Arraylist_location_place = new ArrayList<String>();
        Arraylist_location_key = new ArrayList<String>();
        Arraylist_location_type = new ArrayList<String>();

        Arraylist_business_interest_id = new ArrayList<String>();
        Arraylist_business_interest_name = new ArrayList<String>();

        Arraylist_investor_buyer_id = new ArrayList<String>();
        Arraylist_investor_buyer_name = new ArrayList<String>();

        Arraylist_investor_interest_id = new ArrayList<String>();
        Arraylist_investor_interest_name = new ArrayList<String>();

        Arraylist_fetched_location = new ArrayList<String>();
        Arraylist_selected_final_location = new ArrayList<String>();

        Arraylist_fetched_industries = new ArrayList<String>();
        Arraylist_selected_final_industry = new ArrayList<String>();


        try {
            if (str_main_filter.equals("Business For sale")) {
                LL_business_for_sale_type.setVisibility(View.VISIBLE);
                LL_interested_business_locations.setVisibility(View.VISIBLE);
                LL_interested_industry_sectors.setVisibility(View.VISIBLE);
                LL_Slider_Investment_size.setVisibility(View.VISIBLE);
                LL_Slider_Asset_price.setVisibility(View.VISIBLE);
                LL_Slider_Run_Rate_Sales.setVisibility(View.VISIBLE);
                LL_Slider_EBITA.setVisibility(View.VISIBLE);
                LL_Slider_Established.setVisibility(View.VISIBLE);
                LL_Checkboxes.setVisibility(View.VISIBLE);

                LL_Slider_MonthlySales.setVisibility(View.GONE);
                LL_investor_buyer_type.setVisibility(View.GONE);
                LL_franchise_headquaters_location.setVisibility(View.GONE);
                LL_Investor_Buyer_Investor_Location.setVisibility(View.GONE);
                LL_Investor_Buyer_Investor_Interested_In.setVisibility(View.GONE);


            } else if (str_main_filter.equals("Investment Oppourtinites")) {
                LL_investor_buyer_type.setVisibility(View.VISIBLE);
                LL_interested_business_locations.setVisibility(View.VISIBLE);
                LL_interested_industry_sectors.setVisibility(View.VISIBLE);
                LL_Slider_Investment_size.setVisibility(View.VISIBLE);
                LL_Investor_Buyer_Investor_Location.setVisibility(View.VISIBLE);
                LL_Investor_Buyer_Investor_Interested_In.setVisibility(View.VISIBLE);

                LL_business_for_sale_type.setVisibility(View.GONE);
                LL_Slider_Asset_price.setVisibility(View.GONE);
                LL_Slider_Run_Rate_Sales.setVisibility(View.GONE);
                LL_Slider_EBITA.setVisibility(View.GONE);
                LL_Slider_MonthlySales.setVisibility(View.GONE);
                LL_franchise_headquaters_location.setVisibility(View.GONE);
                LL_Slider_Established.setVisibility(View.GONE);
                LL_Checkboxes.setVisibility(View.GONE);


            } else if (str_main_filter.equals("Franchise Oppourtinites")) {
                LL_franchise_headquaters_location.setVisibility(View.VISIBLE);
                LL_interested_business_locations.setVisibility(View.VISIBLE);
                LL_interested_industry_sectors.setVisibility(View.VISIBLE);
                LL_Slider_MonthlySales.setVisibility(View.VISIBLE);
                LL_Slider_Investment_size.setVisibility(View.VISIBLE);
                LL_Slider_Established.setVisibility(View.VISIBLE);
                LL_Checkboxes.setVisibility(View.VISIBLE);

                LL_business_for_sale_type.setVisibility(View.GONE);
                LL_investor_buyer_type.setVisibility(View.GONE);
                LL_Slider_Asset_price.setVisibility(View.GONE);
                LL_Slider_Run_Rate_Sales.setVisibility(View.GONE);
                LL_Slider_EBITA.setVisibility(View.GONE);
                LL_Investor_Buyer_Investor_Location.setVisibility(View.GONE);
                LL_Investor_Buyer_Investor_Interested_In.setVisibility(View.GONE);

            }

        } catch (Exception e) {

        }

        btn_clear_all.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                LL_business_for_sale_type.setVisibility(View.GONE);
                LL_interested_business_locations.setVisibility(View.GONE);
                LL_interested_industry_sectors.setVisibility(View.GONE);
                LL_Slider_Investment_size.setVisibility(View.GONE);
                LL_Slider_Asset_price.setVisibility(View.GONE);
                LL_Slider_Run_Rate_Sales.setVisibility(View.GONE);
                LL_Slider_EBITA.setVisibility(View.GONE);
                LL_Slider_Established.setVisibility(View.GONE);
                LL_Checkboxes.setVisibility(View.GONE);
                LL_Slider_MonthlySales.setVisibility(View.GONE);
                LL_investor_buyer_type.setVisibility(View.GONE);
                LL_franchise_headquaters_location.setVisibility(View.GONE);
                LL_Investor_Buyer_Investor_Location.setVisibility(View.GONE);
                LL_Investor_Buyer_Investor_Interested_In.setVisibility(View.GONE);

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
                String[] str_valid_industries = auto_bus_busineeslist.getText().toString().split(", ");

                if (!str_valid_industries.equals("") && str_valid_industries == null) {

                    String[] str_asset_industries = auto_bus_busineeslist.getText().toString().split(", ");

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


                } else {

                }

                //IF STATEMENT TO POST VALUES ACCORING TO MAIN FILTER TYPE
                if (str_main_filter.equals("Business For sale")) {

                    // String Values According to checkbox state  - ENTITY TYPE
                    if (chb_limited_liability_company.isChecked()) {
                        str_limited_liability_company = "1";
                    } else {
                        str_limited_liability_company = "0";
                    }

                    if (chb_public_limited_company.isChecked()) {
                        str_public_limited_company = "1";
                    } else {
                        str_public_limited_company = "0";
                    }

                    if (chb_partnership.isChecked()) {
                        str_partnership = "1";
                    } else {
                        str_partnership = "0";
                    }

                    if (chb_S_corporation.isChecked()) {
                        str_S_corporation = "1";
                    } else {
                        str_S_corporation = "0";
                    }

                    if (chb_private_limited_company.isChecked()) {
                        str_private_limited_company = "1";
                    } else {
                        str_private_limited_company = "0";
                    }

                    if (chb_C_corporation.isChecked()) {
                        str_C_corporation = "1";
                    } else {
                        str_C_corporation = "0";
                    }

                    if (chb_limited_liability_partnership.isChecked()) {
                        str_limited_liability_partnership = "1";
                    } else {
                        str_limited_liability_partnership = "0";
                    }

                    if (chb_sole_proprietorship.isChecked()) {
                        str_sole_proprietorship = "1";
                    } else {
                        str_sole_proprietorship = "0";
                    }

                    if (chb_others.isChecked()) {
                        str_others = "1";
                    } else {
                        str_others = "0";
                    }

                    //SEEKBAR VALUES TO STRING
                    String str_investment_size_minimum = txt_investment_size_minimum.getText().toString();
                    String str_investment_size_maximum = txt_investment_size_maximum.getText().toString();
                    String str_runrate_sales_minimum = txt_runrate_sales_minimum.getText().toString();
                    String str_runrate_sales_maximum = txt_runrate_sales_maximum.getText().toString();
                    String str_ebitda_minimum = txt_ebitda_minimum.getText().toString();
                    String str_ebitda_maximum = txt_ebitda_maximum.getText().toString();
                    String str_established_minimum = txt_established_minimum.getText().toString();
                    String str_established_maximum = txt_established_maximum.getText().toString();

                    //PUTTING THE FILTER VALUES IN SHARED PREFERENCES
                    SharedPreferences sharedPreferences_filter = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                    SharedPreferences.Editor editor_filter = sharedPreferences_filter.edit();
                    //FILTER TYPE AND MULTISELECT LOCATION AND INDUSTRY VALUES
                    editor_filter.putString("str_main_filter_type", str_main_filter);
                    editor_filter.putString("str_business_for_sale_transaction_type", str_selected_transaction_id);
                    editor_filter.putString("str_interested_business_locations", str_final_location_update);
                    editor_filter.putString("str_interested_industries", str_final_industry_update);
                    //SEEKBAR VALUES
                    editor_filter.putString("str_investment_size_minimum", str_investment_size_minimum);
                    editor_filter.putString("str_investment_size_maximum", str_investment_size_maximum);
                    editor_filter.putString("str_runrate_sales_minimum", str_runrate_sales_minimum);
                    editor_filter.putString("str_runrate_sales_maximum", str_runrate_sales_maximum);
                    editor_filter.putString("str_ebitda_minimum", str_ebitda_minimum);
                    editor_filter.putString("str_ebitda_maximum", str_ebitda_maximum);
                    editor_filter.putString("str_established_minimum", str_established_minimum);
                    editor_filter.putString("str_established_maximum", str_established_maximum);
                    //LEGAL ENTITY TYPE
                    editor_filter.putString("str_limited_liability_company", str_limited_liability_company);
                    editor_filter.putString("str_public_limited_company", str_public_limited_company);
                    editor_filter.putString("str_partnership", str_partnership);
                    editor_filter.putString("str_S_corporation", str_S_corporation);
                    editor_filter.putString("str_private_limited_company", str_private_limited_company);
                    editor_filter.putString("str_C_corporation", str_C_corporation);
                    editor_filter.putString("str_limited_liability_partnership", str_limited_liability_partnership);
                    editor_filter.putString("str_sole_proprietorship", str_sole_proprietorship);
                    editor_filter.putString("str_others", str_others);
                    editor_filter.commit();

                    System.out.println("investment_size_minimum :: " + str_investment_size_minimum);
                    System.out.println("investment_size_maximum :: " + str_investment_size_maximum);
                    System.out.println("runrate_sales_minimum :: " + str_runrate_sales_minimum);
                    System.out.println("runrate_sales_maximum :: " + str_runrate_sales_maximum);
                    System.out.println("ebitda_minimum :: " + str_ebitda_minimum);
                    System.out.println("ebitda_maximum :: " + str_ebitda_maximum);
                    System.out.println("established_minimum :: " + str_established_minimum);
                    System.out.println("established_maximum :: " + str_established_maximum);
                    System.out.println("str_limited_liability_company :: " + str_limited_liability_company);
                    System.out.println("str_public_limited_company :: " + str_public_limited_company);
                    System.out.println("str_partnership :: " + str_partnership);
                    System.out.println("str_S_corporation :: " + str_S_corporation);
                    System.out.println("str_private_limited_company :: " + str_private_limited_company);
                    System.out.println("str_C_corporation :: " + str_C_corporation);
                    System.out.println("str_limited_liability_partnership :: " + str_limited_liability_partnership);
                    System.out.println("str_sole_proprietorship :: " + str_sole_proprietorship);
                    System.out.println("str_others :: " + str_others);


                    Intent i = new Intent(Activity_Filter_Business_For_Sale.this, MainActivity.class);
                    i.putExtra("type", str_main_filter);
                    startActivity(i);
                    overridePendingTransition(R.anim.pull_in_right, R.anim.pull_in_left);
                    finish();


                } else if (str_main_filter.equals("Investment Oppourtinites")) {

                    //SEEKBAR VALUES TO STRING
                    String str_investor_investment_size_minimum = txt_investment_size_minimum.getText().toString();
                    String str_investor_investment_size_maximum = txt_investment_size_maximum.getText().toString();


                    /********************************************************
                     * Get Multiple Location Details From Searchable Spinner
                     * ******************************************************/

                    String[] str_Investor_loccations = auto_investor_location.getText().toString().split(", ");

                    if (!str_valid_industries.equals("") && str_valid_industries == null) {

                        String[] str_investor_loccations = auto_investor_location.getText().toString().split(", ");
                        Arraylist_fetched_location.clear();
                        for (int i = 0; i < str_investor_loccations.length; i++) {
                            Arraylist_fetched_location.add(str_investor_loccations[i]);
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
                            str_Investor_location = TextUtils.join(", ", Arraylist_selected_final_location);

                        }

                    }else {

                    }


                    System.out.println("FINAL SELECTED LOCATION :: " + str_Investor_location);


                    //PUTTING THE FILTER VALUES IN SHARED PREFERENCES
                    SharedPreferences sharedPreferences_filter = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                    SharedPreferences.Editor editor_filter = sharedPreferences_filter.edit();
                    //FILTER TYPE AND MULTISELECT LOCATION AND INDUSTRY VALUES
                    editor_filter.putString("str_main_filter_type", str_main_filter);
                    editor_filter.putString("str_investor_buyer_role_type", str_selected_investor_buyer_type_id);
                    editor_filter.putString("str_interested_business_locations", str_final_location_update);
                    editor_filter.putString("str_interested_industries", str_final_industry_update);
                    //SEEKBAR VALUES
                    editor_filter.putString("str_investor_investment_size_minimum", str_investor_investment_size_minimum);
                    editor_filter.putString("str_investor_investment_size_maximum", str_investor_investment_size_maximum);
                    editor_filter.putString("str_investor_location", str_Investor_location);
                    editor_filter.putString("str_investor_interested_in", str_selected_interest_id);
                    editor_filter.commit();

                    System.out.println("str_main_filter_type" + str_main_filter);
                    System.out.println("str_investor_buyer_role_type" + str_selected_investor_buyer_type_id);
                    System.out.println("str_final_location_update" + str_final_location_update);
                    System.out.println("str_final_industry_update" + str_final_industry_update);
                    System.out.println("str_investor_investment_size_minimum" + str_investor_investment_size_minimum);
                    System.out.println("str_investor_investment_size_maximum" + str_investor_investment_size_maximum);
                    System.out.println("str_Investor_location" + str_Investor_location);
                    System.out.println("str_selected_interest_id" + str_selected_interest_id);

                    Intent i = new Intent(Activity_Filter_Business_For_Sale.this, MainActivity.class);
                    i.putExtra("type", str_main_filter);
                    startActivity(i);
                    overridePendingTransition(R.anim.pull_in_right, R.anim.pull_in_left);
                    finish();


                } else if (str_main_filter.equals("Franchise Oppourtinites")) {

                    System.out.println(" Button Clicked & Came inside Franchises ");

                    //SEEKBAR VALUES TO STRING
                    String str_franchise_investment_size_minimum = txt_investment_size_minimum.getText().toString();
                    String str_franchise_investment_size_maximum = txt_investment_size_maximum.getText().toString();

                    String str_franchise_monthly_sales_minimum = txt_monthly_sales_minimum.getText().toString();
                    String str_franchise_monthly_sales_maximum = txt_monthly_sales_maximum.getText().toString();

                    String str_franchise_established_minimum = txt_established_minimum.getText().toString();
                    String str_franchise_established_maximum = txt_established_maximum.getText().toString();

                    // String Values According to checkbox state  - ENTITY TYPE
                    if (chb_limited_liability_company.isChecked()) {
                        str_limited_liability_company = "1";
                    } else {
                        str_limited_liability_company = "0";
                    }

                    if (chb_public_limited_company.isChecked()) {
                        str_public_limited_company = "1";
                    } else {
                        str_public_limited_company = "0";
                    }

                    if (chb_partnership.isChecked()) {
                        str_partnership = "1";
                    } else {
                        str_partnership = "0";
                    }

                    if (chb_S_corporation.isChecked()) {
                        str_S_corporation = "1";
                    } else {
                        str_S_corporation = "0";
                    }

                    if (chb_private_limited_company.isChecked()) {
                        str_private_limited_company = "1";
                    } else {
                        str_private_limited_company = "0";
                    }

                    if (chb_C_corporation.isChecked()) {
                        str_C_corporation = "1";
                    } else {
                        str_C_corporation = "0";
                    }

                    if (chb_limited_liability_partnership.isChecked()) {
                        str_limited_liability_partnership = "1";
                    } else {
                        str_limited_liability_partnership = "0";
                    }

                    if (chb_sole_proprietorship.isChecked()) {
                        str_sole_proprietorship = "1";
                    } else {
                        str_sole_proprietorship = "0";
                    }

                    if (chb_others.isChecked()) {
                        str_others = "1";
                    } else {
                        str_others = "0";
                    }

                    //PUTTING THE FILTER VALUES IN SHARED PREFERENCES
                    SharedPreferences sharedPreferences_filter = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                    SharedPreferences.Editor editor_filter = sharedPreferences_filter.edit();
                    //FILTER TYPE AND MULTISELECT LOCATION AND INDUSTRY VALUES
                    editor_filter.putString("str_main_filter_type", str_main_filter);
                    editor_filter.putString("str_franchise_headquaters_location", str_franchise_headquaters_location);
                    editor_filter.putString("str_interested_business_locations", str_final_location_update);
                    editor_filter.putString("str_interested_industries", str_final_industry_update);
                    //SEEKBAR VALUES
                    editor_filter.putString("str_franchise_investment_size_minimum", str_franchise_investment_size_minimum);
                    editor_filter.putString("str_franchise_investment_size_maximum", str_franchise_investment_size_maximum);
                    editor_filter.putString("str_franchise_monthly_sales_minimum", str_franchise_monthly_sales_minimum);
                    editor_filter.putString("str_franchise_monthly_sales_maximum", str_franchise_monthly_sales_maximum);
                    editor_filter.putString("str_franchise_established_minimum", str_franchise_established_minimum);
                    editor_filter.putString("str_franchise_established_maximum", str_franchise_established_maximum);
                    //LEGAL ENTITY TYPE
                    editor_filter.putString("str_limited_liability_company", str_limited_liability_company);
                    editor_filter.putString("str_public_limited_company", str_public_limited_company);
                    editor_filter.putString("str_partnership", str_partnership);
                    editor_filter.putString("str_S_corporation", str_S_corporation);
                    editor_filter.putString("str_private_limited_company", str_private_limited_company);
                    editor_filter.putString("str_C_corporation", str_C_corporation);
                    editor_filter.putString("str_limited_liability_partnership", str_limited_liability_partnership);
                    editor_filter.putString("str_sole_proprietorship", str_sole_proprietorship);
                    editor_filter.putString("str_others", str_others);
                    editor_filter.commit();

                    System.out.println("str_main_filter_type" + str_main_filter);
                    System.out.println("str_franchise_headquaters_location" + str_franchise_headquaters_location);
                    System.out.println("str_final_location_update" + str_final_location_update);
                    System.out.println("str_final_industry_update" + str_final_industry_update);
                    System.out.println("str_franchise_investment_size_minimum :: " + str_franchise_investment_size_minimum);
                    System.out.println("str_franchise_investment_size_maximum :: " + str_franchise_investment_size_maximum);
                    System.out.println("str_franchise_monthly_sales_minimum :: " + str_franchise_monthly_sales_minimum);
                    System.out.println("str_franchise_monthly_sales_maximum :: " + str_franchise_monthly_sales_maximum);
                    System.out.println("str_franchise_established_minimum :: " + str_franchise_established_minimum);
                    System.out.println("str_franchise_established_maximum :: " + str_franchise_established_maximum);
                    System.out.println("str_limited_liability_company :: " + str_limited_liability_company);
                    System.out.println("str_public_limited_company :: " + str_public_limited_company);
                    System.out.println("str_partnership :: " + str_partnership);
                    System.out.println("str_S_corporation :: " + str_S_corporation);
                    System.out.println("str_private_limited_company :: " + str_private_limited_company);
                    System.out.println("str_C_corporation :: " + str_C_corporation);
                    System.out.println("str_limited_liability_partnership :: " + str_limited_liability_partnership);
                    System.out.println("str_sole_proprietorship :: " + str_sole_proprietorship);
                    System.out.println("str_others :: " + str_others);

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
            Get_Business_For_Sale_type();
        } catch (Exception e) {

        }
    }


    /*****************************
     * To get  Business For sale Type
     ***************************/

    public void Get_Business_For_Sale_type() {
        String tag_json_obj = "json_obj_req";
        StringRequest request = new StringRequest(Request.Method.POST,
                AppConfig.url_interested_in, new com.android.volley.Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d(TAG, response.toString());
                try {
                    JSONObject obj = new JSONObject(response);
                    int success = obj.getInt("status");

                    if (success == 1) {

                        JSONArray arr;

                        arr = obj.getJSONArray("data");

                        for (int i = 0; arr.length() > i; i++) {
                            JSONObject obj1 = arr.getJSONObject(i);

                            String interest_key = obj1.getString(TAG_BUSINESS_INTEREST_ID);
                            String interest_name = obj1.getString(TAG_BUSINESS_INTEREST_NAME);

                            Arraylist_business_interest_id.add(interest_key);
                            Arraylist_business_interest_name.add(interest_name);
                        }
                        try {
                            adapter_transaction = new ArrayAdapter<String>(Activity_Filter_Business_For_Sale.this,
                                    android.R.layout.simple_list_item_1, Arraylist_business_interest_name);
                            spn_business_for_sale_type.setAdapter(adapter_transaction);
                            //  spn_business_for_sale_type.setThreshold(1);

                            spn_business_for_sale_type.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
                                                        long arg3) {
                                    t1 = (TextView) arg1;
                                    str_selected_transaction_type_name = t1.getText().toString();
                                    System.out.println("Argument " + arg2);
                                    str_selected_transaction_id = Arraylist_business_interest_id.get(arg2);


                                }
                            });

                        } catch (Exception e) {

                        }

                        try {
                            queue = Volley.newRequestQueue(getApplicationContext());
                            Get_InvestorBuyer_type();
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


    /*****************************
     * To get  Business For sale Type
     ***************************/

    public void Get_InvestorBuyer_type() {
        String tag_json_obj = "json_obj_req";
        StringRequest request = new StringRequest(Request.Method.POST,
                AppConfig.url_investor_buyer_type, new com.android.volley.Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d(TAG, response.toString());
                try {
                    JSONObject obj = new JSONObject(response);
                    int success = obj.getInt("status");

                    if (success == 1) {

                        JSONArray arr;

                        arr = obj.getJSONArray("data");

                        for (int i = 0; arr.length() > i; i++) {
                            JSONObject obj1 = arr.getJSONObject(i);

                            String investor_buyer_id = obj1.getString(TAG_INVESTOR_BUYER_TYPE_ID);
                            String investor_buyer_name = obj1.getString(TAG_INVESTOR_BUYER_TYPE_NAME);

                            Arraylist_investor_buyer_id.add(investor_buyer_id);
                            Arraylist_investor_buyer_name.add(investor_buyer_name);
                        }
                        try {
                            adapter_investor_buyer_type = new ArrayAdapter<String>(Activity_Filter_Business_For_Sale.this,
                                    android.R.layout.simple_list_item_1, Arraylist_investor_buyer_name);
                            spn_investor_buyer_type.setAdapter(adapter_investor_buyer_type);
                            //  spn_business_for_sale_type.setThreshold(1);

                            spn_investor_buyer_type.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
                                                        long arg3) {
                                    t1 = (TextView) arg1;
                                    str_selected_investor_buyer_type_name = t1.getText().toString();
                                    System.out.println("Argument " + arg2);
                                    str_selected_investor_buyer_type_id = Arraylist_investor_buyer_id.get(arg2);


                                }
                            });

                        } catch (Exception e) {

                        }

                        try {
                            queue = Volley.newRequestQueue(getApplicationContext());
                            Get_Sector_List();
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

    /*****************************
     * To get  Business sector List
     ***************************/

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

                            String sector_name = obj1.getString(TAG_SECTOR_NAME);
                            String sector_key = obj1.getString(TAG_SECTOR_KEY);
                            String sector_type = obj1.getString(TAG_SECTOR_TYPE);

                            Arraylist_sector_name.add(sector_name);
                            Arraylist_sector_key.add(sector_key);
                            Arraylist_sector_type.add(sector_type);
                        }
                        try {
                            System.out.println("ARAAAAY :: " + Arraylist_sector_name);

                            ArrayAdapter<String> adapter_sector1 = new ArrayAdapter<String>(Activity_Filter_Business_For_Sale.this,
                                    android.R.layout.simple_list_item_1, Arraylist_sector_name);
                            auto_bus_busineeslist.setAdapter(adapter_sector1);
                            auto_bus_busineeslist
                                    .setTokenizer(new MultiAutoCompleteTextView.CommaTokenizer());
                            auto_bus_busineeslist.setThreshold(1);

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

    /*****************************
     * To get  Business Location List
     ***************************/

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

                            String location_place = obj1.getString(TAG_LOC_PLACE);
                            String location_key = obj1.getString(TAG_LOC_KEY);
                            String location_type = obj1.getString(TAG_LOC_TYPE);

                            Arraylist_location_place.add(location_place);
                            Arraylist_location_key.add(location_key);
                            Arraylist_location_type.add(location_type);
                        }
                        try {
                            System.out.println("ARAAAAY :: " + Arraylist_location_place);

                            ArrayAdapter<String> adapter_process = new ArrayAdapter<String>(Activity_Filter_Business_For_Sale.this,
                                    android.R.layout.simple_list_item_1, Arraylist_location_place);
                            auto_bus_hq_location.setAdapter(adapter_process);
                            auto_bus_hq_location
                                    .setTokenizer(new MultiAutoCompleteTextView.CommaTokenizer());
                            auto_bus_hq_location.setThreshold(1);

                            auto_bus_locationlist.setAdapter(adapter_process);
                            auto_bus_locationlist
                                    .setTokenizer(new MultiAutoCompleteTextView.CommaTokenizer());
                            auto_bus_locationlist.setThreshold(1);

                            auto_investor_location.setAdapter(adapter_process);
                            auto_investor_location
                                    .setTokenizer(new MultiAutoCompleteTextView.CommaTokenizer());
                            auto_investor_location.setThreshold(1);

                        } catch (Exception e) {

                        }

                        try {
                            queue = Volley.newRequestQueue(getApplicationContext());
                            Get_Interested();
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


    /*****************************
     * To get  Interested in  Details
     ***************************/

    public void Get_Interested() {
        String tag_json_obj = "json_obj_req";
        StringRequest request = new StringRequest(Request.Method.POST,
                AppConfig.url_investor_interested, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d(TAG, response.toString());
                try {
                    JSONObject obj = new JSONObject(response);
                    int success = obj.getInt("status");

                    if (success == 1) {

                        JSONArray arr;

                        arr = obj.getJSONArray("data");

                        for (int i = 0; arr.length() > i; i++) {
                            JSONObject obj1 = arr.getJSONObject(i);

                            String interest_key = obj1.getString(TAG_INTEREST_ID);
                            String interest_name = obj1.getString(TAG_INTEREST_NAME);

                            Arraylist_investor_interest_id.add(interest_key);
                            Arraylist_investor_interest_name.add(interest_name);
                        }
                        try {
                            adapter_interested = new ArrayAdapter<String>(Activity_Filter_Business_For_Sale.this,
                                    android.R.layout.simple_list_item_1, Arraylist_investor_interest_name);
                            spn_investor_buyer_interested_in.setAdapter(adapter_interested);

                            spn_investor_buyer_interested_in.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                @Override
                                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                                    t1 = (TextView) view;
                                    str_selected_interest_name = t1.getText().toString();
                                    str_selected_interest_id = Arraylist_investor_interest_id.get(position);

                                }

                                @Override
                                public void onNothingSelected(AdapterView<?> parent) {

                                }
                            });


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
        }, new Response.ErrorListener() {

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

    /*****************************
     * To get  Filter Seekbar Values
     ***************************/

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
                        String min_buisness_investment = obj1.getString(TAG_MIN_BUISNESS_INVESTMENT);
                        String max_buisness_investment = obj1.getString(TAG_MAX_BUISNESS_INVESTMENT);
                        String min_business_ebitda = obj1.getString(TAG_MIN_BUSINESS_EBITDA);
                        String max_business_ebitda = obj1.getString(TAG_MAX_BUSINESS_EBITDA);
                        String min_user_established = obj1.getString(TAG_MIN_USER_ESTABLISHED);
                        String max_user_established = obj1.getString(TAG_MAX_USER_ESTABLISHED);
                        String min_assets_purchased = obj1.getString(TAG_MIN_ASSETS_PURCHASED);
                        String max_assets_purchased = obj1.getString(TAG_MAX_ASSETS_PURCHASED);
                        String min_sell_lease_price = obj1.getString(TAG_MIN_SELL_LEASE_PRICE);
                        String max_sell_lease_price = obj1.getString(TAG_MAX_SELL_LEASE_PRICE);
                        String min_sales = obj1.getString(TAG_MIN_SALES);
                        String max_sales = obj1.getString(TAG_MAX_SALES);
                        String investor_currency_from = obj1.getString(TAG_INVESTOR_CURRENCY_FROM);
                        String investor_currency_to = obj1.getString(TAG_INVESTOR_CURRENCY_TO);
                        String franchise_min_investment = obj1.getString(TAG_FRANCHISE_MIN_INVESTMENT);
                        String franchise_max_investment = obj1.getString(TAG_FRANCHISE_MAX_INVESTMENT);
                        String franchise_min_revenue = obj1.getString(TAG_FRANCHISE_MIN_REVENUE);
                        String franchise_max_revenue = obj1.getString(TAG_FRANCHISE_MAX_REVENUE);
                        String franchise_min_established = obj1.getString(TAG_FRANCHISE_MIN_ESTABLISHED);
                        String franchise_max_established = obj1.getString(TAG_FRANCHISE_MAX_ESTABLISHED);

                        float flt_min_buisness_investment = Float.parseFloat(min_buisness_investment);
                        float flt_max_buisness_investment = Float.parseFloat(max_buisness_investment);
                        float flt_min_business_ebitda = Float.parseFloat(min_business_ebitda);
                        float flt_max_business_ebitda = Float.parseFloat(max_business_ebitda);
                        float flt_min_user_established = Float.parseFloat(min_user_established);
                        float flt_max_user_established = Float.parseFloat(max_user_established);
                        float flt_min_assets_purchased = Float.parseFloat(min_assets_purchased);
                        float flt_max_assets_purchased = Float.parseFloat(max_assets_purchased);
                        float flt_min_sell_lease_price = Float.parseFloat(min_sell_lease_price);
                        float flt_max_sell_lease_price = Float.parseFloat(max_sell_lease_price);
                        float flt_min_sales = Float.parseFloat(min_sales);
                        float flt_max_sales = Float.parseFloat(max_sales);
                        float flt_investor_currency_from = Float.parseFloat(investor_currency_from);
                        float flt_investor_currency_to = Float.parseFloat(investor_currency_to);
                        float flt_franchise_min_investment = Float.parseFloat(franchise_min_investment);
                        float flt_franchise_max_investment = Float.parseFloat(franchise_max_investment);
                        float flt_franchise_min_revenue = Float.parseFloat(franchise_min_revenue);
                        float flt_franchise_max_revenue = Float.parseFloat(franchise_max_revenue);
                        float flt_franchise_min_established = Float.parseFloat(franchise_min_established);
                        float flt_franchise_max_established = Float.parseFloat(franchise_max_established);

                        if (str_main_filter.equals("Business For sale")) {


                            // Investment Size
                            seekbar_investment_size.setSteps(10);
                            seekbar_investment_size.setMinStartValue(flt_min_buisness_investment);
                            seekbar_investment_size.setMaxValue(flt_max_buisness_investment);
                            seekbar_investment_size.setOnRangeSeekbarChangeListener(new OnRangeSeekbarChangeListener() {
                                @Override
                                public void valueChanged(Number minValue, Number maxValue) {
                                    txt_investment_size_minimum.setText(String.valueOf(minValue));
                                    txt_investment_size_maximum.setText(String.valueOf(maxValue));
                                }
                            });

                            // Runrate Sales
                            seekbar_runrate_sales.setMinStartValue(flt_min_sales);
                            seekbar_runrate_sales.setMaxValue(flt_max_sales);
                            seekbar_runrate_sales.setOnRangeSeekbarChangeListener(new OnRangeSeekbarChangeListener() {
                                @Override
                                public void valueChanged(Number minValue, Number maxValue) {
                                    txt_runrate_sales_minimum.setText(String.valueOf(minValue));
                                    txt_runrate_sales_maximum.setText(String.valueOf(maxValue));
                                }
                            });

                            // EBITA
                            seekbar_ebitda.setMinStartValue(flt_min_business_ebitda);
                            seekbar_ebitda.setMaxValue(flt_max_business_ebitda);
                            seekbar_ebitda.setOnRangeSeekbarChangeListener(new OnRangeSeekbarChangeListener() {
                                @Override
                                public void valueChanged(Number minValue, Number maxValue) {
                                    txt_ebitda_minimum.setText(String.valueOf(minValue));
                                    txt_ebitda_maximum.setText(String.valueOf(maxValue));
                                }
                            });

                            // ESTABLISED
                            seekbar_established.setMinStartValue(flt_min_user_established);
                            seekbar_established.setMaxValue(flt_max_user_established);
                            seekbar_established.setOnRangeSeekbarChangeListener(new OnRangeSeekbarChangeListener() {
                                @Override
                                public void valueChanged(Number minValue, Number maxValue) {
                                    txt_established_minimum.setText(String.valueOf(minValue));
                                    txt_established_maximum.setText(String.valueOf(maxValue));
                                }
                            });

                        } else if (str_main_filter.equals("Investment Oppourtinites")) {

                            // Investment Size
                            seekbar_investment_size.setMinStartValue(flt_investor_currency_from);
                            seekbar_investment_size.setMaxValue(flt_investor_currency_to);
                            seekbar_investment_size.setOnRangeSeekbarChangeListener(new OnRangeSeekbarChangeListener() {
                                @Override
                                public void valueChanged(Number minValue, Number maxValue) {
                                    txt_investment_size_minimum.setText(String.valueOf(minValue));
                                    txt_investment_size_maximum.setText(String.valueOf(maxValue));
                                }
                            });

                        } else if (str_main_filter.equals("Franchise Oppourtinites")) {

                            System.out.println("CAME Inside Franchises");

                            // Investment Size
                            seekbar_investment_size.setSteps(10);
                            seekbar_investment_size.setMinStartValue(flt_franchise_min_investment);
                            seekbar_investment_size.setMaxValue(flt_franchise_max_investment);
                            seekbar_investment_size.setOnRangeSeekbarChangeListener(new OnRangeSeekbarChangeListener() {
                                @Override
                                public void valueChanged(Number minValue, Number maxValue) {
                                    txt_investment_size_minimum.setText(String.valueOf(minValue));
                                    txt_investment_size_maximum.setText(String.valueOf(maxValue));
                                }
                            });

                            // MONTHLY SALES
                            seekbar_monthly_sales.setMinStartValue(flt_franchise_min_revenue);
                            seekbar_monthly_sales.setMaxValue(flt_franchise_max_revenue);
                            seekbar_monthly_sales.setOnRangeSeekbarChangeListener(new OnRangeSeekbarChangeListener() {
                                @Override
                                public void valueChanged(Number minValue, Number maxValue) {
                                    txt_monthly_sales_minimum.setText(String.valueOf(minValue));
                                    txt_monthly_sales_maximum.setText(String.valueOf(maxValue));
                                }
                            });

                            // ESTABLISED
                            seekbar_established.setMinStartValue(flt_franchise_min_established);
                            seekbar_established.setMaxValue(flt_franchise_max_established);
                            seekbar_established.setOnRangeSeekbarChangeListener(new OnRangeSeekbarChangeListener() {
                                @Override
                                public void valueChanged(Number minValue, Number maxValue) {
                                    txt_established_minimum.setText(String.valueOf(minValue));
                                    txt_established_maximum.setText(String.valueOf(maxValue));
                                }
                            });

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

                System.out.println("currencye" + str_user_currency);

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
