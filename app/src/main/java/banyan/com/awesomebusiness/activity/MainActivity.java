package banyan.com.awesomebusiness.activity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import banyan.com.awesomebusiness.Activity_Register;
import banyan.com.awesomebusiness.R;
import in.galaxyofandroid.spinerdialog.OnSpinerItemClick;
import in.galaxyofandroid.spinerdialog.SpinnerDialog;

/**
 * Created by JO on 19/07/17.
 */

public class MainActivity extends AppCompatActivity implements FragmentDrawer.FragmentDrawerListener {

    private static String TAG = MainActivity.class.getSimpleName();

    private Toolbar mToolbar;
    private FragmentDrawer drawerFragment;
    TextView popup_country,popup_currency;
    // Popup
    final Context context = this;
    ArrayList<String> items=new ArrayList<>();
    ArrayList<String> items1=new ArrayList<>();
    SpinnerDialog spinnerDialog,spinnerDialog1;
    TextView country, currency;

    String str_selected_country, str_selected_courrency;

    // CART
    RelativeLayout notification_Count, notification_batch, message_Count, message_batch;
    TextView tv_notification, tv_message;
    int i = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mToolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        drawerFragment = (FragmentDrawer)
                getSupportFragmentManager().findFragmentById(R.id.fragment_navigation_drawer);
        drawerFragment.setUp(R.id.fragment_navigation_drawer, (DrawerLayout) findViewById(R.id.drawer_layout), mToolbar);
        drawerFragment.setDrawerListener(this);

