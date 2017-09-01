package banyan.com.awesomebusiness.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.NetworkResponse;
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
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import banyan.com.awesomebusiness.R;
import banyan.com.awesomebusiness.global.AppConfig;
import dmax.dialog.SpotsDialog;

/**
 * Created by Schan on 24-Jul-17.
 */

public class Activity_InvestorProfile extends AppCompatActivity {

    private Toolbar mToolbar;
    SpotsDialog dialog;
    public static RequestQueue queue;
    String TAG = "";
    TextView t1;
    String str_user_currency, str_user_id = "";


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

    SearchableSpinner spn_i_am, spn_interested_in;
    ChipLayout chip_busineeslist, chip_business_location;
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

    /*Multi Select*/
    ArrayList<String> Arraylist_selected_sectorkey = null;
    ArrayList<String> Arraylist_selected_location = null;

    ArrayList<String> Arraylist_location_place = null;
    ArrayList<String> Arraylist_location_key = null;
    ArrayList<String> Arraylist_location_type = null;

    /* Arralist fetched Location list */
    ArrayList<String> Arraylist_fetched_location = null;
    ArrayList<String> Arraylist_selected_final_location = null;


    private ArrayAdapter<String> adapter_i_am;
    private ArrayAdapter<String> adapter_interested;

    String str_selected_role_id, str_selected_role_name = "";
    String str_selected_interest_id, str_selected_interest_name = "";

    String str_final_industry_update, str_final_location_update = "";

    //test purpose (for posting parameters empty) delete later
    String empty="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_investor_profile);

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

        Arraylist_fetched_industries = new ArrayList<String>();
        Arraylist_selected_final_industry = new ArrayList<String>();

        Arraylist_selected_sectorkey = new ArrayList<String>();
        Arraylist_selected_location = new ArrayList<String>();

        Arraylist_location_place = new ArrayList<String>();
        Arraylist_location_key = new ArrayList<String>();
        Arraylist_location_type = new ArrayList<String>();

        Arraylist_fetched_location = new ArrayList<String>();
        Arraylist_selected_final_location = new ArrayList<String>();

        Arraylist_image_encode = new ArrayList<String>();

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        str_user_id = sharedPreferences.getString("str_user_id", "str_user_id");
        str_user_currency = sharedPreferences.getString("str_selected_currency", "str_selected_currency");

        System.out.println("user ID :::::: " + str_user_id + "user currency :::::: " + str_user_currency);

        ChipLayout.MAX_CHARACTER_COUNT = 20;
        chip_busineeslist = (ChipLayout) findViewById(R.id.investor_profile_chipText_industries_interested);
        chip_business_location = (ChipLayout) findViewById(R.id.investor_profile_chipText_business_location);

        edt_name = (EditText) findViewById(R.id.edt_name);
        edt_mobile_number = (EditText) findViewById(R.id.edt_mobile_number);
        edt_email = (EditText) findViewById(R.id.edt_office_email);

        edt_dealsize_minimum = (EditText) findViewById(R.id.edt_dealsize_minimum);
        edt_dealsize_maximum = (EditText) findViewById(R.id.edt_dealsize_maximum);
        edt_company_name = (EditText) findViewById(R.id.edt_company_work_at);
        edt_designation = (EditText) findViewById(R.id.edt_designation);
        edt_wed_linkedin = (EditText) findViewById(R.id.edt_linkedin_profile);
        edt_company_sector = (EditText) findViewById(R.id.edt_company_sector);
        edt_kind_business_interested = (EditText) findViewById(R.id.edt_investor_profile_business_interested);
        edt_company_about = (EditText) findViewById(R.id.edt_about_yourself);


        auto_headquaters = (AutoCompleteTextView) findViewById(R.id.edit_profile_edt_user_location);

