package banyan.com.awesomebusiness.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

import banyan.com.awesomebusiness.Activity_Register;
import banyan.com.awesomebusiness.R;

/**
 * Created by Schan on 21-Jul-17.
 */

public class Activity_Filter extends AppCompatActivity {

    private Toolbar mToolbar;

    Button btn_trans_type, btn_location, btn_industry, btn_done;

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

        btn_done = (Button) findViewById(R.id.btn_filter_done);
        btn_done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Activity_Filter.this, MainActivity.class);
                startActivity(i);
                overridePendingTransition(R.anim.pull_in_right, R.anim.pull_in_left);
                finish();


            }
        });

    }
}
