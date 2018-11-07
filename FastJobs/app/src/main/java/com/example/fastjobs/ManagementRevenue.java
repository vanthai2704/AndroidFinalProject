package com.example.fastjobs;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import com.example.fastjobs.Adapter.RevenueAdapter;
import com.example.fastjobs.Entity.Revenue;

import java.util.ArrayList;
import java.util.List;

public class ManagementRevenue extends AppCompatActivity {

    ListView listView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_management_revenue);
        listView = findViewById(R.id.listRevenue);

        List<Revenue> revenues = new ArrayList<>();
        RevenueAdapter revenueAdapter = new RevenueAdapter(this,revenues);
        listView.setAdapter(revenueAdapter);
        
    }





}
