package com.example.fastjobs.firebase;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class BaseSupport {
    protected FirebaseAuth auth;
    protected DatabaseReference db;
    public BaseSupport(){
        auth = FirebaseAuth.getInstance();
        db = FirebaseDatabase.getInstance().getReference();
    }
}
