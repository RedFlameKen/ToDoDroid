package com.lacaba.tododroid.controller.auth;

import com.google.firebase.auth.FirebaseUser;

@FunctionalInterface
public interface OnSignupSuccessListener {

    public void onSignupSuccess(FirebaseUser firebaseUser);
    
}
