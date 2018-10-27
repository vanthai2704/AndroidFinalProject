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
    public void get(final String post_id, final CallbackSupport callbackSupport){
        dbPost.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot item : dataSnapshot.getChildren())
                {
                    if(item.getKey().equalsIgnoreCase(post_id)){
                        callbackSupport.onCallback(item.getValue(Post.class),post_id,null);
                        break;
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void search(final Post post,final int page, final int pageSize, final CallbackSupport callbackSupport){
        dbPost.addValueEventListener(new ValueEventListener() {
            int index = 1;
            List<Post> posts = new ArrayList<>();
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot item : dataSnapshot.getChildren())
                {
                    Post postItem = item.getValue(Post.class);
                    if(post.getCommune_id() == null || (postItem.getCommune_id() != null
                            && postItem.getCommune_id().toLowerCase().contains(post.getCommune_id().toLowerCase()))){
                        if(post.getCategory_id() == null || (postItem.getCategory_id() != null
                                && postItem.getCategory_id().toLowerCase().contains(post.getCategory_id().toLowerCase()))){
                            if(post.getUser_id() == null || (postItem.getUser_id() != null
                                    && postItem.getUser_id().toLowerCase().contains(post.getUser_id().toLowerCase()))){
                                if(post.getLocation_coordinate() == null || (postItem.getLocation_coordinate() != null
                                        && postItem.getLocation_coordinate().toLowerCase().contains(post.getLocation_coordinate().toLowerCase()))){
                                    if(post.getPost_title() == null || (postItem.getPost_title() != null
                                            && postItem.getPost_title().toLowerCase().contains(post.getPost_title().toLowerCase()))){
                                        if(post.getPost_content() == null || (postItem.getPost_content() != null
                                                && postItem.getPost_content().toLowerCase().contains(post.getPost_content().toLowerCase()))){
                                            if(post.getPost_status() == null || (postItem.getPost_status() != null
                                                    && postItem.getPost_status().toLowerCase().contains(post.getPost_status().toLowerCase()))){
                                                if(index>(page-1)*pageSize && index<= page*pageSize){
                                                    posts.add(postItem);
                                                }
                                                index++;
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
                callbackSupport.onCallback(null, null, posts);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}