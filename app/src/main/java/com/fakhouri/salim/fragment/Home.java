package com.fakhouri.salim.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.firebase.client.AuthData;
import com.firebase.client.Firebase;

/**
 * Created by salim on 12/15/2015.
 */
public class Home extends AppCompatActivity {

    Firebase ref;
    /* Data from the authenticated user */
    private AuthData mAuthData;

    /* Listener for Firebase session changes */
    private Firebase.AuthStateListener mAuthStateListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.home);

        ref = new Firebase("https://logindemo1.firebaseio.com");

        // authinticate user to get the auth
        mAuthStateListener = new Firebase.AuthStateListener() {
            @Override
            public void onAuthStateChanged(AuthData authData) {

                // get the auth
                mAuthData = authData;

                checkUser(authData);
            }
        };
        /* Check if the user is authenticated with Firebase already. If this is the case we can set the authenticated
         * user and hide hide any login buttons */
        ref.addAuthStateListener(mAuthStateListener);

    }
    public void logout(View view) {
        // we need ref
        // we need authdata
        if (this.mAuthData != null) {
            /* logout of Firebase */
            ref.unauth();
        }else{

        }
    }


    private void logout() {
        if (this.mAuthData != null) {
            /* logout of Firebase */
            ref.unauth();

            /*

            /* Logout of any of the Frameworks. This step is optional, but ensures the user is not logged into
             * Facebook/Google+ after logging out of Firebase. /
            if (this.mAuthData.getProvider().equals("facebook")) {
                // Logout from Facebook /
                LoginManager.getInstance().logOut();
            } else if (this.mAuthData.getProvider().equals("google")) {
                // Logout from Google+ /
                if (mGoogleApiClient.isConnected()) {
                    Plus.AccountApi.clearDefaultAccount(mGoogleApiClient);
                    mGoogleApiClient.disconnect();
                }
            }

            */
            // Update authenticated user and show login buttons */
            //setAuthenticatedUser(null);
        }


    }

    private void checkUser(AuthData authData) {
        if(authData != null){

            // all good
            /*
            Intent intent = new Intent(Home.this,Home.class);
            startActivity(intent);
            finish();
            */
        }else{

            // user is not authenticated through him out to log in
            // take him to main to log in or sign up
            Intent intent = new Intent(Home.this,MainActivity.class);
            startActivity(intent);
            finish();
        }

        // store the auth for later use
        this.mAuthData = authData;
    }


}
