package com.apockestafe.team19;

import android.content.Context;
import android.content.Intent;
import android.hardware.camera2.params.Face;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.Profile;
import com.facebook.ProfileTracker;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.FacebookSdk;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthCredential;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.MalformedURLException;
import java.util.Arrays;

import static android.content.Context.MODE_PRIVATE;
import static com.facebook.FacebookSdk.getApplicationContext;
import static com.facebook.internal.FacebookDialogFragment.TAG;

public class LoginFragment extends Fragment /*this extends might needs
 to be changed*/ {

    private CallbackManager mCallbackManager;
    public AccessTokenTracker mAccessTokenTracker;
    public ProfileTracker mProfileTracker;
    private FirebaseAuth mAuth;
    private SharedPreferencesEditor editor;
    public static Context contextOfApplication;


    //private UiLifecycleHelper uiHelper;
    LoginButton loginButton;

    private FacebookCallback<LoginResult> mCallback = new FacebookCallback<LoginResult>() {
        @Override
        public void onSuccess(LoginResult loginResult) {
            AccessToken accesstoken = loginResult.getAccessToken();
            Profile profile = Profile.getCurrentProfile();
            Log.d("get me profile", "Name");
            //handleFacebookAccessToken(accesstoken);
            openNextActivity();

        }

        @Override
        public void onCancel() {
//            FacebookSdk.sdkInitialize(getApplicationContext());
//            LoginManager.getInstance().logOut();
//            AccessToken.setCurrentAccessToken(null);
        }

        @Override
        public void onError(FacebookException e) {

        }
    };

    public LoginFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAuth.getInstance();

        contextOfApplication = getApplicationContext();

        AccessToken at = AccessToken.getCurrentAccessToken();
        try {
            final String data = getActivity().getIntent().getStringExtra("accessToken");
            if (data.equals("null"))
                at = null;
        } catch (Exception e){ e.printStackTrace();}


        if (at != null)
            startActivity(new Intent(getActivity(), MainActivity.class));



        //if(loginr)
        FacebookSdk.sdkInitialize(getActivity().getApplicationContext());

        mCallbackManager = CallbackManager.Factory.create();

        AccessTokenTracker mAccessTokenTracker = new AccessTokenTracker() {
            @Override
            protected void onCurrentAccessTokenChanged(AccessToken oldtracker, AccessToken newtracker) {
                if (newtracker != null)
                    openNextActivity();
            }
        };

        mAccessTokenTracker.startTracking();

        ProfileTracker mProfileTracker = new ProfileTracker() {
            @Override
            protected void onCurrentProfileChanged(Profile oldprofile, Profile newprofile) {
                //Log.d("New Name", "data");
            }
        };

        mProfileTracker.startTracking();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_signin, container, false);

        loginButton = (LoginButton) view.findViewById(R.id.login_button);

        loginButton.setReadPermissions(Arrays.asList("email", "public_profile", "user_status"));
       // loginButton.setPublishPermissions(Arrays.asList("publish_actions"));
        loginButton.setFragment(this);
        loginButton.registerCallback(mCallbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                Log.d(TAG, "facebook:onSuccess:" + loginResult);


//                handleFacebookAccessToken(loginResult.getAccessToken());
                String tokener = loginResult.getAccessToken().getToken();

//                GraphRequest request = GraphRequest.newMeRequest(
//                        loginResult.getAccessToken(),
//                        new GraphRequest.GraphJSONObjectCallback() {
//                            @Override
//                            public void onCompleted(JSONObject object, GraphResponse response) {
//                                Log.v("LoginActivity", response.toString());
//
//                                // Application code
//                                try {
//                                    String email = object.getString("email");
//                                    editor = new SharedPreferencesEditor(getActivity().getSharedPreferences("login", MODE_PRIVATE));
//                                    editor.addMyEmail(email);
//                                } catch (JSONException e) {
//                                    e.printStackTrace();
//                                }
//                            }
//                        });
//                Bundle parameters = new Bundle();
//                parameters.putString("fields", "email");
//                request.setParameters(parameters);
//                request.executeAsync();

                openNextActivity();
            }

            @Override
            public void onCancel() {
                FacebookSdk.sdkInitialize(getApplicationContext());
                LoginManager.getInstance().logOut();
                AccessToken.setCurrentAccessToken(null);
                Log.d(TAG, "facebook:onCancel");

            }

            @Override
            public void onError(FacebookException error) {
                Log.d(TAG, "facebook:onError", error);
            }
        });
        return view;
    }

   // @Override
    //public void onViewCreated(View view, Bundle savedInstanceState) {
        //super.onViewCreated(view, savedInstanceState);
        //LoginButton loginButton = (LoginButton) view.findViewById(R.id.login_button);
      //  loginButton.setReadPermissions("public_profile");
    //    loginButton.setFragment(this);
  //      loginButton.registerCallback(mCallbackManager, mCallback);
//
    //}

    @Override
    public void onResume() {
        super.onResume();
        Profile profile = Profile.getCurrentProfile();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
//                FacebookSdk.sdkInitialize(getApplicationContext());
//                LoginManager.getInstance().logOut();
//                AccessToken.setCurrentAccessToken(null);
            }
        });
        mAccessTokenTracker.stopTracking();
        mProfileTracker.stopTracking();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mCallbackManager.onActivityResult(requestCode, resultCode, data);
    }

    public void openNextActivity(){
        Intent intent = new Intent(getActivity(), MainActivity.class);
        startActivity(intent); //super.getContext() might be incorrect
    }

    private void handleFacebookAccessToken(AccessToken token) {
        Log.d("herehere", "handleFacebookAccessToken:" + token);
        AuthCredential credential = FacebookAuthProvider.getCredential(token.getToken());
        mAuth.signInWithCredential(credential).addOnCompleteListener(super.getActivity(), new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                Log.d(TAG, "signInWithCredential:onComplete:" + task.isSuccessful());
                if(!task.isSuccessful()) {
                    Log.w(TAG, "signInWithCredential", task.getException());
                }
            }
        });
    }

    public static Context getContextOfApplication(){
        return contextOfApplication;
    }


}