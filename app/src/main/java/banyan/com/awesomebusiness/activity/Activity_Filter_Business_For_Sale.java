package banyan.com.awesomebusiness.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.MultiAutoCompleteTextView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.sdsmdg.tastytoast.TastyToast;
import com.toptoche.searchablespinnerlibrary.SearchableSpinner;

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
 * Created by SJR on 21-Jul-17.
 */

public class Activity_Filter_Business_For_Sale extends AppCompatActivity {

    private Toolbar mToolbar;
    ProgressDialog pDialog;
    SpotsDialog dialog;
    public static RequestQueue queue;
    String TAG = "reg";
    TextView t1;

    Button btn_done, btn_clear_all;

    public static final String TAG_BUSINESS_INTEREST_ID = "business_interest_id";
    public static final String TAG_BUSINESS_INTEREST_NAME = "business_interest_name";

    public static final String TAG_INVESTOR_BUYER_TYPE_ID = "investor_an_id";
    public static final String TAG_INVESTOR_BUYER_TYPE_NAME = "investor_an_name";

    public static final String TAG_SECTOR_NAME = "name";
    public static final String TAG_SECTOR_KEY = "key";
    public static final String TAG_SECTOR_TYPE = "type";

    public static final String TAG_LOC_PLACE = "place";
    public static final String TAG_LOC_KEY = "key";
    public static final String TAG_LOC_TYPE = "type";

    ArrayList<String> Arraylist_sector_name = null;
    ArrayList<String> Arraylist_sector_key = null;
    ArrayList<String> Arraylist_sector_type = null;

    /*Multi Select*/
    ArrayList<String> Arraylist_selected_sectorkey = null;
    ArrayList<String> Arraylist_selected_location = null;

    ArrayList<String> Arraylist_location_place = null;
    ArrayList<String> Arraylist_location_key = null;
    ArrayList<String> Arraylist_location_type = null;

    ArrayList<String> Arraylist_business_interest_id = null;
    ArrayList<String> Arraylist_business_interest_name = null;
    private ArrayAdapter<String> adapter_transaction;

    ArrayList<String> Arraylist_investor_buyer_id = null;
    ArrayList<String> Arraylist_investor_buyer_name = null;
    private ArrayAdapter<String> adapter_investor_buyer_type;

    SearchableSpinner spn_main_filter, spn_business_for_sale_type, spn_investor_buyer_type;
    String str_main_filter = "";
    String str_selected_transaction_id, str_selected_transaction_type_name = "";
    String str_selected_investor_buyer_type_id, str_selected_investor_buyer_type_name = "";

    MultiAutoCompleteTextView auto_bus_busineeslist, auto_bus_locationlist, auto_bus_hq_location;

    String str_final_business_sector, str_final_Business_Location = "";

