package com.lacaba.tododroid.model.todo;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.google.firebase.firestore.DocumentId;
import com.google.firebase.firestore.Exclude;
import com.google.firebase.firestore.PropertyName;
import com.google.firebase.firestore.ServerTimestamp;

public class ToDo {

    @DocumentId
    private String id;

    @PropertyName("todolist_id")
    private String todolistId;

    private String title;
    private String description;

    private boolean isDone;

    @PropertyName("due_date")
    @ServerTimestamp
    private Date dueDate;

    @PropertyName("board_type")
    private BoardType boardType;
    
    public ToDo(){}

    private ToDo(Builder builder){
        this.id = builder.id;
        this.todolistId = builder.todolistId;
        this.title = builder.title;
        this.description = builder.description;
        this.isDone = builder.isDone;
        this.dueDate = builder.dueDate;
        this.boardType = builder.boardType;
    }

    public String getId() {
        return id;
    }

    public String getTodolistId() {
        return todolistId;
    }

    public String getTitle() {
        return title;
    }

    public Date getDueDate() {
        return dueDate;
    }

    public String getDescription() {
        return description;
    }

    public boolean isDone() {
        return isDone;
    }

    public BoardType getBoardType() {
        return boardType;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setTodolistId(String todolistId) {
        this.todolistId = todolistId;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setDone(boolean done) {
        isDone = done;
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }

    public void setBoardType(BoardType boardType) {
        this.boardType = boardType;
    }

    @Exclude
    public Map<String, Object> getUpdateMap(){
        Map<String, Object> map = new HashMap<>();
        map.put("title", title);
        map.put("todolistId", todolistId);
        map.put("description", description);
        map.put("done", isDone);
        map.put("dueDate", dueDate);
        map.put("boardType", boardType);
        return map;
    }

    public static class Builder {

        private String id;
        private String todolistId;
        private String title;
        private String description;
        private boolean isDone;
        private Date dueDate;
        private BoardType boardType;
    
        public Builder(){
            isDone = false;
            todolistId = id = title = description = "";
            dueDate = null;
            boardType = BoardType.TODO;
        }

        public Builder id(String id){
            this.id = id;
            return this;
        }

        public Builder title(String title){
            this.title = title;
            return this;
        }

        public Builder description(String description){
            this.description = description;
            return this;
        }

        public Builder isDone(boolean isDone){
            this.isDone = isDone;
            return this;
        }

        public Builder dueDate(Date dueDate){
            this.dueDate = dueDate;
            return this;
        }

        public Builder boardType(BoardType boardType) {
            this.boardType = boardType;
            return this;
        }

        public Builder todolistId(String todolistId) {
            this.todolistId = todolistId;
            return this;
        }

        public ToDo build(){
            return new ToDo(this);
        }

    }

}