        // display the first navigation drawer view on app launch
        displayView(0);

    }

    /**********************************
     * Main Menu
     *********************************/

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);

        // Notification Counter
        MenuItem item1 = menu.findItem(R.id.action_notification);
        MenuItemCompat.setActionView(item1, R.layout.toolbar_notification_update_count_layout);
        notification_Count = (RelativeLayout) MenuItemCompat.getActionView(item1);
        notification_batch = (RelativeLayout) MenuItemCompat.getActionView(item1);
        tv_notification = (TextView) notification_batch.findViewById(R.id.badge_notification);
        tv_notification.setText("0");
        //str_cart = Integer.toString(count);
        //tv.setText("" + cart_size);

        notification_batch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(getApplicationContext(), Activity_Register.class);
                startActivity(i);
            }
        });
        tv_notification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(getApplicationContext(), Activity_Register.class);
                startActivity(i);
            }
        });
        // Messager Counter
        MenuItem item2 = menu.findItem(R.id.action_message);
        MenuItemCompat.setActionView(item2, R.layout.toolbar_message_update_count_layout);
        message_Count = (RelativeLayout) MenuItemCompat.getActionView(item2);
        message_batch = (RelativeLayout) MenuItemCompat.getActionView(item2);
        tv_message = (TextView) message_batch.findViewById(R.id.badge_message);
        tv_message.setText("0");
        //str_cart = Integer.toString(count);
        //tv.setText("" + cart_size);

        message_batch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(getApplicationContext(), Activity_Register.class);
                startActivity(i);
            }
        });
        tv_message.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(getApplicationContext(), Activity_Register.class);
                startActivity(i);
            }
        });

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_add_user) {
            return true;
        }
        if (id == R.id.action_add_business_profile) {
            Toast.makeText(getApplicationContext(), "Business Profile", Toast.LENGTH_LONG).show();
            return true;
        }
        if (id == R.id.action_add_advisor_profile) {
            Toast.makeText(getApplicationContext(), "Advisor Profile", Toast.LENGTH_LONG).show();
            return true;
        }
        if (id == R.id.action_add_investor_profile) {
            Toast.makeText(getApplicationContext(), "Investor Profile", Toast.LENGTH_LONG).show();
            return true;
        }
        if (id == R.id.action_add_franchise_profile) {
            Toast.makeText(getApplicationContext(), "Franchise Profile", Toast.LENGTH_LONG).show();
            return true;
        }
        if (id == R.id.action_setting) {

            Function_AlertDialog();

            return true;
        }

       /* if (id == R.id.action_search) {


            return true;
        }*/

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onDrawerItemSelected(View view, int position) {
        displayView(position);
    }

    private void displayView(int position) {
        Fragment fragment = null;
        String title = getString(R.string.app_name);
        switch (position) {
            case 0:
                fragment = new Fragment_Home();
                title = getString(R.string.title_home);
                break;
            case 1:
                fragment = new Fragment_BusinessForSale();
                title = getString(R.string.title_bus_sale);
                break;
            case 2:
                fragment = new Fragment_InvestorsandBuyers();
                title = getString(R.string.title_investors);
                break;
            case 3:
                fragment = new Fragment_Franchise();
                title = getString(R.string.title_franchies);
                break;
            case 4:
                fragment = new Fragment_How_To();
                title = getString(R.string.title_howto);
                break;
            case 5:
                fragment = new Fragment_QA();
                title = getString(R.string.title_qa);
                break;
            case 6:
                fragment = new Fragment_Company();
                title = getString(R.string.title_company);
                break;
            default:
                break;
        }

        if (fragment != null) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.container_body, fragment);
            fragmentTransaction.commit();

            // set the toolbar title
            getSupportActionBar().setTitle(title);
        }
    }

    public void Function_AlertDialog(){

        LayoutInflater li = LayoutInflater.from(context);
        View promptsView = li.inflate(R.layout.popup_custom, null);

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                context);

        // set prompts.xml to alertdialog builder
        alertDialogBuilder.setView(promptsView);

        popup_country = (TextView) promptsView
                .findViewById(R.id.popup_settings_country);
        popup_currency = (TextView) promptsView
                .findViewById(R.id.popup_settings_courrency);

        popup_country.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(getApplicationContext(), "Country", Toast.LENGTH_LONG).show();
                Fun_alert_country();

            }
        });

        popup_currency.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(getApplicationContext(), "Currency", Toast.LENGTH_LONG).show();
                Fun_alert_currency();
            }
        });

        // set dialog message
        alertDialogBuilder
                .setCancelable(false)
                .setPositiveButton("Save",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,int id) {
                                // get user input and set it to result
                                // edit text
                            }
                        })
                .setNegativeButton("Cancel",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,int id) {
                                dialog.cancel();
                            }
                        });

        // create alert dialog
        AlertDialog alertDialog = alertDialogBuilder.create();

        // show it
        alertDialog.show();
    }

    public void Fun_alert_country() {

        System.out.println("CountryYYYYYYYYYYYYYYYYYYYYYY");
        items.add("India");
        items.add("Australia");
        items.add("America");
        items.add("Pakistan");
        items.add("Mexico");

        spinnerDialog=new SpinnerDialog(MainActivity.this,items,"Select or Search Country",R.style.DialogAnimations_SmileWindow);

        spinnerDialog.bindOnSpinerListener(new OnSpinerItemClick()
        {
            @Override
            public void onClick(String item, int position)
            {
                System.out.println("CountryYYYYYYYYYYYYYYYYYYYYYY00000000000000000000000");
                country.setText(item + " Position: " + position);
            }
        });
    }

    public void Fun_alert_currency() {

        System.out.println("Currencyyyyyyyyyyyyyyyyyyyyyyyyyyyyyy");

        items1.add("INR");
        items1.add("USD");
        items1.add("AD");
        items1.add("DNR");

        spinnerDialog=new SpinnerDialog(MainActivity.this,items1,"Select or Search Currency",R.style.DialogAnimations_SmileWindow);

        spinnerDialog.bindOnSpinerListener(new OnSpinerItemClick()
        {
            @Override
            public void onClick(String item, int position)
            {
                System.out.println("Currencyyyyyyyyyyyyyyyyyyyyyyyyyyyyyy000000000000000");
                currency.setText(item + " Position: " + position);
            }
        });
    }
}