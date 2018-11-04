package com.example.fastjobs.firebase;

import android.content.Context;
import android.support.annotation.NonNull;

import com.example.fastjobs.entity.Category;
import com.example.fastjobs.entity.Image;
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
    public void insert(Category category, Context context){
        List<Image> images = new ArrayList<>();
        images.addAll(category.getImages());
        category.setImages(null);
        final String keyCate = dbCategory.push().getKey();
        category.setCategory_id(keyCate);
        dbCategory.child(keyCate).setValue(category).addOnFailureListener(new OnFailureListener() {
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
                        dbCategory.child(keyCate).child("images").setValue(image).addOnFailureListener(new OnFailureListener() {
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
    public void update(final Category category, Context context){
        List<Image> images = new ArrayList<>();
        images.addAll(category.getImages());
        category.setImages(null);
        Map<String, Object> childUpdates = new HashMap<>();
        childUpdates.put(category.getCategory_id(), category.toMap());
        dbCategory.updateChildren(childUpdates);
        if(images.size() > 0){
            ImageSupport imageSupport = new ImageSupport();
            for (final Image image : images){
                imageSupport.upload(image.getImage_uri(), context, new CallbackSupport() {
                    @Override
                    public void onCallback(Object o, String key, List list) {
                        image.setImage_id(key);
                        dbCategory.child(category.getCategory_id()).child("images").setValue(image).addOnFailureListener(new OnFailureListener() {
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
    public void get(final String category_id, final CallbackSupport callbackSupport){
        dbCategory.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot item : dataSnapshot.getChildren())
                {
                    if(item.getKey().equalsIgnoreCase(category_id)){
                        callbackSupport.onCallback(item.getValue(Category.class), category_id, null);
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
