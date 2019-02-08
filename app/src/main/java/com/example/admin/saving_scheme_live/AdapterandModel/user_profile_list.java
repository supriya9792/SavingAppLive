package com.example.admin.saving_scheme_live.AdapterandModel;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class user_profile_list  {
String date;
String month;
String year;
String amount;
String balance;
String withdraw_deposite;
String username;
String total_bal;
String phone;
String city;


    public user_profile_list() {
    }

    public user_profile_list(String date, String amount,String month,String year,String balance,String withdraw_deposite,String username ,String total_bal,String phone,String city) {
        this.date = date;
        this.month=month;
        this.year=year;
        this.amount=amount;
        this.balance=balance;
        this.withdraw_deposite=withdraw_deposite;
        this.username=username;
        this.total_bal=total_bal;
        this.phone=phone;
        this.city=city;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
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

    public String getWithdraw_deposite() {
        return withdraw_deposite;
    }

    public void setWithdraw_deposite(String withdraw_deposite) {
        this.withdraw_deposite = withdraw_deposite;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getTotal_bal() {
        return total_bal;
    }

    public void setTotal_bal(String total_bal) {
        this.total_bal = total_bal;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }
}
