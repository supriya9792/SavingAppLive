package com.example.admin.saving_scheme_live.Activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.admin.saving_scheme_live.AdapterandModel.Search_userAdapter;
import com.example.admin.saving_scheme_live.AdapterandModel.User_profileAdapter;
import com.example.admin.saving_scheme_live.AdapterandModel.search_user_list;
import com.example.admin.saving_scheme_live.AdapterandModel.user_profile_list;
import com.example.admin.saving_scheme_live.R;
import com.example.admin.saving_scheme_live.Utility.ServerClass;
import com.example.admin.saving_scheme_live.Utility.ServiceUrls;
import com.example.admin.saving_scheme_live.Utility.SharedPrefereneceUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static android.content.ContentValues.TAG;

public class UserProfileActivity extends AppCompatActivity {
    String user_id,user_name,user_city;
    String City1;
    public TextView name,total_balance,city,phone,email,idd,name1,city1,amount1,id_withdraw,
            name_withdraw,city_withdraw,amount_withdraw,record;
    private final String TAG = UserProfileActivity.class.getName();
    private ProgressDialog pd;
        private RecyclerView recyclerView;
    private User_profileAdapter adapter;
    user_profile_list profile_lists;
    private RecyclerView.LayoutManager layoutManager;
    private List<user_profile_list> profilelists;
    ArrayList<user_profile_list> subArrayList = new ArrayList<user_profile_list>();
    String Balance,Balance1;
    String amount_type;

    EditText userid,deposited_amount,userId1,withdraw_amount;
    String mobile,Email;
    ImageView edit;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        record=(TextView)findViewById(R.id.text1);

        total_balance=(TextView)findViewById(R.id.amount1);


        city=(TextView)findViewById(R.id.city);
        phone=(TextView)findViewById(R.id.phone);

