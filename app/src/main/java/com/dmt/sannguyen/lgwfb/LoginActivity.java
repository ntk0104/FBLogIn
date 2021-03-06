package com.dmt.sannguyen.lgwfb;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.facebook.login.widget.ProfilePictureView;

import org.json.JSONObject;

import java.util.Arrays;

public class LoginActivity extends AppCompatActivity {


    ProfilePictureView profilePictureView;
    LoginButton loginButton;
    CallbackManager callbackManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        callbackManager = CallbackManager.Factory.create();
        setContentView(R.layout.login);
        anhXa();
        loginButton.setReadPermissions(Arrays.asList("public_profile","email"));
        setLogin_button();
        

    }

    private void setLogin_button() {
        try {
            loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
                @Override
                public void onSuccess(LoginResult loginResult) {
                    String accesstoken = loginResult.getAccessToken().getToken();
                    Log.d("accesstoken", accesstoken);
                    GraphRequest graphRequest = GraphRequest.newMeRequest(AccessToken.getCurrentAccessToken(), new GraphRequest.GraphJSONObjectCallback() {
                        @Override
                        public void onCompleted(JSONObject object, GraphResponse response) {
//                            Log.d("JSON", object.toString());
//                            try {
//                                txtemail.setText(object.getString("email"));
//                            } catch (JSONException e) {
//                                e.printStackTrace();
//                            }
                        }
                    });

                    Bundle parameter = new Bundle();
                    parameter.putString("fields","name, email, first_name");
                    graphRequest.setParameters(parameter);
                    graphRequest.executeAsync();

                    loginButton.setVisibility(View.INVISIBLE);
                }

                @Override
                public void onCancel() {
                    Log.d("JSON", "onCancel");
                }

                @Override
                public void onError(FacebookException error) {
                    Log.d("JSON", error.toString());

                }
            });
        }catch (Exception e){
            Log.d("JSON", e.toString());
        }

    }


    private void anhXa() {
        profilePictureView = findViewById(R.id.imageprofilepickture);
        loginButton = findViewById(R.id.login_button);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode,resultCode,data);
    }
}
