package com.lacaba.tododroid.repository;

import java.util.ArrayList;
import java.util.Collections;

import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.lacaba.tododroid.controller.db.event.OnWriteFailedListener;
import com.lacaba.tododroid.controller.db.event.OnWriteSuccessListener;
import com.lacaba.tododroid.model.todo.BoardType;
import com.lacaba.tododroid.model.todo.ToDoList;
import com.lacaba.tododroid.util.Consumer;

public class ToDoListRepository {

    public static final String COLLECTION_NAME = "todolists";

    private FirebaseFirestore fstore;

    public ToDoListRepository(){
        fstore = FirebaseFirestore.getInstance();
    }

    public void writeToDoList(ToDoList todolist, OnWriteSuccessListener onSuccess, OnWriteFailedListener onFailed){
        DocumentReference docref = fstore.collection(COLLECTION_NAME).document();
        todolist.setId(docref.getId());
        docref
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

    public void getToDoOrder(String todolistId, BoardType type, Consumer<ArrayList<String>> onResult){
        fstore.collection(COLLECTION_NAME)
            .document(todolistId)
            .collection(type.toString().toLowerCase())
            .get()
            .addOnSuccessListener(docsnap -> {
                onResult.accept(new ArrayList<>(docsnap.toObjects(String.class)));
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
                ArrayList<ToDoList> todolists = new ArrayList<>(docsnap.toObjects(ToDoList.class));
                Collections.sort(todolists, (t1, t2) -> {
                    if(t1.getUpdateDate().before(t2.getUpdateDate()))
                        return 1;
                    if(t1.getUpdateDate().after(t2.getUpdateDate()))
                        return -1;
                    return 0;
                });
                onResult.accept(todolists);
            });
    }
    
}
