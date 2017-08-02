package banyan.com.awesomebusiness.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.percent.PercentLayoutHelper;
import android.support.percent.PercentRelativeLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.sdsmdg.tastytoast.TastyToast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import banyan.com.awesomebusiness.R;
import banyan.com.awesomebusiness.global.AppConfig;
import dmax.dialog.SpotsDialog;

/**
 * Created by Schan on 24-Jul-17.
 */

public class Activity_BusinessProfile extends AppCompatActivity {

    private Toolbar mToolbar;
    SpotsDialog dialog;
    public static RequestQueue queue;
    Button btn_submit;
    String TAG = "I_am";
    TextView t1 , t2;
    AutoCompleteTextView auto_i_am, auto_interested_in;

    public static final String TAG_ROLE_KEY = "business_role_key";
    public static final String TAG_ROLE_NAME = "business_role_name";

    public static final String TAG_INTEREST_KEY ="business_interest_key";
    public static final String TAG_INTEREST_NAME = "business_interest_name";

    ArrayList<String> Arraylist_business_role_key = null;
    ArrayList<String> Arraylist_business_role_name = null;

    ArrayList<String> Arraylist_business_interest_key = null;
    ArrayList<String> Arraylist_business_interest_name = null;

    private ArrayAdapter<String> adapter_i_am;
    ArrayList<String> stringArray_i_am = null;

    private ArrayAdapter<String> adapter_interested;
    ArrayList<String> stringArray_interested = null;


    String str_selected_role_key, str_selected_role_name = "";
    String str_selected_interest_key, str_selected_interest_name = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_business_profile);

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

        auto_i_am = (AutoCompleteTextView) findViewById(R.id.business_profile_autocomp_i_am);
        auto_interested_in = (AutoCompleteTextView) findViewById(R.id.business_profile_autocomp_intersted);

        Arraylist_business_role_key = new ArrayList<String>();
        Arraylist_business_role_name = new ArrayList<String>();

        Arraylist_business_interest_key = new ArrayList<String>();
        Arraylist_business_interest_name = new ArrayList<String>();

        btn_submit = (Button) findViewById(R.id.btn_submit);

        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Business Profile Created", Toast.LENGTH_LONG).show();


            }
        });

        try {
            dialog = new SpotsDialog(Activity_BusinessProfile.this);
            dialog.show();
            queue = Volley.newRequestQueue(getApplicationContext());
            Get_Iam_an();
            queue = Volley.newRequestQueue(getApplicationContext());
            Get_Interested();

        } catch (Exception e) {
            // TODO: handle exception
        }


    }


    /*****************************
     * To get  I am/an  Details
     ***************************/

    public void Get_Iam_an() {
        String tag_json_obj = "json_obj_req";
        System.out.println("SSSTTTTEEEPPP  1111");
        StringRequest request = new StringRequest(Request.Method.GET,
                AppConfig.url_i_am_an, new Response.Listener<String>() {

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
                            JSONObject obj1 = arr.getJSONObject(i);

                            String role_key = obj1.getString(TAG_ROLE_KEY);
                            String role_name = obj1.getString(TAG_ROLE_NAME);

                            Arraylist_business_role_key.add(role_key);
                            Arraylist_business_role_name.add(role_name);
                        }
                        try {
                            adapter_i_am = new ArrayAdapter<String>(Activity_BusinessProfile.this,
                                    android.R.layout.simple_list_item_1, Arraylist_business_role_name);
                            auto_i_am.setAdapter(adapter_i_am);
                            auto_i_am.setThreshold(1);

                            auto_i_am.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
                                                        long arg3) {
                                    t1 = (TextView) arg1;
                                    str_selected_role_name = t1.getText().toString();
                                    System.out.println("Argument " + arg2);
                                    str_selected_role_key = Arraylist_business_role_key.get(arg2 + 1);
                                }
                            });

                        } catch (Exception e) {

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


    /*****************************
     * To get  I am/an  Details
     ***************************/

    public void Get_Interested() {
        String tag_json_obj = "json_obj_req";
        System.out.println("SSSTTTTEEEPPP  1111");
        StringRequest request = new StringRequest(Request.Method.GET,
                AppConfig.url_interested_in, new Response.Listener<String>() {

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
                            JSONObject obj1 = arr.getJSONObject(i);

                            String interest_key = obj1.getString(TAG_INTEREST_KEY);
                            String interest_name = obj1.getString(TAG_INTEREST_NAME);

                            Arraylist_business_interest_key.add(interest_key);
                            Arraylist_business_interest_name.add(interest_name);
                        }
                        try {
                            adapter_interested = new ArrayAdapter<String>(Activity_BusinessProfile.this,
                                    android.R.layout.simple_list_item_1, Arraylist_business_role_name);
                            auto_interested_in.setAdapter(adapter_interested);
                            auto_interested_in.setThreshold(1);

                            auto_interested_in.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
                                                        long arg3) {
                                    t2 = (TextView) arg1;
                                    str_selected_interest_name = t2.getText().toString();
                                    System.out.println("Argument " + arg2);
                                    str_selected_interest_key = Arraylist_business_interest_key.get(arg2 + 1);
                                }
                            });

                        } catch (Exception e) {

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
