package com.example.admin.saving_scheme_live.Activity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Rect;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.DragEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.example.admin.saving_scheme_live.AdapterandModel.Recent_transactionAdapter;
import com.example.admin.saving_scheme_live.AdapterandModel.Search_userAdapter;
import com.example.admin.saving_scheme_live.AdapterandModel.User_profileAdapter;
import com.example.admin.saving_scheme_live.AdapterandModel.recent_transaction_list;
import com.example.admin.saving_scheme_live.AdapterandModel.search_user_list;
import com.example.admin.saving_scheme_live.R;
import com.example.admin.saving_scheme_live.Utility.ServerClass;
import com.example.admin.saving_scheme_live.Utility.ServiceUrls;
import com.example.admin.saving_scheme_live.Utility.SharedPrefereneceUtil;
import com.example.admin.saving_scheme_live.utils.ViewAnimation;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class MainActivity extends AppCompatActivity  {
    private RecyclerView recyclerView;
    private Recent_transactionAdapter adapter;
    recent_transaction_list recenttransactionlist;
    String trans_type;
    private RecyclerView.LayoutManager layoutManager;
    private List<recent_transaction_list> search_lists;
    ArrayList<recent_transaction_list> subArrayList = new ArrayList<recent_transaction_list>();
    LinearLayout layoutBottomSheet;
    private BottomSheetBehavior sheetBehavior;
    private View lyt_deposit;
    private View lyt_withdraw;
    private boolean rotate = false;
    private LinearLayout lyt_text,lyt_search;
    Toolbar toolbar;
    public static String TAG = MainActivity.class.getName();
    ImageView arrow;
    private ProgressDialog pd;
     EditText userid,deposited_amount,userId1,withdraw_amount;
     Button all_transaction;
    private boolean mIsSheetTouched = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setNestedScrollingEnabled(false);

        lyt_text=(LinearLayout)findViewById(R.id.text);
        lyt_search=(LinearLayout)findViewById(R.id.search);

        arrow=(ImageView)findViewById(R.id.uparrow);


        adapter = new Recent_transactionAdapter(this, subArrayList);
        recyclerView.setAdapter(adapter);
        all_transaction=(Button) findViewById(R.id.all_tran);
        all_transaction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                Intent intent = new Intent(MainActivity.this,All_TransactionActivity.class);
                startActivity(intent);

            }
        });
        final FloatingActionButton fab_deposit = (FloatingActionButton) findViewById(R.id.fab_add_money);
        final FloatingActionButton fab_more = (FloatingActionButton) findViewById(R.id.fab_more);
        final FloatingActionButton fab_withdraw = (FloatingActionButton) findViewById(R.id.fab_remove_money);


        lyt_deposit = findViewById(R.id.lyt_deposit);
        lyt_withdraw = findViewById(R.id.lyt_withdraw);
        ViewAnimation.initShowOut(lyt_deposit);
        ViewAnimation.initShowOut(lyt_withdraw);

        fab_more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toggleFabMode(v);

            }
        });

        fab_deposit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                Intent intent = new Intent(MainActivity.this,Search_userActivity.class);
                intent.putExtra("Button_type","Deposite");
                startActivity(intent);

            }
        });

        fab_withdraw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                Intent intent = new Intent(MainActivity.this,Search_userActivity.class);
                intent.putExtra("Button_type","Withdrawn");
                startActivity(intent);

            }
        });
        lyt_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,Search_userActivity.class);
                intent.putExtra("Button_type","Both");
                startActivity(intent);

                Toast.makeText(getApplicationContext(), "Search user..", Toast.LENGTH_SHORT).show();

            }
        });
        recenttransactionClass();

        layoutBottomSheet=findViewById(R.id.bottom_sheet);
        sheetBehavior = BottomSheetBehavior.from(layoutBottomSheet);
        sheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
