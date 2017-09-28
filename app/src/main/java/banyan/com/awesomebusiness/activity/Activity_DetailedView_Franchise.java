package banyan.com.awesomebusiness.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.MultiAutoCompleteTextView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
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

/**
 * Created by Banyan on 9/27/2017.
 */

public class Activity_DetailedView_Franchise extends AppCompatActivity {

    private Toolbar mToolbar;
    SpotsDialog dialog;
    public static RequestQueue queue;
    String TAG = "Auto_Res";
    SessionManager session;
    public static String str_user_id, str_user_name, str_user_email, str_user_photoo;
    String str_user_currency, str_franchise_key = "";

    public static final String TAG_FRANCHISE_ID = "franchise_id";
    public static final String TAG_FRANCHISE_KEY = "franchise_key";
    public static final String TAG_FRANCHISE_USER_NAME = "franchise_user_name";
    public static final String TAG_FRANCHISE_EMAIL = "franchise_email";
    public static final String TAG_FRANCHISE_MOBILE = "franchise_mobile";
    public static final String TAG_FRANCHISE_DESIGNATION = "franchise_designation";
    public static final String TAG_FRANCHISE_BRAND_NAME = "franchise_brand_name";
    public static final String TAG_FRANCHISE_BRAND_OFFERING = "franchise_brand_offering";
    public static final String TAG_FRANCHISE_BRAND_COMPANY = "franchise_brand_company";
    public static final String TAG_FRANCHISE_BRAND_SERVICES = "franchise_brand_services";
    public static final String TAG_FRANCHISE_BRAND_ESTABLISHED = "franchise_brand_established";
    public static final String TAG_FRANCHISE_BRAND_HEADQUATERS = "franchise_brand_headquaters";
    public static final String TAG_BRAND_SALEPARTNER_COUNT = "brand_salepartner_count";
    public static final String TAG_BRAND_SALEPARTNER_BEFORE_PARTNERING = "brand_salepartner_before_partnering";
    public static final String TAG_BRAND_SALEPARTNER_EXPECT = "brand_salepartner_expect";

    public static final String TAG_BRAND_SALEPARTNER_PROCEDURE = "brand_salepartner_procedure";
    public static final String TAG_FRANCHISE_FORMAT = "franchise_format";
    public static final String TAG_FRANCHISE_LOGO = "franchise_logo";
    public static final String TAG_FRANCHISE_CURRENCY = "franchise_currency";
    public static final String TAG_COUNTRY_CURRENCY = "country_currency";


    public static final String TAG_LOCATION_NAME = "location_name";
    public static final String TAG_LOCATION_KEY = "location_key";

    public static final String TAG_INDUSTRY_NAME = "industry_name";
    public static final String TAG_INDUSTRY_KEY = "industry_key";

    public static final String TAG_IMAGE_PATH = "image_path";

    //String Related to no of formats
    //FORMAT 1
    public static final String TAG_FRANCHISE_ID1 = "franchise_id1";
    public static final String TAG_FRANCHISE_FORMAT_NAME1 = "franchise_format_name1";
    public static final String TAG_FRANCHISE_FORMAT_MIN_INVESTMENT1 = "franchise_format_min_investment1";
    public static final String TAG_FRANCHISE_FORMAT_MAX_INVESTMENT1 = "franchise_format_max_investment1";
    public static final String TAG_FRANCHISE_FORMAT_FEE1 = "franchise_format_fee1";
    public static final String TAG_FRANCHISE_FORMAT_NO_OF_STAFFS1 = "franchise_format_no_of_staffs1";
    public static final String TAG_FRANCHISE_FORMAT_ROYALITY1 = "franchise_format_royality1";
    public static final String TAG_FRANCHISE_FORMAT_REVENUE1 = "franchise_format_revenue1";
    public static final String TAG_FRANCHISE_FORMAT_PROFIT1 = "franchise_format_profit1";
    public static final String TAG_FRANCHISE_FORMAT_ACT1 = "franchise_format_act1";
    public static final String TAG_FRANCHISE_FORMAT_MIN_SQFT1 = "franchise_format_min_sqft1";
    public static final String TAG_FRANCHISE_FORMAT_MAX_SQFT1 = "franchise_format_max_sqft1";

    //FORMAT 2
    public static final String TAG_FRANCHISE_ID2 = "franchise_id2";
    public static final String TAG_FRANCHISE_FORMAT_NAME2 = "franchise_format_name2";
    public static final String TAG_FRANCHISE_FORMAT_MIN_INVESTMENT2 = "franchise_format_min_investment2";
    public static final String TAG_FRANCHISE_FORMAT_MAX_INVESTMENT2 = "franchise_format_max_investment2";
    public static final String TAG_FRANCHISE_FORMAT_FEE2 = "franchise_format_fee2";
    public static final String TAG_FRANCHISE_FORMAT_NO_OF_STAFFS2 = "franchise_format_no_of_staffs2";
    public static final String TAG_FRANCHISE_FORMAT_ROYALITY2 = "franchise_format_royality2";
    public static final String TAG_FRANCHISE_FORMAT_REVENUE2 = "franchise_format_revenue2";
    public static final String TAG_FRANCHISE_FORMAT_PROFIT2 = "franchise_format_profit2";
    public static final String TAG_FRANCHISE_FORMAT_ACT2 = "franchise_format_act2";
    public static final String TAG_FRANCHISE_FORMAT_MIN_SQFT2 = "franchise_format_min_sqft2";
    public static final String TAG_FRANCHISE_FORMAT_MAX_SQFT2 = "franchise_format_max_sqft2";

    //FORMAT 3
    public static final String TAG_FRANCHISE_ID3 = "franchise_id3";
    public static final String TAG_FRANCHISE_FORMAT_NAME3 = "franchise_format_name3";
    public static final String TAG_FRANCHISE_FORMAT_MIN_INVESTMENT3 = "franchise_format_min_investment3";
    public static final String TAG_FRANCHISE_FORMAT_MAX_INVESTMENT3 = "franchise_format_max_investment3";
    public static final String TAG_FRANCHISE_FORMAT_FEE3 = "franchise_format_fee3";
    public static final String TAG_FRANCHISE_FORMAT_NO_OF_STAFFS3 = "franchise_format_no_of_staffs3";
    public static final String TAG_FRANCHISE_FORMAT_ROYALITY3 = "franchise_format_royality3";
    public static final String TAG_FRANCHISE_FORMAT_REVENUE3 = "franchise_format_revenue3";
    public static final String TAG_FRANCHISE_FORMAT_PROFIT3 = "franchise_format_profit3";
    public static final String TAG_FRANCHISE_FORMAT_ACT3 = "franchise_format_act3";
    public static final String TAG_FRANCHISE_FORMAT_MIN_SQFT3 = "franchise_format_min_sqft3";
    public static final String TAG_FRANCHISE_FORMAT_MAX_SQFT3 = "franchise_format_max_sqft3";

    //FORMAT 4
    public static final String TAG_FRANCHISE_ID4 = "franchise_id4";
    public static final String TAG_FRANCHISE_FORMAT_NAME4 = "franchise_format_name4";
    public static final String TAG_FRANCHISE_FORMAT_MIN_INVESTMENT4 = "franchise_format_min_investment4";
    public static final String TAG_FRANCHISE_FORMAT_MAX_INVESTMENT4 = "franchise_format_max_investment4";
    public static final String TAG_FRANCHISE_FORMAT_FEE4 = "franchise_format_fee4";
    public static final String TAG_FRANCHISE_FORMAT_NO_OF_STAFFS4 = "franchise_format_no_of_staffs4";
    public static final String TAG_FRANCHISE_FORMAT_ROYALITY4 = "franchise_format_royality4";
    public static final String TAG_FRANCHISE_FORMAT_REVENUE4 = "franchise_format_revenue4";
    public static final String TAG_FRANCHISE_FORMAT_PROFIT4 = "franchise_format_profit4";
    public static final String TAG_FRANCHISE_FORMAT_ACT4 = "franchise_format_act4";
    public static final String TAG_FRANCHISE_FORMAT_MIN_SQFT4 = "franchise_format_min_sqft4";
    public static final String TAG_FRANCHISE_FORMAT_MAX_SQFT4 = "franchise_format_max_sqft4";

    //FORMAT 5
    public static final String TAG_FRANCHISE_ID5 = "franchise_id5";
    public static final String TAG_FRANCHISE_FORMAT_NAME5 = "franchise_format_name5";
    public static final String TAG_FRANCHISE_FORMAT_MIN_INVESTMENT5 = "franchise_format_min_investment5";
    public static final String TAG_FRANCHISE_FORMAT_MAX_INVESTMENT5 = "franchise_format_max_investment5";
    public static final String TAG_FRANCHISE_FORMAT_FEE5 = "franchise_format_fee5";
    public static final String TAG_FRANCHISE_FORMAT_NO_OF_STAFFS5 = "franchise_format_no_of_staffs5";
    public static final String TAG_FRANCHISE_FORMAT_ROYALITY5 = "franchise_format_royality5";
    public static final String TAG_FRANCHISE_FORMAT_REVENUE5 = "franchise_format_revenue5";
    public static final String TAG_FRANCHISE_FORMAT_PROFIT5 = "franchise_format_profit5";
    public static final String TAG_FRANCHISE_FORMAT_ACT5 = "franchise_format_act5";
    public static final String TAG_FRANCHISE_FORMAT_MIN_SQFT5 = "franchise_format_min_sqft5";
    public static final String TAG_FRANCHISE_FORMAT_MAX_SQFT5 = "franchise_format_max_sqft5";

    //FORMAT 6
    public static final String TAG_FRANCHISE_ID6 = "franchise_id6";
    public static final String TAG_FRANCHISE_FORMAT_NAME6 = "franchise_format_name6";
    public static final String TAG_FRANCHISE_FORMAT_MIN_INVESTMENT6 = "franchise_format_min_investment6";
    public static final String TAG_FRANCHISE_FORMAT_MAX_INVESTMENT6 = "franchise_format_max_investment6";
    public static final String TAG_FRANCHISE_FORMAT_FEE6 = "franchise_format_fee6";
    public static final String TAG_FRANCHISE_FORMAT_NO_OF_STAFFS6 = "franchise_format_no_of_staffs6";
    public static final String TAG_FRANCHISE_FORMAT_ROYALITY6 = "franchise_format_royality6";
    public static final String TAG_FRANCHISE_FORMAT_REVENUE6 = "franchise_format_revenue6";
    public static final String TAG_FRANCHISE_FORMAT_PROFIT6 = "franchise_format_profit6";
    public static final String TAG_FRANCHISE_FORMAT_ACT6 = "franchise_format_act6";
    public static final String TAG_FRANCHISE_FORMAT_MIN_SQFT6 = "franchise_format_min_sqft6";
    public static final String TAG_FRANCHISE_FORMAT_MAX_SQFT6 = "franchise_format_max_sqft6";


