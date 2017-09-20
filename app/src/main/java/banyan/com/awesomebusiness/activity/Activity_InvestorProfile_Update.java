package banyan.com.awesomebusiness.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.MultiAutoCompleteTextView;
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
 * Created by Schan on 24-Jul-17.
 */

public class Activity_InvestorProfile_Update extends AppCompatActivity {

    private Toolbar mToolbar;
    SpotsDialog dialog;
    public static RequestQueue queue;
    String TAG = "";
    TextView t1;
    String str_user_currency, str_user_id, str_investor_key = "";

    // COMPANY LOGO - PIC Upload
    String listString = "";
    String encodedstring = "";
    private ArrayList<Image> images = new ArrayList<>();
    ArrayList<String> Arraylist_image_encode = null;

    private int REQUEST_CODE_PICKER = 2000;

    Button btn_add_pic, btn_submit;

    EditText edt_name, edt_mobile_number, edt_email, edt_dealsize_minimum, edt_dealsize_maximum,
            edt_company_name, edt_designation, edt_wed_linkedin,
            edt_company_sector, edt_kind_business_interested, edt_company_about;

    //AUTOCOMPLETETEXTVIEW
    AutoCompleteTextView auto_headquaters;
    String str_select_item, str_final_headquaters = "";

    //Multi Auto Complete Textview
    MultiAutoCompleteTextView auto_busineeslist, auto_locationlist;

    TextView txt_role, txt_interested;

    SearchableSpinner spn_i_am, spn_interested_in;

    String str_final_business_sector, str_final_Business_Location = "";

    String str_name, str_mobile, str_email, str_deal_minimum, str_deal_maximum, str_company_name,
            str_designation, str_web_linkedin, str_company_sector, str_kindof_business_interested, str_company_about = "";

    Integer int_deal_minimum, int_deal_maximum = 0;

    public static final String TAG_ROLE_ID = "investor_an_id";
    public static final String TAG_ROLE_NAME = "investor_an_name";

    public static final String TAG_INTEREST_ID = "investor_interest_id";
    public static final String TAG_INTEREST_NAME = "investor_interest_name";

    public static final String TAG_SECTOR_NAME = "name";
    public static final String TAG_SECTOR_KEY = "key";
    public static final String TAG_SECTOR_TYPE = "type";

    public static final String TAG_LOC_PLACE = "place";
    public static final String TAG_LOC_KEY = "key";
    public static final String TAG_LOC_TYPE = "type";

    public static final String TAG_HEADQUATERS_PLACE = "place";
    public static final String TAG_HEADQUATERS_KEY = "key";
    public static final String TAG_HEADQUATERS_TYPE = "type";

    String str_final_location, str_final_industries = "";

    ArrayList<String> Arraylist_update_location = null;
    ArrayList<String> Arraylist_update_industries = null;

    ArrayList<String> Arraylist_investor_role_id = null;
    ArrayList<String> Arraylist_investor_role_name = null;

    ArrayList<String> Arraylist_investor_interest_id = null;
    ArrayList<String> Arraylist_investor_interest_name = null;

    ArrayList<String> Arraylist_sector_name = null;
    ArrayList<String> Arraylist_sector_key = null;
    ArrayList<String> Arraylist_sector_type = null;


    /* Arralist fetched indestries list */
    ArrayList<String> Arraylist_fetched_industries = null;
    ArrayList<String> Arraylist_selected_final_industry = null;

    /* Arralist fetched Location list */
    ArrayList<String> Arraylist_fetched_location = null;
    ArrayList<String> Arraylist_selected_final_location = null;

    /*Multi Select*/
    ArrayList<String> Arraylist_selected_sectorkey = null;
    ArrayList<String> Arraylist_selected_location = null;

    ArrayList<String> Arraylist_location_place = null;
    ArrayList<String> Arraylist_location_key = null;
    ArrayList<String> Arraylist_location_type = null;

