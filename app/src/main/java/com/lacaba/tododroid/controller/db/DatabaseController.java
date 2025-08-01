package com.lacaba.tododroid.controller.db;

import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.lacaba.tododroid.controller.db.event.OnWriteFailedListener;
import com.lacaba.tododroid.controller.db.event.OnWriteSuccessListener;
import com.lacaba.tododroid.model.todo.ToDo;
import com.lacaba.tododroid.model.todo.ToDoList;

public class DatabaseController {

    private FirebaseFirestore fstore;
    private FirebaseUser user;

    private OnWriteSuccessListener onWriteSuccessListener;
    private OnWriteFailedListener onWriteFailedListener;

    public DatabaseController(FirebaseUser user){
        this.user = user;
        fstore = FirebaseFirestore.getInstance();
    }

    public void writeToDoList(ToDoList todolist){
        todolist.setUserId(user.getUid());
        fstore.collection("todolists")
            .document()
            .set(todolist)
            .addOnSuccessListener(v -> {
                if(onWriteSuccessListener != null)
                    onWriteSuccessListener.onWriteSuccess(null);
            })
            .addOnFailureListener(e -> {
                if(onWriteFailedListener != null)
                    onWriteFailedListener.onWriteFailed(e);
            });
    }

    public void updateToDoList(ToDoList todolist){
        fstore.collection("todolists")
            .document(todolist.getId())
            .update(todolist.getUpdateMap())
            .addOnSuccessListener(v -> {
                if(onWriteSuccessListener != null)
                    onWriteSuccessListener.onWriteSuccess(null);
            })
            .addOnFailureListener(e -> {
                if(onWriteFailedListener != null)
                    onWriteFailedListener.onWriteFailed(e);
            });
    }

    public void addToDo(String todolistId, ToDo todo){
        String document;
        switch (todo.getBoardType()) {
            case TODO:
                document = "todo_todos";
                break;
            case DOING:
                document = "doing_todos";
                break;
            case DONE:
                document = "done_todos";
                break;
            default:
                document = null;
                break;
        }
        fstore.collection("todolists")
            .document(todolistId)
            .update(document, FieldValue.arrayUnion(todo));
    }

    public void setOnWriteSuccessListener(OnWriteSuccessListener onWriteSuccessListener) {
        this.onWriteSuccessListener = onWriteSuccessListener;
    }

    public void setOnWriteFailedListener(OnWriteFailedListener onWriteFailedListener) {
        this.onWriteFailedListener = onWriteFailedListener;
    }

}
