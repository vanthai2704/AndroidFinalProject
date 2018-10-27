package com.example.fastjobs.firebase;

import android.support.annotation.NonNull;

import com.example.fastjobs.entity.Province;
import com.example.fastjobs.entity.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ProvinceSupport extends BaseSupport{
    private DatabaseReference dbProvince;
    public ProvinceSupport(){
        dbProvince = db.child("province");
    }

    public void getAll(final CallbackSupport callbackSupport){
        dbProvince.addValueEventListener(new ValueEventListener() {
            List<Province> provinces = new ArrayList<>();
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot item : dataSnapshot.getChildren())
                {
                    provinces.add(item.getValue(Province.class));
                }
                callbackSupport.onCallback(null, null, provinces);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void get(final String province_id, final CallbackSupport callbackSupport){
        dbProvince.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot item : dataSnapshot.getChildren())
                {
                    if(item.getValue(Province.class).getProvince_id().equalsIgnoreCase(province_id)){
                        callbackSupport.onCallback(item.getValue(Province.class), province_id, null);
                        break;
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

}
