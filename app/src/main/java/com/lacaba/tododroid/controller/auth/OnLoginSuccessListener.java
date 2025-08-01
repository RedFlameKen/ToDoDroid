package com.lacaba.tododroid.controller.auth;

import com.google.firebase.auth.FirebaseUser;

@FunctionalInterface
public interface OnLoginSuccessListener {

    public void onLoginSuccess(FirebaseUser firebaseUser);
    
}
