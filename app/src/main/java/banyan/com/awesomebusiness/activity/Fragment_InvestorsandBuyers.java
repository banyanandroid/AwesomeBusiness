package banyan.com.awesomebusiness.activity;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ListView;
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
import com.aromajoin.actionsheet.ActionSheet;
import com.aromajoin.actionsheet.OnActionListener;
import com.sdsmdg.tastytoast.TastyToast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import banyan.com.awesomebusiness.R;
import banyan.com.awesomebusiness.adapter.List_BusinessProfiles_Adapter;
import banyan.com.awesomebusiness.global.AppConfig;
import dmax.dialog.SpotsDialog;


/**
 * Created by Jo on 19/07/17.
 */
public class Fragment_InvestorsandBuyers extends Fragment {

    public static RequestQueue queue;
    SpotsDialog dialog;
    String TAG = "";
    Button btn_sort, btn_filter, btn_search;
    TextView t1;
    AutoCompleteTextView auto_search_suggest;

    String str_final_search = "";

    private ListView List;
    public List_BusinessProfiles_Adapter adapter;

    public static final String TAG_SEARCH_NAME = "name";
    public static final String TAG_SEARCH_KEY = "key";
    public static final String TAG_SEARCH_TYPE = "type";
    public static final String TAG_SEARCH_TITLE = "title";
    public static final String TAG_SEARCH_MAIN_TYPE = "main_type";

    public static final String TAG_BUSINESS_PROF_ID = "business_id";
    public static final String TAG_BUSINESS_PROF_KEY = "business_key";
    public static final String TAG_BUSINESS_PROF_SHORT_DES = "buisness_short_description";
    public static final String TAG_BUSINESS_PROF_YEARLY_SALES = "business_yearly_sales";
    public static final String TAG_BUSINESS_PROF_EBITDA = "business_ebitda";
    public static final String TAG_BUSINESS_PROF_EBITDA_RANGE = "business_ebitda_range";
    public static final String TAG_BUISNESS_PROF_INVESTMENT = "buisness_investment";
    public static final String TAG_BUISNESS_PROF_DESCRIPTION = "buisness_description";
    public static final String TAG_BUSINESS_PROF_INTEREST_NAME = "business_interest_name";
    public static final String TAG_BUSINESS_PROF_ROLE_NAME = "business_role_name";
    public static final String TAG_COUNTRY_PROF_CURRENCY = "country_currency";
    public static final String TAG_BUSINESS_PROF_TENTATIVE_PRICE = "business_tentative_price";

    public static final String TAG_LOCATION_NAME = "location_name";
    public static final String TAG_LOCATION_KEY = "location_key";

    public static final String TAG_INDUSTRY_NAME = "industry_name";
    public static final String TAG_INDUSTRY_KEY = "industry_key";

    public static final String TAG_IMAGE_PATH = "image_path";

    ArrayList<String> Arraylist_update_location = null;
    ArrayList<String> Arraylist_update_industries = null;
    ArrayList<String> Arraylist_update_images = null;

    String str_final_location, str_final_industry, str_final_image = "";

    ArrayList<String> Arraylist_search_name = null;
    ArrayList<String> Arraylist_search_key = null;
    ArrayList<String> Arraylist_search_type = null;
    ArrayList<String> Arraylist_search_title = null;
    ArrayList<String> Arraylist_search_main_type = null;

    String str_user_currency = "";

    String str_filter_sale_transaction_type, str_filter_interested_business_locations, str_filter_interested_industries,
            str_filter_investment_size_minimum, str_filter_investment_size_maximum, str_filter_runrate_sales_minimum,
            str_filter_runrate_sales_maximum, str_filter_ebitda_minimum, str_filter_ebitda_maximum, str_filter_established_minimum,
            str_filter_established_maximum, str_filter_limited_liability_company, str_filter_public_limited_company, str_filter_partnership,
            str_filter_S_corporation, str_filter_private_limited_company, str_filter_C_corporation, str_filter_limited_liability_partnership,
            str_filter_sole_proprietorship, str_filter_others = "";

    String str_con_investment, str_con_runrate_sales, str_con_ebitda, str_con_established = "";

