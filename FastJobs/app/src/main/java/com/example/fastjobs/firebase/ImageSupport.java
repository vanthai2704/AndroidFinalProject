package com.example.fastjobs.firebase;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.widget.Toast;

import com.example.fastjobs.Entity.Image;
import com.example.fastjobs.Entity.Province;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ImageSupport extends BaseSupport{
    private DatabaseReference dbImage;
    StorageReference storageReference;
    public ImageSupport(){
        dbImage = db.child("image");
        storageReference = FirebaseStorage.getInstance().getReference();
    }
    public void upload(Uri filePath, final Context context, String post_id, String user_id, String category_id){
        final Image image = new Image("A",post_id,user_id, category_id);
        image.setImage_id(UUID.randomUUID().toString());
        final ProgressDialog progressDialog = new ProgressDialog(context);
        progressDialog.setTitle("Uploading...");
        progressDialog.show();

        StorageReference ref = storageReference.child("image/"+ image.getImage_id());
        ref.putFile(filePath)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        progressDialog.dismiss();
                        insert(image);
                        Toast.makeText(context, "Uploaded", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        progressDialog.dismiss();
                        Toast.makeText(context, "Failed "+e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                        double progress = (100.0*taskSnapshot.getBytesTransferred()/taskSnapshot
                                .getTotalByteCount());
                        progressDialog.setMessage("Uploaded "+(int)progress+"%");
                    }
                });
    }

    public void insert(Image image){
        dbImage.child(image.getImage_id()).setValue(image).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                e.printStackTrace();
            }
        });
    }

    public void getAll(){

    }

    public void delete(){

    }

    public void update(){

    }

    public void get(final String image_id, final CallbackSupport callbackSupport){
        StorageReference islandRef = storageReference.child("image").child(image_id);
        final long HUNDRED_MEGABYTE = 10240 * 10240;
        islandRef.getBytes(HUNDRED_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
            @Override
            public void onSuccess(byte[] bytes) {
                Bitmap image = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                callbackSupport.onCallback(image, image_id, null);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle any errors
            }
        });
    }

    public void getImageUser(final String user_id, final CallbackSupport callbackSupport){
        dbImage.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                List<Bitmap> images = new ArrayList<>();
                for (DataSnapshot item : dataSnapshot.getChildren())
                {
                    if(item.getValue(Image.class).getUser_id().equalsIgnoreCase(user_id)){
                        callbackSupport.onCallback(null, user_id, images);
                        break;
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void getImagePost(final String post_id, final CallbackSupport callbackSupport){
        dbImage.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                List<Bitmap> images = new ArrayList<>();
                for (DataSnapshot item : dataSnapshot.getChildren())
                {
                    if(item.getValue(Image.class).getPost_id().equalsIgnoreCase(post_id)){
                        callbackSupport.onCallback(null, post_id, images);
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
