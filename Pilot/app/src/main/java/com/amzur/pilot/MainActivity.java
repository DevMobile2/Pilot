package com.amzur.pilot;


import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.amzur.pilot.myretrofit.Listener;
import com.amzur.pilot.myretrofit.RetrofitService;
import com.amzur.pilot.notifications.RegistrationIntentService;
import com.amzur.pilot.utilities.PreferenceData;
import com.amzur.pilot.utilities.Utils;
import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.squareup.okhttp.ResponseBody;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;

import retrofit.Call;

/**
 * This class provides facebook login for the user.
 * This class provides facebook login for the application.
 */
public class MainActivity extends AppCompatActivity implements FacebookCallback<LoginResult> {
    private static final int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;
    CallbackManager callbackManager;
    AccessTokenTracker accessTokenTracker;
    Button facebookLoginButton;
    LoginButton loginButton;
    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            loginButton.performClick();

            loginButton.setPressed(true);

            loginButton.invalidate();

            loginButton.registerCallback(callbackManager, MainActivity.this);

            loginButton.setPressed(false);

            loginButton.invalidate();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        setContentView(R.layout.activity_main);
        facebookLoginButton = (Button) findViewById(R.id.btFacebookLogin);
        if (facebookLoginButton != null) {
            facebookLoginButton.setTransformationMethod(null);
        }
        callbackManager = CallbackManager.Factory.create();
        loginButton = (LoginButton) findViewById(R.id.login_button);
        facebookLoginButton.setOnClickListener(onClickListener);
        loginButton.setReadPermissions(Arrays.asList("public_profile", "email"));
        accessTokenTracker = new AccessTokenTracker() {
            @Override
            protected void onCurrentAccessTokenChanged(AccessToken accessToken, AccessToken accessToken2) {
                if (accessToken2 == null) {
                    Intent intent = new Intent(MainActivity.this, MainActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                }
            }
        };

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onSuccess(LoginResult loginResult) {

        facebookLoginButton.setVisibility(View.GONE);
        GraphRequest request = GraphRequest.newMeRequest(
                loginResult.getAccessToken(),
                new GraphRequest.GraphJSONObjectCallback() {
                    @Override
                    public void onCompleted(JSONObject object, GraphResponse response) {
                        Log.i("FB Response", object.toString());

                        try {
                            doLogin(object.getString("email"));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        //Bundle bFacebookData = getFacebookData(object);

                    }
                });
        Bundle parameters = new Bundle();
        parameters.putString("fields", "id, first_name, last_name, email,gender, birthday, location");
        request.setParameters(parameters);
        request.executeAsync();

    }

    /**
     * This method send user data to the server to perform login.
     * @param email email id retrieved from the facebook account.
     */
    public void doLogin(String email) {
        JSONObject loginObject = new JSONObject();
        try {
            PreferenceData.putEmail(MainActivity.this,email);
            loginObject.put("email", email);
            loginObject.put("deviceId", Settings.Secure.getString(MyApplication.getInstance().getContentResolver(), Settings.Secure.ANDROID_ID));
            loginObject.put("deviceType", "ANDROID");
            loginObject.put("deviceToken", PreferenceData.getRegistrationId(this));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    Call<ResponseBody> call = MyApplication.getSerivce().authLogin(loginObject.toString(), "application/json");

        call.enqueue(new Listener(new RetrofitService() {
            @Override
            public void onSuccess(String result, int pos, Throwable t) {
                if (pos == 0) {
                    try {

                        JSONObject object = new JSONObject(result);
                        PreferenceData.putString(PreferenceData.PREF_API_KEY, object.getString("api_key"));
                        PreferenceData.putLoginStatus(MainActivity.this, true);
                        Intent intent = new Intent(MainActivity.this, CategoriesActivity.class);
                        startActivity(intent);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
            }
        }, "connecting...", true, MainActivity.this));
    }

    @Override
    public void onCancel() {

    }

    @Override
    public void onError(FacebookException error) {
        Utils.showSnackBarLongTime2(MainActivity.this, "Some thing went wrong please login again").show();
        PreferenceData.signOut(MainActivity.this);
    }


    @Override
    protected void onResume() {
        super.onResume();
        if (checkPlayServices()) {
            startService(new Intent(this, RegistrationIntentService.class));

        } else {
            Log.i("GCM", "No valid Google Play Services APK found.");
        }
        if (PreferenceData.isLogin(MainActivity.this)) {
            Intent intent = new Intent(MainActivity.this, CategoriesActivity.class);
            startActivity(intent);
        }
    }

    /**
     * This method checks whether the google play services are available in the device or not.
     * @return false if the google play services are not available.
     */
    private boolean checkPlayServices() {
        GoogleApiAvailability apiAvailability = GoogleApiAvailability.getInstance();
        if (apiAvailability != null) {
            int resultCode = apiAvailability.isGooglePlayServicesAvailable(this);
            if (resultCode != ConnectionResult.SUCCESS) {
                if (apiAvailability.isUserResolvableError(resultCode)) {
                    apiAvailability.getErrorDialog(this, resultCode, PLAY_SERVICES_RESOLUTION_REQUEST)
                            .show();
                } else {
                    Log.i("GCM", "This device is not supported.");
                    finish();
                }
                return false;
            }
        }
        return true;
    }

}

