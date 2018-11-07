package com.example.fastjobs;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;

public class BankInfomationActivity extends AppCompatActivity {

    Spinner spinner;
    TextView txtSelectedBank;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bankinfomation);
        spinner = findViewById(R.id.drop_down_list_bank);
        txtSelectedBank = findViewById(R.id.txtSelectedBank);

        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.add("Vietcombank");
        arrayList.add("TPBank");
        arrayList.add("Agribank");
        arrayList.add("VPBank");
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,arrayList);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch(position){
                    case 0: txtSelectedBank.setText("Chủ Tài Khoản: Nguyễn Quang Đạt" +"\n"
                            +"Số Tài Khoản: 0295603542"+"\n"
                            + "Ngân Hàng Vietcombank Chi Nhánh Hà Nội"+"\n"
                            +"Nội dung Chuyển Khoản: \"Tên Đăng Nhập\"");
                            break;
                    case 1: txtSelectedBank.setText("Chủ Tài Khoản: Nguyễn Hồng Nhật"+"\n"
                            +"Số Tài Khoản: 174511774"+"\n"
                            + "Ngân Hàng TPBank Chi Nhánh Hà Nội"+"\n"
                            +"Nội dung Chuyển Khoản: \"Tên Đăng Nhập\"");
                        break;
                    case 2: txtSelectedBank.setText("Chủ Tài Khoản: Nguyễn Văn Thái"+"\n"
                            +"Số Tài Khoản: 0994123321"+"\n"
                            + "Ngân Hàng Agribank Chi Nhánh Hà Nội"+"\n"
                            +"Nội dung Chuyển Khoản: \"Tên Đăng Nhập\"");
                        break;
                    case 3: txtSelectedBank.setText("Chủ Tài Khoản: Đới Quốc Doanh"+"\n"
                            +"Số Tài Khoản: 5145698669"+"\n"
                            + "Ngân Hàng VBbank Chi Nhánh Hà Nội"+"\n"
                            +"Nội dung Chuyển Khoản: \"Tên Đăng Nhập\"");
                        break;
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }
    public void BackToMainPage(View view){
        Intent intent = new Intent(getApplicationContext(),MainPage.class);
        startActivity(intent);
    }


}
