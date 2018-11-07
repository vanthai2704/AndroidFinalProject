package com.example.fastjobs.firebase;

import android.content.Context;
import android.support.annotation.NonNull;


import com.example.fastjobs.Entity.Cart;
import com.example.fastjobs.Entity.Image;
import com.example.fastjobs.Entity.Post;
import com.example.fastjobs.Entity.User;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserSupport extends BaseSupport{
    public static String CART_ACTIVE ="ACTIVE";
    private static UserSupport instance = null;
    public static UserSupport getInstance(){
        if(instance == null){
            instance = new UserSupport();
        }
        return instance;
    }

    private DatabaseReference dbUser;
    public UserSupport(){
        dbUser = db.child("user");
    }
    public void insert(final User user, Context context){
        final List<Image> images = new ArrayList<>();
        images.addAll(user.getImages());
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
                        user.setImages(images);
                        update(user);
                    }
                });
            }
        }
    }

    // Validate page > 0
    public void getAll(final int page, final int pageSize, final CallbackSupport callbackSupport){
        dbUser.addValueEventListener(new ValueEventListener() {
            int index = 1;

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                List<User> users = new ArrayList<>();
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

    public void update(User user){
        Map<String, Object> childUpdates = new HashMap<>();
        childUpdates.put(user.getEmail().replaceAll("\\.","_"), user.toMap());
        dbUser.updateChildren(childUpdates);
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

    public void addToCart(final String post_id){
        get(LoginSupport.getInstance().getCurrentUserEmail().replaceAll("\\.", "_"), new CallbackSupport<User>() {
            @Override
            public void onCallback(User user, String key, List<User> users) {
                PostSupport postSupport = PostSupport.getInstance();
                List<Cart> carts = user.getCarts();
                if(carts == null){
                    carts = new ArrayList<>();
                }
                Cart cart = new Cart(post_id, CART_ACTIVE,new Date());
                carts.add(cart);
                user.setCarts(carts);
                update(user);
            }
        });
    }
    private List<Cart> cartsTmp;
    public void getCarts(final CallbackSupport callbackSupport){
        if(cartsTmp == null){
            get(LoginSupport.getInstance().getCurrentUserEmail().replaceAll("\\.", "_"), new CallbackSupport<User>() {
                @Override
                public void onCallback(User user, String key, List<User> users) {
                    cartsTmp = user.getCarts();
                    callbackSupport.onCallback(null, LoginSupport.getInstance().getCurrentUserEmail().replaceAll("\\.", "_"),
                            user.getCarts());
                }
            });
        }else {
            callbackSupport.onCallback(null, LoginSupport.getInstance().getCurrentUserEmail().replaceAll("\\.", "_"),
                    cartsTmp);
        }
    }
}
