package banyan.com.awesomebusiness.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import banyan.com.awesomebusiness.R;

/**
 * Created by Schan on 24-Jul-17.
 */

public class Activity_InvestorProfile extends AppCompatActivity {


    Button btn_submit;
    EditText edt_name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_investor_profile);

        edt_name = (EditText) findViewById(R.id.edt_name);
        btn_submit = (Button)findViewById(R.id.btn_submit);
        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Investor Profile Created Successfully", Toast.LENGTH_LONG).show();


            }
        });
    }
}