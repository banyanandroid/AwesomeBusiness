package banyan.com.awesomebusiness.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
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
import dmax.dialog.SpotsDialog;

/**
 * CREATED BY SCHAN ON 24-JUL-17.
 */

public class Activity_FranchiseProfile extends AppCompatActivity {

    private Toolbar mToolbar;
    Button btn_submit, btn_add_faility_stores_pics, btn_add_brand_logo_pic;
    SpotsDialog dialog;
    public static RequestQueue queue;
    String TAG = "FRANCHISE PROFILE";
    TextView t1;

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


    // SEARCHABLE SPINNER AND STRING FOR COUNTRY PHONE CODE
    SearchableSpinner spn_country_code;
    String str_selected_country_id, str_selected_phone_code;

    public static final String TAG_COUNTRY_ID = "country_id";
    public static final String TAG_COUNTRY_PHONE_CODE = "country_phone_code";
    public static final String TAG_COUNTRY_NAME = "country_name";

    public static final String TAG_INDUSTRT_NAME = "name";
    public static final String TAG_INDUSTRY_KEY = "key";
    public static final String TAG_INDUSTRY_TYPE = "type";

    public static final String TAG_HEADQUATERS_PLACE = "place";
    public static final String TAG_HEADQUATERS_KEY = "key";
    public static final String TAG_HEADQUATERS_TYPE = "type";

    ArrayList<String> Arraylist_country_id = null;
    ArrayList<String> Arraylist_country_phone_code = null;

    private ArrayAdapter<String> adapter_country_code;

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
    String str_final_expand__Location = "";

    ChipLayout chip_industry, chip_expand_locations;

