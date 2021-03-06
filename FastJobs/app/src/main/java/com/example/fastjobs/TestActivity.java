package com.example.fastjobs;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;


import com.example.fastjobs.Entity.Cart;
import com.example.fastjobs.Entity.Image;
import com.example.fastjobs.Entity.Post;
import com.example.fastjobs.Entity.User;
import com.example.fastjobs.firebase.ImageSupport;
import com.example.fastjobs.firebase.LoginSupport;
import com.example.fastjobs.firebase.CallbackSupport;
import com.example.fastjobs.firebase.PostSupport;
import com.example.fastjobs.firebase.UserSupport;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TestActivity extends AppCompatActivity {

    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        imageView = (ImageView) findViewById(R.id.imageView);
        TextView textView = findViewById(R.id.editTextTest1);
        textView.setText("datnqse04777@fpt.edu.vn");
    }


    /********************** EXAMPLE FOR DB SUPPORT ***************************/
    // This is how to use DB support

    /*---- This for Login ----*/
    public void signUpOnclick(View view){
        LoginSupport loginSupport = new LoginSupport();
        loginSupport.signUp("datnqse04777@fpt.edu.vn", "12345678", new CallbackSupport<Boolean>() {
            @Override
            public void onCallback(Boolean aBoolean, String key, List<Boolean> booleans) {
                // Must code any thing depend in this
                // ...
                // ...
            }
        }, this);
    }

    public void loginOnclick(View view){
        LoginSupport loginSupport = new LoginSupport();
        loginSupport.login("datnqse04777@fpt.edu.vn", "12345678", new CallbackSupport<Boolean>() {
            @Override
            public void onCallback(Boolean aBoolean, String key, List<Boolean> booleans) {
                // Must code any thing depend in this
                // ...
                // ...
            }
        });
    }

    public void signOutOnclick(View view){
        LoginSupport loginSupport = new LoginSupport();
        loginSupport.signOut();
    }

    public boolean checkLogined(){
        LoginSupport loginSupport = new LoginSupport();
        return loginSupport.isLogin();
    }
    /*------------------------------------------------------------------------*/


    /*---- This for User information ----*/
    public void insertUser(View view){
        UserSupport userSupport = new UserSupport();
        userSupport.insert(new User("Nguyen Quang Dat","datnqse04777@fpt.edu.vn","0965658574",new Date(), new ArrayList<Image>(), new ArrayList<Cart>()), this);

        // Need insert avatar code like this
        userSupport.get("datnqse04777@fpt.edu.vn", new CallbackSupport<User>() {
            @Override
            public void onCallback(User user, String key, List<User> users) {
                ImageSupport imageSupport = new ImageSupport();
                imageSupport.upload(filePath, getApplicationContext(), new CallbackSupport() {
                    @Override
                    public void onCallback(Object o, String key, List list) {

                    }
                });
            }
        });

    }

    public void updateUser(View view){
        // Can NOT change email because email is key
        UserSupport userSupport = new UserSupport();
        User newUser = new User("New Nguyen Quang Dat","datnqse04777@fpt.edu.vn","0123456789",new Date(), new ArrayList<Image>(), new ArrayList<Cart>());
        userSupport.update(newUser);
    }

    public void getUserByEmail(View view){
        UserSupport userSupport = new UserSupport();
        userSupport.get("datnqse04777@fpt.edu.vn", new CallbackSupport<User>() {
            @Override
            public void onCallback(User user, String key, List<User> users) {
                // Must code any thing depend in this
                // ...
                // ...
            }
        });
    }
    /*------------------------------------------------------------------------*/

    /*---- This for Image ----*/
    private Uri filePath;

    public void chooseImage(View view) {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"),123);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 123 && resultCode == RESULT_OK
                && data != null && data.getData() != null )
        {
            filePath = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                imageView.setImageBitmap(bitmap);
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }
    }

    public void uploadImageToServer(View view){
        ImageSupport imageSupport = new ImageSupport();
        imageSupport.upload(filePath, this, new CallbackSupport() {
            @Override
            public void onCallback(Object o, String key, List list) {

            }
        });
        // Change null to id of where image belong to
    }

    public void getImageFromServer(View view){
        ImageSupport imageSupport = new ImageSupport();
        imageSupport.get("1e634e43-c96a-4a49-9ac0-5ebfead4b19a", new CallbackSupport<Bitmap>() {

            @Override
            public void onCallback(Bitmap bitmap, String key, List<Bitmap> bitmaps) {
                imageView.setImageBitmap(bitmap);
            }
        });
    }

    /*------------------------------------------------------------------------*/

    public void post(View view){
        PostSupport postSupport = new PostSupport();
        Image image = new Image("A","AVATAR", filePath);
        List<Image> images = new ArrayList<>();
        images.add(image);
        Post post = new Post();
        post.setImages(images);
        postSupport.insert(post, this);
    }

}
