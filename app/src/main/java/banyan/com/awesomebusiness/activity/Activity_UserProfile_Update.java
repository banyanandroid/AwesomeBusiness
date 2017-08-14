
package banyan.com.awesomebusiness.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.hbb20.CountryCodePicker;
import com.libaml.android.view.chip.ChipLayout;
import com.sdsmdg.tastytoast.TastyToast;
import com.tapadoo.alerter.Alerter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import banyan.com.awesomebusiness.R;
import banyan.com.awesomebusiness.global.AppConfig;
import banyan.com.awesomebusiness.global.SessionManager;
import dmax.dialog.SpotsDialog;

import static java.security.AccessController.getContext;


public class Activity_UserProfile_Update extends AppCompatActivity {

    private Toolbar mToolbar;
    public static RequestQueue queue;
    SpotsDialog dialog;
    String TAG = "";
    TextView t1;

    //CountryCodePicker
    CountryCodePicker CountryCodePicker;

    // Session Manager Class
    SessionManager session;
    public static String str_user_id, str_user_name;

    EditText edt_name, edt_mobilenumber, edt_companyname, edt_location,
            edt_designation, edt_address, edt_GST_number;

    CheckBox chb_BusinessProposals, chb_NewOpportunitis;

    ChipLayout chip_preferred_industries, chip_preferred_loation;

    Button btn_change_password, btn_update;

    public static final String TAG_SECTOR_NAME = "name";
    public static final String TAG_SECTOR_KEY = "key";
    public static final String TAG_SECTOR_TYPE = "type";

    public static final String TAG_LOC_PLACE = "place";
    public static final String TAG_LOC_KEY = "key";
    public static final String TAG_LOC_TYPE = "type";

    //For Setting Previous values of the user profile
    public static final String TAG_USER_NAME = "user_name";
    public static final String TAG_USER_MOBILE = "user_mobile";
    public static final String TAG_USER_LOCATION = "user_location";
    public static final String TAG_USER_GST_NUMBER = "user_gst_number";
    public static final String TAG_USER_COMPANY_NAME = "user_company_name";
    public static final String TAG_USER_ADDRESS = "user_address";
    public static final String TAG_USER_DESIGINATION = "user_designation";
    public static final String TAG_USER_EMAIL_BUSINESS_PROPOSALS = "user_business_proposals_email";
    public static final String TAG_USER_EMAIL_NEW_OPPORTUNITIES = "user_business_proposals_notify";

    public static final String TAG_PREF_LOCATION_ID = "prefer_location_id";
    public static final String TAG_PREF_LOCATION_TYPE = "prefer_location_type";
    public static final String TAG_PREF_USER_ID = "prefer_user_id";
    public static final String TAG_PREF_LOCATION_CREATEON = "preferences_location_createdon";
    public static final String TAG_PREF_LOCATION_ACT = "preferences_location_act";

    public static final String TAG_PREF_INDUSTRIES_ID = "preferences_industries_id";
    public static final String TAG_PREF_INDUSTRIES_TYPE = "preferences_industry_type";
    public static final String TAG_PREF_ID = "preferences_user_id";
    public static final String TAG_PREF_CREATEON = "preferences_createdon";
    public static final String TAG_PREF_ACT = "preferences_act";


    ArrayList<String> Arraylist_sector_name = null;
    ArrayList<String> Arraylist_sector_key = null;
    ArrayList<String> Arraylist_sector_type = null;

    /*Multi Select*/
    ArrayList<String> Arraylist_selected_sectorkey = null;
    ArrayList<String> Arraylist_selected_location = null;

    ArrayList<String> Arraylist_location_place = null;
    ArrayList<String> Arraylist_location_place_continent = null;
    ArrayList<String> Arraylist_location_place_country = null;
    ArrayList<String> Arraylist_location_place_state = null;
    ArrayList<String> Arraylist_location_place_city = null;
    ArrayList<String> Arraylist_location_key = null;
    ArrayList<String> Arraylist_location_type = null;

    String str_final_business_sector, str_final_Business_Location = "";

    String str_chb_businessproposals, str_chb_newopportunities = "0";

    /********  Sector & Location Preff  *********/

