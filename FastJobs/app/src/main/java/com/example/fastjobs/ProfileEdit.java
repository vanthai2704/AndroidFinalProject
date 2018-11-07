package com.example.fastjobs;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.fastjobs.Entity.User;
import com.example.fastjobs.firebase.CallbackSupport;
import com.example.fastjobs.firebase.LoginSupport;
import com.example.fastjobs.firebase.UserSupport;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileEdit extends Fragment {


    public ProfileEdit() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile_edit, container, false);
        getActivity().setTitle("Edit Profile");
        LoginSupport loginSupport = new LoginSupport();
        final UserSupport userSupport = new UserSupport();
        final TextView txtName = view.findViewById(R.id.txtName);
        final TextView txtDOB = view.findViewById(R.id.txtDOB);
        final TextView txtPhone = view.findViewById(R.id.txtPhone);
        final Button btnSave= view.findViewById(R.id.btnSave);

        userSupport.get(loginSupport.getCurrentUserEmail(), new CallbackSupport<User>() {
            @Override
            public void onCallback(final User user, String key, List<User> users) {
                user.setFullname(txtName.getText().toString());
                user.setPhone(txtPhone.getText().toString());
                SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
                try{
                    Date DOB = format.parse(txtDOB.getText().toString());
                    user.setDob(DOB);
                }catch(ParseException e ){
                    e.printStackTrace();
                }

                btnSave.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        userSupport.update(user);
                        Toast.makeText( getActivity() , "Chỉnh Sửa Thông Tin Thành Công." ,Toast.LENGTH_LONG).show();
                    }
                });

            }
        });

//        UserProfile profile= new UserProfile();
//        getFragmentManager()
//                .beginTransaction()
//                .replace(R.id.contentLayout , profile)
//                .commit();

        return view;
    }



}
