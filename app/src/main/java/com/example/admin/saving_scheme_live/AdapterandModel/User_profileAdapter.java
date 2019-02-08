package com.example.admin.saving_scheme_live.AdapterandModel;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.admin.saving_scheme_live.Activity.UserProfileActivity;
import com.example.admin.saving_scheme_live.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import static android.content.ContentValues.TAG;

/**
 * Created by admin on 2018-8-23.
 */

public class User_profileAdapter extends RecyclerView.Adapter<User_profileAdapter.MyViewHolder> {
    private Context mContext;
    private List<user_profile_list> profile_list;
    private ArrayList<user_profile_list> arraylist;
    String userid;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView amount,balance,date,month,year,withdraw_deposite;
        LinearLayout lyt_parent;
        ImageView image;
        // public final MaterialLetterIcon mIcon;


        public MyViewHolder(View view) {
            super(view);
            date = (TextView) view.findViewById(R.id.date);
            month = (TextView) view.findViewById(R.id.month);
            year = (TextView) view.findViewById(R.id.year);
            amount = (TextView) view.findViewById(R.id.amount);
            balance=(TextView) view.findViewById(R.id.balance);
            withdraw_deposite=(TextView) view.findViewById(R.id.withdraw_deposite);
        }
    }

    public User_profileAdapter(Context mContext, ArrayList<user_profile_list> albumList) {
        this.mContext = mContext;
        this.profile_list = albumList;
        this.arraylist = new ArrayList<user_profile_list>();
        this.arraylist.addAll(albumList);
    }

    @Override
    public int getItemCount() {

        return profile_list.size();
    }
    public user_profile_list getItem(int position) {

        return profile_list.get(position);
    }
    @Override
    public User_profileAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.activity_user_profile_list, parent, false);

        return new User_profileAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final User_profileAdapter.MyViewHolder holder, int position) {
        user_profile_list enq = profile_list.get(position);

//        final String userid=enq.getUserId();
//        Log.v(TAG, String.format("userId*****:",userid));
//        final String username=enq.getName();
//        Log.v(TAG, String.format("userId*****:",userid));
        holder.date.setText(enq.getDate());
        holder.month.setText(enq.getMonth());
        holder.year.setText(enq.getYear());
        holder.amount.setText(enq.getAmount());
        holder.balance.setText(enq.getBalance());
        holder.withdraw_deposite.setText(enq.getWithdraw_deposite());


        //  holder.image.setI(enq.getImage());

        // loading album cover using Glide library
        //Glide.with(mContext).load(enq.getThumbnail()).into(holder.thumbnail);


    }

}
