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
import android.widget.RadioGroup;
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
import com.nguyenhoanglam.imagepicker.activity.ImagePicker;
import com.nguyenhoanglam.imagepicker.activity.ImagePickerActivity;
import com.nguyenhoanglam.imagepicker.model.Image;
import com.sdsmdg.tastytoast.TastyToast;
import com.tapadoo.alerter.Alerter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import banyan.com.awesomebusiness.R;
import banyan.com.awesomebusiness.global.AppConfig;
import banyan.com.awesomebusiness.global.SessionManager;
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
    TextView txt_img_count;
    String str_user_currency = "";

    SessionManager session;
    public static String str_user_id, str_user_name, str_user_email, str_user_photoo;
    public static String str_get_user_name, str_get_user_email, str_get_user_mobile, str_get_user_desigination, str_get_user_company;

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

    public static final String TAG_INDUSTRT_NAME = "name";
    public static final String TAG_INDUSTRY_KEY = "key";
    public static final String TAG_INDUSTRY_TYPE = "type";

    public static final String TAG_HEADQUATERS_PLACE = "place";
    public static final String TAG_HEADQUATERS_KEY = "key";
    public static final String TAG_HEADQUATERS_TYPE = "type";

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

    AutoCompleteTextView auto_headquaters;

    //Multi Auto Complete Textview
    MultiAutoCompleteTextView auto_franchise_business_industry, auto_franchise_business_expand_locations;
    //Final location and industry and headquaters sring to post
    String str_final_headquaters, str_final_location_update, str_final_industry_update = "";
    String str_Headquaters;

    // EDIT TEXT AND THEIR RELATED STRINGS
    EditText edt_name, edt_email, edt_mobile_num, edt_designation, edt_brand_name,
            edt_about_company, edt_train_support, edt_ideal_candidate,
            edt_lookfor_in_salespartner, edt_kindof_support, edt_procedure_salespartner;

    String str_auth_person_name, str_email, str_mobile_num, str_designation,
            str_brand_name, str_about_company, str_train_support,
            str_ideal_candidate = "";

    //Franchise Type
    RadioGroup radioGroup;
    RadioButton rdb_master_franchise, rdb_unite_franchise;
    String str_franchise_type;

    // Spinner AND STRINGS FOR OPPORTUNITIES OFFERED
    Spinner spn_opportunities_offered;
    String str_selected_opportunity, str_opportunity_offered = "";

    // STRING & SEARCHABLE SPINNER  FOR -- THE YEAR COMPANY'S OPERATIONS START
    Spinner spn_years_company_opr_start;
    String str_year_company_opr_start = "";

    // FORMAT 1
    EditText edt_exp_return_minimum, edt_exp_return_maximum,
            edt_investment_needed_minimum, edt_investment_needed_maximum;
    // FORMAT 1 - STRINGS
    String str_exp_return_minimum, str_exp_return_maximum,
            str_investment_needed_minimum, str_investment_needed_maximum = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_franchiseprofile);

        mToolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("Add Franchise Profile");
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
        str_get_user_name = sharedPreferences.getString("str_profile_user_name", "str_profile_user_name");
        str_get_user_email = sharedPreferences.getString("str_profile_user_email", "str_profile_user_email");
        str_get_user_mobile = sharedPreferences.getString("str_user_mobile", "str_user_mobile");
        str_get_user_desigination = sharedPreferences.getString("str_user_designation", "str_user_designation");
        str_get_user_company = sharedPreferences.getString("str_user_company_name", "str_user_company_name");

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

        edt_name = (EditText) findViewById(R.id.edt_auth_person_name);
        edt_email = (EditText) findViewById(R.id.edt_official_email);
        edt_mobile_num = (EditText) findViewById(R.id.edt_mobile_number);
        edt_designation = (EditText) findViewById(R.id.edt_designation);
        edt_brand_name = (EditText) findViewById(R.id.edt_brandname);
        edt_about_company = (EditText) findViewById(R.id.edt_about_company);
        edt_train_support = (EditText) findViewById(R.id.edt_training_support);
        edt_ideal_candidate = (EditText) findViewById(R.id.edt_ideal_candidate);
        edt_lookfor_in_salespartner = (EditText) findViewById(R.id.edt_wt_lookfor_sales_partners);
        edt_kindof_support = (EditText) findViewById(R.id.edt_wt_kindof_support);
        edt_procedure_salespartner = (EditText) findViewById(R.id.edt_wt_procedure);

        spn_opportunities_offered = (Spinner) findViewById(R.id.spn_opportunities_offered);

        auto_headquaters = (AutoCompleteTextView) findViewById(R.id.edit_Franchise_profile_company_headquaters);
        auto_franchise_business_industry = (MultiAutoCompleteTextView) findViewById(R.id.franchise_profile_multi_busi_industry);
        auto_franchise_business_expand_locations = (MultiAutoCompleteTextView) findViewById(R.id.franchise_profile_multi_busi_expand_locations);

        //// NO.OF SALES PARTNER FORMATS EDITTEXT'S
        ////
        ////FORMAT 1
        edt_exp_return_minimum = (EditText) findViewById(R.id.edt_format_one_space_needed_one);
        edt_exp_return_maximum = (EditText) findViewById(R.id.edt_format_one_space_needed_two);
        edt_investment_needed_minimum = (EditText) findViewById(R.id.edt_format_one_inr_one);
        edt_investment_needed_maximum = (EditText) findViewById(R.id.edt_format_one_inr_two);
        //For Adding comma's inbetween numbers in edit text
        edt_exp_return_minimum.addTextChangedListener(new NumberTextWatcherForThousand(edt_exp_return_minimum));
        edt_exp_return_maximum.addTextChangedListener(new NumberTextWatcherForThousand(edt_exp_return_maximum));
        edt_investment_needed_minimum.addTextChangedListener(new NumberTextWatcherForThousand(edt_investment_needed_minimum));
        edt_investment_needed_maximum.addTextChangedListener(new NumberTextWatcherForThousand(edt_investment_needed_maximum));

        txt_img_count = (TextView) findViewById(R.id.franch_prof_txt_img_count);
        btn_add_faility_stores_pics = (Button) findViewById(R.id.btn_facility_photos);
        btn_add_brand_logo_pic = (Button) findViewById(R.id.btn_brand_logo);
        btn_submit = (Button) findViewById(R.id.btn_submit);

        radioGroup = (RadioGroup) findViewById(R.id.add_franchiseprofile_radiogroup_franchise_type);
        // rdb_master_franchise = (RadioButton) findViewById(R.id.rdb_masterfranchise);
        // rdb_unite_franchise = (RadioButton) findViewById(R.id.rdb_unitfranchise);

        edt_name.setText("" + str_get_user_name);
        edt_email.setText("" + str_get_user_email);
        edt_mobile_num.setText("" + str_get_user_mobile);
        edt_designation.setText("" + str_get_user_desigination);

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // find which radio button is selected
                if (checkedId == R.id.rdb_masterfranchise) {

                    str_franchise_type = "1";

                } else if (checkedId == R.id.rdb_unitfranchise) {

                    str_franchise_type = "0";

                } else {

                    str_franchise_type = "";
                }
            }
        });

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

        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                /*****************************
                 * Get Multi Industry Details
                 * ************************/

                //Here we are getting it in a string to put it in shared preferences and get and show in the preview screen
                String str_industries_text = auto_franchise_business_industry.getText().toString();
                //Here we are getting it in a string array to find the location id and location type to post in json

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
                //Here we are getting it in a string to put it in shared preferences and get and show in the preview screen
                String str_locations_text = auto_franchise_business_expand_locations.getText().toString();
                //Here we are getting it in a string array to find the location id and location type to post in json
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

                str_Headquaters = auto_headquaters.getText().toString();
                int Headquaters_position = Arraylist_location_place.indexOf(str_Headquaters);
                String select_Headquaters_id = Arraylist_location_key.get(Headquaters_position + 1);
                String select_Headquaters_type = Arraylist_location_type.get(Headquaters_position + 1);
                str_final_headquaters = select_Headquaters_id + "-" + select_Headquaters_type;
                System.out.println("FINAL SELECTED HEADQUATERS :: " + str_final_headquaters);

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
                str_train_support = edt_train_support.getText().toString();
                str_ideal_candidate = edt_ideal_candidate.getText().toString();
                str_year_company_opr_start = spn_years_company_opr_start.getSelectedItem().toString();

                ///////// GETTING THE VALUES FOR THE STRING FROM THEIR RELATED NO OF FORMAT'S EDIT TEXT'S
                str_exp_return_minimum = edt_exp_return_minimum.getText().toString();
                str_exp_return_maximum = edt_exp_return_maximum.getText().toString();
                str_investment_needed_minimum = edt_investment_needed_minimum.getText().toString();
                str_investment_needed_maximum = edt_investment_needed_maximum.getText().toString();

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
                } else if (str_franchise_type.equals("")) {
                    radioGroup.requestFocus();
                    TastyToast.makeText(getApplicationContext(), "Select Franchise Type", TastyToast.LENGTH_LONG, TastyToast.WARNING);
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
                } else if (str_train_support.equals("")) {
                    edt_train_support.setError("Enter All Products & Services");
                    edt_train_support.requestFocus();
                    TastyToast.makeText(getApplicationContext(), "This Cannot be Empty", TastyToast.LENGTH_LONG, TastyToast.WARNING);
                } else if (str_final_headquaters.equals("")) {
                    auto_headquaters.setError("Enter Company Headquaters");
                    auto_headquaters.requestFocus();
                    TastyToast.makeText(getApplicationContext(), "Company Headquaters Cannot be Empty", TastyToast.LENGTH_LONG, TastyToast.WARNING);
                } else if (str_ideal_candidate.equals("")) {
                    edt_ideal_candidate.setError("Enter No Of Sales Partners");
                    edt_ideal_candidate.requestFocus();
                    TastyToast.makeText(getApplicationContext(), "This Cannot be Empty", TastyToast.LENGTH_LONG, TastyToast.WARNING);
                } else if (str_exp_return_minimum.equals("")) {
                    edt_exp_return_minimum.setError("Enter Minimum Space Needed");
                    TastyToast.makeText(getApplicationContext(), "This Cannot be Empty", TastyToast.LENGTH_LONG, TastyToast.WARNING);
                } else if (str_exp_return_maximum.equals("")) {
                    edt_exp_return_maximum.setError("Enter Maximum Space Needed");
                    TastyToast.makeText(getApplicationContext(), "This Cannot be Empty", TastyToast.LENGTH_LONG, TastyToast.WARNING);
                } else if (str_investment_needed_minimum.equals("")) {
                    edt_investment_needed_minimum.setError("Enter Minimum Investment Needed");
                    TastyToast.makeText(getApplicationContext(), "This Cannot be Empty", TastyToast.LENGTH_LONG, TastyToast.WARNING);
                } else if (str_investment_needed_maximum.equals("")) {
                    edt_investment_needed_maximum.setError("Enter Maximum Investment Needed");
                    TastyToast.makeText(getApplicationContext(), "This Cannot be Empty", TastyToast.LENGTH_LONG, TastyToast.WARNING);
                } else {

                  /*  dialog = new SpotsDialog(Activity_FranchiseProfile.this);
                    dialog.show();
                    queue = Volley.newRequestQueue(getApplicationContext());
                    Function_Submit_FranchiseProfile(); */


                    SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                    SharedPreferences.Editor editor = sharedPreferences.edit();

                    editor.putString("prev_fran_str_auth_person_name", str_auth_person_name);
                    editor.putString("prev_fran_str_email", str_email);
                    editor.putString("prev_fran_str_mobile_num", str_mobile_num);
                    editor.putString("prev_fran_str_designation", str_designation);
                    editor.putString("prev_fran_str_brand_name", str_brand_name);
                    editor.putString("prev_fran_str_opportunity_offered", str_opportunity_offered);
                    editor.putString("prev_fran_str_opportunity_offered_text", str_selected_opportunity);
                    editor.putString("prev_fran_str_final_industry_update", str_final_industry_update);
                    editor.putString("prev_fran_str_final_industry_update_text", str_industries_text);
                    editor.putString("prev_fran_str_about_company", str_about_company);
                    editor.putString("prev_fran_str_train_support", str_train_support);
                    editor.putString("prev_fran_str_year_company_opr_start", str_year_company_opr_start);
                    editor.putString("prev_fran_str_final_headquaters", str_final_headquaters);
                    editor.putString("prev_fran_str_final_headquaters_text", str_Headquaters);
                    editor.putString("prev_fran_str_ideal_candidate", str_ideal_candidate);
                    editor.putString("prev_fran_str_final_location_update", str_final_location_update);
                    editor.putString("prev_fran_str_final_location_update_text", str_locations_text);
                    editor.putString("prev_fran_str_investment_needed_minimum", str_investment_needed_minimum);
                    editor.putString("prev_fran_str_investment_needed_maximum", str_investment_needed_maximum);
                    editor.putString("prev_fran_str_exp_return_minimum", str_exp_return_minimum);
                    editor.putString("prev_fran_str_exp_return_maximum", str_exp_return_maximum);
                    editor.putString("prev_fran_encoded_logo", encoded_logo);
                    editor.putString("prev_fran_listString", listString);
                    editor.putString("prev_fran_str_franchise_type", str_franchise_type);


                    editor.commit();

                    Intent i = new Intent(getApplicationContext(), Activity_FranchiseProfile_Preview.class);
                    startActivity(i);
                    finish();


                }

            }
        });

        try

        {
            dialog = new SpotsDialog(Activity_FranchiseProfile.this);
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

                            String industry_name = obj1.optString(TAG_INDUSTRT_NAME);
                            String industry_key = obj1.optString(TAG_INDUSTRY_KEY);
                            String industry_type = obj1.optString(TAG_INDUSTRY_TYPE);

                            Arraylist_industry_name.add(industry_name);
                            Arraylist_industry_key.add(industry_key);
                            Arraylist_industry_type.add(industry_type);

                        }
                        try {

                            System.out.println("ARAAAAY :: " + Arraylist_industry_name);

                            ArrayAdapter<String> adapter_sector = new ArrayAdapter<String>(Activity_FranchiseProfile.this,
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

                            String headquaters_place = obj1.optString(TAG_HEADQUATERS_PLACE);
                            String headquaters_key = obj1.optString(TAG_HEADQUATERS_KEY);
                            String headquaters_type = obj1.optString(TAG_HEADQUATERS_TYPE);

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

                            String expand_location_place = obj1.optString(TAG_HEADQUATERS_PLACE);
                            String expand_location_key = obj1.optString(TAG_HEADQUATERS_KEY);
                            String expand_location_type = obj1.optString(TAG_HEADQUATERS_TYPE);

                            Arraylist_expand_location_place.add(expand_location_place);
                            Arraylist_expand_location_key.add(expand_location_key);
                            Arraylist_expand_location_type.add(expand_location_type);
                        }

                        try {

                            System.out.println("ARAAAAY :: " + Arraylist_expand_location_place);

                            ArrayAdapter<String> adapter_location = new ArrayAdapter<String>(Activity_FranchiseProfile.this,
                                    android.R.layout.simple_list_item_1, Arraylist_expand_location_place);
                            auto_franchise_business_expand_locations.setAdapter(adapter_location);

                            auto_franchise_business_expand_locations
                                    .setTokenizer(new MultiAutoCompleteTextView.CommaTokenizer());
                            auto_franchise_business_expand_locations.setThreshold(1);

                            System.out.println("ARAAAAY :: " + 222222);

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

                    txt_img_count.setText("(" + Arraylist_image_encode.size() + ")" + " Images Added");
                    btn_add_faility_stores_pics.setText("Change Images");

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

                        Alerter.create(Activity_FranchiseProfile.this)
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
                params.put("name", str_auth_person_name);
                params.put("email", str_email);
                params.put("mobile_no", str_mobile_num);
                params.put("designation", str_designation);
                params.put("brand_name", str_brand_name);
                params.put("offering", str_opportunity_offered);
                params.put("industry", str_final_industry_update);
                params.put("about_company", str_about_company);
                params.put("products_services", str_train_support);
                params.put("years", str_year_company_opr_start);
                params.put("headquarters", str_final_headquaters);
                params.put("salespartner_looking", str_ideal_candidate);
                params.put("location", str_final_location_update);
                params.put("currency_change", str_user_currency);
                params.put("min_invest", str_investment_needed_minimum);
                params.put("max_invest", str_investment_needed_maximum);
                params.put("monthly_revenue", str_exp_return_minimum);
                params.put("max_revenue", str_exp_return_maximum);
                params.put("logo", encoded_logo);
                params.put("images", listString);
                params.put("doc_name", "");
                params.put("document_type", "");
                params.put("documentsname", "");
                params.put("user_id", str_user_id);
                params.put("franchise_type", str_franchise_type);

                System.out.println("PERSON NAME :::::::::::" + str_auth_person_name);
                System.out.println("EMAIL :::::::::::" + str_email);
                System.out.println("MOBILE NUMBER :::::::::::" + str_mobile_num);
                System.out.println("DESIGNATION :::::::::::" + str_designation);
                System.out.println("BRAND NAME :::::::::::" + str_brand_name);
                System.out.println("OPPORTUNITIES ::::" + str_opportunity_offered);
                System.out.println("BUSINESS INDUSTRIES ::::" + str_final_industry_update);
                System.out.println("ABOUT COMPANY :::::::::::" + str_about_company);
                System.out.println("ALL PRODUCTS & SERVICES :::::::::::" + str_train_support);
                System.out.println("YEAR OPERATIONS START :::::::::::" + str_year_company_opr_start);
                System.out.println("HEADQUATERS ::::" + str_final_headquaters);
                System.out.println("NO OF SALES PARTNER :::::::::::" + str_ideal_candidate);
                System.out.println("EXPAND LOCATIONS :::::::::::" + str_final_location_update);
                System.out.println("USER CURRENCY :::::::::::" + str_user_currency);
                System.out.println("USER ID :::::::::::" + str_user_id);

                System.out.println("LOGOOOOOOOOO:::::::::::" + encoded_logo);
                System.out.println("IMAGESSSSSSS:::::::::::" + listString);

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