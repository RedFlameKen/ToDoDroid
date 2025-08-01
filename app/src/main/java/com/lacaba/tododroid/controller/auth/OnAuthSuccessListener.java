package com.lacaba.tododroid.controller.auth;

import com.google.firebase.auth.FirebaseUser;

@FunctionalInterface
public interface OnAuthSuccessListener {

    public void onAuthSuccess(FirebaseUser user);
    
}
