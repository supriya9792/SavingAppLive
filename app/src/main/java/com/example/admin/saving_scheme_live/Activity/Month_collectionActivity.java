package com.example.admin.saving_scheme_live.Activity;

import android.Manifest;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Environment;
import android.support.design.widget.BottomSheetBehavior;
import android.support.v4.app.ActivityCompat;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.admin.saving_scheme_live.Activity.MainActivity;
import com.example.admin.saving_scheme_live.AdapterandModel.Month_collectionAdapter;
import com.example.admin.saving_scheme_live.AdapterandModel.Recent_transactionAdapter;
import com.example.admin.saving_scheme_live.AdapterandModel.month_collection_list;
import com.example.admin.saving_scheme_live.AdapterandModel.recent_transaction_list;
import com.example.admin.saving_scheme_live.AdapterandModel.user_profile_list;

import com.example.admin.saving_scheme_live.R;
import com.example.admin.saving_scheme_live.Utility.ServerClass;
import com.example.admin.saving_scheme_live.Utility.ServiceUrls;
import com.example.admin.saving_scheme_live.Utility.SharedPrefereneceUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import jxl.Workbook;
import jxl.WorkbookSettings;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

public class Month_collectionActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private Month_collectionAdapter adapter;
    month_collection_list monthcollectionlist;
    String trans_type,month,year,month_year;
    private RecyclerView.LayoutManager layoutManager;
    ArrayList<month_collection_list> subArrayList = new ArrayList<month_collection_list>();
    private BottomSheetBehavior bottomSheetBehavior;
    private View lyt_deposit;
    private View lyt_withdraw;
    private boolean rotate = false;
    private LinearLayout lyt_text,lyt_search;
    private TextView Username;
    public static String TAG = MainActivity.class.getName();
    TextView month1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_month_collection);

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        Bundle bundle1 = getIntent().getExtras();
        month = bundle1.getString("Month");
        year=bundle1.getString("Year");
        Log.v(TAG, String.format("Month:",month));
        Log.v(TAG, String.format("Year:",year));
        month_year=month+" "+year;


        monthlytransactionClass();
        initToolbar();
        isWriteStoragePermissionGranted();
    }
    private void initToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setTitle(month_year);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);


    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.export_data, menu);
        return true;
    }
    boolean canAddItem = false;
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_export:
                createExcel();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
    public  boolean isReadStoragePermissionGranted() {
        if (Build.VERSION.SDK_INT >= 23) {
            if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_GRANTED) {
                Log.v(TAG,"Permission is granted1");
                return true;
            } else {

                Log.v(TAG,"Permission is revoked1");
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 3);
                return false;
            }
        }
        else { //permission is automatically granted on sdk<23 upon installation
            Log.v(TAG,"Permission is granted1");
            return true;
        }
    }

    public  boolean isWriteStoragePermissionGranted() {
        if (Build.VERSION.SDK_INT >= 23) {
            if (checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_GRANTED) {
                Log.v(TAG,"Permission is granted2");
                return true;
            } else {

                Log.v(TAG,"Permission is revoked2");
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 2);
                return false;
            }
        }
        else { //permission is automatically granted on sdk<23 upon installation
            Log.v(TAG,"Permission is granted2");
            return true;
        }
    }
    public void createExcel(){
        File sd = Environment.getExternalStorageDirectory();
        String fileName = month_year+new Date().getTime() + ".xls";
        String csvFile = fileName;
        String myfolder=Environment.getExternalStorageDirectory()+"/"+ "SavingSchemeData";
        File directory = new File(myfolder);
        //create directory if not exist
        if (!directory.isDirectory()) {
            directory.mkdirs();
        }
        try {

            //file path

            File file = new File(directory, csvFile);
            WorkbookSettings wbSettings = new WorkbookSettings();
            wbSettings.setLocale(new Locale("en", "EN"));
            WritableWorkbook workbook;
            workbook = Workbook.createWorkbook(file, wbSettings);
            //Excel sheet name. 0 represents first sheet
            WritableSheet sheet = workbook.createSheet("userList", 0);

            sheet.addCell(new Label(0, 0, "UserId")); // column and row
            sheet.addCell(new Label(1, 0, "User Name"));
            sheet.addCell(new Label(2, 0, "User Address"));
            sheet.addCell(new Label(3, 0, "Trasaction Type"));
            sheet.addCell(new Label(4, 0, "Amount"));
            sheet.addCell(new Label(5, 0, "Date Time"));
            int i = 1;
            for (month_collection_list monthlyCollection : subArrayList){

                      String id= monthlyCollection.getId();
                      String name= monthlyCollection.getName();
                      String add= monthlyCollection.getCity();
                      String trans_type=monthlyCollection.getType();
                      String amount=  monthlyCollection.getAmount();
                      String time =monthlyCollection.getStatus();
                      String date=monthlyCollection.getDate();
                      String comb=date+" "+time;
                sheet.addCell(new Label(0, i, id));
                sheet.addCell(new Label(1, i, name));
                sheet.addCell(new Label(2, i, add));
                sheet.addCell(new Label(3, i, trans_type));
                sheet.addCell(new Label(4, i, amount));
                sheet.addCell(new Label(5, i, comb));

                // Balance1="₹"+Balance;

                i++;
            }

            //closing cursor
            workbook.write();
            workbook.close();
            Toast.makeText(getApplication(), "Data Exported in a Excel Sheet", Toast.LENGTH_SHORT).show();
            showCustomDialog();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 2:
                Log.d(TAG, "External storage2");
                if(grantResults[0]== PackageManager.PERMISSION_GRANTED){
                    Log.v(TAG,"Permission: "+permissions[0]+ "was "+grantResults[0]);
                    //resume tasks needing this permission
                   // createExcel();
                }else{
                    //progress.dismiss();
                }
                break;

            case 3:
                Log.d(TAG, "External storage1");
                if(grantResults[0]== PackageManager.PERMISSION_GRANTED){
                    Log.v(TAG,"Permission: "+permissions[0]+ "was "+grantResults[0]);
                    //resume tasks needing this permission
                    //SharePdfFile();
                }else{
                   // progress.dismiss();
                }
                break;
        }
    }
    private void showCustomDialog() {
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE); // before
        dialog.setContentView(R.layout.dataexport_popup);
        dialog.setCancelable(true);

        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.WRAP_CONTENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        ((Button) dialog.findViewById(R.id.ok)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(getApplicationContext(), ((Button) v).getText().toString() + " Clicked", Toast.LENGTH_SHORT).show();
                // dialog.dismiss();
                openDownloadedFolder();
            }
        });

        dialog.show();
        dialog.getWindow().setAttributes(lp);
    }
    private void openDownloadedFolder() {
        //First check if SD Card is present or not

            //Get Download Directory File
            File apkStorage = new File(
                    Environment.getExternalStorageDirectory() + "/"
                          );

            //If file is not present then display Toast
            if (!apkStorage.exists())
                Toast.makeText(Month_collectionActivity.this, "Right now there is no directory. Please download some file first.", Toast.LENGTH_SHORT).show();

            else {

                //If directory is present Open Folder

                /** Note: Directory will open only if there is a app to open directory like File Manager, etc.  **/

                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                Uri uri = Uri.parse(Environment.getExternalStorageDirectory().getPath()
                        + "/");
                intent.setDataAndType(uri, "file/*");
                startActivity(Intent.createChooser(intent, "Open Download Folder"));
            }

    }

    private void monthlytransactionClass() {
        Month_collectionActivity.monthlytransactionTrackClass ru = new Month_collectionActivity.monthlytransactionTrackClass();
        ru.execute("5");
    }
    class monthlytransactionTrackClass extends AsyncTask<String, Void, String> {

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
            monthlytransactionDetails(response);

        }

        @Override
        protected String doInBackground(String... params) {
            Log.v(TAG, String.format("doInBackground ::  params= %s", params));
            HashMap<String, String> monthlytransactionDetails = new HashMap<String, String>();
            monthlytransactionDetails.put("month",month_year);
            Log.v(TAG, String.format("month:::::::", month_year));
            monthlytransactionDetails.put("admin_id",SharedPrefereneceUtil.getSavingSchemeId(Month_collectionActivity.this));
            Log.v(TAG, String.format("admin_id:::::::",SharedPrefereneceUtil.getSavingSchemeId(Month_collectionActivity.this)));
            monthlytransactionDetails.put("action","show_scheme_collection_by_months");
            //EmployeeDetails.put("admin_id", SharedPrefereneceUtil.getadminId(Employee.this));
            String loginResult = ruc.sendPostRequest(ServiceUrls.LOGIN_URL,monthlytransactionDetails);
            Log.v(TAG, String.format("doInBackground :: loginResult= %s", loginResult));
            return loginResult;
        }
    }
    private void monthlytransactionDetails(String jsonResponse) {


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
                            monthcollectionlist = new month_collection_list();
                            Log.v(TAG, "JsonResponseOpeartion ::");
                            JSONObject jsonObj = jsonArrayResult.getJSONObject(i);
                            if (jsonObj != null) {

                                String id = jsonObj.getString("sv_user_id");
                                String name = jsonObj.getString("sv_user_name");
                                String city = jsonObj.getString("sv_user_add");
                                String withdraw = jsonObj.getString("sv_user_rel_deposited_amount");
                                String deposite = jsonObj.getString("sv_user_rel_withdraw_amount");
                                String status = jsonObj.getString("status_time");
                                String date = jsonObj.getString("date");

                                String depositeamount = "₹" + deposite;
                                String withdrawamount = "₹" + withdraw;

                                monthcollectionlist.setId(id);
                                monthcollectionlist.setName(name);
                                monthcollectionlist.setCity(city);
                                monthcollectionlist.setStatus(status);
                                monthcollectionlist.setDate(date);

                                if ((withdraw.equals("0") || withdraw.equals(null))) {
                                    trans_type = "Withdrawn";
                                } else {
                                    trans_type = "Deposited";
                                }
                                monthcollectionlist.setType(trans_type);

                                if (trans_type == "Withdrawn") {
                                    monthcollectionlist.setAmount(depositeamount);
                                } else {
                                    monthcollectionlist.setAmount(withdrawamount);
                                }

                                subArrayList.add(monthcollectionlist);
                                adapter = new Month_collectionAdapter(this, subArrayList);

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
