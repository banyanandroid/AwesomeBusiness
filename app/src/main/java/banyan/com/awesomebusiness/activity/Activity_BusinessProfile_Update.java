package banyan.com.awesomebusiness.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.MultiAutoCompleteTextView;
import android.widget.Spinner;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.libaml.android.view.chip.ChipLayout;
import com.nguyenhoanglam.imagepicker.activity.ImagePicker;
import com.nguyenhoanglam.imagepicker.activity.ImagePickerActivity;
import com.nguyenhoanglam.imagepicker.model.Image;
import com.sdsmdg.tastytoast.TastyToast;
import com.tapadoo.alerter.Alerter;
import com.toptoche.searchablespinnerlibrary.SearchableSpinner;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import banyan.com.awesomebusiness.R;
import banyan.com.awesomebusiness.global.AppConfig;
import dmax.dialog.SpotsDialog;

/**
 * Created by CHANDRU on 24-Jul-17.
 */

public class Activity_BusinessProfile_Update extends AppCompatActivity {

    private Toolbar mToolbar;
    SpotsDialog dialog;
    public static RequestQueue queue;
    Button btn_add_pic, btn_submit;
    String TAG = "Auto_Res";
    String str_user_currency, str_user_id = "";

    String str_business_key = "";

    // PIC Upload

    String listString = "";
    String encodedstring = "";

    private ArrayList<Image> images = new ArrayList<>();

    ArrayList<String> Arraylist_image_encode = null;
    ArrayList<String> Arraylist_dummy = null;

    private int REQUEST_CODE_PICKER = 2000;

    TextView t1;

    CardView Cardview_spn_others, Cardview_spn_selling_leasing;

    public static final String TAG_ROLE_ID = "business_role_id";
    public static final String TAG_ROLE_NAME = "business_role_name";

    public static final String TAG_INTEREST_ID = "business_interest_id";
    public static final String TAG_INTEREST_NAME = "business_interest_name";

    public static final String TAG_SECTOR_NAME = "name";
    public static final String TAG_SECTOR_KEY = "key";
    public static final String TAG_SECTOR_TYPE = "type";

    public static final String TAG_LOC_PLACE = "place";
    public static final String TAG_LOC_KEY = "key";
    public static final String TAG_LOC_TYPE = "type";

    //To Get The Previously Entered Values
    public static final String TAG_BUSINESS_ID = "business_id";
    public static final String TAG_BUSINESS_KEY = "business_key";
    public static final String TAG_BUSINESS_SHORT_DES = "buisness_short_description";
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
    public static final String TAG_BUSINESS_MOBILE_CODE = "business_mobile_code";
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
    public static final String TAG_BUSINESS_CONTACT_DETAILS = "business_contact_details";
    public static final String TAG_BUSINESS_COMPANY_DETAILS = "business_company_details";
    public static final String TAG_BUSINESS_REASON = "business_reason";

    public static final String TAG_LOCATION_NAME = "location_name";
    public static final String TAG_LOCATION_KEY = "location_key";

    public static final String TAG_INDUSTRY_NAME = "industry_name";
    public static final String TAG_INDUSTRY_KEY = "industry_key";


    ArrayList<String> Arraylist_business_role_id = null;
    ArrayList<String> Arraylist_business_role_name = null;

    ArrayList<String> Arraylist_business_interest_id = null;
    ArrayList<String> Arraylist_business_interest_name = null;

    ArrayList<String> Arraylist_sector_name = null;
    ArrayList<String> Arraylist_sector_key = null;
    ArrayList<String> Arraylist_sector_type = null;

    /*Multi Select*/
    ArrayList<String> Arraylist_selected_sectorkey = null;
    ArrayList<String> Arraylist_selected_location = null;

    ArrayList<String> Arraylist_location_place = null;
    ArrayList<String> Arraylist_location_key = null;
    ArrayList<String> Arraylist_location_type = null;

    private ArrayAdapter<String> adapter_i_am;

    ArrayList<String> Arraylist_update_location = null;
    ArrayList<String> Arraylist_update_industries = null;

    String str_final_industry_update, str_final_location_update = "";

    /* Arralist fetched indestries list */
    ArrayList<String> Arraylist_fetched_industries = null;
    ArrayList<String> Arraylist_selected_final_industry = null;

    /* Arralist fetched Location list */
    ArrayList<String> Arraylist_fetched_location = null;
    ArrayList<String> Arraylist_selected_final_location = null;

    private ArrayAdapter<String> adapter_interested;

    SearchableSpinner spn_i_am, spn_interested_in;

    TextView txt_iam, txt_interested_in;

    String str_selected_role_id, str_selected_role_name = "";
    String str_selected_interest_id, str_selected_interest_name = "";
    String str_selected_update_role_name, str_selected_update_interest_name = "";

    // Strings To Post For JSON
    String str_name, str_company_name, str_mobile, str_official_email,
            str_business_established_year, str_no_of_permanent_employees, str_business_desc,
            str_business_highlights, str_business_all_prod_serv, str_business_facility_desc, str_avg_monthly_sales,
            str_latest_yearly_sales, str_EBITDA, str_physical_assests_value,
            str_tentative_selling_price, str_reason_for_sale, str_spn_business_legal_type = "";


    String str_ch_companydetails, str_ch_contactdetails, str_ch_yearly_sales_range,
            str_ch_display_EBITDA_range = "0";

    String str_final_business_sector, str_final_Business_Location = "";

    //Common
    EditText edt_name, edt_mobile, edt_company_name, edt_official_email,

    edt_business_established_year,
            edt_no_of_permanent_employees, edt_business_des, edt_business_highlights,
            edt_business_all_prod_serv, edt_business_facility_desc, edt_avg_monthly_sales, edt_latest_yearly_sales,
            edt_EBITDA, edt_physical_assests_value, edt_tentative_selling_price, edt_reason_for_sale;

    CheckBox chb_companydetails, chb_contatdetails, chb_display_EBITDA_as_range, chb_yearly_sales_range;

    Spinner spn_business_legal_type;

    //Selling or Leasing out business
    EditText edt_year_asset_purchased, edt_asset_seeking_to_sell, edt_asset_features, edt_asset_selling_leasing_price, edt_asset_selling_reason;

    MultiAutoCompleteTextView auto_bus_busineeslist, auto_bus_locationlist, auto_industries_use_asset, auto_asset_loation;

    Spinner spn_amount_fixed_for;

    String str_profile_type = "";

    String str_final_location,str_final_industries = "";

