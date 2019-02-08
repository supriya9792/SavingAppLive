

package com.example.admin.saving_scheme_live.Activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.admin.saving_scheme_live.AdapterandModel.Admin_profileAdapter;
import com.example.admin.saving_scheme_live.AdapterandModel.Search_userAdapter;
import com.example.admin.saving_scheme_live.AdapterandModel.User_profileAdapter;
import com.example.admin.saving_scheme_live.AdapterandModel.admin_profile_list;

import com.example.admin.saving_scheme_live.R;
import com.example.admin.saving_scheme_live.Utility.ServerClass;
import com.example.admin.saving_scheme_live.Utility.ServiceUrls;
import com.example.admin.saving_scheme_live.Utility.SharedPrefereneceUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class AdminProfileActivity extends AppCompatActivity {
    ImageView logout;
    public TextView name,city,mail,phone,total_amount;
    String mobile,Email1;
    private final String TAG = UserProfileActivity.class.getName();
    private ProgressDialog pd;
    admin_profile_list admin_list;
    private List<admin_profile_list> list;
    private RecyclerView recyclerView;
    ArrayList<admin_profile_list> subArrayList = new ArrayList<admin_profile_list>();
    private ArrayList<admin_profile_list> arraylist;
    Admin_profileAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_profile);

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


        name=(TextView) findViewById(R.id.name);
        city=(TextView) findViewById(R.id.city);
        mail=(TextView) findViewById(R.id.email);
        phone=(TextView) findViewById(R.id.phone);
        total_amount=(TextView)findViewById(R.id.amount1);

        logout=findViewById(R.id.logout);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                android.support.v7.app.AlertDialog.Builder alertDialogBuilder = new android.support.v7.app.AlertDialog.Builder(AdminProfileActivity.this);
                alertDialogBuilder.setTitle("");
                alertDialogBuilder
                        .setMessage("Do You Really Want to Logout?")
                        .setCancelable(false)
                        .setPositiveButton("Yes",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        moveTaskToBack(true);
                                        SharedPrefereneceUtil.LogOut(AdminProfileActivity.this);
                                        SharedPreferences settings = AdminProfileActivity.this.getSharedPreferences("Login", Context.MODE_PRIVATE);
                                        settings.edit().clear().apply();
                                        Intent intent = new Intent(AdminProfileActivity.this, LoginActivity.class);
                                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                        startActivity(intent);
                                        finish();
                                        //android.os.Process.killProcess(android.os.Process.myPid());
                                        //System.exit(1);
                                    }
                                })

                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {

                                dialog.cancel();
                            }
                        });

                android.support.v7.app.AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();



            }
        });

        phone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:"+phone.getText().toString()));
                startActivity(intent);
            }
        });

//        mail.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(Intent.ACTION_SENDTO); // it's not ACTION_SEND
//                intent.setType("text/plain");
//                intent.putExtra(Intent.EXTRA_SUBJECT, "Subject of email");
//                intent.putExtra(Intent.EXTRA_TEXT, "Body of email");
//                intent.setData(Uri.parse("mailto:supriyab9792@gmail.com")); // or just "mailto:" for blank
//                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK); // this will make such that when user returns to your app, your app is displayed, instead of the email app.
//                startActivity(intent);
//
////                Intent intent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
////                        "mailto",Email1, null));
////                startActivity(Intent.createChooser(intent, "Choose an Email client :"));
//            }
//        });

