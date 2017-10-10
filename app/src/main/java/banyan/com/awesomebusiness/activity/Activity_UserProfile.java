package banyan.com.awesomebusiness.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.sdsmdg.tastytoast.TastyToast;
import com.tapadoo.alerter.Alerter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import banyan.com.awesomebusiness.R;
import banyan.com.awesomebusiness.adapter.List_BusinessProfiles_Adapter;
import banyan.com.awesomebusiness.adapter.List_RecentActivities_Adapter;
import banyan.com.awesomebusiness.global.AppConfig;
import banyan.com.awesomebusiness.global.SessionManager;
import de.hdodenhof.circleimageview.CircleImageView;
import dmax.dialog.SpotsDialog;


public class Activity_UserProfile extends AppCompatActivity {

    private Toolbar mToolbar;

    TextView txt_username, txt_user_email, txt_user_mobile,
            txt_user_current_role, txt_user_company;

    TextView txt_edit, txt_change_password, txt_myprofile;

    ImageView img_edit, img_change_password;

    CircleImageView img_profile_picture;

    // For Changing Password
    EditText edt_new_password, edt_repeat_new_password;
    String str_new_password, str_repeat_new_password = "";

    // Session Manager Class
    SessionManager session;
    SpotsDialog dialog;
    public static String str_user_id, str_user_name, str_user_email, str_user_photoo;

    String str_profile_user_id, str_profile_user_key, str_profile_user_name, str_profile_user_email, str_user_mobile,
            str_user_gst_num, str_user_location, str_user_timezone, str_user_company_name, str_user_address,
            str_user_designation, str_user_business_proposalEmail, str_user_dealsize,
            str_user_business_proposalNotify, str_user_photo, str_user_created_on = "";

    public static RequestQueue queue;

    static ArrayList<HashMap<String, String>> Recent_Activities_List;

    HashMap<String, String> params = new HashMap<String, String>();

   // private ListView List;
    public List_RecentActivities_Adapter adapter;

    String TAG = "";

    public static final String TAG_USER_ID = "user_id";
    public static final String TAG_USER_KEY = "user_key";
    public static final String TAG_USER_NAME = "user_name";
    public static final String TAG_USER_EMAIL = "user_email";
    public static final String TAG_USER_MOBILE = "user_mobile";
    public static final String TAG_USER_GST_NUMBER = "user_gst_number";
    public static final String TAG_USER_LOCATION = "user_location";
    public static final String TAG_USER_TIME_ZONE = "user_time_zone";
    public static final String TAG_USER_COMPANY_NAME = "user_company_name";
    public static final String TAG_USER_ADDRESS = "user_address";
    public static final String TAG_USER_DESIGINATION = "user_designation";
    public static final String TAG_USER_BUSINESS_PROPOSAL_EMAIL = "user_business_proposals_email";
    public static final String TAG_USER_DEALSIZE = "user_dealsize";
    public static final String TAG_USER_BUSINESS_PROPOSAL_NOTIFY = "user_business_proposals_notify";
    public static final String TAG_USER_PHOTO = "user_photo";
    public static final String TAG_USER_CREATEDON = "user_createdon";

    public static final String TAG_PREF_LOCATION_ID = "prefer_location_id";
    public static final String TAG_PREF_LOCATION_TYPE = "prefer_location_type";
    public static final String TAG_PREF_USER_ID = "prefer_user_id";
    public static final String TAG_PREF_LOCATION_CREATEON = "preferences_location_createdon";
    public static final String TAG_PREF_LOCATION_ACT = "preferences_location_act";

    public static final String TAG_PREF_INDUSTRIES_ID = "preferences_industries_id";
    public static final String TAG_PREF_INDUSTRIES_TYPE = "preferences_industry_type";
    public static final String TAG_PREF_ID = "preferences_user_id";
    public static final String TAG_PREF_CREATEON = "preferences_createdon";
    public static final String TAG_PREF_ACT = "preferences_act";

    public static final String TAG_DETAILS = "details";
    public static final String TAG_TYPE = "type";
    public static final String TAG_KEY = "key";
    public static final String TAG_CREATED_ON = "created_on";

    ArrayList<String> Arraylist_sector = null;
    ArrayList<String> Arraylist_location = null;

