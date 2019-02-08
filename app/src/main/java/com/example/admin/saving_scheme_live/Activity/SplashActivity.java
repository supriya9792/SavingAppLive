package com.example.admin.saving_scheme_live.Activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;


import com.example.admin.saving_scheme_live.R;
import com.example.admin.saving_scheme_live.Utility.InternetConnection;
import com.example.admin.saving_scheme_live.Utility.ServerClass;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import java.util.Iterator;
import java.util.Set;

import static com.example.admin.saving_scheme_live.Utility.ServiceUrls.LOGIN_URL;

public class SplashActivity extends AppCompatActivity {
    private int INTERNET_ACCESS_CODE = 23;
    private final String TAG = SplashActivity.class.getName();
    String saving_scheme_id;
    ProgressBar progressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);


        if (savedInstanceState == null) {


            flyIn();
        }
        /****** Create Thread that will sleep for 5 seconds *************/
        new Handler().postDelayed(new Runnable() {
            public void run() {

                try {
                    // Thread will sleep for 5 seconds
                    SharedPreferences pref = getSharedPreferences("Login", MODE_PRIVATE);
                    String email = pref.getString("email", "");
                    String password = pref.getString("password", "");
                   // Toast.makeText(SplashActivity.this,email,Toast.LENGTH_SHORT).show();
                    if (!email.equalsIgnoreCase("") && !password.equalsIgnoreCase("")) {
                        //nextadminActivity();
                        userLogin(email,password);
                    }
                    else {
                        loginActivity();
                    }


                } catch (Exception e) {

                }
            }
        },3000);
//
        // start thread
        //  background.start();

    }

    private boolean isReadStorageAllowed() {
        //Getting the permission status
        int result = ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_NETWORK_STATE);

        //If permission is granted returning true
        if (result == PackageManager.PERMISSION_GRANTED)
            return true;

        //If permission is not granted returning false
        return false;
    }
    //Requesting permission
    private void requestStoragePermission(){

        if (ActivityCompat.shouldShowRequestPermissionRationale(this, android.Manifest.permission.READ_EXTERNAL_STORAGE)){
            //If the user has denied the permission previously your code will come to this block
            //Here you can explain why you need this permission
            //Explain here why you need this permission

        }

        //And finally ask for the permission
        ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_NETWORK_STATE}, INTERNET_ACCESS_CODE);
    }

    //This method will be called when the user will tap on allow or deny
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        //Checking the request code of our request
        if(requestCode == INTERNET_ACCESS_CODE){

            //If permission is granted
            if(grantResults.length >0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){

                //Displaying a toast
                Toast.makeText(this,"Permission granted now you can read the storage",Toast.LENGTH_LONG).show();
            }else{
                //Displaying another toast if permission is not granted
                Toast.makeText(this,"Oops you just denied the permission",Toast.LENGTH_LONG).show();
            }
        }
    }


    private void flyIn() {}

    @Override
    protected void onPause() {

        //LocalBroadcastManager.getInstance(this).unregisterReceiver(mRegistrationBroadcastReceiver);
        super.onPause();
        // TODO Auto-generated method stub
        super.onPause();
        finish();
    }

    private void userLogin(final String email, final String password) {
        Log.v(TAG, String.format("userLogin :: mobileno,password = %s,%s", email, password));
        if (InternetConnection.checkConnection(getApplicationContext())) {
            UserLoginClass ulc = new UserLoginClass();
            ulc.execute(email, password);
        } else {
            Toast toast = Toast.makeText(getApplicationContext(), "Internet Connection Not Available", Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
        }

    }

    private void loginActivity(){
        Intent intent = new Intent(SplashActivity.this,LoginActivity.class);
        SplashActivity.this.startActivity(intent);
    }
    private void nextadminActivity() {
        Intent intent = new Intent(SplashActivity.this,MainActivity.class);
        startActivity(intent);

    }
    public boolean getLoginCredentials(String currentName, String currentPassword){
        SharedPreferences pref = getSharedPreferences("Login", MODE_PRIVATE);

        String mobile = pref.getString("email", "");
        String password = pref.getString("password", "");

        return (!mobile.equalsIgnoreCase("") && !password.equalsIgnoreCase("")) && (mobile.equalsIgnoreCase(currentName) && password.equalsIgnoreCase(currentPassword));



    }
    private void showToastMessage(String message) {
        Log.v(TAG, String.format("showToastMessage :: message = %s", message));
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
    }
/**
 * Call the next Activity through intent.
 */



    /**
     * Perform the asyncTask sends data to server and gets response.
     */

    class UserLoginClass extends AsyncTask<String, Void, String> {
        @Override
        protected void onPreExecute() {
            Log.v(TAG, "onPreExecute");
            super.onPreExecute();
            //showProgressDialog();
        }

        @Override
        protected void onPostExecute(String response) {
            super.onPostExecute(response);
            Log.v(TAG, String.format("onPostExecute :: response = %s", response));
            // dismissProgressDialog();
            // Toast.makeText(SplashActivity.this,response,Toast.LENGTH_SHORT).show();
            JSONObject jsonObjLoginResponse = null;
            try {
                jsonObjLoginResponse = new JSONObject(response);
                String success = jsonObjLoginResponse.getString(getResources().getString(R.string.success));

                if (success.equalsIgnoreCase(getResources().getString(R.string.two))) {
                    saving_scheme_id = jsonObjLoginResponse.getString("saving_scheme_id");
                    nextadminActivity() ;
                    //loginActivity();
                }else if (success.equalsIgnoreCase(getResources().getString(R.string.zero))) {
                    loginActivity();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
        @Override
        protected String doInBackground(String... params) {
//            Log.v(TAG, String.format("doInBackground ::  params= %s", params));

            HashMap<String, String> loginData = new HashMap<>();
            loginData.put("email", params[0]);
            loginData.put(ServerClass.PASSWORD, params[1]);
            loginData.put(ServerClass.ACTION,"login");
            ServerClass ruc = new ServerClass();
            String loginResult = ruc.sendPostRequest(LOGIN_URL, loginData);

            Log.v(TAG, String.format("doInBackground :: loginResult= %s", loginResult));
            return loginResult;

        }
    }
    /**
     * Show progress dialog.
     */
    private void showProgressDialog() {
        Log.v(TAG, String.format("showProgressDialog"));

        progressBar.setVisibility(View.VISIBLE);

    }

    /**
     * Dismiss the Progress dialog.
     */

    private void dismissProgressDialog() {
        Log.v(TAG, String.format("dismissProgressDialog"));

        progressBar.setVisibility(View.GONE);


    }
}
