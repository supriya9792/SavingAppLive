package com.example.admin.saving_scheme_live.Activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.admin.saving_scheme_live.AdapterandModel.Search_userAdapter;
import com.example.admin.saving_scheme_live.AdapterandModel.search_user_list;
import com.example.admin.saving_scheme_live.R;
import com.example.admin.saving_scheme_live.Utility.ServerClass;
import com.example.admin.saving_scheme_live.Utility.ServiceUrls;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class Search_userActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private Search_userAdapter adapter;
    search_user_list searchlist;
    private RecyclerView.LayoutManager layoutManager;
    private List<search_user_list> search_lists;
    ArrayList<search_user_list> subArrayList = new ArrayList<search_user_list>();
    public static String TAG = Search_userActivity.class.getName();
    private ProgressDialog pd;
    FloatingActionButton fab;
    private EditText inputsearch;
    String Button_type,Balance1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_user);

        Bundle bundle1 = getIntent().getExtras();
        Button_type=bundle1.getString("Button_type");


        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        recyclerView.setAdapter(adapter);




        fab=(FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(Search_userActivity.this,AdduserActivity.class);
                startActivity(intent);
            }
        });
        userClass();
        inputsearch=(EditText)findViewById(R.id.search_text);
        inputsearch.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable arg0) {
                // TODO Auto-generated method stub
                if (Search_userActivity.this.adapter == null){
                    // some print statement saying it is null
                    Toast toast = Toast.makeText(Search_userActivity.this,"no record found", Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                }
                else
                {
                    Search_userActivity.this.adapter.filter(String.valueOf(arg0));

                }
            }

            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1,
                                          int arg2, int arg3) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onTextChanged(CharSequence arg0, int arg1, int arg2,
                                      int arg3) {
                // TODO Auto-generated method stub


            }
        });
        initToolbar();

    }
    private void initToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Users");
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
                Intent intent = new Intent(Search_userActivity.this,MainActivity.class);
                startActivity(intent);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void userClass() {
        Search_userActivity.userTrackClass ru = new Search_userActivity.userTrackClass();
        ru.execute("5");
    }

    private void showProgressDialog() {
        Log.v(TAG, String.format("showProgressDialog"));
        pd = new ProgressDialog(Search_userActivity.this);
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
    public void onBackPressed() {
        Intent intent = new Intent(Search_userActivity.this,MainActivity.class);
        intent.putExtra("Button_type","Both");
        startActivity(intent);
    }

    class userTrackClass extends AsyncTask<String, Void, String> {

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
            userDetails(response);

        }

        @Override
        protected String doInBackground(String... params) {
            Log.v(TAG, String.format("doInBackground ::  params= %s", params));
            HashMap<String, String> feedDetails = new HashMap<String, String>();
            feedDetails.put("action", "show_register_user");
            //EmployeeDetails.put("admin_id", SharedPrefereneceUtil.getadminId(Employee.this));
            String loginResult = ruc.sendPostRequest(ServiceUrls.LOGIN_URL, feedDetails);
            Log.v(TAG, String.format("doInBackground :: loginResult= %s", loginResult));
            return loginResult;
        }


    }

    private void userDetails(String jsonResponse) {


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
                            searchlist=new search_user_list();
                            Log.v(TAG, "JsonResponseOpeartion ::");
                            JSONObject jsonObj = jsonArrayResult.getJSONObject(i);
                            if (jsonObj != null) {

                                String name     = jsonObj.getString("sv_user_name");
                                String address = jsonObj.getString("sv_user_add");
                                String id = jsonObj.getString("sv_user_id");
                                String withdraw_amount = jsonObj.getString("sv_user_withdraw_amount");
                                String deposite_amount=jsonObj.getString("sv_user_deposited_amount");

                                Integer Total_balance=Integer.parseInt(deposite_amount)-Integer.parseInt(withdraw_amount);
                                String Balance=Integer.toString(Total_balance);
                               // Balance1="₹"+Balance;



                                searchlist.setName(name);
                                searchlist.setMob(address);
                                searchlist.setUserId(id);
                                searchlist.setDeposte_withdrawn(Button_type);
                                searchlist.setBalance(Balance);

                                //  feedList.setNews_discription(Discription);

                                subArrayList.add(searchlist);
                                adapter = new Search_userAdapter(this, subArrayList);

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
}
