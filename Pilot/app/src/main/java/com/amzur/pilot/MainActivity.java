package com.amzur.pilot;


import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.amzur.pilot.utilities.PreferenceData;
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

import org.json.JSONObject;

/**
 * This class provides facebook login for the user.
 */
public class MainActivity extends AppCompatActivity implements FacebookCallback<LoginResult> {
    CallbackManager callbackManager;
    AccessTokenTracker accessTokenTracker;
    Button facebookLoginButton;
    LoginButton loginButton;
    ProgressDialog progressDialog;
    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            progressDialog = new ProgressDialog(MainActivity.this);
            progressDialog.setMessage("Loading...");
            progressDialog.show();

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
        callbackManager = CallbackManager.Factory.create();
        loginButton = (LoginButton) findViewById(R.id.login_button);
        facebookLoginButton.setOnClickListener(onClickListener);
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
        GraphRequest request = GraphRequest.newMeRequest(
                loginResult.getAccessToken(),
                new GraphRequest.GraphJSONObjectCallback() {
                    @Override
                    public void onCompleted(JSONObject object, GraphResponse response) {
                        PreferenceData.putLoginStatus(MainActivity.this, true);
                        Intent intent = new Intent(MainActivity.this, CategoriesActivity.class);
                        startActivity(intent);

                    }
                });
        Bundle parameters = new Bundle();
        parameters.putString("fields", "id,name,email,gender,birthday");
        request.setParameters(parameters);
        request.executeAsync();
    }

    @Override
    public void onCancel() {

    }

    @Override
    public void onError(FacebookException error) {

    }


    @Override
    protected void onResume() {
        super.onResume();
        if (PreferenceData.isLogin(MainActivity.this)) {
            Intent intent = new Intent(MainActivity.this, CategoriesActivity.class);
            startActivity(intent);
        }
    }


}

