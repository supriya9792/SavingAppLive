package com.example.admin.saving_scheme_live.Activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;

import com.example.admin.saving_scheme_live.AdapterandModel.Admin_profileAdapter;
import com.example.admin.saving_scheme_live.AdapterandModel.All_transactionAdapter;
import com.example.admin.saving_scheme_live.AdapterandModel.Recent_transactionAdapter;
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
import java.util.List;

public class All_TransactionActivity extends AppCompatActivity {
//    private RecyclerView recyclerView;
//    public static String TAG = MainActivity.class.getName();
//    ImageView admin;
//    private ProgressDialog pd;
//    all_transaction_list alltransactionlist;
//    String trans_type;
//    //All_transactionAdapter adapter;
//    private RecyclerView.LayoutManager layoutManager;
//    private List<all_transaction_list> all_trans_lists;
//    ArrayList<all_transaction_list> subArrayList = new ArrayList<all_transaction_list>();
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
        setContentView(R.layout.activity_all__transaction);

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));


      //  adapter = new Admin_profileAdapter(this, subArrayList);
       // recyclerView.setAdapter(adapter);

        month_collectionClass();
        initToolbar();

    }

    private void initToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("All Transactions");
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
                Intent intent = new Intent(All_TransactionActivity.this,MainActivity.class);
                startActivity(intent);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void month_collectionClass() {
        All_TransactionActivity.month_collectionTrackClass ru = new All_TransactionActivity.month_collectionTrackClass();
        ru.execute("5");
    }
    private void showProgressDialog() {
        Log.v(TAG, String.format("showProgressDialog"));
        pd = new ProgressDialog(All_TransactionActivity.this);
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
            profileDetails.put("admin_id", SharedPrefereneceUtil.getSavingSchemeId(All_TransactionActivity.this));
            Log.v(TAG, String.format("admin_id:",SharedPrefereneceUtil.getSavingSchemeId(All_TransactionActivity.this)));
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

}
