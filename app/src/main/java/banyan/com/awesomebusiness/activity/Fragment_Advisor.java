package banyan.com.awesomebusiness.activity;

/**
 * Created by Jo on 19/07/17.
 */

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Context;
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
import android.view.inputmethod.InputMethodManager;
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
import banyan.com.awesomebusiness.adapter.List_AdvisorProfiles_Adapter;
import banyan.com.awesomebusiness.adapter.List_FranchiseProfiles_Adapter;
import banyan.com.awesomebusiness.adapter.List_InvestorProfiles_Adapter;
import banyan.com.awesomebusiness.global.AppConfig;
import dmax.dialog.SpotsDialog;


public class Fragment_Advisor extends Fragment {


    public static RequestQueue queue;
    SpotsDialog dialog;
    String TAG = "";
    Button btn_sort, btn_filter, btn_search;
    TextView t1;
    AutoCompleteTextView auto_search_suggest;

    String str_final_search, str_search_txt = "";

    private ListView List;
    public List_AdvisorProfiles_Adapter adapter;

    public static final String TAG_SEARCH_NAME = "name";
    public static final String TAG_SEARCH_KEY = "key";
    public static final String TAG_SEARCH_TYPE = "type";
    public static final String TAG_SEARCH_TITLE = "title";
    public static final String TAG_SEARCH_MAIN_TYPE = "main_type";


    public static final String TAG_ADVISOR_ID = "advisor_id";
    public static final String TAG_ADVISOR_KEY = "advisor_key";
    public static final String TAG_ADVISOR_USER_ID = "advisor_user_id";
    public static final String TAG_ADVISOR_NAME = "advisor_name";
    public static final String TAG_ADVISOR_EMAIL = "advisor_email";
    public static final String TAG_ADVISOR_MOBILE = "advisor_mobile";
    public static final String TAG_ADVISOR_COMPANY_NAME = "advisor_company_name";
    public static final String TAG_ADVISOR_ADDRESS_1 = "advisor_address_1";
    public static final String TAG_ADVISOR_ADDRESS_2 = "advisor_address_2";
    public static final String TAG_ADVISOR_CITY_ID = "advisor_city_id";
    public static final String TAG_ADVISOR_TYPE = "advisor_type";
    public static final String TAG_ADVISOR_ZIPCODE = "advisor_zipcode";
    public static final String TAG_ADVISOR_PHONE_NO = "advisor_phone_no";
    public static final String TAG_ADVISOR_ABOUT_COMPANY = "advisor_about_company";
    public static final String TAG_ADVISOR_IP_ADDRESS = "advisor_ip_address";
    public static final String TAG_ADVISOR_CREATED_ON = "advisor_created_on";
    public static final String TAG_ADVISOR_FORMAT_ACT = "advisor_format_act";
    public static final String TAG_RATING = "rating";
    public static final String TAG_IMAGE_ID = "image_id";

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
    String str_sort_by = "";

    static ArrayList<HashMap<String, String>> Advisor_profile_list;

    HashMap<String, String> params = new HashMap<String, String>();


