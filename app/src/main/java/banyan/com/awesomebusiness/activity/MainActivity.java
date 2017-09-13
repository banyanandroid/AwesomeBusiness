package banyan.com.awesomebusiness.activity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
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

import banyan.com.awesomebusiness.Activity_Register;
import banyan.com.awesomebusiness.R;
import banyan.com.awesomebusiness.global.AppConfig;
import dmax.dialog.SpotsDialog;


/**
 * Created by JO on 19/07/17.
 */

public class MainActivity extends AppCompatActivity implements FragmentDrawer.FragmentDrawerListener {

    private static String TAG = MainActivity.class.getSimpleName();

    private Toolbar mToolbar;
    SpotsDialog dialog;
    public static RequestQueue queue;
    private FragmentDrawer drawerFragment;
    TextView t1;
    TextView popup_country, popup_currency;

    public static final String TAG_COUNTRY_ID = "country_id";
    public static final String TAG_COUNTRY_NAME = "country_name";
    public static final String TAG_COUNTRY_CURRENCY = "country_currency";

    // Popup
    final Context context = this;
    ArrayList<String> Arraylist_country_id = null;
    ArrayList<String> Arraylist_country_name = null;
    ArrayList<String> Arraylist_country_currency = null;

    private ArrayAdapter<String> adapter_country_currency;

    String str_selected_country_name, str_selected_country_id, str_selected_currency, str_selected_currency_id;
    String str_selected_country_position, str_selected_currency_position;
    SearchableSpinner spinner_country, spinner_currency;
    TextView txt_select_country, txt_select_currency;
    Switch switch_notification;
    TextView popup_txt_notification;

    String str_previous_selected_country_name, str_previous_selected_currency;

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

        Arraylist_country_id = new ArrayList<String>();
        Arraylist_country_name = new ArrayList<String>();
        Arraylist_country_currency = new ArrayList<String>();

        drawerFragment = (FragmentDrawer)
                getSupportFragmentManager().findFragmentById(R.id.fragment_navigation_drawer);
        drawerFragment.setUp(R.id.fragment_navigation_drawer, (DrawerLayout) findViewById(R.id.drawer_layout), mToolbar);
        drawerFragment.setDrawerListener(this);

        // display the first navigation drawer view on app launch
        displayView(0);

        /*SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        str_previous_selected_country_name = sharedPreferences.getString("str_selected_country_position", "str_selected_country_position");
        str_previous_selected_currency = sharedPreferences.getString("str_selected_currency_position", "str_selected_currency_position");
*/

        try {
            dialog = new SpotsDialog(MainActivity.this);
            dialog.show();
            queue = Volley.newRequestQueue(getApplicationContext());
            Get_Currency_Country();


        } catch (Exception e) {
            // TODO: handle exception
        }


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

