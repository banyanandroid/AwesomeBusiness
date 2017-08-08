package banyan.com.awesomebusiness.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
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

public class Activity_FranchiseProfile extends AppCompatActivity {

    private Toolbar mToolbar;
    Button btn_submit;
    SpotsDialog dialog;
    public static RequestQueue queue;
    String TAG = "FRANCHISE PROFILE";
    TextView t1;

    public static final String TAG_INDUSTRT_NAME = "name";
    public static final String TAG_INDUSTRY_KEY = "key";
    public static final String TAG_INDUSTRY_TYPE = "type";

    public static final String TAG_HEADQUATERS_PLACE = "place";
    public static final String TAG_HEADQUATERS_KEY = "key";
    public static final String TAG_HEADQUATERS_TYPE = "type";

    ArrayList<String> Arraylist_industry_name = null;
    ArrayList<String> Arraylist_industry_key = null;
    ArrayList<String> Arraylist_industry_type = null;

    ArrayList<String> Arraylist_selected_industry_key = null;
    String str_final_industry = "";

    ArrayList<String> Arraylist_location_place = null;
    ArrayList<String> Arraylist_location_key = null;
    ArrayList<String> Arraylist_location_type = null;

    ArrayList<String> Arraylist_selected_location = null;
    String str_final_Business_Location = "";

    ArrayList<String> Arraylist_expand_location_place = null;
    ArrayList<String> Arraylist_expand_location_key = null;
    ArrayList<String> Arraylist_expand_location_type = null;

    ArrayList<String> Arraylist_selected_expand_location = null;
    String str_final_expand__Location = "";

    ChipLayout chip_industry, chip_headquaters , chip_expand_locations ;

    EditText edt_name, edt_email, edt_mobile_num, edt_designation, edt_brand_name,
            edt_about_company, edt_all_prod, edt_opertions_start, et_no_of_sales_partners,
            edt_lookfor_in_salespartner, edt_kindof_support, edt_procedure_salespartner;

    CheckBox chb_franchise, chb_dealership, chb_reseller, chb_distributor, chb_salspartner;

    Spinner spn_no_of_salespartner_formats;

    CardView cv_format_one, cv_format_two, cv_format_three, cv_format_four, cv_format_five, cv_format_six;

    String str_no_of_formats;

    String str_opportunity_franchise, str_opportunity_dealership, str_opportunity_reseller,
            str_opportunity_distributor, str_opportunity_salespartner = "0";

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

        Arraylist_industry_name = new ArrayList<String>();
        Arraylist_industry_key = new ArrayList<String>();
        Arraylist_industry_type = new ArrayList<String>();

        Arraylist_selected_industry_key = new ArrayList<String>();

        Arraylist_location_place = new ArrayList<String>();
        Arraylist_location_key = new ArrayList<String>();
        Arraylist_location_type = new ArrayList<String>();

        Arraylist_selected_location = new ArrayList<String>();

        Arraylist_expand_location_place = new ArrayList<String>();
        Arraylist_expand_location_key = new ArrayList<String>();
        Arraylist_expand_location_type = new ArrayList<String>();

        Arraylist_selected_expand_location = new ArrayList<String>();

        ChipLayout.MAX_CHARACTER_COUNT = 20;
        chip_industry = (ChipLayout) findViewById(R.id.Franchise_chipset_industry);
        chip_headquaters = (ChipLayout) findViewById(R.id.Franchise_chipset_company_headquaters);
        chip_expand_locations = (ChipLayout) findViewById(R.id.Franchise_chipset_locations_expand);

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
        edt_opertions_start = (EditText) findViewById(R.id.edt_when_operations_start);
        et_no_of_sales_partners = (EditText) findViewById(R.id.edt_no_of_sales_partners);
        edt_lookfor_in_salespartner = (EditText) findViewById(R.id.edt_wt_lookfor_sales_partners);
        edt_kindof_support = (EditText) findViewById(R.id.edt_wt_kindof_support);
        edt_procedure_salespartner = (EditText) findViewById(R.id.edt_wt_procedure);

        spn_no_of_salespartner_formats = (Spinner) findViewById(R.id.spn_sales_partners_format);

        chb_franchise = (CheckBox) findViewById(R.id.chbx_franchise);
        chb_dealership = (CheckBox) findViewById(R.id.chbx_dealership);
        chb_reseller = (CheckBox) findViewById(R.id.chbx_reseller_opportunity);
        chb_distributor = (CheckBox) findViewById(R.id.chbx_distributor);
        chb_salspartner = (CheckBox) findViewById(R.id.chbx_salespartner);


        spn_no_of_salespartner_formats.setOnItemSelectedListener(
                new AdapterView.OnItemSelectedListener() {
                    public void onItemSelected(
                            AdapterView<?> parent, View view, int position, long id) {

                        str_no_of_formats = spn_no_of_salespartner_formats.getSelectedItem().toString();

                        if (str_no_of_formats.equals("1")) {

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


        btn_submit = (Button) findViewById(R.id.btn_submit);
        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // String Values According to checkbox state
                if (chb_franchise.isChecked()) {
                    str_opportunity_franchise = "1";
                }
                if (chb_dealership.isChecked()) {
                    str_opportunity_dealership = "1";
                }
                if (chb_reseller.isChecked()) {
                    str_opportunity_reseller = "1";
                }
                if (chb_distributor.isChecked()) {
                    str_opportunity_distributor = "1";
                }
                if (chb_salspartner.isChecked()) {
                    str_opportunity_salespartner = "1";
                }

                Toast.makeText(getApplicationContext(), "Franchise Profile Created Successfully", Toast.LENGTH_LONG).show();


            }
        });

        try {
            dialog = new SpotsDialog(Activity_FranchiseProfile.this);
            dialog.show();
            queue = Volley.newRequestQueue(getApplicationContext());
            Get_Business_industry();

        } catch (Exception e) {
            // TODO: handle exception
        }


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
                            chip_headquaters.setAdapter(adapter_location);


                            System.out.println("ARAAAAY :: " + 222222);
                            chip_headquaters.setOnItemClickListener(new AdapterView.OnItemClickListener() {
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

                                    System.out.println("FINAL Business Location :: " + str_final_Business_Location);


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

}