package com.lacaba.tododroid.repository;

import java.util.ArrayList;

import com.google.firebase.firestore.FirebaseFirestore;
import com.lacaba.tododroid.controller.db.event.OnWriteFailedListener;
import com.lacaba.tododroid.controller.db.event.OnWriteSuccessListener;
import com.lacaba.tododroid.model.todo.BoardType;
import com.lacaba.tododroid.model.todo.ToDo;
import com.lacaba.tododroid.util.Consumer;

public class ToDoRepository {

    public static final String COLLECTION_NAME = "todos";

    private FirebaseFirestore fstore;

    public ToDoRepository(){
        fstore = FirebaseFirestore.getInstance();
    }

    public void writeToDo(ToDo todo, OnWriteSuccessListener onSuccess, OnWriteFailedListener onFailed){
        fstore.collection(COLLECTION_NAME)
            .document()
            .set(todo)
            .addOnSuccessListener(v -> {
                if(onSuccess != null)
                    onSuccess.onWriteSuccess(null);
            })
            .addOnFailureListener(e -> {
                if(onFailed != null)
                    onFailed.onWriteFailed(e);
            });
    }

    public void findToDoById(String todoId, Consumer<ToDo> onResult){
        fstore.collection(COLLECTION_NAME)
                .document(todoId)
                .get()
                .addOnSuccessListener(docsnap -> {
                    onResult.accept(docsnap.toObject(ToDo.class));
                });
    }

    public void getToDosByBoardType(String todolistId, BoardType type, Consumer<ArrayList<ToDo>> onResult){
        fstore.collection(COLLECTION_NAME)
                .whereEqualTo("todolistId", todolistId)
                .whereEqualTo("boardType", type)
                .get()
                .addOnSuccessListener(docsnap -> {
                    onResult.accept(new ArrayList<>(docsnap.toObjects(ToDo.class)));
                });
    }

    public void updateToDo(ToDo todo, OnWriteSuccessListener onSuccess, OnWriteFailedListener onFailed){
        fstore.collection(COLLECTION_NAME)
            .document(todo.getId())
            .update(todo.getUpdateMap())
            .addOnSuccessListener(v -> {
                if(onSuccess != null)
                    onSuccess.onWriteSuccess(null);
            })
            .addOnFailureListener(e -> {
                if(onFailed != null)
                    onFailed.onWriteFailed(e);
            });
    }
    
    public void deleteToDoById(String todoId, OnWriteSuccessListener onSuccess, OnWriteFailedListener onFailed){
        fstore.collection(COLLECTION_NAME)
            .document(todoId)
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
    
}