    ArrayList<String> Arraylist_pref_sector_id = null;
    ArrayList<String> Arraylist_pref_sector_type = null;
    ArrayList<String> Arraylist_pref_location_id = null;
    ArrayList<String> Arraylist_pref_location_type = null;

    String str_prev_profile_user_name, str_prev_user_mobile,
            str_prev_user_gst_num, str_prev_user_company_name, str_prev_user_address,
            str_prev_user_designation, str_prev_user_location, str_prev_email_business_proposals, str_prev_email_new_opportunities = "";

    String str_up_profile_user_name, str_up_user_mobile,
            str_up_user_gst_num, str_up__user_company_name, str_up_user_address,
            str_up_user_designation, str_up_user_location = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_update);

        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        session = new SessionManager(getApplicationContext());
        session.checkLogin();
        HashMap<String, String> user = session.getUserDetails();
        // name
        str_user_name = user.get(SessionManager.KEY_USER);
        str_user_id = user.get(SessionManager.KEY_USER_ID);

        mToolbar.setNavigationIcon(getResources().getDrawable(R.drawable.ic_action_back));
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(i);
                finish();
            }
        });

        Arraylist_sector_name = new ArrayList<String>();
        Arraylist_sector_key = new ArrayList<String>();
        Arraylist_sector_type = new ArrayList<String>();

        Arraylist_selected_sectorkey = new ArrayList<String>();
        Arraylist_selected_location = new ArrayList<String>();

        Arraylist_location_place = new ArrayList<String>();

        Arraylist_location_place_continent = new ArrayList<String>();
        Arraylist_location_place_country = new ArrayList<String>();
        Arraylist_location_place_state = new ArrayList<String>();
        Arraylist_location_place_city = new ArrayList<String>();

        Arraylist_location_key = new ArrayList<String>();
        Arraylist_location_type = new ArrayList<String>();

        Arraylist_pref_sector_id = new ArrayList<String>();
        Arraylist_pref_sector_type = new ArrayList<String>();
        Arraylist_pref_location_id = new ArrayList<String>();
        Arraylist_pref_location_type = new ArrayList<String>();

        //CountryCodePicker
        CountryCodePicker = (CountryCodePicker) findViewById(R.id.CountryCodePicker);
        CountryCodePicker.setOnCountryChangeListener(new CountryCodePicker.OnCountryChangeListener() {
            @Override
            public void onCountrySelected() {
                Toast.makeText(getApplicationContext(), "Changed To " + CountryCodePicker.getSelectedCountryName(), Toast.LENGTH_SHORT).show();
            }
        });

        ChipLayout.MAX_CHARACTER_COUNT = 20;
        chip_preferred_industries = (ChipLayout) findViewById(R.id.business_profile_chipText_business_industry);
        chip_preferred_loation = (ChipLayout) findViewById(R.id.business_profile_chipText_business_location);

        btn_change_password = (Button) findViewById(R.id.edit_profile_btn_changepassword);
        btn_update = (Button) findViewById(R.id.user_profile_btn_submit);

        chb_BusinessProposals = (CheckBox) findViewById(R.id.chbx_emailfreq_businessproposals);
        chb_NewOpportunitis = (CheckBox) findViewById(R.id.chbx_emailfreq_newOpportunities);

        edt_name = (EditText) findViewById(R.id.edit_profile_edt_user_name);
        edt_mobilenumber = (EditText) findViewById(R.id.edit_profile_edt_user_number);
        edt_companyname = (EditText) findViewById(R.id.edit_profile_edt_user_companyname);
        edt_location = (EditText) findViewById(R.id.edit_profile_edt_user_location);
        edt_designation = (EditText) findViewById(R.id.edit_profile_edt_user_designation);
        edt_address = (EditText) findViewById(R.id.edit_profile_edt_user_address);
        edt_GST_number = (EditText) findViewById(R.id.edit_profile_edt_user_GSTnumber);

        try {
            dialog = new SpotsDialog(Activity_UserProfile_Update.this);
            dialog.show();
            queue = Volley.newRequestQueue(getApplicationContext());
            Get_User_Profile();

        } catch (Exception e) {
            // TODO: handle exception
        }

        btn_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                str_up_profile_user_name = edt_name.getText().toString();
                str_up_user_mobile = edt_mobilenumber.getText().toString();
                str_up_user_gst_num = edt_GST_number.getText().toString();
                str_up__user_company_name = edt_companyname.getText().toString();
                str_up_user_address = edt_address.getText().toString();
                str_up_user_designation = edt_designation.getText().toString();
                str_up_user_location = edt_location.getText().toString();