    static ArrayList<HashMap<String, String>> Business_profile_list;

    HashMap<String, String> params = new HashMap<String, String>();

    public Fragment_InvestorsandBuyers() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_investors_buyers, container, false);

        auto_search_suggest = (AutoCompleteTextView) rootView.findViewById(R.id.investor_autocomp_search);
        List = (ListView) rootView.findViewById(R.id.bus_investor_listview);
        btn_sort = (Button) rootView.findViewById(R.id.frag_investor_btn_sort);
        btn_filter = (Button) rootView.findViewById(R.id.frag_investor_btn_filter);
        btn_search = (Button) rootView.findViewById(R.id.frag_investor_btn_search);

        Arraylist_search_name = new ArrayList<String>();
        Arraylist_search_key = new ArrayList<String>();
        Arraylist_search_type = new ArrayList<String>();
        Arraylist_search_title = new ArrayList<String>();
        Arraylist_search_main_type = new ArrayList<String>();

        Arraylist_update_location = new ArrayList<String>();
        Arraylist_update_industries = new ArrayList<String>();
        Arraylist_update_images = new ArrayList<String>();

        // Hashmap for ListView
        Business_profile_list = new ArrayList<HashMap<String, String>>();

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        str_user_currency = sharedPreferences.getString("str_selected_currency", "str_selected_currency");
        str_filter_sale_transaction_type = sharedPreferences.getString("str_business_for_sale_transaction_type", "str_business_for_sale_transaction_type");
        str_filter_interested_business_locations = sharedPreferences.getString("str_interested_business_locations", "str_interested_business_locations");
        str_filter_interested_industries = sharedPreferences.getString("str_interested_industries", "str_interested_industries");

        str_filter_investment_size_minimum = sharedPreferences.getString("str_investment_size_minimum", "str_investment_size_minimum");
        str_filter_investment_size_maximum = sharedPreferences.getString("str_investment_size_maximum", "str_investment_size_maximum");
        str_filter_runrate_sales_minimum = sharedPreferences.getString("str_runrate_sales_minimum", "str_runrate_sales_minimum");
        str_filter_runrate_sales_maximum = sharedPreferences.getString("str_runrate_sales_maximum", "str_runrate_sales_maximum");
        str_filter_ebitda_minimum = sharedPreferences.getString("str_ebitda_minimum", "str_ebitda_minimum");
        str_filter_ebitda_maximum = sharedPreferences.getString("str_ebitda_maximum", "str_ebitda_maximum");
        str_filter_established_minimum = sharedPreferences.getString("str_established_minimum", "str_established_minimum");
        str_filter_established_maximum = sharedPreferences.getString("str_established_maximum", "str_established_maximum");
        str_filter_limited_liability_company = sharedPreferences.getString("str_limited_liability_company", "str_limited_liability_company");
        str_filter_public_limited_company = sharedPreferences.getString("str_public_limited_company", "str_public_limited_company");
        str_filter_partnership = sharedPreferences.getString("str_partnership", "str_partnership");
        str_filter_S_corporation = sharedPreferences.getString("str_S_corporation", "str_S_corporation");
        str_filter_private_limited_company = sharedPreferences.getString("str_private_limited_company", "str_private_limited_company");
        str_filter_C_corporation = sharedPreferences.getString("str_C_corporation", "str_C_corporation");
        str_filter_limited_liability_partnership = sharedPreferences.getString("str_limited_liability_partnership", "str_limited_liability_partnership");
        str_filter_sole_proprietorship = sharedPreferences.getString("str_sole_proprietorship", "str_sole_proprietorship");
        str_filter_others = sharedPreferences.getString("str_others", "str_others");

        str_con_investment = str_filter_investment_size_minimum + "," + str_filter_investment_size_maximum;
        str_con_runrate_sales = str_filter_runrate_sales_minimum + "," + str_filter_runrate_sales_maximum;
        str_con_ebitda = str_filter_ebitda_minimum + "," + str_filter_ebitda_maximum;
        str_con_established = str_filter_established_minimum + "," + str_filter_established_maximum;

        btn_sort.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showActionSheet(view);
            }
        });

        btn_filter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("profile_type_from", "Business For sale");
                editor.commit();

                Intent i = new Intent(getActivity(), Activity_Filter_Business_For_Sale.class);
                Bundle bundle = ActivityOptions.makeCustomAnimation(getActivity(), R.anim.pull_in_left, R.anim.pull_in_right).toBundle();
                startActivity(i, bundle);

            }
        });

        btn_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (!str_final_search.equals("")) {

                    String str_search_txt = auto_search_suggest.getText().toString();
                    int txt_pos = Arraylist_search_name.indexOf(str_search_txt);
                    String str_search_key = Arraylist_search_key.get(txt_pos);
                    String str_search_type = Arraylist_search_type.get(txt_pos);
                    String str_search_title = Arraylist_search_title.get(txt_pos);
                    String str_search_main_type = Arraylist_search_main_type.get(txt_pos);

                    str_final_search = str_search_key + "-" + str_search_type + "-" + str_search_main_type;
                    System.out.println("User Location :: " + str_final_search);

                    try {
                        dialog = new SpotsDialog(getActivity());
                        dialog.show();
                        queue = Volley.newRequestQueue(getActivity());
                        Get_Business_Profile();
                    } catch (Exception e) {
                        // TODO: handle exception
                    }

                } else {

                    TastyToast.makeText(getActivity(), "Please Enter Your Search key", TastyToast.LENGTH_LONG, TastyToast.WARNING);

                }

            }
        });


        try {
            dialog = new SpotsDialog(getActivity());
            dialog.show();
            queue = Volley.newRequestQueue(getActivity());
            Get_Search_Autofil();
        } catch (Exception e) {
            // TODO: handle exception
        }

        // Inflate the layout for this fragment
        return rootView;
    }

    private void showActionSheet(View anchor) {
        ActionSheet actionSheet = new ActionSheet(getActivity());
        actionSheet.setTitle("sort by");
        actionSheet.setSourceView(anchor);
        actionSheet.addAction("Newest", ActionSheet.Style.DEFAULT, new OnActionListener() {
            @Override
            public void onSelected(ActionSheet actionSheet, String title) {
                performAction(title);
                actionSheet.dismiss();
            }
        });
        actionSheet.addAction("Oldest", ActionSheet.Style.DEFAULT, new OnActionListener() {
            @Override
            public void onSelected(ActionSheet actionSheet, String title) {
                performAction(title);
                actionSheet.dismiss();
            }
        });

        actionSheet.addAction("Relevant", ActionSheet.Style.DEFAULT, new OnActionListener() {
            @Override
            public void onSelected(ActionSheet actionSheet, String title) {
                performAction(title);
                actionSheet.dismiss();
            }
        });

        actionSheet.show();
    }

    private void performAction(String title) {

        Toast.makeText(getActivity(), "Sort By " + title, Toast.LENGTH_LONG).show();

    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }


    /*****************************
     * To get  Interested in  Details
     ***************************/

    public void Get_Search_Autofil() {
        String tag_json_obj = "json_obj_req";
        StringRequest request = new StringRequest(Request.Method.POST,
                AppConfig.url_dashboard_search_autofil, new Response.Listener<String>() {

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

                            String search_name = obj1.getString("name");
                            String search_key = obj1.getString("key");
                            String search_type = obj1.getString("type");
                            String search_title = obj1.getString("title");
                            String search_mail_type = obj1.getString("main_type");

                            Arraylist_search_name.add(search_name);
                            Arraylist_search_key.add(search_key);
                            Arraylist_search_type.add(search_type);
                            Arraylist_search_title.add(search_title);
                            Arraylist_search_main_type.add(search_mail_type);
                        }
                        try {

                            ArrayAdapter<String> adapter_sector = new ArrayAdapter<String>(getActivity(),
                                    android.R.layout.simple_list_item_1, Arraylist_search_name);

                            auto_search_suggest.setAdapter(adapter_sector);
                            auto_search_suggest.setThreshold(1);

                            auto_search_suggest.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                @Override
                                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                                    t1 = (TextView) view;
                                    String str_location_name = t1.getText().toString();
                                    int i = Arraylist_search_name.indexOf(str_location_name);

                                    String str_search_key = Arraylist_search_key.get(i);
                                    String str_search_type = Arraylist_search_type.get(i);
                                    String str_search_title = Arraylist_search_title.get(i);
                                    String str_search_main_type = Arraylist_search_main_type.get(i);

                                    str_final_search = str_search_key + "-" + str_search_type + "-" + str_search_main_type;
                                    System.out.println("User Location :: " + str_final_search);

                                }
                            });


                        } catch (Exception e) {

                        }
                        dialog.dismiss();
                        try {
                            Params_check();
                        } catch (Exception e) {

                        }

                    } else if (success == 0) {
                        TastyToast.makeText(getActivity(), "Something Went Wrong :(", TastyToast.LENGTH_LONG, TastyToast.ERROR);
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

    /**********************************
     *  Initial Validation
     * **********************************/

    private void Params_check() {

        if (str_filter_C_corporation.equals("str_C_corporation")) {
            str_filter_C_corporation = "";
        } else {

        }
        if (str_filter_public_limited_company.equals("str_public_limited_company")) {
            str_filter_public_limited_company = "";
        } else {

        }
        if (str_filter_others.equals("str_others")) {
            str_filter_others = "";
        }
        if (str_filter_limited_liability_partnership.equals("str_limited_liability_partnership")) {
            str_filter_limited_liability_partnership = "";
        } else {

        }
        if (str_filter_sole_proprietorship.equals("str_sole_proprietorship")) {
            str_filter_sole_proprietorship = "";
        } else {

        }
        if (str_filter_S_corporation.equals("str_S_corporation")) {
            str_filter_S_corporation = "";
        } else {

        }
        if (str_filter_partnership.equals("str_partnership")) {
            str_filter_partnership = "";
        } else {

        }
        if (str_filter_private_limited_company.equals("str_private_limited_company")) {
            str_filter_private_limited_company = "";
        } else {

        }
        if (str_filter_limited_liability_company.equals("str_limited_liability_company")) {
            str_filter_limited_liability_company = "";
        } else {

        }
        if (str_filter_sale_transaction_type.equals("str_business_for_sale_transaction_type")) {
            str_filter_sale_transaction_type = "";
        } else {

        }
        if (str_con_investment.equals("str_investment_size_minimum,str_investment_size_maximum")) {
            str_con_investment = "";
        } else {

        }
        if (str_con_runrate_sales.equals("str_runrate_sales_minimum,str_runrate_sales_maximum")) {
            str_con_runrate_sales = "";
        } else {

        }
        if (str_con_ebitda.equals("str_ebitda_minimum,str_ebitda_maximum")) {
            str_con_ebitda = "";
        } else {

        }
        if (str_con_established.equals("str_established_minimum,str_established_maximum")) {
            str_con_established = "";
        } else {

        }
        if (str_filter_interested_industries.equals("str_interested_industries")) {
            str_filter_interested_industries = "";
        } else {

        }
        if (str_filter_interested_business_locations.equals("str_interested_business_locations")) {
            str_filter_interested_business_locations = "";
        } else {

        }

        System.out.println("CC" + str_filter_C_corporation);
        System.out.println("plc" + str_filter_public_limited_company);
        System.out.println("Other" + str_filter_others);
        System.out.println("llp" + str_filter_limited_liability_partnership);
        System.out.println("sp" + str_filter_sole_proprietorship);
        System.out.println("sc" + str_filter_S_corporation);
        System.out.println("partner" + str_filter_partnership);
        System.out.println("plp" + str_filter_private_limited_company);
        System.out.println("llc" + str_filter_limited_liability_company);
        System.out.println("transactions" + str_filter_sale_transaction_type);
        System.out.println("investment" + str_con_investment);
        System.out.println("run_rate_sales" + str_con_runrate_sales);
        System.out.println("EBITDA" + str_con_ebitda);
        System.out.println("established" + str_con_established);
        System.out.println("industries" + str_filter_interested_industries);
        System.out.println("keyword" + str_final_search);
        System.out.println("locations" + str_filter_interested_business_locations);
        System.out.println("purchased" + "");
        System.out.println("asset_investment" + "");
        System.out.println("sort_by" + "");
        System.out.println("currency" + str_user_currency);
        try {
            dialog = new SpotsDialog(getActivity());
            dialog.show();
            queue = Volley.newRequestQueue(getActivity());
            Get_Business_Profile();
        } catch (Exception e) {
            // TODO: handle exception
        }

    }

    /*****************************
     * To get Business Profiles
     ***************************/

    public void Get_Business_Profile() {
        String tag_json_obj = "json_obj_req";
        StringRequest request = new StringRequest(Request.Method.POST,
                AppConfig.url_dashboard_search_result_business_profile, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d(TAG, response.toString());
                try {
                    JSONObject obj = new JSONObject(response);

                    System.out.println("RESPONSE OBJ : " + obj);

                    int success = obj.getInt("status");

                    if (success == 1) {

                        JSONArray arr;
                        JSONArray arr_location;
                        JSONArray arr_industry;
                        JSONArray arr_images;

                        arr = obj.getJSONArray("data");
                        for (int i = 0; arr.length() > i; i++) {
                            JSONObject obj1 = arr.getJSONObject(i);

                            String business_id = obj1.getString(TAG_BUSINESS_PROF_ID);
                            String business_key = obj1.getString(TAG_BUSINESS_PROF_KEY);
                            String buisness_short_description = obj1.getString(TAG_BUSINESS_PROF_SHORT_DES);
                            String business_yearly_sales = obj1.getString(TAG_BUSINESS_PROF_YEARLY_SALES);
                            String business_ebitda = obj1.getString(TAG_BUSINESS_PROF_EBITDA);
                            String business_ebitda_range = obj1.getString(TAG_BUSINESS_PROF_EBITDA_RANGE);
                            String buisness_investment = obj1.getString(TAG_BUISNESS_PROF_INVESTMENT);
                            String buisness_description = obj1.getString(TAG_BUISNESS_PROF_DESCRIPTION);
                            String business_interest_name = obj1.getString(TAG_BUSINESS_PROF_INTEREST_NAME);
                            String business_role_name = obj1.getString(TAG_BUSINESS_PROF_ROLE_NAME);
                            String country_currency = obj1.getString(TAG_COUNTRY_PROF_CURRENCY);
                            String tentative_price = obj1.getString(TAG_BUSINESS_PROF_TENTATIVE_PRICE);


                            arr_location = obj1.getJSONArray("location");
                            if (arr_location != null) {
                                Arraylist_update_location.clear();
                                for (int j = 0; arr_location.length() > j; j++) {
                                    JSONObject obj_location = arr_location.getJSONObject(j);

                                    String location_name = obj_location.getString(TAG_LOCATION_NAME);
                                    String location_key = obj_location.getString(TAG_LOCATION_KEY);

                                    Arraylist_update_location.add(location_name);

                                }
                                str_final_location = TextUtils.join(", ", Arraylist_update_location);
                            }

                            arr_industry = obj1.getJSONArray("industry");
                            if (arr_industry != null) {
                                System.out.println("Length Industry:: " + arr_industry.length());
                                Arraylist_update_industries.clear();
                                for (int k = 0; arr_industry.length() > k; k++) {
                                    JSONObject obj_indus = arr_industry.getJSONObject(k);
                                    System.out.println("INDUS :: " + obj_indus);
                                    String industry_name = obj_indus.getString(TAG_INDUSTRY_NAME);
                                    String industry_key = obj_indus.getString(TAG_INDUSTRY_KEY);

                                    Arraylist_update_industries.add(industry_name);
                                }
                                str_final_industry = TextUtils.join(", ", Arraylist_update_industries);
                            }

                            arr_images = obj1.getJSONArray("images");
                            if (arr_images != null) {
                                System.out.println("Length images:: " + arr_images.length());
                                Arraylist_update_images.clear();
                                for (int l = 0; arr_images.length() > l; l++) {
                                    JSONObject obj_image = arr_images.getJSONObject(l);
                                    String image_path = obj_image.getString(TAG_IMAGE_PATH);

                                    Arraylist_update_images.add(image_path);
                                }

                                if (Arraylist_update_images.size() > 0){
                                    str_final_image = Arraylist_update_images.get(0);
                                    System.out.println("IMAGE : " + str_final_image);
                                }
                            }

                            // creating new HashMap
                            HashMap<String, String> map = new HashMap<String, String>();
                            // adding each child node to HashMap key => value
                            map.put(TAG_BUSINESS_PROF_ID, business_id);
                            map.put(TAG_BUSINESS_PROF_KEY, business_key);
                            map.put(TAG_BUSINESS_PROF_SHORT_DES, buisness_short_description);
                            map.put(TAG_BUSINESS_PROF_YEARLY_SALES, business_yearly_sales);
                            map.put(TAG_BUSINESS_PROF_EBITDA, business_ebitda);
                            map.put(TAG_BUSINESS_PROF_EBITDA_RANGE, business_ebitda_range);
                            map.put(TAG_BUISNESS_PROF_INVESTMENT, buisness_investment);
                            map.put(TAG_BUISNESS_PROF_DESCRIPTION, buisness_description);
                            map.put(TAG_BUSINESS_PROF_INTEREST_NAME, business_interest_name);
                            map.put(TAG_BUSINESS_PROF_ROLE_NAME, business_role_name);
                            map.put(TAG_COUNTRY_PROF_CURRENCY, country_currency);
                            map.put(TAG_BUSINESS_PROF_TENTATIVE_PRICE, tentative_price);
                            map.put(TAG_LOCATION_NAME, str_final_location);
                            map.put(TAG_INDUSTRY_NAME, str_final_industry);
                            map.put(TAG_IMAGE_PATH, str_final_image);

                            Business_profile_list.add(map);
                            adapter = new List_BusinessProfiles_Adapter(getActivity(),
                                    Business_profile_list);
                            List.setAdapter(adapter);
                            System.out.println("HASHMAP ARRAY" + Business_profile_list);
                        }

                        dialog.dismiss();
                    } else if (success == 0) {
                        TastyToast.makeText(getActivity(), "Something Went Wrong :(", TastyToast.LENGTH_LONG, TastyToast.ERROR);
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

                params.put("CC", str_filter_C_corporation);
                params.put("plc", str_filter_public_limited_company);
                params.put("Other", str_filter_others);
                params.put("llp", str_filter_limited_liability_partnership);
                params.put("sp", str_filter_sole_proprietorship);
                params.put("sc", str_filter_S_corporation);
                params.put("partner", str_filter_partnership);
                params.put("plp", str_filter_private_limited_company);
                params.put("llc", str_filter_limited_liability_company);
                params.put("transactions", str_filter_sale_transaction_type);
                params.put("investment", str_con_investment);
                params.put("run_rate_sales", str_con_runrate_sales);
                params.put("EBITDA", str_con_ebitda);
                params.put("established", str_con_established);
                params.put("industries", str_filter_interested_industries);
                params.put("keyword", str_final_search);
                params.put("locations", str_filter_interested_business_locations);
                params.put("purchased", "");
                params.put("asset_investment", "");
                params.put("sort_by", "");
                params.put("currency", str_user_currency);

                System.out.println("CC" + str_filter_C_corporation);
                System.out.println("plc" + str_filter_public_limited_company);
                System.out.println("Other" + str_filter_others);
                System.out.println("llp" + str_filter_limited_liability_partnership);
                System.out.println("sp" + str_filter_sole_proprietorship);
                System.out.println("sc" + str_filter_S_corporation);
                System.out.println("partner" + str_filter_partnership);
                System.out.println("plp" + str_filter_private_limited_company);
                System.out.println("llc" + str_filter_limited_liability_company);
                System.out.println("transactions" + str_filter_sale_transaction_type);
                System.out.println("investment" + str_con_investment);
                System.out.println("run_rate_sales" + str_con_runrate_sales);
                System.out.println("EBITDA" + str_con_ebitda);
                System.out.println("established" + str_con_established);
                System.out.println("industries" + str_filter_interested_industries);
                System.out.println("keyword" + str_final_search);
                System.out.println("locations" + str_filter_interested_business_locations);
                System.out.println("purchased" + "");
                System.out.println("asset_investment" + "");
                System.out.println("sort_by" + "");
                System.out.println("currency" + str_user_currency);


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
