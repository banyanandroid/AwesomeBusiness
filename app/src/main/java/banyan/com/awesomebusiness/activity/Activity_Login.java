package banyan.com.awesomebusiness.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.percent.PercentLayoutHelper;
import android.support.percent.PercentRelativeLayout;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.Profile;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.sdsmdg.tastytoast.TastyToast;
import com.tapadoo.alerter.Alerter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import banyan.com.awesomebusiness.R;
import banyan.com.awesomebusiness.global.AppConfig;
import dmax.dialog.SpotsDialog;


/**
 * Created by Jo on 22/07/2017.
 */

public class Activity_Login extends AppCompatActivity implements View.OnClickListener,
        GoogleApiClient.OnConnectionFailedListener {

    private TextView tvSignupInvoker;
    private LinearLayout llSignup;
    private TextView tvSigninInvoker;
    private LinearLayout llSignin;
    private Button btnSignup;
    private Button btnSignin;
    LoginButton loginButton;
    EditText edt_email , edt_password , edt_singup_email , edt_signup_pass , edt_signup_repeat_pass ;

    private static final String TAG = Activity_Login.class.getSimpleName();
    private static final String TAG_login = "Login";
    private static final int RC_SIGN_IN = 100;

    CallbackManager callbackManager;

    private GoogleApiClient mGoogleApiClient;

    private SignInButton btn_GoogleSignIn;

    public static RequestQueue queue;

    String str_signup_email , str_signup_pass , str_signup_repeat_pass;

    SpotsDialog dialog;

    private static final String TAG_EMAIL = "user_email";
    private static final String TAG_PASSWORD = "password";

    Button  btn_fb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        callbackManager = CallbackManager.Factory.create();

        tvSignupInvoker = (TextView) findViewById(R.id.tvSignupInvoker);
        tvSigninInvoker = (TextView) findViewById(R.id.tvSigninInvoker);

        btn_GoogleSignIn = (SignInButton) findViewById(R.id.sign_in_button);
        btn_GoogleSignIn.setSize(SignInButton.SIZE_STANDARD);

        btnSignup = (Button) findViewById(R.id.btnSignup);
        btnSignin = (Button) findViewById(R.id.btnSignin);



       // btn_fb = (Button) findViewById(R.id.btnSignin_fb);

        edt_email = (EditText) findViewById(R.id.edt_email);
        edt_password = (EditText) findViewById(R.id.edt_password);
        edt_singup_email = (EditText) findViewById(R.id.edt_signup_email);
        edt_signup_pass = (EditText) findViewById(R.id.edt_signup_password);
        edt_signup_repeat_pass = (EditText) findViewById(R.id.edt_signup_repeat_pass);

        llSignup = (LinearLayout) findViewById(R.id.llSignup);
        llSignin = (LinearLayout) findViewById(R.id.llSignin);

        loginButton = (LoginButton)findViewById(R.id.login_button);
        loginButton.setReadPermissions("email");

        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {

               String facebook_id ,f_name , m_name , l_name , gender ,profile_image , full_name , email_id = "";

                if(AccessToken.getCurrentAccessToken() != null){
                    RequestData();
                    Profile profile = Profile.getCurrentProfile();
                    if (profile != null) {
                        facebook_id=profile.getId();
                        Log.e("facebook_id", facebook_id);
                        f_name=profile.getFirstName();
                        Log.e("f_name", f_name);
                        m_name=profile.getMiddleName();
                        Log.e("m_name", m_name);
                        l_name=profile.getLastName();
                        Log.e("l_name", l_name);
                        full_name=profile.getName();
                        Log.e("full_name", full_name);
                        profile_image=profile.getProfilePictureUri(400, 400).toString();
                        Log.e("profile_image", profile_image);


                        Toast.makeText(getApplicationContext(), "Successs" + "Fb_id" + facebook_id
                                + "FullName" + full_name , Toast.LENGTH_LONG).show();
                    }

                }

                Toast.makeText(getApplicationContext(), "Successs" ,Toast.LENGTH_LONG).show();

            }

            @Override
            public void onCancel() {

                Toast.makeText(getApplicationContext(), "Cancelled", Toast.LENGTH_LONG).show();

            }

            @Override
            public void onError(FacebookException error) {

                Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_LONG).show();

            }
        });




        findViewById(R.id.sign_in_button).setOnClickListener(this);

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this /* FragmentActivity */, this /* OnConnectionFailedListener */)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();

        tvSignupInvoker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showSignupForm();
            }
        });

        tvSigninInvoker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showSigninForm();
            }
        });
        showSigninForm();

