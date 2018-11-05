package com.example.fastjobs.firebase;

import android.content.Context;
import android.support.annotation.NonNull;

import com.example.fastjobs.Entity.Image;
import com.example.fastjobs.Entity.User;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserSupport extends BaseSupport{
    private DatabaseReference dbUser;
    public UserSupport(){
        dbUser = db.child("user");
    }
    public void insert(final User user, Context context){
        List<Image> images = new ArrayList<>();
        images.addAll(user.getImages());
        user.setImages(null);
        dbUser.child(user.getEmail().replaceAll("\\.","_")).setValue(user).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                e.printStackTrace();
            }
        });
        if(images.size() > 0){
            ImageSupport imageSupport = new ImageSupport();
            for (final Image image : images){
                imageSupport.upload(image.getImage_uri(), context, new CallbackSupport() {
                    @Override
                    public void onCallback(Object o, String key, List list) {
                        image.setImage_id(key);
                        dbUser.child(user.getEmail().replaceAll("\\.","_")).child(key).setValue(image).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                e.printStackTrace();
                            }
                        });
                    }
                });
            }
        }
    }

    // Validate page > 0
    public void getAll(final int page, final int pageSize, final CallbackSupport callbackSupport){
        dbUser.addValueEventListener(new ValueEventListener() {
            int index = 1;
            List<User> users = new ArrayList<>();
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot item : dataSnapshot.getChildren())
                {
                    if(index>(page-1)*pageSize && index<= page*pageSize){
                        users.add(item.getValue(User.class));
                    }else {
                        break;
                    }
                    index++;
                }
                callbackSupport.onCallback(null, null, users);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void delete(String email){
        dbUser.child(email.replaceAll("\\.","_")).removeValue();
    }

    public void update(final User user, Context context){
        List<Image> images = new ArrayList<>();
        images.addAll(user.getImages());
        user.setImages(null);
        Map<String, Object> childUpdates = new HashMap<>();
        childUpdates.put(user.getEmail().replaceAll("\\.","_"), user.toMap());
        dbUser.updateChildren(childUpdates);
        if(images.size() > 0){
            ImageSupport imageSupport = new ImageSupport();
            for (final Image image : images){
                imageSupport.upload(image.getImage_uri(), context, new CallbackSupport() {
                    @Override
                    public void onCallback(Object o, String key, List list) {
                        image.setImage_id(key);
                        dbUser.child(user.getEmail().replaceAll("\\.","_")).child("images").setValue(image).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                e.printStackTrace();
                            }
                        });
                    }
                });
            }
        }
    }

    public void get(final String email, final CallbackSupport callbackSupport){
        dbUser.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot item : dataSnapshot.getChildren())
                {
                    if(item.getKey().equalsIgnoreCase(email.replaceAll("\\.","_"))){
                        callbackSupport.onCallback(item.getValue(User.class), item.getKey(), null);
                        break;
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void search(final String email, final CallbackSupport callbackSupport){
        dbUser.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot item : dataSnapshot.getChildren())
                {
                    User user = item.getValue(User.class);
                    if(user.getEmail().equalsIgnoreCase(email)){
                        callbackSupport.onCallback(user, item.getKey(), null);
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
