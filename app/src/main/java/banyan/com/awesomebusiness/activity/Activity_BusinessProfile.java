package banyan.com.awesomebusiness.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.TextInputLayout;
import android.support.percent.PercentLayoutHelper;
import android.support.percent.PercentRelativeLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
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
import dmax.dialog.SpotsDialog;

/**
 * Created by Schan on 24-Jul-17.
 */

public class Activity_BusinessProfile extends AppCompatActivity {

    private Toolbar mToolbar;
    SpotsDialog dialog;
    public static RequestQueue queue;
    Button btn_submit;
    String TAG = "Auto_Res";

    TextView t1;
    AutoCompleteTextView auto_i_am, auto_interested_in;
    ChipLayout chip_busineeslist, chip_business_location;
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

    private ArrayAdapter<String> adapter_interested;

    String str_selected_role_id, str_selected_role_name = "";

    String str_selected_interest_id, str_selected_interest_name = "";

    // Strings To Post For JSON
    String str_name, str_company_name, str_mobile, str_official_email,
            str_business_established_year, str_no_of_permanent_employees, str_business_desc,
            str_business_highlights, str_business_all_prod_serv, str_business_facility_desc, str_avg_monthly_sales,
            str_latest_yearly_sales, str_EBITDA, str_physical_assests_value,
            str_tentative_selling_price, str_reason_for_sale, str_spn_business_legal_type, str_user_id = "";

    String str_user_currency = "ATS";

    String str_ch_companydetails, str_ch_contactdetails, str_ch_yearly_sales_range,
            str_ch_display_EBITDA_range = "0";

    String str_final_business_sector, str_final_Business_Location = "";

    TextInputLayout txt_inp_name, txt_inp_mobile, txt_inp_company_name, txt_inp_email;


    EditText edt_name, edt_mobile, edt_company_name, edt_official_email, edt_business_established_year,
            edt_no_of_permanent_employees, edt_business_des, edt_business_highlights,
            edt_business_all_prod_serv, edt_business_facility_desc, edt_avg_monthly_sales, edt_latest_yearly_sales,
            edt_EBITDA, edt_physical_assests_value, edt_tentative_selling_price, edt_reason_for_sale;

    CheckBox chb_companydetails, chb_contatdetails, chb_display_EBITDA_as_range, chb_yearly_sales_range;

