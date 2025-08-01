package com.lacaba.tododroid.controller.auth;

@FunctionalInterface
public interface OnAuthFailedListener {

    public void onAuthFailed(Exception e);
    
}
