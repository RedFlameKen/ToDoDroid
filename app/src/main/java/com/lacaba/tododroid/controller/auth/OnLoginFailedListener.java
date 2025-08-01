package com.lacaba.tododroid.controller.auth;

import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;

@FunctionalInterface
public interface OnLoginFailedListener {

    public void onLoginFailedListener(Task<AuthResult> task);
    
}
