package com.example.admin.saving_scheme_live.AdapterandModel;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.admin.saving_scheme_live.R;

public class recent_transaction_list {
    String id;
    String name;
    String city;
    String amount;
    String type;
    String status;

    public recent_transaction_list() {
    }

    public recent_transaction_list(String id, String name, String city, String amount, String type, String status) {
        this.id = id;
        this.name = name;
        this.city = city;
        this.amount = amount;
        this.type = type;
        this.status=status;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
