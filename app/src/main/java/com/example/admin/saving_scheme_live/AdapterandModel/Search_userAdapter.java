package com.example.admin.saving_scheme_live.AdapterandModel;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.admin.saving_scheme_live.Activity.Search_userActivity;
import com.example.admin.saving_scheme_live.Activity.UserProfileActivity;
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
import java.util.Locale;

import static android.content.ContentValues.TAG;

/**
 * Created by admin on 2018-8-20.
 */

public class Search_userAdapter extends RecyclerView.Adapter<Search_userAdapter.MyViewHolder> {

    private Context mContext;
    search_user_list searchlist;
    private List<search_user_list> search_list;
    private ArrayList<search_user_list> arraylist;
     String Balance1,UserID;
    public String userid;
    EditText deposited_amount,withdraw_amount;
    TextView id_deposite,name_deposite,city_deposite,amount_deposite,id_withdraw,name_withdraw,
            city_withdraw,amount_withdraw;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView username,city,userId;
      public FloatingActionButton fab_deposit,fab_withdraw;
        LinearLayout lyt_parent;
        ImageView image;
       // public final MaterialLetterIcon mIcon;


        public MyViewHolder(View view) {
            super(view);
            username = (TextView) view.findViewById(R.id.name);
            city = (TextView) view.findViewById(R.id.mobile);
            userId=(TextView) view.findViewById(R.id.id);
            fab_deposit = (FloatingActionButton)view.findViewById(R.id.fab_add_money);
            fab_withdraw = (FloatingActionButton)view.findViewById(R.id.fab_remove_money);
            lyt_parent=view.findViewById(R.id.lyt_parent);

        }
    }
    public Search_userAdapter(Context mContext, ArrayList<search_user_list> albumList) {
        this.mContext = mContext;
        this.search_list = albumList;
        this.arraylist = new ArrayList<search_user_list>();
        this.arraylist.addAll(albumList);
    }
    @Override
    public int getItemCount() {

        return search_list.size();
    }
    public search_user_list getItem(int position) {

        return search_list.get(position);
    }
    @Override
    public Search_userAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.activity_search_user_list, parent, false);

        return new Search_userAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final Search_userAdapter.MyViewHolder holder, int position) {

        final search_user_list enq = search_list.get(position);
        holder.username.setText(enq.getName());
        holder.city.setText(enq.getAdd());
        holder.userId.setText(enq.getUserId());

        final String userid=enq.getUserId();
        UserID=userid;
        //Log.v(TAG, String.format("userId*****:",userid));

        final String username=enq.getName();
       // Log.v(TAG, String.format("userId*****:",username));

        final String city=enq.getAdd();
        final String ttl_bal_userid=enq.getBalance();//Log.v(TAG, String.format("userId*****:",city));

        String button_type=enq.getDeposte_withdrawn();
                if(button_type.equals("Deposite")){
                    holder.fab_withdraw.setVisibility(View.INVISIBLE);
                    holder.fab_deposit.setVisibility(View.VISIBLE);
                }else if(button_type.equals("Withdrawn")){
                    holder.fab_withdraw.setVisibility(View.VISIBLE);
                    holder.fab_deposit.setVisibility(View.INVISIBLE);
                }else{
                holder.fab_deposit.setVisibility(View.VISIBLE);
                holder.fab_withdraw.setVisibility(View.VISIBLE);
            }

        holder.lyt_parent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent intent = new Intent(mContext, UserProfileActivity.class);
                intent.putExtra("userId",userid);
                intent.putExtra("username",username);
                intent.putExtra("city",city);
                //Log.v(TAG, String.format("userId:",userid));
                mContext.startActivity(intent);
            }
        });

        holder.fab_deposit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
               // profileClass();
                // get prompts.xml view
                LayoutInflater li = LayoutInflater.from(mContext);
                View promptsView = li.inflate(R.layout.money_deposite_popup,null);

                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(mContext);

                // set prompts.xml to alertdialog builder
                alertDialogBuilder.setView(promptsView);


                id_deposite = (TextView) promptsView.findViewById(R.id.id1);
                UserID=userid;
                id_deposite.setText(UserID);
                name_deposite=(TextView) promptsView.findViewById(R.id.name1);
                name_deposite.setText(enq.getName());

                city_deposite=(TextView) promptsView.findViewById(R.id.city1);
                amount_deposite=(TextView)promptsView.findViewById(R.id.amount1);
                city_deposite.setText(enq.getMob());
                String bal="₹"+ttl_bal_userid;
                amount_deposite.setText(bal);
                deposited_amount = (EditText) promptsView.findViewById(R.id.deposited_amount);

                // set dialog message
                alertDialogBuilder
                        .setCancelable(false)
                        .setPositiveButton("SUBMIT",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                       // depositeclass();
                                        String deposite=deposited_amount.getText().toString();
                                       // Toast.makeText(mContext, deposite, Toast.LENGTH_LONG).show();
                                        System.out.print("Deposite Amount:"+deposite);
                                        if(deposite.equals("")||deposite.equals("0")){
                                            Toast.makeText(mContext, "Please fill the deposite amount", Toast.LENGTH_LONG).show();
                                        }else{
                                          int  deposite_amt=Integer.parseInt(deposited_amount.getText().toString());
                                            //String numberAsString = String.format ("%d", deposite_amt);
                                            System.out.print("Deposite Amount:"+deposite_amt);
                                           // Toast.makeText(mContext, numberAsString, Toast.LENGTH_SHORT).show();
                                          //  Toast.makeText(mContext,"Deposite amt "+ deposite_amt, Toast.LENGTH_SHORT).show();
                                            if(deposite_amt == 0){
                                                Toast.makeText(mContext, "Please fill the Valid Deposite amount", Toast.LENGTH_LONG).show();
                                            }else {
                                                //Toast.makeText(mContext, UserID, Toast.LENGTH_LONG).show();
                                                depositeclass();
                                                //depositeAmount(UserID,String.valueOf(deposite_amt));
                                                ((Activity) mContext).finish();
                                                ((Activity) mContext).overridePendingTransition( 0, 0);
                                                ((Activity) mContext).startActivity(((Activity) mContext).getIntent());
                                                ((Activity) mContext).overridePendingTransition( 0, 0);
                                                // moveTaskToBack(true);
                                            }

                                        }
                                        Log.v(TAG, String.format("Deposite amount:",deposite));


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

       holder.fab_withdraw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {

                // get prompts.xml view
                LayoutInflater li = LayoutInflater.from(mContext);
                View promptsView = li.inflate(R.layout.money_withdraw_popup,null);

                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(mContext);

                // set prompts.xml to alertdialog builder
                alertDialogBuilder.setView(promptsView);

                id_withdraw = (TextView) promptsView.findViewById(R.id.id12);
                UserID=userid;

                id_withdraw.setText(UserID);
                name_withdraw=(TextView) promptsView.findViewById(R.id.name2);
                name_withdraw.setText(enq.getName());
                city_withdraw=(TextView) promptsView.findViewById(R.id.city2);
                amount_withdraw=(TextView)promptsView.findViewById(R.id.amount12);
                city_withdraw.setText(enq.getMob());
                String bal="₹"+ttl_bal_userid;
                amount_withdraw.setText(bal);
                withdraw_amount = (EditText) promptsView.findViewById(R.id.withdrawn_amount);

                // set dialog message
                alertDialogBuilder
                        .setCancelable(false)
                        .setPositiveButton("SUBMIT",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        int  total_balance=Integer.parseInt(ttl_bal_userid);
                                        int withdraw_amt=0;
                                        String with=withdraw_amount.getText().toString();

                                        if(with.equals("") || with.equals("0")){
                                            Toast.makeText(mContext, "Please fill the Withdraw amount", Toast.LENGTH_LONG).show();
                                        }else{
                                            withdraw_amt=Integer.parseInt(withdraw_amount.getText().toString());
                                            System.out.print("Withdraw Amount:"+withdraw_amt);
                                            if(withdraw_amt == 0){
                                                Toast.makeText(mContext, "Please fill the Valid Withdraw amount", Toast.LENGTH_LONG).show();
                                            }else{
                                                if(withdraw_amt > total_balance){
                                                    Toast.makeText(mContext,"Your Balance is Low",Toast.LENGTH_SHORT).show();

                                                }else{
                                                    witdrawclass();
                                                    String withdraw=withdraw_amount.getText().toString();
                                                    //withdrawAmount(UserID,String.valueOf(withdraw_amt));
                                                    ((Activity) mContext).finish();
                                                    ((Activity) mContext).overridePendingTransition( 0, 0);
                                                    ((Activity) mContext).startActivity(((Activity) mContext).getIntent());
                                                    ((Activity) mContext).overridePendingTransition( 0, 0);
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
    }

    public void  depositeclass() {
        new Search_userAdapter.depositeTrackClass().execute("5");
    }
    class depositeTrackClass extends AsyncTask<String, Void, String> {

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
           // Log.v(TAG, String.format("onPostExecute :: response = %s", response));
            DepositeDetails(response);

        }

        @Override
        protected String doInBackground(String... params) {
           // Log.v(TAG, String.format("doInBackground ::  params= %s", params));
            HashMap<String, String> AddUserDetails = new HashMap<String, String>();
            AddUserDetails.put("user_id",UserID);
//            Log.v("user_id:::::::",UserID );
            AddUserDetails.put("deposite_amount",deposited_amount.getText().toString());
            //Log.v("deposite_amount:::::::",deposited_amount.getText().toString());
            String Saving_scheme_id = SharedPrefereneceUtil.getSavingSchemeId((Activity) mContext);
            AddUserDetails.put("scheme_id",Saving_scheme_id);
           // Log.v("saving_scheme_ref_id", Saving_scheme_id);
            AddUserDetails.put("action","update_deposite_amount_by_id");
            String loginResult2 = ruc.sendPostRequest(ServiceUrls.LOGIN_URL, AddUserDetails);

           // Log.v(TAG, String.format("doInBackground :: loginResult= %s", loginResult2));
            return loginResult2;
        }


    }
    private void DepositeDetails(String jsonResponse) {

       // Log.v(TAG, String.format("loginServerResponse :: response = %s", jsonResponse));

        JSONObject jsonObjLoginResponse = null;
        try {
            jsonObjLoginResponse = new JSONObject(jsonResponse);
            String success = jsonObjLoginResponse.getString(mContext.getResources().getString(R.string.success));

            if (success.equalsIgnoreCase(mContext.getResources().getString(R.string.two)))  {
                Toast.makeText(mContext,"Money Deposited Sucessfully",Toast.LENGTH_SHORT).show();
                deposited_amount.getText().clear();
//                Intent intent =new Intent(mContext,Search_userActivity.class);
//                mContext.startActivity(intent);


            }
            else if (success.equalsIgnoreCase(mContext.getResources().getString(R.string.zero)))
            {
                Toast.makeText(mContext,"Failed to add",Toast.LENGTH_SHORT).show();
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void  witdrawclass() {
        new Search_userAdapter.withdrawTrackClass().execute("5");
    }
    class withdrawTrackClass extends AsyncTask<String, Void, String> {

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
            //Toast.makeText(CandiateListView.this, response, Toast.LENGTH_LONG).show();
            //  Toast.makeText(NewEnquiryActivity.this, response, Toast.LENGTH_LONG).show();
            WithdrawDetails(response);

        }

        @Override
        protected String doInBackground(String... params) {
            Log.v(TAG, String.format("doInBackground ::  params= %s", params));
            HashMap<String, String> AddUserDetails = new HashMap<String, String>();
            AddUserDetails.put("user_id",UserID);
            Log.v("user_id********", UserID);
            AddUserDetails.put("withdraw_amount",withdraw_amount.getText().toString());
            Log.v("withdraw_amount", withdraw_amount.getText().toString());
            String Saving_scheme_id = SharedPrefereneceUtil.getSavingSchemeId((Activity) mContext);
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
            String success = jsonObjLoginResponse.getString(mContext.getResources().getString(R.string.success));

            if (success.equalsIgnoreCase(mContext.getResources().getString(R.string.two)))  {


                Toast.makeText(mContext,"Money Withdraw Sucessfully",Toast.LENGTH_SHORT).show();
                withdraw_amount.getText().clear();


            }


            else if (success.equalsIgnoreCase(mContext.getResources().getString(R.string.zero)))
            {
                Toast.makeText(mContext,"Failed to add",Toast.LENGTH_SHORT).show();
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void filter(String charText) {
        charText = charText.toLowerCase(Locale.getDefault());
        search_list.clear();
        if (charText.length() == 0) {
            search_list.addAll(arraylist);
        } else {
            for (search_user_list wp : arraylist) {
                if (wp.getName().toLowerCase(Locale.getDefault())
                        .contains(charText)|| wp.getUserId().toLowerCase(Locale.getDefault()).contains(charText)) {
                    search_list.add(wp);
                }
            }
        }
        notifyDataSetChanged();
    }

}
