package banyan.com.awesomebusiness.activity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
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
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
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
import com.toptoche.searchablespinnerlibrary.SearchableSpinner;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Iterator;
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
    EditText edt_singin_email, edt_signin_pass, edt_singup_email, edt_signup_pass, edt_signup_repeat_pass;
    TextView txt_forgot_pwd;

    // POPUP
    final Context context = this;
    EditText edt_pwd;
    String str_forgot_pwd = "";

    private static final String TAG = Activity_Login.class.getSimpleName();
    private static final String TAG_login = "Login";
    private static final int RC_SIGN_IN = 100;

    CallbackManager callbackManager;

    private GoogleApiClient mGoogleApiClient;

    private SignInButton btn_GoogleSignIn;

    public static RequestQueue queue;

    String signin_email, signin_pass;

    String str_signin_email, str_signin_pass, str_signup_email, str_signup_pass, str_signup_repeat_pass;
    String str_social_email, str_social_name, str_social_image, str_social_type;
    String str_user_email, str_user_id;

    SpotsDialog dialog,dialog_popup;

    private static final String TAG_EMAIL = "user_email";
    private static final String TAG_ID = "id";

    Button btn_fb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        callbackManager = CallbackManager.Factory.create();

        tvSignupInvoker = (TextView) findViewById(R.id.tvSignupInvoker);
        tvSigninInvoker = (TextView) findViewById(R.id.tvSigninInvoker);

        txt_forgot_pwd = (TextView) findViewById(R.id.login_txt_forgot_pwd);

        btn_GoogleSignIn = (SignInButton) findViewById(R.id.sign_in_button);
        btn_GoogleSignIn.setSize(SignInButton.SIZE_STANDARD);

        btnSignup = (Button) findViewById(R.id.btnSignup);
        btnSignin = (Button) findViewById(R.id.btnSignin);

        edt_singin_email = (EditText) findViewById(R.id.edt_email);
        edt_signin_pass = (EditText) findViewById(R.id.edt_password);
        edt_singup_email = (EditText) findViewById(R.id.edt_signup_email);
        edt_signup_pass = (EditText) findViewById(R.id.edt_signup_password);
        edt_signup_repeat_pass = (EditText) findViewById(R.id.edt_signup_repeat_pass);

        llSignup = (LinearLayout) findViewById(R.id.llSignup);
        llSignin = (LinearLayout) findViewById(R.id.llSignin);

        loginButton = (LoginButton) findViewById(R.id.login_button);
        loginButton.setReadPermissions("email");

        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {

                String facebook_id, f_name, m_name, l_name, gender, profile_image, full_name, email_id = "";

                System.out.println("STEP 1");
                if (AccessToken.getCurrentAccessToken() != null) {
                    System.out.println("STEP 2");
                    RequestData();
                    System.out.println("STEP 3");
                    Profile profile = Profile.getCurrentProfile();
                    System.out.println("STEP 4");
                    if (profile != null) {
                        System.out.println("STEP 5");
                        facebook_id = profile.getId();
                        Log.e("facebook_id", facebook_id);
                        f_name = profile.getFirstName();
                        Log.e("f_name", f_name);
                        m_name = profile.getMiddleName();
                        Log.e("m_name", m_name);
                        l_name = profile.getLastName();
                        Log.e("l_name", l_name);
                        full_name = profile.getName();
                        Log.e("full_name", full_name);
                        profile_image = profile.getProfilePictureUri(400, 400).toString();
                        Log.e("profile_image", profile_image);


                        str_social_name = full_name;
                        str_social_image = profile_image;
                        str_social_type = "2";

                    }

                }
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

        txt_forgot_pwd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Function_AlertDialog();

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

                signin_email = edt_singin_email.getText().toString();
                signin_pass = edt_signin_pass.getText().toString();

                if (signin_email.equals("")) {
                    TastyToast.makeText(getApplicationContext(), "Enter Email ID", TastyToast.LENGTH_SHORT, TastyToast.WARNING);
                    edt_singin_email.setError("Please Enter Email ID");
                } else if (signin_pass.equals("")) {
                    TastyToast.makeText(getApplicationContext(), "Enter Password", TastyToast.LENGTH_SHORT, TastyToast.WARNING);
                    edt_signin_pass.setError("Please Enter Password");
                } else {

                    dialog = new SpotsDialog(Activity_Login.this);
                    dialog.show();
                    queue = Volley.newRequestQueue(Activity_Login.this);
                    Function_Login();

                }


            }
        });


    }

    public void RequestData() {
        GraphRequest request = GraphRequest.newMeRequest(AccessToken.getCurrentAccessToken(), new GraphRequest.GraphJSONObjectCallback() {
            @Override
            public void onCompleted(JSONObject object, GraphResponse response) {

                JSONObject json = response.getJSONObject();
                System.out.println("Json data :" + json);
                try {
                    if (json != null) {
                        String text = "<b>Name :</b> " + json.getString("name") + "<br><br><b>Email :</b> " + json.getString("email") +
                                "<br><br><b>Profile link :</b> " + json.getString("link");
                       /* details_txt.setText(Html.fromHtml(text));
                        profile.setProfileId(json.getString("id"));*/
                        str_social_email = json.getString("email");

                        Toast.makeText(getApplicationContext(), "11111111Successs" +
                                str_social_email + json.getString("name"), Toast.LENGTH_LONG).show();

                        System.out.println("Mail ::: :" + str_social_email + "Name ::: :" + str_social_name +
                                "Image url " + str_social_image + "type" + str_social_type);
                    }


                    try {
                        str_social_type = "2";
                        dialog = new SpotsDialog(Activity_Login.this);
                        dialog.show();
                        queue = Volley.newRequestQueue(Activity_Login.this);
                        Function_Social_Login();
                    } catch (Exception e) {

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
                str_social_image = acct.getPhotoUrl().toString();
            } catch (Exception e) {
            }
            String email = acct.getEmail();
            Toast.makeText(getApplicationContext(), "Name : " + personName + "Email : " + email, Toast.LENGTH_LONG).show();
            Log.e(TAG, "Name: " + personName + ", email: " + email
                    + ", Image: ");


            try {
                str_social_name = personName;
                str_social_email = email;
                str_social_type = "1";

                dialog = new SpotsDialog(Activity_Login.this);
                dialog.show();
                queue = Volley.newRequestQueue(Activity_Login.this);
                Function_Social_Login();
            } catch (Exception e) {

            }


        } else {

            Toast.makeText(getApplicationContext(), "Oops..! Something Wrong....", Toast.LENGTH_LONG).show();

        }
    }

    /******************************************
     *    User Registration
     * *******************************************/

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

    /******************************************
     *    User Login
     * *******************************************/

    private void Function_Login() {

        StringRequest request = new StringRequest(Request.Method.POST,
                AppConfig.url_login, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d(TAG_login, response.toString());
                Log.d("USER_LOGIN", response.toString());
                try {
                    JSONObject obj = new JSONObject(response);
                    System.out.println("REG 00" + obj);

                    int success = obj.getInt("status");

                    System.out.println("REG" + success);

                    if (success == 1) {

                        dialog.dismiss();

                        str_user_email = obj.getString(TAG_EMAIL);
                        str_user_id = obj.getString(TAG_ID);

                        // session.createLoginSession(str_user_name, str_reg_id);

                        Intent i = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(i);
                        finish();

                    } else {
                        TastyToast.makeText(getApplicationContext(), "Oops...! Login Failed :(", TastyToast.LENGTH_LONG, TastyToast.ERROR);
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

            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();

                params.put("user_email", signin_email);
                params.put("password", signin_pass);

                System.out.println("User_Email" + signin_email);
                System.out.println("Password" + signin_pass);

                return params;
            }

        };

        // Adding request to request queue
        queue.add(request);
    }

    /******************************************
     *    User Social Login
     * *******************************************/

    private void Function_Social_Login() {

        StringRequest request = new StringRequest(Request.Method.POST,
                AppConfig.url_social_login, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d(TAG_login, response.toString());
                Log.d("USER_LOGIN", response.toString());
                try {
                    JSONObject obj = new JSONObject(response);
                    System.out.println("REG 00" + obj);

                    int success = obj.getInt("status");

                    System.out.println("REG" + success);

                    if (success == 1) {

                        dialog.dismiss();

                        str_user_email = obj.getString(TAG_EMAIL);
                        str_user_id = obj.getString(TAG_ID);

                        // session.createLoginSession(str_user_name, str_reg_id);

                        Intent i = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(i);
                        finish();

                    } else {
                        TastyToast.makeText(getApplicationContext(), "Oops...! Login Failed :(", TastyToast.LENGTH_LONG, TastyToast.ERROR);
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

            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();

                params.put("user_email", str_social_email);
                params.put("user_photo", str_social_image);
                params.put("user_name", str_social_name);
                params.put("sociallogin", str_social_type);

                System.out.println("User_Email" + str_social_email);
                System.out.println("user_photo" + str_social_image);
                System.out.println("user_name" + str_social_name);
                System.out.println("sociallogin" + str_social_type);

                return checkParams(params);
            }

            private Map<String, String> checkParams(Map<String, String> map) {
                Iterator<Map.Entry<String, String>> it = map.entrySet().iterator();
                while (it.hasNext()) {
                    Map.Entry<String, String> pairs = (Map.Entry<String, String>) it.next();
                    if (pairs.getValue() == null) {
                        map.put(pairs.getKey(), "");
                    }
                }
                return map;
            }

        };

        int socketTimeout = 60000;//30 seconds - change to what you want
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        request.setRetryPolicy(policy);
        // Adding request to request queue
        queue.add(request);
    }


    /******************************************
     *    Forgot Password
     * *******************************************/

    private void Function_Forgot_Password() {

        StringRequest request = new StringRequest(Request.Method.POST,
                AppConfig.url_login, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d(TAG_login, response.toString());
                Log.d("USER_LOGIN", response.toString());
                try {
                    JSONObject obj = new JSONObject(response);
                    System.out.println("REG 00" + obj);

                    int success = obj.getInt("status");

                    System.out.println("REG" + success);

                    if (success == 1) {

                        dialog_popup.dismiss();



                    } else {
                        TastyToast.makeText(getApplicationContext(), "Oops...! Failed to Retrive:(", TastyToast.LENGTH_LONG, TastyToast.ERROR);
                    }

                    dialog_popup.dismiss();
                } catch (JSONException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();

                params.put("user_email", str_forgot_pwd);

                System.out.println("User_Email" + str_forgot_pwd);

                return params;
            }

        };

        // Adding request to request queue
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


    /******************************************
     *    Forgot Pwd Aleter
     * *******************************************/

    public void Function_AlertDialog() {

        LayoutInflater li = LayoutInflater.from(context);
        View promptsView = li.inflate(R.layout.popup_forgot_pwd, null);

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                context);
        alertDialogBuilder.setView(promptsView);

        edt_pwd  = (EditText) promptsView.findViewById(R.id.edt_forgot_pwd_email);

        alertDialogBuilder
                .setCancelable(false)
                .setPositiveButton("Save",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {

                                str_forgot_pwd = edt_pwd.getText().toString();

                                if (str_forgot_pwd.equals("")){
                                    edt_pwd.setError("Please Enter Email ID");
                                    TastyToast.makeText(getApplicationContext(), "Please Enter Email ID", TastyToast.LENGTH_LONG, TastyToast.ERROR);
                                }else {
                                    try {
                                        dialog_popup = new SpotsDialog(Activity_Login.this);
                                        dialog_popup.show();
                                        queue = Volley.newRequestQueue(Activity_Login.this);
                                        Function_Forgot_Password();
                                    } catch (Exception e) {

                                    }
                                }

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