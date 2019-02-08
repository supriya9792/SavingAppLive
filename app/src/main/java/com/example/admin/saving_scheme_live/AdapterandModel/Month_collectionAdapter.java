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

public class Month_collectionAdapter extends RecyclerView.Adapter<Month_collectionAdapter.MyViewHolder> {

    private Context mContext;
    private List<month_collection_list> month_tran_list;
    private ArrayList<month_collection_list> arraylist;
    String userid;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView id,name,city,amount,type,status,date;
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
            date=(TextView) view.findViewById(R.id.date);

            //lyt_parent=view.findViewById(R.id.lyt_parent);
        }
    }
    public Month_collectionAdapter(Context mContext, ArrayList<month_collection_list> albumList) {
        this.mContext = mContext;
        this.month_tran_list = albumList;
        this.arraylist = new ArrayList<month_collection_list>();
        this.arraylist.addAll(albumList);
    }
    @Override
    public int getItemCount() {

        return month_tran_list.size();
    }
    public month_collection_list getItem(int position) {

        return month_tran_list.get(position);
    }
    @Override
    public Month_collectionAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.activity_month_collection_list, parent, false);

        return new Month_collectionAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final Month_collectionAdapter.MyViewHolder holder, int position) {
        month_collection_list enq = month_tran_list.get(position);


        holder.id.setText(enq.getId());
        holder.name.setText(enq.getName());
        holder.city.setText(enq.getCity());
        holder.amount.setText(enq.getAmount());
        holder.type.setText(enq.getType());
        holder.status.setText(enq.getStatus());
        holder.date.setText(enq.getDate());

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
