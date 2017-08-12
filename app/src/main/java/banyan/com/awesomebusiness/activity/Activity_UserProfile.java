package banyan.com.awesomebusiness.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.tapadoo.alerter.Alerter;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import banyan.com.awesomebusiness.R;
import banyan.com.awesomebusiness.global.AppConfig;
import banyan.com.awesomebusiness.global.SessionManager;
import de.hdodenhof.circleimageview.CircleImageView;
import dmax.dialog.SpotsDialog;


public class Activity_UserProfile extends AppCompatActivity {

    private Toolbar mToolbar;

    TextView txt_username, txt_user_email, txt_user_mobile,
            txt_user_current_role, txt_user_company;

    TextView txt_edit;

    ImageView img_edit;

    CircleImageView img_profile_picture;

    // Session Manager Class
    SessionManager session;
    SpotsDialog dialog;
    public static String str_user_id, str_user_name, str_user_email, str_user_photoo;

    String str_profile_user_id, str_profile_user_key, str_profile_user_name, str_profile_user_email, str_user_mobile,
            str_user_gst_num, str_user_location, str_user_timezone, str_user_company_name, str_user_address,
            str_user_designation, str_user_business_proposalEmail, str_user_dealsize,
            str_user_business_proposalNotify, str_user_facebook, str_user_google, str_user_linkedin,
            str_user_facebook_id, str_user_google_id, str_user_linkedin_id, str_user_ip,
            str_user_photo, str_user_created_on = "";

    public static RequestQueue queue;

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
    public static final String TAG_USER_FACEBOOK = "user_facebook";
    public static final String TAG_USER_LINKEDIN = "user_linkedin";
    public static final String TAG_USER_GOOGLE = "user_google";
    public static final String TAG_USER_FACEBOOK_ID = "user_facebook_id";
    public static final String TAG_USER_GOOGLE_ID = "user_google_id";
    public static final String TAG_USER_LINKEDIN_ID = "user_linkedin_id";
    public static final String TAG_USER_IP = "user_ip_address";
    public static final String TAG_USER_PHOTO = "user_photo";
    public static final String TAG_USER_CREATEDON = "user_createdon";

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

        mToolbar.setNavigationIcon(getResources().getDrawable(R.drawable.ic_action_back));
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(i);
                finish();
            }
        });


        img_profile_picture = (CircleImageView) findViewById(R.id.user_profile_picture);
        img_edit = (ImageView) findViewById(R.id.profile_img_edit);
        txt_edit = (TextView) findViewById(R.id.profile_txt_edit);

        txt_username = (TextView) findViewById(R.id.userprofile_txt_username);
        txt_user_email = (TextView) findViewById(R.id.userprofile_txt_user_email);
        txt_user_mobile = (TextView) findViewById(R.id.userprofile_txt_user_mobile);
        txt_user_current_role = (TextView) findViewById(R.id.userprofile_txt_user_role);
        txt_user_company = (TextView) findViewById(R.id.userprofile_txt_user_company);

        img_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Function_Edit_profile();
            }
        });
        txt_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Function_Edit_profile();
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
                        str_user_facebook = obj_data.getString(TAG_USER_FACEBOOK);
                        str_user_google = obj_data.getString(TAG_USER_GOOGLE);
                        str_user_linkedin = obj_data.getString(TAG_USER_LINKEDIN);
                        str_user_facebook_id = obj_data.getString(TAG_USER_FACEBOOK_ID);
                        str_user_google_id = obj_data.getString(TAG_USER_GOOGLE_ID);
                        str_user_linkedin_id = obj_data.getString(TAG_USER_LINKEDIN_ID);
                        str_user_ip = obj_data.getString(TAG_USER_IP);
                        str_user_photo = obj_data.getString(TAG_USER_PHOTO);
                        str_user_created_on = obj_data.getString(TAG_USER_CREATEDON);

                        try {

                            Glide.with(getApplicationContext()).load(str_user_photo)
                                    .thumbnail(0.5f)
                                    .crossFade()
                                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                                    .into(img_profile_picture);

                            txt_username.setText("" + str_profile_user_name);
                            txt_user_email.setText("" + str_profile_user_email);
                            txt_user_mobile.setText("" + str_user_mobile);
                            txt_user_current_role.setText("" + str_user_designation);
                            txt_user_company.setText("" + str_user_company_name);


                        } catch (Exception e) {

                        }

                        dialog.dismiss();
                    } else if (success == 0) {

                        dialog.dismiss();

                        Alerter.create(Activity_UserProfile.this)
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
                Alerter.create(Activity_UserProfile.this)
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

                System.out.println("USER_ID ::: " + str_user_id);


                return params;
            }

        };

        // Adding request to request queue
        queue.add(request);
    }


}