package com.example.fastjobs.firebase;

import android.support.annotation.NonNull;


import com.example.fastjobs.Entity.Commune;
import com.example.fastjobs.Entity.District;
import com.example.fastjobs.Entity.Province;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class CommuneSupport extends BaseSupport{
    private static CommuneSupport instance = null;
    public static CommuneSupport getInstance(){
        if(instance == null){
            instance = new CommuneSupport();
        }
        return instance;
    }
    private DatabaseReference dbCommune;
    public CommuneSupport(){
        dbCommune = db.child("commune");
    }

    private List<Commune> allCommunes;
    public void getAll(final String district_id, final CallbackSupport callbackSupport){
        if(allCommunes == null){
            dbCommune.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    List<Commune> communes = new ArrayList<>();
                    for (DataSnapshot item : dataSnapshot.getChildren())
                    {
                        if(item.getValue(Commune.class).getDistrict_id().equalsIgnoreCase(district_id)){
                            communes.add(item.getValue(Commune.class));
                        }
                    }
                    allCommunes = communes;
                    callbackSupport.onCallback(null, null, communes);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }else {
            callbackSupport.onCallback(null, null, allCommunes);
        }

    }

    public void get(final String commune_id, final CallbackSupport callbackSupport){
        dbCommune.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot item : dataSnapshot.getChildren())
                {
                    if(item.getValue(Commune.class).getCommune_id().equalsIgnoreCase(commune_id)){
                        callbackSupport.onCallback(item.getValue(Commune.class), commune_id, null);
                        break;
                    }

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void getFullLocation(final String commune_id, final CallbackSupport callbackSupport){
        get(commune_id, new CallbackSupport<Commune>() {
            @Override
            public void onCallback(final Commune commune, String key, List<Commune> communes) {
                DistrictSupport.getInstance().get(commune_id, new CallbackSupport<District>() {
                    @Override
                    public void onCallback(final District district, String key, List<District> districts) {
                        ProvinceSupport.getInstance().get(district.getProvince_id(), new CallbackSupport<Province>() {
                            @Override
                            public void onCallback(Province province, String key, List<Province> provinces) {
                                String fullLocation ="";
                                fullLocation+=commune.getName()+","+district.getName()+","+province.getName();
                            }
                        });
                    }
                });
            }
        });
    }
}