/*

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                user_email = edt_email.getText().toString();
                user_password = edt_password.getText().toString();

                if (user_email.equals("")) {
                    TastyToast.makeText(getApplicationContext(), "Please Enter User ID", TastyToast.LENGTH_SHORT, TastyToast.WARNING);
                    edt_email.setError("Please Enter Email ID");
                } else if (user_password.equals("")) {
                    TastyToast.makeText(getApplicationContext(), "Please Enter Password", TastyToast.LENGTH_SHORT, TastyToast.WARNING);
                    edt_password.setError("Please Enter Password");
                } else {

                    dialog = new SpotsDialog(Activity_Login.this);
                    dialog.show();
                    queue = Volley.newRequestQueue(Activity_Login.this);
                    Function_Login();
                }

            }
        });


*/
        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Animation clockwise = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.rotate_right_to_left);
                btnSignup.startAnimation(clockwise);
                str_signup_email = edt_singup_email.getText().toString();
                str_signup_pass = edt_signup_pass.getText().toString();
                str_signup_repeat_pass = edt_signup_repeat_pass.getText().toString();

                if (str_signup_email.equals("")) {
                    TastyToast.makeText(getApplicationContext(), "Enter Email ID", TastyToast.LENGTH_SHORT, TastyToast.WARNING);
                    edt_singup_email.setError("Please Enter Email ID");
                } else if (str_signup_pass.equals("")) {
                    TastyToast.makeText(getApplicationContext(), "Enter Password", TastyToast.LENGTH_SHORT, TastyToast.WARNING);
                    edt_signup_pass.setError("Please Enter Password");
                } else if (str_signup_repeat_pass.equals("")) {
                    TastyToast.makeText(getApplicationContext(), "Enter Password", TastyToast.LENGTH_SHORT, TastyToast.WARNING);
                    edt_signup_repeat_pass.setError("Please Enter Password");
                } else if (!(str_signup_pass.equals(str_signup_repeat_pass))) {
                    TastyToast.makeText(getApplicationContext(), "Passwords Do Not Match", TastyToast.LENGTH_SHORT, TastyToast.WARNING);
                    edt_signup_repeat_pass.setError("Please Repeat same Password");
                } else {

                    dialog = new SpotsDialog(Activity_Login.this);
                    dialog.show();
                    queue = Volley.newRequestQueue(Activity_Login.this);
                    Function_Register();
                }


            }
        });



        btnSignin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Animation clockwise = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.rotate_right_to_left);
                btnSignup.startAnimation(clockwise);
                str_signup_email = edt_singup_email.getText().toString();
                str_signup_pass = edt_signup_pass.getText().toString();
                str_signup_repeat_pass = edt_signup_repeat_pass.getText().toString();

                if (str_signup_email.equals("")) {
                    TastyToast.makeText(getApplicationContext(), "Enter Email ID", TastyToast.LENGTH_SHORT, TastyToast.WARNING);
                    edt_singup_email.setError("Please Enter Email ID");
                } else if (str_signup_pass.equals("")) {
                    TastyToast.makeText(getApplicationContext(), "Enter Password", TastyToast.LENGTH_SHORT, TastyToast.WARNING);
                    edt_signup_pass.setError("Please Enter Password");
                } else if (str_signup_repeat_pass.equals("")) {
                    TastyToast.makeText(getApplicationContext(), "Enter Password", TastyToast.LENGTH_SHORT, TastyToast.WARNING);
                    edt_signup_repeat_pass.setError("Please Enter Password");
                } else if (!(str_signup_pass.equals(str_signup_repeat_pass))) {
                    TastyToast.makeText(getApplicationContext(), "Passwords Do Not Match", TastyToast.LENGTH_SHORT, TastyToast.WARNING);
                    edt_signup_repeat_pass.setError("Please Repeat same Password");
                } else {

                    dialog = new SpotsDialog(Activity_Login.this);
                    dialog.show();
                    queue = Volley.newRequestQueue(Activity_Login.this);
                    Function_Register();
                }


            }
        });



    }

    public void RequestData(){
        GraphRequest request = GraphRequest.newMeRequest(AccessToken.getCurrentAccessToken(), new GraphRequest.GraphJSONObjectCallback() {
            @Override
            public void onCompleted(JSONObject object, GraphResponse response) {

                JSONObject json = response.getJSONObject();
                System.out.println("Json data :"+json);
                try {
                    if(json != null){
                        String text = "<b>Name :</b> "+json.getString("name")+"<br><br><b>Email :</b> "+json.getString("email")+"<br><br><b>Profile link :</b> "+json.getString("link");
                       /* details_txt.setText(Html.fromHtml(text));
                        profile.setProfileId(json.getString("id"));*/
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
        Bundle parameters = new Bundle();
        parameters.putString("fields", "id,name,link,email,picture");
        request.setParameters(parameters);
        request.executeAsync();
    }

    private void signIn() {
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }



    private void handleSignInResult(GoogleSignInResult result) {
        Log.d(TAG, "handleSignInResult:" + result.isSuccess());
        if (result.isSuccess()) {

            GoogleSignInAccount acct = result.getSignInAccount();

            Log.e(TAG, "display name: " + acct.getDisplayName());

            String personName = acct.getDisplayName();
            try {
                String personPhotoUrl = acct.getPhotoUrl().toString();
            }catch (Exception e) {
            }
            String email = acct.getEmail();
            Toast.makeText(getApplicationContext(), "Name : " +personName + "Email : "+ email , Toast.LENGTH_LONG).show();
            Log.e(TAG, "Name: " + personName + ", email: " + email
                    + ", Image: ");


        } else {

            Toast.makeText(getApplicationContext(), "Oops..! Something Wrong....", Toast.LENGTH_LONG).show();

        }
    }

    private void Function_Register() {

        StringRequest request = new StringRequest(Request.Method.POST,
                AppConfig.url_registration, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d(TAG_login, response.toString());

                try {
                    JSONObject obj = new JSONObject(response);
                    System.out.println("RESPONSE : " + response);
                    int success = obj.getInt("status");
                    if (success == 1) {
                        dialog.dismiss();

                        Alerter.create(Activity_Login.this)
                                .setTitle("Registration Success")
                                .setText("WELCOME to Awesome Business")
                                .setBackgroundColor(R.color.colorAccent)
                                .show();
                    } else {
                        dialog.dismiss();
                        TastyToast.makeText(getApplicationContext(), "Oops...! Registration Failed :(", TastyToast.LENGTH_LONG, TastyToast.ERROR);
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

                params.put("user_email", str_signup_email);
                params.put("password", str_signup_pass);

                System.out.println("user_email" + str_signup_email);
                System.out.println("password" + str_signup_pass);

                return params;
            }
        };
        queue.add(request);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.sign_in_button:
                Toast.makeText(getApplicationContext(), "Logging in via Google", Toast.LENGTH_LONG).show();
                signIn();
                break;
            // ...
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        callbackManager.onActivityResult(requestCode, resultCode, data);
        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            handleSignInResult(result);
        }
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        // An unresolvable error has occurred and Google APIs (including Sign-In) will not
        // be available.
        Log.d(TAG, "onConnectionFailed:" + connectionResult);
    }


    private void showSignupForm() {
        PercentRelativeLayout.LayoutParams paramsLogin = (PercentRelativeLayout.LayoutParams) llSignin.getLayoutParams();
        PercentLayoutHelper.PercentLayoutInfo infoLogin = paramsLogin.getPercentLayoutInfo();
        infoLogin.widthPercent = 0.15f;
        llSignin.requestLayout();


        PercentRelativeLayout.LayoutParams paramsSignup = (PercentRelativeLayout.LayoutParams) llSignup.getLayoutParams();
        PercentLayoutHelper.PercentLayoutInfo infoSignup = paramsSignup.getPercentLayoutInfo();
        infoSignup.widthPercent = 0.85f;
        llSignup.requestLayout();

        tvSignupInvoker.setVisibility(View.GONE);
        tvSigninInvoker.setVisibility(View.VISIBLE);
        Animation translate = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.translate_right_to_left);
        llSignup.startAnimation(translate);

        Animation clockwise = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.rotate_right_to_left);
        btnSignup.startAnimation(clockwise);

    }

    private void showSigninForm() {
        PercentRelativeLayout.LayoutParams paramsLogin = (PercentRelativeLayout.LayoutParams) llSignin.getLayoutParams();
        PercentLayoutHelper.PercentLayoutInfo infoLogin = paramsLogin.getPercentLayoutInfo();
        infoLogin.widthPercent = 0.85f;
        llSignin.requestLayout();


        PercentRelativeLayout.LayoutParams paramsSignup = (PercentRelativeLayout.LayoutParams) llSignup.getLayoutParams();
        PercentLayoutHelper.PercentLayoutInfo infoSignup = paramsSignup.getPercentLayoutInfo();
        infoSignup.widthPercent = 0.15f;
        llSignup.requestLayout();

        Animation translate = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.translate_left_to_right);
        llSignin.startAnimation(translate);

        tvSignupInvoker.setVisibility(View.VISIBLE);
        tvSigninInvoker.setVisibility(View.GONE);
        Animation clockwise = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.rotate_left_to_right);
        btnSignin.startAnimation(clockwise);
    }




}
