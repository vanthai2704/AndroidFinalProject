package com.example.fastjobs.firebase;

import android.support.annotation.NonNull;
import android.util.Log;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseUser;

public class LoginSupport extends BaseSupport{
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

    public Task<AuthResult> signUp(final String email,final String password, final CallbackSupport callbackSupport){
        return auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    callbackSupport.onCallback(true, email, null);
                    Log.e("SIGNUP","SignUp success");
                }else {
                    callbackSupport.onCallback(false, email, null);
                    Log.e("SIGNUP","SignUp fail - "+task.getException().getMessage());
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
}