    AutoCompleteTextView auto_headquaters;


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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_franchise_profile);

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

        Arraylist_country_id = new ArrayList<String>();
        Arraylist_country_phone_code = new ArrayList<String>();


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

        //Image Upload
        Arraylist_image_encode = new ArrayList<String>();


        // ARRAYLIST , ARRAY ADAPTER , SEARCHABLE SPINNER  FOR -- THE YEAR COMPANY'S OPERATIONS START
        ArrayList<String> years = new ArrayList<String>();
        int CurerntYear = Calendar.getInstance().get(Calendar.YEAR);
        for (int i = 1800; i <= CurerntYear; i++) {
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


        // SEARCHABLESPINNER
        spn_country_code = (SearchableSpinner) findViewById(R.id.franchise_profile_spinner_country);
        spn_country_code.setTitle("Country Code");

        ChipLayout.MAX_CHARACTER_COUNT = 20;
        chip_industry = (ChipLayout) findViewById(R.id.Franchise_chipset_industry);
        chip_expand_locations = (ChipLayout) findViewById(R.id.Franchise_chipset_locations_expand);

        auto_headquaters = (AutoCompleteTextView) findViewById(R.id.edit_Franchise_profile_company_headquaters);

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
                str_selected_opportunity = spn_opportunities_offered.getSelectedItem().toString();
                switch (str_selected_opportunity) {
                    case "":
                        spn_opportunities_offered.requestFocus();
                        TastyToast.makeText(getApplicationContext(), "Select One", TastyToast.LENGTH_LONG, TastyToast.WARNING);

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
                } else if (str_about_company.equals("")) {
                    edt_about_company.setError("Enter about your company");
                    edt_about_company.requestFocus();
                    TastyToast.makeText(getApplicationContext(), "This Cannot be Empty", TastyToast.LENGTH_LONG, TastyToast.WARNING);
                } else if (str_all_prod_serv.equals("")) {
                    edt_all_prod.setError("Enter All Products & Services");
                    edt_all_prod.requestFocus();
                    TastyToast.makeText(getApplicationContext(), "This Cannot be Empty", TastyToast.LENGTH_LONG, TastyToast.WARNING);
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
                }
                //Unfortunately Stopped during this checking process
               /* else if (str_final_headquaters.equals("")) {
                    auto_headquaters.setError("Enter Company Headquaters");
                    auto_headquaters.requestFocus();
                    TastyToast.makeText(getApplicationContext(), "Company Headquaters Cannot be Empty", TastyToast.LENGTH_LONG, TastyToast.WARNING);
                } else if (str_final_location_update.equals("")) {
                    chip_expand_locations.setFocusable(true);
                    chip_expand_locations.requestFocus();
                    TastyToast.makeText(getApplicationContext(), "Enter The Locations You Want to Expand", TastyToast.LENGTH_LONG, TastyToast.WARNING);
                } else if (str_final_industry_update.equals("")) {
                    chip_industry.setFocusable(true);
                    chip_industry.requestFocus();
                    TastyToast.makeText(getApplicationContext(), "Enter Your Industry", TastyToast.LENGTH_LONG, TastyToast.WARNING);
                }*/


                // THIS ELSE IF STATEMENT IS TO CHECK WHETHER THE  NO OF SALES PARTNER FORMATS IS SELECTED OR NOT
                else if (str_no_of_formats.equals("No of Sales Partner Formats")) {
                    TastyToast.makeText(getApplicationContext(), "Please Select No of Sales Partner Formats ", TastyToast.LENGTH_LONG, TastyToast.WARNING);
                }

                if (str_no_of_formats != null && !str_no_of_formats.isEmpty()) {
                    // THIS SWITCH STATEMENT IS TO VALIDATE
                    // the STRINGS GOT FROM EDIT TEXT'S OF GET THE SELECTED NO OF FORMATS
                    switch (str_no_of_formats) {

                        case "1":
                            // IF STATEMENT FOR VALIDATING STRINGS OF FORMAT 1(WHEN NO OF SELECTED FORMATS 1)
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
                            break;

                        case "2":
                            // IF STATEMENT FOR VALIDATING STRING OF FORMAT 1 (WHEN NO OF SELECTED FORMATS 2)
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
                            }
                            break;

                        case "3":
                            // IF STATEMENT FOR VALIDATING STRING OF FORMAT 1 (WHEN NO OF SELECTED FORMATS 3)
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
                            }

                            break;

                        case "4":

                            // IF STATEMENT FOR VALIDATING STRING OF FORMAT 1 (WHEN NO OF SELECTED FORMATS 4)
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
                            }

                            break;

                        case "5":

                            // IF STATEMENT FOR VALIDATING STRING OF FORMAT 1 (WHEN NO OF SELECTED FORMATS 5)
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
                            }

                            break;

                        case "6":
                            // IF STATEMENT FOR VALIDATING STRING OF FORMAT 1 (WHEN NO OF SELECTED FORMATS 6)
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
                            }

                            break;
                    }

                } else {


                    System.out.println("PERSON NAME :::::::::::" + str_auth_person_name);
                    System.out.println("EMAIL :::::::::::" + str_email);
                    System.out.println("MOBILE NUMBER :::::::::::" + str_mobile_num);
                    System.out.println("DESIGNATION :::::::::::" + str_designation);
                    System.out.println("BRAND NAME :::::::::::" + str_brand_name);
                    System.out.println("ABOUT COMPANY :::::::::::" + str_about_company);
                    System.out.println("ALL PRODUCTS & SERVICES :::::::::::" + str_all_prod_serv);
                    System.out.println("YEAR OPERATIONS START :::::::::::" + str_year_company_opr_start);
                    System.out.println(" NO OF SALES PARTNER :::::::::::" + str_no_of_salespartner);
                    System.out.println("LOOK FOR IN SALES PARTNER :::::::::::" + str_lookfor_in_salespartner);
                    System.out.println("KIND OF SUPPORT  :::::::::::" + str_kindof_support);
                    System.out.println("PROCEDURE SALES PARTNER :::::::::::" + str_procedure_salespartner);
                    System.out.println("FORMAT 1111111112 NAME :::::::::::" + str_format_name_1);
                    System.out.println("FORMAT 2222222222 NAME :::::::::::" + str_format_name_2);
                    System.out.println("OPPORTUNITIES ::::" + str_opportunity_offered);

                    try {
                        dialog = new SpotsDialog(Activity_FranchiseProfile.this);
                        dialog.show();
                        queue = Volley.newRequestQueue(getApplicationContext());
                        // Function_Submit_FranchiseProfile();

                    } catch (Exception e) {
                        // TODO: handle exception
                    }
                }
            }
        });

        try {
            dialog = new SpotsDialog(Activity_FranchiseProfile.this);
            dialog.show();
            queue = Volley.newRequestQueue(getApplicationContext());
            Get_CountryCode();
        } catch (Exception e) {
            // TODO: handle exception
        }

    }

    /*****************************
     * To get  Country Code
     ***************************/

    public void Get_CountryCode() {
        String tag_json_obj = "json_obj_req";
        System.out.println("STEP  1111111111111");
        StringRequest request = new StringRequest(Request.Method.POST,
                AppConfig.url_country, new Response.Listener<String>() {


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

                            String Country_id = obj1.getString(TAG_COUNTRY_ID);
                            String Country_PhoneCode = obj1.getString(TAG_COUNTRY_PHONE_CODE);
                            String Country_Name = obj1.getString(TAG_COUNTRY_NAME);

                            Arraylist_country_id.add(Country_id);
                            Arraylist_country_phone_code.add(Country_PhoneCode);
                        }


                        try {

                            adapter_country_code = new ArrayAdapter<String>(Activity_FranchiseProfile.this,
                                    android.R.layout.simple_list_item_1, Arraylist_country_phone_code);
                            spn_country_code.setAdapter(adapter_country_code);

                            spn_country_code.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
                                                        long arg3) {
                                    t1 = (TextView) arg1;
                                    str_selected_phone_code = t1.getText().toString();
                                    str_selected_country_id = Arraylist_country_id.get(arg2);

                                    System.out.println("Countryyyyyyyyyyyyy CODEEEEEEE ::::::::::::::: " + str_selected_phone_code);
                                    System.out.println("Countryyyyyyyyyyyyy IIDDDDDDDD ::::::::::::::: " + str_selected_country_id);

                                }
                            });

                        } catch (Exception e) {

                        }

                        try {
                            queue = Volley.newRequestQueue(getApplicationContext());
                            Get_Business_industry();

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

                            ArrayAdapter<String> adapter_sector = new ArrayAdapter<String>(Activity_FranchiseProfile.this,
                                    android.R.layout.simple_list_item_1, Arraylist_industry_name);
                            chip_industry.setAdapter(adapter_sector);

                            chip_industry.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                @Override
                                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                                    System.out.println("Position :::::::: " + position);

                                    t1 = (TextView) view;
                                    String str_industry_key = t1.getText().toString();
                                    int i = Arraylist_industry_name.indexOf(str_industry_key);

                                    String str_select_sector_key = Arraylist_industry_key.get(i);
                                    String str_select_sector_type = Arraylist_industry_type.get(i);
                                    String str_select_item = str_select_sector_key + "-" + str_select_sector_type;
                                    Arraylist_selected_industry_key.add(str_select_item);

                                    for (String s : Arraylist_selected_industry_key) {
                                        str_final_industry += s + ",";
                                    }

                                    System.out.println("FINAL SECTORRRRRRRRRR :: " + str_final_industry);


                                }
                            });

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

                            ArrayAdapter<String> adapter_location = new ArrayAdapter<String>(Activity_FranchiseProfile.this,
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
                                   /* Arraylist_selected_location.add(str_select_item);

                                    for (String s : Arraylist_selected_location) {
                                        str_final_Business_Location += s + ",";
                                    }*/
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

                            ArrayAdapter<String> adapter_location = new ArrayAdapter<String>(Activity_FranchiseProfile.this,
                                    android.R.layout.simple_list_item_1, Arraylist_expand_location_place);
                            chip_expand_locations.setAdapter(adapter_location);


                            System.out.println("ARAAAAY :: " + 222222);
                            chip_expand_locations.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                @Override
                                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


                                    System.out.println("Position :::::::: " + position);

                                    t1 = (TextView) view;
                                    String str_expand_location_key = t1.getText().toString();
                                    int i = Arraylist_expand_location_place.indexOf(str_expand_location_key);

                                    String str_select_expand_location_key = Arraylist_expand_location_key.get(i);
                                    String str_select_expand_location_type = Arraylist_expand_location_type.get(i);
                                    String str_select_expand_item = str_select_expand_location_key + "-" + str_select_expand_location_type;
                                    Arraylist_selected_expand_location.add(str_select_expand_item);

                                    for (String s : Arraylist_selected_expand_location) {
                                        str_final_expand__Location += s + ",";
                                    }

                                    System.out.println("FINAL Expand Location :: " + str_final_expand__Location);


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

                        Alerter.create(Activity_FranchiseProfile.this)
                                .setTitle("Success")
                                .setText("Bussiness Profile Added Successfully")
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


                params.put("name", str_auth_person_name);
                params.put("email", str_email);
                params.put("mobile_no", str_mobile_num);
                params.put("designation", str_designation);
                params.put("brand_name", str_brand_name);
                params.put("about_company", str_about_company);
                params.put("products_services", str_all_prod_serv);

                params.put("offering", str_opportunity_offered);
                params.put("industry", str_opportunity_offered);
                params.put("years", str_opportunity_offered);
                params.put("headquarters", str_opportunity_offered);
                params.put("sales_partners", str_opportunity_offered);
                params.put("salespartner_looking", str_opportunity_offered);
                params.put("salespartner_expectation", str_opportunity_offered);
                params.put("location", str_opportunity_offered);


                params.put("franchise_format_count", str_opportunity_offered);
                params.put("currency_change", str_opportunity_offered);


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

                return params;
            }
        };
        queue.add(request);
    }


}