    ArrayList<String> Arraylist_update_location = null;
    ArrayList<String> Arraylist_update_industries = null;
    ArrayList<String> Arraylist_update_images = null;


    TextView txt_brand_name, txt_brand_offering, txt_about_brand,
            txt_prod_serv_overview, txt_preferred_locations, txt_how_to_own, txt_support;

    // CARDVIEW FOR NO.OF SALES PARTNER FORMATS
    CardView cv_format_one, cv_format_two, cv_format_three, cv_format_four, cv_format_five, cv_format_six;

    // FORMAT 1
    TextView format_name_1, format_spaceneeded_minimum_1, format_spaceneeded_maximum_1,
            format_investment_needed_minimum_1, format_investment_needed_maximum_1,
            format_brand_fee_1, format_staff_required_1, format_royalty_commission_1,
            format_salespartner_monthly_revenue_1, format_operating_profitmargin_1;

    // FORMAT 2
    TextView format_name_2, format_spaceneeded_minimum_2, format_spaceneeded_maximum_2,
            format_investment_needed_minimum_2, format_investment_needed_maximum_2,
            format_brand_fee_2, format_staff_required_2, format_royalty_commission_2,
            format_salespartner_monthly_revenue_2, format_operating_profitmargin_2;

    // FORMAT 3
    TextView format_name_3, format_spaceneeded_minimum_3, format_spaceneeded_maximum_3,
            format_investment_needed_minimum_3, format_investment_needed_maximum_3,
            format_brand_fee_3, format_staff_required_3, format_royalty_commission_3,
            format_salespartner_monthly_revenue_3, format_operating_profitmargin_3;

    // FORMAT 4
    TextView format_name_4, format_spaceneeded_minimum_4, format_spaceneeded_maximum_4,
            format_investment_needed_minimum_4, format_investment_needed_maximum_4,
            format_brand_fee_4, format_staff_required_4, format_royalty_commission_4,
            format_salespartner_monthly_revenue_4, format_operating_profitmargin_4;

    // FORMAT 5
    TextView format_name_5, format_spaceneeded_minimum_5, format_spaceneeded_maximum_5,
            format_investment_needed_minimum_5, format_investment_needed_maximum_5,
            format_brand_fee_5, format_staff_required_5, format_royalty_commission_5,
            format_salespartner_monthly_revenue_5, format_operating_profitmargin_5;

    // FORMAT 6
    TextView format_name_6, format_spaceneeded_minimum_6, format_spaceneeded_maximum_6,
            format_investment_needed_minimum_6, format_investment_needed_maximum_6,
            format_brand_fee_6, format_staff_required_6, format_royalty_commission_6,
            format_salespartner_monthly_revenue_6, format_operating_profitmargin_6;


    Button btn_contact_business;

    ImageView img_franchise_logo;

    TextView title, industries, locations;

