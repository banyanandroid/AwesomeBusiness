package banyan.com.awesomebusiness.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import banyan.com.awesomebusiness.R;

/**
 * Created by Schan on 24-Jul-17.
 */

public class Activity_InvestorProfile extends AppCompatActivity {

    private Toolbar mToolbar;
    Button btn_submit;
    EditText edt_name, edt_mobile_number, edt_email;

    // Checkbox for selecting Interests
    CheckBox chb_acq_buying_business, chb_investing_in_business, chb_lending_to_business,
            chb_buy_property_plant_machinery, chb_take_fran_distri_sales_agency;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_investor_profile);

        mToolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("Investor Profile");
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
        btn_submit = (Button) findViewById(R.id.btn_submit);
        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Investor Profile Created Successfully", Toast.LENGTH_LONG).show();


            }
        });
    }
}