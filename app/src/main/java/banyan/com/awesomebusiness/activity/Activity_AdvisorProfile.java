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

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
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
import java.util.Iterator;
import java.util.Map;

import banyan.com.awesomebusiness.R;
import banyan.com.awesomebusiness.global.AppConfig;
import banyan.com.awesomebusiness.global.SessionManager;
import dmax.dialog.SpotsDialog;

/**
 * CREATED BY SCHAN ON 24-JUL-17.
 */

public class Activity_AdvisorProfile extends AppCompatActivity {

    private Toolbar mToolbar;
    Button btn_submit, btn_add_brand_logo_pic;
    SpotsDialog dialog;
    public static RequestQueue queue;
    String TAG = "ADVISOR PROFILE";
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
    ArrayList<String> Arraylist_specialized_sector_fetched_industries = null;
    ArrayList<String> Arraylist_specialized_sector_selected_final_industry = null;

    /* Arralist fetched Geo Area  Location list */
    ArrayList<String> Arraylist_geo_area_fetched_location = null;
    ArrayList<String> Arraylist_geo_area_selected_final_location = null;

    /* Arralist user Location list */
    ArrayList<String> Arraylist_fetched_user_location = null;
    ArrayList<String> Arraylist_selected_user_final_location = null;


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

    //Multi Auto Complete Textview
    MultiAutoCompleteTextView auto_user_location, auto_advisor_specialized_sector, auto_advisor_geo_area_locations;
    //Final location and industry and headquaters sring to post
    String str_final_headquaters, str_final_geo_area_location_update, str_final_specialized_industry_update, str_final_user_location_update = "";

    String str_locations_text, str_industries_text, str_user_locations_text;
    // EDIT TEXT AND THEIR RELATED STRINGS
    EditText edt_name, edt_email, edt_mobile_num, edt_company_name, edt_address_line_one, edt_address_line_two,
            edt_zipcode, edt_phone_number, edt_summary, edt_business_minimum, edt_business_maximum;

    String str_auth_person_name, str_email, str_mobile_num, str_company_name, str_address_line_one, str_address_line_two,
            str_zipcode, str_phone_number, str_summary, str_business_minimum, str_business_maximum = "";

    Integer int_business_minimum, int_business_maximum = 0;


