package com.example.fastjobs;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.fastjobs.entity.User;
import com.example.fastjobs.firebase.LoginSupport;
import com.example.fastjobs.firebase.UserCallback;
import com.example.fastjobs.firebase.UserSupport;

import java.util.Date;
import java.util.List;

public class TestActivity extends AppCompatActivity {

    UserSupport userSupport;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        userSupport = new UserSupport();
        TextView textView = findViewById(R.id.editTextTest1);
        textView.setText("datnqse04777@fpt.edu.vn");
    }

    public void test(View view){
        LoginSupport loginSupport = new LoginSupport();
        EditText email = findViewById(R.id.editTextTest1);
        EditText password =findViewById(R.id.editTextTest2);
        loginSupport.login(email.getText().toString(),password.getText().toString());
        for(int i = 1; i < 12; i++){
            userSupport.insert(new User("Nguyen Quang Dat","datnqse04777@fpt.edu.vn", "A"+i, new Date()));
        }


    }

    public void insert(View view){
        userSupport.getAll(3, 3, new UserCallback() {
            @Override
            public void onCallback(User user, String key, List<User> users) {
                for(User u : users){
                    System.out.println(u.getPhone());
                }
            }
        });
    }
}
