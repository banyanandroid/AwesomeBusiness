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
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.JsonObject;
import com.libaml.android.view.chip.ChipLayout;
import com.sdsmdg.tastytoast.TastyToast;
import com.toptoche.searchablespinnerlibrary.SearchableSpinner;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import banyan.com.awesomebusiness.Activity_Register;
import banyan.com.awesomebusiness.R;
import banyan.com.awesomebusiness.api.RetroFitApi;
import banyan.com.awesomebusiness.global.AppConfig;
import dmax.dialog.SpotsDialog;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by SJR on 21-Jul-17.
 */

public class Activity_Filter extends AppCompatActivity {

    private Toolbar mToolbar;
    ProgressDialog pDialog;
    SpotsDialog dialog;
    public static RequestQueue queue;
    String TAG = "reg";
    TextView t1;

    Button btn_done;

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

    ChipLayout chip_busineeslist, chip_business_location;

    String str_final_business_sector, str_final_Business_Location = "";

    LinearLayout LL_business_for_sale_type, LL_investor_buyer_type, LL_franchise_headquaters_location,
            LL_interested_business_locations, LL_interested_industry_sectors, LL_Slider_Investment_size,
            LL_Slider_Asset_price, LL_Slider_Run_Rate_Sales, LL_Slider_EBITA, LL_Slider_Established,
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
        LL_Checkboxes = (LinearLayout) findViewById(R.id.layout_checkboxes);
        LL_Investor_Buyer_Investor_Location = (LinearLayout) findViewById(R.id.layout_investor_buyer_investor_location);
        LL_Investor_Buyer_Investor_Interested_In = (LinearLayout) findViewById(R.id.layout_investor_buyer_investor_interested_in);


        ChipLayout.MAX_CHARACTER_COUNT = 20;
        chip_busineeslist = (ChipLayout) findViewById(R.id.business_profile_chipText_business_industry);
        chip_business_location = (ChipLayout) findViewById(R.id.business_profile_chipText_business_location);

        spn_main_filter = (SearchableSpinner) findViewById(R.id.spn_filter_main);

        spn_business_for_sale_type = (SearchableSpinner) findViewById(R.id.spn_filter_transtype);
        spn_investor_buyer_type = (SearchableSpinner) findViewById(R.id.spn_filter_investor_buyer_type);


        spn_main_filter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                str_main_filter = spn_main_filter.getSelectedItem().toString();

