package com.example.admin.saving_scheme_live.Activity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;
import com.basgeekball.awesomevalidation.utility.RegexTemplate;

import com.example.admin.saving_scheme_live.R;
import com.example.admin.saving_scheme_live.Utility.ServerClass;
import com.example.admin.saving_scheme_live.Utility.ServiceUrls;
import com.example.admin.saving_scheme_live.Utility.SharedPrefereneceUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class AdduserActivity extends AppCompatActivity implements View.OnClickListener  {

    AutoCompleteTextView name ,address,mobile;
    Button add;

    private AwesomeValidation awesomeValidation;
    private final String TAG = AdduserActivity.class.getName();
    private ProgressDialog pd;
    public  String userId;
    public  String username;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adduser);

        name = (AutoCompleteTextView) findViewById(R.id.name);
        address = (AutoCompleteTextView) findViewById(R.id.address);
        mobile = (AutoCompleteTextView) findViewById(R.id.mobile);


        awesomeValidation = new AwesomeValidation(ValidationStyle.BASIC);

        awesomeValidation.addValidation(this, R.id.name, "^[A-Za-z\\s]{1,}[\\.]{0,1}[A-Za-z\\s]{0,}$", R.string.nameerror);
        awesomeValidation.addValidation(this, R.id.address, RegexTemplate.NOT_EMPTY, R.string.addresserror);
        awesomeValidation.addValidation(this, R.id.mobile, "[0-9]{10}", R.string.mobileerror);
        add = (Button) findViewById(R.id.Submmit_bt);
        add.setOnClickListener(this);
    }

    public void  adduserclass() {
        AdduserActivity.adduserTrackClass ru = new AdduserActivity.adduserTrackClass();
        ru.execute("5");
    }
    private void showProgressDialog() {
        Log.v(TAG, String.format("showProgressDialog"));
        pd = new ProgressDialog(AdduserActivity.this);
        pd.setMessage("loading");
        pd.setCancelable(false);
        pd.show();
    }

    private void dismissProgressDialog() {
        Log.v(TAG, String.format("dismissProgressDialog"));
        pd.cancel();
    }

    class adduserTrackClass extends AsyncTask<String, Void, String> {

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
            //Toast.makeText(CandiateListView.this, response, Toast.LENGTH_LONG).show();
            //  Toast.makeText(NewEnquiryActivity.this, response, Toast.LENGTH_LONG).show();
            AddUserDetails(response);

        }

        @Override
        protected String doInBackground(String... params) {
            Log.v(TAG, String.format("doInBackground ::  params= %s", params));
            HashMap<String, String> AddUserDetails = new HashMap<String, String>();
            AddUserDetails.put("name",name.getText().toString());
            AddUserDetails.put("add",address.getText().toString());
            AddUserDetails.put("mobileno",mobile.getText().toString());
            String Saving_scheme_id = SharedPrefereneceUtil.getSavingSchemeId(AdduserActivity.this);
            AddUserDetails.put("saving_scheme_ref_id",Saving_scheme_id);
            Log.v("saving_scheme_ref_id", Saving_scheme_id);
            AddUserDetails.put("action","register_user");
            String loginResult2 = ruc.sendPostRequest(ServiceUrls.LOGIN_URL, AddUserDetails);

            Log.v(TAG, String.format("doInBackground :: loginResult= %s", loginResult2));
            return loginResult2;
        }


    }
    private void AddUserDetails(String jsonResponse) {

        Log.v(TAG, String.format("loginServerResponse :: response = %s", jsonResponse));

        JSONObject jsonObjLoginResponse = null;
        try {
            jsonObjLoginResponse = new JSONObject(jsonResponse);
            String success = jsonObjLoginResponse.getString(getResources().getString(R.string.success));

            if (success.equalsIgnoreCase(getResources().getString(R.string.two)))  {

                userId = jsonObjLoginResponse.getString("sv_user_id");
                username = jsonObjLoginResponse.getString("sv_user_name");
                Log.v(TAG, String.format("loginServerResponse :: sv_user_id = %s", userId));
                Log.v(TAG, String.format("loginServerResponse :: sv_user_name = %s", username));
                showCustomDialog();

                Toast.makeText(AdduserActivity.this,"Added Sucessfully",Toast.LENGTH_SHORT).show();

                name.getText().clear();
                address.getText().clear();
                mobile.getText().clear();

            }


            else if (success.equalsIgnoreCase(getResources().getString(R.string.zero)))
            {
                Toast.makeText(AdduserActivity.this,"Failed to add",Toast.LENGTH_SHORT).show();
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    private void showCustomDialog(){
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE); // before
        dialog.setContentView(R.layout.dialog_dark);
        dialog.setCancelable(true);

        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.WRAP_CONTENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;

        ((TextView) dialog.findViewById(R.id.username)).setText(username);
        ((TextView) dialog.findViewById(R.id.id)).setText(userId);


        ((ImageButton) dialog.findViewById(R.id.bt_close)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                Intent intent = new Intent(AdduserActivity.this,Search_userActivity.class);
                intent.putExtra("Button_type","Both");
                startActivity(intent);
            }
        });


        dialog.show();
        dialog.getWindow().setAttributes(lp);
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
            adduserclass();

            //process the data further
        }
    }

//    public void onBackPressed() {
//        Intent intent = new Intent(AdduserActivity.this,Search_userActivity.class);
//        intent.putExtra("Button_type","Both");
//        startActivity(intent);
//    }
}
