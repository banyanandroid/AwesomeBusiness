package banyan.com.awesomebusiness.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.JsonObject;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

import banyan.com.awesomebusiness.Activity_Register;
import banyan.com.awesomebusiness.R;
import banyan.com.awesomebusiness.api.RetroFitApi;
import banyan.com.awesomebusiness.global.AppConfig;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by SJR on 21-Jul-17.
 */

public class Activity_Filter extends AppCompatActivity {

    private Toolbar mToolbar;
    ProgressDialog pDialog;
    public static RequestQueue queue;
    String TAG = "reg";
    static String url_register = "http://epictech.in/apiawesome/index.php/transaction";
    static String url_register1 = "http://epictech.in/apiawesome/index.php/transaction/createtransaction";

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

        //logging();

        pDialog = new ProgressDialog(Activity_Filter.this);
        pDialog.setMessage("Please wait...");
        pDialog.show();
        pDialog.setCancelable(false);
        System.out.println("CALLED 00000");
        queue = Volley.newRequestQueue(Activity_Filter.this);
        Function_Register();

    }

    public void logging() {
        int flag = 1;

        RestAdapter adapter = new RestAdapter.Builder()
                .setEndpoint(AppConfig.BASE_URL) //Setting the Root URL
                .build();
       JsonObject obj = new JsonObject();
       /*  obj.addProperty("action_type", flag);
        obj.addProperty("email", email);
        obj.addProperty("password", password);
        obj.addProperty("phone", phone);
        obj.addProperty("f_name", name);
        obj.addProperty("l_name", " ");
        obj.addProperty("lat", str_lat);
        obj.addProperty("lon", str_long);
        obj.addProperty("city", str_city);
        obj.addProperty("state", str_state);
        obj.addProperty("country", str_country);
        obj.addProperty("zip", str_zipcode);
        obj.addProperty("street", str_address);
        obj.addProperty("street_no", str_address);*/

        Log.d("register", obj.toString());
        RetroFitApi.Register api = adapter.create(RetroFitApi.Register.class);

        api.registerapi(
                obj,
                new Callback<Response>() {
                    @Override
                    public void success(Response result, Response response) {

                        try {

                            BufferedReader reader = new BufferedReader(new InputStreamReader(result.getBody().in()));
                            String resp;
                            resp = reader.readLine();
                            Log.d("Response", "" + resp);

                            JSONObject jObj = new JSONObject(resp);
                            int status = jObj.getInt("status");
//                            String message = jObj.getString("count");
                            if (status == 1) {
                                Toast.makeText(getApplicationContext(), status, Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(getApplicationContext(), status, Toast.LENGTH_SHORT).show();
                            }

                        } catch (Exception e) {
                            Log.d("Exception", e.toString());
                        }
                        // MD.dismiss();
                    }

                    @Override
                    public void failure(RetrofitError error) {
                        Log.d("dfdf", error.toString());
                        //if (error.getResponse().getStatus() == 404) {
                        Toast.makeText(Activity_Filter.this, "Server Not Found", Toast.LENGTH_LONG).show();
                        // }
                        // MD.dismiss();
                    }
                }
        );
    }

    /*private void Function_Register() {
        StringRequest request = new StringRequest(Request.Method.POST,
                url_register, new com.android.volley.Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d(TAG, response.toString());
                Log.d("USER_REGISTER", response.toString());
                try {
                    JSONObject obj = new JSONObject(response);
                    int success = obj.getInt("success");

                    System.out.println("REG" + success);

                    if (success == 1) {


                        System.out.println("REEEGGGG ::::  Sucess");

                    } else if (success == 2) {


                    } else {

                        System.out.println("REEEGGGG ::::  FAILED");
                    }
                } catch (JSONException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                pDialog.hide();
            }
        }, new com.android.volley.Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                pDialog.hide();
            }
        }) {
            @Override
            public byte[] getBody() throws AuthFailureError {
                HashMap<String, String> params2 = new HashMap<String, String>();
                params2.put("name", "Val");
                params2.put("subject", "Test Subject");
                return new JSONObject(params2).toString().getBytes();
            }

            @Override
            public String getBodyContentType() {
                return "application/json";
            }
        };

        // Adding request to request queue
        queue.add(request);
    }*/

    private void Function_Register() {

        System.out.println("CALLED 1111111111");
        System.out.println("CALLED " + url_register);
        StringRequest request = new StringRequest(Request.Method.POST,
                url_register1, new com.android.volley.Response.Listener<String>() {

            @Override
            public void onResponse(String response) {

                System.out.println("CALLED 33333333333333");
                Log.d(TAG, response.toString());
                Log.d("USER_REGISTER", response.toString());
                System.out.println("CALLED 33333333333333");
                try {
                    JSONObject obj = new JSONObject(response);
                    int status = obj.getInt("status");

                    System.out.println("REG" + status);

                    if (status == 1) {


                        System.out.println("REEEGGGG ::::  Sucess");

                    } else if (status == 2) {


                    } else {

                        System.out.println("REEEGGGG ::::  FAILED");
                    }
                } catch (JSONException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                pDialog.hide();
            }
        }, new com.android.volley.Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                pDialog.hide();
            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();


                System.out.println("CALLED 22222222222222222222");

                params.put("business_interest_name", "business_interest_name");

                System.out.println("business_interest_name" + "business_interest_name");

                return params;
            }

        };

        // Adding request to request queue
        queue.add(request);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        hidePDialog();
    }

    private void hidePDialog() {
        if (pDialog != null) {
            pDialog.dismiss();
            pDialog = null;
        }
    }
}