                if (str_main_filter.equals("Business For sale")) {

                    LL_investor_buyer_type.setVisibility(View.GONE);
                    LL_franchise_headquaters_location.setVisibility(View.GONE);

                } else if (str_main_filter.equals("Investor/Buyers")) {


                } else if (str_main_filter.equals("Franchises")) {


                }

            }
        });


       /* spn_main_filter.setOnItemSelectedListener(
                new AdapterView.OnItemSelectedListener() {
                    public void onItemSelected(
                            AdapterView<?> parent, View view, int position, long id) {

                        str_main_filter = spn_main_filter.getSelectedItem().toString();

                        if (str_main_filter.equals("Business For sale")) {

                            LL_investor_buyer_type.setVisibility(View.GONE);
                            LL_franchise_headquaters_location.setVisibility(View.GONE);

                        } else if (str_main_filter.equals("Investor/Buyers")) {


                        } else if (str_main_filter.equals("Franchises")) {


                        }

                    }

                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });*/


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

                Intent i = new Intent(Activity_Filter.this, MainActivity.class);
                startActivity(i);
                overridePendingTransition(R.anim.pull_in_right, R.anim.pull_in_left);
                finish();


            }
        });

        //logging();

      /*  pDialog = new ProgressDialog(Activity_Filter.this);
        pDialog.setMessage("Please wait...");
        pDialog.show();
        pDialog.setCancelable(false);
        System.out.println("CALLED 00000");
        queue = Volley.newRequestQueue(Activity_Filter.this);*/
        // Function_Register();

        try {

            dialog = new SpotsDialog(Activity_Filter.this);
            dialog.show();
            queue = Volley.newRequestQueue(Activity_Filter.this);
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
                            adapter_transaction = new ArrayAdapter<String>(Activity_Filter.this,
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
                            adapter_investor_buyer_type = new ArrayAdapter<String>(Activity_Filter.this,
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

                            ArrayAdapter<String> adapter_sector = new ArrayAdapter<String>(Activity_Filter.this,
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

                            ArrayAdapter<String> adapter_sector = new ArrayAdapter<String>(Activity_Filter.this,
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


    public void logging() {
        int flag = 1;

        RestAdapter adapter = new RestAdapter.Builder()
                .setEndpoint(AppConfig.BASE_URL) //Setting the Root URL
                .build();
        JsonObject obj = new JsonObject();
       /*  obj.addProperty("action_type", flag);
        obj.addProperty("email", email);
        obj.addProperty("password", password);
        obj.addProperty("phone", phone);
        obj.addProperty("f_name", name);
        obj.addProperty("l_name", " ");
        obj.addProperty("lat", str_lat);
        obj.addProperty("lon", str_long);
        obj.addProperty("city", str_city);
        obj.addProperty("state", str_state);
        obj.addProperty("country", str_country);
        obj.addProperty("zip", str_zipcode);
        obj.addProperty("street", str_address);
        obj.addProperty("street_no", str_address);*/

        Log.d("register", obj.toString());
        RetroFitApi.Register api = adapter.create(RetroFitApi.Register.class);

        api.registerapi(
                obj,
                new Callback<Response>() {
                    @Override
                    public void success(Response result, Response response) {

                        try {

                            BufferedReader reader = new BufferedReader(new InputStreamReader(result.getBody().in()));
                            String resp;
                            resp = reader.readLine();
                            Log.d("Response", "" + resp);

                            JSONObject jObj = new JSONObject(resp);
                            int status = jObj.getInt("status");
//                            String message = jObj.getString("count");
                            if (status == 1) {
                                Toast.makeText(getApplicationContext(), status, Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(getApplicationContext(), status, Toast.LENGTH_SHORT).show();
                            }

                        } catch (Exception e) {
                            Log.d("Exception", e.toString());
                        }
                        // MD.dismiss();
                    }

                    @Override
                    public void failure(RetrofitError error) {
                        Log.d("dfdf", error.toString());
                        //if (error.getResponse().getStatus() == 404) {
                        Toast.makeText(Activity_Filter.this, "Server Not Found", Toast.LENGTH_LONG).show();
                        // }
                        // MD.dismiss();
                    }
                }
        );
    }

    /*private void Function_Register() {
        StringRequest request = new StringRequest(Request.Method.POST,
                url_register, new com.android.volley.Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d(TAG, response.toString());
                Log.d("USER_REGISTER", response.toString());
                try {
                    JSONObject obj = new JSONObject(response);
                    int success = obj.getInt("success");

                    System.out.println("REG" + success);

                    if (success == 1) {


                        System.out.println("REEEGGGG ::::  Sucess");

                    } else if (success == 2) {


                    } else {

                        System.out.println("REEEGGGG ::::  FAILED");
                    }
                } catch (JSONException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                pDialog.hide();
            }
        }, new com.android.volley.Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                pDialog.hide();
            }
        }) {
            @Override
            public byte[] getBody() throws AuthFailureError {
                HashMap<String, String> params2 = new HashMap<String, String>();
                params2.put("name", "Val");
                params2.put("subject", "Test Subject");
                return new JSONObject(params2).toString().getBytes();
            }

            @Override
            public String getBodyContentType() {
                return "application/json";
            }
        };

        // Adding request to request queue
        queue.add(request);
    }*/

   /* private void Function_Register() {

        System.out.println("CALLED 1111111111");
        System.out.println("CALLED " + url_register);
        StringRequest request = new StringRequest(Request.Method.POST,
                url_register1, new com.android.volley.Response.Listener<String>() {

            @Override
            public void onResponse(String response) {

                System.out.println("CALLED 33333333333333");
                Log.d(TAG, response.toString());
                Log.d("USER_REGISTER", response.toString());
                System.out.println("CALLED 33333333333333");
                try {
                    JSONObject obj = new JSONObject(response);
                    int status = obj.getInt("status");

                    System.out.println("REG" + status);

                    if (status == 1) {


                        System.out.println("REEEGGGG ::::  Sucess");

                    } else if (status == 2) {


                    } else {

                        System.out.println("REEEGGGG ::::  FAILED");
                    }
                } catch (JSONException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                pDialog.hide();
            }
        }, new com.android.volley.Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                pDialog.hide();
            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();


                System.out.println("CALLED 22222222222222222222");

                params.put("business_interest_name", "business_interest_name");

                System.out.println("business_interest_name" + "business_interest_name");

                return params;
            }

        };

        // Adding request to request queue
        queue.add(request);
    }*/

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
