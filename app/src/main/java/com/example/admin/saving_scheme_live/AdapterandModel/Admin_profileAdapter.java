package com.example.admin.saving_scheme_live.AdapterandModel;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.admin.saving_scheme_live.Activity.Month_collectionActivity;
import com.example.admin.saving_scheme_live.Activity.UserProfileActivity;
import com.example.admin.saving_scheme_live.R;

import java.util.ArrayList;
import java.util.List;

import static com.example.admin.saving_scheme_live.Activity.MainActivity.TAG;

/**
 * Created by admin on 2018-9-4.
 */

public class Admin_profileAdapter extends RecyclerView.Adapter<Admin_profileAdapter.MyViewHolder> {
    private Context mContext;
    private List<admin_profile_list> admin_list;
    private ArrayList<admin_profile_list> arraylist;
    String userid;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView amount,balance,date,month,year,withdraw,deposite;
        LinearLayout lyt_parent;
        ImageView image;
        // public final MaterialLetterIcon mIcon;


        public MyViewHolder(View view) {
            super(view);
            month = (TextView) view.findViewById(R.id.month);
            year = (TextView) view.findViewById(R.id.year);
            amount = (TextView) view.findViewById(R.id.amount);
            withdraw=(TextView) view.findViewById(R.id.amount1);
            deposite=(TextView) view.findViewById(R.id.amount2);
            lyt_parent=(LinearLayout)view.findViewById(R.id.lyt_parent);
        }
    }

    public Admin_profileAdapter(Context mContext, ArrayList<admin_profile_list> albumList) {
        this.mContext = mContext;
        this.admin_list = albumList;
        this.arraylist = new ArrayList<admin_profile_list>();
        this.arraylist.addAll(albumList);
    }

    @Override
    public int getItemCount() {

        return admin_list.size();
    }
    public admin_profile_list getItem(int position) {

        return admin_list.get(position);
    }
    @Override
    public Admin_profileAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.activity_admin_profile_list, parent, false);

        return new Admin_profileAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final Admin_profileAdapter.MyViewHolder holder, int position) {
        admin_profile_list enq = admin_list.get(position);

        final String Month=enq.getMonth();
        //Log.v(TAG, String.format("userId*****:",Month));
        final String Year=enq.getYear();
       // Log.v(TAG, String.format("userId*****:",Year));

        holder.month.setText(enq.getMonth());
        holder.year.setText(enq.getYear());
        holder.amount.setText(enq.getBalance());
        holder.deposite.setText(enq.getBalance1());
        holder.withdraw.setText(enq.getBalance2());


        holder.lyt_parent.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {


            Intent intent = new Intent(mContext, Month_collectionActivity.class);
            intent.putExtra("Month",Month);
            intent.putExtra("Year",Year);
            //Log.v(TAG, String.format("userId:",userid));
            mContext.startActivity(intent);
        }
    });

    }
}
