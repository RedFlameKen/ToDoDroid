package com.lacaba.tododroid.repository;

import com.google.firebase.firestore.FirebaseFirestore;
import com.lacaba.tododroid.controller.db.event.OnWriteFailedListener;
import com.lacaba.tododroid.controller.db.event.OnWriteSuccessListener;
import com.lacaba.tododroid.model.user.User;
import com.lacaba.tododroid.util.Consumer;

public class UserRepository {

    public static final String COLLECTION_NAME = "users";

    private FirebaseFirestore fstore;

    public UserRepository(){
        fstore = FirebaseFirestore.getInstance();
    }

    public void writeUser(User user, OnWriteSuccessListener onSuccess, OnWriteFailedListener onFailed){
        fstore.collection(COLLECTION_NAME).document(user.getId()).set(user)
            .addOnSuccessListener(docref -> {
                if(onSuccess != null)
                    onSuccess.onWriteSuccess(null);
            })
            .addOnFailureListener(e -> {
                if(onFailed != null)
                    onFailed.onWriteFailed(e);
            });
    }

    public void findUserById(String uid, Consumer<User> onResult){
        fstore.collection(COLLECTION_NAME)
            .document(uid)
            .get()
            .addOnSuccessListener(datasnap -> {
                onResult.accept(datasnap.toObject(User.class));
            });
    }

    public void updateUser(User user, OnWriteSuccessListener onSuccess, OnWriteFailedListener onFailed){
        fstore.collection(COLLECTION_NAME)
            .document(user.getId())
            .update(user.getUpdateMap())
            .addOnSuccessListener(v -> {
                if(onSuccess != null){
                    onSuccess.onWriteSuccess(null);
                }
            })
            .addOnFailureListener(e -> {
                onFailed.onWriteFailed(e);
            });
    }

    public void deleteUserById(String uid, OnWriteSuccessListener onSuccess, OnWriteFailedListener onFailed){
        fstore.collection(COLLECTION_NAME)
            .document(uid)
            .delete()
            .addOnSuccessListener(v -> {
                if(onSuccess != null){
                    onSuccess.onWriteSuccess(null);
                }
            })
            .addOnFailureListener(e -> {
                onFailed.onWriteFailed(e);
            });
    }
    
}
