package com.example.fastjobs.firebase;

import android.support.annotation.NonNull;

import com.example.fastjobs.entity.Category;
import com.example.fastjobs.entity.Post;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CategorySupport extends BaseSupport {
    private DatabaseReference dbCategory;
    public CategorySupport(){
        dbCategory = db.child("category");
    }
    public void insert(Category category){
        String key = dbCategory.push().getKey();
        category.setCategory_id(key);
        dbCategory.child(key).setValue(category).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                e.printStackTrace();
            }
        });
    }
    public void getAll(final CallbackSupport callbackSupport){
        dbCategory.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                List<Category> categories = new ArrayList<>();
                for (DataSnapshot item : dataSnapshot.getChildren())
                {
                    categories.add(item.getValue(Category.class));
                }
                callbackSupport.onCallback(null, null, categories);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
    public void delete(Category category){
        dbCategory.child(category.getCategory_id()).removeValue();
    }
    public void update(Category category){
        Map<String, Object> childUpdates = new HashMap<>();
        childUpdates.put(category.getCategory_id(), category.toMap());
        dbCategory.updateChildren(childUpdates);
    }
    public void find(final String category_id, final CallbackSupport callbackSupport){
        dbCategory.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot item : dataSnapshot.getChildren())
                {
                    if(item.getKey().equalsIgnoreCase(category_id)){
                        Category category = item.getValue(Category.class);
                        category.setCategory_id(item.getKey());
                        callbackSupport.onCallback(category, item.getKey(), null);
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