    Spinner spn_business_legal_type;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_business_profile);

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

        txt_inp_name = (TextInputLayout) findViewById(R.id.edt_textinput_name);

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

        auto_i_am = (AutoCompleteTextView) findViewById(R.id.business_profile_autocomp_i_am);
        auto_interested_in = (AutoCompleteTextView) findViewById(R.id.business_profile_autocomp_intersted);

        ChipLayout.MAX_CHARACTER_COUNT = 20;
        chip_busineeslist = (ChipLayout) findViewById(R.id.business_profile_chipText_busi_industry);
        chip_business_location = (ChipLayout) findViewById(R.id.business_profile_chipText_busi_loca_at);

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        str_user_id = sharedPreferences.getString("str_user_id", "str_user_id");

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


        btn_submit = (Button) findViewById(R.id.btn_submit);

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

                // Spinner Value to String
                str_spn_business_legal_type = spn_business_legal_type.getSelectedItem().toString();

                if (str_name.equals("")) {
                    // txt_inp_name.setError("Enter Name");
                    //  txt_inp_name.setErrorEnabled(true);
                    edt_name.setFocusable(true);
                    TastyToast.makeText(getApplicationContext(), "Name Cannot be empty", TastyToast.LENGTH_LONG, TastyToast.WARNING);
                } else if (str_company_name.equals("")) {
                    // edt_company_name.setError("Enter Company Name");
                    edt_company_name.setFocusable(true);
                    TastyToast.makeText(getApplicationContext(), "Company Cannot be Empty", TastyToast.LENGTH_LONG, TastyToast.WARNING);
                } else if (str_mobile.equals("")) {
                    // edt_mobile.setError("Enter Mobile Number");
                    edt_mobile.setFocusable(true);
                    TastyToast.makeText(getApplicationContext(), "Mobile Number Cannot be Empty", TastyToast.LENGTH_LONG, TastyToast.WARNING);
                } else if (str_official_email.equals("")) {
                    // edt_official_email.setError("Enter Official Email");
                    edt_official_email.setFocusable(true);
                    TastyToast.makeText(getApplicationContext(), "Email Cannot be Empty", TastyToast.LENGTH_LONG, TastyToast.WARNING);
                } else if (str_business_established_year.equals("")) {
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
                    TastyToast.makeText(getApplicationContext(), "Cannot be Empty", TastyToast.LENGTH_LONG, TastyToast.WARNING);
                } else if (str_physical_assests_value.equals("")) {
                    edt_physical_assests_value.setError("Please Enter Date");
                    TastyToast.makeText(getApplicationContext(), "Cannot be Empty", TastyToast.LENGTH_LONG, TastyToast.WARNING);
                } else if (str_tentative_selling_price.equals("")) {
                    edt_tentative_selling_price.setError("Please Enter Date");
                    TastyToast.makeText(getApplicationContext(), "Cannot be Empty", TastyToast.LENGTH_LONG, TastyToast.WARNING);
                } else if (str_reason_for_sale.equals("")) {
                    edt_reason_for_sale.setError("Please Enter Date");
                    TastyToast.makeText(getApplicationContext(), "Cannot be Empty", TastyToast.LENGTH_LONG, TastyToast.WARNING);
                } else if (str_spn_business_legal_type.equals("Select Business legal entity type")) {
                    spn_business_legal_type.setFocusable(true);
                    spn_business_legal_type.setFocusableInTouchMode(true);
                    spn_business_legal_type.requestFocus();
                    spn_business_legal_type.performClick();
                    TastyToast.makeText(getApplicationContext(), "Select Business legal entity type", TastyToast.LENGTH_LONG, TastyToast.WARNING);
                } else if (str_final_business_sector.equals("")) {
                    chip_busineeslist.setFocusable(true);
                    chip_busineeslist.setFocusableInTouchMode(true);
                    chip_busineeslist.requestFocus();
                    TastyToast.makeText(getApplicationContext(), "Select Business Sector", TastyToast.LENGTH_LONG, TastyToast.WARNING);
                } else if (str_final_Business_Location.equals("")) {
                    chip_business_location.setFocusable(true);
                    chip_business_location.setFocusableInTouchMode(true);
                    chip_business_location.requestFocus();
                    TastyToast.makeText(getApplicationContext(), "Select Business Location", TastyToast.LENGTH_LONG, TastyToast.WARNING);
                } else if (str_selected_role_id.equals("")) {
                    auto_i_am.setError("Enter Your Role");
                    TastyToast.makeText(getApplicationContext(), "Select Your Role", TastyToast.LENGTH_LONG, TastyToast.WARNING);
                } else if (str_selected_interest_id.equals("")) {
                    auto_interested_in.setError("Enter Your Interest");
                    TastyToast.makeText(getApplicationContext(), "Select Your Interest Type", TastyToast.LENGTH_LONG, TastyToast.WARNING);
                } else {
                    dialog = new SpotsDialog(Activity_BusinessProfile.this);
                    dialog.show();
                    queue = Volley.newRequestQueue(Activity_BusinessProfile.this);
                    Function_Submit_BusinessProfile();

                }

            }
        });

        try {
            dialog = new SpotsDialog(Activity_BusinessProfile.this);
            dialog.show();
            queue = Volley.newRequestQueue(getApplicationContext());
            Get_Iam_an();
            queue = Volley.newRequestQueue(getApplicationContext());
            Get_Interested();

        } catch (Exception e) {
            // TODO: handle exception
        }


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

                            adapter_i_am = new ArrayAdapter<String>(Activity_BusinessProfile.this,
                                    android.R.layout.simple_list_item_1, Arraylist_business_role_name);
                            auto_i_am.setAdapter(adapter_i_am);
                            auto_i_am.setThreshold(1);


                            auto_i_am.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
                                                        long arg3) {
                                    t1 = (TextView) arg1;
                                    str_selected_role_name = t1.getText().toString();
                                    str_selected_role_id = Arraylist_business_role_id.get(arg2);
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
                            adapter_interested = new ArrayAdapter<String>(Activity_BusinessProfile.this,
                                    android.R.layout.simple_list_item_1, Arraylist_business_interest_name);
                            auto_interested_in.setAdapter(adapter_interested);
                            auto_interested_in.setThreshold(1);

                            auto_interested_in.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
                                                        long arg3) {
                                    t1 = (TextView) arg1;
                                    str_selected_interest_name = t1.getText().toString();
                                    System.out.println("Argument " + arg2);
                                    str_selected_interest_id = Arraylist_business_interest_id.get(arg2);
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

                            ArrayAdapter<String> adapter_sector = new ArrayAdapter<String>(Activity_BusinessProfile.this,
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

                            ArrayAdapter<String> adapter_sector = new ArrayAdapter<String>(Activity_BusinessProfile.this,
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

                        Alerter.create(Activity_BusinessProfile.this)
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


/*

PARAMETERS
                $user_name=$_POST['user_name'];
                $company_name=$_POST['company_name'];
                $mobile_no=$_POST['mobile_no'];
                $email=$_POST['email'];
                $user_contact=$_POST['user_contact'];
                $user_company=$_POST['user_company'];
                $role=$_POST['role'];
                $interest_in=$_POST['interest_in'];
                $year=$_POST['year'];
                $locations='locations';
                $industry='industry';
                $employee=$_POST['employee'];
                $entitys=$_POST['entitys'];
                $business_description=$_POST['business_description'];
                $highlights=$_POST['highlights'];
                $services=$_POST['services'];
                $business_facility=$_POST['business_facility'];
                $monthly_expected_sales=$_POST['monthly_expected_sales'];
                $yearly_expected_sales=$_POST['yearly_expected_sales'];
                $yearly_sales_range=$_POST['yearly_sales_range'];
                $ebitda=$_POST['ebitda'];
                $display=$_POST['display'];
                $physical_assets=$_POST['physical_assets'];
                $currency=$_POST['tentative_price'];
                $reason=$_POST['reason'];
                $user_id=$_POST['user_id'];
                $user_currency=$_POST['user_currency'];

                */