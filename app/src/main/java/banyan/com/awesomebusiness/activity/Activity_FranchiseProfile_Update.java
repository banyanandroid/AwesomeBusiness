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
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.MultiAutoCompleteTextView;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

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
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import banyan.com.awesomebusiness.R;
import banyan.com.awesomebusiness.global.AppConfig;
import banyan.com.awesomebusiness.global.SessionManager;
import dmax.dialog.SpotsDialog;

/**
 * CREATED BY SCHAN ON 24-JUL-17.
 */

public class Activity_FranchiseProfile_Update extends AppCompatActivity {

    private Toolbar mToolbar;
    Button btn_submit, btn_add_faility_stores_pics, btn_add_brand_logo_pic;
    SpotsDialog dialog;
    public static RequestQueue queue;
    String TAG = "FRANCHISE PROFILE";
    TextView t1;
    String str_user_currency, str_franchise_key = "";

    SessionManager session;
    public static String str_user_id, str_user_name, str_user_email, str_user_photoo;

    //FOR IMAGE UPLOAD
    private int REQUEST_CODE_PICKER = 2000;
    private int REQUEST_CODE_PICKER1 = 2000;
    private ArrayList<Image> images = new ArrayList<>();
    private ArrayList<Image> image = new ArrayList<>();
    ArrayList<String> Arraylist_image_encode = null;
    String listString = "";
    String encodedstring = "";
    String encoded_logo = "";
    String image_type = "";

    public static final String TAG_FRANCHISE_ID = "franchise_id";
    public static final String TAG_FRANCHISE_KEY = "franchise_key";
    public static final String TAG_FRANCHISE_USER_NAME = "franchise_user_name";
    public static final String TAG_FRANCHISE_EMAIL = "franchise_email";
    public static final String TAG_FRANCHISE_MOBILE = "franchise_mobile";
    public static final String TAG_FRANCHISE_DESIGNATION = "franchise_designation";
    public static final String TAG_FRANCHISE_BRAND_NAME = "franchise_brand_name";
    public static final String TAG_FRANCHISE_BRAND_OFFERING = "franchise_brand_offering";
    public static final String TAG_FRANCHISE_BRAND_COMPANY = "franchise_brand_company";
    public static final String TAG_FRANCHISE_BRAND_SERVICES = "franchise_brand_services";
    public static final String TAG_FRANCHISE_BRAND_ESTABLISHED = "franchise_brand_established";
    public static final String TAG_FRANCHISE_BRAND_HEADQUATERS = "franchise_brand_headquaters_name";
    public static final String TAG_BRAND_SALEPARTNER_COUNT = "brand_salepartner_count";
    public static final String TAG_BRAND_SALEPARTNER_BEFORE_PARTNERING = "brand_salepartner_before_partnering";
    public static final String TAG_BRAND_SALEPARTNER_EXPECT = "brand_salepartner_expect";

    public static final String TAG_BRAND_SALEPARTNER_PROCEDURE = "brand_salepartner_procedure";
    public static final String TAG_FRANCHISE_FORMAT = "franchise_format";
    public static final String TAG_FRANCHISE_LOGO = "franchise_logo";
    public static final String TAG_FRANCHISE_CURRENCY = "franchise_currency";
    public static final String TAG_COUNTRY_CURRENCY = "country_currency";

    public static final String TAG_INDUSTRT_NAME = "name";
    public static final String TAG_INDUSTRY_KEY = "key";
    public static final String TAG_INDUSTRY_TYPE = "type";

    public static final String TAG_HEADQUATERS_PLACE = "place";
    public static final String TAG_HEADQUATERS_KEY = "key";
    public static final String TAG_HEADQUATERS_TYPE = "type";

    public static final String TAG_LOCATION_NAME = "location_name";
    public static final String TAG_LOCATION_KEY = "location_key";

    public static final String TAG_INDUSTRY_NAMEE = "industry_name";
    public static final String TAG_INDUSTRY_KEYY = "industry_key";


    //String Related to no of formats
    //FORMAT 1
    public static final String TAG_FRANCHISE_ID1 = "franchise_id1";
    public static final String TAG_FRANCHISE_FORMAT_NAME1 = "franchise_format_name1";
    public static final String TAG_FRANCHISE_FORMAT_MIN_INVESTMENT1 = "franchise_format_min_investment1";
    public static final String TAG_FRANCHISE_FORMAT_MAX_INVESTMENT1 = "franchise_format_max_investment1";
    public static final String TAG_FRANCHISE_FORMAT_FEE1 = "franchise_format_fee1";
    public static final String TAG_FRANCHISE_FORMAT_NO_OF_STAFFS1 = "franchise_format_no_of_staffs1";
    public static final String TAG_FRANCHISE_FORMAT_ROYALITY1 = "franchise_format_royality1";
    public static final String TAG_FRANCHISE_FORMAT_REVENUE1 = "franchise_format_revenue1";
    public static final String TAG_FRANCHISE_FORMAT_PROFIT1 = "franchise_format_profit1";
    public static final String TAG_FRANCHISE_FORMAT_ACT1 = "franchise_format_act1";
    public static final String TAG_FRANCHISE_FORMAT_MIN_SQFT1 = "franchise_format_min_sqft1";
    public static final String TAG_FRANCHISE_FORMAT_MAX_SQFT1 = "franchise_format_max_sqft1";

    //FORMAT 2
    public static final String TAG_FRANCHISE_ID2 = "franchise_id2";
    public static final String TAG_FRANCHISE_FORMAT_NAME2 = "franchise_format_name2";
    public static final String TAG_FRANCHISE_FORMAT_MIN_INVESTMENT2 = "franchise_format_min_investment2";
    public static final String TAG_FRANCHISE_FORMAT_MAX_INVESTMENT2 = "franchise_format_max_investment2";
    public static final String TAG_FRANCHISE_FORMAT_FEE2 = "franchise_format_fee2";
    public static final String TAG_FRANCHISE_FORMAT_NO_OF_STAFFS2 = "franchise_format_no_of_staffs2";
    public static final String TAG_FRANCHISE_FORMAT_ROYALITY2 = "franchise_format_royality2";
    public static final String TAG_FRANCHISE_FORMAT_REVENUE2 = "franchise_format_revenue2";
    public static final String TAG_FRANCHISE_FORMAT_PROFIT2 = "franchise_format_profit2";
    public static final String TAG_FRANCHISE_FORMAT_ACT2 = "franchise_format_act2";
    public static final String TAG_FRANCHISE_FORMAT_MIN_SQFT2 = "franchise_format_min_sqft2";
    public static final String TAG_FRANCHISE_FORMAT_MAX_SQFT2 = "franchise_format_max_sqft2";

    //FORMAT 3
    public static final String TAG_FRANCHISE_ID3 = "franchise_id3";
    public static final String TAG_FRANCHISE_FORMAT_NAME3 = "franchise_format_name3";
    public static final String TAG_FRANCHISE_FORMAT_MIN_INVESTMENT3 = "franchise_format_min_investment3";
    public static final String TAG_FRANCHISE_FORMAT_MAX_INVESTMENT3 = "franchise_format_max_investment3";
    public static final String TAG_FRANCHISE_FORMAT_FEE3 = "franchise_format_fee3";
    public static final String TAG_FRANCHISE_FORMAT_NO_OF_STAFFS3 = "franchise_format_no_of_staffs3";
    public static final String TAG_FRANCHISE_FORMAT_ROYALITY3 = "franchise_format_royality3";
    public static final String TAG_FRANCHISE_FORMAT_REVENUE3 = "franchise_format_revenue3";
    public static final String TAG_FRANCHISE_FORMAT_PROFIT3 = "franchise_format_profit3";
    public static final String TAG_FRANCHISE_FORMAT_ACT3 = "franchise_format_act3";
    public static final String TAG_FRANCHISE_FORMAT_MIN_SQFT3 = "franchise_format_min_sqft3";
    public static final String TAG_FRANCHISE_FORMAT_MAX_SQFT3 = "franchise_format_max_sqft3";

    //FORMAT 4
    public static final String TAG_FRANCHISE_ID4 = "franchise_id4";
    public static final String TAG_FRANCHISE_FORMAT_NAME4 = "franchise_format_name4";
    public static final String TAG_FRANCHISE_FORMAT_MIN_INVESTMENT4 = "franchise_format_min_investment4";
    public static final String TAG_FRANCHISE_FORMAT_MAX_INVESTMENT4 = "franchise_format_max_investment4";
    public static final String TAG_FRANCHISE_FORMAT_FEE4 = "franchise_format_fee4";
    public static final String TAG_FRANCHISE_FORMAT_NO_OF_STAFFS4 = "franchise_format_no_of_staffs4";
    public static final String TAG_FRANCHISE_FORMAT_ROYALITY4 = "franchise_format_royality4";
    public static final String TAG_FRANCHISE_FORMAT_REVENUE4 = "franchise_format_revenue4";
    public static final String TAG_FRANCHISE_FORMAT_PROFIT4 = "franchise_format_profit4";
    public static final String TAG_FRANCHISE_FORMAT_ACT4 = "franchise_format_act4";
    public static final String TAG_FRANCHISE_FORMAT_MIN_SQFT4 = "franchise_format_min_sqft4";
    public static final String TAG_FRANCHISE_FORMAT_MAX_SQFT4 = "franchise_format_max_sqft4";

    //FORMAT 5
    public static final String TAG_FRANCHISE_ID5 = "franchise_id5";
    public static final String TAG_FRANCHISE_FORMAT_NAME5 = "franchise_format_name5";
    public static final String TAG_FRANCHISE_FORMAT_MIN_INVESTMENT5 = "franchise_format_min_investment5";
    public static final String TAG_FRANCHISE_FORMAT_MAX_INVESTMENT5 = "franchise_format_max_investment5";
    public static final String TAG_FRANCHISE_FORMAT_FEE5 = "franchise_format_fee5";
    public static final String TAG_FRANCHISE_FORMAT_NO_OF_STAFFS5 = "franchise_format_no_of_staffs5";
    public static final String TAG_FRANCHISE_FORMAT_ROYALITY5 = "franchise_format_royality5";
    public static final String TAG_FRANCHISE_FORMAT_REVENUE5 = "franchise_format_revenue5";
    public static final String TAG_FRANCHISE_FORMAT_PROFIT5 = "franchise_format_profit5";
    public static final String TAG_FRANCHISE_FORMAT_ACT5 = "franchise_format_act5";
    public static final String TAG_FRANCHISE_FORMAT_MIN_SQFT5 = "franchise_format_min_sqft5";
    public static final String TAG_FRANCHISE_FORMAT_MAX_SQFT5 = "franchise_format_max_sqft5";

    //FORMAT 6
    public static final String TAG_FRANCHISE_ID6 = "franchise_id6";
    public static final String TAG_FRANCHISE_FORMAT_NAME6 = "franchise_format_name6";
    public static final String TAG_FRANCHISE_FORMAT_MIN_INVESTMENT6 = "franchise_format_min_investment6";
    public static final String TAG_FRANCHISE_FORMAT_MAX_INVESTMENT6 = "franchise_format_max_investment6";
    public static final String TAG_FRANCHISE_FORMAT_FEE6 = "franchise_format_fee6";
    public static final String TAG_FRANCHISE_FORMAT_NO_OF_STAFFS6 = "franchise_format_no_of_staffs6";
    public static final String TAG_FRANCHISE_FORMAT_ROYALITY6 = "franchise_format_royality6";
    public static final String TAG_FRANCHISE_FORMAT_REVENUE6 = "franchise_format_revenue6";
    public static final String TAG_FRANCHISE_FORMAT_PROFIT6 = "franchise_format_profit6";
    public static final String TAG_FRANCHISE_FORMAT_ACT6 = "franchise_format_act6";
    public static final String TAG_FRANCHISE_FORMAT_MIN_SQFT6 = "franchise_format_min_sqft6";
    public static final String TAG_FRANCHISE_FORMAT_MAX_SQFT6 = "franchise_format_max_sqft6";


    ArrayList<String> Arraylist_industry_name = null;
    ArrayList<String> Arraylist_industry_key = null;
    ArrayList<String> Arraylist_industry_type = null;

    /* Arralist fetched indestries list */
    ArrayList<String> Arraylist_fetched_industries = null;
    ArrayList<String> Arraylist_selected_final_industry = null;

    /* Arralist fetched Location list */
    ArrayList<String> Arraylist_fetched_location = null;

    ArrayList<String> Arraylist_selected_final_location = null;

    ArrayList<String> Arraylist_selected_industry_key = null;
    String str_final_industry = "";

    ArrayList<String> Arraylist_location_place = null;
    ArrayList<String> Arraylist_location_key = null;
    ArrayList<String> Arraylist_location_type = null;

    String str_select_item = "";

    ArrayList<String> Arraylist_selected_location = null;
    String str_final_Business_Location = "";

    ArrayList<String> Arraylist_expand_location_place = null;
    ArrayList<String> Arraylist_expand_location_key = null;
    ArrayList<String> Arraylist_expand_location_type = null;

    ArrayList<String> Arraylist_selected_expand_location = null;

    ArrayList<String> Arraylist_update_location = null;
    ArrayList<String> Arraylist_update_industries = null;

    AutoCompleteTextView auto_headquaters;

    //Multi Auto Complete Textview
    MultiAutoCompleteTextView auto_franchise_business_industry, auto_franchise_business_expand_locations;
    //Final location and industry and headquaters sring to post
    String str_final_headquaters, str_final_location_update, str_final_industry_update = "";

    // EDIT TEXT AND THEIR RELATED STRINGS
    EditText edt_name, edt_email, edt_mobile_num, edt_designation, edt_brand_name,
            edt_about_company, edt_all_prod, edt_no_of_sales_partners,
            edt_lookfor_in_salespartner, edt_kindof_support, edt_procedure_salespartner;

    String str_auth_person_name, str_email, str_mobile_num, str_designation,
            str_brand_name, str_about_company, str_all_prod_serv,
            str_no_of_salespartner, str_lookfor_in_salespartner, str_kindof_support, str_procedure_salespartner = "";

    // Spinner AND STRINGS FOR OPPORTUNITIES OFFERED
    Spinner spn_opportunities_offered;
    String str_selected_opportunity, str_opportunity_offered = "";

    // CARDVIEW FOR NO.OF SALES PARTNER FORMATS & ITS REQUIRED WIDGETS
    CardView cv_format_one, cv_format_two, cv_format_three, cv_format_four, cv_format_five, cv_format_six;
    Spinner spn_no_of_salespartner_formats;
    String str_no_of_formats = "";

    // STRING & SEARCHABLE SPINNER  FOR -- THE YEAR COMPANY'S OPERATIONS START
    Spinner spn_years_company_opr_start;
    String str_year_company_opr_start = "";

    //To get previous Values for edit
    String str_final_location, str_final_industries = "";

    // FORMAT 1
    EditText edt_format_name_1, edt_format_spaceneeded_minimum_1, edt_format_spaceneeded_maximum_1,
            edt_format_investment_needed_minimum_1, edt_format_investment_needed_maximum_1,
            edt_format_brand_fee_1, edt_format_staff_required_1, edt_format_royalty_commission_1,
            edt_format_salespartner_monthly_revenue_1, edt_format_operating_profitmargin_1;
    // FORMAT 1 - STRINGS
    String str_format_name_1, str_format_spaceneeded_minimum_1, str_format_spaceneeded_maximum_1,
            str_format_investment_needed_minimum_1, str_format_investment_needed_maximum_1,
            str_format_brand_fee_1, str_format_staff_required_1, str_format_royalty_commission_1,
            str_format_salespartner_monthly_revenue_1, str_format_operating_profitmargin_1 = "";

    // FORMAT 2
    EditText edt_format_name_2, edt_format_spaceneeded_minimum_2, edt_format_spaceneeded_maximum_2,
            edt_format_investment_needed_minimum_2, edt_format_investment_needed_maximum_2,
            edt_format_brand_fee_2, edt_format_staff_required_2, edt_format_royalty_commission_2,
            edt_format_salespartner_monthly_revenue_2, edt_format_operating_profitmargin_2;
    // FORMAT 2 - STRINGS
    String str_format_name_2, str_format_spaceneeded_minimum_2, str_format_spaceneeded_maximum_2,
            str_format_investment_needed_minimum_2, str_format_investment_needed_maximum_2,
            str_format_brand_fee_2, str_format_staff_required_2, str_format_royalty_commission_2,
            str_format_salespartner_monthly_revenue_2, str_format_operating_profitmargin_2 = "";

    // FORMAT 3
    EditText edt_format_name_3, edt_format_spaceneeded_minimum_3, edt_format_spaceneeded_maximum_3,
            edt_format_investment_needed_minimum_3, edt_format_investment_needed_maximum_3,
            edt_format_brand_fee_3, edt_format_staff_required_3, edt_format_royalty_commission_3,
            edt_format_salespartner_monthly_revenue_3, edt_format_operating_profitmargin_3;
    // FORMAT 3 - STRINGS
    String str_format_name_3, str_format_spaceneeded_minimum_3, str_format_spaceneeded_maximum_3,
            str_format_investment_needed_minimum_3, str_format_investment_needed_maximum_3,
            str_format_brand_fee_3, str_format_staff_required_3, str_format_royalty_commission_3,
            str_format_salespartner_monthly_revenue_3, str_format_operating_profitmargin_3 = "";

    // FORMAT 4
    EditText edt_format_name_4, edt_format_spaceneeded_minimum_4, edt_format_spaceneeded_maximum_4,
            edt_format_investment_needed_minimum_4, edt_format_investment_needed_maximum_4,
            edt_format_brand_fee_4, edt_format_staff_required_4, edt_format_royalty_commission_4,
            edt_format_salespartner_monthly_revenue_4, edt_format_operating_profitmargin_4;
    // FORMAT 4 - STRINGS
    String str_format_name_4, str_format_spaceneeded_minimum_4, str_format_spaceneeded_maximum_4,
            str_format_investment_needed_minimum_4, str_format_investment_needed_maximum_4,
            str_format_brand_fee_4, str_format_staff_required_4, str_format_royalty_commission_4,
            str_format_salespartner_monthly_revenue_4, str_format_operating_profitmargin_4 = "";

    // FORMAT 5
    EditText edt_format_name_5, edt_format_spaceneeded_minimum_5, edt_format_spaceneeded_maximum_5,
            edt_format_investment_needed_minimum_5, edt_format_investment_needed_maximum_5,
            edt_format_brand_fee_5, edt_format_staff_required_5, edt_format_royalty_commission_5,
            edt_format_salespartner_monthly_revenue_5, edt_format_operating_profitmargin_5;
    // FORMAT 5 - STRINGS
    String str_format_name_5, str_format_spaceneeded_minimum_5, str_format_spaceneeded_maximum_5,
            str_format_investment_needed_minimum_5, str_format_investment_needed_maximum_5,
            str_format_brand_fee_5, str_format_staff_required_5, str_format_royalty_commission_5,
            str_format_salespartner_monthly_revenue_5, str_format_operating_profitmargin_5 = "";

    // FORMAT 6
    EditText edt_format_name_6, edt_format_spaceneeded_minimum_6, edt_format_spaceneeded_maximum_6,
            edt_format_investment_needed_minimum_6, edt_format_investment_needed_maximum_6,
            edt_format_brand_fee_6, edt_format_staff_required_6, edt_format_royalty_commission_6,
            edt_format_salespartner_monthly_revenue_6, edt_format_operating_profitmargin_6;
    // FORMAT 6 - STRINGS
    String str_format_name_6, str_format_spaceneeded_minimum_6, str_format_spaceneeded_maximum_6,
            str_format_investment_needed_minimum_6, str_format_investment_needed_maximum_6,
            str_format_brand_fee_6, str_format_staff_required_6, str_format_royalty_commission_6,
            str_format_salespartner_monthly_revenue_6, str_format_operating_profitmargin_6 = "";

