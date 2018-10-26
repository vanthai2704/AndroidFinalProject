package com.example.fastjobs.firebase;

import android.support.annotation.NonNull;

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

public class PostSupport extends BaseSupport{
    private DatabaseReference dbPost;
    public PostSupport(){
        dbPost = db.child("post");
    }

    public void insert(Post post){
        String key = dbPost.push().getKey();
        post.setPost_id(key);
        dbPost.child(key).setValue(post).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                e.printStackTrace();
            }
        });
    }
    public void getAll(final int page, final int pageSize, final CallbackSupport callbackSupport){
        dbPost.addValueEventListener(new ValueEventListener() {
            int index = 1;
            List<Post> posts = new ArrayList<>();
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot item : dataSnapshot.getChildren())
                {
                    if(index>(page-1)*pageSize && index<= page*pageSize){
                        posts.add(item.getValue(Post.class));
                    }
                    index++;
                }
                callbackSupport.onCallback(null, null, posts);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
    public void delete(Post post){
        dbPost.child(post.getPost_id()).removeValue();
    }
    public void update(Post post){
        Map<String, Object> childUpdates = new HashMap<>();
        childUpdates.put(post.getPost_id(), post.toMap());
        dbPost.updateChildren(childUpdates);
    }
    public void find(final String post_id, final CallbackSupport callbackSupport){
        dbPost.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot item : dataSnapshot.getChildren())
                {
                    if(item.getKey().equalsIgnoreCase(post_id)){
                        Post post = item.getValue(Post.class);
                        post.setPost_id(item.getKey());
                        callbackSupport.onCallback(post,item.getKey(),null);
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
