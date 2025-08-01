package com.lacaba.tododroid.controller.auth;

import java.util.ArrayList;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.lacaba.tododroid.model.ResourceRepository;
import com.lacaba.tododroid.model.user.User;
import com.lacaba.tododroid.repository.UserRepository;
import com.lacaba.tododroid.util.Consumer;
import com.lacaba.tododroid.view.activity.LoginActivity;

import android.app.Activity;
import android.content.Intent;

public class UserController {

    private ResourceRepository resourceRepository;
    private FirebaseAuth mAuth;
    private Activity activity;
    private OnAuthSuccessListener onAuthSuccessListener;
    private OnAuthFailedListener onAuthFailedListener;

    private UserRepository userRepository;

    public UserController(Activity activity){
        this.activity = activity;
        resourceRepository = ResourceRepository.getInstance();
        mAuth = FirebaseAuth.getInstance();
        resourceRepository.setCurFirebaseUser(mAuth.getCurrentUser());
        userRepository = new UserRepository();
    }

    public void getCurrentUserFromFirebaseUser(Consumer<User> onResult){
        FirebaseUser fUser = mAuth.getCurrentUser();
        resourceRepository.setCurFirebaseUser(fUser);
        userRepository.findUserById(fUser.getUid(), onResult);
    }

    public void login(String email, String password, Runnable onSuccess){
        mAuth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(activity, task -> {
                if(task.isSuccessful()){
                    FirebaseUser fUser = mAuth.getCurrentUser();
                    resourceRepository.setCurFirebaseUser(fUser);
                    if(onSuccess != null){
                        onSuccess.run();
                    }
                } else {
                    if(onAuthFailedListener != null){
                        onAuthFailedListener.onAuthFailed(task.getException());
                    }
                }
            });
    }

    public void signup(String email, String password){
        mAuth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(activity, task -> {
                if(task.isSuccessful()){
                    FirebaseUser fUser = mAuth.getCurrentUser();
                    User user = new User.Builder()
                        .id(fUser.getUid())
                        .username("User")
                        .setTodolists(new ArrayList<>())
                        .build();
                    userRepository.writeUser(user, null,
                            e -> {
                                if(onAuthFailedListener != null){
                                    onAuthFailedListener.onAuthFailed(e);
                                    resourceRepository.setCurUser(user);
                                    return;
                                }
                            });
                    if(onAuthSuccessListener != null){
                        onAuthSuccessListener.onAuthSuccess(fUser);
                    }
                } else {
                    task.getException().printStackTrace();
                    if(onAuthFailedListener != null){
                        onAuthFailedListener.onAuthFailed(task.getException());
                    }
                }
            });
    }

    public boolean isUserLoggedIn(){
        FirebaseUser user = mAuth.getCurrentUser();
        if(user != null)
            return true;
        return false;
    }

    public void logout(){
        FirebaseUser user = mAuth.getCurrentUser();
        if(user != null){
            mAuth.signOut();
            Intent intent = new Intent(activity, LoginActivity.class);
            activity.startActivity(intent);
            activity.finish();
        }
    }

    public void updateUsername(String username, Runnable onSuccess){
        UserProfileChangeRequest request  = new UserProfileChangeRequest.Builder()
            .setDisplayName(username).build();
        FirebaseUser fUser = mAuth.getCurrentUser();
        fUser.updateProfile(request)
            .addOnCompleteListener(task -> {
                if(task.isSuccessful()){
                    User user = resourceRepository.getCurUser();
                    user.setUsername(username);
                    userRepository.updateUser(user, null, null);
                    if(onSuccess != null)
                        onSuccess.run();
                } else {
                    if(onAuthFailedListener != null){
                        onAuthFailedListener.onAuthFailed(task.getException());
                    }
                }
            });
    }

    public void setOnAuthSuccessListener(OnAuthSuccessListener onAuthSuccessListener) {
        this.onAuthSuccessListener = onAuthSuccessListener;
    }

    public void setOnAuthFailedListener(OnAuthFailedListener onAuthFailedListener) {
        this.onAuthFailedListener = onAuthFailedListener;
    }

}