    private ArrayAdapter<String> adapter_i_am;
    private ArrayAdapter<String> adapter_interested;

    String str_selected_role_id, str_selected_role_name = "";
    String str_selected_interest_id, str_selected_interest_name = "";

    String str_final_industry_update, str_final_location_update = "";

    //test purpose (for posting parameters empty) delete later
    String empty = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_investor_profile_update);

        mToolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("Investor Profile");
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

        Arraylist_investor_role_id = new ArrayList<String>();
        Arraylist_investor_role_name = new ArrayList<String>();

        Arraylist_investor_interest_id = new ArrayList<String>();
        Arraylist_investor_interest_name = new ArrayList<String>();

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

        Arraylist_fetched_industries = new ArrayList<String>();
        Arraylist_selected_final_industry = new ArrayList<String>();

        Arraylist_fetched_location = new ArrayList<String>();
        Arraylist_selected_final_location = new ArrayList<String>();

        Arraylist_image_encode = new ArrayList<String>();

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        str_user_id = sharedPreferences.getString("str_user_id", "str_user_id");
        str_user_currency = sharedPreferences.getString("str_selected_currency", "str_selected_currency");
        str_investor_key = sharedPreferences.getString("investor_key", "investor_key");

        System.out.println("user ID :::::: " + str_user_id + "user currency :::::: " + str_user_currency);

        edt_name = (EditText) findViewById(R.id.invest_prof_up_edt_name);
        edt_mobile_number = (EditText) findViewById(R.id.invest_prof_up_edt_mobile_number);
        edt_email = (EditText) findViewById(R.id.invest_prof_up_edt_office_email);

        edt_dealsize_minimum = (EditText) findViewById(R.id.invest_prof_up_edt_dealsize_minimum);
        edt_dealsize_maximum = (EditText) findViewById(R.id.invest_prof_up_edt_dealsize_maximum);
        edt_company_name = (EditText) findViewById(R.id.invest_prof_up_edt_company_work_at);
        edt_designation = (EditText) findViewById(R.id.invest_prof_up_edt_designation);
        edt_wed_linkedin = (EditText) findViewById(R.id.invest_prof_up_edt_linkedin_profile);
        edt_company_sector = (EditText) findViewById(R.id.invest_prof_up_edt_company_sector);
        edt_kind_business_interested = (EditText) findViewById(R.id.invest_prof_up_edt_investor_profile_business_interested);
        edt_company_about = (EditText) findViewById(R.id.invest_prof_up_edt_about_yourself);


        auto_headquaters = (AutoCompleteTextView) findViewById(R.id.invest_prof_up_edit_profile_edt_user_location);

        auto_busineeslist = (MultiAutoCompleteTextView) findViewById(R.id.investor_profile_industries_multi_interested);
        auto_locationlist = (MultiAutoCompleteTextView) findViewById(R.id.investor_profile_business_multi_location);

        txt_role = (TextView) findViewById(R.id.invest_prof_upd_txt_your_role);
        txt_interested = (TextView) findViewById(R.id.invest_prof_upd_txt_your_interest);

        // Searchable Spinner
        spn_i_am = (SearchableSpinner) findViewById(R.id.invest_prof_up_spn_i_am);
        spn_i_am.setTitle("Select Your Role");
        spn_interested_in = (SearchableSpinner) findViewById(R.id.invest_prof_up_spn_intersted);
        spn_interested_in.setTitle("Select Your Interest");

        btn_add_pic = (Button) findViewById(R.id.invest_prof_up_btn_add_photos);
        btn_add_pic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ImagePicker();
            }
        });


        btn_submit = (Button) findViewById(R.id.invest_prof_up_btn_submit);
        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String str_bus_list = auto_busineeslist.getText().toString();
                String str_loc_list = auto_locationlist.getText().toString();

                if (str_bus_list.equals("")) {

                    TastyToast.makeText(getApplicationContext(), "Please Enter Interested Industries ", TastyToast.LENGTH_LONG, TastyToast.WARNING);

                } else {

                    /******************************
                     * Get Multi Sector Details
                     * *************************/
                    String[] str_industries = auto_busineeslist.getText().toString().split(", ");

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

                }

                if (str_loc_list.equals("")) {
                    TastyToast.makeText(getApplicationContext(), "Please Enter Interested Location ", TastyToast.LENGTH_LONG, TastyToast.WARNING);
                } else {

                    /******************************
                     * Get Multi Location Details
                     * *************************/

                    String[] str_location = auto_locationlist.getText().toString().split(", ");

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
                        System.out.println("location_position : " + location_position);
                        System.out.println("Arraylist_location_key : " + Arraylist_location_key);
                        String select_location_id = Arraylist_location_key.get(location_position);
                        String select_location_type = Arraylist_location_type.get(location_position);

                        String location = select_location_id + "-" + select_location_type;
                        Arraylist_selected_final_location.add(location);

                        str_final_location_update = TextUtils.join(", ", Arraylist_selected_final_location);

                    }
                    System.out.println("FINAL SELECTED LOCATION :: " + str_final_location_update);

                }

                String str_Headquaters = auto_headquaters.getText().toString();
                int Headquaters_position = Arraylist_location_place.indexOf(str_Headquaters);
                String select_Headquaters_id = Arraylist_location_key.get(Headquaters_position);
                String select_Headquaters_type = Arraylist_location_type.get(Headquaters_position);
                str_final_headquaters = select_Headquaters_id + "-" + select_Headquaters_type;

                String role = txt_role.getText().toString();
                String interested = txt_interested.getText().toString();

                int role_pos = Arraylist_investor_role_name.indexOf(role);
                int interest_pos = Arraylist_investor_interest_name.indexOf(interested);

                str_selected_role_id = Arraylist_investor_role_id.get(role_pos);
                str_selected_interest_id = Arraylist_investor_interest_id.get(interest_pos);

                str_name = edt_name.getText().toString();
                str_email = edt_email.getText().toString();
                str_mobile = edt_mobile_number.getText().toString();

                str_deal_minimum = edt_dealsize_minimum.getText().toString();
                str_deal_maximum = edt_dealsize_maximum.getText().toString();

                str_company_name = edt_company_name.getText().toString();
                str_designation = edt_designation.getText().toString();
                str_web_linkedin = edt_wed_linkedin.getText().toString();
                str_company_sector = edt_company_sector.getText().toString();
                str_kindof_business_interested = edt_kind_business_interested.getText().toString();
                str_company_about = edt_company_about.getText().toString();