        // Searchable Spinner
        spn_i_am = (SearchableSpinner) findViewById(R.id.business_profile_spn_i_am);
        spn_i_am.setTitle("Select Your Role");
        spn_interested_in = (SearchableSpinner) findViewById(R.id.business_profile_spn_intersted);
        spn_interested_in.setTitle("Select Your Interest");

        btn_add_pic = (Button) findViewById(R.id.btn_add_photos);
        btn_add_pic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ImagePicker();
            }
        });


        btn_submit = (Button) findViewById(R.id.btn_submit);
        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                //Temporary static values assigned
                str_final_industry_update = "1-sector,2-sector,3-sector,";
                str_final_location_update = "1-state,2-city,1-city,";

                //CLEARING THE ARRAYLIST TO REMOVE PREVIOUS VALUES
                Arraylist_fetched_industries.clear();
                Arraylist_selected_final_industry.clear();

                ///////////////////////
                ///////  FOR GETTING ENTERED BUSINESS HEADQUATERS TYPE AND ID
                ///////////////////////

                String str_Headquaters = auto_headquaters.getText().toString();
                int Headquaters_position = Arraylist_location_place.indexOf(str_Headquaters);
                String select_Headquaters_id = Arraylist_location_key.get(Headquaters_position + 1);
                String select_Headquaters_type = Arraylist_location_type.get(Headquaters_position + 1);
                str_final_headquaters = select_Headquaters_id + "-" + select_Headquaters_type;

                ///////////////////////
                ///////  FOR GETTING ENTERED SECTOR TYPE AND ID
                ///////////////////////

                /*
                String str_industry_from_chip = chip_busineeslist.getText().toString();
                System.out.println("FETCHED INDUSTRIES FROMCHIPLAYOUTTTTTTTT :: " + str_industry_from_chip);
                String[] items_comma = str_industry_from_chip.split(",");
                for (String item_comma : items_comma) {
                    String[] items_left = item_comma.split("\\[");
                    for (String item_left : items_left) {
                        if (item_left.equals("")) {

                        } else {
                            Character last_letter = item_left.charAt(item_left.length() - 1);
                            if (last_letter.equals(']')) {
                            } else {
                                Arraylist_fetched_industries.add(item_left);
                            }
                        }
                    }
                    String[] items_right = item_comma.split("\\]");
                    for (String item_right : items_right) {

                        if (item_right.equals("")) {

                        } else {
                            Character first_letter = item_right.charAt(0);
                            if (first_letter.equals('[')) {
                            } else {
                                Arraylist_fetched_industries.add(item_right);
                            }
                        }

                    }
                }
                System.out.println("Arraylist_fetched_industries :::: " + Arraylist_fetched_industries);
                System.out.println("Arraylist_SIZEEEEEE :::: " + Arraylist_fetched_industries.size());

                for (int i = 0; i < Arraylist_fetched_industries.size(); i++) {

                    String get_indestry = Arraylist_fetched_industries.get(i);
                    int indus_position = Arraylist_sector_name.indexOf(get_indestry);

                    String select_sect_id = Arraylist_sector_key.get(indus_position + 1);
                    String select_sect_type = Arraylist_sector_type.get(indus_position + 1);

                    System.out.println("FINAL :: " + select_sect_id);

                    String sector = select_sect_id + "-" + select_sect_type;
                    Arraylist_selected_final_industry.add(sector);

                    for (String s : Arraylist_selected_final_industry) {
                        str_final_industry_update += s + ",";
                    }
                }
                System.out.println("FINAL SELECTED INDUSTRY :: " + str_final_industry_update);
                */


                ///////////////////////
                ///////  FOR GETTING PREVIOUSLY ENTERED LOCATION TYPE AND ID
                ///////////////////////

                /*
                String str_location_from_chip = chip_business_location.getText().toString();
                System.out.println("LOCATION FROM CHIPSETTTTTTTTT" + str_location_from_chip);

                String[] items_loc_comma = str_location_from_chip.split(",");
                for (String item_loc_comma : items_loc_comma) {
                    String[] items_loc_left = item_loc_comma.split("\\[");
                    for (String item_loc_left : items_loc_left) {
                        if (item_loc_left.equals("")) {

                        } else {
                            Character last_letter = item_loc_left.charAt(item_loc_left.length() - 1);
                            if (last_letter.equals(']')) {
                            } else {
                                System.out.println("right filter" + item_loc_left);
                                Arraylist_fetched_location.add(item_loc_left);
                            }
                        }
                    }

                    String[] items_loc_right = item_loc_comma.split("\\]");
                    for (String item_loc_right : items_loc_right) {

                        if (item_loc_right.equals("")) {

                        } else {
                            Character first_letter = item_loc_right.charAt(0);
                            if (first_letter.equals('[')) {
                            } else {
                                System.out.println("left filter" + item_loc_right);
                                Arraylist_fetched_location.add(item_loc_right);
                            }
                        }

                    }
                }
                for (int i = 0; i < Arraylist_fetched_location.size(); i++) {

                    Arraylist_selected_final_location.clear();
                    String get_Location = Arraylist_fetched_location.get(i);
                    get_Location = get_Location.trim();
                    int location_position = Arraylist_location_place.indexOf(get_Location);
                    String str_location_type = Arraylist_location_type.get(location_position);
                    String select_location_id = Arraylist_location_key.get(location_position + 1);
                    String select_location_type = Arraylist_location_type.get(location_position + 1);

                    String location = select_location_id + "-" + select_location_type;
                    Arraylist_selected_final_location.add(location);

                    for (String L : Arraylist_selected_final_location) {
                        str_final_location_update += L + ",";
                    }
                    System.out.println("FINAL LOCATIONNNNNNNNNNN :: " + str_final_location_update);
                }

*/
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
                } /*else if (str_selected_role_id.equals("")) {
                    spn_i_am.requestFocus();
                    TastyToast.makeText(getApplicationContext(), "Select Your Role", TastyToast.LENGTH_LONG, TastyToast.WARNING);
                } else if (str_selected_interest_id.equals("")) {
                    spn_interested_in.requestFocus();
                    TastyToast.makeText(getApplicationContext(), "Select Your Interest", TastyToast.LENGTH_LONG, TastyToast.WARNING);
                }*/ else if (str_deal_minimum.equals("")) {
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
                    dialog = new SpotsDialog(Activity_InvestorProfile.this);
                    dialog.show();
                    queue = Volley.newRequestQueue(Activity_InvestorProfile.this);
                    Function_Submit_InvestorProfile();
                }


            }
        });

        try {
            dialog = new SpotsDialog(Activity_InvestorProfile.this);
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

        queue = Volley.newRequestQueue(Activity_InvestorProfile.this);
        Function_Post();

        //System.out.println("ENCODE :: " + listString);

        //Log.d(":STRING:", listString);
    }


    /*********************************
     *  POST
     * *********************************/

    private void Function_Post() {

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

                            adapter_i_am = new ArrayAdapter<String>(Activity_InvestorProfile.this,
                                    android.R.layout.simple_list_item_1, Arraylist_investor_role_name);
                            spn_i_am.setAdapter(adapter_i_am);


                            spn_i_am.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
                                                        long arg3) {

                                    System.out.println("CLICKEEEEDDDDD");

                                    t1 = (TextView) arg1;
                                    str_selected_role_name = t1.getText().toString();
                                    str_selected_role_id = Arraylist_investor_role_id.get(arg2);

                                    System.out.println("STEP  1111111111111" + str_selected_role_id);
                                    System.out.println("STEP  2222222222222" + str_selected_role_name);

                                }
                            });

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
                            adapter_interested = new ArrayAdapter<String>(Activity_InvestorProfile.this,
                                    android.R.layout.simple_list_item_1, Arraylist_investor_interest_name);
                            spn_interested_in.setAdapter(adapter_interested);


                            spn_interested_in.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
                                                        long arg3) {
                                    t1 = (TextView) arg1;
                                    str_selected_interest_name = t1.getText().toString();
                                    System.out.println("Argument " + arg2);
                                    str_selected_interest_id = Arraylist_investor_interest_id.get(arg2);


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

                            ArrayAdapter<String> adapter_sector = new ArrayAdapter<String>(Activity_InvestorProfile.this,
                                    android.R.layout.simple_list_item_1, Arraylist_sector_name);
                            chip_busineeslist.setAdapter(adapter_sector);

                            chip_busineeslist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                @Override
                                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                                    System.out.println("Position :::::::: " + position);

                                    t1 = (TextView) view;
                                    String str_sector_key = t1.getText().toString();
                                    int i = Arraylist_sector_name.indexOf(str_sector_key);

                                    String str_select_sector_key = Arraylist_sector_key.get(i);
                                    String str_select_sector_type = Arraylist_sector_type.get(i);
                                    String str_select_item = str_select_sector_key + "-" + str_select_sector_type;
                                    Arraylist_selected_sectorkey.add(str_select_item);

                                    for (String s : Arraylist_selected_sectorkey) {
                                        str_final_business_sector += s + ",";
                                    }

                                    System.out.println("FINAL SECTORRRRRRRRRR :: " + str_final_business_sector);


                                }
                            });

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

                            ArrayAdapter<String> adapter_sector = new ArrayAdapter<String>(Activity_InvestorProfile.this,
                                    android.R.layout.simple_list_item_1, Arraylist_location_place);
                            chip_business_location.setAdapter(adapter_sector);

                            System.out.println("ARAAAAY :: " + 222222);
                            chip_business_location.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                @Override
                                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                                    System.out.println("Position :::::::: " + position);


                                    t1 = (TextView) view;
                                    String str_location_key = t1.getText().toString();
                                    int i = Arraylist_location_place.indexOf(str_location_key);

                                    String str_select_location_key = Arraylist_location_key.get(i);
                                    String str_select_location_type = Arraylist_location_type.get(i);

                                    String str_select_item = str_select_location_key + "-" + str_select_location_type;
                                    Arraylist_selected_location.add(str_select_item);

                                    for (String s : Arraylist_selected_location) {
                                        str_final_Business_Location += s + ",";
                                    }

                                    System.out.println("FINAL SECTORRRRRRRRRR :: " + str_final_Business_Location);


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

                            ArrayAdapter<String> adapter_location = new ArrayAdapter<String>(Activity_InvestorProfile.this,
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


    /******************************************
     *    SUBMIT BUSINESS PROFILE FORM
     * *******************************************/

    private void Function_Submit_InvestorProfile() {

        StringRequest request = new StringRequest(Request.Method.POST,
                AppConfig.url_investor_profile_add, new Response.Listener<String>() {

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

                        Alerter.create(Activity_InvestorProfile.this)
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




/*


   (isset($_POST['name_u'])) && (isset($_POST['mob_u'])) && (isset($_POST['email_u'])) &&
				(isset($_POST['inter_u'])) && (isset($_POST['am_an'])) && (isset($_POST['indust']))
				&& (isset($_POST['location_u'])) &&
				(isset($_POST['user_currency'])) && (isset($_POST['invest_inr']))
				 && (isset($_POST['invest_to'])) &&
				(isset($_POST['location'])) && (isset($_POST['com_y'])) && (isset($_POST['desig'])) &&
				(isset($_POST['linked'])) && (isset($_POST['com_s'])) && (isset($_POST['busi_in'])) &&
				(isset($_POST['abt_you'])) && (isset($_POST['profile_img'])) && (isset($_POST['profile_document']))
				 && (isset($_POST['logo_file']))&& (isset($_POST['user_id']))


				 */
