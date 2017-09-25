package banyan.com.awesomebusiness.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.RequestQueue;

import java.util.HashMap;

import banyan.com.awesomebusiness.R;
import banyan.com.awesomebusiness.global.SessionManager;
import dmax.dialog.SpotsDialog;

/**
 * Created by CHANDRU on 24-Jul-17.
 */

public class Activity_DetailedView_Business_For_Sale extends AppCompatActivity {

    private Toolbar mToolbar;
    SpotsDialog dialog;
    public static RequestQueue queue;

    SessionManager session;
    public static String str_user_id, str_user_name, str_user_email, str_user_photoo;
    String str_user_currency = "";

    Button btn_contact_business;

    ImageView img_business_for_sale;

    TextView txt_title, txt_established, txt_employees, txt_legal_entity, txt_reported_sales,
            txt_run_rate_ales, txt_ebitda_margin, txt_industries, txt_locations, txt_listed_by, txt_status,
            txt_seeking_investment_currency_type, txt_seeking_investment_amount,
            txt_business_overview, txt_products_services_overview, txt_facilities_overview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed_view_business_for_sale);

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


        img_business_for_sale = (ImageView) findViewById(R.id.ativity_details_image_view);


        txt_title = (TextView) findViewById(R.id.ativity_details_txt_title);
        txt_established = (TextView) findViewById(R.id.ativity_details_txt_established_year);
        txt_employees = (TextView) findViewById(R.id.ativity_details_txt_employees);
        txt_legal_entity = (TextView) findViewById(R.id.ativity_details_legal_entity);
        txt_reported_sales = (TextView) findViewById(R.id.ativity_details_reported_sales);
        txt_run_rate_ales = (TextView) findViewById(R.id.ativity_details_run_rate_sales);
        txt_ebitda_margin = (TextView) findViewById(R.id.ativity_details_ebitda_margin);
        txt_industries = (TextView) findViewById(R.id.ativity_details_industries);
        txt_locations = (TextView) findViewById(R.id.ativity_details_locations);
        txt_listed_by = (TextView) findViewById(R.id.ativity_details_listed_by);
        txt_status = (TextView) findViewById(R.id.ativity_details_status);
        txt_seeking_investment_currency_type = (TextView) findViewById(R.id.ativity_details_txt_seeking_amount_currency_type);
        txt_seeking_investment_amount = (TextView) findViewById(R.id.ativity_details_txt_seeking_amount);
        txt_business_overview = (TextView) findViewById(R.id.ativity_details_txt_business_overview);
        txt_products_services_overview = (TextView) findViewById(R.id.ativity_details_txt_prod_serv_overview);
        txt_facilities_overview = (TextView) findViewById(R.id.ativity_details_txt_facilities_overview);

        btn_contact_business = (Button) findViewById(R.id.btn_contact_business);


    }


}


