package com.example.fastjobs.View;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.fastjobs.MainActivity;
import com.example.fastjobs.R;
import com.example.fastjobs.firebase.CallbackSupport;
import com.example.fastjobs.firebase.LoginSupport;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.tooltip.Tooltip;

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
        Toast.makeText(getApplicationContext(),"Register",Toast.LENGTH_LONG).show();
        Intent intent = new Intent(this, RegisterActivity.class);
        startActivity(intent);

    }

    private void showTooltip (String tooltipmail,String tooltippass) throws InterruptedException {

        Matcher matcher= Pattern.compile(validemail).matcher(tooltipmail);


        if(tooltipmail.equals("") || !matcher.matches()){
            Tooltip tooltip = new Tooltip.Builder(username).setText("InValid Email")
                    .setTextColor(Color.RED)
                    .setGravity(Gravity.TOP)
                    .setCornerRadius(8f)
                    .setDismissOnClick(true)
                    .show();
            //Thread.sleep(3000);
            //tooltip.wait(2000);
            //tooltip.dismiss();
            return;
        }
        if(tooltippass.equals("")){
            Tooltip tooltip = new Tooltip.Builder(password).setText("InValid Password")
                    .setTextColor(Color.RED)
                    .setGravity(Gravity.TOP)
                    .setCornerRadius(8f)
                    .setDismissOnClick(true)
                    .show();
            //Thread.sleep(3000);
            //tooltip.dismiss();
            return;
        }


        if (matcher.matches()){
            final ProgressDialog dialog = ProgressDialog.show(LoginActivity.this, "",
                    "Loading. Please wait...", true);
            LoginSupport loginSupport = new LoginSupport();
            loginSupport.login(tooltipmail, tooltippass, new CallbackSupport<Boolean>() {
                @Override
                public void onCallback(Boolean aBoolean, String key, List<Boolean> booleans) {
                    if(aBoolean){
                        Intent intent = new Intent(LoginActivity.super.getBaseContext(), MainActivity.class);
                        startActivity(intent);
                    }
                    else{
                        Toast.makeText(getApplicationContext(),"Email or Password is InValid",Toast.LENGTH_LONG).show();
                    }
                    dialog.dismiss();
                }
            });

        }

    }
}