/*

                      if ((isset($_POST['name'])) &&
                        (isset($_POST['email']))
                        && (isset($_POST['mobile_no']))
                        && (isset($_POST['designation']))
                        && (isset($_POST['brand_name']))
                        && (isset($_POST['offering'])) &&
                        (isset($_POST['industry'])) &&
                        (isset($_POST['about_company'])) &&
                        (isset($_POST['products_services']))
                        && (isset($_POST['years'])) &&
                        (isset($_POST['headquarters'])) &&
                        (isset($_POST['sales_partners'])) &&
                        (isset($_POST['salespartner_looking'])) &&
                        (isset($_POST['salespartner_expectation'])) &&
                        (isset($_POST['procedure_salespartner'])) &&
                        (isset($_POST['location'])) &&
                        (isset($_POST['franchise_format_count'])) &&
                        (isset($_POST['currency_change'])) &&


                        (isset($_POST['format_name'])) &&
                        (isset($_POST['min_sq'])) &&
                        (isset($_POST['max_sq'])) &&
                        (isset($_POST['min_invest'])) &&
                        (isset($_POST['max_invest'])) &&
                        (isset($_POST['brand_fee'])) &&
                        (isset($_POST['staff_require'])) &&
                        (isset($_POST['royalty_commission'])) &&
                        (isset($_POST['monthly_revenue'])) &&
                        (isset($_POST['profit_margin'])) &&


                        (isset($_POST['format_name2'])) &&
                        (isset($_POST['min_sq2'])) &&
                        (isset($_POST['max_sq2'])) &&
                        (isset($_POST['min_invest2'])) &&
                        (isset($_POST['max_invest2'])) &&
                        (isset($_POST['brand_fee2'])) &&
                        (isset($_POST['staff_require2'])) &&
                        (isset($_POST['royalty_commission2'])) &&
                        (isset($_POST['monthly_revenue2'])) &&
                        (isset($_POST['profit_margin2'])) &&


                        (isset($_POST['format_name3'])) &&
                        (isset($_POST['min_sq3'])) &&
                        (isset($_POST['max_sq3'])) &&
                        (isset($_POST['min_invest3'])) &&
                        (isset($_POST['max_invest3'])) &&
                        (isset($_POST['brand_fee3'])) &&
                        (isset($_POST['staff_require3'])) &&
                        (isset($_POST['royalty_commission3'])) &&
                        (isset($_POST['monthly_revenue3'])) &&
                        (isset($_POST['profit_margin3'])) &&


                        (isset($_POST['format_name4'])) &&
                        (isset($_POST['min_sq4'])) &&
                        (isset($_POST['max_sq4'])) &&
                        (isset($_POST['min_invest4'])) &&
                        (isset($_POST['max_invest4'])) &&
                        (isset($_POST['brand_fee4'])) &&
                        (isset($_POST['staff_require4'])) &&
                        (isset($_POST['royalty_commission4'])) &&
                        (isset($_POST['monthly_revenue4'])) &&
                        (isset($_POST['profit_margin4'])) &&


                        (isset($_POST['format_name5'])) &&
                        (isset($_POST['min_sq5'])) &&
                        (isset($_POST['max_sq5'])) &&
                        (isset($_POST['min_invest5'])) &&
                        (isset($_POST['max_invest5'])) &&
                        (isset($_POST['brand_fee5'])) &&
                        (isset($_POST['staff_require5'])) &&
                        (isset($_POST['royalty_commission5'])) &&
                        (isset($_POST['monthly_revenue5'])) &&
                        (isset($_POST['profit_margin5'])) &&


                        (isset($_POST['format_name6'])) &&
                        (isset($_POST['min_sq6'])) &&
                        (isset($_POST['max_sq6'])) &&
                        (isset($_POST['min_invest6'])) &&
                        (isset($_POST['max_invest6'])) &&
                        (isset($_POST['brand_fee6'])) &&
                        (isset($_POST['staff_require6'])) &&
                        (isset($_POST['royalty_commission6'])) &&
                        (isset($_POST['monthly_revenue6'])) &&
                        (isset($_POST['profit_margin6'])) &&


                        (isset($_POST['logo'])) &&
                        (isset($_POST['images'])) &&
                        (isset($_POST['doc_name'])) &&
                        (isset($_POST['document_type']))) {


 */