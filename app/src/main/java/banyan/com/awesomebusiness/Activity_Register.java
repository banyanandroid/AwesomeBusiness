package banyan.com.awesomebusiness;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gcm.GCMRegistrar;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import static banyan.com.awesomebusiness.CommonUtilities.SENDER_ID;

/**
 * Created by User on 7/20/2017.
 */

public class Activity_Register extends AppCompatActivity {

    Button btn_reg;
    String GcmId = null;
    ProgressDialog pDialog;
    public static RequestQueue queue;
    String TAG = "reg";
    static String url_register = "http://www.epictech.in/awesome/android/reg.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        btn_reg = (Button) findViewById(R.id.button);

        btn_reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Get_GCMID();

            }
        });


    }

    private void Get_GCMID() {
        GcmId = GCMRegistrar.getRegistrationId(Activity_Register.this);

        if (GcmId.isEmpty()) {

            try {
                System.out.println("IaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaD Empty");
                GCMRegistrar.register(Activity_Register.this, SENDER_ID);
                // System.out.println("IaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaD Empty"+GCMIntentService.regid);

                GcmId = GCMRegistrar.getRegistrationId(this);
                System.out.println("IaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaD Empty" + GcmId);

            } catch (Exception e) {

            }

        } else {
            Toast.makeText(getApplicationContext(), GcmId, Toast.LENGTH_LONG)
                    .show();
            System.out.println("neeeeeeeeeeeeeeeeeeeeeeeeeeeeewwwwwww" + GcmId);


            pDialog = new ProgressDialog(Activity_Register.this);
            pDialog.setMessage("Please wait...");
            pDialog.show();
            pDialog.setCancelable(false);
            queue = Volley.newRequestQueue(Activity_Register.this);
            Function_Register();

            if (GCMRegistrar.isRegisteredOnServer(Activity_Register.this)) {
                // Skips registration.

            }
        }
    }

    private void Function_Register() {
        StringRequest request = new StringRequest(Request.Method.POST,
                url_register, new Response.Listener<String>() {

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
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                pDialog.hide();
            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();

                params.put("gcm", GcmId);

                System.out.println("gcm" + GcmId);

                return params;
            }

        };

        // Adding request to request queue
        queue.add(request);
    }

    // Need To update this
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
