package com.example.fastjobs.firebase;

import android.support.annotation.NonNull;

import com.example.fastjobs.Entity.Post;
import com.example.fastjobs.Entity.Revenue;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class RevenueSupport extends BaseSupport{
    private static RevenueSupport instance = null;
    public static RevenueSupport getInstance(){
        if(instance == null){
            instance = new RevenueSupport();
        }
        return instance;
    }

    private DatabaseReference dbRevenue;
    public RevenueSupport(){
        dbRevenue = db.child("revenue");
    }
    public void insert(Revenue revenue){
        final String keyRevenue = dbRevenue.push().getKey();
        dbRevenue.child(keyRevenue).setValue(revenue).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                e.printStackTrace();
            }
        });
    }

    public void getAll(final int page, final int pageSize, final CallbackSupport callbackSupport){
        dbRevenue.addValueEventListener(new ValueEventListener() {
            int index = 1;

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                List<Revenue> revenues = new ArrayList<>();
                for (DataSnapshot item : dataSnapshot.getChildren())
                {
                    if(index>(page-1)*pageSize && index<= page*pageSize){
                        revenues.add(item.getValue(Revenue.class));
                    }
                    index++;
                }
                Collections.reverse(revenues);
                callbackSupport.onCallback(null, null, revenues);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
