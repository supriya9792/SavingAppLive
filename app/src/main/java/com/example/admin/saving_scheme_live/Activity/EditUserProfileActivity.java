package com.example.admin.saving_scheme_live.Activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Toast;

import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;
import com.basgeekball.awesomevalidation.utility.RegexTemplate;

import com.example.admin.saving_scheme_live.R;
import com.example.admin.saving_scheme_live.Utility.ServerClass;
import com.example.admin.saving_scheme_live.Utility.ServiceUrls;
import com.example.admin.saving_scheme_live.Utility.SharedPrefereneceUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

public class EditUserProfileActivity extends AppCompatActivity implements View.OnClickListener{
    private final String TAG = EditUserProfileActivity.class.getName();
    private ProgressDialog pd;
    String user_id;
    AutoCompleteTextView editname ,editaddress,editmobile;
    Button add;
    private AwesomeValidation awesomeValidation;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_user_profile);

        Bundle bundle1 = getIntent().getExtras();
        user_id = bundle1.getString("user_id");
      //  profileClass();
        //Toast.makeText(EditUserProfileActivity.this, user_id, Toast.LENGTH_LONG).show();

        editname = (AutoCompleteTextView) findViewById(R.id.name);
        editaddress = (AutoCompleteTextView) findViewById(R.id.address);
        editmobile = (AutoCompleteTextView) findViewById(R.id.mobile);

        awesomeValidation = new AwesomeValidation(ValidationStyle.BASIC);

        awesomeValidation.addValidation(this, R.id.name, "^[A-Za-z\\s]{1,}[\\.]{0,1}[A-Za-z\\s]{0,}$", R.string.nameerror);
        awesomeValidation.addValidation(this, R.id.address, RegexTemplate.NOT_EMPTY, R.string.addresserror);
        // awesomeValidation.addValidation(this, R.id.mobile, "^[7-9][0-9]{9}$", R.string.mobileerror);


        add = (Button) findViewById(R.id.Submmit_bt);
        add.setOnClickListener(this);

    }

    private void profileClass() {
        EditUserProfileActivity.profileTrackClass ru = new EditUserProfileActivity.profileTrackClass();
        ru.execute("5");
    }

    private void showProgressDialog() {
        Log.v(TAG, String.format("showProgressDialog"));
        pd = new ProgressDialog(EditUserProfileActivity.this);
        pd.setMessage("loading");
        pd.setCancelable(false);
        pd.show();
    }

    /**
     * Dismiss Progress Dialog.
     */
    private void dismissProgressDialog() {
        Log.v(TAG, String.format("dismissProgressDialog"));

        pd.cancel();


    }

    class profileTrackClass extends AsyncTask<String, Void, String> {

        ServerClass ruc = new ServerClass();


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Log.v(TAG, "onPreExecute");
            showProgressDialog();
        }

        @Override
        protected void onPostExecute(String response) {
            super.onPostExecute(response);
            Log.v(TAG, String.format("onPostExecute :: response = %s", response));
            dismissProgressDialog();
            //Toast.makeText(Employee.this, response, Toast.LENGTH_LONG).show();
            profileDetails(response);

        }

        @Override
        protected String doInBackground(String... params) {
            Log.v(TAG, String.format("doInBackground ::  params= %s", params));
            HashMap<String, String> profileDetails = new HashMap<String, String>();
            profileDetails.put("action", "show_user_profile_by_id_for_edit");
            profileDetails.put("user_id",user_id);
            Log.v(TAG, String.format("user_id:",user_id));
            String loginResult = ruc.sendPostRequest(ServiceUrls.LOGIN_URL, profileDetails);
            Log.v(TAG, String.format("doInBackground :: loginResult= %s", loginResult));
            return loginResult;
        }


    }

    private void profileDetails(String jsonResponse) {


        Log.v(TAG, String.format("JsonResponseOperation :: jsonResponse = %s", jsonResponse));
//        RelativeLayout relativeLayout=(RelativeLayout)findViewById(R.id.relativeLayoutPrabhagDetails);
        if (jsonResponse != null) {


            try {
                Log.v(TAG, "JsonResponseOpeartion :: test");
                JSONObject object = new JSONObject(jsonResponse);
                if (object != null) {
                    JSONArray jsonArrayResult = object.getJSONArray("result");

                    if (jsonArrayResult != null && jsonArrayResult.length() > 0){

                        for (int i = 0; i < jsonArrayResult.length(); i++) {

                            Log.v(TAG, "JsonResponseOpeartion ::");
                            JSONObject jsonObj = jsonArrayResult.getJSONObject(i);
                            if (jsonObj != null) {

                                String username  = jsonObj.getString("sv_user_name");
                                String usermobile  = jsonObj.getString("sv_user_mobile");
                                String usercity= jsonObj.getString("sv_user_add");

                                editname.setText(username);
                                editaddress.setText(usercity);
                                editmobile.setText(usermobile);

                            }
                        }}else if(jsonArrayResult.length()==0){

                        System.out.println("No records found");
                    }
                }
            } catch (JSONException e) {
                Log.v(TAG, "JsonResponseOpeartion :: catch");
                e.printStackTrace();
            }
        }
    }

    public void  edituserclass() {
        EditUserProfileActivity.edituserTrackClass ru = new EditUserProfileActivity.edituserTrackClass();
        ru.execute("5");
    }



    class edituserTrackClass extends AsyncTask<String, Void, String> {

        ServerClass ruc = new ServerClass();


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Log.v(TAG, "onPreExecute");
            //showProgressDialog();
        }

        @Override
        protected void onPostExecute(String response) {
            super.onPostExecute(response);
            Log.v(TAG, String.format("onPostExecute :: response = %s", response));
           // dismissProgressDialog();
            //Toast.makeText(CandiateListView.this, response, Toast.LENGTH_LONG).show();
            //  Toast.makeText(NewEnquiryActivity.this, response, Toast.LENGTH_LONG).show();
            EditUserDetails(response);

        }

        @Override
        protected String doInBackground(String... params) {
            Log.v(TAG, String.format("doInBackground ::  params= %s", params));
            HashMap<String, String> AddUserDetails = new HashMap<String, String>();
            AddUserDetails.put("user_name",editname.getText().toString());
            AddUserDetails.put("add",editaddress.getText().toString());
            AddUserDetails.put("phone",editmobile.getText().toString());
            AddUserDetails.put("user_id",user_id);
            Log.v("user_id", user_id);
            AddUserDetails.put("action","edit_register_user_profile");
            String loginResult2 = ruc.sendPostRequest(ServiceUrls.LOGIN_URL, AddUserDetails);

            Log.v(TAG, String.format("doInBackground :: loginResult= %s", loginResult2));
            return loginResult2;
        }


    }
    private void EditUserDetails(String jsonResponse) {

        Log.v(TAG, String.format("loginServerResponse :: response = %s", jsonResponse));

        JSONObject jsonObjLoginResponse = null;
        try {
            jsonObjLoginResponse = new JSONObject(jsonResponse);
            String success = jsonObjLoginResponse.getString(getResources().getString(R.string.success));

            if (success.equalsIgnoreCase(getResources().getString(R.string.two))) {
                Toast.makeText(EditUserProfileActivity.this, "Edit Sucessfully", Toast.LENGTH_SHORT).show();

                editname.getText().clear();
                editaddress.getText().clear();
                editmobile.getText().clear();
                Intent intent = new Intent(EditUserProfileActivity.this,Search_userActivity.class);
                intent.putExtra("Button_type","Both");
                startActivity(intent);

            } else if (success.equalsIgnoreCase(getResources().getString(R.string.zero))) {
                Toast.makeText(EditUserProfileActivity.this, "Failed to Edit", Toast.LENGTH_SHORT).show();
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    @Override
    public void onClick(View v) {
        submitForm();
    }
    private void submitForm() {
        //first validate the form then move ahead
        //if this becomes true that means validation is successfull
        if (awesomeValidation.validate()) {
            // Toast.makeText(this, "Validation Successfull", Toast.LENGTH_LONG).show();
            edituserclass();

            //process the data further
            editname.getText().clear();
            editaddress.getText().clear();
            editmobile.getText().clear();
            Intent intent = new Intent(EditUserProfileActivity.this,Search_userActivity.class);
            intent.putExtra("Button_type","Both");
            startActivity(intent);
        }
    }

}
