package com.lacaba.tododroid.model;

import com.google.firebase.auth.FirebaseUser;
import com.lacaba.tododroid.model.user.User;

public class ResourceRepository {

    private User curUser;
    private FirebaseUser curFirebaseUser;

    private ResourceRepository(){
    }

    private static ResourceRepository INSTANCE;

    public static ResourceRepository getInstance(){
        if(INSTANCE == null){
            INSTANCE = new ResourceRepository();
        }
        return INSTANCE;
    }

    public FirebaseUser getCurFirebaseUser() {
        return curFirebaseUser;
    }

    public void setCurFirebaseUser(FirebaseUser curFirebaseUser) {
        this.curFirebaseUser = curFirebaseUser;
    }

    public void setCurUser(User curUser) {
        this.curUser = curUser;
    }

    public User getCurUser() {
        return curUser;
    }
    
}