    // Spinner AND STRINGS FOR OPPORTUNITIES OFFERED
    Spinner spn_advisor_type;
    String str_selected_advisor_type, str_advisor_type = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_advisor_profile);

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
        str_get_user_name = sharedPreferences.getString("str_profile_user_name", "str_profile_user_name");
        str_get_user_email = sharedPreferences.getString("str_profile_user_email", "str_profile_user_email");
        str_get_user_mobile = sharedPreferences.getString("str_user_mobile", "str_user_mobile");
        str_get_user_desigination = sharedPreferences.getString("str_user_designation", "str_user_designation");
        str_get_user_company = sharedPreferences.getString("str_user_company_name", "str_user_company_name");

        Arraylist_industry_name = new ArrayList<String>();
        Arraylist_industry_key = new ArrayList<String>();
        Arraylist_industry_type = new ArrayList<String>();

        Arraylist_specialized_sector_fetched_industries = new ArrayList<String>();
        Arraylist_specialized_sector_selected_final_industry = new ArrayList<String>();

        Arraylist_selected_industry_key = new ArrayList<String>();

        Arraylist_location_place = new ArrayList<String>();
        Arraylist_location_key = new ArrayList<String>();
        Arraylist_location_type = new ArrayList<String>();

        Arraylist_geo_area_fetched_location = new ArrayList<String>();
        Arraylist_geo_area_selected_final_location = new ArrayList<String>();

        Arraylist_fetched_user_location = new ArrayList<String>();
        Arraylist_selected_user_final_location = new ArrayList<String>();

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

        edt_name = (EditText) findViewById(R.id.edt_auth_person_name);
        edt_email = (EditText) findViewById(R.id.edt_official_email);
        edt_mobile_num = (EditText) findViewById(R.id.edt_mobile_number);
        edt_company_name = (EditText) findViewById(R.id.edt_company_name);
        edt_address_line_one = (EditText) findViewById(R.id.edt_address_line_one);
        edt_address_line_two = (EditText) findViewById(R.id.edt_address_line_two);
        edt_zipcode = (EditText) findViewById(R.id.edt_zip_code);
        edt_phone_number = (EditText) findViewById(R.id.edt_phone_number);
        edt_summary = (EditText) findViewById(R.id.edt_company_summary);
        edt_business_minimum = (EditText) findViewById(R.id.edt_business_size_from);
        edt_business_maximum = (EditText) findViewById(R.id.edt_business_size_to);

        spn_advisor_type = (Spinner) findViewById(R.id.spn_advisor_type);

        auto_advisor_specialized_sector = (MultiAutoCompleteTextView) findViewById(R.id.advisor_profile_multi_specialized_sector);
        auto_advisor_geo_area_locations = (MultiAutoCompleteTextView) findViewById(R.id.advisor_profile_multi_geo_area_covered);
        auto_user_location = (MultiAutoCompleteTextView) findViewById(R.id.edit_advisor_profile_user_location);


        txt_img_count = (TextView) findViewById(R.id.franch_prof_txt_img_count);
        btn_add_brand_logo_pic = (Button) findViewById(R.id.btn_brand_logo);
        btn_submit = (Button) findViewById(R.id.btn_submit);

        edt_name.setText("" + str_get_user_name);
        edt_email.setText("" + str_get_user_email);
        edt_mobile_num.setText("" + str_get_user_mobile);
        edt_company_name.setText("" + str_get_user_company);

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
                 * Get specialized sector Details
                 * ************************/
                //Here we are getting it in a string to put it in shared preferences and get and show in the preview screen
                str_industries_text = auto_advisor_specialized_sector.getText().toString();
                //Here we are getting it in a string array to find the location id and location type to post in json
                String[] str_industries = auto_advisor_specialized_sector.getText().toString().split(", ");

                Arraylist_specialized_sector_fetched_industries.clear();
                for (int i = 0; i < str_industries.length; i++) {
                    Arraylist_specialized_sector_fetched_industries.add(str_industries[i]);
                }
                System.out.println("array : " + Arraylist_specialized_sector_fetched_industries);

                Arraylist_specialized_sector_selected_final_industry.clear();
                for (int i = 0; i < Arraylist_specialized_sector_fetched_industries.size(); i++) {

                    String get_indestry = Arraylist_specialized_sector_fetched_industries.get(i);
                    get_indestry = get_indestry.trim();
                    System.out.println("get_indestry : " + get_indestry);
                    int indus_position = Arraylist_industry_name.indexOf(get_indestry);
                    String select_sect_id = Arraylist_industry_key.get(indus_position);
                    String select_sect_type = Arraylist_industry_type.get(indus_position);

                    String sector = select_sect_id + "-" + select_sect_type;
                    Arraylist_specialized_sector_selected_final_industry.add(sector);

                    str_final_specialized_industry_update = TextUtils.join(", ", Arraylist_specialized_sector_selected_final_industry);

                }
                System.out.println("FINAL SELECTED INDUSTRYYYYYYYYYYYYY :: " + str_final_specialized_industry_update);


                /*****************************
                 * Get geo area locations Details
                 * ************************/
                //Here we are getting it in a string to put it in shared preferences and get and show in the preview screen
                str_locations_text = auto_advisor_geo_area_locations.getText().toString();
                //Here we are getting it in a string array to find the location id and location type to post in json
                String[] str_location = auto_advisor_geo_area_locations.getText().toString().split(", ");

                Arraylist_geo_area_fetched_location.clear();
                for (int i = 0; i < str_location.length; i++) {
                    Arraylist_geo_area_fetched_location.add(str_location[i]);
                }
                System.out.println("array : " + Arraylist_geo_area_fetched_location);

                Arraylist_geo_area_selected_final_location.clear();
                for (int i = 0; i < Arraylist_geo_area_fetched_location.size(); i++) {

                    String get_location = Arraylist_geo_area_fetched_location.get(i);
                    get_location = get_location.trim();
                    System.out.println("get_location : " + get_location);
                    int location_position = Arraylist_location_place.indexOf(get_location);
                    String select_location_id = Arraylist_location_key.get(location_position);
                    String select_location_type = Arraylist_location_type.get(location_position);

                    String location = select_location_id + "-" + select_location_type;
                    Arraylist_geo_area_selected_final_location.add(location);

                    str_final_geo_area_location_update = TextUtils.join(", ", Arraylist_geo_area_selected_final_location);

                }
                System.out.println("FINAL SELECTED GEOGRAPHICAL LOCATIONNNNNNNNN :: " + str_final_geo_area_location_update);

                /*****************************
                 * Get  USER Location Details
                 * ************************/
                //Here we are getting it in a string to put it in shared preferences and get and show in the preview screen
                str_user_locations_text = auto_user_location.getText().toString();
                //Here we are getting it in a string array to find the location id and location type to post in json
                String[] str_user_location = auto_user_location.getText().toString().split(", ");

                Arraylist_fetched_user_location.clear();
                for (int i = 0; i < str_user_location.length; i++) {
                    Arraylist_fetched_user_location.add(str_user_location[i]);
                }
                System.out.println("array : " + Arraylist_fetched_user_location);

                Arraylist_selected_user_final_location.clear();
                for (int i = 0; i < Arraylist_fetched_user_location.size(); i++) {

                    String get_location = Arraylist_fetched_user_location.get(i);
                    get_location = get_location.trim();
                    System.out.println("get_location : " + get_location);
                    int location_position = Arraylist_location_place.indexOf(get_location);
                    String select_location_id = Arraylist_location_key.get(location_position);
                    String select_location_type = Arraylist_location_type.get(location_position);

                    String location = select_location_id + "-" + select_location_type;
                    Arraylist_selected_user_final_location.add(location);

                    str_final_user_location_update = TextUtils.join(", ", Arraylist_selected_user_final_location);

                }
                System.out.println("FINAL USER LOCATIONN :: " + str_final_user_location_update);


                str_selected_advisor_type = spn_advisor_type.getSelectedItem().toString();
                System.out.println("ADVISOR TYPE" + str_selected_advisor_type);

                switch (str_selected_advisor_type) {
                    case "":
                        spn_advisor_type.requestFocus();
                        TastyToast.makeText(getApplicationContext(), "Select type", TastyToast.LENGTH_LONG, TastyToast.WARNING);

                    case "--Select Type--":
                        spn_advisor_type.requestFocus();
                        TastyToast.makeText(getApplicationContext(), "Select Advisor type", TastyToast.LENGTH_LONG, TastyToast.WARNING);

                    case "Advisor":
                        str_advisor_type = "1";
                        break;
                    case "Broker":
                        str_advisor_type = "2";
                        break;
                    case "Consultant":
                        str_advisor_type = "3";
                        break;
                    case "Any":
                        str_advisor_type = "4";
                        break;

                }


                ///////// GETTING THE VALUES FOR THE STRING FROM THEIR RELATED EDIT TEXT'S
                str_auth_person_name = edt_name.getText().toString();
                str_email = edt_email.getText().toString();
                str_mobile_num = edt_mobile_num.getText().toString();
                str_company_name = edt_company_name.getText().toString();
                str_address_line_one = edt_address_line_one.getText().toString();
                str_address_line_two = edt_address_line_two.getText().toString();
                str_zipcode = edt_zipcode.getText().toString();
                str_phone_number = edt_phone_number.getText().toString();
                str_summary = edt_summary.getText().toString();

                str_business_minimum = edt_business_minimum.getText().toString();
                str_business_maximum = edt_business_maximum.getText().toString();

                if (str_business_minimum.equals("")) {
                    edt_business_minimum.setError("Enter Minimum Business");
                    edt_business_minimum.requestFocus();
                    TastyToast.makeText(getApplicationContext(), "This Cannot be Empty", TastyToast.LENGTH_LONG, TastyToast.WARNING);
                } /*else {
                    int_business_minimum = Integer.valueOf(str_business_minimum);
                }*/

                if (str_business_maximum.equals("")) {
                    edt_business_maximum.setError("Enter Maximum Deal Size");
                    edt_business_maximum.requestFocus();
                    TastyToast.makeText(getApplicationContext(), "This Cannot be Empty", TastyToast.LENGTH_LONG, TastyToast.WARNING);
                } /*else {
                    int_business_maximum = Integer.valueOf(str_business_maximum);
                }
*/

                if (str_business_minimum.equals("")) {
                    edt_business_minimum.setError("Enter Minimum Business");
                    edt_business_minimum.requestFocus();
                    TastyToast.makeText(getApplicationContext(), "This Cannot be Empty", TastyToast.LENGTH_LONG, TastyToast.WARNING);
                } else if (str_business_maximum.equals("")) {
                    edt_business_maximum.setError("Enter Maximum Deal Size");
                    edt_business_maximum.requestFocus();
                    TastyToast.makeText(getApplicationContext(), "This Cannot be Empty", TastyToast.LENGTH_LONG, TastyToast.WARNING);
                }

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
                } else if (str_company_name.equals("")) {
                    edt_company_name.setError("Enter Designation");
                    edt_company_name.requestFocus();
                    TastyToast.makeText(getApplicationContext(), "Designation Cannot be Empty", TastyToast.LENGTH_LONG, TastyToast.WARNING);
                } else if (str_address_line_one.equals("")) {
                    edt_address_line_one.setError("Enter Address Line One");
                    edt_address_line_one.requestFocus();
                    TastyToast.makeText(getApplicationContext(), "Address Cannot be Empty", TastyToast.LENGTH_LONG, TastyToast.WARNING);
                } else if (str_address_line_two.equals("")) {
                    edt_address_line_two.setError("Enter Address Line Two");
                    edt_address_line_two.requestFocus();
                    TastyToast.makeText(getApplicationContext(), "Address Cannot be Empty", TastyToast.LENGTH_LONG, TastyToast.WARNING);
                } else if (str_zipcode.equals("")) {
                    edt_zipcode.setError("Enter Zip Code");
                    edt_zipcode.requestFocus();
                    TastyToast.makeText(getApplicationContext(), "Zip Code Cannot be Empty", TastyToast.LENGTH_LONG, TastyToast.WARNING);
                } else if (str_phone_number.equals("")) {
                    edt_phone_number.setError("Enter Phone number");
                    edt_phone_number.requestFocus();
                    TastyToast.makeText(getApplicationContext(), "Phone number Cannot be Empty", TastyToast.LENGTH_LONG, TastyToast.WARNING);
                } else if (str_summary.equals("")) {
                    edt_summary.setError("Enter Summary");
                    edt_summary.requestFocus();
                    TastyToast.makeText(getApplicationContext(), "Summary Cannot be Empty", TastyToast.LENGTH_LONG, TastyToast.WARNING);
                } /*else if (int_business_maximum <= int_business_minimum) {
                    edt_business_maximum.setError("Invalid Value");
                    edt_business_maximum.requestFocus();
                    TastyToast.makeText(getApplicationContext(), "Should be greater than Minimum Business Value", TastyToast.LENGTH_LONG, TastyToast.WARNING);
                }*/ else {

                   /* dialog = new SpotsDialog(Activity_AdvisorProfile.this);
                    dialog.show();
                    queue = Volley.newRequestQueue(getApplicationContext());
                    Function_Submit_AdvisorProfile(); */


                    SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                    SharedPreferences.Editor editor = sharedPreferences.edit();

                    editor.putString("prev_adv_prof_str_auth_person_name", str_auth_person_name);
                    editor.putString("prev_adv_prof_str_email", str_email);
                    editor.putString("prev_adv_prof_str_mobile_num", str_mobile_num);
                    editor.putString("prev_adv_prof_str_company_name", str_company_name);
                    editor.putString("prev_adv_prof_str_advisor_type", str_advisor_type);
                    editor.putString("prev_adv_prof_str_advisor_type_text", str_selected_advisor_type);
                    editor.putString("prev_adv_prof_str_address_line_one", str_address_line_one);
                    editor.putString("prev_adv_prof_str_address_line_two", str_address_line_two);
                    editor.putString("prev_adv_prof_str_final_user_location_update", str_final_user_location_update);
                    editor.putString("prev_adv_prof_str_final_user_location_update_text", str_user_locations_text);
                    editor.putString("prev_adv_prof_str_zipcode", str_zipcode);
                    editor.putString("prev_adv_prof_str_phone_number", str_phone_number);
                    editor.putString("prev_adv_prof_str_summary", str_summary);
                    editor.putString("prev_adv_prof_str_business_minimum", str_business_minimum);
                    editor.putString("prev_adv_prof_str_business_maximum", str_business_maximum);
                    editor.putString("prev_adv_prof_str_final_geo_area_location_update", str_final_geo_area_location_update);
                    editor.putString("prev_adv_prof_str_final_geo_area_text", str_locations_text);
                    editor.putString("prev_adv_prof_str_final_specialized_industry_update", str_final_specialized_industry_update);
                    editor.putString("prev_adv_prof_str_final_specialized_industry_text", str_industries_text);
                    editor.putString("prev_adv_prof_encoded_logo", encoded_logo);


                    editor.commit();

                    Intent i = new Intent(getApplicationContext(), Activity_AdvisorProfile_Preview.class);
                    startActivity(i);
                    finish();


                }

            }
        });

        try

        {
            dialog = new SpotsDialog(Activity_AdvisorProfile.this);
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

                            ArrayAdapter<String> adapter_sector = new ArrayAdapter<String>(Activity_AdvisorProfile.this,
                                    android.R.layout.simple_list_item_1, Arraylist_industry_name);
                            auto_advisor_specialized_sector.setAdapter(adapter_sector);

                            auto_advisor_specialized_sector
                                    .setTokenizer(new MultiAutoCompleteTextView.CommaTokenizer());
                            auto_advisor_specialized_sector.setThreshold(1);


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

                            ArrayAdapter<String> adapter_location = new ArrayAdapter<String>(Activity_AdvisorProfile.this,
                                    android.R.layout.simple_list_item_1, Arraylist_location_place);
                            auto_user_location.setAdapter(adapter_location);

                            auto_user_location
                                    .setTokenizer(new MultiAutoCompleteTextView.CommaTokenizer());
                            auto_user_location.setThreshold(1);

                            System.out.println("ARAAAAY :: " + 222222);


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

                            ArrayAdapter<String> adapter_location = new ArrayAdapter<String>(Activity_AdvisorProfile.this,
                                    android.R.layout.simple_list_item_1, Arraylist_expand_location_place);
                            auto_advisor_geo_area_locations.setAdapter(adapter_location);

                            auto_advisor_geo_area_locations
                                    .setTokenizer(new MultiAutoCompleteTextView.CommaTokenizer());
                            auto_advisor_geo_area_locations.setThreshold(1);

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
     *    SUBMIT ADVISOR PROFILE FORM
     * *******************************************/

    private void Function_Submit_AdvisorProfile() {

        StringRequest request = new StringRequest(Request.Method.POST,
                AppConfig.url_add_advisor_profile, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d(TAG, response.toString());

                try {
                    JSONObject obj = new JSONObject(response);
                    System.out.println("RESPONSE : " + response);
                    int success = obj.getInt("status");
                    if (success == 1) {
                        dialog.dismiss();

                        Alerter.create(Activity_AdvisorProfile.this)
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
                params.put("company_name", str_company_name);
                params.put("advisor_type", str_advisor_type);
                params.put("addr_1", str_address_line_one);
                params.put("addr_2", str_address_line_two);
                params.put("city", str_final_user_location_update);
                params.put("zip", str_zipcode);
                params.put("ph_no", str_phone_number);
                params.put("business_description", str_summary);

                params.put("location", str_final_geo_area_location_update);
                params.put("sectors", str_final_specialized_industry_update);
                params.put("logo", encoded_logo);

                params.put("user_id", str_user_id);
                params.put("currency", str_user_currency);
                params.put("size_from", str_business_minimum);
                params.put("size_to", str_business_maximum);

                System.out.println("PERSON NAME :::::::::::" + str_auth_person_name);
                System.out.println("EMAIL :::::::::::" + str_email);
                System.out.println("MOBILE NUMBER :::::::::::" + str_mobile_num);
                System.out.println("COMPANY NAME :::::::::::" + str_company_name);
                System.out.println("OPPORTUNITIES ::::" + str_advisor_type);
                System.out.println("BUSINESS INDUSTRIES ::::" + str_final_specialized_industry_update);
                System.out.println("HEADQUATERS ::::" + str_final_headquaters);
                System.out.println("EXPAND LOCATIONS :::::::::::" + str_final_geo_area_location_update);
                System.out.println("USER CURRENCY :::::::::::" + str_user_currency);
                System.out.println("USER ID :::::::::::" + str_user_id);
                System.out.println("LOGOOOOOOOOO:::::::::::" + encoded_logo);


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