    public static TabLayout tabLayout;
    public static ViewPager viewPager;
    public static int int_items = 3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        session = new SessionManager(getApplicationContext());
        session.checkLogin();
        HashMap<String, String> user = session.getUserDetails();
        // name
        str_user_name = user.get(SessionManager.KEY_USER);
        str_user_email = user.get(SessionManager.KEY_USER_EMAIL);
        str_user_photoo = user.get(SessionManager.KEY_USER_PHOTO);
        str_user_id = user.get(SessionManager.KEY_USER_ID);

        Arraylist_sector = new ArrayList<String>();
        Arraylist_location = new ArrayList<String>();

        mToolbar.setNavigationIcon(getResources().getDrawable(R.drawable.ic_action_back));
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(i);
                finish();
            }
        });

        tabLayout = (TabLayout) findViewById(R.id.tabs_profile);
        viewPager = (ViewPager) findViewById(R.id.viewpager_profile);

        /**
         *Set an Apater for the View Pager
         */
        viewPager.setAdapter(new MyAdapter(getSupportFragmentManager()));

        /**
         * Now , this is a workaround ,
         * The setupWithViewPager dose't works without the runnable .
         * Maybe a Support Library Bug .
         */

        tabLayout.post(new Runnable() {
            @Override
            public void run() {
                tabLayout.setupWithViewPager(viewPager);
            }
        });


        img_profile_picture = (CircleImageView) findViewById(R.id.user_profile_picture);
        txt_edit = (TextView) findViewById(R.id.profile_txt_edit);

        txt_username = (TextView) findViewById(R.id.userprofile_txt_username);
        txt_user_email = (TextView) findViewById(R.id.userprofile_txt_user_email);
        txt_user_mobile = (TextView) findViewById(R.id.userprofile_txt_user_mobile);
        txt_user_current_role = (TextView) findViewById(R.id.userprofile_txt_user_role);
        txt_user_company = (TextView) findViewById(R.id.userprofile_txt_user_company);
        txt_change_password = (TextView) findViewById(R.id.profile_txt_editpassword);
        txt_myprofile = (TextView) findViewById(R.id.profile_txt_myprofile);

      //  List = (ListView) findViewById(R.id.user_activity_listview);

        // Hashmap for ListView
        Recent_Activities_List = new ArrayList<HashMap<String, String>>();

        //ON CLICKING THIS WILL DIRECT TO TAB LAYOUT OF USER PROFILES
        txt_myprofile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(getApplicationContext(), Activity_User_Profiles.class);
                startActivity(i);
                finish();

            }
        });

        txt_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Function_Edit_profile();
            }
        });

        txt_change_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Function_AlertDialog();
            }
        });


        try {
            dialog = new SpotsDialog(Activity_UserProfile.this);
            dialog.show();
            queue = Volley.newRequestQueue(Activity_UserProfile.this);
            Get_User_Profile();

        } catch (Exception e) {
            // TODO: handle exceptions
        }

    }

    class MyAdapter extends FragmentPagerAdapter {

        public MyAdapter(FragmentManager fm) {
            super(fm);
        }

        /**
         * Return fragment with respect to Position .
         */

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    return new Tab_Profile_Viewed();
                case 1:
                    return new Tab_Profile_Contacted();
                case 2:
                    return new Tab_Profile_Bookmarks();
            }
            return null;
        }

        @Override
        public int getCount() {

            return int_items;

        }

        /**
         * This method returns the title of the tab according to the position.
         */

        @Override
        public CharSequence getPageTitle(int position) {

            switch (position) {
                case 0:
                    return "Viewed";
                case 1:
                    return "Contacted";
                case 2:
                    return "Bookmarks";

            }
            return null;
        }
    }

    /*****************************
     * Edit Profile
     ***************************/

    public void Function_Edit_profile() {

        Intent i = new Intent(getApplicationContext(), Activity_UserProfile_Update.class);
        startActivity(i);
        finish();

    }

    /*****************************
     * GET User Profile
     ***************************/

    public void Get_User_Profile() {


        StringRequest request = new StringRequest(Request.Method.POST,
                AppConfig.url_user_profile, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d(TAG, response.toString());
                System.out.println("CAME RESPONSE ::: " + response.toString());

                try {
                    JSONObject obj = new JSONObject(response);
                    int success = obj.getInt("status");

                    if (success == 1) {
                        String str_obj = obj.getString("data");
                        JSONObject obj_data = new JSONObject(str_obj);

                        str_profile_user_id = obj_data.getString(TAG_USER_ID);
                        str_profile_user_key = obj_data.getString(TAG_USER_KEY);
                        str_profile_user_name = obj_data.getString(TAG_USER_NAME);
                        str_profile_user_email = obj_data.getString(TAG_USER_EMAIL);
                        str_user_mobile = obj_data.getString(TAG_USER_MOBILE);
                        str_user_gst_num = obj_data.getString(TAG_USER_GST_NUMBER);
                        str_user_location = obj_data.getString(TAG_USER_LOCATION);
                        str_user_timezone = obj_data.getString(TAG_USER_TIME_ZONE);
                        str_user_company_name = obj_data.getString(TAG_USER_COMPANY_NAME);
                        str_user_address = obj_data.getString(TAG_USER_ADDRESS);
                        str_user_designation = obj_data.getString(TAG_USER_DESIGINATION);
                        str_user_business_proposalEmail = obj_data.getString(TAG_USER_BUSINESS_PROPOSAL_EMAIL);
                        str_user_dealsize = obj_data.getString(TAG_USER_DEALSIZE);
                        str_user_business_proposalNotify = obj_data.getString(TAG_USER_BUSINESS_PROPOSAL_NOTIFY);
                        str_user_photo = obj_data.getString(TAG_USER_PHOTO);
                        str_user_created_on = obj_data.getString(TAG_USER_CREATEDON);

                        try {
                            Glide.with(getApplicationContext())
                                    .load(str_user_photo)
                                    .placeholder(R.drawable.placeholder)
                                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                                    .into(img_profile_picture);

                            txt_username.setText("" + str_profile_user_name);
                            txt_user_email.setText("" + str_profile_user_email);
                            txt_user_mobile.setText("" + str_user_mobile);
                            txt_user_current_role.setText("" + str_user_designation + " | ");
                            txt_user_company.setText(" " + str_user_company_name);

                            SharedPreferences sharedPreferences = PreferenceManager
                                    .getDefaultSharedPreferences(getApplicationContext());
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.putString("str_profile_user_name", str_profile_user_name);
                            editor.putString("str_profile_user_email", str_profile_user_email);
                            editor.putString("str_user_mobile", str_user_mobile);
                            editor.putString("str_user_designation", str_user_designation);
                            editor.putString("str_user_company_name", str_user_company_name);
                            editor.commit();

                        } catch (Exception e) {

                        }
                        dialog.dismiss();
                       /* try {
                            dialog = new SpotsDialog(Activity_UserProfile.this);
                            dialog.show();
                            queue = Volley.newRequestQueue(Activity_UserProfile.this);
                            Get_Recent_Activities();
                        } catch (Exception e) {

                        }*/

                    } else if (success == 0) {

                        dialog.dismiss();

                        Alerter.create(Activity_UserProfile.this)
                                .setTitle("WORLD BUSINESSES FOR SALE")
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
                Alerter.create(Activity_UserProfile.this)
                        .setTitle("WORLD BUSINESSES FOR SALE")
                        .setText("Internal Error :(\n" + error.getMessage())
                        .setBackgroundColor(R.color.colorPrimaryDark)
                        .show();
            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();

                params.put("user_id", str_user_id);

                System.out.println("USER_ID ::: " + str_user_id);


                return params;
            }

        };

        // Adding request to request queue
        queue.add(request);
    }

    /*****************************
     * Alert Dialog To Chande Password
     ***************************/

    public void Function_AlertDialog() {

        LayoutInflater li = LayoutInflater.from(this);
        View promptsView = li.inflate(R.layout.popup_change_user_password, null);

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                this);

        alertDialogBuilder.setView(promptsView);


        edt_new_password = (EditText) promptsView.findViewById(R.id.popup_edt_newpassword);
        edt_repeat_new_password = (EditText) promptsView.findViewById(R.id.popup_edt_repeat_newpassword);

       /* str_new_password = edt_new_password.getText().toString();
        str_repeat_new_password = edt_repeat_new_password.getText().toString();
*/

        // set dialog message
        alertDialogBuilder
                .setCancelable(false)
                .setTitle("Change Your Account Password")
                .setPositiveButton("Save", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {


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

        alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                str_new_password = edt_new_password.getText().toString();
                str_repeat_new_password = edt_repeat_new_password.getText().toString();


                if (str_new_password.equals("")) {
                    edt_new_password.setError("Cannot Be Empty");
                    TastyToast.makeText(getApplicationContext(), "This Field Cannot be empty", TastyToast.LENGTH_LONG, TastyToast.WARNING);
                    return;
                } else if (str_repeat_new_password.equals("")) {
                    edt_repeat_new_password.setError("Cannot Be Empty");
                    TastyToast.makeText(getApplicationContext(), "This Field Cannot be empty", TastyToast.LENGTH_LONG, TastyToast.WARNING);
                    return;
                } else if (str_new_password.length() < 6) {
                    edt_new_password.setError("Minimum 6 characters");
                    TastyToast.makeText(getApplicationContext(), "Password Should Containt Atleast 6 Characters", TastyToast.LENGTH_LONG, TastyToast.WARNING);
                    return;
                } else if (!str_repeat_new_password.equals(str_new_password)) {
                    edt_repeat_new_password.setError("Passwords Do Not Match ");
                    TastyToast.makeText(getApplicationContext(), "Repeat your new password here", TastyToast.LENGTH_LONG, TastyToast.WARNING);
                    return;
                } else {
                    TastyToast.makeText(getApplicationContext(), "New Password" + str_new_password, TastyToast.LENGTH_LONG, TastyToast.SUCCESS);
                    dialog = new SpotsDialog(Activity_UserProfile.this);
                    dialog.show();
                    queue = Volley.newRequestQueue(Activity_UserProfile.this);
                    Function_Change_Password();
                }
            }
        });

    }

    /******************************************
     *    Change User Password
     * *******************************************/

    private void Function_Change_Password() {

        StringRequest request = new StringRequest(Request.Method.POST,
                AppConfig.url_change_password, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d(TAG, response.toString());

                try {
                    JSONObject obj = new JSONObject(response);
                    System.out.println("RESPONSE : " + response);
                    int success = obj.getInt("status");
                    if (success == 1) {
                        dialog.dismiss();

                        Alerter.create(Activity_UserProfile.this)
                                .setTitle("Success")
                                .setText("Your password was changed successfully....!")
                                .setBackgroundColor(R.color.colorAccent)
                                .show();

                        Intent i = new Intent(getApplicationContext(), Activity_UserProfile.class);
                        startActivity(i);
                        finish();


                    } else {
                        dialog.dismiss();
                        TastyToast.makeText(getApplicationContext(), "Oops...! Cant Change Password Now :(", TastyToast.LENGTH_LONG, TastyToast.ERROR);
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

                params.put("email_id", str_user_email);
                params.put("password", str_new_password);

                ////////////////

                System.out.println("Email IDDDDDD" + str_user_email);
                System.out.println("New PassworDDDDD" + str_new_password);


                return params;
            }
        };
        queue.add(request);
    }


    /*****************************
     * GET User Profile
     ***************************/

    public void Get_Recent_Activities() {

        StringRequest request = new StringRequest(Request.Method.POST,
                AppConfig.url_recent_activities, new Response.Listener<String>() {
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
                            JSONObject obj_data = arr.getJSONObject(i);

                            String str_details = obj_data.getString(TAG_DETAILS);
                            String str_type = obj_data.getString(TAG_TYPE);
                            String str_key = obj_data.getString(TAG_KEY);
                            String str_created_on = obj_data.getString(TAG_CREATED_ON);

                            // creating new HashMap
                            HashMap<String, String> map = new HashMap<String, String>();
                            // adding each child node to HashMap key => value
                            map.put(TAG_DETAILS, str_details);
                            map.put(TAG_TYPE, str_type);
                            map.put(TAG_KEY, str_key);
                            map.put(TAG_CREATED_ON, str_created_on);

                            Recent_Activities_List.add(map);

                            adapter = new List_RecentActivities_Adapter(Activity_UserProfile.this,
                                    Recent_Activities_List);
                          //  List.setAdapter(adapter);
                            System.out.println("HASHMAP ARRAY" + Recent_Activities_List);
                        }
                        dialog.dismiss();
                    } else if (success == 0) {

                        dialog.dismiss();

                        Alerter.create(Activity_UserProfile.this)
                                .setTitle("WORLD BUSINESSES FOR SALE")
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
                Alerter.create(Activity_UserProfile.this)
                        .setTitle("WORLD BUSINESSES FOR SALE")
                        .setText("Internal Error :(\n" + error.getMessage())
                        .setBackgroundColor(R.color.colorPrimaryDark)
                        .show();
            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();

                params.put("user_id", str_user_id);

                System.out.println("USER_ID ::: " + str_user_id);

                return params;
            }

        };

        // Adding request to request queue
        queue.add(request);
    }

}