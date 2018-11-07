package com.example.fastjobs;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import com.example.fastjobs.Adapter.RevenueAdapter;
import com.example.fastjobs.Entity.Revenue;
import com.example.fastjobs.View.LoginActivity;
import com.example.fastjobs.firebase.CallbackSupport;
import com.example.fastjobs.firebase.LoginSupport;
import com.example.fastjobs.firebase.RevenueSupport;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ManagementRevenue extends AppCompatActivity {

    ListView listView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_management_revenue);
        listView = findViewById(R.id.listRevenue);
        this.setTitle("Management Revenue");
        final ManagementRevenue managementRevenue = this;
        RevenueSupport revenueSupport = RevenueSupport.getInstance();
        revenueSupport.getAll(1, 100, new CallbackSupport<Revenue>() {
            @Override
            public void onCallback(Revenue revenue, String key, List<Revenue> revenues) {
                RevenueAdapter revenueAdapter = new RevenueAdapter(managementRevenue,revenues);
                listView.setAdapter(revenueAdapter);
            }
        });



    }

    public void LogOut(View view){
        LoginSupport loginSupport = new LoginSupport();
        loginSupport.signOut();
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }



}
