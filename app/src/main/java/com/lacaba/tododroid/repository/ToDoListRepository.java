package com.lacaba.tododroid.repository;

import java.util.ArrayList;

import com.google.firebase.firestore.FirebaseFirestore;
import com.lacaba.tododroid.controller.db.event.OnWriteFailedListener;
import com.lacaba.tododroid.controller.db.event.OnWriteSuccessListener;
import com.lacaba.tododroid.model.todo.ToDoList;
import com.lacaba.tododroid.util.Consumer;

public class ToDoListRepository {

    public static final String COLLECTION_NAME = "todolists";

    private FirebaseFirestore fstore;

    public ToDoListRepository(){
        fstore = FirebaseFirestore.getInstance();
    }

    public void writeToDoList(ToDoList todolist, OnWriteSuccessListener onSuccess, OnWriteFailedListener onFailed){
        fstore.collection(COLLECTION_NAME)
            .document()
            .set(todolist)
            .addOnSuccessListener(v -> {
                if(onSuccess != null)
                    onSuccess.onWriteSuccess(null);
            })
            .addOnFailureListener(e -> {
                if(onFailed != null)
                    onFailed.onWriteFailed(e);
            });
    }

    public void findToDoListById(String todolistId, Consumer<ToDoList> onResult){
        fstore.collection(COLLECTION_NAME)
                .document(todolistId)
                .get()
                .addOnSuccessListener(docsnap -> {
                    onResult.accept(docsnap.toObject(ToDoList.class));
                });
    }

    public void updateToDoList(ToDoList todolist, OnWriteSuccessListener onSuccess, OnWriteFailedListener onFailed){
        fstore.collection(COLLECTION_NAME)
            .document(todolist.getId())
            .update(todolist.getUpdateMap())
            .addOnSuccessListener(v -> {
                if(onSuccess != null)
                    onSuccess.onWriteSuccess(null);
            })
            .addOnFailureListener(e -> {
                if(onFailed != null)
                    onFailed.onWriteFailed(e);
            });
    }
    
    public void deleteToDoListById(String todolistId, OnWriteSuccessListener onSuccess, OnWriteFailedListener onFailed){
        fstore.collection(COLLECTION_NAME)
            .document(todolistId)
            .delete()
            .addOnSuccessListener(v -> {
                if(onSuccess != null)
                    onSuccess.onWriteSuccess(null);
            })
            .addOnFailureListener(e -> {
                if(onFailed != null)
                    onFailed.onWriteFailed(e);
            });
    }

    public void getToDoListsByUID(String userId, Consumer<ArrayList<ToDoList>> onResult){
        fstore.collection(COLLECTION_NAME)
            .whereEqualTo("userId", userId)
            .get()
            .addOnSuccessListener(docsnap -> {
                onResult.accept(new ArrayList<>(docsnap.toObjects(ToDoList.class)));
            });
    }
    
}