// String Values According to checkbox state
                if (chb_BusinessProposals.isChecked()) {
                    str_chb_businessproposals = "1";
                } else if (chb_NewOpportunitis.isChecked()) {
                    str_chb_newopportunities = "1";
                } else if (str_up_profile_user_name.equals("")) {
                    edt_name.setError("Enter Name");
                    TastyToast.makeText(getApplicationContext(), "Name Cannot be Empty", TastyToast.LENGTH_LONG, TastyToast.WARNING);
                } else if (str_up_user_mobile.equals("")) {
                    edt_mobilenumber.setError("Enter Mobile Number");
                    TastyToast.makeText(getApplicationContext(), " Mobile Number Cannot be Empty", TastyToast.LENGTH_LONG, TastyToast.WARNING);
                } else if (str_up_user_gst_num.equals("")) {
                    edt_GST_number.setError("Please Enter GST Number");
                    TastyToast.makeText(getApplicationContext(), "GST Number Cannot be Empty", TastyToast.LENGTH_LONG, TastyToast.WARNING);
                } else if (str_up__user_company_name.equals("")) {
                    edt_companyname.setError("Please Enter Company Name");
                    TastyToast.makeText(getApplicationContext(), "Company Name Cannot be Empty", TastyToast.LENGTH_LONG, TastyToast.WARNING);
                } else if (str_up_user_address.equals("")) {
                    edt_address.setError("Please Enter Address");
                    TastyToast.makeText(getApplicationContext(), "Address Cannot be Empty", TastyToast.LENGTH_LONG, TastyToast.WARNING);
                } else if (str_up_user_designation.equals("")) {
                    edt_designation.setError("Please Enter Designation");
                    TastyToast.makeText(getApplicationContext(), " Designation Cannot be Empty", TastyToast.LENGTH_LONG, TastyToast.WARNING);
                } else if (str_up_user_location.equals("")) {
                    edt_location.setError("Please Enter Location");
                    TastyToast.makeText(getApplicationContext(), "Location Cannot be Empty", TastyToast.LENGTH_LONG, TastyToast.WARNING);
                } else {
                    dialog = new SpotsDialog(Activity_UserProfile_Update.this);
                    dialog.show();
                    queue = Volley.newRequestQueue(Activity_UserProfile_Update.this);
                    Update_Profile();

                }


            }
        });

    }


    /*****************************
     * GET User Profile
     ***************************/

    public void Get_User_Profile() {

        StringRequest request = new StringRequest(Request.Method.POST,
                AppConfig.url_user_profile, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d(TAG, response.toString());
                System.out.println("CAME RESPONSE ::: " + response.toString());

                try {
                    JSONObject obj = new JSONObject(response);
                    int success = obj.getInt("status");

                    if (success == 1) {
                        String str_obj = obj.getString("data");
                        JSONObject obj_data = new JSONObject(str_obj);

                        str_prev_profile_user_name = obj_data.getString(TAG_USER_NAME);
                        str_prev_user_mobile = obj_data.getString(TAG_USER_MOBILE);
                        str_prev_user_gst_num = obj_data.getString(TAG_USER_GST_NUMBER);
                        str_prev_user_company_name = obj_data.getString(TAG_USER_COMPANY_NAME);
                        str_prev_user_address = obj_data.getString(TAG_USER_ADDRESS);
                        str_prev_user_designation = obj_data.getString(TAG_USER_DESIGINATION);
                        str_prev_user_location = obj_data.getString(TAG_USER_LOCATION);
                        str_prev_email_business_proposals = obj_data.getString(TAG_USER_EMAIL_BUSINESS_PROPOSALS);
                        str_prev_email_new_opportunities = obj_data.getString(TAG_USER_EMAIL_NEW_OPPORTUNITIES);

                        try {
                            edt_name.setText("" + str_prev_profile_user_name);
                            edt_mobilenumber.setText("" + str_prev_user_mobile);
                            edt_companyname.setText("" + str_prev_user_company_name);
                            edt_location.setText("" + str_prev_user_location);
                            edt_designation.setText("" + str_prev_user_designation);
                            edt_address.setText("" + str_prev_user_address);
                            edt_GST_number.setText("" + str_prev_user_gst_num);

                            if (str_prev_email_business_proposals.equals("1")) {

                                chb_BusinessProposals.setChecked(true);
                            } else {
                                chb_BusinessProposals.setChecked(false);
                            }

                            if (str_prev_email_new_opportunities.equals("1")) {

                                chb_NewOpportunitis.setChecked(true);

                            } else {
                                chb_NewOpportunitis.setChecked(false);
                            }

                        } catch (Exception e) {

                        }

                        JSONArray arr;

                        arr = obj.getJSONArray("result");
                        System.out.println("Arrayyyyyyyyyyyyyy ::::::::::::::: " + arr);

                        for (int i = 0; arr.length() > i; i++) {
                            JSONObject obj1 = arr.getJSONObject(i);

                            String prefer_location_id = obj1.getString(TAG_PREF_LOCATION_ID);
                            String prefer_location_type = obj1.getString(TAG_PREF_LOCATION_TYPE);

                            Arraylist_pref_location_id.add(prefer_location_id);
                            Arraylist_pref_location_type.add(prefer_location_type);
                        }

                        try {


                        } catch (Exception e) {

                        }


                        JSONArray arr1;

                        arr1 = obj.getJSONArray("results");
                        System.out.println("Arrayyyyyyyyyyyyyy ::::::::::::::: " + arr1);

                        for (int j = 0; arr1.length() > j; j++) {
                            JSONObject obj_sec = arr1.getJSONObject(j);

                            String preferences_industries_id = obj_sec.getString(TAG_PREF_INDUSTRIES_ID);
                            String preferences_industry_type = obj_sec.getString(TAG_PREF_INDUSTRIES_TYPE);

                            Arraylist_pref_sector_id.add(preferences_industries_id);
                            Arraylist_pref_sector_type.add(preferences_industry_type);
                        }

                        try {
                            queue = Volley.newRequestQueue(getApplicationContext());
                            Get_Sector_List();

                        } catch (Exception e) {

                        }

                    } else if (success == 0) {

                        dialog.dismiss();

                        Alerter.create(Activity_UserProfile_Update.this)
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
                Alerter.create(Activity_UserProfile_Update.this)
                        .setTitle("AWESOME BUSINESSES")
                        .setText("Internal Error :(\n" + error.getMessage())
                        .setBackgroundColor(R.color.colorPrimaryDark)
                        .show();
            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();

                params.put("user_id", "3");

                System.out.println("USER_ID ::: " + "3");


                return params;
            }

        };

        // Adding request to request queue
        queue.add(request);
    }

    /*****************************
     * To get  Business sector
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

                            ArrayAdapter<String> adapter_sector = new ArrayAdapter<String>(Activity_UserProfile_Update.this,
                                    android.R.layout.simple_list_item_1, Arraylist_sector_name);
                            chip_preferred_industries.setAdapter(adapter_sector);

                            chip_preferred_industries.setOnItemClickListener(new AdapterView.OnItemClickListener() {
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
     * To get  Preferred Location
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

                            if (location_type.equals("continent")){
                                Arraylist_location_place_continent.add(location_place);
                            }else if (location_type.equals("country")){
                                Arraylist_location_place_country.add(location_place);
                            }else if (location_type.equals("state")){
                                Arraylist_location_place_state.add(location_place);
                            }else if (location_type.equals("city")){
                                Arraylist_location_place_city.add(location_place);
                            }


                            Arraylist_location_key.add(location_key);
                            Arraylist_location_type.add(location_type);
                        }
                        try {

                            System.out.println("ARAAAAY :: " + Arraylist_location_place);

                            ArrayAdapter<String> adapter_sector = new ArrayAdapter<String>(Activity_UserProfile_Update.this,
                                    android.R.layout.simple_list_item_1, Arraylist_location_place);
                            chip_preferred_loation.setAdapter(adapter_sector);

                            System.out.println("ARAAAAY :: " + 222222);
                            chip_preferred_loation.setOnItemClickListener(new AdapterView.OnItemClickListener() {
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

                        //TO SET PREVIOUSLY SELECTED INDUSTRIES IN CHIPLAYOUT

                        System.out.println(" Arraylist_pref_location_id :: " + Arraylist_pref_location_id);
                        System.out.println("Arraylist_location_place_country" + Arraylist_location_place_country);
                        System.out.println(" Arraylist_location_place_continent :: " + Arraylist_location_place_continent);
                        System.out.println(" Arraylist_location_place_state :: " + Arraylist_location_place_state);
                        System.out.println(" Arraylist_location_place_city :: " + Arraylist_location_place_city);

                        ArrayList<String> Arraylist_chipset_location = new ArrayList<>();

                        for (int i = 0; i < Arraylist_pref_location_id.size(); i++) {

                            String str_id = Arraylist_pref_location_id.get(i);
                            int position = Arraylist_pref_location_id.indexOf(str_id);
                            String str_type = Arraylist_pref_location_type.get(i);

                            if (str_type.equals("continent")){
                                String str_location_name = Arraylist_location_place_continent.get(position);
                                Arraylist_chipset_location.add(str_location_name);
                            }else if (str_type.equals("country")){
                                String str_location_name = Arraylist_location_place_country.get(position);
                                Arraylist_chipset_location.add(str_location_name);
                            }else if (str_type.equals("state")){
                                String str_location_name = Arraylist_location_place_state.get(position);
                                Arraylist_chipset_location.add(str_location_name);
                            }else if (str_type.equals("city")){

                                String str_location_name = Arraylist_location_place_city.get(position);
                                Arraylist_chipset_location.add(str_location_name);
                            }

                        }

                        System.out.println("SELECTED :::: " + Arraylist_chipset_location);

                        chip_preferred_loation.setText(Arraylist_chipset_location);

                        //TO SET PREVIOUSLY SELECTED LOCATION IN CHIPLAYOUT

                        ArrayList<String> Arraylist_chipset_industries = new ArrayList<>();

                        for (int i = 0; i < Arraylist_pref_sector_id.size(); i++) {

                            String str_id = Arraylist_pref_location_id.get(i);
                            int position = Arraylist_pref_location_id.indexOf(str_id);

                            String str_industry_name = Arraylist_pref_location_id.get(position);



                            Arraylist_chipset_industries.add(str_industry_name);
                            chip_preferred_industries.setText(Arraylist_chipset_industries);

                            System.out.println("PREVIOUSLY SELECTED INDUSTRIESSSSSSSSSS :: " + str_final_Business_Location);


                        }

                        dialog.dismiss();

                    } else if (success == 0) {
                        dialog.dismiss();
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
     * To UPDATE PROFILE
     ***************************/


    private void Update_Profile() {

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

                        Alerter.create(Activity_UserProfile_Update.this)
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

                //FROM EDIT TEXT
                params.put("user_name", str_up_profile_user_name);

                params.put("ph_no", str_up_user_mobile);

                params.put("location", str_up_user_location);

                params.put("company_name", str_up__user_company_name);

                params.put("designation", str_up_user_designation);

                params.put("gst_number", str_up_user_gst_num);

                params.put("address", str_up_user_address);

                params.put("industries", str_final_business_sector);

                params.put("cities", str_final_Business_Location);

                params.put("emailfrequency", str_chb_businessproposals);

                params.put("notify", str_chb_newopportunities);

                params.put("userid", str_user_id);


                System.out.println("User_Name" + str_up_profile_user_name);


                return params;
            }
        };
        queue.add(request);
    }


}
/*

     $user_name = $_POST['user_name'];
               $mobile_code = $_POST['mobile_code'];
               $ph_no = $_POST['ph_no'];
         $location = $_POST['location'];

               $company_name = $_POST['company_name'];
               $designation = $_POST['designation'];
               $gst_number = $_POST['gst_number'];
               $address = $_POST['address'];
               $industries = $_POST['industries'];
               $cities = $_POST['cities'];
               $emailfrequency = $_POST['emailfrequency'];
               $notify = $_POST['notify'];
               $userid = $_POST['userid'];

        */
