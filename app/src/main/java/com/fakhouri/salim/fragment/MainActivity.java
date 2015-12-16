package com.fakhouri.salim.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import com.firebase.client.AuthData;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;

import java.util.Map;

public class MainActivity extends AppCompatActivity {

    public static Firebase ref = null;

    EditText email;
    EditText password;
    EditText firstname;
    EditText lastname;
    EditText username;
    EditText ageInt;
    /* Data from the authenticated user */
    private AuthData mAuthData;

    /* Listener for Firebase session changes */
    private Firebase.AuthStateListener mAuthStateListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        // ************************************************************
        // ***********************************************************

        // set up firebase
        Firebase.setAndroidContext(this);
        // get reference the firebase main
        ref = new Firebase("https://logindemo1.firebaseio.com");

        // authinticate user if logged in send him to home
        mAuthStateListener = new Firebase.AuthStateListener() {
            @Override
            public void onAuthStateChanged(AuthData authData) {

                setAuthenticatedUser(authData);
            }
        };
        /* Check if the user is authenticated with Firebase already. If this is the case we can set the authenticated
         * user and hide hide any login buttons */
        ref.addAuthStateListener(mAuthStateListener);

        // **********************************************************
        // ******************************************************8888

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);

        email = (EditText)findViewById(R.id.email);
        password = (EditText)findViewById(R.id.password);
        firstname = (EditText)findViewById(R.id.firstname);
        lastname = (EditText)findViewById(R.id.lastname);
        username = (EditText)findViewById(R.id.username);
        ageInt = (EditText)findViewById(R.id.age);



        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    private void setAuthenticatedUser(AuthData authData) {
        if(authData != null){

            // all good send him to home
            Intent intent = new Intent(MainActivity.this,Home.class);
            startActivity(intent);
            finish();
        }else{

            // user is not authenticated through him out to log in
        }

        // store the auth for later use
        this.mAuthData = authData;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void loginPassword(View view) {
        // get values
        String mEmail = email.getText().toString().toLowerCase();
        String mPassword = password.getText().toString();

        if(mEmail.equals("") || mPassword.equals("")){

            // do nothing
            Toast.makeText(this,"Require",Toast.LENGTH_LONG).show();


        }else{

            // first we create the user (aka sign up),, then we log him in
            // to do so we need a ref
            ref.createUser(mEmail, mPassword, new Firebase.ValueResultHandler<Map<String, Object>>() {
                @Override
                public void onSuccess(Map<String, Object> stringObjectMap) {
                    Toast.makeText(MainActivity.this,"Success",Toast.LENGTH_LONG).show();
                   Log.e("succes user","my user id"+ stringObjectMap.get("uid"));

                    // here call the log in method
                    authMeToLogin();

                }// end on success

                @Override
                public void onError(FirebaseError firebaseError) {
                    Toast.makeText(MainActivity.this,"Firebase Errot"+firebaseError.getMessage(),Toast.LENGTH_LONG).show();
                    //Log.e("error type", String.valueOf(" "+mEmail instanceof String));
                }
            });

        }
    }

    public void authMeToLogin(){
        ref.authWithPassword(email.getText().toString(), password.getText().toString(), new Firebase.AuthResultHandler() {
            @Override
            public void onAuthenticated(AuthData authData) {
                System.out.println("User ID: " + authData.getUid() + ", Provider: " + authData.getProvider());
                Toast.makeText(MainActivity.this,"Firebase auth",Toast.LENGTH_LONG).show();
                Intent intentHome = new Intent(MainActivity.this, Home.class);
                startActivity(intentHome);
                finish();

                // assign user to firebase database
                // do some checking on values
                String age = ageInt.getText().toString();
                int nAge = Integer.parseInt(age);
                User myFirebaseUser = new User(firstname.getText().toString(),
                        lastname.getText().toString(),
                        username.getText().toString(),
                        email.getText().toString(),
                        nAge

                        );
                assignUserToFirebaseDatabase(authData.getUid(),myFirebaseUser);

            }
            @Override
            public void onAuthenticationError(FirebaseError firebaseError) {
                // there was an error

            }
        });
    }

    public void assignUserToFirebaseDatabase(String uid,User firebaseUser){

        // assign an appropriate listener
        Firebase userKey = ref.child("users").child(uid);
        userKey.setValue(firebaseUser);

    }
}
