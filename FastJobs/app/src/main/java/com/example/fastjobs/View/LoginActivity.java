package com.example.fastjobs.View;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.fastjobs.MainActivity;
import com.example.fastjobs.R;
import com.example.fastjobs.firebase.LoginSupport;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LoginActivity extends AppCompatActivity {

    String validemail = "[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,256}" +"\\@" +"[a-zA-Z0-9]" +
            "[a-zA-Z0-9\\-]{0,64}" +"(" +"\\." +"[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25}" +")+";
    private TextView username,password;
    private Button login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        login = findViewById(R.id.login);
    }

    public void Login(View v){

        String email = username.getText().toString();
        String pass = password.getText().toString();

        Matcher matcher= Pattern.compile(validemail).matcher(email);

        if(email.equals("") || !matcher.matches()){
            Toast.makeText(getApplicationContext(),"InValid Email",Toast.LENGTH_LONG).show();
            return;
        }
        if(pass.equals("")){
            Toast.makeText(getApplicationContext(),"InValid Password",Toast.LENGTH_LONG).show();
            return;
        }


        if (matcher.matches()){
            LoginSupport loginSupport = new LoginSupport();
            loginSupport.login(email,pass);
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            finish();
            return;
        }

    }
}
