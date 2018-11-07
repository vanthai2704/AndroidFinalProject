package com.example.fastjobs;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;

import java.util.ArrayList;

public class PaymentActivity extends AppCompatActivity {

    Spinner spinner;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);
        spinner = findViewById(R.id.drop_down_list_bank);

        //ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,R.id.drop_down_list_bank,);
        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.add("Vietcombank");
        arrayList.add("TPBank");
        arrayList.add("");
        //spinner.setAdapter();
    }



}
