package com.example.admin.saving_scheme_live.AdapterandModel;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.admin.saving_scheme_live.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by admin on 2018-9-7.
 */

public class All_transactionAdapter extends RecyclerView.Adapter<All_transactionAdapter.MyViewHolder>{
    private Context mContext;
    private List<all_transaction_list> all_tran_list;
    private ArrayList<all_transaction_list> arraylist;
    String userid;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView id,name,city,amount,type,status;
        LinearLayout lyt_parent;
        ImageView image;
        // public final MaterialLetterIcon mIcon;


        public MyViewHolder(View view) {
            super(view);
            id = (TextView) view.findViewById(R.id.id);
            name = (TextView) view.findViewById(R.id.name);
            city=(TextView) view.findViewById(R.id.city);
            amount=(TextView) view.findViewById(R.id.amount);
            type=(TextView) view.findViewById(R.id.status);
            status=(TextView) view.findViewById(R.id.status_time);
            //lyt_parent=view.findViewById(R.id.lyt_parent);
        }
    }
    public All_transactionAdapter(Context mContext, ArrayList<all_transaction_list> albumList) {
        this.mContext = mContext;
        this.all_tran_list = albumList;
        this.arraylist = new ArrayList<all_transaction_list>();
        this.arraylist.addAll(albumList);
    }
    @Override
    public int getItemCount() {

        return all_tran_list.size();
    }
    public all_transaction_list getItem(int position) {

        return all_tran_list.get(position);
    }
    @Override
    public All_transactionAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.activity_all_transaction_list, parent, false);

        return new All_transactionAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final All_transactionAdapter.MyViewHolder holder, int position) {
        all_transaction_list enq = all_tran_list.get(position);


        holder.id.setText(enq.getId());
        holder.name.setText(enq.getName());
        holder.city.setText(enq.getCity());
        holder.amount.setText(enq.getAmount());
        holder.type.setText(enq.getType());
        holder.status.setText(enq.getStatus());

//        holder.lyt_parent.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//
//                Intent intent = new Intent(mContext, UserProfileActivity.class);
//                intent.putExtra("userId",userid);
//                intent.putExtra("username",username);
//                Log.v(TAG, String.format("userId:",userid));
//                mContext.startActivity(intent);
//            }
//        });
    }
}
