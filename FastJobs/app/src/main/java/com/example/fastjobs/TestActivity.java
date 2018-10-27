package com.example.fastjobs;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.fastjobs.entity.User;
import com.example.fastjobs.firebase.ImageSupport;
import com.example.fastjobs.firebase.LoginSupport;
import com.example.fastjobs.firebase.CallbackSupport;
import com.example.fastjobs.firebase.UserSupport;

import java.io.IOException;
import java.util.List;

public class TestActivity extends AppCompatActivity {

    private ImageView imageView;
    UserSupport userSupport;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        userSupport = new UserSupport();
        imageView = (ImageView) findViewById(R.id.imageView);
        TextView textView = findViewById(R.id.editTextTest1);
        textView.setText("datnqse04777@fpt.edu.vn");
    }

    public void test(View view){
//        LoginSupport loginSupport = new LoginSupport();
//        EditText email = findViewById(R.id.editTextTest1);
//        EditText password =findViewById(R.id.editTextTest2);
//        loginSupport.login(email.getText().toString(),password.getText().toString());
//        chooseImage();
        ImageSupport imageSupport = new ImageSupport();
        imageSupport.find("1e634e43-c96a-4a49-9ac0-5ebfead4b19a", new CallbackSupport<Bitmap>() {

            @Override
            public void onCallback(Bitmap bitmap, String key, List<Bitmap> bitmaps) {
                imageView.setImageBitmap(bitmap);
            }
        });
    }

    public void insert(View view){
//        userSupport.getAll(1, 3, new CallbackSupport<User>() {
//            @Override
//            public void onCallback(User user, String key, List<User> users) {
//                for(User u : users){
//                    System.out.println(u.getPhone());
//                }
//            }
//        });
        //FirebaseDatabase.getInstance().getReference().child("provice").setValue(null);
        ImageSupport imageSupport = new ImageSupport();
        imageSupport.upload(filePath, this, null, null, null);
    }

    private void chooseImage() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"),71);
    }

    private Uri filePath;
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 71 && resultCode == RESULT_OK
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
}
