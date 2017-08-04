package banyan.com.awesomebusiness.activity;

import android.content.Intent;
import android.os.Bundle;
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
    ChipLayout chip_busineeslist , chip_business_location ;
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
            str_tentative_selling_price, str_reason_for_sale, str_spn_business_legal_type, str_ch_companydetails,
            str_ch_contactdetails, str_ch_yearly_sales_range,
            str_ch_display_EBITDA_range, str_user_id, str_user_currency = "";

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
        chip_business_location  = (ChipLayout) findViewById(R.id.business_profile_chipText_busi_loca_at);

        Arraylist_business_role_id = new ArrayList<String>();
        Arraylist_business_role_name = new ArrayList<String>();

        Arraylist_business_interest_id = new ArrayList<String>();
        Arraylist_business_interest_name = new ArrayList<String>();

        Arraylist_sector_name = new ArrayList<String>();
        Arraylist_sector_key = new ArrayList<String>();
        Arraylist_sector_type = new ArrayList<String>();

        Arraylist_selected_sectorkey = new ArrayList<String>();
        Arraylist_selected_location = new ArrayList<String>();

        Arraylist_location_place  = new ArrayList<String>();
        Arraylist_location_key   = new ArrayList<String>();
        Arraylist_location_type   = new ArrayList<String>();

        btn_submit = (Button) findViewById(R.id.btn_submit);

        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

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
                        }catch (Exception e) {

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
                                    android.R.layout.simple_list_item_1,Arraylist_sector_name);
                            chip_busineeslist.setAdapter(adapter_sector);

                            chip_busineeslist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                @Override
                                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                                    System.out.println("Position :::::::: " + position);

                                    t1 = (TextView) view;
                                    String str_sector_key  = t1.getText().toString();
                                    int i = Arraylist_sector_name.indexOf(str_sector_key);

                                    String str_select_sector_key = Arraylist_sector_key.get(i);
                                    String str_select_sector_type = Arraylist_sector_type.get(i);
                                    String str_select_item = str_select_sector_key + "-" + str_select_sector_type;
                                    Arraylist_selected_sectorkey.add(str_select_item);
                                    String listString = "";
                                    for (String s : Arraylist_selected_sectorkey)
                                    {
                                        listString += s + ",";
                                    }

                                    System.out.println("FINAL SECTORRRRRRRRRR :: " + listString);


                                }
                            });

                        } catch (Exception e) {

                        }

                        try {
                            queue = Volley.newRequestQueue(getApplicationContext());
                            Get_Business_Location();
                        }catch (Exception e) {

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
                                    android.R.layout.simple_list_item_1,Arraylist_location_place);
                            chip_business_location.setAdapter(adapter_sector);

                            System.out.println("ARAAAAY :: " + 222222);
                            chip_business_location.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                @Override
                                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                                    System.out.println("Position :::::::: " + position);


                                    t1 = (TextView) view;
                                    String str_location_key  = t1.getText().toString();
                                    int i = Arraylist_location_place.indexOf(str_location_key);

                                    String str_select_location_key = Arraylist_location_key.get(i);
                                    String str_select_location_type = Arraylist_location_type.get(i);
                                    String str_select_item = str_select_location_key + "-" + str_select_location_type;
                                    Arraylist_selected_location.add(str_select_item);
                                    String str_Business_Location = "";
                                    for (String s : Arraylist_selected_location)
                                    {
                                        str_Business_Location += s + ",";
                                    }

                                    System.out.println("FINAL SECTORRRRRRRRRR :: " + str_Business_Location);


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
}