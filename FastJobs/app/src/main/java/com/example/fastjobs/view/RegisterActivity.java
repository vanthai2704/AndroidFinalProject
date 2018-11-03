package com.example.fastjobs.view;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


import com.example.fastjobs.MainPage;
import com.example.fastjobs.R;
import com.example.fastjobs.entity.User;
import com.example.fastjobs.firebase.CallbackSupport;
import com.example.fastjobs.firebase.LoginSupport;

import java.util.List;
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

    public void EmailClick(View v){
        email.setError(null);
    }

    public void PasswordClick(View v){
        pass.setError(null);
    }

    public void RepasswordClick(View v){
        repass.setError(null);
    }

    public void Register(View v){
        String getemail = email.getText().toString();
        String getpass = pass.getText().toString();
        String getrepass = repass.getText().toString();

        Matcher matcher= Pattern.compile(LoginActivity.validemail).matcher(getemail);
        if(getemail.equals("")){
            email.setError("Email is required");
            email.requestFocus();
            return;
        }
        if(!matcher.matches()){
            email.setError("Invalid Email");
            email.requestFocus();
            return;
        }
        if(getpass.equals("")){
            pass.setError("Password is required");
            pass.requestFocus();
            return;
        }
        if(!getpass.equals(getrepass)){
            repass.setError("Repeat Password and Password not be the same");
            repass.requestFocus();
            return;
        }


        if (matcher.matches()){
            final ProgressDialog dialog = ProgressDialog.show(RegisterActivity.this, "",
                    "Loading. Please wait...", true);
            LoginSupport loginSupport = new LoginSupport();
            loginSupport.signUp(getemail, getpass, new CallbackSupport<User>() {
                @Override
                public void onCallback(User user, String key, List<User> users) {
                    Intent intent = new Intent(RegisterActivity.super.getBaseContext(), LoginActivity.class);
                    startActivity(intent);
                    finish();
                    dialog.dismiss();
                }
            });
        }
    }
}
