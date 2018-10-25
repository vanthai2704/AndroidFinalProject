package com.example.fastjobs.firebase;

import android.support.annotation.NonNull;
import com.example.fastjobs.entity.User;
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
    private ArrayList<User> users;
    private DatabaseReference dbUser;
    public UserSupport(){
        dbUser = db.child("user");
        users= new ArrayList<>();
    }
    public void insert(User user){
        String userId = dbUser.push().getKey();
        dbUser.child(userId).setValue(user).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                e.printStackTrace();
            }
        });
    }

    // Validate page > 0
    public void getAll(final int page, final int pageSize, final UserCallback userCallback){
        dbUser.addValueEventListener(new ValueEventListener() {
            int index = 1;
            List<User> users = new ArrayList<>();
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot item : dataSnapshot.getChildren())
                {
                    if(index>(page-1)*pageSize && index<= page*pageSize){
                        users.add(item.getValue(User.class));
                    }
                    index++;
                }
                userCallback.onCallback(null, null, users);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void delete(String email){
        find(email, new UserCallback() {
            @Override
            public void onCallback(User user, String key, List<User> users) {
                dbUser.child(key).removeValue();
            }
        });
    }

    public void update(final User userUpdate){
        find(userUpdate.getEmail(), new UserCallback() {
            @Override
            public void onCallback(User user, String key, List<User> users) {
                Map<String, Object> childUpdates = new HashMap<>();
                childUpdates.put(key, userUpdate.toMap());
                dbUser.updateChildren(childUpdates);
            }
        });
    }

    public void find(final String email, final UserCallback userCallback){
        dbUser.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot item : dataSnapshot.getChildren())
                {
                    User user = item.getValue(User.class);
                    if(user.getEmail().equalsIgnoreCase(email)){
                        userCallback.onCallback(user, item.getKey(), null);
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