//        show_Admin_profile();
//        show_total_collection();
        profileClass();
        Total_collectioneClass();
        month_collectionClass();
    }

    private void profileClass() {
        AdminProfileActivity.profileTrackClass ru = new AdminProfileActivity.profileTrackClass();
        ru.execute("5");
    }

    private void showProgressDialog() {
        Log.v(TAG, String.format("showProgressDialog"));
        pd = new ProgressDialog(AdminProfileActivity.this);
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
            //showProgressDialog();
        }

        @Override
        protected void onPostExecute(String response) {
            super.onPostExecute(response);
            Log.v(TAG, String.format("onPostExecute :: response = %s", response));
            //dismissProgressDialog();
            //Toast.makeText(Employee.this, response, Toast.LENGTH_LONG).show();
            profileDetails(response);

        }

        @Override
        protected String doInBackground(String... params) {
            Log.v(TAG, String.format("doInBackground ::  params= %s", params));
            HashMap<String, String> profileDetails = new HashMap<String, String>();
            profileDetails.put("action", "show_scheme_profile_by_id");
            profileDetails.put("admin_id", SharedPrefereneceUtil.getSavingSchemeId(AdminProfileActivity.this));
            Log.v(TAG, String.format("admin_id:",SharedPrefereneceUtil.getSavingSchemeId(AdminProfileActivity.this)));
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

                    if (jsonArrayResult != null && jsonArrayResult.length() > 0) {
                        for (int i = 0; i < jsonArrayResult.length(); i++) {
                            Log.v(TAG, "JsonResponseOpeartion ::");
                            JSONObject jsonObj = jsonArrayResult.getJSONObject(i);
                            if (jsonObj != null) {

                                String name1=jsonObj.getString("saving_scheme_name");
                                Email1 = jsonObj.getString("saving_scheme_email");
                                mobile = jsonObj.getString("saving_scheme_mobile");
                                String City1 = jsonObj.getString("saving_scheme_city");

                               String City12="   "+City1;
                               String mobile1="   "+mobile;
                               String email12="   "+Email1;

                               name.setText(name1);
                                mail.setText(email12);
                                phone.setText(mobile1);
                                city.setText(City12);

                            }
                        }
                    } else if (jsonArrayResult.length() == 0) {
                        System.out.println("No records found");
                    }
                }
            } catch (JSONException e) {
                Log.v(TAG, "JsonResponseOpeartion :: catch");
                e.printStackTrace();
            }
        }
    }

    private void Total_collectioneClass() {
        AdminProfileActivity.total_collectionTrackClass ru = new AdminProfileActivity.total_collectionTrackClass();
        ru.execute("5");
    }
    class total_collectionTrackClass extends AsyncTask<String, Void, String> {

        ServerClass ruc = new ServerClass();


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Log.v(TAG, "onPreExecute");
           // showProgressDialog();
        }

        @Override
        protected void onPostExecute(String response) {
            super.onPostExecute(response);
            Log.v(TAG, String.format("onPostExecute :: response = %s", response));
            //dismissProgressDialog();
            //Toast.makeText(Employee.this, response, Toast.LENGTH_LONG).show();
            total_collectionDetails(response);

        }

        @Override
        protected String doInBackground(String... params) {
            Log.v(TAG, String.format("doInBackground ::  params= %s", params));
            HashMap<String, String> profileDetails = new HashMap<String, String>();
            profileDetails.put("action","show_scheme_collection_by_id");
            profileDetails.put("admin_id", SharedPrefereneceUtil.getSavingSchemeId(AdminProfileActivity.this));
            Log.v(TAG, String.format("admin_id:",SharedPrefereneceUtil.getSavingSchemeId(AdminProfileActivity.this)));
            String loginResult = ruc.sendPostRequest(ServiceUrls.LOGIN_URL, profileDetails);
            Log.v(TAG, String.format("doInBackground :: loginResult= %s", loginResult));
            return loginResult;
        }


    }
    private void total_collectionDetails(String jsonResponse) {


        Log.v(TAG, String.format("JsonResponseOperation :: jsonResponse = %s", jsonResponse));
//        RelativeLayout relativeLayout=(RelativeLayout)findViewById(R.id.relativeLayoutPrabhagDetails);
        if (jsonResponse != null) {


            try {
                Log.v(TAG, "JsonResponseOpeartion :: test");
                JSONObject object = new JSONObject(jsonResponse);
                if (object != null) {
                    String jsonArrayResult = object.getString("result");

                    String total_collection="₹ "+jsonArrayResult;
                    total_amount.setText(total_collection);

                    Log.v(TAG, String.format("jsonArrayResult :: --------", jsonArrayResult));
                }
            } catch (JSONException e) {
                Log.v(TAG, "JsonResponseOpeartion :: catch");
                e.printStackTrace();
            }
        }
    }


    private void month_collectionClass() {
        AdminProfileActivity.month_collectionTrackClass ru = new AdminProfileActivity.month_collectionTrackClass();
        ru.execute("5");
    }
    class month_collectionTrackClass extends AsyncTask<String, Void, String> {

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
            month_collectionDetails(response);

        }

        @Override
        protected String doInBackground(String... params) {
            Log.v(TAG, String.format("doInBackground ::  params= %s", params));
            HashMap<String, String> profileDetails = new HashMap<String, String>();
            profileDetails.put("action","show_scheme_collection_months");
            profileDetails.put("admin_id", SharedPrefereneceUtil.getSavingSchemeId(AdminProfileActivity.this));
            Log.v(TAG, String.format("admin_id:",SharedPrefereneceUtil.getSavingSchemeId(AdminProfileActivity.this)));
            String loginResult = ruc.sendPostRequest(ServiceUrls.LOGIN_URL, profileDetails);
            Log.v(TAG, String.format("doInBackground :: loginResult= %s", loginResult));
            return loginResult;
        }
    }
    private void month_collectionDetails(String jsonResponse) {


        Log.v(TAG, String.format("JsonResponseOperation :: jsonResponse = %s", jsonResponse));
//        RelativeLayout relativeLayout=(RelativeLayout)findViewById(R.id.relativeLayoutPrabhagDetails);
        if (jsonResponse != null) {


            try {
                Log.v(TAG, "JsonResponseOpeartion :: test");
                JSONObject object = new JSONObject(jsonResponse);
                if (object != null) {
                    JSONArray jsonArrayResult = object.getJSONArray("result");

                    if (jsonArrayResult != null && jsonArrayResult.length() > 0) {
                        for (int i = 0; i < jsonArrayResult.length(); i++) {
                            admin_list=new admin_profile_list();
                            Log.v(TAG, "JsonResponseOpeartion ::");
                            JSONObject jsonObj = jsonArrayResult.getJSONObject(i);
                            if (jsonObj != null) {

                                String date1=jsonObj.getString("collection_month");
                                String deposite=jsonObj.getString("deposite");
                                String Withdrawn=jsonObj.getString("withdrawn");
                                String ttl_collection = jsonObj.getString("ttl_month_collection");
                                String collection="₹"+ttl_collection;

                                String [] dateParts = date1.split(" ");
                                String month = dateParts[0];
                                String year = dateParts[1];

                                admin_list.setBalance1("₹"+deposite);
                                admin_list.setBalance2("₹"+Withdrawn);
                                admin_list.setBalance(collection);
                                admin_list.setMonth(month);
                                admin_list.setYear(year);



                                //  feedList.setNews_discription(Discription);

                                subArrayList.add(admin_list);
                                adapter = new Admin_profileAdapter(this, subArrayList);

                                recyclerView.setAdapter(adapter);


                            }
                        }
                    } else if (jsonArrayResult.length() == 0) {
                        System.out.println("No records found");
                    }
                }
            } catch (JSONException e) {
                Log.v(TAG, "JsonResponseOpeartion :: catch");
                e.printStackTrace();
            }
        }
    }

    public void onBackPressed() {
        Intent intent = new Intent(AdminProfileActivity.this,MainActivity.class);

        startActivity(intent);
    }

}