        phone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:"+phone.getText().toString()));
                startActivity(intent);
            }
        });

        edit=(ImageView) findViewById(R.id.edit);
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(UserProfileActivity.this,EditUserProfileActivity.class);
                intent.putExtra("user_id",user_id);
                startActivity(intent);
            }
        });


        profileClass();

        final FloatingActionButton fab_deposit = (FloatingActionButton) findViewById(R.id.fab_add_money);
        final FloatingActionButton fab_withdraw = (FloatingActionButton) findViewById(R.id.fab_remove_money);


        Bundle bundle1 = getIntent().getExtras();
        if(bundle1.equals(" ")||(bundle1.equals(null))){

        }else {
            user_id = bundle1.getString("userId");
            user_name=bundle1.getString("username");
            user_city=bundle1.getString("city");
            Log.v(TAG, String.format("user_id:",user_id));
            Log.v(TAG, String.format("user_name:",user_name));
        }
       // Toast.makeText(UserProfileActivity.this,user_id,Toast.LENGTH_SHORT).show();




        name=findViewById(R.id.name);
        name.setText(user_name);


        //profile_lists=subArrayList;


      fab_deposit.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View arg0) {

            // get prompts.xml view
            LayoutInflater li = LayoutInflater.from(UserProfileActivity.this);
            View promptsView = li.inflate(R.layout.money_deposite_popup,null);

            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(UserProfileActivity.this);

            // set prompts.xml to alertdialog builder
            alertDialogBuilder.setView(promptsView);

            idd = (TextView) promptsView.findViewById(R.id.id1);
            idd.setText(user_id);
            name1=(TextView) promptsView.findViewById(R.id.name1);
            name1.setText(user_name);
            city1=(TextView) promptsView.findViewById(R.id.city1);
            amount1=(TextView)promptsView.findViewById(R.id.amount1);
            city1.setText(City1);
            amount1.setText(Balance);
            deposited_amount = (EditText) promptsView.findViewById(R.id.deposited_amount);

            // set dialog message
            alertDialogBuilder
                    .setCancelable(false)
                    .setPositiveButton("SUBMIT",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    //depositeclass();
                                    String deposite=deposited_amount.getText().toString();
                                    // Toast.makeText(mContext, deposite, Toast.LENGTH_LONG).show();
                                    System.out.print("Deposite Amount:"+deposite);
                                    if(deposite.equals("")||deposite.equals("0")){
                                        Toast.makeText(UserProfileActivity.this, "Please fill the deposite amount", Toast.LENGTH_LONG).show();
                                    }else{
                                        int  deposite_amt=Integer.parseInt(deposited_amount.getText().toString());
                                        //String numberAsString = String.format ("%d", deposite_amt);
                                        System.out.print("Deposite Amount:"+deposite_amt);
                                        // Toast.makeText(mContext, numberAsString, Toast.LENGTH_SHORT).show();
                                        //  Toast.makeText(mContext,"Deposite amt "+ deposite_amt, Toast.LENGTH_SHORT).show();
                                        if(deposite_amt == 0){
                                            Toast.makeText(UserProfileActivity.this, "Please fill the Valid Deposite amount", Toast.LENGTH_LONG).show();
                                        }else {
                                            depositeclass();
                                           // Toast.makeText(UserProfileActivity.this, UserID, Toast.LENGTH_LONG).show();
                                            //depositeAmount(user_id,String.valueOf(deposite_amt));
                                            finish();
                                            overridePendingTransition( 0, 0);
                                            startActivity(getIntent());
                                            overridePendingTransition( 0, 0);
                                            // moveTaskToBack(true);
                                        }

                                    }
                                }
                            })
                    .setNegativeButton("CANCEL",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog,int id) {
                                    dialog.cancel();
                                }
                            });

            // create alert dialog
            AlertDialog alertDialog = alertDialogBuilder.create();
            // show it
            alertDialog.show();
        }
    });
        fab_withdraw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {

                // get prompts.xml view
                LayoutInflater li = LayoutInflater.from(UserProfileActivity.this);
                View promptsView = li.inflate(R.layout.money_withdraw_popup,null);

                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(UserProfileActivity.this);

                // set prompts.xml to alertdialog builder
                alertDialogBuilder.setView(promptsView);

                id_withdraw = (TextView) promptsView.findViewById(R.id.id12);
                id_withdraw.setText(user_id);
                name_withdraw=(TextView) promptsView.findViewById(R.id.name2);
                name_withdraw.setText(user_name);
                city_withdraw=(TextView) promptsView.findViewById(R.id.city2);
                amount_withdraw=(TextView)promptsView.findViewById(R.id.amount12);
                city_withdraw.setText(City1);
                amount_withdraw.setText(Balance);
                 final String bal[]= Balance.split("₹");
                withdraw_amount = (EditText) promptsView.findViewById(R.id.withdrawn_amount);


                // set dialog message
                alertDialogBuilder
                        .setCancelable(false)
                        .setPositiveButton("SUBMIT",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        int  total_balance=Integer.parseInt(bal[1]);
                                        int withdraw_amt=0;
                                        String with=withdraw_amount.getText().toString();

                                        if(with.equals("") || with.equals("0")){
                                            Toast.makeText(UserProfileActivity.this, "Please fill the Withdraw amount", Toast.LENGTH_LONG).show();
                                        }else{
                                            withdraw_amt=Integer.parseInt(withdraw_amount.getText().toString());
                                            System.out.print("Withdraw Amount:"+withdraw_amt);
                                            if(withdraw_amt == 0){
                                                Toast.makeText(UserProfileActivity.this, "Please fill the Valid Withdraw amount", Toast.LENGTH_LONG).show();
                                            }else{
                                                if(withdraw_amt > total_balance){
                                                    Toast.makeText(UserProfileActivity.this,"Your Balance is Low",Toast.LENGTH_SHORT).show();

                                                }else{
                                                    witdrawclass();
                                                    String withdraw=withdraw_amount.getText().toString();
                                                  // withdrawAmount(user_id,String.valueOf(withdraw_amt));
                                                   finish();
                                                   overridePendingTransition( 0, 0);
                                                   startActivity(getIntent());
                                                   overridePendingTransition( 0, 0);
                                                }
                                            }
                                        }
                                    }
                                })


                        .setNegativeButton("CANCEL",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog,int id) {
                                        dialog.cancel();
                                    }
                                });

                // create alert dialog
                AlertDialog alertDialog = alertDialogBuilder.create();

                // show it
                alertDialog.show();

            }
        });
        //profileClass();
        initToolbar();
    }

    private void initToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("User Profile");
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_navigation, menu);
        return true;
    }
    boolean canAddItem = false;
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_home:
                Intent intent = new Intent(UserProfileActivity.this,MainActivity.class);
                startActivity(intent);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
    private void profileClass() {
        UserProfileActivity.profileTrackClass ru = new UserProfileActivity.profileTrackClass();
        ru.execute("5");
    }

    private void showProgressDialog() {
        Log.v(TAG, String.format("showProgressDialog"));
        pd = new ProgressDialog(UserProfileActivity.this);
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
            profileDetails.put("action", "show_user_by_id");
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
                String success =object.getString(getResources().getString(R.string.success));
                if (success.equalsIgnoreCase(getResources().getString(R.string.zero)))
                {
                    Balance="0";
                    Balance1="₹"+Balance;
                    total_balance.setText("₹ 0");
                   // TextView tv=new TextView(UserProfileActivity.this);
                    record.setText("No Transaction Record Found");
                   // Toast.makeText(UserProfileActivity.this,"Failed to add",Toast.LENGTH_SHORT).show();

                }
                if (object != null) {
                    JSONArray jsonArrayResult = object.getJSONArray("result");

                    if (jsonArrayResult != null && jsonArrayResult.length() > 0){
                        float bal=0;
                        for (int i = 0; i < jsonArrayResult.length(); i++) {
                            profile_lists=new user_profile_list();
                            Log.v(TAG, "JsonResponseOpeartion ::");
                            JSONObject jsonObj = jsonArrayResult.getJSONObject(i);
                            if (jsonObj != null) {

                                String day = jsonObj.getString("day");
                                String month = jsonObj.getString("month");
                                String year = jsonObj.getString("year");
                                String withdraw_amount = jsonObj.getString("sv_user_withdraw_amount");
                                String deposite_amount=jsonObj.getString("sv_user_deposited_amount");
                                String withdraw_rel_amount=jsonObj.getString("sv_user_rel_deposited_amount");
                                String deposite_rel_amount=jsonObj.getString("sv_user_rel_withdraw_amount");


                                mobile  = jsonObj.getString("sv_user_mobile");
                                String City= jsonObj.getString("sv_user_add");
                                String time=jsonObj.getString("time");

                                Integer Total_balance=Integer.parseInt(deposite_amount)-Integer.parseInt(withdraw_amount);
                                Balance=Integer.toString(Total_balance);
                                if(i==0){
                                        bal=Float.parseFloat(Balance);
                                }else{

                                }


                                String Phone1="    "+mobile;
                                City1="     "+City;


                                phone.setText(Phone1);
                                city.setText(City1);

                                String day_month=day+" "+month;


                                Balance1="₹"+Balance;

                                 total_balance.setText(Balance1);

                                 String depositeamount="₹"+deposite_rel_amount;
                                 String withdrawamount="₹"+withdraw_rel_amount;

                                if( (withdraw_rel_amount.equals("0") ||withdraw_rel_amount.equals(null)))
                                {
                                   amount_type="Withdrawn";
                                }else{
                                    amount_type="Deposited";
                                }

                                if(amount_type=="Withdrawn"){
                                    profile_lists.setAmount(depositeamount);
                                }else{
                                    profile_lists.setAmount(withdrawamount);
                                }

                                String Bal=String.valueOf("₹"+bal) ;
                                profile_lists.setBalance(Bal);
                                profile_lists.setDate(day_month);
                                profile_lists.setMonth(year);
                                profile_lists.setYear(time);
                                profile_lists.setWithdraw_deposite(amount_type);

                                if(amount_type=="Withdrawn"){
                                    bal+=Float.parseFloat(deposite_rel_amount);
                                }else if(amount_type=="Deposited"){
                                    bal-=Float.parseFloat(withdraw_rel_amount);
                                }

                                subArrayList.add(profile_lists);
                                adapter = new User_profileAdapter(this, subArrayList);

                                recyclerView.setAdapter(adapter);

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

    public void  depositeclass() {
        UserProfileActivity.depositeTrackClass ru = new UserProfileActivity.depositeTrackClass();
        ru.execute("5");
    }
    class depositeTrackClass extends AsyncTask<String, Void, String> {

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
            DepositeDetails(response);

        }

        @Override
        protected String doInBackground(String... params) {
            Log.v(TAG, String.format("doInBackground ::  params= %s", params));
            HashMap<String, String> AddUserDetails = new HashMap<String, String>();
            AddUserDetails.put("user_id",user_id);
            Log.v("user_id:::::::",user_id );
            AddUserDetails.put("deposite_amount",deposited_amount.getText().toString());
            Log.v("deposite_amount:::::::",deposited_amount.getText().toString());
            String Saving_scheme_id = SharedPrefereneceUtil.getSavingSchemeId(UserProfileActivity.this);
            AddUserDetails.put("scheme_id",Saving_scheme_id);
            Log.v("saving_scheme_ref_id", Saving_scheme_id);
            AddUserDetails.put("action","update_deposite_amount_by_id");
            String loginResult2 = ruc.sendPostRequest(ServiceUrls.LOGIN_URL, AddUserDetails);

            Log.v(TAG, String.format("doInBackground :: loginResult= %s", loginResult2));
            return loginResult2;
        }


    }
    private void DepositeDetails(String jsonResponse) {

        Log.v(TAG, String.format("loginServerResponse :: response = %s", jsonResponse));

        JSONObject jsonObjLoginResponse = null;
        try {
            jsonObjLoginResponse = new JSONObject(jsonResponse);
            String success = jsonObjLoginResponse.getString(getResources().getString(R.string.success));

            if (success.equalsIgnoreCase(getResources().getString(R.string.two)))  {
                Toast.makeText(UserProfileActivity.this,"Money Deposited Sucessfully",Toast.LENGTH_SHORT).show();
                deposited_amount.getText().clear();
                finish();
                startActivity(getIntent());

            }


            else if (success.equalsIgnoreCase(getResources().getString(R.string.zero)))
            {
                Toast.makeText(UserProfileActivity.this,"Failed to add",Toast.LENGTH_SHORT).show();
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void  witdrawclass() {
        UserProfileActivity.withdrawTrackClass ru = new UserProfileActivity.withdrawTrackClass();
        ru.execute("5");
    }
    class withdrawTrackClass extends AsyncTask<String, Void, String> {

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
            WithdrawDetails(response);

        }

        @Override
        protected String doInBackground(String... params) {
            Log.v(TAG, String.format("doInBackground ::  params= %s", params));
            HashMap<String, String> AddUserDetails = new HashMap<String, String>();
            AddUserDetails.put("user_id",user_id);
            Log.v("user_id********", user_id);
            AddUserDetails.put("withdraw_amount",withdraw_amount.getText().toString());
            Log.v("withdraw_amount", withdraw_amount.getText().toString());
            String Saving_scheme_id = SharedPrefereneceUtil.getSavingSchemeId(UserProfileActivity.this);
            AddUserDetails.put("scheme_id",Saving_scheme_id);
            Log.v("saving_scheme_ref_id", Saving_scheme_id);
            AddUserDetails.put("action","update_withdraw_amount_by_id");
            String loginResult2 = ruc.sendPostRequest(ServiceUrls.LOGIN_URL, AddUserDetails);

            Log.v(TAG, String.format("doInBackground :: loginResult= %s", loginResult2));
            return loginResult2;
        }


    }
    private void WithdrawDetails(String jsonResponse) {

        Log.v(TAG, String.format("loginServerResponse :: response = %s", jsonResponse));

        JSONObject jsonObjLoginResponse = null;
        try {
            jsonObjLoginResponse = new JSONObject(jsonResponse);
            String success = jsonObjLoginResponse.getString(getResources().getString(R.string.success));

            if (success.equalsIgnoreCase(getResources().getString(R.string.two)))  {

                Toast.makeText(UserProfileActivity.this,"Money Withdraw Sucessfully",Toast.LENGTH_SHORT).show();
                withdraw_amount.getText().clear();

                finish();
                startActivity(getIntent());
            }
            else if (success.equalsIgnoreCase(getResources().getString(R.string.zero)))
            {
                Toast.makeText(UserProfileActivity.this,"Failed to add",Toast.LENGTH_SHORT).show();
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

//    public void onBackPressed() {
//        Intent intent = new Intent(UserProfileActivity.this,Search_userActivity.class);
//        intent.putExtra("Button_type","Both");
//        startActivity(intent);
//    }
}
