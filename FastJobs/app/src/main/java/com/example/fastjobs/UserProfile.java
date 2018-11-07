package com.example.fastjobs;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.fastjobs.Entity.User;
import com.example.fastjobs.firebase.CallbackSupport;
import com.example.fastjobs.firebase.LoginSupport;
import com.example.fastjobs.firebase.UserSupport;

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
        View view =inflater.inflate(R.layout.fragment_userprofile, container, false);
        getActivity().setTitle("Profile");

        LoginSupport loginSupport = new LoginSupport();
        UserSupport userSupport = new UserSupport();
        final TextView txtName = view.findViewById(R.id.txtName);
        final TextView txtPhone = view.findViewById(R.id.txtPhone);
        final TextView txtDOB = view.findViewById(R.id.txtDOB);
        final TextView txtEmail = view.findViewById(R.id.txtEmail);
        userSupport.get(loginSupport.getCurrentUserEmail(), new CallbackSupport<User>() {
            @Override
            public void onCallback(User user, String key, List<User> users) {
                txtName.setText(user.getFullname());
                txtPhone.setText(user.getPhone());
                txtDOB.setText(user.getDob().toString());
                txtEmail.setText(user.getEmail());
            }
        });
        // Inflate the layout for this fragment
        return view;
    }

}