                Intent i = new Intent(getApplicationContext(), Activity_Notifications.class);
                startActivity(i);
            }
        });
        tv_notification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(getApplicationContext(), Activity_Notifications.class);
                startActivity(i);
            }
        });

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_add_user) {
            return true;
        }
        if (id == R.id.action_add_business_profile) {

            Intent i = new Intent(getApplicationContext(), Activity_BusinessProfile.class);
            startActivity(i);
        }
        if (id == R.id.action_add_advisor_profile) {

            Toast.makeText(getApplicationContext(), "Advisor Profile", Toast.LENGTH_LONG).show();
            return true;
        }
        if (id == R.id.action_add_investor_profile) {
            /*Toast.makeText(getApplicationContext(), "Investor Profile", Toast.LENGTH_LONG).show();
            return true;*/
            Intent i = new Intent(getApplicationContext(), Activity_InvestorProfile.class);
            startActivity(i);
        }
        if (id == R.id.action_add_franchise_profile) {

            Intent i = new Intent(getApplicationContext(), Activity_FranchiseProfile.class);
            startActivity(i);
        }
        if (id == R.id.action_setting) {

            try {
                spinner_country.setTitle(str_previous_selected_country_name);
                spinner_currency.setTitle(str_previous_selected_currency);
            } catch (Exception e) {
            }

            Function_AlertDialog();

            return true;
        }

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
                fragment = new Fragment_Messsage();
                title = getString(R.string.title_message);
                break;
            case 5:
                fragment = new Fragment_How_To();
                title = getString(R.string.title_howto);
                break;
            case 6:
                fragment = new Fragment_QA();
                title = getString(R.string.title_qa);
                break;
            case 7:
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

    public void Function_AlertDialog() {

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        str_previous_selected_country_name = sharedPreferences.getString("country_position", "country_position");
        str_previous_selected_currency = sharedPreferences.getString("currency_position", "currency_position");

        LayoutInflater li = LayoutInflater.from(context);
        View promptsView = li.inflate(R.layout.popup_custom, null);

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                context);
        alertDialogBuilder.setView(promptsView);

        spinner_country = (SearchableSpinner) promptsView.findViewById(R.id.popup_spinner_country);
        spinner_currency = (SearchableSpinner) promptsView.findViewById(R.id.popup_spinner_currency);
        txt_select_country = (TextView) promptsView.findViewById(R.id.popup_txt_country);
        txt_select_currency = (TextView) promptsView.findViewById(R.id.popup_txt_currency);

        switch_notification = (Switch) promptsView.findViewById(R.id.popup_switvh_notification);
        popup_txt_notification = (TextView) promptsView.findViewById(R.id.popup_txt_notification);

        if (str_previous_selected_country_name.equals("country_position")) {
            txt_select_country.setText("");
        } else {
            txt_select_country.setText("" + str_previous_selected_country_name);
        }
        if (str_previous_selected_currency.equals("currency_position")) {
            txt_select_currency.setText("");
        } else {
            txt_select_currency.setText("" + str_previous_selected_currency);
        }

        try {
            spinner_country
                    .setAdapter(new ArrayAdapter<String>(MainActivity.this,
                            android.R.layout.simple_spinner_dropdown_item,
                            Arraylist_country_name));

            spinner_country.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                    System.out.println("Countryyyyyyyyyyyy");

                    t1 = (TextView) view;
                    str_selected_country_name = t1.getText().toString();
                    str_selected_country_id = Arraylist_country_id.get(position);
                    int pos = position;
                    str_selected_country_position = String.valueOf(pos);

                    txt_select_country.setText("" + str_selected_country_name);
                    SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("country_position", str_selected_country_name);
                    editor.commit();
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });

            spinner_currency
                    .setAdapter(new ArrayAdapter<String>(MainActivity.this,
                            android.R.layout.simple_spinner_dropdown_item,
                            Arraylist_country_currency));

            // spinner_currency.setSelection(2);


            spinner_currency.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                    t1 = (TextView) view;
                    str_selected_currency = t1.getText().toString();

                    str_selected_currency_id = Arraylist_country_id.get(position);
                    int pos = position;
                    str_selected_currency_position = String.valueOf(pos);

                    txt_select_currency.setText("" + str_selected_currency);
                    SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("currency_position", str_selected_currency);
                    editor.commit();
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });


        } catch (Exception e) {

        }


        switch_notification.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    // If the switch button is on
                    popup_txt_notification.setText("ON");
                } else {
                    // If the switch button is off
                    popup_txt_notification.setText("OFF");
                }
            }
        });


        // set dialog message
        alertDialogBuilder
                .setCancelable(false)
                .setPositiveButton("Save",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                // get user input and set it to result
                                // edit text
                                SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                                SharedPreferences.Editor editor = sharedPreferences.edit();
                                editor.putString("str_selected_country_name", str_selected_country_name);
                                editor.putString("str_selected_country_id", str_selected_country_id);
                                editor.putString("str_selected_currency", str_selected_currency_id);
                                editor.commit();

                            }
                        })
                .setNegativeButton("Cancel",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });

        // create alert dialog
        AlertDialog alertDialog = alertDialogBuilder.create();

        // show it
        alertDialog.show();
    }

    /*****************************
     * To get  Currency and Country
     ***************************/

    public void Get_Currency_Country() {
        String tag_json_obj = "json_obj_req";
        StringRequest request = new StringRequest(Request.Method.POST,
                AppConfig.url_country, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d(TAG, response.toString());
                try {
                    JSONObject obj = new JSONObject(response);
                    int success = obj.getInt("status");

                    if (success == 1) {

                        JSONArray arr;

                        arr = obj.getJSONArray("data");
                        System.out.println("Arrayyyyyyyyyyyyyy ::::::::::::::: " + arr);

                        for (int i = 0; arr.length() > i; i++) {
                            JSONObject obj1 = arr.getJSONObject(i);

                            String country_id = obj1.getString(TAG_COUNTRY_ID);
                            String country_name = obj1.getString(TAG_COUNTRY_NAME);
                            String country_currency = obj1.getString(TAG_COUNTRY_CURRENCY);

                            Arraylist_country_id.add(country_id);
                            Arraylist_country_name.add(country_name);
                            Arraylist_country_currency.add(country_currency);
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

}