    public Fragment_Advisor() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_advisor, container, false);

        auto_search_suggest = (AutoCompleteTextView) rootView.findViewById(R.id.advisor_autocomp_search);
        List = (ListView) rootView.findViewById(R.id.advisor_prof_listview);
        btn_sort = (Button) rootView.findViewById(R.id.frag_advisor_btn_sort);
        btn_filter = (Button) rootView.findViewById(R.id.frag_advisor_btn_filter);
        btn_search = (Button) rootView.findViewById(R.id.advisor_btn_search);

        Arraylist_search_name = new ArrayList<String>();
        Arraylist_search_key = new ArrayList<String>();
        Arraylist_search_type = new ArrayList<String>();
        Arraylist_search_title = new ArrayList<String>();
        Arraylist_search_main_type = new ArrayList<String>();

        Arraylist_update_location = new ArrayList<String>();
        Arraylist_update_industries = new ArrayList<String>();
        Arraylist_update_images = new ArrayList<String>();


        // Hashmap for ListView
        Advisor_profile_list = new ArrayList<HashMap<String, String>>();

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        str_user_currency = sharedPreferences.getString("str_selected_currency", "str_selected_currency");

        //Fragment Transition
        str_final_search = sharedPreferences.getString("search_id", "search_id");
        str_search_txt = sharedPreferences.getString("search_key", "search_key");


        try {

            if (str_search_txt.equals("search_key")) {
                System.out.println("Default Search key : " + str_search_txt);
            } else {
                System.out.println("Default Search key : " + str_search_txt);
                auto_search_suggest.setText(str_search_txt);
            }

        } catch (Exception e) {

        }


        List.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {

                System.out.println("ITEM CLICKED");

                String advisor_id = Advisor_profile_list.get(position).get(TAG_ADVISOR_ID);
                String advisor_key = Advisor_profile_list.get(position).get(TAG_ADVISOR_KEY);

                SharedPreferences sharedPreferences = PreferenceManager
                        .getDefaultSharedPreferences(getActivity());
                SharedPreferences.Editor editor = sharedPreferences.edit();

                editor.putString("advisor_id", advisor_id);
                editor.putString("advisor_key", advisor_key);

                editor.commit();

                Intent i = new Intent(getActivity(), Activity_DetailedView_Advisor_Profile.class);
                startActivity(i);

            }

        });

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
                editor.putString("profile_type_from", "Franchise Oppourtinites");
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

                    if (txt_pos == -1) {
                        TastyToast.makeText(getActivity(), "Please Enter Valid Search key", TastyToast.LENGTH_LONG, TastyToast.WARNING);
                    } else {

                        String str_search_key = Arraylist_search_key.get(txt_pos);
                        String str_search_type = Arraylist_search_type.get(txt_pos);
                        String str_search_title = Arraylist_search_title.get(txt_pos);
                        String str_search_main_type = Arraylist_search_main_type.get(txt_pos);

                        str_final_search = str_search_key + "-" + str_search_type + "-" + str_search_main_type;
                        System.out.println("User Location :: " + str_final_search);
                        System.out.println("TITLE :: " + str_search_title);

                        if (str_search_title.equals("Business for sale")) {

                            SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            // editor.putString("str_main_filter_type", "Franchise Oppourtinites");
                            editor.putString("search_key", str_search_txt);
                            editor.putString("search_id", str_final_search);
                            editor.commit();

                            Intent i = new Intent(getActivity(), MainActivity.class);
                            i.putExtra("type", "Business for sale");
                            startActivity(i);

                        } else if (str_search_title.equals("Investment Oppourtinites")) {

                            SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
                            SharedPreferences.Editor editor = sharedPreferences.edit();
//                        editor.putString("str_main_filter_type", "Investment Oppourtinites");
                            editor.putString("search_key", str_search_txt);
                            editor.putString("search_id", str_final_search);
                            editor.commit();

                            Intent i = new Intent(getActivity(), MainActivity.class);
                            i.putExtra("type", "Investment Oppourtinites");
                            startActivity(i);

                        } else if (str_search_title.equals("Franchise Oppourtinites")) {

                            SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
                            SharedPreferences.Editor editor = sharedPreferences.edit();
//                        editor.putString("str_main_filter_type", "Franchise Oppourtinites");
                            editor.putString("search_key", str_search_txt);
                            editor.putString("search_id", str_final_search);
                            editor.commit();

                            Intent i = new Intent(getActivity(), MainActivity.class);
                            i.putExtra("type", "Investment Oppourtinites");
                            startActivity(i);

                        } else if (str_search_title.equals("Advisors")) {

                            try {
                                dialog = new SpotsDialog(getActivity());
                                dialog.show();
                                Advisor_profile_list.clear();
                                queue = Volley.newRequestQueue(getActivity());
                                Get_Advisor_Profile();
                            } catch (Exception e) {
                                // TODO: handle exception
                            }

                        } else {

                        }

                    }

                } else {

                    TastyToast.makeText(getActivity(), "Please Enter Valid Search key", TastyToast.LENGTH_LONG, TastyToast.WARNING);

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
        actionSheet.addAction("Recently Listed", ActionSheet.Style.DEFAULT, new OnActionListener() {
            @Override
            public void onSelected(ActionSheet actionSheet, String title) {
                performAction(title);
                actionSheet.dismiss();
            }
        });
        actionSheet.addAction("Established Year(oldest first)", ActionSheet.Style.DEFAULT, new OnActionListener() {
            @Override
            public void onSelected(ActionSheet actionSheet, String title) {
                performAction(title);
                actionSheet.dismiss();
            }
        });

        actionSheet.addAction("Established Year(newest first)", ActionSheet.Style.DEFAULT, new OnActionListener() {
            @Override
            public void onSelected(ActionSheet actionSheet, String title) {
                performAction(title);
                actionSheet.dismiss();
            }
        });

        actionSheet.addAction("EBITDA", ActionSheet.Style.DEFAULT, new OnActionListener() {
            @Override
            public void onSelected(ActionSheet actionSheet, String title) {
                performAction(title);
                actionSheet.dismiss();
            }
        });

        actionSheet.addAction("Investment size(low to high)", ActionSheet.Style.DEFAULT, new OnActionListener() {
            @Override
            public void onSelected(ActionSheet actionSheet, String title) {
                performAction(title);
                actionSheet.dismiss();
            }
        });

        actionSheet.addAction("Investment size( high to low)", ActionSheet.Style.DEFAULT, new OnActionListener() {
            @Override
            public void onSelected(ActionSheet actionSheet, String title) {
                performAction(title);
                actionSheet.dismiss();
            }
        });

        actionSheet.show();
    }

    private void performAction(String title) {

        if (title.equals("Recently Listed")) {
            str_sort_by = "1";
        } else if (title.equals("Established Year(oldest first)")) {
            str_sort_by = "2";
        } else if (title.equals("Established Year(newest first)")) {
            str_sort_by = "3";
        } else if (title.equals("EBITDA")) {
            str_sort_by = "4";
        } else if (title.equals("Investment size(low to high)")) {
            str_sort_by = "5";
        } else if (title.equals("Investment size( high to low)")) {
            str_sort_by = "6";
        } else {
            str_sort_by = "";
        }

        Toast.makeText(getActivity(), "Sort By " + title, Toast.LENGTH_LONG).show();
        try {
            dialog = new SpotsDialog(getActivity());
            dialog.show();
            Advisor_profile_list.clear();
            queue = Volley.newRequestQueue(getActivity());
            Get_Advisor_Profile();
        } catch (Exception e) {
            // TODO: handle exception
        }
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
     * To get  Search Details
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

                                    InputMethodManager in = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                                    in.hideSoftInputFromWindow(view.getWindowToken(), 0);

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
                            dialog = new SpotsDialog(getActivity());
                            dialog.show();
                            queue = Volley.newRequestQueue(getActivity());
                            Get_Advisor_Profile();
                        } catch (Exception e) {
                            // TODO: handle exception
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


    /*****************************
     * To get Advisor Profiles
     ***************************/

    public void Get_Advisor_Profile() {
        String tag_json_obj = "json_obj_req";
        StringRequest request = new StringRequest(Request.Method.POST,
                AppConfig.url_dashboard_search_result_advisor_profile, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d(TAG, response.toString());
                try {
                    JSONObject obj = new JSONObject(response);

                    System.out.println("RESPONSE OBJ : " + obj);

                    int success = obj.getInt("status");

                    if (success == 1) {

                        System.out.println("111111111");

                        JSONArray arr;
                        JSONArray arr_location;
                        JSONArray arr_industry;
                        JSONArray arr_images;

                        arr = obj.getJSONArray("data");
                        for (int i = 0; arr.length() > i; i++) {
                            JSONObject obj1 = arr.getJSONObject(i);

                            System.out.println("22222222222");

                            String advisor_id = obj1.getString(TAG_ADVISOR_ID);
                            String advisor_key = obj1.getString(TAG_ADVISOR_KEY);
                            String advisor_user_id = obj1.getString(TAG_ADVISOR_USER_ID);
                            String advisor_name = obj1.getString(TAG_ADVISOR_NAME);
                            String advisor_email = obj1.getString(TAG_ADVISOR_EMAIL);
                            String advisor_mobile = obj1.getString(TAG_ADVISOR_MOBILE);
                            String advisor_company_name = obj1.getString(TAG_ADVISOR_COMPANY_NAME);
                            String advisor_address_1 = obj1.getString(TAG_ADVISOR_ADDRESS_1);
                            String advisor_address_2 = obj1.getString(TAG_ADVISOR_ADDRESS_2);
                            String advisor_city_id = obj1.getString(TAG_ADVISOR_CITY_ID);
                            String advisor_type = obj1.getString(TAG_ADVISOR_TYPE);
                            String advisor_zipcode = obj1.getString(TAG_ADVISOR_ZIPCODE);
                            String advisor_phone_no = obj1.getString(TAG_ADVISOR_PHONE_NO);
                            String advisor_about_company = obj1.getString(TAG_ADVISOR_ABOUT_COMPANY);
                            String advisor_ip_address = obj1.getString(TAG_ADVISOR_IP_ADDRESS);
                            String advisor_created_on = obj1.getString(TAG_ADVISOR_CREATED_ON);
                            String advisor_rating = obj1.getString(TAG_RATING);
                            String advisor_format_act = obj1.getString(TAG_ADVISOR_FORMAT_ACT);


                            arr_location = obj1.getJSONArray("location");
                            if (arr_location != null) {
                                Arraylist_update_location.clear();

                                for (int j = 0; arr_location.length() > j; j++) {
                                    JSONObject obj_location = arr_location.getJSONObject(j);

                                    String location_name = obj_location.optString(TAG_LOCATION_NAME);
                                    String location_key = obj_location.optString(TAG_LOCATION_KEY);

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
                                    String industry_name = obj_indus.optString(TAG_INDUSTRY_NAME);
                                    String industry_key = obj_indus.optString(TAG_INDUSTRY_KEY);

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

                                if (Arraylist_update_images.size() > 0) {
                                    str_final_image = Arraylist_update_images.get(0);
                                    System.out.println("IMAGE : " + str_final_image);
                                }

                            }


                            // creating new HashMap
                            // creating new HashMap
                            HashMap<String, String> map = new HashMap<String, String>();

                            // adding each child node to HashMap key => value
                            map.put(TAG_ADVISOR_ID, advisor_id);
                            map.put(TAG_ADVISOR_KEY, advisor_key);

                            map.put(TAG_ADVISOR_USER_ID, advisor_user_id);
                            map.put(TAG_ADVISOR_NAME, advisor_name);
                            map.put(TAG_ADVISOR_EMAIL, advisor_email);
                            map.put(TAG_ADVISOR_MOBILE, advisor_mobile);
                            map.put(TAG_ADVISOR_COMPANY_NAME, advisor_company_name);
                            map.put(TAG_ADVISOR_ADDRESS_1, advisor_address_1);
                            map.put(TAG_ADVISOR_ADDRESS_2, advisor_address_2);
                            map.put(TAG_ADVISOR_CITY_ID, advisor_city_id);
                            map.put(TAG_ADVISOR_TYPE, advisor_type);
                            map.put(TAG_ADVISOR_ZIPCODE, advisor_zipcode);
                            map.put(TAG_ADVISOR_PHONE_NO, advisor_phone_no);
                            map.put(TAG_ADVISOR_ABOUT_COMPANY, advisor_about_company);
                            map.put(TAG_ADVISOR_IP_ADDRESS, advisor_ip_address);
                            map.put(TAG_ADVISOR_CREATED_ON, advisor_created_on);
                            map.put(TAG_RATING, advisor_rating);
                            map.put(TAG_ADVISOR_FORMAT_ACT, advisor_format_act);

                            map.put(TAG_LOCATION_NAME, str_final_location);
                            map.put(TAG_INDUSTRY_NAME, str_final_industry);
                            map.put(TAG_IMAGE_PATH, str_final_image);


                            Advisor_profile_list.add(map);
                            adapter = new List_AdvisorProfiles_Adapter(getActivity(),
                                    Advisor_profile_list);
                            List.setAdapter(adapter);
                            System.out.println("HASHMAP ARRAY" + Advisor_profile_list);
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

                params.put("currency", str_user_currency);

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
