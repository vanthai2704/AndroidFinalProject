package com.example.fastjobs.View;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.fastjobs.MainPage;
import com.example.fastjobs.ManagementRevenue;
import com.example.fastjobs.R;
import com.example.fastjobs.firebase.CallbackSupport;
import com.example.fastjobs.firebase.LoginSupport;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LoginActivity extends AppCompatActivity {

    static final String validemail = "[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,256}" +"\\@" +"[a-zA-Z0-9]" +
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

    public void Login(View v) throws InterruptedException {


        String email = username.getText().toString();
        String pass = password.getText().toString();

        showTooltip(email,pass);

    }
    public void Register(View v){
        Intent intent = new Intent(this, RegisterActivity.class);
        startActivity(intent);

    }

    public void EmailClick(View v){
        username.setError(null);
    }

    public void PasswordClick(View v){
        password.setError(null);
    }

    private void showTooltip (String tooltipmail,String tooltippass) throws InterruptedException {

        Matcher matcher= Pattern.compile(validemail).matcher(tooltipmail);


        if(tooltipmail.equals("") ){
            username.setError("Email is required");
            username.requestFocus();
            return;
        }
        if(!matcher.matches()){
            username.setError("Invalid Email");
            username.requestFocus();
            return;
        }
        if(tooltippass.equals("")){
            password.setError("Password is required");
            password.requestFocus();
            return;
        }
        if(tooltipmail.equals("admin@gmail.com") && tooltippass.equals("123456")){
            Intent intent = new Intent(LoginActivity.super.getBaseContext(), ManagementRevenue.class);
            startActivity(intent);
            finish();
        }

        if (matcher.matches()){
            final ProgressDialog dialog = ProgressDialog.show(LoginActivity.this, "",
                    "Loading. Please wait...", true);
            LoginSupport loginSupport = LoginSupport.getInstance();
            loginSupport.login(tooltipmail, tooltippass, new CallbackSupport<Boolean>() {
                @Override
                public void onCallback(Boolean aBoolean, String key, List<Boolean> booleans) {
                    if(aBoolean){
                        Intent intent = new Intent(LoginActivity.super.getBaseContext(), MainPage.class);
                        startActivity(intent);
                        finish();
                    }
                    else{
                        Toast.makeText(getApplicationContext(),"Email or password is wrong!",Toast.LENGTH_LONG).show();
                    }
                    dialog.dismiss();
                }
            });

        }

    }
}
