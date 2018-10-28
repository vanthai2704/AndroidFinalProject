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

public class RegisterActivity extends AppCompatActivity {

    private TextView email, pass, repass;
    private Button register;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        email = findViewById(R.id.editText_enter_username);
        pass = findViewById(R.id.editText_enter_password);
        repass = findViewById(R.id.editText_enter_rpassword);
        register = findViewById(R.id.button_register);
    }

    public void Register(View v){
        String getemail = email.getText().toString();
        String getpass = pass.getText().toString();
        String getrepass = repass.getText().toString();

        Matcher matcher= Pattern.compile(LoginActivity.validemail).matcher(getemail);
        if(getemail.equals("") || !matcher.matches()){
            Toast.makeText(getApplicationContext(),"InValid Email",Toast.LENGTH_LONG).show();
            return;
        }
        if(getpass.equals("")){
            Toast.makeText(getApplicationContext(),"Require Password",Toast.LENGTH_LONG).show();
            return;
        }
        if(!getpass.equals(getrepass)){
            Toast.makeText(getApplicationContext(),"Repeat Password and Password not be the same",Toast.LENGTH_LONG).show();
            return;
        }


        if (matcher.matches()){
            LoginSupport loginSupport = new LoginSupport();
            loginSupport.signUp(getemail,getpass);
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            finish();
            return;
        }
    }
}