//        sheetBehavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
//            @Override
//            public void onStateChanged(@NonNull View bottomSheet, int newState) {
//                switch (newState) {
//                    case BottomSheetBehavior.STATE_HIDDEN:
//                        break;
//                    case BottomSheetBehavior.STATE_EXPANDED: {
//                        //btnBottomSheet.setText("Close Sheet");
//                    }
//                    break;
//                    case BottomSheetBehavior.STATE_COLLAPSED: {
//                        //btnBottomSheet.setText("Expand Sheet");
//                    }
//                    break;
//                    case BottomSheetBehavior.STATE_DRAGGING:
//                        break;
//                    case BottomSheetBehavior.STATE_SETTLING:
//                        break;
//                }
//            }
//
//            @Override
//            public void onSlide(@NonNull View bottomSheet, float slideOffset) {
//
//            }
//        });
         //layoutBottomSheet.onDragEvent()
        arrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toggleBottomSheet();
            }
        });

        initToolbar();

    }
    private void initToolbar() {
         toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        String name = SharedPrefereneceUtil.getSavingSchemename(MainActivity.this);
        getSupportActionBar().setTitle(name);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);

        NestedScrollView nested_content = (NestedScrollView) findViewById(R.id.nested_scroll_view);
        nested_content.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                if (scrollY < oldScrollY) { // up

                    // showViews();
                    //showBottomSheetDialog();
                    //toggleBottomSheet();
                    sheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);

                    animateSearchBar(false);
                    // toolbar.setVisibility(View.VISIBLE);
                }
                if (scrollY > oldScrollY) { // down
                    // hideViews();
                    //toggleBottomSheet();
                  sheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                    //animateSearchBar(true);
                    //toolbar.setVisibility(View.INVISIBLE);
                }
            }
        });
    }
    boolean isSearchBarHide = false;
    private void animateSearchBar(final boolean hide) {
        if (isSearchBarHide && hide || !isSearchBarHide && !hide) return;
        isSearchBarHide = hide;
        int moveY = hide ? -(2 * toolbar.getHeight()) : 0;
        //toolbar.animate().translationY(-toolbar.getBottom()).setInterpolator(new AccelerateInterpolator()).start();
        toolbar.animate().translationY(moveY).setStartDelay(100).setDuration(300).start();
        // toolbar.setBackgroundColor(getResources().getColor(R.color.transparent));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.admin_profile_navigation, menu);
        return true;
    }
    boolean canAddItem = false;
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_home:
                Intent intent = new Intent(MainActivity.this,AdminProfileActivity.class);
                startActivity(intent);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void toggleBottomSheet() {
        if (sheetBehavior.getState() != BottomSheetBehavior.STATE_EXPANDED) {
            sheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
            arrow.setImageDrawable(getResources().getDrawable(R.drawable.uparrow));

        } else {
            sheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
            arrow.setImageDrawable(getResources().getDrawable(R.drawable.downarrow));


        }
    }
    boolean doubleBackToExitPressedOnce = false;
    @Override
    public void onBackPressed() {
        if (sheetBehavior.getState()== BottomSheetBehavior.STATE_EXPANDED) {
            sheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
            return;
        }else if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        } else {

            android.support.v7.app.AlertDialog.Builder alertDialogBuilder = new android.support.v7.app.AlertDialog.Builder(this);
            alertDialogBuilder.setTitle("Exit Application?");
            alertDialogBuilder
                    .setMessage("Click yes to exit!")
                    .setCancelable(false)
                    .setPositiveButton("Yes",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    moveTaskToBack(true);
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

            //  super.onBackPressed();
        }


        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce = false;
            }
        }, 2000);


    }

    private void toggleFabMode(View v) {
        rotate = ViewAnimation.rotateFab(v, !rotate);
        if (rotate) {
            ViewAnimation.showIn(lyt_deposit);
            ViewAnimation.showIn(lyt_withdraw);
            lyt_text.setVisibility(View.INVISIBLE);
            //back_drop.setVisibility(View.VISIBLE);
        } else {
            ViewAnimation.showOut(lyt_deposit);
            ViewAnimation.showOut(lyt_withdraw);
            lyt_text.setVisibility(View.VISIBLE);
            //back_drop.setVisibility(View.GONE);
        }
    }
    private void recenttransactionClass() {
        MainActivity.recenttransactionTrackClass ru = new MainActivity.recenttransactionTrackClass();
        ru.execute("5");
    }
    class recenttransactionTrackClass extends AsyncTask<String, Void, String> {

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
           // dismissProgressDialog();
            //Toast.makeText(Employee.this, response, Toast.LENGTH_LONG).show();
            recenttransactionDetails(response);

        }

        @Override
        protected String doInBackground(String... params) {
            Log.v(TAG, String.format("doInBackground ::  params= %s", params));
            HashMap<String, String> recenttransactionDetails = new HashMap<String, String>();
            recenttransactionDetails.put("action", "show_recent_transaction");
            //EmployeeDetails.put("admin_id", SharedPrefereneceUtil.getadminId(Employee.this));
            String loginResult = ruc.sendPostRequest(ServiceUrls.LOGIN_URL, recenttransactionDetails);
            Log.v(TAG, String.format("doInBackground :: loginResult= %s", loginResult));
            return loginResult;
        }


    }
    private void recenttransactionDetails(String jsonResponse) {


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
                            recenttransactionlist=new recent_transaction_list();
                            Log.v(TAG, "JsonResponseOpeartion ::");
                            JSONObject jsonObj = jsonArrayResult.getJSONObject(i);
                            if (jsonObj != null) {

                                String id = jsonObj.getString("sv_user_id");
                                String name = jsonObj.getString("sv_user_name");
                                String city = jsonObj.getString("sv_user_add");
                                String withdraw  = jsonObj.getString("sv_user_rel_deposited_amount");
                                String deposite = jsonObj.getString("sv_user_rel_withdraw_amount");
                                String status=jsonObj.getString("status_time");

                                String depositeamount="₹"+deposite;
                                String withdrawamount="₹"+withdraw;

                                recenttransactionlist.setId(id);
                                recenttransactionlist.setName(name);
                                recenttransactionlist.setCity(city);
                                recenttransactionlist.setStatus(status);

                                if( (withdraw.equals("0") ||withdraw.equals(null)))
                                {
                                    trans_type="Withdrawn";
                                }else{
                                    trans_type="Deposited";
                                }
                                recenttransactionlist.setType(trans_type);

                                if(trans_type=="Withdrawn"){
                                    recenttransactionlist.setAmount(depositeamount);
                                }else{
                                    recenttransactionlist.setAmount(withdrawamount);
                                }

                                subArrayList.add(recenttransactionlist);
                                adapter = new Recent_transactionAdapter(this, subArrayList);

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
