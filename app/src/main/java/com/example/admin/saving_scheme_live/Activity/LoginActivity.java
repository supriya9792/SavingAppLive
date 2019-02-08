package com.example.admin.saving_scheme_live.Activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import com.example.admin.saving_scheme_live.R;
import com.example.admin.saving_scheme_live.Utility.InternetConnection;
import com.example.admin.saving_scheme_live.Utility.ServerClass;
import com.example.admin.saving_scheme_live.Utility.ServiceUrls;
import com.example.admin.saving_scheme_live.Utility.SharedPrefereneceUtil;
import com.example.admin.saving_scheme_live.Utility.Utility;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

import static com.example.admin.saving_scheme_live.Utility.ServiceUrls.LOGGEDIN_SHARED_PREF;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener{


    private AutoCompleteTextView mEmailView;
    private EditText mPasswordView;
    private View mProgressView;
    private View mLoginFormView;
    private TextInputLayout inputLayoutPassword,inputLayoutusername;
    private Button button;
    private final String TAG = LoginActivity.class.getName();
    String Semail,Spassword;
    TextView signup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mEmailView = (AutoCompleteTextView) findViewById(R.id.email);
        // populateAutoComplete();
        inputLayoutPassword = (TextInputLayout) findViewById(R.id.input_password);
        inputLayoutusername = (TextInputLayout) findViewById(R.id.input_email);
        mPasswordView = (EditText) findViewById(R.id.password);

        button = (Button) findViewById(R.id.email_sign_in_button);
        button.setOnClickListener(this);


        mProgressView = findViewById(R.id.login_progress);



    }

    private void showProgressDialog() {
        Log.v(TAG, String.format("showProgressDialog"));
        Utility.showProgressDialog(this);

    }

    /**
     * Dismiss the Progress dialog.
     */

    private void dismissProgressDialog() {
        Log.v(TAG, String.format("dismissProgressDialog"));
        Utility.hideProgressBar();

    }
    private void saveLoginCredentials() {

        SharedPreferences pref = getApplicationContext().getSharedPreferences("Login", MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putBoolean(LOGGEDIN_SHARED_PREF, true);
        editor.putString("email",Semail );  // Saving string
        editor.putString("password", Spassword);
        editor.commit(); // commit changes
        //Toast.makeText(LoginActivity.this,"save login credentials ",Toast.LENGTH_SHORT).show();
    }
    /**
     * Get the user input.
     */

    private void login() {
        Log.v(TAG, String.format("login"));
        Semail =mEmailView.getText().toString().trim();
        Spassword = mPasswordView.getText().toString().trim();

        userLogin(Semail, Spassword);
    }


    private void userLogin(final String email, final String password) {
        Log.v(TAG, String.format("userLogin :: email,password = %s,%s", email, password));

        if(InternetConnection.checkConnection(LoginActivity.this)) {
            UserLoginClass ulc = new UserLoginClass();
            ulc.execute(email, password);
        }
        else
        {

            Toast.makeText(LoginActivity.this,"Please Connect to Internet",Toast.LENGTH_SHORT).show();
        }
    }
    class UserLoginClass extends AsyncTask<String, Void, String> {
        @Override
        protected void onPreExecute() {
            Log.v(TAG,"onPreExecute");
            super.onPreExecute();
            showProgressDialog();
        }

        @Override
        protected void onPostExecute(String response) {
            super.onPostExecute(response);
            Log.v(TAG, String.format("onPostExecute :: response = %s", response));
            dismissProgressDialog();
//            showToastMessage(response);
            //Toast.makeText(LoginActivity.this, response, Toast.LENGTH_LONG).show();
            loginServerResponse(response);
        }

        /**
         *
         * @param params
         * @return
         */
        @Override
        protected String doInBackground(String... params) {
//            Log.v(TAG, String.format("doInBackground ::  params= %s", params));

            HashMap<String, String> loginData = new HashMap<>();
            loginData.put("email",mEmailView.getText().toString());
            loginData.put("password",mPasswordView.getText().toString());
            SharedPrefereneceUtil.setemail(LoginActivity.this, Semail);
            loginData.put(ServerClass.ACTION,"login");
            ServerClass ruc = new ServerClass();
            String loginResult = ruc.sendPostRequest(ServiceUrls.LOGIN_URL, loginData);

            Log.v(TAG, String.format("doInBackground :: loginResult= %s", loginResult));
            return loginResult;

        }
    }

    /**
     * Server response Operations.
     */

    private void loginServerResponse(String response) {
        Log.v(TAG, String.format("loginServerResponse :: response = %s", response));

        JSONObject jsonObjLoginResponse = null;
        try {
            jsonObjLoginResponse = new JSONObject(response);
            String success = jsonObjLoginResponse.getString(getResources().getString(R.string.success));


            if(success.equalsIgnoreCase(getResources().getString(R.string.two))){
                String userId = jsonObjLoginResponse.getString("saving_scheme_id");
                String name = jsonObjLoginResponse.getString("saving_scheme_name");
                SharedPrefereneceUtil.setSavingSchemeId(LoginActivity.this, userId);
                Log.v(TAG, String.format("loginServerResponse :: userId = %s", userId));
                SharedPrefereneceUtil.setSavingSchemename(LoginActivity.this, name);
                Log.v(TAG, String.format("loginServerResponse :: name = %s", name));
                // SharedPrefereneceUtil.setEmail(LoginActivity.this,email);
                saveLoginCredentials();
                nextEmpActivity();

            }else if (success.equalsIgnoreCase(getResources().getString(R.string.zero)))
            {
                showToastMessage(getResources().getString(R.string.inavlidlogin));
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    private void loginActivity(){
        Intent intent = new Intent(LoginActivity.this, LoginActivity.class);
        startActivity(intent);
    }
    private void nextEmpActivity() {
        Intent intent = new Intent(LoginActivity.this,MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
    }

    private void showToastMessage(String message) {
        Log.v(TAG, String.format("showToastMessage :: message = %s", message));
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onClick(View v) {
        if (v == button) {


            login();
        }

    }
}