    String str_final_location, str_final_industries, str_final_image = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed_view_franchise);

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

        session = new SessionManager(getApplicationContext());
        session.checkLogin();
        HashMap<String, String> user = session.getUserDetails();

        // name
        str_user_name = user.get(SessionManager.KEY_USER);
        str_user_email = user.get(SessionManager.KEY_USER_EMAIL);
        str_user_photoo = user.get(SessionManager.KEY_USER_PHOTO);
        str_user_id = user.get(SessionManager.KEY_USER_ID);

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        str_user_currency = sharedPreferences.getString("str_selected_currency", "str_selected_currency");
        str_franchise_key = sharedPreferences.getString("franchise_key", "franchise_key");

        System.out.println("USER_ID----" + str_user_id);
        System.out.println("FRANCHISE KEY-----" + str_franchise_key);
        System.out.println("CURRENCY-----" + str_user_currency);


        Arraylist_update_location = new ArrayList<String>();
        Arraylist_update_industries = new ArrayList<String>();
        Arraylist_update_images = new ArrayList<String>();

        txt_brand_name = (TextView) findViewById(R.id.activity_franchise_details_brand_name);
        txt_brand_offering = (TextView) findViewById(R.id.activity_franchise_details_brand_offering);
        txt_about_brand = (TextView) findViewById(R.id.activity_franchise_details_txt_about_brand_name);
        txt_prod_serv_overview = (TextView) findViewById(R.id.activity_franchise_details_txt_products_services_overview);
        txt_preferred_locations = (TextView) findViewById(R.id.activity_franchise_details_txt_preferred_locations);
        txt_how_to_own = (TextView) findViewById(R.id.activity_franchise_details_txt_how_to_own);
        txt_support = (TextView) findViewById(R.id.activity_franchise_details_txt_support);

        cv_format_one = (CardView) findViewById(R.id.ativity_franchise_details_card_view_format_one);
        cv_format_two = (CardView) findViewById(R.id.ativity_franchise_details_card_view_format_two);
        cv_format_three = (CardView) findViewById(R.id.ativity_franchise_details_card_view_format_three);
        cv_format_four = (CardView) findViewById(R.id.ativity_franchise_details_card_view_format_four);
        cv_format_five = (CardView) findViewById(R.id.ativity_franchise_details_card_view_format_five);
        cv_format_six = (CardView) findViewById(R.id.ativity_franchise_details_card_view_format_six);


        //// NO.OF SALES PARTNER FORMATS TextView'S
        ////
        ////FORMAT 1
        format_name_1 = (TextView) findViewById(R.id.ativity_franchise_details_format_name_format_one);
        format_spaceneeded_minimum_1 = (TextView) findViewById(R.id.ativity_franchise_details_space_needed_from_format_one);
        format_spaceneeded_maximum_1 = (TextView) findViewById(R.id.ativity_franchise_details_space_needed_to_format_one);
        format_investment_needed_minimum_1 = (TextView) findViewById(R.id.ativity_franchise_details_investment_size_from_format_one);
        format_investment_needed_maximum_1 = (TextView) findViewById(R.id.ativity_franchise_details_investment_size_to_format_one);
        format_brand_fee_1 = (TextView) findViewById(R.id.ativity_franchise_details_brand_fee_format_one);
        format_staff_required_1 = (TextView) findViewById(R.id.ativity_franchise_details_staffs_format_one);
        format_royalty_commission_1 = (TextView) findViewById(R.id.ativity_franchise_details_royalty_comission_format_one);
        format_salespartner_monthly_revenue_1 = (TextView) findViewById(R.id.ativity_franchise_details_monthly_sales_format_one);
        format_operating_profitmargin_1 = (TextView) findViewById(R.id.ativity_franchise_details_profit_margin_format_one);

        ////FORMAT 2
        format_name_2 = (TextView) findViewById(R.id.ativity_franchise_details_format_name_format_two);
        format_spaceneeded_minimum_2 = (TextView) findViewById(R.id.ativity_franchise_details_space_needed_from_format_two);
        format_spaceneeded_maximum_2 = (TextView) findViewById(R.id.ativity_franchise_details_space_needed_to_format_two);
        format_investment_needed_minimum_2 = (TextView) findViewById(R.id.ativity_franchise_details_investment_size_from_format_two);
        format_investment_needed_maximum_2 = (TextView) findViewById(R.id.ativity_franchise_details_investment_size_to_format_two);
        format_brand_fee_2 = (TextView) findViewById(R.id.ativity_franchise_details_brand_fee_format_two);
        format_staff_required_2 = (TextView) findViewById(R.id.ativity_franchise_details_staffs_format_two);
        format_royalty_commission_2 = (TextView) findViewById(R.id.ativity_franchise_details_royalty_comission_format_two);
        format_salespartner_monthly_revenue_2 = (TextView) findViewById(R.id.ativity_franchise_details_monthly_sales_format_two);
        format_operating_profitmargin_2 = (TextView) findViewById(R.id.ativity_franchise_details_profit_margin_format_two);

        ////FORMAT 3
        format_name_3 = (TextView) findViewById(R.id.ativity_franchise_details_format_name_format_three);
        format_spaceneeded_minimum_3 = (TextView) findViewById(R.id.ativity_franchise_details_space_needed_from_format_three);
        format_spaceneeded_maximum_3 = (TextView) findViewById(R.id.ativity_franchise_details_space_needed_to_format_three);
        format_investment_needed_minimum_3 = (TextView) findViewById(R.id.ativity_franchise_details_investment_size_from_format_three);
        format_investment_needed_maximum_3 = (TextView) findViewById(R.id.ativity_franchise_details_investment_size_to_format_three);
        format_brand_fee_3 = (TextView) findViewById(R.id.ativity_franchise_details_brand_fee_format_three);
        format_staff_required_3 = (TextView) findViewById(R.id.ativity_franchise_details_staffs_format_three);
        format_royalty_commission_3 = (TextView) findViewById(R.id.ativity_franchise_details_royalty_comission_format_three);
        format_salespartner_monthly_revenue_3 = (TextView) findViewById(R.id.ativity_franchise_details_monthly_sales_format_three);
        format_operating_profitmargin_3 = (TextView) findViewById(R.id.ativity_franchise_details_profit_margin_format_three);

        ////FORMAT 4
        format_name_4 = (TextView) findViewById(R.id.ativity_franchise_details_format_name_format_four);
        format_spaceneeded_minimum_4 = (TextView) findViewById(R.id.ativity_franchise_details_space_needed_from_format_four);
        format_spaceneeded_maximum_4 = (TextView) findViewById(R.id.ativity_franchise_details_space_needed_to_format_four);
        format_investment_needed_minimum_4 = (TextView) findViewById(R.id.ativity_franchise_details_investment_size_from_format_four);
        format_investment_needed_maximum_4 = (TextView) findViewById(R.id.ativity_franchise_details_investment_size_to_format_four);
        format_brand_fee_4 = (TextView) findViewById(R.id.ativity_franchise_details_brand_fee_format_four);
        format_staff_required_4 = (TextView) findViewById(R.id.ativity_franchise_details_staffs_format_four);
        format_royalty_commission_4 = (TextView) findViewById(R.id.ativity_franchise_details_royalty_comission_format_four);
        format_salespartner_monthly_revenue_4 = (TextView) findViewById(R.id.ativity_franchise_details_monthly_sales_format_four);
        format_operating_profitmargin_4 = (TextView) findViewById(R.id.ativity_franchise_details_profit_margin_format_four);

        ////FORMAT 5
        format_name_5 = (TextView) findViewById(R.id.ativity_franchise_details_format_name_format_five);
        format_spaceneeded_minimum_5 = (TextView) findViewById(R.id.ativity_franchise_details_space_needed_from_format_five);
        format_spaceneeded_maximum_5 = (TextView) findViewById(R.id.ativity_franchise_details_space_needed_to_format_five);
        format_investment_needed_minimum_5 = (TextView) findViewById(R.id.ativity_franchise_details_investment_size_from_format_five);
        format_investment_needed_maximum_5 = (TextView) findViewById(R.id.ativity_franchise_details_investment_size_to_format_five);
        format_brand_fee_5 = (TextView) findViewById(R.id.ativity_franchise_details_brand_fee_format_five);
        format_staff_required_5 = (TextView) findViewById(R.id.ativity_franchise_details_staffs_format_five);
        format_royalty_commission_5 = (TextView) findViewById(R.id.ativity_franchise_details_royalty_comission_format_five);
        format_salespartner_monthly_revenue_5 = (TextView) findViewById(R.id.ativity_franchise_details_monthly_sales_format_five);
        format_operating_profitmargin_5 = (TextView) findViewById(R.id.ativity_franchise_details_profit_margin_format_five);

        ////FORMAT 6
        format_name_6 = (TextView) findViewById(R.id.ativity_franchise_details_format_name_format_six);
        format_spaceneeded_minimum_6 = (TextView) findViewById(R.id.ativity_franchise_details_space_needed_from_format_six);
        format_spaceneeded_maximum_6 = (TextView) findViewById(R.id.ativity_franchise_details_space_needed_to_format_six);
        format_investment_needed_minimum_6 = (TextView) findViewById(R.id.ativity_franchise_details_investment_size_from_format_six);
        format_investment_needed_maximum_6 = (TextView) findViewById(R.id.ativity_franchise_details_investment_size_to_format_six);
        format_brand_fee_6 = (TextView) findViewById(R.id.ativity_franchise_details_brand_fee_format_six);
        format_staff_required_6 = (TextView) findViewById(R.id.ativity_franchise_details_staffs_format_six);
        format_royalty_commission_6 = (TextView) findViewById(R.id.ativity_franchise_details_royalty_comission_format_six);
        format_salespartner_monthly_revenue_6 = (TextView) findViewById(R.id.ativity_franchise_details_monthly_sales_format_six);
        format_operating_profitmargin_6 = (TextView) findViewById(R.id.ativity_franchise_details_profit_margin_format_six);


        img_franchise_logo = (ImageView) findViewById(R.id.ativity_franchise_details_image_view);

        btn_contact_business = (Button) findViewById(R.id.btn_ativity_franchise_details_contact_business);

        btn_contact_business.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                TastyToast.makeText(getApplicationContext(), "Button Clicked...!", TastyToast.LENGTH_LONG, TastyToast.SUCCESS);

            }

        });

        try {
            dialog = new SpotsDialog(Activity_DetailedView_Franchise.this);
            dialog.show();
            queue = Volley.newRequestQueue(getApplicationContext());
            Get_Details();

        } catch (Exception e) {
            // TODO: handle exception
        }
    }

    /*****************************
     * GET Details
     ***************************/

    public void Get_Details() {

        StringRequest request = new StringRequest(Request.Method.POST,
                AppConfig.url_dashboard_search_result_franchise_profile_detail, new Response.Listener<String>() {
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
                        JSONArray arr_industry;
                        JSONArray arr_formats;


                        arr_main = obj.getJSONArray("data");

                        for (int i = 0; arr_main.length() > i; i++) {

                            JSONObject obj_data = arr_main.getJSONObject(i);

                            String franchise_id = obj_data.getString(TAG_FRANCHISE_ID);
                            String franchise_key = obj_data.getString(TAG_FRANCHISE_KEY);
                            String franchise_user_name = obj_data.getString(TAG_FRANCHISE_USER_NAME);
                            String franchise_email = obj_data.getString(TAG_FRANCHISE_EMAIL);
                            String franchise_mobile = obj_data.getString(TAG_FRANCHISE_MOBILE);
                            String franchise_designation = obj_data.getString(TAG_FRANCHISE_DESIGNATION);
                            String franchise_brand_name = obj_data.getString(TAG_FRANCHISE_BRAND_NAME);
                            String franchise_brand_offering = obj_data.getString(TAG_FRANCHISE_BRAND_OFFERING);
                            String franchise_brand_company = obj_data.getString(TAG_FRANCHISE_BRAND_COMPANY);
                            String franchise_brand_services = obj_data.getString(TAG_FRANCHISE_BRAND_SERVICES);
                            String franchise_brand_established = obj_data.getString(TAG_FRANCHISE_BRAND_ESTABLISHED);
                            String franchise_brand_headquaters = obj_data.getString(TAG_FRANCHISE_BRAND_HEADQUATERS);
                            String brand_salepartner_count = obj_data.getString(TAG_BRAND_SALEPARTNER_COUNT);
                            String brand_salepartner_before_partnering = obj_data.getString(TAG_BRAND_SALEPARTNER_BEFORE_PARTNERING);
                            String brand_salepartner_expect = obj_data.getString(TAG_BRAND_SALEPARTNER_EXPECT);
                            String brand_salepartner_procedure = obj_data.getString(TAG_BRAND_SALEPARTNER_PROCEDURE);
                            String franchise_format = obj_data.getString(TAG_FRANCHISE_FORMAT);
                            String franchise_logo = obj_data.getString(TAG_FRANCHISE_LOGO);
                            String franchise_currency = obj_data.getString(TAG_FRANCHISE_CURRENCY);
                            String country_currency = obj_data.getString(TAG_COUNTRY_CURRENCY);

                            arr_location = obj_data.getJSONArray("location");
                            Arraylist_update_location.clear();
                            for (int j = 0; arr_location.length() > j; j++) {
                                JSONObject obj_location = arr_location.getJSONObject(j);

                                String location_name = obj_location.getString(TAG_LOCATION_NAME);
                                String location_key = obj_location.getString(TAG_LOCATION_KEY);

                                Arraylist_update_location.add(location_name);

                            }
                            str_final_location = TextUtils.join(", ", Arraylist_update_location);

                            System.out.println("LOCATION ::: " + str_final_location);

                            arr_industry = obj_data.getJSONArray("industry");
                            Arraylist_update_industries.clear();
                            for (int k = 0; arr_industry.length() > k; k++) {
                                JSONObject obj_industry = arr_industry.getJSONObject(k);

                                String industry_name = obj_industry.getString(TAG_INDUSTRY_NAME);
                                String industry_key = obj_industry.getString(TAG_INDUSTRY_KEY);

                                Arraylist_update_industries.add(industry_name);

                            }
                            str_final_industries = TextUtils.join(", ", Arraylist_update_industries);

                            try {

                                Glide.with(getApplicationContext())
                                        .load(franchise_logo)
                                        .placeholder(R.drawable.placeholder)
                                        .into(img_franchise_logo);

                                txt_brand_name.setText("" + franchise_brand_name);
                                txt_about_brand.setText("" + franchise_brand_company);
                                txt_prod_serv_overview.setText("" + franchise_brand_services);
                                txt_preferred_locations.setText("" + str_final_location + ", ");
                                txt_how_to_own.setText("" + brand_salepartner_procedure);
                                txt_support.setText("" + brand_salepartner_expect);

                                //Setting selection for spinner - (OPPORTUNITIES OFFERED)
                                if (franchise_brand_offering.equals("1")) {

                                    txt_brand_offering.setText("Franchise Opportunity");

                                } else if (franchise_brand_offering.equals("2")) {

                                    txt_brand_offering.setText("Dealership Opportunity");

                                } else if (franchise_brand_offering.equals("3")) {

                                    txt_brand_offering.setText("Reseller Opportunity");

                                } else if (franchise_brand_offering.equals("4")) {

                                    txt_brand_offering.setText("Distributor Opportunity");

                                } else if (franchise_brand_offering.equals("5")) {

                                    txt_brand_offering.setText("Sales Opportunity");

                                }

                                if (franchise_format.equals("1")) {

                                    cv_format_one.setVisibility(View.VISIBLE);
                                    cv_format_two.setVisibility(View.GONE);
                                    cv_format_three.setVisibility(View.GONE);
                                    cv_format_four.setVisibility(View.GONE);
                                    cv_format_five.setVisibility(View.GONE);
                                    cv_format_six.setVisibility(View.GONE);


                                } else if (franchise_format.equals("2")) {

                                    cv_format_one.setVisibility(View.VISIBLE);
                                    cv_format_two.setVisibility(View.VISIBLE);

                                    cv_format_three.setVisibility(View.GONE);
                                    cv_format_four.setVisibility(View.GONE);
                                    cv_format_five.setVisibility(View.GONE);
                                    cv_format_six.setVisibility(View.GONE);

                                } else if (franchise_format.equals("3")) {

                                    cv_format_one.setVisibility(View.VISIBLE);
                                    cv_format_two.setVisibility(View.VISIBLE);
                                    cv_format_three.setVisibility(View.VISIBLE);

                                    cv_format_four.setVisibility(View.GONE);
                                    cv_format_five.setVisibility(View.GONE);
                                    cv_format_six.setVisibility(View.GONE);

                                } else if (franchise_format.equals("4")) {

                                    cv_format_one.setVisibility(View.VISIBLE);
                                    cv_format_two.setVisibility(View.VISIBLE);
                                    cv_format_three.setVisibility(View.VISIBLE);
                                    cv_format_four.setVisibility(View.VISIBLE);

                                    cv_format_five.setVisibility(View.GONE);
                                    cv_format_six.setVisibility(View.GONE);

                                } else if (franchise_format.equals("5")) {

                                    cv_format_one.setVisibility(View.VISIBLE);
                                    cv_format_two.setVisibility(View.VISIBLE);
                                    cv_format_three.setVisibility(View.VISIBLE);
                                    cv_format_four.setVisibility(View.VISIBLE);
                                    cv_format_five.setVisibility(View.VISIBLE);

                                    cv_format_six.setVisibility(View.GONE);

                                } else if (franchise_format.equals("6")) {

                                    cv_format_one.setVisibility(View.VISIBLE);
                                    cv_format_two.setVisibility(View.VISIBLE);
                                    cv_format_three.setVisibility(View.VISIBLE);
                                    cv_format_four.setVisibility(View.VISIBLE);
                                    cv_format_five.setVisibility(View.VISIBLE);
                                    cv_format_six.setVisibility(View.VISIBLE);

                                }


                                //GETTING FORMATS AND THERIR VALUES AND SETTING IT IN STRINGS
                                arr_formats = obj_data.getJSONArray("format");
                                System.out.println("INSIDE FORMAT ARRAYYYYY" + arr_formats);
                                if (franchise_format.equals("1")) {

                                    JSONObject obj_format1 = arr_formats.getJSONObject(0);

                                    String franchise_id1 = obj_format1.getString(TAG_FRANCHISE_ID1);
                                    String franchise_format_name1 = obj_format1.getString(TAG_FRANCHISE_FORMAT_NAME1);
                                    String franchise_format_min_investment1 = obj_format1.getString(TAG_FRANCHISE_FORMAT_MIN_INVESTMENT1);
                                    String franchise_format_max_investment1 = obj_format1.getString(TAG_FRANCHISE_FORMAT_MAX_INVESTMENT1);
                                    String franchise_format_fee1 = obj_format1.getString(TAG_FRANCHISE_FORMAT_FEE1);
                                    String franchise_format_no_of_staffs1 = obj_format1.getString(TAG_FRANCHISE_FORMAT_NO_OF_STAFFS1);
                                    String franchise_format_royality1 = obj_format1.getString(TAG_FRANCHISE_FORMAT_ROYALITY1);
                                    String franchise_format_revenue1 = obj_format1.getString(TAG_FRANCHISE_FORMAT_REVENUE1);
                                    String franchise_format_profit1 = obj_format1.getString(TAG_FRANCHISE_FORMAT_PROFIT1);
                                    String franchise_format_act1 = obj_format1.getString(TAG_FRANCHISE_FORMAT_ACT1);
                                    String franchise_format_min_sqft1 = obj_format1.getString(TAG_FRANCHISE_FORMAT_MIN_SQFT1);
                                    String franchise_format_max_sqft1 = obj_format1.getString(TAG_FRANCHISE_FORMAT_MAX_SQFT1);


                                    format_name_1.setText("" + franchise_format_name1);
                                    format_spaceneeded_minimum_1.setText("" + franchise_format_min_sqft1);
                                    format_spaceneeded_maximum_1.setText("" + franchise_format_max_sqft1);
                                    format_investment_needed_minimum_1.setText("" + franchise_format_min_investment1);
                                    format_investment_needed_maximum_1.setText("" + franchise_format_max_investment1);
                                    format_brand_fee_1.setText("" + franchise_format_fee1);
                                    format_staff_required_1.setText("" + franchise_format_no_of_staffs1);
                                    format_royalty_commission_1.setText("" + franchise_format_royality1);
                                    format_salespartner_monthly_revenue_1.setText("" + franchise_format_revenue1);
                                    format_operating_profitmargin_1.setText("" + franchise_format_profit1);


                                } else if (franchise_format.equals("2")) {

                                    JSONObject obj_format1 = arr_formats.getJSONObject(0);

                                    String franchise_id1 = obj_format1.getString(TAG_FRANCHISE_ID1);
                                    String franchise_format_name1 = obj_format1.getString(TAG_FRANCHISE_FORMAT_NAME1);
                                    String franchise_format_min_investment1 = obj_format1.getString(TAG_FRANCHISE_FORMAT_MIN_INVESTMENT1);
                                    String franchise_format_max_investment1 = obj_format1.getString(TAG_FRANCHISE_FORMAT_MAX_INVESTMENT1);
                                    String franchise_format_fee1 = obj_format1.getString(TAG_FRANCHISE_FORMAT_FEE1);
                                    String franchise_format_no_of_staffs1 = obj_format1.getString(TAG_FRANCHISE_FORMAT_NO_OF_STAFFS1);
                                    String franchise_format_royality1 = obj_format1.getString(TAG_FRANCHISE_FORMAT_ROYALITY1);
                                    String franchise_format_revenue1 = obj_format1.getString(TAG_FRANCHISE_FORMAT_REVENUE1);
                                    String franchise_format_profit1 = obj_format1.getString(TAG_FRANCHISE_FORMAT_PROFIT1);
                                    String franchise_format_act1 = obj_format1.getString(TAG_FRANCHISE_FORMAT_ACT1);
                                    String franchise_format_min_sqft1 = obj_format1.getString(TAG_FRANCHISE_FORMAT_MIN_SQFT1);
                                    String franchise_format_max_sqft1 = obj_format1.getString(TAG_FRANCHISE_FORMAT_MAX_SQFT1);


                                    format_name_1.setText("" + franchise_format_name1);
                                    format_spaceneeded_minimum_1.setText("" + franchise_format_min_sqft1);
                                    format_spaceneeded_maximum_1.setText("" + franchise_format_max_sqft1);
                                    format_investment_needed_minimum_1.setText("" + franchise_format_min_investment1);
                                    format_investment_needed_maximum_1.setText("" + franchise_format_max_investment1);
                                    format_brand_fee_1.setText("" + franchise_format_fee1);
                                    format_staff_required_1.setText("" + franchise_format_no_of_staffs1);
                                    format_royalty_commission_1.setText("" + franchise_format_royality1);
                                    format_salespartner_monthly_revenue_1.setText("" + franchise_format_revenue1);
                                    format_operating_profitmargin_1.setText("" + franchise_format_profit1);

                                    JSONObject obj_format2 = arr_formats.getJSONObject(1);

                                    String franchise_id2 = obj_format2.getString(TAG_FRANCHISE_ID2);
                                    String franchise_format_name2 = obj_format2.getString(TAG_FRANCHISE_FORMAT_NAME2);
                                    String franchise_format_min_investment2 = obj_format2.getString(TAG_FRANCHISE_FORMAT_MIN_INVESTMENT2);
                                    String franchise_format_max_investment2 = obj_format2.getString(TAG_FRANCHISE_FORMAT_MAX_INVESTMENT2);
                                    String franchise_format_fee2 = obj_format2.getString(TAG_FRANCHISE_FORMAT_FEE2);
                                    String franchise_format_no_of_staffs2 = obj_format2.getString(TAG_FRANCHISE_FORMAT_NO_OF_STAFFS2);
                                    String franchise_format_royality2 = obj_format2.getString(TAG_FRANCHISE_FORMAT_ROYALITY2);
                                    String franchise_format_revenue2 = obj_format2.getString(TAG_FRANCHISE_FORMAT_REVENUE2);
                                    String franchise_format_profit2 = obj_format2.getString(TAG_FRANCHISE_FORMAT_PROFIT2);
                                    String franchise_format_act2 = obj_format2.getString(TAG_FRANCHISE_FORMAT_ACT2);
                                    String franchise_format_min_sqft2 = obj_format2.getString(TAG_FRANCHISE_FORMAT_MIN_SQFT2);
                                    String franchise_format_max_sqft2 = obj_format2.getString(TAG_FRANCHISE_FORMAT_MAX_SQFT2);

                                    format_name_2.setText("" + franchise_format_name2);
                                    format_spaceneeded_minimum_2.setText("" + franchise_format_min_sqft2);
                                    format_spaceneeded_maximum_2.setText("" + franchise_format_max_sqft2);
                                    format_investment_needed_minimum_2.setText("" + franchise_format_min_investment2);
                                    format_investment_needed_maximum_2.setText("" + franchise_format_max_investment2);
                                    format_brand_fee_2.setText("" + franchise_format_fee2);
                                    format_staff_required_2.setText("" + franchise_format_no_of_staffs2);
                                    format_royalty_commission_2.setText("" + franchise_format_royality2);
                                    format_salespartner_monthly_revenue_2.setText("" + franchise_format_revenue2);
                                    format_operating_profitmargin_2.setText("" + franchise_format_profit2);


                                } else if (franchise_format.equals("3")) {

                                    JSONObject obj_format1 = arr_formats.getJSONObject(0);

                                    String franchise_id1 = obj_format1.getString(TAG_FRANCHISE_ID1);
                                    String franchise_format_name1 = obj_format1.getString(TAG_FRANCHISE_FORMAT_NAME1);
                                    String franchise_format_min_investment1 = obj_format1.getString(TAG_FRANCHISE_FORMAT_MIN_INVESTMENT1);
                                    String franchise_format_max_investment1 = obj_format1.getString(TAG_FRANCHISE_FORMAT_MAX_INVESTMENT1);
                                    String franchise_format_fee1 = obj_format1.getString(TAG_FRANCHISE_FORMAT_FEE1);
                                    String franchise_format_no_of_staffs1 = obj_format1.getString(TAG_FRANCHISE_FORMAT_NO_OF_STAFFS1);
                                    String franchise_format_royality1 = obj_format1.getString(TAG_FRANCHISE_FORMAT_ROYALITY1);
                                    String franchise_format_revenue1 = obj_format1.getString(TAG_FRANCHISE_FORMAT_REVENUE1);
                                    String franchise_format_profit1 = obj_format1.getString(TAG_FRANCHISE_FORMAT_PROFIT1);
                                    String franchise_format_act1 = obj_format1.getString(TAG_FRANCHISE_FORMAT_ACT1);
                                    String franchise_format_min_sqft1 = obj_format1.getString(TAG_FRANCHISE_FORMAT_MIN_SQFT1);
                                    String franchise_format_max_sqft1 = obj_format1.getString(TAG_FRANCHISE_FORMAT_MAX_SQFT1);


                                    format_name_1.setText("" + franchise_format_name1);
                                    format_spaceneeded_minimum_1.setText("" + franchise_format_min_sqft1);
                                    format_spaceneeded_maximum_1.setText("" + franchise_format_max_sqft1);
                                    format_investment_needed_minimum_1.setText("" + franchise_format_min_investment1);
                                    format_investment_needed_maximum_1.setText("" + franchise_format_max_investment1);
                                    format_brand_fee_1.setText("" + franchise_format_fee1);
                                    format_staff_required_1.setText("" + franchise_format_no_of_staffs1);
                                    format_royalty_commission_1.setText("" + franchise_format_royality1);
                                    format_salespartner_monthly_revenue_1.setText("" + franchise_format_revenue1);
                                    format_operating_profitmargin_1.setText("" + franchise_format_profit1);

                                    JSONObject obj_format2 = arr_formats.getJSONObject(1);

                                    String franchise_id2 = obj_format2.getString(TAG_FRANCHISE_ID2);
                                    String franchise_format_name2 = obj_format2.getString(TAG_FRANCHISE_FORMAT_NAME2);
                                    String franchise_format_min_investment2 = obj_format2.getString(TAG_FRANCHISE_FORMAT_MIN_INVESTMENT2);
                                    String franchise_format_max_investment2 = obj_format2.getString(TAG_FRANCHISE_FORMAT_MAX_INVESTMENT2);
                                    String franchise_format_fee2 = obj_format2.getString(TAG_FRANCHISE_FORMAT_FEE2);
                                    String franchise_format_no_of_staffs2 = obj_format2.getString(TAG_FRANCHISE_FORMAT_NO_OF_STAFFS2);
                                    String franchise_format_royality2 = obj_format2.getString(TAG_FRANCHISE_FORMAT_ROYALITY2);
                                    String franchise_format_revenue2 = obj_format2.getString(TAG_FRANCHISE_FORMAT_REVENUE2);
                                    String franchise_format_profit2 = obj_format2.getString(TAG_FRANCHISE_FORMAT_PROFIT2);
                                    String franchise_format_act2 = obj_format2.getString(TAG_FRANCHISE_FORMAT_ACT2);
                                    String franchise_format_min_sqft2 = obj_format2.getString(TAG_FRANCHISE_FORMAT_MIN_SQFT2);
                                    String franchise_format_max_sqft2 = obj_format2.getString(TAG_FRANCHISE_FORMAT_MAX_SQFT2);

                                    format_name_2.setText("" + franchise_format_name2);
                                    format_spaceneeded_minimum_2.setText("" + franchise_format_min_sqft2);
                                    format_spaceneeded_maximum_2.setText("" + franchise_format_max_sqft2);
                                    format_investment_needed_minimum_2.setText("" + franchise_format_min_investment2);
                                    format_investment_needed_maximum_2.setText("" + franchise_format_max_investment2);
                                    format_brand_fee_2.setText("" + franchise_format_fee2);
                                    format_staff_required_2.setText("" + franchise_format_no_of_staffs2);
                                    format_royalty_commission_2.setText("" + franchise_format_royality2);
                                    format_salespartner_monthly_revenue_2.setText("" + franchise_format_revenue2);
                                    format_operating_profitmargin_2.setText("" + franchise_format_profit2);


                                    JSONObject obj_format3 = arr_formats.getJSONObject(2);

                                    String franchise_id3 = obj_format3.getString(TAG_FRANCHISE_ID3);
                                    String franchise_format_name3 = obj_format3.getString(TAG_FRANCHISE_FORMAT_NAME3);
                                    String franchise_format_min_investment3 = obj_format3.getString(TAG_FRANCHISE_FORMAT_MIN_INVESTMENT3);
                                    String franchise_format_max_investment3 = obj_format3.getString(TAG_FRANCHISE_FORMAT_MAX_INVESTMENT3);
                                    String franchise_format_fee3 = obj_format3.getString(TAG_FRANCHISE_FORMAT_FEE3);
                                    String franchise_format_no_of_staffs3 = obj_format3.getString(TAG_FRANCHISE_FORMAT_NO_OF_STAFFS3);
                                    String franchise_format_royality3 = obj_format3.getString(TAG_FRANCHISE_FORMAT_ROYALITY3);
                                    String franchise_format_revenue3 = obj_format3.getString(TAG_FRANCHISE_FORMAT_REVENUE3);
                                    String franchise_format_profit3 = obj_format3.getString(TAG_FRANCHISE_FORMAT_PROFIT3);
                                    String franchise_format_act3 = obj_format3.getString(TAG_FRANCHISE_FORMAT_ACT3);
                                    String franchise_format_min_sqft3 = obj_format3.getString(TAG_FRANCHISE_FORMAT_MIN_SQFT3);
                                    String franchise_format_max_sqft3 = obj_format3.getString(TAG_FRANCHISE_FORMAT_MAX_SQFT3);

                                    format_name_3.setText("" + franchise_format_name3);
                                    format_spaceneeded_minimum_3.setText("" + franchise_format_min_sqft3);
                                    format_spaceneeded_maximum_3.setText("" + franchise_format_max_sqft3);
                                    format_investment_needed_minimum_3.setText("" + franchise_format_min_investment3);
                                    format_investment_needed_maximum_3.setText("" + franchise_format_max_investment3);
                                    format_brand_fee_3.setText("" + franchise_format_fee3);
                                    format_staff_required_3.setText("" + franchise_format_no_of_staffs3);
                                    format_royalty_commission_3.setText("" + franchise_format_royality3);
                                    format_salespartner_monthly_revenue_3.setText("" + franchise_format_revenue3);
                                    format_operating_profitmargin_3.setText("" + franchise_format_profit3);

                                } else if (franchise_format.equals("4")) {

                                    JSONObject obj_format1 = arr_formats.getJSONObject(0);

                                    String franchise_id1 = obj_format1.getString(TAG_FRANCHISE_ID1);
                                    String franchise_format_name1 = obj_format1.getString(TAG_FRANCHISE_FORMAT_NAME1);
                                    String franchise_format_min_investment1 = obj_format1.getString(TAG_FRANCHISE_FORMAT_MIN_INVESTMENT1);
                                    String franchise_format_max_investment1 = obj_format1.getString(TAG_FRANCHISE_FORMAT_MAX_INVESTMENT1);
                                    String franchise_format_fee1 = obj_format1.getString(TAG_FRANCHISE_FORMAT_FEE1);
                                    String franchise_format_no_of_staffs1 = obj_format1.getString(TAG_FRANCHISE_FORMAT_NO_OF_STAFFS1);
                                    String franchise_format_royality1 = obj_format1.getString(TAG_FRANCHISE_FORMAT_ROYALITY1);
                                    String franchise_format_revenue1 = obj_format1.getString(TAG_FRANCHISE_FORMAT_REVENUE1);
                                    String franchise_format_profit1 = obj_format1.getString(TAG_FRANCHISE_FORMAT_PROFIT1);
                                    String franchise_format_act1 = obj_format1.getString(TAG_FRANCHISE_FORMAT_ACT1);
                                    String franchise_format_min_sqft1 = obj_format1.getString(TAG_FRANCHISE_FORMAT_MIN_SQFT1);
                                    String franchise_format_max_sqft1 = obj_format1.getString(TAG_FRANCHISE_FORMAT_MAX_SQFT1);


                                    format_name_1.setText("" + franchise_format_name1);
                                    format_spaceneeded_minimum_1.setText("" + franchise_format_min_sqft1);
                                    format_spaceneeded_maximum_1.setText("" + franchise_format_max_sqft1);
                                    format_investment_needed_minimum_1.setText("" + franchise_format_min_investment1);
                                    format_investment_needed_maximum_1.setText("" + franchise_format_max_investment1);
                                    format_brand_fee_1.setText("" + franchise_format_fee1);
                                    format_staff_required_1.setText("" + franchise_format_no_of_staffs1);
                                    format_royalty_commission_1.setText("" + franchise_format_royality1);
                                    format_salespartner_monthly_revenue_1.setText("" + franchise_format_revenue1);
                                    format_operating_profitmargin_1.setText("" + franchise_format_profit1);

                                    JSONObject obj_format2 = arr_formats.getJSONObject(1);

                                    String franchise_id2 = obj_format2.getString(TAG_FRANCHISE_ID2);
                                    String franchise_format_name2 = obj_format2.getString(TAG_FRANCHISE_FORMAT_NAME2);
                                    String franchise_format_min_investment2 = obj_format2.getString(TAG_FRANCHISE_FORMAT_MIN_INVESTMENT2);
                                    String franchise_format_max_investment2 = obj_format2.getString(TAG_FRANCHISE_FORMAT_MAX_INVESTMENT2);
                                    String franchise_format_fee2 = obj_format2.getString(TAG_FRANCHISE_FORMAT_FEE2);
                                    String franchise_format_no_of_staffs2 = obj_format2.getString(TAG_FRANCHISE_FORMAT_NO_OF_STAFFS2);
                                    String franchise_format_royality2 = obj_format2.getString(TAG_FRANCHISE_FORMAT_ROYALITY2);
                                    String franchise_format_revenue2 = obj_format2.getString(TAG_FRANCHISE_FORMAT_REVENUE2);
                                    String franchise_format_profit2 = obj_format2.getString(TAG_FRANCHISE_FORMAT_PROFIT2);
                                    String franchise_format_act2 = obj_format2.getString(TAG_FRANCHISE_FORMAT_ACT2);
                                    String franchise_format_min_sqft2 = obj_format2.getString(TAG_FRANCHISE_FORMAT_MIN_SQFT2);
                                    String franchise_format_max_sqft2 = obj_format2.getString(TAG_FRANCHISE_FORMAT_MAX_SQFT2);

                                    format_name_2.setText("" + franchise_format_name2);
                                    format_spaceneeded_minimum_2.setText("" + franchise_format_min_sqft2);
                                    format_spaceneeded_maximum_2.setText("" + franchise_format_max_sqft2);
                                    format_investment_needed_minimum_2.setText("" + franchise_format_min_investment2);
                                    format_investment_needed_maximum_2.setText("" + franchise_format_max_investment2);
                                    format_brand_fee_2.setText("" + franchise_format_fee2);
                                    format_staff_required_2.setText("" + franchise_format_no_of_staffs2);
                                    format_royalty_commission_2.setText("" + franchise_format_royality2);
                                    format_salespartner_monthly_revenue_2.setText("" + franchise_format_revenue2);
                                    format_operating_profitmargin_2.setText("" + franchise_format_profit2);


                                    JSONObject obj_format3 = arr_formats.getJSONObject(2);

                                    String franchise_id3 = obj_format3.getString(TAG_FRANCHISE_ID3);
                                    String franchise_format_name3 = obj_format3.getString(TAG_FRANCHISE_FORMAT_NAME3);
                                    String franchise_format_min_investment3 = obj_format3.getString(TAG_FRANCHISE_FORMAT_MIN_INVESTMENT3);
                                    String franchise_format_max_investment3 = obj_format3.getString(TAG_FRANCHISE_FORMAT_MAX_INVESTMENT3);
                                    String franchise_format_fee3 = obj_format3.getString(TAG_FRANCHISE_FORMAT_FEE3);
                                    String franchise_format_no_of_staffs3 = obj_format3.getString(TAG_FRANCHISE_FORMAT_NO_OF_STAFFS3);
                                    String franchise_format_royality3 = obj_format3.getString(TAG_FRANCHISE_FORMAT_ROYALITY3);
                                    String franchise_format_revenue3 = obj_format3.getString(TAG_FRANCHISE_FORMAT_REVENUE3);
                                    String franchise_format_profit3 = obj_format3.getString(TAG_FRANCHISE_FORMAT_PROFIT3);
                                    String franchise_format_act3 = obj_format3.getString(TAG_FRANCHISE_FORMAT_ACT3);
                                    String franchise_format_min_sqft3 = obj_format3.getString(TAG_FRANCHISE_FORMAT_MIN_SQFT3);
                                    String franchise_format_max_sqft3 = obj_format3.getString(TAG_FRANCHISE_FORMAT_MAX_SQFT3);

                                    format_name_3.setText("" + franchise_format_name3);
                                    format_spaceneeded_minimum_3.setText("" + franchise_format_min_sqft3);
                                    format_spaceneeded_maximum_3.setText("" + franchise_format_max_sqft3);
                                    format_investment_needed_minimum_3.setText("" + franchise_format_min_investment3);
                                    format_investment_needed_maximum_3.setText("" + franchise_format_max_investment3);
                                    format_brand_fee_3.setText("" + franchise_format_fee3);
                                    format_staff_required_3.setText("" + franchise_format_no_of_staffs3);
                                    format_royalty_commission_3.setText("" + franchise_format_royality3);
                                    format_salespartner_monthly_revenue_3.setText("" + franchise_format_revenue3);
                                    format_operating_profitmargin_3.setText("" + franchise_format_profit3);

                                    JSONObject obj_format4 = arr_formats.getJSONObject(3);

                                    String franchise_id4 = obj_format4.getString(TAG_FRANCHISE_ID4);
                                    String franchise_format_name4 = obj_format4.getString(TAG_FRANCHISE_FORMAT_NAME4);
                                    String franchise_format_min_investment4 = obj_format4.getString(TAG_FRANCHISE_FORMAT_MIN_INVESTMENT4);
                                    String franchise_format_max_investment4 = obj_format4.getString(TAG_FRANCHISE_FORMAT_MAX_INVESTMENT4);
                                    String franchise_format_fee4 = obj_format4.getString(TAG_FRANCHISE_FORMAT_FEE4);
                                    String franchise_format_no_of_staffs4 = obj_format4.getString(TAG_FRANCHISE_FORMAT_NO_OF_STAFFS4);
                                    String franchise_format_royality4 = obj_format4.getString(TAG_FRANCHISE_FORMAT_ROYALITY4);
                                    String franchise_format_revenue4 = obj_format4.getString(TAG_FRANCHISE_FORMAT_REVENUE4);
                                    String franchise_format_profit4 = obj_format4.getString(TAG_FRANCHISE_FORMAT_PROFIT4);
                                    String franchise_format_act4 = obj_format4.getString(TAG_FRANCHISE_FORMAT_ACT4);
                                    String franchise_format_min_sqft4 = obj_format4.getString(TAG_FRANCHISE_FORMAT_MIN_SQFT4);
                                    String franchise_format_max_sqft4 = obj_format4.getString(TAG_FRANCHISE_FORMAT_MAX_SQFT4);

                                    format_name_4.setText("" + franchise_format_name4);
                                    format_spaceneeded_minimum_4.setText("" + franchise_format_min_sqft4);
                                    format_spaceneeded_maximum_4.setText("" + franchise_format_max_sqft4);
                                    format_investment_needed_minimum_4.setText("" + franchise_format_min_investment4);
                                    format_investment_needed_maximum_4.setText("" + franchise_format_max_investment4);
                                    format_brand_fee_4.setText("" + franchise_format_fee4);
                                    format_staff_required_4.setText("" + franchise_format_no_of_staffs4);
                                    format_royalty_commission_4.setText("" + franchise_format_royality4);
                                    format_salespartner_monthly_revenue_4.setText("" + franchise_format_revenue4);
                                    format_operating_profitmargin_4.setText("" + franchise_format_profit4);

                                } else if (franchise_format.equals("5")) {

                                    JSONObject obj_format1 = arr_formats.getJSONObject(0);

                                    String franchise_id1 = obj_format1.getString(TAG_FRANCHISE_ID1);
                                    String franchise_format_name1 = obj_format1.getString(TAG_FRANCHISE_FORMAT_NAME1);
                                    String franchise_format_min_investment1 = obj_format1.getString(TAG_FRANCHISE_FORMAT_MIN_INVESTMENT1);
                                    String franchise_format_max_investment1 = obj_format1.getString(TAG_FRANCHISE_FORMAT_MAX_INVESTMENT1);
                                    String franchise_format_fee1 = obj_format1.getString(TAG_FRANCHISE_FORMAT_FEE1);
                                    String franchise_format_no_of_staffs1 = obj_format1.getString(TAG_FRANCHISE_FORMAT_NO_OF_STAFFS1);
                                    String franchise_format_royality1 = obj_format1.getString(TAG_FRANCHISE_FORMAT_ROYALITY1);
                                    String franchise_format_revenue1 = obj_format1.getString(TAG_FRANCHISE_FORMAT_REVENUE1);
                                    String franchise_format_profit1 = obj_format1.getString(TAG_FRANCHISE_FORMAT_PROFIT1);
                                    String franchise_format_act1 = obj_format1.getString(TAG_FRANCHISE_FORMAT_ACT1);
                                    String franchise_format_min_sqft1 = obj_format1.getString(TAG_FRANCHISE_FORMAT_MIN_SQFT1);
                                    String franchise_format_max_sqft1 = obj_format1.getString(TAG_FRANCHISE_FORMAT_MAX_SQFT1);


                                    format_name_1.setText("" + franchise_format_name1);
                                    format_spaceneeded_minimum_1.setText("" + franchise_format_min_sqft1);
                                    format_spaceneeded_maximum_1.setText("" + franchise_format_max_sqft1);
                                    format_investment_needed_minimum_1.setText("" + franchise_format_min_investment1);
                                    format_investment_needed_maximum_1.setText("" + franchise_format_max_investment1);
                                    format_brand_fee_1.setText("" + franchise_format_fee1);
                                    format_staff_required_1.setText("" + franchise_format_no_of_staffs1);
                                    format_royalty_commission_1.setText("" + franchise_format_royality1);
                                    format_salespartner_monthly_revenue_1.setText("" + franchise_format_revenue1);
                                    format_operating_profitmargin_1.setText("" + franchise_format_profit1);

                                    JSONObject obj_format2 = arr_formats.getJSONObject(1);

                                    String franchise_id2 = obj_format2.getString(TAG_FRANCHISE_ID2);
                                    String franchise_format_name2 = obj_format2.getString(TAG_FRANCHISE_FORMAT_NAME2);
                                    String franchise_format_min_investment2 = obj_format2.getString(TAG_FRANCHISE_FORMAT_MIN_INVESTMENT2);
                                    String franchise_format_max_investment2 = obj_format2.getString(TAG_FRANCHISE_FORMAT_MAX_INVESTMENT2);
                                    String franchise_format_fee2 = obj_format2.getString(TAG_FRANCHISE_FORMAT_FEE2);
                                    String franchise_format_no_of_staffs2 = obj_format2.getString(TAG_FRANCHISE_FORMAT_NO_OF_STAFFS2);
                                    String franchise_format_royality2 = obj_format2.getString(TAG_FRANCHISE_FORMAT_ROYALITY2);
                                    String franchise_format_revenue2 = obj_format2.getString(TAG_FRANCHISE_FORMAT_REVENUE2);
                                    String franchise_format_profit2 = obj_format2.getString(TAG_FRANCHISE_FORMAT_PROFIT2);
                                    String franchise_format_act2 = obj_format2.getString(TAG_FRANCHISE_FORMAT_ACT2);
                                    String franchise_format_min_sqft2 = obj_format2.getString(TAG_FRANCHISE_FORMAT_MIN_SQFT2);
                                    String franchise_format_max_sqft2 = obj_format2.getString(TAG_FRANCHISE_FORMAT_MAX_SQFT2);

                                    format_name_2.setText("" + franchise_format_name2);
                                    format_spaceneeded_minimum_2.setText("" + franchise_format_min_sqft2);
                                    format_spaceneeded_maximum_2.setText("" + franchise_format_max_sqft2);
                                    format_investment_needed_minimum_2.setText("" + franchise_format_min_investment2);
                                    format_investment_needed_maximum_2.setText("" + franchise_format_max_investment2);
                                    format_brand_fee_2.setText("" + franchise_format_fee2);
                                    format_staff_required_2.setText("" + franchise_format_no_of_staffs2);
                                    format_royalty_commission_2.setText("" + franchise_format_royality2);
                                    format_salespartner_monthly_revenue_2.setText("" + franchise_format_revenue2);
                                    format_operating_profitmargin_2.setText("" + franchise_format_profit2);


                                    JSONObject obj_format3 = arr_formats.getJSONObject(2);

                                    String franchise_id3 = obj_format3.getString(TAG_FRANCHISE_ID3);
                                    String franchise_format_name3 = obj_format3.getString(TAG_FRANCHISE_FORMAT_NAME3);
                                    String franchise_format_min_investment3 = obj_format3.getString(TAG_FRANCHISE_FORMAT_MIN_INVESTMENT3);
                                    String franchise_format_max_investment3 = obj_format3.getString(TAG_FRANCHISE_FORMAT_MAX_INVESTMENT3);
                                    String franchise_format_fee3 = obj_format3.getString(TAG_FRANCHISE_FORMAT_FEE3);
                                    String franchise_format_no_of_staffs3 = obj_format3.getString(TAG_FRANCHISE_FORMAT_NO_OF_STAFFS3);
                                    String franchise_format_royality3 = obj_format3.getString(TAG_FRANCHISE_FORMAT_ROYALITY3);
                                    String franchise_format_revenue3 = obj_format3.getString(TAG_FRANCHISE_FORMAT_REVENUE3);
                                    String franchise_format_profit3 = obj_format3.getString(TAG_FRANCHISE_FORMAT_PROFIT3);
                                    String franchise_format_act3 = obj_format3.getString(TAG_FRANCHISE_FORMAT_ACT3);
                                    String franchise_format_min_sqft3 = obj_format3.getString(TAG_FRANCHISE_FORMAT_MIN_SQFT3);
                                    String franchise_format_max_sqft3 = obj_format3.getString(TAG_FRANCHISE_FORMAT_MAX_SQFT3);

                                    format_name_3.setText("" + franchise_format_name3);
                                    format_spaceneeded_minimum_3.setText("" + franchise_format_min_sqft3);
                                    format_spaceneeded_maximum_3.setText("" + franchise_format_max_sqft3);
                                    format_investment_needed_minimum_3.setText("" + franchise_format_min_investment3);
                                    format_investment_needed_maximum_3.setText("" + franchise_format_max_investment3);
                                    format_brand_fee_3.setText("" + franchise_format_fee3);
                                    format_staff_required_3.setText("" + franchise_format_no_of_staffs3);
                                    format_royalty_commission_3.setText("" + franchise_format_royality3);
                                    format_salespartner_monthly_revenue_3.setText("" + franchise_format_revenue3);
                                    format_operating_profitmargin_3.setText("" + franchise_format_profit3);

                                    JSONObject obj_format4 = arr_formats.getJSONObject(3);

                                    String franchise_id4 = obj_format4.getString(TAG_FRANCHISE_ID4);
                                    String franchise_format_name4 = obj_format4.getString(TAG_FRANCHISE_FORMAT_NAME4);
                                    String franchise_format_min_investment4 = obj_format4.getString(TAG_FRANCHISE_FORMAT_MIN_INVESTMENT4);
                                    String franchise_format_max_investment4 = obj_format4.getString(TAG_FRANCHISE_FORMAT_MAX_INVESTMENT4);
                                    String franchise_format_fee4 = obj_format4.getString(TAG_FRANCHISE_FORMAT_FEE4);
                                    String franchise_format_no_of_staffs4 = obj_format4.getString(TAG_FRANCHISE_FORMAT_NO_OF_STAFFS4);
                                    String franchise_format_royality4 = obj_format4.getString(TAG_FRANCHISE_FORMAT_ROYALITY4);
                                    String franchise_format_revenue4 = obj_format4.getString(TAG_FRANCHISE_FORMAT_REVENUE4);
                                    String franchise_format_profit4 = obj_format4.getString(TAG_FRANCHISE_FORMAT_PROFIT4);
                                    String franchise_format_act4 = obj_format4.getString(TAG_FRANCHISE_FORMAT_ACT4);
                                    String franchise_format_min_sqft4 = obj_format4.getString(TAG_FRANCHISE_FORMAT_MIN_SQFT4);
                                    String franchise_format_max_sqft4 = obj_format4.getString(TAG_FRANCHISE_FORMAT_MAX_SQFT4);

                                    format_name_4.setText("" + franchise_format_name4);
                                    format_spaceneeded_minimum_4.setText("" + franchise_format_min_sqft4);
                                    format_spaceneeded_maximum_4.setText("" + franchise_format_max_sqft4);
                                    format_investment_needed_minimum_4.setText("" + franchise_format_min_investment4);
                                    format_investment_needed_maximum_4.setText("" + franchise_format_max_investment4);
                                    format_brand_fee_4.setText("" + franchise_format_fee4);
                                    format_staff_required_4.setText("" + franchise_format_no_of_staffs4);
                                    format_royalty_commission_4.setText("" + franchise_format_royality4);
                                    format_salespartner_monthly_revenue_4.setText("" + franchise_format_revenue4);
                                    format_operating_profitmargin_4.setText("" + franchise_format_profit4);

                                    JSONObject obj_format5 = arr_formats.getJSONObject(4);

                                    String franchise_id5 = obj_format5.getString(TAG_FRANCHISE_ID5);
                                    String franchise_format_name5 = obj_format5.getString(TAG_FRANCHISE_FORMAT_NAME5);
                                    String franchise_format_min_investment5 = obj_format5.getString(TAG_FRANCHISE_FORMAT_MIN_INVESTMENT5);
                                    String franchise_format_max_investment5 = obj_format5.getString(TAG_FRANCHISE_FORMAT_MAX_INVESTMENT5);
                                    String franchise_format_fee5 = obj_format5.getString(TAG_FRANCHISE_FORMAT_FEE5);
                                    String franchise_format_no_of_staffs5 = obj_format5.getString(TAG_FRANCHISE_FORMAT_NO_OF_STAFFS5);
                                    String franchise_format_royality5 = obj_format5.getString(TAG_FRANCHISE_FORMAT_ROYALITY5);
                                    String franchise_format_revenue5 = obj_format5.getString(TAG_FRANCHISE_FORMAT_REVENUE5);
                                    String franchise_format_profit5 = obj_format5.getString(TAG_FRANCHISE_FORMAT_PROFIT5);
                                    String franchise_format_act5 = obj_format5.getString(TAG_FRANCHISE_FORMAT_ACT5);
                                    String franchise_format_min_sqft5 = obj_format5.getString(TAG_FRANCHISE_FORMAT_MIN_SQFT5);
                                    String franchise_format_max_sqft5 = obj_format5.getString(TAG_FRANCHISE_FORMAT_MAX_SQFT5);

                                    format_name_5.setText("" + franchise_format_name5);
                                    format_spaceneeded_minimum_5.setText("" + franchise_format_min_sqft5);
                                    format_spaceneeded_maximum_5.setText("" + franchise_format_max_sqft5);
                                    format_investment_needed_minimum_5.setText("" + franchise_format_min_investment5);
                                    format_investment_needed_maximum_5.setText("" + franchise_format_max_investment5);
                                    format_brand_fee_5.setText("" + franchise_format_fee5);
                                    format_staff_required_5.setText("" + franchise_format_no_of_staffs5);
                                    format_royalty_commission_5.setText("" + franchise_format_royality5);
                                    format_salespartner_monthly_revenue_5.setText("" + franchise_format_revenue5);
                                    format_operating_profitmargin_5.setText("" + franchise_format_profit5);

                                } else if (franchise_format.equals("6")) {

                                    JSONObject obj_format1 = arr_formats.getJSONObject(0);

                                    String franchise_id1 = obj_format1.getString(TAG_FRANCHISE_ID1);
                                    String franchise_format_name1 = obj_format1.getString(TAG_FRANCHISE_FORMAT_NAME1);
                                    String franchise_format_min_investment1 = obj_format1.getString(TAG_FRANCHISE_FORMAT_MIN_INVESTMENT1);
                                    String franchise_format_max_investment1 = obj_format1.getString(TAG_FRANCHISE_FORMAT_MAX_INVESTMENT1);
                                    String franchise_format_fee1 = obj_format1.getString(TAG_FRANCHISE_FORMAT_FEE1);
                                    String franchise_format_no_of_staffs1 = obj_format1.getString(TAG_FRANCHISE_FORMAT_NO_OF_STAFFS1);
                                    String franchise_format_royality1 = obj_format1.getString(TAG_FRANCHISE_FORMAT_ROYALITY1);
                                    String franchise_format_revenue1 = obj_format1.getString(TAG_FRANCHISE_FORMAT_REVENUE1);
                                    String franchise_format_profit1 = obj_format1.getString(TAG_FRANCHISE_FORMAT_PROFIT1);
                                    String franchise_format_act1 = obj_format1.getString(TAG_FRANCHISE_FORMAT_ACT1);
                                    String franchise_format_min_sqft1 = obj_format1.getString(TAG_FRANCHISE_FORMAT_MIN_SQFT1);
                                    String franchise_format_max_sqft1 = obj_format1.getString(TAG_FRANCHISE_FORMAT_MAX_SQFT1);


                                    format_name_1.setText("" + franchise_format_name1);
                                    format_spaceneeded_minimum_1.setText("" + franchise_format_min_sqft1);
                                    format_spaceneeded_maximum_1.setText("" + franchise_format_max_sqft1);
                                    format_investment_needed_minimum_1.setText("" + franchise_format_min_investment1);
                                    format_investment_needed_maximum_1.setText("" + franchise_format_max_investment1);
                                    format_brand_fee_1.setText("" + franchise_format_fee1);
                                    format_staff_required_1.setText("" + franchise_format_no_of_staffs1);
                                    format_royalty_commission_1.setText("" + franchise_format_royality1);
                                    format_salespartner_monthly_revenue_1.setText("" + franchise_format_revenue1);
                                    format_operating_profitmargin_1.setText("" + franchise_format_profit1);

                                    JSONObject obj_format2 = arr_formats.getJSONObject(1);

                                    String franchise_id2 = obj_format2.getString(TAG_FRANCHISE_ID2);
                                    String franchise_format_name2 = obj_format2.getString(TAG_FRANCHISE_FORMAT_NAME2);
                                    String franchise_format_min_investment2 = obj_format2.getString(TAG_FRANCHISE_FORMAT_MIN_INVESTMENT2);
                                    String franchise_format_max_investment2 = obj_format2.getString(TAG_FRANCHISE_FORMAT_MAX_INVESTMENT2);
                                    String franchise_format_fee2 = obj_format2.getString(TAG_FRANCHISE_FORMAT_FEE2);
                                    String franchise_format_no_of_staffs2 = obj_format2.getString(TAG_FRANCHISE_FORMAT_NO_OF_STAFFS2);
                                    String franchise_format_royality2 = obj_format2.getString(TAG_FRANCHISE_FORMAT_ROYALITY2);
                                    String franchise_format_revenue2 = obj_format2.getString(TAG_FRANCHISE_FORMAT_REVENUE2);
                                    String franchise_format_profit2 = obj_format2.getString(TAG_FRANCHISE_FORMAT_PROFIT2);
                                    String franchise_format_act2 = obj_format2.getString(TAG_FRANCHISE_FORMAT_ACT2);
                                    String franchise_format_min_sqft2 = obj_format2.getString(TAG_FRANCHISE_FORMAT_MIN_SQFT2);
                                    String franchise_format_max_sqft2 = obj_format2.getString(TAG_FRANCHISE_FORMAT_MAX_SQFT2);

                                    format_name_2.setText("" + franchise_format_name2);
                                    format_spaceneeded_minimum_2.setText("" + franchise_format_min_sqft2);
                                    format_spaceneeded_maximum_2.setText("" + franchise_format_max_sqft2);
                                    format_investment_needed_minimum_2.setText("" + franchise_format_min_investment2);
                                    format_investment_needed_maximum_2.setText("" + franchise_format_max_investment2);
                                    format_brand_fee_2.setText("" + franchise_format_fee2);
                                    format_staff_required_2.setText("" + franchise_format_no_of_staffs2);
                                    format_royalty_commission_2.setText("" + franchise_format_royality2);
                                    format_salespartner_monthly_revenue_2.setText("" + franchise_format_revenue2);
                                    format_operating_profitmargin_2.setText("" + franchise_format_profit2);


                                    JSONObject obj_format3 = arr_formats.getJSONObject(2);

                                    String franchise_id3 = obj_format3.getString(TAG_FRANCHISE_ID3);
                                    String franchise_format_name3 = obj_format3.getString(TAG_FRANCHISE_FORMAT_NAME3);
                                    String franchise_format_min_investment3 = obj_format3.getString(TAG_FRANCHISE_FORMAT_MIN_INVESTMENT3);
                                    String franchise_format_max_investment3 = obj_format3.getString(TAG_FRANCHISE_FORMAT_MAX_INVESTMENT3);
                                    String franchise_format_fee3 = obj_format3.getString(TAG_FRANCHISE_FORMAT_FEE3);
                                    String franchise_format_no_of_staffs3 = obj_format3.getString(TAG_FRANCHISE_FORMAT_NO_OF_STAFFS3);
                                    String franchise_format_royality3 = obj_format3.getString(TAG_FRANCHISE_FORMAT_ROYALITY3);
                                    String franchise_format_revenue3 = obj_format3.getString(TAG_FRANCHISE_FORMAT_REVENUE3);
                                    String franchise_format_profit3 = obj_format3.getString(TAG_FRANCHISE_FORMAT_PROFIT3);
                                    String franchise_format_act3 = obj_format3.getString(TAG_FRANCHISE_FORMAT_ACT3);
                                    String franchise_format_min_sqft3 = obj_format3.getString(TAG_FRANCHISE_FORMAT_MIN_SQFT3);
                                    String franchise_format_max_sqft3 = obj_format3.getString(TAG_FRANCHISE_FORMAT_MAX_SQFT3);

                                    format_name_3.setText("" + franchise_format_name3);
                                    format_spaceneeded_minimum_3.setText("" + franchise_format_min_sqft3);
                                    format_spaceneeded_maximum_3.setText("" + franchise_format_max_sqft3);
                                    format_investment_needed_minimum_3.setText("" + franchise_format_min_investment3);
                                    format_investment_needed_maximum_3.setText("" + franchise_format_max_investment3);
                                    format_brand_fee_3.setText("" + franchise_format_fee3);
                                    format_staff_required_3.setText("" + franchise_format_no_of_staffs3);
                                    format_royalty_commission_3.setText("" + franchise_format_royality3);
                                    format_salespartner_monthly_revenue_3.setText("" + franchise_format_revenue3);
                                    format_operating_profitmargin_3.setText("" + franchise_format_profit3);

                                    JSONObject obj_format4 = arr_formats.getJSONObject(3);

                                    String franchise_id4 = obj_format4.getString(TAG_FRANCHISE_ID4);
                                    String franchise_format_name4 = obj_format4.getString(TAG_FRANCHISE_FORMAT_NAME4);
                                    String franchise_format_min_investment4 = obj_format4.getString(TAG_FRANCHISE_FORMAT_MIN_INVESTMENT4);
                                    String franchise_format_max_investment4 = obj_format4.getString(TAG_FRANCHISE_FORMAT_MAX_INVESTMENT4);
                                    String franchise_format_fee4 = obj_format4.getString(TAG_FRANCHISE_FORMAT_FEE4);
                                    String franchise_format_no_of_staffs4 = obj_format4.getString(TAG_FRANCHISE_FORMAT_NO_OF_STAFFS4);
                                    String franchise_format_royality4 = obj_format4.getString(TAG_FRANCHISE_FORMAT_ROYALITY4);
                                    String franchise_format_revenue4 = obj_format4.getString(TAG_FRANCHISE_FORMAT_REVENUE4);
                                    String franchise_format_profit4 = obj_format4.getString(TAG_FRANCHISE_FORMAT_PROFIT4);
                                    String franchise_format_act4 = obj_format4.getString(TAG_FRANCHISE_FORMAT_ACT4);
                                    String franchise_format_min_sqft4 = obj_format4.getString(TAG_FRANCHISE_FORMAT_MIN_SQFT4);
                                    String franchise_format_max_sqft4 = obj_format4.getString(TAG_FRANCHISE_FORMAT_MAX_SQFT4);

                                    format_name_4.setText("" + franchise_format_name4);
                                    format_spaceneeded_minimum_4.setText("" + franchise_format_min_sqft4);
                                    format_spaceneeded_maximum_4.setText("" + franchise_format_max_sqft4);
                                    format_investment_needed_minimum_4.setText("" + franchise_format_min_investment4);
                                    format_investment_needed_maximum_4.setText("" + franchise_format_max_investment4);
                                    format_brand_fee_4.setText("" + franchise_format_fee4);
                                    format_staff_required_4.setText("" + franchise_format_no_of_staffs4);
                                    format_royalty_commission_4.setText("" + franchise_format_royality4);
                                    format_salespartner_monthly_revenue_4.setText("" + franchise_format_revenue4);
                                    format_operating_profitmargin_4.setText("" + franchise_format_profit4);

                                    JSONObject obj_format5 = arr_formats.getJSONObject(4);

                                    String franchise_id5 = obj_format5.getString(TAG_FRANCHISE_ID5);
                                    String franchise_format_name5 = obj_format5.getString(TAG_FRANCHISE_FORMAT_NAME5);
                                    String franchise_format_min_investment5 = obj_format5.getString(TAG_FRANCHISE_FORMAT_MIN_INVESTMENT5);
                                    String franchise_format_max_investment5 = obj_format5.getString(TAG_FRANCHISE_FORMAT_MAX_INVESTMENT5);
                                    String franchise_format_fee5 = obj_format5.getString(TAG_FRANCHISE_FORMAT_FEE5);
                                    String franchise_format_no_of_staffs5 = obj_format5.getString(TAG_FRANCHISE_FORMAT_NO_OF_STAFFS5);
                                    String franchise_format_royality5 = obj_format5.getString(TAG_FRANCHISE_FORMAT_ROYALITY5);
                                    String franchise_format_revenue5 = obj_format5.getString(TAG_FRANCHISE_FORMAT_REVENUE5);
                                    String franchise_format_profit5 = obj_format5.getString(TAG_FRANCHISE_FORMAT_PROFIT5);
                                    String franchise_format_act5 = obj_format5.getString(TAG_FRANCHISE_FORMAT_ACT5);
                                    String franchise_format_min_sqft5 = obj_format5.getString(TAG_FRANCHISE_FORMAT_MIN_SQFT5);
                                    String franchise_format_max_sqft5 = obj_format5.getString(TAG_FRANCHISE_FORMAT_MAX_SQFT5);

                                    format_name_5.setText("" + franchise_format_name5);
                                    format_spaceneeded_minimum_5.setText("" + franchise_format_min_sqft5);
                                    format_spaceneeded_maximum_5.setText("" + franchise_format_max_sqft5);
                                    format_investment_needed_minimum_5.setText("" + franchise_format_min_investment5);
                                    format_investment_needed_maximum_5.setText("" + franchise_format_max_investment5);
                                    format_brand_fee_5.setText("" + franchise_format_fee5);
                                    format_staff_required_5.setText("" + franchise_format_no_of_staffs5);
                                    format_royalty_commission_5.setText("" + franchise_format_royality5);
                                    format_salespartner_monthly_revenue_5.setText("" + franchise_format_revenue5);
                                    format_operating_profitmargin_5.setText("" + franchise_format_profit5);

                                    JSONObject obj_format6 = arr_formats.getJSONObject(5);

                                    String franchise_id6 = obj_format6.getString(TAG_FRANCHISE_ID6);
                                    String franchise_format_name6 = obj_format6.getString(TAG_FRANCHISE_FORMAT_NAME6);
                                    String franchise_format_min_investment6 = obj_format6.getString(TAG_FRANCHISE_FORMAT_MIN_INVESTMENT6);
                                    String franchise_format_max_investment6 = obj_format6.getString(TAG_FRANCHISE_FORMAT_MAX_INVESTMENT6);
                                    String franchise_format_fee6 = obj_format6.getString(TAG_FRANCHISE_FORMAT_FEE6);
                                    String franchise_format_no_of_staffs6 = obj_format6.getString(TAG_FRANCHISE_FORMAT_NO_OF_STAFFS6);
                                    String franchise_format_royality6 = obj_format6.getString(TAG_FRANCHISE_FORMAT_ROYALITY6);
                                    String franchise_format_revenue6 = obj_format6.getString(TAG_FRANCHISE_FORMAT_REVENUE6);
                                    String franchise_format_profit6 = obj_format6.getString(TAG_FRANCHISE_FORMAT_PROFIT6);
                                    String franchise_format_act6 = obj_format6.getString(TAG_FRANCHISE_FORMAT_ACT6);
                                    String franchise_format_min_sqft6 = obj_format6.getString(TAG_FRANCHISE_FORMAT_MIN_SQFT6);
                                    String franchise_format_max_sqft6 = obj_format6.getString(TAG_FRANCHISE_FORMAT_MAX_SQFT6);

                                    format_name_6.setText("" + franchise_format_name6);
                                    format_spaceneeded_minimum_6.setText("" + franchise_format_min_sqft6);
                                    format_spaceneeded_maximum_6.setText("" + franchise_format_max_sqft6);
                                    format_investment_needed_minimum_6.setText("" + franchise_format_min_investment6);
                                    format_investment_needed_maximum_6.setText("" + franchise_format_max_investment6);
                                    format_brand_fee_6.setText("" + franchise_format_fee6);
                                    format_staff_required_6.setText("" + franchise_format_no_of_staffs6);
                                    format_royalty_commission_6.setText("" + franchise_format_royality6);
                                    format_salespartner_monthly_revenue_6.setText("" + franchise_format_revenue6);
                                    format_operating_profitmargin_6.setText("" + franchise_format_profit6);

                                }
                                dialog.dismiss();

                            } catch (Exception e) {

                            }
                        }

                    } else if (success == 0) {

                        dialog.dismiss();

                        Alerter.create(Activity_DetailedView_Franchise.this)
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
                Alerter.create(Activity_DetailedView_Franchise.this)
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
                params.put("franchise_key", str_franchise_key);
                params.put("currency", str_user_currency);

                System.out.println("USER_ID ::: " + str_user_id);
                System.out.println("FRANCHISE KEY ::: " + str_franchise_key);

                return params;
            }

        };

        // Adding request to request queue
        queue.add(request);
    }


}


