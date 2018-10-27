package com.example.fastjobs.firebase;

import android.support.annotation.NonNull;

import com.example.fastjobs.entity.District;
import com.example.fastjobs.entity.Province;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class DistrictSupport extends BaseSupport{
    private DatabaseReference dbDistrict;
    public DistrictSupport(){
        dbDistrict = db.child("district");
    }

    public void getAll(final String province_id, final CallbackSupport callbackSupport){
        dbDistrict.addValueEventListener(new ValueEventListener() {
            List<District> districts = new ArrayList<>();
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot item : dataSnapshot.getChildren())
                {
                    if(item.getValue(District.class).getProvince_id().equalsIgnoreCase(province_id)){
                        districts.add(item.getValue(District.class));
                    }
                }
                callbackSupport.onCallback(null, null, districts);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void get(final String district_id, final CallbackSupport callbackSupport){
        dbDistrict.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot item : dataSnapshot.getChildren())
                {
                    if(item.getValue(District.class).getDistrict_id().equalsIgnoreCase(district_id)){
                        callbackSupport.onCallback(item.getValue(District.class), district_id, null);
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
