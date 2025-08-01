package com.lacaba.tododroid.model.todo;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.google.firebase.firestore.DocumentId;
import com.google.firebase.firestore.Exclude;
import com.google.firebase.firestore.PropertyName;
import com.google.firebase.firestore.ServerTimestamp;

public class ToDoList {

    @DocumentId
    private String id;

    @PropertyName("name")
    private String name;

    @PropertyName("user_id")
    private String userId;

    @PropertyName("todo_todos")
    private ArrayList<String> todo;
    @PropertyName("doing_todos")
    private ArrayList<String> doing;
    @PropertyName("done_todos")
    private ArrayList<String> done;

    @PropertyName("update_date")
    @ServerTimestamp
    private Date updateDate;

    public ToDoList(){}

    private ToDoList(Builder builder){
        id = builder.id;
        name = builder.name;
        userId = builder.userId;
        updateDate = builder.updateDate;
        todo = builder.todo;
        doing = builder.doing;
        done = builder.done;
    }

    public String getName() {
        return name;
    }

    public String getId() {
        return id;
    }

    public String getUserId() {
        return userId;
    }

    public ArrayList<String> getTodo() {
        return todo;
    }

    public ArrayList<String> getDoing() {
        return doing;
    }

    public ArrayList<String> getDone() {
        return done;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    public void setTodo(ArrayList<String> todo) {
        this.todo = todo;
    }

    public void setDoing(ArrayList<String> doing) {
        this.doing = doing;
    }

    public void setDone(ArrayList<String> done) {
        this.done = done;
    }

    @Exclude
    public Map<String, Object> getUpdateMap(){
        Map<String, Object> map = new HashMap<>();
        map.put("name", name);
        map.put("updateDate", updateDate);
        map.put("todo", todo);
        map.put("doing", doing);
        map.put("done", done);
        return map;
    }

    public static class Builder {

        private String id;
        private String name;
        private String userId;
        private Date updateDate;
        private ArrayList<String> todo;
        private ArrayList<String> doing;
        private ArrayList<String> done;

        public Builder(){
            id = userId = name = "";
            done = doing = todo = null;
            updateDate = null;
        }

        public Builder id(String id){
            this.id = id;
            return this;
        }

        public Builder name(String name){
            this.name = name;
            return this;
        }

        public Builder userId(String userId){
            this.userId = userId;
            return this;
        }

        public Builder todo(ArrayList<String> todo) {
            this.todo = todo;
            return this;
        }

        public Builder doing(ArrayList<String> doing) {
            this.doing = doing;
            return this;
        }

        public Builder done(ArrayList<String> done) {
            this.done = done;
            return this;
        }

        public Builder updateDate(Date updateDate) {
            this.updateDate = updateDate;
            return this;
        }

        public ToDoList build(){
            return new ToDoList(this);
        }

    }

}