/*
                //CONVERTING DEAL SIZE MINIMUM & MAXIMUM VALUES TO INTEGER TO CHECK MAXIMUM IS GREATER THAN MINIMUM VALUES
                int_deal_minimum = Integer.valueOf(str_deal_minimum);
                int_deal_maximum = Integer.valueOf(str_deal_maximum);
*/
                if (str_name.equals("")) {
                    edt_name.setError("Enter  Name");
                    edt_name.requestFocus();
                    TastyToast.makeText(getApplicationContext(), "Name Cannot be Empty", TastyToast.LENGTH_LONG, TastyToast.WARNING);
                } else if (str_email.equals("")) {
                    edt_email.setError("Enter Email");
                    edt_email.requestFocus();
                    TastyToast.makeText(getApplicationContext(), "Email Cannot be Empty", TastyToast.LENGTH_LONG, TastyToast.WARNING);
                } else if (str_mobile.equals("")) {
                    edt_mobile_number.setError("Enter Mobile Number");
                    edt_mobile_number.requestFocus();
                    TastyToast.makeText(getApplicationContext(), "Mobile Number Cannot be Empty", TastyToast.LENGTH_LONG, TastyToast.WARNING);
                } else if (str_selected_role_id.equals("")) {
                    spn_i_am.setFocusable(true);
                    spn_i_am.setFocusableInTouchMode(true);
                    spn_i_am.requestFocus();
                    spn_i_am.performClick();
                    TastyToast.makeText(getApplicationContext(), "Select Your Role", TastyToast.LENGTH_LONG, TastyToast.WARNING);
                } else if (str_selected_interest_id.equals("")) {
                    spn_i_am.setFocusable(true);
                    spn_i_am.setFocusableInTouchMode(true);
                    spn_i_am.requestFocus();
                    spn_i_am.performClick();
                    TastyToast.makeText(getApplicationContext(), "Select Your Interest", TastyToast.LENGTH_LONG, TastyToast.WARNING);
                } else if (str_deal_minimum.equals("")) {
                    edt_dealsize_minimum.setError("Enter Minimum Deal Size");
                    edt_dealsize_minimum.requestFocus();
                    TastyToast.makeText(getApplicationContext(), "This Cannot be Empty", TastyToast.LENGTH_LONG, TastyToast.WARNING);
                } else if (str_deal_maximum.equals("")) {
                    edt_dealsize_maximum.setError("Enter Maximum Deal Size");
                    edt_dealsize_maximum.requestFocus();
                    TastyToast.makeText(getApplicationContext(), "This Cannot be Empty", TastyToast.LENGTH_LONG, TastyToast.WARNING);
                } /*else if (int_deal_maximum <= int_deal_minimum) {
                    edt_dealsize_maximum.setError("Invalid Value");
                    edt_dealsize_maximum.requestFocus();
                    TastyToast.makeText(getApplicationContext(), "Should be greater than Minimum Deal Size", TastyToast.LENGTH_LONG, TastyToast.WARNING);
                }*/ else if (str_company_name.equals("")) {
                    edt_company_name.setError("Enter Company Name");
                    edt_company_name.requestFocus();
                    TastyToast.makeText(getApplicationContext(), "Company Name Cannot be Empty", TastyToast.LENGTH_LONG, TastyToast.WARNING);
                } else if (str_final_headquaters.equals("")) {
                    auto_headquaters.setError("Enter Company Location");
                    auto_headquaters.requestFocus();
                    TastyToast.makeText(getApplicationContext(), "Company Location Cannot be Empty", TastyToast.LENGTH_LONG, TastyToast.WARNING);
                } else if (str_designation.equals("")) {
                    edt_designation.setError("Enter Designation");
                    edt_designation.requestFocus();
                    TastyToast.makeText(getApplicationContext(), "Designation Cannot be Empty", TastyToast.LENGTH_LONG, TastyToast.WARNING);
                } else if (str_web_linkedin.equals("")) {
                    edt_wed_linkedin.setError("Enter Link");
                    edt_wed_linkedin.requestFocus();
                    TastyToast.makeText(getApplicationContext(), "This Cannot be Empty", TastyToast.LENGTH_LONG, TastyToast.WARNING);
                } else if (str_company_sector.equals("")) {
                    edt_company_sector.setError("Enter Company's Sector");
                    edt_company_sector.requestFocus();
                    TastyToast.makeText(getApplicationContext(), "Company's Sector Cannot be Empty", TastyToast.LENGTH_LONG, TastyToast.WARNING);
                } else if (str_kindof_business_interested.equals("")) {
                    edt_kind_business_interested.setError("Enter the kind of business interested");
                    edt_kind_business_interested.requestFocus();
                    TastyToast.makeText(getApplicationContext(), "This Cannot be Empty", TastyToast.LENGTH_LONG, TastyToast.WARNING);
                } else if (str_company_about.equals("")) {
                    edt_company_about.setError("Enter About the Company");
                    edt_company_about.requestFocus();
                    TastyToast.makeText(getApplicationContext(), "This Sector Cannot be Empty", TastyToast.LENGTH_LONG, TastyToast.WARNING);
                } else {
                    dialog = new SpotsDialog(Activity_InvestorProfile_Update.this);
                    dialog.show();
                    queue = Volley.newRequestQueue(Activity_InvestorProfile_Update.this);
                    Function_Submit_InvestorProfile();
                }


            }
        });

        try {
            dialog = new SpotsDialog(Activity_InvestorProfile_Update.this);
            dialog.show();
            queue = Volley.newRequestQueue(getApplicationContext());
            Get_Iam_an();

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
                .limit(1) // max images can be selected (999 by default)
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

        //System.out.println("ENCODE :: " + listString);

        //Log.d(":STRING:", listString);
    }


    /*****************************
     * To get  I am/an  Details
     ***************************/

    public void Get_Iam_an() {
        String tag_json_obj = "json_obj_req";
        System.out.println("STEP  1111111111111");
        StringRequest request = new StringRequest(Request.Method.POST,
                AppConfig.url_investor_iam, new Response.Listener<String>() {

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

                            Arraylist_investor_role_id.add(role_key);
                            Arraylist_investor_role_name.add(role_name);
                        }
                        try {

                            adapter_i_am = new ArrayAdapter<String>(Activity_InvestorProfile_Update.this,
                                    android.R.layout.simple_list_item_1, Arraylist_investor_role_name);
                            spn_i_am.setAdapter(adapter_i_am);

                            spn_i_am.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
                                                        long arg3) {

                                    System.out.println("CLICKEEEEDDDDD");

                                    t1 = (TextView) arg1;
                                    str_selected_role_name = t1.getText().toString();
                                    str_selected_role_id = Arraylist_investor_role_id.get(arg2);

                                    txt_role.setText(str_selected_role_name);
                                    System.out.println("STEP  1111111111111" + str_selected_role_id);
                                    System.out.println("STEP  2222222222222" + str_selected_role_name);

                                }
                            });

                        } catch (Exception e) {

                        }

                        dialog.dismiss();
                        try {
                            dialog = new SpotsDialog(Activity_InvestorProfile_Update.this);
                            dialog.show();
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
                            adapter_interested = new ArrayAdapter<String>(Activity_InvestorProfile_Update.this,
                                    android.R.layout.simple_list_item_1, Arraylist_investor_interest_name);
                            spn_interested_in.setAdapter(adapter_interested);


                            spn_interested_in.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
                                                        long arg3) {
                                    t1 = (TextView) arg1;
                                    str_selected_interest_name = t1.getText().toString();
                                    System.out.println("Argument " + arg2);
                                    str_selected_interest_id = Arraylist_investor_interest_id.get(arg2);

                                    txt_interested.setText(str_selected_interest_name);
                                }
                            });

                        } catch (Exception e) {

                        }

                        dialog.dismiss();

                        try {
                            dialog = new SpotsDialog(Activity_InvestorProfile_Update.this);
                            dialog.show();
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
     * To get  Sector List
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
                            ArrayAdapter<String> adapter_process = new ArrayAdapter<String>(Activity_InvestorProfile_Update.this,
                                    android.R.layout.simple_list_item_1, Arraylist_sector_name);
                            auto_busineeslist.setAdapter(adapter_process);
                            auto_busineeslist
                                    .setTokenizer(new MultiAutoCompleteTextView.CommaTokenizer());
                            auto_busineeslist.setThreshold(1);

                        } catch (Exception e) {

                        }

                        dialog.dismiss();

                        try {
                            dialog = new SpotsDialog(Activity_InvestorProfile_Update.this);
                            dialog.show();
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

                            ArrayAdapter<String> adapter_location = new ArrayAdapter<String>(Activity_InvestorProfile_Update.this,
                                    android.R.layout.simple_list_item_1, Arraylist_location_place);
                            auto_locationlist.setAdapter(adapter_location);
                            auto_locationlist
                                    .setTokenizer(new MultiAutoCompleteTextView.CommaTokenizer());
                            auto_locationlist.setThreshold(1);

                        } catch (Exception e) {

                        }

                        dialog.dismiss();
                        try {
                            dialog = new SpotsDialog(Activity_InvestorProfile_Update.this);
                            dialog.show();
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

                            ArrayAdapter<String> adapter_location = new ArrayAdapter<String>(Activity_InvestorProfile_Update.this,
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

                        dialog.dismiss();
                        try {
                            dialog = new SpotsDialog(Activity_InvestorProfile_Update.this);
                            dialog.show();
                            queue = Volley.newRequestQueue(getApplicationContext());
                            Get_InvestorProfile();
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
     * GET Invstor Profile Previous Values
     ***************************/

    public void Get_InvestorProfile() {

        StringRequest request = new StringRequest(Request.Method.POST,
                AppConfig.url_user_investor_profile_update, new Response.Listener<String>() {
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
                        JSONArray arr_sector;
                        JSONArray arr_documents;
                        JSONArray arr_images;

                        arr_main = obj.getJSONArray("data");
                        for (int i = 0; arr_main.length() > i; i++) {
                            JSONObject obj_data = arr_main.getJSONObject(i);

                            String investor_id = obj_data.getString("investor_id");
                            String investor_key = obj_data.getString("investor_key");
                            String investor_name = obj_data.getString("investor_name");
                            String investor_confidential_email = obj_data.getString("investor_confidential_email");
                            String investor_confidential_mobile = obj_data.getString("investor_confidential_mobile");
                            String investor_user_role = obj_data.getString("investor_user_role");
                            String investor_currency_from = obj_data.getString("investor_currency_from");
                            String investor_currency_to = obj_data.getString("investor_currency_to");
                            String investor_currency = obj_data.getString("investor_currency");
                            String investor_company_name = obj_data.getString("investor_company_name");
                            String investor_designation = obj_data.getString("investor_designation");
                            String investor_profile_url = obj_data.getString("investor_profile_url");
                            String investor_short_description = obj_data.getString("investor_short_description");
                            String investor_an = obj_data.getString("investor_an");
                            String investor_about_user = obj_data.getString("investor_about_user");
                            String investor_an_name = obj_data.getString("investor_an_name");
                            String investor_interest_name = obj_data.getString("investor_interest_name");
                            String investor_interest = obj_data.getString("investor_interest");
                            String currency_name = obj_data.getString("currency_name");
                            String investor_city = obj_data.getString("investor_city");
                            String investor_city_type = obj_data.getString("investor_city_type");
                            String investor_company_sector = obj_data.getString("investor_company_sector");
                            String investor_location_name = obj_data.getString("investor_location_name");

                            arr_location = obj_data.getJSONArray("location");
                            Arraylist_update_location.clear();
                            for (int j = 0; arr_location.length() > j; j++) {
                                JSONObject obj_location = arr_location.getJSONObject(j);

                                String location_name = obj_location.getString("location_name");
                                String location_key = obj_location.getString("location_key");

                                Arraylist_update_location.add(location_name);

                            }
                            str_final_location = TextUtils.join(", ", Arraylist_update_location);

                            System.out.println("LOCATION ::: " + str_final_location);

                            arr_sector = obj_data.getJSONArray("industry");
                            Arraylist_update_industries.clear();
                            for (int k = 0; arr_sector.length() > k; k++) {
                                JSONObject obj_industry = arr_sector.getJSONObject(k);

                                String industry_name = obj_industry.getString("industry_name");
                                String industry_key = obj_industry.getString("industry_key");

                                Arraylist_update_industries.add(industry_name);

                            }
                            str_final_industries = TextUtils.join(", ", Arraylist_update_industries);

                            try {
                                txt_role.setText(investor_user_role);
                                txt_interested.setText(investor_interest_name);
                                edt_name.setText("" + investor_name);
                                edt_mobile_number.setText("" + investor_confidential_mobile);
                                edt_email.setText("" + investor_confidential_email);
                                edt_dealsize_minimum.setText("" + investor_currency_from);
                                edt_dealsize_maximum.setText("" + investor_currency_to);
                                edt_company_name.setText("" + investor_company_name);
                                edt_designation.setText("" + investor_designation);
                                edt_wed_linkedin.setText("" + investor_profile_url);
                                edt_company_sector.setText("" + investor_company_sector);
                                edt_kind_business_interested.setText("" + investor_short_description);
                                edt_company_about.setText("" + investor_about_user);
                                auto_headquaters.setText("" + investor_location_name);
                                auto_busineeslist.setText("" + str_final_industries + ", ");
                                auto_locationlist.setText("" + str_final_location + ", ");
                                dialog.dismiss();
                            } catch (Exception e) {

                            }

                        }

                        dialog.dismiss();
                    } else if (success == 0) {

                        dialog.dismiss();

                        Alerter.create(Activity_InvestorProfile_Update.this)
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
                Alerter.create(Activity_InvestorProfile_Update.this)
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
                params.put("investor_key", str_investor_key);

                System.out.println("USER_IDDDDDDDDDDDDDDDDDD ::: " + str_user_id);
                System.out.println("INVESTORRRRR KEYYYYYYYYYYYYY ::: " + str_investor_key);

                return params;
            }

        };

        // Adding request to request queue
        queue.add(request);
    }

    /******************************************
     *    SUBMIT BUSINESS PROFILE FORM
     * *******************************************/

    private void Function_Submit_InvestorProfile() {

        StringRequest request = new StringRequest(Request.Method.POST,
                AppConfig.url_update_investor_profile, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                System.out.println("CAMEEEE INNNNNN");
                Log.d(TAG, response.toString());

                try {
                    JSONObject obj = new JSONObject(response);
                    System.out.println("RESPONSE : " + response);
                    int success = obj.getInt("status");
                    if (success == 1) {
                        dialog.dismiss();

                        Alerter.create(Activity_InvestorProfile_Update.this)
                                .setTitle("Success")
                                .setText("Investor Profile Added Successfully")
                                .setBackgroundColor(R.color.colorAccent)
                                .show();
                    } else {
                        dialog.dismiss();

                        TastyToast.makeText(getApplicationContext(), "Oops...! Profile Creation Failed :(", TastyToast.LENGTH_LONG, TastyToast.ERROR);
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
                System.out.println("VOLLLEYYYYYYY ERRRROORRRRR");
                dialog.dismiss();

            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();


                str_name = edt_name.getText().toString();
                str_email = edt_email.getText().toString();
                str_mobile = edt_mobile_number.getText().toString();

                str_deal_minimum = edt_dealsize_minimum.getText().toString();
                str_deal_maximum = edt_dealsize_maximum.getText().toString();

                str_company_name = edt_company_name.getText().toString();
                str_designation = edt_designation.getText().toString();
                str_web_linkedin = edt_wed_linkedin.getText().toString();
                str_company_sector = edt_company_sector.getText().toString();
                str_kindof_business_interested = edt_kind_business_interested.getText().toString();
                str_company_about = edt_company_about.getText().toString();


                params.put("name_u", str_name);
                params.put("mob_u", str_mobile);
                params.put("email_u", str_email);

                params.put("inter_u", str_selected_interest_id);
                params.put("am_an", str_selected_role_id);
                params.put("indust", str_final_industry_update);
                params.put("location_u", str_final_location_update);

                params.put("invest_inr", str_deal_minimum);
                params.put("invest_to", str_deal_maximum);

                params.put("location", str_final_headquaters);

                params.put("com_y", str_company_name);
                params.put("desig", str_designation);
                params.put("linked", str_web_linkedin);

                params.put("com_s", str_company_sector);
                params.put("busi_in", str_kindof_business_interested);
                params.put("abt_you", str_company_about);

                params.put("profile_img", empty);
                params.put("profile_document", empty);
                params.put("logo_file", listString);

                params.put("user_currency", str_user_currency);
                params.put("user_id", str_user_id);

                return params;
            }
        };
        queue.add(request);
    }


}