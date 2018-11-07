package com.example.fastjobs.firebase;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;

import com.example.fastjobs.Entity.Cart;
import com.example.fastjobs.Entity.Image;
import com.example.fastjobs.Entity.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;

public class LoginSupport extends BaseSupport{
    private static LoginSupport instance = null;
    public static LoginSupport getInstance(){
        if(instance == null){
            instance = new LoginSupport();
        }
        return instance;
    }
    public Task<AuthResult> login(final String email,final String password, final CallbackSupport callbackSupport){
        return auth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    callbackSupport.onCallback(true, email, null);
                    Log.e("LOGIN","Login success");
                }else {
                    callbackSupport.onCallback(false, email, null);
                    Log.e("LOGIN","Login fail - "+task.getException().getMessage());
                }
            }
        });
    }

    public Task<AuthResult> signUp(final String email, final String password, final CallbackSupport callbackSupport, final Context context){
        return auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    UserSupport userSupport = new UserSupport();
                    User user = new User();
                    user.setCarts(new ArrayList<Cart>());
                    user.setImages(new ArrayList<Image>());
                    user.setEmail(email);
                    userSupport.insert(user, context);
                    callbackSupport.onCallback(true, email, null);
                    Log.e("SIGNUP","SignUp success");
                }else {
                    Log.e("SIGNUP","SignUp fail - "+task.getException().getMessage());
                    callbackSupport.onCallback(false, email, null);
                }
            }
        });
    }

    public Task<Void> resetPassword(String email){
        return auth.sendPasswordResetEmail(email);
    }

    public void signOut() {
        auth.signOut();
    }

    public Task<Void> changePassword(String password){
        FirebaseUser user = auth.getCurrentUser();
        return user.updatePassword(password);
    }

    public Task<Void> changeEmail(String email){
        FirebaseUser user = auth.getCurrentUser();
        return user.updateEmail(email);
    }

    public boolean isLogin(){
        return auth.getCurrentUser() != null;
    }

    public String getCurrentUserEmail(){
        return auth.getCurrentUser().getEmail();
    }
}
