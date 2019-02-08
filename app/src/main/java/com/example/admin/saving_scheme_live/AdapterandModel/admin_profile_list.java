package com.example.admin.saving_scheme_live.AdapterandModel;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.admin.saving_scheme_live.R;

public class admin_profile_list{

   String month;
   String year;
   String balance;
   String balance1;
   String balance2;

    public admin_profile_list() {

    }

    public admin_profile_list(String month, String year, String balance, String balance1,String balance2) {

        this.month = month;
        this.year = year;
        this.balance = balance;
        this.balance1=balance1;
        this.balance2=balance2;
    }


    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getBalance() {
        return balance;
    }

    public void setBalance(String balance) {
        this.balance = balance;
    }

    public String getBalance1() {
        return balance1;
    }

    public void setBalance1(String balance1) {
        this.balance1 = balance1;
    }

    public String getBalance2() {
        return balance2;
    }

    public void setBalance2(String balance2) {
        this.balance2 = balance2;
    }
}