    LinearLayout LL_business_for_sale_type, LL_investor_buyer_type, LL_franchise_headquaters_location,
            LL_interested_business_locations, LL_interested_industry_sectors, LL_Slider_Investment_size,
            LL_Slider_Asset_price, LL_Slider_Run_Rate_Sales, LL_Slider_EBITA, LL_Slider_Established, LL_Slider_MonthlySales,
            LL_Checkboxes, LL_Investor_Buyer_Investor_Location, LL_Investor_Buyer_Investor_Interested_In;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter_menu);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        mToolbar.setNavigationIcon(getResources().getDrawable(R.drawable.ic_action_close));
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(getApplicationContext(), MainActivity.class);
                overridePendingTransition(R.anim.pull_in_right, R.anim.pull_in_left);
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
        Arraylist_location_key = new ArrayList<String>();
        Arraylist_location_type = new ArrayList<String>();

        Arraylist_business_interest_id = new ArrayList<String>();
        Arraylist_business_interest_name = new ArrayList<String>();

        Arraylist_investor_buyer_id = new ArrayList<String>();
        Arraylist_investor_buyer_name = new ArrayList<String>();

        LL_business_for_sale_type = (LinearLayout) findViewById(R.id.layout_business_for_sale_type);
        LL_investor_buyer_type = (LinearLayout) findViewById(R.id.layout_investor_buyer_type);
        LL_franchise_headquaters_location = (LinearLayout) findViewById(R.id.layout_franchise_headquaters_location);
        LL_interested_business_locations = (LinearLayout) findViewById(R.id.layout_interested_business_locations);
        LL_interested_industry_sectors = (LinearLayout) findViewById(R.id.layout_interested_business_industries);
        LL_Slider_Investment_size = (LinearLayout) findViewById(R.id.layout_slider_investment_size);
        LL_Slider_Asset_price = (LinearLayout) findViewById(R.id.layout_slider_asset_price);
        LL_Slider_Run_Rate_Sales = (LinearLayout) findViewById(R.id.layout_slider_run_rate_sales);
        LL_Slider_EBITA = (LinearLayout) findViewById(R.id.layout_slider_EBITDA);
        LL_Slider_Established = (LinearLayout) findViewById(R.id.layout_slider_established);
        LL_Slider_MonthlySales = (LinearLayout) findViewById(R.id.layout_slider_monthly_sales);
        LL_Checkboxes = (LinearLayout) findViewById(R.id.layout_checkboxes);
        LL_Investor_Buyer_Investor_Location = (LinearLayout) findViewById(R.id.layout_investor_buyer_investor_location);
        LL_Investor_Buyer_Investor_Interested_In = (LinearLayout) findViewById(R.id.layout_investor_buyer_investor_interested_in);

        LL_business_for_sale_type.setVisibility(View.GONE);
        LL_interested_business_locations.setVisibility(View.GONE);
        LL_interested_industry_sectors.setVisibility(View.GONE);
        LL_Slider_Investment_size.setVisibility(View.GONE);
        LL_Slider_Asset_price.setVisibility(View.GONE);
        LL_Slider_Run_Rate_Sales.setVisibility(View.GONE);
        LL_Slider_EBITA.setVisibility(View.GONE);
        LL_Slider_Established.setVisibility(View.GONE);
        LL_Checkboxes.setVisibility(View.GONE);
        LL_Slider_MonthlySales.setVisibility(View.GONE);
        LL_investor_buyer_type.setVisibility(View.GONE);
        LL_franchise_headquaters_location.setVisibility(View.GONE);
        LL_Investor_Buyer_Investor_Location.setVisibility(View.GONE);
        LL_Investor_Buyer_Investor_Interested_In.setVisibility(View.GONE);

        auto_bus_hq_location = (MultiAutoCompleteTextView) findViewById(R.id.filter_profile_multi_businesheadquaters__location);
        auto_bus_locationlist = (MultiAutoCompleteTextView) findViewById(R.id.business_profile_multi_business_location);
        auto_bus_busineeslist = (MultiAutoCompleteTextView) findViewById(R.id.business_profile_multi_business_industry);

        spn_main_filter = (SearchableSpinner) findViewById(R.id.spn_filter_main);

        spn_business_for_sale_type = (SearchableSpinner) findViewById(R.id.spn_filter_transtype);
        spn_investor_buyer_type = (SearchableSpinner) findViewById(R.id.spn_filter_investor_buyer_type);

        spn_main_filter.setOnItemSelectedListener(
                new AdapterView.OnItemSelectedListener() {
                    public void onItemSelected(
                            AdapterView<?> parent, View view, int position, long id) {

                        str_main_filter = spn_main_filter.getSelectedItem().toString();

                        if (str_main_filter.equals("Business For sale")) {

                            LL_business_for_sale_type.setVisibility(View.VISIBLE);
                            LL_interested_business_locations.setVisibility(View.VISIBLE);
                            LL_interested_industry_sectors.setVisibility(View.VISIBLE);
                            LL_Slider_Investment_size.setVisibility(View.VISIBLE);
                            LL_Slider_Asset_price.setVisibility(View.VISIBLE);
                            LL_Slider_Run_Rate_Sales.setVisibility(View.VISIBLE);
                            LL_Slider_EBITA.setVisibility(View.VISIBLE);
                            LL_Slider_Established.setVisibility(View.VISIBLE);
                            LL_Checkboxes.setVisibility(View.VISIBLE);

                            LL_Slider_MonthlySales.setVisibility(View.GONE);
                            LL_investor_buyer_type.setVisibility(View.GONE);
                            LL_franchise_headquaters_location.setVisibility(View.GONE);
                            LL_Investor_Buyer_Investor_Location.setVisibility(View.GONE);
                            LL_Investor_Buyer_Investor_Interested_In.setVisibility(View.GONE);


                        } else if (str_main_filter.equals("Investor/Buyers")) {

                            LL_investor_buyer_type.setVisibility(View.VISIBLE);
                            LL_interested_business_locations.setVisibility(View.VISIBLE);
                            LL_interested_industry_sectors.setVisibility(View.VISIBLE);
                            LL_Slider_Investment_size.setVisibility(View.VISIBLE);
                            LL_Investor_Buyer_Investor_Location.setVisibility(View.VISIBLE);
                            LL_Investor_Buyer_Investor_Interested_In.setVisibility(View.VISIBLE);

                            LL_business_for_sale_type.setVisibility(View.GONE);
                            LL_Slider_Asset_price.setVisibility(View.GONE);
                            LL_Slider_Run_Rate_Sales.setVisibility(View.GONE);
                            LL_Slider_EBITA.setVisibility(View.GONE);
                            LL_Slider_MonthlySales.setVisibility(View.GONE);
                            LL_franchise_headquaters_location.setVisibility(View.GONE);
                            LL_Slider_Established.setVisibility(View.GONE);
                            LL_Checkboxes.setVisibility(View.GONE);


                        } else if (str_main_filter.equals("Franchises")) {


                            LL_interested_business_locations.setVisibility(View.VISIBLE);
                            LL_interested_industry_sectors.setVisibility(View.VISIBLE);
                            LL_Slider_MonthlySales.setVisibility(View.VISIBLE);
                            LL_franchise_headquaters_location.setVisibility(View.VISIBLE);
                            LL_Slider_Investment_size.setVisibility(View.VISIBLE);
                            LL_Slider_EBITA.setVisibility(View.VISIBLE);
                            LL_Checkboxes.setVisibility(View.VISIBLE);

                            LL_business_for_sale_type.setVisibility(View.GONE);
                            LL_investor_buyer_type.setVisibility(View.GONE);
                            LL_Slider_Asset_price.setVisibility(View.GONE);
                            LL_Slider_Run_Rate_Sales.setVisibility(View.GONE);
                            LL_Slider_Established.setVisibility(View.GONE);
                            LL_Investor_Buyer_Investor_Location.setVisibility(View.GONE);
                            LL_Investor_Buyer_Investor_Interested_In.setVisibility(View.GONE);


                        }

                    }

                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });

        btn_clear_all = (Button) findViewById(R.id.btn_filter_clear);
        btn_clear_all.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                LL_business_for_sale_type.setVisibility(View.GONE);
                LL_interested_business_locations.setVisibility(View.GONE);
                LL_interested_industry_sectors.setVisibility(View.GONE);
                LL_Slider_Investment_size.setVisibility(View.GONE);
                LL_Slider_Asset_price.setVisibility(View.GONE);
                LL_Slider_Run_Rate_Sales.setVisibility(View.GONE);
                LL_Slider_EBITA.setVisibility(View.GONE);
                LL_Slider_Established.setVisibility(View.GONE);
                LL_Checkboxes.setVisibility(View.GONE);
                LL_Slider_MonthlySales.setVisibility(View.GONE);
                LL_investor_buyer_type.setVisibility(View.GONE);
                LL_franchise_headquaters_location.setVisibility(View.GONE);
                LL_Investor_Buyer_Investor_Location.setVisibility(View.GONE);
                LL_Investor_Buyer_Investor_Interested_In.setVisibility(View.GONE);

            }
        });

        btn_done = (Button) findViewById(R.id.btn_filter_done);
        btn_done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("str_filter_final_Business_Location", str_final_Business_Location);
                editor.putString("str_filter_final_business_sector", str_final_business_sector);
                editor.putString("str_filter_selected_transaction_type_name", str_selected_transaction_type_name);
                editor.commit();

                Intent i = new Intent(Activity_Filter_Business_For_Sale.this, MainActivity.class);
                startActivity(i);
                overridePendingTransition(R.anim.pull_in_right, R.anim.pull_in_left);
                finish();


            }
        });

        try {

            dialog = new SpotsDialog(Activity_Filter_Business_For_Sale.this);
            dialog.show();
            queue = Volley.newRequestQueue(Activity_Filter_Business_For_Sale.this);
            Get_Business_For_Sale_type();

        } catch (Exception e) {

        }
    }


    /*****************************
     * To get  Business For sale Type
     ***************************/

    public void Get_Business_For_Sale_type() {
        String tag_json_obj = "json_obj_req";
        StringRequest request = new StringRequest(Request.Method.POST,
                AppConfig.url_interested_in, new com.android.volley.Response.Listener<String>() {

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

                            String interest_key = obj1.getString(TAG_BUSINESS_INTEREST_ID);
                            String interest_name = obj1.getString(TAG_BUSINESS_INTEREST_NAME);

                            Arraylist_business_interest_id.add(interest_key);
                            Arraylist_business_interest_name.add(interest_name);
                        }
                        try {
                            adapter_transaction = new ArrayAdapter<String>(Activity_Filter_Business_For_Sale.this,
                                    android.R.layout.simple_list_item_1, Arraylist_business_interest_name);
                            spn_business_for_sale_type.setAdapter(adapter_transaction);
                            //  spn_business_for_sale_type.setThreshold(1);

                            spn_business_for_sale_type.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
                                                        long arg3) {
                                    t1 = (TextView) arg1;
                                    str_selected_transaction_type_name = t1.getText().toString();
                                    System.out.println("Argument " + arg2);
                                    str_selected_transaction_id = Arraylist_business_interest_id.get(arg2);


                                }
                            });

                        } catch (Exception e) {

                        }

                        try {
                            queue = Volley.newRequestQueue(getApplicationContext());
                            Get_InvestorBuyer_type();
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
        }, new com.android.volley.Response.ErrorListener() {

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
     * To get  Business For sale Type
     ***************************/

    public void Get_InvestorBuyer_type() {
        String tag_json_obj = "json_obj_req";
        StringRequest request = new StringRequest(Request.Method.POST,
                AppConfig.url_investor_buyer_type, new com.android.volley.Response.Listener<String>() {

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

                            String investor_buyer_id = obj1.getString(TAG_INVESTOR_BUYER_TYPE_ID);
                            String investor_buyer_name = obj1.getString(TAG_INVESTOR_BUYER_TYPE_NAME);

                            Arraylist_investor_buyer_id.add(investor_buyer_id);
                            Arraylist_investor_buyer_name.add(investor_buyer_name);
                        }
                        try {
                            adapter_investor_buyer_type = new ArrayAdapter<String>(Activity_Filter_Business_For_Sale.this,
                                    android.R.layout.simple_list_item_1, Arraylist_investor_buyer_name);
                            spn_investor_buyer_type.setAdapter(adapter_investor_buyer_type);
                            //  spn_business_for_sale_type.setThreshold(1);

                            spn_investor_buyer_type.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
                                                        long arg3) {
                                    t1 = (TextView) arg1;
                                    str_selected_investor_buyer_type_name = t1.getText().toString();
                                    System.out.println("Argument " + arg2);
                                    str_selected_investor_buyer_type_id = Arraylist_investor_buyer_id.get(arg2);


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
        }, new com.android.volley.Response.ErrorListener() {

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
                AppConfig.url_business, new com.android.volley.Response.Listener<String>() {

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

                            ArrayAdapter<String> adapter_sector1 = new ArrayAdapter<String>(Activity_Filter_Business_For_Sale.this,
                                    android.R.layout.simple_list_item_1, Arraylist_sector_name);
                            auto_bus_busineeslist.setAdapter(adapter_sector1);
                            auto_bus_busineeslist
                                    .setTokenizer(new MultiAutoCompleteTextView.CommaTokenizer());
                            auto_bus_busineeslist.setThreshold(1);

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
        }, new com.android.volley.Response.ErrorListener() {

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
                AppConfig.url_business_location, new com.android.volley.Response.Listener<String>() {

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

                            ArrayAdapter<String> adapter_process = new ArrayAdapter<String>(Activity_Filter_Business_For_Sale.this,
                                    android.R.layout.simple_list_item_1, Arraylist_location_place);
                            auto_bus_hq_location.setAdapter(adapter_process);
                            auto_bus_hq_location
                                    .setTokenizer(new MultiAutoCompleteTextView.CommaTokenizer());
                            auto_bus_hq_location.setThreshold(1);

                            auto_bus_locationlist.setAdapter(adapter_process);
                            auto_bus_locationlist
                                    .setTokenizer(new MultiAutoCompleteTextView.CommaTokenizer());
                            auto_bus_locationlist.setThreshold(1);

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
        }, new com.android.volley.Response.ErrorListener() {

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

    @Override
    public void onDestroy() {
        super.onDestroy();
        hidePDialog();
    }

    private void hidePDialog() {
        if (pDialog != null) {
            pDialog.dismiss();
            pDialog = null;
        }
    }
}
