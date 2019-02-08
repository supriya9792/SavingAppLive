package com.example.admin.saving_scheme_live.AdapterandModel;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.admin.saving_scheme_live.R;

public class search_user_list {


//    public static final String TABLE_NAME = "sv_user";
//
//    public static final String COLUMN_ID = "sv_user_id";
//    public static final String COLUMN_NOTE = "saving_scheme_ref_id";
//    public static final String COLUMN_TIMESTAMP = "timestamp";
//
//    private int id;
//    private String note;
//    private String timestamp;
//
//
//    // Create table SQL query
//    public static final String CREATE_TABLE =
//            "CREATE TABLE " + TABLE_NAME + "("
//                    + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
//                    + COLUMN_NOTE + " TEXT,"
//                    + COLUMN_TIMESTAMP + " DATETIME DEFAULT CURRENT_TIMESTAMP"
//                    + ")";
    String name;
    String add;
    String mob;
    String userId;
    String deposte_withdrawn;
    String balance;

    public search_user_list(){}
    public search_user_list( String mob, String add, String name,String userId,String deposte_withdrawn,String balance) {
        this.add = add;
        this.mob = mob;
        this.name = name;
        this.userId=userId;
        this.deposte_withdrawn=deposte_withdrawn;
        this.balance=balance;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAdd() {
        return add;
    }

    public void setAdd(String add) {
        this.add = add;
    }

    public String getMob() {
        return mob;
    }

    public void setMob(String mob) {
        this.mob = mob;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getDeposte_withdrawn() {
        return deposte_withdrawn;
    }

    public void setDeposte_withdrawn(String deposte_withdrawn) {
        this.deposte_withdrawn = deposte_withdrawn;
    }

    public String getBalance() {
        return balance;
    }

    public void setBalance(String balance) {
        this.balance = balance;
    }
}