    // Integers to check entered (Space neede , Investment needed)min values in formats is greater than the max value or not
    Integer int_format_1_space_needed_minimum, int_format_1_space_needed_maximum,
            int_format_2_space_needed_minimum, int_format_2_space_needed_maximum,
            int_format_3_space_needed_minimum, int_format_3_space_needed_maximum,
            int_format_4_space_needed_minimum, int_format_4_space_needed_maximum,
            int_format_5_space_needed_minimum, int_format_5_space_needed_maximum,
            int_format_6_space_needed_minimum, int_format_6_space_needed_maximum,
            int_format_1_investment_needed_minimum, int_format_1_investment_needed_maximum,
            int_format_2_investment_needed_minimum, int_format_2_investment_needed_maximum,
            int_format_3_investment_needed_minimum, int_format_3_investment_needed_maximum,
            int_format_4_investment_needed_minimum, int_format_4_investment_needed_maximum,
            int_format_5_investment_needed_minimum, int_format_5_investment_needed_maximum,
            int_format_6_investment_needed_minimum, int_format_6_investment_needed_maximum = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_franchise_profile_update);

        mToolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("Franchise Profile");
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
        str_franchise_key = sharedPreferences.getString("franchise_key", "franchise_key");

        Arraylist_industry_name = new ArrayList<String>();
        Arraylist_industry_key = new ArrayList<String>();
        Arraylist_industry_type = new ArrayList<String>();

        Arraylist_fetched_industries = new ArrayList<String>();
        Arraylist_selected_final_industry = new ArrayList<String>();

        Arraylist_selected_industry_key = new ArrayList<String>();

        Arraylist_location_place = new ArrayList<String>();
        Arraylist_location_key = new ArrayList<String>();
        Arraylist_location_type = new ArrayList<String>();

        Arraylist_fetched_location = new ArrayList<String>();
        Arraylist_selected_final_location = new ArrayList<String>();

        Arraylist_selected_location = new ArrayList<String>();

        Arraylist_expand_location_place = new ArrayList<String>();
        Arraylist_expand_location_key = new ArrayList<String>();
        Arraylist_expand_location_type = new ArrayList<String>();

        Arraylist_selected_expand_location = new ArrayList<String>();

        Arraylist_update_location = new ArrayList<String>();
        Arraylist_update_industries = new ArrayList<String>();

        //Image Upload
        Arraylist_image_encode = new ArrayList<String>();


        // ARRAYLIST , ARRAY ADAPTER , SEARCHABLE SPINNER  FOR -- THE YEAR COMPANY'S OPERATIONS START
        ArrayList<String> years = new ArrayList<String>();
        int CurerntYear = Calendar.getInstance().get(Calendar.YEAR);
        for (int i = 1900; i <= CurerntYear; i++) {
            years.add(Integer.toString(i));
        }

        ArrayAdapter<String> years_adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, years);
        spn_years_company_opr_start = (Spinner) findViewById(R.id.franchise_profile_spinner_years);
        spn_years_company_opr_start.setAdapter(years_adapter);

        //THE FOLLOWING STRING AND IF STATEMENT IS TO SET DEFAULT SELECTED YEAR FOR THE SPINNER
        String str_default_year = "2000";
        if (!str_default_year.equals(null)) {
            int spinnerPosition = years_adapter.getPosition(str_default_year);
            spn_years_company_opr_start.setSelection(spinnerPosition);
        }


        cv_format_one = (CardView) findViewById(R.id.card_view_format_one);
        cv_format_one.setVisibility(View.GONE);

        cv_format_two = (CardView) findViewById(R.id.card_view_format_two);
        cv_format_two.setVisibility(View.GONE);

        cv_format_three = (CardView) findViewById(R.id.card_view_format_three);
        cv_format_three.setVisibility(View.GONE);

        cv_format_four = (CardView) findViewById(R.id.card_view_format_four);
        cv_format_four.setVisibility(View.GONE);

        cv_format_five = (CardView) findViewById(R.id.card_view_format_five);
        cv_format_five.setVisibility(View.GONE);

        cv_format_six = (CardView) findViewById(R.id.card_view_format_six);
        cv_format_six.setVisibility(View.GONE);


        edt_name = (EditText) findViewById(R.id.edt_auth_person_name);
        edt_email = (EditText) findViewById(R.id.edt_official_email);
        edt_mobile_num = (EditText) findViewById(R.id.edt_mobile_number);
        edt_designation = (EditText) findViewById(R.id.edt_designation);
        edt_brand_name = (EditText) findViewById(R.id.edt_brandname);
        edt_about_company = (EditText) findViewById(R.id.edt_about_company);
        edt_all_prod = (EditText) findViewById(R.id.edt_list_product_services);
        edt_no_of_sales_partners = (EditText) findViewById(R.id.edt_no_of_sales_partners);
        edt_lookfor_in_salespartner = (EditText) findViewById(R.id.edt_wt_lookfor_sales_partners);
        edt_kindof_support = (EditText) findViewById(R.id.edt_wt_kindof_support);
        edt_procedure_salespartner = (EditText) findViewById(R.id.edt_wt_procedure);

        spn_no_of_salespartner_formats = (Spinner) findViewById(R.id.spn_sales_partners_format);
        spn_opportunities_offered = (Spinner) findViewById(R.id.spn_opportunities_offered);

        auto_headquaters = (AutoCompleteTextView) findViewById(R.id.edit_Franchise_profile_company_headquaters);
        auto_franchise_business_industry = (MultiAutoCompleteTextView) findViewById(R.id.franchise_profile_multi_busi_industry);
        auto_franchise_business_expand_locations = (MultiAutoCompleteTextView) findViewById(R.id.franchise_profile_multi_busi_expand_locations);


        //// NO.OF SALES PARTNER FORMATS EDITTEXT'S
        ////
        ////FORMAT 1
        edt_format_name_1 = (EditText) findViewById(R.id.edt_format_one_format_name);
        edt_format_spaceneeded_minimum_1 = (EditText) findViewById(R.id.edt_format_one_space_needed_one);
        edt_format_spaceneeded_maximum_1 = (EditText) findViewById(R.id.edt_format_one_space_needed_two);
        edt_format_investment_needed_minimum_1 = (EditText) findViewById(R.id.edt_format_one_inr_one);
        edt_format_investment_needed_maximum_1 = (EditText) findViewById(R.id.edt_format_one_inr_two);
        edt_format_brand_fee_1 = (EditText) findViewById(R.id.edt_format_one_wt_brand_fee);
        edt_format_staff_required_1 = (EditText) findViewById(R.id.edt_format_one_avg_no_of_staff);
        edt_format_royalty_commission_1 = (EditText) findViewById(R.id.edt_format_one_royalty_commission);
        edt_format_salespartner_monthly_revenue_1 = (EditText) findViewById(R.id.edt_format_one_avg_monthly_revenue);
        edt_format_operating_profitmargin_1 = (EditText) findViewById(R.id.edt_format_one_avg_profit_margin);

        ////FORMAT 2
        edt_format_name_2 = (EditText) findViewById(R.id.edt_format_two_format_name);
        edt_format_spaceneeded_minimum_2 = (EditText) findViewById(R.id.edt_format_two_space_needed_one);
        edt_format_spaceneeded_maximum_2 = (EditText) findViewById(R.id.edt_format_two_space_needed_two);
        edt_format_investment_needed_minimum_2 = (EditText) findViewById(R.id.edt_format_two_inr_one);
        edt_format_investment_needed_maximum_2 = (EditText) findViewById(R.id.edt_format_two_inr_two);
        edt_format_brand_fee_2 = (EditText) findViewById(R.id.edt_format_two_wt_brand_fee);
        edt_format_staff_required_2 = (EditText) findViewById(R.id.edt_format_two_avg_no_of_staff);
        edt_format_royalty_commission_2 = (EditText) findViewById(R.id.edt_format_two_royalty_commission);
        edt_format_salespartner_monthly_revenue_2 = (EditText) findViewById(R.id.edt_format_two_avg_monthly_revenue);
        edt_format_operating_profitmargin_2 = (EditText) findViewById(R.id.edt_format_two_avg_profit_margin);

        ////FORMAT 3
        edt_format_name_3 = (EditText) findViewById(R.id.edt_format_three_format_name);
        edt_format_spaceneeded_minimum_3 = (EditText) findViewById(R.id.edt_format_three_space_needed_one);
        edt_format_spaceneeded_maximum_3 = (EditText) findViewById(R.id.edt_format_three_space_needed_two);
        edt_format_investment_needed_minimum_3 = (EditText) findViewById(R.id.edt_format_three_inr_one);
        edt_format_investment_needed_maximum_3 = (EditText) findViewById(R.id.edt_format_three_inr_two);
        edt_format_brand_fee_3 = (EditText) findViewById(R.id.edt_format_three_wt_brand_fee);
        edt_format_staff_required_3 = (EditText) findViewById(R.id.edt_format_three_avg_no_of_staff);
        edt_format_royalty_commission_3 = (EditText) findViewById(R.id.edt_format_three_royalty_commission);
        edt_format_salespartner_monthly_revenue_3 = (EditText) findViewById(R.id.edt_format_three_avg_monthly_revenue);
        edt_format_operating_profitmargin_3 = (EditText) findViewById(R.id.edt_format_three_avg_profit_margin);

        ////FORMAT 4
        edt_format_name_4 = (EditText) findViewById(R.id.edt_format_four_format_name);
        edt_format_spaceneeded_minimum_4 = (EditText) findViewById(R.id.edt_format_four_space_needed_one);
        edt_format_spaceneeded_maximum_4 = (EditText) findViewById(R.id.edt_format_four_space_needed_two);
        edt_format_investment_needed_minimum_4 = (EditText) findViewById(R.id.edt_format_four_inr_one);
        edt_format_investment_needed_maximum_4 = (EditText) findViewById(R.id.edt_format_four_inr_two);
        edt_format_brand_fee_4 = (EditText) findViewById(R.id.edt_format_four_wt_brand_fee);
        edt_format_staff_required_4 = (EditText) findViewById(R.id.edt_format_four_avg_no_of_staff);
        edt_format_royalty_commission_4 = (EditText) findViewById(R.id.edt_format_four_royalty_commission);
        edt_format_salespartner_monthly_revenue_4 = (EditText) findViewById(R.id.edt_format_four_avg_monthly_revenue);
        edt_format_operating_profitmargin_4 = (EditText) findViewById(R.id.edt_format_four_avg_profit_margin);

        ////FORMAT 5
        edt_format_name_5 = (EditText) findViewById(R.id.edt_format_five_format_name);
        edt_format_spaceneeded_minimum_5 = (EditText) findViewById(R.id.edt_format_five_space_needed_one);
        edt_format_spaceneeded_maximum_5 = (EditText) findViewById(R.id.edt_format_five_space_needed_two);
        edt_format_investment_needed_minimum_5 = (EditText) findViewById(R.id.edt_format_five_inr_one);
        edt_format_investment_needed_maximum_5 = (EditText) findViewById(R.id.edt_format_five_inr_two);
        edt_format_brand_fee_5 = (EditText) findViewById(R.id.edt_format_five_wt_brand_fee);
        edt_format_staff_required_5 = (EditText) findViewById(R.id.edt_format_five_avg_no_of_staff);
        edt_format_royalty_commission_5 = (EditText) findViewById(R.id.edt_format_five_royalty_commission);
        edt_format_salespartner_monthly_revenue_5 = (EditText) findViewById(R.id.edt_format_five_avg_monthly_revenue);
        edt_format_operating_profitmargin_5 = (EditText) findViewById(R.id.edt_format_five_avg_profit_margin);

        ////FORMAT 6
        edt_format_name_6 = (EditText) findViewById(R.id.edt_format_six_format_name);
        edt_format_spaceneeded_minimum_6 = (EditText) findViewById(R.id.edt_format_six_space_needed_one);
        edt_format_spaceneeded_maximum_6 = (EditText) findViewById(R.id.edt_format_six_space_needed_two);
        edt_format_investment_needed_minimum_6 = (EditText) findViewById(R.id.edt_format_six_inr_one);
        edt_format_investment_needed_maximum_6 = (EditText) findViewById(R.id.edt_format_six_inr_two);
        edt_format_brand_fee_6 = (EditText) findViewById(R.id.edt_format_six_wt_brand_fee);
        edt_format_staff_required_6 = (EditText) findViewById(R.id.edt_format_six_avg_no_of_staff);
        edt_format_royalty_commission_6 = (EditText) findViewById(R.id.edt_format_six_royalty_commission);
        edt_format_salespartner_monthly_revenue_6 = (EditText) findViewById(R.id.edt_format_six_avg_monthly_revenue);
        edt_format_operating_profitmargin_6 = (EditText) findViewById(R.id.edt_format_six_avg_profit_margin);


        btn_add_faility_stores_pics = (Button) findViewById(R.id.btn_facility_photos);
        btn_add_brand_logo_pic = (Button) findViewById(R.id.btn_brand_logo);
        btn_submit = (Button) findViewById(R.id.btn_submit);

        btn_add_faility_stores_pics.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                image_type = "store_pics";
                ImagePicker();
            }
        });

        btn_add_brand_logo_pic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                image_type = "logo";
                ImagePicker1();
            }
        });

        //  HERE WE MAKE CARD VIEWS OF " NO.OF SALES PARTNER FORMATS "  VISIBLE OR INVISIBLE ACCORDING TO SPINNER VALUE SELECTION
        spn_no_of_salespartner_formats.setOnItemSelectedListener(
                new AdapterView.OnItemSelectedListener() {
                    public void onItemSelected(
                            AdapterView<?> parent, View view, int position, long id) {

                        str_no_of_formats = spn_no_of_salespartner_formats.getSelectedItem().toString();

                        if (str_no_of_formats.equals("No of Sales Partner Formats")) {

                            cv_format_one.setVisibility(View.GONE);
                            cv_format_two.setVisibility(View.GONE);
                            cv_format_three.setVisibility(View.GONE);
                            cv_format_four.setVisibility(View.GONE);
                            cv_format_five.setVisibility(View.GONE);
                            cv_format_six.setVisibility(View.GONE);

                        } else if (str_no_of_formats.equals("1")) {

                            cv_format_one.setVisibility(View.VISIBLE);

                            cv_format_two.setVisibility(View.GONE);
                            cv_format_three.setVisibility(View.GONE);
                            cv_format_four.setVisibility(View.GONE);
                            cv_format_five.setVisibility(View.GONE);
                            cv_format_six.setVisibility(View.GONE);


                        } else if (str_no_of_formats.equals("2")) {
                            cv_format_one.setVisibility(View.VISIBLE);
                            cv_format_two.setVisibility(View.VISIBLE);

                            cv_format_three.setVisibility(View.GONE);
                            cv_format_four.setVisibility(View.GONE);
                            cv_format_five.setVisibility(View.GONE);
                            cv_format_six.setVisibility(View.GONE);


                        } else if (str_no_of_formats.equals("3")) {
                            cv_format_one.setVisibility(View.VISIBLE);
                            cv_format_two.setVisibility(View.VISIBLE);
                            cv_format_three.setVisibility(View.VISIBLE);

                            cv_format_four.setVisibility(View.GONE);
                            cv_format_five.setVisibility(View.GONE);
                            cv_format_six.setVisibility(View.GONE);


                        } else if (str_no_of_formats.equals("4")) {
                            cv_format_one.setVisibility(View.VISIBLE);
                            cv_format_two.setVisibility(View.VISIBLE);
                            cv_format_three.setVisibility(View.VISIBLE);
                            cv_format_four.setVisibility(View.VISIBLE);

                            cv_format_five.setVisibility(View.GONE);
                            cv_format_six.setVisibility(View.GONE);


                        } else if (str_no_of_formats.equals("5")) {
                            cv_format_one.setVisibility(View.VISIBLE);
                            cv_format_two.setVisibility(View.VISIBLE);
                            cv_format_three.setVisibility(View.VISIBLE);
                            cv_format_four.setVisibility(View.VISIBLE);
                            cv_format_five.setVisibility(View.VISIBLE);

                            cv_format_six.setVisibility(View.GONE);


                        } else if (str_no_of_formats.equals("6")) {
                            cv_format_one.setVisibility(View.VISIBLE);
                            cv_format_two.setVisibility(View.VISIBLE);
                            cv_format_three.setVisibility(View.VISIBLE);
                            cv_format_four.setVisibility(View.VISIBLE);
                            cv_format_five.setVisibility(View.VISIBLE);
                            cv_format_six.setVisibility(View.VISIBLE);

                        }

                    }

                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });


        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                /*****************************
                 * Get Multi Industry Details
                 * ************************/
                String[] str_industries = auto_franchise_business_industry.getText().toString().split(", ");

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
                    int indus_position = Arraylist_industry_name.indexOf(get_indestry);
                    String select_sect_id = Arraylist_industry_key.get(indus_position);
                    String select_sect_type = Arraylist_industry_type.get(indus_position);

                    String sector = select_sect_id + "-" + select_sect_type;
                    Arraylist_selected_final_industry.add(sector);

                    str_final_industry_update = TextUtils.join(", ", Arraylist_selected_final_industry);

                }
                System.out.println("FINAL SELECTED INDUSTRYYYYYYYYYYYYY :: " + str_final_industry_update);


                /*****************************
                 * Get Multi Location Details
                 * ************************/

                String[] str_location = auto_franchise_business_expand_locations.getText().toString().split(", ");

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
                System.out.println("FINAL SELECTED LOCATIONNNNNNNNN :: " + str_final_location_update);


                ///////////////////////
                ///////  FOR GETTING ENTERED BUSINESS HEADQUATERS TYPE AND ID
                ///////////////////////

                String str_Headquaters = auto_headquaters.getText().toString();
                int Headquaters_position = Arraylist_location_place.indexOf(str_Headquaters);
                String select_Headquaters_id = Arraylist_location_key.get(Headquaters_position + 1);
                String select_Headquaters_type = Arraylist_location_type.get(Headquaters_position + 1);
                str_final_headquaters = select_Headquaters_id + "-" + select_Headquaters_type;
                System.out.println("FINAL SELECTED HEADQUATERS :: " + str_final_headquaters);


                ///////// TO GET THE SPINNER VALUE TO THE STRING
                str_no_of_formats = spn_no_of_salespartner_formats.getSelectedItem().toString();

                System.out.println("No OF FORMATSSSSSSS" + str_no_of_formats);


                str_selected_opportunity = spn_opportunities_offered.getSelectedItem().toString();

                System.out.println("OPPORTUNITIES OFFERED" + str_selected_opportunity);


                switch (str_selected_opportunity) {
                    case "":
                        spn_opportunities_offered.requestFocus();
                        TastyToast.makeText(getApplicationContext(), "Select one oppurtunity type", TastyToast.LENGTH_LONG, TastyToast.WARNING);

                    case "Select One":
                        spn_opportunities_offered.requestFocus();
                        TastyToast.makeText(getApplicationContext(), "Select one oppurtunity type", TastyToast.LENGTH_LONG, TastyToast.WARNING);

                    case "Franchise":
                        str_opportunity_offered = "1";
                        break;
                    case "Dealership":
                        str_opportunity_offered = "2";
                        break;
                    case "Reseller":
                        str_opportunity_offered = "3";
                        break;
                    case "Distributor":
                        str_opportunity_offered = "4";
                        break;
                    case "Sales Partner":
                        str_opportunity_offered = "5";
                        break;
                }


                ///////// GETTING THE VALUES FOR THE STRING FROM THEIR RELATED EDIT TEXT'S
                str_auth_person_name = edt_name.getText().toString();
                str_email = edt_email.getText().toString();
                str_mobile_num = edt_mobile_num.getText().toString();
                str_designation = edt_designation.getText().toString();
                str_brand_name = edt_brand_name.getText().toString();
                str_about_company = edt_about_company.getText().toString();
                str_all_prod_serv = edt_all_prod.getText().toString();
                str_no_of_salespartner = edt_no_of_sales_partners.getText().toString();
                str_lookfor_in_salespartner = edt_lookfor_in_salespartner.getText().toString();
                str_kindof_support = edt_kindof_support.getText().toString();
                str_procedure_salespartner = edt_procedure_salespartner.getText().toString();
                str_year_company_opr_start = spn_years_company_opr_start.getSelectedItem().toString();


                ///////// GETTING THE VALUES FOR THE STRING FROM THEIR RELATED NO OF FORMAT'S EDIT TEXT'S
                str_format_name_1 = edt_format_name_1.getText().toString();
                str_format_spaceneeded_minimum_1 = edt_format_spaceneeded_minimum_1.getText().toString();
                str_format_spaceneeded_maximum_1 = edt_format_spaceneeded_maximum_1.getText().toString();
                str_format_investment_needed_minimum_1 = edt_format_investment_needed_minimum_1.getText().toString();
                str_format_investment_needed_maximum_1 = edt_format_investment_needed_maximum_1.getText().toString();
                str_format_brand_fee_1 = edt_format_brand_fee_1.getText().toString();
                str_format_staff_required_1 = edt_format_staff_required_1.getText().toString();
                str_format_royalty_commission_1 = edt_format_royalty_commission_1.getText().toString();
                str_format_salespartner_monthly_revenue_1 = edt_format_salespartner_monthly_revenue_1.getText().toString();
                str_format_operating_profitmargin_1 = edt_format_operating_profitmargin_1.getText().toString();

                str_format_name_2 = edt_format_name_2.getText().toString();
                str_format_spaceneeded_minimum_2 = edt_format_spaceneeded_minimum_2.getText().toString();
                str_format_spaceneeded_maximum_2 = edt_format_spaceneeded_maximum_2.getText().toString();
                str_format_investment_needed_minimum_2 = edt_format_investment_needed_minimum_2.getText().toString();
                str_format_investment_needed_maximum_2 = edt_format_investment_needed_maximum_2.getText().toString();
                str_format_brand_fee_2 = edt_format_brand_fee_2.getText().toString();
                str_format_staff_required_2 = edt_format_staff_required_2.getText().toString();
                str_format_royalty_commission_2 = edt_format_royalty_commission_2.getText().toString();
                str_format_salespartner_monthly_revenue_2 = edt_format_salespartner_monthly_revenue_2.getText().toString();
                str_format_operating_profitmargin_2 = edt_format_operating_profitmargin_2.getText().toString();

                str_format_name_3 = edt_format_name_3.getText().toString();
                str_format_spaceneeded_minimum_3 = edt_format_spaceneeded_minimum_3.getText().toString();
                str_format_spaceneeded_maximum_3 = edt_format_spaceneeded_maximum_3.getText().toString();
                str_format_investment_needed_minimum_3 = edt_format_investment_needed_minimum_3.getText().toString();
                str_format_investment_needed_maximum_3 = edt_format_investment_needed_maximum_3.getText().toString();
                str_format_brand_fee_3 = edt_format_brand_fee_3.getText().toString();
                str_format_staff_required_3 = edt_format_staff_required_3.getText().toString();
                str_format_royalty_commission_3 = edt_format_royalty_commission_3.getText().toString();
                str_format_salespartner_monthly_revenue_3 = edt_format_salespartner_monthly_revenue_3.getText().toString();
                str_format_operating_profitmargin_3 = edt_format_operating_profitmargin_3.getText().toString();

                str_format_name_4 = edt_format_name_4.getText().toString();
                str_format_spaceneeded_minimum_4 = edt_format_spaceneeded_minimum_4.getText().toString();
                str_format_spaceneeded_maximum_4 = edt_format_spaceneeded_maximum_4.getText().toString();
                str_format_investment_needed_minimum_4 = edt_format_investment_needed_minimum_4.getText().toString();
                str_format_investment_needed_maximum_4 = edt_format_investment_needed_maximum_4.getText().toString();
                str_format_brand_fee_4 = edt_format_brand_fee_4.getText().toString();
                str_format_staff_required_4 = edt_format_staff_required_4.getText().toString();
                str_format_royalty_commission_4 = edt_format_royalty_commission_4.getText().toString();
                str_format_salespartner_monthly_revenue_4 = edt_format_salespartner_monthly_revenue_4.getText().toString();
                str_format_operating_profitmargin_4 = edt_format_operating_profitmargin_4.getText().toString();

                str_format_name_5 = edt_format_name_5.getText().toString();
                str_format_spaceneeded_minimum_5 = edt_format_spaceneeded_minimum_5.getText().toString();
                str_format_spaceneeded_maximum_5 = edt_format_spaceneeded_maximum_5.getText().toString();
                str_format_investment_needed_minimum_5 = edt_format_investment_needed_minimum_5.getText().toString();
                str_format_investment_needed_maximum_5 = edt_format_investment_needed_maximum_5.getText().toString();
                str_format_brand_fee_5 = edt_format_brand_fee_5.getText().toString();
                str_format_staff_required_5 = edt_format_staff_required_5.getText().toString();
                str_format_royalty_commission_5 = edt_format_royalty_commission_5.getText().toString();
                str_format_salespartner_monthly_revenue_5 = edt_format_salespartner_monthly_revenue_5.getText().toString();
                str_format_operating_profitmargin_5 = edt_format_operating_profitmargin_5.getText().toString();

                str_format_name_6 = edt_format_name_6.getText().toString();
                str_format_spaceneeded_minimum_6 = edt_format_spaceneeded_minimum_6.getText().toString();
                str_format_spaceneeded_maximum_6 = edt_format_spaceneeded_maximum_6.getText().toString();
                str_format_investment_needed_minimum_6 = edt_format_investment_needed_minimum_6.getText().toString();
                str_format_investment_needed_maximum_6 = edt_format_investment_needed_maximum_6.getText().toString();
                str_format_brand_fee_6 = edt_format_brand_fee_6.getText().toString();
                str_format_staff_required_6 = edt_format_staff_required_6.getText().toString();
                str_format_royalty_commission_6 = edt_format_royalty_commission_6.getText().toString();
                str_format_salespartner_monthly_revenue_6 = edt_format_salespartner_monthly_revenue_6.getText().toString();
                str_format_operating_profitmargin_6 = edt_format_operating_profitmargin_6.getText().toString();


                if (str_auth_person_name.equals("")) {
                    edt_name.setError("Enter authorized person Name");
                    edt_name.requestFocus();
                    TastyToast.makeText(getApplicationContext(), "Name Cannot be Empty", TastyToast.LENGTH_LONG, TastyToast.WARNING);
                } else if (str_email.equals("")) {
                    edt_email.setError("Enter Email");
                    edt_email.requestFocus();
                    TastyToast.makeText(getApplicationContext(), "Email Cannot be Empty", TastyToast.LENGTH_LONG, TastyToast.WARNING);
                } else if (str_mobile_num.equals("")) {
                    edt_mobile_num.setError("Enter Mobile Number");
                    edt_mobile_num.requestFocus();
                    TastyToast.makeText(getApplicationContext(), "Mobile Number Cannot be Empty", TastyToast.LENGTH_LONG, TastyToast.WARNING);
                } else if (str_designation.equals("")) {
                    edt_designation.setError("Enter Designation");
                    edt_designation.requestFocus();
                    TastyToast.makeText(getApplicationContext(), "Designation Cannot be Empty", TastyToast.LENGTH_LONG, TastyToast.WARNING);
                } else if (str_brand_name.equals("")) {
                    edt_brand_name.setError("Enter BrandName");
                    edt_brand_name.requestFocus();
                    TastyToast.makeText(getApplicationContext(), "Brand Name Cannot be Empty", TastyToast.LENGTH_LONG, TastyToast.WARNING);
                } else if (str_final_industry_update.equals("")) {
                    auto_franchise_business_industry.setError("Enter Industries");
                    auto_franchise_business_industry.requestFocus();
                    TastyToast.makeText(getApplicationContext(), "Industries Cannot be Empty", TastyToast.LENGTH_LONG, TastyToast.WARNING);
                } else if (str_about_company.equals("")) {
                    edt_about_company.setError("Enter about your company");
                    edt_about_company.requestFocus();
                    TastyToast.makeText(getApplicationContext(), "This Cannot be Empty", TastyToast.LENGTH_LONG, TastyToast.WARNING);
                } else if (str_all_prod_serv.equals("")) {
                    edt_all_prod.setError("Enter All Products & Services");
                    edt_all_prod.requestFocus();
                    TastyToast.makeText(getApplicationContext(), "This Cannot be Empty", TastyToast.LENGTH_LONG, TastyToast.WARNING);
                } else if (str_final_headquaters.equals("")) {
                    auto_headquaters.setError("Enter Company Headquaters");
                    auto_headquaters.requestFocus();
                    TastyToast.makeText(getApplicationContext(), "Company Headquaters Cannot be Empty", TastyToast.LENGTH_LONG, TastyToast.WARNING);
                } else if (str_no_of_salespartner.equals("")) {
                    edt_no_of_sales_partners.setError("Enter No Of Sales Partners");
                    edt_no_of_sales_partners.requestFocus();
                    TastyToast.makeText(getApplicationContext(), "This Cannot be Empty", TastyToast.LENGTH_LONG, TastyToast.WARNING);
                } else if (str_lookfor_in_salespartner.equals("")) {
                    edt_lookfor_in_salespartner.setError("Enter What do you look for in sales partner");
                    edt_lookfor_in_salespartner.requestFocus();
                    TastyToast.makeText(getApplicationContext(), "This Cannot be Empty", TastyToast.LENGTH_LONG, TastyToast.WARNING);
                } else if (str_kindof_support.equals("")) {
                    edt_kindof_support.setError("Enter what kind of support you need from sales partner");
                    edt_kindof_support.requestFocus();
                    TastyToast.makeText(getApplicationContext(), "This Cannot be Empty", TastyToast.LENGTH_LONG, TastyToast.WARNING);
                } else if (str_procedure_salespartner.equals("")) {
                    edt_procedure_salespartner.setError("Enter the procedure to become sales partner");
                    edt_procedure_salespartner.requestFocus();
                    TastyToast.makeText(getApplicationContext(), "This Cannot be Empty", TastyToast.LENGTH_LONG, TastyToast.WARNING);
                } else if (str_no_of_formats.equals("No of Sales Partner Formats")) {
                    TastyToast.makeText(getApplicationContext(), "Please Select No of Sales Partner Formats ", TastyToast.LENGTH_LONG, TastyToast.WARNING);
                }


                if (str_no_of_formats.equals("1")) {

                    // IF STATEMENT FOR VALIDATING STRINGS OF FORMAT 1(WHEN NO OF SELECTED FORMATS 1)
                    System.out.println("FORMATS COUNT:: 111111111111111111111 ");
                    if (str_format_name_1.equals("")) {
                        edt_format_name_1.setError("Enter Format Name");
                        TastyToast.makeText(getApplicationContext(), "Name Cannot be Empty", TastyToast.LENGTH_LONG, TastyToast.WARNING);
                    } else if (str_format_spaceneeded_minimum_1.equals("")) {
                        edt_format_spaceneeded_minimum_1.setError("Enter Minimum Space Needed");
                        TastyToast.makeText(getApplicationContext(), "This Cannot be Empty", TastyToast.LENGTH_LONG, TastyToast.WARNING);
                    } else if (str_format_spaceneeded_maximum_1.equals("")) {
                        edt_format_spaceneeded_maximum_1.setError("Enter Maximum Space Needed");
                        TastyToast.makeText(getApplicationContext(), "This Cannot be Empty", TastyToast.LENGTH_LONG, TastyToast.WARNING);
                    } else if (str_format_investment_needed_minimum_1.equals("")) {
                        edt_format_investment_needed_minimum_1.setError("Enter Minimum Investment Needed");
                        TastyToast.makeText(getApplicationContext(), "This Cannot be Empty", TastyToast.LENGTH_LONG, TastyToast.WARNING);
                    } else if (str_format_investment_needed_maximum_1.equals("")) {
                        edt_format_investment_needed_maximum_1.setError("Enter Maximum Investment Needed");
                        TastyToast.makeText(getApplicationContext(), "This Cannot be Empty", TastyToast.LENGTH_LONG, TastyToast.WARNING);
                    } else if (str_format_brand_fee_1.equals("")) {
                        edt_format_brand_fee_1.setError("Enter Brand Fee Included in this investment");
                        TastyToast.makeText(getApplicationContext(), "This Cannot be Empty", TastyToast.LENGTH_LONG, TastyToast.WARNING);
                    } else if (str_format_staff_required_1.equals("")) {
                        edt_format_staff_required_1.setError("Enter No Of Staff Required");
                        TastyToast.makeText(getApplicationContext(), "This Cannot be Empty", TastyToast.LENGTH_LONG, TastyToast.WARNING);
                    } else if (str_format_royalty_commission_1.equals("")) {
                        edt_format_royalty_commission_1.setError("Enter Royalty / Commission");
                        TastyToast.makeText(getApplicationContext(), "This Cannot be Empty", TastyToast.LENGTH_LONG, TastyToast.WARNING);
                    } else if (str_format_salespartner_monthly_revenue_1.equals("")) {
                        edt_format_salespartner_monthly_revenue_1.setError("Enter Sales Partner Monthly Revenue");
                        TastyToast.makeText(getApplicationContext(), "This Cannot be Empty", TastyToast.LENGTH_LONG, TastyToast.WARNING);
                    } else if (str_format_operating_profitmargin_1.equals("")) {
                        edt_format_operating_profitmargin_1.setError("Enter Operating Profit Margin %");
                        TastyToast.makeText(getApplicationContext(), "This Cannot be Empty", TastyToast.LENGTH_LONG, TastyToast.WARNING);
                    } else {

                        dialog = new SpotsDialog(Activity_FranchiseProfile_Update.this);
                        dialog.show();
                        queue = Volley.newRequestQueue(getApplicationContext());
                        Function_Submit_FranchiseProfile();


                    }


                } else if (str_no_of_formats.equals("2")) {

                    // IF STATEMENT FOR VALIDATING STRING OF FORMAT 1 (WHEN NO OF SELECTED FORMATS 2)


                    System.out.println("FORMATS COUNT:: 222222222222222222 ");

                    if (str_format_name_1.equals("")) {
                        edt_format_name_1.setError("Enter Format Name");
                        TastyToast.makeText(getApplicationContext(), "Name Cannot be Empty", TastyToast.LENGTH_LONG, TastyToast.WARNING);
                    } else if (str_format_spaceneeded_minimum_1.equals("")) {
                        edt_format_spaceneeded_minimum_1.setError("Enter Minimum Space Needed");
                        TastyToast.makeText(getApplicationContext(), "This Cannot be Empty", TastyToast.LENGTH_LONG, TastyToast.WARNING);
                    } else if (str_format_spaceneeded_maximum_1.equals("")) {
                        edt_format_spaceneeded_maximum_1.setError("Enter Maximum Space Needed");
                        TastyToast.makeText(getApplicationContext(), "This Cannot be Empty", TastyToast.LENGTH_LONG, TastyToast.WARNING);
                    } else if (str_format_investment_needed_minimum_1.equals("")) {
                        edt_format_investment_needed_minimum_1.setError("Enter Minimum Investment Needed");
                        TastyToast.makeText(getApplicationContext(), "This Cannot be Empty", TastyToast.LENGTH_LONG, TastyToast.WARNING);
                    } else if (str_format_investment_needed_maximum_1.equals("")) {
                        edt_format_investment_needed_maximum_1.setError("Enter Maximum Investment Needed");
                        TastyToast.makeText(getApplicationContext(), "This Cannot be Empty", TastyToast.LENGTH_LONG, TastyToast.WARNING);
                    } else if (str_format_brand_fee_1.equals("")) {
                        edt_format_brand_fee_1.setError("Enter Brand Fee Included in this investment");
                        TastyToast.makeText(getApplicationContext(), "This Cannot be Empty", TastyToast.LENGTH_LONG, TastyToast.WARNING);
                    } else if (str_format_staff_required_1.equals("")) {
                        edt_format_staff_required_1.setError("Enter No Of Staff Required");
                        TastyToast.makeText(getApplicationContext(), "This Cannot be Empty", TastyToast.LENGTH_LONG, TastyToast.WARNING);
                    } else if (str_format_royalty_commission_1.equals("")) {
                        edt_format_royalty_commission_1.setError("Enter Royalty / Commission");
                        TastyToast.makeText(getApplicationContext(), "This Cannot be Empty", TastyToast.LENGTH_LONG, TastyToast.WARNING);
                    } else if (str_format_salespartner_monthly_revenue_1.equals("")) {
                        edt_format_salespartner_monthly_revenue_1.setError("Enter Sales Partner Monthly Revenue");
                        TastyToast.makeText(getApplicationContext(), "This Cannot be Empty", TastyToast.LENGTH_LONG, TastyToast.WARNING);
                    } else if (str_format_operating_profitmargin_1.equals("")) {
                        edt_format_operating_profitmargin_1.setError("Enter Operating Profit Margin %");
                        TastyToast.makeText(getApplicationContext(), "This Cannot be Empty", TastyToast.LENGTH_LONG, TastyToast.WARNING);
                    }
                    // IF STATEMENT FOR VALIDATING STRING OF FORMAT 2 (WHEN NO OF SELECTED FORMATS 2)
                    else if (str_format_name_2.equals("")) {
                        edt_format_name_2.setError("Enter Format Name");
                        TastyToast.makeText(getApplicationContext(), "Name Cannot be Empty", TastyToast.LENGTH_LONG, TastyToast.WARNING);
                    } else if (str_format_spaceneeded_minimum_2.equals("")) {
                        edt_format_spaceneeded_minimum_2.setError("Enter Minimum Space Needed");
                        TastyToast.makeText(getApplicationContext(), "This Cannot be Empty", TastyToast.LENGTH_LONG, TastyToast.WARNING);
                    } else if (str_format_spaceneeded_maximum_2.equals("")) {
                        edt_format_spaceneeded_maximum_2.setError("Enter Maximum Space Needed");
                        TastyToast.makeText(getApplicationContext(), "This Cannot be Empty", TastyToast.LENGTH_LONG, TastyToast.WARNING);
                    } else if (str_format_investment_needed_minimum_2.equals("")) {
                        edt_format_investment_needed_minimum_2.setError("Enter Minimum Investment Needed");
                        TastyToast.makeText(getApplicationContext(), "This Cannot be Empty", TastyToast.LENGTH_LONG, TastyToast.WARNING);
                    } else if (str_format_investment_needed_maximum_2.equals("")) {
                        edt_format_investment_needed_maximum_2.setError("Enter Maximum Investment Needed");
                        TastyToast.makeText(getApplicationContext(), "This Cannot be Empty", TastyToast.LENGTH_LONG, TastyToast.WARNING);
                    } else if (str_format_brand_fee_2.equals("")) {
                        edt_format_brand_fee_2.setError("Enter Brand Fee Included in this investment");
                        TastyToast.makeText(getApplicationContext(), "This Cannot be Empty", TastyToast.LENGTH_LONG, TastyToast.WARNING);
                    } else if (str_format_staff_required_2.equals("")) {
                        edt_format_staff_required_2.setError("Enter No Of Staff Required");
                        TastyToast.makeText(getApplicationContext(), "This Cannot be Empty", TastyToast.LENGTH_LONG, TastyToast.WARNING);
                    } else if (str_format_royalty_commission_2.equals("")) {
                        edt_format_royalty_commission_2.setError("Enter Royalty / Commission");
                        TastyToast.makeText(getApplicationContext(), "This Cannot be Empty", TastyToast.LENGTH_LONG, TastyToast.WARNING);
                    } else if (str_format_salespartner_monthly_revenue_2.equals("")) {
                        edt_format_salespartner_monthly_revenue_2.setError("Enter Sales Partner Monthly Revenue");
                        TastyToast.makeText(getApplicationContext(), "This Cannot be Empty", TastyToast.LENGTH_LONG, TastyToast.WARNING);
                    } else if (str_format_operating_profitmargin_2.equals("")) {
                        edt_format_operating_profitmargin_2.setError("Enter Operating Profit Margin %");
                        TastyToast.makeText(getApplicationContext(), "This Cannot be Empty", TastyToast.LENGTH_LONG, TastyToast.WARNING);
                    } else {

                        dialog = new SpotsDialog(Activity_FranchiseProfile_Update.this);
                        dialog.show();
                        queue = Volley.newRequestQueue(getApplicationContext());
                        Function_Submit_FranchiseProfile();


                    }


                } else if (str_no_of_formats.equals("3")) {

                    // IF STATEMENT FOR VALIDATING STRING OF FORMAT 1 (WHEN NO OF SELECTED FORMATS 3)


                    System.out.println("FORMATS COUNT:: 333333333333333333 ");

                    if (str_format_name_1.equals("")) {
                        edt_format_name_1.setError("Enter Format Name");
                        TastyToast.makeText(getApplicationContext(), "Name Cannot be Empty", TastyToast.LENGTH_LONG, TastyToast.WARNING);
                    } else if (str_format_spaceneeded_minimum_1.equals("")) {
                        edt_format_spaceneeded_minimum_1.setError("Enter Minimum Space Needed");
                        TastyToast.makeText(getApplicationContext(), "This Cannot be Empty", TastyToast.LENGTH_LONG, TastyToast.WARNING);
                    } else if (str_format_spaceneeded_maximum_1.equals("")) {
                        edt_format_spaceneeded_maximum_1.setError("Enter Maximum Space Needed");
                        TastyToast.makeText(getApplicationContext(), "This Cannot be Empty", TastyToast.LENGTH_LONG, TastyToast.WARNING);
                    } else if (str_format_investment_needed_minimum_1.equals("")) {
                        edt_format_investment_needed_minimum_1.setError("Enter Minimum Investment Needed");
                        TastyToast.makeText(getApplicationContext(), "This Cannot be Empty", TastyToast.LENGTH_LONG, TastyToast.WARNING);
                    } else if (str_format_investment_needed_maximum_1.equals("")) {
                        edt_format_investment_needed_maximum_1.setError("Enter Maximum Investment Needed");
                        TastyToast.makeText(getApplicationContext(), "This Cannot be Empty", TastyToast.LENGTH_LONG, TastyToast.WARNING);
                    } else if (str_format_brand_fee_1.equals("")) {
                        edt_format_brand_fee_1.setError("Enter Brand Fee Included in this investment");
                        TastyToast.makeText(getApplicationContext(), "This Cannot be Empty", TastyToast.LENGTH_LONG, TastyToast.WARNING);
                    } else if (str_format_staff_required_1.equals("")) {
                        edt_format_staff_required_1.setError("Enter No Of Staff Required");
                        TastyToast.makeText(getApplicationContext(), "This Cannot be Empty", TastyToast.LENGTH_LONG, TastyToast.WARNING);
                    } else if (str_format_royalty_commission_1.equals("")) {
                        edt_format_royalty_commission_1.setError("Enter Royalty / Commission");
                        TastyToast.makeText(getApplicationContext(), "This Cannot be Empty", TastyToast.LENGTH_LONG, TastyToast.WARNING);
                    } else if (str_format_salespartner_monthly_revenue_1.equals("")) {
                        edt_format_salespartner_monthly_revenue_1.setError("Enter Sales Partner Monthly Revenue");
                        TastyToast.makeText(getApplicationContext(), "This Cannot be Empty", TastyToast.LENGTH_LONG, TastyToast.WARNING);
                    } else if (str_format_operating_profitmargin_1.equals("")) {
                        edt_format_operating_profitmargin_1.setError("Enter Operating Profit Margin %");
                        TastyToast.makeText(getApplicationContext(), "This Cannot be Empty", TastyToast.LENGTH_LONG, TastyToast.WARNING);
                    }
                    // IF STATEMENT FOR VALIDATING STRING OF FORMAT 2 (WHEN NO OF SELECTED FORMATS 3)
                    else if (str_format_name_2.equals("")) {
                        edt_format_name_2.setError("Enter Format Name");
                        TastyToast.makeText(getApplicationContext(), "Name Cannot be Empty", TastyToast.LENGTH_LONG, TastyToast.WARNING);
                    } else if (str_format_spaceneeded_minimum_2.equals("")) {
                        edt_format_spaceneeded_minimum_2.setError("Enter Minimum Space Needed");
                        TastyToast.makeText(getApplicationContext(), "This Cannot be Empty", TastyToast.LENGTH_LONG, TastyToast.WARNING);
                    } else if (str_format_spaceneeded_maximum_2.equals("")) {
                        edt_format_spaceneeded_maximum_2.setError("Enter Maximum Space Needed");
                        TastyToast.makeText(getApplicationContext(), "This Cannot be Empty", TastyToast.LENGTH_LONG, TastyToast.WARNING);
                    } else if (str_format_investment_needed_minimum_2.equals("")) {
                        edt_format_investment_needed_minimum_2.setError("Enter Minimum Investment Needed");
                        TastyToast.makeText(getApplicationContext(), "This Cannot be Empty", TastyToast.LENGTH_LONG, TastyToast.WARNING);
                    } else if (str_format_investment_needed_maximum_2.equals("")) {
                        edt_format_investment_needed_maximum_2.setError("Enter Maximum Investment Needed");
                        TastyToast.makeText(getApplicationContext(), "This Cannot be Empty", TastyToast.LENGTH_LONG, TastyToast.WARNING);
                    } else if (str_format_brand_fee_2.equals("")) {
                        edt_format_brand_fee_2.setError("Enter Brand Fee Included in this investment");
                        TastyToast.makeText(getApplicationContext(), "This Cannot be Empty", TastyToast.LENGTH_LONG, TastyToast.WARNING);
                    } else if (str_format_staff_required_2.equals("")) {
                        edt_format_staff_required_2.setError("Enter No Of Staff Required");
                        TastyToast.makeText(getApplicationContext(), "This Cannot be Empty", TastyToast.LENGTH_LONG, TastyToast.WARNING);
                    } else if (str_format_royalty_commission_2.equals("")) {
                        edt_format_royalty_commission_2.setError("Enter Royalty / Commission");
                        TastyToast.makeText(getApplicationContext(), "This Cannot be Empty", TastyToast.LENGTH_LONG, TastyToast.WARNING);
                    } else if (str_format_salespartner_monthly_revenue_2.equals("")) {
                        edt_format_salespartner_monthly_revenue_2.setError("Enter Sales Partner Monthly Revenue");
                        TastyToast.makeText(getApplicationContext(), "This Cannot be Empty", TastyToast.LENGTH_LONG, TastyToast.WARNING);
                    } else if (str_format_operating_profitmargin_2.equals("")) {
                        edt_format_operating_profitmargin_2.setError("Enter Operating Profit Margin %");
                        TastyToast.makeText(getApplicationContext(), "This Cannot be Empty", TastyToast.LENGTH_LONG, TastyToast.WARNING);
                    }
                    // IF STATEMENT FOR VALIDATING STRING OF FORMAT 3 (WHEN NO OF SELECTED FORMATS 3)
                    else if (str_format_name_3.equals("")) {
                        edt_format_name_3.setError("Enter Format Name");
                        TastyToast.makeText(getApplicationContext(), "Name Cannot be Empty", TastyToast.LENGTH_LONG, TastyToast.WARNING);
                    } else if (str_format_spaceneeded_minimum_3.equals("")) {
                        edt_format_spaceneeded_minimum_3.setError("Enter Minimum Space Needed");
                        TastyToast.makeText(getApplicationContext(), "This Cannot be Empty", TastyToast.LENGTH_LONG, TastyToast.WARNING);
                    } else if (str_format_spaceneeded_maximum_3.equals("")) {
                        edt_format_spaceneeded_maximum_3.setError("Enter Maximum Space Needed");
                        TastyToast.makeText(getApplicationContext(), "This Cannot be Empty", TastyToast.LENGTH_LONG, TastyToast.WARNING);
                    } else if (str_format_investment_needed_minimum_3.equals("")) {
                        edt_format_investment_needed_minimum_3.setError("Enter Minimum Investment Needed");
                        TastyToast.makeText(getApplicationContext(), "This Cannot be Empty", TastyToast.LENGTH_LONG, TastyToast.WARNING);
                    } else if (str_format_investment_needed_maximum_3.equals("")) {
                        edt_format_investment_needed_maximum_3.setError("Enter Maximum Investment Needed");
                        TastyToast.makeText(getApplicationContext(), "This Cannot be Empty", TastyToast.LENGTH_LONG, TastyToast.WARNING);
                    } else if (str_format_brand_fee_3.equals("")) {
                        edt_format_brand_fee_3.setError("Enter Brand Fee Included in this investment");
                        TastyToast.makeText(getApplicationContext(), "This Cannot be Empty", TastyToast.LENGTH_LONG, TastyToast.WARNING);
                    } else if (str_format_staff_required_3.equals("")) {
                        edt_format_staff_required_3.setError("Enter No Of Staff Required");
                        TastyToast.makeText(getApplicationContext(), "This Cannot be Empty", TastyToast.LENGTH_LONG, TastyToast.WARNING);
                    } else if (str_format_royalty_commission_3.equals("")) {
                        edt_format_royalty_commission_3.setError("Enter Royalty / Commission");
                        TastyToast.makeText(getApplicationContext(), "This Cannot be Empty", TastyToast.LENGTH_LONG, TastyToast.WARNING);
                    } else if (str_format_salespartner_monthly_revenue_3.equals("")) {
                        edt_format_salespartner_monthly_revenue_3.setError("Enter Sales Partner Monthly Revenue");
                        TastyToast.makeText(getApplicationContext(), "This Cannot be Empty", TastyToast.LENGTH_LONG, TastyToast.WARNING);
                    } else if (str_format_operating_profitmargin_3.equals("")) {
                        edt_format_operating_profitmargin_3.setError("Enter Operating Profit Margin %");
                        TastyToast.makeText(getApplicationContext(), "This Cannot be Empty", TastyToast.LENGTH_LONG, TastyToast.WARNING);
                    } else {

                        dialog = new SpotsDialog(Activity_FranchiseProfile_Update.this);
                        dialog.show();
                        queue = Volley.newRequestQueue(getApplicationContext());
                        Function_Submit_FranchiseProfile();


                    }


                } else if (str_no_of_formats.equals("4")) {

                    // IF STATEMENT FOR VALIDATING STRING OF FORMAT 1 (WHEN NO OF SELECTED FORMATS 4)

                    System.out.println("FORMATS COUNT:: 4444444444444444444444 ");

                    if (str_format_name_1.equals("")) {
                        edt_format_name_1.setError("Enter Format Name");
                        TastyToast.makeText(getApplicationContext(), "Name Cannot be Empty", TastyToast.LENGTH_LONG, TastyToast.WARNING);
                    } else if (str_format_spaceneeded_minimum_1.equals("")) {
                        edt_format_spaceneeded_minimum_1.setError("Enter Minimum Space Needed");
                        TastyToast.makeText(getApplicationContext(), "This Cannot be Empty", TastyToast.LENGTH_LONG, TastyToast.WARNING);
                    } else if (str_format_spaceneeded_maximum_1.equals("")) {
                        edt_format_spaceneeded_maximum_1.setError("Enter Maximum Space Needed");
                        TastyToast.makeText(getApplicationContext(), "This Cannot be Empty", TastyToast.LENGTH_LONG, TastyToast.WARNING);
                    } else if (str_format_investment_needed_minimum_1.equals("")) {
                        edt_format_investment_needed_minimum_1.setError("Enter Minimum Investment Needed");
                        TastyToast.makeText(getApplicationContext(), "This Cannot be Empty", TastyToast.LENGTH_LONG, TastyToast.WARNING);
                    } else if (str_format_investment_needed_maximum_1.equals("")) {
                        edt_format_investment_needed_maximum_1.setError("Enter Maximum Investment Needed");
                        TastyToast.makeText(getApplicationContext(), "This Cannot be Empty", TastyToast.LENGTH_LONG, TastyToast.WARNING);
                    } else if (str_format_brand_fee_1.equals("")) {
                        edt_format_brand_fee_1.setError("Enter Brand Fee Included in this investment");
                        TastyToast.makeText(getApplicationContext(), "This Cannot be Empty", TastyToast.LENGTH_LONG, TastyToast.WARNING);
                    } else if (str_format_staff_required_1.equals("")) {
                        edt_format_staff_required_1.setError("Enter No Of Staff Required");
                        TastyToast.makeText(getApplicationContext(), "This Cannot be Empty", TastyToast.LENGTH_LONG, TastyToast.WARNING);
                    } else if (str_format_royalty_commission_1.equals("")) {
                        edt_format_royalty_commission_1.setError("Enter Royalty / Commission");
                        TastyToast.makeText(getApplicationContext(), "This Cannot be Empty", TastyToast.LENGTH_LONG, TastyToast.WARNING);
                    } else if (str_format_salespartner_monthly_revenue_1.equals("")) {
                        edt_format_salespartner_monthly_revenue_1.setError("Enter Sales Partner Monthly Revenue");
                        TastyToast.makeText(getApplicationContext(), "This Cannot be Empty", TastyToast.LENGTH_LONG, TastyToast.WARNING);
                    } else if (str_format_operating_profitmargin_1.equals("")) {
                        edt_format_operating_profitmargin_1.setError("Enter Operating Profit Margin %");
                        TastyToast.makeText(getApplicationContext(), "This Cannot be Empty", TastyToast.LENGTH_LONG, TastyToast.WARNING);
                    }
                    // IF STATEMENT FOR VALIDATING STRING OF FORMAT 2 (WHEN NO OF SELECTED FORMATS 4)
                    else if (str_format_name_2.equals("")) {
                        edt_format_name_2.setError("Enter Format Name");
                        TastyToast.makeText(getApplicationContext(), "Name Cannot be Empty", TastyToast.LENGTH_LONG, TastyToast.WARNING);
                    } else if (str_format_spaceneeded_minimum_2.equals("")) {
                        edt_format_spaceneeded_minimum_2.setError("Enter Minimum Space Needed");
                        TastyToast.makeText(getApplicationContext(), "This Cannot be Empty", TastyToast.LENGTH_LONG, TastyToast.WARNING);
                    } else if (str_format_spaceneeded_maximum_2.equals("")) {
                        edt_format_spaceneeded_maximum_2.setError("Enter Maximum Space Needed");
                        TastyToast.makeText(getApplicationContext(), "This Cannot be Empty", TastyToast.LENGTH_LONG, TastyToast.WARNING);
                    } else if (str_format_investment_needed_minimum_2.equals("")) {
                        edt_format_investment_needed_minimum_2.setError("Enter Minimum Investment Needed");
                        TastyToast.makeText(getApplicationContext(), "This Cannot be Empty", TastyToast.LENGTH_LONG, TastyToast.WARNING);
                    } else if (str_format_investment_needed_maximum_2.equals("")) {
                        edt_format_investment_needed_maximum_2.setError("Enter Maximum Investment Needed");
                        TastyToast.makeText(getApplicationContext(), "This Cannot be Empty", TastyToast.LENGTH_LONG, TastyToast.WARNING);
                    } else if (str_format_brand_fee_2.equals("")) {
                        edt_format_brand_fee_2.setError("Enter Brand Fee Included in this investment");
                        TastyToast.makeText(getApplicationContext(), "This Cannot be Empty", TastyToast.LENGTH_LONG, TastyToast.WARNING);
                    } else if (str_format_staff_required_2.equals("")) {
                        edt_format_staff_required_2.setError("Enter No Of Staff Required");
                        TastyToast.makeText(getApplicationContext(), "This Cannot be Empty", TastyToast.LENGTH_LONG, TastyToast.WARNING);
                    } else if (str_format_royalty_commission_2.equals("")) {
                        edt_format_royalty_commission_2.setError("Enter Royalty / Commission");
                        TastyToast.makeText(getApplicationContext(), "This Cannot be Empty", TastyToast.LENGTH_LONG, TastyToast.WARNING);
                    } else if (str_format_salespartner_monthly_revenue_2.equals("")) {
                        edt_format_salespartner_monthly_revenue_2.setError("Enter Sales Partner Monthly Revenue");
                        TastyToast.makeText(getApplicationContext(), "This Cannot be Empty", TastyToast.LENGTH_LONG, TastyToast.WARNING);
                    } else if (str_format_operating_profitmargin_2.equals("")) {
                        edt_format_operating_profitmargin_2.setError("Enter Operating Profit Margin %");
                        TastyToast.makeText(getApplicationContext(), "This Cannot be Empty", TastyToast.LENGTH_LONG, TastyToast.WARNING);
                    }
                    // IF STATEMENT FOR VALIDATING STRING OF FORMAT 3 (WHEN NO OF SELECTED FORMATS 4)
                    else if (str_format_name_3.equals("")) {
                        edt_format_name_3.setError("Enter Format Name");
                        TastyToast.makeText(getApplicationContext(), "Name Cannot be Empty", TastyToast.LENGTH_LONG, TastyToast.WARNING);
                    } else if (str_format_spaceneeded_minimum_3.equals("")) {
                        edt_format_spaceneeded_minimum_3.setError("Enter Minimum Space Needed");
                        TastyToast.makeText(getApplicationContext(), "This Cannot be Empty", TastyToast.LENGTH_LONG, TastyToast.WARNING);
                    } else if (str_format_spaceneeded_maximum_3.equals("")) {
                        edt_format_spaceneeded_maximum_3.setError("Enter Maximum Space Needed");
                        TastyToast.makeText(getApplicationContext(), "This Cannot be Empty", TastyToast.LENGTH_LONG, TastyToast.WARNING);
                    } else if (str_format_investment_needed_minimum_3.equals("")) {
                        edt_format_investment_needed_minimum_3.setError("Enter Minimum Investment Needed");
                        TastyToast.makeText(getApplicationContext(), "This Cannot be Empty", TastyToast.LENGTH_LONG, TastyToast.WARNING);
                    } else if (str_format_investment_needed_maximum_3.equals("")) {
                        edt_format_investment_needed_maximum_3.setError("Enter Maximum Investment Needed");
                        TastyToast.makeText(getApplicationContext(), "This Cannot be Empty", TastyToast.LENGTH_LONG, TastyToast.WARNING);
                    } else if (str_format_brand_fee_3.equals("")) {
                        edt_format_brand_fee_3.setError("Enter Brand Fee Included in this investment");
                        TastyToast.makeText(getApplicationContext(), "This Cannot be Empty", TastyToast.LENGTH_LONG, TastyToast.WARNING);
                    } else if (str_format_staff_required_3.equals("")) {
                        edt_format_staff_required_3.setError("Enter No Of Staff Required");
                        TastyToast.makeText(getApplicationContext(), "This Cannot be Empty", TastyToast.LENGTH_LONG, TastyToast.WARNING);
                    } else if (str_format_royalty_commission_3.equals("")) {
                        edt_format_royalty_commission_3.setError("Enter Royalty / Commission");
                        TastyToast.makeText(getApplicationContext(), "This Cannot be Empty", TastyToast.LENGTH_LONG, TastyToast.WARNING);
                    } else if (str_format_salespartner_monthly_revenue_3.equals("")) {
                        edt_format_salespartner_monthly_revenue_3.setError("Enter Sales Partner Monthly Revenue");
                        TastyToast.makeText(getApplicationContext(), "This Cannot be Empty", TastyToast.LENGTH_LONG, TastyToast.WARNING);
                    } else if (str_format_operating_profitmargin_3.equals("")) {
                        edt_format_operating_profitmargin_3.setError("Enter Operating Profit Margin %");
                        TastyToast.makeText(getApplicationContext(), "This Cannot be Empty", TastyToast.LENGTH_LONG, TastyToast.WARNING);
                    }
                    // IF STATEMENT FOR VALIDATING STRING OF FORMAT 4 (WHEN NO OF SELECTED FORMATS 4)
                    else if (str_format_name_4.equals("")) {
                        edt_format_name_4.setError("Enter Format Name");
                        TastyToast.makeText(getApplicationContext(), "Name Cannot be Empty", TastyToast.LENGTH_LONG, TastyToast.WARNING);
                    } else if (str_format_spaceneeded_minimum_4.equals("")) {
                        edt_format_spaceneeded_minimum_4.setError("Enter Minimum Space Needed");
                        TastyToast.makeText(getApplicationContext(), "This Cannot be Empty", TastyToast.LENGTH_LONG, TastyToast.WARNING);
                    } else if (str_format_spaceneeded_maximum_4.equals("")) {
                        edt_format_spaceneeded_maximum_4.setError("Enter Maximum Space Needed");
                        TastyToast.makeText(getApplicationContext(), "This Cannot be Empty", TastyToast.LENGTH_LONG, TastyToast.WARNING);
                    } else if (str_format_investment_needed_minimum_4.equals("")) {
                        edt_format_investment_needed_minimum_4.setError("Enter Minimum Investment Needed");
                        TastyToast.makeText(getApplicationContext(), "This Cannot be Empty", TastyToast.LENGTH_LONG, TastyToast.WARNING);
                    } else if (str_format_investment_needed_maximum_4.equals("")) {
                        edt_format_investment_needed_maximum_4.setError("Enter Maximum Investment Needed");
                        TastyToast.makeText(getApplicationContext(), "This Cannot be Empty", TastyToast.LENGTH_LONG, TastyToast.WARNING);
                    } else if (str_format_brand_fee_4.equals("")) {
                        edt_format_brand_fee_4.setError("Enter Brand Fee Included in this investment");
                        TastyToast.makeText(getApplicationContext(), "This Cannot be Empty", TastyToast.LENGTH_LONG, TastyToast.WARNING);
                    } else if (str_format_staff_required_4.equals("")) {
                        edt_format_staff_required_4.setError("Enter No Of Staff Required");
                        TastyToast.makeText(getApplicationContext(), "This Cannot be Empty", TastyToast.LENGTH_LONG, TastyToast.WARNING);
                    } else if (str_format_royalty_commission_4.equals("")) {
                        edt_format_royalty_commission_4.setError("Enter Royalty / Commission");
                        TastyToast.makeText(getApplicationContext(), "This Cannot be Empty", TastyToast.LENGTH_LONG, TastyToast.WARNING);
                    } else if (str_format_salespartner_monthly_revenue_4.equals("")) {
                        edt_format_salespartner_monthly_revenue_4.setError("Enter Sales Partner Monthly Revenue");
                        TastyToast.makeText(getApplicationContext(), "This Cannot be Empty", TastyToast.LENGTH_LONG, TastyToast.WARNING);
                    } else if (str_format_operating_profitmargin_4.equals("")) {
                        edt_format_operating_profitmargin_4.setError("Enter Operating Profit Margin %");
                        TastyToast.makeText(getApplicationContext(), "This Cannot be Empty", TastyToast.LENGTH_LONG, TastyToast.WARNING);
                    } else {

                        dialog = new SpotsDialog(Activity_FranchiseProfile_Update.this);
                        dialog.show();
                        queue = Volley.newRequestQueue(getApplicationContext());
                        Function_Submit_FranchiseProfile();


                    }


                } else if (str_no_of_formats.equals("5")) {

                    // IF STATEMENT FOR VALIDATING STRING OF FORMAT 1 (WHEN NO OF SELECTED FORMATS 5)

                    System.out.println("FORMATS COUNT:: 5555555555555555555555 ");

                    if (str_format_name_1.equals("")) {
                        edt_format_name_1.setError("Enter Format Name");
                        TastyToast.makeText(getApplicationContext(), "Name Cannot be Empty", TastyToast.LENGTH_LONG, TastyToast.WARNING);
                    } else if (str_format_spaceneeded_minimum_1.equals("")) {
                        edt_format_spaceneeded_minimum_1.setError("Enter Minimum Space Needed");
                        TastyToast.makeText(getApplicationContext(), "This Cannot be Empty", TastyToast.LENGTH_LONG, TastyToast.WARNING);
                    } else if (str_format_spaceneeded_maximum_1.equals("")) {
                        edt_format_spaceneeded_maximum_1.setError("Enter Maximum Space Needed");
                        TastyToast.makeText(getApplicationContext(), "This Cannot be Empty", TastyToast.LENGTH_LONG, TastyToast.WARNING);
                    } else if (str_format_investment_needed_minimum_1.equals("")) {
                        edt_format_investment_needed_minimum_1.setError("Enter Minimum Investment Needed");
                        TastyToast.makeText(getApplicationContext(), "This Cannot be Empty", TastyToast.LENGTH_LONG, TastyToast.WARNING);
                    } else if (str_format_investment_needed_maximum_1.equals("")) {
                        edt_format_investment_needed_maximum_1.setError("Enter Maximum Investment Needed");
                        TastyToast.makeText(getApplicationContext(), "This Cannot be Empty", TastyToast.LENGTH_LONG, TastyToast.WARNING);
                    } else if (str_format_brand_fee_1.equals("")) {
                        edt_format_brand_fee_1.setError("Enter Brand Fee Included in this investment");
                        TastyToast.makeText(getApplicationContext(), "This Cannot be Empty", TastyToast.LENGTH_LONG, TastyToast.WARNING);
                    } else if (str_format_staff_required_1.equals("")) {
                        edt_format_staff_required_1.setError("Enter No Of Staff Required");
                        TastyToast.makeText(getApplicationContext(), "This Cannot be Empty", TastyToast.LENGTH_LONG, TastyToast.WARNING);
                    } else if (str_format_royalty_commission_1.equals("")) {
                        edt_format_royalty_commission_1.setError("Enter Royalty / Commission");
                        TastyToast.makeText(getApplicationContext(), "This Cannot be Empty", TastyToast.LENGTH_LONG, TastyToast.WARNING);
                    } else if (str_format_salespartner_monthly_revenue_1.equals("")) {
                        edt_format_salespartner_monthly_revenue_1.setError("Enter Sales Partner Monthly Revenue");
                        TastyToast.makeText(getApplicationContext(), "This Cannot be Empty", TastyToast.LENGTH_LONG, TastyToast.WARNING);
                    } else if (str_format_operating_profitmargin_1.equals("")) {
                        edt_format_operating_profitmargin_1.setError("Enter Operating Profit Margin %");
                        TastyToast.makeText(getApplicationContext(), "This Cannot be Empty", TastyToast.LENGTH_LONG, TastyToast.WARNING);
                    }
                    // IF STATEMENT FOR VALIDATING STRING OF FORMAT 2 (WHEN NO OF SELECTED FORMATS 5)
                    else if (str_format_name_2.equals("")) {
                        edt_format_name_2.setError("Enter Format Name");
                        TastyToast.makeText(getApplicationContext(), "Name Cannot be Empty", TastyToast.LENGTH_LONG, TastyToast.WARNING);
                    } else if (str_format_spaceneeded_minimum_2.equals("")) {
                        edt_format_spaceneeded_minimum_2.setError("Enter Minimum Space Needed");
                        TastyToast.makeText(getApplicationContext(), "This Cannot be Empty", TastyToast.LENGTH_LONG, TastyToast.WARNING);
                    } else if (str_format_spaceneeded_maximum_2.equals("")) {
                        edt_format_spaceneeded_maximum_2.setError("Enter Maximum Space Needed");
                        TastyToast.makeText(getApplicationContext(), "This Cannot be Empty", TastyToast.LENGTH_LONG, TastyToast.WARNING);
                    } else if (str_format_investment_needed_minimum_2.equals("")) {
                        edt_format_investment_needed_minimum_2.setError("Enter Minimum Investment Needed");
                        TastyToast.makeText(getApplicationContext(), "This Cannot be Empty", TastyToast.LENGTH_LONG, TastyToast.WARNING);
                    } else if (str_format_investment_needed_maximum_2.equals("")) {
                        edt_format_investment_needed_maximum_2.setError("Enter Maximum Investment Needed");
                        TastyToast.makeText(getApplicationContext(), "This Cannot be Empty", TastyToast.LENGTH_LONG, TastyToast.WARNING);
                    } else if (str_format_brand_fee_2.equals("")) {
                        edt_format_brand_fee_2.setError("Enter Brand Fee Included in this investment");
                        TastyToast.makeText(getApplicationContext(), "This Cannot be Empty", TastyToast.LENGTH_LONG, TastyToast.WARNING);
                    } else if (str_format_staff_required_2.equals("")) {
                        edt_format_staff_required_2.setError("Enter No Of Staff Required");
                        TastyToast.makeText(getApplicationContext(), "This Cannot be Empty", TastyToast.LENGTH_LONG, TastyToast.WARNING);
                    } else if (str_format_royalty_commission_2.equals("")) {
                        edt_format_royalty_commission_2.setError("Enter Royalty / Commission");
                        TastyToast.makeText(getApplicationContext(), "This Cannot be Empty", TastyToast.LENGTH_LONG, TastyToast.WARNING);
                    } else if (str_format_salespartner_monthly_revenue_2.equals("")) {
                        edt_format_salespartner_monthly_revenue_2.setError("Enter Sales Partner Monthly Revenue");
                        TastyToast.makeText(getApplicationContext(), "This Cannot be Empty", TastyToast.LENGTH_LONG, TastyToast.WARNING);
                    } else if (str_format_operating_profitmargin_2.equals("")) {
                        edt_format_operating_profitmargin_2.setError("Enter Operating Profit Margin %");
                        TastyToast.makeText(getApplicationContext(), "This Cannot be Empty", TastyToast.LENGTH_LONG, TastyToast.WARNING);
                    }
                    // IF STATEMENT FOR VALIDATING STRING OF FORMAT 3 (WHEN NO OF SELECTED FORMATS 5)
                    else if (str_format_name_3.equals("")) {
                        edt_format_name_3.setError("Enter Format Name");
                        TastyToast.makeText(getApplicationContext(), "Name Cannot be Empty", TastyToast.LENGTH_LONG, TastyToast.WARNING);
                    } else if (str_format_spaceneeded_minimum_3.equals("")) {
                        edt_format_spaceneeded_minimum_3.setError("Enter Minimum Space Needed");
                        TastyToast.makeText(getApplicationContext(), "This Cannot be Empty", TastyToast.LENGTH_LONG, TastyToast.WARNING);
                    } else if (str_format_spaceneeded_maximum_3.equals("")) {
                        edt_format_spaceneeded_maximum_3.setError("Enter Maximum Space Needed");
                        TastyToast.makeText(getApplicationContext(), "This Cannot be Empty", TastyToast.LENGTH_LONG, TastyToast.WARNING);
                    } else if (str_format_investment_needed_minimum_3.equals("")) {
                        edt_format_investment_needed_minimum_3.setError("Enter Minimum Investment Needed");
                        TastyToast.makeText(getApplicationContext(), "This Cannot be Empty", TastyToast.LENGTH_LONG, TastyToast.WARNING);
                    } else if (str_format_investment_needed_maximum_3.equals("")) {
                        edt_format_investment_needed_maximum_3.setError("Enter Maximum Investment Needed");
                        TastyToast.makeText(getApplicationContext(), "This Cannot be Empty", TastyToast.LENGTH_LONG, TastyToast.WARNING);
                    } else if (str_format_brand_fee_3.equals("")) {
                        edt_format_brand_fee_3.setError("Enter Brand Fee Included in this investment");
                        TastyToast.makeText(getApplicationContext(), "This Cannot be Empty", TastyToast.LENGTH_LONG, TastyToast.WARNING);
                    } else if (str_format_staff_required_3.equals("")) {
                        edt_format_staff_required_3.setError("Enter No Of Staff Required");
                        TastyToast.makeText(getApplicationContext(), "This Cannot be Empty", TastyToast.LENGTH_LONG, TastyToast.WARNING);
                    } else if (str_format_royalty_commission_3.equals("")) {
                        edt_format_royalty_commission_3.setError("Enter Royalty / Commission");
                        TastyToast.makeText(getApplicationContext(), "This Cannot be Empty", TastyToast.LENGTH_LONG, TastyToast.WARNING);
                    } else if (str_format_salespartner_monthly_revenue_3.equals("")) {
                        edt_format_salespartner_monthly_revenue_3.setError("Enter Sales Partner Monthly Revenue");
                        TastyToast.makeText(getApplicationContext(), "This Cannot be Empty", TastyToast.LENGTH_LONG, TastyToast.WARNING);
                    } else if (str_format_operating_profitmargin_3.equals("")) {
                        edt_format_operating_profitmargin_3.setError("Enter Operating Profit Margin %");
                        TastyToast.makeText(getApplicationContext(), "This Cannot be Empty", TastyToast.LENGTH_LONG, TastyToast.WARNING);
                    }
                    // IF STATEMENT FOR VALIDATING STRING OF FORMAT 4 (WHEN NO OF SELECTED FORMATS 5)
                    else if (str_format_name_4.equals("")) {
                        edt_format_name_4.setError("Enter Format Name");
                        TastyToast.makeText(getApplicationContext(), "Name Cannot be Empty", TastyToast.LENGTH_LONG, TastyToast.WARNING);
                    } else if (str_format_spaceneeded_minimum_4.equals("")) {
                        edt_format_spaceneeded_minimum_4.setError("Enter Minimum Space Needed");
                        TastyToast.makeText(getApplicationContext(), "This Cannot be Empty", TastyToast.LENGTH_LONG, TastyToast.WARNING);
                    } else if (str_format_spaceneeded_maximum_4.equals("")) {
                        edt_format_spaceneeded_maximum_4.setError("Enter Maximum Space Needed");
                        TastyToast.makeText(getApplicationContext(), "This Cannot be Empty", TastyToast.LENGTH_LONG, TastyToast.WARNING);
                    } else if (str_format_investment_needed_minimum_4.equals("")) {
                        edt_format_investment_needed_minimum_4.setError("Enter Minimum Investment Needed");
                        TastyToast.makeText(getApplicationContext(), "This Cannot be Empty", TastyToast.LENGTH_LONG, TastyToast.WARNING);
                    } else if (str_format_investment_needed_maximum_4.equals("")) {
                        edt_format_investment_needed_maximum_4.setError("Enter Maximum Investment Needed");
                        TastyToast.makeText(getApplicationContext(), "This Cannot be Empty", TastyToast.LENGTH_LONG, TastyToast.WARNING);
                    } else if (str_format_brand_fee_4.equals("")) {
                        edt_format_brand_fee_4.setError("Enter Brand Fee Included in this investment");
                        TastyToast.makeText(getApplicationContext(), "This Cannot be Empty", TastyToast.LENGTH_LONG, TastyToast.WARNING);
                    } else if (str_format_staff_required_4.equals("")) {
                        edt_format_staff_required_4.setError("Enter No Of Staff Required");
                        TastyToast.makeText(getApplicationContext(), "This Cannot be Empty", TastyToast.LENGTH_LONG, TastyToast.WARNING);
                    } else if (str_format_royalty_commission_4.equals("")) {
                        edt_format_royalty_commission_4.setError("Enter Royalty / Commission");
                        TastyToast.makeText(getApplicationContext(), "This Cannot be Empty", TastyToast.LENGTH_LONG, TastyToast.WARNING);
                    } else if (str_format_salespartner_monthly_revenue_4.equals("")) {
                        edt_format_salespartner_monthly_revenue_4.setError("Enter Sales Partner Monthly Revenue");
                        TastyToast.makeText(getApplicationContext(), "This Cannot be Empty", TastyToast.LENGTH_LONG, TastyToast.WARNING);
                    } else if (str_format_operating_profitmargin_4.equals("")) {
                        edt_format_operating_profitmargin_4.setError("Enter Operating Profit Margin %");
                        TastyToast.makeText(getApplicationContext(), "This Cannot be Empty", TastyToast.LENGTH_LONG, TastyToast.WARNING);
                    }
                    // IF STATEMENT FOR VALIDATING STRING OF FORMAT 5 (WHEN NO OF SELECTED FORMATS 5)
                    else if (str_format_name_5.equals("")) {
                        edt_format_name_5.setError("Enter Format Name");
                        TastyToast.makeText(getApplicationContext(), "Name Cannot be Empty", TastyToast.LENGTH_LONG, TastyToast.WARNING);
                    } else if (str_format_spaceneeded_minimum_5.equals("")) {
                        edt_format_spaceneeded_minimum_5.setError("Enter Minimum Space Needed");
                        TastyToast.makeText(getApplicationContext(), "This Cannot be Empty", TastyToast.LENGTH_LONG, TastyToast.WARNING);
                    } else if (str_format_spaceneeded_maximum_5.equals("")) {
                        edt_format_spaceneeded_maximum_5.setError("Enter Maximum Space Needed");
                        TastyToast.makeText(getApplicationContext(), "This Cannot be Empty", TastyToast.LENGTH_LONG, TastyToast.WARNING);
                    } else if (str_format_investment_needed_minimum_5.equals("")) {
                        edt_format_investment_needed_minimum_5.setError("Enter Minimum Investment Needed");
                        TastyToast.makeText(getApplicationContext(), "This Cannot be Empty", TastyToast.LENGTH_LONG, TastyToast.WARNING);
                    } else if (str_format_investment_needed_maximum_5.equals("")) {
                        edt_format_investment_needed_maximum_5.setError("Enter Maximum Investment Needed");
                        TastyToast.makeText(getApplicationContext(), "This Cannot be Empty", TastyToast.LENGTH_LONG, TastyToast.WARNING);
                    } else if (str_format_brand_fee_5.equals("")) {
                        edt_format_brand_fee_5.setError("Enter Brand Fee Included in this investment");
                        TastyToast.makeText(getApplicationContext(), "This Cannot be Empty", TastyToast.LENGTH_LONG, TastyToast.WARNING);
                    } else if (str_format_staff_required_5.equals("")) {
                        edt_format_staff_required_5.setError("Enter No Of Staff Required");
                        TastyToast.makeText(getApplicationContext(), "This Cannot be Empty", TastyToast.LENGTH_LONG, TastyToast.WARNING);
                    } else if (str_format_royalty_commission_5.equals("")) {
                        edt_format_royalty_commission_5.setError("Enter Royalty / Commission");
                        TastyToast.makeText(getApplicationContext(), "This Cannot be Empty", TastyToast.LENGTH_LONG, TastyToast.WARNING);
                    } else if (str_format_salespartner_monthly_revenue_5.equals("")) {
                        edt_format_salespartner_monthly_revenue_5.setError("Enter Sales Partner Monthly Revenue");
                        TastyToast.makeText(getApplicationContext(), "This Cannot be Empty", TastyToast.LENGTH_LONG, TastyToast.WARNING);
                    } else if (str_format_operating_profitmargin_5.equals("")) {
                        edt_format_operating_profitmargin_5.setError("Enter Operating Profit Margin %");
                        TastyToast.makeText(getApplicationContext(), "This Cannot be Empty", TastyToast.LENGTH_LONG, TastyToast.WARNING);
                    } else {

                        dialog = new SpotsDialog(Activity_FranchiseProfile_Update.this);
                        dialog.show();
                        queue = Volley.newRequestQueue(getApplicationContext());
                        Function_Submit_FranchiseProfile();


                    }


                } else if (str_no_of_formats.equals("6")) {

                    // IF STATEMENT FOR VALIDATING STRING OF FORMAT 1 (WHEN NO OF SELECTED FORMATS 6)

                    System.out.println("FORMATS COUNT:: 66666666666666666666666 ");

                    if (str_format_name_1.equals("")) {
                        edt_format_name_1.setError("Enter Format Name");

                        TastyToast.makeText(getApplicationContext(), "Name Cannot be Empty", TastyToast.LENGTH_LONG, TastyToast.WARNING);
                    } else if (str_format_spaceneeded_minimum_1.equals("")) {
                        edt_format_spaceneeded_minimum_1.setError("Enter Minimum Space Needed");
                        TastyToast.makeText(getApplicationContext(), "This Cannot be Empty", TastyToast.LENGTH_LONG, TastyToast.WARNING);
                    } else if (str_format_spaceneeded_maximum_1.equals("")) {
                        edt_format_spaceneeded_maximum_1.setError("Enter Maximum Space Needed");
                        TastyToast.makeText(getApplicationContext(), "This Cannot be Empty", TastyToast.LENGTH_LONG, TastyToast.WARNING);
                    } else if (str_format_investment_needed_minimum_1.equals("")) {
                        edt_format_investment_needed_minimum_1.setError("Enter Minimum Investment Needed");
                        TastyToast.makeText(getApplicationContext(), "This Cannot be Empty", TastyToast.LENGTH_LONG, TastyToast.WARNING);
                    } else if (str_format_investment_needed_maximum_1.equals("")) {
                        edt_format_investment_needed_maximum_1.setError("Enter Maximum Investment Needed");
                        TastyToast.makeText(getApplicationContext(), "This Cannot be Empty", TastyToast.LENGTH_LONG, TastyToast.WARNING);
                    } else if (str_format_brand_fee_1.equals("")) {
                        edt_format_brand_fee_1.setError("Enter Brand Fee Included in this investment");
                        TastyToast.makeText(getApplicationContext(), "This Cannot be Empty", TastyToast.LENGTH_LONG, TastyToast.WARNING);
                    } else if (str_format_staff_required_1.equals("")) {
                        edt_format_staff_required_1.setError("Enter No Of Staff Required");
                        TastyToast.makeText(getApplicationContext(), "This Cannot be Empty", TastyToast.LENGTH_LONG, TastyToast.WARNING);
                    } else if (str_format_royalty_commission_1.equals("")) {
                        edt_format_royalty_commission_1.setError("Enter Royalty / Commission");
                        TastyToast.makeText(getApplicationContext(), "This Cannot be Empty", TastyToast.LENGTH_LONG, TastyToast.WARNING);
                    } else if (str_format_salespartner_monthly_revenue_1.equals("")) {
                        edt_format_salespartner_monthly_revenue_1.setError("Enter Sales Partner Monthly Revenue");
                        TastyToast.makeText(getApplicationContext(), "This Cannot be Empty", TastyToast.LENGTH_LONG, TastyToast.WARNING);
                    } else if (str_format_operating_profitmargin_1.equals("")) {
                        edt_format_operating_profitmargin_1.setError("Enter Operating Profit Margin %");
                        TastyToast.makeText(getApplicationContext(), "This Cannot be Empty", TastyToast.LENGTH_LONG, TastyToast.WARNING);
                    }
                    // IF STATEMENT FOR VALIDATING STRING OF FORMAT 2 (WHEN NO OF SELECTED FORMATS 6)
                    else if (str_format_name_2.equals("")) {
                        edt_format_name_2.setError("Enter Format Name");
                        TastyToast.makeText(getApplicationContext(), "Name Cannot be Empty", TastyToast.LENGTH_LONG, TastyToast.WARNING);
                    } else if (str_format_spaceneeded_minimum_2.equals("")) {
                        edt_format_spaceneeded_minimum_2.setError("Enter Minimum Space Needed");
                        TastyToast.makeText(getApplicationContext(), "This Cannot be Empty", TastyToast.LENGTH_LONG, TastyToast.WARNING);
                    } else if (str_format_spaceneeded_maximum_2.equals("")) {
                        edt_format_spaceneeded_maximum_2.setError("Enter Maximum Space Needed");
                        TastyToast.makeText(getApplicationContext(), "This Cannot be Empty", TastyToast.LENGTH_LONG, TastyToast.WARNING);
                    } else if (str_format_investment_needed_minimum_2.equals("")) {
                        edt_format_investment_needed_minimum_2.setError("Enter Minimum Investment Needed");
                        TastyToast.makeText(getApplicationContext(), "This Cannot be Empty", TastyToast.LENGTH_LONG, TastyToast.WARNING);
                    } else if (str_format_investment_needed_maximum_2.equals("")) {
                        edt_format_investment_needed_maximum_2.setError("Enter Maximum Investment Needed");
                        TastyToast.makeText(getApplicationContext(), "This Cannot be Empty", TastyToast.LENGTH_LONG, TastyToast.WARNING);
                    } else if (str_format_brand_fee_2.equals("")) {
                        edt_format_brand_fee_2.setError("Enter Brand Fee Included in this investment");
                        TastyToast.makeText(getApplicationContext(), "This Cannot be Empty", TastyToast.LENGTH_LONG, TastyToast.WARNING);
                    } else if (str_format_staff_required_2.equals("")) {
                        edt_format_staff_required_2.setError("Enter No Of Staff Required");
                        TastyToast.makeText(getApplicationContext(), "This Cannot be Empty", TastyToast.LENGTH_LONG, TastyToast.WARNING);
                    } else if (str_format_royalty_commission_2.equals("")) {
                        edt_format_royalty_commission_2.setError("Enter Royalty / Commission");
                        TastyToast.makeText(getApplicationContext(), "This Cannot be Empty", TastyToast.LENGTH_LONG, TastyToast.WARNING);
                    } else if (str_format_salespartner_monthly_revenue_2.equals("")) {
                        edt_format_salespartner_monthly_revenue_2.setError("Enter Sales Partner Monthly Revenue");
                        TastyToast.makeText(getApplicationContext(), "This Cannot be Empty", TastyToast.LENGTH_LONG, TastyToast.WARNING);
                    } else if (str_format_operating_profitmargin_2.equals("")) {
                        edt_format_operating_profitmargin_2.setError("Enter Operating Profit Margin %");
                        TastyToast.makeText(getApplicationContext(), "This Cannot be Empty", TastyToast.LENGTH_LONG, TastyToast.WARNING);
                    }
                    // IF STATEMENT FOR VALIDATING STRING OF FORMAT 3 (WHEN NO OF SELECTED FORMATS 6)
                    else if (str_format_name_3.equals("")) {
                        edt_format_name_3.setError("Enter Format Name");
                        TastyToast.makeText(getApplicationContext(), "Name Cannot be Empty", TastyToast.LENGTH_LONG, TastyToast.WARNING);
                    } else if (str_format_spaceneeded_minimum_3.equals("")) {
                        edt_format_spaceneeded_minimum_3.setError("Enter Minimum Space Needed");
                        TastyToast.makeText(getApplicationContext(), "This Cannot be Empty", TastyToast.LENGTH_LONG, TastyToast.WARNING);
                    } else if (str_format_spaceneeded_maximum_3.equals("")) {
                        edt_format_spaceneeded_maximum_3.setError("Enter Maximum Space Needed");
                        TastyToast.makeText(getApplicationContext(), "This Cannot be Empty", TastyToast.LENGTH_LONG, TastyToast.WARNING);
                    } else if (str_format_investment_needed_minimum_3.equals("")) {
                        edt_format_investment_needed_minimum_3.setError("Enter Minimum Investment Needed");
                        TastyToast.makeText(getApplicationContext(), "This Cannot be Empty", TastyToast.LENGTH_LONG, TastyToast.WARNING);
                    } else if (str_format_investment_needed_maximum_3.equals("")) {
                        edt_format_investment_needed_maximum_3.setError("Enter Maximum Investment Needed");
                        TastyToast.makeText(getApplicationContext(), "This Cannot be Empty", TastyToast.LENGTH_LONG, TastyToast.WARNING);
                    } else if (str_format_brand_fee_3.equals("")) {
                        edt_format_brand_fee_3.setError("Enter Brand Fee Included in this investment");
                        TastyToast.makeText(getApplicationContext(), "This Cannot be Empty", TastyToast.LENGTH_LONG, TastyToast.WARNING);
                    } else if (str_format_staff_required_3.equals("")) {
                        edt_format_staff_required_3.setError("Enter No Of Staff Required");
                        TastyToast.makeText(getApplicationContext(), "This Cannot be Empty", TastyToast.LENGTH_LONG, TastyToast.WARNING);
                    } else if (str_format_royalty_commission_3.equals("")) {
                        edt_format_royalty_commission_3.setError("Enter Royalty / Commission");
                        TastyToast.makeText(getApplicationContext(), "This Cannot be Empty", TastyToast.LENGTH_LONG, TastyToast.WARNING);
                    } else if (str_format_salespartner_monthly_revenue_3.equals("")) {
                        edt_format_salespartner_monthly_revenue_3.setError("Enter Sales Partner Monthly Revenue");
                        TastyToast.makeText(getApplicationContext(), "This Cannot be Empty", TastyToast.LENGTH_LONG, TastyToast.WARNING);
                    } else if (str_format_operating_profitmargin_3.equals("")) {
                        edt_format_operating_profitmargin_3.setError("Enter Operating Profit Margin %");
                        TastyToast.makeText(getApplicationContext(), "This Cannot be Empty", TastyToast.LENGTH_LONG, TastyToast.WARNING);
                    }
                    // IF STATEMENT FOR VALIDATING STRING OF FORMAT 4 (WHEN NO OF SELECTED FORMATS 6)
                    else if (str_format_name_4.equals("")) {
                        edt_format_name_4.setError("Enter Format Name");
                        TastyToast.makeText(getApplicationContext(), "Name Cannot be Empty", TastyToast.LENGTH_LONG, TastyToast.WARNING);
                    } else if (str_format_spaceneeded_minimum_4.equals("")) {
                        edt_format_spaceneeded_minimum_4.setError("Enter Minimum Space Needed");
                        TastyToast.makeText(getApplicationContext(), "This Cannot be Empty", TastyToast.LENGTH_LONG, TastyToast.WARNING);
                    } else if (str_format_spaceneeded_maximum_4.equals("")) {
                        edt_format_spaceneeded_maximum_4.setError("Enter Maximum Space Needed");
                        TastyToast.makeText(getApplicationContext(), "This Cannot be Empty", TastyToast.LENGTH_LONG, TastyToast.WARNING);
                    } else if (str_format_investment_needed_minimum_4.equals("")) {
                        edt_format_investment_needed_minimum_4.setError("Enter Minimum Investment Needed");
                        TastyToast.makeText(getApplicationContext(), "This Cannot be Empty", TastyToast.LENGTH_LONG, TastyToast.WARNING);
                    } else if (str_format_investment_needed_maximum_4.equals("")) {
                        edt_format_investment_needed_maximum_4.setError("Enter Maximum Investment Needed");
                        TastyToast.makeText(getApplicationContext(), "This Cannot be Empty", TastyToast.LENGTH_LONG, TastyToast.WARNING);
                    } else if (str_format_brand_fee_4.equals("")) {
                        edt_format_brand_fee_4.setError("Enter Brand Fee Included in this investment");
                        TastyToast.makeText(getApplicationContext(), "This Cannot be Empty", TastyToast.LENGTH_LONG, TastyToast.WARNING);
                    } else if (str_format_staff_required_4.equals("")) {
                        edt_format_staff_required_4.setError("Enter No Of Staff Required");
                        TastyToast.makeText(getApplicationContext(), "This Cannot be Empty", TastyToast.LENGTH_LONG, TastyToast.WARNING);
                    } else if (str_format_royalty_commission_4.equals("")) {
                        edt_format_royalty_commission_4.setError("Enter Royalty / Commission");
                        TastyToast.makeText(getApplicationContext(), "This Cannot be Empty", TastyToast.LENGTH_LONG, TastyToast.WARNING);
                    } else if (str_format_salespartner_monthly_revenue_4.equals("")) {
                        edt_format_salespartner_monthly_revenue_4.setError("Enter Sales Partner Monthly Revenue");
                        TastyToast.makeText(getApplicationContext(), "This Cannot be Empty", TastyToast.LENGTH_LONG, TastyToast.WARNING);
                    } else if (str_format_operating_profitmargin_4.equals("")) {
                        edt_format_operating_profitmargin_4.setError("Enter Operating Profit Margin %");
                        TastyToast.makeText(getApplicationContext(), "This Cannot be Empty", TastyToast.LENGTH_LONG, TastyToast.WARNING);
                    }
                    // IF STATEMENT FOR VALIDATING STRING OF FORMAT 5 (WHEN NO OF SELECTED FORMATS 6)
                    else if (str_format_name_5.equals("")) {
                        edt_format_name_5.setError("Enter Format Name");
                        TastyToast.makeText(getApplicationContext(), "Name Cannot be Empty", TastyToast.LENGTH_LONG, TastyToast.WARNING);
                    } else if (str_format_spaceneeded_minimum_5.equals("")) {
                        edt_format_spaceneeded_minimum_5.setError("Enter Minimum Space Needed");
                        TastyToast.makeText(getApplicationContext(), "This Cannot be Empty", TastyToast.LENGTH_LONG, TastyToast.WARNING);
                    } else if (str_format_spaceneeded_maximum_5.equals("")) {
                        edt_format_spaceneeded_maximum_5.setError("Enter Maximum Space Needed");
                        TastyToast.makeText(getApplicationContext(), "This Cannot be Empty", TastyToast.LENGTH_LONG, TastyToast.WARNING);
                    } else if (str_format_investment_needed_minimum_5.equals("")) {
                        edt_format_investment_needed_minimum_5.setError("Enter Minimum Investment Needed");
                        TastyToast.makeText(getApplicationContext(), "This Cannot be Empty", TastyToast.LENGTH_LONG, TastyToast.WARNING);
                    } else if (str_format_investment_needed_maximum_5.equals("")) {
                        edt_format_investment_needed_maximum_5.setError("Enter Maximum Investment Needed");
                        TastyToast.makeText(getApplicationContext(), "This Cannot be Empty", TastyToast.LENGTH_LONG, TastyToast.WARNING);
                    } else if (str_format_brand_fee_5.equals("")) {
                        edt_format_brand_fee_5.setError("Enter Brand Fee Included in this investment");
                        TastyToast.makeText(getApplicationContext(), "This Cannot be Empty", TastyToast.LENGTH_LONG, TastyToast.WARNING);
                    } else if (str_format_staff_required_5.equals("")) {
                        edt_format_staff_required_5.setError("Enter No Of Staff Required");
                        TastyToast.makeText(getApplicationContext(), "This Cannot be Empty", TastyToast.LENGTH_LONG, TastyToast.WARNING);
                    } else if (str_format_royalty_commission_5.equals("")) {
                        edt_format_royalty_commission_5.setError("Enter Royalty / Commission");
                        TastyToast.makeText(getApplicationContext(), "This Cannot be Empty", TastyToast.LENGTH_LONG, TastyToast.WARNING);
                    } else if (str_format_salespartner_monthly_revenue_5.equals("")) {
                        edt_format_salespartner_monthly_revenue_5.setError("Enter Sales Partner Monthly Revenue");
                        TastyToast.makeText(getApplicationContext(), "This Cannot be Empty", TastyToast.LENGTH_LONG, TastyToast.WARNING);
                    } else if (str_format_operating_profitmargin_5.equals("")) {
                        edt_format_operating_profitmargin_5.setError("Enter Operating Profit Margin %");
                        TastyToast.makeText(getApplicationContext(), "This Cannot be Empty", TastyToast.LENGTH_LONG, TastyToast.WARNING);
                    }
                    // IF STATEMENT FOR VALIDATING STRING OF FORMAT 6 (WHEN NO OF SELECTED FORMATS 6)
                    else if (str_format_name_6.equals("")) {
                        edt_format_name_6.setError("Enter Format Name");
                        TastyToast.makeText(getApplicationContext(), "Name Cannot be Empty", TastyToast.LENGTH_LONG, TastyToast.WARNING);
                    } else if (str_format_spaceneeded_minimum_6.equals("")) {
                        edt_format_spaceneeded_minimum_6.setError("Enter Minimum Space Needed");
                        TastyToast.makeText(getApplicationContext(), "This Cannot be Empty", TastyToast.LENGTH_LONG, TastyToast.WARNING);
                    } else if (str_format_spaceneeded_maximum_6.equals("")) {
                        edt_format_spaceneeded_maximum_6.setError("Enter Maximum Space Needed");
                        TastyToast.makeText(getApplicationContext(), "This Cannot be Empty", TastyToast.LENGTH_LONG, TastyToast.WARNING);
                    } else if (str_format_investment_needed_minimum_6.equals("")) {
                        edt_format_investment_needed_minimum_6.setError("Enter Minimum Investment Needed");
                        TastyToast.makeText(getApplicationContext(), "This Cannot be Empty", TastyToast.LENGTH_LONG, TastyToast.WARNING);
                    } else if (str_format_investment_needed_maximum_6.equals("")) {
                        edt_format_investment_needed_maximum_6.setError("Enter Maximum Investment Needed");
                        TastyToast.makeText(getApplicationContext(), "This Cannot be Empty", TastyToast.LENGTH_LONG, TastyToast.WARNING);
                    } else if (str_format_brand_fee_6.equals("")) {
                        edt_format_brand_fee_6.setError("Enter Brand Fee Included in this investment");
                        TastyToast.makeText(getApplicationContext(), "This Cannot be Empty", TastyToast.LENGTH_LONG, TastyToast.WARNING);
                    } else if (str_format_staff_required_6.equals("")) {
                        edt_format_staff_required_6.setError("Enter No Of Staff Required");
                        TastyToast.makeText(getApplicationContext(), "This Cannot be Empty", TastyToast.LENGTH_LONG, TastyToast.WARNING);
                    } else if (str_format_royalty_commission_6.equals("")) {
                        edt_format_royalty_commission_6.setError("Enter Royalty / Commission");
                        TastyToast.makeText(getApplicationContext(), "This Cannot be Empty", TastyToast.LENGTH_LONG, TastyToast.WARNING);
                    } else if (str_format_salespartner_monthly_revenue_6.equals("")) {
                        edt_format_salespartner_monthly_revenue_6.setError("Enter Sales Partner Monthly Revenue");
                        TastyToast.makeText(getApplicationContext(), "This Cannot be Empty", TastyToast.LENGTH_LONG, TastyToast.WARNING);
                    } else if (str_format_operating_profitmargin_6.equals("")) {
                        edt_format_operating_profitmargin_6.setError("Enter Operating Profit Margin %");
                        TastyToast.makeText(getApplicationContext(), "This Cannot be Empty", TastyToast.LENGTH_LONG, TastyToast.WARNING);
                    } else {

                        dialog = new SpotsDialog(Activity_FranchiseProfile_Update.this);
                        dialog.show();
                        queue = Volley.newRequestQueue(getApplicationContext());
                        Function_Submit_FranchiseProfile();

                    }


                }


            }
        });

        try

        {
            dialog = new SpotsDialog(Activity_FranchiseProfile_Update.this);
            dialog.show();
            queue = Volley.newRequestQueue(getApplicationContext());
            Get_Business_industry();
        } catch (
                Exception e)

        {
            // TODO: handle exception
        }

    }


    /*****************************
     * To get  Business Industry List
     ***************************/

    public void Get_Business_industry() {
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

                            String industry_name = obj1.getString(TAG_INDUSTRT_NAME);
                            String industry_key = obj1.getString(TAG_INDUSTRY_KEY);
                            String industry_type = obj1.getString(TAG_INDUSTRY_TYPE);

                            Arraylist_industry_name.add(industry_name);
                            Arraylist_industry_key.add(industry_key);
                            Arraylist_industry_type.add(industry_type);

                        }
                        try {

                            System.out.println("ARAAAAY :: " + Arraylist_industry_name);

                            ArrayAdapter<String> adapter_sector = new ArrayAdapter<String>(Activity_FranchiseProfile_Update.this,
                                    android.R.layout.simple_list_item_1, Arraylist_industry_name);
                            auto_franchise_business_industry.setAdapter(adapter_sector);

                            auto_franchise_business_industry
                                    .setTokenizer(new MultiAutoCompleteTextView.CommaTokenizer());
                            auto_franchise_business_industry.setThreshold(1);


                        } catch (Exception e) {

                        }

                        try {
                            queue = Volley.newRequestQueue(getApplicationContext());
                            Get_Business_Headquaters();

                        } catch (Exception e) {
                            // TODO: handle exception
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

    public void Get_Business_Headquaters() {
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

                            String headquaters_place = obj1.getString(TAG_HEADQUATERS_PLACE);
                            String headquaters_key = obj1.getString(TAG_HEADQUATERS_KEY);
                            String headquaters_type = obj1.getString(TAG_HEADQUATERS_TYPE);

                            Arraylist_location_place.add(headquaters_place);
                            Arraylist_location_key.add(headquaters_key);
                            Arraylist_location_type.add(headquaters_type);

                        }

                        try {

                            System.out.println("ARAAAAY :: " + Arraylist_location_place);

                            ArrayAdapter<String> adapter_location = new ArrayAdapter<String>(Activity_FranchiseProfile_Update.this,
                                    android.R.layout.simple_list_item_1, Arraylist_location_place);
                            auto_headquaters.setAdapter(adapter_location);


                            System.out.println("ARAAAAY :: " + 222222);
                            auto_headquaters.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                @Override
                                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


                                    System.out.println("Position :::::::: " + position);


                                    t1 = (TextView) view;
                                    String str_location_key = t1.getText().toString();
                                    int i = Arraylist_location_place.indexOf(str_location_key);

                                    String str_select_location_key = Arraylist_location_key.get(i);
                                    String str_select_location_type = Arraylist_location_type.get(i);
                                    str_select_item = str_select_location_key + "-" + str_select_location_type;
                                    System.out.println("FINAL Business Location :: " + str_select_item);


                                }
                            });

                        } catch (Exception e) {

                        }

                        try {
                            queue = Volley.newRequestQueue(getApplicationContext());
                            Get_Business_Expand_Location();

                        } catch (Exception e) {
                            // TODO: handle exception
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
     * To get  Business Expand Location
     ***************************/

    public void Get_Business_Expand_Location() {
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

                            String expand_location_place = obj1.getString(TAG_HEADQUATERS_PLACE);
                            String expand_location_key = obj1.getString(TAG_HEADQUATERS_KEY);
                            String expand_location_type = obj1.getString(TAG_HEADQUATERS_TYPE);

                            Arraylist_expand_location_place.add(expand_location_place);
                            Arraylist_expand_location_key.add(expand_location_key);
                            Arraylist_expand_location_type.add(expand_location_type);
                        }


                        try {

                            System.out.println("ARAAAAY :: " + Arraylist_expand_location_place);

                            ArrayAdapter<String> adapter_location = new ArrayAdapter<String>(Activity_FranchiseProfile_Update.this,
                                    android.R.layout.simple_list_item_1, Arraylist_expand_location_place);
                            auto_franchise_business_expand_locations.setAdapter(adapter_location);

                            auto_franchise_business_expand_locations
                                    .setTokenizer(new MultiAutoCompleteTextView.CommaTokenizer());
                            auto_franchise_business_expand_locations.setThreshold(1);

                            System.out.println("ARAAAAY :: " + 222222);


                        } catch (Exception e) {

                        }

                        try {
                            queue = Volley.newRequestQueue(getApplicationContext());
                            Get_Franchise_Profile();

                        } catch (Exception e) {
                            // TODO: handle exception
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
     * GET Franchise Profile
     ***************************/

    public void Get_Franchise_Profile() {

        StringRequest request = new StringRequest(Request.Method.POST,
                AppConfig.url_user_franchise_profile_update, new Response.Listener<String>() {
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
                        JSONArray arr_formats;


                        arr_main = obj.getJSONArray("data");

                        for (int i = 0; arr_main.length() > i; i++) {

                            JSONObject obj_data = arr_main.getJSONObject(i);

                            String franchise_id = obj_data.getString(TAG_FRANCHISE_ID);
                            String franchise_key = obj_data.getString(TAG_FRANCHISE_KEY);
                            String franchise_user_name = obj_data.getString(TAG_FRANCHISE_USER_NAME);
                            String franchise_email = obj_data.getString(TAG_FRANCHISE_EMAIL);
                            String franchise_mobile = obj_data.getString(TAG_FRANCHISE_MOBILE);
                            String franchise_designation = obj_data.getString(TAG_FRANCHISE_DESIGNATION);
                            String franchise_brand_name = obj_data.getString(TAG_FRANCHISE_BRAND_NAME);
                            String franchise_brand_offering = obj_data.getString(TAG_FRANCHISE_BRAND_OFFERING);
                            String franchise_brand_company = obj_data.getString(TAG_FRANCHISE_BRAND_COMPANY);
                            String franchise_brand_services = obj_data.getString(TAG_FRANCHISE_BRAND_SERVICES);
                            String franchise_brand_established = obj_data.getString(TAG_FRANCHISE_BRAND_ESTABLISHED);
                            String franchise_brand_headquaters = obj_data.getString(TAG_FRANCHISE_BRAND_HEADQUATERS);
                            String brand_salepartner_count = obj_data.getString(TAG_BRAND_SALEPARTNER_COUNT);
                            String brand_salepartner_before_partnering = obj_data.getString(TAG_BRAND_SALEPARTNER_BEFORE_PARTNERING);
                            String brand_salepartner_expect = obj_data.getString(TAG_BRAND_SALEPARTNER_EXPECT);
                            String brand_salepartner_procedure = obj_data.getString(TAG_BRAND_SALEPARTNER_PROCEDURE);
                            String franchise_format = obj_data.getString(TAG_FRANCHISE_FORMAT);
                            String franchise_logo = obj_data.getString(TAG_FRANCHISE_LOGO);
                            String franchise_currency = obj_data.getString(TAG_FRANCHISE_CURRENCY);
                            String country_currency = obj_data.getString(TAG_COUNTRY_CURRENCY);

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

                                String industry_name = obj_industry.getString(TAG_INDUSTRY_NAMEE);
                                String industry_key = obj_industry.getString(TAG_INDUSTRY_KEYY);

                                Arraylist_update_industries.add(industry_name);

                            }
                            str_final_industries = TextUtils.join(", ", Arraylist_update_industries);

                            try {
                                //setting editext values
                                edt_name.setText("" + franchise_user_name);
                                edt_name.setEnabled(false);
                                edt_email.setText("" + franchise_email);
                                edt_email.setEnabled(false);
                                edt_mobile_num.setText("" + franchise_mobile);
                                edt_mobile_num.setEnabled(false);
                                edt_designation.setText("" + franchise_designation);
                                edt_designation.setEnabled(false);
                                edt_brand_name.setText("" + franchise_brand_name);
                                edt_about_company.setText("" + franchise_brand_company);
                                edt_all_prod.setText("" + franchise_brand_services);
                                edt_no_of_sales_partners.setText("" + brand_salepartner_count);
                                edt_lookfor_in_salespartner.setText("" + brand_salepartner_before_partnering);
                                edt_kindof_support.setText("" + brand_salepartner_expect);
                                edt_procedure_salespartner.setText("" + brand_salepartner_procedure);

                                //Setting headquaters for Autocomplete textview
                                auto_headquaters = (AutoCompleteTextView) findViewById(R.id.edit_Franchise_profile_company_headquaters);
                                auto_headquaters.setText(franchise_brand_headquaters);

                                //Setting selection for spinner - (OPPORTUNITIES OFFERED)
                                if (franchise_brand_offering.equals("1")) {

                                    spn_opportunities_offered.setSelection(1);

                                } else if (franchise_brand_offering.equals("2")) {

                                    spn_opportunities_offered.setSelection(2);

                                } else if (franchise_brand_offering.equals("3")) {

                                    spn_opportunities_offered.setSelection(3);

                                } else if (franchise_brand_offering.equals("4")) {

                                    spn_opportunities_offered.setSelection(4);

                                } else if (franchise_brand_offering.equals("5")) {

                                    spn_opportunities_offered.setSelection(5);

                                }

                                //Setting selection for spinner - (NO_OF_SALESPARTNER_FORMATS)
                                if (franchise_format.equals("1")) {

                                    spn_no_of_salespartner_formats.setSelection(1);

                                } else if (franchise_format.equals("2")) {

                                    spn_no_of_salespartner_formats.setSelection(2);

                                } else if (franchise_format.equals("3")) {

                                    spn_no_of_salespartner_formats.setSelection(3);

                                } else if (franchise_format.equals("4")) {

                                    spn_no_of_salespartner_formats.setSelection(4);

                                } else if (franchise_format.equals("5")) {

                                    spn_no_of_salespartner_formats.setSelection(5);

                                } else if (franchise_format.equals("6")) {

                                    spn_no_of_salespartner_formats.setSelection(6);

                                }

                                System.out.println("INSIDE LOCATION " + str_final_location);
                                System.out.println("INSIDE SECTOR " + str_final_industries);

                                auto_franchise_business_industry = (MultiAutoCompleteTextView) findViewById(R.id.franchise_profile_multi_busi_industry);
                                auto_franchise_business_expand_locations = (MultiAutoCompleteTextView) findViewById(R.id.franchise_profile_multi_busi_expand_locations);

                                auto_franchise_business_industry.setText("" + str_final_industries + ", ");
                                auto_franchise_business_expand_locations.setText("" + str_final_location + ", ");

                                System.out.println("INSIDE AFTER LOCATION " + str_final_location);
                                System.out.println("INSIDE AFTER SECTOR " + str_final_industries);


                                if (franchise_format.equals("1")) {

                                    cv_format_one.setVisibility(View.VISIBLE);
                                    cv_format_two.setVisibility(View.GONE);
                                    cv_format_three.setVisibility(View.GONE);
                                    cv_format_four.setVisibility(View.GONE);
                                    cv_format_five.setVisibility(View.GONE);
                                    cv_format_six.setVisibility(View.GONE);


                                } else if (franchise_format.equals("2")) {

                                    cv_format_one.setVisibility(View.VISIBLE);
                                    cv_format_two.setVisibility(View.VISIBLE);

                                    cv_format_three.setVisibility(View.GONE);
                                    cv_format_four.setVisibility(View.GONE);
                                    cv_format_five.setVisibility(View.GONE);
                                    cv_format_six.setVisibility(View.GONE);

                                } else if (franchise_format.equals("3")) {

                                    cv_format_one.setVisibility(View.VISIBLE);
                                    cv_format_two.setVisibility(View.VISIBLE);
                                    cv_format_three.setVisibility(View.VISIBLE);

                                    cv_format_four.setVisibility(View.GONE);
                                    cv_format_five.setVisibility(View.GONE);
                                    cv_format_six.setVisibility(View.GONE);

                                } else if (franchise_format.equals("4")) {

                                    cv_format_one.setVisibility(View.VISIBLE);
                                    cv_format_two.setVisibility(View.VISIBLE);
                                    cv_format_three.setVisibility(View.VISIBLE);
                                    cv_format_four.setVisibility(View.VISIBLE);

                                    cv_format_five.setVisibility(View.GONE);
                                    cv_format_six.setVisibility(View.GONE);

                                } else if (franchise_format.equals("5")) {

                                    cv_format_one.setVisibility(View.VISIBLE);
                                    cv_format_two.setVisibility(View.VISIBLE);
                                    cv_format_three.setVisibility(View.VISIBLE);
                                    cv_format_four.setVisibility(View.VISIBLE);
                                    cv_format_five.setVisibility(View.VISIBLE);

                                    cv_format_six.setVisibility(View.GONE);

                                } else if (franchise_format.equals("6")) {

                                    cv_format_one.setVisibility(View.VISIBLE);
                                    cv_format_two.setVisibility(View.VISIBLE);
                                    cv_format_three.setVisibility(View.VISIBLE);
                                    cv_format_four.setVisibility(View.VISIBLE);
                                    cv_format_five.setVisibility(View.VISIBLE);
                                    cv_format_six.setVisibility(View.VISIBLE);

                                }


                                //GETTING FORMATS AND THERIR VALUES AND SETTING IT IN STRINGS
                                arr_formats = obj_data.getJSONArray("format");
                                System.out.println("INSIDE FORMAT ARRAYYYYY" + arr_formats);
                                if (franchise_format.equals("1")) {

                                    JSONObject obj_format1 = arr_formats.getJSONObject(0);

                                    String franchise_id1 = obj_format1.getString(TAG_FRANCHISE_ID1);
                                    String franchise_format_name1 = obj_format1.getString(TAG_FRANCHISE_FORMAT_NAME1);
                                    String franchise_format_min_investment1 = obj_format1.getString(TAG_FRANCHISE_FORMAT_MIN_INVESTMENT1);
                                    String franchise_format_max_investment1 = obj_format1.getString(TAG_FRANCHISE_FORMAT_MAX_INVESTMENT1);
                                    String franchise_format_fee1 = obj_format1.getString(TAG_FRANCHISE_FORMAT_FEE1);
                                    String franchise_format_no_of_staffs1 = obj_format1.getString(TAG_FRANCHISE_FORMAT_NO_OF_STAFFS1);
                                    String franchise_format_royality1 = obj_format1.getString(TAG_FRANCHISE_FORMAT_ROYALITY1);
                                    String franchise_format_revenue1 = obj_format1.getString(TAG_FRANCHISE_FORMAT_REVENUE1);
                                    String franchise_format_profit1 = obj_format1.getString(TAG_FRANCHISE_FORMAT_PROFIT1);
                                    String franchise_format_act1 = obj_format1.getString(TAG_FRANCHISE_FORMAT_ACT1);
                                    String franchise_format_min_sqft1 = obj_format1.getString(TAG_FRANCHISE_FORMAT_MIN_SQFT1);
                                    String franchise_format_max_sqft1 = obj_format1.getString(TAG_FRANCHISE_FORMAT_MAX_SQFT1);


                                    edt_format_name_1.setText("" + franchise_format_name1);
                                    edt_format_spaceneeded_minimum_1.setText("" + franchise_format_min_sqft1);
                                    edt_format_spaceneeded_maximum_1.setText("" + franchise_format_max_sqft1);
                                    edt_format_investment_needed_minimum_1.setText("" + franchise_format_min_investment1);
                                    edt_format_investment_needed_maximum_1.setText("" + franchise_format_max_investment1);
                                    edt_format_brand_fee_1.setText("" + franchise_format_fee1);
                                    edt_format_staff_required_1.setText("" + franchise_format_no_of_staffs1);
                                    edt_format_royalty_commission_1.setText("" + franchise_format_royality1);
                                    edt_format_salespartner_monthly_revenue_1.setText("" + franchise_format_revenue1);
                                    edt_format_operating_profitmargin_1.setText("" + franchise_format_profit1);


                                } else if (franchise_format.equals("2")) {

                                    JSONObject obj_format1 = arr_formats.getJSONObject(0);

                                    String franchise_id1 = obj_format1.getString(TAG_FRANCHISE_ID1);
                                    String franchise_format_name1 = obj_format1.getString(TAG_FRANCHISE_FORMAT_NAME1);
                                    String franchise_format_min_investment1 = obj_format1.getString(TAG_FRANCHISE_FORMAT_MIN_INVESTMENT1);
                                    String franchise_format_max_investment1 = obj_format1.getString(TAG_FRANCHISE_FORMAT_MAX_INVESTMENT1);
                                    String franchise_format_fee1 = obj_format1.getString(TAG_FRANCHISE_FORMAT_FEE1);
                                    String franchise_format_no_of_staffs1 = obj_format1.getString(TAG_FRANCHISE_FORMAT_NO_OF_STAFFS1);
                                    String franchise_format_royality1 = obj_format1.getString(TAG_FRANCHISE_FORMAT_ROYALITY1);
                                    String franchise_format_revenue1 = obj_format1.getString(TAG_FRANCHISE_FORMAT_REVENUE1);
                                    String franchise_format_profit1 = obj_format1.getString(TAG_FRANCHISE_FORMAT_PROFIT1);
                                    String franchise_format_act1 = obj_format1.getString(TAG_FRANCHISE_FORMAT_ACT1);
                                    String franchise_format_min_sqft1 = obj_format1.getString(TAG_FRANCHISE_FORMAT_MIN_SQFT1);
                                    String franchise_format_max_sqft1 = obj_format1.getString(TAG_FRANCHISE_FORMAT_MAX_SQFT1);


                                    edt_format_name_1.setText("" + franchise_format_name1);
                                    edt_format_spaceneeded_minimum_1.setText("" + franchise_format_min_sqft1);
                                    edt_format_spaceneeded_maximum_1.setText("" + franchise_format_max_sqft1);
                                    edt_format_investment_needed_minimum_1.setText("" + franchise_format_min_investment1);
                                    edt_format_investment_needed_maximum_1.setText("" + franchise_format_max_investment1);
                                    edt_format_brand_fee_1.setText("" + franchise_format_fee1);
                                    edt_format_staff_required_1.setText("" + franchise_format_no_of_staffs1);
                                    edt_format_royalty_commission_1.setText("" + franchise_format_royality1);
                                    edt_format_salespartner_monthly_revenue_1.setText("" + franchise_format_revenue1);
                                    edt_format_operating_profitmargin_1.setText("" + franchise_format_profit1);

                                    JSONObject obj_format2 = arr_formats.getJSONObject(1);

                                    String franchise_id2 = obj_format2.getString(TAG_FRANCHISE_ID2);
                                    String franchise_format_name2 = obj_format2.getString(TAG_FRANCHISE_FORMAT_NAME2);
                                    String franchise_format_min_investment2 = obj_format2.getString(TAG_FRANCHISE_FORMAT_MIN_INVESTMENT2);
                                    String franchise_format_max_investment2 = obj_format2.getString(TAG_FRANCHISE_FORMAT_MAX_INVESTMENT2);
                                    String franchise_format_fee2 = obj_format2.getString(TAG_FRANCHISE_FORMAT_FEE2);
                                    String franchise_format_no_of_staffs2 = obj_format2.getString(TAG_FRANCHISE_FORMAT_NO_OF_STAFFS2);
                                    String franchise_format_royality2 = obj_format2.getString(TAG_FRANCHISE_FORMAT_ROYALITY2);
                                    String franchise_format_revenue2 = obj_format2.getString(TAG_FRANCHISE_FORMAT_REVENUE2);
                                    String franchise_format_profit2 = obj_format2.getString(TAG_FRANCHISE_FORMAT_PROFIT2);
                                    String franchise_format_act2 = obj_format2.getString(TAG_FRANCHISE_FORMAT_ACT2);
                                    String franchise_format_min_sqft2 = obj_format2.getString(TAG_FRANCHISE_FORMAT_MIN_SQFT2);
                                    String franchise_format_max_sqft2 = obj_format2.getString(TAG_FRANCHISE_FORMAT_MAX_SQFT2);

                                    edt_format_name_2.setText("" + franchise_format_name2);
                                    edt_format_spaceneeded_minimum_2.setText("" + franchise_format_min_sqft2);
                                    edt_format_spaceneeded_maximum_2.setText("" + franchise_format_max_sqft2);
                                    edt_format_investment_needed_minimum_2.setText("" + franchise_format_min_investment2);
                                    edt_format_investment_needed_maximum_2.setText("" + franchise_format_max_investment2);
                                    edt_format_brand_fee_2.setText("" + franchise_format_fee2);
                                    edt_format_staff_required_2.setText("" + franchise_format_no_of_staffs2);
                                    edt_format_royalty_commission_2.setText("" + franchise_format_royality2);
                                    edt_format_salespartner_monthly_revenue_2.setText("" + franchise_format_revenue2);
                                    edt_format_operating_profitmargin_2.setText("" + franchise_format_profit2);


                                } else if (franchise_format.equals("3")) {

                                    JSONObject obj_format1 = arr_formats.getJSONObject(0);

                                    String franchise_id1 = obj_format1.getString(TAG_FRANCHISE_ID1);
                                    String franchise_format_name1 = obj_format1.getString(TAG_FRANCHISE_FORMAT_NAME1);
                                    String franchise_format_min_investment1 = obj_format1.getString(TAG_FRANCHISE_FORMAT_MIN_INVESTMENT1);
                                    String franchise_format_max_investment1 = obj_format1.getString(TAG_FRANCHISE_FORMAT_MAX_INVESTMENT1);
                                    String franchise_format_fee1 = obj_format1.getString(TAG_FRANCHISE_FORMAT_FEE1);
                                    String franchise_format_no_of_staffs1 = obj_format1.getString(TAG_FRANCHISE_FORMAT_NO_OF_STAFFS1);
                                    String franchise_format_royality1 = obj_format1.getString(TAG_FRANCHISE_FORMAT_ROYALITY1);
                                    String franchise_format_revenue1 = obj_format1.getString(TAG_FRANCHISE_FORMAT_REVENUE1);
                                    String franchise_format_profit1 = obj_format1.getString(TAG_FRANCHISE_FORMAT_PROFIT1);
                                    String franchise_format_act1 = obj_format1.getString(TAG_FRANCHISE_FORMAT_ACT1);
                                    String franchise_format_min_sqft1 = obj_format1.getString(TAG_FRANCHISE_FORMAT_MIN_SQFT1);
                                    String franchise_format_max_sqft1 = obj_format1.getString(TAG_FRANCHISE_FORMAT_MAX_SQFT1);


                                    edt_format_name_1.setText("" + franchise_format_name1);
                                    edt_format_spaceneeded_minimum_1.setText("" + franchise_format_min_sqft1);
                                    edt_format_spaceneeded_maximum_1.setText("" + franchise_format_max_sqft1);
                                    edt_format_investment_needed_minimum_1.setText("" + franchise_format_min_investment1);
                                    edt_format_investment_needed_maximum_1.setText("" + franchise_format_max_investment1);
                                    edt_format_brand_fee_1.setText("" + franchise_format_fee1);
                                    edt_format_staff_required_1.setText("" + franchise_format_no_of_staffs1);
                                    edt_format_royalty_commission_1.setText("" + franchise_format_royality1);
                                    edt_format_salespartner_monthly_revenue_1.setText("" + franchise_format_revenue1);
                                    edt_format_operating_profitmargin_1.setText("" + franchise_format_profit1);

                                    JSONObject obj_format2 = arr_formats.getJSONObject(1);

                                    String franchise_id2 = obj_format2.getString(TAG_FRANCHISE_ID2);
                                    String franchise_format_name2 = obj_format2.getString(TAG_FRANCHISE_FORMAT_NAME2);
                                    String franchise_format_min_investment2 = obj_format2.getString(TAG_FRANCHISE_FORMAT_MIN_INVESTMENT2);
                                    String franchise_format_max_investment2 = obj_format2.getString(TAG_FRANCHISE_FORMAT_MAX_INVESTMENT2);
                                    String franchise_format_fee2 = obj_format2.getString(TAG_FRANCHISE_FORMAT_FEE2);
                                    String franchise_format_no_of_staffs2 = obj_format2.getString(TAG_FRANCHISE_FORMAT_NO_OF_STAFFS2);
                                    String franchise_format_royality2 = obj_format2.getString(TAG_FRANCHISE_FORMAT_ROYALITY2);
                                    String franchise_format_revenue2 = obj_format2.getString(TAG_FRANCHISE_FORMAT_REVENUE2);
                                    String franchise_format_profit2 = obj_format2.getString(TAG_FRANCHISE_FORMAT_PROFIT2);
                                    String franchise_format_act2 = obj_format2.getString(TAG_FRANCHISE_FORMAT_ACT2);
                                    String franchise_format_min_sqft2 = obj_format2.getString(TAG_FRANCHISE_FORMAT_MIN_SQFT2);
                                    String franchise_format_max_sqft2 = obj_format2.getString(TAG_FRANCHISE_FORMAT_MAX_SQFT2);

                                    edt_format_name_2.setText("" + franchise_format_name2);
                                    edt_format_spaceneeded_minimum_2.setText("" + franchise_format_min_sqft2);
                                    edt_format_spaceneeded_maximum_2.setText("" + franchise_format_max_sqft2);
                                    edt_format_investment_needed_minimum_2.setText("" + franchise_format_min_investment2);
                                    edt_format_investment_needed_maximum_2.setText("" + franchise_format_max_investment2);
                                    edt_format_brand_fee_2.setText("" + franchise_format_fee2);
                                    edt_format_staff_required_2.setText("" + franchise_format_no_of_staffs2);
                                    edt_format_royalty_commission_2.setText("" + franchise_format_royality2);
                                    edt_format_salespartner_monthly_revenue_2.setText("" + franchise_format_revenue2);
                                    edt_format_operating_profitmargin_2.setText("" + franchise_format_profit2);


                                    JSONObject obj_format3 = arr_formats.getJSONObject(2);

                                    String franchise_id3 = obj_format3.getString(TAG_FRANCHISE_ID3);
                                    String franchise_format_name3 = obj_format3.getString(TAG_FRANCHISE_FORMAT_NAME3);
                                    String franchise_format_min_investment3 = obj_format3.getString(TAG_FRANCHISE_FORMAT_MIN_INVESTMENT3);
                                    String franchise_format_max_investment3 = obj_format3.getString(TAG_FRANCHISE_FORMAT_MAX_INVESTMENT3);
                                    String franchise_format_fee3 = obj_format3.getString(TAG_FRANCHISE_FORMAT_FEE3);
                                    String franchise_format_no_of_staffs3 = obj_format3.getString(TAG_FRANCHISE_FORMAT_NO_OF_STAFFS3);
                                    String franchise_format_royality3 = obj_format3.getString(TAG_FRANCHISE_FORMAT_ROYALITY3);
                                    String franchise_format_revenue3 = obj_format3.getString(TAG_FRANCHISE_FORMAT_REVENUE3);
                                    String franchise_format_profit3 = obj_format3.getString(TAG_FRANCHISE_FORMAT_PROFIT3);
                                    String franchise_format_act3 = obj_format3.getString(TAG_FRANCHISE_FORMAT_ACT3);
                                    String franchise_format_min_sqft3 = obj_format3.getString(TAG_FRANCHISE_FORMAT_MIN_SQFT3);
                                    String franchise_format_max_sqft3 = obj_format3.getString(TAG_FRANCHISE_FORMAT_MAX_SQFT3);

                                    edt_format_name_3.setText("" + franchise_format_name3);
                                    edt_format_spaceneeded_minimum_3.setText("" + franchise_format_min_sqft3);
                                    edt_format_spaceneeded_maximum_3.setText("" + franchise_format_max_sqft3);
                                    edt_format_investment_needed_minimum_3.setText("" + franchise_format_min_investment3);
                                    edt_format_investment_needed_maximum_3.setText("" + franchise_format_max_investment3);
                                    edt_format_brand_fee_3.setText("" + franchise_format_fee3);
                                    edt_format_staff_required_3.setText("" + franchise_format_no_of_staffs3);
                                    edt_format_royalty_commission_3.setText("" + franchise_format_royality3);
                                    edt_format_salespartner_monthly_revenue_3.setText("" + franchise_format_revenue3);
                                    edt_format_operating_profitmargin_3.setText("" + franchise_format_profit3);

                                } else if (franchise_format.equals("4")) {

                                    JSONObject obj_format1 = arr_formats.getJSONObject(0);

                                    String franchise_id1 = obj_format1.getString(TAG_FRANCHISE_ID1);
                                    String franchise_format_name1 = obj_format1.getString(TAG_FRANCHISE_FORMAT_NAME1);
                                    String franchise_format_min_investment1 = obj_format1.getString(TAG_FRANCHISE_FORMAT_MIN_INVESTMENT1);
                                    String franchise_format_max_investment1 = obj_format1.getString(TAG_FRANCHISE_FORMAT_MAX_INVESTMENT1);
                                    String franchise_format_fee1 = obj_format1.getString(TAG_FRANCHISE_FORMAT_FEE1);
                                    String franchise_format_no_of_staffs1 = obj_format1.getString(TAG_FRANCHISE_FORMAT_NO_OF_STAFFS1);
                                    String franchise_format_royality1 = obj_format1.getString(TAG_FRANCHISE_FORMAT_ROYALITY1);
                                    String franchise_format_revenue1 = obj_format1.getString(TAG_FRANCHISE_FORMAT_REVENUE1);
                                    String franchise_format_profit1 = obj_format1.getString(TAG_FRANCHISE_FORMAT_PROFIT1);
                                    String franchise_format_act1 = obj_format1.getString(TAG_FRANCHISE_FORMAT_ACT1);
                                    String franchise_format_min_sqft1 = obj_format1.getString(TAG_FRANCHISE_FORMAT_MIN_SQFT1);
                                    String franchise_format_max_sqft1 = obj_format1.getString(TAG_FRANCHISE_FORMAT_MAX_SQFT1);


                                    edt_format_name_1.setText("" + franchise_format_name1);
                                    edt_format_spaceneeded_minimum_1.setText("" + franchise_format_min_sqft1);
                                    edt_format_spaceneeded_maximum_1.setText("" + franchise_format_max_sqft1);
                                    edt_format_investment_needed_minimum_1.setText("" + franchise_format_min_investment1);
                                    edt_format_investment_needed_maximum_1.setText("" + franchise_format_max_investment1);
                                    edt_format_brand_fee_1.setText("" + franchise_format_fee1);
                                    edt_format_staff_required_1.setText("" + franchise_format_no_of_staffs1);
                                    edt_format_royalty_commission_1.setText("" + franchise_format_royality1);
                                    edt_format_salespartner_monthly_revenue_1.setText("" + franchise_format_revenue1);
                                    edt_format_operating_profitmargin_1.setText("" + franchise_format_profit1);

                                    JSONObject obj_format2 = arr_formats.getJSONObject(1);

                                    String franchise_id2 = obj_format2.getString(TAG_FRANCHISE_ID2);
                                    String franchise_format_name2 = obj_format2.getString(TAG_FRANCHISE_FORMAT_NAME2);
                                    String franchise_format_min_investment2 = obj_format2.getString(TAG_FRANCHISE_FORMAT_MIN_INVESTMENT2);
                                    String franchise_format_max_investment2 = obj_format2.getString(TAG_FRANCHISE_FORMAT_MAX_INVESTMENT2);
                                    String franchise_format_fee2 = obj_format2.getString(TAG_FRANCHISE_FORMAT_FEE2);
                                    String franchise_format_no_of_staffs2 = obj_format2.getString(TAG_FRANCHISE_FORMAT_NO_OF_STAFFS2);
                                    String franchise_format_royality2 = obj_format2.getString(TAG_FRANCHISE_FORMAT_ROYALITY2);
                                    String franchise_format_revenue2 = obj_format2.getString(TAG_FRANCHISE_FORMAT_REVENUE2);
                                    String franchise_format_profit2 = obj_format2.getString(TAG_FRANCHISE_FORMAT_PROFIT2);
                                    String franchise_format_act2 = obj_format2.getString(TAG_FRANCHISE_FORMAT_ACT2);
                                    String franchise_format_min_sqft2 = obj_format2.getString(TAG_FRANCHISE_FORMAT_MIN_SQFT2);
                                    String franchise_format_max_sqft2 = obj_format2.getString(TAG_FRANCHISE_FORMAT_MAX_SQFT2);

                                    edt_format_name_2.setText("" + franchise_format_name2);
                                    edt_format_spaceneeded_minimum_2.setText("" + franchise_format_min_sqft2);
                                    edt_format_spaceneeded_maximum_2.setText("" + franchise_format_max_sqft2);
                                    edt_format_investment_needed_minimum_2.setText("" + franchise_format_min_investment2);
                                    edt_format_investment_needed_maximum_2.setText("" + franchise_format_max_investment2);
                                    edt_format_brand_fee_2.setText("" + franchise_format_fee2);
                                    edt_format_staff_required_2.setText("" + franchise_format_no_of_staffs2);
                                    edt_format_royalty_commission_2.setText("" + franchise_format_royality2);
                                    edt_format_salespartner_monthly_revenue_2.setText("" + franchise_format_revenue2);
                                    edt_format_operating_profitmargin_2.setText("" + franchise_format_profit2);


                                    JSONObject obj_format3 = arr_formats.getJSONObject(2);

                                    String franchise_id3 = obj_format3.getString(TAG_FRANCHISE_ID3);
                                    String franchise_format_name3 = obj_format3.getString(TAG_FRANCHISE_FORMAT_NAME3);
                                    String franchise_format_min_investment3 = obj_format3.getString(TAG_FRANCHISE_FORMAT_MIN_INVESTMENT3);
                                    String franchise_format_max_investment3 = obj_format3.getString(TAG_FRANCHISE_FORMAT_MAX_INVESTMENT3);
                                    String franchise_format_fee3 = obj_format3.getString(TAG_FRANCHISE_FORMAT_FEE3);
                                    String franchise_format_no_of_staffs3 = obj_format3.getString(TAG_FRANCHISE_FORMAT_NO_OF_STAFFS3);
                                    String franchise_format_royality3 = obj_format3.getString(TAG_FRANCHISE_FORMAT_ROYALITY3);
                                    String franchise_format_revenue3 = obj_format3.getString(TAG_FRANCHISE_FORMAT_REVENUE3);
                                    String franchise_format_profit3 = obj_format3.getString(TAG_FRANCHISE_FORMAT_PROFIT3);
                                    String franchise_format_act3 = obj_format3.getString(TAG_FRANCHISE_FORMAT_ACT3);
                                    String franchise_format_min_sqft3 = obj_format3.getString(TAG_FRANCHISE_FORMAT_MIN_SQFT3);
                                    String franchise_format_max_sqft3 = obj_format3.getString(TAG_FRANCHISE_FORMAT_MAX_SQFT3);

                                    edt_format_name_3.setText("" + franchise_format_name3);
                                    edt_format_spaceneeded_minimum_3.setText("" + franchise_format_min_sqft3);
                                    edt_format_spaceneeded_maximum_3.setText("" + franchise_format_max_sqft3);
                                    edt_format_investment_needed_minimum_3.setText("" + franchise_format_min_investment3);
                                    edt_format_investment_needed_maximum_3.setText("" + franchise_format_max_investment3);
                                    edt_format_brand_fee_3.setText("" + franchise_format_fee3);
                                    edt_format_staff_required_3.setText("" + franchise_format_no_of_staffs3);
                                    edt_format_royalty_commission_3.setText("" + franchise_format_royality3);
                                    edt_format_salespartner_monthly_revenue_3.setText("" + franchise_format_revenue3);
                                    edt_format_operating_profitmargin_3.setText("" + franchise_format_profit3);

                                    JSONObject obj_format4 = arr_formats.getJSONObject(3);

                                    String franchise_id4 = obj_format4.getString(TAG_FRANCHISE_ID4);
                                    String franchise_format_name4 = obj_format4.getString(TAG_FRANCHISE_FORMAT_NAME4);
                                    String franchise_format_min_investment4 = obj_format4.getString(TAG_FRANCHISE_FORMAT_MIN_INVESTMENT4);
                                    String franchise_format_max_investment4 = obj_format4.getString(TAG_FRANCHISE_FORMAT_MAX_INVESTMENT4);
                                    String franchise_format_fee4 = obj_format4.getString(TAG_FRANCHISE_FORMAT_FEE4);
                                    String franchise_format_no_of_staffs4 = obj_format4.getString(TAG_FRANCHISE_FORMAT_NO_OF_STAFFS4);
                                    String franchise_format_royality4 = obj_format4.getString(TAG_FRANCHISE_FORMAT_ROYALITY4);
                                    String franchise_format_revenue4 = obj_format4.getString(TAG_FRANCHISE_FORMAT_REVENUE4);
                                    String franchise_format_profit4 = obj_format4.getString(TAG_FRANCHISE_FORMAT_PROFIT4);
                                    String franchise_format_act4 = obj_format4.getString(TAG_FRANCHISE_FORMAT_ACT4);
                                    String franchise_format_min_sqft4 = obj_format4.getString(TAG_FRANCHISE_FORMAT_MIN_SQFT4);
                                    String franchise_format_max_sqft4 = obj_format4.getString(TAG_FRANCHISE_FORMAT_MAX_SQFT4);

                                    edt_format_name_4.setText("" + franchise_format_name4);
                                    edt_format_spaceneeded_minimum_4.setText("" + franchise_format_min_sqft4);
                                    edt_format_spaceneeded_maximum_4.setText("" + franchise_format_max_sqft4);
                                    edt_format_investment_needed_minimum_4.setText("" + franchise_format_min_investment4);
                                    edt_format_investment_needed_maximum_4.setText("" + franchise_format_max_investment4);
                                    edt_format_brand_fee_4.setText("" + franchise_format_fee4);
                                    edt_format_staff_required_4.setText("" + franchise_format_no_of_staffs4);
                                    edt_format_royalty_commission_4.setText("" + franchise_format_royality4);
                                    edt_format_salespartner_monthly_revenue_4.setText("" + franchise_format_revenue4);
                                    edt_format_operating_profitmargin_4.setText("" + franchise_format_profit4);

                                } else if (franchise_format.equals("5")) {

                                    JSONObject obj_format1 = arr_formats.getJSONObject(0);

                                    String franchise_id1 = obj_format1.getString(TAG_FRANCHISE_ID1);
                                    String franchise_format_name1 = obj_format1.getString(TAG_FRANCHISE_FORMAT_NAME1);
                                    String franchise_format_min_investment1 = obj_format1.getString(TAG_FRANCHISE_FORMAT_MIN_INVESTMENT1);
                                    String franchise_format_max_investment1 = obj_format1.getString(TAG_FRANCHISE_FORMAT_MAX_INVESTMENT1);
                                    String franchise_format_fee1 = obj_format1.getString(TAG_FRANCHISE_FORMAT_FEE1);
                                    String franchise_format_no_of_staffs1 = obj_format1.getString(TAG_FRANCHISE_FORMAT_NO_OF_STAFFS1);
                                    String franchise_format_royality1 = obj_format1.getString(TAG_FRANCHISE_FORMAT_ROYALITY1);
                                    String franchise_format_revenue1 = obj_format1.getString(TAG_FRANCHISE_FORMAT_REVENUE1);
                                    String franchise_format_profit1 = obj_format1.getString(TAG_FRANCHISE_FORMAT_PROFIT1);
                                    String franchise_format_act1 = obj_format1.getString(TAG_FRANCHISE_FORMAT_ACT1);
                                    String franchise_format_min_sqft1 = obj_format1.getString(TAG_FRANCHISE_FORMAT_MIN_SQFT1);
                                    String franchise_format_max_sqft1 = obj_format1.getString(TAG_FRANCHISE_FORMAT_MAX_SQFT1);


                                    edt_format_name_1.setText("" + franchise_format_name1);
                                    edt_format_spaceneeded_minimum_1.setText("" + franchise_format_min_sqft1);
                                    edt_format_spaceneeded_maximum_1.setText("" + franchise_format_max_sqft1);
                                    edt_format_investment_needed_minimum_1.setText("" + franchise_format_min_investment1);
                                    edt_format_investment_needed_maximum_1.setText("" + franchise_format_max_investment1);
                                    edt_format_brand_fee_1.setText("" + franchise_format_fee1);
                                    edt_format_staff_required_1.setText("" + franchise_format_no_of_staffs1);
                                    edt_format_royalty_commission_1.setText("" + franchise_format_royality1);
                                    edt_format_salespartner_monthly_revenue_1.setText("" + franchise_format_revenue1);
                                    edt_format_operating_profitmargin_1.setText("" + franchise_format_profit1);

                                    JSONObject obj_format2 = arr_formats.getJSONObject(1);

                                    String franchise_id2 = obj_format2.getString(TAG_FRANCHISE_ID2);
                                    String franchise_format_name2 = obj_format2.getString(TAG_FRANCHISE_FORMAT_NAME2);
                                    String franchise_format_min_investment2 = obj_format2.getString(TAG_FRANCHISE_FORMAT_MIN_INVESTMENT2);
                                    String franchise_format_max_investment2 = obj_format2.getString(TAG_FRANCHISE_FORMAT_MAX_INVESTMENT2);
                                    String franchise_format_fee2 = obj_format2.getString(TAG_FRANCHISE_FORMAT_FEE2);
                                    String franchise_format_no_of_staffs2 = obj_format2.getString(TAG_FRANCHISE_FORMAT_NO_OF_STAFFS2);
                                    String franchise_format_royality2 = obj_format2.getString(TAG_FRANCHISE_FORMAT_ROYALITY2);
                                    String franchise_format_revenue2 = obj_format2.getString(TAG_FRANCHISE_FORMAT_REVENUE2);
                                    String franchise_format_profit2 = obj_format2.getString(TAG_FRANCHISE_FORMAT_PROFIT2);
                                    String franchise_format_act2 = obj_format2.getString(TAG_FRANCHISE_FORMAT_ACT2);
                                    String franchise_format_min_sqft2 = obj_format2.getString(TAG_FRANCHISE_FORMAT_MIN_SQFT2);
                                    String franchise_format_max_sqft2 = obj_format2.getString(TAG_FRANCHISE_FORMAT_MAX_SQFT2);

                                    edt_format_name_2.setText("" + franchise_format_name2);
                                    edt_format_spaceneeded_minimum_2.setText("" + franchise_format_min_sqft2);
                                    edt_format_spaceneeded_maximum_2.setText("" + franchise_format_max_sqft2);
                                    edt_format_investment_needed_minimum_2.setText("" + franchise_format_min_investment2);
                                    edt_format_investment_needed_maximum_2.setText("" + franchise_format_max_investment2);
                                    edt_format_brand_fee_2.setText("" + franchise_format_fee2);
                                    edt_format_staff_required_2.setText("" + franchise_format_no_of_staffs2);
                                    edt_format_royalty_commission_2.setText("" + franchise_format_royality2);
                                    edt_format_salespartner_monthly_revenue_2.setText("" + franchise_format_revenue2);
                                    edt_format_operating_profitmargin_2.setText("" + franchise_format_profit2);


                                    JSONObject obj_format3 = arr_formats.getJSONObject(2);

                                    String franchise_id3 = obj_format3.getString(TAG_FRANCHISE_ID3);
                                    String franchise_format_name3 = obj_format3.getString(TAG_FRANCHISE_FORMAT_NAME3);
                                    String franchise_format_min_investment3 = obj_format3.getString(TAG_FRANCHISE_FORMAT_MIN_INVESTMENT3);
                                    String franchise_format_max_investment3 = obj_format3.getString(TAG_FRANCHISE_FORMAT_MAX_INVESTMENT3);
                                    String franchise_format_fee3 = obj_format3.getString(TAG_FRANCHISE_FORMAT_FEE3);
                                    String franchise_format_no_of_staffs3 = obj_format3.getString(TAG_FRANCHISE_FORMAT_NO_OF_STAFFS3);
                                    String franchise_format_royality3 = obj_format3.getString(TAG_FRANCHISE_FORMAT_ROYALITY3);
                                    String franchise_format_revenue3 = obj_format3.getString(TAG_FRANCHISE_FORMAT_REVENUE3);
                                    String franchise_format_profit3 = obj_format3.getString(TAG_FRANCHISE_FORMAT_PROFIT3);
                                    String franchise_format_act3 = obj_format3.getString(TAG_FRANCHISE_FORMAT_ACT3);
                                    String franchise_format_min_sqft3 = obj_format3.getString(TAG_FRANCHISE_FORMAT_MIN_SQFT3);
                                    String franchise_format_max_sqft3 = obj_format3.getString(TAG_FRANCHISE_FORMAT_MAX_SQFT3);

                                    edt_format_name_3.setText("" + franchise_format_name3);
                                    edt_format_spaceneeded_minimum_3.setText("" + franchise_format_min_sqft3);
                                    edt_format_spaceneeded_maximum_3.setText("" + franchise_format_max_sqft3);
                                    edt_format_investment_needed_minimum_3.setText("" + franchise_format_min_investment3);
                                    edt_format_investment_needed_maximum_3.setText("" + franchise_format_max_investment3);
                                    edt_format_brand_fee_3.setText("" + franchise_format_fee3);
                                    edt_format_staff_required_3.setText("" + franchise_format_no_of_staffs3);
                                    edt_format_royalty_commission_3.setText("" + franchise_format_royality3);
                                    edt_format_salespartner_monthly_revenue_3.setText("" + franchise_format_revenue3);
                                    edt_format_operating_profitmargin_3.setText("" + franchise_format_profit3);

                                    JSONObject obj_format4 = arr_formats.getJSONObject(3);

                                    String franchise_id4 = obj_format4.getString(TAG_FRANCHISE_ID4);
                                    String franchise_format_name4 = obj_format4.getString(TAG_FRANCHISE_FORMAT_NAME4);
                                    String franchise_format_min_investment4 = obj_format4.getString(TAG_FRANCHISE_FORMAT_MIN_INVESTMENT4);
                                    String franchise_format_max_investment4 = obj_format4.getString(TAG_FRANCHISE_FORMAT_MAX_INVESTMENT4);
                                    String franchise_format_fee4 = obj_format4.getString(TAG_FRANCHISE_FORMAT_FEE4);
                                    String franchise_format_no_of_staffs4 = obj_format4.getString(TAG_FRANCHISE_FORMAT_NO_OF_STAFFS4);
                                    String franchise_format_royality4 = obj_format4.getString(TAG_FRANCHISE_FORMAT_ROYALITY4);
                                    String franchise_format_revenue4 = obj_format4.getString(TAG_FRANCHISE_FORMAT_REVENUE4);
                                    String franchise_format_profit4 = obj_format4.getString(TAG_FRANCHISE_FORMAT_PROFIT4);
                                    String franchise_format_act4 = obj_format4.getString(TAG_FRANCHISE_FORMAT_ACT4);
                                    String franchise_format_min_sqft4 = obj_format4.getString(TAG_FRANCHISE_FORMAT_MIN_SQFT4);
                                    String franchise_format_max_sqft4 = obj_format4.getString(TAG_FRANCHISE_FORMAT_MAX_SQFT4);

                                    edt_format_name_4.setText("" + franchise_format_name4);
                                    edt_format_spaceneeded_minimum_4.setText("" + franchise_format_min_sqft4);
                                    edt_format_spaceneeded_maximum_4.setText("" + franchise_format_max_sqft4);
                                    edt_format_investment_needed_minimum_4.setText("" + franchise_format_min_investment4);
                                    edt_format_investment_needed_maximum_4.setText("" + franchise_format_max_investment4);
                                    edt_format_brand_fee_4.setText("" + franchise_format_fee4);
                                    edt_format_staff_required_4.setText("" + franchise_format_no_of_staffs4);
                                    edt_format_royalty_commission_4.setText("" + franchise_format_royality4);
                                    edt_format_salespartner_monthly_revenue_4.setText("" + franchise_format_revenue4);
                                    edt_format_operating_profitmargin_4.setText("" + franchise_format_profit4);

                                    JSONObject obj_format5 = arr_formats.getJSONObject(4);

                                    String franchise_id5 = obj_format5.getString(TAG_FRANCHISE_ID5);
                                    String franchise_format_name5 = obj_format5.getString(TAG_FRANCHISE_FORMAT_NAME5);
                                    String franchise_format_min_investment5 = obj_format5.getString(TAG_FRANCHISE_FORMAT_MIN_INVESTMENT5);
                                    String franchise_format_max_investment5 = obj_format5.getString(TAG_FRANCHISE_FORMAT_MAX_INVESTMENT5);
                                    String franchise_format_fee5 = obj_format5.getString(TAG_FRANCHISE_FORMAT_FEE5);
                                    String franchise_format_no_of_staffs5 = obj_format5.getString(TAG_FRANCHISE_FORMAT_NO_OF_STAFFS5);
                                    String franchise_format_royality5 = obj_format5.getString(TAG_FRANCHISE_FORMAT_ROYALITY5);
                                    String franchise_format_revenue5 = obj_format5.getString(TAG_FRANCHISE_FORMAT_REVENUE5);
                                    String franchise_format_profit5 = obj_format5.getString(TAG_FRANCHISE_FORMAT_PROFIT5);
                                    String franchise_format_act5 = obj_format5.getString(TAG_FRANCHISE_FORMAT_ACT5);
                                    String franchise_format_min_sqft5 = obj_format5.getString(TAG_FRANCHISE_FORMAT_MIN_SQFT5);
                                    String franchise_format_max_sqft5 = obj_format5.getString(TAG_FRANCHISE_FORMAT_MAX_SQFT5);

                                    edt_format_name_5.setText("" + franchise_format_name5);
                                    edt_format_spaceneeded_minimum_5.setText("" + franchise_format_min_sqft5);
                                    edt_format_spaceneeded_maximum_5.setText("" + franchise_format_max_sqft5);
                                    edt_format_investment_needed_minimum_5.setText("" + franchise_format_min_investment5);
                                    edt_format_investment_needed_maximum_5.setText("" + franchise_format_max_investment5);
                                    edt_format_brand_fee_5.setText("" + franchise_format_fee5);
                                    edt_format_staff_required_5.setText("" + franchise_format_no_of_staffs5);
                                    edt_format_royalty_commission_5.setText("" + franchise_format_royality5);
                                    edt_format_salespartner_monthly_revenue_5.setText("" + franchise_format_revenue5);
                                    edt_format_operating_profitmargin_5.setText("" + franchise_format_profit5);

                                } else if (franchise_format.equals("6")) {

                                    JSONObject obj_format1 = arr_formats.getJSONObject(0);

                                    String franchise_id1 = obj_format1.getString(TAG_FRANCHISE_ID1);
                                    String franchise_format_name1 = obj_format1.getString(TAG_FRANCHISE_FORMAT_NAME1);
                                    String franchise_format_min_investment1 = obj_format1.getString(TAG_FRANCHISE_FORMAT_MIN_INVESTMENT1);
                                    String franchise_format_max_investment1 = obj_format1.getString(TAG_FRANCHISE_FORMAT_MAX_INVESTMENT1);
                                    String franchise_format_fee1 = obj_format1.getString(TAG_FRANCHISE_FORMAT_FEE1);
                                    String franchise_format_no_of_staffs1 = obj_format1.getString(TAG_FRANCHISE_FORMAT_NO_OF_STAFFS1);
                                    String franchise_format_royality1 = obj_format1.getString(TAG_FRANCHISE_FORMAT_ROYALITY1);
                                    String franchise_format_revenue1 = obj_format1.getString(TAG_FRANCHISE_FORMAT_REVENUE1);
                                    String franchise_format_profit1 = obj_format1.getString(TAG_FRANCHISE_FORMAT_PROFIT1);
                                    String franchise_format_act1 = obj_format1.getString(TAG_FRANCHISE_FORMAT_ACT1);
                                    String franchise_format_min_sqft1 = obj_format1.getString(TAG_FRANCHISE_FORMAT_MIN_SQFT1);
                                    String franchise_format_max_sqft1 = obj_format1.getString(TAG_FRANCHISE_FORMAT_MAX_SQFT1);


                                    edt_format_name_1.setText("" + franchise_format_name1);
                                    edt_format_spaceneeded_minimum_1.setText("" + franchise_format_min_sqft1);
                                    edt_format_spaceneeded_maximum_1.setText("" + franchise_format_max_sqft1);
                                    edt_format_investment_needed_minimum_1.setText("" + franchise_format_min_investment1);
                                    edt_format_investment_needed_maximum_1.setText("" + franchise_format_max_investment1);
                                    edt_format_brand_fee_1.setText("" + franchise_format_fee1);
                                    edt_format_staff_required_1.setText("" + franchise_format_no_of_staffs1);
                                    edt_format_royalty_commission_1.setText("" + franchise_format_royality1);
                                    edt_format_salespartner_monthly_revenue_1.setText("" + franchise_format_revenue1);
                                    edt_format_operating_profitmargin_1.setText("" + franchise_format_profit1);

                                    JSONObject obj_format2 = arr_formats.getJSONObject(1);

                                    String franchise_id2 = obj_format2.getString(TAG_FRANCHISE_ID2);
                                    String franchise_format_name2 = obj_format2.getString(TAG_FRANCHISE_FORMAT_NAME2);
                                    String franchise_format_min_investment2 = obj_format2.getString(TAG_FRANCHISE_FORMAT_MIN_INVESTMENT2);
                                    String franchise_format_max_investment2 = obj_format2.getString(TAG_FRANCHISE_FORMAT_MAX_INVESTMENT2);
                                    String franchise_format_fee2 = obj_format2.getString(TAG_FRANCHISE_FORMAT_FEE2);
                                    String franchise_format_no_of_staffs2 = obj_format2.getString(TAG_FRANCHISE_FORMAT_NO_OF_STAFFS2);
                                    String franchise_format_royality2 = obj_format2.getString(TAG_FRANCHISE_FORMAT_ROYALITY2);
                                    String franchise_format_revenue2 = obj_format2.getString(TAG_FRANCHISE_FORMAT_REVENUE2);
                                    String franchise_format_profit2 = obj_format2.getString(TAG_FRANCHISE_FORMAT_PROFIT2);
                                    String franchise_format_act2 = obj_format2.getString(TAG_FRANCHISE_FORMAT_ACT2);
                                    String franchise_format_min_sqft2 = obj_format2.getString(TAG_FRANCHISE_FORMAT_MIN_SQFT2);
                                    String franchise_format_max_sqft2 = obj_format2.getString(TAG_FRANCHISE_FORMAT_MAX_SQFT2);

                                    edt_format_name_2.setText("" + franchise_format_name2);
                                    edt_format_spaceneeded_minimum_2.setText("" + franchise_format_min_sqft2);
                                    edt_format_spaceneeded_maximum_2.setText("" + franchise_format_max_sqft2);
                                    edt_format_investment_needed_minimum_2.setText("" + franchise_format_min_investment2);
                                    edt_format_investment_needed_maximum_2.setText("" + franchise_format_max_investment2);
                                    edt_format_brand_fee_2.setText("" + franchise_format_fee2);
                                    edt_format_staff_required_2.setText("" + franchise_format_no_of_staffs2);
                                    edt_format_royalty_commission_2.setText("" + franchise_format_royality2);
                                    edt_format_salespartner_monthly_revenue_2.setText("" + franchise_format_revenue2);
                                    edt_format_operating_profitmargin_2.setText("" + franchise_format_profit2);


                                    JSONObject obj_format3 = arr_formats.getJSONObject(2);

                                    String franchise_id3 = obj_format3.getString(TAG_FRANCHISE_ID3);
                                    String franchise_format_name3 = obj_format3.getString(TAG_FRANCHISE_FORMAT_NAME3);
                                    String franchise_format_min_investment3 = obj_format3.getString(TAG_FRANCHISE_FORMAT_MIN_INVESTMENT3);
                                    String franchise_format_max_investment3 = obj_format3.getString(TAG_FRANCHISE_FORMAT_MAX_INVESTMENT3);
                                    String franchise_format_fee3 = obj_format3.getString(TAG_FRANCHISE_FORMAT_FEE3);
                                    String franchise_format_no_of_staffs3 = obj_format3.getString(TAG_FRANCHISE_FORMAT_NO_OF_STAFFS3);
                                    String franchise_format_royality3 = obj_format3.getString(TAG_FRANCHISE_FORMAT_ROYALITY3);
                                    String franchise_format_revenue3 = obj_format3.getString(TAG_FRANCHISE_FORMAT_REVENUE3);
                                    String franchise_format_profit3 = obj_format3.getString(TAG_FRANCHISE_FORMAT_PROFIT3);
                                    String franchise_format_act3 = obj_format3.getString(TAG_FRANCHISE_FORMAT_ACT3);
                                    String franchise_format_min_sqft3 = obj_format3.getString(TAG_FRANCHISE_FORMAT_MIN_SQFT3);
                                    String franchise_format_max_sqft3 = obj_format3.getString(TAG_FRANCHISE_FORMAT_MAX_SQFT3);

                                    edt_format_name_3.setText("" + franchise_format_name3);
                                    edt_format_spaceneeded_minimum_3.setText("" + franchise_format_min_sqft3);
                                    edt_format_spaceneeded_maximum_3.setText("" + franchise_format_max_sqft3);
                                    edt_format_investment_needed_minimum_3.setText("" + franchise_format_min_investment3);
                                    edt_format_investment_needed_maximum_3.setText("" + franchise_format_max_investment3);
                                    edt_format_brand_fee_3.setText("" + franchise_format_fee3);
                                    edt_format_staff_required_3.setText("" + franchise_format_no_of_staffs3);
                                    edt_format_royalty_commission_3.setText("" + franchise_format_royality3);
                                    edt_format_salespartner_monthly_revenue_3.setText("" + franchise_format_revenue3);
                                    edt_format_operating_profitmargin_3.setText("" + franchise_format_profit3);

                                    JSONObject obj_format4 = arr_formats.getJSONObject(3);

                                    String franchise_id4 = obj_format4.getString(TAG_FRANCHISE_ID4);
                                    String franchise_format_name4 = obj_format4.getString(TAG_FRANCHISE_FORMAT_NAME4);
                                    String franchise_format_min_investment4 = obj_format4.getString(TAG_FRANCHISE_FORMAT_MIN_INVESTMENT4);
                                    String franchise_format_max_investment4 = obj_format4.getString(TAG_FRANCHISE_FORMAT_MAX_INVESTMENT4);
                                    String franchise_format_fee4 = obj_format4.getString(TAG_FRANCHISE_FORMAT_FEE4);
                                    String franchise_format_no_of_staffs4 = obj_format4.getString(TAG_FRANCHISE_FORMAT_NO_OF_STAFFS4);
                                    String franchise_format_royality4 = obj_format4.getString(TAG_FRANCHISE_FORMAT_ROYALITY4);
                                    String franchise_format_revenue4 = obj_format4.getString(TAG_FRANCHISE_FORMAT_REVENUE4);
                                    String franchise_format_profit4 = obj_format4.getString(TAG_FRANCHISE_FORMAT_PROFIT4);
                                    String franchise_format_act4 = obj_format4.getString(TAG_FRANCHISE_FORMAT_ACT4);
                                    String franchise_format_min_sqft4 = obj_format4.getString(TAG_FRANCHISE_FORMAT_MIN_SQFT4);
                                    String franchise_format_max_sqft4 = obj_format4.getString(TAG_FRANCHISE_FORMAT_MAX_SQFT4);

                                    edt_format_name_4.setText("" + franchise_format_name4);
                                    edt_format_spaceneeded_minimum_4.setText("" + franchise_format_min_sqft4);
                                    edt_format_spaceneeded_maximum_4.setText("" + franchise_format_max_sqft4);
                                    edt_format_investment_needed_minimum_4.setText("" + franchise_format_min_investment4);
                                    edt_format_investment_needed_maximum_4.setText("" + franchise_format_max_investment4);
                                    edt_format_brand_fee_4.setText("" + franchise_format_fee4);
                                    edt_format_staff_required_4.setText("" + franchise_format_no_of_staffs4);
                                    edt_format_royalty_commission_4.setText("" + franchise_format_royality4);
                                    edt_format_salespartner_monthly_revenue_4.setText("" + franchise_format_revenue4);
                                    edt_format_operating_profitmargin_4.setText("" + franchise_format_profit4);

                                    JSONObject obj_format5 = arr_formats.getJSONObject(4);

                                    String franchise_id5 = obj_format5.getString(TAG_FRANCHISE_ID5);
                                    String franchise_format_name5 = obj_format5.getString(TAG_FRANCHISE_FORMAT_NAME5);
                                    String franchise_format_min_investment5 = obj_format5.getString(TAG_FRANCHISE_FORMAT_MIN_INVESTMENT5);
                                    String franchise_format_max_investment5 = obj_format5.getString(TAG_FRANCHISE_FORMAT_MAX_INVESTMENT5);
                                    String franchise_format_fee5 = obj_format5.getString(TAG_FRANCHISE_FORMAT_FEE5);
                                    String franchise_format_no_of_staffs5 = obj_format5.getString(TAG_FRANCHISE_FORMAT_NO_OF_STAFFS5);
                                    String franchise_format_royality5 = obj_format5.getString(TAG_FRANCHISE_FORMAT_ROYALITY5);
                                    String franchise_format_revenue5 = obj_format5.getString(TAG_FRANCHISE_FORMAT_REVENUE5);
                                    String franchise_format_profit5 = obj_format5.getString(TAG_FRANCHISE_FORMAT_PROFIT5);
                                    String franchise_format_act5 = obj_format5.getString(TAG_FRANCHISE_FORMAT_ACT5);
                                    String franchise_format_min_sqft5 = obj_format5.getString(TAG_FRANCHISE_FORMAT_MIN_SQFT5);
                                    String franchise_format_max_sqft5 = obj_format5.getString(TAG_FRANCHISE_FORMAT_MAX_SQFT5);

                                    edt_format_name_5.setText("" + franchise_format_name5);
                                    edt_format_spaceneeded_minimum_5.setText("" + franchise_format_min_sqft5);
                                    edt_format_spaceneeded_maximum_5.setText("" + franchise_format_max_sqft5);
                                    edt_format_investment_needed_minimum_5.setText("" + franchise_format_min_investment5);
                                    edt_format_investment_needed_maximum_5.setText("" + franchise_format_max_investment5);
                                    edt_format_brand_fee_5.setText("" + franchise_format_fee5);
                                    edt_format_staff_required_5.setText("" + franchise_format_no_of_staffs5);
                                    edt_format_royalty_commission_5.setText("" + franchise_format_royality5);
                                    edt_format_salespartner_monthly_revenue_5.setText("" + franchise_format_revenue5);
                                    edt_format_operating_profitmargin_5.setText("" + franchise_format_profit5);

                                    JSONObject obj_format6 = arr_formats.getJSONObject(5);

                                    String franchise_id6 = obj_format6.getString(TAG_FRANCHISE_ID6);
                                    String franchise_format_name6 = obj_format6.getString(TAG_FRANCHISE_FORMAT_NAME6);
                                    String franchise_format_min_investment6 = obj_format6.getString(TAG_FRANCHISE_FORMAT_MIN_INVESTMENT6);
                                    String franchise_format_max_investment6 = obj_format6.getString(TAG_FRANCHISE_FORMAT_MAX_INVESTMENT6);
                                    String franchise_format_fee6 = obj_format6.getString(TAG_FRANCHISE_FORMAT_FEE6);
                                    String franchise_format_no_of_staffs6 = obj_format6.getString(TAG_FRANCHISE_FORMAT_NO_OF_STAFFS6);
                                    String franchise_format_royality6 = obj_format6.getString(TAG_FRANCHISE_FORMAT_ROYALITY6);
                                    String franchise_format_revenue6 = obj_format6.getString(TAG_FRANCHISE_FORMAT_REVENUE6);
                                    String franchise_format_profit6 = obj_format6.getString(TAG_FRANCHISE_FORMAT_PROFIT6);
                                    String franchise_format_act6 = obj_format6.getString(TAG_FRANCHISE_FORMAT_ACT6);
                                    String franchise_format_min_sqft6 = obj_format6.getString(TAG_FRANCHISE_FORMAT_MIN_SQFT6);
                                    String franchise_format_max_sqft6 = obj_format6.getString(TAG_FRANCHISE_FORMAT_MAX_SQFT6);

                                    edt_format_name_6.setText("" + franchise_format_name6);
                                    edt_format_spaceneeded_minimum_6.setText("" + franchise_format_min_sqft6);
                                    edt_format_spaceneeded_maximum_6.setText("" + franchise_format_max_sqft6);
                                    edt_format_investment_needed_minimum_6.setText("" + franchise_format_min_investment6);
                                    edt_format_investment_needed_maximum_6.setText("" + franchise_format_max_investment6);
                                    edt_format_brand_fee_6.setText("" + franchise_format_fee6);
                                    edt_format_staff_required_6.setText("" + franchise_format_no_of_staffs6);
                                    edt_format_royalty_commission_6.setText("" + franchise_format_royality6);
                                    edt_format_salespartner_monthly_revenue_6.setText("" + franchise_format_revenue6);
                                    edt_format_operating_profitmargin_6.setText("" + franchise_format_profit6);

                                }


                            } catch (Exception e) {

                            }
                        }

                    } else if (success == 0) {

                        dialog.dismiss();

                        Alerter.create(Activity_FranchiseProfile_Update.this)
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
                Alerter.create(Activity_FranchiseProfile_Update.this)
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
                params.put("franchise_key", str_franchise_key);

                System.out.println("USER_IDDDDDDDDDDDDDDDDDD ::: " + str_user_id);
                System.out.println("BUSINESS KEYYYYYYYYYYYYY ::: " + str_franchise_key);

                return params;
            }

        };

        // Adding request to request queue
        queue.add(request);
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
            image = data.getParcelableArrayListExtra(ImagePickerActivity.INTENT_EXTRA_SELECTED_IMAGES);
            StringBuilder sb = new StringBuilder();
            for (int i = 0, l = images.size(); i < l; i++) {

                String str_img_path = images.get(i).getPath();

                Bitmap bmBitmap = BitmapFactory.decodeFile(str_img_path);
                ByteArrayOutputStream bao = new ByteArrayOutputStream();
                bmBitmap.compress(Bitmap.CompressFormat.JPEG, 25, bao);
                byte[] ba = bao.toByteArray();
                encodedstring = Base64.encodeToString(ba, 0);
                Log.e("base64", "-----" + encodedstring);

                if (image_type.equals("store_pics")) {

                    Arraylist_image_encode.add(encodedstring);

                } else if (image_type.equals("logo")) {
                    encoded_logo = encodedstring;
                } else {
                    TastyToast.makeText(getApplicationContext(), "Internal Error !", TastyToast.LENGTH_LONG, TastyToast.ERROR);
                }
            }

            if (image_type.equals("store_pics")) {
                Encode_Image1();
            } else if (image_type.equals("logo")) {
                encoded_logo = encodedstring;
            } else {
                TastyToast.makeText(getApplicationContext(), "Internal Server Error !", TastyToast.LENGTH_LONG, TastyToast.ERROR);
            }
        }
    }

    public void Encode_Image1() {
        listString = TextUtils.join("IMAGE:", Arraylist_image_encode);
    }


    public void ImagePicker1() {
        ImagePicker.create(this)
                .folderMode(true) // set folder mode (false by default)
                .folderTitle("Folder") // folder selection title
                .imageTitle("Tap to select") // image selection title
                .single() // single mode
                .multi() // multi mode (default mode)
                .limit(1) // max images can be selected (999 by default)
                .showCamera(true) // show camera or not (true by default)
                .imageDirectory("Camera")   // captured image directory name ("Camera" folder by default)
                .origin(image) // original selected images, used in multi mode
                .start(REQUEST_CODE_PICKER1); // start image picker activity with request code
    }

    // Traditional intent
    public void startWithIntent1() {
        Intent intent = new Intent(this, ImagePickerActivity.class);

        intent.putExtra(ImagePickerActivity.INTENT_EXTRA_FOLDER_MODE, true);
        intent.putExtra(ImagePickerActivity.INTENT_EXTRA_MODE, ImagePickerActivity.MODE_MULTIPLE);
        intent.putExtra(ImagePickerActivity.INTENT_EXTRA_LIMIT, 1);
        intent.putExtra(ImagePickerActivity.INTENT_EXTRA_SHOW_CAMERA, true);
        intent.putExtra(ImagePickerActivity.INTENT_EXTRA_SELECTED_IMAGES, image);
        intent.putExtra(ImagePickerActivity.INTENT_EXTRA_FOLDER_TITLE, "Album");
        intent.putExtra(ImagePickerActivity.INTENT_EXTRA_IMAGE_TITLE, "Tap to select images");
        intent.putExtra(ImagePickerActivity.INTENT_EXTRA_IMAGE_DIRECTORY, "Camera");
        startActivityForResult(intent, REQUEST_CODE_PICKER1);
    }


    /******************************************
     *    SUBMIT FRANCHISE PROFILE FORM
     * *******************************************/

    private void Function_Submit_FranchiseProfile() {

        StringRequest request = new StringRequest(Request.Method.POST,
                AppConfig.url_update_franchise_profile, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d(TAG, response.toString());

                try {
                    JSONObject obj = new JSONObject(response);
                    System.out.println("FINAL SUBMIT RESPONSE : " + response);
                    int success = obj.getInt("status");
                    if (success == 1) {
                        dialog.dismiss();

                        Alerter.create(Activity_FranchiseProfile_Update.this)
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
                params.put("designation", str_designation);
                params.put("brand_name", str_brand_name);
                params.put("about_company", str_about_company);
                params.put("products_services", str_all_prod_serv);
                params.put("offering", str_opportunity_offered);
                params.put("industry", str_final_industry_update);
                params.put("years", str_year_company_opr_start);
                params.put("headquarters", str_final_headquaters);
                params.put("sales_partners", str_no_of_salespartner);
                params.put("salespartner_looking", str_lookfor_in_salespartner);
                params.put("salespartner_expectation", str_kindof_support);
                params.put("procedure_salespartner", str_procedure_salespartner);
                params.put("location", str_final_location_update);
                params.put("franchise_format_count", str_no_of_formats);

                //OTHERS
                params.put("logo", encoded_logo);
                params.put("images", listString);
                params.put("doc_name", "DOCUMENT NAMEEEEEEEEE");
                params.put("document_type", "DOCUMENT TYPEEEE");
                params.put("currency_change", str_user_currency);
                params.put("user_id", str_user_id);
                params.put("franchise_key", str_franchise_key);


                //FORMAT 1
                params.put("format_name", str_format_name_1);
                params.put("min_sq", str_format_spaceneeded_minimum_1);
                params.put("max_sq", str_format_spaceneeded_maximum_1);
                params.put("min_invest", str_format_investment_needed_minimum_1);
                params.put("max_invest", str_format_investment_needed_maximum_1);
                params.put("brand_fee", str_format_brand_fee_1);
                params.put("staff_require", str_format_staff_required_1);
                params.put("royalty_commission", str_format_royalty_commission_1);
                params.put("monthly_revenue", str_format_salespartner_monthly_revenue_1);
                params.put("profit_margin", str_format_operating_profitmargin_1);


                //FORMAT 2
                params.put("format_name2", str_format_name_2);
                params.put("min_sq2", str_format_spaceneeded_minimum_2);
                params.put("max_sq2", str_format_spaceneeded_maximum_2);
                params.put("min_invest2", str_format_investment_needed_minimum_2);
                params.put("max_invest2", str_format_investment_needed_maximum_2);
                params.put("brand_fee2", str_format_brand_fee_2);
                params.put("staff_require2", str_format_staff_required_2);
                params.put("royalty_commission2", str_format_royalty_commission_2);
                params.put("monthly_revenue2", str_format_salespartner_monthly_revenue_2);
                params.put("profit_margin2", str_format_operating_profitmargin_2);


                //FORMAT 3
                params.put("format_name3", str_format_name_3);
                params.put("min_sq3", str_format_spaceneeded_minimum_3);
                params.put("max_sq3", str_format_spaceneeded_maximum_3);
                params.put("min_invest3", str_format_investment_needed_minimum_3);
                params.put("max_invest3", str_format_investment_needed_maximum_3);
                params.put("brand_fee3", str_format_brand_fee_3);
                params.put("staff_require3", str_format_staff_required_3);
                params.put("royalty_commission3", str_format_royalty_commission_3);
                params.put("monthly_revenue3", str_format_salespartner_monthly_revenue_3);
                params.put("profit_margin3", str_format_operating_profitmargin_3);


                //FORMAT 4
                params.put("format_name4", str_format_name_4);
                params.put("min_sq4", str_format_spaceneeded_minimum_4);
                params.put("max_sq4", str_format_spaceneeded_maximum_4);
                params.put("min_invest4", str_format_investment_needed_minimum_4);
                params.put("max_invest4", str_format_investment_needed_maximum_4);
                params.put("brand_fee4", str_format_brand_fee_4);
                params.put("staff_require4", str_format_staff_required_4);
                params.put("royalty_commission4", str_format_royalty_commission_4);
                params.put("monthly_revenue4", str_format_salespartner_monthly_revenue_4);
                params.put("profit_margin4", str_format_operating_profitmargin_4);

                //FORMAT 5
                params.put("format_name5", str_format_name_5);
                params.put("min_sq5", str_format_spaceneeded_minimum_5);
                params.put("max_sq5", str_format_spaceneeded_maximum_5);
                params.put("min_invest5", str_format_investment_needed_minimum_5);
                params.put("max_invest5", str_format_investment_needed_maximum_5);
                params.put("brand_fee5", str_format_brand_fee_5);
                params.put("staff_require5", str_format_staff_required_5);
                params.put("royalty_commission5", str_format_royalty_commission_5);
                params.put("monthly_revenue5", str_format_salespartner_monthly_revenue_5);
                params.put("profit_margin5", str_format_operating_profitmargin_5);


                //FORMAT 6
                params.put("format_name6", str_format_name_6);
                params.put("min_sq6", str_format_spaceneeded_minimum_6);
                params.put("max_sq6", str_format_spaceneeded_maximum_6);
                params.put("min_invest6", str_format_investment_needed_minimum_6);
                params.put("max_invest6", str_format_investment_needed_maximum_6);
                params.put("brand_fee6", str_format_brand_fee_6);
                params.put("staff_require6", str_format_staff_required_6);
                params.put("royalty_commission6", str_format_royalty_commission_6);
                params.put("monthly_revenue6", str_format_salespartner_monthly_revenue_6);
                params.put("profit_margin6", str_format_operating_profitmargin_6);


                System.out.println("PERSON NAME :::::::::::" + str_auth_person_name);
                System.out.println("EMAIL :::::::::::" + str_email);
                System.out.println("MOBILE NUMBER :::::::::::" + str_mobile_num);
                System.out.println("DESIGNATION :::::::::::" + str_designation);
                System.out.println("BRAND NAME :::::::::::" + str_brand_name);
                System.out.println("OPPORTUNITIES ::::" + str_opportunity_offered);
                System.out.println("BUSINESS INDUSTRIES ::::" + str_final_industry_update);
                System.out.println("ABOUT COMPANY :::::::::::" + str_about_company);
                System.out.println("ALL PRODUCTS & SERVICES :::::::::::" + str_all_prod_serv);
                System.out.println("YEAR OPERATIONS START :::::::::::" + str_year_company_opr_start);
                System.out.println("HEADQUATERS ::::" + str_final_headquaters);
                System.out.println("NO OF SALES PARTNER :::::::::::" + str_no_of_salespartner);
                System.out.println("LOOK FOR IN SALES PARTNER :::::::::::" + str_lookfor_in_salespartner);
                System.out.println("KIND OF SUPPORT  :::::::::::" + str_kindof_support);
                System.out.println("PROCEDURE SALES PARTNER :::::::::::" + str_procedure_salespartner);
                System.out.println("EXPAND LOCATIONS :::::::::::" + str_final_location_update);
                System.out.println("NO OF SALES PARTNER FORMATS :::::::::::" + str_no_of_formats);
                System.out.println("USER CURRENCY :::::::::::" + str_user_currency);
                System.out.println("USER ID :::::::::::" + str_user_id);


                System.out.println("LOGOOOOOOOOO:::::::::::" + encoded_logo);
                System.out.println("IMAGESSSSSSS:::::::::::" + listString);


                return params;
            }
        };
        queue.add(request);
    }


}