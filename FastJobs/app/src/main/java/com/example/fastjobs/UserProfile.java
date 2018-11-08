package com.example.fastjobs;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.fastjobs.Entity.User;
import com.example.fastjobs.firebase.CallbackSupport;
import com.example.fastjobs.firebase.LoginSupport;
import com.example.fastjobs.firebase.UserSupport;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class UserProfile extends Fragment {


    public UserProfile() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view =inflater.inflate(R.layout.fragment_userprofile, container, false);
        getActivity().setTitle("Profile");

        LoginSupport loginSupport = new LoginSupport();
        UserSupport userSupport = new UserSupport();
        final TextView txtName = view.findViewById(R.id.txtName);
        final TextView txtCash = view.findViewById(R.id.txtCash);
        final TextView txtPhone = view.findViewById(R.id.txtPhone);
        final TextView txtDOB = view.findViewById(R.id.txtDOB);
        final TextView txtEmail = view.findViewById(R.id.txtEmail);

        userSupport.get(loginSupport.getCurrentUserEmail(), new CallbackSupport<User>() {
            @Override
            public void onCallback(User user, String key, List<User> users) {
                txtEmail.setText(user.getEmail());
                txtCash.setText("Your Cash : " + user.getCash());
                txtName.setText(user.getFullname());
                txtPhone.setText(user.getPhone());
                if(user.getDob() != null){
                    SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
                    String date = format.format(user.getDob());
                        txtDOB.setText(date);
                    }else {
                        txtDOB.setText("DOB");
                }


            }
        });
        // Inflate the layout for this fragment
        Button btnEdit = view.findViewById(R.id.btnEdit);
        Button btnDeposit = view.findViewById(R.id.btnDeposit);
        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editClick();
            }
        });
        btnDeposit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                depositClick();
            }
        });
        return view;
    }

    public void editClick(){
        //Chuyen sang Edit View
        ProfileEdit profileEdit = new ProfileEdit();
        getFragmentManager()
                .beginTransaction()
                .replace(R.id.contentLayout , profileEdit)
                .commit();
    }

    public void depositClick(){
        //Chuyen sang Edit View
        Intent intent = new Intent(getActivity().getApplicationContext(),BankInfomationActivity.class);
        getActivity().startActivity(intent);

    }

}