    String str_year_asset_purchased, str_asset_seeking_to_sell, str_asset_features, str_asset_selling_leasing_price, str_asset_selling_eason,
            str_industries_use_asset, str_asset_loation, str_amount_fixed_for;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_business_profile_update);

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

        btn_add_pic = (Button) findViewById(R.id.btn_add_photos);
        btn_submit = (Button) findViewById(R.id.bus_prof_update_btn_submit);

        // AutoCompleteTextView
        spn_i_am = (SearchableSpinner) findViewById(R.id.business_profile_autocomp_i_am);
        spn_i_am.setTitle("Select Yourr Role");
        spn_interested_in = (SearchableSpinner) findViewById(R.id.business_profile_autocomp_intersted);
        spn_interested_in.setTitle("Select Yourr Interest");

        txt_iam = (TextView) findViewById(R.id.bus_prof_upd_txt_your_role);
        txt_interested_in = (TextView) findViewById(R.id.bus_prof_upd_txt_your_interest);

        // Common Edittext
        edt_name = (EditText) findViewById(R.id.edt_name);
        edt_mobile = (EditText) findViewById(R.id.edt_mobile_number);
        edt_company_name = (EditText) findViewById(R.id.edt_company_name);
        edt_official_email = (EditText) findViewById(R.id.edt_official_email);

        edt_business_established_year = (EditText) findViewById(R.id.edt_when_established);
        edt_no_of_permanent_employees = (EditText) findViewById(R.id.edt_permanant_employees);
        edt_business_des = (EditText) findViewById(R.id.edt_business_desc);
        edt_business_highlights = (EditText) findViewById(R.id.edt_business_highlights);
        edt_business_all_prod_serv = (EditText) findViewById(R.id.edt_list_product_services);
        edt_business_facility_desc = (EditText) findViewById(R.id.edt_facility_desc);
        edt_avg_monthly_sales = (EditText) findViewById(R.id.edt_avg_mnthly_sales);
        edt_latest_yearly_sales = (EditText) findViewById(R.id.edt_latest_yearly_sales);
        edt_EBITDA = (EditText) findViewById(R.id.edt_EBITDA_operating_profit_margin);
        edt_physical_assests_value = (EditText) findViewById(R.id.edt_phy_assests_value);
        edt_tentative_selling_price = (EditText) findViewById(R.id.edt_tentative_selling_price);
        edt_reason_for_sale = (EditText) findViewById(R.id.edt_reason_for_sale);

        chb_companydetails = (CheckBox) findViewById(R.id.chbx_display_company_details);
        chb_contatdetails = (CheckBox) findViewById(R.id.chbx_display_contact_details);
        chb_yearly_sales_range = (CheckBox) findViewById(R.id.chb_yearly_sales_range);
        chb_display_EBITDA_as_range = (CheckBox) findViewById(R.id.chb_editba_range);

        spn_business_legal_type = (Spinner) findViewById(R.id.spn_business_legel_entity);

        auto_bus_busineeslist = (MultiAutoCompleteTextView) findViewById(R.id.business_profile_multi_busi_industry);
        auto_bus_locationlist = (MultiAutoCompleteTextView) findViewById(R.id.business_profile_multi_busi_loca_at);

        auto_industries_use_asset = (MultiAutoCompleteTextView) findViewById(R.id.business_profile_multi_Industries_use_asset);
        auto_asset_loation = (MultiAutoCompleteTextView) findViewById(R.id.business_profile_multi_asset_loca_at);

        //Selling or Leasing out business
        edt_year_asset_purchased = (EditText) findViewById(R.id.edt_loan_when_asset_purchased);
        edt_asset_seeking_to_sell = (EditText) findViewById(R.id.edt_wt_asset_sell);
        edt_asset_features = (EditText) findViewById(R.id.edt_asset_features);
        edt_asset_selling_leasing_price = (EditText) findViewById(R.id.edt_price_selling_leasing);
        edt_asset_selling_reason = (EditText) findViewById(R.id.edt_reason_for_sell_asset);

        spn_amount_fixed_for = (Spinner) findViewById(R.id.spn_amount_for);

        Cardview_spn_others = (CardView) findViewById(R.id.card_view_three);
        Cardview_spn_selling_leasing = (CardView) findViewById(R.id.card_view_interested_leasing_business);

        Cardview_spn_others.setVisibility(View.GONE);
        Cardview_spn_selling_leasing.setVisibility(View.GONE);

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        str_user_id = sharedPreferences.getString("str_user_id", "str_user_id");
        str_user_currency = sharedPreferences.getString("str_selected_currency", "str_selected_currency");
        str_business_key = sharedPreferences.getString("business_key", "business_key");

        System.out.println("user ID :::::: " + str_user_id + "user currency :::::: " + str_user_currency);

        Arraylist_business_role_id = new ArrayList<String>();
        Arraylist_business_role_name = new ArrayList<String>();

        Arraylist_business_interest_id = new ArrayList<String>();
        Arraylist_business_interest_name = new ArrayList<String>();

        Arraylist_sector_name = new ArrayList<String>();
        Arraylist_sector_key = new ArrayList<String>();
        Arraylist_sector_type = new ArrayList<String>();

        Arraylist_selected_sectorkey = new ArrayList<String>();
        Arraylist_selected_location = new ArrayList<String>();

        Arraylist_location_place = new ArrayList<String>();
        Arraylist_location_key = new ArrayList<String>();
        Arraylist_location_type = new ArrayList<String>();

        Arraylist_update_location = new ArrayList<String>();
        Arraylist_update_industries = new ArrayList<String>();

        Arraylist_fetched_location = new ArrayList<String>();
        Arraylist_selected_final_location = new ArrayList<String>();

        Arraylist_fetched_industries = new ArrayList<String>();
        Arraylist_selected_final_industry = new ArrayList<String>();


        // IMG PIC
        Arraylist_image_encode = new ArrayList<String>();
        Arraylist_dummy = new ArrayList<String>();



        btn_add_pic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ImagePicker();
            }
        });

        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // String Values According to checkbox state
                if (chb_companydetails.isChecked()) {
                    str_ch_companydetails = "1";
                }
                if (chb_contatdetails.isChecked()) {
                    str_ch_contactdetails = "1";
                }
                if (chb_yearly_sales_range.isChecked()) {
                    str_ch_yearly_sales_range = "1";
                }
                if (chb_display_EBITDA_as_range.isChecked()) {
                    str_ch_display_EBITDA_range = "1";
                }

                str_name = edt_name.getText().toString();
                str_company_name = edt_company_name.getText().toString();
                str_mobile = edt_mobile.getText().toString();
                str_official_email = edt_official_email.getText().toString();
                str_business_established_year = edt_business_established_year.getText().toString();
                str_no_of_permanent_employees = edt_no_of_permanent_employees.getText().toString();
                str_business_desc = edt_business_des.getText().toString();
                str_business_highlights = edt_business_highlights.getText().toString();
                str_business_all_prod_serv = edt_business_all_prod_serv.getText().toString();
                str_business_facility_desc = edt_business_facility_desc.getText().toString();
                str_avg_monthly_sales = edt_avg_monthly_sales.getText().toString();
                str_latest_yearly_sales = edt_latest_yearly_sales.getText().toString();
                str_EBITDA = edt_EBITDA.getText().toString();
                str_physical_assests_value = edt_physical_assests_value.getText().toString();
                str_tentative_selling_price = edt_tentative_selling_price.getText().toString();
                str_reason_for_sale = edt_reason_for_sale.getText().toString();

                str_selected_update_role_name = txt_iam.getText().toString();
                str_selected_update_interest_name = txt_interested_in.getText().toString();

                //Asset _ for sale
                str_year_asset_purchased = edt_year_asset_purchased.getText().toString();
                str_asset_seeking_to_sell = edt_asset_seeking_to_sell.getText().toString();
                str_asset_features = edt_asset_features.getText().toString();
                str_asset_selling_leasing_price = edt_asset_selling_leasing_price.getText().toString();
                str_asset_selling_eason = edt_asset_selling_reason.getText().toString();
                str_amount_fixed_for = spn_amount_fixed_for.getSelectedItem().toString();

                // Spinner Value to String
                str_spn_business_legal_type = spn_business_legal_type.getSelectedItem().toString();

                if (str_name.equals("")) {
                    edt_name.setFocusable(true);
                    TastyToast.makeText(getApplicationContext(), "Name Cannot be empty", TastyToast.LENGTH_LONG, TastyToast.WARNING);
                } else if (str_company_name.equals("")) {
                    edt_company_name.setFocusable(true);
                    TastyToast.makeText(getApplicationContext(), "Company Cannot be Empty", TastyToast.LENGTH_LONG, TastyToast.WARNING);
                } else if (str_mobile.equals("")) {
                    edt_mobile.setFocusable(true);
                    TastyToast.makeText(getApplicationContext(), "Mobile Number Cannot be Empty", TastyToast.LENGTH_LONG, TastyToast.WARNING);
                } else if (str_official_email.equals("")) {
                    edt_official_email.setFocusable(true);
                    TastyToast.makeText(getApplicationContext(), "Email Cannot be Empty", TastyToast.LENGTH_LONG, TastyToast.WARNING);
                }else if (str_selected_update_role_name == null || str_selected_update_role_name.isEmpty()) {
                    spn_i_am.setFocusable(true);
                    spn_i_am.setFocusableInTouchMode(true);
                    spn_i_am.requestFocus();
                    spn_i_am.performClick();
                    TastyToast.makeText(getApplicationContext(), "Select Your Role", TastyToast.LENGTH_LONG, TastyToast.WARNING);
                } else if (str_selected_update_interest_name == null || str_selected_update_interest_name.isEmpty()) {
                    spn_interested_in.setFocusable(true);
                    spn_interested_in.setFocusableInTouchMode(true);
                    spn_interested_in.requestFocus();
                    spn_interested_in.performClick();
                    TastyToast.makeText(getApplicationContext(), "Select Your Interest Type", TastyToast.LENGTH_LONG, TastyToast.WARNING);
                }

                if (!str_selected_update_interest_name.equals("Selling / Leasing  Assets")) {

                    System.out.println("str_final_business_sector  : " + str_final_business_sector);
                    System.out.println("str_final_Business_Location  : " + str_final_Business_Location);

                    /*****************************
                     * Get Multi Sector Details
                     * ************************/
                    String[] str_industries = auto_bus_busineeslist.getText().toString().split(", ");

                    Arraylist_fetched_industries.clear();
                    for (int i = 0; i < str_industries.length; i++) {
                        Arraylist_fetched_industries.add(str_industries[i]);
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

                    /*****************************
                     * Get Multi Location Details
                     * ************************/

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
                    System.out.println("FINAL SELECTED LOCATION :: " + str_final_location_update);

                    if (str_business_established_year.equals("")) {
                        edt_business_established_year.setError("Enter Year");
                        TastyToast.makeText(getApplicationContext(), "Year Cannot be Empty", TastyToast.LENGTH_LONG, TastyToast.WARNING);
                    } else if (str_final_industry_update.equals("") || str_final_industry_update.equals("null")) {
                        TastyToast.makeText(getApplicationContext(), "Select Business Sector", TastyToast.LENGTH_LONG, TastyToast.WARNING);
                    } else if (str_final_location_update.equals("")) {
                        TastyToast.makeText(getApplicationContext(), "Select Business Location", TastyToast.LENGTH_LONG, TastyToast.WARNING);
                    } else if (str_no_of_permanent_employees.equals("")) {
                        edt_no_of_permanent_employees.setError("Enter Permanent Employees");
                        TastyToast.makeText(getApplicationContext(), "Permanent Employees Cannot be Empty", TastyToast.LENGTH_LONG, TastyToast.WARNING);
                    } else if (str_spn_business_legal_type.equals("Select Business legal entity type")) {
                        spn_business_legal_type.setFocusable(true);
                        spn_business_legal_type.setFocusableInTouchMode(true);
                        spn_business_legal_type.requestFocus();
                        spn_business_legal_type.performClick();
                        TastyToast.makeText(getApplicationContext(), "Select Business legal entity type", TastyToast.LENGTH_LONG, TastyToast.WARNING);
                    } else if (str_business_desc.equals("")) {
                        edt_business_des.setError("Enter Business Description");
                        TastyToast.makeText(getApplicationContext(), "Business Description Cannot be Empty", TastyToast.LENGTH_LONG, TastyToast.WARNING);
                    } else if (str_business_highlights.equals("")) {
                        edt_business_highlights.setError("Enter Business Highlights");
                        TastyToast.makeText(getApplicationContext(), "Business Highlights Cannot be Empty", TastyToast.LENGTH_LONG, TastyToast.WARNING);
                    } else if (str_business_all_prod_serv.equals("")) {
                        edt_business_all_prod_serv.setError("Enter Products & Services");
                        TastyToast.makeText(getApplicationContext(), "Products & Services Cannot be Empty", TastyToast.LENGTH_LONG, TastyToast.WARNING);
                    } else if (str_business_facility_desc.equals("")) {
                        edt_business_facility_desc.setError("Enter Business Facility Description");
                        TastyToast.makeText(getApplicationContext(), "Business Facility Description Cannot be Empty", TastyToast.LENGTH_LONG, TastyToast.WARNING);
                    } else if (str_avg_monthly_sales.equals("")) {
                        edt_avg_monthly_sales.setError("Enter Average Monthly Sales");
                        TastyToast.makeText(getApplicationContext(), "Average Monthly Sales   Cannot be Empty", TastyToast.LENGTH_LONG, TastyToast.WARNING);
                    } else if (str_latest_yearly_sales.equals("")) {
                        edt_latest_yearly_sales.setError("Enter Latest Yearly Sales");
                        TastyToast.makeText(getApplicationContext(), "Yearly Sales Cannot be Empty", TastyToast.LENGTH_LONG, TastyToast.WARNING);
                    } else if (str_EBITDA.equals("")) {
                        edt_EBITDA.setError("Enter EBITDA");
                        TastyToast.makeText(getApplicationContext(), "EBITDA Cannot be Empty", TastyToast.LENGTH_LONG, TastyToast.WARNING);
                    } else if (str_physical_assests_value.equals("")) {
                        edt_physical_assests_value.setError("Please Enter Assets Value");
                        TastyToast.makeText(getApplicationContext(), " Assets Value Cannot be Empty", TastyToast.LENGTH_LONG, TastyToast.WARNING);
                    } else if (str_tentative_selling_price.equals("")) {
                        edt_tentative_selling_price.setError("Please Enter Tentative Selling Price");
                        TastyToast.makeText(getApplicationContext(), " Tentative Selling Price Cannot be Empty", TastyToast.LENGTH_LONG, TastyToast.WARNING);
                    } else if (str_reason_for_sale.equals("")) {
                        edt_reason_for_sale.setError("Please Enter Reason For Sale");
                        TastyToast.makeText(getApplicationContext(), "Reason For Sale Cannot be Empty", TastyToast.LENGTH_LONG, TastyToast.WARNING);
                    } else {
                        dialog = new SpotsDialog(Activity_BusinessProfile_Update.this);
                        dialog.show();
                        queue = Volley.newRequestQueue(Activity_BusinessProfile_Update.this);
                        Function_Submit_BusinessProfile();
                    }

                }else if(!str_selected_update_interest_name.equals("Selling / Leasing  Assets")){

                    /******************************
                     * Get Asset Multi Sector Details
                     * *************************/
                    String[] str_asset_industries = auto_industries_use_asset.getText().toString().split(", ");

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

                    /******************************
                     * Get Asset Multi Location Details
                     * *************************/

                    String[] str_asset_location = auto_asset_loation.getText().toString().split(", ");

                    for (int i = 0; i < str_asset_location.length; i++) {
                        Arraylist_fetched_location.add(str_asset_location[i]);
                    }
                    System.out.println("array : " + Arraylist_fetched_location);

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
                    System.out.println("FINAL SELECTED LOCATION :: " + str_final_location_update);


                    if (str_year_asset_purchased.equals("")) {
                        edt_year_asset_purchased.setFocusable(true);
                        TastyToast.makeText(getApplicationContext(), "Purchased Year Cannot be Empty", TastyToast.LENGTH_LONG, TastyToast.WARNING);
                    } else if (str_final_industry_update.equals("")) {
                        TastyToast.makeText(getApplicationContext(), "Select Assets Sector", TastyToast.LENGTH_LONG, TastyToast.WARNING);
                    } else if (str_final_location_update.equals("")) {
                        TastyToast.makeText(getApplicationContext(), "Select Assets Location", TastyToast.LENGTH_LONG, TastyToast.WARNING);
                    } else if (str_asset_seeking_to_sell.equals("")) {
                        edt_asset_seeking_to_sell.setFocusable(true);
                        TastyToast.makeText(getApplicationContext(), "This Cannot be Empty", TastyToast.LENGTH_LONG, TastyToast.WARNING);
                    } else if (str_asset_features.equals("")) {
                        edt_asset_features.setFocusable(true);
                        TastyToast.makeText(getApplicationContext(), "Features Cannot be Empty", TastyToast.LENGTH_LONG, TastyToast.WARNING);
                    } else if (str_asset_selling_leasing_price.equals("")) {
                        edt_asset_selling_leasing_price.setFocusable(true);
                        TastyToast.makeText(getApplicationContext(), "Features Cannot be Empty", TastyToast.LENGTH_LONG, TastyToast.WARNING);
                    } else if (str_amount_fixed_for.equals("Above Amount Is Fixed For") || str_asset_selling_leasing_price.equals("")) {
                        spn_amount_fixed_for.setFocusable(true);
                        TastyToast.makeText(getApplicationContext(), "This Cannot be Empty", TastyToast.LENGTH_LONG, TastyToast.WARNING);
                    } else if (str_asset_selling_eason.equals("")) {
                        edt_asset_selling_reason.setFocusable(true);
                        TastyToast.makeText(getApplicationContext(), "Reason For Sale Cannot be Empty", TastyToast.LENGTH_LONG, TastyToast.WARNING);
                    } else {

                        if (str_user_currency.equals("str_selected_currency")) {
                            TastyToast.makeText(getApplicationContext(), "Please Update your profile Before Post", TastyToast.LENGTH_LONG, TastyToast.WARNING);
                        } else {
                            dialog = new SpotsDialog(Activity_BusinessProfile_Update.this);
                            dialog.show();
                            queue = Volley.newRequestQueue(Activity_BusinessProfile_Update.this);
                            Function_Submit_BusinessProfile();
                        }
                    }
                }

               /* if (str_business_established_year.equals("")) {
                    edt_business_established_year.setError("Enter Year");
                    TastyToast.makeText(getApplicationContext(), "Year Cannot be Empty", TastyToast.LENGTH_LONG, TastyToast.WARNING);
                } else if (str_no_of_permanent_employees.equals("")) {
                    edt_no_of_permanent_employees.setError("Enter Permanent Employees");
                    TastyToast.makeText(getApplicationContext(), "Permanent Employees Cannot be Empty", TastyToast.LENGTH_LONG, TastyToast.WARNING);
                } else if (str_business_desc.equals("")) {
                    edt_business_des.setError("Enter Business Description");
                    TastyToast.makeText(getApplicationContext(), "Business Description Cannot be Empty", TastyToast.LENGTH_LONG, TastyToast.WARNING);
                } else if (str_business_highlights.equals("")) {
                    edt_business_highlights.setError("Enter Business Highlights");
                    TastyToast.makeText(getApplicationContext(), "Business Highlights Cannot be Empty", TastyToast.LENGTH_LONG, TastyToast.WARNING);
                } else if (str_business_all_prod_serv.equals("")) {
                    edt_business_all_prod_serv.setError("Enter Products & Services");
                    TastyToast.makeText(getApplicationContext(), "Products & Services Cannot be Empty", TastyToast.LENGTH_LONG, TastyToast.WARNING);
                } else if (str_business_facility_desc.equals("")) {
                    edt_business_facility_desc.setError("Enter Business Facility Description");
                    TastyToast.makeText(getApplicationContext(), "Business Facility Description Cannot be Empty", TastyToast.LENGTH_LONG, TastyToast.WARNING);
                } else if (str_avg_monthly_sales.equals("")) {
                    edt_avg_monthly_sales.setError("Enter Average Monthly Sales");
                    TastyToast.makeText(getApplicationContext(), "Average Monthly Sales   Cannot be Empty", TastyToast.LENGTH_LONG, TastyToast.WARNING);
                } else if (str_latest_yearly_sales.equals("")) {
                    edt_latest_yearly_sales.setError("Enter Latest Yearly Sales");
                    TastyToast.makeText(getApplicationContext(), "Yearly Sales Cannot be Empty", TastyToast.LENGTH_LONG, TastyToast.WARNING);
                } else if (str_EBITDA.equals("")) {
                    edt_EBITDA.setError("Enter EBITDA");
                    TastyToast.makeText(getApplicationContext(), "EBITDA Cannot be Empty", TastyToast.LENGTH_LONG, TastyToast.WARNING);
                } else if (str_physical_assests_value.equals("")) {
                    edt_physical_assests_value.setError("Please Enter Assets Value");
                    TastyToast.makeText(getApplicationContext(), " Assets Value Cannot be Empty", TastyToast.LENGTH_LONG, TastyToast.WARNING);
                } else if (str_tentative_selling_price.equals("")) {
                    edt_tentative_selling_price.setError("Please Enter Tentative Selling Price");
                    TastyToast.makeText(getApplicationContext(), " Tentative Selling Price Cannot be Empty", TastyToast.LENGTH_LONG, TastyToast.WARNING);
                } else if (str_reason_for_sale.equals("")) {
                    edt_reason_for_sale.setError("Please Enter Reason For Sale");
                    TastyToast.makeText(getApplicationContext(), "Reason For Sale Cannot be Empty", TastyToast.LENGTH_LONG, TastyToast.WARNING);
                } else if (str_spn_business_legal_type.equals("Select Business legal entity type")) {
                    spn_business_legal_type.setFocusable(true);
                    spn_business_legal_type.setFocusableInTouchMode(true);
                    spn_business_legal_type.requestFocus();
                    spn_business_legal_type.performClick();
                    TastyToast.makeText(getApplicationContext(), "Select Business legal entity type", TastyToast.LENGTH_LONG, TastyToast.WARNING);
                } else if (str_final_business_sector.equals("")) {
                    TastyToast.makeText(getApplicationContext(), "Select Business Sector", TastyToast.LENGTH_LONG, TastyToast.WARNING);
                } else if (str_final_Business_Location.equals("")) {
                    TastyToast.makeText(getApplicationContext(), "Select Business Location", TastyToast.LENGTH_LONG, TastyToast.WARNING);
                } else if (str_selected_role_id.equals("")) {
                    spn_i_am.setFocusable(true);
                    spn_i_am.setFocusableInTouchMode(true);
                    spn_i_am.requestFocus();
                    spn_i_am.performClick();
                    TastyToast.makeText(getApplicationContext(), "Select Your Role", TastyToast.LENGTH_LONG, TastyToast.WARNING);
                } else if (str_selected_interest_id.equals("")) {
                    spn_interested_in.setFocusable(true);
                    spn_interested_in.setFocusableInTouchMode(true);
                    spn_interested_in.requestFocus();
                    spn_interested_in.performClick();
                    TastyToast.makeText(getApplicationContext(), "Select Your Interest Type", TastyToast.LENGTH_LONG, TastyToast.WARNING);
                } else {
                    dialog = new SpotsDialog(Activity_BusinessProfile_Update.this);
                    dialog.show();
                    queue = Volley.newRequestQueue(Activity_BusinessProfile_Update.this);
                    Function_Submit_BusinessProfile();
                }*/
            }
        });

        try {
            dialog = new SpotsDialog(Activity_BusinessProfile_Update.this);
            dialog.show();
            queue = Volley.newRequestQueue(getApplicationContext());
            Get_Iam_an();
            queue = Volley.newRequestQueue(getApplicationContext());
            Get_Interested();

        } catch (Exception e) {
            // TODO: handle exception
        }
    }

    /*******************************
     *  PIC UPLOADER
     * ***************************/
    // Recomended builder
    public void ImagePicker() {
        ImagePicker.create(this)
                .folderMode(true) // set folder mode (false by default)
                .folderTitle("Folder") // folder selection title
                .imageTitle("Tap to select") // image selection title
                .single() // single mode
                .multi() // multi mode (default mode)
                .limit(10) // max images can be selected (999 by default)
                .showCamera(true) // show camera or not (true by default)
                .imageDirectory("Camera")   // captured image directory name ("Camera" folder by default)
                .origin(images) // original selected images, used in multi mode
                .start(REQUEST_CODE_PICKER); // start image picker activity with request code
    }

    // Traditional intent
    public void startWithIntent() {
        Intent intent = new Intent(this, ImagePickerActivity.class);

        intent.putExtra(ImagePickerActivity.INTENT_EXTRA_FOLDER_MODE, true);
        intent.putExtra(ImagePickerActivity.INTENT_EXTRA_MODE, ImagePickerActivity.MODE_MULTIPLE);
        intent.putExtra(ImagePickerActivity.INTENT_EXTRA_LIMIT, 10);
        intent.putExtra(ImagePickerActivity.INTENT_EXTRA_SHOW_CAMERA, true);
        intent.putExtra(ImagePickerActivity.INTENT_EXTRA_SELECTED_IMAGES, images);
        intent.putExtra(ImagePickerActivity.INTENT_EXTRA_FOLDER_TITLE, "Album");
        intent.putExtra(ImagePickerActivity.INTENT_EXTRA_IMAGE_TITLE, "Tap to select images");
        intent.putExtra(ImagePickerActivity.INTENT_EXTRA_IMAGE_DIRECTORY, "Camera");
        startActivityForResult(intent, REQUEST_CODE_PICKER);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CODE_PICKER && resultCode == RESULT_OK && data != null) {
            images = data.getParcelableArrayListExtra(ImagePickerActivity.INTENT_EXTRA_SELECTED_IMAGES);
            StringBuilder sb = new StringBuilder();
            for (int i = 0, l = images.size(); i < l; i++) {

                String str_img_path = images.get(i).getPath();

                Bitmap bmBitmap = BitmapFactory.decodeFile(str_img_path);
                ByteArrayOutputStream bao = new ByteArrayOutputStream();
                bmBitmap.compress(Bitmap.CompressFormat.JPEG, 25, bao);
                byte[] ba = bao.toByteArray();
                encodedstring = Base64.encodeToString(ba, 0);
                Log.e("base64", "-----" + encodedstring);

                Arraylist_image_encode.add(encodedstring);
            }

            Encode_Image1();
            // textView.setText(sb.toString());
        }
    }

    public void Encode_Image1() {

        for (String s : Arraylist_image_encode) {
            listString += s + "IMAGE:";
        }
    }

    /*********************************
     *  POST
     * *********************************/

    private void Function_Login() {

        String url_login = "http://epictech.in/apiawesome/index.php/apicontroller/addimage";

        StringRequest request = new StringRequest(Request.Method.POST,
                url_login, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d(TAG_LOC_KEY, response.toString());
                Log.d("USER_LOGIN", response.toString());
                try {
                    JSONObject obj = new JSONObject(response);
                    System.out.println("REG 00" + obj);

                    int success = obj.getInt("status");

                    System.out.println("REG" + success);

                    if (success == 1) {

                    } else {

                    }
                } catch (JSONException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();

                params.put("image", listString);
                System.out.println("IMG :: " + listString);

                return params;
            }
        };

        // Adding request to request queue
        queue.add(request);
    }


    /*****************************
     * To get  I am/an  Details
     ***************************/

    public void Get_Iam_an() {
        String tag_json_obj = "json_obj_req";
        System.out.println("STEP  1111111111111");
        StringRequest request = new StringRequest(Request.Method.POST,
                AppConfig.url_iam, new Response.Listener<String>() {


            @Override
            public void onResponse(String response) {
                Log.d(TAG, response.toString());

                try {
                    JSONObject obj = new JSONObject(response);
                    int success = obj.getInt("status");
                    System.out.println("STEP  22222222222");

                    if (success == 1) {

                        JSONArray arr;

                        arr = obj.getJSONArray("data");
                        System.out.println("STEP  33333333");

                        for (int i = 0; arr.length() > i; i++) {
                            JSONObject obj1 = arr.getJSONObject(i);

                            String role_key = obj1.getString(TAG_ROLE_ID);
                            String role_name = obj1.getString(TAG_ROLE_NAME);

                            Arraylist_business_role_id.add(role_key);
                            Arraylist_business_role_name.add(role_name);
                        }
                        try {

                            adapter_i_am = new ArrayAdapter<String>(Activity_BusinessProfile_Update.this,
                                    android.R.layout.simple_list_item_1, Arraylist_business_role_name);
                            spn_i_am.setAdapter(adapter_i_am);


                            spn_i_am.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
                                                        long arg3) {
                                    t1 = (TextView) arg1;
                                    str_selected_role_name = t1.getText().toString();
                                    str_selected_role_id = Arraylist_business_role_id.get(arg2);

                                    txt_iam.setText(""+str_selected_role_name);
                                }
                            });

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
     * To get  Interested in  Details
     ***************************/

    public void Get_Interested() {
        String tag_json_obj = "json_obj_req";
        StringRequest request = new StringRequest(Request.Method.POST,
                AppConfig.url_interested_in, new Response.Listener<String>() {

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

                            Arraylist_business_interest_id.add(interest_key);
                            Arraylist_business_interest_name.add(interest_name);
                        }
                        try {
                            adapter_interested = new ArrayAdapter<String>(Activity_BusinessProfile_Update.this,
                                    android.R.layout.simple_list_item_1, Arraylist_business_interest_name);
                            spn_interested_in.setAdapter(adapter_interested);


                            spn_interested_in.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
                                                        long arg3) {
                                    t1 = (TextView) arg1;
                                    str_selected_interest_name = t1.getText().toString();
                                    System.out.println("Argument " + arg2);
                                    str_selected_interest_id = Arraylist_business_interest_id.get(arg2);

                                    txt_interested_in.setText("" + str_selected_interest_name);

                                    try {
                                        if (str_selected_interest_name.equals("Selling / Leasing  Assets")) {
                                            Cardview_spn_others.setVisibility(View.GONE);
                                            Cardview_spn_selling_leasing.setVisibility(View.VISIBLE);
                                        } else if (!str_selected_interest_name.equals("Selling / Leasing  Assets")) {
                                            Cardview_spn_others.setVisibility(View.VISIBLE);
                                            Cardview_spn_selling_leasing.setVisibility(View.GONE);
                                        }
                                    } catch (Exception e) {

                                    }


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
     * To get  Business sector List
     ***************************/

    public void Get_Sector_List() {
        String tag_json_obj = "json_obj_req";
        System.out.println("CAME 1");
        StringRequest request = new StringRequest(Request.Method.POST,
                AppConfig.url_business, new Response.Listener<String>() {

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

                            ArrayAdapter<String> adapter_process = new ArrayAdapter<String>(Activity_BusinessProfile_Update.this,
                                    android.R.layout.simple_list_item_1, Arraylist_sector_name);
                            auto_bus_busineeslist.setAdapter(adapter_process);
                            auto_bus_busineeslist
                                    .setTokenizer(new MultiAutoCompleteTextView.CommaTokenizer());
                            auto_bus_busineeslist.setThreshold(1);

                            auto_industries_use_asset.setAdapter(adapter_process);
                            auto_industries_use_asset
                                    .setTokenizer(new MultiAutoCompleteTextView.CommaTokenizer());
                            auto_industries_use_asset.setThreshold(1);

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
     * To get  Business Location List
     ***************************/

    public void Get_Business_Location() {
        String tag_json_obj = "json_obj_req";
        System.out.println("CAME 1");
        StringRequest request = new StringRequest(Request.Method.POST,
                AppConfig.url_business_location, new Response.Listener<String>() {

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

                            String location_place = obj1.getString(TAG_LOC_PLACE);
                            String location_key = obj1.getString(TAG_LOC_KEY);
                            String location_type = obj1.getString(TAG_LOC_TYPE);

                            Arraylist_location_place.add(location_place);
                            Arraylist_location_key.add(location_key);
                            Arraylist_location_type.add(location_type);
                        }
                        try {

                            System.out.println("ARAAAAY :: " + Arraylist_location_place);

                            ArrayAdapter<String> adapter_location = new ArrayAdapter<String>(Activity_BusinessProfile_Update.this,
                                    android.R.layout.simple_list_item_1, Arraylist_location_place);

                            auto_bus_locationlist.setAdapter(adapter_location);
                            auto_bus_locationlist
                                    .setTokenizer(new MultiAutoCompleteTextView.CommaTokenizer());
                            auto_bus_locationlist.setThreshold(1);

                            auto_asset_loation.setAdapter(adapter_location);
                            auto_asset_loation
                                    .setTokenizer(new MultiAutoCompleteTextView.CommaTokenizer());
                            auto_asset_loation.setThreshold(1);

                        } catch (Exception e) {

                        }

                        try {
                            queue = Volley.newRequestQueue(getApplicationContext());
                            Get_Business_Profile();
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
     * GET Business Profile
     ***************************/

    public void Get_Business_Profile() {

        StringRequest request = new StringRequest(Request.Method.POST,
                AppConfig.url_user_business_profile_update, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d(TAG, response.toString());
                System.out.println("CAME RESPONSE ::: " + response.toString());

                try {

                    JSONObject obj = new JSONObject(response);
                    int success = obj.getInt("status");

                    if (success == 1) {

                        JSONArray arr_main;
                        JSONArray arr_location;
                        JSONArray arr_industry;


                        arr_main = obj.getJSONArray("data");

                        for (int i = 0; arr_main.length() > i; i++) {

                            JSONObject obj_data = arr_main.getJSONObject(i);

                            String business_id = obj_data.getString(TAG_BUSINESS_ID);
                            String business_key = obj_data.getString(TAG_BUSINESS_KEY);
                            String country_currency = obj_data.getString(TAG_COUNTRY_CURRENCY);
                            String business_currency = obj_data.getString(TAG_BUSINESS_CURRENCY);

                            String business_user_name = obj_data.getString(TAG_BUSINESS_USER_NAME); // user nme
                            String business_user_mobile = obj_data.getString(TAG_BUSINESS_USER_MOBILE); // user mobile
                           // String business_mobile_code = obj_data.getString(TAG_BUSINESS_MOBILE_CODE); // country code
                            String business_user_email = obj_data.getString(TAG_BUSINESS_USER_EMAIL); // user email
                            String business_company_name = obj_data.getString(TAG_BUSINESS_COMPANY_NAME); // user company name

                            String business_contact_details = obj_data.getString(TAG_BUSINESS_CONTACT_DETAILS); // chb_contact details
                            String business_company_details = obj_data.getString(TAG_BUSINESS_COMPANY_DETAILS); // chb_company details
                            String business_interest_name = obj_data.getString(TAG_BUSINESS_INTEREST_NAME); //spn intereted in
                            String business_user_interest_in = obj_data.getString(TAG_BUSINESS_USER_INTEREST_IN); // spn intereted in - selected items id
                            String business_role_name = obj_data.getString(TAG_BUSINESS_ROLE_NAME);  //spn select ur role
                            String business_user_role = obj_data.getString(TAG_BUSINESS_USER_ROLE); //spn select ur role - selected items id

                            String business_established = obj_data.getString(TAG_BUSINESS_ESTABLISHED);    //yr business purchased
                            String business_employee_count = obj_data.getString(TAG_BUSINESS_EMPLOYEE_COUNT); // no of permanant employees
                            String business_legal_entity_type = obj_data.getString(TAG_BUSINESS_LEGAL_ENTITY_TYPE); //spn business legal entity type
                            String buisness_short_description = obj_data.getString(TAG_BUSINESS_SHORT_DES); // describe business in single line
                            String buisness_description = obj_data.getString(TAG_BUISNESS_DESCRIPTION); // highlights of business
                            String buisness_products_services = obj_data.getString(TAG_BUISNESS_PRODUCTS_SERVICES); // list all products & services
                            String buisness_facility = obj_data.getString(TAG_BUISNESS_FACILITY); // describe ur facility
                            String business_month_sales = obj_data.getString(TAG_BUSINESS_MONTH_SALES); // business average monthly sales
                            String business_yearly_sales = obj_data.getString(TAG_BUSINESS_YEARLY_SALES); // latest yearly sales

                            String business_ebitda = obj_data.getString(TAG_BUSINESS_EBITDA); //EBITA operating profit margin
                            String business_ebitda_range = obj_data.getString(TAG_BUSINESS_EBITDA_RANGE); // chb_display as range
                            String buisness_investment = obj_data.getString(TAG_BUISNESS_INVESTMENT); // value physical assets owned by business
                            String business_tentative_price = obj_data.getString(TAG_BUSINESS_TENTATIVE_PRICE); //tentative selling price
                            String business_reason = obj_data.getString(TAG_BUSINESS_REASON);
                            String business_assets_purchased = obj_data.getString(TAG_BUSINESS_ASSETS_PURCHASED); // year asset purchased
                            //assets location - In another array
                            String business_assets_description = obj_data.getString(TAG_BUSINESS_ASSETS_DESCRIPTION); // what assets u seeking to sell
                            String business_assets_features = obj_data.getString(TAG_BUSINESS_ASSETS_FEATURES); // features of the asset
                            String business_sell_lease_price = obj_data.getString(TAG_BUSINESS_SELL_LEASE_PRICE); // what price u selling / leasing
                            String business_assets_reason = obj_data.getString(TAG_BUSINESS_ASSETS_REASON); //Reason for selling this asset

                            String business_sell_type = obj_data.getString(TAG_BUSINESS_SELL_TYPE); // spn amount fixed for

                            String business_sell_lease = obj_data.getString(TAG_BUSINESS_SELL_LEASE); // unknown -null -no need to set
                            String business_sell_lease_cost = obj_data.getString(TAG_BUSINESS_SELL_LEASE_COST); //unknown null -no need to set

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

                            try {

                                edt_name.setText("" + business_user_name);
                                edt_name.setEnabled(false);
                                edt_company_name.setText("" + business_company_name);
                                edt_company_name.setEnabled(false);
                                edt_mobile.setText("" + business_user_mobile);
                                edt_mobile.setEnabled(false);
                                edt_official_email.setText("" + business_user_email);
                                edt_official_email.setEnabled(false);

                                txt_iam.setText("" + business_user_role);
                                txt_interested_in.setText("" + business_interest_name);

                                if (business_interest_name.equals("Selling / Leasing  Assets")) {
                                    str_profile_type = "asset";
                                    Cardview_spn_others.setVisibility(View.GONE);
                                    Cardview_spn_selling_leasing.setVisibility(View.VISIBLE);
                                } else if (!business_interest_name.equals("Selling / Leasing  Assets")) {
                                    str_profile_type = "normal";
                                    Cardview_spn_others.setVisibility(View.VISIBLE);
                                    Cardview_spn_selling_leasing.setVisibility(View.GONE);
                                }

                                System.out.println("INSIDE LOCATION " + str_final_location);
                                System.out.println("INSIDE SECTOR " + str_final_industries);

                                auto_bus_busineeslist = (MultiAutoCompleteTextView) findViewById(R.id.business_profile_multi_busi_industry);
                                auto_bus_locationlist = (MultiAutoCompleteTextView) findViewById(R.id.business_profile_multi_busi_loca_at);

                                auto_industries_use_asset = (MultiAutoCompleteTextView) findViewById(R.id.business_profile_multi_Industries_use_asset);
                                auto_asset_loation = (MultiAutoCompleteTextView) findViewById(R.id.business_profile_multi_asset_loca_at);

                                auto_bus_busineeslist.setText("" + str_final_industries+", ");
                                auto_bus_locationlist.setText("" + str_final_location+", ");

                                auto_industries_use_asset.setText("" + str_final_industries+", ");
                                auto_asset_loation.setText("" + str_final_location+", ");

                                System.out.println("INSIDE AFTER LOCATION " + str_final_location);
                                System.out.println("INSIDE AFTER SECTOR " + str_final_industries);

                                edt_business_established_year.setText("" + business_established);
                                edt_no_of_permanent_employees.setText("" + business_employee_count);
                                edt_business_highlights.setText("" + buisness_description);
                                edt_business_des.setText("" + buisness_short_description);
                                edt_business_all_prod_serv.setText("" + buisness_products_services);
                                edt_business_facility_desc.setText("" + buisness_facility);
                                edt_avg_monthly_sales.setText("" + business_month_sales);
                                edt_latest_yearly_sales.setText("" + business_yearly_sales);
                                edt_EBITDA.setText("" + business_ebitda);
                                edt_physical_assests_value.setText("" + buisness_investment);
                                edt_tentative_selling_price.setText("" + business_tentative_price);
                                edt_reason_for_sale.setText("" + business_reason);
                                edt_year_asset_purchased.setText("" + business_assets_purchased);
                                edt_asset_seeking_to_sell.setText("" + business_assets_description);
                                edt_asset_features.setText("" + business_assets_features);
                                edt_asset_selling_leasing_price.setText("" + business_sell_lease_price);
                                edt_asset_selling_reason.setText("" + business_assets_reason);

                                // Setting Check/Uncheck for checkbox Display Contact Details
                                if (business_contact_details.equals("1")) {
                                    chb_contatdetails.setChecked(true);
                                } else {
                                    chb_contatdetails.setChecked(false);
                                }

                                // Setting Check/Uncheck for checkbox Display Company Details
                                if (business_company_details.equals("1")) {
                                    chb_companydetails.setChecked(true);
                                } else {
                                    chb_companydetails.setChecked(false);
                                }

                                // Setting Check/Uncheck for checkbox EBITDA Range
                                if (business_ebitda_range.equals("1")) {
                                    chb_display_EBITDA_as_range.setChecked(true);
                                } else {
                                    chb_display_EBITDA_as_range.setChecked(false);
                                }

                                // Setting Selected Item for Spinner - Legal Entity Type
                                if (business_legal_entity_type.equals("Sole Proprietorship/Sole Trader")) {

                                    spn_business_legal_type.setSelection(1);

                                } else if (business_legal_entity_type.equals("General Partnership")) {

                                    spn_business_legal_type.setSelection(2);

                                } else if (business_legal_entity_type.equals("Limited Liability Partnership (LLP)")) {
                                    spn_business_legal_type.setSelection(3);

                                } else if (business_legal_entity_type.equals("Limited Liability Company (LLC)")) {

                                    spn_business_legal_type.setSelection(4);

                                } else if (business_legal_entity_type.equals("Private Limited Company")) {

                                    spn_business_legal_type.setSelection(5);

                                } else if (business_legal_entity_type.equals("Public Limited Company")) {

                                    spn_business_legal_type.setSelection(6);

                                } else if (business_legal_entity_type.equals("S Corporation")) {

                                    spn_business_legal_type.setSelection(7);

                                } else if (business_legal_entity_type.equals("C Corporation")) {

                                    spn_business_legal_type.setSelection(8);

                                }

                                // Setting Selected Item for Spinner - Amount Is Fixed For
                                if (business_sell_type.equals("Sole Proprietorship/Sole Trader")) {

                                    spn_business_legal_type.setSelection(1);

                                } else if (business_sell_type.equals("General Partnership")) {

                                    spn_business_legal_type.setSelection(2);

                                } else if (business_sell_type.equals("Limited Liability Partnership (LLP)")) {

                                    spn_business_legal_type.setSelection(3);

                                }

                            } catch (Exception e) {

                            }
                        }

                    } else if (success == 0) {

                        dialog.dismiss();

                        Alerter.create(Activity_BusinessProfile_Update.this)
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
                Alerter.create(Activity_BusinessProfile_Update.this)
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
                    System.out.println("RESPONSE : " + response);
                    int success = obj.getInt("status");
                    if (success == 1) {
                        dialog.dismiss();

                        Alerter.create(Activity_BusinessProfile_Update.this)
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
                params.put("user_contact", str_ch_contactdetails);
                params.put("user_company", str_ch_companydetails);

                // String selected from dynamic values - Auto Complete
                params.put("role", str_selected_role_id);
                params.put("interest_in", str_selected_interest_id);

                // String selected from dynamic values - MultiSelect (chipset)
                params.put("locations", str_final_Business_Location);
                params.put("industry", str_final_business_sector);

                params.put("year", str_business_established_year);
                params.put("employee", str_no_of_permanent_employees);
                params.put("entitys", str_spn_business_legal_type);
                params.put("business_description", str_business_desc);
                params.put("highlights", str_business_highlights);
                params.put("services", str_business_all_prod_serv);
                params.put("business_facility", str_business_facility_desc);
                params.put("monthly_expected_sales", str_avg_monthly_sales);
                params.put("yearly_expected_sales", str_latest_yearly_sales);
                params.put("yearly_sales_range", str_ch_yearly_sales_range);
                params.put("ebitda", str_EBITDA);
                params.put("display", str_ch_display_EBITDA_range);
                params.put("physical_assets", str_physical_assests_value);
                params.put("tentative_price", str_tentative_selling_price);
                params.put("reason", str_reason_for_sale);

                // Strings got by shared prefrences
                params.put("user_id", str_user_id);
                params.put("user_currency", str_user_currency);
                params.put("image", listString);

                ////////////////


                System.out.println("user_name" + str_name);
                System.out.println("company_name" + str_company_name);
                System.out.println("mobile_no" + str_mobile);
                System.out.println("email" + str_official_email);
                System.out.println("user_contact" + str_ch_contactdetails);
                System.out.println("user_company" + str_ch_companydetails);
                System.out.println("role" + str_selected_role_id);
                System.out.println("interest_in" + str_selected_interest_id);
                System.out.println("locations" + str_final_Business_Location);
                System.out.println("industry" + str_final_business_sector);
                System.out.println("year" + str_business_established_year);
                System.out.println("employee" + str_no_of_permanent_employees);
                System.out.println("entitys" + str_spn_business_legal_type);
                System.out.println("business_description" + str_business_desc);
                System.out.println("highlights" + str_business_highlights);
                System.out.println("services" + str_business_all_prod_serv);
                System.out.println("business_facility" + str_business_facility_desc);
                System.out.println("monthly_expected_sales" + str_avg_monthly_sales);
                System.out.println("yearly_expected_sales" + str_latest_yearly_sales);
                System.out.println("yearly_sales_range" + str_ch_yearly_sales_range);
                System.out.println("ebitda" + str_EBITDA);
                System.out.println("display" + str_ch_display_EBITDA_range);
                System.out.println("physical_assets" + str_physical_assests_value);
                System.out.println("tentative_price" + str_tentative_selling_price);
                System.out.println("reason" + str_reason_for_sale);
                System.out.println("user_id" + str_user_id);
                System.out.println("user_currency" + str_user_currency);


                return params;
            }
        };
        queue.add(request);
    }
}


